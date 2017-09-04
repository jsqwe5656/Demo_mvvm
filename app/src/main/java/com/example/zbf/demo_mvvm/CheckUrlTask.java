package com.example.zbf.demo_mvvm;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 使用异步函数处理网络请求
 * Created by zbf on 2017/6/22.
 */
public class CheckUrlTask extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... params)
    {
 /*       try
        {
            return urlResponse(params[0]);
        } catch (IOException e)
        {
            return (e.getMessage());
        }*/
        GetExample getExample = new GetExample();
        try
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
            {
                String string = getExample.run("http://gc.ditu.aliyun.com/regeocoding?l=39.938133,116.395739&type=001");
                return string;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;

    }


    private String urlResponse(String param) throws IOException
    {
        InputStream is = null;
        URL url;
        try
        {
            url = new URL(param);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* ms */);
            conn.setConnectTimeout(15000/* ms */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //尝试连接
            conn.connect();

            int response = conn.getResponseCode();
            is = conn.getInputStream();
            return String.valueOf(response);
        } catch (Exception e)
        {
            return e.getMessage();
        } finally
        {
            if (is != null)
                is.close();
        }

    }
}
