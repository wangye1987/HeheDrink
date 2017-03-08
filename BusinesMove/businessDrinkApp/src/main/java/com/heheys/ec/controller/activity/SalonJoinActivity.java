package com.heheys.ec.controller.activity;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
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
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.MyCardBaseBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:沙龙报名页面
 * 
 * Date:2015-9-27
 * 
 * Author:liuzhouliang
 */
public class SalonJoinActivity extends BaseActivity {

	private int sid;// 报名沙龙ID
	private Button commit;
	private JoinSalonHandler joinSalonHandler;
	private BaseBean joinSalonBean;
	private int fromType;
	private TextView my_name;
	private TextView my_tel;
	private TextView my_landline;
	private TextView my_weixin;
	private TextView my_company;
	private TextView my_position;
	private TextView my_address;
	private TextView my_remark;
	private MyCardBaseBean bean;
	private LinearLayout my_linear_landline;
	private TextView my_create_card;
	private TextView my_card_status;
	private LinearLayout my_linear_card;
	private LinearLayout my_visible;
	private int flag;
	private TextView my_tv_notice;
	@Override
	protected void onCreate() {
		setBaseContentView(R.layout.salon_join);

	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadChildView() {
		my_name = (TextView) findViewById(R.id.card_name);
		my_position = (TextView) findViewById(R.id.card_position);
		my_company = (TextView) findViewById(R.id.card_company);
		my_tel= (TextView) findViewById(R.id.card_tel);
		my_landline = (TextView) findViewById(R.id.card_landline);
		my_weixin = (TextView) findViewById(R.id.card_weixin);
		my_address = (TextView) findViewById(R.id.card_address);
		my_remark = (TextView) findViewById(R.id.card_remark);
		my_tv_notice = (TextView) findViewById(R.id.tv_notice);
		my_create_card = (TextView) findViewById(R.id.create_card);
		my_card_status = (TextView) findViewById(R.id.card_status);
		my_linear_card = (LinearLayout) findViewById(R.id.linear_card);
		my_linear_landline = (LinearLayout) findViewById(R.id.linear_landline);
		my_visible = (LinearLayout) findViewById(R.id.base_activity_no_businesscard);
		my_visible.setBackgroundColor(getResources().getColor(R.color.color_f5f4f4));
		commit = (Button) findViewById(R.id.commit);
		my_tv_notice.setText("只有创建个人名片才能报名哦!");
		my_create_card.setText("创建个人名片");
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
		// TODO Auto-generated method stub
		joinSalonHandler = new JoinSalonHandler(this);
		Intent intent = getIntent();
		if (intent != null) {
			sid = intent.getIntExtra("sid", 0);
			fromType = intent.getIntExtra("FROM", -1);
		}
	}

	@Override
	protected void getNetData() {
			ApiHttpCilent.getInstance(baseActivity).MyBusinessCard(baseActivity,
					new CallBackBusinessCardMy());

	}
	// 隐藏等待框
	private void Dismess() {
		SalonJoinActivity.this.runOnUiThread(new Runnable() {
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
			if ("1".equals(bean.getStatus())) {// 正常获取个人名片
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult();
			}
			else if("0".equals(bean.getStatus())){
				message.what = ConstantsUtil.HTTP_FAILE;// 错误获取个人名片
				message.obj = bean.getError();
			}
			joinSalonHandler.sendMessage(message);
			return bean;
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
				my_visible.setVisibility(View.GONE);
				my_linear_card.setVisibility(View.VISIBLE);
				commit.setVisibility(View.VISIBLE);
				//"审核状态"1通过2未通过3待审核
				tvRight.setVisibility(View.VISIBLE);
				if("1".equals(data.getStatus())){
					commit.setEnabled(true);
					commit.setClickable(true);
					my_card_status.setText("");
				}else if("2".equals(data.getStatus()) || "3".equals(data.getStatus())){
					if("2".equals(data.getStatus()))
						   my_card_status.setText("审核未通过");
					else
						   my_card_status.setText("名片待审核");
					commit.setEnabled(false);
					commit.setClickable(false);
					commit.setBackgroundResource(R.drawable.verifystatus_button);
				}else{
					tvRight.setVisibility(View.INVISIBLE);
					ToastUtil.showToast(getBaseContext(), "数据异常,请稍后重试");
				}
			} else {
				my_visible.setVisibility(View.VISIBLE);
				my_linear_card.setVisibility(View.GONE);
				commit.setVisibility(View.GONE);
				tvRight.setVisibility(View.INVISIBLE);
			}
		} else {
			my_visible.setVisibility(View.VISIBLE);
			my_linear_card.setVisibility(View.GONE);
			commit.setVisibility(View.GONE);
			tvRight.setVisibility(View.INVISIBLE);
			commit.setEnabled(false);
			commit.setClickable(false);
			ToastUtil.showToast(getBaseContext(), "数据异常,请重试");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.commit:
			map.put("signup","");
			MobclickAgent.onEvent(baseActivity, "0090", map); 
			joinSalonRequest();
			break;
		case R.id.create_card:
			map.put("Sharon_sign_up_create_card","");
			MobclickAgent.onEvent(baseActivity, "Sharon_sign_up_create_card", map); 
			StartActivityUtil.startActivityForResult(this,
					AddBusinessCardActivity.class, ConstantsUtil.ACTIVITY_BACK);
			break;
		case R.id.base_activity_title_right_righttv:
			map.put("Sharon_sign_up_modify_card","");
			MobclickAgent.onEvent(baseActivity, "Sharon_sign_up_modify_card", map); 
			Intent intent = new Intent(this, AddBusinessCardActivity.class);
			intent.putExtra("bean", bean);
			StartActivityUtil.startActivityForResult(this, intent,
					ConstantsUtil.ACTIVITY_BACK);
			break;
		case R.id.base_activity_title_backicon:
			map.put("signup","");
			MobclickAgent.onEvent(baseActivity, "0089", map); 
			// 返回键处理
			onBackPressed();
			break;
		default:
			break;
		}
	}

	
	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent intent) {
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
	/**
	 * 
	 * Describe:沙龙报名请求
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void joinSalonRequest() {
		ApiHttpCilent.getInstance(this).salonJoin(this, sid + "", new JoinSalonRequestCallBack());
	}

	/**
	 * 
	 * Describe:沙龙报名请求回调
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public class JoinSalonRequestCallBack extends
			BaseJsonHttpResponseHandler<BaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dismess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			joinSalonHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Dismess();
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dismess();
			Gson gson = new Gson();
			joinSalonBean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(joinSalonBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				message.obj = joinSalonBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = joinSalonBean.getError();
			}
			joinSalonHandler.sendMessage(message);
			return joinSalonBean;
		}
	}

	/**
	 * 
	 * Describe:加入沙龙消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public static class JoinSalonHandler extends WeakHandler<SalonJoinActivity> {

		public JoinSalonHandler(SalonJoinActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				/**
				 * 处理成功的数据
				 */
				
				Intent mIntent = new Intent();
				// 设置结果，并进行传送
				if (0 == getReference().fromType) {
					/**
					 * 详情页面回调
					 */
					getReference().setResult(1002, mIntent);
				} else if (1 == getReference().fromType) {
					/**
					 * 列表页面回调
					 */
					getReference().setResult(1001, mIntent);
				}
				
				getReference().finish();
				getReference().commit.setEnabled(true);
				break;
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindDate();
				getReference().commit.setEnabled(true);
				break;
			case ConstantsUtil.HTTP_FAILE:
				getReference().commit.setEnabled(false);
				/**
				 * 处理失败的数据
				 */
//				if ("100".equals(getReference().bean.getError().getCode())) {
//					MyApplication.getInstance().startLogin(new LoginCallBack() {
//						@Override
//						public void loginSuccess() {
//							Intent intentbusinessCard = new Intent(getReference(), SalonJoinActivity.class);
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
					if (back != null) {
					ToastUtil.showToast(getReference().getBaseContext(),
							back.getInfo().toString());
					}else{
						ToastUtil.showToast(getReference(), "请求失败");
					}
//				}
				break;
			}
		}

	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		commit.setOnClickListener(this);
		my_create_card.setOnClickListener(this);
		tvRight.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		return "报名";
	}

	@Override
	protected String setRightText() {
		return "修改名片";
	}

	@Override
	protected int setLeftImageResource() {
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		return 0;
	}

	@Override
	protected int setRightImageResource() {
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
