# Stage 1: Build with Gradle Wrapper and Corretto 21
FROM gradle:8.14-jdk21-corretto AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

# Stage 2: Run the Spring Boot app with Corretto 21
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]