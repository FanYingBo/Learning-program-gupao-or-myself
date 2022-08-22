package com.study.selfs.http.client.okhttp;

import okhttp3.*;

import java.io.IOException;

public class OkHttpClientDemo {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://localhost:8080/hello?name=tom").get().build();
        Call call = okHttpClient.newCall(request);
        // 异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(Thread.currentThread().getName()+": "+response.body().string());
            }
        });
    }
}
