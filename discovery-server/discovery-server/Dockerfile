# Build stage
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests package

# Run stage
FROM eclipse-temurin:23-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9011
ENTRYPOINT ["java","-jar","app.jar"]
