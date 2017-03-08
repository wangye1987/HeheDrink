package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.CityChioceBean;

public class CityListAdapter extends  BaseListAdapter<CityChioceBean>{

	private TextView tv_city;
	private ImageView iv_check;
	private List<CityChioceBean> data;
	private Context context;
	private CityChioceBean bean;
	private int type;
	public CityListAdapter(List<CityChioceBean> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}
	
	/**type.equal("sale")
	 * 
	 * @param type
	 * 
	 * 通过处理type值显示是多选还是单选
	 */
	public void setType(int type){
		this.type = type;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(dataList!=null && dataList.size()>0){
			bean = dataList.get(position);
		}
		if(convertView==null){
			convertView = baseInflater.inflate(R.layout.city_item, parent, false);
		}
		tv_city = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_city);
		iv_check = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_check);
		if(bean!=null){
			tv_city.setText(bean.getName());
		}
		//处理多选情况 买货单可以多选 买货单不能
//		if(2==type){
			if(bean.getIsCheck())
				iv_check.setVisibility(View.VISIBLE);
			else
				iv_check.setVisibility(View.INVISIBLE);
//		}
		return convertView;
	}

}
