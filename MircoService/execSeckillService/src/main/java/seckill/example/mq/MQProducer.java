package seckill.example.mq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wang on 17-6-29.
 */
@Component
public class MQProducer {
    private static final Logger logger = LoggerFactory.getLogger(MQProducer.class);
    /**
     * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例
     * 注意：ProducerGroupName需要由应用来保证唯一
     * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
     * 因为服务器会回查这个Group下的任意一个Producer
     */
    private final String GROUP_NAME = "transaction-balance";
    private final String NAMESRV_ADDR = "127.0.0.1:9876;";
    private  TransactionMQProducer producer = new TransactionMQProducer(GROUP_NAME);
    private  String[] tags = new String[]{"prepare", "deduct"};

    public  MQProducer(){
        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
    public void sendPrepareMessage() throws Exception{
        byte[] messageBody ="prepareTransactional".getBytes();
        Message msg = new Message("pay",tags[0],"prepareOrder",messageBody);
        SendResult sendResult = producer.send(msg);
    }
    public void sendMessage(long userPhone,long goodsId,long price) throws Exception{
        byte[] messageBody = String.valueOf(userPhone+";"+goodsId+";"+price).getBytes();
        Message msg = new Message("pay",tags[1],"deductOrder",messageBody);
        SendResult sendResult = producer.send(msg);
    }
    public void shutdown(){
        producer.shutdown();
    }
}
