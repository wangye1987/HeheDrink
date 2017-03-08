package com.heheys.ec.controller.fragment; 

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-25 下午6:30:40 
 * 类说明  设置界面
 * @param
 */
public class SettingFragment extends BaseFragment {

	private View view;
	private Context context;

	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.setting, container, true);
		context = getActivity();
		initView();
		initDate();
		return view;
	}

	private void initDate() {
		// TODO Auto-generated method stub
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean hasTitleIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean hasDownIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "设置";
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
	    MobclickAgent.onPageStart("SettingFragment"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("SettingFragment"); 
	}
}
 