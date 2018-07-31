
* In order to successfully run this project, You need:


        change the password in connection.java to connect MySQL server.





---------------------
The project contains 4 packages:

1. BasicJava: Including connection,customers,MyQuery, Transaction:

             connection.java: class has methods to create connection, execute/update sql, close connection methods. 
             
2. Dao : Including customersDao, TransactionDao:
             
             customersDao.java: class has customer interface
             TransactionDao.java: class has transaction interface
             
3. Module: customersDaoImpl, TransactionDaoImpl:
            
            customersDaoImpl.java: class to implement customersDao/overwrite methods.
            TransactionDaoImpl: class to implement TransactionDao/overwrite methods.
            
4. Run: Main, customersRun. TransactionRun
