package com.kanke.service;

import com.kanke.api.Expense;
import com.kanke.api.ExpenseHandler;
import com.kanke.db.DAO;

import java.util.List;

/**
 * Created by kishaku on 24/11/2015.
 */
public class ExpenseHandlerImpl implements ExpenseHandler {


    public Expense addExpense(Expense newExp) {
        DAO.instance().addExpense(newExp);
        return newExp;

    }
    public Expense getExpense(int expensesId) {
        return DAO.instance().getExpense(expensesId);
    }

    public List<Expense> getExpenses() {
        return DAO.instance().getExpenses();
    }


    public void deleteExpense(int expensesId) {
        DAO.instance().cancelExpense(expensesId);
    }

}
