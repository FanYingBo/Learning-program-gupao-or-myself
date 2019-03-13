package com.study.gupao.designmode.template;

import com.study.gupao.designmode.template.dao.MemberDao;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class TemplateDemo {

    public static void main(String[] args) {
//        File file = new File("D:/com.zip");
//        File toFile = new File("D:/testcan/com.zip");
//        long start = System.currentTimeMillis();
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            FileOutputStream fos = new FileOutputStream(toFile);
//            FileChannel fisChannel = fis.getChannel();
//            FileChannel fosChannel = fos.getChannel();
//            fisChannel.transferTo(0,fisChannel.size(),fosChannel);
//            System.out.println("传输完成-----耗时:"+(System.currentTimeMillis()-start));
//            fisChannel.close();
//            fosChannel.close();
//            fis.close();
//            fos.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        DataSource dataSource = null;
        MemberDao memberDao = new MemberDao(dataSource);
        memberDao.query();
    }
}
