package com.study.selfs.tcc;

public class TccTransactionContext {

    TccTransactionContext header;
    TccTransactionContext tail;

    TccTransactionHandler tccTransactionHandler;

    public TccTransactionContext(TccTransactionHandler tccTransactionHandler) {
        this.tccTransactionHandler = tccTransactionHandler;
    }



}
