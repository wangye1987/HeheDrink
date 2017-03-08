package com.heheys.ec.controller.activity; 

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.DeleteEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-25 上午10:04:51 
 * 类说明  忘记密码
 * @param
 */
public class FrogetPwdActivity extends BaseActivity {
	private int mCountDown;
	private Timer countDownTimer;
	private TextView outh_code;
	private Button commit_verify;
	private DeleteEditText phone;
	private static Context context;
	private DeleteEditText pass_code;
	private DeleteEditText password_one;
	private DeleteEditText password_two;
	private int code ;
	private MyHandler handler = new MyHandler(this);
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.forgert_pwd_beat2);
		initView();
	}

	private void initView() {
		context = FrogetPwdActivity.this;
		phone = (DeleteEditText) findViewById(R.id.phone);	
		pass_code = (DeleteEditText) findViewById(R.id.pass_code);	
		password_one = (DeleteEditText) findViewById(R.id.password_one);	
		password_two = (DeleteEditText) findViewById(R.id.password_two);	
		outh_code = (TextView) findViewById(R.id.outh_code);	
		commit_verify = (Button) findViewById(R.id.commit_verify);	
		outh_code.setOnClickListener(this);
		commit_verify.setOnClickListener(this);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub

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
		return "找回密码";
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
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.outh_code:
			HashMap<String,String> maps = new HashMap<String,String>();
			maps.put("buzType","找回密码");
			MobclickAgent.onEvent(baseActivity, "C_LGN_PW_1", maps); 
			if(ToastNoNetWork.ToastError(context)){
				return;
			}
			String tel = phone.getText().toString().trim();
			if(tel==null||tel.length()==0){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_phone_null));
				return;
			}
			if(StringUtil.isMobileNo(tel)){
				//处理联网获取验证码 成功后执行下面的代码
				startCountDown();
				code = 1;//获取验证码code=1
				ApiHttpCilent.getInstance(getApplicationContext()).ObtinCode(context, tel, new MyCallBack(),ConstantsUtil.CODE_FLAG_3);//获取验证码
			}else{
				ToastUtil.showToast(context, getResources().getString(R.string.notice_phone));
				
				
			}
			break;
		case R.id.commit_verify:
			MobclickAgent.onEvent(baseActivity, "C_LGN_PW_2"); 
			if(ToastNoNetWork.ToastError(context)){
				return;
			}
			String phone_text = phone.getText().toString().trim();
			String code_text = pass_code.getText().toString().trim();
			String one_text = password_one.getText().toString().trim();
			String two_text = password_two.getText().toString().trim();
			if(phone_text==null || phone_text.equals("")){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_tel));
				return;
			}
			if(!StringUtil.isMobileNo(phone_text)){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_correct_phone));
				return;
			}
			if(code_text==null || code_text.equals("")){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_code));
				return;
			}
			if(one_text==null || one_text.equals("")){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_password));
				return;
			}
			if(two_text==null || two_text.equals("")){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_password_next));
				return;
			}
			if(!two_text.equals(one_text)){
				ToastUtil.showToast(context, getResources().getString(R.string.password_same));
				return;
			}
			if(two_text.length()<6 || one_text.length()<6){
				ToastUtil.showToast(context, getResources().getString(R.string.pwd_one_length));
				return;
			}
			code = 2;//获取验证码code=2
			//忘记密码调取接口
			ApiHttpCilent.getInstance(getApplicationContext()).EditPassword(context, phone_text,code_text,one_text, new MyCallBack());
			break;
		case R.id.base_activity_title_backicon:
			HashMap<String,String> mapss = new HashMap<String,String>();
			mapss.put("frogetpwdback","");
			MobclickAgent.onEvent(baseActivity, "C_LGN_PW_2"); 
			// 返回键处理
			onBackPressed();
			break;
		}
	}
	void Dimess(){
		FrogetPwdActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
		}
	class MyCallBack extends  BaseJsonHttpResponseHandler<BaseBean>{
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
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
			if("1".equals(loginBean.getStatus())&& code==1){//正常获取验证码
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = loginBean.getResult();
			}else if("1".equals(loginBean.getStatus()) && code==2){//正确修改密码
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				message.obj = loginBean.getResult();
			}else if("0".equals(loginBean.getStatus()) && code==1){
				message.what = ConstantsUtil.HTTP_FAILE;//验证码获取错误
				message.obj = loginBean.getError().getInfo();
			}else{
				message.what = ConstantsUtil.HTTP_FAILE_LOGIN;
				message.obj = loginBean.getError().getInfo();//修改密码错误
			}
			handler.sendMessage(message);
		
			return loginBean;
		}
	}

	public static  class MyHandler extends WeakHandler<FrogetPwdActivity>{

		public MyHandler(FrogetPwdActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				ToastUtil.showToast(getReference(),"获取验证码成功");
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				ToastUtil.showToast(getReference(),"密码修改成功");
				getReference().finish();
				Intent intent = new Intent(context,LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity((Activity) context, intent);
				break;
			case ConstantsUtil.HTTP_FAILE:
				ToastUtil.showToast(getReference(),(String) msg.obj);
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN://
				ToastUtil.showToast(getReference(),(String) msg.obj);
				break;
			default:
				break;
			}
		}
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
					outh_code.setText(mCountDown + "S"+"重新获取");
					outh_code.setClickable(false);
//					outh_code.setBackgroundResource(R.drawable.shape_forget);
					outh_code.setTextColor(getResources().getColor(R.color.color_999999));
					commit_verify.setBackgroundResource(R.drawable.sharp_round);
//					commit_verify.setEnabled(true);
					break;
				case ConstantsUtil.COUNT_DOWN_END:
					outh_code.setText("获取验证码");
					outh_code.setClickable(true);
					outh_code.setTextColor(getResources().getColor(R.color.color_FFd938));
//					outh_code.setBackgroundResource(R.drawable.sharp_round);
					break;
				default:
					break;
				}
			}
		};
		 public void onResume() {
			    super.onResume();
			    MobclickAgent.onResume(this);       //统计时长
			}
			public void onPause() {
			    super.onPause();
			    MobclickAgent.onPause(this);
			}
}
 