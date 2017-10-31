package com.example.zbf.demo_mvvm.web;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zbf.demo_mvvm.R;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class MainActivity extends AppCompatActivity
{
    private WebView webView;
    private String url = "192.168.1.19:8081/trend/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        setWebSettings();
        webView.loadUrl(url);






/*
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
*/
    }

    /**
     * 设置webView设置
     */
    private void setWebSettings() {
        WebSettings websettings = webView.getSettings();
        //支持js
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置字符编码
        websettings.setDefaultTextEncodingName("GBK");





    }


    private WebViewClient client = new WebViewClient()
    {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView webView,
                                              HttpAuthHandler httpAuthHandler,
                                              String s, String s1) {
            super.onReceivedHttpAuthRequest(webView, httpAuthHandler, s, s1);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }

        @Override
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            if(null != data)
            {
                String tUrl = data.getStringExtra("WEB_URL");
                if(null != tUrl && tUrl.length() > 0)
                {
                    url = tUrl;
                    webView.loadUrl(url);
//                    SharePreferUtils.setSharePreferValue(this,"WEB_URL", "main_page",url);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
