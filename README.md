# FinLimit
### Микросервис, который будет интегрирован в банковскую систему для расчета превышения клиентом лимита по транзакциям.



## Содержание

- [Стек](#Стек)
- [Предварительная подготовка](#предварительная-подготовка)
- [Установка](#установка)
- [Документация по API](#документация-по-API)
- [JavaDocs](#JavaDocs)

## Стек
- **Java 21**
- **Spring Boot 3.4.3**
- **PostgreSQL**
- **Apach Cassandra**
- **Open API/Swagger**
- **Liquibase**

## Предварительная подготовка
На вашем компьтери должны быть установлены Git и Docker

## Установка

1. **Клонируйте репозиторий:**

    ```sh
    git clone https://github.com/EvRy111001110001/TaskManagement
    cd task-management-system
    ```

2. **Заполните переменные в файле env:** (например так)
    ```sh
    DB_USERNAME=username123
    DB_PASSWORD=password123
    KEY_API=123533456547686
    ```
3. **Подготовить базы данных для приложения:**
        откройте Docker и запустите контенеры с базами данных
    ```sh
    docker run --name  postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
    docker run --name cassandra -d -p 9042:9042 cassandra:latest
    ```   

4. **Запустите приложение:**
    ```sh
    java -jar target/your-app.jar
    ``` 

## Документация по API
Документация по API доступна через Swagger UI. После запуска приложения вы можете получить к ней доступ по адресу: http://localhost:8080/swagger-ui.html

## JavaDocs
HTML-документацию можно найти в папке docs


## TODO

- [ ] Добавить Docker-контейнер
- [ ] Настроить CI/CD  
