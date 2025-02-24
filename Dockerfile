FROM maven:3.9.5-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /target/online-tiffin-service-0.0.1-SNAPSHOT.jar online-tiffin-service.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "online-tiffin-service.jar" ]