## ADR: Search microservice necessity

### Context

Adding a search microservice to the system will add additional complexity to the system. We need to consider if we
really need a search microservice. We need to consider the following questions: can we achieve the same functionality by
using the database engine?
There is a need for the following search functionalities in the system:

- car/user search by ID
- car/user search by location
- car/user search by radius

### Decision

All of these functionalities can be achieved by using the database engine - MongoDB. We don't need to create a separated
search microservice with a search engine like Elasticsearch.
Lack of search engine functionalities is not a problem because we don't need to perform complex search queries of users
or cars. We only need to perform simple queries like search by ID, name or search by location.

### Consequences

- We don't need to maintain a search microservice.
- We don't need to maintain a search engine like Elasticsearch.
- We don't need to add additional complexity to the system by adding asynchronous communication between the search
  microservice and the other microservices.