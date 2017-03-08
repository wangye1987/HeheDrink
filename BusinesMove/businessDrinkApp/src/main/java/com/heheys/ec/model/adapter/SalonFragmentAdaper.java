package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;

/**
 * Describe:沙龙页面数据适配器
 * 
 * Date:2015-9-24
 * 
 * Author:liuzhouliang
 */
public class SalonFragmentAdaper extends BaseListAdapter<String> {
	private List<String> mList;

	public SalonFragmentAdaper(List<String> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		mList = data;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			convertView = baseInflater.inflate(R.layout.fragment_salon_item,
					parent, false);
		}

		TextView tView = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_salon_item_name);

		return convertView;
	}

}
