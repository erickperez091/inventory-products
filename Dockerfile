FROM eclipse-temurin:21-alpine

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 9080

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-XX:+UseG1GC","-XX:+UseStringDeduplication","-XX:+ExitOnOutOfMemoryError","-jar","app.jar"]