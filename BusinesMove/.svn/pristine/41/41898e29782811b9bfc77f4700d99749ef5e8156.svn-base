/*
package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.controller.fragment.SearchCityFragment;
import com.heheys.ec.controller.fragment.ShowCityFragment;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.FileManager;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.CityListBean;
import com.heheys.ec.model.dataBean.CityListBean.CityDataList;
import com.heheys.ec.model.dataBean.CityListBean.CityDataList.CityData;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.sqliteHelper.HomeCitySQL;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.DeleteEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

*/
/**
 * Describe:选择城市
 * 
 * Date:2015-9-22
 * 
 * Author:liuzhouliang
 *//*

public class CheckCityActivity extends BaseActivity {
	private static String TAG = CheckCityActivity.class.getName();
	// fragment管理器
	private FragmentManager fragmentManager;
	// 搜索框父容器
	private LinearLayout llSearchContainer;
	// 输入框控件
	private DeleteEditText etInput;
	// 取消控件
	public static TextView tvCancle;
	// 记录当前选择的Fragment的位置,0表示城市列表页面，1表示搜索列表页面
	public static int curentFragmentPosition;
	// 在监听输入的时候动态给搜索页发消息
	private static MessgaeHandler searchHandler;
	// 搜索图标控件
	private ImageView ivSearch;
	// 城市列表页面
	private ShowCityFragment showCityListFragment;
	// 搜索城市页面
	private SearchCityFragment searchCityFragment;
	// 城市列表数据
	private List<String> cityList;
	public List<CityData> listObj;
	public String fromWhere;
	public String isShowBackString;
	 List<CityData>  sqlData;
	private RelativeLayout re_main;

	@Override
	protected void onCreate() {
		setBaseContentView(R.layout.check_city);

	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadChildView() {
		initData();
		etInput = (DeleteEditText) findViewById(R.id.check_city_search_et);
		tvCancle = (TextView) findViewById(R.id.check_city_cancle_tv);
		llSearchContainer = (LinearLayout) findViewById(R.id.check_city_search_container);
		ivSearch = (ImageView) findViewById(R.id.check_city_search_icon);
		re_main = (RelativeLayout) findViewById(R.id.base_activity_load_fail_main);
		if (ConstantsUtil.CHECK_CITY_NOT_SHOW_BACK.equals(isShowBackString)) {
			*/
/**
			 * 隐藏返回键
			 *//*

			hideBack();
		}
		if (ConstantsUtil.CHECK_CITY_SHOW_BACK.equals(isShowBackString)) {
			*/
/**
			 * 显示返回键
			 *//*

			showBack();
		}
	}

	*/
/**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void initData() {
		hideBack();
		fragmentManager = getSupportFragmentManager();
		cityList = new ArrayList<String>();
		// cityList =
		// Arrays.asList(getResources().getStringArray(R.array.date));
		searchHandler = new MessgaeHandler(this);
		// showCityListFragment();
		fromWhere = getIntent().getStringExtra(
				ConstantsUtil.CHECK_CITY_FROM_KEY);
		isShowBackString = getIntent().getStringExtra(
				ConstantsUtil.CHECK_CITY_SHOW_KEY);
	}

	@Override
	protected void getNetData() {
		
		new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sqlData=HomeCitySQL.getInstance(baseContext).getCityList();
				
			}
		}.run();
		
		if(sqlData!=null&&sqlData.size()>0){
			listObj=sqlData;
			if (listObj != null
					&& listObj.size() > 0) {
				for (CityData dataObj :listObj) {
					cityList.add(dataObj.getName());
				}
				showCityListFragment();
			} else {
				*/
/**
				 * 处理空数据
				 *//*

			}
		}else{
			ApiHttpCilent.getInstance(baseContext)
			.getCityList(baseContext, new RequestCallBack());
		}
		
	
	}
	void Dimess(){
	CheckCityActivity.this.runOnUiThread(new Runnable() {
		public void run() {
			if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
				ApiHttpCilent.loading.dismiss();
		}
	});
	}
	*/
/**
	 * 
	 * Describe:数据请求回调
	 * 
	 * Date:2015-10-8
	 * 
	 * Author:liuzhouliang
	 *//*

	public class RequestCallBack extends
			BaseJsonHttpResponseHandler<CityListBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, CityListBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			searchHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				CityListBean arg3) {
		}

		@Override
		protected CityListBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			CityListBean cityListData = gson.fromJson(response,
					CityListBean.class);
			Message message = Message.obtain();
			if ("1".equals(cityListData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = cityListData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = cityListData.getError().getInfo();
			}
			searchHandler.sendMessage(message);
			return cityListData;
		}

	}

	@Override
	protected void reloadCallback() {
		setBaseContentView(R.layout.check_city);
		getNetData();
	}

	@Override
	protected void setChildViewListener() {
		re_main.setOnClickListener(this);
		tvCancle.setOnClickListener(this);
		etInput.setOnClickListener(this);
		ivSearch.setOnClickListener(this);
		etInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("seach",s.toString());
				MobclickAgent.onEvent(baseActivity, "0003", map); 
				searchData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	*/
/**
	 * 
	 * Describe:执行搜索内容
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void searchData(String content) {
		if (searchCityFragment != null) {
			searchCityFragment.searchCity(content);
		}
	}

	@Override
	protected String setTitleName() {
		return "选择城市";
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
	public void onClick(View v) {
		// TODO Auto-generated method stub

		super.onClick(v);
		switch (v.getId()) {
		case R.id.check_city_cancle_tv:
			*/
/**
			 * 处理取消事件
			 *//*


			// if (SearchCityFragment.tagIsSearch) {
			// */
/**
			// * 如果是可搜索状态
			// *//*

			// if (StringUtil.isEmpty(etInput.getText().toString())) {
			// ToastUtil.showToast(baseContext, "请输入");
			// return;
			// } else {
			// Message message = Message.obtain();
			// message.what = 0;
			// Bundle data = new Bundle();
			// data.putString("DATA", etInput.getText().toString());
			// message.setData(data);
			// searchHandler.sendMessage(message);
			// }
			//
			// } else {
			//
			// }
			showCityListFragment();
			break;

		case R.id.check_city_search_et:
			*/
/**
			 * 处理输入框的点击事件
			 *//*

			handleInputAction();
			break;

		case R.id.check_city_search_icon:
			*/
/**
			 * 处理搜索控件点击事件
			 *//*

			if (StringUtil.isEmpty(etInput.getText().toString())) {
				ToastUtil.showToast(baseContext, "请输入");
				return;
			} else {
				Message message = Message.obtain();
				message.what = 1002;
				Bundle data = new Bundle();
				data.putString("DATA", etInput.getText().toString());
				message.setData(data);
				searchHandler.sendMessage(message);
			}
			break;
		case R.id.base_activity_load_fail_main:
			StartActivityUtil.startActivity(baseActivity, MainActivity.class);
			finish();
			break;
		}
	}

	*/
/**
	 * 
	 * Describe:处理输入事件
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void handleInputAction() {
		// TODO Auto-generated method stub

		if (curentFragmentPosition == 1) {
			*/
/**
			 * 处理搜索事件
			 *//*


		} else if (curentFragmentPosition == 0) {
			*/
/**
			 * 由城市列表页面切换到搜索页面
			 *//*

			ViewUtil.hideKeyBoard(etInput, baseContext);
			showSearchCityFragment();

		}

	}

	*/
/**
	 * 
	 * Describe:显示城市列表页面
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void showCityListFragment() {
		ViewUtil.hideKeyBoard(etInput, baseContext);
		llSearchContainer.setVisibility(View.VISIBLE);
		// 地址列表中隐藏取消文本
		tvCancle.setVisibility(View.GONE);
		// 记录fragment位置
		curentFragmentPosition = 0;
		FragmentTransaction transaction = fragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.out_alpha, 0);
		// 隐藏存在的fragment
		hideFragment(transaction);

		if (showCityListFragment == null) {
			// 对象不存在
			showCityListFragment = new ShowCityFragment(cityList);

		}
		if (!showCityListFragment.isAdded()) {
			// 没有被添加过
			transaction.add(R.id.check_city_fragment_container,
					showCityListFragment).commitAllowingStateLoss();
		} else {
			// 被添加过
			transaction.show(showCityListFragment).commitAllowingStateLoss();
		}
	}

	*/
/**
	 * 
	 * Describe:显示搜索页面
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	public void showSearchCityFragment() {
		ViewUtil.showKeyBoard(etInput, baseContext);
		tvCancle.setVisibility(View.VISIBLE);
		curentFragmentPosition = 1;
		FragmentTransaction transaction = fragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.out_alpha, 0);
		hideFragment(transaction);
		if (searchCityFragment == null) {
			searchCityFragment = new SearchCityFragment(searchHandler);

		}
		if (!searchCityFragment.isAdded()) {
			transaction.add(R.id.check_city_fragment_container,
					searchCityFragment).commitAllowingStateLoss();
		} else {
			transaction.show(searchCityFragment).commitAllowingStateLoss();
		}
	}

	*/
/**
	 * 
	 * Describe:隐藏FRAGMENT
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void hideFragment(FragmentTransaction transaction) {
		if (showCityListFragment != null) {
			transaction.hide(showCityListFragment);
		}
		if (searchCityFragment != null) {
			transaction.hide(searchCityFragment);
		}

	}

	public static class MessgaeHandler extends WeakHandler<CheckCityActivity> {

		public MessgaeHandler(CheckCityActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				*/
/**
				 * 取消搜索事件
				 *//*

				getReference().showCityListFragment();
				break;

			case 1001:
				*/
/**
				 * 处理选择完了城市消息
				 *//*

				Bundle dataBundle = msg.getData();
				if (dataBundle != null) {
					String cityString = dataBundle.getString("searchContent");
					// ToastUtil.showToast(getReference().baseContext,
					// cityString);
					// getReference().showCityListFragment();
					// getReference().showCityListFragment.tvPosition.setText(cityString);
					ViewUtil.hideKeyBoard(getReference().etInput,
							getReference().baseContext);
					int position = dataBundle.getInt("position");
					getReference().saveCheckCityInfor(cityString);
					StartActivityUtil.startActivity(getReference(),
							MainActivity.class);
					getReference().finish();
				}
				break;

			case 1002:
				Bundle searchDataBundle = msg.getData();
				String contentString = searchDataBundle.getString("DATA");
				getReference().searchData(contentString);
				break;
			case ConstantsUtil.HTTP_SUCCESS:
				*/
/**
				 * 处理成功的数据
				 *//*

				if (msg.obj != null && msg.obj instanceof CityDataList) {
					CityDataList data = (CityDataList) msg.obj;
					getReference().listObj = data.getList();
					*/
/**
					 * 本地数据持久化
					 *//*

					new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							HomeCitySQL.getInstance(getReference().baseContext)
							.saveCityInfor(getReference().listObj);
						}
					}.run();;
					
					if (getReference().listObj != null
							&& getReference().listObj.size() > 0) {
						for (CityData dataObj : getReference().listObj) {
							getReference().cityList.add(dataObj.getName());
						}
						getReference().showCityListFragment();
						LogUtil.d(TAG, data.getList().toString());
					} else {
						*/
/**
						 * 处理空数据
						 *//*

					}

				} else {
					*/
/**
					 * 处理空数据
					 *//*

				}

				break;
			case ConstantsUtil.HTTP_FAILE:
				*/
/**
				 * 处理失败的数据
				 *//*

				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					*/
/**
					 * 处理失败数据
					 *//*

				} else {
					ToastUtil.showToast(getReference(), "请求失败");
					getReference().showLoadFailView();
				}

				break;
			}
		}

	}

	*/
/**
	 * 
	 * Describe:缓存选择城市信息
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:liuzhouliang
	 *//*

	public void saveCheckCityInfor(String cityString) {
		// SharedPreferencesUtil.writeSharedPreferencesInt(this,
		// ContantsUtil.SAVE_CHECK_CITY_INFOR, key, value)
		MyApplication.currentCheckCity = cityString;
		final CityData obj = new CityData();
		obj.setName(cityString);
		if(listObj !=null ){
		int size = listObj.size();
		if(size>0){
		for (int i = 0; i < size; i++) {
			String nameString = listObj.get(i).getName();
			if (cityString.contains(nameString)) {
				obj.setId(listObj.get(i).getId());
			}
		 }
		}
		MyApplication.setCheckCityInfor(obj);
		new Runnable() {

			@Override
			public void run() {
				FileManager.saveObject(baseActivity, obj,
						ConstantsUtil.SAVE_CHECK_CITY_INFOR);
			}
		}.run();}
		// FileManager.saveObject(baseActivity, obj,
		// ConstantsUtil.SAVE_CHECK_CITY_INFOR);
		// ToastUtil.showToast(baseContext,
		// "name=" + obj.getName() + "id==" + obj.getId());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPause(this);
	}
}
*/
