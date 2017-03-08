package com.heheys.ec.controller.activity; 

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-24 下午7:07:45 
 * 类说明 注册设置密码
 * @param
 */
public class RegisterTowActivity extends BaseActivity {

	private EditText password_one;
	private EditText password_two;
	private Button commit_register;
	private String pwd_one;
	private String pwd_two;
	private Context context;
	private String phone;
	private String code;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.register_two);
		initView();
		initDate();
	}

	private void initDate() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if(intent!=null){
			phone = intent.getStringExtra("phone");
			code = intent.getStringExtra("code");
		}
	}

	private void initView() {
		context = RegisterTowActivity.this;
		password_one = (EditText) findViewById(R.id.password_one);
		password_two = (EditText) findViewById(R.id.password_two);
		commit_register = (Button) findViewById(R.id.commit_register);
		commit_register.setOnClickListener(this);
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
		return "设置登录密码";
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
		case R.id.commit_register:
			pwd_one = password_one.getText().toString().trim();
			pwd_two = password_two.getText().toString().trim();
			if(pwd_one==null || pwd_one.equals("")){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_password));
				return;
			}
			if(pwd_two==null || pwd_two.equals("")){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_password_next));
				return;
			}
			//提交
//			ApiHttpCilent.getInstance(context).RegisterApp(context, mobile, callback)
			break;

		default:
			break;
		}
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
 