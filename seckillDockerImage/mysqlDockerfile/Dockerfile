FROM registry.cn-hangzhou.aliyuncs.com/qinyujia-test/mysql
MAINTAINER WangXi <wangxi950709@gmail.com>
ENV MYSQL_ROOT_PASSWORD=toor
ENV AUTO_RUN_DIR /docker-entrypoint-initdb.d
ENV INIT_SQL schema.sql
copy ./$INIT_SQL $AUTO_RUN_DIR/
