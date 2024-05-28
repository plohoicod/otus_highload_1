FROM gradle:jdk17-jammy AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

RUN ls /home/gradle/src/build/libs/


FROM eclipse-temurin:17.0.11_9-jre

EXPOSE 8081

RUN mkdir /app/


COPY --from=builder /home/gradle/src/build/libs/*SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"]