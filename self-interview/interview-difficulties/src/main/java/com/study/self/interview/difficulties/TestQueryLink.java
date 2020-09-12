package com.study.self.interview.difficulties;

/**
 * 大文件查找
 */
public class TestQueryLink {
    public static void main(String[] args) {

        InternetLinkFileQuery internetLinkFileQuery = new InternetLinkFileQuery("D:\\batch\\address.txt","www.vuxubhq.com");

        internetLinkFileQuery.init();

        internetLinkFileQuery.waitAllThreadComplete();
    }
}
