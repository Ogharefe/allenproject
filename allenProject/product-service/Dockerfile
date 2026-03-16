FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar
COPY target/product-service-0.0.1-SNAPSHOT.jar .

# Copy the wait-for script
COPY wait-for-postgres-and-kafka.sh /wait-for-postgres-and-kafka.sh

# Make it executable
RUN chmod +x /wait-for-postgres-and-kafka.sh
RUN sed -i 's/\r$//' /wait-for-postgres-and-kafka.sh
# Use the script as entrypoint
ENTRYPOINT ["/wait-for-postgres-and-kafka.sh", "postgres-product", "5432", "kafka", "9092", "product-service-0.0.1-SNAPSHOT.jar"]
