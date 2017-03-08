package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.PushBaseBean;
import com.heheys.ec.model.dataBean.PushBaseBean.PushInfoBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ActivityManagerUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

//import com.umeng.update.UmengUpdateAgent;
//import com.umeng.update.UpdateResponse;

/**
 * 
 * Describe:升级页面
 * 
 * Date:2015-10-21
 * 
 * Author:liuzhouliang
 */
public class UpdateActivity extends Activity implements OnClickListener {
	private boolean isForceUpdate;
	private LinearLayout llLeft, llLRight;
	private TextView tvLeft, tvRight, tvContent;
//	private UpdateResponse response;
	private LinearLayout linear_close;
	//认证通过提示信息
	private LinearLayout linear_auth_success;
	//认证全部通过
	private LinearLayout title_linear;
	private LinearLayout linear_tel;
	//认证失败
//	private LinearLayout linear_auth_fail;
	private TextView tv_ts_title;
	private TextView tv_ts_news;
	private TextView tv_ts;
	private TextView tv_tips;
	
	private TextView tv_tq1;
	private TextView tv_tq2 ;
	private TextView tv_tq3;
	private PushBaseBean pushBaseBean;
	private MyMessageHandler messageHandler = new MyMessageHandler(this);
//	private TextView fail_reason;
	private TextView hehe_main;
	private TextView hehe_register;
	private String passWord;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		initDta();
	}

	/**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:liuzhouliang
	 */

	private void initDta() {
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		setContentView(R.layout.update_confirm_dialog);
		getChildView();
		// getIntentData();
		getIntentFrom();
		setViewListener();

	}

	@SuppressLint("NewApi") private void getIntentFrom() {
		passWord = SharedPreferencesUtil.getSharedPreferencesString(this,"passwordlogin", "passwordlogin", "");
		pushBaseBean = (PushBaseBean) getIntent().getSerializableExtra(
				"pushBaseBean");
		if (pushBaseBean != null && pushBaseBean.getResult() != null) {
			PushInfoBean pushInfoBean = pushBaseBean.getResult();
			if(pushInfoBean.getBiztype().equals("9") || pushInfoBean.getBiztype().equals("8")|| pushInfoBean.getBiztype().equals("0")){
				//8 新用户优惠券 9 认证通过 0 指定账户发优惠券
				title_linear.setVisibility(View.VISIBLE);
				linear_auth_success.setVisibility(View.GONE);
				tv_ts_news
					.setText(StringUtil.isEmpty(pushBaseBean.getResult()
							.getContent()) ? "" : pushBaseBean.getResult()
							.getContent());
				tv_ts_title.setText(StringUtil.isEmpty(pushBaseBean.getResult()
					.getTitle()) ? "" : pushBaseBean.getResult().getTitle());
				//获取基本信息 及时更新审核状态
				ApiHttpCilent.getInstance(getApplicationContext()).GetInfo(this, new MyCallBack());
		  }
//		  else if(pushInfoBean.getBiztype().equals("10")){
//			  //获取是否认证特权
//			  title_linear.setVisibility(View.GONE);
//			  linear_auth_success.setVisibility(View.VISIBLE);
//			  if(pushInfoBean.getMsgtips().getSuccess()){
//				  setLeftDraw(R.drawable.yes_auth ,tv_ts);
//			  }else{
//				  tv_tq1.setVisibility(View.INVISIBLE);
//				  tv_tq3.setVisibility(View.INVISIBLE);
//				  hehe_main.setBackground(getResources().getDrawable(R.drawable.gary_button));
//				  hehe_main.setTextColor(getResources().getColor(R.color.color_ffffff));
//				  setLeftDraw(R.drawable.register_fail ,tv_ts);
//			  }
//			  //tips
//			  tv_tips.setText(StringUtil.isEmpty(pushInfoBean.getMsgtips().getContent())?"":pushInfoBean.getMsgtips().getContent());
//			  //标题
//			  tv_ts.setText(StringUtil.isEmpty(pushInfoBean.getMsgtips().getTitle())?"":pushInfoBean.getMsgtips().getTitle());
//			  ArrayList<Msglist> listAuth = (ArrayList<Msglist>) pushInfoBean.getMsgtips().getMsglist();
//			  int size = listAuth.size();
//			  if(size == 3)
//				  linear_tel.setVisibility(View.GONE);
//			  for(int i=0;i<size;i++){
//				  if(i == 0){
//					  if(pushInfoBean.getMsgtips().getSuccess()){
//						  tv_tq1.setText(StringUtil.isEmpty(listAuth.get(i).getMsg())?"":listAuth.get(i).getMsg());
//						  if(listAuth.get(i).getSuccess()){
//							  setLeftDraw(R.drawable.yes_auth ,tv_tq1);
//						  }else{
//							  setLeftDraw(R.drawable.no_auth ,tv_tq1);
//						  }
//					  	}else{
//						  tv_tq2.setText(StringUtil.isEmpty(listAuth.get(i).getMsg())?"":listAuth.get(i).getMsg());
//					  }
//				  }
//				  if(i == 1){
//					  tv_tq2.setText(StringUtil.isEmpty(listAuth.get(i).getMsg())?"":listAuth.get(i).getMsg());
//					  if(listAuth.get(i).getSuccess())
//						  setLeftDraw(R.drawable.yes_auth ,tv_tq2);
//					  else
//						  setLeftDraw(R.drawable.no_auth ,tv_tq2);
//				  }
//				  if(i == 2){
//					  tv_tq3.setText(StringUtil.isEmpty(listAuth.get(i).getMsg())?"":listAuth.get(i).getMsg());
//					  if(listAuth.get(i).getSuccess())
//						  setLeftDraw(R.drawable.yes_auth ,tv_tq3);
//					  else
//						  setLeftDraw(R.drawable.no_auth ,tv_tq3);
//				  }
//			  }
//		  }
		}
	}

	
	void setLeftDraw(int drawables,TextView tv){
		Drawable drawable= getResources().getDrawable(drawables);  
		/// 这一步必须要做,否则不会显示.  
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
		tv.setCompoundDrawables(drawable,null,null,null);  
	}
	public static class MyMessageHandler extends WeakHandler<UpdateActivity> {

		public MyMessageHandler(UpdateActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				//获取基本信息成功
				break;
			case ConstantsUtil.HTTP_FAILE:
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					ToastUtil.showToast(getReference(), "请求失败");
				}
				break;
			}
		}

	}

	private void Dimessis() {
		UpdateActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (UpdateActivity.this != null
						&& !UpdateActivity.this.isFinishing()) {
					if (ApiHttpCilent.loading != null
							&& ApiHttpCilent.loading.isShowing())
						ApiHttpCilent.loading.dismiss();
				}
			}
		});
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<BaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimessis();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Dimessis();
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimessis();
			Gson gson = new Gson();
			BaseBean loginBean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(loginBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = loginBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = loginBean.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return loginBean;
		}
	}

	/**
	 * 
	 * Describe:获取控件
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:liuzhouliang
	 */
	private void getChildView() {
		tvRight = (TextView) findViewById(R.id.confirm_dialog_right_tv);
		tvLeft = (TextView) findViewById(R.id.confirm_dialog_left_tv);
		tvContent = (TextView) findViewById(R.id.confirm_dialog_content_tv);
		llLeft = (LinearLayout) findViewById(R.id.confirm_dialog_left_ll);
		llLRight = (LinearLayout) findViewById(R.id.confirm_dialog_right_ll);
		
		
		linear_tel = (LinearLayout) findViewById(R.id.linear_tel);
		linear_close = (LinearLayout) findViewById(R.id.linear_close);
		title_linear = (LinearLayout) findViewById(R.id.title_linear);
		tv_ts_title = (TextView) findViewById(R.id.tv_ts_title);
		tv_ts_news = (TextView) findViewById(R.id.tv_ts_news);
		tv_ts = (TextView) findViewById(R.id.tv_ts);
		tv_tips = (TextView) findViewById(R.id.tv_tips);
		linear_auth_success = (LinearLayout) findViewById(R.id.linear_auth_success);
		tv_tq1 = (TextView) findViewById(R.id.tv_tq1);
		tv_tq2 = (TextView) findViewById(R.id.tv_tq2);
		tv_tq3 = (TextView) findViewById(R.id.tv_tq3);
		hehe_register = (TextView) findViewById(R.id.hehe_register);
		hehe_main = (TextView) findViewById(R.id.hehe_main);
//		
//		linear_auth_fail = (LinearLayout) findViewById(R.id.linear_auth_fail);
//		//失败原因
//		fail_reason = (TextView) findViewById(R.id.fail_reason);
		hehe_main.setOnClickListener(this);
		hehe_register.setOnClickListener(this);
		linear_close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 
	 * Describe:获取回传参数
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:liuzhouliang
	 */
//	private void getIntentData() {
//		isForceUpdate = getIntent().getBooleanExtra(
//				ConstantsUtil.IS_FORCE_UPDATE_KEY, false);
//		response = (UpdateResponse) getIntent().getSerializableExtra(
//				ConstantsUtil.UPDATE_DATA_KEY);
//		tvContent.setText(response.updateLog);
//		tvLeft.setText("升级");
//		if (isForceUpdate) {
//			/**
//			 * 强制升级
//			 */
//			tvRight.setText("退出");
//		} else {
//			/**
//			 * 非强制升级
//			 */
//			tvRight.setText("取消");
//		}
//	}

	/**
	 * 
	 * Describe:设置控件监听
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:liuzhouliang
	 */
	private void setViewListener() {
		llLeft.setOnClickListener(this);
		llLRight.setOnClickListener(this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			setIntent(intent);
		}
//		getIntentData();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& isOutOfBounds(this, event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 
	 * Describe:处理空白区域触摸事件
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:liuzhouliang
	 */
	private boolean isOutOfBounds(Activity context, MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(context)
				.getScaledWindowTouchSlop();
		final View decorView = context.getWindow().getDecorView();
		return (x < -slop) || (y < -slop)
				|| (x > (decorView.getWidth() + slop))
				|| (y > (decorView.getHeight() + slop));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.confirm_dialog_left_ll:
			/**
			 * 左侧控件事件:响应下载
			 */
//			UmengUpdateAgent.startDownload(this, response);
			break;

		case R.id.confirm_dialog_right_ll:
			/**
			 * 右侧视图事件
			 */
			if (isForceUpdate) {
				/**
				 * 强制升级:退出程序
				 */
				finish();
				ActivityManagerUtil.getActivityManager().exitApp(this);
			} else {
				/**
				 * 非强制升级：隐藏窗口
				 */
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
				finish();
			}
			break;
		case R.id.hehe_register:
			
			//审核失败 重新提交资料
			Intent intent = new Intent();
			intent.putExtra("isRegister", false);
			intent.putExtra("pwd_two", passWord);
			intent.setClass(this,AuthActivity.class);
			StartActivityUtil.startActivity(this, intent);
			finish();
			break;
		case R.id.hehe_main:
			//审核失败 暂时不管
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
