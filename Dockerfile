FROM maven:3.9.9-amazoncorretto-21-debian-bookworm AS build
COPY . .
RUN mvn clean package

FROM amazoncorretto:21.0.7
COPY --from=build /target/MotivationAppBackend-0.0.1-SNAPSHOT.jar back.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","back.jar"]