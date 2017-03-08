/*
package com.heheys.ec.model.adapter;

import java.util.List;

import com.heheys.ec.R;
//import com.heheys.ec.controller.activity.CheckCityActivity.MessgaeHandler;
import com.heheys.ec.model.dataBean.AddressModel;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

*/
/**
 * DESCRIBE:搜索联想数据适配器
 * 
 * AUTHOR:liuzhouliang
 * 
 * TIME:2014-3-28上午10:26:58
 *//*


public class SearchCityAdaper extends BaseAdapter {
	private Context mContext;
	private LayoutInflater li;
	private List<AddressModel> mdata;
	private MessgaeHandler mHandler;

	public SearchCityAdaper(Context context, List<AddressModel> data,
			MessgaeHandler handler) {
		super();
		this.mContext = context;
		li = LayoutInflater.from(mContext);
		this.mdata = data;
		mHandler = handler;
	}

	public void setNewData(List<AddressModel> data) {
		this.mdata = data;

	};

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return mdata == null ? 0 : mdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return mdata == null ? position : mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = li.inflate(R.layout.position_search_list_item, null);
		TextView tv = (TextView) convertView
				.findViewById(R.id.position_search_list_item_tv);
		tv.setText(mdata.get(position).getName());
		setCheckPositionListener(convertView, mdata.get(position).getName(),position);
		return convertView;
	}

	private void setCheckPositionListener(View item, final String city,final int position) {
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message message = Message.obtain();
				message.what = 1001;
				Bundle dataBundle = new Bundle();
				dataBundle.putString("searchContent", city);
				dataBundle.putInt("position", position);
				message.setData(dataBundle);
				if (mHandler != null) {
					mHandler.sendMessage(message);
				}
			}
		});
	}
	
	
}
*/
