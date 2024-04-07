package com.machineCoding.splitwise.controllers;

import com.machineCoding.splitwise.dto.UserExpenseRequestDto;
import com.machineCoding.splitwise.dto.UserExpenseResponseDto;
import com.machineCoding.splitwise.models.Expense;
import com.machineCoding.splitwise.services.UserExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserExpenseController {
    @Autowired
    private UserExpenseService userExpenseService;
    public UserExpenseResponseDto getExpenseResponse(UserExpenseRequestDto request)
    {
        Expense expense = userExpenseService.addExpense(request);
        UserExpenseResponseDto responseDto = new UserExpenseResponseDto();
        responseDto.setExpense(expense);
        return responseDto;
    }
}
