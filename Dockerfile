FROM gradle:jdk17-alpine as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace

FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /builder/build/libs/tpm-api-*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
