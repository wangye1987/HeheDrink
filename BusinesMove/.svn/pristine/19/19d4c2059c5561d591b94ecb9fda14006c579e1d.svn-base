package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.CustomerBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.ProvinceList;

public class ProviceAdapter extends BaseListAdapter<ProvinceList>{
	private Context context;
	public ProviceAdapter(List data, Context context) {
		super(data, context);
		this.context = context;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ProvinceList bean =  dataList.get(position);
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.spinner_item, parent,
					false);
		}
		TextView text1 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_spinner);
		text1.setText(bean.getProvince().getName());
		return convertView;
	}
}
