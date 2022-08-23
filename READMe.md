## monitoring Kafka start container by hosting it in thost network
docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092
## kafka command to monitor the cluster 
kcat -L -b localhost:19092 -L is list, -b is broker

## run local clusters
kcat -L -b localhost:19092

## go to docker-cmpose folder and run
docker-compose -f common.yml -f kafka_cluster.yml -f services.yml up

## inspect docker logs continously
docker logs -f CONTAINER_ID

## use kafka as a consumer
kcat -C -b localhost:19092 -t twitter-topic
