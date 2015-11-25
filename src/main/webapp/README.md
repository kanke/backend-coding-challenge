Goal
--------------
A simple web-app backend to complement the supplied front-end code.

Completed Work
--------------

1. Saves expenses as entered to a database.
2. Retrieves them for display on the page. 
3. Adds a new column to the table displaying the VAT amount for each expense.
4. This README contains instructions on how to build and run this app.

Extra Credit
------------
Calculates the VAT client-side as the user enters a new expense, before they save the expense to the database.

##### What frameworks, tools and tech stack did I use?
* Intelli J IDE
* Maven 3.3.3 as build tool
* XSD to generate domain files
* Hibernate as ORM
* MYSQL as RDBMS
* JDk1.8
* Mockito and Junit as testing framework
* Jersey and Jackson to convert to/from JSON

##### What application servers did I use?
Tomcat version 7

##### What database did I use?
MySQL. ***Make sure you have the MySQL server and Tomcat running at all times whilst using this application*** else use skip tests maven command to skip the test. Instructions on how to do this is explained below.

##### Why doesn’t the test include X?
It does include tests!

To Build Project
==================

To build project:- <code>mvn clean install</code>

To skip tests(if you don't have mysql running and database table created):- <code>mvn clean install -Dmaven.test.skip=true</code>

To deploy to Tomcat(Tomcat version 7):- <code>mvn tomcat7:deploy</code>

To redeploy:- <code>mvn tomcat7:redeploy</code>

To undeploy:- <code>mvn tomcat7:undeploy</code>

To Create Database
==================

To create database and table in database(MYSQL). This creates database and table  - Run expenses.sql script located in /src/main/java/

**To run this project successfully, please add the following to your Tomcat and Maven configurations.**

<code>
1.1 Tomcat Authentication
Add an user with roles manager-gui and manager-script.

%TOMCAT7_PATH%/conf/tomcat-users.xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>

	<role rolename="manager-gui"/>
	<role rolename="manager-script"/>
	<user username="admin" password="password" roles="manager-gui,manager-script" />

</tomcat-users>

1.2 Maven Authentication
Add above Tomcat’s user in the Maven setting file, later Maven will use this user to login Tomcat server.

%MAVEN_PATH%/conf/settings.xml
<?xml version="1.0" encoding="UTF-8"?>
<settings ...>
	<servers>

		<server>
			<id>TomcatServer</id>
			<username>admin</username>
			<password>password</password>
		</server>

	</servers>
</settings>
</code>

To view Application
==================

After following above steps, navigate to <code>http://localhost:8080/#/expenses</code> to add, delete and view all expenses.

 ***NOTE:- Make sure you have the MySQL server and Tomcat running at all times whilst using this application***