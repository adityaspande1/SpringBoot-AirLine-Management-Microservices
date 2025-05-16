# Multi-stage build for user-service

# Stage 1: Build and test the application
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .
# Copy the src directory
COPY src/ ./src/

# Run tests and build the JAR file
RUN mvn clean test package

# Stage 2: Create the runtime image
FROM openjdk:21-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8082

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"] 