FROM redis:3-alpine
MAINTAINER WangXi <wangxi950709@gamil.com>

copy oneSlave.conf /data/oneSlave.conf
RUN chown redis:redis /data/*
ENTRYPOINT ["redis-server","/data/oneSlave.conf"]
