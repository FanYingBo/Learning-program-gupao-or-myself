package com.study.gupao;

import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.io.IOServer;
import com.study.gupao.io.bio.BioServer;
import com.study.gupao.io.nio.NioServer;
import com.study.gupao.netty.http.HttpNettyServer;
import com.study.gupao.netty.protobuf.ProtoBufNettyServer;

/**
 * 创建不同类型IO server
 */
public class ServerFactory {


    public static IOServer create(IOServerKinds kind, int port, StringBorderBuild stringBorderBuild){
        if(kind.equals(IOServerKinds.BIO_SERVER)){
            return new BioServer(port,stringBorderBuild);
        }else if(kind.equals(IOServerKinds.NIO_SERVER)){
            return new NioServer(port,stringBorderBuild);
        }else if(kind.equals(IOServerKinds.HTTP_NETTY_SERVER)){
            return new HttpNettyServer(port);
        }else{
            return new ProtoBufNettyServer(port);
        }
    }

    public enum IOServerKinds {
        BIO_SERVER ,
        NIO_SERVER,
        PROTOBUF_NETTY_SERVER,
        HTTP_NETTY_SERVER
    }

}
