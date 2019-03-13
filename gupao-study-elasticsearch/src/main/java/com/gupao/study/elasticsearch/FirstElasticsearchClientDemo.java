package com.gupao.study.elasticsearch;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

public class FirstElasticsearchClientDemo {

    public static void main(String[] args) {
        Settings settings = Settings.builder().put().build();
        TransportClient transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.1.156",9300)));


        System.out.println("连接elasticsearch成功");

        transportClient.close();
    }
}
