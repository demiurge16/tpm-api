FROM docker.elastic.co/kibana/kibana:8.5.3

ENV ELASTICSEARCH_HOSTS=http://elasticsearch:9200

COPY ./kibana.yml /usr/share/kibana/config/kibana.yml

EXPOSE 5601

ENTRYPOINT ["/usr/local/bin/kibana-docker"]