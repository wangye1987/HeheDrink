package com.heheys.ec.animationManage;


import com.heheys.ec.R;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.utils.LogUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ParabolaAnimationUtil {

    private Activity mActivity = null;
    private View mFromView;
    private View mToView;
    private int mFromX;
    private int mFromY;
    private int mToX;
    private int mToY;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    private ViewGroup animLayout = null;
    /**
     * 动画控件
     */
    private View mAnimView = null;
    private ParabolaAnimationListener animationListener = null;
    private Object tag = null;
    /**
     * 动画持续时间
     */
    public static final int DURATION = 1000;
    /**
     * 淡出效果时长
     */
    //private long alphaDuration = 1000;
    /**
     * 
     * @param context
     * @param from 显示图片控件 传入后需要计算坐标
     * @param to 动画结束位置控件
     */
    public ParabolaAnimationUtil(Activity context,View from,View to)
    {
        this.mActivity = context;
        this.mFromView = from;
        this.mToView = to;
        int[] fromLocal = new int[2];
        mFromView.getLocationInWindow(fromLocal);
        this.mFromX = fromLocal[0];
        this.mFromY = fromLocal[1];
        int[] toLocal = new int[2];
        this.mToView.getLocationInWindow(toLocal);
        this.mToX = toLocal[0];
        this.mToY = toLocal[1];
        this.screenHeight = ViewUtil.getScreenHeight(mActivity);
        //LogUtil.d("ParabolaAnimationUtil mFromX = " + mFromX + " mFromY = " + mFromY + " mToX = " + mToX + " mToY = " + mToY);
        initLayout();
    }
    public ParabolaAnimationUtil(Activity context,View from,int fromX,int fromY,View to)
    {
        this.mActivity = context;
        this.mFromView = from;
        this.mToView = to;
        this.mFromX = fromX;
        this.mFromY = fromY;
        int[] toLocal = new int[2];
        this.mToView.getLocationInWindow(toLocal);
        this.mToX = toLocal[0];
        this.mToY = toLocal[1];
        this.screenHeight = ViewUtil.getScreenHeight(mActivity);
        initLayout();
    }
    /**
     * 设置动画完成后回调监听
     * @param listener
     * @param tag
     */
    public void setAnimationListener(ParabolaAnimationListener listener,Object tag)
    {
        this.animationListener = listener;
        this.tag = tag;
    }
    /**
     * 启动动画
     * @param duration
     */
    public void start(long duration)
    {
        if(mAnimView != null)
        {
            setAnim(mAnimView,duration);
        }
    }
    private void initLayout() {
        animLayout = createAnimLayout();
        mAnimView = addViewToAnimLayout(animLayout,this.mFromView);
        animLayout.addView(mAnimView);
    }
    /**
     * 设置动画
     * @param v
     */
    private void setAnim(View v,long duration) {
        float toXDelta;
        float toYDelta;
        toXDelta = mToX - mFromX;
        toYDelta = mToY - mFromY;
        if(this.mFromView.getWidth() >  this.mToView.getWidth())
        {
            toXDelta  = toXDelta  - (this.mFromView.getWidth() - this.mToView.getWidth())/2;
        }else
        {
            toXDelta  = toXDelta  + (this.mToView.getWidth() - this.mFromView.getWidth())/2;
        }
        if(this.mFromView.getHeight() > this.mToView.getHeight())
        {
            toYDelta  = toYDelta  - (this.mFromView.getHeight() - this.mToView.getHeight())/2;
        }else
        {
            toYDelta  = toYDelta  + (this.mToView.getHeight() - this.mFromView.getHeight())/2;
        }

        /*************************Begin wudi modify Begin******************************/
        //顶点X轴
        float topX = 0;
        // 优化抛物线弧度适配,适配宫格右侧列表项动画
        	if(mFromX > ViewUtil.getScreenHeight(mActivity)/2){
            topX = toXDelta/3;
        } else {
            if(mFromY <= screenHeight/5)
            {
                topX = toXDelta/4;
            }else if(mFromY > screenHeight/5 && mFromY <= screenHeight/4)
            {
                topX = toXDelta/4;
            }else if(mFromY > screenHeight/4 && mFromY <= screenHeight*1/3)
            {
                topX = toXDelta/3;
            }else if(mFromY > screenHeight/3 && mFromY <= screenHeight*1/2)
            {
                topX = 9*toXDelta/25;
            }else if(mFromY > screenHeight/2 && mFromY <= screenHeight*3/4)
            {
                topX = 3*toXDelta/7;
            }else
            {
                topX = 4*toXDelta/9;
            }
        }

//        topX = toXDelta*(1-toYDelta/(screenHeight - mFromY));
//        System.out.println("topX =" + topX + " mToY=" + mToY + " mFromY=" + mFromY);
        //LogUtil.d("ParabolaAnimationUtil setAnim toXDelta = " + toXDelta + " toYDelta = " + toYDelta + " topX = " + topX);
        ParabolaAnimation anim = new ParabolaAnimation(0, toXDelta, 0, toYDelta, topX);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation anim) {
                if(animationListener != null)
                {
                    animationListener.onAnimationStart(tag);
                }
            }
            
            @Override
            public void onAnimationRepeat(Animation anim) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onAnimationEnd(Animation anim) {
                mAnimView.clearAnimation();
                //alphaOut(mAnimView);
                mAnimView.setVisibility(View.GONE);
                if(animationListener != null)
                {
                    animationListener.onAnimationEnd(tag);
                }
            }
        });
        v.startAnimation(anim);
//        v.setAnimation(anim);
//        anim.startNow();
    }

    /*private void alphaOut(final View v)
    {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(alphaDuration);
        anim.setFillAfter(true);
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation anim) {
                v.setVisibility(View.GONE);
            }
            
            @Override
            public void onAnimationRepeat(Animation anim) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onAnimationEnd(Animation anim) {
                v.setVisibility(View.GONE);
                v.clearAnimation();
            }
        });
        v.startAnimation(anim);
//        anim.startNow();
    }*/

    /**
     * 创建动画布局
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }
    private View addViewToAnimLayout(ViewGroup vg,View v) {
        ImageView animView = new ImageView(mActivity);
        v.setDrawingCacheEnabled(true);
        if(v.getDrawingCache() != null)
        {
            Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
            animView.setImageBitmap(bitmap);
        }else
        {
            if(v.getWidth() > 400)
            {
                animView.setImageResource(R.drawable.product_default);
            }else
            {
                animView.setImageResource(R.drawable.product_default);
            }
        }
        v.setDrawingCacheEnabled(false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(v.getWidth(),v.getHeight());
        lp.leftMargin = this.mFromX;
        lp.topMargin = this.mFromY;
        LogUtil.d("ParabolaAnimationUtil setAnim leftMargin = " + mFromX + " topMargin = " + mFromY + " width = " + v.getWidth() + " height = " + v.getHeight());
        animView.setLayoutParams(lp);
        return animView;
    }
    /**
     * 回调监听接口
     * @author xhb
     *
     */
    public interface ParabolaAnimationListener
    {
        public void onAnimationStart(Object tag);
        public void onAnimationEnd(Object tag);
    }
}
