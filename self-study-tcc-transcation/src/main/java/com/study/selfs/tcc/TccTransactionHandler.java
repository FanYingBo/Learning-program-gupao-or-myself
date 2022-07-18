package com.study.selfs.tcc;

public interface TccTransactionHandler {

    boolean tryExecute();

    boolean commit();

    boolean cancel();
}
