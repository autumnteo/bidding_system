call mvn package -f ./bid-module/pom.xml
echo on
call mvn package -f ./eureka-module/pom.xml
echo on
call mvn package -f ./gateway-module/pom.xml
echo on
call mvn package -f ./processing-module/pom.xml
echo on
call mvn package -f ./resource-module/pom.xml
echo on
call mvn package -f ./user-module.xml
echo on
echo "BUILD COMPLETED"
PAUSE