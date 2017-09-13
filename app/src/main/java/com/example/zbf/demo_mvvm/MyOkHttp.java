package com.example.zbf.demo_mvvm;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 封装okhttp 请求
 * Created by zbf on 2017/9/13.
 */
public class MyOkHttp
{
    private void getHttp(String url, final IOResult listen)
    {
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder()
                .build();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                listen.HttpListen(e.getMessage(),1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                listen.HttpListen(response.body().string(),0);
            }
        });
    }

    private void postHttp_From(String url,IOResult listen)
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key","value");                     //TODO 添加form键值对

        RequestBody requestBody = builder.build();
        //构建Post请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });





    }
}
