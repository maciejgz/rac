set COMPOSE_PROJECT_NAME=rac
docker-compose -f mongodb.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f kafka.yml -p %COMPOSE_PROJECT_NAME% up -d
docker-compose -f elasticsearch.yml -p %COMPOSE_PROJECT_NAME% up -d