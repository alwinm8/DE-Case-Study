/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy.DAOImplementation;

import decasestudy.CountSum;
import decasestudy.DAO.TransactionDAO;
import decasestudy.Queries;
import decasestudy.Transaction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alwinmathew
 */
public class TransactionDAOImplementation implements TransactionDAO 
{

    @Override
    public List<Transaction> queryFindTransactionsZipMonthYear(Connection connection, int zip, int month, int year) throws SQLException
    {
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryFindTransactionsZipMonthYear, zip, month, year);
        ResultSet rs = statement.executeQuery(sql);
        LinkedList<Transaction> list = new LinkedList<>();
        while (rs.next())
        {
            int transaction_id = rs.getInt("TRANSACTION_ID");
            int day = rs.getInt("DAY");
            String credit_card_no = rs.getString("CREDIT_CARD_NO");
            int ssn = rs.getInt("CUST_SSN");
            int branch_code = rs.getInt("BRANCH_CODE");
            String transaction_type = rs.getString("TRANSACTION_TYPE");
            double transaction_value = rs.getDouble("TRANSACTION_VALUE");
            Transaction transaction = new Transaction(transaction_id, day, month, year, credit_card_no, ssn, branch_code, transaction_type, transaction_value);
            list.add(transaction);
        }
        
        return list;
    }

    @Override
    public CountSum queryFindNumberValuesType(Connection connection, String type) throws SQLException
    {
        
        int count;
        String sum;
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryFindNumberValuesTransaction, type);
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next())
        {
            count = rs.getInt("COUNT(TRANSACTION_ID)");
            sum = rs.getString("SUM(TRANSACTION_VALUE)");
            CountSum countsum = new CountSum(count, sum);
            return countsum;
        }

        return null;
    }
    
    @Override
    public CountSum queryFindNumberValuesState(Connection connection, String state) throws SQLException
    {
        int count;
        String sum;
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryFindNumberValuesState, state);
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next())
        {
            count = rs.getInt("COUNT(TRANSACTION_ID)");
            sum = rs.getString("SUM(TRANSACTION_VALUE)");
            CountSum countsum = new CountSum(count, sum);
            return countsum;
        }
        
        return null;
    }
    
}
