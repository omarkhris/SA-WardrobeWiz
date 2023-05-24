# Use a base image with Java 8 installed
FROM openjdk:8-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the target directory to the container
COPY target/*.jar app.jar

# Expose the port on which your application listens
EXPOSE 8888

# Set the entrypoint command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]
