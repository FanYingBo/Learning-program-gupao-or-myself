package com.study.gupao.io.nio;

import com.study.gupao.io.AbstractIOServer;
import com.study.gupao.io.IOServer;
import com.study.gupao.buffer.ReadWriteBuffer;
import com.study.gupao.format.MessageUtils;
import com.study.gupao.format.StringBorderBuild;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class NioServer extends AbstractIOServer implements IOServer {

    private static final Log log = LogFactory.getLog(NioServer.class);

    private InetSocketAddress inetSocketAddress;

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private Charset charset = Charset.forName("UTF-8");

    private StringBorderBuild border;

    public NioServer(int port, StringBorderBuild border){
        this.serverPort = port;
        this.inetSocketAddress = new InetSocketAddress(this.serverPort);
        this.border = border;
    }
    @Override
    public void start() {
        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);// 非阻塞模式

            serverSocketChannel.bind(inetSocketAddress,100);// 挂起的最大连接数
            log.info("The nio server has been created and bind port "+this.serverPort);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            processSelector();
        } catch (IOException e) {
            log.error("error occurred: "+e.getMessage());
        }finally{
            try{
                selector.close();
            }catch(IOException e){
                log.error("selector close failed");
            }finally{
                log.info("server close");
            }
        }
    }


    private void processSelector(){
        while(!Thread.currentThread().isInterrupted()){
            try {
                int count = selector.select();
                if(count == 0){
                    continue;
                }
//                selector.keys();//无法更改的集合selectionKeys    Collections.unmodifiableSet();
                //获取事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//无法增加的集合selectionKey,可移除的 sun.nio.ch.Util.ungrowableSet();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    //
                    SelectionKey selectionKey = iterator.next();
                    // 处理事件
                    processSelectionKeys(selectionKey);
                    // 移除
                    removeKeyAfterProcess(iterator);

                }
                Random random = new Random();
                Thread.sleep(1000+random.nextInt(1000));
            } catch (IOException e) {
                log.error("error occurred: "+e.getMessage());
            } catch (InterruptedException e) {
                log.error("error occurred: "+e.getMessage());
            }
        }
    }

    private void processSelectionKeys(SelectionKey selectionKey) throws IOException {
        StringBuffer receiveMessageBuffer = new StringBuffer("");
        try{
            // 事件为OP_ACCEPT
            if(selectionKey.isAcceptable()){
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                // 向注册器注册感兴趣的事件 绑定特定的对象
                socketChannel.register(selector,SelectionKey.OP_READ,
                        new ReadWriteBuffer(1024,1024));
                log.info("accept from "+socketChannel.getRemoteAddress());
            }

            if(selectionKey.isReadable()) {
                ReadWriteBuffer attachBuffer = (ReadWriteBuffer)selectionKey.attachment();
                ByteBuffer readBuffer = attachBuffer.getReadBuffer();
                ByteBuffer writeBuffer = attachBuffer.getWriteBuffer();
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                // 从底层socket向缓冲区中读入数据
                socketChannel.read(readBuffer);
                // flip() 从buffer中读数据
                readBuffer.flip();//从buffer中读数据之前需要进行flip()

                // 通配符截取指定长度的数据

                CharBuffer charBuffer = charset.decode(readBuffer);
                String allReceiveMessage = new String(charBuffer.array());
                MessageUtils.decodeStringMessage(socketChannel, receiveMessageBuffer , allReceiveMessage, border.getEndBorder());
//                String readBufferData = new String(charBuffer.array());
//                int sepIndex;
//                if((sepIndex = readBufferData.indexOf("&")) >= 0){
//                    String dataFromClient = readBufferData.substring(0, sepIndex);
//                }
                readBuffer.rewind();
//                readBuffer.put((readBufferData.substring(sepIndex+1)+"^").getBytes());
                writeBuffer.put(("echo from service:").getBytes());
                writeBuffer.put(readBuffer);
                writeBuffer.put(border.getEndBorder().getBytes());
//                CharBuffer charBuffer = charset.decode(writeBuffer);
//                log.info("write to the channel: "+socketChannel.getRemoteAddress()+" message: "+new String(charBuffer.array()));
                readBuffer.clear();
                // 设置通道事件 为可写
                selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
            }

            if(selectionKey.isWritable()) {
                ReadWriteBuffer attachBuffer = (ReadWriteBuffer)selectionKey.attachment();
                ByteBuffer writeBuffer = attachBuffer.getWriteBuffer();
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                writeBuffer.flip();//
                int len = 0;
                while(writeBuffer.hasRemaining()){
                    len= socketChannel.write(writeBuffer);// 从buffer中写入到socketChannel
                    if(len == 0){
                        break;
                    }
                }

                writeBuffer.compact();
                /*说明数据全部写入到底层的socket写缓冲区*/
                if(len != 0){
                    /*取消通道的写事件*/
                    selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_WRITE));
                }
            }
        }catch (IOException e){
            log.error("error occurred: "+e.getMessage());
            selectionKey.cancel();
            selectionKey.channel().close();
        }
    }

    private void removeKeyAfterProcess(Iterator<SelectionKey> iterator){
        iterator.remove();
    }
    @Override
    public boolean close() {
        return false;
    }

}
