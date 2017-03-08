package com.heheys.ec.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.BrandBaseBean.BrandBean;

import java.util.List;

public class WineBrandAdapter extends BaseListAdapter<BrandBean> {
	
	private TextView tv_brand_name;
	private TextView tv_left_red;
	private int mSelect;
	public WineBrandAdapter(List<BrandBean> data, Context context) {
		super(data, context);
	}
	 public void changeSelected(int positon){ //刷新方法
	     if(positon != mSelect){
	      mSelect = positon;
	     notifyDataSetChanged();
	     }
	    }
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		BrandBean wineBean = dataList.get(position);
		if(convertView == null)
		{
			convertView = baseInflater.inflate(R.layout.sx_item, parent, false);
		}
		tv_brand_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_brand_name);
		tv_left_red = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_left_red);
		
		tv_brand_name.setText(wineBean.getName());
		
		 if(mSelect==position){    
			  convertView.setBackgroundResource(R.color.white);  //选中项背景
			  tv_brand_name.setTextColor(mcontext.getResources().getColor(R.color.color_333333));
			  tv_left_red.setVisibility(View.VISIBLE);
	        }else{
	        	convertView.setBackgroundResource(R.color.color_ececec);  //其他项背景
	        	tv_brand_name.setTextColor(mcontext.getResources().getColor(R.color.color_666666));
	        	tv_left_red.setVisibility(View.INVISIBLE);
	        }
		return convertView;
	}

}