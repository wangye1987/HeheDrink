package com.heheys.ec.controller.activity; 

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.controller.fragment.AddressFragment;
import com.heheys.ec.controller.fragment.NewAddFragemnt;
import com.heheys.ec.model.dataBean.AddressBean;
import com.heheys.ec.model.dataBean.AddressListBean;
import com.heheys.ec.utils.FragmentUtil;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-25 下午2:45:45 
 * 类说明  编辑送货地址
 * @param
 */
public class EditAddressActivity extends BaseActivity {

	private Context context;
	private AddressListBean bean;
	private FrameLayout content_frameLayout;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.edit_add);
		initView();
		initDate();
		initFragment();
	}

	private void initView() {
		content_frameLayout = (FrameLayout)findViewById(R.id.content_frameLayout);
	}

	private void initDate() {
		context = EditAddressActivity.this;
		Intent intent = getIntent();
		if(intent!=null){
			bean = (AddressListBean) intent.getSerializableExtra("bean");
			if(bean != null)
			ResetTitle("编辑收货地址");
		}
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		FragmentUtil.stratFragment((FragmentActivity) context,
				new NewAddFragemnt(bean), R.id.content_frameLayout, false,null);
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
		return "新增收货地址";
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
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
 