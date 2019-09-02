docker build -t catalogo .
docker network create altaformazione
docker rm -f $(docker ps -aq) //lista tutti gli id di container ed elimanali

#!/bin/bash
export DOCKER_BUILDKIT=1
docker build -f Dockerfile-experimental -t registry .
docker run --network altaformazione --name zipkin -p 9411:9411 -d openzipkin/zipkin
docker run --network altaformazione --name mongo -d mongo:latest

#In each src/main/app.properties must be these variables
docker run --memory 256m --network altaformazione -p 8761:8761 --name registry -d registry
docker run --network altaformazione --memory 256m --name catalog -p 18081:8080 -e "MONGO_URL=mongo" -e "ZIPKIN_URL=http://zipkin:9411" -e "EUREKA_URL=http://registry:8761/eureka" -d catalogo
docker run --network altaformazione --memory 256m --name purchase -p 28081:8080 -e "MONGO_URL=mongo" -e "ZIPKIN_URL=http://zipkin:9411" -e "EUREKA_URL=http://registry:8761/eureka" -d acquisti
docker run --network altaformazione --memory 256m --name zuul-proxy -p 5000:5000 -e "ZIPKIN_URL=http://zipkin:9411" -e "EUREKA_URL=http://registry:8761/eureka" -d zuul-proxy


declare -i i;i=0; while true; do i+=1; echo $i; curl -X GET -H "Content-Type: application/json" 'http://catalogo.local/api/products'; echo ""; sleep 2; done;
