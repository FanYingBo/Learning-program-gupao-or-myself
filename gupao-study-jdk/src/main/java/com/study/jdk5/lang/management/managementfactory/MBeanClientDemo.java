package com.study.jdk5.lang.management.managementfactory;


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
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi://127.0.0.1:8888/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc4AAtVbmljYXN0UmVmMgAADTE2OS4yNTQuMTguNDAAACK4V6sx1+47v4Zit0xkAAABcYIlxUmAAQB4");
        JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
        ObjectName objectName = new ObjectName("test object:name=test name");
        Scanner scanner = new Scanner(System.in);
        String name = null;
        while((name = scanner.nextLine()) != null){
            mBeanServerConnection.invoke(objectName,"printHello",new String[]{name},new String[]{"java.lang.String"});
        }

    }
}
