package com.study.gupao.designmode.proxy.dynamic.custom;

import java.io.*;
import java.net.URISyntaxException;

public class MyClassLoader extends ClassLoader{

    private File classFile;

    public MyClassLoader(){
        String path = "";
        try {
            path = MyClassLoader.class.getResource("/").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.classFile = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = MyClassLoader.class.getPackage().getName() + "." + name;
        ByteArrayOutputStream byteArrayOutputStream = null;
        FileInputStream fileIutputStream = null;
        try{

            if(this.classFile != null){
                File file = new File(classFile, className.replace(".", File.separator)+".class");
                fileIutputStream = new FileInputStream(file);
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while( (len =fileIutputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,len);
                }
            }
            return defineClass(className,byteArrayOutputStream.toByteArray(),0,byteArrayOutputStream.size());
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(fileIutputStream != null){
                try {
                    fileIutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return super.findClass(className);
    }
}
