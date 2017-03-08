package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ListView;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.model.adapter.MyClientListAdapter;
import com.heheys.ec.model.dataBean.LinkManListBean;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-9-22 下午1:09:24 类说明 我的客户列表页
 * @param
 */
public class MyClientListActivity extends BaseActivity {

	private ListView client_lv;
	private Context context;
	private List<LinkManListBean> listlink;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		
	}

	private void initDate() {
		listlink = new ArrayList<LinkManListBean>();
		MyClientListAdapter clientAdapter = new MyClientListAdapter(listlink, context);
		for(int i=1;i<20;i++){
			LinkManListBean linkbean = new LinkManListBean();
			linkbean.setAddress("soho A座"+i);
			listlink.add(linkbean);
		}
		client_lv.setAdapter(clientAdapter);
	}

	private void initView() {
		client_lv = (ListView) findViewById(R.id.client_lv);
	}
	@Override
	protected void setBaseContentView(int layoutResID) {
	// TODO Auto-generated method stub
		super.setBaseContentView(layoutResID);
		setContentView(R.layout.myclient_list);
		context = MyClientListActivity.this;
		initView();
		initDate();
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
		return "我的";
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
