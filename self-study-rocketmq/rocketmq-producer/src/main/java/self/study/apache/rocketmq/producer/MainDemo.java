package self.study.apache.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainDemo {

    public static void main(String[] args) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup("testGroup");
        defaultMQProducer.setNamesrvAddr("192.168.0.196:9876;192.168.0.187:9876");
        defaultMQProducer.setInstanceName("producer");
        String serverName = "";
        if(Objects.nonNull(args) && args.length >= 1){
            serverName = args[0];
        }
        try {
            defaultMQProducer.start();
            for(int i = 0;;i ++) {
                Message message = new Message();
                message.setTopic("localTest");
                message.setTags("TagA");
                message.setBody((serverName + ": local test send the " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 同步发送
                defaultMQProducer.send(message);
                // 异步
//                defaultMQProducer.send(message, new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//
//                    }
//
//                    @Override
//                    public void onException(Throwable e) {
//
//                    }
//                });
                System.out.printf("send message: %s%n", new String(message.getBody()));
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
