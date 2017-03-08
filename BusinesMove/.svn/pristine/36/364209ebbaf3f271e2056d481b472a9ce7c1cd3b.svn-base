package com.heheys.ec.view;

import com.heheys.ec.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * Describe:侧边栏显示字母控件
 * 
 * Date:2015-9-22
 * 
 * Author:liuzhouliang
 */
public class SideViewO extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 字符集合
	public static String[] charList = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "#" };
	// 记录选择的位置,-1表示未选中
	private int choosePosition = -1;
	private Paint paint = new Paint();
	// 显示提示的控件
	private TextView mTextDialog;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideViewO(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideViewO(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideViewO(Context context) {
		super(context);
	}

	/**
	 * 控制绘制的过程
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/**
		 * 获取视图宽、高
		 */
		int height = getHeight();
		int width = getWidth();
		// 获取每一个字母的高度
		int singleHeight = height / charList.length;
		/**
		 * 在画布上遍历绘制出当前显示的文本
		 */
		for (int i = 0; i < charList.length; i++) {
			// paint.setColor(Color.rgb(33, 65, 98));
//			paint.setColor(Color.parseColor("#69af05"));
//			paint.setColor(getResources().getColor(R.drawable.red));
			paint.setColor(Color.parseColor("red"));
			paint.setTypeface(Typeface.DEFAULT);
			paint.setAntiAlias(true);
			paint.setTextSize(30);
			/**
			 * 选中状态的处理，更改画笔的颜色
			 */
			if (i == choosePosition) {
				paint.setColor(Color.parseColor("#f0f0f0"));
				paint.setFakeBoldText(true);
			}
			/**
			 * 未选中状态的处理,绘制文本起点：x坐标，视图宽的的二分之一-文本宽的的二分之一;y坐标根据文本视图高度累加
			 */
			float xPos = width / 2 - paint.measureText(charList[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(charList[i], xPos, yPos, paint);
			// 重置画笔
			paint.reset();
		}

	}

	/**
	 * 处理屏幕事件的响应
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		// 获取Y点的坐标
		final float y = event.getY();
		final int oldChoose = choosePosition;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数
		final int position = (int) (y / getHeight() * charList.length);

		switch (action) {
		case MotionEvent.ACTION_UP:
			/**
			 * 松开的处理
			 */
			setBackgroundColor(this.getContext().getResources()
					.getColor(R.color.white));
			choosePosition = -1;
			invalidate();
			/**
			 * 隐藏提示视图
			 */
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			/**
			 * 非松开状态下的处理
			 */
			setBackgroundColor(this.getContext().getResources()
					.getColor(R.color.white));
			/**
			 * 触摸位置的更新回调监听接口,更新UI
			 */
			if (oldChoose != position) {
				if (position >= 0 && position < charList.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(charList[position]);
					}
					/**
					 * 显示提示视图
					 */
					if (mTextDialog != null) {
						mTextDialog.setText(charList[position]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					choosePosition = position;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 
	 * Describe:设置View触摸监听的方法
	 * 
	 * Date:2015-9-22
	 * 
	 * Author:liuzhouliang
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 
	 * Describe:定义字符触摸的监听接口
	 * 
	 * Date:2015-9-22
	 * 
	 * Author:liuzhouliang
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}