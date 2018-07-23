set -e

service mysql start

mysql < docker/mysql/sakila-schema.sql
mysql < docker/mysql/sakila-data.sql

tail -f /dev/null