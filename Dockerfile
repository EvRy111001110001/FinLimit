FROM openjdk:21-slim AS builder

WORKDIR /app

COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

FROM openjdk:21-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

# Добавляем задержку перед запуском
ENTRYPOINT ["sh", "-c", "sleep 60 && java -jar /app/app.jar"]