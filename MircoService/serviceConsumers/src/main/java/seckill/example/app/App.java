package seckill.example.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import seckill.example.controller.ConsumerController;

/**
 * Created by wang on 17-6-3.
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class App {
    /*
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    */

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).web(true).run(args);
    }
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

    @FeignClient(value = "query-service")
    public interface QueryServiceClient {
        @RequestMapping(method = RequestMethod.GET, value = "/seckill/time/now")
        String times();
    }

}
