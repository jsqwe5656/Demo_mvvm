package com.example.zbf.demo_mvvm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.healforce.healthapplication.R;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by user on 2015/11/17.
 */
public class HistoryDataShowView extends View
{
    int width;
    int height;

    private int type = 1;    //  显示哪种类型数据
    private int flag = 1;    //  显示哪个时间段的数据
    private int indicator = 1;    //   显示哪项指标
    private Map<Integer, Integer> data;
    private int xPoint = getResources().getDimensionPixelSize(R.dimen.x32);     //    X轴起点
    private int yPoint = getResources().getDimensionPixelSize(R.dimen.x200);
    private int xLength = getResources().getDimensionPixelSize(R.dimen.x400);  //  x轴长
    private int yLength = getResources().getDimensionPixelSize(R.dimen.x180);  //  y轴高
    private int xSpace;   //  x轴间距
    private int ySpace;   //  y轴间距
    String title;
    int ava;


    public static int BLOODOXYGEN = 1;
    public static int BLOODPRESSURE = 2;
    public static int HEARTALL = 3;
    public static int BLOODSUGAR = 4;
    public static int BODYTEMER = 5;
    public static int BODYWEIGHT = 6;

    public static int DAY = 1;
    public static int WEEK = 2;
    public static int MOUTH = 3;

    int previewPoint = -1;    //    折线的前一个点


    public HistoryDataShowView(Context context) {
        super(context);
        data = new TreeMap<>();
    }

    public HistoryDataShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        data = new TreeMap<>();
    }

    public HistoryDataShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        data = new TreeMap<>();
    }

    public void setData(Map<Integer, Integer> map, int type, int flag, int indicator, String title, int ava) {
        this.data = map;
        this.type = type;
        this.flag = flag;
        this.indicator = indicator;
        this.title = title;
        this.ava = ava;
        previewPoint = -1;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        xLength = width - getResources().getDimensionPixelSize(R.dimen.x64);
        canvas.drawColor(Color.WHITE);
        Paint p1 = new Paint();
        p1.setStyle(Paint.Style.FILL_AND_STROKE);
        p1.setAntiAlias(true); //去锯齿
        p1.setColor(getResources().getColor(R.color.yellow));//  画线笔
        Paint p2 = new Paint();
        p2.setStyle(Paint.Style.FILL_AND_STROKE);
        p2.setAntiAlias(true); //去锯齿
        p2.setColor(Color.RED);//  画点笔
        String xStart = null;
        String xMid = null;
        String xEnd = null;
        String sAva = null;

        if (type == BLOODOXYGEN) {

            if (indicator == 1 || indicator == 2) {
                ySpace = (yLength - 50) / 50;
            } else if (indicator == 3) {
                ySpace = (yLength - 50) / 20;
            }

            if (flag == DAY) {
                xSpace = xLength / 23;
                xStart = "0";
                xMid = "12";
                xEnd = "0";
                sAva = "日平均值:" + ava;
            } else if (flag == WEEK) {
                xSpace = xLength / 6;
                xStart = "日";
                xMid = "三";
                xEnd = "六";
                sAva = "周平均值:" + ava;
            } else if (flag == MOUTH) {
                xSpace = xLength / 29;
                xStart = "1";
                xMid = "15";
                xEnd = "30";
                sAva = "月平均值:" + ava;
            }

            //    画上下两条线
            canvas.drawLine(xPoint, yPoint, xPoint + xLength, yPoint, p1);

            canvas.drawLine(xPoint, yPoint - yLength + 50, xPoint + xLength, yPoint - yLength + 50, p1);
            //    画标题
            canvas.drawText(title, xPoint, yPoint - yLength + 10, p1);
            canvas.drawText(sAva, xPoint, yPoint - yLength + 35, p1);
            //    画x轴刻度
            canvas.drawText(xStart, xPoint, yPoint + 20, p1);
            canvas.drawText(xMid, xPoint + width / 2 - 40, yPoint + 20, p1);
            canvas.drawText(xEnd, xPoint + width - 100, yPoint + 20, p1);
            //    画y轴刻度
//            canvas.drawText(yStart, xPoint - 20, yPoint, p1);
//            canvas.drawText(yEnd, xPoint - 30, yPoint - yLength + 50, p1);

            //  spo2和心率数值较大，所以和pi的画法分开
            //  spo2和心率y轴起始为50，终点为100
            //  pi起始为0，终点为20
            if (indicator == 1 || indicator == 2) {
                //    画平均值刻度线,如96
                for (int i = 32; i < xLength + 30; i += 3) {
                    if (i % 2 == 0) {
                        canvas.drawLine(i, yPoint - ((ava - 50) * ySpace), i + 3, yPoint - ((ava - 50) * ySpace), p1);
                    }

                }
                if (data.size() > 1) {
                    for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                        if (previewPoint != -1) {
                            canvas.drawLine(xPoint + previewPoint * xSpace, yPoint - ((data.get(previewPoint) - 50) * ySpace),
                                    xPoint + entry.getKey() * xSpace, yPoint - ((data.get(entry.getKey()) - 50) * ySpace), p1);
                            canvas.drawCircle(xPoint + previewPoint * xSpace, yPoint - ((data.get(previewPoint) - 50) * ySpace), 2.0f, p2);
                        }

                        if (previewPoint != entry.getKey()) {
                            previewPoint = entry.getKey();
                        }
                    }

                }
            } else if (indicator == 3) {
                //    画平均值刻度线,如96
                for (int i = 32; i < xLength + 30; i += 3) {
                    if (i % 2 == 0) {
                        canvas.drawLine(i, yPoint - (ava * ySpace), i + 3, yPoint - (ava * ySpace), p1);
                    }

                }
                if (data.size() > 1) {
                    for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                        if (previewPoint != -1) {
                            canvas.drawLine(xPoint + previewPoint * xSpace, yPoint - (data.get(previewPoint) * ySpace),
                                    xPoint + entry.getKey() * xSpace, yPoint - (data.get(entry.getKey()) * ySpace), p1);
                            canvas.drawCircle(xPoint + previewPoint * xSpace, yPoint - (data.get(previewPoint) * ySpace), 2.0f, p2);
                        }

                        if (previewPoint != entry.getKey()) {
                            previewPoint = entry.getKey();
                        }
                    }

                }
            }

        }
    }
}
