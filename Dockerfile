FROM openjdk:8
ADD target/mychat.jar mychat.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "mychat.jar"]
