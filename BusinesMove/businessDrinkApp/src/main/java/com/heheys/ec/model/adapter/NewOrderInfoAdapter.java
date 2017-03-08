package com.heheys.ec.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.Iisupportbean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderItemList;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.SuitListItem;
import com.heheys.ec.model.dataBean.PaymentBean.PayList;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.SoftEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间：2016-3-23 下午2:56:57
 *  类说明  提交订单订单预览
 */
public class NewOrderInfoAdapter extends BaseListAdapter<OrderItemList> {

	private Context context;
	private List<PayList> data;
	private TextView factory_name;
	private TextView child_orderId;
	private ImageView iv_goods;
	private TextView tv_name,normal_price;
	private TextView tv_num,textView2;
	private TextView line_textview;
	private TextView delivery_price;
	private TextView tvReduce;
	private TextView tvAdd;
	private SoftEditText etProductNum;
	private int wine_num = 1;
	private String pv_num;
	private TextView tv_rate;
	private TextView tv_error;
	private TextView order_tip,order_gg;
	private TextView tv_goods,tv_price,tv_unit,tv_title,tv_support;
	private TextView tv_dj,tv_unit2;
	private TextView tv_itemtotal,tv_isdj;
	private ImageView tv_pin,iv_image;
	private LinearLayout linear_dj,linear_dq;
	private String payStatus;
	private int VIEW_TYPE = 2;
	private final int SINGLE_PRODECTION = 0;//单品
	private final int SUIT_PRODECTION = 1;//套餐
	private TextView total_suit;
	private TextView total_suit_price;
	private OrderSuitItemAdapter suititemAdapter;
	private List<SuitListItem> createList = new ArrayList<SuitListItem>();
	private TextView tv_support_view;
	private View view;
	private LinearLayout.LayoutParams lp;

	public NewOrderInfoAdapter(List<OrderItemList> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position).getIssuit() != 2?SINGLE_PRODECTION:SUIT_PRODECTION;
	}
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return VIEW_TYPE;
	}
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		         OrderItemList bean = dataList.get(position);
		        int type = getItemViewType(position);
				if(convertView == null){
					switch (type) {
					case SINGLE_PRODECTION:
						//单品视图
						convertView = baseInflater.inflate(R.layout.order_detail_item, parent, false);
						break;
					case SUIT_PRODECTION:
						//套装视图
						convertView = baseInflater.inflate(R.layout.order_suit_item, parent, false);
						break;
					default:
						break;
					}
				}
				
			if(SINGLE_PRODECTION == type){
				//单品
				LinearLayout linear_top_sigle = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_top_sigle_view);
				LinearLayout linear_buttom_sigle = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_buttom_sigle);
				tv_error = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_error);
				order_tip = (TextView) ViewHolderUtil.getItemView(convertView, R.id.order_tip);
				tv_goods = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_goods);
				tv_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_price);
				tv_dj = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_dj);
				tv_unit = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_unit);
				tv_unit2 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_unit2);
				tv_num = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_num);
				textView2 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.textView2);
				tv_itemtotal = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_itemtotal);
				tv_title = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_title);
				tv_support = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_support);
				order_gg = (TextView) ViewHolderUtil.getItemView(convertView, R.id.order_gg);
				iv_image = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_image);
				tv_pin = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.tv_pin);
				linear_dq = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_dq);
//				LinearLayout re_roal = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.re_roal_view);
//				if("0".equals(bean.getType())){
//					//拼单
//					tv_pin.setVisibility(View.VISIBLE);
//					linear_dq.setVisibility(View.VISIBLE);
//					tv_title.setVisibility(View.VISIBLE);
//					ViewUtil.setActivityPrice(tv_price, bean.getCurrentprice());
//					tv_unit.setText(bean.getUnit());
//					ViewUtil.setActivityPrice(tv_dj, bean.getDeamount());
//				}else{
//					//批发
//					tv_pin.setVisibility(View.INVISIBLE);
//					linear_dq.setVisibility(View.INVISIBLE);
//					tv_title.setVisibility(View.GONE);
//					ViewUtil.setActivityPrice(tv_dj, bean.getCurrentprice());
//				}
//				0合伙买 1甩卖 2:甩卖活动 3:E发行

				if("0".equals(bean.getType())){
					order_tip.setText("合伙买");
					if(!StringUtil.isEmpty(bean.getBalacePayAmount())) {
						if(Float.parseFloat(bean.getBalacePayAmount())>0) {
							//待付订金
							linear_dq.setVisibility(View.VISIBLE);
							textView2.setText("待付尾款:");
							textView2.setVisibility(View.VISIBLE);
							tv_unit.setText("/"+bean.getUnit());
							tv_price.setText("¥ "+bean.getBalacePayAmount());

							tv_title.setText("预付订金:");
							tv_title.setVisibility(View.VISIBLE);
							tv_dj.setText("¥ "+bean.getDeamount());
							tv_unit2.setText("/"+bean.getUnit());
						}else{
							//待付全款
							linear_dq.setVisibility(View.GONE);
							tv_title.setText("合伙价：");
							tv_title.setVisibility(View.VISIBLE);
							tv_dj.setText(bean.getDeamount());
							tv_unit2.setText("/"+bean.getUnit());
						}
					}

					order_gg.setText("规格:"+bean.getBottlevol());
					tv_title.setVisibility(View.VISIBLE);
					if(!StringUtil.isEmpty(bean.getErrorInfo())){
						tv_error.setVisibility(View.VISIBLE);
						tv_error.setText(bean.getErrorInfo());
					}else{
						tv_error.setVisibility(View.GONE);
					}
				}else if("1".equals(bean.getType())){
					order_tip.setText("特卖");
					tv_title.setVisibility(View.GONE);
					tv_dj.setText("¥ "+bean.getCurrentprice());
					tv_unit2.setText("/"+bean.getUnit());
				}
//				    tv_pin.setVisibility(View.INVISIBLE);
//					linear_dq.setVisibility(View.INVISIBLE);
//					tv_title.setVisibility(View.GONE);
//					ViewUtil.setActivityPrice(tv_dj, bean.getCurrentprice());
				/*
				 * 是否支持货到付款
				 * */
					SetSupportNitice(bean, linear_top_sigle, linear_buttom_sigle);
				   tv_num.setText("x"+bean.getNum());
				   tv_goods.setText(bean.getName());
//				   ViewUtil.setActivityPrice(tv_itemtotal, Utils.DoPrice(Float.parseFloat(bean.getAmount()))+"");
				   tv_itemtotal.setText("¥ "+bean.getAmount());
				   MyApplication.imageLoader.displayImage(bean.getPic(), iv_image,
						MyApplication.optionsGoodsDetail);
				}else{
					//组合套餐
					total_suit = (TextView) ViewHolderUtil.getItemView(convertView, R.id.total_suit);
					total_suit_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.total_suit_price);
					//上面2个权限视图
					LinearLayout linear_top2 = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_top_view);
					LinearLayout linear_buttom2 = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_buttom_view);
					
					TextView tv_tz = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_tz);
					ImageView iv_arrow = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_arrow);
					ListView lv_suit = (ListView) ViewHolderUtil.getItemView(convertView, R.id.lv_suit);
					LinearLayout linear_more = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_more);
					iv_arrow.setImageResource(R.drawable.down_suit);
					tv_tz.setText("套装价 : ¥"+bean.getAmount());
					total_suit.setText("共"+bean.getNum()+"套");
//					ViewUtil.setActivityPrice(total_suit_price, Utils.DoPrice(Float.parseFloat(bean.getAmount()))+"");
					total_suit_price.setText("¥ "+bean.getAmount());
					if(dataList.get(position).getSuitlist() !=null && dataList.get(position).getSuitlist().size()>0){
						createList.clear();
						createList.add(dataList.get(position).getSuitlist().get(0));
						suititemAdapter = new OrderSuitItemAdapter(createList, mcontext);
						lv_suit.setAdapter(suititemAdapter);
						Utils.setListViewHeightBasedOnChildren(lv_suit);
					}
					if(bean.getSuitlist().size() > 1)
					linear_more.setOnClickListener(new MoreClick(lv_suit,linear_more,iv_arrow,position,suititemAdapter,bean));
					SetSupportNitice(bean, linear_top2, linear_buttom2);
				}
			if(dataList.get(position).getIssuit() != 2)
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
							intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
								dataList.get(position).getAid());
						intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY,dataList.get(position).getType());
						StartActivityUtil.startActivity((Activity) mcontext, intent);
					}
				});
				return convertView;

	}
	
	
	private View getSupportView(){
		View  view  = LinearLayout.inflate(mcontext, R.layout.issupport_item, null);
		tv_support_view = (TextView) view.findViewById(R.id.tv_support);
		lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		tv_support_view.setLayoutParams(lp);
		return view;
	}
	/*
	 * 
	 * 动态显示是否支持
	 * */
	private void SetSupportNitice(final OrderItemList bean,
			LinearLayout linear_top, LinearLayout linear_buttom) {
		ArrayList<Iisupportbean> listsupport = new ArrayList<Iisupportbean>();
		listsupport.clear();
		Iisupportbean iisuport = new Iisupportbean();
		if(bean.getIscod() == 0){
			iisuport.setIscod(true);
		}
		if(bean.getSupportRewardScore() == 0){
			iisuport.setSupportRewardScore(true);
		}
		if(bean.getSupportCoupon() == 0){
			iisuport.setSupportCoupon(true);
		}
		if(bean.getSupportScore() == 0){
			iisuport.setSupportScore(true);
		}
		listsupport.add(iisuport);

		linear_top.removeAllViews();
		linear_buttom.removeAllViews();
		int size = listsupport.size();
		if(size > 0){
			Iisupportbean iisupport = listsupport.get(0);
			if(iisupport.isIscod()){
				view = getSupportView();
				tv_support_view.setText("不支持货到付款");
				linear_top.addView(view,lp);
				linear_top.setVisibility(View.VISIBLE);
			}
			if(iisupport.isSupportRewardScore()){
				view = getSupportView();
				tv_support_view.setText("不支持消费奖励积分");
				linear_top.addView(view,lp);
				linear_top.setVisibility(View.VISIBLE);
			}
			if(iisupport.isSupportCoupon()){
				view = getSupportView();
				tv_support_view.setText("不支持使用优惠券");
				linear_top.addView(view,lp);
				linear_top.setVisibility(View.VISIBLE);
			}
			if(iisupport.isSupportScore()){
				view = getSupportView();
				tv_support_view.setText("不支持使用积分");
				if(linear_top.getChildCount() < 3){
					linear_top.addView(view,lp);
					linear_top.setVisibility(View.VISIBLE);
			    }else{
					linear_buttom.addView(view,lp);
					linear_buttom.setVisibility(View.VISIBLE);
				}
			}
		}
		//生成布局
		if(linear_top.getChildCount() < 3 && linear_top.getVisibility() == View.VISIBLE){
		    int childSize = linear_top.getChildCount();
			for(int i=0;i < 3-childSize;i++){
				View  view2  = LinearLayout.inflate(mcontext, R.layout.issupport_item, null);
				TextView  tv_support2 = (TextView) view2.findViewById(R.id.tv_support);
				tv_support2.setText("不支持消费奖励积分");
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				view2.setVisibility(View.INVISIBLE);
				linear_top.addView(view2,lp2);
			}
		}
		//撑起布局
		if(linear_buttom.getChildCount() < 3 && linear_buttom.getVisibility() == View.VISIBLE){
			int childSize = linear_buttom.getChildCount();
			for(int i=0;i< 3 - childSize;i++){
				View  view2  = LinearLayout.inflate(mcontext, R.layout.issupport_item, null);
				TextView  tv_support2 = (TextView) view2.findViewById(R.id.tv_support);
				tv_support2.setText("不支持使用优惠券");
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				view2.setVisibility(View.INVISIBLE);
				linear_buttom.addView(view2,lp2);
			}
		}
	}
	
	/*
	 * 
	 * 点击更多监听
	 * */
	private class MoreClick implements OnClickListener{
		private ListView lv_suit;
		private LinearLayout linear_more;
		private ImageView iv;
		private int position;
		private OrderSuitItemAdapter suititemAdapter;
		private OrderItemList bean;
		private MoreClick(ListView lv_suit,LinearLayout linear_more,
				ImageView iv,int position,OrderSuitItemAdapter suititemAdapter,OrderItemList bean){
			this.lv_suit = lv_suit;
			this.linear_more = linear_more;
			this.iv = iv;
			this.position = position;
			this.suititemAdapter = suititemAdapter;
			this.bean = bean;
		}

		@Override
		public void onClick(View v) {
			if(lv_suit.getCount() == 1){
				    iv.setImageResource(R.drawable.up_suit);
				  	suititemAdapter.setNewData(bean.getSuitlist());
				}else{
					iv.setImageResource(R.drawable.down_suit);
					createList.clear();
					createList.add(dataList.get(position).getSuitlist().get(0));
					suititemAdapter.setNewData(createList);
				}
			suititemAdapter.notifyDataSetChanged();
			Utils.setListViewHeightBasedOnChildren(lv_suit);
		}
	}
}
