/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy;


/**
 *
 * @author alwinmathew
 */
public class Queries 
{
    //Transaction details module
    //Query 1 - To display the transactions made by customers living in a given zipcode for a given month and year. Order by day in descending order.
    public final static String queryFindTransactionsZipMonthYear = "SELECT * FROM CDW_SAPP_CREDITCARD JOIN CDW_SAPP_CUSTOMER ON CDW_SAPP_CUSTOMER.SSN = CDW_SAPP_CREDITCARD.CUST_SSN WHERE CDW_SAPP_CUSTOMER.CUST_ZIP = %d AND CDW_SAPP_CREDITCARD.MONTH = %d AND CDW_SAPP_CREDITCARD.YEAR = %d ORDER BY CDW_SAPP_CREDITCARD.DAY DESC;";
    //Query 2 - To display the number and total values of transactions for a given type.
    public final static String queryFindNumberValuesTransaction = "SELECT COUNT(TRANSACTION_ID), SUM(TRANSACTION_VALUE) FROM CDW_SAPP_CREDITCARD WHERE TRANSACTION_TYPE = '%s' GROUP BY TRANSACTION_TYPE;";    
    //Query 3 - To display the number and total values of transactions for branches in a given state
    public final static String queryFindNumberValuesState = "SELECT COUNT(TRANSACTION_ID), SUM(TRANSACTION_VALUE) FROM CDW_SAPP_CREDITCARD JOIN CDW_SAPP_BRANCH WHERE CDW_SAPP_BRANCH.BRANCH_STATE = 'TX';";
    
    //Customer details module
    //Query 4 - To check the existing account details of a customer.
    public final static String queryGetExistingAccount = "SELECT * FROM CDW_SAPP_CUSTOMER WHERE SSN=%d;";
    //Query 5 - To modify the existing account details of a customer.
    public final static String queryModifyExistingAccount = "UPDATE CDW_SAPP_CUSTOMER SET FIRST_NAME='%s', MIDDLE_NAME='%s', LAST_NAME='%s', CREDIT_CARD_NO='%s', APT_NO='%s', STREET_NAME='%s', CUST_CITY='%s', CUST_STATE='%s', CUST_COUNTRY='%s', CUST_ZIP='%s', CUST_PHONE=%d, CUST_EMAIL='%s', LAST_UPDATED='%s' WHERE SSN=%d;";
    //Query 6 - To generate monthly bill for a credit card number for a given month and year.
    public final static String queryMonthlyBillCreditCard = "SELECT CREDIT_CARD_NO, SUM(TRANSACTION_VALUE) FROM CDW_SAPP_CREDITCARD WHERE CREDIT_CARD_NO = '%s' AND MONTH = %d AND YEAR = %d;";
    //Query 7 - To display the transactions made by a customer between two dates. Order by year, month, and day in descending order.
    public final static String queryTransactionsBetweenDates = "SELECT TRANSACTION_ID, DAY, MONTH, YEAR, CREDIT_CARD_NO, CUST_SSN, BRANCH_CODE, TRANSACTION_TYPE, TRANSACTION_VALUE FROM CDW_SAPP_CREDITCARD WHERE DATE(CONCAT(YEAR, '-', MONTH, '-', DAY)) BETWEEN '%d-%d-%d' AND '%d-%d-%d' ORDER BY DATE(CONCAT(YEAR, '-', MONTH, '-', DAY)) DESC;";

    //Helper query for Query 5
    public final static String queryCheckCustomer = "SELECT * FROM CDW_SAPP_CUSTOMER WHERE SSN=%d AND CREDIT_CARD_NO='%s';";
}

