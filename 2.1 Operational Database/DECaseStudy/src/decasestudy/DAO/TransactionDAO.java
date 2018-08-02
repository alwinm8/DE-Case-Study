/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy.DAO;

import decasestudy.Transaction;
import decasestudy.CountSum;
import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;
import java.sql.SQLException;

/**
 *
 * @author alwinmathew
 */
public interface TransactionDAO 
{
    //the primary key for Credit Card (Transaction) is the transaction id
    public List<Transaction> queryFindTransactionsZipMonthYear(Connection connection, int zip, int month, int year) throws SQLException;
    public CountSum queryFindNumberValuesType(Connection connection, String type) throws SQLException;
    public CountSum queryFindNumberValuesState(Connection connection, String state) throws SQLException;
}
