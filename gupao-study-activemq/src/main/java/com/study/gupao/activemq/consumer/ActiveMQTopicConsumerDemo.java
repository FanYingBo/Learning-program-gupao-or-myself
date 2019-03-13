package com.study.gupao.activemq.consumer;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQTopicConsumerDemo {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.156:61616");
        Connection connection = null;
        try {
            connection =  connectionFactory.createConnection();
//            connection.setClientID("demo-1");
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("client-topic-test");
            MessageConsumer consumer = session.createConsumer(topic);
            // 持久化订阅
//            MessageConsumer durableSubscriber = session.createDurableSubscriber(topic,"demo-1");
            while(true){
                Message receive = consumer.receive();
                if(receive instanceof TextMessage){
                    System.out.println(((TextMessage) receive).getText());
                }else if(receive instanceof BytesMessage){
                    System.out.println(((BytesMessage)receive).readUTF());
                }else if(receive instanceof MapMessage){
                    System.out.println(((MapMessage)receive).getMapNames());
                }else if(receive instanceof StreamMessage){
                    System.out.println(((StreamMessage)receive).readString());
                }else{
                    System.out.println(((ObjectMessage)receive).getObject());
                }
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
