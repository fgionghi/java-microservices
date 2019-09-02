FROM maven:3-jdk-12 AS mvn
WORKDIR /tmp
COPY ./pom.xml /tmp/pom.xml
COPY ./src /tmp/src
WORKDIR /tmp/
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:12-alpine
ENV FOLDER=/tmp/target
ENV APP=Acquisti-0.0.1-SNAPSHOT.jar
ARG USER=purchase
ARG USER_ID=3004
ARG USER_GROUP=purchase
ARG USER_GROUP_ID=3004
ARG USER_HOME=/home/${USER}

RUN  addgroup -g ${USER_GROUP_ID} ${USER_GROUP}; \
     adduser -u ${USER_ID} -D -g '' -h ${USER_HOME} -G ${USER_GROUP} ${USER} ;

WORKDIR  /home/${USER}/app
RUN chown ${USER}:${USER_GROUP} /home/${USER}/app
COPY --from=mvn --chown=purchase:purchase ${FOLDER}/${APP} /home/${USER}/app/purchase.jar

USER purchase
CMD ["java", "-XX:MaxRAMPercentage=50", "-jar", "purchase.jar"]
