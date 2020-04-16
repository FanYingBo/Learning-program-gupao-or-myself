package com.study.jdk5.lang.management.managementfactory;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * 注册MBean 或者 MXBean
 * 1.jconsole 命令可以查看MBean 并且能够调用MBean中的方法
 * 2.也可以自行构建客户端来访问MBean
 */
public class ManagementFactoryRegisterBeanDemo {
    public static void main(String[] args) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            TestObject testObj = new TestObject();
            ObjectName objectName = new ObjectName("test object:name=test name");
            mBeanServer.registerMBean(testObj, objectName);
            System.out.println(mBeanServer.isRegistered(objectName));
            System.out.println(mBeanServer.getMBeanCount());
            System.out.println(mBeanServer.getObjectInstance(objectName));
            JMXServiceURL jmxServiceURL = new JMXServiceURL("rmi","127.0.0.1",8888);
            JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mBeanServer);
            mBeanServer.registerMBean(jmxConnectorServer,new ObjectName("jmx.connect:jmx=connect-local"));
            jmxConnectorServer.start();
            System.out.println(jmxConnectorServer.getAddress());
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

