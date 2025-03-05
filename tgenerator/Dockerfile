FROM maven:3.9-eclipse-temurin-23-alpine as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /opt/build/tgenerator
COPY pom.xml /opt/build/tgenerator/
COPY src /opt/build/tgenerator/src/
RUN mvn -f /opt/build/tgenerator/pom.xml clean package -B -DskipTests


FROM eclipse-temurin:23-jdk-alpine
WORKDIR /opt/app/db
COPY --from=BUILDER /opt/build/tgenerator/target/*.jar /opt/app/tgenerator/tgenerator.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar", "/opt/app/tgenerator/tgenerator.jar"]