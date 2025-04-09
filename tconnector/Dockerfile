FROM maven:3.9-eclipse-temurin-23-alpine as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /opt/build/tconnector
COPY pom.xml /opt/build/tconnector/
COPY src /opt/build/tconnector/src/
RUN mvn -f /opt/build/tconnector/pom.xml clean package -B -DskipTests


FROM eclipse-temurin:23-jdk-alpine
WORKDIR /opt/app/tconnector
COPY --from=BUILDER /opt/build/tconnector/target/*.jar /opt/app/tconnector/tconnector.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "--add-opens=java.base/java.lang=ALL-UNNAMED", "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED", "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED", "--add-opens=java.base/java.io=ALL-UNNAMED", "--add-opens=java.base/java.net=ALL-UNNAMED", "--add-opens=java.base/java.nio=ALL-UNNAMED", "--add-opens=java.base/java.util=ALL-UNNAMED", "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED", "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED", "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED", "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED", "--add-opens=java.base/sun.security.action=ALL-UNNAMED", "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED", "--add-opens=java.base/java.time=ALL-UNNAMED", "-jar", "/opt/app/tconnector/tconnector.jar"]