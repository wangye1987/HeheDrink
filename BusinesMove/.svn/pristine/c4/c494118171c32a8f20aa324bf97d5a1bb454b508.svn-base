package com.heheys.ec.controller.activity;/*package com.heheys.ec.controller.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.ToastUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.NewOrderInfoAdapter;
import com.heheys.ec.model.adapter.OrderInfoAdapter;
import com.heheys.ec.model.dataBean.AddressListBean;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BasebeanSign.ResultSignBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderItemList;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderList;
import com.heheys.ec.model.dataBean.PaymentBean;
import com.heheys.ec.model.dataBean.PaymentBean.PayList;
import com.heheys.ec.model.dataBean.ShoppingCartListBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.Alipay;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.thirdPartyModule.payModule.PayResult;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.view.OrderDialog;
import com.heheys.ec.view.OrderDialog.RefreshAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

*//**
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-21 下午7:50:37 类说明
 * @param 创建预付订单界面
 *//*
public class OrderDetailActivity extends BaseActivity {
	private TextView delivery_price;
	private ListView lv_order;
	private Context context;
	private LinearLayout pay_type;
	private LinearLayout linear_address;
	private LinearLayout linear_name;
	private int type = 2;// 1 微信 2 京东
	private static final int JD_PAY = 1;
	private static final int ZFB_PAY = 2;
	private static final int WX_PAY = 3;
	private int paytype = -1;//是否选中支付方式
	private Button to_pay;
	private MyHandler handler = new MyHandler(this);
	private Context mContext;
	private List<OrderItemList> list;//所有产品集合
	private List<OrderItemList> listRemove = new ArrayList<OrderItemList>();//待删除iid所有产品集合
	private List<OrderItemList> listRemoveSimple = new ArrayList<OrderItemList>();//待删除iid所有产品集合临时变量
	private OrderList bean;// 服务器返回数据bean
	private TextView tv_final;
	private TextView tv_total_price;
	private TextView tv_white;
	private TextView tv_send_price;
	private TextView tv_goods_price;
	private TextView tv_address;
	private TextView tv_mobile;
	private TextView tv_name;
	private ImageView iv_arrow;
	private ShoppingCartListBean obj;
	private NewOrderInfoAdapter adapter;
	private LinearLayout add_item;
	private List<ShoppingCartSelectBean> listshop;
	private int flag;
	private int addressId;
	private LinearLayout linear_buttom;
	// private View footerViewbg;
	private FrameLayout bottomParent;
	private int duration;
	private CreatebaseOrderBean createorderbean;
	private List<ShoppingCartSelectBean> listiid;
	private boolean nodata;
	private LinearLayout linear_zfb,linear_jd,linear_wx;
	private ImageView iv_wx,iv_zfb,iv_jd;
	private void initView() {
		bottomParent = (FrameLayout) findViewById(R.id.order_detail_bottom_parent);
		mContext = OrderDetailActivity.this;
		lv_order = (ListView) findViewById(R.id.order_lv);
		View headerView = getLayoutInflater().inflate(R.layout.listview_header,
				null);
		View footerView = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		lv_order.addHeaderView(headerView);

		// footer视图不可点击

		lv_order.addFooterView(footerView, null, true);
		lv_order.setDivider(null);
		lv_order.setDividerHeight(0);
//		lv_order.addFooterView(footerViewbg);

		 header view 
		iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
		tv_name = (TextView) headerView.findViewById(R.id.tv_name_connact);
		tv_mobile = (TextView) headerView.findViewById(R.id.tv_mobile);
		tv_address = (TextView) headerView.findViewById(R.id.tv_address);
		linear_address = (LinearLayout) headerView
				.findViewById(R.id.linear_address);
		linear_name = (LinearLayout) headerView.findViewById(R.id.linear_name);

		 footer view 
		pay_type = (LinearLayout) footerView.findViewById(R.id.pay_type);
		delivery_price = (TextView) footerView
				.findViewById(R.id.delivery_price);
		tv_goods_price = (TextView) footerView
				.findViewById(R.id.tv_goods_price);
		tv_send_price = (TextView) footerView.findViewById(R.id.tv_send_price);
		tv_total_price = (TextView) footerView
				.findViewById(R.id.tv_total_price);
		
		linear_zfb = (LinearLayout) footerView
				.findViewById(R.id.linear_zfb);
		linear_wx = (LinearLayout) footerView
				.findViewById(R.id.linear_wx);
		linear_jd = (LinearLayout) footerView
				.findViewById(R.id.linear_jd);
		iv_wx = (ImageView) footerView
				.findViewById(R.id.iv_wx);
		iv_jd = (ImageView) footerView
				.findViewById(R.id.iv_jd);
		iv_zfb = (ImageView) footerView
				.findViewById(R.id.iv_zfb);
		linear_jd.setOnClickListener(paytypelister);
		linear_zfb.setOnClickListener(paytypelister);
		linear_wx.setOnClickListener(paytypelister);
		

		tv_white = (TextView) footerView.findViewById(R.id.tv_white);

		linear_buttom = (LinearLayout) findViewById(R.id.linear_buttom);
		to_pay = (Button) findViewById(R.id.to_pay);
		tv_final = (TextView) findViewById(R.id.tv_final);
		add_item = (LinearLayout) findViewById(R.id.add_item);// 动态添加商品名称视图
		to_pay.setOnClickListener(this);
		linear_address.setOnClickListener(this);
		iv_arrow.setVisibility(View.VISIBLE);
	}

	private int getTargetHeight(View v) {
		try {
			Method m = v.getClass().getDeclaredMethod("onMeasure", int.class,
					int.class);
			m.setAccessible(true);
			m.invoke(v, MeasureSpec.makeMeasureSpec(
					((View) v.getParent()).getMeasuredWidth(),
					MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED));
		} catch (Exception e) {

		}
		return v.getMeasuredHeight();
	}

	private void initDate() {
		list = new ArrayList<OrderItemList>();
		Intent intent = getIntent();
		if (intent != null) {
			// 获取购物车传递过来的ShoppingCartListBean信息
			obj = (ShoppingCartListBean) intent
					.getSerializableExtra(ConstantsUtil.SHOPCAR_PRODUCT_LIST_KEY);
			listshop = obj.getListDataBeans();
			bean = (OrderList) intent
					.getSerializableExtra(ConstantsUtil.ORDER_CREATEPREPARE_KEY);
			setDate();
			list = bean.getList();
			adapter = new NewOrderInfoAdapter(list, context);
			lv_order.setAdapter(adapter);
		}
	}

	// 回填数据bean
	private void setDate() {
		to_pay.setEnabled(true);
		if (bean.getAddress().getAddress() == null
				|| bean.getAddress().getAddress().equals("")) {
			linear_name.setVisibility(View.GONE);
			tv_address.setText("增加收货地址");
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
//			lp.height = ViewUtil.px2dip(context, 130);
//			tv_address.setLayoutParams(lp);
		} else {
			linear_name.setVisibility(View.VISIBLE);
			tv_name.setText("  " + bean.getAddress().getName());
			tv_mobile.setText("  " + bean.getAddress().getMobile());
			tv_address.setText(bean.getAddress().getProvincename()
					+ bean.getAddress().getCityname()
					+ bean.getAddress().getCountyname()
					+ bean.getAddress().getAddress());
			addressId = bean.getAddress().getId();
		}
		linear_buttom.setVisibility(View.VISIBLE);
//		ViewUtil.setActivityPrice(tv_final, bean.getAmount());// 支付定金总额
		ViewUtil.setActivityPrice(tv_send_price, bean.getTransamount());// 运费
//		ViewUtil.setActivityPrice(tv_total_price, bean.getTotal());// 订单总金额
//		ViewUtil.setActivityPrice(tv_goods_price, bean.getTotalamount());
		// 如果是删除地址后返回就不重新加载下面的视图
		if (flag == 2)
			return;
		if (list == null)
			return;
		list = bean.getList();
		int size = list.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				LinearLayout linearLayout = new LinearLayout(mContext);
				linearLayout.setOrientation(0);// 0 横向排列
				TextView tv = new TextView(mContext);
				tv.setSingleLine();
				tv.setEllipsize(TruncateAt.END);
				tv.setMaxEms(10);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				// lp.setMargins(10, 5, 10, 5);
				tv.setLayoutParams(lp);
				tv.setText(list.get(i).getName());
				tv.setTextColor(mContext.getResources().getColor(R.color.white));
				TextView tvnotice = new TextView(mContext);
				tvnotice.setText("  商品订金:");
				TextView tvprice = new TextView(mContext);
				// tvprice.setTextColor(getResources().getColor(R.color.color_ff0000));
				ViewUtil.setActivityPrice(tvprice, list.get(i).getDamount());
				tvprice.setTextColor(mContext.getResources().getColor(
						R.color.white));
				tvnotice.setTextColor(mContext.getResources().getColor(
						R.color.white));
				linearLayout.addView(tv);
				linearLayout.addView(tvnotice);
				linearLayout.addView(tvprice);
				add_item.addView(linearLayout);
			}
			int height = ViewUtil.getHeight(bottomParent);
			LinearLayout llLayout = new LinearLayout(baseContext);
			View childView = new View(baseContext);
			childView.setBackgroundColor(getResources().getColor(R.color.white));

			LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, height);
			llLayout.addView(childView, llParams);
			lv_order.addFooterView(llLayout);
		}
	}

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.order_detail);
		duration = (int) System.currentTimeMillis();// 刚加载页面时间
		context = OrderDetailActivity.this;
		initView();
		initDate();
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		
	}

	OnClickListener paytypelister = new OnClickListener() {
		@Override
		public void onClick(View v) {
				switch (v.getId()) {
				case R.id.linear_jd:
					inVisible();
					visible(iv_jd);
					paytype = JD_PAY;
					break;
				case R.id.linear_zfb:
					inVisible();
					visible(iv_zfb);
					paytype = ZFB_PAY;
					break;
				case R.id.linear_wx:
					inVisible();
					visible(iv_wx);
					paytype = WX_PAY;
					break;
				default:
					break;
				}
			}
	};
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

	private void Dimess() {
		runOnUiThread(new Runnable() {
			public void run() {
				ApiHttpCilent.loading.dismiss();
			}
		});
	}

//	class MyCallBack extends BaseJsonHttpResponseHandler<PaymentBaseBean> {
//		@Override
//		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//				String arg3, PaymentBaseBean arg4) {
//			Dimess();
//		}
//
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, String arg2,
//				PaymentBaseBean arg3) {
//			Dimess();
//		}
//
//		@Override
//		protected PaymentBaseBean parseResponse(String response, boolean arg1)
//				throws Throwable {
//			// TODO Auto-generated method stub
//			Dimess();
//			Gson gson = new Gson();
//			java.lang.reflect.Type type = new TypeToken<PaymentBaseBean>() {
//			}.getType();
//			PaymentBaseBean bean = gson.fromJson(response, type);
//			Message message = Message.obtain();
//			if ("1".equals(bean.getStatus())) {// 正常
//				message.what = ConstantsUtil.HTTP_SUCCESS;
//				message.obj = bean.getResult();
//			} else {
//				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
//						.getCode())) {
//					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
//				} else {
//					message.what = ConstantsUtil.HTTP_FAILE;// 错误
//					message.obj = bean.getError().getInfo();
//				}
//			}
//			handler.sendMessage(message);
//			return bean;
//		}
//	}

	public class MyHandler extends WeakHandler<OrderDetailActivity> {

		private Alipay alipay;

		@SuppressLint("HandlerLeak")
		public MyHandler(OrderDetailActivity reference) {
			super(reference);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				if(flag==3){//处理生成订单成功事件
					if(paytype == JD_PAY){
						ArrayList<Integer> orderList =  (ArrayList<Integer>) msg.obj;
						Intent intents = new Intent(context, JDPayActivity.class);
						intents.putExtra("flag", 1);// 传递1是购物车跳转过去
						intents.putIntegerArrayListExtra("orderList", orderList);
						StartActivityUtil.startActivity((Activity) context, intents);
					}else if(paytype == ZFB_PAY){
//						alipay = new Alipay(getReference(),handler);
//						alipay.pay();
					}else if(paytype == WX_PAY){
						
					}
				}else{
					bean = (OrderList) msg.obj;
					if (bean != null) {
						list = bean.getList();
						adapter.setNewData(list);
						setDate();
						to_pay.setEnabled(true);
						to_pay.setBackground(getResources().getDrawable(
								R.drawable.sharp_round));
					}
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				// 处理设置默认地址接口成功
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				//处理鉴权成功事件
				ResultSignBean resultbean = (ResultSignBean) msg.obj;
				if(resultbean != null)
				alipay.Topay(resultbean.getSign());
				break;
			case ConstantsUtil.HTTP_NEED_LOGIN:
				StartActivityUtil.startActivity((Activity) context,
						LoginActivity.class);
				to_pay.setEnabled(false);
				to_pay.setBackground(getResources().getDrawable(
						R.drawable.verifystatus_button));
				break;
			case ConstantsUtil.HTTP_FAILE:
				if(flag==3){//处理生成订单时候的错误
					if(createorderbean.getError().getCode().equals("116")){//失效订单或者被抢光订单
				    final List<String> listiid = createorderbean.getError().getErrmsg();//返回失效订单iid集合
					listRemove.clear();
					listRemoveSimple.clear();//存储没有失效订单集合
//					for(String error:listiid){
//						for(OrderList paylist:list){
//							if(paylist.getIid().equals(error)){
//									listRemove.add(paylist);//异常产品集合
//							}else{
//								if(!listRemoveSimple.contains(paylist))
//									listRemoveSimple.add(paylist);//正常产品集合
//							}
//						}
//					}
//					new OrderDialog().CreateDialog(baseActivity,listRemoveSimple,listRemove,new RefreshAdapter() {
//						@Override
//						public void setData(List<PayList> data) {
//							if(data!=null){
//							List<ShoppingCartSelectBean> listNew = new ArrayList<ShoppingCartSelectBean>();
//							for(PayList paylist:listRemoveSimple){
//										ShoppingCartSelectBean bean = new ShoppingCartSelectBean();
//										bean.setId(paylist.getAid()+"");
//										bean.setNum(paylist.getNum());
//										listNew.add(bean);
//							 }
//							listshop = listNew;
//							adapter.setNewData(listRemoveSimple);
//							}
//						}
//					});
					}else{
						String back = (String) msg.obj;
						ToastUtil.showToast(context, back);
					}
				}else{
					String back = (String) msg.obj;
					ToastUtil.showToast(context, back);
					to_pay.setEnabled(false);
					to_pay.setBackground(getResources().getDrawable(
							R.drawable.verifystatus_button));
				}
				break;
			case SDK_PAY_FLAG: 
				PayResult payResult = new PayResult((String) msg.obj);
				*//**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 *//*
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(getReference(), "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(getReference(), "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(getReference(), "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			
			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		// case R.id.pay_type:
		// ToastUtil.showToast(context, "支付方式");
		// Bundle bundle = new Bundle();
		// bundle.putInt("type", ConstantsUtil.WX_PAY);//1 微信支付 2京东支付
		// StartActivityUtil.startActivityForResult((Activity) context,
		// PayTypeActivity.class, bundle, ConstantsUtil.ACTIVITY_BACK);
		// break;
		case R.id.to_pay:
			if(ToastNoNetWork.ToastError(context))
				return;
			map.put("pingdan","");
			MobclickAgent.onEvent(baseActivity, "0051", map); 
			if (bean != null) {
				duration = (int) System.currentTimeMillis() - duration;// 点击支付按钮时间
				HashMap<String, String> map_value = new HashMap<String, String>();
//				map_value.put("price", bean.getAmount());// 支付订单金额 (定金或者是订单总额)
				MobclickAgent.onEventValue(this, "pay", map_value, duration);
			}
			if (addressId == 0) {
				ToastUtil.showToast(context, "您的收货地址没有填写");
				return;
			}
			if(paytype == -1){
				ToastUtil.showToast(context, "您的支付方式没有填写");
				return;
			}
			flag = 3;//生成订单标志
//			Context mContext,String from,
//			List<ShoppingCartSelectBean> list, int addressid, int paytype,String oid,
//
//			BaseJsonHttpResponseHandler<CreatebaseOrderBean> callback
//			ApiHttpCilent.getInstance(context).CreatweOrder(context,"1", listshop, addressId, paytype, new MyOrderCallBack());
			break;
		case R.id.linear_address:
			map.put("address","");
			MobclickAgent.onEvent(baseActivity, "0049", map); 
			flag = 2;
			Intent intent = new Intent(context, SettingBaseActivity.class);
			intent.putExtra("type", ConstantsUtil.ADD_MANAGER);
			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE);
			break;
		case R.id.base_activity_title_backicon:
			map.put("orderdetaileback","");
			MobclickAgent.onEvent(baseActivity, "0052", map); 
			// 返回键处理
			onBackPressed();
			break;
		
		default:
			break;
		}
	}

	void inVisible(){
		iv_wx.setVisibility(View.INVISIBLE);
		iv_jd.setVisibility(View.INVISIBLE);
		iv_zfb.setVisibility(View.INVISIBLE);
	}
	void visible(ImageView iv){
		ImageView visibleView = null;
		if(iv_jd == iv)
			visibleView = iv;
		else if(iv_zfb == iv)
			visibleView = iv;
		else if(iv_wx == iv)
			visibleView = iv;
			
		if(visibleView != null)
			visibleView.setVisibility(View.VISIBLE);
	}
	
	class MyOrderCallBack extends BaseJsonHttpResponseHandler<CreatebaseOrderBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, CreatebaseOrderBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			handler.sendMessage(message);
		}
		
		@Override
		
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				CreatebaseOrderBean arg3) {
			Dimess();
		}
		
		
		@Override
		protected CreatebaseOrderBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			createorderbean = gson.fromJson(response, CreatebaseOrderBean.class);
			Message message = Message.obtain();
			if ("1".equals(createorderbean.getStatus()) ) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
//				message.obj  = createorderbean.getResult().getSid();
			}else {
				if(ConstantsUtil.ERROE_LOGIN_CODE.equals(createorderbean.getError().getCode())){
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				}else{
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = createorderbean.getError().getInfo();
				}
			}
			handler.sendMessage(message);
			return createorderbean;
		}
	}
	
	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "提交订单";
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (ConstantsUtil.ACTIVITY_BACK == requestCode) {
			if (intent != null)
				type = intent.getIntExtra("type", 0);
			switch (type) {
			case ConstantsUtil.WX_PAY:
				delivery_price.setText(context.getText(R.string.weixingpay));
				break;
			case ConstantsUtil.JD_PAY:
				delivery_price.setText(context.getText(R.string.jd_pay));
				break;
			default:
				break;
			}
		} else if (ConstantsUtil.REQUEST_CODE == requestCode) {
			if (intent == null)
				return;
			AddressListBean bean = (AddressListBean) intent
					.getSerializableExtra("bean");
			nodata = intent.getBooleanExtra("nodata", false);
			if(nodata){
				linear_name.setVisibility(View.GONE);
				tv_address.setText("增加收货地址");
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
//				lp.height = ViewUtil.px2dip(context, 130);
//				tv_address.setLayoutParams(lp);
			}
			if (bean == null) {
				return;
			}else{
				flag = 1;// 点击回调地址不刷新请求 如果是就改变flag不刷新预付订单接口
				tv_name.setText(bean.getName());
				tv_mobile.setText(bean.getMobile());
				tv_address.setText(bean.getProvincename() + bean.getCityname()
						+ bean.getCountyname() + bean.getAddress());
				addressId = bean.getId();
				linear_name.setVisibility(View.VISIBLE);
				// 调取设置默认地址接口
				ApiHttpCilent.getInstance(mContext).SetDefaultAddress(mContext,
						addressId, new MyAddressCallBack());
			}
		}
	}

	
	 * 设置默认地址 *
	 
	class MyAddressCallBack extends BaseJsonHttpResponseHandler<BaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Dimess();
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<BaseBean>() {
			}.getType();
			BaseBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = bean.getError().getInfo();
			}
			handler.sendMessage(message);
			return bean;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		if (flag == 2) {
//			ApiHttpCilent.getInstance(mContext).CreatPaymentOrder(mContext,
//					listshop, new MyOrderCallBack());
//			ApiHttpCilent.getInstance(context).CreateOrder(context, listshop, addressId, type, new MyOrderCallBack());
//		}
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	private static final int SDK_PAY_FLAG = 1;
}
*/