package com.study.jdk5.net.networkinterface;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


/**
 *
 */
public class NetWorkInterfaceDemo {

    public static void main(String[] args) {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(localHost);//获取本地网络接口
            NetworkInterface eth3 = NetworkInterface.getByName("eth3");
            System.out.println(byInetAddress);
            System.out.println(byInetAddress.equals(eth3));
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();//
            while(networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                System.out.println(networkInterface);
            }
            Enumeration<InetAddress> inetAddresses = byInetAddress.getInetAddresses();
            while(inetAddresses.hasMoreElements()){
                InetAddress inetAddress = inetAddresses.nextElement();
                System.out.println(inetAddress);
            }
            String name = byInetAddress.getName();
            System.out.println(name);
            name = byInetAddress.getDisplayName();
            System.out.println(name);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
