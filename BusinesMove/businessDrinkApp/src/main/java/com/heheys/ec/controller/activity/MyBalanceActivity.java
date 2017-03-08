package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BaseCardBean;
import com.heheys.ec.model.dataBean.BaseCardBean.ResultCardBean;
import com.heheys.ec.model.dataBean.BaseRecordBean;
import com.heheys.ec.model.dataBean.BaseRecordBean.StatueBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.HehePayBaseBean;
import com.heheys.ec.model.dataBean.LoginNamePwd;
import com.heheys.ec.model.dataBean.MyBalanceBaseBean;
import com.heheys.ec.model.dataBean.MyBalanceBaseBean.ResultBanlanceBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.DeleteEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-25 下午12:03:51 类说明 我的余额
 */
public class MyBalanceActivity extends BaseActivity implements OnClickListener {

	// 显示余额
	private TextView tv_mybalance, tv_tel,tv_istx;
	// 提现按钮
	private Button bt_getmoney,bt_getmoney_one, outh_code, bt_bind;
	// 服务器返回数据bean
	private MyBalanceBaseBean basebean;
	// 服务器返回数据bean 默认卡号
	private BaseCardBean basecardbean;
	// 服务器返回数据bean result
	private ResultBanlanceBean resultbean;
	// 服务器返回数据bean result 卡号数据bean
	private ResultCardBean resultcardbean;
	// 消息桥梁balanceHandler
	private BalanceHandler balanceHandler = new BalanceHandler(this);
	private LinearLayout linear_withdraw,linear_tel;
	private DeleteEditText withdraw_et;
	private DeleteEditText pass_code;
	private float balance;

	private boolean isFirst =  true;
	private String tellength;
	private int mCountDown;
	private Timer countDownTimer;
	private int flag;
	private String mywithdraw;
	private String my_balance;
	private HehePayBaseBean hehepay;
	private RelativeLayout rv_coupon;
	private RelativeLayout rv_hehemoney;
	private RelativeLayout rv_points;
	private TextView tv_coupon;
	private TextView tv_points;
	private TextView tv_hehemoney;
	// 初始化视图
	private void initView() {
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
		tv_mybalance = (TextView) findViewById(R.id.tv_mybalance);
		tv_hehemoney = (TextView) findViewById(R.id.tv_hehemoney);
		tv_coupon = (TextView) findViewById(R.id.tv_coupon);
		tv_points = (TextView) findViewById(R.id.tv_points);
		tv_tel = (TextView) findViewById(R.id.tv_tel);
		tv_istx = (TextView) findViewById(R.id.tv_istx);
		bt_getmoney = (Button) findViewById(R.id.bt_getmoney);
//		bt_getmoney_tx = (Button) findViewById(R.id.bt_getmoney_tx);
		bt_getmoney_one = (Button) findViewById(R.id.bt_getmoney_one);
		outh_code = (Button) findViewById(R.id.outh_code);
		bt_bind = (Button) findViewById(R.id.bt_bind);
		rv_points = (RelativeLayout) findViewById(R.id.rv_points);//积分
		rv_coupon = (RelativeLayout) findViewById(R.id.rv_coupon);//优惠卷
		rv_hehemoney = (RelativeLayout) findViewById(R.id.rv_hehemoney);//喝币
		linear_withdraw = (LinearLayout) findViewById(R.id.linear_withdraw);
		linear_tel = (LinearLayout) findViewById(R.id.linear_tel);
		withdraw_et = (DeleteEditText) findViewById(R.id.withdraw_et);
		pass_code = (DeleteEditText) findViewById(R.id.pass_code);
		bt_getmoney.setOnClickListener(this);
		bt_getmoney_one.setOnClickListener(this);
		outh_code.setOnClickListener(this);
		bt_bind.setOnClickListener(this);
		rv_coupon.setOnClickListener(this);
		rv_points.setOnClickListener(this);
		rv_hehemoney.setOnClickListener(this);
//		bt_getmoney_tx.setOnClickListener(this);
		setEable(true);
		tv_istx.setVisibility(View.GONE);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		tvRight.setOnClickListener(this);
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		ApiHttpCilent.getInstance(getApplicationContext()).GetMyBalance(baseContext,
				new BanlanceCallBack());
		ApiHttpCilent.getInstance(getApplicationContext()).GetUserCard(baseContext,
				new CardCallBack());
	}

	class BalanceHandler extends WeakHandler<MyBalanceActivity> {

		@SuppressLint("HandlerLeak") public BalanceHandler(MyBalanceActivity reference) {
			super(reference);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				resultbean = (ResultBanlanceBean) msg.obj;
				if (resultbean != null) {
					bindData();
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				if(flag==2){
					if(hehepay!=null)
					tv_mybalance.setText(hehepay.getResult().getBanace());
					ToastUtil.showToast(baseActivity, "已提交审核");
					tv_istx.setText("提现审核已提交,请耐心等候");
					tv_istx.setVisibility(View.VISIBLE);
					setEable(false);
				}else{
					resultcardbean = (ResultCardBean) msg.obj;
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				StatueBean recordBean = (StatueBean) msg.obj;
//				s
				break;
			case ConstantsUtil.HTTP_FAILE:
				ErrorBean error = (ErrorBean) msg.obj;
				if(error != null && !StringUtil.isEmpty(error.getInfo()))
					ToastUtil.showToast(baseActivity, error.getInfo());
				break;
			default:
				break;
			}
		}
	}

	//提现后设置不可编辑
	private void setEable(boolean iscanclick){
		withdraw_et.setEnabled(iscanclick);
		pass_code.setEnabled(iscanclick);
		outh_code.setEnabled(iscanclick);
		bt_getmoney.setEnabled(iscanclick);
		bt_getmoney.setBackgroundResource(R.color.color_ababab);
	}
	
	/*
	 * 邦定数据
	 */
	private void bindData() {
		if (!StringUtil.isEmpty(resultbean.getBalance())) {
			my_balance = resultbean.getBalance();
			ViewUtil.setActivityPrice(tv_mybalance,my_balance);
			tv_coupon.setText(resultbean.getCoupon()==null?"":resultbean.getCoupon()+"张");
			tv_points.setText(resultbean.getScore()==null?"":resultbean.getScore()+"分");
			tv_hehemoney.setText(resultbean.getCoin()==null?"":resultbean.getCoin()+"喝币");
			// 如果余额大于0 可以支付
			balance = Float.parseFloat(resultbean.getBalance());
			if (balance > 0) {
				bt_getmoney_one.setEnabled(true);
				bt_getmoney_one.setClickable(true);
				bt_getmoney_one.setBackgroundResource(R.drawable.bt_bg_yellow);
			}
			
		}
	}

	// 异常等待框
	private void Dimess() {
		MyBalanceActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	public class BanlanceCallBack extends
			BaseJsonHttpResponseHandler<MyBalanceBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MyBalanceBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			balanceHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MyBalanceBaseBean arg3) {
		}

		@Override
		protected MyBalanceBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			 Dimess();
			Gson gson = new Gson();
			basebean = gson.fromJson(response, MyBalanceBaseBean.class);
			if (basebean != null) {
				Message msg = new Message();
				if (("1").equals(basebean.getStatus())) {
					msg.what = ConstantsUtil.HTTP_SUCCESS;
					msg.obj = basebean.getResult();
				} else if (("0").equals(basebean.getStatus())) {
					msg.what = ConstantsUtil.HTTP_FAILE;
					msg.obj = basebean.getError();
				}
				balanceHandler.sendMessage(msg);
			}
			return basebean;
		}

	}

	
	//账户
	public class CardCallBack extends BaseJsonHttpResponseHandler<BaseCardBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseCardBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			balanceHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseCardBean arg3) {
		}

		@Override
		protected BaseCardBean parseResponse(String response, boolean arg1)
				throws Throwable {
			 Dimess();
			Gson gson = new Gson();
			basecardbean = gson.fromJson(response, BaseCardBean.class);
			if (basecardbean != null) {
				Message msg = new Message();
				if (("1").equals(basecardbean.getStatus())) {
					msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
					msg.obj = basecardbean.getResult();
				} else if (("0").equals(basecardbean.getStatus())) {
					msg.what = ConstantsUtil.HTTP_FAILE;
					msg.obj = basecardbean.getError();
				}
				balanceHandler.sendMessage(msg);
			}
			return basecardbean;
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
//		tvRight.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "我的钱包";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return "提现记录";
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
		Intent intent = new Intent();
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.bt_getmoney_one:
			// 提现按钮
			if(isFirst){
				MobclickAgent.onEvent(baseActivity, "C_WAL_WTH_1"); //申请提现
			if (balance > 0) {
				bt_getmoney.setText("申请");
				bt_getmoney.setVisibility(View.VISIBLE);
				bt_getmoney_one.setVisibility(View.GONE);
				rv_coupon.setVisibility(View.GONE);
				rv_hehemoney.setVisibility(View.GONE);
				rv_points.setVisibility(View.GONE);
				linear_withdraw.setVisibility(View.VISIBLE);
				ResetTitle("申请提现");
				tvRight.setVisibility(View.VISIBLE);
				bt_getmoney.setBackgroundResource(R.color.color_ababab);
				bt_getmoney.setEnabled(false);
				isFirst = false;
				}
			}
			break;
		case R.id.bt_getmoney:
			MobclickAgent.onEvent(baseActivity, "C_WTH_2"); //申请
			mywithdraw = withdraw_et.getText().toString().trim();
			if(StringUtil.isEmpty(mywithdraw)){
				ToastUtil.showToast(baseActivity, "提现金额不能为空");
				return ;
			}
			if(Float.parseFloat(mywithdraw)<=0){
				ToastUtil.showToast(baseActivity, "提现金额填写错误");
				return ;
			}
			if(StringUtil.isEmpty(resultcardbean.getAccountnum())){//没有默认账号
				ToastUtil.showToast(baseActivity, "设置收款账号后 方可提现");
				StartActivityUtil.startActivityForResult(baseActivity, WithDrawActivity.class,ConstantsUtil.REQUEST_CODE);
			}else{
				if(StringUtil.isEmpty(pass_code.getText().toString().trim())){
					ToastUtil.showToast(baseActivity, "验证码不能为空");
					return ;
				}
				flag = 2;//提现操作
				ApiHttpCilent.getInstance(getApplicationContext()).getWithDraw(baseActivity, withdraw_et.getText().toString().trim(), pass_code.getText().toString().trim(),
						new MyBalanceCallBack());
			}
		
			break;
		case R.id.base_activity_title_backicon:
			// 返回键处理
			backPress();
			break;
		case R.id.linear_back:
			// 返回键处理
			backPress();
			break;
		case R.id.outh_code:
			map.put("buzType", "申请提现");
			MobclickAgent.onEvent(baseActivity, "C_WTH_1",map); //申请提现
			@SuppressWarnings("unchecked")
			ArrayList<LoginNamePwd> listlogin = (ArrayList<LoginNamePwd>) SharedPreferencesUtil.getObject(baseContext, "loginUserPwd");
			if(listlogin!=null && listlogin.size()>0){
				tellength = listlogin.get(listlogin.size()-1).getUserName().toString();
				tv_tel.setText(listlogin.get(listlogin.size()-1).getUserName().toString());
				linear_tel.setVisibility(View.VISIBLE);
				AuthTel();
				bt_getmoney.setEnabled(true);
			}else{
				ToastUtil.showToast(baseActivity, "获取手机失败");
				linear_tel.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.bt_bind:
			MobclickAgent.onEvent(baseActivity, "C_WTH_3"); //设置收款账号
			//设置默认绑定的卡号
			intent.setClass(this,WithDrawActivity.class);
			intent.putExtra("account", resultcardbean);
			StartActivityUtil.startActivityForResult(baseActivity, WithDrawActivity.class,ConstantsUtil.REQUEST_CODE_TWO);
			break;
		case R.id.base_activity_title_right_righttv:
			MobclickAgent.onEvent(baseActivity, "C_WTH_4"); //收款账号记录
			if (!IsLogin.isLogin(baseActivity)) {
				MyApplication.getInstance().startLogin(new LoginCallBack() {
					@Override
					public void loginSuccess() {
						StartActivityUtil.startActivity(baseActivity, WithdrawRecordActivity.class);
					}
					@Override                                                                                                                                                                                                                                                         
					public void loginFail() {
					}
				}, baseActivity);
			}else{
				StartActivityUtil.startActivity(baseActivity, WithdrawRecordActivity.class);
			}
			break;
		case R.id.rv_coupon:
			MobclickAgent.onEvent(baseActivity, "C_WAL_CPN_1"); //我的优惠券
			intent.setClass(this,CouponActivity.class);
			intent.putExtra("key", "scan");//查看
			StartActivityUtil.startActivity(this, intent);
			break;
		case R.id.rv_points:
			//我的积分
			intent.setClass(this,MyPointsActivity.class);
			StartActivityUtil.startActivityForResult(this, intent, ConstantsUtil.REQUEST_CODE_FIVE);
			break;
		case R.id.rv_hehemoney:
			//喝币
			intent.setClass(this,HeHeMoneyActivity.class);
			StartActivityUtil.startActivity(this, intent);
			break;
		default:
			break;
		}
	}
	
	
	void backPress(){
		if(isFirst){
			onBackPressed();
		}else{
			ResetTitle("我的余额");
			linear_withdraw.setVisibility(View.GONE);
			bt_getmoney_one.setText("申请提现");
			bt_getmoney.setVisibility(View.GONE);
			tv_mybalance.setVisibility(View.VISIBLE);
			bt_getmoney_one.setVisibility(View.VISIBLE);
			isFirst = true;
			bt_getmoney_one.setBackgroundResource(R.drawable.bt_bg_yellow);
			bt_getmoney_one.setEnabled(true);
		}
	}
	
	@Override
	protected void onActivityResult(int requstcode, int resultcode, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(requstcode, resultcode, arg2);
		switch (requstcode) {
		case ConstantsUtil.REQUEST_CODE:
			//返回结果 提现
			flag = 1;//
//			ApiHttpCilent.getInstance(baseActivity).getWithDraw(baseActivity, withdraw_et.getText().toString().trim(), pass_code.getText().toString().trim(),
//					new MyBalanceCallBack());
			ApiHttpCilent.getInstance(getApplicationContext()).GetUserCard(baseContext,
					new CardCallBack());
			break;
			//设置完账号在重新查询一遍
		case ConstantsUtil.REQUEST_CODE_TWO:
			ApiHttpCilent.getInstance(getApplicationContext()).GetUserCard(baseContext,
					new CardCallBack());
		case ConstantsUtil.REQUEST_CODE_FIVE:
			//获取
			getNetData();
			break;
		default:
			break;
		}
	}
	private void AuthTel() {
//		if(ValidatorUtil.isMobile(tellength)){
			startCountDown();
//		}else{
//			ToastUtil.showToast(baseActivity, getResources().getString(R.string.notice_correct_phone));
//			return;
//		}
		flag  = 1;
		ApiHttpCilent.getInstance(getApplicationContext()).ObtinCode(baseActivity, tellength, new MyCallBack(),ConstantsUtil.CODE_FLAG_5);//获取验证码
	}
	
	//验证码倒计时
		private void startCountDown() {
			mCountDown = ConstantsUtil.BACK_SECOND;
			countDownTimer = new Timer();
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() {
					mCountDown--;
					if (mCountDown < 0) {
						countDownHandler
								.sendEmptyMessage(ConstantsUtil.COUNT_DOWN_END);
						countDownTimer.cancel();
						countDownTimer = null;
					} else {
						countDownHandler.sendEmptyMessage(ConstantsUtil.COUNT_DOWNING);
					}
				}
			};
			countDownTimer.schedule(timerTask, 0, ConstantsUtil.COUNT_DOWN_TIME);
		}

		/**
		 * 倒计时消息处理
		 */
		private Handler countDownHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ConstantsUtil.COUNT_DOWNING:
					outh_code.setText(mCountDown + "秒");
					outh_code.setClickable(false);
//					outh_code.setBackgroundResource(R.drawable.shape_button_gray);
					outh_code.setBackgroundResource(R.drawable.shape_button_gray);
					bt_getmoney.setBackgroundResource(R.drawable.bt_bg_yellow);
					break;
				case ConstantsUtil.COUNT_DOWN_END:
					outh_code.setText("发验证码");
					outh_code.setClickable(true);
					outh_code.setBackgroundResource(R.drawable.bt_bg_yellow);
					break;
				default:
					break;
				}
			}
		};
	
		/*
		 * 提现余额回调
		 * */
		class MyBalanceCallBack extends  BaseJsonHttpResponseHandler<HehePayBaseBean>{
			
			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					String arg3, HehePayBaseBean arg4) {
				Dimess();
				Message message = Message.obtain();
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				balanceHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2,
					HehePayBaseBean arg3) {
				Dimess();
			}

			@Override
			protected HehePayBaseBean parseResponse(String response, boolean arg1)
					throws Throwable {
				// TODO Auto-generated method stub
				Dimess();
				Gson gson = new Gson();
				hehepay = gson.fromJson(response,HehePayBaseBean.class);
				Message message = Message.obtain();
				if("1".equals(hehepay.getStatus())&& flag==2){//提现成功
					message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				}else if("0".equals(hehepay.getStatus())){
					message.what = ConstantsUtil.HTTP_FAILE;//提取错误
					message.obj = hehepay.getError();
				}
				balanceHandler.sendMessage(message);
				return hehepay;
			}
		}
		/*
		 * 验证码回调
		 * */
		class MyCallBack extends  BaseJsonHttpResponseHandler<BaseBean>{
			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					String arg3, BaseBean arg4) {
				Dimess();
				Message message = Message.obtain();
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				balanceHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2,
					BaseBean arg3) {
				Dimess();
			}

			@Override
			protected BaseBean parseResponse(String response, boolean arg1)
					throws Throwable {
				// TODO Auto-generated method stub
				Dimess();
				Gson gson = new Gson();
				BaseBean loginBean = gson.fromJson(response,BaseBean.class);
				Message message = Message.obtain();
				if("1".equals(loginBean.getStatus())&& flag==1 ){//正常获取验证码
					message.what = ConstantsUtil.HTTP_SUCCESS;
				}else if("1".equals(loginBean.getStatus())&& flag==2 ){//提现成功
					message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				}else if("0".equals(loginBean.getStatus())){
					message.what = ConstantsUtil.HTTP_FAILE;//验证码错误
					message.obj = loginBean.getError();
				}
				balanceHandler.sendMessage(message);
				return loginBean;
			}
		}
		//查询我的提现记录
		class RecordCallBack extends  BaseJsonHttpResponseHandler<BaseRecordBean>{
			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					String arg3, BaseRecordBean arg4) {
				Dimess();
				Message message = Message.obtain();
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				balanceHandler.sendMessage(message);
			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2,
					BaseRecordBean arg3) {
				Dimess();
			}
			
			@Override
			protected BaseRecordBean parseResponse(String response, boolean arg1)
					throws Throwable {
				// TODO Auto-generated method stub
				Dimess();
				Gson gson = new Gson();
				BaseRecordBean recordBean = gson.fromJson(response,BaseRecordBean.class);
				Message message = Message.obtain();
				if("1".equals(recordBean.getStatus())){//正常获取提现记录
					message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
					message.obj = recordBean.getResult();
				}else if("0".equals(recordBean.getStatus())){
					message.what = ConstantsUtil.HTTP_FAILE;//验证码错误
					message.obj = recordBean.getError();
				}
				balanceHandler.sendMessage(message);
				return recordBean;
			}
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.my_balance);
		initView();
	}

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("PG_WAL");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("PG_WAL");
		MobclickAgent.onPause(this);
	}
}
