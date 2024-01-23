# Use an OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container at the defined working directory
COPY target/adminproxy-0.0.1-SNAPSHOT.jar /app/adminproxy-0.0.1-SNAPSHOT.jar

# Expose the port that the Spring Boot application uses
EXPOSE 8001

# Command to run the application
CMD ["java", "-jar", "adminproxy-0.0.1-SNAPSHOT.jar"]


#How to build and run the docker
# 1. mvn clean install
# 2. docker build -t adminproxy .
# 3. docker run -p 8001:8001 adminproxy
