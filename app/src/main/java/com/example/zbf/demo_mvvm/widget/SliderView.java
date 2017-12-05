package com.example.zbf.demo_mvvm.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.healforce.healthapplication.bean.OnSiderViewScrollStateChangeListener;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by user on 2015/11/20.
 */
public class SliderView extends HorizontalScrollView
{
    // 在HorizontalScrollView有个LinearLayout
    private LinearLayout linearLayout;
    // 菜单，内容页
    private ViewGroup menu;
    private ViewGroup content;
    //菜单宽度
    private int menuWidth;
    // 屏幕宽度
    private int screenWidth;
    // 菜单与屏幕右侧的距离(dp)
    private int myMenuPaddingRight = 80;
    // 避免多次调用onMeasure的标志
    private boolean once = false;
    Handler handler = new Handler();
    OnSiderViewScrollStateChangeListener mlistener;

    public SliderView(Context context) {
        super(context);
        init(context);
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;  //  获得屏幕宽度
        //    将dp转换为px
        myMenuPaddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,
                context.getResources().getDisplayMetrics());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once){//    只执行一次
            //    获得第一个子元素
            linearLayout = (LinearLayout) this.getChildAt(0);
            menu = (ViewGroup) linearLayout.getChildAt(0);
            content = (ViewGroup) linearLayout.getChildAt(1);

            //    设置子view的宽度
            menuWidth = menu.getLayoutParams().width = screenWidth - myMenuPaddingRight;
            content.getLayoutParams().width = screenWidth;
            once = true;
        }
    }
    //设置View的位置，首先，先将Menu隐藏（ScrollView的画面内容
    // （非滚动条）正数表示向左移，向上移）
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollTo(menuWidth, 0);
                }
            },100);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX >= menuWidth/2){
                    smoothScrollTo(menuWidth,0);
                }else {
                    smoothScrollTo(0,0);
                }
                return true;

        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mlistener != null){
            mlistener.setOnOnSiderViewScrollStateChange(l , oldl);
        }
        float scale = l* 1.0f/menuWidth;

        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);
        // 调用属性动画，设置TranslationX
        ViewHelper.setTranslationX(menu, menuWidth * scale * 0.8f);
        ViewHelper.setScaleX(menu, leftScale);
        ViewHelper.setScaleY(menu, leftScale);
        ViewHelper.setAlpha(menu, leftAlpha);
        // 设置content的缩放的中心点
        ViewHelper.setPivotX(content, 0);
        ViewHelper.setPivotY(content, content.getHeight() / 2);
        ViewHelper.setScaleX(content, rightScale);
        ViewHelper.setScaleY(content, rightScale);

    }

    public void setOnSiderViewScrollStateChange(OnSiderViewScrollStateChangeListener mlistener) {
        this.mlistener = mlistener;
    }
}
