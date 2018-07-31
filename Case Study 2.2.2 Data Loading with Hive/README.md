Author : Peter Li

In this project, I used Hive
1. Firstly, create a staging table, load the data from HDFS directory( where sqoop imports to),

  The staging table is an external table which locates at '/user/maria_dev/CREDIT_CARD_HIVE_TABLES/CDW_SAPP_BRANCH_STATIC'
  
  when you running query in Hive, run the first part, take Branch table as example:
                                    
                                   
                                  create external table CDW_SAPP_D_BRANCH_static
                                  (BRANCH_CODE string, 
                                   BRANCH_NAME string,
                                   BRANCH_STREET string,
                                   BRANCH_CITY string,
                                   BRANCH_STATE string,
                                   BRANCH_ZIP string,
                                   BRANCH_PHONE string,
                                   LAST_UPDATED timestamp)
                                 ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
                                 ESCAPED BY '"' LINES TERMINATED BY '\n'
                                 location '/user/maria_dev/CREDIT_CARD_HIVE_TABLES/CDW_SAPP_BRANCH_STATIC';

                                 load data inpath '/user/maria_dev/CREDIT_CARD_SYSTEM/CDW_SAPP_BRANCH/'
                                 overwrite into table CDW_SAPP_D_BRANCH_static;  
  
2. After 
