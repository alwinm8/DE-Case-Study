/**
 * @author alwinmathew
 * This is the main class where the connections are made and the 7 queries are ran.
 */
package decasestudy;

import decasestudy.DAOImplementation.CustomerDAOImplementation;
import decasestudy.DAOImplementation.TransactionDAOImplementation;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class DECaseStudy {
    
    //connection must be static because there will only be one instance throughout the program
    static Connection myConn = null;
    static CustomerDAOImplementation customerDAOImp;
    static TransactionDAOImplementation transactionDAOImp;
    
    //connect to CDW_SAPP schema, SSL is false for localhost development, and use central standard time for time zone
    public static String url = "jdbc:mysql://localhost:3306/CDW_SAPP?useSSL=false&serverTimezone=CST";
    public static String user = "root";
    public static String pass = "mysql"; //**remove this before submitting!!**
    
    //you can pass url, user, and password as parameters
    //arg0 = url
    //arg1 = user
    //arg2 = pass
    public static void main(String[] args) throws SQLException 
    {
        //if arguments are provided, use 3 arguments to create connection
        if (args.length != 0)
        {
            url = args[0];
            user = args[1];
            pass = args[2];
        }
            
        try
        {
            //try connection to MySQL DB
            myConn = DriverManager.getConnection(url, user, pass);
            //System.out.println("The connection is successful!");
        }
        catch (SQLException e)
        {
            System.out.println("The connection has failed with exception" + e);
            System.exit(1);
        } 
        
        //initialize DAO
        customerDAOImp = new CustomerDAOImplementation();
        transactionDAOImp = new TransactionDAOImplementation();
        
        //begin the prompt
        Scanner userInput = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.println("\nWelcome to the Credit Card System! \nPlease enter 1 for Transaction Details, enter 2 for Customer Details, 3 to exit the Credit Card System.");
            int secondchoice, choice = userInput.nextInt();            
            switch (choice) {
            //Transaction Details, queries 1 - 3 are the choices possible
                case 1:
                    System.out.println("1. Display the transactions made by customers living in a given zipcode for a given month and year.\n2. Display the number and total values of transactions for a given type.\n3. Display the number and total values of transactions for branches in a given state.\n4. Go back to main menu.");
                    secondchoice = userInput.nextInt();
                    if (secondchoice == 1)
                    {
                        //query 1
                        int zip, month, year;
                        Transaction transaction;
                        System.out.println("Please enter zip code.");
                        zip = userInput.nextInt();
                        System.out.println("Please enter the month as a whole number.\t(ex. 2 for February)");
                        month = userInput.nextInt();
                        System.out.println("Please enter the year.");
                        year = userInput.nextInt();
                        List<Transaction> rs = transactionDAOImp.queryFindTransactionsZipMonthYear(myConn, zip, month, year);
                        rs.forEach((transaction1) -> 
                        {
                            System.out.println("Transaction ID:" + transaction1.getTransactionID() + "\tDay:" + transaction1.getDay() + "\tMonth:" + transaction1.getMonth() + "\tYear:" + transaction1.getYear() + "\tCredit Card No:" + transaction1.getCreditCardNo() + "\tBranch Code:" + transaction1.getBranchCode() + "\tTransaction Type:" + transaction1.getTransactionType() + "\tTransaction Value:" + transaction1.getTransactionValue());
                        });
                    }
                    else if (secondchoice == 2)
                    {
                        //query 2
                        String type;
                        userInput.nextLine();
                        System.out.println("Please enter the type.\t(ex. 'Gas')");
                        type = userInput.nextLine();
                        CountSum countsum = transactionDAOImp.queryFindNumberValuesType(myConn, type);
                        System.out.println("Count:" + countsum.COUNT + "\tSum:" + countsum.SUM);
                        
                    }
                    else if (secondchoice == 3)
                    {
                        //query 3
                        String state;
                        userInput.nextLine();
                        System.out.println("Please enter the state.\t(ex. 'TX')");
                        state = userInput.nextLine();
                        CountSum countsum = transactionDAOImp.queryFindNumberValuesState(myConn, state);
                        System.out.println("Count:" + countsum.COUNT + "\tSum:" + countsum.SUM);
                    }
                    else if (secondchoice == 4)
                    {
                        //go to main menu
                        break;
                    }
                    else
                    {
                        //invalid choice
                        System.out.println("The input choice is invalid. Please try again.");
                    }
                    break;
            //Customer Details, queries 4 - 7 are the choices possible
                case 2:
                    System.out.println("1. Check the existing account details of a customer.\n2. Modify the existing account details of a customer.\n3. Generate monthly bill for a credit card number for a given month and year.\n4. Display the transactions made by a customer between two dates.\n5. Go back to main menu.");
                    secondchoice = userInput.nextInt();
                    if (secondchoice == 1)
                    {
                        //query 4
                        System.out.println("Please enter the SSN for the customer you would like to see account details for.");
                        int ssn = userInput.nextInt();
                        Customer customer = customerDAOImp.retrieve(myConn, ssn);
                        if (customer == null)
                        {
                            System.out.println("");
                            break;
                        }
                        System.out.println("First Name: " + customer.getFirstName() + "\tMiddle Name: " + customer.getMiddleName() + "\tLast Name: " + customer.getLastName() + "\tSSN: " + customer.getSSN() + "\tCredit Card No:" + customer.getCreditCardNo() + "\tApt No: " + customer.getAptNo() + "\tStreet Name: " + customer.getStreetName() + "\tCity: " + customer.getCustCity() + "\tState: " + customer.getCustState() + "\tCountry: " + customer.getCustCountry() + "\tZip Code: " + customer.getCustZip() + "\tPhone: " + customer.getCustPhone() + "\tEmail: " + customer.getCustEmail() + "\tLast Updated:" + customer.getLastUpdated());
                    }
                    else if (secondchoice == 2)
                    {
                        //query 5
                        int ssn;
                        String credit_card_no;
                        System.out.println("Please enter in the SSN.");
                        ssn = userInput.nextInt();
                        System.out.println("Please enter in the credit card number.");
                        credit_card_no = userInput.next();
                        boolean check = customerDAOImp.checkCustomer(myConn, ssn, credit_card_no);
                        if (!check)
                        {
                            break;
                        }
                        Customer customer = new Customer();
                        int first_name, middle_name, last_name, apt_no, street_name;
                        //email and apt no is not working
                        System.out.println("Please enter in the first name.");
                        customer.setFirstName(userInput.next());
                        System.out.println("Please enter in the middle name.");
                        customer.setMiddleName(userInput.next());
                        System.out.println("Please enter in the last name.");
                        customer.setLastName(userInput.next());
                        userInput.nextLine();
                        System.out.println("Please enter in the apartment number.");
                        customer.setAptNo(userInput.nextLine());
                        System.out.println("Please enter in the street name.");
                        customer.setStreetName(userInput.nextLine());
                        System.out.println("Please enter in the city.");
                        customer.setCustCity(userInput.nextLine());
                        System.out.println("Please enter in the state.");
                        customer.setCustState(userInput.nextLine());
                        System.out.println("Please enter in the country.");
                        customer.setCustCountry(userInput.nextLine());
                        System.out.println("Please enter in the zip code.");
                        customer.setCustZip(userInput.nextLine());
                        System.out.println("Please enter in the phone.");
                        customer.setCustPhone(userInput.nextInt());
                        userInput.nextLine();
                        System.out.println("Please enter in the email.");
                        customer.setCustEmail(userInput.nextLine());
                        customer.setLastUpdated();
                        customer.setSSN(ssn);
                        customer.setCreditCardNo(credit_card_no);
                        int updatecheck = customerDAOImp.update(myConn, customer);
                        if (updatecheck != 1)
                        {
                            System.out.println("Error while updating, did not update record correctly.");
                        }
                    }
                    else if (secondchoice == 3)
                    {
                        //query 6
                        System.out.println("Please enter credit card number.");
                        String credit_card_no = userInput.next();
                        System.out.println("Please enter the month.");
                        int month = userInput.nextInt();
                        System.out.println("Please enter the year.");
                        int year = userInput.nextInt();
                        Transaction transaction = new Transaction();
                        transaction.setCreditCardNo(credit_card_no);
                        transaction.setMonth(month);
                        transaction.setYear(year);
                        MonthlyBill reportbill = customerDAOImp.queryMonthlyBillCreditCard(myConn, transaction);
                        System.out.println("\nBill:\nCC #:" + reportbill.getCreditCardNo() + "\tBalance:$" + reportbill.getSum() + "\n");
                    }
                    else if (secondchoice == 4)
                    {
                        //query 7
                        int startyear, endyear, startmonth, endmonth, startday, endday;
                        System.out.println("Start day:");
                        startday = userInput.nextInt();
                        System.out.println("Start month:");
                        startmonth = userInput.nextInt();
                        System.out.println("Start year:");
                        startyear = userInput.nextInt();
                        System.out.println("End day:");
                        endday = userInput.nextInt();
                        System.out.println("End month:");
                        endmonth = userInput.nextInt();
                        System.out.println("End year:");
                        endyear = userInput.nextInt();
                        List<Transaction> rs = customerDAOImp.queryTransactionsBetweenDates(myConn, startyear, endyear, startmonth, endmonth, startday, endday);
                        rs.forEach((transaction) -> 
                        {
                            System.out.println("Transaction ID:" + transaction.getTransactionID() + "\tDay:" + transaction.getDay() + "\tMonth:" + transaction.getMonth() + "\tYear:" + transaction.getYear() + "\tCredit Card No:" + transaction.getCreditCardNo() + "\tBranch Code:" + transaction.getBranchCode() + "\tTransaction Type:" + transaction.getTransactionType() + "\tTransaction Value:" + transaction.getTransactionValue());
                        });
   
                    }
                    else if (secondchoice == 5)
                    {
                        //go to main menu
                        break;
                    }
                    else
                    {
                        //invalid choice
                        System.out.println("The input choice is invalid. Please try again.");
                    }
                    break;
                case 3:
                    //close all connections and ports and exit the program
                    userInput.close();
                    myConn.close();
                    System.out.println("\nThe program will now exit!");
                    System.exit(0);
                    break OUTER;
                default:
                    System.out.println("ERROR: Please enter in a valid number.");
                    break;
            }
        }

        
    }
    
}
