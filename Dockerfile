FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/*.jar /app/service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
