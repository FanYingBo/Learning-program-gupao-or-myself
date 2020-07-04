package com.study.selfs.custom.http.request;

import java.io.*;

public class HttpCustomRequest {

    private InputStream inputStream;
    private String contentType;
    private String requestType;
    private String requestUri;
    private boolean isParsed;
    private String httpVersion;

    public HttpCustomRequest(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public String read() throws IOException {
        byte[] bytes = new byte[2048];
        StringBuilder stringBuilder = new StringBuilder();
        if(this.inputStream.read(bytes) != -1){
            stringBuilder.append(new String(bytes));
        }
        String requestBody = stringBuilder.toString();
        System.out.println(requestBody);
        if("".equals(requestBody)){
            throw new RuntimeException("400");
        }
        String[] lines = requestBody.split("\\n");
        for(String line : lines){
            parseRequestHeaders(line);
        }
        isParsed = true;
        return "Google visit";
    }

    public String getRequestMethod(){
        if(isParsed){
            return this.requestType;
        }
        return "GET";
    }

    public String getRequestUri(){
        if(isParsed){
            return this.requestUri;
        }
        return "/hello";
    }

    public void close() throws IOException {
        inputStream.close();
    }
    private void parseRequestHeaders(String str){
        if(str.contains("GET") || str.contains("POST")){
            String[] strings = str.split(" ");
            this.requestType = strings[0];
            this.requestUri = strings[1];
            this.httpVersion = strings[2];
        }
    }


}
