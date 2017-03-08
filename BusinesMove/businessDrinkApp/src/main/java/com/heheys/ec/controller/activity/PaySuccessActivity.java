package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.AlipayBaseBean;
import com.heheys.ec.model.dataBean.AlipayBaseBean.Order;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.orderItem;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderItemList;
import com.heheys.ec.model.dataBean.RechargeResultBean;
import com.heheys.ec.model.dataBean.RechargeResultBean.PayResultBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

//import com.heheys.ec.controller.fragment.ShoppingCartActivity;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间：2016-4-1 下午5:28:32
 *  类说明 支付成功
 */
public class PaySuccessActivity extends BaseActivity implements OnClickListener{

//	private TextView tv_total;
	private TextView freight_tv;
//	private TextView tv_status;
	private TextView tv_shopping;
	private TextView tv_point;
	private TextView tv_mypoint;
	private TextView tv_right;
	private LinearLayout order_linear;
	private LinearLayout linear_jf;
	private LinearLayout linear_transmation;
	private ArrayList<OrderItemList> list;
	private ArrayList<orderItem> listOrder;
	private View view;
	private String orderid;
//	private String totalAmount;
	private String score;//订单获取的积分
//	private String flag = "";
//	private String from;//0 一键购买 1 购物车购买
//	private String status;//支付是否成功 0 成功  1失败
	private RelativeLayout ra_coin;
	private ScrollView sc_order;
	private ImageView iv_result;
	private TextView tv_result;
	private TextView tv_recharge;
	private TextView tv_shopping_coin;
	private String oid;
	private MyHandler handler;
	private AlipayBaseBean alibasebean;
	private RechargeResultBean rechargeResultBean;
	private PayResultBean payResultBean;
	private String ststus;
	//1 扫描结果页 0 正常支付页
	private String orderFrom;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.pay_success);
	}

	public class MyHandler extends WeakHandler<PaySuccessActivity> {

		

		@SuppressLint("HandlerLeak")
		public MyHandler(PaySuccessActivity reference) {
			super(reference);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_FAILE:
				String result = (String) msg.obj;
				ToastUtil.showToast(PaySuccessActivity.this, result);
				break;
			case ConstantsUtil.ALI_BACK_CODE:
				//获取订单详情
				ra_coin.setVisibility(View.GONE);
				sc_order.setVisibility(View.VISIBLE);
				
				ststus = alibasebean.getResult().getStatus();
				if("1".equals(ststus)){
//					ToastUtil.showToast(PaySuccessActivity.this, "支付成功");
					tv_shopping.setText("继续逛逛");
					ResetTitle("支付成功");
				}else if("2".equals(ststus)){
//					ToastUtil.showToast(PaySuccessActivity.this, "支付失败");
					tv_shopping.setText("重新支付");
					ResetTitle("支付失败");
				}else if("0".equals(ststus)){
					//status == 0 代表线下订单提交成功
//					ToastUtil.showToast(PaySuccessActivity.this, "提交成功");
					tv_shopping.setText("继续逛逛");
					ResetTitle("提交成功");
				}
				Order orderResult = alibasebean.getResult().getOrder();
				if("-1".equals(orderResult.getPaytype())) {
//					linear_transmation.setVisibility(View.GONE);
					ResetTitle("提交成功");
				}
			if(orderResult != null){
				List<com.heheys.ec.model.dataBean.AlipayBaseBean.OrderItemList> listbean_new_list = alibasebean.getResult().getOrder().getList();
				 if(listbean_new_list!=null && listbean_new_list.size()>0){
					 int orderSize = listbean_new_list.size();
						for(int i=0;i<orderSize;i++){
							View  view = LinearLayout.inflate(PaySuccessActivity.this, R.layout.order_goods_item,null);
							//需要添加view的视图
							LinearLayout linear_add_good = (LinearLayout) view.findViewById(R.id.linear_add_good);
							TextView tv_payed = (TextView) view.findViewById(R.id.tv_payed);
							TextView tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
							tv_payed.setText(listbean_new_list.get(i).getPayMoney());
							tv_order_num.setText(listbean_new_list.get(i).getOrderNum());
							int itemsize = listbean_new_list.get(i).getProducts().size();
							List<AlipayBaseBean.Products> list_produce = listbean_new_list.get(i).getProducts();
							for(int j=0;j<itemsize;j++) {
								View  view_item = LinearLayout.inflate(PaySuccessActivity.this, R.layout.goods_item,null);
								TextView tv_goods_name = (TextView) view_item.findViewById(R.id.tv_goods_name);
								TextView tv_goods_num  = (TextView) view_item.findViewById(R.id.tv_goods_num);
								tv_goods_name.setText("商品名称:"+list_produce.get(j).getTitle());
								tv_goods_num.setText("购买数量:"+list_produce.get(j).getNum()+list_produce.get(j).getUnit());
								linear_add_good.addView(view_item);
							}
						  order_linear.addView(view);
							if(i < orderSize-1){
								TextView tv = new TextView(PaySuccessActivity.this);
								LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,10);
								tv.setBackgroundColor(getResources().getColor(R.color.color_eeeeeee));
								tv.setLayoutParams(lp);
							}
						}
						if(freight_tv!= null)
						freight_tv.setText(alibasebean.getResult().getOrder().getFreight());
				 }
				//获取的积分 
				   score = alibasebean.getResult().getOrder().getMadescore();
					if(!StringUtil.isEmpty(score)){
					if(Long.parseLong(score)>0){
						if(alibasebean.getResult()!=null && alibasebean.getResult().getOrder()!=null){
							Order orderreault = 	alibasebean.getResult().getOrder();
						if(orderreault!=null && !StringUtil.isEmpty(orderreault.getPaytype())){
							     if("-1".equals(orderreault.getPaytype())){
							    	 tv_point.setText("货到付款服务使用积分:"+score+"分");
							    	 SpannableStringBuilder spannable = new SpannableStringBuilder(tv_point.getText().toString().trim());
							    	 ForegroundColorSpan redSpan = new ForegroundColorSpan(baseActivity.getResources().getColor(R.color.color_FF3838));
							    	 spannable.setSpan(redSpan, 11, tv_point.getText().toString().trim().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							    	 tv_point.setText(spannable);
							     }else{
							    	 tv_point.setText("本次奖励积分:"+score+"分");
							    	 SpannableStringBuilder spannables = new SpannableStringBuilder(tv_point.getText().toString().trim());
							    	 ForegroundColorSpan redSpan = new ForegroundColorSpan(baseActivity.getResources().getColor(R.color.color_FF3838));
							    	 spannables.setSpan(redSpan, 7, tv_point.getText().toString().trim().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							    	 tv_point.setText(spannables);
							     }
							     linear_jf.setVisibility(View.VISIBLE);
							}
						}
					}else{
						linear_jf.setVisibility(View.GONE);
					  }
					}else{
						linear_jf.setVisibility(View.GONE);
					}}
				break;
			case ConstantsUtil.HTTP_SUCCESS:
				bindData();
				break;
			default:
				break;
			}
		}
	}
	 @SuppressLint("NewApi") public void bindData() {
			// TODO Auto-generated method stub
			if(rechargeResultBean != null && rechargeResultBean.getResult() != null){
				ra_coin.setVisibility(View.VISIBLE);
				sc_order.setVisibility(View.GONE);
				payResultBean = rechargeResultBean.getResult();
				if(payResultBean.getSuccess()){
					iv_result.setImageResource(R.drawable.success);
					tv_result.setText("充值成功!");
					tvTitleName.setText("支付成功!");
					tv_recharge.setText("查看充值记录");
				}else{
					iv_result.setImageResource(R.drawable.fail);
					tv_result.setText("充值失败!");
					tvTitleName.setText("支付失败!");
					tv_recharge.setText("重新充值");
				}
			}
		}
	//获取订单信息
	private void initData() {
		handler = new MyHandler(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		if(bundle.getBoolean("isOrder")){
			/*
			 * 订单返回结果
			 * */
			orderid = 	bundle.getString("orderId");
			//获取当前订单支付信息
			ApiHttpCilent.getInstance(getApplicationContext()).SignPayResult(this, orderid, new PayCallBack());
		}else{
			/*
			 * 充值喝喝币订单返回结果
			 * */
			ra_coin.setVisibility(View.VISIBLE);
			sc_order.setVisibility(View.GONE);
			tv_shopping.setVisibility(View.GONE);
			oid = bundle.getString("oid");
        	//查询充值订单结果
        	ApiHttpCilent.getInstance(getApplicationContext()).ResultRecharge(this, oid, new ResultCallBack());
		}
	}
class PayCallBack extends BaseJsonHttpResponseHandler<AlipayBaseBean> {
		
		

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AlipayBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			handler.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AlipayBaseBean arg3) {
			Dimess();
		}
		
		@Override
		protected AlipayBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			alibasebean = gson.fromJson(response, AlipayBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(alibasebean.getStatus()) ) {// 获取支付结果
				message.what = ConstantsUtil.ALI_BACK_CODE;
			}else if ("0".equals(alibasebean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = alibasebean.getError().getInfo();
			}
			handler.sendMessage(message);
			return alibasebean;
		}
	}
	private void Dimess() {
		PaySuccessActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	class ResultCallBack extends BaseJsonHttpResponseHandler<RechargeResultBean> {


		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, RechargeResultBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			handler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				RechargeResultBean arg3) {
			Dimess();
		}

		@Override
		protected RechargeResultBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			rechargeResultBean = gson.fromJson(response, RechargeResultBean.class);
			Message message = Message.obtain();
			if ("1".equals(rechargeResultBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = rechargeResultBean.getResult();
			} else if ("0".equals(rechargeResultBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = rechargeResultBean.getError();
			}
			handler.sendMessage(message);
			return rechargeResultBean;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
			// 返回键处理
			onBackPressed();
			MobclickAgent.onEvent(baseActivity, "C_PAY_STA_2");
		case R.id.tv_shopping:
			if("1".equals(ststus) || "0".equals(ststus)){
				intent.setClass(baseActivity, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, intent);
				finish();
				MobclickAgent.onEvent(baseActivity,"C_PAY_FAI_1");
			}else{
             //继续支付 跳转到订单列表
				intent.setClass(baseActivity, MyAllOrderActivity.class);
				intent.putExtra("status", "1");
				StartActivityUtil.startActivity(baseActivity, intent);
				MobclickAgent.onEvent(baseActivity,"C_PAY_FAI_2");
			}
			break;
		case R.id.tv_mypoint:
			//我的积分
			intent.setClass(this,MyPointsActivity.class);
			StartActivityUtil.startActivity(this, intent);
			break;
		case R.id.tv_shopping_coin:
			intent.setClass(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
					ConstantsUtil.SHOPPING_CART_NOT_SHOW_BACK);
			StartActivityUtil.startActivity(this, intent);
			this.finish();
			break;
		case R.id.tv_recharge:
			if(payResultBean != null){
				if(payResultBean.getSuccess()){
					//成功查看充值记录
					intent.setClass(this, HeheMoneyListActivity.class);
					StartActivityUtil.startActivity(this, intent);
				}else{
					intent.setClass(this, HeHeMoneyActivity.class);
					StartActivityUtil.startActivity(this, intent);
				}
			}
			break;
		case R.id.base_activity_title_right_righttv:
			//跳转到首页
			intent.setClass(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
					ConstantsUtil.SHOPPING_CART_NOT_SHOW_BACK);
			StartActivityUtil.startActivity(this, intent);
			this.finish();
			break;
		default:
			break;
		}
	}
	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		hideBack();
		ra_coin = (RelativeLayout) findViewById(R.id.ra_coin);//喝喝币支付成功视图
		sc_order = (ScrollView) findViewById(R.id.sc_order);//订单支付成功视图
		iv_result = (ImageView) findViewById(R.id.iv_result);//充值结果图片
		tv_result = (TextView) findViewById(R.id.tv_result);//充值结果
		tv_recharge = (TextView) findViewById(R.id.tv_recharge);//继续充值
		tv_shopping_coin = (TextView) findViewById(R.id.tv_shopping_coin);//去逛逛
		
		tv_right = (TextView) findViewById(R.id.base_activity_title_right_righttv);
		tv_mypoint = (TextView) findViewById(R.id.tv_mypoint);
		tv_point = (TextView) findViewById(R.id.tv_point);
//		tv_orderid = (TextView) findViewById(R.id.tv_orderid);
//		tv_total = (TextView) findViewById(R.id.tv_total);
		freight_tv = (TextView) findViewById(R.id.freight_tv);
//		tv_status = (TextView) findViewById(tv_status);
		tv_shopping = (TextView) findViewById(R.id.tv_shopping);
		order_linear = (LinearLayout) findViewById(R.id.order_linear);
		linear_jf = (LinearLayout) findViewById(R.id.linear_jf);
		linear_transmation = (LinearLayout) findViewById(R.id.linear_transmation);
		tv_mypoint.setOnClickListener(this);
		tv_shopping.setOnClickListener(this);
		tv_shopping_coin.setOnClickListener(this);
		tv_recharge.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		initData();
	}
	
	
	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
       
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "支付成功";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return "去首页";
	}

	@Override
	protected int setLeftImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

}
