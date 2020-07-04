package com.study.selfs.custom.http.response;

import java.io.IOException;
import java.io.OutputStream;

public class HttpCustomResponse {

    public OutputStream outputStream;

    public HttpCustomResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void write(String ret) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\r\n")
                    .append("Content-Type: text/html;\r\n")
                    .append("\r\n")
                    .append(ret);
        outputStream.write(stringBuilder.toString().getBytes());
    }

    public void close() throws IOException {
        outputStream.close();
    }
}
