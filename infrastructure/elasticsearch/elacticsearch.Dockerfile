FROM docker.elastic.co/elasticsearch/elasticsearch:8.5.3 AS setup

# run elasticsearch and generate the certificates and an enrollment token
# for the kibana instance

RUN elasticsearch-certutil ca --silent --pem --out /usr/share/elasticsearch/config/certs/ca