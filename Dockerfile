FROM openjdk:latest
COPY ./target/seMethodsProject-1.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethodsProject-1.2-jar-with-dependencies.jar"]

