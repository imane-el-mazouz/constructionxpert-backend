FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/app.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8099
ENTRYPOINT ["java","-jar","/app.jar"]
