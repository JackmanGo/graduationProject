FROM redis:3-alpine
MAINTAINER WangXi <wangxi950709@gamil.com>

copy twoSlave.conf /data/twoSlave.conf
RUN chown redis:redis /data/*
ENTRYPOINT ["redis-server","/data/twoSlave.conf"]
