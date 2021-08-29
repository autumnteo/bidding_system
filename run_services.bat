start "Starting Eureka Module" /WAIT java -jar ./eureka-module/target/eureka-module-1.0.jar
start "Starting Gateway Module" /WAIT java -jar ./gateway-module/target/gateway-module-1.0.jar
start "Starting Bid Module" /WAIT java -jar ./bid-module/target/bid-module-1.0.jar
start "Starting Resource Module" /WAIT java -jar ./resource-module/target/resource-module-1.0.jar
start "Starting User Module" /WAIT java -jar ./user-module/target/user-module-1.0.jar
start "Starting Processing Module" /WAIT java -jar ./processing-module/target/processing-module-1.0.jar
ECHO "STARTUP COMPLETE"
PAUSE