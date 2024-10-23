# Use an OpenJDK image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY build/libs/A1_AppEntw-0.0.1-SNAPSHOT.jar /app/LabAppEntw_Task1.jar

# Run the application
ENTRYPOINT ["java", "-jar", "LabAppEntw_Task1.jar"]



