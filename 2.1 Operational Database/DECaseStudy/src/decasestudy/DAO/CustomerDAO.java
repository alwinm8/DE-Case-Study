/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import decasestudy.Customer;
import decasestudy.MonthlyBill;
import decasestudy.Transaction;
import java.util.List;

/**
 *
 * @author alwinmathew
 */
public interface CustomerDAO 
{
    //the primary key for Customer consists of credit card number and SSN, but we will just use ssn to identify the customer
    Customer retrieve(Connection connection, int ssn) throws SQLException;
    int update(Connection connection, Customer customer) throws SQLException;
    public MonthlyBill queryMonthlyBillCreditCard(Connection connection, Transaction transaction) throws SQLException;
    public List<Transaction> queryTransactionsBetweenDates(Connection connection, int startyear, int endyear, int startmonth, int endmonth, int startday, int endday) throws SQLException;
}
