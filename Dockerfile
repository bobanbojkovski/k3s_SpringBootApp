FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILDER

WORKDIR /app
COPY ./demo/pom.xml ./
COPY ./demo/src ./src/
RUN mvn package


FROM openjdk:8-alpine 

WORKDIR /app
COPY --from=MAVEN_BUILDER /app/target/demo-0.0.1-SNAPSHOT.jar ./
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
