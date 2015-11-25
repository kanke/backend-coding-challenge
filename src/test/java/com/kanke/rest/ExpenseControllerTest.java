package com.kanke.rest;

import com.kanke.api.Expense;
import com.kanke.api.ExpenseHandler;
import com.kanke.db.DAO;
import com.kanke.service.ExpenseHandlerImpl;
import org.hibernate.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by kishaku on 24/11/2015.
 */


@RunWith(MockitoJUnitRunner.class)
public class ExpenseControllerTest {

    @Spy
    @InjectMocks
    private ExpenseController expenseController;

    @Mock
    private ExpenseHandler expenseHandler;

    @Mock
    private SessionFactory factory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Serializable serializable;

    @Spy
    @InjectMocks
    private DAO dao;

    @Mock
    private List<Expense> exp;

    @Mock
    private Response response;

    private Expense expense;

    @Mock
    private Response.ResponseBuilder responseBuilder;

    @Mock
    private Calendar date;

    private int expensesId = 2000;

    @Spy
    @InjectMocks
    private DAO daoObject;

    @Mock
    private Query query;

    @Mock
    private List<Expense> list;


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

        exp.add(expense);

    }

    @Test
    public void shouldRespondWithExpense() {

        ExpenseHandlerImpl expenseHandler = mock(ExpenseHandlerImpl.class);

        when(expenseHandler.getExpenses()).thenReturn(exp);
        when(response.getStatus()).thenReturn(200);
        when(responseBuilder.build()).thenReturn(response);

        expenseController.getExpenses();

        assertEquals(200, response.getStatus());
        verify(expenseController, atLeastOnce()).getExpenses();
    }


    @Test
    public void shouldNotRespondWithExpense() {

        ExpenseHandlerImpl expenseHandler = mock(ExpenseHandlerImpl.class);

        when(expenseHandler.getExpenses()).thenReturn(null);
        when(response.getStatus()).thenReturn(404);
        when(responseBuilder.build()).thenReturn(response);

        expenseController.getExpenses();

        assertEquals(404, response.getStatus());
        verify(expenseController, atLeastOnce()).getExpenses();
    }


    @Test
    public void shouldRespondWithSaveExpense() {

        when(session.save(expense)).thenReturn(serializable);
        when(session.getTransaction()).thenReturn(transaction);
        transaction.commit();

        when(response.getStatus()).thenReturn(201);
        when(responseBuilder.build()).thenReturn(response);

        expenseController.saveExpense(expense);

        assertEquals(201, response.getStatus());
        verify(expenseController, atLeastOnce()).saveExpense(expense);
    }


    @Test
    public void shouldNotRespondWithSaveExpense() {


        when(session.save(expense)).thenReturn(serializable);
        when(session.getTransaction()).thenReturn(transaction);
        transaction.commit();

        when(response.getStatus()).thenReturn(404);
        when(responseBuilder.build()).thenReturn(response);

        doThrow(MappingException.class).when(expenseController).saveExpense(expense);

        assertEquals(404, response.getStatus());
        verify(expenseController, never()).saveExpense(expense);
    }


    @Test
    public void shouldRespondWithDeletedExpense() {

        Expense expense1 = (Expense) session.load(Expense.class, expensesId);
        session.delete(expense1);
        transaction.commit();
        session.close();

        when(response.getStatus()).thenReturn(200);
        when(responseBuilder.build()).thenReturn(response);

        expenseController.cancelExpense(expensesId);

        assertEquals(200, response.getStatus());
        verify(expenseController, atLeastOnce()).cancelExpense(expensesId);
    }

    @Test
    public void shouldNotRespondWithDeletedExpense() {

        Expense expense1 = (Expense) session.load(Expense.class, expensesId);
        session.delete(expense1);
        transaction.commit();
        session.close();

        daoObject.cancelExpense(expensesId);
        when(response.getStatus()).thenReturn(404);
        when(responseBuilder.build()).thenReturn(response);

        doThrow(MappingException.class).when(expenseController).cancelExpense(expensesId);

        assertEquals(404, response.getStatus());
        verify(expenseController, never()).cancelExpense(expensesId);
    }

    @Test
    public void shouldRespondWithGetExpense() {
        list.add(expense);
        String hql = "from Expense where expensesId = :expensesId";
        when(session.createQuery(hql)).thenReturn(query);
        query.setParameter("expensesId", expensesId);
        when(query.list()).thenReturn(list);
        when(list.get(0)).thenReturn(expense);

        when(response.getStatus()).thenReturn(200);
        when(responseBuilder.build()).thenReturn(response);

        daoObject.getExpense(expensesId);
        expenseController.getExpense(1);

        assertEquals(200, response.getStatus());
        verify(expenseController, atLeastOnce()).getExpense(1);
    }

    @Test
    public void shouldNotRespondWithGetExpense() {
        list.add(expense);
        String hql = "from Expense where expensesId = :expensesId";
        when(session.createQuery(hql)).thenReturn(query);
        query.setParameter("expensesId", expensesId);
        when(query.list()).thenReturn(list);
        when(list.get(0)).thenReturn(expense);

        when(response.getStatus()).thenReturn(404);
        when(responseBuilder.build()).thenReturn(response);

        daoObject.getExpense(expensesId);
        doThrow(MappingException.class).when(expenseController).getExpense(1);

        assertEquals(404, response.getStatus());
        verify(expenseController, never()).getExpense(1);
    }


}
