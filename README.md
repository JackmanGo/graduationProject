# seckill demo for graduationProject
#### 文件目录结构说明
seckillFront为Angular2项目，需要NodeJs环境，依赖3000端口
seckillBackend为秒杀系统的单机工程，依赖8080端口
seckillDockerImage为容器化时构建Docker的Dockerfile
MircoService为微服务器化后的每个项目
#### 微服务后的端口分配
服务注册中心MircoService/serviceKernal,监听端口8000
查询服务MircoService/queryService,监听端口8001。service-id:query-service
服务消费者MircoService/serviceConsumers,监听端口8002。service-id:service-consumer
服务网关zuul，监听端口8003。service-id:api
