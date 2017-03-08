package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.view.GridViewScrollview;

/**
 * Describe:酒水需求种类数据适配器
 *
 * Date:2015年11月25日下午1:08:05
 *
 * Author:LZL
 *
 */
public class DrinksDemandTypeAdapter extends BaseListAdapter<String> {
	private GridViewScrollview mGridView;
	private WineTypeCheckListener checkListener;

	public DrinksDemandTypeAdapter(List<String> data, Context context,
			GridViewScrollview gv) {
		super(data, context);
		// TODO Auto-generated constructor stub
		mGridView = gv;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.drinks_demand_brand_item, parent, false);
		}
		final TextView tvBrand = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.drinks_demand_brand_item_tv);
		tvBrand.setText(dataList.get(position));
        
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int size=mGridView.getChildCount();
				for(int i=0;i<size;i++){
					View view=mGridView.getChildAt(i);
					TextView tv=(TextView) view.findViewById(R.id.drinks_demand_brand_item_tv);
					tv.setTextColor(mcontext.getResources().getColor(
							R.color.color_565656));
					tv.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
				}
				tvBrand.setTextColor(mcontext.getResources().getColor(
						R.color.color_f9883d));
				tvBrand.setBackgroundResource(R.drawable.groupbuy_list_item_bg_select);
				checkListener.checkWineType(position);
			}
		});
		return convertView;
	}
	
	public interface WineTypeCheckListener{
		public void checkWineType(int type);
	}
	
	public void setWineTypeCheckListener(WineTypeCheckListener listener){
		checkListener =listener;
	}

}
