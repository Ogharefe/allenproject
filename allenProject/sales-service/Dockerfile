# Use a base image with Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/*.jar app.jar

# Copy the wait-for-postgres-and-kafka.sh script
COPY wait-for-postgres-and-kafka.sh wait-for-postgres-and-kafka.sh

# Make the script executable
RUN chmod +x wait-for-postgres-and-kafka.sh
RUN sed -i 's/\r$//' wait-for-postgres-and-kafka.sh

# Install netcat (nc) which is needed for the wait script
RUN apk add --no-cache netcat-openbsd

# Expose the port the application runs on
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["./wait-for-postgres-and-kafka.sh", "postgres-sales", "5432", "kafka", "9092", "app.jar"]
