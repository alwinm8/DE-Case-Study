--insert into partitioned tables
set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;

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

--tables will be fresh for the next use
DROP TABLE default.temp_customer;
DROP TABLE default.temp_branch;
DROP TABLE default.temp_time;
DROP TABLE default.temp_creditcard;
