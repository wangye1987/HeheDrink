package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.PoductDetailBaseInforBean;

/**
 * Describe:商品详情基础信息数据适配器
 * 
 * Date:2015-10-14
 * 
 * Author:liuzhouliang
 */
public class PoductDetailBaseInforAdapter extends
		BaseListAdapter<PoductDetailBaseInforBean> {

	public PoductDetailBaseInforAdapter(List<PoductDetailBaseInforBean> data,
			Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.product_detail_des_item, parent,false);
		}
		TextView tvNameView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.product_detail_des_item_name);
		TextView tvContentView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.product_detail_des_item_content);
		tvNameView.setText(dataList.get(position).getNameString());
		tvContentView.setText(dataList.get(position).getContenString());
		return convertView;
	}

}
