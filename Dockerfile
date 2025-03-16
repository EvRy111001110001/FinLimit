# 1. Билдим приложение
FROM openjdk:21-slim AS builder

WORKDIR /app

COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# 2. Создаём финальный контейнер
FROM openjdk:21-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]