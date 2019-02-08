FROM mysql

WORKDIR /tmp

COPY data_test/world.sql /tmp/

COPY data_test/world.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD example