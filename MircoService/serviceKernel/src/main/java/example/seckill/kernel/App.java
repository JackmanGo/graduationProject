package example.seckill.kernel;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by wang on 17-5-30.
 */
@EnableEurekaServer
@SpringBootApplication
public class App {
     public static void main(String[] args) {
            new SpringApplicationBuilder(App.class).web(true).run(args);
        }
}
