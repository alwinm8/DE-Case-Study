/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy;
import java.sql.Timestamp;

/**
 *
 * @author alwinmathew
 */
public class Customer 
{
    //use the given Source File Structure xlsx file
    String FIRST_NAME;
    String MIDDLE_NAME;
    String LAST_NAME;
    int SSN;
    String CREDIT_CARD_NO;
    String APT_NO;
    String STREET_NAME;
    String CUST_CITY;
    String CUST_STATE;
    String CUST_COUNTRY;
    String CUST_ZIP; //idk why the database has this stored as varchar
    int CUST_PHONE; //phone numbers are too long to place inside of an regular int, I dont know why this is an int in the schema but sure
    String CUST_EMAIL;
    Timestamp LAST_UPDATED;
    
    //empty constructor
    public Customer()
    {
        
    }
    //constructor with parameters for Customer, timestamp does not need to be passed and will be generated automatically
    public Customer(String first_name, String middle_name, String last_name, int ssn, String credit_card_no, String apt_no, String street_name, String cust_city, String cust_state, String cust_country, String cust_zip, int cust_phone, String cust_email, Timestamp last_updated)
    {
        this.FIRST_NAME = first_name;
        this.MIDDLE_NAME = middle_name;
        this.LAST_NAME = last_name;
        this.SSN = ssn;
        this.CREDIT_CARD_NO = credit_card_no;
        this.APT_NO = apt_no;
        this.STREET_NAME = street_name;
        this.CUST_CITY = cust_city;
        this.CUST_STATE = cust_state;
        this.CUST_COUNTRY = cust_country;
        this.CUST_ZIP = cust_zip;
        this.CUST_PHONE = cust_phone;
        this.CUST_EMAIL = cust_email;
        //this will create a new timestamp object with the current **SYSTEM** time
        this.LAST_UPDATED = new Timestamp(System.currentTimeMillis());
    }
    
    //get and set for FIRST_NAME
    public String getFirstName()
    {
        return this.FIRST_NAME;
    }
    public void setFirstName(String first_name)
    {
        this.FIRST_NAME = first_name;
    }
    //get and set for MIDDLE_NAME
    public String getMiddleName()
    {
        return this.MIDDLE_NAME;
    }
    public void setMiddleName(String middle_name)
    {
        this.MIDDLE_NAME = middle_name;
    }
    //get and set for LAST_NAME
    public String getLastName()
    {
        return this.LAST_NAME;
    }
    public void setLastName(String last_name)
    {
        this.LAST_NAME = last_name;
    }
    //get and set for SSN
    public int getSSN()
    {
        return this.SSN;
    }
    public void setSSN(int ssn)
    {
        this.SSN = ssn;
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
    //get and set for APT_NO
    public String getAptNo()
    {
        return this.APT_NO;
    }
    public void setAptNo(String apt_no)
    {
        this.APT_NO = apt_no;
    }
    //get and set for STREET_NAME
    public String getStreetName()
    {
        return this.STREET_NAME;
    }
    public void setStreetName(String street_name)
    {
        this.STREET_NAME = street_name;
    }
    //get and set for CUST_CITY
    public String getCustCity()
    {
        return this.CUST_CITY;
    }
    public void setCustCity(String cust_city)
    {
        this.CUST_CITY = cust_city;
    }
    //get and set for CUST_STATE
    public String getCustState()
    {
        return this.CUST_STATE;
    }
    public void setCustState(String cust_state)
    {
        this.CUST_STATE = cust_state;
    }
    //get and set for CUST_COUNTRY
    public String getCustCountry()
    {
        return this.CUST_COUNTRY;
    }
    public void setCustCountry(String cust_country)
    {
        this.CUST_COUNTRY = cust_country;
    }
    //get and set for CUST_ZIP
    public String getCustZip()
    {
        return this.CUST_ZIP;
    }
    public void setCustZip(String cust_zip)
    {
        this.CUST_ZIP = cust_zip;
    }
    //get and set for CUST_PHONE
    public int getCustPhone()
    {
        return this.CUST_PHONE;
    }
    public void setCustPhone(int cust_phone)
    {
        this.CUST_PHONE = cust_phone;
    }
    //get and set for CUST_EMAIL
    public String getCustEmail()
    {
        return this.CUST_EMAIL;
    }
    public void setCustEmail(String cust_email)
    {
        this.CUST_EMAIL = cust_email;
    }
    //get and set for LAST_UPDATED -> SQL timestamp
    public Timestamp getLastUpdated()
    {
        LAST_UPDATED = new Timestamp(System.currentTimeMillis());
        return this.LAST_UPDATED;
    }
    public void setLastUpdated()
    {
        this.LAST_UPDATED = new Timestamp(System.currentTimeMillis());
    }
    
}
