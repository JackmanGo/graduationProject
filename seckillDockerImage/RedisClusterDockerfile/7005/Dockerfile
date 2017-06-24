FROM redis:3-alpine
MAINTAINER WangXi <wangxi950709@gamil.com>

copy threeSlave.conf /data/threeSlave.conf
RUN chown redis:redis /data/*
ENTRYPOINT ["redis-server","/data/threeSlave.conf"]
