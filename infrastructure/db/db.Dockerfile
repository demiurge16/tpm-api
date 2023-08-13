FROM postgres:15.3-alpine

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=1qaz@WSX

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432

CMD ["postgres"]
