FROM openjdk:21-jdk-alpine

ARG JAR_FILE=target/user-service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8099

ENTRYPOINT ["java","-jar","/app.jar"]
