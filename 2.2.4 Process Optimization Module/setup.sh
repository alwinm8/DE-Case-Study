#this script will setup the second coordinator and workflow, assumes you have already run the first setup script for first coordinator and workflow
#move hive scripts, setup and insert, to hdfs
hdfs dfs -copyFromLocal ./setup.hql /user/maria_dev/case_study/setup.hql
hdfs dfs -copyFromLocal ./insert.hql /user/maria_dev/case_study/insert.hql

#move workflow_2 and coordinator_2 to hdfs
hdfs dfs -copyFromLocal ./workflow_2.xml /user/maria_dev/case_study/workflow_2.xml
hdfs dfs -copyFromLocal ./coordinator_2.xml /user/maria_dev/case_study/coordinator_2.xml

#run metastore script to create sqoop metastore commands
./metastore.sh

#run oozie coordinator job 
oozie job -oozie http://localhost:11000/oozie -config ./job.properties -run
