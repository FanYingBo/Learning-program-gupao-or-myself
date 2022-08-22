package com.study.selfs.rmi.client;

import com.study.selfs.rmi.RMIService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClientDemo {

    public static void main(String[] args) {
        try {
            RMIService rmiService = (RMIService) Naming.lookup("rmi://127.0.0.1:8181/rmiService");
            System.out.println(rmiService.helloRmi("tom"));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
