FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/task-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]
