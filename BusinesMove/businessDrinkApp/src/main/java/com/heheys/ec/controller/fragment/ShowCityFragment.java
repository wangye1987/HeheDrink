/*
package com.heheys.ec.controller.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.controller.activity.CheckCityActivity;
import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.ShowCityAdapter;
import com.heheys.ec.model.dataBean.AddressModel;
import com.heheys.ec.utils.CharacterParser;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.PinyinComparator;
import com.heheys.ec.view.SideView;
import com.heheys.ec.view.SideView.OnTouchingLetterChangedListener;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

*/
/**
 * 
 * Describe:城市展示页面
 * 
 * Date:2015-9-22
 * 
 * Author:liuzhouliang
 *//*


public class ShowCityFragment extends BaseFragment {
	private View parentView;

	private ShowCityAdapter adapter;
	// 汉字转换成拼音的类
	public static CharacterParser characterParser;
	// 转换成拼音的数据对象的集合
	public static List<AddressModel> sourceDataList;
	// 根据拼音来排列ListView里面的数据类
	private PinyinComparator pinyinComparator;
	// 右边显示字符栏的视图
	private SideView sideView;
	// 提示选择控件
	private TextView tvToast;
	private String strPosition;
	public static MessageHandler messageHandler;;
	private ListView mListView;
	private List<String> mList;
	private TextView tvLocation, tvHotCity, tvCurrentPosition;
	private View headView;
	private RelativeLayout currentCityParent;

	private RelativeLayout re_main;

	public ShowCityFragment(List<String> data) {
		super();
		mList = data;
	}

	@Override
	protected boolean isShowLeftIcon() {
		return false;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = baseInflate.inflate(R.layout.show_city_fragment,
				container, true);
		initView(parentView);
		initData();
		return parentView;
	}

	*/
/**
	 * 
	 * Describe:初始化控件
	 * 
	 * Date:2015-9-22
	 * 
	 * Author:liuzhouliang
	 *//*

	private void initView(View parentView) {
		// TODO Auto-generated method stub
		headView = baseInflate.inflate(R.layout.show_city_list_head, null);
		currentCityParent = (RelativeLayout) headView
				.findViewById(R.id.show_city_list_head_current_parent);
		tvHotCity = (TextView) headView
				.findViewById(R.id.show_city_fragment_hotcity);
		tvLocation = (TextView) headView
				.findViewById(R.id.show_city_fragment_location_city);
		sideView = (SideView) parentView
				.findViewById(R.id.show_city_fragment_sideview);
		tvToast = (TextView) parentView
				.findViewById(R.id.show_city_fragment_taost);
		re_main = (RelativeLayout) parentView
				.findViewById(R.id.base_activity_load_fail_main);
		sideView.setTextView(tvToast);
		mListView = (ListView) parentView
				.findViewById(R.id.show_city_fragment_lv);
		tvCurrentPosition = (TextView) headView
				.findViewById(R.id.show_city_fragment_position);
		re_main.setOnClickListener(this);
		CheckCityActivity activity = (CheckCityActivity) getActivity();
		if (ConstantsUtil.CHECK_CITY_FROM_MAIN.equals(activity.fromWhere)) {
			*/
/**
			 * 主页面进入，显示当前城市
			 *//*

			currentCityParent.setVisibility(View.VISIBLE);
		}
		if (ConstantsUtil.CHECK_CITY_FROM_GUIDE.equals(activity.fromWhere)) {
			*/
/**
			 * 第一次进入，隐藏当前城市
			 *//*

			currentCityParent.setVisibility(View.GONE);

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
		MyApplication application = (MyApplication) getActivity()
				.getApplication();
		application.mLocationResult = tvLocation;
		application.tvCurrentCity = tvCurrentPosition;
		if (!(StringUtil.isEmpty(application.locationCity))) {
			tvLocation.setText(application.locationCity);
		} else {
		}
		if (!(StringUtil.isEmpty(MyApplication.currentCheckCity))) {
			tvCurrentPosition.setText(MyApplication.currentCheckCity);
		}
		// tvCurrentPosition
		// .setText(application.currentCheckCity);
		messageHandler = new MessageHandler(this);
		pinyinComparator = new PinyinComparator();
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		// 源数据转换成拼音后的数据
		sourceDataList = changeSourceData(mList);
		// 根据a-z进行排序源数据
		Collections.sort(sourceDataList, pinyinComparator);
		adapter = new ShowCityAdapter(this.getActivity(), sourceDataList,
				messageHandler);
		mListView.addHeaderView(headView);
		mListView.setAdapter(adapter);

	}

	*/
/**
	 * 
	 * Describe:对源数据进行汉字转拼音
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private List<AddressModel> changeSourceData(List<String> data) {
		List<AddressModel> mSortList = new ArrayList<AddressModel>();

		for (int i = 0; i < data.size(); i++) {
			AddressModel sortModel = new AddressModel();
			sortModel.setName(data.get(i));
			// 地址汉字转换成拼音
			String pinyin = characterParser.getSelling(data.get(i));
			String abbreviation = getAbbreviation(data.get(i));
			sortModel.setAbbreviation(abbreviation);
			// 截取拼音首位将字母转换成大写
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	*/
/**
	 * 
	 * Describe:获取缩写名
	 * 
	 * Date:2015-9-22
	 * 
	 * Author:liuzhouliang
	 *//*

	private String getAbbreviation(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		if (string != null) {
			char[] singleName = string.toCharArray();
			for (char ch : singleName) {
				String pinyin = characterParser.getSelling(ch + "");
				if (pinyin != null) {
					sb.append(pinyin.charAt(0));
				}
			}
		} else {
			return "";
		}
		return sb.toString();
	}

	@Override
	protected void getNetData() {
	}

	@Override
	protected boolean hasTitle() {
		return false;
	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected String setTitleName() {
		return "";
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
	protected void setViewListener() {

		*/
/**
		 * 设置右侧触摸监听
		 *//*

		sideView.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				//城市数据统计
				HashMap<String,String> map = new HashMap<String,String>();
				if(!StringUtil.isEmpty(s))
				map.put("citypingyin",s);
				MobclickAgent.onEvent(baseActivity, "C_INIT_CTY_3", map); 
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					// LISTVIEW滚动到指定item
					mListView.setSelection(position);
				}

			}
		});
		tvLocation.setOnClickListener(this);
		tvCurrentPosition.setOnClickListener(this);
		tvHotCity.setOnClickListener(this);
	}

	public static class MessageHandler extends WeakHandler<ShowCityFragment> {

		public MessageHandler(ShowCityFragment reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1002:
				*/
/**
				 * 处理选择完了城市消息
				 *//*

				Bundle dataBundle = msg.getData();
				if (dataBundle != null) {
					String cityString = dataBundle.getString("searchContent");
					// ToastUtil
					// .showToast(getReference().getContext(), cityString);
					int position = dataBundle.getInt("position");
					CheckCityActivity activity = (CheckCityActivity) getReference()
							.getActivity();
					activity.saveCheckCityInfor(cityString);
					StartActivityUtil.startActivity(getReference()
							.getActivity(), MainActivity.class);
					getReference().getActivity().finish();
					break;
				}
			}
		}
	}

	@Override
	protected boolean hasTitleIcon() {
		return false;
	}

	@Override
	protected boolean hasDownIcon() {
		return false;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.show_city_fragment_location_city:
			*/
/**
			 * 点击已经定位城市
			 *//*

			if (!StringUtil.isEmpty(tvLocation.getText().toString())
					&& !tvLocation.getText().toString().equals("定位失败")) {
				//城市数据统计
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("citycurrt",tvLocation.getText().toString());
				MobclickAgent.onEvent(baseActivity, "0004", map); 
				CheckCityActivity activity = (CheckCityActivity) getActivity();
				activity.saveCheckCityInfor(tvLocation.getText().toString());
//				if(IsLogin.isLogin(baseActivity)){
				StartActivityUtil.startActivity(getActivity(),
						MainActivity.class);
//				}else{
//					StartActivityUtil.startActivity(getActivity(),
//							LoginActivity.class);
//				}
				getActivity().finish();
			}

			break;

		case R.id.show_city_fragment_hotcity:
			*/
/**
			 * 热门城市事件
			 *//*

			if (!StringUtil.isEmpty(tvHotCity.getText().toString())) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("citycurrthot",tvHotCity.getText().toString());
				MobclickAgent.onEvent(baseActivity, "0005", map); 
				CheckCityActivity activity = (CheckCityActivity) getActivity();
				activity.saveCheckCityInfor(tvHotCity.getText().toString());
//				if(IsLogin.isLogin(baseActivity)){
				StartActivityUtil.startActivity(getActivity(),
						MainActivity.class);
//				}else{
//					StartActivityUtil.startActivity(getActivity(),
//							LoginActivity.class);
//				}
				getActivity().finish();
			}
			MobclickAgent.onEvent(baseActivity,"C_INIT_CTY_2");//热门城市
			break;

		case R.id.show_city_fragment_position:
			*/
/**
			 * 当前定位城市
			 *//*

			if (!StringUtil.isEmpty(tvCurrentPosition.getText().toString())) {
				//城市数据统计
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("citycurrt",tvCurrentPosition.getText().toString());
				MobclickAgent.onEvent(baseActivity, "0003", map); 
				CheckCityActivity activity = (CheckCityActivity) getActivity();
				activity.saveCheckCityInfor(tvCurrentPosition.getText()
						.toString());
//				if(IsLogin.isLogin(baseActivity)){
				StartActivityUtil.startActivity(getActivity(),
						MainActivity.class);
//				}else{
//					StartActivityUtil.startActivity(getActivity(),
//							LoginActivity.class);
//				}
				getActivity().finish();
			}
			break;
		case R.id.base_activity_load_fail_main:
			//直接到主界面
			StartActivityUtil.startActivity(baseActivity, MainActivity.class);
			getActivity().finish();
			break;
		default:
			break;
		}
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("ShowCityFragment");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("ShowCityFragment");
	}
}
*/
