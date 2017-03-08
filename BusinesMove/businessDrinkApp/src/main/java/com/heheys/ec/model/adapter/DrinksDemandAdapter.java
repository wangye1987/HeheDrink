package com.heheys.ec.model.adapter;

import java.util.List;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.DrinksDemandBean.DrinksDemandData.DrinksDemandInfor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Describe:酒水需求数据适配器
 *
 * Date:2015年11月24日下午3:18:09
 *
 * Author:LZL
 *
 */
public class DrinksDemandAdapter extends BaseListAdapter<DrinksDemandInfor> {

	public DrinksDemandAdapter(List<DrinksDemandInfor> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.drinks_demand_item,
					parent, false);
		}
		ImageView ivType = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.drinks_demand_item_type);
		TextView tvCreateTime = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.drinks_demand_item_create_time);
		TextView tvName = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.drinks_demand_item_name);
		TextView tvNeedTime = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.drinks_demand_item_need_time);
		TextView tvNeedNum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.drinks_demand_item_need_num);
		TextView tvNeedPrice = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.drinks_demand_item_need_price);
		tvName.setText(dataList.get(position).getDrinksName());
		tvNeedNum.setText(dataList.get(position).getDrinksNums()+dataList.get(position).getNumsUnit());
		tvNeedPrice.setText(dataList.get(position).getDrinksPrice()+"元/"+dataList.get(position).getNumsUnit());
		String type = dataList.get(position).getTradeMark();
		int typeInt = 1;
		if (!StringUtil.isEmpty(type)) {
			typeInt = Integer.parseInt(type);
		}
		switch (typeInt) {
		case 0:
			/**
			 * 卖酒需求
			 */
			ivType.setImageResource(R.drawable.wine_demand_icon_sell);
			break;
		case 1:
			/**
			 * 买酒需求
			 */
			ivType.setImageResource(R.drawable.wine_demand_icon_buy);
			break;
		}
		tvCreateTime.setText(dataList.get(position).getCreateTime());
		tvNeedTime.setText(dataList.get(position).getStartTime() + "-"
				+ dataList.get(position).getEndTime());
		return convertView;
	}

}
