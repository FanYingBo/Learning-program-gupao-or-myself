package com.study.jdk7.util.concurrent.countedcompleter;

import java.util.Objects;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

public class Searcher extends CountedCompleter<String> {

    private AtomicReference<String> atomicReference;

    private String[] arrStr;

    private int low, high;

    private String lookingForData;

    public Searcher(AtomicReference<String> atomicReference, String[] arrStr, int low, int high){
        this(null,atomicReference,arrStr,low,high);
    }

    private Searcher(Searcher searcher, AtomicReference<String> atomicReference, String[] arrStr, int low, int high) {
        super(searcher);
        this.atomicReference = atomicReference;
        this.arrStr = arrStr;
        this.low = low;
        this.high = high;
    }

    @Override
    public String getRawResult() {
        return atomicReference.get();
    }

    @Override
    public void compute() {
        int l = low, h = high;
        while (atomicReference.get() == null && h >= l) {
            if (h - l >= 2) {
                int mid = (l + h) >>> 1;
                addToPendingCount(1);
                new Searcher(this, atomicReference, arrStr, mid, h)
                        .putThings(this.lookingForData)
                        .fork();
                h = mid;
            } else {
                String x = arrStr[l];
                if (matches(x) && atomicReference.compareAndSet(null, x))
                    quietlyCompleteRoot(); // root task is now joinable
                break;
            }
        }
        tryComplete();
    }
    private Boolean matches(String str){
        if(Objects.isNull(lookingForData)){
            return false;
        }
        if("tom".equals(str)){
            return true;
        }
        return false;
    }

    public Searcher putThings(String obj) {
        this.lookingForData = obj;
        return this;
    }
}
