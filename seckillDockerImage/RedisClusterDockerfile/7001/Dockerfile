FROM redis:3-alpine
MAINTAINER WangXi <wangxi950709@gamil.com>

copy two.conf /data/two.conf
RUN chown redis:redis /data/*
ENTRYPOINT ["redis-server","/data/two.conf"]
