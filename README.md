# seckill demo for graduationProject
#### 文件目录结构说明
seckillFront为Angular2项目，需要NodeJs环境，依赖3000端口
seckillBackend为秒杀系统的单机工程，依赖8080端口
seckillDockerImage为容器化时构建Docker的Dockerfile
MircoService为微服务器化后的每个项目
#### 微服务后的端口分配
服务注册中心MircoService/serviceKernal,监听端口8000
查询服务MircoService/queryService,监听端口8001。service-id:query-service
执行服务MircoService/execSeckillService,监听端口8002。service-id:exec-service
扣用户余额服务MircoService/serviceDeductBalances,监听端口8003。service-id:deductBalance-service
服务网关zuul，监听端口8004。service-id:api
#### RestAPI接口划分
完全遵循RestAPI的设计规范上定义出两个服务的接口后，在添加了Zuul之后进行了修改
** Zuul网关接口,命名遵循 /api/版本号/: **
查询服务query-service：/api/v0.5/inquiry/
执行操作服务exec-service: /api/v0.5/execution/
** query-service 查询服务接口命名: **
查询全部商品 GET  :/commodities
查询某个商品的详情 GET :/commodity/{commodityId}
查询某个用户对于某件商品秒杀成功的详情: GET :/seckill/{commodityId}
** exec-service查询接口命令：**
查询秒杀接口 GET :/{commodityId}
执行秒杀操作 POST :/{seckillId}/{md5}


