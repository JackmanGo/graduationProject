package seckill.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import seckill.example.consumers.QueryServiceClient;
import seckill.example.dao.SuccessKilledDao;
import seckill.example.dto.Exposer;
import seckill.example.dto.SeckillExecution;
import seckill.example.entity.Seckill;
import seckill.example.entity.SuccessKilled;
import seckill.example.enums.SeckillStateEnum;
import seckill.example.exception.RepeatKillException;
import seckill.example.exception.SeckillCloseException;
import seckill.example.exception.SeckillException;
import seckill.example.service.SeckillService;

import java.util.Date;

/**
 * Created by wang on 17-6-21.
 */
public class SeckillServiceImpl implements SeckillService{
    @Autowired
    public SuccessKilledDao successKilledDao;
    @Autowired
    QueryServiceClient queryServiceClient;

    //加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
    private final String salt="asdasdq2wadiio124rhalkd'l.";
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //查询query服务来获取
        Seckill seckill = queryServiceClient.getDetails(seckillId);
        if (seckill==null) //说明查不到这个秒杀产品的记录
        {
            return new Exposer(seckillId);
        }else {
            //判断秒杀是否开启
            Date startTime=seckill.getStartTime();
            Date endTime=seckill.getEndTime();
            //系统当前时间
            Date nowTime=new Date();
            if (startTime.getTime()>nowTime.getTime() || endTime.getTime()<nowTime.getTime())
            {
                return new Exposer(seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
            }
            //秒杀开启，返回秒杀商品的id、用给接口加密的md5
            String md5=getMD5(seckillId);
            return new Exposer(md5,seckillId);
        }
    }
    //秒杀是否成功，成功:减库存，增加明细；失败:抛出异常，事务回滚
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5==null||!md5.equals(getMD5(seckillId)))
        {
            throw new SeckillException("seckill data rewrite");//md5不一致秒杀数据被重写了
        }
        //执行秒杀逻辑:减库存+增加购买明细
        Date nowTime=new Date();
        try {
            //减库存
            int updateCount = successKilledDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //没有更新库存记录，说明秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //否则更新了库存，秒杀成功,增加明细
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //每个用户限购一件，看是否该明细被重复插入，即用户是否重复秒杀
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息
                    //查询query服务
                    SuccessKilled successKilled = queryServiceClient.querySuccessSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        }catch (SeckillCloseException e1)
        {
            throw e1;
        }catch (RepeatKillException e2)
        {
            throw e2;
        }catch (Exception e)
        {
            //所以编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error :"+e.getMessage());
        }

    }
    private String getMD5(long seckillId)
    {
        String base=seckillId+"/"+salt;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
