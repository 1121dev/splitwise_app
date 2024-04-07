package com.machineCoding.splitwise.repositories;

import com.machineCoding.splitwise.models.Expense;
import com.machineCoding.splitwise.models.ExpenseUser;
import com.machineCoding.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseUserRepsository extends JpaRepository<ExpenseUser, Long> {

    List<ExpenseUser> findAllByUser(User user);

    @Override
    ExpenseUser save(ExpenseUser expenseUser);
}
