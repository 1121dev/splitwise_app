package com.machineCoding.splitwise.controllers;

import com.machineCoding.splitwise.services.SettleUpService;
import com.machineCoding.splitwise.services.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.machineCoding.splitwise.dto.SettleUpUserRequestDto;
import com.machineCoding.splitwise.dto.SettleUpUserResponseDto;

import java.util.List;

@Controller

public class SettleUpController {
    @Autowired
    private SettleUpService settleUpService;
    public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto request) {
        SettleUpUserResponseDto response = new SettleUpUserResponseDto();

        List<Transaction> transactionList = settleUpService.settleUpUser(request.getUserId());

        response.setTransactions(transactionList);
        return response;
    }

    public void settleUpGroup() {

    }
}
