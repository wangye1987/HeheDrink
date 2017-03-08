package com.heheys.ec.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.heheys.ec.R;
import com.nineoldandroids.animation.ObjectAnimator;

public class ScrollViewPullLargen extends ScrollView {

	private View rl_top;
	private View ll_content;
	private ObjectAnimator oa;
	private float lastY = -1;
	private float detalY = -1;
	//是否到底部
	private boolean isButtom = false;
	//是否到底部了是否还在继续上拉
	private boolean isButtomScrll = false;
	//第一次滑动到底部不执行
	private int index = 0;
	/** 
     * ScrollView正在向上滑动 
     */  
    public static final int SCROLL_UP = 0x01;  
  
    /** 
     * ScrollView正在向下滑动 
     */  
    public static final int SCROLL_DOWN = 0x10;  
  
    /** 
     * 最小的滑动距离 
     */  
    private static final int SCROLLLIMIT = 40; 
	private int range;
	private int rangeWidth;
	
	private boolean isTouchOrRunning;
	private boolean isActionCancel;
	private LinearLayout more_info;

	public ScrollViewPullLargen(Context context) {
		super(context);
	}

	public ScrollViewPullLargen(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewPullLargen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setVerticalScrollBarEnabled(false);
		rl_top = findViewById(R.id.product_detail_vp);
		more_info = (LinearLayout) findViewById(R.id.linear_moreinfo);
		rl_top.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						rl_top.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						range = rl_top.getHeight();
						rl_top.getLayoutParams().height = range;
						rangeWidth = rl_top.getWidth();
						rl_top.getLayoutParams().width = rangeWidth;
					}
				});

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isActionCancel = false;
			isTouchOrRunning = true;
			lastY = ev.getY();
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			isTouchOrRunning = true;
			if (getScrollY() != 0) {
				detalY = 0;
				// 上拉滚动
			} else {
				// 下拉变大
				detalY = ev.getY() - lastY;
				if (detalY > 0) {
					setT((int) -detalY / 5);
					return true;
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
			isTouchOrRunning = false;
			if (getScrollY() == 0) {
				reset();
			}
			upY = ev.getY();
			View childView = getChildAt(0);
			if(childView.getMeasuredHeight() <= getScrollY() + getHeight()){
			if(index==2 && downY-upY > SCROLLLIMIT ){
				 if (mListener != null)  
		                mListener.scrollOritention(SCROLL_UP);  
			 }
			}
			break;
		case MotionEvent.ACTION_DOWN:
			downY = ev.getY();
			if(index == 1){
				index = 2;
			}
			break;
		}
		return super.onTouchEvent(ev);
	}
	@Override
	 public boolean performClick() {
	  // Calls the super implementation, which generates an AccessibilityEvent
	        // and calls the onClick() listener on the view, if any
	        super.performClick();

	        // Handle the action for the custom click here

	        return true;
	 }
	private ScrollListener mListener ;
	private float upY;
	private float downY;  
	  
    public static interface ScrollListener {  
        public void scrollOritention(int oritention);  
    }  
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (t > range) {
			return;
		}
	}
	 public void setScrollListener(ScrollListener l) {  
	        this.mListener = l;  
	    } 
	/**
	 * 滚动到下拉的位置
	 * 
	 * @param t
	 */
	public void setT(int t) {
		scrollTo(0, t);
		if (t < 0) {
			animatePull(t);
		}
	}

	/**
	 * 下拉动画
	 * 
	 * @param t
	 */
	private void animatePull(int t) {
		rl_top.getLayoutParams().height = range - t;
		rl_top.getLayoutParams().width = rangeWidth - t;
		rl_top.requestLayout();
	}

	/**
	 * 拉动下拉时恢复初始状态
	 */
	private void reset() {
		if (oa != null && oa.isRunning()) {
			return;
		}
		oa = ObjectAnimator.ofInt(this, "t", (int) -detalY / 5, 0);
		oa.setDuration(150);
		oa.start();
	}
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
	    int newScrollY = scrollY + deltaY;
	            final int bottom = maxOverScrollY + scrollRangeY;
	            final int top = -maxOverScrollY;
	             if (newScrollY > bottom) {
	                System.out.println("滑动到底端");
	                more_info.setVisibility(View.VISIBLE);
	                isButtom  = true;
	                index = 1;
	            }
	            else if (newScrollY < top) {
	               System.out.println("滑动到顶端");
	           }else{
	            	 isButtom  = false;
		             index = 0;
	            }
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
}
