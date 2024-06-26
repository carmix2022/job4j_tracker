FROM maven:3.6.3-openjdk-17

RUN mkdir job4j_tracker

WORKDIR job4j_tracker

COPY . .

RUN mvn package -Dmaven.test.skip=true

#CMD ["mvn", "liquibase:update", "-Pdocker"]
#
#CMD ["java", "-jar", "target/tracker.jar"]

ENTRYPOINT ["sh", "-c", "mvn liquibase:update -Pdocker && java -jar target/tracker.jar"]