package com.study.gupao.nio;

import com.study.gupao.buffer.ReadWriteBuffer;
import com.study.gupao.format.MessageUtils;
import com.study.gupao.format.StringBorderBuild;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@Slf4j
public class NioClient implements Runnable{

    private int port;

    private InetSocketAddress inetSocketAddress;

    private SocketChannel socketChannel;

    private Selector selector;

    private Charset charset = Charset.forName("UTF-8");

    private int count=1;

    private StringBorderBuild border;

    public NioClient(int port, StringBorderBuild border) {
        this.port = port;
        this.border = border;
        this.inetSocketAddress = new InetSocketAddress(this.port);
    }

    @Override
    public void run() {
        start();
    }
    public void start(){
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            int interestOps = SelectionKey.OP_READ | SelectionKey.OP_WRITE;//注册时间
            socketChannel.register(selector,interestOps ,new ReadWriteBuffer(256,256));
            socketChannel.connect(inetSocketAddress);
            while(!socketChannel.finishConnect()){
                if(socketChannel.finishConnect()){
                    break;
                }
            }

        } catch (IOException e) {
            log.error("error occurred: "+e.getMessage());
        }
        try{
            while(!Thread.currentThread().isInterrupted()){
                // 阻塞，等待事件的selectionKeys
                selector.select();
                processSelector();

                Random random = new Random();
                Thread.sleep(1000+random.nextInt(1000));
            }
        }catch (IOException e){
            log.error("error occurred: "+e.getMessage());
        } catch (InterruptedException e) {
            log.error("error occurred: "+e.getMessage());
        } finally {
            try {
                selector.close();
            } catch (IOException e1) {
                log.error(Thread.currentThread().getName() + " close selector failed");
            }finally{
                log.info(Thread.currentThread().getName() + "  closed");
            }
        }

    }

    private void processSelector(){
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

        while(selectionKeyIterator.hasNext()){
            SelectionKey selectionKey = selectionKeyIterator.next();
            processSelectKeys(selectionKey);
            // 移除
            removeSelectionKeyAfterProcess(selectionKeyIterator);

            count++;
        }
    }



    private void processSelectKeys(SelectionKey selectionKey){
        StringBuffer receiveMessageBuffer = new StringBuffer("");
        try{
            ReadWriteBuffer readWriteBuffer = (ReadWriteBuffer) selectionKey.attachment();
            ByteBuffer readBuffer = readWriteBuffer.getReadBuffer();
            ByteBuffer writeBuffer = readWriteBuffer.getWriteBuffer();
            /*Set中的每个key代表一个通道*/
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            // 表示socket缓冲区有数据可读
            if(selectionKey.isReadable()){
                socketChannel.read(readBuffer);
                readBuffer.flip();//反转
                CharBuffer charbuffer = charset.decode(readBuffer);
                String allReceiveMessage = new String(charbuffer.array());
                MessageUtils.decodeStringMessage(socketChannel, receiveMessageBuffer , allReceiveMessage,border.getEndBorder());
                readBuffer.clear();
            }
            if(selectionKey.isWritable()){
                writeBuffer.put((Thread.currentThread().getName()+"_"+count).getBytes("UTF-8"));
                writeBuffer.put(border.getEndBorder().getBytes());
                writeBuffer.flip();
                // 将buffer写入到socket缓冲区
                socketChannel.write(writeBuffer);
                writeBuffer.clear();
            }
        }catch (Exception e){
            log.error("error occurred: "+e.getMessage());
        }

    }

    private void removeSelectionKeyAfterProcess(Iterator<SelectionKey> selectionKeyIterator) {
        selectionKeyIterator.remove();
    }

}
