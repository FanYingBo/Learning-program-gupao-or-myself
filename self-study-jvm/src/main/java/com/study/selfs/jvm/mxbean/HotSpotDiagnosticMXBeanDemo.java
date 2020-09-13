package com.study.selfs.jvm.mxbean;

import com.study.selfs.jvm.mxbean.util.JVMUtils;
import com.sun.management.HotSpotDiagnosticMXBean;

import java.io.IOException;

/**
 * 打印堆栈的文件 必须以 .hprof 结尾
 *
 * （可以使用 jvisualvm 来解析）
 */
public class HotSpotDiagnosticMXBeanDemo {

    public static void main(String[] args) {
        HotSpotDiagnosticMXBean hotSpotMBean = JVMUtils.getHotSpotMBean();
        try {
            hotSpotMBean.dumpHeap("D:\\dump-test.hprof",false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
