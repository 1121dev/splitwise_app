package com.machineCoding.splitwise.commands;

import com.machineCoding.splitwise.controllers.UserExpenseController;
import com.machineCoding.splitwise.dto.UserExpenseRequestDto;
import com.machineCoding.splitwise.dto.UserExpenseResponseDto;
import com.machineCoding.splitwise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class UserExpenseCommand implements  Command{
    @Autowired
    private UserExpenseController addExpenseController;
    @Override
    public void execute(String input) {
        Scanner sc = new Scanner(System.in);
        List<String>words = List.of(input.split(" "));
        Long user = Long.valueOf(words.get(0));
        Map<Long,Integer>payingUsers = new HashMap<>();
        List<Long>expenseParticipants = new ArrayList<>();
        int n = 0;
        System.out.println("Please enter the number of users who paid");
        n = sc.nextInt();
        System.out.println("Please enter the userId's and the amount that they have paid");
        while(n!=0)
        {
            Long id = (long) sc.nextInt();
            int amount = sc.nextInt();
            payingUsers.put(id,amount);
            n--;
        }
        System.out.println("How many users are involved in this expense");
        n = sc.nextInt();
        System.out.println("Please enter the user id's who are involved in this expense");
        while(n!=0)
        {
            Long id = (long) sc.nextInt();
            expenseParticipants.add(id);
            n--;
        }

        UserExpenseRequestDto request = new UserExpenseRequestDto();
        request.setCreator(user);
        request.setPayingUsers(payingUsers);
        request.setExpenseParticipants(expenseParticipants);

        UserExpenseResponseDto response = addExpenseController.getExpenseResponse(request);
        if(response==null)
            throw new RuntimeException();
        System.out.println("The Expense have been successfully added");

    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));
        return words.size()==2 && words.get(1).equalsIgnoreCase(CommandKeywords.addExpense);
    }
}
