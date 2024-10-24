FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/A1_AppEntw-0.0.1-SNAPSHOT.jar /app/A1_AppEntw.jar

CMD ["java", "-jar", "A1_AppEntw.jar"]
