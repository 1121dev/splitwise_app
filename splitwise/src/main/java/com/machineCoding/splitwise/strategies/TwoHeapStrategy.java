package com.machineCoding.splitwise.strategies;

import com.machineCoding.splitwise.models.Expense;
import com.machineCoding.splitwise.models.ExpenseUser;
import com.machineCoding.splitwise.models.ExpenseUserType;
import com.machineCoding.splitwise.models.User;
import com.machineCoding.splitwise.services.Transaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class TwoHeapStrategy implements SettleUpStrategy {

    @Override

    public List<Transaction> settle(Set<Expense>expenseSet) {
        Map<User, Integer> amtMap = new HashMap<>();
//
        for (Expense expense : expenseSet) {

            List<ExpenseUser> expenseUserList = expense.getExpenseUserList();
            for (ExpenseUser expenseUser : expenseUserList) {
                User user = expenseUser.getUser();
                Integer amount = expenseUser.getAmount();
                ExpenseUserType expenseUserType = expenseUser.getExpenseUserType();
                Integer finalAmt = amtMap.getOrDefault(user, 0);
                if (expenseUserType.equals(ExpenseUserType.PAID))
                    finalAmt += amount;
                else if (expenseUserType.equals(ExpenseUserType.HAD_TO_PAY))
                    finalAmt -= amount;
                amtMap.put(user, finalAmt);
            }
        }

        PriorityQueue<HeapEntry> maxHeap = new PriorityQueue<>(Comparator.comparing(HeapEntry::getAmount, Comparator.reverseOrder()));
        PriorityQueue<HeapEntry> minHeap = new PriorityQueue<>(Comparator.comparing(HeapEntry::getAmount));

        for (Map.Entry<User, Integer> entry : amtMap.entrySet()) {
            if (entry.getValue() < 0)
                minHeap.add(new HeapEntry(entry.getKey(), entry.getValue()));
            else if (entry.getValue() > 0)
                maxHeap.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }

        List<Transaction> transactionList = new ArrayList<>();

        while (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            HeapEntry taker = maxHeap.peek();
            HeapEntry giver = minHeap.peek();

            Integer netAmount = Integer.min(Math.abs(taker.getAmount()), Math.abs(giver.getAmount()));

            taker.setAmount(taker.getAmount() - netAmount);
            giver.setAmount(giver.getAmount() + netAmount);

            Transaction transaction = new Transaction();
            transaction.setTo(taker.getUser());
            transaction.setFrom(giver.getUser());
            transaction.setAmount(netAmount);

            transactionList.add(transaction);

            if (taker.getAmount() == 0)
                maxHeap.remove();
            if (giver.getAmount() == 0)
                minHeap.remove();
        }
        return transactionList;
    }

    @Getter
    @Setter
    class HeapEntry implements Comparable<HeapEntry> {
        private User user;
        private Integer amount;

        public HeapEntry(User user, Integer amount) {
            this.user = user;
            this.amount = amount;
        }

        @Override
        public int compareTo(HeapEntry other) {
            return this.amount.compareTo(other.amount);
        }
    }
}
