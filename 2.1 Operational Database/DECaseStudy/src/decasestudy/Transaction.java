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
public class Transaction 
{
    //use the given Source File Structure xlsx file
    int TRANSACTION_ID;
    int DAY;
    int MONTH;
    int YEAR;
    String CREDIT_CARD_NO;
    int CUST_SSN;
    int BRANCH_CODE;
    String TRANSACTION_TYPE;
    double TRANSACTION_VALUE; //maybe this should be a float?
    
    //empty constructor of Transactions...could be bad idea, can cause problems.
    public Transaction()
    {
        //do nothing! use getters and setters to fill this empty object. 
    }
    
    //public constructor of Transaction
    public Transaction(int transaction_id, int day, int month, int year, String credit_card_no, int cust_ssn, int branch_code, String transaction_type, double transaction_value)
    {
        this.TRANSACTION_ID = transaction_id;
        this.DAY = day;
        this.MONTH = month;
        this.YEAR = year;
        this.CREDIT_CARD_NO = credit_card_no;
        this.CUST_SSN = cust_ssn;
        this.BRANCH_CODE = branch_code;
        this.TRANSACTION_TYPE = transaction_type;
        this.TRANSACTION_VALUE = transaction_value;
    }
    //get for TRANSACTION_ID, there can 
    public int getTransactionID()
    {
        return this.TRANSACTION_ID;
    }
    //get and set for DAY
    public int getDay()
    {
        return this.DAY;
    }
    public void setDay(int day)
    {
        this.DAY = day;
    }
    //get and set for MONTH
    public int getMonth()
    {
        return this.MONTH;
    }
    public void setMonth(int month)
    {
        this.MONTH = month;
    }
    //get and set for YEAR
    public int getYear()
    {
        return this.YEAR;
    }
    public void setYear(int year)
    {
        this.YEAR = year;
    }
    //get and set for CREDIT_CARD_NO
    public String getCreditCardNo()
    {
        return this.CREDIT_CARD_NO;
    }
    public void setCreditCardNo(String credit_card_no)
    {
        this.CREDIT_CARD_NO = credit_card_no;
    }
    //get and set for CUST_SSN
    public int getCustomerSSN()
    {
        return this.CUST_SSN;
    }
    public void setCustomerSSN(int ssn)
    {
        this.CUST_SSN = ssn;
    }
    //get and set for BRANCH_CODE
    public int getBranchCode()
    {
        return this.BRANCH_CODE;
    }
    public void setBranchCode(int branch_code)
    {
        this.BRANCH_CODE = branch_code;
    }
    //get and set for TRANSACTION_TYPE
    public String getTransactionType()
    {
        return this.TRANSACTION_TYPE;
    }
    public void setTransactionType(String transaction_type)
    {
        this.TRANSACTION_TYPE = transaction_type;
    }
    //get and set for TRANSACTION_VALUE
    public double getTransactionValue()
    {
        return this.TRANSACTION_VALUE;
    }   
    public void setTransactionValue(double transaction_value)
    {
        this.TRANSACTION_VALUE = transaction_value;
    }
    
}
