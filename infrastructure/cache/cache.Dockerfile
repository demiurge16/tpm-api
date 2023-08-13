FROM redis/redis-stack-server:6.2.6-v9

EXPOSE 6379

CMD redis-server --requirepass 1qaz@WSX
