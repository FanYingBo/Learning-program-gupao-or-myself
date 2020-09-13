package com.study.selfs.jvm.mxbean.util;

import com.sun.management.HotSpotDiagnosticMXBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.Set;

/**
 * 常用于Java 进程的监控
 * 通过本地Windows jconsole 命令查看可以看到所有的MXBean
 */
public class JVMUtils {

    public static final String Hot_Spot_Diagnostic_bean_name = "com.sun.management:type=HotSpotDiagnostic";

    /**
     *  HotSpot 虚拟机
     * @return
     */
    public static HotSpotDiagnosticMXBean getHotSpotMBean(){
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<HotSpotDiagnosticMXBean>) () -> doGetHotSpotMBean());
        } catch (PrivilegedActionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HotSpotDiagnosticMXBean doGetHotSpotMBean() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = platformMBeanServer.queryNames(ObjectName.getInstance(Hot_Spot_Diagnostic_bean_name), null);
        Iterator<ObjectName> iterator = objectNames.iterator();
        if(iterator.hasNext()){
            ObjectName objectName = iterator.next();
            HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean =
                    ManagementFactory.newPlatformMXBeanProxy(platformMBeanServer, objectName.toString(), HotSpotDiagnosticMXBean.class);
            return hotSpotDiagnosticMXBean;
        }
        return null;
    }
}
