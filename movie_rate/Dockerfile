# Use an official Java 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file to the container
COPY target/movie_rate-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose port 53 for the application DNS service to listen on
EXPOSE 8082

# Start the Java application
CMD ["java", "-jar" ,"/app/application.jar"]