## ADR: static code analysis

### Context
What tools to use for static code analysis?

### Decision
We will use [SonarQube](https://www.sonarqube.org/) for static code analysis. Sonar will be run in a docker container.
Sonar analysis should be run as part of the CI pipeline.

### Consequences
Continuous code quality checks will be in place. This will help to keep the code clean and maintainable.
