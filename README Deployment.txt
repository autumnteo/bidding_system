Welcome to the Deployment Instructions for the AntBuild Bidding Application

Requirements: Java 11, Apache Maven (https://maven.apache.org/install.html) , WAMP (https://www.w3resource.com/php/installation/install-wamp.php)

Step 1: Import the bidapplication.sql file to create the required database

Step 2: Create the required user with the following snippet (Username: is213, Password: root)

CREATE USER 'is213'@'%' IDENTIFIED WITH mysql_native_password AS 'root';GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, FILE, ALTER ON *.* TO 'is213'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;

Step 4: Execute the build_services.bat script to build all the services required for the application. Your MySQL DB should be turned on during this process. Communication failures flagged by the build between the module and the Eureka Discovery Service is normal and expected.

Step 5: Add the contents of the UI-Module to your /wwww folder in your local Apache Web Server installation.

Step 6: Run each of the jar files using the run_services.bat