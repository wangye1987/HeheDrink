package com.heheys.ec.controller.activity;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.SalonCustomerDetailBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:沙龙客户页面
 * 
 * Date:2015-10-18
 * 
 * Author:liuzhouliang
 */
public class SalonCustomerDetail extends BaseActivity {
	private String uid, sid;
	private TextView nameTextView, positionTextView, companyTextView,
			phoneTextView, weixinTextView;
	private SalonCustomerDetailBean salonCustomerDetailBean;
	private BaseBean dataResultBaseBean;
	private Button exchangeState;

	private CustomerMessageHandler messageHandler;
	private CustomerContactMessageHandler contactMessageHandler;
	public CheckType detailType;
	private boolean isPush;

	public enum CheckType {
		AGREE_EXCHANGE, EXCHANGE
	}

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.salon_customer_detail);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		nameTextView = (TextView) findViewById(R.id.salon_customer_name);
		positionTextView = (TextView) findViewById(R.id.salon_customer_position);
		exchangeState = (Button) findViewById(R.id.salon_customer_state);
		companyTextView = (TextView) findViewById(R.id.salon_customer_company);
		phoneTextView = (TextView) findViewById(R.id.salon_customer_phone);
		weixinTextView = (TextView) findViewById(R.id.salon_customer_weixin);
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
		contactMessageHandler = new CustomerContactMessageHandler(this);
		messageHandler = new CustomerMessageHandler(this);

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		sid = getIntent().getStringExtra("sid");
		uid = getIntent().getStringExtra("uid");
		isPush = getIntent().getBooleanExtra("isPush", false);
//		ToastUtil.showToast(baseActivity, "uid=" + uid + "sid==" + sid);
		ApiHttpCilent.getInstance(this).salonCustomerDetail(this, uid, sid,
				new CustomerDetailRequestCallBack());
	}
	private void Dimess() {
		SalonCustomerDetail.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	/**
	 * 
	 * Describe:客户详情数据回调
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public class CustomerDetailRequestCallBack extends
			BaseJsonHttpResponseHandler<SalonCustomerDetailBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, SalonCustomerDetailBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				SalonCustomerDetailBean arg3) {
			Dimess();
		}

		@Override
		protected SalonCustomerDetailBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dimess();
			Gson gson = new Gson();
			salonCustomerDetailBean = gson.fromJson(response,
					SalonCustomerDetailBean.class);
			Message message = Message.obtain();
			if ("1".equals(salonCustomerDetailBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = salonCustomerDetailBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = salonCustomerDetailBean.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return salonCustomerDetailBean;
		}

	}

	/**
	 * 
	 * Describe:沙龙顾客详情消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public static class CustomerMessageHandler extends
			WeakHandler<SalonCustomerDetail> {

		public CustomerMessageHandler(SalonCustomerDetail reference) {
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
	@SuppressLint("NewApi") private void bindViewData() {
		nameTextView.setText(salonCustomerDetailBean.getResult().getName());
		positionTextView.setText(salonCustomerDetailBean.getResult()
				.getPosition());
		companyTextView.setText(salonCustomerDetailBean.getResult()
				.getCompany());
		phoneTextView.setText(salonCustomerDetailBean.getResult().getMobile());
		weixinTextView.setText(salonCustomerDetailBean.getResult().getWeixin());
		// 0 未交换联系方式 1 已发送 2 已交换 5 对方发送交换请求 6 本人
		String stateString = salonCustomerDetailBean.getResult().getStatus();
		// if ("0".equals(stateString)) {
		// exchangeState.setText("交换联系方式");
		// exchangeState.setTextColor(baseActivity.getResources().getColor(
		// R.color.white));
		// exchangeState.setBackground(baseActivity
		// .getDrawable(R.drawable.sharp_round));
		// exchangeState.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// detailType = CheckType.EXCHANGE;
		// exchangeContact(uid);
		// }
		// });
		// }
		if ("1".equals(stateString)) {
			exchangeState.setText("已发送");
			exchangeState.setTextColor(getResources()
					.getColor(R.color.title_bg));
			exchangeState.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
		} else if ("2".equals(stateString)) {
			exchangeState.setText("已交换");
			exchangeState.setTextColor(getResources()
					.getColor(R.color.title_bg));
			exchangeState.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
		} else if ("5".equals(stateString)) {
			exchangeState.setText("同意交换");
			exchangeState.setTextColor(getResources().getColor(R.color.white));
			exchangeState.setBackground(getResources().getDrawable(R.drawable.sharp_round));
			exchangeState.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					detailType = CheckType.AGREE_EXCHANGE;
					agreeExchangeContact(uid);
				}
			});
		} else {
			exchangeState.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * Describe:同意交换联系方式
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void agreeExchangeContact(String uid) {
		ApiHttpCilent.getInstance(this).agreeExchangeContanct(this, uid,
				sid + "", new ContactRequestCallBack());
	}

	/**
	 * 
	 * Describe:请求交换联系方式
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void exchangeContact(String uid) {
		ApiHttpCilent.getInstance(this).exchangeContanct(this, uid, sid + "",
				new ContactRequestCallBack());
	}

	/**
	 * 
	 * Describe:名片请求返回
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public class ContactRequestCallBack extends
			BaseJsonHttpResponseHandler<BaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			contactMessageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			dataResultBaseBean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(dataResultBaseBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = dataResultBaseBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = dataResultBaseBean.getError().getInfo();
			}
			contactMessageHandler.sendMessage(message);
			return dataResultBaseBean;
		}

	}

	/**
	 * 
	 * Describe：请求名片消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public static class CustomerContactMessageHandler extends
			WeakHandler<SalonCustomerDetail> {

		public CustomerContactMessageHandler(SalonCustomerDetail reference) {
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
				switch (getReference().detailType) {
				case AGREE_EXCHANGE:
					/**
					 * 同意交换名片
					 */
					ToastUtil.showToast(getReference(), "同意交换名片成功");
					getReference().exchangeState.setText("已发送");
					getReference().exchangeState.setTextColor(getReference()
							.getResources().getColor(R.color.title_bg));
					getReference().exchangeState
							.setBackgroundColor(getReference().getResources()
									.getColor(android.R.color.transparent));
					getReference().exchangeState.setOnClickListener(null);
					break;
				case EXCHANGE:
					/**
					 * 交换名片
					 */
					ToastUtil.showToast(getReference(), "交换名片成功");
					getReference().exchangeState.setText("已交换");
					getReference().exchangeState.setTextColor(getReference()
							.getResources().getColor(R.color.title_bg));
					getReference().exchangeState
							.setBackgroundColor(getReference().getResources()
									.getColor(android.R.color.transparent));
					getReference().exchangeState.setOnClickListener(null);
					break;
				default:
					break;
				}
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
				} else {
					switch (getReference().detailType) {
					case AGREE_EXCHANGE:
						/**
						 * 同意交换名片
						 */
						ToastUtil.showToast(getReference(), "同意交换名片失败");
						break;
					case EXCHANGE:
						/**
						 * 交换名片
						 */
						ToastUtil.showToast(getReference(), "交换名片失败");
						break;
					default:
						break;
					}

				}

				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
		if(isPush){
			Intent i = new Intent(baseContext,SalonListActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("isPush", true);
			StartActivityUtil.startActivity(baseActivity, i);
			finish();
		}else{
			onBackPressed();
		}
		break;
		}
	}
	
	//返回键处理
	public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	if(isPush){
				Intent i = new Intent(baseContext,SalonListActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			}else{
				onBackPressed();
			}
        }  
        return false;  
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
		return "嘉宾";
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

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
