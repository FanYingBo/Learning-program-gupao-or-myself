package com.study.selfs.jdk5.lang.management.managementfactory;


import java.lang.management.*;

/**
 * @since 1.5 JMX
 * @see MemoryMXBean
 * @see MemoryUsage
 * @see ThreadInfo
 *
 */
public class ManagementFactoryDemo {

    public static void main(String[] args) {
        // 获得所有的线程信息
        printAllThreadInfo();
//        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
//        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
//        int count = memoryMXBean.getObjectPendingFinalizationCount();
//        memoryMXBean.setVerbose(false);
////        memoryMXBean.gc();
//        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
//        System.out.println("max:"+heapMemoryUsage.getMax()/(1024*1024*1024) + "G used:" +heapMemoryUsage.getUsed()/(1024*1024)+"M"+" obj count:"+count);

    }

    private static void printAllThreadInfo(){
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] allThreadIds = threadMXBean.getAllThreadIds();
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(allThreadIds);
        for(ThreadInfo threadInfo : threadInfos){
            System.out.println("*************线程:"+threadInfo.getThreadId()+" thread name:"+threadInfo.getThreadName()+"的信息*************");
            System.out.println("*            lock name:"+threadInfo.getLockName()+" lock owner:"+threadInfo.getLockOwnerName()+"                 *");
            System.out.println("*            blocked time:"+threadInfo.getBlockedTime()+" block count:"+threadInfo.getBlockedCount()+"                 *");
            printMonitorInfo(threadInfo);
        }
        System.out.println("total thread count:"+threadMXBean.getThreadCount()+" CurrentThreadCpuTime:"+threadMXBean.getCurrentThreadCpuTime()+" CurrentThreadUserTime:"+threadMXBean.getCurrentThreadUserTime());

    }

    private static void printMonitorInfo(ThreadInfo threadInfo){
        MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
        for(MonitorInfo monitorInfo:lockedMonitors){
            System.out.println("*           LockedStack :"+monitorInfo.getLockedStackDepth()+" LockedStackFrame:"+monitorInfo.getLockedStackFrame());
        }
    }
}
