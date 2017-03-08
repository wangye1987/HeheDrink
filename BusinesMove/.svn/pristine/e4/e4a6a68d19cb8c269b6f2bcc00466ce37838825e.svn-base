package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.SuitItem;

/**
 * @author wangkui
 *  我的订单 套装适配器 列表进入
 */
public class MyOrderSuitAdapter extends BaseListAdapter<SuitItem>{


	private ImageView iv_suit;
	private TextView tv_name;
	private TextView tv_price_unit;
	private TextView tv_unit_every;
	private TextView tv_every_price;
	private SuitItem suitItem;

	public MyOrderSuitAdapter(List<SuitItem> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		suitItem = dataList.get(position);
		if(convertView == null){
			convertView = baseInflater.inflate(R.layout.orderdetail_suit_item, parent, false);
		}
		iv_suit = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_suit);
		tv_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_name);
		tv_price_unit = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_price_unit);
		tv_unit_every = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_unit_every);
		tv_every_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_every_price);
		MyApplication.imageLoader.displayImage(suitItem.getPic(), iv_suit,
				MyApplication.optionsGoodsDetail);
		tv_name.setText(StringUtil.isEmpty(suitItem.getName())?"":suitItem.getName());
		
		//每套该商品价格
		tv_price_unit.setText(StringUtil.isEmpty(suitItem.getCurrentprice())?"":suitItem.getCurrentprice());
		//每套有多少件该商品
		tv_unit_every.setText(StringUtil.isEmpty(suitItem.getNumPerSuitInfo())?"":suitItem.getNumPerSuitInfo());
		//每套有该商品合计价格
		tv_every_price.setText(StringUtil.isEmpty(suitItem.getNumPerSuitInfo())?"":suitItem.getNumPerSuitInfo());
		return convertView;
	}
}
