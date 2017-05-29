package seckill.example.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seckill.example.dao.cache.RedisCacheDao;

/**
 * Created by wang on 17-2-18.
 */
@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheDao redisCacheDao()  {
        RedisCacheDao ds = new RedisCacheDao("127.0.0.1",6379);
        return ds;
    }
}
