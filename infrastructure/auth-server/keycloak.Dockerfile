FROM quay.io/keycloak/keycloak:22.0.1 AS builder

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_DB=postgres

WORKDIR /opt/keycloak
# use proper certificate for production
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:22.0.1
COPY --from=builder /opt/keycloak /opt/keycloak

ENV KC_PROXY=edge
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://db:5432/tpm
ENV KC_DB_SCHEMA=keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=1qaz@WSX

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=1qaz@WSX

COPY ./realm.json /opt/keycloak/data/import/realm.json

EXPOSE 8080
EXPOSE 8443

ENTRYPOINT [ \
    "/opt/keycloak/bin/kc.sh", \
    "start", \
    "--optimized", \
    "--import-realm" \
]
