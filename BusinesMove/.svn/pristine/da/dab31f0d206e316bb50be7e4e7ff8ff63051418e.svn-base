package com.heheys.ec.model.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.controller.activity.CouponActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.CouponBaseBean.Couponbean.couponitem;

/**
 * @author wangkui
 *  
 *  @version 优惠券适配器
 */
public class CouponMessageAdapter extends BaseListAdapter<couponitem> {

	Activity mActivity;
	private TextView tv_title;
	private TextView tv_content;
	public CouponMessageAdapter(ArrayList<couponitem> list,Activity mActivity){
		super(list, mActivity);
		this.mActivity = mActivity;
	}
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		couponitem bean = dataList.get(position);
		if(convertView == null){
			convertView = baseInflater.inflate(R.layout.coupon_msg_item, parent, false);
		}
		tv_title = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_title);
		tv_content = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_content);
		tv_title.setText(bean.getCouponTitle());
		tv_content.setText(bean.getCouponContent());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StartActivityUtil.startActivity(mActivity, CouponActivity.class);
			}
		});
		return convertView;
	}

}
