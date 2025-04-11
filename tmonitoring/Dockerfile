FROM maven:3.9-eclipse-temurin-23-alpine as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /opt/build/tmonitoring
COPY pom.xml /opt/build/tmonitoring/
COPY src /opt/build/tmonitoring/src/
RUN mvn -f /opt/build/tmonitoring/pom.xml clean package -B -DskipTests


FROM eclipse-temurin:23-jdk-alpine
WORKDIR /opt/app/tmonitoring
COPY --from=BUILDER /opt/build/tmonitoring/target/*.jar /opt/app/tmonitoring/tmonitoring.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT} 8091

ENTRYPOINT ["java","-jar", "/opt/app/tmonitoring/tmonitoring.jar"]