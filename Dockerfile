FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar /app/F1Challenge.jar
CMD ["java", "-jar", "F1Challenge.jar"]
