From registry.saas.hand-china.com/hmap/mysql:latest

ENV MYSQL_ROOT_PASSWORD 123456

COPY setup.sh /mysql/setup.sh
COPY docker/mysql/sakila-schema.sql /mysql/sakila-schema.sql
COPY docker/mysql/sakila-data.sql /mysql/sakila-data.sql

CMD ["sh", "/mysql/setup.sh"]