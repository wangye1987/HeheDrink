package com.heheys.ec.controller.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.SalonFragmentAdaper;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * Describe:沙龙页面
 * 
 * Date:2015-9-24
 * 
 * Author:liuzhouliang
 */
public class SalonFragment extends BaseFragment implements OnRefreshListener,
		OnMoreListener {
	private RefreshListView refreshListView;
	private SalonFragmentAdaper salonFragmentAdaper;

	@Override
	public void onClick(View v) {
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_salon, container,
				true);
		initView(rootView);
		return rootView;
	}

	/**
	 * 
	 * Describe:初始化控件
	 * 
	 * Date:2015-9-24
	 * 
	 * Author:liuzhouliang
	 */
	private void initView(View rootView) {
		refreshListView = (RefreshListView) rootView
				.findViewById(R.id.fragment_salon_lv);
		initData();
	}

	/**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-9-24
	 * 
	 * Author:liuzhouliang
	 */
	private void initData() {
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 12; i++) {
			data.add(i + "");
		}
		salonFragmentAdaper = new SalonFragmentAdaper(data, baseActivity);
		bindViewData();
	}

	/**
	 * 
	 * Describe:绑定控件数据
	 * 
	 * Date:2015-9-24
	 * 
	 * Author:liuzhouliang
	 */
	private void bindViewData() {
		refreshListView.setAdapter(salonFragmentAdaper);
	}

	@Override
	protected void getNetData() {
	}

	@Override
	protected void setViewListener() {
		refreshListView.setonLoadListener(this);
		refreshListView.setonRefreshListener(this);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected String setTitleName() {
		return "沙龙";
	}

	@Override
	protected String setRightText() {
		return null;
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

	@Override
	protected boolean isShowLeftIcon() {
		return false;
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
	public void onLoadMore() {
	}

	@Override
	public void onRefresh() {
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("SalonFragment"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("SalonFragment"); 
	}
}
