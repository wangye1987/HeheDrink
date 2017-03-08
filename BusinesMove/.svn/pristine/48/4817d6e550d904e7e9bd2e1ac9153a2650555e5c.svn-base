package com.heheys.ec.model.adapter; 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.model.dataBean.WineBean;

import java.util.List;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-24 下午2:14:59 
 * 类说明 酒类需求适配器
 * @param
 */
public class WineAdapter extends BaseAdapter {
	Context context;
	List<WineBean> list;
	private LayoutInflater inflater;
	private View view;

	public WineAdapter(Context context,List<WineBean> list){
		this.context = context;
		this.list = list;
//		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		 ViewHolder holder = null;
		 WineBean bean  = list.get(position);
		// TODO Auto-generated method stub
		if(contentView==null){
			holder = new ViewHolder();
			contentView = LayoutInflater.from(context).inflate(R.layout.wine_item,null);
			holder.wine_name = (TextView) contentView.findViewById(R.id.wine_name);
			holder.wine_num = (TextView) contentView.findViewById(R.id.wine_num);
			holder.wine_date = (TextView) contentView.findViewById(R.id.wine_date);
			holder.wine_mark = (TextView) contentView.findViewById(R.id.wine_mark);
			contentView.setTag(holder);
		}else{
			holder = (ViewHolder) contentView.getTag();
		}
		holder.wine_name.setText(bean.getWineName());
		holder.wine_num.setText(bean.getWineNum());
		holder.wine_date.setText(bean.getWineDate());
		holder.wine_mark.setText(bean.getWineRemark());
		
		return contentView;
	}
	
	public class ViewHolder{
		TextView wine_name;
		TextView wine_num;
		TextView wine_date;
		TextView wine_mark;
	}

}
 