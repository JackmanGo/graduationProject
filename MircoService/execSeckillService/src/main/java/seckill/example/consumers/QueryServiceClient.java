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
    @RequestMapping(method = RequestMethod.GET, value = "/detail/{seckillId}")
    Seckill getDetails(@PathVariable("seckillId") Long seckillId);
    @RequestMapping(method = RequestMethod.GET, value = "/success/{seckilled}")
    SuccessKilled querySuccessSeckill(@PathVariable("seckillId") long seckillId,long userPhone);
}

