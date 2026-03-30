FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/bff-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8084
CMD ["java", "-jar", "/app/app.jar"]
