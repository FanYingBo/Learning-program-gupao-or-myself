package com.study.gupao.io.bio;

import com.study.gupao.format.MessageUtils;
import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.io.AbstractIOClient;
import com.study.gupao.io.IOClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioClient extends AbstractIOClient {

    private static final Log log = LogFactory.getLog(BioClient.class);

    private int port;
    private StringBorderBuild border;
    private Socket socket ;
    private InetSocketAddress inetSocketAddress;
    private int timeOut = 3000;
    private int count = 1;

    public BioClient(int port, StringBorderBuild border) {
        this.port = port;
        this.border = border;
        this.inetSocketAddress = new InetSocketAddress(this.port);
    }

    @Override
    public void start(){
        socket = new Socket();
        try {
            socket.setKeepAlive(true);
            socket.connect(this.inetSocketAddress,timeOut);
            socket.setReceiveBufferSize(9 * 1024);
            socket.setSendBufferSize(9 * 1024);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write((Thread.currentThread().getName()+"_"+count).getBytes(StandardCharsets.UTF_8));
            outputStream.write(border.getEndBorder().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            byte[] byteBuf = new byte[1024];
            while(inputStream.read(byteBuf) != -1){
//                Thread.sleep(1000);
                MessageUtils.decodeByteStringMessage(byteBuf,border.getEndBorder());
//                System.out.println(new String(byteBuf));
                count ++;
                outputStream.write((Thread.currentThread().getName()+"_"+count+border.getEndBorder()).getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean close() {
        return false;
    }
}
