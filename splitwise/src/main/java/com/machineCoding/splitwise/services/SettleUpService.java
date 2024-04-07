package com.machineCoding.splitwise.services;

import com.machineCoding.splitwise.models.Expense;
import com.machineCoding.splitwise.models.ExpenseUser;
import com.machineCoding.splitwise.models.Group;
import com.machineCoding.splitwise.models.User;
import com.machineCoding.splitwise.repositories.ExpenseRepository;
import com.machineCoding.splitwise.repositories.ExpenseUserRepsository;
import com.machineCoding.splitwise.repositories.GroupRepository;
import com.machineCoding.splitwise.repositories.UserRespository;
import com.machineCoding.splitwise.strategies.SettleUpStrategy;
import com.machineCoding.splitwise.strategies.TwoHeapStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private ExpenseUserRepsository expenseUserRepsository;

    private TwoHeapStrategy settleUpStrategy;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    public List<Transaction> settleUpUser(Long userId)
    {   settleUpStrategy = new TwoHeapStrategy();
        /*
        * 1. Get all the expenses that this user id part of
        * 2. Iterate through all the expense and find out all the people involved
        * and how much is owed/owes for each
        * 3. Apply the min max heap algo to calc the list of transactions
        * 4. Return the list of transactions
        * */
        Optional<User> userOptional = userRespository.findById(userId);
        if(userOptional.isEmpty())
            throw new RuntimeException();

        User user = userOptional.get();

        List<ExpenseUser> expenseUsers = expenseUserRepsository.findAllByUser(user);
        List<Expense> expenses = new ArrayList<>();
        Set<Expense> expenseSet = new HashSet<>();
        for(ExpenseUser expenseUser : expenseUsers)
        {
            //expenses.add(expenseUser.getExpense());
            expenseSet.add(expenseUser.getExpense());
        }

        List<Transaction> transactions = settleUpStrategy.settle(expenseSet);


        List<Transaction> filteredTransaction = new ArrayList<>();

        for(Transaction transaction : transactions)
        {
            if(transaction.getFrom().getId()==user.getId() || transaction.getTo().getId()==user.getId())
            {
                filteredTransaction.add(transaction);
            }
        }


        return filteredTransaction;

    }
//    public List<Transaction> settleUpGroup(Long groupId)
//    {
//        Optional<Group> groupOptional = groupRepository.findById(groupId);
//        if(groupOptional.isEmpty())
//            throw new RuntimeException();
//
//        Group group = groupOptional.get();
//        List<Expense> expenses = expenseRepository.findAllByGroup(group);
//
//        List<Transaction> transactions = settleUpStrategy.settle(expense);
//        return transactions;
//    }
}
