docker-compose -f mongodb.yml -p rac up -d
docker-compose -f kafka.yml -p rac up -d
docker-compose -f elk.yml -p rac up -d
docker-compose -f monitoring.yml -p rac up -d
docker-compose -f sonar.yml -p rac up -d
docker-compose -f cassandra.yml -p rac up -d