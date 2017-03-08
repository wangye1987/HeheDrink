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
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.PaymentBean.PayList;
import com.heheys.ec.view.SoftEditText;

/**
 * @author 作者 E-mail:wk
 * @version 创建时间：2015-9-22 下午6:56:42 类说明
 * @param 订单详细适配器
 */
public class OrderInfoAdapter extends BaseListAdapter<PayList> {

	private Context context;
	private List<PayList> data;
	private TextView factory_name;
	private TextView child_orderId;
	private ImageView iv_goods;
	private TextView tv_name,normal_price;
	private TextView tv_num;
	private TextView line_textview;
	private TextView tv_price;
	private TextView delivery_price;
	private TextView tvReduce;
	private TextView tvAdd;
	private SoftEditText etProductNum;
	private int wine_num = 1;
	private String pv_num;
	private TextView tv_rate;
	public OrderInfoAdapter(List<PayList> data, Object obj,
			Context context) {
		super(data, obj, context);
		// TODO Auto-generated constructor stub
	}

	public OrderInfoAdapter(List<PayList> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final PayList bean = dataList.get(position);
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.order_info_item, parent, false);
		}
		factory_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.factory_name);
		child_orderId = (TextView) ViewHolderUtil.getItemView(convertView, R.id.child_orderId);
		tv_rate = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_rate);
		iv_goods = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_goods);
		tv_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_name);
		normal_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.normal_price);
		tv_num = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_num);
		line_textview = (TextView) ViewHolderUtil.getItemView(convertView, R.id.line_textview);
		tv_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_price);//当前价格
		delivery_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.delivery_price);
		tvReduce = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_num_reduce);
		tvAdd = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_num_add);
		etProductNum = (SoftEditText) ViewHolderUtil
				.getItemView(convertView,
						R.id.fragment_shopping_cart_item_num_et);
		factory_name.setText(bean.getFactory());
		tv_name.setText(bean.getName());
		tv_num.setText("x"+bean.getNum());
		tv_rate.setText(bean.getRate());
//		delivery_price.setText(bean.getTransamount());
		ViewUtil.setActivityPrice(delivery_price, bean.getTransamount());
		tvReduce.setTag(position);
		tvAdd.setTag(position);
		ViewUtil.setActivityPrice(tv_price, bean.getCurrentprice());
		ViewUtil.setNormalPrice(normal_price, bean.getPrice());
		MyApplication.imageLoader.displayImage(bean.getPic(), iv_goods,
				MyApplication.options_banner);
		if(position!=dataList.size()-1){
			line_textview.setVisibility(View.VISIBLE);
		}else{
			line_textview.setVisibility(View.GONE);
		}
		/*tvReduce.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pv_num = etProductNum.getText().toString().trim();
				if(Integer.parseInt(pv_num)<=1){
					ToastUtil.showToast(context,"商品数不能小于1");
				}else{
					wine_num = Integer.parseInt( bean.getGoods_num());{
						for(OrderInfoBean info:data){
							if(info.getChildOrderId().equals(bean.getChildOrderId())){
								wine_num-=1; 
								bean.setGoods_num(""+wine_num);
								break;
							}
						}
						etProductNum.setText(""+wine_num);
					}
				}
				notifyDataSetChanged();
			}
		});
		tvAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pv_num = etProductNum.getText().toString().trim();
				if(Integer.parseInt(pv_num)==100){
					ToastUtil.showToast(context,"商品数不能大于100");
				}else{
					wine_num = Integer.parseInt(bean.getGoods_num());
					for(OrderInfoBean info:data){
						if(info.getChildOrderId().equals(bean.getChildOrderId())){
							wine_num+=1; 
							bean.setGoods_num(""+wine_num);
							break;
						}
					}
					
					etProductNum.setText(""+wine_num);
				}
				notifyDataSetChanged();
			}
		});*/
		return convertView;
	}

	

}
