FROM redis:3-alpine
MAINTAINER WangXi <wangxi950709@gamil.com>

copy three.conf /data/three.conf
RUN chown redis:redis /data/*
ENTRYPOINT ["redis-server","/data/three.conf"]
