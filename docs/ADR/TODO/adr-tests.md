## ADR: What type of tests to use and what testing framework to use

### Context

We need to decide what type of tests to use and what testing framework to use. We need to consider the following
questions:

- what type of tests to write?
    - Unit Testing: These are the most granular type of tests where individual functions or methods within a service are
      tested. The goal is to ensure that each function behaves as expected in isolation.
    - Integration Testing: These tests are designed to verify the interaction between different services or components.
      They ensure that the system works correctly when services are integrated together.
    - Functional Testing: These tests are used to verify the system functionality without considering the internal
      parts. They test the system against functional requirements and specifications.
    - End-to-End Testing: These tests validate the entire process flow from start to end. They ensure that the system
      behaves as expected in a production-like scenario.
    - Contract Testing: These tests ensure that the communication between different services is working as expected.
      They validate the requests and responses between services against a contract.
    - Performance Testing: These tests are used to check the system performance under a particular load. They help
      identify any bottlenecks or performance issues.
    - Resilience Testing: These tests are used to check the system's ability to handle and recover from failures. They
      often involve introducing failures and observing the system's response.
    - Security Testing: These tests are used to identify any vulnerabilities or security risks in the system.
- what testing framework to use?
- Should we use Test Containers for integration tests? [Test Containers](https://www.testcontainers.org/)
- Should we test architecture using ArchUnit?

### Decision

### Consequences
