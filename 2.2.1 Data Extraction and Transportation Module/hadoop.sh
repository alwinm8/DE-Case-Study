#create credit card system directory if does not exist
hdfs dfs -mkdir -p /user/maria_dev/Credit_Card_System/

#delete all files in credit card system directory if exists
hdfs dfs -rm -r -f /user/maria_dev/Credit_Card_System/*

#import data from customer table
sqoop import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver \
--username root --password password \
--query 'SELECT SSN, CONCAT(UCASE(SUBSTRING(FIRST_NAME, 1, 1)),LOWER(SUBSTRING(FIRST_NAME, 2))), LOWER(MIDDLE_NAME), CONCAT(UCASE(SUBSTRING(LAST_NAME, 1, 1)),LOWER(SUBSTRING(LAST_NAME, 2))), CREDIT_CARD_NO, CONCAT(STREET_NAME, APT_NO), CUST_CITY, CUST_STATE, CUST_COUNTRY, CUST_ZIP, CONCAT(LEFT(CUST_PHONE,3), "-", RIGHT(CUST_PHONE,4)), CUST_EMAIL, LAST_UPDATED FROM CDW_SAPP_CUSTOMER WHERE $CONDITIONS' \
--target-dir /user/maria_dev/Credit_Card_System/CDW_SAPP_D_CUSTOMER/ \
-m 1

#import data from the branch
sqoop import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver \
--username root --password password \
--query 'SELECT BRANCH_CODE, BRANCH_NAME, BRANCH_STREET, BRANCH_CITY, BRANCH_STATE, CASE WHEN BRANCH_ZIP IS NULL THEN "999999" ELSE BRANCH_ZIP END, CONCAT("(", LEFT(BRANCH_PHONE, 3), ")", MID(BRANCH_PHONE, 4, 3), "-", RIGHT(BRANCH_PHONE, 4)), LAST_UPDATED FROM CDW_SAPP_BRANCH WHERE $CONDITIONS' \
--target-dir /user/maria_dev/Credit_Card_System/CDW_SAPP_D_BRANCH/ \
-m 1

#import data from the time
sqoop import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver \
--username root --password password \
--query 'SELECT CONCAT(YEAR, CASE WHEN MONTH > 9 THEN MONTH ELSE CONCAT("0", MONTH) END, CASE WHEN DAY > 9 THEN DAY ELSE CONCAT("0", DAY) END), DAY, MONTH, QUARTER(CONCAT(YEAR,"-", CASE WHEN MONTH > 9 THEN MONTH ELSE CONCAT("0", MONTH) END,"-", CASE WHEN DAY > 9 THEN DAY ELSE CONCAT("0", DAY) END)), YEAR FROM CDW_SAPP_CREDITCARD WHERE $CONDITIONS' \
--target-dir /user/maria_dev/Credit_Card_System/CDW_SAPP_D_TIME/ \
-m 1

#import data from credit card
sqoop import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver \
--username root --password password \
--query 'SELECT CREDIT_CARD_NO, CONCAT(YEAR, CASE WHEN MONTH > 9 THEN MONTH ELSE CONCAT("0", MONTH) END , CASE WHEN DAY > 9 THEN DAY ELSE CONCAT("0", DAY) END),CUST_SSN, BRANCH_CODE, TRANSACTION_TYPE, TRANSACTION_VALUE FROM CDW_SAPP_CREDITCARD WHERE $CONDITIONS' \
--target-dir /user/maria_dev/Credit_Card_System/CDW_SAPP_F_CREDIT_CARD/ \
-m 1

#move files from inside folders into text files in credit card system directory
hdfs dfs -mv /user/maria_dev/Credit_Card_System/CDW_SAPP_D_CUSTOMER/part-m-00000 /user/maria_dev/Credit_Card_System/CDW_SAPP_CUSTOMER.txt
hdfs dfs -mv /user/maria_dev/Credit_Card_System/CDW_SAPP_D_BRANCH/part-m-00000 /user/maria_dev/Credit_Card_System/CDW_SAPP_BRANCH.txt
hdfs dfs -mv /user/maria_dev/Credit_Card_System/CDW_SAPP_D_TIME/part-m-00000 /user/maria_dev/Credit_Card_System/CDW_SAPP_TIME.txt
hdfs dfs -mv /user/maria_dev/Credit_Card_System/CDW_SAPP_F_CREDIT_CARD/part-m-00000 /user/maria_dev/Credit_Card_System/CDW_SAPP_CREDITCARD.txt
