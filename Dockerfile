FROM eclipse-temurin:21-jdk-alpine AS project-build
RUN apk add --no-cache maven

WORKDIR /usr/project
COPY . /usr/project

RUN mvn clean package -Pproduction

FROM eclipse-temurin:21-jre

COPY --from=project-build /usr/project/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=America/New_York", "-jar", "/app.jar"]
