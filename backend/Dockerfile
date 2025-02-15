FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

RUN apt-get update && apt-get install -y curl

RUN addgroup --system spring && adduser --system spring --ingroup spring
RUN chown -R spring:spring /app

USER spring:spring

ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

EXPOSE 8080
EXPOSE 5005

CMD ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="$JAVA_OPTS"