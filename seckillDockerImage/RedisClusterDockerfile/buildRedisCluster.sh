#!/bin/sh
redis-trib.rb create --replicas 1 172.17.0.2:7000 172.17.0.3:7001 172.17.0.4:7002 172.17.0.5:7003 172.17.0.6:7004 172.17.0.7:7005
