# open-school-project

Простой RESTful сервис для управления задачами на Spring Boot
(CRUD приложение + логи через аспекты)
1. Task(id, title, description,userId)
2. POST /tasks — создание новой задачи.
3. GET /tasks/{id} — получение задачи по ID.
4. PUT /tasks/{id} — обновление задачи.
5. DELETE /tasks/{id} — удаление задачи.
6. GET /tasks — получение списка всех задач.




В качестве БД используется PostgreSQL 14
```
login: postgres

pass: postgres
```
В случае отсутствия инструментов инспектироваия содержимого брокера Kafka, 
после запуска всех образов с помощью Kafka-ui через браузер доступен просмотр содержимого топиков в Kafka по адресу: 
`http://localhost:8090/`


Для добавления записей через Postman:
```
{
"title": "Task1",
"description": "aaaaaaaaaaaaa",
"userId": 1
}

{
"title": "Task2",
"description": "bbbbbbbbbbbbb",
"userId": 2
}
```

Тестирование
отправляя HTTP-запросы используя Postman или curl:

Создание задачи:

`curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"title": "Task 1", "description": "Description 1", "userId": 1, "status": "NEW"}'`

Обновление задачи:

`curl -X PUT http://localhost:8080/tasks/1 -H "Content-Type: application/json" -d '{"title": "Task 1 Updated", "description": "Updated Description", "userId": 1, "status": "IN_PROGRESS"}'`

Получение всех задач:

`curl -X GET http://localhost:8080/tasks`

Удаление задачи:

`curl -X DELETE http://localhost:8080/tasks/1`



//=====================

Итого :

* Логирование входящих запросов:  @Before для логирования начала выполнения метода.
* Логирование результатов:  @After для логирования завершения выполнения метода.
* Логирование входящих данных:  @Around для логирования аргументов и возвращаемых значений.
* Логирование ошибок:  @AfterThrowing для логирования исключений.
* Логгирование успешного выполнения: @AfterReturning  для логгирования корректного завершения метода

## Kafka:
1. Использовать docker-compose для установки Kafka в Docker (пример демонстрируется на уроке)
1.1 Установить необходимые инструменты для работы с Kafka. Big Data Tools, Offset Explorer и др. на свое усмотрение и возможности, результатом должна быть возможность "заглянуть" в кафку
2. Создать тестовый topic, установленными средствами
3. Отправить в топик тестовые сообщения
4. Убедиться в их наличии
5. Сконфигурировать Kafka, Producer, Consumer в вашем сервисе рабты с Task.
6. Продюсер пишет в топик id и новый статус task у которых он изменился, при соответствующем входящем запросе (обновления task).
7. Консьюмер слушает этот топик, читает оттуда сообщения, и отправляет в NotificationService (условно сервис заглушка, для имитации отправки уведомления, можно просто логировать)
8. Дополнительно: использовать spring-boot-starter-mail, и в NotificationService реализовать отправку email *

# Используемый стек:
- Java 17, Spring Boot 3.4 (Spring Web, Spring Data, Spring AOP )
- DataBase - PostgreSQl
- Kafka
- Docker (docker-compose)