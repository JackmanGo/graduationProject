package seckill.example.mq;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seckill.example.service.BalanceService;

import java.util.List;

/**
 * Created by wang on 17-6-29.
 */
@Component
public class MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);
    private final String GROUP_NAME = "transaction-balance";
    private final String NAMESRV_ADDR = "127.0.0.1:9876;";
    private  String[] tags = new String[]{"prepare", "deduct"};
    private DefaultMQPushConsumer consumer;
    @Autowired
    BalanceService balanceService;
    public MQConsumer() {
        try {
            this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
            this.consumer.setNamesrvAddr(NAMESRV_ADDR);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe("pay", "*");
            this.consumer.registerMessageListener(new Listener());
            this.consumer.start();
            logger.info("consumer start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end) throws Exception {
        long current = System.currentTimeMillis();
        return this.consumer.queryMessage(topic, key, maxNum, begin, end);
    }
    class Listener implements MessageListenerConcurrently {
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MessageExt msg = msgs.get(0);
            try {
                String topic = msg.getTopic();
                logger.debug("topic:"+topic);

                //Message userPhone;goodsId;price
                String[] deductBalanceInfo = new String(msg.getBody(), "utf-8").split(";");
                Long userPhone = Long.valueOf(deductBalanceInfo[0]);
                Long goodsId = Long.valueOf(deductBalanceInfo[1]);
                Long price = Long.valueOf(deductBalanceInfo[2]);
                //业务逻辑处理
                balanceService.deductBalances(userPhone,goodsId,price);

            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
