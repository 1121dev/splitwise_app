package com.machineCoding.splitwise.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity

public class Expense extends BaseModel{
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;
    private int amount;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "expense")
    @Fetch(FetchMode.JOIN)
    private List<ExpenseUser> expenseUserList;
}
