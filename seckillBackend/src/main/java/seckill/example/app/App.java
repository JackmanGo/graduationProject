package seckill.example.app;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("seckill.example")
public class App extends WebMvcConfigurerAdapter{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(App.class);	// 增加拦截器

	@Autowired
	public PlatformTransactionManager platformTransactionManager;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestLog());
	}

	/*
	//事务管理器
	@Bean(name="jdbcTransaction")
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		System.out.println("....>>>>"+dataSource);
		return new DataSourceTransactionManager(dataSource);

	}
	*/

	public static void main(String args[]) {
		SpringApplication.run(App.class, args);
	}
											

}
