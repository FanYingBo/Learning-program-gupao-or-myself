package com.study.jdk5.lang.management.managementfactory;


import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Scanner;

/**
 * 远程调用MBean
 */
public class MBeanClientDemo {

    public static void main(String[] args) throws Exception {
        // url 为MBean服务启动注册的url
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi://localhost:1010/jndi/rmi://localhost:8888/jmxrmi");
        JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
        ObjectName objectName = new ObjectName("test object:name=test name");
        Scanner scanner = new Scanner(System.in);
        String name = null;
        while((name = scanner.nextLine()) != null){
            // 第一种方式
//            mBeanServerConnection.invoke(objectName,"printHello",new String[]{name},new String[]{"java.lang.String"});
            // 第二种方式
            TestObjectMXBean testObjectMXBean = JMX.newMBeanProxy(mBeanServerConnection, objectName, TestObjectMXBean.class);
            testObjectMXBean.printHello(name);
        }

    }
}
