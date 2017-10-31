package com.example.zbf.demo_mvvm.pdf;

import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.zbf.demo_mvvm.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFActivity2 extends AppCompatActivity
{
    PDFView pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        final String pdfUrl = "http://192.168.1.101:8081/report/testDownload";
        pdf = findViewById(R.id.pdfView);

        new Thread()
        {
            @Override
            public void run() {
                try
                {
                    URL url = new URL(pdfUrl);
                    HttpURLConnection connection = (HttpURLConnection)
                            url.openConnection();
                    connection.setRequestMethod("GET");//试过POST 可能报错
                    connection.setDoInput(true);
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    //实现连接
                    connection.connect();

                    System.out.println("connection.getResponseCode()=" + connection.getResponseCode());
                    if (connection.getResponseCode() == 200)
                    {
                        InputStream is = connection.getInputStream();
                        //这里给过去就行了
                        pdf.fromStream(is)
//                                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onDraw(new OnDrawListener()
                                {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })
                                .onLoad(new OnLoadCompleteListener()
                                {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        Toast.makeText(getApplicationContext(), "loadComplete", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .onPageChange(new OnPageChangeListener()
                                {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

                                    }
                                })
                                .onPageScroll(new OnPageScrollListener()
                                {
                                    @Override
                                    public void onPageScrolled(int page, float positionOffset) {

                                    }
                                })
                                .onError(new OnErrorListener()
                                {
                                    @Override
                                    public void onError(Throwable t) {
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .enableAnnotationRendering(false)
                                .password(null)
                                .scrollHandle(null)
                                .load();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                super.run();
            }
        }.start();

    }


}
