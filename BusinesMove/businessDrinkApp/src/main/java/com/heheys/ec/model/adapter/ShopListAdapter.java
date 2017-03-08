package com.heheys.ec.model.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.ShopListDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.ShopListBaseBean;
import com.heheys.ec.model.dataBean.ShopListBaseBean.ShopListResult;
import com.heheys.ec.model.dataBean.ShopListBaseBean.ShopResult;
import com.heheys.ec.utils.ToastNoNetWork;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-31 下午5:48:01
 *  类说明
 */
public class ShopListAdapter extends BaseListAdapter<ShopResult> {

	private ImageView iv_pic;
	private TextView iv_name;
	private RatingBar rb_star;
	private TextView sale_num;
	private TextView tv_desc;

	public ShopListAdapter(List<ShopResult> data, Context context) {
		super(data, context);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = baseInflater.inflate(R.layout.shop_list_item, parent, false);
		}
		final ShopResult bean = dataList.get(position);
		iv_pic = (ImageView)ViewHolderUtil.getItemView(convertView, R.id.iv_pic);
		iv_name = (TextView)ViewHolderUtil.getItemView(convertView, R.id.iv_name);
		sale_num = (TextView)ViewHolderUtil.getItemView(convertView, R.id.sale_num);
		rb_star = (RatingBar)ViewHolderUtil.getItemView(convertView, R.id.rb_star);
		tv_desc = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_desc);
		iv_name.setText(bean.getName());
		rb_star.setRating(Float.parseFloat(bean.getStarnum()));
		tv_desc.setText(bean.getDesc());
		sale_num.setText(bean.getPronum());
		MyApplication.imageLoader.displayImage(bean.getIcon(), iv_pic, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(ToastNoNetWork.ToastError( mcontext))
					return;
				Intent intent = new Intent( mcontext, ShopListDetailActivity.class);
				intent.putExtra("shopid", bean.getShopid());
				StartActivityUtil.startActivity((Activity) mcontext,intent);
				MobclickAgent.onEvent(mcontext,"C_SHP_LST_3");
			}
		});
		return convertView;
	}

}
