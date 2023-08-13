FROM quay.io/minio/minio:RELEASE.2023-03-24T21-41-23Z

ENV MINIO_ROOT_USER=admin
ENV MINIO_ROOT_PASSWORD=1qaz@WSX

EXPOSE 9000 9001

RUN mkdir -p /data

COPY entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
