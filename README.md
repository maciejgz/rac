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

#### Non-functional
- Gradle as a build tool
- Event sourcing - Kafka as a message broker
- Internal communication between services by events with sagas
- Requests to the services through the API Gateway
- API Gateway receives a success message right after submission of rent request - then asynchronous commit is sent after succesful saga execution

### Components
- rac-api-gateway - 8080 - API gateway
- rac-user-service - 8081 - User management service
- rac-rent-service - 8082 - Renting service
- rac-search-service - 8083 - search functionality
- rac-car-service - 8084 - car fleet management
- rac-location-service - 8085 - gathering location data of the cars
- rac-discovery-service - http://localhost:8761/ - Spring Eureka server
- rac-configuration-service - 8888 - Spring Cloud Config
- MongoDB - 27017 - centralized database - each microservice will have a different schema
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
- Sonar

| component                 | port  | comment                                                                  |        group        |
|---------------------------|:-----:|:-------------------------------------------------------------------------|:-------------------:|
| rac-api-gateway           | 8080  | HTTP API Gateway                                                         |         APP         |
| rac-user-service          | 8081  | user management service                                                  |         APP         |
| rac-rent-service          | 8082  | renting service                                                          |         APP         |
| rac-search-service        | 8083  | search service                                                           |         APP         |
| rac-car-service           | 8084  | car fleet management                                                     |         APP         |
| rac-location-service      | 8085  | Cars location service                                                    |         APP         |
| rac-discovery-service     | 8761  | Spring Cloud Eureka - discovery service                                  |         APP         |
| rac-configuration-service | 8888  | Spring Cloud Config - centralized config service                         |         APP         |
| rac-mongodb               | 27017 | MongoDB - database                                                       |         DB          |
| rac-kafka                 | 9092  | Kafka - Message broker                                                   |   MESSAGE BROKER    |
| rac-zookeeper             | 2181  | Zookeeper - Kafka nodes synchronization service                          |   MESSAGE BROKER    |
| rac-elasticsearch         | 9200  | Elasticsearch - for search...                                            |       SEARCH        |
| rac-kibana                | 5601  | Kibana - Web UI for Elasticsearch                                        |       SEARCH        |
| rac-zipkin                | 9411  | Zipkin - distributed tracing collector                                   | DISTRIBUTED TRACING |
| rac-prometheus            | 9090  | Prometheus - time series database receives data from Micrometer agents   |     MONITORING      |
| rac-grafana               | 9091  | Grafana - Web UI for the containers monitoring data stored in Prometheus |     MONITORING      |
| rac-sonar                 | 9000  | Sonar - code analysis                                                    |    CODE ANALYSIS    |

## Build and run

### Build
Build with Gradle wrapper from the root repository directory:

#### build project
```shell
.\gradlew.bat build
```

#### Code analysis
To run Sonar code analysis, run the following command in the root repository directory with [sonar container](docker/sonar.yml) running locally.
Then login with admin/admin credentials, change the password to admin1 and run the analysis with the following command:

```shell
.\gradlew.bat clean compileJava sonar
```

#### Build Docker images

#### Generate java docs

### Run
To run a project, at first, 3rd party services shall be started. To do so, run the following command in the [docker](docker) directory:

```shell
.\start_3rd_party_components.bat 
```

Then run all the Spring Boot services with the following command in the root repository directory.
