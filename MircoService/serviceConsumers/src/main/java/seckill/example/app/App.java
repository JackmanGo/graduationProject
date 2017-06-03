package seckill.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by wang on 17-6-3.
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("seckill.example")
public class App {
    /*
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
