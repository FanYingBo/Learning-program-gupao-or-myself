package com.study.selfs.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIService extends Remote {

    String helloRmi(String name) throws RemoteException;
}
