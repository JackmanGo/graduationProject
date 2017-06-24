FROM redis:3-alpine
MAINTAINER WangXi <wangxi950709@gamil.com>

copy one.conf /data/one.conf
RUN chown redis:redis /data/*
ENTRYPOINT ["redis-server","/data/one.conf"]
