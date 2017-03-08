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
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.Iisupportbean;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.SuitItem;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.orderItem;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.SoftEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-29 上午11:42:33 类说明
 * 
 *            订单详情
 */
public class MyOrderDetaileAdapter extends BaseListAdapter<orderItem> {
	private TextView factory_name;
	private TextView child_orderId;
	private ImageView iv_goods;
	private TextView tv_name, normal_price;
	private TextView tv_num;
	private TextView line_textview;
	private TextView tv_price;
	private TextView delivery_price;
	private TextView tvReduce;
	private TextView tvAdd;
	private SoftEditText etProductNum;
	private int wine_num = 1;
	private String pv_num;
	private TextView tv_rate, tv_support;
	private TextView tv_goods, is_dj;
	private TextView tv_dj, tv_unit, tv_unit2;
	private TextView tv_itemtotal, tv_isdj, tv_title;
	private ImageView tv_pin, iv_status_activity, iv_image;
	private LinearLayout linear_dq, linear_dj;
	private String orderStatus;
	private int VIEW_TYPE = 2;
	private final int SINGLE_PRODECTION = 0;// 单品
	private final int SUIT_PRODECTION = 1;// 套餐
	private List<SuitItem> createList = new ArrayList<SuitItem>();
	private MyOrderSuitItemAdapter suititemAdapter;
	private TextView tv;
	private View view;
	private TextView tv_support_view;
	private LinearLayout.LayoutParams lp;
	private TextView tv_isdj_unit;
	public MyOrderDetaileAdapter(List<orderItem> data,String status, Context context) {
		super(data, context);
        this.orderStatus = status;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position).getIssuit() != 2 ? SINGLE_PRODECTION
				: SUIT_PRODECTION;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return VIEW_TYPE;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		final orderItem bean = dataList.get(position);
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case SINGLE_PRODECTION:
				convertView = baseInflater.inflate(R.layout.new_order_item,
						parent, false);
				break;
			case SUIT_PRODECTION:
				convertView = baseInflater.inflate(R.layout.order_suit_item,
						parent, false);
				break;
			default:
				break;
			}
		}

		if (SUIT_PRODECTION == type) {
			// 套装视图
			TextView total_suit = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.total_suit);
			TextView total_suit_price = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.total_suit_price);
			// 上面2个权限视图
			LinearLayout linear_top = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_top_view);
			LinearLayout linear_buttom = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_buttom_view);

			TextView tv_tz = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_tz);
			ImageView iv_arrow = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.iv_arrow);
			ListView lv_suit = (ListView) ViewHolderUtil.getItemView(
					convertView, R.id.lv_suit);
			LinearLayout linear_more = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_more);
			iv_arrow.setImageResource(R.drawable.down_suit);
			tv_tz.setText("套装价 : ¥" + bean.getAmount());
			total_suit.setText("共" + bean.getNum() + "套");
			total_suit_price.setText("¥ " + bean.getAmount());
			if (dataList.get(position).getSuitlist() != null
					&& dataList.get(position).getSuitlist().size() > 0) {
				createList.clear();
				createList.add(dataList.get(position).getSuitlist().get(0));
				suititemAdapter = new MyOrderSuitItemAdapter(createList,
						mcontext);
				lv_suit.setAdapter(suititemAdapter);
				Utils.setListViewHeightBasedOnChildren(lv_suit);
			}
			if (bean.getSuitlist().size() > 1)
				linear_more
						.setOnClickListener(new MoreClick(lv_suit, linear_more,
								iv_arrow, position, suititemAdapter, bean));

			SetSupportNitice(bean, linear_top, linear_buttom);

		} else {
			// 单品
			LinearLayout linear_top_sigle = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_top_sigle);
			LinearLayout linear_buttom = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_buttom_sigle_view);
			tv_goods = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_goods);



			tv_num = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_num);
			tv_itemtotal = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_itemtotal);


			tv_support = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_support);
			iv_image = (ImageView) ViewHolderUtil.getItemView(convertView,
					R.id.iv_image);
			tv_pin = (ImageView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_pin);
			iv_status_activity = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.iv_status_activity);
			linear_dj = (LinearLayout) ViewHolderUtil.getItemView(convertView,
					R.id.linear_dj);

//			LinearLayout linear_wk = (LinearLayout) ViewHolderUtil.getItemView(convertView,
//					R.id.linear_wk);
//			TextView tv_title_price = (TextView) ViewHolderUtil.getItemView(convertView,
//					R.id.tv_title_price);
//			TextView tv_price = (TextView) ViewHolderUtil.getItemView(convertView,
//					R.id.tv_price);
//			TextView tv_price_units = (TextView) ViewHolderUtil.getItemView(convertView,
//					R.id.tv_price_units);

			//待付尾款title
			is_dj = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.is_dj);
			//待付尾款value
			tv_isdj = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_isdj);
			tv_isdj_unit = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_isdj_unit);

			//待付订金
			tv_title = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_title);
			tv_dj = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_dj);
			tv_unit2 = (TextView) ViewHolderUtil.getItemView(convertView,
					R.id.tv_unit2);



//			orderStatus = dataList.getStatus();
			if ("0".equals(bean.getType())) {
				// 合伙买订单
//				tv_pin.setVisibility(View.VISIBLE);
				// status: 支付状态 1 待付款(定金),2 待付款(尾款) 3 待付款(全额) 5 已支付 22 已退款 9 进行中
//				result.list[i].amount	单品总金额
//				result.list[i].deamount	单品定金总定金
//				result.list[i].currentprice	当前价格
//				result.list[i].balacePayAmount	尾款总金额
//				result.list[i].suitlist[i].currentprice	当前价格
				if ("1".equals(orderStatus)) {
					/**
					 * 待付定金
					 * */
					if(!StringUtil.isEmpty(bean.getBalacePayAmount())) {
						if(Float.parseFloat(bean.getBalacePayAmount())>0) {
						is_dj.setText("待付尾款:");
						tv_isdj.setText("¥" + bean.getBalacePayAmount());
						tv_isdj_unit.setText("/" + bean.getUnit());

						tv_title.setText("预付订金:");
						tv_dj.setText("¥" + bean.getCurrentprice());
						tv_unit2.setText("/" + bean.getUnit());
						}else{
							tv_title.setText("合伙价:");
							tv_dj.setText("¥" + bean.getCurrentprice());
							tv_unit2.setText("/" + bean.getUnit());
						}
					}
				} else if ("2".equals(orderStatus) || "9".equals(orderStatus)) {
					/**
					 * 待付尾款
					 * */
					if(!StringUtil.isEmpty(bean.getBalacePayAmount())) {
						if(Float.parseFloat(bean.getBalacePayAmount())>0) {
							is_dj.setText("已付定金:");
							tv_isdj.setText("¥" + bean.getCurrentprice());
							tv_isdj_unit.setText("/" + bean.getUnit());

							tv_title.setText("待付尾款:");
							tv_dj.setText("¥" + bean.getBalacePayAmount());
							tv_unit2.setText("/" + bean.getUnit());
						}else{
							tv_title.setText("合伙价:");
							tv_dj.setText("¥" + bean.getCurrentprice());
							tv_unit2.setText("/" + bean.getUnit());
						}
					}
				}else if("3".equals(orderStatus)){
					/**
					 * 待付全款状态
					 * */
					tv_title.setText("待付全款:");
					tv_dj.setText("¥"+bean.getCurrentprice());
					tv_unit2.setText("/"+bean.getUnit());

				}else if("5".equals(orderStatus) || "7".equals(orderStatus) || "12".equals(orderStatus) ){
					/**
					 * 已支付全部
					 * */
					if(!StringUtil.isEmpty(bean.getBalacePayAmount())) {
						if(Float.parseFloat(bean.getBalacePayAmount())>0) {
						is_dj.setText("已付定金:");
						tv_isdj.setText("¥" + bean.getCurrentprice());
						tv_isdj_unit.setText("/" + bean.getUnit());

						tv_title.setText("已付尾款:");
						tv_dj.setText("¥" + bean.getBalacePayAmount());
						tv_unit2.setText("/" + bean.getUnit());
					 }else{
						tv_title.setText("合伙价:");
						tv_dj.setText("¥" + bean.getCurrentprice());
						tv_unit2.setText("/" + bean.getUnit());
					 }
					}
				}else if("21".equals(orderStatus) || "22".equals(orderStatus) || "23".equals(orderStatus) ){
					/**
					 *待发货
					 * */
					is_dj.setText("定金:");
					tv_isdj.setText("¥"+bean.getCurrentprice());
					tv_isdj_unit.setText("/"+bean.getUnit());

					tv_title.setText("尾款:");
					tv_dj.setText("¥"+bean.getBalacePayAmount());
					tv_unit2.setText("/"+bean.getUnit());

				}else{
                    /**
					 * 其他情况
					 * */
					if(!StringUtil.isEmpty(bean.getBalacePayAmount())) {
						if(Float.parseFloat(bean.getBalacePayAmount())>0) {
							is_dj.setText("已付定金:");
							tv_isdj.setText("¥" + bean.getCurrentprice());
							tv_isdj_unit.setText("/" + bean.getUnit());

							tv_title.setText("待付尾款:");
							tv_dj.setText("¥" + bean.getBalacePayAmount());
							tv_unit2.setText("/" + bean.getUnit());
						}else{
							tv_title.setText("合伙价:");
							tv_dj.setText("¥" + bean.getCurrentprice());
							tv_unit2.setText("/" + bean.getUnit());
						}
					}
				}
			} else {
				// 批发
				if ("3".equals(orderStatus)) {
					/**
					 * 待付全款
					 * */
					tv_title.setVisibility(View.GONE);
					ViewUtil.setActivityPrice(tv_dj, bean.getCurrentprice());
				}
				ViewUtil.setActivityPrice(tv_dj, bean.getCurrentprice());
				linear_dj.setVisibility(View.GONE);
			}
			if ("22".equals(orderStatus)) {
				iv_status_activity.setVisibility(View.VISIBLE);
			}

			SetSupportNitice(bean, linear_top_sigle, linear_buttom);
			tv_num.setText("x" + bean.getNum());

			tv_unit2.setText("/" + bean.getUnit());
			tv_goods.setText(bean.getName());
			ViewUtil.setActivityPrice(tv_itemtotal, bean.getAmount());
			MyApplication.imageLoader.displayImage(bean.getPic(), iv_image,
					MyApplication.options_banner);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mcontext,
						NewProductDetailActivity.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						dataList.get(position).getAid());
				intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY,
						dataList.get(position).getType());
				StartActivityUtil.startActivity((Activity) mcontext, intent);
			}
		});
		return convertView;
	}

	private View getSupportView() {
		View view = LinearLayout.inflate(mcontext, R.layout.issupport_item,
				null);
		tv_support_view = (TextView) view.findViewById(R.id.tv_support);
		lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		tv_support_view.setLayoutParams(lp);
		return view;
	}

	/*
	 * 
	 * 动态显示是否支持
	 */
	private void SetSupportNitice(final orderItem bean,
			LinearLayout linear_top, LinearLayout linear_buttom) {
		ArrayList<Iisupportbean> listsupport = new ArrayList<Iisupportbean>();
		listsupport.clear();
		Iisupportbean iisuport = new Iisupportbean();
		if (bean.getIscod() == 0) {
			iisuport.setIscod(true);
		}
		if (bean.getSupportRewardScore() == 0) {
			iisuport.setSupportRewardScore(true);
		}
		if (bean.getSupportCoupon() == 0) {
			iisuport.setSupportCoupon(true);
		}
		if (bean.getSupportScore() == 0) {
			iisuport.setSupportScore(true);
		}
		listsupport.add(iisuport);

		// 防止来回刷新listview重新生成视图
		linear_top.removeAllViews();
		linear_buttom.removeAllViews();
		int size = listsupport.size();
		if (size > 0) {
			Iisupportbean iisupport = listsupport.get(0);
			if (iisupport.isIscod()) {
				view = getSupportView();
				tv_support_view.setText("不支持货到付款");
				linear_top.addView(view, lp);
				linear_top.setVisibility(View.VISIBLE);
			}
			if (iisupport.isSupportRewardScore()) {
				view = getSupportView();
				tv_support_view.setText("不支持消费奖励积分");
				linear_top.addView(view, lp);
				linear_top.setVisibility(View.VISIBLE);
			}
			if (iisupport.isSupportCoupon()) {
				view = getSupportView();
				tv_support_view.setText("不支持使用优惠券");
				linear_top.addView(view, lp);
				linear_top.setVisibility(View.VISIBLE);
			}
			if (iisupport.isSupportScore()) {
				view = getSupportView();
				tv_support_view.setText("不支持使用积分");
				if (linear_top.getChildCount() < 3) {
					linear_top.addView(view, lp);
					linear_top.setVisibility(View.VISIBLE);
				} else {
					linear_buttom.addView(view, lp);
					linear_buttom.setVisibility(View.VISIBLE);
				}
			}
		}
		// 生成布局
		if (linear_top.getChildCount() < 3
				&& linear_top.getVisibility() == View.VISIBLE) {
			int childSize = linear_top.getChildCount();
			for (int i = 0; i < 3 - childSize; i++) {
				View view2 = LinearLayout.inflate(mcontext,
						R.layout.issupport_item, null);
				TextView tv_support2 = (TextView) view2
						.findViewById(R.id.tv_support);
				tv_support2.setText("不支持消费奖励积分");
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				view2.setVisibility(View.INVISIBLE);
				linear_top.addView(view2, lp2);
			}
		}
		// 撑起布局
		if (linear_buttom.getChildCount() < 3
				&& linear_buttom.getVisibility() == View.VISIBLE) {
			int childSize = linear_buttom.getChildCount();
			for (int i = 0; i < 3 - childSize; i++) {
				View view2 = LinearLayout.inflate(mcontext,
						R.layout.issupport_item, null);
				TextView tv_support2 = (TextView) view2
						.findViewById(R.id.tv_support);
				tv_support2.setText("不支持使用优惠券");
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				view2.setVisibility(View.INVISIBLE);
				linear_buttom.addView(view2, lp2);
			}
		}
	}

	/*
	 * 
	 * 点击更多监听
	 */
	private class MoreClick implements OnClickListener {
		private ListView lv_suit;
		private LinearLayout linear_more;
		private ImageView iv;
		private int position;
		private MyOrderSuitItemAdapter suititemAdapter;
		private orderItem bean;

		private MoreClick(ListView lv_suit, LinearLayout linear_more,
				ImageView iv, int position,
				MyOrderSuitItemAdapter suititemAdapter, orderItem bean) {
			this.lv_suit = lv_suit;
			this.linear_more = linear_more;
			this.iv = iv;
			this.position = position;
			this.suititemAdapter = suititemAdapter;
			this.bean = bean;
		}

		@Override
		public void onClick(View v) {
			if (lv_suit.getCount() == 1) {
				iv.setImageResource(R.drawable.up_suit);
				suititemAdapter.setNewData(bean.getSuitlist());
			} else {
				iv.setImageResource(R.drawable.down_suit);
				createList.clear();
				createList.add(dataList.get(position).getSuitlist().get(0));
				suititemAdapter.setNewData(createList);
			}
			suititemAdapter.notifyDataSetChanged();
			Utils.setListViewHeightBasedOnChildren(lv_suit);
		}
	}
	// private class MoreClick implements OnClickListener{
	// private ListView lv_suit;
	// private LinearLayout linear_more;
	// private ImageView iv;
	// private int positon;
	// private MyOrderSuitAdapter suititemAdapter;
	// private List<SuitItem> createList = new ArrayList<SuitItem>();
	// MoreClick(ListView lv_suit,LinearLayout linear_more,ImageView iv,int
	// positon,MyOrderSuitAdapter suititemAdapter){
	// this.lv_suit = lv_suit;
	// this.linear_more = linear_more;
	// this.iv = iv;
	// this.positon = positon;
	// this.suititemAdapter = suititemAdapter;
	// createList.clear();
	// //初始化获取第一条数
	// if(dataList.get(positon).getSuitlist() !=null &&
	// dataList.get(positon).getSuitlist().size()>0){
	// createList.add(dataList.get(positon).getSuitlist().get(0));
	// suititemAdapter = new MyOrderSuitAdapter(createList, mcontext);
	// lv_suit.setAdapter(suititemAdapter);
	// }
	// }
	//
	// @Override
	// public void onClick(View v) {
	// if(iv.getVisibility() == View.VISIBLE){
	// iv.setImageResource(R.drawable.down_suit);
	// lv_suit.setVisibility(View.GONE);
	// }else{
	// iv.setImageResource(R.drawable.up_suit);
	// suititemAdapter.setNewData(dataList.get(positon).getSuitlist());
	// suititemAdapter.notifyDataSetChanged();
	// }
	// }
	// }
}
