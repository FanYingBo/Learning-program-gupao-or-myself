package com.study.selfs.jdk5.nio.file.filesystems;

import java.nio.file.WatchEvent;

public interface FilePathModifyListener {

    void onFileCreate(WatchEvent<?> watchEvent);

    void onFileDelete(WatchEvent<?> watchEvent);

    void onDirCreate(WatchEvent<?> watchEvent);

    void onDirDelete(WatchEvent<?> watchEvent);

    void onReset(WatchEvent<?> watchEvent);
}
