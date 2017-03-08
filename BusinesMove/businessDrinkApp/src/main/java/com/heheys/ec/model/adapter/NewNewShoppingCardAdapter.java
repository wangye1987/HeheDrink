package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.view.SoftEditText;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-4-1 下午2:49:47
 *  类说明
 */
public class NewNewShoppingCardAdapter extends BaseListAdapter<NewProductInfo> {

	public NewNewShoppingCardAdapter(List<NewProductInfo> data,
			Context context) {
		super(data, context);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.new_fragment_shopping_cart_item, parent, false);
		}
		final NewProductInfo bean = dataList.get(position);
		final CheckBox cb = (CheckBox) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_cb);
		TextView tv_wine_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_wine_name);//名字
//		LinearLayout linear_gg = (LinearLayout) ViewHolderUtil.getItemView(
//				convertView, R.id.linear_gg);//规格视图
		TextView tv_price_now = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price_now);//当前价
		TextView tv_price_unit_now = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price_unit_now);//当前价
		TextView tv_price_unit = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price_unit);//
		TextView tv_dj = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_dj);//
		TextView tv_price = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price);//
		TextView tv_unit = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_unit);//定金单位
		TextView tv_guige = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_guige);//定金单位
		TextView tv_notice_unit = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_notice_unit);//定金单位
//		TextView tv_chandi = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.tv_chandi);//定金单位
		ImageView tvReduce = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_num_reduce);
		ImageView iv_pin = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_pin);
		LinearLayout linear_price_tv = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_price_tv);
		LinearLayout linear_dqj = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_dqj);
		final ImageView tvAdd = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_num_add);
		final SoftEditText etProductNum = (SoftEditText) ViewHolderUtil
				.getItemView(convertView,
						R.id.fragment_shopping_cart_item_num_et);
		final ImageView iView = (ImageView) convertView
				.findViewById(R.id.iv_wineurl);
		
		tv_wine_name.setText(bean.getName());
		tv_price.setText(bean.getCprice());
		tv_unit.setText("/"+bean.getUnit());
		ViewUtil.setActivityPrice(tv_price, bean.getCurrentprice());
		if("箱".equals(bean.getUnit())){
			tv_notice_unit.setText(bean.getUnit()+"/瓶");
		}else{
			tv_notice_unit.setText(bean.getUnit());
		}
	
		if("0".equals(bean.getType())){
			
			//拼单
			iv_pin.setVisibility(View.VISIBLE);
			linear_dqj.setVisibility(View.VISIBLE);//显示当前价 视图
			tv_price_unit_now.setVisibility(View.VISIBLE);
			tv_price_unit.setVisibility(View.VISIBLE);
			linear_price_tv.setVisibility(View.VISIBLE);
			tv_price.setVisibility(View.VISIBLE);
			tv_unit.setVisibility(View.VISIBLE);
//			linear_gg.setVisibility(View.GONE);
			ViewUtil.setActivityPrice(tv_price_unit_now, bean.getCurrentprice());//当前价
			tv_price_unit.setText("/"+bean.getUnit());//当前价单位
			tv_price.setText(bean.getCprice());//定金价格
			tv_unit.setText("/"+bean.getUnit());//定金价格单位
		}else{
			//批发
//			linear_gg.setVisibility(View.VISIBLE);
			iv_pin.setVisibility(View.INVISIBLE);
			tv_guige.setText(bean.getGuige());
//			tv_chandi.setText(bean.getFactory());
			linear_price_tv.setVisibility(View.GONE);//定金试图
			tv_price_now.setVisibility(View.GONE);//定金试图
			ViewUtil.setActivityPrice(tv_price_unit_now, bean.getCurrentprice());//当前价
			tv_price_unit.setText("/"+bean.getUnit());//当前价单位
		}
		
		
		String urlString = bean.getPic();

		MyApplication.imageLoader.displayImage(urlString, iView,
				MyApplication.options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						bean.setMbitmap(loadedImage);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
					}
				});
		
//		LinearLayout llPriceParentLayout = (LinearLayout) ViewHolderUtil
//				.getItemView(convertView,
//						R.id.fragment_shopping_cart_item_price_parent);//下面合计视图
//		ImageView ivOutTime = (ImageView) ViewHolderUtil.getItemView(
//				convertView, R.id.fragment_shopping_cart_item_outtime);//是否完成视图
//		fragment_shopping_cart_bottom_parent
//		LinearLayout llTotalNumParentLayout = (LinearLayout) ViewHolderUtil
//				.getItemView(convertView,R.id.fragment_shopping_cart_item_total_num);//是否加减购买视图
		return convertView;
	}

}
