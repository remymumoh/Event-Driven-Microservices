## monitoring Kafka start container by hosting it in thost network
docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092
## kafka command to monitor the cluster 
kcat -L -b localhost:19092 -L is list, -b is broker

## check running local clusters
kcat -L -b localhost:19092

## go to docker-cmpose folder and run
docker-compose -f common.yml -f kafka_cluster.yml -f services.yml up

## inspect docker logs continously
docker logs -f CONTAINER_ID

## use kafka as a consumer
kcat -C -b localhost:19092 -t twitter-topic


## Start up sequence
start Config server. Then docker-compose -f common.yml -f kafka_cluster.yml up

## change between git profiles
git config user.name "personal"
git config user.name "work"
git config user.name -- check current active profile

## encrypting service for jce
ls ~/.sdkman/candidates
cd ~/.sdkman/candidates/springboot
spring encrypt springCloud_Pwd! --key 'Demo_Pwd!2020' -- pass the password

## connect to a container shell
docker exec -it CONTAINER_ID /bin/bash

docker exec -it 55872071534d /bin/bash
55872071534d

## config server not yet up fix
run docker-compose up then  localhost:8888/config-client/twitter_to_kafka on browser and login using username: spring_cloud_user password: springcloud_Pwd!

## check if localhost:8888 is healthy
curl http://localhost:8888/actuator/health

## Elastic search to docker-compose folder and run
docker-compose -f common.yml -f elastic_cluster.yml up