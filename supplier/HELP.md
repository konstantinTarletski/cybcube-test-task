## Supplier needs :
   
   * kafka (localhost:9092)
   * zookeeper(localhost:2181)

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