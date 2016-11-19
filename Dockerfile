FROM openjdk:8
COPY /target/emilena-api-1.0-SNAPSHOT.jar /usr/
COPY /src/test/resources/application.yml /usr/

CMD ["java", "-jar", "/usr/emilena-api-1.0-SNAPSHOT.jar", "server", "/usr/application.yml"]

EXPOSE 9090
EXPOSE 9091