# Cybcube test task

## General information
This test task consist of 2 microservices, which communicate with each other via Apache Kafka. 

First microservice (supplier) can fowrk in to ways: 
* Reads persons list from propeties file randomly (filename can be defined).  Read "calculation seed" from property file and send all this data to queue.
* Listen to `HTTP POST` message, get "person" from it, read "calculation seed" from property file and send all this data to queue.

Another microservice (consumer) listen to queue, calculate rating of person, save info to database (postgres) with "visit counter" in extra, writes information to console and also write it to cache manager (Redis). 

As I understand from test task no any databse is needed, but I decided to use it in extra.

## Used technologies and tools

* Java 11
* Gradle
* Cache manager - redis
* Spring cloud steams - Queue manages (supports RabbitMQ, Kafka and some others. Can be easily switch from one to another without changing code (only properties file))
* Postgres
* SpringBoot 2
* Lombok
* Docker
* Docker-compose
* Spring Data JPA
* Some other small spring embedded things

## Supplier microservice API

There are 2 end points in this microservice
* To send custom person to queue
`POST http://localhost:8811/api/supplier/sent-person-to-queue`

    Data format:
    ```
    {
      "first_name":"ABC",
      "last_name":"123",
      "age": 44
    }
    ```
* To enable/disable scheduler for sending predefined persons from test-data file.
    
    `GET http://localhost:8811/api/supplier/enable-scheduler?enabled=false`
    
    Where `enabled` can be `true` or `false`
    
    If scheduler is enabled, then it begins to send to queue predefined persons from test data.
    Person list can be defined in separate properties file. Name ot this file can be set in properties-file or like environment variable. 
    Scheduler period also can be defined in properties file.

## Consumer microservice API

There are 2 end points in this microservice

* To see all persons from redis

    `GET http://localhost:8812/api/consumer/get-redis-person-list`
    
* To see all persons from DB

    `GET http://localhost:8812/api/consumer/get-db-person-list`

## Sender configuration

* Configurations in `application.properties` file:
    ```
    test-data=classpath:test-data.properties
    scheduler-period=2000
    ```
    * `test-data` is link to test data. See below.
    * `scheduler-period` is period of scheduler sender, to send persons from predefined list.
* Configurations in `test-data.properties` file:
    ```
    test-data.calculation-seed=0.5
    
    test-data.person[2].firstName=Dmitry
    test-data.person[2].lastName=Ivanovich
    test-data.person[2].age=4
    ```
    * `test-data.calculation-seed` is calculation seed from the test task document.
    *  Configurations for predefined persons for scheduler.
        ```
          test-data.person[2].firstName=Dmitry
          test-data.person[2].lastName=Ivanovich
          test-data.person[2].age=4
        ```
        For now there is 7 persons

## How to run project

This project can be run in 2 ways:
* With docker-compose
* Like local applications

### Run with docker-compose

To run this application with "docker-compose" you need just run the command :

`docker-compose up -d`

Yon do not need even to have Java installed on your local computer, because compilation of the source code take place inside docker images.
Docker-compose will start those docker images needed for this project :
* consumer
* redis-commander
* supplier
* kafka
* zookeeper
* redis
* postgres

### Run like local applications

Before run (consumer) and (supplier) applications, you need provide environment for them:

supplier needs :

* kafka (localhost:9092)
* zookeeper(localhost:2181)

consumer needs:

* kafka (localhost:9092)
* zookeeper(localhost:2181)
* postgres (localhost:5432; password=postgres; username=postgres)
* redis (localhost:6379)

You can run all this applications by yourself with this data, or you can just run command :

`docker-compose up -d postgres redis-commander kafka`

But before running this command, you need to modify `docker-compose.yaml` to set kafka on `localhost`.

Line 15 : `  - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092`

, or define `kafka` as `localhost` for your PC. 

And all needed containers will start automatically for you.
After that you can just run **consumer** and **supplier** like normal SpringBoot applications.

### See the results

It is 3 ways to see result data generated by this project:
* In console -- **consumer** writes to console calculated rating of each person in format like it was pointed in task description:
`"<firstName> <lastName> has <socialRatingScore> score"`

* In Redis UI - "redis-commander"  -- You need to type in browser this address : `localhost:8813` and You will see all persons data cached by redis. Where key is `<firstName> <lastName>` and values is `score`

* In consumer API endpoints.

    * To see all persons from redis
    
        `GET http://localhost:8812/api/consumer/get-redis-person-list`
        
    * To see all persons from DB
    
        `GET http://localhost:8812/api/consumer/get-db-person-list`

### Tests

I covered by tests service layer of supplier and consumer microservice.
In this project not much "business" logic and there is no any necessity to cover with tests all classes, because basically, I will test external libs, but not code written by me.

### TODO

* Add swagger for easy testing
