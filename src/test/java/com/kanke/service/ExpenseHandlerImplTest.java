package com.kanke.service;

import com.kanke.api.Expense;
import com.kanke.api.ExpenseHandler;
import com.kanke.db.DAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by kishaku on 24/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ExpenseHandlerImplTest {

    @Spy
    @InjectMocks
    private ExpenseHandlerImpl expenseHandlerImpl;

    @Mock
    private ExpenseHandler expenseHandler;

    @Mock
    private SessionFactory factory;

    @Mock
    private Session session;

    private int expensesId = 1000;

    @Mock
    private Transaction transaction;

    @Mock
    private Serializable serializable;

    @Spy
    @InjectMocks
    private DAO daoObject;

    @Mock
    private Query query;

    @Mock
    private List<Expense> list;

    @Mock
    private List<Expense> mockList;

    private Calendar date;

    private Expense expense;

    @After
    public void validate() {
        Expense expense1 = (Expense) session.load(Expense.class, expensesId);
        session.delete(expense1);
        transaction.commit();
        session.close();
        daoObject.cancelExpense(expensesId);

        validateMockitoUsage();
    }


    @Before
    public void setup() {

        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        expense = new Expense();
        expense.setDate(date);
        expense.setAmount(10.00);
        expense.setExpenseId(expensesId);
        expense.setReason("test");

    }


    @Test
    public void shouldAddExpense() {

        when(session.save(expense)).thenReturn(serializable);
        when(session.getTransaction()).thenReturn(transaction);
        transaction.commit();

        assertEquals(daoObject.addExpense(expense), expenseHandlerImpl.addExpense(expense));

    }

    @Test
    public void shouldGetExpense() {
        list.add(expense);
        String hql = "from Expense where expensesId = :expensesId";
        when(session.createQuery(hql)).thenReturn(query);
        query.setParameter("expensesId", expensesId);
        when(query.list()).thenReturn(list);
        when(list.get(0)).thenReturn(expense);

        daoObject.getExpense(expensesId);
        expenseHandlerImpl.getExpense(1);

        verify(expenseHandlerImpl, atLeastOnce()).getExpense(1);

    }

    @Test
    public void shouldGetExpenses() {

        String hql = "from Expense";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.list()).thenReturn(list);

        daoObject.getExpenses();
        expenseHandlerImpl.getExpenses();

        verify(expenseHandlerImpl, atLeastOnce()).getExpenses();

    }


    @Test
    public void shouldCancelExpenses() {

        Expense expense1 = (Expense) session.load(Expense.class, expensesId);
        session.delete(expense1);
        transaction.commit();
        session.close();

        daoObject.cancelExpense(expensesId);
        expenseHandlerImpl.deleteExpense(expensesId);

        verify(expenseHandlerImpl, atLeastOnce()).deleteExpense(expensesId);

    }

}
