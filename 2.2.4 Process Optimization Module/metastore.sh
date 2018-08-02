#create metastore jobs in order to execute them in the oozie workflow 2
#create sqoop_customer
sqoop job -meta-connect jdbc:hsqldb:hsql://localhost:16000/sqoop --create sqoop_update_customer -- import  --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver --query 'SELECT SSN, CONCAT(UCASE(SUBSTRING(FIRST_NAME, 1, 1)),LOWER(SUBSTRING(FIRST_NAME, 2))), LOWER(MIDDLE_NAME), CONCAT(UCASE(SUBSTRING(LAST_NAME, 1, 1)),LOWER(SUBSTRING(LAST_NAME, 2))), CREDIT_CARD_NO, CONCAT(STREET_NAME, APT_NO), CUST_CITY, CUST_STATE, CUST_COUNTRY, CUST_ZIP, CONCAT(LEFT(CUST_PHONE,3), "-", RIGHT(CUST_PHONE,4)), CUST_EMAIL, LAST_UPDATED FROM CDW_SAPP_CUSTOMER WHERE $CONDITIONS' \
--incremental lastmodified --check-column LAST_UPDATED --append --last-value  0 \
--target-dir /Credit_Card_System/CDW_SAPP_D_CUSTOMER/ \
-m 1

#create sqoop_branch
sqoop job -meta-connect jdbc:hsqldb:hsql://localhost:16000/sqoop --create sqoop_update_branch -- import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver --query 'SELECT BRANCH_CODE, BRANCH_NAME, BRANCH_STREET, BRANCH_CITY, BRANCH_STATE, CASE WHEN BRANCH_ZIP IS NULL THEN "999999" ELSE BRANCH_ZIP END, CONCAT("(", LEFT(BRANCH_PHONE, 3), ")", MID(BRANCH_PHONE, 4, 3), "-", RIGHT(BRANCH_PHONE, 4)), LAST_UPDATED FROM CDW_SAPP_BRANCH WHERE $CONDITIONS' \
--incremental lastmodified --check-column LAST_UPDATED --append --last-value  0 \
--target-dir /Credit_Card_System/CDW_SAPP_D_BRANCH/ \
-m 1

#create sqoop_time
sqoop job -meta-connect jdbc:hsqldb:hsql://localhost:16000/sqoop --create sqoop_update_time -- import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver --query 'SELECT TIMEID, DAY, MONTH, QUARTER, YEAR FROM (SELECT CAST(CONCAT(YEAR, CASE WHEN MONTH > 9 THEN MONTH ELSE CONCAT("0", MONTH) END, CASE WHEN DAY > 9 THEN DAY ELSE CONCAT("0", DAY) END) AS UNSIGNED) AS TIMEID, DAY, MONTH, QUARTER(CONCAT(YEAR,"-", CASE WHEN MONTH > 9 THEN MONTH ELSE CONCAT("0", MONTH) END,"-", CASE WHEN DAY > 9 THEN DAY ELSE CONCAT("0", DAY) END)) AS QUARTER, YEAR FROM CDW_SAPP_CREDITCARD) AS TEMP1 WHERE $CONDITIONS' \
--incremental append --check-column TIMEID --last-value  0 \
--target-dir /Credit_Card_System/CDW_SAPP_D_TIME/ \
-m 1

#create sqoop_creditcard
sqoop job -meta-connect jdbc:hsqldb:hsql://localhost:16000/sqoop --create sqoop_update_creditcard -- import --connect jdbc:mysql://localhost/CDW_SAPP --driver com.mysql.jdbc.Driver --query 'SELECT CREDIT_CARD_NO, TIMEID, CUST_SSN, BRANCH_CODE, TRANSACTION_TYPE, TRANSACTION_VALUE FROM (SELECT CREDIT_CARD_NO, CAST(CONCAT(YEAR, CASE WHEN MONTH > 9 THEN MONTH ELSE CONCAT("0", MONTH) END , CASE WHEN DAY > 9 THEN DAY ELSE CONCAT("0", DAY) END) AS UNSIGNED) AS TIMEID,CUST_SSN, BRANCH_CODE, TRANSACTION_TYPE, TRANSACTION_VALUE FROM CDW_SAPP_CREDITCARD) AS TEMP2 WHERE $CONDITIONS' \
--incremental append --check-column TIMEID --last-value  0 \
--target-dir /Credit_Card_System/CDW_SAPP_F_CREDIT_CARD/ \
-m 1