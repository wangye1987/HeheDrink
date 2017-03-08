package com.heheys.ec.controller.fragment;

import com.heheys.ec.animationManage.ParabolaAnimation;
import com.heheys.ec.lib.view.ViewUtil;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * Describe:抛物线动画管理
 * 
 * Date:2015-9-25
 * 
 * Author:liuzhouliang
 */
public class ShoppingCartAnimationManager {
	private static Activity mActivity = null;
	private static ViewGroup anim_mask_layout; // 动画层
	private static ViewGroup anim_detail_mask_layout; // 商品详情动画层
	private static final int TIME_ANIMATION = 1200; // 动画的执行时间
	private static final int TIME_DETAIL_ANIMATION = 1300; // 动画的执行时间
	private static final int REPEAT_COUNT = 0; // 动画重复执行的次数
	private static final int LOCATION_COUNT = 2;
	private static final int LOCATION_X_INDEX = 0;
	private static final int LOCATION_Y_INDEX = 1;
	private static final int LOCATION_OFF_SET = 60;
	private static final int LOCATION_OFF_SET_X = 10;
	private static final int LOCATION_OFFSET_Y = 10;
	private static final int LOCATION_GRID_SECOND_OFFSET = 100;
	private static final int STANDARD_WIDTH = 720;

	private static final float DETAIL_ROUND_SCALE = 121f / 250f; // 需小于1/2

	/*
	 * v 需要执行动画效果的动画层
	 * 
	 * start_location 屏幕坐标组合
	 * 
	 * shopCart 动画目标位置视图
	 * 
	 * */
	public static void setListAnimation(Activity activity, final View v,
			int[] start_location, View shopCart) {
		mActivity = activity;
		anim_mask_layout = null;
		// 创建动画层
		anim_mask_layout = createAnimLayout();
		// 将动画视图添加到动画层
		anim_mask_layout.addView(v);

		// 偏移量
		int offSet = ViewUtil.dip2px(activity, LOCATION_OFF_SET);
		// 偏移量X
		int offSetX = ViewUtil.dip2px(activity, LOCATION_OFF_SET_X);
		// 偏移量Y
		int offSetY = ViewUtil.dip2px(activity, LOCATION_OFFSET_Y);

		// int startX = start_location[0] + offSetX;
		// int startY = start_location[1] - offSetY;
		int startX = start_location[0];
		int startY = start_location[1];

		//获取当前需要执行动画视图距离左边和上面的距离
		final View view = addViewToAnimLayout(v, startX, startY);
		int[] end_location = new int[LOCATION_COUNT];// 这是用来存储动画结束位置的X、Y坐标
		shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

		int deviceHeight = ViewUtil.getScreenHeight(activity);

		// 计算位移
		// int endX = end_location[LOCATION_X_INDEX] - start_location[0]
		// - ViewUtil.dip2px(mActivity, 90) ;
		int endX = end_location[LOCATION_X_INDEX]-20;
		int endY = end_location[LOCATION_Y_INDEX];// 动画位移的y坐标
		// int endX = end_location[LOCATION_X_INDEX]
		// + (int) (offSet * (float) (2 - startY / deviceHeight));
		// int endY = end_location[LOCATION_Y_INDEX] - startY;// 动画位移的y坐标

		// if ((float) startY / deviceHeight > (float) 3 / 5) {
		// endX = end_location[LOCATION_X_INDEX]
		// + (int) (offSet * (float) (2 - startY / deviceHeight));
		// endY -= ViewUtil.dip2px(activity, 20);
		// }

		/**
		 * 移动动画
		 */
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
//		translateAnimationX.setInterpolator(new OvershootInterpolator());
		translateAnimationX.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
//		translateAnimationY.setInterpolator(new OvershootInterpolator());
		translateAnimationY.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		translateAnimationY.setFillAfter(true);

		/**
		 * 缩放动画
		 */
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 0f, 0.7f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		scaleAnimation.setFillAfter(true);

		/**
		 * 淡入淡出动画
		 */
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.3f);
		alphaAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		alphaAnimation.setFillAfter(true);

		/**
		 * 旋转动画
		 */
		/*
		 * RotateAnimation ratateAnimation = new RotateAnimation(0, 180,
		 * Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		 * scaleAnimation.setInterpolator(new
		 * AnticipateOvershootInterpolator());
		 * scaleAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		 * scaleAnimation.setFillAfter(true);
		 */

		// 动画设置
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);
		// set.addAnimation(ratateAnimation);
		set.addAnimation(translateAnimationX);
		set.addAnimation(translateAnimationY);
		set.setDuration(TIME_ANIMATION);// 动画的执行时间

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

	/**
	 * 
	 * Describe:创建动画层
	 * 
	 * Date:2015-11-3
	 * 
	 * Author:liuzhouliang
	 */
	@SuppressWarnings("ResourceType")
	private static ViewGroup createAnimLayout() {
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
	 * 创建动画布局
	 * 
	 * @return
	 */
	private static ViewGroup createDetailAnimLayout() {
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

	/**
	 * 
	 * Describe:重置动画视图的布局
	 * 
	 * Date:2015-11-3
	 * 
	 * Author:liuzhouliang
	 */
	private static View addViewToAnimLayout(final View view, int startX,
			int startY) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = startX;
		lp.topMargin = startY;
		view.setLayoutParams(lp);
		return view;
	}

	/**
	 * 设置商品宫格列表添加购物车抛物线动画效果
	 * 
	 * @param activity
	 * @param v
	 *            动画图标
	 * @param start_location
	 * @param shopCart
	 *            终点购物车图标
	 */
	public static void setGridAnimation(Activity activity, final View v,
			int[] start_location, View shopCart) {
		mActivity = activity;
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);// 把开始图标添加到动画层

		int startX = start_location[0];
		int startY = start_location[1];

		// 偏移量
		int offSet = ViewUtil.dip2px(activity, LOCATION_OFF_SET);
		int offSetColoum = ViewUtil.dip2px(activity,
				LOCATION_GRID_SECOND_OFFSET);

		// 设备屏幕宽度
		int deviceWidth = ViewUtil.getScreenWith(activity);

		final View view = addViewToAnimLayout(v, startX, startY);
		int[] end_location = new int[LOCATION_COUNT];// 这是用来存储动画结束位置的X、Y坐标
		shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int end_first_x = end_location[LOCATION_X_INDEX] + offSet;// 动画位移的X坐标
		int end_second_x = (int) (offSetColoum * (float) deviceWidth / STANDARD_WIDTH);// 动画位移的X坐标
		int endY = end_location[LOCATION_Y_INDEX] - startY - offSet;// 动画位移的y坐标

		/**
		 * 移动动画
		 */
		TranslateAnimation translateAnimationX;

		if (startX > deviceWidth / 2) {
			// 宫格列表第二列图标初始化
			translateAnimationX = new TranslateAnimation(0, end_second_x, 0, 0);
		} else {
			// 宫格列表第一列图标初始化
			translateAnimationX = new TranslateAnimation(0, end_first_x, 0, 0);
		}

		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY
				.setInterpolator(new AnticipateOvershootInterpolator());
		translateAnimationY.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		translateAnimationY.setFillAfter(true);

		/**
		 * 缩放动画
		 */
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 0f, 0.8f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		scaleAnimation.setFillAfter(true);

		/**
		 * 淡入淡出动画
		 */
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.3f);
		alphaAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		alphaAnimation.setFillAfter(true);

		// 动画设置
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);
		set.addAnimation(translateAnimationX);
		set.addAnimation(translateAnimationY);
		set.setDuration(TIME_ANIMATION);// 动画的执行时间

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

	public class ParabolaInterpolator implements Interpolator {

		private float tempT = 0.0f;

		public ParabolaInterpolator() {

		}

		@Override
		public float getInterpolation(float t) {
			if (t == 1.0f) {
				return t;
			} else if (t < 0.5f) {
				return (float) Math.pow(t, 2);
			} else if (t >= 0.5f && t <= 0.7f) {
				if (tempT == 0) {
					tempT = t * t;
				}
				return tempT;
			} else {
				return (float) Math.pow(t, 2);
			}
		}
	}

	/**
	 * 设置商品列表添加购物车抛物线动画效果
	 * 
	 * @param activity
	 * @param v
	 *            动画图标
	 * @param start_location
	 * @param shopCart
	 *            终点购物车图标
	 */
	public static void setDetailAnimation(Activity activity, final View v,int[] start_location,
			ImageView shopCart) {
		mActivity = activity;
		anim_detail_mask_layout = null;
		anim_detail_mask_layout = createAnimLayout();
		anim_detail_mask_layout.addView(v);// 把开始图标添加到动画层

		int deviceWidth = ViewUtil.getScreenWith(activity);
		int deviceHeight = ViewUtil.getScreenHeight(activity);

//		int startX = -ViewUtil.dip2px(activity, 13);
		int startX = start_location[0];
		int endX = deviceWidth;
//		int startY = ViewUtil.dip2px(activity, 160);
		int startY = start_location[1];
		int endY = deviceHeight;

		final View view = addViewToAnimLayout(v, startX, startY);
		int[] end_location = new int[LOCATION_COUNT];// 这是用来存储动画结束位置的X、Y坐标
		shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

		/**
		 * 移动动画
		 */
//		TranslateAnimation translateAnimationX = new TranslateAnimation(startX,
				TranslateAnimation translateAnimationX = new TranslateAnimation(0,
						(0.8f*end_location[0]), 0, 0);
		translateAnimationX.setInterpolator(new AccelerateInterpolator());
		translateAnimationX.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, 0.8f*end_location[1]-50);
		translateAnimationY
//				.setInterpolator(new AnticipateOvershootInterpolator());
		.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		translateAnimationY.setFillAfter(true);

		/**
		 * 缩放动画
		 */
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 0f, 0.6f, 0f,
				Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF,
				1f);
		scaleAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		scaleAnimation.setFillAfter(true);

		/**
		 * 淡入淡出动画
		 */
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.3f);
		alphaAnimation.setRepeatCount(REPEAT_COUNT);// 动画重复执行的次数
		alphaAnimation.setFillAfter(true);

		// 动画设置
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);
		set.addAnimation(translateAnimationX);
		set.addAnimation(translateAnimationY);
		set.setDuration(TIME_DETAIL_ANIMATION);// 动画的执行时间

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

	/**
	 * 设置商品列表添加购物车抛物线动画效果
	 * 
	 * @param activity
	 * @param v
	 *            动画图标
	 * @param start_location
	 * @param shopCart
	 *            终点购物车图标
	 */
	public static void setProductsDetailAnimation(Activity activity,
			final View v, View shopCart, int fromWidth, int fromHeight,
			int fromX, int fromY) {
		mActivity = activity;

		int[] end_location = new int[LOCATION_COUNT];// 这是用来存储动画结束位置的X、Y坐标
		shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

		int toX = end_location[0];
		int toY = end_location[1];

		float toXDelta = toX - fromX;
		float toYDelta = toY - fromY;

		toXDelta = toXDelta - (fromWidth - shopCart.getWidth()) / 2;
		toYDelta = toYDelta - (fromHeight - shopCart.getHeight()) / 2;

		// topX需小于1/2toXDelta
		// float topX = DETAIL_ROUND_SCALE * toXDelta;
		float topX = (0.3f) * toXDelta;
		ParabolaAnimation anim = new ParabolaAnimation(0, toXDelta, 0,
				toYDelta, topX);
		anim.setDuration(TIME_DETAIL_ANIMATION);
		anim.setFillAfter(true);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());

		ViewGroup animLayout = createDetailAnimLayout();
		final View mAnimView = addViewToAnimLayout(animLayout, v, fromWidth,
				fromHeight, fromX, fromY);
		animLayout.addView(mAnimView);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation anim) {

			}

			@Override
			public void onAnimationRepeat(Animation anim) {

			}

			@Override
			public void onAnimationEnd(Animation anim) {
				mAnimView.clearAnimation();
				mAnimView.setVisibility(View.GONE);
			}
		});
		mAnimView.startAnimation(anim);
	}

	private static View addViewToAnimLayout(ViewGroup vg, View v,
			int fromWidth, int fromHeight, int fromX, int fromY) {
		View animView = v;

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(fromWidth,
				fromHeight);
		lp.leftMargin = fromX;
		lp.topMargin = fromY;
		animView.setLayoutParams(lp);
		return animView;
	}

}
