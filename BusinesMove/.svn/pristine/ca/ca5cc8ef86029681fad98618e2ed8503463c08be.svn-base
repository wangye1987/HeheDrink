package com.heheys.ec.controller.fragment; 

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-28 下午6:05:33 
 * 类说明  提交反馈界面
 * @param
 */
public class RetroactionFragment extends BaseFragment {

	private View view;
	private Button bu_commit;
	private EditText et_retroaction;
	private Context context;

	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.retroaction, container, true);
		initView();
		return view;
	}

	private void initView() {
		context = getActivity();
		et_retroaction = (EditText) view.findViewById(R.id.et_retroaction);
		bu_commit = (Button) view.findViewById(R.id.bu_commit);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bu_commit:
			String st_retroaction = et_retroaction.getText().toString().trim();
			if(st_retroaction.length()<0){
				ToastUtil.showToast(context, getResources().getString(R.string.notice_retroaction));
			}else{
				//提交数据到服务
			}
			break;

		default:
			break;
		}
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
		return "问题反馈";
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
	    MobclickAgent.onPageStart("RetroactionFragment"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("RetroactionFragment"); 
	}
}
 