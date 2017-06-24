package seckill.example.consumers;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seckill.example.entity.Seckill;
import seckill.example.entity.SuccessKilled;

/**
 * Created by wang on 17-6-22.
 */
@FeignClient("query-service")
public interface QueryServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/commodity/{seckillId}")
    Seckill getDetails(@PathVariable("seckillId") Long seckillId);
    //long userPhone必须是参数第一位 负责会报错Body parameters cannot be used with form parameters.
    @RequestMapping(method = RequestMethod.GET, value = "/seckill/{seckilled}")
    SuccessKilled querySuccessSeckill(long userPhone,@PathVariable("seckillId") long seckillId);
}

