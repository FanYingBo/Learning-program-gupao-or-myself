package com.study.gupao.activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQTopicProducerDemo {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.156:61616");
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("client-topic-test");
            MessageProducer producer = session.createProducer(topic);
            TextMessage textMessage = session.createTextMessage("notice: someone have lost key chain in the office ");
            int count = 0;
            while(count < 50){
                count++;
                producer.send(textMessage);
                session.commit();
                Thread.sleep(3000);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
