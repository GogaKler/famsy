FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

CMD ["./mvnw", "spring-boot:run"]