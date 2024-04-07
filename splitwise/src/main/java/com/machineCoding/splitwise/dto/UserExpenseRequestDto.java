package com.machineCoding.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class UserExpenseRequestDto {
    Long creator;
    Map<Long,Integer> payingUsers;
    List<Long> expenseParticipants;
}
