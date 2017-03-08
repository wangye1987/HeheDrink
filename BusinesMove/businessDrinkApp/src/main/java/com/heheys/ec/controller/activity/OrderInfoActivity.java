package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.OrderBaseDetailBean;
import com.heheys.ec.model.dataBean.OrderBaseDetailBean.OrderinfoBean;
import com.heheys.ec.model.dataBean.ShoppingCartListBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.StatusString;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-16 上午11:45:54 类说明
 * @param 订单详情
 *            (订单列表过来)
 */
public class OrderInfoActivity extends BaseActivity {

	private TextView tv_orderid;
	private TextView tv_payed;
	private TextView tv_status;
	private MyHandler handler = new MyHandler(this);
	private TextView currt_price;
	private TextView tv_childsendprice;
	private TextView tv_winenum;
	private TextView tv_factaryName;
	private TextView nomarl_price;

	private TextView delivery_price;
	private ListView lv_order;
	private Context mContext;
	private LinearLayout pay_type;
	private LinearLayout linear_address;
	private LinearLayout linear_status;
	private LinearLayout linear_name;
	private LinearLayout linear_paytype;
	private int type = 2;
	private Button to_pay;
	private OrderinfoBean bean;// 服务器返回数据bean
	// private TextView payed_price;
	private TextView textView4;
	private TextView tv_dong;
	private TextView tv_final;
	private TextView tv_pay;
	private TextView tv_ispay;
	private TextView tv_total_price;
	private TextView tv_send_price;
	private TextView tv_goods_price;
	private TextView tv_address;
	private TextView order_status;
	private TextView tv_mobile;
	private TextView tv_name;
	private ShoppingCartListBean obj;
	private LinearLayout add_item;
	private LinearLayout linear_subscription;
	private LinearLayout linear_buttom;
	private List<ShoppingCartSelectBean> listshop;
	private int flag;
	private TextView tv_wineName;
	private TextView tv_money;
	private TextView tv_price;
	private TextView normal_price;
	private TextView tv_num;
	private TextView tv_rate;
	private ImageView iv_goods;
	private ArrayList<Integer> orderId;
	private TextView tv_subscription;
	private TextView tv_totalName;
	private TextView order_id;
	private TextView tv_dinjing;
	private int status;//订单状态 根据状态显示不同的订单页面
	private int requestMode;//请求模式 1 订单详细 2 确定收货
	private int id;
	private View footerViewbg;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.order_info);
		initDate();
	}

	private void initDate() {
		// TODO Auto-generated method stub
		footerViewbg=baseLayoutInflater.inflate(R.layout.footview_bg_order, null);
		mContext = OrderInfoActivity.this;
		Intent intent = getIntent();
		if (intent != null) {
			int orderId = intent.getIntExtra("orderId", -1);
			requestMode = 1;//请求订单详细
			ApiHttpCilent.getInstance(getApplicationContext()).GetOrderDetail(mContext,
					orderId, new MyCallBack());
		}
	}

	private void Dimess() {
		OrderInfoActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<OrderBaseDetailBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, OrderBaseDetailBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				OrderBaseDetailBean arg3) {
			Dimess();
		}

		@Override
		protected OrderBaseDetailBean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<OrderBaseDetailBean>() {
			}.getType();
			OrderBaseDetailBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				if(requestMode==1){
					message.obj = bean.getResult();
				}
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError().getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = bean.getError().getInfo();
				}
			}
			handler.sendMessage(message);
			return bean;
		}
	}

	public class MyHandler extends WeakHandler<OrderInfoActivity> {

		public MyHandler(OrderInfoActivity reference) {
			super(reference);
		}

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				if(requestMode==1){
					bean = (OrderinfoBean) msg.obj;
					if (bean != null) {
						GetDate();
					}
				}else if(requestMode==2){//确认收货成功
					ToastUtil.showToast(mContext, "操作成功");
					to_pay.setVisibility(View.GONE);
					linear_buttom.setVisibility(View.GONE);
					order_status.setText("已完成");
				}
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(mContext, back);
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		mContext = OrderInfoActivity.this;
		orderId = new ArrayList<Integer>();
		/* header view */
		tv_name = (TextView) findViewById(R.id.tv_name_connact);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		tv_address = (TextView) findViewById(R.id.tv_address);
		order_status = (TextView) findViewById(R.id.order_status);
		linear_status = (LinearLayout) findViewById(R.id.linear_status);
		linear_address = (LinearLayout) findViewById(R.id.linear_address);
		linear_name = (LinearLayout) findViewById(R.id.linear_name);
		linear_paytype = (LinearLayout) findViewById(R.id.linear_paytype);

		/* footer view */
		pay_type = (LinearLayout) findViewById(R.id.pay_type);
		tv_dong = (TextView) findViewById(R.id.tv_dong);// 商品金额
		textView4 = (TextView) findViewById(R.id.textView4);// 商品金额
		tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);// 商品金额
		tv_send_price = (TextView) findViewById(R.id.tv_send_price);// 运费金额 下面大的
		tv_total_price = (TextView) findViewById(R.id.tv_total_price);// 总价格

		tv_money = (TextView) findViewById(R.id.tv_money);// 酒名称
		tv_wineName = (TextView) findViewById(R.id.tv_name);// 酒名称
		tv_price = (TextView) findViewById(R.id.tv_price);// 当前价格
		normal_price = (TextView) findViewById(R.id.normal_price);// 市场价
		tv_num = (TextView) findViewById(R.id.tv_num);// 数量
		tv_rate = (TextView) findViewById(R.id.tv_rate);// 百分比
		delivery_price = (TextView) findViewById(R.id.delivery_price);// 运费 单项
		tv_subscription = (TextView) findViewById(R.id.tv_subscription);//已支付订金
		tv_totalName = (TextView) findViewById(R.id.tv_totalName);
		order_id = (TextView) findViewById(R.id.order_id);//订单号
		iv_goods = (ImageView) findViewById(R.id.iv_goods);// 酒图片

		to_pay = (Button) findViewById(R.id.to_pay);// 支付按钮
		tv_final = (TextView) findViewById(R.id.tv_final);//
		tv_pay = (TextView) findViewById(R.id.tv_pay);//
		tv_ispay = (TextView) findViewById(R.id.tv_ispay);//
		add_item = (LinearLayout) findViewById(R.id.add_item);
		linear_subscription = (LinearLayout) findViewById(R.id.linear_subscription);//显示定金支付数额界面
		linear_buttom = (LinearLayout) findViewById(R.id.linear_buttom);//显示定金支付数额界面
		tv_dinjing = (TextView) findViewById(R.id.tv_dinjing);//订金总额
		to_pay.setOnClickListener(this);
		linear_address.setOnClickListener(this);
	}
	/*
	 * 回填数据，填充页面
	 * 
	 * */
	void GetDate() {
		linear_name.setVisibility(View.VISIBLE);
		tv_name.setText(" "+bean.getAddress().getContact());
		tv_mobile.setText(" "+bean.getAddress().getMobile());
		tv_address.setText(bean.getAddress().getAddress());

		if(bean.getTransamount()!=null)
		ViewUtil.setActivityPrice(delivery_price, bean.getTransamount());
		if(bean.getAmount()!=null)
		ViewUtil.setActivityPrice(tv_goods_price, bean.getAmount());
		if(bean.getTransamount()!=null)
		ViewUtil.setActivityPrice(tv_send_price, bean.getTransamount());
		if(bean.getOrderamount()!=null)
		ViewUtil.setActivityPrice(tv_total_price,bean.getOrderamount());
		if(bean.getCurrentprice()!=null)
		ViewUtil.setActivityPrice(tv_price,bean.getCurrentprice());
		if(bean.getPrice()!=null)
		ViewUtil.setNormalPrice(normal_price,bean.getPrice());
		if(bean.getTamount()!=null)
		ViewUtil.setActivityPrice(tv_final,bean.getTamount());
		
		tv_wineName.setText(bean.getName());
		tv_num.setText(bean.getNum() + bean.getUnit());
		tv_rate.setText(bean.getRate());
		MyApplication.imageLoader.displayImage(bean.getPic(), iv_goods,
				MyApplication.options);
		id = bean.getId();
		orderId.clear();
		orderId.add(id);//获取子订单号 通过该订单号去支付 
		tv_totalName.setText(bean.getName());
		tv_dinjing.setText(bean.getDeamount());
		order_id.setText(id+"");
		
//		1 付定金
//		2 付尾款
//		7 确认收货
//		 * 正常: 1 待付款(定金)，9 进行中，--成单操作-- 2 待付款(尾款)，-3 待付款(全额)-，--付款结束-- 10 受理中 ，--受理操作-- 6 备货中，--发货操作-- 7待收货， --确认收货-- 8 已完成  
//		 * 异常: 21 已取消 22已退款 23  已失效 4 线下支付
//		 */ 1 2 3 7 
		
		status = bean.getStatus();
			if(status==1){
				to_pay.setText(StatusString.getStatus(bean.getStatus()));
				tv_total_price.setTextColor(getResources().getColor(R.color.color_f98c43));
				tv_goods_price.setTextColor(getResources().getColor(R.color.black));
				tv_send_price.setTextColor(getResources().getColor(R.color.black));
				to_pay.setVisibility(View.VISIBLE);
				add_item.setVisibility(View.VISIBLE);
				linear_status.setVisibility(View.GONE);
				tv_dong.setVisibility(View.GONE);
				tv_pay.setVisibility(View.VISIBLE);
				tv_pay.setText("支付订金");
				linear_subscription.setVisibility(View.VISIBLE);
				
				to_pay.setVisibility(View.VISIBLE);
				tv_pay.setVisibility(View.VISIBLE);
				add_item.setVisibility(View.GONE);
				
				tv_ispay.setVisibility(View.GONE);
				tv_final.setVisibility(View.VISIBLE);
				tv_pay.setText(bean.getName());
				tv_final.setText(" 订金总额：¥"+bean.getDeamount());
				linear_buttom.setVisibility(View.VISIBLE);
			}else if(status== 2){
				to_pay.setText(StatusString.getStatus(bean.getStatus()));
				to_pay.setVisibility(View.VISIBLE);
				linear_status.setVisibility(View.GONE);
				add_item.setVisibility(View.GONE);
				tv_pay.setVisibility(View.VISIBLE);
				StatusVisible();
				tv_pay.setText("支付尾款");
				linear_subscription.setVisibility(View.VISIBLE);
				to_pay.setVisibility(View.VISIBLE);
				tv_pay.setVisibility(View.VISIBLE);
				tv_ispay.setVisibility(View.VISIBLE);
				linear_buttom.setVisibility(View.VISIBLE);
			}else if(status==7){
				StatusVisible();
				to_pay.setText("确认收货");
				tv_ispay.setText("已支付金额：");
				if(bean.getDeamount()!=null) 
					ViewUtil.setActivityPrice(tv_subscription,bean.getHaspayamount());
				tv_pay.setVisibility(View.INVISIBLE);
				order_status.setText(StatusString.getStatus(bean.getStatus()));
				linear_paytype.setVisibility(View.GONE);
				textView4.setVisibility(View.GONE);
				tv_final.setVisibility(View.GONE);
				add_item.setVisibility(View.GONE);
				linear_subscription.setVisibility(View.VISIBLE);
				to_pay.setVisibility(View.VISIBLE);
				tv_pay.setVisibility(View.GONE);
				tv_ispay.setVisibility(View.VISIBLE);
				linear_buttom.setVisibility(View.VISIBLE);
			}else{
				linear_buttom.setVisibility(View.VISIBLE);
				StatusVisible();
				tv_pay.setVisibility(View.VISIBLE);
				linear_status.setVisibility(View.VISIBLE);
				order_status.setText(bean.getStatusname());
				linear_buttom.setVisibility(View.GONE);
				linear_paytype.setVisibility(View.GONE);
				tv_pay.setVisibility(View.GONE);
				if(status==3){
					tv_pay.setVisibility(View.VISIBLE);
					linear_subscription.setVisibility(View.VISIBLE);
					linear_buttom.setVisibility(View.VISIBLE);
				}else{
					linear_subscription.setVisibility(View.GONE);
				}
				if(status==4){
					tv_ispay.setVisibility(View.GONE);
				}
				if(status==6 || status==8 ||status ==9|| status==10 || status==22){
					linear_subscription.setVisibility(View.VISIBLE);
					if(status ==9){
						tv_ispay.setText("已支付订金：");
					}else{
						tv_ispay.setText("已支付金额：");
					}
					if(bean.getDeamount()!=null) 
						ViewUtil.setActivityPrice(tv_subscription,bean.getHaspayamount());
				}
				to_pay.setVisibility(View.GONE);
				linear_buttom.setVisibility(View.GONE);
			}
	}

	//更加状态显示不同的view
	private void StatusVisible() {
		tv_goods_price.setTextColor(getResources().getColor(R.color.black));
		tv_send_price.setTextColor(getResources().getColor(R.color.black));
		tv_total_price.setTextColor(getResources().getColor(R.color.black));
		linear_subscription.setVisibility(View.VISIBLE);//显示已付订金视图
		tv_dong.setVisibility(View.GONE);
		if(bean.getDeamount()!=null)
			tv_subscription.setText(bean.getDeamount());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.to_pay:
			//需要支付尾款
			if(status==1 || status==2){
			Intent intents = new Intent(mContext,JDPayActivity.class);
			intents.putExtra("flag", 2);//传递2是订单详细支付跳转过去
			intents.putIntegerArrayListExtra("orderId", orderId);//传递子订单号集合
			StartActivityUtil.startActivity((Activity) mContext, intents);
			//确认收货
			}else if(status==7){
				requestMode = 2;//请求收到货接口
				ApiHttpCilent.getInstance(getApplicationContext()).ConfirmGoods(mContext,id,new MyCallBack());
			}
			break;
		default:
			break;
		}
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
		return "订单详情";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return null;
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
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
