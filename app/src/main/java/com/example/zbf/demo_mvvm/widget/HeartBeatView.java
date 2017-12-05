package com.example.zbf.demo_mvvm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/11/12.
 */
public class HeartBeatView extends View
{

    int width;
    int height;

    private List<Integer> data = new ArrayList<>();

    public HeartBeatView(Context context)
    {
        super(context);
    }

    public HeartBeatView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HeartBeatView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void setData(List<Integer> data)
    {
        this.data = data;
        //刷新界面
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        Rect rect;
        if (data.size() > 0)
        {
            for (int i = 0; i < data.size(); i++)
            {
                rect = new Rect(0, height, width, height - data.get(i));
                canvas.drawRect(rect, paint);
            }
        } else
        {
            rect = new Rect(0, height, width, 0);
            canvas.drawRect(rect, paint);
        }

    }
}
