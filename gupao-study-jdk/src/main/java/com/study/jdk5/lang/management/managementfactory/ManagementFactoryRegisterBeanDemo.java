package com.study.jdk5.lang.management.managementfactory;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * 注册MBean 或者 MXBean
 * 1.jconsole 命令可以查看MBean 并且能够调用MBean中的方法
 * 2.也可以自行构建客户端来访问MBean
 */
public class ManagementFactoryRegisterBeanDemo {

    private static final String objectNameStr = "test object:name=test name";
    private static final int bindPort = 8888;

    public static void main(String[] args) {
//        randomAddressMBeanServer();
        fixedAddressMBeanServer();
    }

    private static void fixedAddressMBeanServer(){
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        // Construct the ObjectName for the Hello MBean we will register
        ObjectName mbeanName = null;
        try {
            LocateRegistry.createRegistry(bindPort);
            mbeanName = new ObjectName(objectNameStr);
            // Create the MBean
            TestObject testObj = new TestObject();
            // Register the Hello World MBean
            mbs.registerMBean(testObj, mbeanName);
            String url = "service:jmx:rmi://localhost:1010/jndi/rmi://localhost:" + bindPort + "/jmxrmi";
            JMXServiceURL jmxUrl = new JMXServiceURL(url);
            JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxUrl, null, mbs);
            jmxConnServer.start();
            System.out.println(jmxConnServer.getAddress());
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        }


    }
    /**
     * 随机地址的MBeanServer
     */
    private static void randomAddressMBeanServer(){
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            TestObject testObj = new TestObject();
            ObjectName objectName = new ObjectName(objectNameStr);
            mBeanServer.registerMBean(testObj, objectName);
            System.out.println(mBeanServer.isRegistered(objectName));
            System.out.println(mBeanServer.getMBeanCount());
            System.out.println(mBeanServer.getObjectInstance(objectName));
            JMXServiceURL jmxServiceURL = new JMXServiceURL("rmi","127.0.0.1",bindPort);
            JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mBeanServer);
//            mBeanServer.registerMBean(jmxConnectorServer,new ObjectName("jmx.connect:jmx=connect-local"));
            jmxConnectorServer.start();
            System.out.println(jmxConnectorServer.getAddress()); // 这时的地址是随机的
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

