package com.machineCoding.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExpenseUser extends BaseModel{
    @ManyToOne(fetch = FetchType.EAGER)
    private Expense expense;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseUserType expenseUserType;
    private int amount;
}
