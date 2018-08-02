#this script will setup workflow and coordinator for workflow 1
#create case study folder in hdfs
hdfs dfs -mkdir /user/maria_dev/case_study/

#move workflow and coordinator xml files to case study folder
hdfs dfs -copyFromLocal ./workflow.xml /user/maria_dev/case_study/workflow.xml
hdfs dfs -copyFromLocal ./coordinator.xml /user/maria_dev/case_study/coordinator.xml

#move the hive script file to the case study folder
hdfs dfs -copyFromLocal ../2.2.2\ Data\ Loading\ Module/hive.hql /user/maria_dev/case_study/hive.hql

#create lib directory and copy java-json extension library jar
hdfs dfs -mkdir /user/maria_dev/case_study/lib/
hdfs dfs -copyFromLocal ./java-json.jar /user/maria_dev/case_study/lib/java-json.jar

#run the sqoop metastore script and setup all the sqoop metastore jobs for oozie
./metastore.sh

#load the coordinator into oozie
oozie job -oozie http://localhost:11000/oozie -config ./job.properties -run
