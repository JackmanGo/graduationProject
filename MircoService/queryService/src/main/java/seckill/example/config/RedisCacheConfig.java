package seckill.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * Created by wang on 17-6-9.
 */
@Configuration
public class RedisCacheConfig {
/*
  spring.redis.host=127.0.0.1
  spring.redis.port=6379
  spring.redis.timeout=0
  #最大可用连接数（默认为8，负数表示无限）
  spring.redis.pool.max-active=8
  #最大空闲连接数（默认为8，负数表示无限）
  spring.redis.pool.max-idle=8
  #最小空闲连接数（默认为0，该值只有为正数才有作用）
  spring.redis.pool.min-idle=0
  #从连接池中获取连接最大等待时间（默认为-1，单位为毫秒，负数表示无限）
  spring.redis.pool.max-wait=-1
  */
/*
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setUsePool(true);
        redisConnectionFactory.setHostName("127.0.0.1");
        redisConnectionFactory.setPort(3306);
        return redisConnectionFactory;
    }
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jf) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jf);
        return redisTemplate;
    }
    */
    @Bean
    public Jedis getJedis(){
       Jedis jedis = new Jedis("127.0.0.1",3306);
       return  jedis;
    }

}
