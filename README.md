# SETUP PROJECT
      Java 17+
      Spring Boot 3.2
      H2
      Postgres DB
      Rest API
      Docker
      Kafka
      Swagger
      GIT
#### 1. Find in project file task_manager/docker-compose.yml  and use this command
 * docker-compose up
#### *After this the project will start working*
#### Samples : *docker-compose*
```
version: '2'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.4
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - 22181:2181

  kafka:
    image: confluentinc/cp-kafka:7.4.4
    depends_on:
      - zookeeper
    ports:
      - 29092:29092
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092,PLAINTEXT_HOST://localhost:29092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```
# Table of describe requests

| Request                               |              Request Type              | Description        |
|---------------------------------------|:--------------------------------------:|--------------------|
| /api/task_management/{id}             |                  PUT                   | Update status task |
| /api/task_management/{id}             |                 DELETE                 | Remove task        |
| /api/task_management/{id}             |                 PATCH                  | Update body task   |
| /api/task_management                  |                  GET                   | Get all tasks      |
| /api/task_management                  |                  POST                  | Create task        |

