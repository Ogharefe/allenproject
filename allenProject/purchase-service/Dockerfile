FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar
COPY target/purchase-service-0.0.1-SNAPSHOT.jar .

# Copy the wait-for script
COPY wait-for-postgres-and-kafka.sh /wait-for-postgres-and-kafka.sh

# Make it executable
RUN chmod +x /wait-for-postgres-and-kafka.sh
RUN sed -i 's/\r$//' /wait-for-postgres-and-kafka.sh
RUN apk add --no-cache netcat-openbsd
# Use the script as entrypoint
ENTRYPOINT ["/wait-for-postgres-and-kafka.sh", "postgres-purchase", "5432", "kafka", "9092", "purchase-service-0.0.1-SNAPSHOT.jar"]
# ENTRYPOINT ["/wait-for-postgres-and-kafka.sh", "${POSTGRES_HOST}", "${POSTGRES_PORT}", "${KAFKA_HOST}", "${KAFKA_PORT}"]
