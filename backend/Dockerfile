FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

RUN apt-get update && apt-get install -y curl

ARG USER_ID=1000
ARG GROUP_ID=1000

RUN groupadd -g ${GROUP_ID} spring && \
    useradd -u ${USER_ID} -g spring -s /bin/bash spring

RUN mkdir -p /home/spring/.m2 && \
    chown -R spring:spring /home/spring && \
    chown -R spring:spring /app

RUN chown -R spring:spring /app

USER spring:spring

ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
ENV MAVEN_CONFIG=/home/spring/.m2

EXPOSE 8080
EXPOSE 5005

CMD ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="$JAVA_OPTS"