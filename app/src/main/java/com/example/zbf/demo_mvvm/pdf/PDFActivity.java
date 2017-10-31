package com.example.zbf.demo_mvvm.pdf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zbf.demo_mvvm.IOHttpCallBack;
import com.example.zbf.demo_mvvm.MyOkHttp;
import com.example.zbf.demo_mvvm.R;
import com.example.zbf.demo_mvvm.web.MainActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;

public class PDFActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    PDFView pdf;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        final String pdfUrl = "http://blog.csdn.net/cfy137000/article/details/54838608";
        pdf = findViewById(R.id.pdfView);
        btn = findViewById(R.id.btn_date);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PDFActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                //设置主色调
                dpd.setAccentColor(Color.parseColor("#256cdf"));
/*                dpd.setThemeDark(modeDarkDate.isChecked());
                dpd.vibrate(vibrateDate.isChecked());
                dpd.dismissOnPause(dismissDate.isChecked());
                dpd.showYearPickerFirst(showYearFirst.isChecked());
                dpd.setVersion(showVersion2.isChecked() ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);*/
                dpd.show(getFragmentManager(),"DatePicker");

            }
        });
/*        DownloadUtil.get().download(pdfUrl, "/download", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                Log.e("zbf", "下载完成");
//                loadPdf("download");
            }

            @Override
            public void onDownloading(int progress) {
//                progressBar.setProgress(progress);
                Log.e("zbf", "下载中");
            }

            @Override
            public void onDownloadFailed() {

                Log.e("zbf", "下载失败");
            }
        });*/
    }

    /**
     * 来自文件 加载PDF
     */
    private void loadPDF(File url) {
        pdf.fromFile(url)
//                                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                })
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Toast.makeText(getApplicationContext(), "loadComplete", Toast.LENGTH_SHORT).show();
                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {

                    }
                })
                .onError(new OnErrorListener() {
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

    /**
     * 来自输入流 加载PDF
     */
    private void loadPDF(InputStream inputStream)
    {
        pdf.fromStream(inputStream)
//                                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                })
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Toast.makeText(getApplicationContext(), "loadComplete", Toast.LENGTH_SHORT).show();
                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {

                    }
                })
                .onError(new OnErrorListener() {
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

    /**
     * 生成文件
     *
     * @param filePath 文件地址
     * @param fileName 文件名称
     */
    public File makeFilePath(String filePath, String fileName) {

        File file = null;

        makeRootDirectory(filePath);

        try {

            file = new File(filePath + fileName);

            if (!file.exists()) {

                file.createNewFile();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return file;

    }


    /**
     * 生成文件夹
     *
     * @param filePath 文件夹地址
     */
    public static void makeRootDirectory(String filePath) {

        File file = null;

        try {

            file = new File(filePath);

            if (!file.exists()) {

                file.mkdir();

            }

        } catch (Exception e) {

            Log.i("error:", e + "");

        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.e("zbf","year:" + year + "monthOfYear:" + monthOfYear + "dayOfMonth:" + dayOfMonth);
    }
}
