Kenya Medical Research Institute - Wellcome Trust Programme
---------------------------------------------------------------------------
---------------------------------------------------------------------------

Prerequisites
-------------

1.An installation of Java Development Kit (JDK) 1.6 or higher. For windows
  users, the JDK installation should be included in the environment path
  i.e append 
	%JAVA_HOME%/bin to system path (%JAVA_HOME% being path to JDK
        installation)
2.An Installation of Apache Tomcat 7 on the server or local machine
3.An installation of Apache Maven 3.0.5. For windows users, the Apache Maven
  installation should be included in the enviromnent path i.e. append
        %MVN_HOME%/bin to system path (%MVN_HOME% being path to Apache Maven
        installation)
4.An installation of MySQL server 5.x in the production environment with the 
  database of the data you want to report/post to DHISv2.
5.An internet connection


Installation
------------

1.Open shell/command prompt window and navigate to %PROJECT_HOME%/src/main/webapp/WEB-INF 
  (%PROJECT_HOME%) being the location of the kemri-wellcome-dhisreport folder.

2.Open the mysql.jdbc.properties file and edit the following lines with your own installation
  settings in the following manner:

	jdbc.url=jdbc:mysql://[server name/ip]:3306/[database name]
	jdbc.username=[database root user login]
	jdbc.password=[database root user password]

3.Navigate to %PROJECT_HOME%/src/main/resources, open log4j.properties file and edit the following
  line to point to a folder that tomcat7 user has permission to write to
  
 	log4j.appender.file.File=[Path to log location]/RedCapDhisv2.log

4.Navigate to the root folder i.e %PROJECT_HOME% and type the command
	
	mvn package install
5.After completion of execution, copy the %PROJECT_HOME%/target/KWTRDI.war folder to 
  %TOMCAT7_HOME%/webapps/

6.Start the Tomcat7 server if not started
