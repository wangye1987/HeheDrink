package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.CityBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.ProvinceList;

public class AreaAdapter extends BaseListAdapter<CityBean>{
	private Context context;
	public AreaAdapter(List data, Context context) {
		super(data, context);
		this.context = context;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final CityBean bean =  dataList.get(position);
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.spinner_item, parent,
					false);
		}
		TextView text1 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_spinner);
		text1.setText(bean.getName());
		return convertView;
	}

}

