package com.heheys.ec.view;

import com.heheys.ec.utils.HtmlUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * Describe:带倒计时的TextView
 * 
 * Date:2015-9-28
 * 
 * Author:liuzhouliang
 */
public class TimeCutDownTextView extends TextView implements Runnable {

	Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息

	private boolean run = false; // 是否启动了
	private boolean isActivityStart = true; // 活动是否开始了 默认促销活动开始

	private long days; // 天
	private long diffTime; // 倒计时时间差
	private long diffStartTime; // 活动刚开始倒计时到活动结束时间差(用于开始前倒计时到开始倒计时的时间设置)
	private long hours; // 小时
	private long minutes; // 分
	private long seconds; // 秒

	public TimeCutDownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
	}

	public TimeCutDownTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
//		TypedArray array = context.obtainStyledAttributes(attrs,
//				R.styleable.TimeCutDownTextView);
//
//		array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
	}

	public TimeCutDownTextView(Context context) {
		super(context);
	}

	public void seDiffTime(long diffTime) {
		this.diffTime = diffTime;
	}

	/**
	 * 用于活动开始前设置，设置两个时间，距离活动开始时间差和活动开始到结束剩余时间差
	 * 
	 * @param diffTime
	 */
	public void seDiffStartTime(long diffStartTime) {
		this.diffStartTime = diffStartTime;
	}

	public long getDiffTime() {
		return diffTime;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public boolean isActivityStart() {
		return isActivityStart;
	}

	public void setActivityStart(boolean isActivityStart) {
		this.isActivityStart = isActivityStart;
	}

	@Override
	public void run() {
		// 标示已经启动
		run = true;
		if (isActivityStart) {
			setRunningTimeText(diffTime);
		} else {
			if (diffTime < 0) {
				setBeforeTimeText(diffStartTime);
			} else {
				setBeforeTimeText(diffTime);
			}
		}

		diffTime = diffTime - 1000;

		if (diffTime > 0) {
			postDelayed(this, 1000);
		} else {
			if (isActivityStart) {
				this.setVisibility(View.GONE);
				run = false;
				return;
			} else {
				isActivityStart = true;
				diffTime = diffStartTime;
				postDelayed(this, 1000);
			}
		}
	}

	/**
	 * 设置活动进行时要显示的剩余时间
	 */
	public void setRunningTimeText(Long diffTime) {
		String cutDownTime;
		if (null == diffTime) {
			this.setText("");
			return;
		}

		days = diffTime / (1000 * 60 * 60 * 24);
		hours = (diffTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		minutes = (diffTime - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60))
				/ (1000 * 60);
		seconds = (diffTime - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);

		/*
		 * Log.d("wdtest",
		 * "setRunningTimeText-------------------------- days = " + days +
		 * " hours = " + hours + " minutes = " + minutes + " seconds = " +
		 * seconds + " productId = " + productId);
		 */

		cutDownTime = "剩余" + HtmlUtil.TEXT_RED_HTML_FONT_LEFT + days
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "天"
				+ HtmlUtil.TEXT_RED_HTML_FONT_LEFT + hours
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "小时"
				+ HtmlUtil.TEXT_RED_HTML_FONT_LEFT + minutes
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "分"
				+ HtmlUtil.TEXT_RED_HTML_FONT_LEFT + seconds
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "秒";

		this.setText(Html.fromHtml(cutDownTime));
	}

	/**
	 * 获得活动进行时要显示的剩余时间
	 */
	public void setBeforeTimeText(Long diffTime) {
		days = diffTime / (1000 * 60 * 60 * 24);
		hours = (diffTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		minutes = (diffTime - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60))
				/ (1000 * 60);
		seconds = (diffTime - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);

		Log.d("wdtest", "getBeforeShowTime-------------------------- days = "
				+ days + " hours = " + hours + " minutes = " + minutes
				+ " seconds = " + seconds);

		String cutDownTime = "距开始" + HtmlUtil.TEXT_RED_HTML_FONT_LEFT + days
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "天"
				+ HtmlUtil.TEXT_RED_HTML_FONT_LEFT + hours
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "小时"
				+ HtmlUtil.TEXT_RED_HTML_FONT_LEFT + minutes
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "分"
				+ HtmlUtil.TEXT_RED_HTML_FONT_LEFT + seconds
				+ HtmlUtil.TEXT_HTML_FONT_RIGHT + "秒";

		this.setText(Html.fromHtml(cutDownTime));
	}

}
