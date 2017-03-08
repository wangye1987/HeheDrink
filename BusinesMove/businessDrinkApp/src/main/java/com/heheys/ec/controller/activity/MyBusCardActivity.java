package com.heheys.ec.controller.activity;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.AddbusCardBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.MyCardBaseBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-17 下午4:32:31 类说明
 * @param 我的名片详情
 */
public class MyBusCardActivity extends BaseActivity {

	private MyCardHandler myHandler;
	private MyCardBaseBean bean;
	boolean isEdit = true;
	private LinearLayout my_visible;
	private LinearLayout my_linear_card;
	private TextView my_name;
	private TextView my_tel;
	private TextView my_landline;
	private TextView my_weixin;
	private TextView my_company;
	private TextView my_position;
	private TextView my_address;
	private TextView my_remark;
	private LinearLayout my_linear_landline;
	private TextView my_create_card;
	private TextView my_card_status;
	private boolean isPush;
	HashMap<String,String> map = new HashMap<String,String>();
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.mycard_detail);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		my_name = (TextView) findViewById(R.id.card_name);
		my_position = (TextView) findViewById(R.id.card_position);
		my_company = (TextView) findViewById(R.id.card_company);
		my_tel= (TextView) findViewById(R.id.card_tel);
		my_weixin = (TextView) findViewById(R.id.card_weixin);
		my_address = (TextView) findViewById(R.id.card_address);
		my_remark = (TextView) findViewById(R.id.card_remark);
		my_landline = (TextView) findViewById(R.id.card_landline);
		my_visible = (LinearLayout) findViewById(R.id.base_activity_no_businesscard);
		my_create_card = (TextView) findViewById(R.id.create_card);
		my_card_status = (TextView) findViewById(R.id.card_status);
		my_linear_card = (LinearLayout) findViewById(R.id.linear_card);
		my_linear_landline = (LinearLayout) findViewById(R.id.linear_landline);
		myHandler = new MyCardHandler(this);

	}

	@Override
	protected void getNetData() {
		isPush = getIntent().getBooleanExtra("isPush", false);
//		if(isPush)
//			SharedPreferencesUtil.writeSharedPreferencesBoolean(this, "message", "new", false);
		if(ToastNoNetWork.ToastError(baseActivity)){
			return;
		}
		ApiHttpCilent.getInstance(getApplicationContext()).MyBusinessCard(baseActivity,
				new CallBackBusinessCardMy());
	}

	public static class MyCardHandler extends WeakHandler<MyBusCardActivity> {

		public MyCardHandler(MyBusCardActivity reference) {
			super(reference);
		}

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 获得正常数据
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindDate();
				break;
			// 获得异常数据
			case ConstantsUtil.HTTP_FAILE:
//				if ("100".equals(getReference().bean.getError().getCode())) {
//					MyApplication.getInstance().startLogin(new LoginCallBack() {
//						@Override
//						public void loginSuccess() {
//							Intent intentbusinessCard = new Intent(
//									getReference(), MyBusCardActivity.class);
//							StartActivityUtil.startActivity(getReference(),
//									intentbusinessCard);
//						}
//
//						@Override
//						public void loginFail() {
//						}
//					}, getReference());
//				} else {
					ErrorBean back = (ErrorBean) msg.obj;
					ToastUtil.showToast(getReference().getBaseContext(),
							back.toString());
//				}
				break;
			default:
				break;
			}

		}
	}

	private void bindDate() {
		// 绑定数据
		if (bean.getResult() != null) {
			if (bean.getResult().getId() != 0) {
				AddbusCardBean data = bean.getResult();
				my_name.setText(data.getName());
				String sttel = data.getMobile();
				if(sttel.contains(",")){
					String tel[] = sttel.split(",");
					if(tel.length>2){
						my_tel.setText(sttel.substring(0, sttel.lastIndexOf(","))+"\n"+sttel.substring(sttel.lastIndexOf(",")+1,sttel.length()));
					}else{
						my_tel.setText(sttel);
					}
				}else{
					my_tel.setText(sttel);
				}
				String stland = data.getLandline();
				if(stland.equals("") || stland==null){
					my_linear_landline.setVisibility(View.GONE);
				}else{
					my_linear_landline.setVisibility(View.VISIBLE);
					if(stland.contains(",")){
						String landline[] = stland.split(",");
						if(landline.length>2){
							my_landline.setText(stland.substring(0, stland.lastIndexOf(","))+"\n"+stland.substring(stland.lastIndexOf(",")+1,stland.length()));
						}else{
							my_landline.setText(stland);
						}
					}else{
						my_landline.setText(stland);
					}
				}
				my_weixin.setText(data.getWeixin().toString());
				my_company.setText(data.getCompany());
				my_position.setText(data.getPosition());
				my_address.setText(data.getAddress());
				tvRight.setText("编辑");
				tvRight.setVisibility(View.VISIBLE);
				my_visible.setVisibility(View.GONE);
				my_linear_card.setVisibility(View.VISIBLE);
				if("1".equals(data.getStatus())){
					my_card_status.setText("");
				}else if("2".equals(data.getStatus()) || "3".equals(data.getStatus())){
					if("2".equals(data.getStatus()))
						   my_card_status.setText("审核未通过");
					else
						   my_card_status.setText("名片待审核");
				}else{
					tvRight.setVisibility(View.INVISIBLE);
					ToastUtil.showToast(getBaseContext(), "数据异常,请稍后重试");
				}
			} else {
				my_visible.setVisibility(View.VISIBLE);
				my_linear_card.setVisibility(View.GONE);
			}
		} else {
			ToastUtil.showToast(getBaseContext(), "数据异常,请重试");
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.base_activity_title_right_righttv:
			map.put("View_card_edit","");
			MobclickAgent.onEvent(baseActivity, "View_card_edit", map);
			Intent intent = new Intent(this, AddBusinessCardActivity.class);
			intent.putExtra("bean", bean);
			StartActivityUtil.startActivityForResult(this, intent,
					ConstantsUtil.ACTIVITY_BACK);
			break;
		case R.id.create_card:
			StartActivityUtil.startActivityForResult(this,
					AddBusinessCardActivity.class, ConstantsUtil.ACTIVITY_BACK);
			break;
		case R.id.base_activity_title_backicon:// 处理消息推送后退事件
			if (isPush) {
				Intent i = new Intent(baseContext, IdCardManagerActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("isPush", true);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				map.put("View_card_return","");
				MobclickAgent.onEvent(baseActivity, "View_card_return", map);
				onBackPressed();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestcode, int resultcode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestcode, resultcode, intent);
		switch (requestcode) {
		case ConstantsUtil.ACTIVITY_BACK:
			getNetData();
			break;

		default:
			break;
		}
	}

	// 隐藏等待框
	private void Dismess() {
		MyBusCardActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	private class CallBackBusinessCardMy extends
			BaseJsonHttpResponseHandler<MyCardBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MyCardBaseBean arg4) {
			// TODO Auto-generated method stub
			Dismess();

		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MyCardBaseBean bean) {
			// TODO Auto-generated method stub
			Dismess();
		}

		@Override
		protected MyCardBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dismess();
			Gson gson = new Gson();
			bean = gson.fromJson(response, MyCardBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = bean.getError();
			}
			myHandler.sendMessage(message);
			return bean;
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		tvRight.setOnClickListener(this);
		my_create_card.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "查看个人名片";
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
	// 返回键处理 //处理消息推送后退事件
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (isPush) {
					Intent i = new Intent(baseContext, IdCardManagerActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					StartActivityUtil.startActivity(baseActivity, i);
					finish();
				} else {
					onBackPressed();
				}
			}
			return false;
		}
		 public void onResume() {
			    super.onResume();
			    MobclickAgent.onResume(this);       //统计时长
			}
			public void onPause() {
			    super.onPause();
			    MobclickAgent.onPause(this);
			}
}