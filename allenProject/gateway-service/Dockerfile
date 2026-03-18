FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY target/gateway-service-0.0.1-SNAPSHOT.jar app.jar

# Expose debug port
EXPOSE 5008

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5008","-jar","/app/app.jar"]
