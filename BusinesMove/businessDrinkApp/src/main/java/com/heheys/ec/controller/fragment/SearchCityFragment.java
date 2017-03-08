/*
package com.heheys.ec.controller.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.heheys.ec.R;
import com.heheys.ec.controller.activity.CheckCityActivity.MessgaeHandler;
import com.heheys.ec.model.adapter.SearchCityAdaper;
import com.heheys.ec.model.dataBean.AddressModel;
import com.heheys.ec.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

*/
/**
 * DESCRIBE:搜索页面
 * 
 * AUTHOR:liuzhouliang
 * 
 * TIME:2014-3-10下午1:58:17
 *//*


public class SearchCityFragment extends Fragment {
	private View parentView;
	public MessgaeHandler searchHandler;
	private ListView lv;
	private SearchCityAdaper searchAdaper;
	private List<AddressModel> filterDateList;
	// 标记是否为搜索事件
	// public boolean tagIsSearch = false;
	public List<AddressModel> newData;

	public SearchCityFragment(MessgaeHandler handler) {
		super();
		// TODO Auto-generated constructor stub
		searchHandler = handler;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		parentView = inflater.inflate(R.layout.search_city_fragment, container,
				false);
		initView(parentView);
		bindViewData();
		// setViewListener();
		return parentView;
	}

	*/
/**
	 * 
	 * Describe:初始化控件
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void initView(View parentView) {

		lv = (ListView) parentView.findViewById(R.id.search_city_fragment_lv);
		filterDateList = new ArrayList<AddressModel>();
		searchAdaper = new SearchCityAdaper(this.getActivity(), filterDateList,
				searchHandler);

	}

	*/
/**
	 * 
	 * Describe:绑定数据
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	private void bindViewData() {
		lv.setAdapter(searchAdaper);
	}

	*/
/**
	 * 
	 * Describe:设置控件监听
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	// private void setViewListener() {
	// lv.setOnItemClickListener(this);
	// }

	*/
/**
	 * 
	 * Describe:处理搜索时候的数据
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	public void searchCity(String content) {
		searchData(content);
	}

	*/
/**
	 * 
	 * Describe:刷新数据
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*

	public void searchData(String filterStr) {

		if (TextUtils.isEmpty(filterStr)) {
			// 输入为空显示初始数据
			filterDateList = null;
			searchAdaper.setNewData(filterDateList);
			searchAdaper.notifyDataSetChanged();
			return;
		} else {
			*/
/**
			 * 输入不为空，清除源数据，根据输入值进行遍历匹配
			 *//*

			newData = new ArrayList<AddressModel>();
			for (AddressModel sortModel : ShowCityFragment.sourceDataList) {
				String name = sortModel.getName();
				String abbreviation = sortModel.getAbbreviation();
				if (name.indexOf(filterStr.toString()) != -1
						|| ShowCityFragment.characterParser.getSelling(name)
								.startsWith(filterStr.toString())
						|| abbreviation.equals(filterStr)) {

					newData.add(sortModel);
				}
			}
			if (newData.size() == 0) {
				ToastUtil.showToast(getContext(), "没有该地区");
				filterDateList.clear();
				searchAdaper.notifyDataSetChanged();
				return;
			}
		}
		filterDateList = newData;
		searchAdaper.setNewData(filterDateList);
		searchAdaper.notifyDataSetChanged();
		*/
/**
		 * 设置为可搜索状态
		 *//*

		// CheckCityActivity.tvCancle.setText("搜索");
		// tagIsSearch = true;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SearchCityFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SearchCityFragment");
	}
}
*/
