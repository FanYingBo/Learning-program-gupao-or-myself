package com.study.selfs.tcc.handler;

import com.study.selfs.tcc.TccTransactionHandler;
import com.study.selfs.tcc.fun.TryFunction;

public class OrderServiceHandler implements TccTransactionHandler{

    @Override
    public boolean tryExecute() {
        return false;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public boolean cancel() {
        return false;
    }
}
