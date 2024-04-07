package com.machineCoding.splitwise.services;

import com.machineCoding.splitwise.dto.UserExpenseRequestDto;
import com.machineCoding.splitwise.models.*;
import com.machineCoding.splitwise.repositories.ExpenseRepository;
import com.machineCoding.splitwise.repositories.ExpenseUserRepsository;
import com.machineCoding.splitwise.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private ExpenseUserRepsository expenseUserRepsository;

    public Expense addExpense(UserExpenseRequestDto request)
    {
        Expense expense = new Expense();
        expense.setExpenseType(ExpenseType.NORMAL);
        expense.setDescription("Dummy Expense");
        expense.setExpenseUserList(new ArrayList<>());
        expense.setAmount(0);
        expense.setGroup(null);
        expense.setCreatedBy(null);

        // Save the Expense entity
        Expense newExpense = expenseRepository.save(expense);

        // Fetch the expense creator
        Optional<User> expenseCreator = userRespository.findById(request.getCreator());
        if (expenseCreator.isEmpty())
            throw new RuntimeException("User not available");

        User createdBy = expenseCreator.get();
        newExpense.setCreatedBy(createdBy);

        // Process paying users
        processPayingUsers(request.getPayingUsers(), newExpense);

        // Process expense participants
        processExpenseParticipants(request.getExpenseParticipants(), newExpense);

        // Save the updated Expense entity with populated expenseUserList
        return expenseRepository.save(newExpense);
    }

        private void processPayingUsers(Map<Long, Integer> payingUsers, Expense expense)
        {
        int expenseAmount = 0;
        List<ExpenseUser> expenseUserList = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : payingUsers.entrySet())
        {
            User currentUser = userRespository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + entry.getKey()));

            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setExpense(expense);
            expenseUser.setUser(currentUser);
            expenseUser.setAmount(entry.getValue());
            expenseUser.setExpenseUserType(ExpenseUserType.PAID);

            expenseAmount += entry.getValue();
            expenseUserList.add(expenseUserRepsository.save(expenseUser));
        }
        expense.setAmount(expenseAmount);
        expense.getExpenseUserList().addAll(expenseUserList);
        }

        private void processExpenseParticipants(List<Long> expenseParticipants, Expense expense)
        {
        int noOfParticipants = expenseParticipants.size();
        int toPayAmount = expense.getAmount() / noOfParticipants;
        List<ExpenseUser> expenseUserList = expense.getExpenseUserList();
            for (Long id : expenseParticipants)
           {
            User currentUser = userRespository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setExpense(expense);
            expenseUser.setUser(currentUser);
            expenseUser.setAmount(toPayAmount);
            expenseUser.setExpenseUserType(ExpenseUserType.HAD_TO_PAY);

            expenseUserList.add(expenseUserRepsository.save(expenseUser));
           }
        }

}
