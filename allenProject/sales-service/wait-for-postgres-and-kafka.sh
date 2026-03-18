#!/bin/sh
set -e

# Wait for Postgres
host1=$1
port1=$2
echo "Waiting for Postgres at $host1:$port1..."
while ! nc -z $host1 $port1; do
  sleep 2
done
echo "Postgres is up."

# Wait for Kafka
host2=$3
port2=$4
echo "Waiting for Kafka at $host2:$port2..."
while ! nc -z $host2 $port2; do
  sleep 2
done
echo "Kafka is up."

# Start the Spring Boot app with debug options
exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5007 -jar /app/$5
