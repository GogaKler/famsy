FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]