## ADR: Type of the saga pattern to use

### Context

As a part of the RAC system we need to implement a saga pattern to handle the business transactions. We need to decide
which type of saga pattern to use. We need to consider the following questions:

- should we use choreography or orchestration?
- should we use asynchronous or synchronous communication between the saga participants?

### Decision

The biggest part of this project is the learning process. We want to learn how to implement a saga pattern. We want to
try to use asynchronous communication between the saga participants and choreography with all the compensating actions
in the process. Due to that requirement we will use the **choreography-based saga pattern** with **asynchronous**
communication and eventual consistency without any additional framework.
As an evolution of the system we can consider using some framework like **Eventuate Tram**
[TRAM](https://eventuate.io/abouteventuatetram.html) to implement the saga.

### Consequences

- We need to implement the saga pattern by ourselves.
- We need to implement the compensating actions by ourselves.

