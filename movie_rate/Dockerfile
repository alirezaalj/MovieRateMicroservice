# Use an official Java 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file to the container
COPY target/movie_rate-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose port 8082 for the application
EXPOSE 8082

# Start the Java application
CMD ["java", "-jar" ,"/app/application.jar"]