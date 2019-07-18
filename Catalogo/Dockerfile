FROM maven as buildmvn
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:alpine as run 

WORKDIR /app
COPY --from=buildmvn /app/target/Catalogo-0.0.1-SNAPSHOT.jar /app//Catalogo-0.0.1-SNAPSHOT.jar

CMD java -jar Catalogo-0.0.1-SNAPSHOT.jar