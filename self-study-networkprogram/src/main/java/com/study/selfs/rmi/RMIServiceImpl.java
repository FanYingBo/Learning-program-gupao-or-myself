package com.study.selfs.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServiceImpl extends UnicastRemoteObject implements RMIService{

    public RMIServiceImpl() throws RemoteException {

    }

    @Override
    public String helloRmi(String name) throws RemoteException{
        return "this is "+name;
    }
}
