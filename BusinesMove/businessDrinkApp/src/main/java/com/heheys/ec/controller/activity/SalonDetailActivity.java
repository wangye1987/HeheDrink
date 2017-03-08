package com.heheys.ec.controller.activity;

import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ScrollViewListView;
import com.heheys.ec.model.adapter.SalonCustomerListAdapter;
import com.heheys.ec.model.dataBean.MySalonDetailBean;
import com.heheys.ec.model.dataBean.MySalonDetailBean.MySalonDetailInfor.MySalonCustomerInfor;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:沙龙详情页面
 * 
 * Date:2015-10-18
 * 
 * Author:liuzhouliang
 */
public class SalonDetailActivity extends BaseActivity {
	private ScrollViewListView lv;
	private SalonCustomerListAdapter mAdapter;
	private static int id;
	private SalonDetailMessageHandler salonDetailMessageHandler;
	private MySalonDetailBean mySalonDetailBean;
	private List<MySalonCustomerInfor> salonCustomerInfors;
	private ImageView ivImageView;
	private TextView tvSubject, tvTimeStart, tvTimeEnd, tvPlace, tvNum,
			tvMobile, tvIntroduce;
	private Button btState;
	private LinearLayout llPhoneParent, customerParent;
	private LinearLayout linear_button;
	private String phoneNum;
	public static String userIdString;
	private String salonState;
	private boolean isPush;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.salon_detail);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		linear_button = (LinearLayout) findViewById(R.id.linear_button);
		customerParent = (LinearLayout) findViewById(R.id.salon_dedtail_customer_parent);
		llPhoneParent = (LinearLayout) findViewById(R.id.salon_detail_phone_parent);
		btState = (Button) findViewById(R.id.salon_detail_state);
		tvNum = (TextView) findViewById(R.id.salon_detail_num);
		tvPlace = (TextView) findViewById(R.id.salon_detail_place);
		tvTimeStart = (TextView) findViewById(R.id.salon_detail_time_start);
		tvTimeEnd = (TextView) findViewById(R.id.salon_detail_time_end);
		tvSubject = (TextView) findViewById(R.id.salon_detail_subject);
		ivImageView = (ImageView) findViewById(R.id.salon_detail_iv);
		lv = (ScrollViewListView) findViewById(R.id.salon_detail_lv);
		tvMobile = (TextView) findViewById(R.id.salon_detail_mobile);
		tvIntroduce = (TextView) findViewById(R.id.salon_detail_introduce);
		initData();
	}

	/**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void initData() {
		salonDetailMessageHandler = new SalonDetailMessageHandler(this);

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		salonState = getIntent().getStringExtra("state");
		id = getIntent().getIntExtra("id", 0);
		userIdString = getIntent().getStringExtra("userid");
		isPush = getIntent().getBooleanExtra("isPush", false);
		if(isPush)
			SharedPreferencesUtil.writeSharedPreferencesBoolean(this, "message", "new", false);
		
		 if(IsLogin.isLogin(this)){
		 ResultBean userInfoResultBean = (ResultBean) SharedPreferencesUtil
		 .getObject(baseActivity, "resultbean");
		 userIdString = userInfoResultBean.getId();
		 }
		ApiHttpCilent.getInstance(this).getMySalonDetail(this, id,
				userIdString, new SalonDetailRequestCallBack());
	}
	private void Dimess() {
		SalonDetailActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	/**
	 * 
	 * Describe:沙龙详情数据请求回调处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public class SalonDetailRequestCallBack extends
			BaseJsonHttpResponseHandler<MySalonDetailBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MySalonDetailBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			salonDetailMessageHandler.sendMessage(message);
		}

		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MySalonDetailBean arg3) {
		}

		@Override
		protected MySalonDetailBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			mySalonDetailBean = gson
					.fromJson(response, MySalonDetailBean.class);
			Message message = Message.obtain();
			if ("1".equals(mySalonDetailBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = mySalonDetailBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = mySalonDetailBean.getError().getInfo();
			}
			salonDetailMessageHandler.sendMessage(message);
			return mySalonDetailBean;
		}

	}

	/**
	 * 
	 * Describe:沙龙详情消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public static class SalonDetailMessageHandler extends
			WeakHandler<SalonDetailActivity> {

		public SalonDetailMessageHandler(SalonDetailActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				/**
				 * 处理成功的数据
				 */
				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
					
//					if(getReference().mySalonDetailBean.getError().getCode().equals("100")){
//						MyApplication.getInstance().startLogin(new LoginCallBack() {
//							
//							@Override
//							public void loginSuccess() {
//								// TODO Auto-generated method stub
//								ApiHttpCilent.getInstance(getReference()).getMySalonDetail(getReference(), id,
//										userIdString, getReference().new SalonDetailRequestCallBack());
//								
//							}
//							
//							@Override
//							public void loginFail() {
//								// TODO Auto-generated method stub
//								
//							}
//						}, getReference());
//					}
				} else {
					ToastUtil.showToast(getReference(), "请求失败");

				}

				break;
			}
		}

	}

	/**
	 * 
	 * Describe:绑定控件数据
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	@SuppressLint("NewApi")
	private void bindViewData() {

		phoneNum = mySalonDetailBean.getResult().getConsulmobile();
		List<String> picUrlStrings = mySalonDetailBean.getResult().getPiclist();
		String baseUrl = mySalonDetailBean.getResult().getBaseurl();
		if (!StringUtil.isEmpty(baseUrl)) {
			if (picUrlStrings != null && picUrlStrings.size() > 0) {
				MyApplication.imageLoader.displayImage(
						baseUrl + picUrlStrings.get(0), ivImageView,
						MyApplication.options, new ImageLoadingListener() {

							@Override
							public void onLoadingStarted(String imageUri,
									View view) {
							}

							@Override
							public void onLoadingFailed(String imageUri,
									View view, FailReason failReason) {
							}

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
							}

							@Override
							public void onLoadingCancelled(String imageUri,
									View view) {
							}
						});
			}
		}
		tvMobile.setText(mySalonDetailBean.getResult().getConsulmobile());
		tvIntroduce.setText(mySalonDetailBean.getResult().getSummary());
		tvTitleName.setText(mySalonDetailBean.getResult().getSubject());
		tvNum.setText(mySalonDetailBean.getResult().getMaxnum());
		tvSubject.setText(mySalonDetailBean.getResult().getSubject());
		tvTimeStart.setText(mySalonDetailBean.getResult().getStarttime());
		tvTimeEnd.setText(mySalonDetailBean.getResult().getEndtime());
		tvPlace.setText(mySalonDetailBean.getResult().getAddress());
		String signString = mySalonDetailBean.getResult().getSign();
		if ("0".equals(signString)) {
			/**
			 * 未报名
			 */
			customerParent.setVisibility(View.GONE);
			btState.setText("立即报名");
			btState.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Intent intent = new Intent(baseContext,
					// SalonJoinActivity.class);
					// intent.putExtra("sid", id);
					// intent.putExtra("FROM", 0);
					// StartActivityUtil.startActivityForResult(baseActivity,
					// intent, 1002);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("signup","");
					MobclickAgent.onEvent(baseContext, "0085", map); 
					if (!IsLogin.isLogin(baseActivity)) {

						/**
						 * 未登录
						 */
						MyApplication.getInstance().startLogin(
								new LoginCallBack() {

									@Override
									public void loginSuccess() {
										Intent intent = new Intent(baseContext,
												SalonJoinActivity.class);
										intent.putExtra("sid", id);
										intent.putExtra("FROM", 0);
										StartActivityUtil
												.startActivityForResult(
														baseActivity, intent,
														1002);
									}

									@Override
									public void loginFail() {
									}
								}, baseActivity);
					} else {
						/**
						 * 已经登陆
						 */
						Intent intent = new Intent(baseContext,
								SalonJoinActivity.class);
						intent.putExtra("sid", id);
						intent.putExtra("FROM", 0);
						StartActivityUtil.startActivityForResult(baseActivity,
								intent, 1002);
					}
				}
			});
			linear_button.setVisibility(View.VISIBLE);
		}
		if ("1".equals(signString)) {
			btState.setText("已报名");
			customerParent.setVisibility(View.VISIBLE);
			btState.setBackground(this.getResources().getDrawable(
					R.drawable.bg_gray_corner));
			/**
			 * 绑定客户列表数据
			 */
			salonCustomerInfors = mySalonDetailBean.getResult().getSigner();
			if (salonCustomerInfors != null && salonCustomerInfors.size() > 0) {
				mAdapter = new SalonCustomerListAdapter(salonCustomerInfors,
						this, id, userIdString);
				lv.setAdapter(mAdapter);
				customerParent.setVisibility(View.VISIBLE);
			} else {
				/**
				 * 报名嘉宾列表为空
				 */
				customerParent.setVisibility(View.GONE);
			}
			linear_button.setVisibility(View.VISIBLE);
		}
		if ("1".equals(salonState)) {
			/**
			 * 进行中
			 */
			btState.setVisibility(View.VISIBLE);
			linear_button.setVisibility(View.VISIBLE);
		}
		if ("2".equals(salonState)) {
			/**
			 * 已经结束
			 */
			btState.setVisibility(View.GONE);
			linear_button.setVisibility(View.GONE);
		}
		if ("3".equals(salonState)) {
			/**
			 * 已经报满
			 */
			btState.setVisibility(View.GONE);
			linear_button.setVisibility(View.GONE);
		}
		setViewListener();
	}

	/**
	 * 
	 * Describe:设置控件监听
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void setViewListener() {
		llPhoneParent.setOnClickListener(this);
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onClick(View v) {
//		super.onClick(v);
		switch (v.getId()) {
		case R.id.salon_detail_phone_parent:
			Intent intentCallPhone = new Intent(Intent.ACTION_CALL);
			Uri data = Uri.parse("tel:" + phoneNum);
			intentCallPhone.setData(data);
			startActivity(intentCallPhone);
			break;
		case R.id.base_activity_title_backicon:// 处理消息推送后退事件
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("salondetailback","");
			MobclickAgent.onEvent(baseContext, "0084", map); 
			if (isPush) {
				Intent i = new Intent(baseContext, SalonListActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("isPush", true);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				onBackPressed();
			}
			break;
		default:
			break;
		}
	}

	// 返回键处理 //处理消息推送后退事件
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isPush) {
				Intent i = new Intent(baseContext, SalonListActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				onBackPressed();
			}
		}
		return false;
	}

	/**
	 * 报名后刷新当前页面数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		super.onActivityResult(requestCode, resultCode, arg2);
		switch (requestCode) {
		case 1002:
			// ToastUtil.showToast(baseContext, "test");
			getNetData();
			break;
		default:
			break;
		}
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
