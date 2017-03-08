package com.heheys.ec.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author wangkui
 *  
 *  动画manager
 */
public class AnimationCartUtil {
	static AnimationCartUtil animationCartUtil;
	public static AnimationCartUtil getInstance(){
		if(animationCartUtil == null){
			animationCartUtil = new AnimationCartUtil();
		}
		return animationCartUtil;
	}
	
	private static ViewGroup anim_mask_layout;//动画层
	private static ViewGroup anim_mask_layout_view;//动画层

	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("ResourceType")
	public  static ViewGroup createAnimLayout(Activity mContext) {
		ViewGroup rootView = (ViewGroup) (mContext.getWindow().getDecorView());
		LinearLayout animLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
//		android.view.WindowManager.LayoutParams mPopupWindowLayoutParams = new WindowManager.LayoutParams();
//		mPopupWindowLayoutParams.type = android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}
	
	/**
	 * @Description: activity 弹出层view 上创建动画层 
	 * @param
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("ResourceType")
	public  static ViewGroup createAnimLayoutPopu(Activity mContext,View view) {
//		ViewGroup rootView = (ViewGroup) (mContext.getWindow().getDecorView());
		LinearLayout animLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		((ViewGroup) view).addView(animLayout);
		return animLayout;
	}
	private static View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}
	
	
	static ViewGroup getAnimation(Context mContext){
		anim_mask_layout = createAnimLayout((Activity) mContext);
		return anim_mask_layout;
	}
	static ViewGroup getViewAnimation(Context mContext,View view){
		anim_mask_layout_view = createAnimLayoutPopu((Activity) mContext,view);
		return anim_mask_layout_view;
	}
	public static void setViewAnim(Context mcontext,ImageView ivShoppingCart,final View v, int[] start_location,View popuView) {
		anim_mask_layout_view = null;
//		anim_mask_layout_view = getViewAnimation(mcontext,popuView);
		getViewAnimation(mcontext,popuView).addView(v);//把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout_view, v,
				start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		ivShoppingCart.getLocationInWindow(end_location);// shopCart是那个购物车
		
		// 计算位移
		int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);
		
		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);
		
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 0f, 0.6f, 0f,
				Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT,
				1f);
		scaleAnimation.setRepeatCount(0);// 动画重复执行的次数
		
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			}
		});
	}
	public static void setAnim(Context mcontext,ImageView ivShoppingCart,final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = getAnimation(mcontext);
		anim_mask_layout.addView(v);//把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
			ivShoppingCart.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 0f, 0.6f, 0f,
				Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT,
				1f);
		scaleAnimation.setRepeatCount(0);// 动画重复执行的次数
		
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			}
		});
	}

}
