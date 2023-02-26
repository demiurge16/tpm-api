FROM quay.io/keycloak/keycloak:20.0.3 AS builder

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_PROXY=passthrough
ENV KC_HTTP_PORT=80
ENV KC_HTTPS_PORT=443
ENV KC_HOSTNAME_STRICT=false

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://db:5432/tpm
ENV KC_DB_SCHEMA=keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=1qaz@WSX

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=1qaz@WSX

COPY ./realm.json /tmp/realm.json

EXPOSE 8081

ENTRYPOINT [ \
    "/opt/keycloak/bin/kc.sh", \
    "start-dev", \
    "-Dkeycloak.migration.action=import", \
    "-Dkeycloak.migration.provider=singleFile", \
    "-Dkeycloak.migration.file=/tmp/realm.json" \
]
