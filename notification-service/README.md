🌐 Spring Boot Notification Service  
This is a Spring Boot application that provides a Notification Service. The project uses Springfox with OpenAPI 3.0 for API documentation, MailHog for email testing, and RabbitMQ for message queuing.  

🚀 Getting Started
These instructions will help you set up the project on your local machine for development and testing purposes.

📋 Prerequisites
Java 8 or later
Maven 3.x
Docker

📦 Clone the Repository
To get the project, clone the repository to your local machine:
git clone https://github.com/yourusername/spring-boot-notification.git

📧 Set up MailHog and RabbitMQ
Run MailHog and RabbitMQ using Docker:
docker run -d -p 8025:8025 -p 1025:1025 --name mailhog mailhog/mailhog
docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management
This will start MailHog on ports 8025 (web interface) and 1025 (SMTP), and RabbitMQ on ports 5672 (AMQP) and 15672 (management interface).

🏗️ Build and Run the Application
Navigate to the project folder and use Maven to build and run the application:
cd spring-boot-notification
mvn clean install
mvn spring-boot:run
The application will start on port 8087.

📚 API Documentation
The API documentation is generated using Springfox with OpenAPI 3.0. You can access the Swagger UI at the following URL:
http://localhost:8087/swagger-ui/

📨 MailHog
MailHog is an email testing tool that can be used to test email sending in your application. You can access its web interface at the following URL:
http://localhost:8025

🐇 RabbitMQ
RabbitMQ is a message broker used for asynchronous processing in this project. You can access the RabbitMQ management interface at the following URL:
http://localhost:15672
The default login credentials for RabbitMQ are guest for both username and password.

📁 Project Structure
The main components of the project include:

NotificationService: This will contain the function of mail service.
NotificationController: The REST controller that handles incoming API requests and rabbit queue message.
RabbitConfig: The configuration class for rabbitMQ
SwaggerConfig: The configuration class for setting up Springfox with OpenAPI 3.0.

📦 Dependencies
This project uses the following dependencies:

Spring Boot: Provides core functionality for building Spring applications.
Springfox: Allows for API documentation generation using OpenAPI 3.0.
MailHog: Email testing tool used to capture and display emails.
RabbitMQ: Message broker for asynchronous processing.

Add the following dependencies to your pom.xml file:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
<!-- Add other dependencies required for MailHog and RabbitMQ integration -->

📄 License
This project is licensed under the MIT License.
