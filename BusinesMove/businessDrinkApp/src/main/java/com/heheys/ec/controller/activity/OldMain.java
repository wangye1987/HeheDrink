/*
package com.heheys.ec.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.controller.fragment.HomeFragment;
import com.heheys.ec.controller.fragment.OrderFragment;
import com.heheys.ec.controller.fragment.UserCenterFragment;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.MainTabAdapter;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.Bean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.view.TabView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * 
 * Describe:主页面
 * 
 * Date:2015-9-27
 * 
 * Author:liuzhouliang
 *//*

public class OldMain extends FragmentActivity implements
		OnPageChangeListener, View.OnClickListener {
	private static String TAG = MainActivity.class.getName();
	private ViewPager mViewPager;
	public TabView mTabHome, mTabShoppingCart, mTabOrder, mTabUserCenter;
	private Fragment[] mFragmentData = new Fragment[] { new HomeFragment()
			, new OrderFragment(),
			new UserCenterFragment() };
	private List<TabView> mTabIndicator = new ArrayList<TabView>();
	private MainTabAdapter mTabAdapter;
	private String tabType;
	private static Context mContext;
	private MyHandler handler = new MyHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LogUtil.d(TAG, "onStart");
		mContext = OldMain.this;
		initView();
		initData();
		initTabIndicator();
		bindViewData();
		setViewListener();
	}

	@Override
	protected void onStart() {
		super.onStart();
//		LogUtil.d(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
//		LogUtil.d(TAG, "onResume");
		 MobclickAgent.onResume(this);       //统计时长
	}

	*/
/**
	 * 初始化控件
	 *//*

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.activity_main_vp);
		mTabHome = (TabView) findViewById(R.id.activity_main_home_tab);
		mTabShoppingCart = (TabView) findViewById(R.id.activity_main_shoppingcart_tab);
		mTabOrder = (TabView) findViewById(R.id.activity_main_order_tab);
		mTabUserCenter = (TabView) findViewById(R.id.activity_main_usercenter_tab);
	}

	*/
/**
	 * 初始化页面数据
	 *//*

	private void initData() {
		tabType = getIntent().getStringExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY);

		mTabAdapter = new MainTabAdapter(getSupportFragmentManager(),
				mFragmentData);

		ApiHttpCilent.getInstance(mContext).InitProvinceList(mContext,
				new MyCallBack());
	}

	private void Dimess() {
		runOnUiThread(new Runnable() {
			public void run() {
				ApiHttpCilent.loading.dismiss();
			}
		});
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<ProvinceListBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ProvinceListBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ProvinceListBaseBean arg3) {
			Dimess();
		}

		@Override
		protected ProvinceListBaseBean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			ProvinceListBaseBean bean = gson.fromJson(response,
					ProvinceListBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = bean.getError().getInfo();
			}
			handler.sendMessage(message);

			return bean;
		}
	}

	public static class MyHandler extends WeakHandler<OldMain> {

		public MyHandler(OldMain reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				Bean bean = (Bean) msg.obj;
				SharedPreferencesUtil.saveProvinceObject(mContext, bean);
				break;
			case ConstantsUtil.HTTP_FAILE:
				// String back = (String) msg.obj;
				// ToastUtil.showToast(mContext, back);
				break;
			default:
				break;
			}
		}
	}

	*/
/**
	 * 初始化TAB视图集合
	 *//*

	private void initTabIndicator() {
		mTabIndicator.add(mTabHome);
		mTabIndicator.add(mTabShoppingCart);
		mTabIndicator.add(mTabOrder);
		mTabIndicator.add(mTabUserCenter);

		mTabHome.setIconAlpha(1.0f);
		setTabView(tabType);
	}

	*/
/**
	 * 
	 * Describe:设置TAB显示类型
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:liuzhouliang
	 *//*


	private void setTabView(String type) {
		if (StringUtil.isEmpty(type)) {
			return;
		}

		if (ConstantsUtil.MAIN_TAB_HOME.equals(type)) {
			*/
/**
			 * 首页TAB
			 *//*

			resetOtherTabs();
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
		}
		if (ConstantsUtil.MAIN_TAB_SHOP_CAR.equals(type)) {
			*/
/**
			 * 购物车TAB
			 *//*

			resetOtherTabs();
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
		}
		if (ConstantsUtil.MAIN_TAB_ORDER.equals(type)) {
			*/
/**
			 * 订单TAB
			 *//*

			resetOtherTabs();
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
		}
		if (ConstantsUtil.MAIN_TAB_USER.equals(type)) {
			*/
/**
			 * 我的TAB
			 *//*

			resetOtherTabs();
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
		}

	}

	*/
/**
	 * 绑定页面数据
	 *//*

	private void bindViewData() {
		mViewPager.setAdapter(mTabAdapter);

	}

	*/
/**
	 * 设置控件监听事件
	 *//*

	@SuppressWarnings("deprecation")
	private void setViewListener() {
		mTabHome.setOnClickListener(this);
		mTabShoppingCart.setOnClickListener(this);
		mTabOrder.setOnClickListener(this);
		mTabUserCenter.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		Log.e(TAG, "position = " + position + " , positionOffset = "
				+ positionOffset);

		if (positionOffset > 0) {
			TabView left = mTabIndicator.get(position);
			TabView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {

		resetOtherTabs();

		switch (v.getId()) {
		case R.id.activity_main_home_tab:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.activity_main_shoppingcart_tab:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.activity_main_order_tab:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.activity_main_usercenter_tab:
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;

		}

	}

	*/
/**
	 * 重置其他的Tab
	 *//*

	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
*/
