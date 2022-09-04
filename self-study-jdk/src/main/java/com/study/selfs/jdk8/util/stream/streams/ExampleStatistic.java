package com.study.selfs.jdk8.util.stream.streams;

import java.math.BigDecimal;

public class ExampleStatistic {

    private int batchNumber;
    /**
     * 按顺序递增
     */
    private BigDecimal lotNumber;
    /**
     * 根据lotNumber 递增
     */
    private BigDecimal executeValue;

    public ExampleStatistic(int batchNumber, BigDecimal lotNumber, BigDecimal executeValue) {
        this.batchNumber = batchNumber;
        this.lotNumber = lotNumber;
        this.executeValue = executeValue;
    }

    public ExampleStatistic(BigDecimal lotNumber, BigDecimal executeValue) {
        this.lotNumber = lotNumber;
        this.executeValue = executeValue;
    }

    public BigDecimal getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(BigDecimal lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigDecimal getExecuteValue() {
        return executeValue;
    }

    public void setExecuteValue(BigDecimal executeValue) {
        this.executeValue = executeValue;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }
}
