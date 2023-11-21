# Rent a Car

Java 21, Spring Cloud based microservices application for renting cars. POC of the Spring Cloud microservices
architecture written in DDD and hexagonal architecture.

### Repository

https://github.com/maciejgz/rac

### Tech stack

- **Language** - Java 21
- **Frameworks** - Spring Boot, Spring Cloud
- **Database** - MongoDB
- **Message broker** - Kafka
- **Search** - Elasticsearch
- **Monitoring** - Micrometer, Prometheus, Grafana
- **Tracing** - Zipkin
- **Code analysis** - Sonar
- **Build tool** - Gradle
- **Containerization** - Docker, Docker Compose

### Requirements

#### Functional

- User can search for a car to rent by his coordinates
- User can rent an available car
- Position of the car is constantly updated and available for the administrator
- User can return a car
- User can see a history of his rents
- Administrator can add or remove a car from the pool of the available cars
- User is charged for:
    - distance traveled (to simplify - the distance is calculated as a difference between the
      current and the previous carLocation of the car) - 1$ per km
    - time of the rent - 0.2$ per minute

#### Non-functional

- Gradle as a build tool
- DDD and hexagonal architecture - keep application and domain logic independent of the framework. All beans shall be
  created in the infrastructure layer config classes.
  The only exception are the transactional annotations in the application service layer.
- Domain events should be different from the events sent to the message broker. Domain events are used for internal
  communication between services. For the simplicity, we will use the same events for both internal and external
  communication.
- Domain services should operate on pure data without commands. Commands reach the application service layer and from
  there we pass on pure data or domain DTOs.
- Kafka as a message broker
- Internal communication between services by events with sagas
- Requests to the services through the API Gateway
- API Gateway receives a success message right after submission of rent request - then asynchronous commit is sent after
  successful saga execution
- In the final version use time series Cassandra column database for storing the car carLocation updates. At first -
  MongoDB shall be used.

### Components

- rac-api-gateway - 8080 - API gateway
- rac-user-service - 8081 - User management service
- rac-rent-service - 8082 - Renting service
- rac-search-service - 8083 - search functionality
- rac-car-service - 8084 - car fleet management
- rac-location-service - 8085 - gathering carLocation data of the cars and users. All the agents are sending the data to
  this service. Should use Cassandra column database for storing the carLocation data.
- rac-simulation-service - 8086 - simulates the data of the cars and users
- rac-discovery-service - http://localhost:8761/ - Spring Eureka server
- rac-configuration-service - 8888 - Spring Cloud Config
- MongoDB - 27017 - centralized database - each microservice will have a different schema
- Cassandra - 9042 - time series database for storing the car and user carLocation updates
- Message broker
    - Kafka - 9092 - message broker
    - Zookeeper
- Search
    - Elasticsearch
    - Kibana
- Zipkin - http://localhost:9411/ - distributed tracing service
- Monitoring
    - Micrometer - observability facade
    - Prometheus - time series database
    - Grafana - http://localhost:9091/ - monitoring admin/admin
- Sonar - http://localhost:9000/ - code analysis

| component                 | port  | comment                                                                  |        group        |
|---------------------------|:-----:|:-------------------------------------------------------------------------|:-------------------:|
| rac-api-gateway           | 8080  | HTTP API Gateway                                                         |         APP         |
| rac-user-service          | 8081  | user management service                                                  |         APP         |
| rac-rent-service          | 8082  | renting service                                                          |         APP         |
| rac-search-service        | 8083  | search service                                                           |         APP         |
| rac-car-service           | 8084  | car fleet management                                                     |         APP         |
| rac-location-service      | 8085  | Cars and users location service                                          |         APP         |
| rac-simulation-service    | 8086  | Simulation service                                                       |         APP         |
| rac-discovery-service     | 8761  | Spring Cloud Eureka - discovery service                                  |         APP         |
| rac-configuration-service | 8888  | Spring Cloud Config - centralized config service                         |         APP         |
| rac-mongodb               | 27017 | MongoDB - database                                                       |         DB          |
| rac-cassandra             | 9042  | Cassandra - database                                                     |         DB          |
| rac-kafka                 | 9092  | Kafka - Message broker                                                   |   MESSAGE BROKER    |
| rac-zookeeper             | 2181  | Zookeeper - Kafka nodes synchronization service                          |   MESSAGE BROKER    |
| rac-elasticsearch         | 9200  | Elasticsearch - for search...                                            |       SEARCH        |
| rac-kibana                | 5601  | Kibana - Web UI for Elasticsearch                                        |       SEARCH        |
| rac-zipkin                | 9411  | Zipkin - distributed tracing collector                                   | DISTRIBUTED TRACING |
| rac-prometheus            | 9090  | Prometheus - time series database receives data from Micrometer agents   |     MONITORING      |
| rac-grafana               | 9091  | Grafana - Web UI for the containers monitoring data stored in Prometheus |     MONITORING      |
| rac-sonar                 | 9000  | Sonar - code analysis                                                    |    CODE ANALYSIS    |

### Events

#### User service events

| event ID         |  topic   | comment                                                        |
|------------------|:--------:|:---------------------------------------------------------------|
| RAC_USER_CREATED | RAC_USER | Sent when user is created                                      |
| RAC_USER_DELETED | RAC_USER | Sent when user is deleted                                      |
| RAC_USER_CHARGED | RAC_USER | Sent when user is charged for rent or due to some other reason |

### Car service events

| event ID                               |  topic  | comment                                                            |
|----------------------------------------|:-------:|:-------------------------------------------------------------------|
| RAC_CAR_CREATED                        | RAC_CAR | Sent when car is added to the fleet                                |
| RAC_CAR_DELETED                        | RAC_CAR | Sent when user is deleted                                          |
| RAC_CAR_RENT_SUCCESS                   | RAC_CAR | Sent when car is rented                                            |
| RAC_CAR_RENT_FAILED_ALREADY_RENTED     | RAC_CAR | Sent when car cannot be rented - already rented by other user      |
| RAC_CAR_RENT_FAILED_NOT_EXIST          | RAC_CAR | Sent when car cannot be rented - car already not exist in the pool |
| RAC_CAR_RETURN_SUCCESS                 | RAC_CAR | Sent when car is successfully returned from rental                 |
| RAC_CAR_RETURN_FAILED_ALREADY_RETURNED | RAC_CAR | Sent when car cannot be returned - already returned                |
| RAC_CAR_RETURN_FAILED_NOT_EXIST        | RAC_CAR | Sent when car cannot be returned - already returned                |

#### Rent related events

| event ID              |  topic   | comment                                                                                            |
|-----------------------|:--------:|:---------------------------------------------------------------------------------------------------|
| RAC_RENT_REQUEST_USER | RAC_RENT | Sent when rent is requested to validate request in the user service                                |
| RAC_RENT_REQUEST_CAR  | RAC_RENT | Sent when rent is requested and confirmed in user service. Car service should validate the request |
| RAC_RENT_CONFIRMATION | RAC_RENT | Sent when rent is confirmed in car service and should be confirmed in rent service                 |
| RAC_RENT_FAILED_USER  | RAC_RENT | Issue in rent validation in user service. Consumed by                                              |
| RAC_RENT_FAILED_CAR   | RAC_RENT | Issue in rent validation in car service                                                            |

#### Return related events

| event ID                    |   topic    | comment                                                                                                    |
|-----------------------------|:----------:|:-----------------------------------------------------------------------------------------------------------|
| RAC_RETURN_REQUEST_LOCATION | RAC_RETURN | Sent when return is requested to validate request and calculate distance traveled                          |
| RAC_RETURN_REQUEST_USER     | RAC_RETURN | Sent when return is requested and confirmed in carLocation service to validate request in the user service |
| RAC_RETURN_REQUEST_CAR      | RAC_RETURN | Sent when return is requested and confirmed in user service. Car service should validate the request       |
| RAC_RETURN_CONFIRMATION     | RAC_RETURN | Sent when return is confirmed in car service and should be confirmed in rent service                       |
| RAC_RETURN_FAILED_LOCATION  | RAC_RETURN | Issue in return validation in carLocation service                                                          |
| RAC_RETURN_FAILED_USER      | RAC_RETURN | Issue in return validation in user service                                                                 |
| RAC_RETURN_FAILED_CAR       | RAC_RETURN | Issue in return validation in car service                                                                  |

#### Location service events

| event ID                  |       topic       | comment                                    |
|---------------------------|:-----------------:|:-------------------------------------------|
| RAC_LOCATION_USER_CHANGED | RAC_LOCATION_USER | Sent when user has changed his carLocation |
| RAC_LOCATION_CAR_CHANGED  | RAC_LOCATION_CAR  | Sent when car has changed his carLocation  |

### Sagas

#### Rent car saga

- User sends a rent HTTP request to the API Gateway
- API Gateway sends a rent request to the rac-rent-service (HTTP)
- Rent service validates the request and:
    - in case of success: creates a rent, starts a saga and sends a rent request to the rac-user-service (Kafka) - *
      *RAC_RENT_REQUEST_USER**
    - in case of failure: sends a rent failure to the api gateway (HTTP)
- User service validates the event and:
    - in case of success - sets the user as a renter with the rental id and sends a rent request to the
      rac-car-service (Kafka) - **RAC_RENT_REQUEST_CAR**
    - in case of failure - sends a rent failure to the rac-rent-service (Kafka) - **RAC_RENT_FAILED_USER**, then the
      saga is compensated in the rac-rent-service
- Car service validates the request and:
    - in case of success - sets the car as rented with the rental id and sends a rent confirmation to the
      rac-rent-service (Kafka) - **RAC_RENT_CONFIRMATION**. Search service is updated with the car status.
    - in case of failure - sends a rent failure to the rac-rent-service and rac-user-service (Kafka) - *
      *RAC_RENT_FAILED_CAR**. Then the saga is compensated in the rac-rent-service and rac-user-service

When a rental is initiated, customer and car GPS agents should increase the frequency of data sending to the carLocation
service - once per 5s. Location service should update the car and user carLocation in the database and push
notifications (**RAC_LOCATION_USER_CHANGED**, **RAC_LOCATION_CAR_CHANGED**) to Kafka (events consumed by the search,
car, user and rent
service).
User is notified about the successful rent by the Websocket connection.

#### Return car saga

- User sends a return HTTP request to the API Gateway
- API Gateway sends a return request to the rac-rent-service (HTTP)
- rac-rent-service validates the request and:
    - in case of success - calculate distance traveled querying rac-carLocation-service by event (Kafka) - *
      *RAC_RETURN_REQUEST_LOCATION**
    - in case of failure - sends a return failure api gateway (HTTP)
- rac-carLocation-service validates **RAC_RETURN_REQUEST_LOCATION**. In case of success:
    - calculates the distance traveled and sends a return request to the rac-user-service (Kafka) - *
      *RAC_RETURN_REQUEST_USER**
    - in case of failure - sends a return failure to the rac-rent-service (Kafka) - **RAC_RETURN_FAILED_LOCATION**, then
      the
      saga is compensated in the rac-rent-service
- rac-user-service validates the request and:
    - in case of success - charges user for the distance traveled, sends an event to the rac-car-service (Kafka) -
      **RAC_RETURN_REQUEST_CAR**
    - in case of failure - sends a return failure to the rac-rent-service (Kafka) - **RAC_RETURN_FAILED_USER**, then the
      saga is compensated in the rac-rent-service
- rac-car-service validates the request and:
    - in case of success - sets the car as available and sends a return confirmation to the rac-rent-service (Kafka) -
      **RAC_RETURN_CONFIRMATION**. Search service is updated with the car status.
    - in case of failure - sends a return failure to the rac-rent-service and rac-user-service (Kafka) - *
      *RAC_RETURN_FAILED_CAR**.
      Then the saga is compensated in the rac-rent-service and rac-user-service (charged money
      is returned to the user).
- rac-rent-service sends a return success to the api gateway (HTTP). In case of failure, the saga is compensated in the
  rac-rent-service and error message has to be returned to the api gateway over websocket. User then has to contact the
  support.

When a rental is finished, customer and car GPS agents should decrease the frequency of data sending to the carLocation
service - once per 30s.
Location service should update the car and user carLocation in the database and push notifications (
**RAC_LOCATION_USER_CHANGED**, **RAC_LOCATION_CAR_CHANGED**) to Kafka (events consumed by the search, car, user and rent
service).
User is notified about the successful return by the Websocket connection.

### Simulation service

Simulation service let to simulate the data of the cars and users. It is used for testing purposes.
Simulation is configured through the config service. It is possible to configure the number of concurrent threads
simulating active sessions of users and administrators adding or removing cars from the pool of available cars.
Scenarios have a probability to be executed. It is possible to configure the probability of each scenario.

Possible simulation scenarios:

- User registers
- User unregisters
- Administrator adds a car
- Administrator removes a car from the pool of available cars
- User rents a car - then localizations of the user and the car are updated and then user returns the car
- User tries to rent already rented car

## Build and run

### Build

Build with Gradle wrapper from the root repository directory:

#### build project

```shell
.\gradlew.bat build
```

#### Code analysis

To run Sonar code analysis, run the following command in the root repository directory
with [sonar container](docker/sonar.yml) running locally.
Then login with admin/admin credentials, change the password to admin1 and run the analysis with the following command:

```shell
.\gradlew.bat clean compileJava sonar
```

#### Build Docker images

#### Generate java docs

### Run

To run a project, at first, 3rd party services shall be started. To do so, run the following command in
the [docker](docker) directory:

```shell
.\start_3rd_party_components.bat 
```

Then run all the Spring Boot services with the following command in the root repository directory.