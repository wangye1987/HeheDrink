package com.heheys.ec.animationManage;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.heheys.ec.lib.view.ViewUtil;

/**
 * 
 * Describe:商品详情动画类
 *
 * Date:2015年12月8日下午3:45:14
 *
 * Author:LZL
 *
 */
public class ProductDetailAnimation {
	// 动画层
	private ViewGroup animationLayer;
	private Activity mActivity;
	private static ProductDetailAnimation obj = new ProductDetailAnimation();
	private static final int ANIMATION_TIME = 2000; // 动画的执行时间

	public static ProductDetailAnimation getInstance() {
		return obj;

	}

	/**
	 * 
	 * Describe:动画启动
	 *
	 * Date:2015年12月8日下午3:46:07
	 *
	 * Author:LZL
	 *
	 */
	public void startAnimation(final View animationView, View endView,
			int[] startLocation, int[] endLocation, Activity mActivity) {
		this.mActivity = mActivity;
		animationLayer = null;
		animationLayer = createAnimationLayer();
		// 将动画视图加入到动画层
		animationLayer.addView(animationView);
		// 设置动画视图布局参数
		final View manimationView = setAnimationViewLayout(animationView,
				startLocation);
		// 动画结束的时候X轴移动的位置
		int endX = endLocation[0] - startLocation[0] - endView.getWidth()/2 ;
		// 动画结束的时候Y轴移动的位置
		int endY = endLocation[1] - startLocation[1] - endView.getHeight();
		/**
		 * X轴移动动画
		 */
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);
		translateAnimationX.setFillAfter(true);
		/**
		 * Y轴移动动画
		 */
		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);
		translateAnimationX.setFillAfter(true);
		/**
		 * 缩放动画
		 */
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.2f, 1.0f,
				0.2f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(ANIMATION_TIME);
		scaleAnimation.setRepeatCount(0);// 动画重复执行的次数
		scaleAnimation.setFillAfter(true);
		/**
		 * 淡入淡出动画
		 */
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.3f);
		alphaAnimation.setRepeatCount(0);// 动画重复执行的次数
		alphaAnimation.setDuration(ANIMATION_TIME);
		alphaAnimation.setFillAfter(true);
		/**
		 * 执行复合动画
		 */
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(false);
		set.setInterpolator(new BounceInterpolator());
		set.addAnimation(scaleAnimation);
		set.addAnimation(translateAnimationX);
		set.addAnimation(translateAnimationY);
		set.addAnimation(alphaAnimation);
		
		set.setDuration(ANIMATION_TIME);// 动画的执行时间
		manimationView.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				manimationView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				manimationView.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 
	 * Describe:创建动画层，帧布局上层覆盖视图
	 *
	 * Date:2015年12月8日下午4:22:11
	 *
	 * Author:LZL
	 *
	 */
	@SuppressWarnings("ResourceType")
	private ViewGroup createAnimationLayer() {
		ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(mActivity);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	/**
	 * 
	 * Describe:设置动画视图布局参数
	 *
	 * Date:2015年12月9日上午10:57:43
	 *
	 * Author:LZL
	 *
	 */
	private View setAnimationViewLayout(final View animationView, int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewUtil.dip2px(mActivity, 60), ViewUtil.dip2px(mActivity, 60));
		lp.leftMargin = x;
		lp.topMargin = y;
		animationView.setLayoutParams(lp);
		return animationView;
	}

}
