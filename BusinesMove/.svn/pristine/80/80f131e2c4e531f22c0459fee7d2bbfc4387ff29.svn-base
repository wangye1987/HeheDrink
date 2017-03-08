package com.heheys.ec.model.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.controller.activity.BrandWineActivity;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.BrandList;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:品牌列表数据适配器
 * 
 * Date:2015-10-12
 * 
 * Author:liuzhouliang
 */
public class GroupBuyBrandAdapter extends BaseListAdapter<BrandList> {
	private GridView mGridView;
	private CheckBrandListener listener;
	private GroupWholeSaleActivity mgroupBuyList;
	private GroupWholeSaleActivity mgroupWhole;
	private BrandWineActivity mbrandList;
	private String checkBrandId;
	private Context context;
	private String buyorsale;
//	public GroupBuyBrandAdapter(GridView gridView, List<BrandList> data,
//			Context context, GroupBuyListActivity groupBuyList) {
//		super(data, context);
//		// TODO Auto-generated constructor stub
//		mGridView = gridView;
//		mgroupBuyList = groupBuyList;
//		checkBrandId = mgroupBuyList.checkBrandId;
//		this.context = context;
//	}
	public GroupBuyBrandAdapter(GridView gridView, List<BrandList> data,
			Context context, GroupWholeSaleActivity mWholeSale) {
		super(data, context);
		// TODO Auto-generated constructor stub
		mGridView = gridView;
		mgroupWhole = mWholeSale;
//		checkBrandId = mgroupBuyList.checkBrandId;
		this.context = context;
	}
	public GroupBuyBrandAdapter(GridView gridView,String buyorsale, List<BrandList> data,
			Context context, BrandWineActivity mbrandList) {
		super(data, context);
		mGridView = gridView;
		checkBrandId = mbrandList.checkBrandId;
		this.context = context;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.groupbuy_list_brand_item, parent, false);
		}
		final TextView tv = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.groupbuy_list_brand_item_bt);
		String brandIdString = dataList.get(position).getId();
		if(checkBrandId!=null){
		if (checkBrandId.equals(brandIdString)) {
			tv.setPressed(true);
		} else {
			tv.setPressed(false);
		}}
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < mGridView.getChildCount(); i++) {
					View item = mGridView.getChildAt(i);
					TextView btChild = (TextView) item
							.findViewById(R.id.groupbuy_list_brand_item_bt);
					btChild.setTextColor(mcontext.getResources().getColor(
							R.color.color_565656));
					btChild.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
					HashMap<String,String> map = new HashMap<String,String>();
					if("buy".equals(buyorsale)){
						map.put("Buy_brand_brand","");
						MobclickAgent.onEvent(context, "Buy_brand_brand", map); 
					}else{
						map.put("Sell_brand_brand","");
						MobclickAgent.onEvent(context, "Buy_brand_brand", map); 
					}
				}
				listener.checkCallBack(dataList.get(position));
			}
		});
		tv.setText(dataList.get(position).getName());
		return convertView;
	}

	public interface CheckBrandListener {
		public void checkCallBack(BrandList obj);
	}

	public void setCheckListener(CheckBrandListener listener) {
		this.listener = listener;
	}
}
