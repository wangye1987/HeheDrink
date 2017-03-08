package com.heheys.ec.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.view.ViewUtil;

import static com.heheys.ec.R.id.confirm_dialog_left_tv;

/**
 * Describe:自定义通用提示框
 * <p/>
 * Date:2015-7-8
 * <p/>
 * Author:liuzhouliang
 */
public class CommonDialog extends Dialog implements View.OnClickListener {

	private static CommonDialog dialog = null;
	private Context mContext = null;
	private String title = null;
	private String content = null;
	private OnDialogListener mOnDialogListener = null;
	/**
	 * 控件部分
	 */
	private LinearLayout llRightText = null;
	private LinearLayout linear_button = null;
	private TextView tvTitle = null;
	private TextView tvContent = null;
	private TextView tvRight = null;
	private TextView tvleft = null;
	private String left = null;
	private String right = null;
	private boolean isHideRightLayout;
	private TextView tvContent1 = null;
	private TextView tvContent2 = null;
	private TextView tvContent3 = null;
	private TextView tvContent4 = null;
	private TextView tvContent5 = null;
	private TextView tvContent6 = null;
	private String tel5;
	private String tel4;
	private String tel3;
	private String tel2;
	private String tel1;
	private static LinearLayout llParentLayout;
	private static BackGroundListener backGroundListener;

	private CommonDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * Describe:获取窗口实例
	 * <p/>
	 * Date:2015-7-8
	 * <p/>
	 * Author:liuzhouliang
	 */
	public static CommonDialog makeText(Context context, String title,
										String content, OnDialogListener onDialogListener) {
		dialog = new CommonDialog(context, R.style.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.mContext = context;
		dialog.title = title;
		dialog.content = content;
		dialog.setContentView(R.layout.confirm_dialog);
		View  view = LinearLayout.inflate(context,R.layout.confirm_dialog,null);
		int width = ViewUtil.getDisplayWidth((Activity) context);
		int height = ViewUtil.getDisplayHeight((Activity) context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width*3/5,height/4);
		view.setLayoutParams(lp);
//		dialog.getWindow().setLayout(width*3/5,height/4);
		dialog.mOnDialogListener = onDialogListener;
		return dialog;
	}
	public static CommonDialog makeTextCancle(Context context, String title,
										String content, String left,OnDialogListener onDialogListener) {
		dialog = new CommonDialog(context, R.style.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.mContext = context;
		dialog.title = title;
		dialog.left = left;
		dialog.content = content;
		View  view = LinearLayout.inflate(context,R.layout.confirm_dialog,null);
		dialog.setContentView(R.layout.confirm_dialog);
		int width = ViewUtil.getDisplayWidth((Activity) context);
		int height = ViewUtil.getDisplayHeight((Activity) context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width*3/5,height/4);
		view.setLayoutParams(lp);
//		dialog.getWindow().setLayout(width*3/5,height/4);
		dialog.mOnDialogListener = onDialogListener;
		return dialog;
	}
	public static void showAnimationView(LinearLayout mllParentLayout) {
		llParentLayout = mllParentLayout;
	}

	public static CommonDialog makeText(Context context, String content,
			OnDialogListener onDialogListener) {
		dialog = new CommonDialog(context, R.style.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.mContext = context;
		dialog.content = content;
		dialog.setContentView(R.layout.confirm_dialog);
		dialog.mOnDialogListener = onDialogListener;
		return dialog;
	}

	// dismess当前对话框
	public static void Dissmess() {
		if (dialog != null) {
			dialog.dismiss();
			dialog.cancel();
		}
	}

	/**
	 * Describe:设置左侧按钮文本
	 * <p/>
	 * Date:2015-7-8
	 * <p/>
	 * Author:liuzhouliang
	 */
	public void setLeftText(String text) {
		this.left = text;
	}

	/**
	 * Describe:设置右侧按钮文本
	 * <p/>
	 * Date:2015-7-8
	 * <p/>
	 * Author:liuzhouliang
	 */
	public void setRightText(String text) {
		this.right = text;
	}

	/**
	 * Describe:隐藏右侧按钮
	 * <p/>
	 * Date:2015-7-8
	 * <p/>
	 * Author:liuzhouliang
	 */
	public void setRightViewHide(boolean hideLayout) {

		isHideRightLayout = hideLayout;
	}

	/**
	 * Describe:显示对话框
	 * <p/>
	 * Date:2015-7-8
	 * <p/>
	 * Author:liuzhouliang
	 */
	public void showDialog() {
		loadViews();
		if (isHideRightLayout) {
			if (null != llRightText) {
				llRightText.setVisibility(View.GONE);
			}
		}
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.WindowAnimation);
		WindowManager.LayoutParams wm = window.getAttributes();
		wm.gravity = Gravity.CENTER;
		wm.width = ViewUtil.getScreenWith(mContext) * 11 / 12;
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	private void loadViews() {
		llRightText = (LinearLayout) findViewById(R.id.confirm_dialog_right_ll);
		linear_button = (LinearLayout) findViewById(R.id.linear_button);
		tvTitle = (TextView) findViewById(R.id.confirm_dialog_title_tv);
		tvContent1 = (TextView) findViewById(R.id.confirm_dialog_content_tv1);
		tvContent2 = (TextView) findViewById(R.id.confirm_dialog_content_tv2);
		tvContent3 = (TextView) findViewById(R.id.confirm_dialog_content_tv3);
		tvContent4 = (TextView) findViewById(R.id.confirm_dialog_content_tv4);
		tvContent5 = (TextView) findViewById(R.id.confirm_dialog_content_tv5);
		tvContent6 = (TextView) findViewById(R.id.confirm_dialog_content_tv6);
		tvleft = (TextView) findViewById(confirm_dialog_left_tv);
		tvRight = (TextView) findViewById(R.id.confirm_dialog_right_tv);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (backGroundListener != null) {
					backGroundListener.hideListener();
				}

			}
		});
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		dialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				if (backGroundListener != null) {
					backGroundListener.showListenr();
				}

			}
		});
		tvleft.setOnClickListener(this);
		tvRight.setOnClickListener(this);
//		tvContent1.setOnClickListener(this);
//		tvContent2.setOnClickListener(this);
//		tvContent3.setOnClickListener(this);
//		tvContent4.setOnClickListener(this);
//		tvContent5.setOnClickListener(this);
		String tel_array = this.content;
		// LinearLayout.LayoutParams lp = new
		// LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
		// lp.setMargins(0, 0, 0, 15);
		if (tel_array.contains(",")) {
			linear_button.setVisibility(View.GONE);
			tvTitle.setText("请选择拨打的电话?");
			String array[] = tel_array.split(",");
			for (int i = 0; i < array.length; i++) {
				if (i == 0) {
					tel1 = array[i];
					tvContent1.setText(array[i]);
				} else if (i == 1) {
					tel2 = array[i];
					tvContent2.setText(tel2);
					tvContent2.setVisibility(View.VISIBLE);
					// tvContent6.setVisibility(View.VISIBLE);
				} else if (i == 2) {
					tel3 = array[i];
					tvContent3.setText(tel3);
					tvContent3.setVisibility(View.VISIBLE);
					// tvContent6.setVisibility(View.VISIBLE);
				} else if (i == 3) {
					tel4 = array[i];
					tvContent4.setText(tel4);
					tvContent4.setVisibility(View.VISIBLE);
					// tvContent6.setVisibility(View.VISIBLE);
				} else if (i == 4) {
					tel5 = array[i];
					tvContent5.setText(tel5);
					tvContent5.setVisibility(View.VISIBLE);
					// tvContent6.setVisibility(View.VISIBLE);
				}
			}
		} else {
			linear_button.setVisibility(View.VISIBLE);
			tvTitle.setText("是否拨打电话?");
			tel1 = this.content;
			tvContent1.setText(tel1);
			// tvContent6.setVisibility(View.GONE);
		}
		if (this.right != null) {
			tvRight.setText(this.right);
		}
		if (this.left != null) {
			tvleft.setText(this.left);
		}
		if (this.title != null) {
			tvTitle.setText(this.title);
		}
	}

	/**
	 * 结果监听器
	 */
	public interface OnDialogListener {
		public static final int LEFT = 1;
		public static final int RIGHT = 0;

		public void onResult(int result, CommonDialog commonDialog, String tel);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.confirm_dialog_right_tv:
			if (CommonDialog.this.mOnDialogListener != null)
				CommonDialog.this.mOnDialogListener.onResult(
						OnDialogListener.RIGHT, CommonDialog.this,
						dialog.content);
			break;
		case confirm_dialog_left_tv:
			CallTel();
			break;
		case R.id.confirm_dialog_content_tv1:
			dialog.content = tel1;
			CallTel();
			break;
		case R.id.confirm_dialog_content_tv2:
			dialog.content = tel2;
			CallTel();
			break;
		case R.id.confirm_dialog_content_tv3:
			dialog.content = tel3;
			CallTel();
			break;
		case R.id.confirm_dialog_content_tv4:
			dialog.content = tel4;
			CallTel();
			break;
		case R.id.confirm_dialog_content_tv5:
			dialog.content = tel5;
			CallTel();

			break;
		default:
			break;
		}
	}

	private void CallTel() {
		if (CommonDialog.this.mOnDialogListener != null)
			CommonDialog.this.mOnDialogListener.onResult(OnDialogListener.LEFT,
					CommonDialog.this, dialog.content);
	}

	public interface BackGroundListener {
		public void showListenr();

		public void hideListener();

	}

	public static void setBackListener(BackGroundListener listener) {
		backGroundListener = listener;
	}

}
