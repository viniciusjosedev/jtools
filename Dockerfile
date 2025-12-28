# DEVELOPMENT
FROM eclipse-temurin:17-jdk AS development

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

CMD ["./docker-entrypoint.sh"]

# BUILD
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# PRODUCTION
FROM eclipse-temurin:17-jdk AS production

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]