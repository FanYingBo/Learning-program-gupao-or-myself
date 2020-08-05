package com.study.selfs.jdk5.nio.file.files;

import java.io.IOException;
import java.nio.file.*;

/**
 * jdk 1.7
 * @see java.nio.file.FileSystems
 * @see java.nio.file.WatchService
 * @see java.nio.file.WatchKey
 * @see java.nio.file.Path
 * @see java.nio.file.StandardWatchEventKinds
 * @see java.nio.file.StandardWatchEventKinds.StdWatchEventKind
 */

public class FilesDemo {

    public static void main(String[] args) {
        FileSystem fileSystem = FileSystems.getDefault();

        try {
            WatchService watchService = fileSystem.newWatchService();
            Path path = fileSystem.getPath("D:\\filemonitors");
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY
                    );
            WatchKey poll = watchService.poll();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
