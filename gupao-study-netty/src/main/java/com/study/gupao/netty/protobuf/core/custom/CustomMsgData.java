package com.study.gupao.netty.protobuf.core.custom;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

public class CustomMsgData {
    private int mainCmd;
    private int subCmd;
    private byte[] protoBufData;
    private int offset;
    private int length;
    private MessageLite messageLite;

    public CustomMsgData(int mainCmd, int subCmd,MessageLite messageLite){
        this(mainCmd,subCmd,messageLite.toByteArray(),0,messageLite.toByteArray().length);
    }

    public CustomMsgData(int mainCmd, int subCmd,byte[] protoBufData,int offset, int length) {
        this.mainCmd = mainCmd;
        this.subCmd = subCmd;
        this.protoBufData = protoBufData;
        this.offset = offset;
        this.length = length;
    }

    public int getMainCmd() {
        return mainCmd;
    }

    public void setMainCmd(int mainCmd) {
        this.mainCmd = mainCmd;
    }

    public int getSubCmd() {
        return subCmd;
    }

    public void setSubCmd(int subCmd) {
        this.subCmd = subCmd;
    }

    public byte[] getProtoBufData() {
        return protoBufData;
    }

    public void setProtoBufData(byte[] protoBufData) {
        this.protoBufData = protoBufData;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "CustomMsgData{" +
                "mainCmd=" + mainCmd +
                ", subCmd=" + subCmd +
                ", messageLite=" + messageLite +
                '}';
    }

    public <T extends MessageLite> T parseTo(MessageLite protoType) throws InvalidProtocolBufferException {
        return (T) protoType.getParserForType().parseFrom(this.protoBufData,offset,length);
    }
}
