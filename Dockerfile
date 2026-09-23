# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache dependencies separately from source for faster rebuilds
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Run as non-root for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /workspace/target/*.jar app.jar

# Defaults only — real values are injected at runtime by Render (see render.yaml)
ENV DB_HOST=localhost \
    DB_PORT=5432 \
    DB_NAME=ms_exam \
    DB_USER=postgres \
    DB_PASSWORD=postgres \
    JAVA_OPTS=""

# Render sets PORT dynamically; Spring Boot must bind to it
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
