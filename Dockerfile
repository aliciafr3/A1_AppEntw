FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/A1_AppEntw-0.0.1-SNAPSHOT.jar /app/LabAppEntw_Task1.jar

ENTRYPOINT ["java", "-jar", "LabAppEntw_Task1.jar"]



