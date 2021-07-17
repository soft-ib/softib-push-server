FROM openjdk:8-jdk-alpine
EXPOSE 8785
COPY target/softib-push-server-0.0.1-SNAPSHOT.jar softib-push-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "sh", "-c", "java -jar /softib-push-server-0.0.1-SNAPSHOT.jar" ]