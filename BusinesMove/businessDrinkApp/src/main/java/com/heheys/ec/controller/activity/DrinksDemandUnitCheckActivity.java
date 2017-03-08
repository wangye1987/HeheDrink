package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.model.adapter.DrinksDemandUnitCheckAdapter;
import com.heheys.ec.model.adapter.DrinksDemandUnitCheckAdapter.UnitCheckCallBack;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:酒类需求单位页面
 *
 * Date:2015年11月25日下午1:51:02
 *
 * Author:LZL
 *
 */
public class DrinksDemandUnitCheckActivity extends BaseActivity implements UnitCheckCallBack {
	private ListView mListView;
	private DrinksDemandUnitCheckAdapter mAdapter;
	private List<String> unitData;
	HashMap<String,String> map = new HashMap<String,String>();
	private String buyorsale;
	private String title_key;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		initData();
		setBaseContentView(R.layout.drinks_demand_unit_check);

	}
	
	private void initData(){
		unitData=getIntent().getStringArrayListExtra("NAME");
		buyorsale = getIntent().getStringExtra("buyorsale");
		
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.drinks_demand_unit_check_lv);

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
//		List<String> data = new ArrayList<String>();
//		for (int i = 0; i < 10; i++) {
//			data.add(i + "");
//		}
//		unitData=getIntent().getStringArrayListExtra("NAME");
		mAdapter = new DrinksDemandUnitCheckAdapter(unitData, baseContext);
		mListView.setAdapter(mAdapter);
		mAdapter.setCheckListener(this);

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
		return "单位";
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
	public void checkCallBack(String content) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.putExtra("unit", content);
		setResult(RESULT_OK, intent);
		finish();
		if("buy".equals(buyorsale)){
			if(content.equals("瓶")){
				title_key = "Buy_unit_bottle";
			}else{
				title_key = "Buy_unit_box";
			}
		}else{
			if(content.equals("瓶")){
				title_key = "Sell_unit_bottle";
			}else{
				title_key = "Sell_unit_box";
			}
		}
			map.put(title_key,"");
			MobclickAgent.onEvent(baseContext, title_key, map); 
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		super.onClick(v);
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
			if("buy".equals(buyorsale)){
				title_key = "Buy_unit_return";
			}else{
				title_key = "Sell_unit_return";
			}
			map.put(title_key,"");
			MobclickAgent.onEvent(baseActivity, title_key, map); 
			// 返回键处理
			onBackPressed();
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
