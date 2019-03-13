package com.study.test.util;

public class PrintUtils {

    public static void print(Thread thread){
        print(thread,null);
    }

    public static void print(Object ... msg){
        print(null,msg);
    }
    public static void print(Thread thread,Object ... msg){
        StringBuffer sb = new StringBuffer();
        StringBuffer sbMsg = new StringBuffer();
        if(thread != null && msg == null){
            System.out.printf("[线程 %s] 正在执行 \n",thread.getName());
        }else if(thread == null && msg != null){
            for(Object o : msg){
                sbMsg.append(String.valueOf(o)+" ");
            }
            System.out.printf(sbMsg.toString()+"\n");
        }else {
            sb.append("[线程 %s] 正在执行");
            for(int i = 0;i < msg.length; i++){
                sb.append("%s ");
                sbMsg.append(msg[i]+" ");
            }
            sb.append("\n");
            System.out.printf(sb.toString(),thread.getName(),sbMsg.toString());
        }
    }

}
