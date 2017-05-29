package seckill.example.app;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan("seckill.example")
public class App extends WebMvcConfigurerAdapter{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(App.class);	// 增加拦截器

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestLog());
	}


	public static void main(String args[]) {
		SpringApplication.run(App.class, args);
	}
											

}
