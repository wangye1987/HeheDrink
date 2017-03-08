package com.heheys.ec.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.MyhhMoneyBaseBean.RechargeCard;

import java.util.List;

public class RechangeAdapter extends BaseListAdapter<RechargeCard> {

	//面值
	private TextView tv_money;
	//面值需要支付的金额
	private TextView tv_needmoney;
	//喝喝币
	private TextView hb;
	//售价
	private TextView tv_price;

	//当前选择的面值
	private int mSelect;
	
	public RechangeAdapter(List<RechargeCard> data, Context context) {
		super(data, context);
	}

	public void setbg(int positon){
		if(positon != mSelect){
			mSelect = positon;
		     notifyDataSetChanged();
		     }
	}
	@SuppressLint("NewApi") @Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		RechargeCard rechange = dataList.get(position);
		if(convertView == null){
			convertView = baseInflater.inflate(R.layout.hehe_rechange_item, parent, false);
		}
		hb = (TextView) ViewHolderUtil.getItemView(convertView, R.id.hb);
		tv_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_price);
		tv_money = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_money);
		tv_needmoney = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_needmoney);
		tv_money.setText(rechange.getCoin());
		tv_needmoney.setText(rechange.getPrice());
		//不是当前选中的面值
		if(position == mSelect){
			convertView.setBackground(mcontext.getResources().getDrawable(R.drawable.rechang_red_bg));
			setafbg(R.color.color_FF3838);
		}else{
			convertView.setBackground(mcontext.getResources().getDrawable(R.drawable.rechange_bg));
			setafbg(R.color.color_2b2b2b);
		}
		return convertView;
	}
	
	void setafbg(int color){
		hb.setTextColor(mcontext.getResources().getColor(color));
		tv_price.setTextColor(mcontext.getResources().getColor(color));
		tv_money.setTextColor(mcontext.getResources().getColor(color));
		tv_needmoney.setTextColor(mcontext.getResources().getColor(color));
	}
}