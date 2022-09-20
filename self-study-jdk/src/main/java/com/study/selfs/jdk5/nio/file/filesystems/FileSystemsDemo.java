package com.study.selfs.jdk5.nio.file.filesystems;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * @see FileSystems 1.7
 * @see WatchService
 *  文件监听实现 ，有点鸡肋只能监听当前目录下的文件
 *  <dependencies>
 *     <dependency>
 *         <groupId>commons-io</groupId>
 *         <artifactId>commons-io</artifactId>
 *         <version>2.6</version>
 *     </dependency>
 * </dependency>
 */
public class FileSystemsDemo {

    public static void main(String[] args) {
        Path path = Paths.get("D:\\test");
        try {
            WatchService watchService = path.getFileSystem().newWatchService();
            path.register(watchService,StandardWatchEventKinds.ENTRY_CREATE
                    , StandardWatchEventKinds.ENTRY_DELETE
                    , StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.OVERFLOW);
            FilePathModifyListener filePathModifyListener = new DefaultFilePathModifyListener();
            while(true){
                WatchKey watchKey = watchService.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for(WatchEvent watchEvent:watchEvents){
                    WatchEvent.Kind eventType = watchEvent.kind();
                    if(eventType.equals(StandardWatchEventKinds.ENTRY_CREATE)){
                        System.out.println(path+" create file "+watchEvent.context());
                    }else if(eventType.equals(StandardWatchEventKinds.ENTRY_DELETE)){
                        System.out.println(path+" delete file "+watchEvent.context());
                    }else{
                        System.out.println(path+" modify file "+watchEvent.context());
                    }
                }
                //每次处理完事件后，必须调用 reset 方法重置 watchKey 的状态为 ready，否则 watchKey 无法继续监听事件
                if (!watchKey.reset()) {
                    break;
                }
            }

//        String property = System.getProperty("java.nio.file.spi.DefaultFileSystemProvider");
//        System.out.println(property);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
