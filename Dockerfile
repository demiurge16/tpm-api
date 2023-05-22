FROM gradle:jdk17-alpine as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace

FROM openjdk:17-alpine
ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
WORKDIR /app
EXPOSE 8080
EXPOSE 5005
COPY --from=builder /builder/build/libs/tpm-api-*.jar ./app.jar
ENTRYPOINT java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 app.jar
