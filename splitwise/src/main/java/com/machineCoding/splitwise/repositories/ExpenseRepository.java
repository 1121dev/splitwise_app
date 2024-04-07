package com.machineCoding.splitwise.repositories;

import com.machineCoding.splitwise.models.Expense;
import com.machineCoding.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findAllByGroup(Group group);

    @Override
    Expense save(Expense entity);

}
