package com.study.selfs.jdk5.nio.file.filesystems;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * @see FileSystems 1.7
 * @see WatchService
 *  文件监听实现
 */
public class FileSystemsDemo {

    public static void main(String[] args) {
        Path path = Paths.get("D:\\test");
        try {
            WatchService watchService = path.getFileSystem().newWatchService();
            path.register(watchService,StandardWatchEventKinds.ENTRY_CREATE
                    , StandardWatchEventKinds.ENTRY_DELETE
                    , StandardWatchEventKinds.ENTRY_MODIFY);
            while(true){
                WatchKey watchKey = watchService.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for(WatchEvent watchEvent:watchEvents){
                    WatchEvent.Kind eventType = watchEvent.kind();
                    if(eventType.equals(StandardWatchEventKinds.ENTRY_CREATE)){
                        System.out.println(path.toString()+" create file "+watchEvent.context());
                    }else if(eventType.equals(StandardWatchEventKinds.ENTRY_DELETE)){
                        System.out.println(path.toString()+" delete file "+watchEvent.context());
                    }else{
                        System.out.println(path.toString()+" modify file "+watchEvent.context());
                    }
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
