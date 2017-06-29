package seckill.example.controller;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seckill.example.dto.Exposer;
import seckill.example.dto.SeckillExecution;
import seckill.example.dto.SeckillResult;
import seckill.example.enums.SeckillStateEnum;
import seckill.example.exception.RepeatKillException;
import seckill.example.exception.SeckillCloseException;
import seckill.example.exception.SeckillRequestException;
import seckill.example.service.SeckillService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wang on 17-6-21.
 */
@RestController
public class SeckillController {
    private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
    @Autowired
    public SeckillService seckillService;

    @RequestMapping(value = "/{seckillId}", method = RequestMethod.GET, produces = {
            "application/json;charset=UTF-8" })
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId, HttpServletResponse response) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            logger.info(exposer.toString());
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8" })
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5, @Param("price") long price, @CookieValue(value = "killPhone", required = false) Long phones) {
        Long phone = Long.valueOf("18408221624");
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        try {
            //悲观锁 调用秒杀，通过减库存和增加购买明细两步操作
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5,price);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillRequestException e1) {
            //拦截md5错误的异常
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.DATA_REWRITE);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e1) {
            //拦截重复秒杀的异常
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e2) {
            //拦截秒杀活动已关闭异常
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            //未知异常
            logger.error(e.getMessage());
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, execution);
        }

    }
}
