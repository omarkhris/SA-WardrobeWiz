# Use a base image with Java 11 installed
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the target directory to the container
COPY ShoppingCart/target/*.jar app.jar

# Expose the port on which your application listens
EXPOSE 8080

# Set the entrypoint command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
