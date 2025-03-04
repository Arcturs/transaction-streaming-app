FROM maven:3.9-eclipse-temurin-23-alpine as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /opt/build/tproducer
COPY pom.xml /opt/build/tproducer/
COPY src /opt/build/tproducer/src/
RUN mvn -f /opt/build/tproducer/pom.xml clean package -B -DskipTests


FROM openjdk:23
WORKDIR /opt/app/db
COPY --from=BUILDER /opt/build/tproducer/target/*.jar /opt/app/tproducer/tproducer.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar", "/opt/app/tproducer/tproducer.jar"]