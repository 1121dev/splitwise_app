package com.machineCoding.splitwise.dto;

import com.machineCoding.splitwise.models.Expense;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpenseResponseDto {
    private Expense expense;
}
