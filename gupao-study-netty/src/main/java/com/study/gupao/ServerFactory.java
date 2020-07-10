package com.study.gupao;

import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.io.IOServer;
import com.study.gupao.io.bio.BioServer;
import com.study.gupao.io.nio.NioServer;
import com.study.gupao.netty.http.HttpNettyServer;
import com.study.gupao.netty.protobuf.CustomProtoBufNettyServer;
import com.study.gupao.netty.protobuf.ProtoBufNettyServer;

/**
 * 工厂模式
 * 创建不同类型IO server
 */
public class ServerFactory {


    public static IOServer create(IOServerKinds kind, int port, StringBorderBuild stringBorderBuild){
        switch (kind){
            case BIO_SERVER:
                return new BioServer(port,stringBorderBuild);
            case NIO_SERVER:
                return new NioServer(port,stringBorderBuild);
            case HTTP_NETTY_SERVER:
                return new HttpNettyServer(port);
            case PROTOBUF_NETTY_SERVER:
                return new ProtoBufNettyServer(port);
            case CUSTOM_PROTO_NETTY_SERVER:
                return new CustomProtoBufNettyServer(port);
            default:
                return null;
        }
    }

    public enum IOServerKinds {
        BIO_SERVER ,
        NIO_SERVER,
        PROTOBUF_NETTY_SERVER,
        CUSTOM_PROTO_NETTY_SERVER,
        HTTP_NETTY_SERVER
    }

}
