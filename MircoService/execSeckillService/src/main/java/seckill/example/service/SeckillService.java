package seckill.example.service;

import org.springframework.transaction.annotation.Transactional;
import seckill.example.dto.Exposer;
import seckill.example.dto.SeckillExecution;
import seckill.example.exception.RepeatKillException;
import seckill.example.exception.SeckillCloseException;
import seckill.example.exception.SeckillException;

/**
 * Created by wang on 17-6-21.
 */
public interface SeckillService {
    /**
     * 在秒杀开启时输出秒杀接口的地址，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    @Transactional
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;

}
