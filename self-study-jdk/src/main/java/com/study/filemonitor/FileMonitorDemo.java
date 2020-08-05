package com.study.filemonitor;

public class FileMonitorDemo {
    public static void main(String[] args) {
        // 文件监听子系统，只实现一个文件下的监听
        // 文件监听效果： 1.被监听文件夹文件的增加，删除，修改操作
        //             2.打印出被监听文件夹下的某个文件被更改了
        //            3.打印出某个文件被更改的内容
//        File fileDir = new File(System.getProperty("user.dir"));
//        long modified = fileDir.lastModified();
//        String[] files = fileDir.list();
//        while(true){
//            if(modified == fileDir.lastModified()){
//                continue;
//            }
//            modified = fileDir.lastModified();
//            String[] list = fileDir.list();
//            print(Arrays.toString(list));
//        }
        FileScanner fileScanner = new FileScanner("D:\\filemonitor");

    }

    private static void print(Object object){
        System.out.println(object);
    }

}


