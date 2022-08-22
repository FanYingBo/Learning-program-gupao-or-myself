package com.study.selfs.rmi.server;

import com.study.selfs.rmi.RMIService;
import com.study.selfs.rmi.RMIServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class JavaRmiServerDemo {

    public static void main(String[] args) {
        try {
            RMIService rmiService = new RMIServiceImpl();
            LocateRegistry.createRegistry(8181);
            Naming.bind("rmi://127.0.0.1:8181/rmiService",rmiService);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
