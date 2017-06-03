package seckill.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wang on 17-6-3.
 */
@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    @Autowired
    QueryServiceClient feignClient;

    /*
    @Autowired
    RestTemplate restTemplate;
    @RequestMapping(value = "/time/now/ribbon", method = RequestMethod.GET)
    public String times() {
        //return restTemplate.getForEntity("http://query-service/seckill/time/now", String.class).getBody();
        return restTemplate.getForEntity("http://127.0.0.1:8001/seckill/time/now", String.class).getBody();

    }
    */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public String time() {
        return feignClient.times();
    }
}
