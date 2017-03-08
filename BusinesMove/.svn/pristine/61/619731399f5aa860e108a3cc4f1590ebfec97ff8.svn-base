package com.heheys.ec.model.adapter; 

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-12-3 下午4:27:44 
 * 类说明 
 * @param 地址选择界面适配器
 */
public class AdapterDistrict extends BaseAdapter {

	private List<String> data;
	private List<String> data_id;
	private Context mContext;
	private boolean isEdit;
	private CallbackDistrict callback;
	public AdapterDistrict(Context mContext,List<String> data,List<String> data_id,CallbackDistrict callback) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.data_id = data_id;
		this.mContext = mContext; 
		this.callback = callback;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void  isEdit(boolean isEdit){
		this.isEdit = isEdit;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final String id_district = data.get(position);
		final int index = position;
		ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView  = LinearLayout.inflate(mContext, R.layout.district_item, null);
				holder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
				holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
			    convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_city.setText(id_district);
			if(isEdit){
				holder.iv_delete.setVisibility(View.VISIBLE);
			}else{
				holder.iv_delete.setVisibility(View.INVISIBLE);
			}
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					data.remove(index);
					data_id.remove(index);
					callback.setBack(id_district);
					notifyDataSetChanged();
				}
			});
		return convertView;
	}

	public class ViewHolder{
		TextView  tv_city;
		ImageView iv_delete;
	}
	
	public interface CallbackDistrict{
		void setBack(String id);
	}
	
}
 