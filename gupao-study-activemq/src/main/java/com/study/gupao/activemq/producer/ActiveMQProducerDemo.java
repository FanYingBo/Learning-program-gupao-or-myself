package com.study.gupao.activemq.producer;


import jdk.nashorn.internal.codegen.types.BooleanType;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *                ConnectionFactory
 *                        |  createConnection
 *                    connection
 *                        |createSession
 * producer<---create---session ---create--->consumer
 *
 * 生产者/消费者模式
 */
public class ActiveMQProducerDemo {

    public static void main(String[] args) {
        //jms.producerWindowSize
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.156:61616?jms.producerWindowSize=10240");
//        ((ActiveMQConnectionFactory) connectionFactory).setUseAsyncSend(Boolean.TRUE);//异步发送
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
//            ((ActiveMQConnection)connection).setUseAsyncSend(Boolean.TRUE);// 异步发送
            //
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("myQueue");
            MessageProducer producer = session.createProducer(destination);

//            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage textMessage = session.createTextMessage("hello word!");
//            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            int count = 0;
            while(count < 50){
                producer.send(textMessage);
                session.commit();
                Thread.sleep(3000);
                count++;
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
