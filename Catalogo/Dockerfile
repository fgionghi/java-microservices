FROM openjdk:11

WORKDIR /app
ADD . /app 

RUN ./mvnw package -Dmaven.test.skip=true 
CMD ./mvnw spring-boot:run
