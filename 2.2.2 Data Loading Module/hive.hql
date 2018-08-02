--drop tables if they exist
DROP TABLE IF EXISTS default.temp_customer;
DROP TABLE IF EXISTS default.temp_branch;
DROP TABLE IF EXISTS default.temp_time;
DROP TABLE IF EXISTS default.temp_creditcard;
DROP TABLE IF EXISTS default.CDW_SAPP_D_CUSTOMER;
DROP TABLE IF EXISTS default.CDW_SAPP_D_BRANCH;
DROP TABLE IF EXISTS default.CDW_SAPP_D_TIME;
DROP TABLE IF EXISTS default.CDW_SAPP_F_CREDIT_CARD;

--create temporary table for customer and load
CREATE TABLE default.temp_customer(
	CUST_SSN DECIMAL(9),
	CUST_F_NAME VARCHAR(40),
	CUST_M_NAME VARCHAR(40),
	CUST_L_NAME VARCHAR(40),
	CUST_CC_NO STRING,
	CUST_STREET VARCHAR(38),
	CUST_CITY VARCHAR(30),
	CUST_STATE VARCHAR(30),
	CUST_COUNTRY VARCHAR(30),
	CUST_ZIP DECIMAL(9),
	CUST_PHONE VARCHAR(8),
	CUST_EMAIL VARCHAR(40),
	LAST_UPDATED TIMESTAMP
	)
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;
	
	LOAD DATA INPATH '/Credit_Card_System/CDW_SAPP_CUSTOMER.txt' INTO TABLE temp_customer;

--create temporary table for branch and load
CREATE TABLE default.temp_branch(
	BRANCH_CODE DECIMAL(9),
	BRANCH_NAME VARCHAR(25),
	BRANCH_STREET VARCHAR(30),
	BRANCH_CITY VARCHAR(30),
	BRANCH_STATE VARCHAR(30),
	BRANCH_ZIP DECIMAL(9),
	BRANCH_PHONE VARCHAR(13),
	LAST_UPDATED TIMESTAMP
	)
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

	LOAD DATA INPATH '/Credit_Card_System/CDW_SAPP_BRANCH.txt' INTO TABLE temp_branch;

--create temporary table for time and load
CREATE TABLE default.temp_time(
	TIMEID VARCHAR(8),
	DAY DECIMAL(2),
	MONTH DECIMAL(2),
	QUARTER VARCHAR(8),
	YEAR DECIMAL(4)
	)
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

	LOAD DATA INPATH '/Credit_Card_System/CDW_SAPP_TIME.txt' INTO TABLE temp_time;

--create temporary table for credit card and load
CREATE TABLE default.temp_creditcard(
	CUST_CC_NO STRING,
	TIMEID VARCHAR(8),
	CUST_SSN DECIMAL(9),
	BRANCH_CODE DECIMAL(10),
	TRANSACTION_TYPE VARCHAR(30),
	TRANSACTION_VALUE DECIMAL(20,3)
	)
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

	LOAD DATA INPATH '/Credit_Card_System/CDW_SAPP_CREDITCARD.txt' INTO TABLE temp_creditcard;

--set important variables for dynamic partitioning
set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;

--create table for customer with partitions
CREATE TABLE default.CDW_SAPP_D_CUSTOMER(
	CUST_SSN DECIMAL(9),
	CUST_F_NAME VARCHAR(40),
	CUST_M_NAME VARCHAR(40),
	CUST_L_NAME VARCHAR(40),
	CUST_CC_NO STRING,
	CUST_STREET VARCHAR(38),
	CUST_CITY VARCHAR(30),
	CUST_COUNTRY VARCHAR(30),
	CUST_ZIP DECIMAL(9),
	CUST_PHONE VARCHAR(8),
	CUST_EMAIL VARCHAR(40),
	LAST_UPDATED TIMESTAMP
	)
	PARTITIONED BY (CUST_STATE VARCHAR(30))
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

--create table for branch with partitions
CREATE TABLE default.CDW_SAPP_D_BRANCH(
	BRANCH_CODE DECIMAL(9),
	BRANCH_NAME VARCHAR(25),
	BRANCH_STREET VARCHAR(30),
	BRANCH_CITY VARCHAR(30),
	BRANCH_ZIP DECIMAL(9),
	BRANCH_PHONE VARCHAR(13),
	LAST_UPDATED TIMESTAMP
	)
	PARTITIONED BY (BRANCH_STATE VARCHAR(30))
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

--create table for time with partitions
CREATE TABLE default.CDW_SAPP_D_TIME(
	TIMEID VARCHAR(8),
	DAY DECIMAL(2),
	MONTH DECIMAL(2),
	YEAR DECIMAL(4)
	)
	PARTITIONED BY (QUARTER VARCHAR(8))
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

--create table for credit card with partitions
CREATE TABLE default.CDW_SAPP_F_CREDIT_CARD(
	CUST_CC_NO STRING,
	TIMEID VARCHAR(8),
	CUST_SSN DECIMAL(9),
	TRANSACTION_TYPE VARCHAR(30),
	TRANSACTION_VALUE DECIMAL(20,3)
	)
	PARTITIONED BY (BRANCH_CODE DECIMAL(10))
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	STORED AS TEXTFILE;

--load data into customer
INSERT INTO TABLE default.CDW_SAPP_D_CUSTOMER
	PARTITION(CUST_STATE)
	SELECT CUST_SSN,
	CUST_F_NAME,
	CUST_M_NAME,
	CUST_L_NAME,
	CUST_CC_NO,
	CUST_STREET,
	CUST_CITY,
	CUST_COUNTRY,
	CUST_ZIP,
	CUST_PHONE,
	CUST_EMAIL,
	LAST_UPDATED,
	CUST_STATE
	FROM default.temp_customer;

--load data into branch
INSERT INTO TABLE default.CDW_SAPP_D_BRANCH
	PARTITION(BRANCH_STATE)
	SELECT 	BRANCH_CODE,
	BRANCH_NAME,
	BRANCH_STREET,
	BRANCH_CITY,
	BRANCH_ZIP,
	BRANCH_PHONE,
	LAST_UPDATED,
	BRANCH_STATE
	FROM default.temp_branch;

--load data into time
INSERT INTO TABLE default.CDW_SAPP_D_TIME
	PARTITION(QUARTER)
	SELECT TIMEID,
	DAY,
	MONTH,
	YEAR,
	QUARTER
	FROM default.temp_time;

--load data into credit card
INSERT INTO TABLE default.CDW_SAPP_F_CREDIT_CARD
	PARTITION(BRANCH_CODE)
	SELECT CUST_CC_NO,
	TIMEID,
	CUST_SSN,
	TRANSACTION_TYPE,
	TRANSACTION_VALUE,
	BRANCH_CODE
	FROM default.temp_creditcard;