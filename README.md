# FinLimit
### Микросервис для интеграции в банковскую систему, предназначенный для расчета превышения клиентом лимита по транзакциям.

## Содержание

- [Стек](#стек)
- [Предварительная подготовка](#предварительная-подготовка)
- [Установка](#установка)
- [Документация по API](#документация-по-api)
- [JavaDocs](#javadocs)
- [TODO](#todo)

## Стек
- **Java 21**
- **Spring Boot 3.4.3**
- **PostgreSQL**
- **Apache Cassandra**
- **OpenAPI/Swagger**
- **Liquibase**

## Предварительная подготовка
На вашем компьютере должны быть установлены **Git** и **Docker**.

## Установка

1. **Клонируйте репозиторий:**
    ```sh
    git clone https://github.com/EvRy111001110001/FinLimit.git
    cd FinLimit
    ```

2. **Заполните переменные в файле `.env`** (пример):
    ```sh
    DB_USERNAME=username123
    DB_PASSWORD=password123
    KEY_API=123533456547686
    ```

3. **Запустите базы данных в Docker:**
    ```sh
    docker run --name postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
    docker run --name cassandra -d -p 9042:9042 cassandra:latest
    ```

4. **Соберите приложение:**
    ```sh
    ./gradlew clean build -x test
    ```

5. **Создайте Docker-образ:**
    ```sh
    docker build -t finlimit-app .
    ```

6. **Запустите Docker-контейнер приложения:**
    ```sh
    docker run -p 8080:8080 --env-file .env finlimit-app
    ```

## Документация по API
Документация API доступна через Swagger UI.  
После запуска приложения она будет доступна по адресу:  
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## JavaDocs
HTML-документация сгенерирована в папке `docs`.

## TODO
- [ ] Добавить Docker Compose
- [ ] Настроить CI/CD