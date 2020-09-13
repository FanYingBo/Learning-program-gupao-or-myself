package com.study.self.interview.difficulties.filequery;

/**
 * 大文件查找
 * 125M 文件 10M 内存 耗时 3090 毫秒
 * 800M 文件 10M 内存 耗时 13777 毫秒
 * 特点：
 * 1.根据系统内存空间大小对文件分批次读取
 * 2.根据系统内核个数，多线程分配内存空间
 */
public class TestQueryLink {
    public static void main(String[] args) {
        testDemo("D:\\batch\\address3.txt","www.rreliwjiq.com");
    }

    public static void testDemo(String filePath, String queryStr){
        long start = System.currentTimeMillis();

        InternetLinkFileQuery internetLinkFileQuery = new InternetLinkFileQuery(filePath, queryStr);

        internetLinkFileQuery.init();

        internetLinkFileQuery.waitAllThreadComplete();

        System.out.println("文件查找结束，耗时： "+ (System.currentTimeMillis() - start)+" ms");
    }

}
