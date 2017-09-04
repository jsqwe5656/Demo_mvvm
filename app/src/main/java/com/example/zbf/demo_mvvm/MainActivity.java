package com.example.zbf.demo_mvvm;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                GetExample getExample = new GetExample();
                try
                {
                    String string = getExample.run("http://gc.ditu.aliyun.com/regeocoding?l=39.938133,116.395739&type=001");
                    Log.e("zbf",string);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }
}
