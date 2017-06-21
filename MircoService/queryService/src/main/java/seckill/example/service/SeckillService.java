package seckill.example.service;

import seckill.example.entity.SuccessKilled;
import seckill.example.entity.Seckill;

import java.util.List;

/**
 * Created by wang on 17-6-6.
 * 业务接口:站在使用者(程序员)的角度设计接口
 * 三个方面:1.方法定义粒度，方法定义的要非常清楚2.参数，要越简练越好
 * 3.返回类型(return 类型一定要友好/或者return异常，我们允许的异常)
 */
public interface SeckillService {
    /**
     * 查询全部的秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();
    /**
     *查询单个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     *查询秒杀结果
     */
    SuccessKilled getSuccessSeckill(long seckillId, long userPhone);
}
