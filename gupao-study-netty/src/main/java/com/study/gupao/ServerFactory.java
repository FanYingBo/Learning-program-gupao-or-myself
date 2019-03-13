package com.study.gupao;

import com.study.gupao.bio.BioServer;
import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.netty.NettyServer;
import com.study.gupao.nio.NioServer;

/**
 * 创建不同类型IO server
 */
public class ServerFactory {


    public static IOServer create(IOServerKinds kind, int port, StringBorderBuild stringBorderBuild){
        if(kind.equals(IOServerKinds.BIO_SERVER)){
            return new BioServer(port,stringBorderBuild);
        }else if(kind.equals(IOServerKinds.NIO_SERVER)){
            return new NioServer(port,stringBorderBuild);
        }else{
            return new NettyServer();
        }
    }

    public static enum IOServerKinds {
        BIO_SERVER ,
        NIO_SERVER,
        NETTY_SERVER
    }

}
