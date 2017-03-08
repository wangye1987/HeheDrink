package com.heheys.ec.model.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.SuitListShopping;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.StatusString;

public class ShoppingCartItemAdapter extends BaseListAdapter<SuitListShopping>{

	private CheckBox cb;
	private boolean isEditType;
	private Context mContext;
	public ShoppingCartItemAdapter(List<SuitListShopping> data, Context context) {
		super(data, context);
		this.mContext = context;
//		this.isEditType = isEditType;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		 SuitListShopping beanSuit = dataList.get(position);
		if(convertView == null){
			convertView = baseInflater.inflate(R.layout.shoppingcart_item, parent,false);
		}
		ImageView ivOutTime = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_outtime);//戳
		ImageView iv_pic = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_pic);//
		TextView tv_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_name);//
		TextView tv_gg = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_gg);
		TextView tv_price = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price);//
		TextView tv_unit = (TextView) ViewHolderUtil.getItemView(
				 convertView, R.id.tv_unit);//
		TextView tv_every = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_every);//
		tv_name.setText(StringUtil.isEmpty(beanSuit.getName())?"":beanSuit.getName());
		if(!beanSuit.getUnit().equals("瓶")){
			tv_gg.setText("规格:"+(StringUtil.isEmpty(beanSuit.getGuige())?"":beanSuit.getGuige()));
			tv_gg.setVisibility(View.VISIBLE);
		}else{
			tv_gg.setVisibility(View.INVISIBLE);
		}
		tv_unit.setText("/"+(StringUtil.isEmpty(beanSuit.getUnit())?"":beanSuit.getUnit()));
		tv_every.setText(StringUtil.isEmpty(beanSuit.getNumPerSuitInfo())?"":beanSuit.getNumPerSuitInfo());
		tv_price.setText("¥"+(StringUtil.isEmpty(beanSuit.getCurrentprice())?"":beanSuit.getCurrentprice()));
		MyApplication.imageLoader.displayImage(beanSuit.getPic(), iv_pic, MyApplication.options);
		StatusString.IsActivity(beanSuit.getStatus(), ivOutTime, "1");
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						NewProductDetailActivity.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						dataList.get(position).getAid());
				StartActivityUtil.startActivity((Activity) mContext,intent);
			}
		});
		return convertView;
	}
	private void IvImageStatue(boolean isEditType,boolean checkBoxState,String state,CheckBox cb,NewProductInfo bean,ImageView ivOutTime){
		if (!checkBoxState) {
			cb.setChecked(false);
			/**
			 * 未选择状态=============
			 */
			if ("2".equals(state)) {
				/**
				 * 已经抢光
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime != null)
				ivOutTime.setVisibility(View.VISIBLE);
				if(bean.getType().equals("1")){
					if(ivOutTime != null)
					ivOutTime.setImageResource(R.drawable.yishouqing);
			   }else{
					if(ivOutTime != null)
					ivOutTime.setImageResource(R.drawable.yiqiangguang);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}

//				llPriceParentLayout.setVisibility(View.GONE);
			} else if("3".equals(state)){
				/**
				 * 未成单
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime != null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.weichengdan);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else if ("7".equals(state)) {
				/**
				 * 已经结束
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime != null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.activity_over);
			    }
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			} else if ("5".equals(state)) {
				/**
				 * 已经售罄
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime != null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else if ("6".equals(state)) {
				/**
				 * 已经下架
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime != null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else if("4".equals(state)){
				//已成单
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime != null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.yichengdan);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else {
				/**
				 * 活动未结束
				 */
//				llTotalNumParentLayout.setVisibility(View.VISIBLE);
				if(ivOutTime != null){
				ivOutTime.setVisibility(View.GONE);
				}
				cb.setVisibility(View.VISIBLE);
			}

		} else {
			/**
			 * 已经选择状态==========================
			 * 
			 * 此处监听必须在checkbox初始化状态前
			 * 添加监听器的代码在初始化checkBox属性的代码之后,也就是说当初始化checkBox属性时,由于可能改变其状态,
			 * 导致调用了onCheckedChange
			 * ()方法,而这个监听器是在上一次初始化的时候添加的,那么当然其index就是上一次的positon值
			 * ,而不是本次的,所以每次保存checkBox属性状态的时候
			 * ，都把值赋到的list集合里其它对象上去了,而不是与本次index相关的对象上,这才是发生莫名其妙错乱的真正原因.
			 */
			cb.setChecked(true);
			if ("2".equals(state)) {
				/**
				 * 已经抢光
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.VISIBLE);
				}
				if(bean.getType().equals("1")){
					if(ivOutTime !=null)
					ivOutTime.setImageResource(R.drawable.yishouqing);
				}else{
					if(ivOutTime !=null)
					ivOutTime.setImageResource(R.drawable.yiqiangguang);
			   }
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
//				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
//						R.color.color_b7b7b7));
			} else if ("7".equals(state)) {
				/**
				 * 已经结束
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.activity_over);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			} else if ("5".equals(state)) {
				/**
				 * 已经售罄
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else if ("6".equals(state)) {
				/**
				 * 已经下架
				 */
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else if("4".equals(state)){
				//已成单
//				llTotalNumParentLayout.setVisibility(View.GONE);
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.yichengdan);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else if("3".equals(state)){
				/**
				 * 未成单
				 */
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.weichengdan);
				}
				if (!isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
			}else {
//				llTotalNumParentLayout.setVisibility(View.VISIBLE);
				if(ivOutTime !=null){
				ivOutTime.setVisibility(View.GONE);
				}
				cb.setVisibility(View.VISIBLE);
			}
		}
	}
}
