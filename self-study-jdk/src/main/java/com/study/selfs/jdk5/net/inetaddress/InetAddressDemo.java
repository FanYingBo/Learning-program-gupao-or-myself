package com.study.selfs.jdk5.net.inetaddress;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 1.InetAddress会与本地DNS服务器建立连接来查找名字和数字地址，如果改地址被查找过，则会被缓存在本地，找不到地址则会抛出UnknownHostException
 * 2.当通过IP地址来查找对应的InetAddress的时候，会为IP地址创建一个InetAddress对象，而不检查DNS，当显式地调用getHostName()才会查找DNS
 * 3.主机名的查找要比IP地址稳定的多，IP地址经常因为服务器变更而更换
 * 4.安全性问题，InetAddress对象被认为存在一个不安全操作，因为这需要一个DNS查找
 *
 *
 *
 * java 对不成功的DNS查询缓存只有10秒
 */
public class InetAddressDemo {

    public static void main(String[] args) {
        try {
//            InetAddress inetAddress = InetAddress.getLocalHost();// 获取本地地址
            InetAddress inetAddress = InetAddress.getByName("redisdoc.com");

            String address = inetAddress.getHostName();// 先查找本地的DNS缓存
            System.out.println("address: "+address);
            address = inetAddress.getCanonicalHostName();// 每次获取都查找DNS服务器，并且可能会更新本地的DNS缓存
            System.out.println("address: "+address);
            String hostAddress = inetAddress.getHostAddress();// 获取IP地址
            System.out.println("HostAddress: "+hostAddress+" ip-version: ipv"+ipAddressVersion(inetAddress));

            System.out.println("wildcard address: "+inetAddress.isAnyLocalAddress());//是否是通配地址 IPv4通配地址是0.0.0.0  IPv6通配地址是0:0:0:0:0:0:0:0
            System.out.println("loopback address: "+inetAddress.isLoopbackAddress());//是否是回溯地址 回溯地址直接在IP层连接同一台计算机，而不使用任何物理硬件，可以绕过以太网
            System.out.println("IPv6 local address: "+inetAddress.isLinkLocalAddress());// 是否是IPv6本地连接地址
            System.out.println("IPv6 site local address: "+inetAddress.isSiteLocalAddress());// 是否是本地网站地址
            System.out.println("multicast address: "+inetAddress.isMulticastAddress());//组播地址，组播地址会将内容广播给所有预订的计算机
            System.out.println("global multicast address: "+inetAddress.isMCGlobal()); //全球组播地址
            System.out.println("org multicast address: "+inetAddress.isMCOrgLocal());//组织范围组播地址
            System.out.println("site multicast address: "+inetAddress.isMCSiteLocal());//网站范围组播地址
            System.out.println("link multicast address: "+inetAddress.isMCLinkLocal());//子网范围的组播地址
            System.out.println("node multicast address: "+inetAddress.isMCNodeLocal());//本地接口组播地址

            System.out.println("isReachable: "+inetAddress.isReachable(1000));//测试主机是否可达
            if(ipAddressVersion(inetAddress) == 4){
                System.out.println(((Inet4Address)inetAddress));
            }else if(ipAddressVersion(inetAddress) == 6){
                System.out.println(((Inet6Address)inetAddress).isIPv4CompatibleAddress());
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int ipAddressVersion(InetAddress inetAddress){
        byte[] address = inetAddress.getAddress();
        System.out.println("byte address: "+Arrays.toString(address));
        if(address.length == 4){
            return 4;
        }else if(address.length == 16){
            return 6;
        }else{
            return -1;
        }
    }



}
