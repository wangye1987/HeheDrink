package com.heheys.ec.controller.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;

/**
 * @author wangkui
 * 
 * 关于我们
 */
public class AboutUs extends BaseActivity implements OnClickListener{

	private LinearLayout linear_help;
	private LinearLayout linear_help_hhm;
	private String serverurl;
	private String hhmurl;
	private TextView tv_tel;
	  private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.about_us);
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		linear_help = (LinearLayout) findViewById(R.id.linear_help);
		linear_help_hhm = (LinearLayout) findViewById(R.id.linear_help_hhm);
		tv_tel = (TextView) findViewById(R.id.tv_tel);
		tv_tel.setText(StringUtil.isEmpty(MyApplication.getInstance().getServiceline())?"":MyApplication.getInstance().getServiceline());
	}
	private void callPhone() {
		// TODO Auto-generated method stub
		Uri data = Uri.parse("tel:"+MyApplication.getInstance().getServiceline());
		Intent intents = new Intent(Intent.ACTION_CALL,data);
		this.startActivity(intents);
	}
	public void ShowDialog(String notice, String title, final int flag,
			final int type) {
		CommonDialog.makeText(this, title, notice, new OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
					String tel) {
				// TODO Auto-generated method stub
				if (OnDialogListener.LEFT == result && flag == 1) {
					if (ActivityCompat.checkSelfPermission(AboutUs.this,
			                Manifest.permission.CALL_PHONE)
			                != PackageManager.PERMISSION_GRANTED)
			        	{
			            ActivityCompat.requestPermissions(AboutUs.this,
			                    new String[]{Manifest.permission.CALL_PHONE},
			                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
			        } else
			        {
			            callPhone();
			        }
					CommonDialog.Dissmess();
				}  else {
					CommonDialog.Dissmess();
				}
			}
		}).showDialog();
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
		linear_help.setOnClickListener(this);
		linear_help_hhm.setOnClickListener(this);
		tv_tel.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "关于喝喝";
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
		case R.id.linear_help:
			serverurl = MyApplication.getInstance().getServerurl();
		 if( !StringUtil.isEmpty(serverurl)){
			Intent intent = new Intent(baseActivity, JDPayActivity.class);//获取电子合同
			intent.putExtra("url", serverurl);
			intent.putExtra("title", "货到付款使用帮助");
			intent.putExtra("flag", 4);//货到电子合同
			StartActivityUtil.startActivity(baseActivity, intent);
			}
			break;
		case R.id.linear_help_hhm:
			hhmurl = MyApplication.getInstance().getHhmurl();
		 if( !StringUtil.isEmpty(hhmurl)){
			Intent intent = new Intent(baseActivity, JDPayActivity.class);//获取电子合同
			intent.putExtra("url", hhmurl);
			intent.putExtra("title", "合伙买使用帮助");
			intent.putExtra("flag", 4);//货到电子合同
			StartActivityUtil.startActivity(baseActivity, intent);
			}
			break;
		case R.id.tv_tel:
			ShowDialog("是否拨打电话:" + MyApplication.getInstance().getServiceline(), "温馨提示", 1, 0);
		break;
		default:
			break;
		}
	}

}
