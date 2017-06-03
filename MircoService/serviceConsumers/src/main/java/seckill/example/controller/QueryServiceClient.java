package seckill.example.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wang on 17-6-3.
 */
@FeignClient("query-service")
public interface QueryServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/time/now")
    String times();
}
