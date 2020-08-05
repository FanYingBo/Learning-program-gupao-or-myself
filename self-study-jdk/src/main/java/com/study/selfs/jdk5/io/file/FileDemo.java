package com.study.selfs.jdk5.io.file;

import java.io.File;
import java.io.IOException;

/**
 * @see java.io.File
 * jdk1.0
 */
public class FileDemo {

    public static void main(String[] args) {
        File file = new File("D:\\filehashssd");

        try {
            file.createNewFile();
//            System.out.println("标准路径："+file.getCanonicalFile());
//            System.out.println("绝对路径："+file.getAbsoluteFile());
//            // 根据所写的pathname获取
            System.out.println("相对路径："+file.getPath());
            System.out.println("文件名："+file.getName());
            System.out.println(file.hashCode());
            System.out.println(file.getParent());
//            FileInputStream fis = new FileInputStream(file);
//            byte[] bytes = new byte[256];
//            int len = -1;
//            int datalen = 0;
//            byte[] data = new byte[0];
//            while((len = fis.read(bytes))!=-1){
//                byte[] temp = data;
//                datalen+=len;
//                data = new byte[datalen];
//                System.arraycopy(bytes,0,data,temp.length,len);
//                System.arraycopy(temp,0,data,0,temp.length);
//            }
//            System.out.println(new String(data));

          //  System.out.println(Arrays.toString("\r\n".getBytes())); [13, 10]
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
