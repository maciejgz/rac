set COMPOSE_PROJECT_NAME=rac
docker-compose -f mongodb.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f kafka.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f elk.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f monitoring.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f sonar.yml -p %COMPOSE_PROJECT_NAME% up -d