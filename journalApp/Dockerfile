# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render passes the port in $PORT, so use that
ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar -Dserver.port=$PORT app.jar"]
