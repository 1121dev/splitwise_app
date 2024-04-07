package com.machineCoding.splitwise.strategies;

import com.machineCoding.splitwise.models.Expense;
import com.machineCoding.splitwise.services.Transaction;

import java.util.List;
import java.util.Set;

public interface SettleUpStrategy {
    public List<Transaction> settle(Set<Expense> expenseSet);
}
