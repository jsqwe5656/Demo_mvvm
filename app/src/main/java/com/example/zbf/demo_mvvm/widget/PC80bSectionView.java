package com.example.zbf.demo_mvvm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.healforce.healthapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 心电仪时间滑动小控件
 */
public class PC80bSectionView extends View
{
    private List<Float> data = new ArrayList<>();   //   数据源
    public static float width;                      //   控件宽
    private float height;                           //   控件高
    private DrawView drawView;
    //    随手指移动的正方形的中心
    private float rec_centerX;
    private float rec_centerY;
    public static Rect rect = new Rect();
    private static long lastRectMills = 0;

    private float half_height;
    private float rectCX;
    private Paint paint = new Paint();
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PC80bSectionView(Context context)
    {
        this(context, null, 0);
    }

    public PC80bSectionView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PC80bSectionView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 画随手指移动的正方形
     *
     * @param canvas
     * @param paint
     */
    private void drawRect(Canvas canvas, Paint paint)
    {
        half_height = height / 3.0F;
        rectCX = width / 15.0F;
        rec_centerY = half_height + 2;
        if (rec_centerX < rectCX)
        {
            rec_centerX = rectCX;
        }

        if (rec_centerX > width - rectCX)
        {
            rec_centerX = (width - rectCX);
        }

        rect.left = (int) (rec_centerX - rectCX + 1);
        rect.right = (int) (rec_centerX + rectCX - 1);
        rect.top = (int) (rec_centerY - half_height + 1);
        rect.bottom = (int) (rec_centerY + half_height - 2);

        paint.setStrokeWidth(2);
        paint.setColor(Color.GRAY);
        canvas.drawLine(rect.left, rect.bottom, rect.left, rect.top, paint);            //左边框
        canvas.drawLine(rect.right, rect.bottom, rect.right, rect.top, paint);          //有边框
        canvas.drawLine(rect.left, rect.top, rect.right, rect.top, paint);              //上边框
        canvas.drawLine(rect.left, rect.bottom, rect.right, rect.bottom, paint);        //下边框
    }

    /**
     * 画波形
     *
     * @param canvas
     * @param paint
     */
    private void drawLine(Canvas canvas, Paint paint)
    {
        float sectionHeight = height / 3 * 2;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, (int) sectionHeight, width, (int) sectionHeight, paint);
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.SANS_SERIF);
        for (int m = 0; m <= 4500; m += 750)
        {
            if (m % 750 == 0)
            {
                int mn = m / 750 * 5;
                if (mn / 5 % 2 == 0)
                {
                    textPaint.setTextSize(height / 5F);
                } else
                {
                    textPaint.setTextSize(height / 7F);
                }
                if (m == 0)
                {
                    textPaint.setTextAlign(Paint.Align.LEFT);
//                    canvas.drawText(mn + "s", 5, height * 14 / 15F, textPaint);
                    canvas.drawLine(1, sectionHeight, 1, sectionHeight - getResources().getDimensionPixelSize(R.dimen.x10) * 2, paint);
                } else if (m == 4500)
                {
                    textPaint.setTextAlign(Paint.Align.RIGHT);
//                    canvas.drawText(mn + "s", m * (width / 4500.0F) - 5, height * 14 / 15F, textPaint);
                    canvas.drawLine(m * (width / 4500.0F) - 1, sectionHeight,
                            m * (width / 4500.0F) - 1, sectionHeight - getResources().getDimensionPixelSize(R.dimen.x10) * 2, paint);
                } else
                {
                    textPaint.setTextAlign(Paint.Align.CENTER);
//                    canvas.drawText(mn + "s", m * (width / 4500.0F), height * 14 / 15F, textPaint);
                    canvas.drawLine(m * (width / 4500.0F) + 1, sectionHeight,
                            m * (width / 4500.0F) + 1, sectionHeight - getResources().getDimensionPixelSize(R.dimen.x10) * 2, paint);
                }

            }
        }
        paint.setColor(Color.BLUE);
        if (this.data != null && this.data.size() > 1)
        {
//            for (int i = 1; i < data.size(); ) {
//                //    4500表示每次心电仪传输过来的30秒数据的个数
//                float startX = (i - 1) * (width / 4500.0F);
//                float startY = height - (data.get(i - 1)) / 10.0F;
//                float endX = i * (width / 4500.0F);
//                float endY = height - data.get(i) / 10.0F;
//                canvas.drawLine(startX, startY, endX, endY, paint);
////                i++;
//            }
        }
    }

    public void postData(List<Float> list)
    {
        data.addAll(list);
        postInvalidate();
    }

    public void clearData()
    {
        if ((data != null) && (data.size() > 1))
        {
            data.clear();
        }
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        drawRect(canvas, paint);
        drawLine(canvas, paint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                rec_centerX = ((int) event.getX());
                long currentTimeMills = System.currentTimeMillis();
                invalidate();
                if (currentTimeMills - lastRectMills >= 200)
                {
                    //    每次正方形发生移动计算出其中包含区间并将响应区间数据发送至pulseView显示
                    setData(drawView);
                    lastRectMills = currentTimeMills;
                }
        }
        return true;
    }

    public void setData(DrawView drawView)
    {

        if (drawView != null)
        {
            List<Float> list = new ArrayList<>();
            for (int i = 0; i < data.size(); i++)
            {
                if ((i >= (int) (rect.centerX() * 4500.0F / width) - 300) && list.size() <= 599)
                {
                    list.add(data.get(i));
                }
            }
            drawView.addDataByPC80B(list);
        }
    }

    public void setPulseView(DrawView drawView)
    {
        this.drawView = drawView;
    }
}