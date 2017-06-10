package seckill.example.cache;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import seckill.example.entity.Seckill;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wang on 17-6-9.
 */
@Component
public class RedisCache implements Cache {
    private Jedis redisTemplate = getJedis();
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private  String id; // cache instance id
    private static final int EXPIRE_TIME_IN_MINUTES = 60*60; // redis过期时间
    private static RuntimeSchema<CacheEntry> schema = RuntimeSchema.createFrom(CacheEntry.class);

    public RedisCache(){

    }
    public RedisCache(String id){
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }
    /*
     *mybatis缓存操作对象的标识符。一个mapper对应一个mybatis的缓存操作对象。
     */
    @Override
    public String getId() {
        return id;
    }
    /*
     *将查询结果塞入缓存
     */
    @Override
    public void putObject(Object key, Object value) {
        logger.info("will cache data："+value);
        ArrayList<Seckill> valueList = (ArrayList<Seckill>)value;
        CacheEntry cacheEntry = new CacheEntry(valueList);
        logger.info("conver to list:"+valueList);
        byte[] bytesValue = ProtostuffIOUtil.toByteArray(cacheEntry,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        /*
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
        */
        byte[] bytesKey = key.toString().getBytes();
        redisTemplate.setex(bytesKey,EXPIRE_TIME_IN_MINUTES,bytesValue);
        logger.debug("Put query result to redis");
    }
    /*
     * 从缓存中获取被缓存的查询结果。
     */
    @Override
    public Object getObject(Object key) {
        byte[] bytesKey = key.toString().getBytes();
        byte[] bytesValue = redisTemplate.get(bytesKey);
        logger.info("get cache key:"+bytesKey);
        logger.info("get cache value:"+bytesValue);
        /*
        ValueOperations opsForValue = redisTemplate.opsForValue();
        logger.debug("Get cached query result from redis");
        return opsForValue.get(key);
        */
        CacheEntry value = schema.newMessage();
        if(bytesValue!=null) {
            ProtostuffIOUtil.mergeFrom(bytesValue, value, schema);
            //logger.info("序列化后:"+value);
            return value.getSeckills();
        }
        return null;

    }
    /*
     *从缓存中删除对应的key、value。一般回滚触发
     */
    @Override
    public Object removeObject(Object key) {
        redisTemplate.del(key.toString());
        logger.debug("Remove cached query result from redis");
        return null;
    }
    /*
     *从缓存中删除对应的key、value
     */
    @Override
    public void clear() {
        redisTemplate.flushDB();
        logger.debug("Clear all the cached query result from redis");
    }
    /*
     *缓存的数量
     */
    @Override
    public int getSize() {
        return 0;
    }
    /*
     *实现原子性的缓存操作使用的锁
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public Jedis getJedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        return  jedis;
    }

}
class CacheEntry{
    private List<Seckill> seckills;
    public CacheEntry(List<Seckill> seckills){
        this.seckills=seckills;
    }
    public List<Seckill> getSeckills() {
        return seckills;
    }

    public void setSeckills(List<Seckill> seckills) {
        this.seckills = seckills;
    }
}
