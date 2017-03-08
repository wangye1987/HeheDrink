package com.heheys.ec.model.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:酒类单位数据适配器
 *
 * Date:2015年11月25日下午1:57:14
 *
 * Author:LZL
 *
 */
public class DrinksDemandUnitCheckAdapter extends BaseListAdapter<String> {
	private UnitCheckCallBack mcallBack;

	public DrinksDemandUnitCheckAdapter(List<String> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.drinks_demand_unit_check_item, parent, false);
		}
		TextView tvType = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.drinks_demand_unit_check_item_tv);
		tvType.setText(dataList.get(position));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
             if(mcallBack!=null){
            	 mcallBack.checkCallBack(dataList.get(position));
             }
			}
		});
		return convertView;
	}
	
	
	public interface UnitCheckCallBack{
		public void checkCallBack(String content);
		
	}
	
	public void setCheckListener(UnitCheckCallBack listener){
		mcallBack=listener;
		
	}

}
