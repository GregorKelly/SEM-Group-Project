FROM mysql

WORKDIR /tmp

COPY db/*.sql /tmp/

COPY db/world.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD example