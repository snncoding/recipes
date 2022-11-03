FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY recipes-0.0.1-SNAPSHOT.jar recipes.jar
ENTRYPOINT ["java", "-jar", "recipes"]