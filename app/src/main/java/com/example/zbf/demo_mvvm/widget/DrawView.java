package com.example.zbf.demo_mvvm.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.healforce.healthapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Honglang on 2016/4/22.
 */

public class DrawView extends View
{

    private int XLength;
    private float XPoint;
    private int XScale;
    public int YLength;
    private float YPoint;
    private int YScale;
    private int width;
    private int height;
    private float xPointUnit;

    private static final int MaxDataSize = 600;
    private List<Float> data;
    private Paint paint = new Paint();

    //用来存储传送的x,y轴方向的格子数
    private static int wCount;
    private static int hCount;
    //屏幕方向标志,0为竖直方向，1为横向
    private static int pOrL;
    private float y0 = 0;

    public DrawView(Context paramContext)
    {
        this(paramContext, null);
    }

    public DrawView(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int resId)
    {
        super(context, attrs, resId);
        data = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        setProperties(width, height, pOrL);
    }

    public void setProperties(int width, int height, int pOrL)
    {
        Log.i("tag", "width=" + width);
        Log.i("tag", "height=" + height);
        XLength = width / wCount * wCount;
        YLength = height / hCount * hCount;
        XScale = XLength / wCount;
        YScale = YLength / hCount;
        YLength = YScale * hCount;
        XLength = XScale * wCount;
        XPoint = width % XScale / 2;     //原点X轴值
        YPoint = height - height % YScale / 2;       //原点Y轴值
        y0 = YPoint - YLength / 2.0F;
        Log.i("tag", "(XPoint,YPoint)= (" + XPoint + "," + YPoint + ")");
        Log.i("tag", "XScale=" + XScale + "  ,YScale=" + YScale);
    }

    public void setWhCounters(int w, int h, int pOrL)
    {
        wCount = w;
        hCount = h;
        this.pOrL = pOrL;
    }

    private Handler mh = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if (msg.what == 0)
            {                //判断接受消息类型
                postInvalidate();
            }
        }
    };

    public void addDataByPC80B(List<Float> list)
    {
        if (data != null && data.size() > 0)
        {
            data.clear();
        }
        for (int i = 0; i < list.size(); i++)
        {
            data.add(list.get(i));
            postInvalidate();
//            if (data.size() >= MaxDataSize) {
//                data.remove(0);
//            }
        }
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
    }

    private void setStroke(Paint paint, int color, int width)
    {
        paint.setColor(color);
        paint.setStrokeWidth(width);
    }

    protected void onDraw(Canvas canvas) throws IndexOutOfBoundsException
    {
        super.onDraw(canvas);
        xPointUnit = (XLength - 2 - 3) / 599F;
        //    画竖直格子线
        for (int j = wCount; j >= 0; j--)
        {
            if (j == 0)
            {
                setStroke(paint, getResources().getColor(R.color.black), 3);
                canvas.drawLine(XPoint + j * XScale, YPoint, XPoint + XScale * j, YPoint - YLength, paint);
            }
            else
            {
                if (j % 5 == 0)
                {
                    setStroke(paint, getResources().getColor(R.color.light_gray), 2);
                }
                else
                {
                    setStroke(paint, getResources().getColor(R.color.light_gray), 2);
                }
                canvas.drawLine(XPoint + j * XScale - 1, YPoint - 3F, XPoint + XScale * j - 1, YPoint - YLength - 3F, paint);
            }
        }
        //    画水平格子线
        for (int i = hCount; i >= 0; i--)
        {
            if (i == 0)
            {
                setStroke(paint, getResources().getColor(R.color.black), 3);
                canvas.drawLine(XPoint, YPoint - i * YScale - 1.5F, XPoint + XLength, YPoint - i * YScale - 1.5F, paint);
            }
            else
            {
                if (i == hCount / 2)
                {
                    setStroke(paint, getResources().getColor(R.color.green), 2);
                }
                else if (i == (hCount / 2 - 2) || i == (hCount / 2 + 2))
                {
                    setStroke(paint, getResources().getColor(R.color.cornflowerBlue), 2);
                }
                else
                {
                    setStroke(paint, getResources().getColor(R.color.light_gray), 2);
                }
                canvas.drawLine(XPoint + 3F, YPoint - i * YScale + 1, XPoint + XLength, YPoint - i * YScale + 1, paint);
            }
        }
        if (data.size() > 0)
        {
            //    画搏动折线
            paint.setColor(getResources().getColor(R.color.red));
            paint.setStrokeWidth(2.0F);
            for (int k = 1; k < data.size(); k++)
            {
                if (k == 1)
                {
                    canvas.drawLine(XPoint + 3 + (k - 1) * xPointUnit,
                            y0 - (data.get(k - 1)) * YScale / 80,
                            XPoint + 3 + k * xPointUnit,
                            y0 - (data.get(k)) * YScale / 80, paint);
                }
                else
                {
                    canvas.drawLine(XPoint + 3 + (k - 1) * xPointUnit,
                            y0 - (data.get(k - 1)) * YScale / 80,
                            XPoint + 3 + k * xPointUnit,
                            y0 - (data.get(k)) * YScale / 80, paint);
                }
//                canvas.drawLine(XPoint + (k - 1) * (5 * XScale / 120 + 1), y0 - (data.get(k - 1) - 98.0F) * YScale / 40,
//                        XPoint + k * (XLength / 120 + 1), y0 - (data.get(k) - 98.0F) * YScale / 40, paint);
            }
            //    画刷新线
//            paint.setColor(getResources().getColor(R.color.light_gray));
//            paint.setStrokeWidth(5.0F);
//            canvas.drawLine(index * XScale, YPoint - YLength, index * XScale, YPoint, paint);
        }
    }

//    public void setData(List<Float> list) {
//        for (int i = 0; i < list.size(); i++) {
//            if (data.size() < MaxDataSize) {
//                data.add(list.get(i));
//            } else {
//                if (index == MaxDataSize) {
//                    index = 0;
//                }
//                data.set(index, list.get(i));
//                index++;
//            }
//            postInvalidate();
//        }
//    }
}
