package com.heheys.ec.controller.fragment; 

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.model.adapter.WineAdapter;
import com.heheys.ec.model.dataBean.WineBean;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-24 下午1:55:09 
 * 类说明 
 * @param
 */
public class MyWineFragment extends BaseFragment {

	private View view;
	private ListView lv;
	private WineAdapter adapter;

	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.wine_layout, container, true);
		initView();
		initDate();
		return view;
	}

	private void initDate() {
		// TODO Auto-generated method stub
		List<WineBean> list = new ArrayList<WineBean>();
		for(int i=0;i<6;i++){
			WineBean bean = new WineBean();
			bean.setWineDate("");
			bean.setWineRemark("");
			if(i==0){
				bean.setWineName("");
				bean.setWineNum(100+"");
			}else if(i==1){
				bean.setWineName("");
				bean.setWineNum(5000+"");
			}else if(i==3){
				bean.setWineName("");
				bean.setWineNum(200+"");
			}
			list.add(bean);
		}
		adapter = new WineAdapter(getActivity(), list);
		lv.setAdapter(adapter);
		
	}

	private void initView() {
		lv = (ListView) view.findViewById(R.id.lv);
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
		return "酒水需求";
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
	    MobclickAgent.onPageStart("MyWineFragment"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MyWineFragment"); 
	}
}
 