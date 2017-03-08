package com.heheys.ec.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.PointsBaseBean.Poinitem;
import com.heheys.ec.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyPointsAdapter extends BaseListAdapter<Poinitem> {
	Context mContext;
	private TextView tv_point;
	private TextView tv_point_detail;
	private TextView tv_point_time;

	public MyPointsAdapter(List<Poinitem> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	
	String Data(long milliseconds){
		Date date =new Date(milliseconds);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-DD HH-MM", Locale.US);
		return sd.format(date);
	}
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = baseInflater.inflate(R.layout.point_item, parent, false);
		}
		Poinitem bean = dataList.get(position);
		tv_point = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_point);
		tv_point_detail = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_point_detail);
		tv_point_time = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_point_time);
		if(!StringUtil.isEmpty(bean.getSourcesInfo()))
			tv_point_detail.setText(bean.getSourcesType()+bean.getSourcesInfo());
		else
			tv_point_detail.setText(bean.getSourcesType());
		tv_point_time.setText(bean.getCreateTime());
		if(bean.getScore()>0){
			tv_point.setTextColor(mContext.getResources().getColor(R.color.color_FBBE1B));
			tv_point.setText("+"+Utils.DoPriceInt(bean.getScore()));
		}else{
			tv_point.setTextColor(mContext.getResources().getColor(R.color.color_333333));
			tv_point.setText(" "+Utils.DoPriceInt(bean.getScore()));
		}
		return convertView;
	}
}
