FROM gradle:jdk21-alpine AS build
WORKDIR /khufarm
LABEL authors="USER"

COPY build.gradle settings.gradle ./
RUN gradle dependencies


COPY src ./src
RUN gradle build --no-daemon -x test

FROM openjdk:21-jdk-slim
WORKDIR /khufarm

COPY --from=build /khufarm/build/libs/*.jar khufarm.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "khufarm.jar"]