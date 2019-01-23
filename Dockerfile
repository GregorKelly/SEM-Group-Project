FROM openjdk:latest
COPY ./target/seMethodsProject-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethodsProject-1.0-SNAPSHOT-jar-with-dependencies.jar"]

