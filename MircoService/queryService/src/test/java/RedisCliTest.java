import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wang on 17-6-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisCliTest {
    /*
    @Value("${redis-master1}")
    private String redisServerMasterOneIp;
    @Value("${redis-master1-port}")
    private int redisServerMasterPortOne;
    @Value("${redis-master2}")
    private String redisServerMasterTwoIp;
    @Value("${redis-master2-port}")
    private int redisServerMasterPortTwo;
    @Value("${redis-master3}")
    private String redisServerMasterThreeIp;
    @Value("${redis-master3-port}")
    private int redisServerMasterPortThree;
    */
    @Test
    public void testRedisCluster(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        /*
        Jedis jedis = new Jedis("127.0.0.1",7006);
        String fooValue = jedis.get("foo");
        */
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7000));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7001));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7002));
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        String fooValue = jc.get("foo");
        Assert.assertEquals(fooValue,"bar");
    }
}
