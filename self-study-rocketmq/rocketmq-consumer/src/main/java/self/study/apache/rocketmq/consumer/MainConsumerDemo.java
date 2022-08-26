package self.study.apache.rocketmq.consumer;

import jdk.nashorn.internal.runtime.Context;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Notice: the consumer group needed add to the name server and then the consumer able consume
 *
 */
public class MainConsumerDemo {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_consumer");
        consumer.setNamesrvAddr("192.168.0.196:9876;192.168.0.187:9876");
        consumer.subscribe("localTest", "*");
        // 消费模式 默认是集群模式（负载均衡模式），还有是广播模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setPostSubscriptionWhenPull(true);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setClientIP("192.168.0.101");
        consumer.setInstanceName("consumer");
        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    for (MessageExt msg : msgs) {
                        String topic = msg.getTopic();
                        String tags = msg.getTags();
                        String msgBody = new String(msg.getBody());
                        System.out.printf("receive message success topic:%s tags:%s %s %n",topic, tags, msgBody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("start consumer success.%n");
    }
}
