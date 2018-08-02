/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy.DAOImplementation;

import decasestudy.Customer;
import decasestudy.Transaction;
import decasestudy.MonthlyBill;
import decasestudy.DAO.CustomerDAO;
import decasestudy.Queries;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author alwinmathew
 */
public class CustomerDAOImplementation implements CustomerDAO {
    
    //empty constructor, not really important for implmenetation
    public CustomerDAOImplementation()
    {

    }

    //Implement Query 4 - To check the existing account details of a customer.
    @Override
    public Customer retrieve(Connection connection, int ssn) throws SQLException 
    {
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryGetExistingAccount, ssn);
        ResultSet rs = statement.executeQuery(sql);
        if (rs.first())
        {
            //get all the values from the first result in the result set
            String first_name = rs.getString("FIRST_NAME");
            String middle_name = rs.getString("MIDDLE_NAME");
            String last_name = rs.getString("LAST_NAME");
            //ssn is already a parameter, just place it into the customer object
            String credit_card_no = rs.getString("CREDIT_CARD_NO");
            String apt_no = rs.getString("APT_NO");
            String street_name = rs.getString("STREET_NAME");
            String cust_city = rs.getString("CUST_CITY");
            String cust_state = rs.getString("CUST_STATE");
            String cust_country = rs.getString("CUST_COUNTRY");
            String cust_zip = rs.getString("CUST_ZIP");
            int cust_phone = rs.getInt("CUST_PHONE");
            String cust_email = rs.getString("CUST_EMAIL");
            Timestamp last_updated = rs.getTimestamp("LAST_UPDATED");
            Customer customer = new Customer(first_name, middle_name, last_name, ssn, credit_card_no, apt_no, street_name, cust_city, cust_state, cust_country, cust_zip, cust_phone, cust_email, last_updated);
            return customer;
        }
        //handle exception for not found
        return null;
    }
    
    //Implement Query 5 - To modify the existing account details of a customer.
    @Override
    public int update(Connection connection, Customer customer) throws SQLException 
    {    
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryModifyExistingAccount, customer.getFirstName(), customer.getMiddleName(), customer.getLastName(), customer.getCreditCardNo(), customer.getAptNo(), customer.getStreetName(), customer.getCustCity(), customer.getCustState(), customer.getCustCountry(), customer.getCustZip(), customer.getCustPhone(), customer.getCustEmail(), customer.getLastUpdated(), customer.getSSN());
        int result = statement.executeUpdate(sql);
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Implement Query 6 - To generate monthly bill for a credit card number for a given month and year.
    @Override
    public MonthlyBill queryMonthlyBillCreditCard(Connection connection, Transaction transaction) throws SQLException
    {
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryMonthlyBillCreditCard, transaction.getCreditCardNo(), transaction.getMonth(), transaction.getYear());
        ResultSet rs = statement.executeQuery(sql);      
        if (rs.first())
        {
            String credit_card_no = rs.getString("CREDIT_CARD_NO");
            double sum = rs.getDouble("SUM(TRANSACTION_VALUE)");
            MonthlyBill newbill = new MonthlyBill(credit_card_no, sum);
            return newbill;
        }
        //if nothing is found in result set, there must be not transactions
        return null;
    }
    
    //Implement Query 7 - To display the transactions made by a customer between two dates. Order by year, month, and day in descending order.
    @Override
    public List<Transaction> queryTransactionsBetweenDates(Connection connection, int startyear, int endyear, int startmonth, int endmonth, int startday, int endday) throws SQLException
    {
        Statement statement = connection.createStatement();
        String sql = String.format(Queries.queryTransactionsBetweenDates, startyear, startmonth, startday, endyear, endmonth, endday);
        ResultSet rs = statement.executeQuery(sql);
        LinkedList<Transaction> list = new LinkedList<>();
        while (rs.next())
        {
            int transaction_id = rs.getInt("TRANSACTION_ID");
            int day = rs.getInt("DAY");
            int month = rs.getInt("MONTH");
            int year = rs.getInt("YEAR");
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
    
    //this is a helper function to validate if a customer exists based on a primary key either of ssn or credit card number
    public boolean checkCustomer(Connection connection, int ssn, String credit_card_no) throws SQLException
    {
        Statement statement = connection.createStatement();
        String sql =  String.format(Queries.queryCheckCustomer, ssn, credit_card_no);
        ResultSet rs = statement.executeQuery(sql);
        int count = 0;
        while(rs.next())
        {
            count++;
        }
        //0 matches
        if (count == 0)
        {
            System.out.println("Could not find any unique matches for that SSN and credit card number.");
            return false;
        }
        //not unique?
        else if (count > 1)
        {
            System.out.println("There are too many results. A unique match could not be found for that SSN and credit card number.");
            return false;
        }
        
        return true;
    }
}
