FROM openjdk:8-alpine

WORKDIR /app
COPY demo-0.0.1-SNAPSHOT.jar ./
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]


