FROM --platform=$BUILDPLATFORM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B dependecy:go-offline
COPY src ./src
RUN ./mvnw -B clean package -DskipTests


FROM eclipse-temurin:21-jre AS run
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
