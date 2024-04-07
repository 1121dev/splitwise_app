package com.machineCoding.splitwise.dto;

import com.machineCoding.splitwise.services.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SettleUpUserResponseDto {
    private List<Transaction> transactions;
}
