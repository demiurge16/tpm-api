FROM nginx:1.23.1-alpine
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY ./sites-enabled /etc/nginx/sites-enabled

EXPOSE 80
EXPOSE 443
