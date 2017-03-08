package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.NewOrderInfoAdapter;
import com.heheys.ec.model.dataBean.AddressListBean;
import com.heheys.ec.model.dataBean.AlipayBaseBean;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BaseHehePayBean;
import com.heheys.ec.model.dataBean.BasebeanSign.ResultSignBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean.SidBean;
import com.heheys.ec.model.dataBean.InvoiceHistoryBean.Invoice;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.Hehemoney;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderItemList;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderList;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.Score;
import com.heheys.ec.model.dataBean.PayCouponbean;
import com.heheys.ec.model.dataBean.PaymentBean.PayList;
import com.heheys.ec.model.dataBean.ShoppingCartListBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.WXPayBaseBean;
import com.heheys.ec.model.dataBean.WXPayBaseBean.weChat;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.Alipay;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.thirdPartyModule.payModule.PayResult;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BalanceCallBack;
import com.heheys.ec.view.AlertDialogCustom.HeheCoinCallBack;
import com.heheys.ec.view.OrderDialog;
import com.heheys.ec.view.OrderDialog.RefreshAdapter;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import static com.heheys.ec.R.id.linear_fp;
//import static com.heheys.ec.R.id.linear_hb;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-23 下午1:25:48 类说明 确认订单界面  购物车或者详情界面进入
 *  @param
 */
public class  NewOrderDetailActivity extends BaseActivity {
	private TextView delivery_price;
	private ListView lv_order;
	private Context context;
	private LinearLayout pay_type;
	private LinearLayout linear_address;
	private LinearLayout linear_name;
	private int type = 2;// 1 微信 2 京东
	private String paytype = "";// 是否选中支付方式
	private Button to_pay;
	private MyHandler handler;
	private Context mContext;
	private List<OrderItemList> list = new ArrayList<>();// 所有产品集合
	private List<PayList> listRemove = new ArrayList<>();// 待删除iid所有产品集合
	private List<PayList> listRemoveSimple = new ArrayList<PayList>();// 待删除iid所有产品集合临时变量
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
	private FrameLayout bottomParent;
	private CreatebaseOrderBean createorderbean;
	private List<ShoppingCartSelectBean> listiid;
	private boolean isneedinvoice = false;//是否需要发票
	private boolean nodata;
	private String fronttitle ;
	private LinearLayout linear_zfb, linear_jd, linear_wx;
	private ImageView iv_wx, iv_zfb, iv_jd;
	boolean iscancle = false;
	public static final int SDK_PAY_FLAG = 1;
	private ImageView iv_pay;
	private TextView tv_pay,tv_hehebalance;
	private TextView tv_yunfei;
	private TextView tv_transuredec;
	private TextView tv_totalNum;
	private LinearLayout linear_pay;
	private String from = "1";
	private String unique_id;//服务器返回随机数
	private SidBean orderId;//生成订单返回的订单号
	private IWXAPI api;
	private AlipayBaseBean alibasebean;
	private WXPayBaseBean wxpaybasebean;
	private SidBean goodsOrderId;
	private Alipay alipay;
	private String balance;
	private String hehebalance;//喝喝币余额
	private LinearLayout linear_yhj;
//	private LinearLayout linear_fp;
//	private LinearLayout linear_jf;
//	private LinearLayout linear_hb;
	private PayCouponbean coupon;
	private TextView tv_yhj;
//	private TextView tv_fp;
//	private TextView tv_jf;
//	private TextView tv_hb;
	private List<ShoppingCartSelectBean> selectProductNotInEdit;//购物车提交数据
	private String couponid_sub;//优惠卷ID
	private int id_type ;//普通发票 1 增值税发票 2
	private int id_title = -1;//个人1 公司 2
	private int id_content = -1;//明细 0 酒水 1
	private String id_title_content;
	private Invoice invoice;//选择的发票信息
	// 生产预付订单信息消息处理
	private MyOrderHandler orderMessageHandler;
	private String address;//回踩地址 计算运费
	private String score;
	private RelativeLayout re_parent;
	private String coin;//使用喝喝币数量
	private String isdefault = "1";//默认传1 返回预付单包含最优优惠卷
	private Hehemoney coinbean;//我的喝喝币beans
	private SpannableStringBuilder spannable;
	private ForegroundColorSpan colorSpan;
	private AlertDialogCustom codedialog;
	private Hehemoney hehemoney;
	private Score scorebean;
	private BaseHehePayBean baseHehePayBean;
	private int startIndex;
	private int endIndex;
	private boolean islist;
	private void initView() {
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
		re_parent = (RelativeLayout) findViewById(R.id.re_parent);
		bottomParent = (FrameLayout) findViewById(R.id.order_detail_bottom_parent);
		mContext = NewOrderDetailActivity.this;
		orderMessageHandler = new MyOrderHandler(NewOrderDetailActivity.this);
		lv_order = (ListView) findViewById(R.id.order_lv);
		tv_totalNum = (TextView) findViewById(R.id.tv_totalNum);
		tv_final = (TextView) findViewById(R.id.tv_final);
		to_pay = (Button) findViewById(R.id.to_pay);
		View headerView = getLayoutInflater().inflate(R.layout.listview_header,
				null);
		View footerView = getLayoutInflater().inflate(R.layout.new_listview_footer,
				null);
		lv_order.addHeaderView(headerView);
		// footer视图不可点击

		lv_order.addFooterView(footerView, null, true);
		lv_order.setDivider(null);
		lv_order.setDividerHeight(0);
		iv_pay = (ImageView) footerView.findViewById(R.id.iv_pay);
		tv_pay = (TextView) footerView.findViewById(R.id.tv_pay);
		tv_hehebalance = (TextView) footerView.findViewById(R.id.tv_hehebalance);
		linear_pay = (LinearLayout) footerView.findViewById(R.id.linear_pay);
		tv_transuredec = (TextView) footerView.findViewById(R.id.tv_transuredec);
		tv_yunfei = (TextView) footerView.findViewById(R.id.tv_yunfei);
		linear_yhj = (LinearLayout) footerView.findViewById(R.id.linear_yhj);
		tv_yhj = (TextView) footerView.findViewById(R.id.tv_yhj);

//		linear_fp = (LinearLayout) footerView.findViewById(linear_fp);
//		tv_fp = (TextView) footerView.findViewById(R.id.tv_fp);
//		linear_jf = (LinearLayout) footerView.findViewById(linear_jf);
//		tv_jf = (TextView) footerView.findViewById(R.id.tv_jf);
//		linear_hb = (LinearLayout) footerView.findViewById(linear_hb);
//		tv_hb = (TextView) footerView.findViewById(R.id.tv_hb);

		/* header view */
		iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
		tv_name = (TextView) headerView.findViewById(R.id.tv_name_connact);
		tv_mobile = (TextView) headerView.findViewById(R.id.tv_mobile);
		tv_address = (TextView) headerView.findViewById(R.id.tv_address);
		linear_address = (LinearLayout) headerView
				.findViewById(R.id.linear_address);
		linear_name = (LinearLayout) headerView.findViewById(R.id.linear_name);
		linear_address.setOnClickListener(this);
		linear_pay.setOnClickListener(this);
		to_pay.setOnClickListener(this);
		linear_yhj.setOnClickListener(this);
//		linear_fp.setOnClickListener(this);
//		linear_jf.setOnClickListener(this);
//		linear_hb.setOnClickListener(this);
		adapter = new NewOrderInfoAdapter(list, context);
		lv_order.setAdapter(adapter);
		codedialog = new AlertDialogCustom();
		handler = new MyHandler(this);
	}


	
	@SuppressWarnings("unchecked")
	private void initDate() {
		try {
			if(SecurityInit.Initialize(this) == 0){
				 SecurityCipher securityCipher = new SecurityCipher(this);
				 String wx_later_wx = securityCipher.decryptString(ConstantsUtil.APP_ID,ConstantsUtil.JAQ_KEY);
				 	api =  WXAPIFactory.createWXAPI(this, wx_later_wx);
				 }else{
					 api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
				 }
		} catch (JAQException e) {
			// TODO Auto-generated catch block
			api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
			e.printStackTrace();
		}
		
		colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_af2942));
		list = new ArrayList<>();
		Intent intent = getIntent();
		if (intent != null) {
			from = intent.getStringExtra("from");
			if(StringUtil.isEmpty(from))
				from = "1";
			bean = (OrderList) intent.getSerializableExtra("bean");
			selectProductNotInEdit = (List<ShoppingCartSelectBean>) intent.getSerializableExtra("selectProductNotInEditToNext");
			SuccessCPreOrder(bean);
		}
	}

	//生成预览订单
	private void Preprview() {
		ApiHttpCilent.getInstance(getApplicationContext()).CreatPaymentOrder(baseActivity,paytype,isdefault,score,addressId,selectProductNotInEdit, from,couponid_sub,new MyShoppingCallBack());
	}
	class MyShoppingCallBack extends BaseJsonHttpResponseHandler<NewOrderBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewOrderBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewOrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected NewOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<NewOrderBaseBean>() {
			}.getType();
			NewOrderBaseBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
						.getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = bean.getError().getInfo();
				}
			}
			orderMessageHandler.sendMessage(message);
			return bean;
		}
	}

	public static class MyOrderHandler extends WeakHandler<NewOrderDetailActivity> {

		@SuppressLint("HandlerLeak")
		public MyOrderHandler(NewOrderDetailActivity reference) {
			super(reference);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bean = (OrderList) msg.obj;
				getReference().SuccessCPreOrder(getReference().bean);
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(getReference().baseActivity, back);
				break;
			default:
				break;
			}
		}

	}
	//创建预览订单成功
	private void SuccessCPreOrder(OrderList bean) {
		if (bean != null) {
			unique_id = bean.getUnique_id();
			ShoppingCartListBean obj = new ShoppingCartListBean();
			obj.setListDataBeans(selectProductNotInEdit);
			listshop = obj.getListDataBeans();//购车缓存数据
			setDefault();
			list = bean.getList();
			setDate();//绑定数据
			adapter.setNewData(list);
			adapter.notifyDataSetChanged();
			Utils.setListViewHeightBasedOnChildren(lv_order);
		}
	}

	//如果SID 或者AID没有数据就给初始值
	void setDefault(){
		if(listshop == null || listshop.size() == 0)
			return;
		for(ShoppingCartSelectBean select : listshop){
			if(StringUtil.isEmpty(select.getAid()))
				select.setAid("");
			if(StringUtil.isEmpty(select.getSid()))
				select.setSid("");
		}
	}
	// 回填数据bean
	private void setDate() {
		if (bean.getAddress().getAddress() == null
				|| bean.getAddress().getAddress().equals("")) {
			linear_name.setVisibility(View.GONE);
			tv_address.setText("增加收货地址");
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
//		linear_buttom.setVisibility(View.VISIBLE);
		// 如果是删除地址后返回就不重新加载下面的视图
		if(bean.getPaytype() != null){
			paytype = bean.getPaytype();
			setPayType(paytype);
		}
		if (flag == 2)
			return;
		if (list == null)
			return;
		tv_transuredec.setText(bean.getTransdesc());
		tv_yunfei.setText(bean.getTransamount());
		tv_totalNum.setText("共计"+bean.getTotalnum()+"件");
//		if(!StringUtil.isEmpty(bean.getCouponnum())){
//			if(Integer.parseInt(bean.getCouponnum())>0){
//				tv_yhj.setText("有优惠券可用");
//			}else{
//				tv_yhj.setText("暂无优惠券可用");
//			}
//		}
		if(bean.getUsecoupon()){
			if(bean.getCoupon() !=null){
				tv_yhj.setText(bean.getCoupon().getMsg());
				couponid_sub = bean.getCoupon().getCouponid();
			}
		}
		if(bean.getScore()!=null && !StringUtil.isEmpty(bean.getScore().getScoreblance())){
//			tv_jf.setText(bean.getScoreinfo());
//			String userscoretx = tv_jf.getText().toString().trim();
//			spannable = new SpannableStringBuilder(userscoretx);
//			if(userscoretx.contains("￥")){
//			spannable.setSpan(colorSpan, userscoretx.indexOf("￥")+1,userscoretx.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tv_jf.setText(spannable);
//			}
			score = bean.getScore().getUsescore();
			}
		coinbean = bean.getCoin();
		if(coinbean !=null)
		coin = coinbean.getUsecoin();
//		if(coinbean!=null && !StringUtil.isEmpty(coinbean.getCoinblance())){
//			tv_hb.setText(bean.getCoininfo());
//			String usercointx = tv_hb.getText().toString().trim();
//			spannable = new SpannableStringBuilder(usercointx);
//			if(usercointx.contains("￥")){
//			spannable.setSpan(colorSpan, usercointx.indexOf("￥")+1,usercointx.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tv_hb.setText(spannable);
//			}
//		}
		ViewUtil.setActivityPrice(tv_final, bean.getTotalprice());
		int height = ViewUtil.getHeight(bottomParent);
		LinearLayout llLayout = new LinearLayout(baseContext);
		View childView = new View(baseContext);
		childView.setBackgroundColor(getResources().getColor(R.color.white));

		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, height);
		llLayout.addView(childView, llParams);
		lv_order.addFooterView(llLayout);
		to_pay.setEnabled(true);
        boolean isNotSupport = true;
		int sizes = list.size();
		for(int i=0;i<sizes;i++){
			if(!StringUtil.isEmpty(list.get(i).getErrorInfo())){
				isNotSupport = false;
				break;
			}
		}

		if(!isNotSupport){
			to_pay.setEnabled(false);
			to_pay.setBackgroundResource(R.drawable.shape_button_gray);
		}else{
			to_pay.setBackgroundResource(R.drawable.pay_bt);
		}
	}

	@SuppressLint("ResourceAsColor") private void setPayType(String type){
		/*
		 * 生成下面的视图
		 * 
		 * **/
		tv_hehebalance.setVisibility(View.INVISIBLE);
		iv_pay.setVisibility(View.VISIBLE);
		linear_pay.setEnabled(true);
		linear_yhj.setEnabled(true);
//		linear_fp.setEnabled(true);
		linear_yhj.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//		linear_fp.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//		tv_fp.setTextColor(mContext.getResources().getColor(R.color.color_999999));
//		tv_yhj.setTextColor(mContext.getResources().getColor(R.color.color_999999));
//		tv_fp.setText("暂不开发票");
//		tv_yhj.setText("喝喝币支付暂使用优惠券");
		if(!StringUtil.isEmpty(bean.getCouponnum())){
			if(Integer.parseInt(bean.getCouponnum())>0){
				tv_yhj.setText("有优惠券可用");
			}else{
				tv_yhj.setText("暂无优惠券可用");
			}
		}
//		setInvoce();
		if("0".equals(type)){
			tv_pay.setText("余额支付");
			iv_pay.setImageResource(R.drawable.hehe);
			tv_hehebalance.setVisibility(View.VISIBLE);
			balance = bean.getBalance();
			ViewUtil.setActivityPrice(tv_hehebalance, balance);
		}else if("1".equals(type)){
			tv_pay.setText("支付宝");
			iv_pay.setImageResource(R.drawable.zhifubao);
		}else if("2".equals(type)){
			tv_pay.setText("微信");
			iv_pay.setImageResource(R.drawable.weixin);
		}else if("3".equals(type)){
			tv_pay.setText("京东");
			iv_pay.setImageResource(R.drawable.jingdong);
		}else if("-1".equals(type)){
			tv_pay.setText("货到付款(消耗100积分)");
			iv_pay.setImageResource(R.drawable.arrive);
		}else if("6".equals(type)){
			linear_yhj.setEnabled(false);
//			linear_fp.setEnabled(false);
//			tv_fp.setText("喝币支付不可开具发票");
			tv_yhj.setText("喝币支付不支持使用优惠券");
			linear_yhj.setBackgroundColor(mContext.getResources().getColor(R.color.color_f7f7f7));
//			linear_fp.setBackgroundColor(mContext.getResources().getColor(R.color.color_f7f7f7));
			tv_pay.setText("喝币支付");
			iv_pay.setImageResource(R.drawable.hehe_pay);
			if(bean.getCoin() != null) {
				hehebalance = bean.getCoin().getCoinblance();
				if(!StringUtil.isEmpty(hehebalance)) {
					ViewUtil.setActivityPrice(tv_hehebalance, hehebalance);
					tv_hehebalance.setVisibility(View.VISIBLE);
				}
			}
		}else if("".equals(type)){
			//处理使用喝喝币等情况
			tv_pay.setText(StringUtil.isEmpty(bean.getPaytypeinfo())?"":bean.getPaytypeinfo());
			iv_pay.setVisibility(View.INVISIBLE);
			//支付方式不可点击
			linear_pay.setEnabled(false);
		}
	}
	
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.order_detail);
		context = NewOrderDetailActivity.this;
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
		((Activity) mContext).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}


	/**
	 * 支付成功跳转
	 * */
	private void PaySuccessJump(final String oid) {
		Intent intent = new Intent(baseActivity, PaySuccessActivity.class);
		Bundle bundle = new Bundle();
		bundle.putBoolean("isOrder", true);//订单 区分订单支付结果和喝喝币支付结果
		bundle.putString("orderId", oid);//订单号
		intent.putExtra("bundle", bundle);
		StartActivityUtil.startActivity(baseActivity,intent);
		finish();
	}
	public class MyHandler extends WeakHandler<NewOrderDetailActivity> {
		@SuppressLint("HandlerLeak")
		public MyHandler(NewOrderDetailActivity reference) {
			super(reference);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS: 
				if (flag == 3) {// 处理生成订单成功事件
					orderId = (SidBean) msg.obj;
					goodsOrderId = orderId;
				     if(ConstantsUtil.HEHE_PAY.equals(paytype)){
								//喝喝余额支付
								 iscancle = true;
								 codedialog.CreatePayMsg(4,true,NewOrderDetailActivity.this,orderId.getOid(),new BalanceCallBack() {
									@Override
									public void backBalance(String balance) {
										tv_hehebalance.setText(balance);
										ToastUtil.showToast(mContext, "余额支付成功");
										//余额支付跳转
										PaySuccessJump(orderId.getOid());//第二个参数 0代表支付成 1代表失败
									}
									@Override
									public void close() {
										finish();
										ToastUtil.showToast(mContext, "余额支付失败,请去\"我的订单\"继续支付");
									}
								});
					   }else if(ConstantsUtil.HB_PAY.equals(paytype)){
						   //喝喝币支付
						 //如果使用了喝喝币 发送短消息
					   		CreateCode();
					   }else if(ConstantsUtil.LINE_OFF.equals(paytype)){
							 //货到付款
							 ToastUtil.showToast(mContext, "订单提交成功");
							 //现在订单生成成功跳转
							 PaySuccessJump(orderId.getOid());

					   }else{
						   		ToPay(orderId.getOid(), "");
					   }
				} else {
					if (bean != null) {
						list = bean.getList();
						adapter.setNewData(list);
						setDate();
					}
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_HEHEPAY:
				//全额喝喝币支付
				ToastUtil.showToast(baseActivity, "支付成功");
				//喝喝币支付支付结果页
				PaySuccessJump(orderId.getOid());
//				MyApplication.getInstance().setList(list);
//				Intent intent = new Intent(baseActivity, WXPayEntryActivity.class);
//				intent.putExtra("oid", orderId.getOid());
//				StartActivityUtil.startActivity(baseActivity,intent);
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				// 处理设置默认地址接口成功
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				// 处理鉴权成功事件
				ResultSignBean resultbean = (ResultSignBean) msg.obj;
				if (resultbean != null)
					alipay.Topay(resultbean.getAlipay());
				break;
			case ConstantsUtil.HTTP_SUCCESS_WXPAY:
				//微信支付获取鉴权信息成功
				WXPay();
				break;
			case ConstantsUtil.HTTP_NEED_LOGIN:
				StartActivityUtil.startActivity((Activity) context,
						LoginActivity.class);
//				to_pay.setEnabled(false);
//				to_pay.setBackground(getResources().getDrawable(
//						R.drawable.verifystatus_button));
				break;
			case ConstantsUtil.HTTP_FAILE:
				if (flag == 3) {// 处理生成订单时候的错误
					if(createorderbean !=null && createorderbean.getError() !=null && createorderbean.getError().getCode() !=null){
					if (createorderbean.getError().getCode().equals("116")) {// 失效订单或者被抢光订单
						final List<String> listiid = createorderbean.getError()
								.getErrmsg();// 返回失效订单iid集合
						listRemove.clear();
						listRemoveSimple.clear();// 存储没有失效订单集合
						new OrderDialog().CreateDialog(baseActivity,
								listRemoveSimple, listRemove,
								new RefreshAdapter() {
									@Override
									public void setData(List<PayList> data) {
										if (data != null) {
											List<ShoppingCartSelectBean> listNew = new ArrayList<ShoppingCartSelectBean>();
											for (PayList paylist : listRemoveSimple) {
												ShoppingCartSelectBean bean = new ShoppingCartSelectBean();
												bean.setAid(paylist.getAid()+ "");
												bean.setNum(paylist.getNum());
												listNew.add(bean);
											}
											listshop = listNew;
//											adapter.setNewData(listRemoveSimple);
										}
									}
								});
						}else{
							String back = (String) msg.obj;
							ToastUtil.showToast(context, back);
						}
					} else {
						String back = (String) msg.obj;
						ToastUtil.showToast(context, back);
					}
				} else {
					String back = (String) msg.obj;
					ToastUtil.showToast(context, back);
				}
				break;
			case ConstantsUtil.ALI_BACK_CODE:
				//阿里支付回调
//				"status":"" 0、没有订单支付记录1、支付成功，可退款，2、支付失败，3、等待支付 4、支付超时关闭，5、交易结束，不可退款 
				String ststus = alibasebean.getResult().getStatus();
 				if("1".equals(ststus)){
					ToastUtil.showToast(mContext, "支付成功");
					PaySuccessJump(goodsOrderId.getOid());
				}else if("2".equals(ststus)){
					ToastUtil.showToast(mContext, "支付失败");
				}
 				finish();
				break;
			case SDK_PAY_FLAG:
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				//以服务器结果为最终确认结果
			
				SignAlipay();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				/*if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(getReference(), "支付成功", Toast.LENGTH_SHORT)
							.show();
					PaySuccessJump(goodsOrderId,"0");
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(getReference(), "支付结果确认中",
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(getReference(), "支付失败",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				}*/
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				//重新计算订单价格
			   bean = (OrderList) msg.obj;
			   setDate();
			   adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}

 //发送验证码
		private void CreateCode() {
			// TODO Auto-generated method stub
			codedialog.CreateCoinPayMsg(4,true, (BaseActivity) context, orderId.getOid(),new HeheCoinCallBack() {
				@Override
				public void code(String code) {
					// TODO Auto-generated method stub
						ToPay(orderId.getOid(),code);
				}
			} );
		}
		//支付订单
		private void ToPay(final String oid,String code) {
			Intent intent = new Intent();
			intent.setAction("com.order.success");  
			sendBroadcast(intent);
			if (ConstantsUtil.JD_PAY.equals(paytype)) {
				//京东支付
				Intent intents = new Intent(context,
						JDPayActivity.class);
				intents.putExtra("flag",2);//
				intents.putExtra("orderId", oid);
				intents.putExtra("code", code);
				StartActivityUtil.startActivity((Activity) context,
						intents);
			} else if (ConstantsUtil.ZFB_PAY.equals(paytype)) {
				//支付宝支付
				alipay = new Alipay(getReference().mContext, handler);
				alipay.pay(oid,code,"");
			} else if (ConstantsUtil.WX_PAY.equals(paytype)) {
				//微信支付
				if(api.isWXAppInstalled() && api.isWXAppSupportAPI()){
					ApiHttpCilent.getInstance(getApplicationContext()).WXPaySign(mContext, oid,code,"wechat", new WXPayCallBack());
				}else{
					ToastUtil.showToast(baseActivity, "请您安装微信客户端在完成支付");
				}
			}else if(ConstantsUtil.HB_PAY.equals(paytype)){
				//当前订单总金额是喝喝币支付 时执行
				ApiHttpCilent.getInstance(getApplicationContext()).PayHeheMoney(mContext, oid,code, new AllHehePayCallBack());
			}
		}

	}
	
	//请求支付宝异步回调结果
		private void SignAlipay(){
			ApiHttpCilent.getInstance(getApplicationContext()).SignPayResult(mContext, goodsOrderId.getOid(),  new AlipayCallBack());
		}
		class AlipayCallBack extends BaseJsonHttpResponseHandler<AlipayBaseBean> {
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
	/**
	 * 微信支付
	 */
	private void WXPay() {
		// 调取微信支付sdk
        if(wxpaybasebean.getResult() != null){
        weChat wechat = wxpaybasebean.getResult().getWechat();
        if(wechat !=null){
        	PayReq request = new PayReq();
        	request.appId = wechat.getAppid();
        	request.partnerId = wechat.getPartnerid();
        	request.prepayId= wechat.getPrepayid();
        	request.packageValue = wechat.getPackages();
        	request.nonceStr= wechat.getNoncestr();
        	request.timeStamp = wechat.getTimestamp();
        	request.sign = wechat.getSign();
        	request.extData		= "app data";
		if(api!=null)
			api.sendReq(request);
			MyApplication.getInstance().setOrder(true);
			MyApplication.getInstance().setOid(goodsOrderId.getOid());
           }
        }
	}
	private String value(int key){
		switch (key) {
		case 0:
			return "喝喝余额支付";
		case 1:
			return "支付宝支付";
		case 2:
			return "微信支付";
		case 3:
			return "京东支付";
		case 5:
			return "线下支付";
		default:
			break;
		}
		return "其他支付";
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		HashMap<String, String> map = new HashMap<String, String>();
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.to_pay:
			if(bean !=null){
				if(!"".equals(paytype))
					map.put("payType", value(Integer.parseInt(paytype)));
				else
					map.put("payType", bean.getPaytypeinfo());
			if(!StringUtil.isEmpty(bean.getTotalprice()))
			MobclickAgent.onEventValue(baseActivity,"S_ORD_SUB_1",map,(int) Float.parseFloat(bean.getTotalprice()));
			if (ToastNoNetWork.ToastError(context))
				return;
			if (addressId == 0) {
				ToastUtil.showToast(context, "您的收货地址没有填写");
				return;
			}
//			if (paytype == -1) {
//				ToastUtil.showToast(context, "您的支付方式没有填写");
//				return;
//			}
			if(paytype.equals(ConstantsUtil.HEHE_PAY) && !StringUtil.isEmpty(balance) && Float.parseFloat(balance)<Float.parseFloat(bean.getTotalprice())){
					 ToastUtil.showToast(context, "您的余额不足,请重新选择支付方式");
				     return;
			}
			flag = 3;// 生成订单标志
			if(coupon!=null){
				couponid_sub = coupon.getCouponid();
			}
			/**
			 * 创建订单
			 * 
			 * */
			ApiHttpCilent.getInstance(getApplicationContext()).CreateOrder(context,score,from,listshop,addressId,
					paytype, unique_id==null?"":unique_id,couponid_sub,id_title,id_title_content,id_content,new MyOrderCallBack());
			}
			break;
			//地址点击事件
		case R.id.linear_address:
			MobclickAgent.onEvent(baseActivity,"C_ORD_SUB_1");
//			map.put("address", "");
//			MobclickAgent.onEvent(baseActivity, "0049", map);
			flag = 2;
			intent.setClass(context, SettingBaseActivity.class);
			intent.putExtra("type", ConstantsUtil.ADD_MANAGER);
//			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE);
			StartActivityUtil.startActivityForResult((Activity) context, intent, ConstantsUtil.REQUEST_CODE);
			break;
		case R.id.base_activity_title_backicon:
			map.put("orderdetaileback", "");
//			MobclickAgent.onEvent(baseActivity, "0052", map);
			// 返回键处理
			onBackPressed();
			break;
		case R.id.linear_pay://支付方式
			if(!"".equals(paytype)){
			MobclickAgent.onEvent(baseActivity,"C_ORD_SUB_2");
			intent.putExtra("payType", paytype);
			intent.putExtra("addressId", addressId);
			intent.putExtra("OrderList", bean);
			intent.setClass(context, PayTypeActivity.class);
			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE_TWO);
			overridePendingTransition(R.anim.dialog_buttom_enter, 0);
			}
			break;
		case R.id.linear_yhj:
			if(bean != null){
			MobclickAgent.onEvent(baseActivity,"C_ORD_SUB_3");
			intent.putExtra("key", "use");//使用
			intent.putExtra("baseamount",bean.getBaseamount());//可用使用优惠券的基准价格
			intent.setClass(baseActivity, CouponActivity.class);
			StartActivityUtil.startActivityForResult(baseActivity, intent, ConstantsUtil.REQUEST_CODE_FOUR);
			}
			break;
//		case linear_fp:
//			if(bean != null){
//			MobclickAgent.onEvent(baseActivity,"C_ORD_SUB_4");
//			//发票
////			if(invoice != null){
//			intent.putExtra("usercouponBean", invoice);
//			intent.putExtra("createOrder", true);//是否是下单界面
//			intent.setClass(baseActivity, InvoiceActivity.class);
//			StartActivityUtil.startActivityForResult(baseActivity, intent, ConstantsUtil.REQUEST_CODE_FIVE);
//				}
////			}
//			break;
//		case linear_jf:
//			//积分
//			if( bean!=null ){
//				scorebean = bean.getScore();
//				if(scorebean == null){
//				scorebean = new Score();
//				scorebean.setMaxscore("0");
//				scorebean.setScoreblance("0");
//				scorebean.setScoreprice("0");
//				scorebean.setUsescore("0");
//				}
//			intent.putExtra("score",scorebean);
//			intent.setClass(context, PayTypeActivity.class);
//			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE_SIX);
//			overridePendingTransition(R.anim.dialog_buttom_enter, 0);
//			}
//			break;
//		case linear_hb:
//			//HEHE
//			if(bean!=null){
//				hehemoney =  bean.getCoin();
//				if(hehemoney == null){
//					hehemoney = new Hehemoney();
//					hehemoney.setMaxcoin("0");
//					hehemoney.setCoinblance("0");
//					hehemoney.setUsecoin("0.00");
//					hehemoney.setCoinprice("0");
//				}
//			intent.putExtra("coin", hehemoney);
//			intent.setClass(context, PayTypeActivity.class);
//			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE_SEVEN);
//			overridePendingTransition(R.anim.dialog_buttom_enter, 0);
//			}
//			break;
//		case R.id.base_activity_title_backicon:
//			//回传页码
//			if(islist){
//			intent.putExtra("startIndex", startIndex);
//			intent.putExtra("endIndex", endIndex);
//			setResult(RESULT_OK, intent);
//			}
//			finish();
		default:
			break;
		}
	}

	void inVisible() {
		iv_wx.setVisibility(View.INVISIBLE);
		iv_jd.setVisibility(View.INVISIBLE);
		iv_zfb.setVisibility(View.INVISIBLE);
	}

	void visible(ImageView iv) {
		ImageView visibleView = null;
		if (iv_jd == iv)
			visibleView = iv;
		else if (iv_zfb == iv)
			visibleView = iv;
		else if (iv_wx == iv)
			visibleView = iv;

		if (visibleView != null)
			visibleView.setVisibility(View.VISIBLE);
	}
	
	
	//全额喝喝币支付
	class AllHehePayCallBack extends
	BaseJsonHttpResponseHandler<BaseHehePayBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseHehePayBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			handler.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseHehePayBean arg3) {
			Dimess();
		}
		
		@Override
		protected BaseHehePayBean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			baseHehePayBean = gson
					.fromJson(response, BaseHehePayBean.class);
			Message message = Message.obtain();
			if ("1".equals(baseHehePayBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_HEHEPAY;
				message.obj = baseHehePayBean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(baseHehePayBean
						.getError().getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = baseHehePayBean.getError().getInfo();
				}
			}
			handler.sendMessage(message);
			return baseHehePayBean;
		}
	}
	/*
	 * 
	 * 微信支付回调
	 * */
	class WXPayCallBack extends
	BaseJsonHttpResponseHandler<WXPayBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, WXPayBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			handler.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				WXPayBaseBean arg3) {
			Dimess();
		}
		
		@Override
		protected WXPayBaseBean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			wxpaybasebean = gson
					.fromJson(response, WXPayBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(wxpaybasebean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_WXPAY;
				message.obj = wxpaybasebean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(wxpaybasebean
						.getError().getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = wxpaybasebean.getError().getInfo();
				}
			}
			handler.sendMessage(message);
			return wxpaybasebean;
		}
	}
	class MyOrderCallBack extends
			BaseJsonHttpResponseHandler<CreatebaseOrderBean> {

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
		protected CreatebaseOrderBean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			createorderbean = gson
					.fromJson(response, CreatebaseOrderBean.class);
			Message message = Message.obtain();
			if ("1".equals(createorderbean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = createorderbean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(createorderbean
						.getError().getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
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
			case 2:
				delivery_price.setText(context.getText(R.string.weixingpay));
				break;
			case 3:
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
			if (nodata) {
				linear_name.setVisibility(View.GONE);
				tv_address.setText("增加收货地址");
			}
			if (bean == null) {
				addressId = 0;
				return;
			} else {
				flag = 1;// 点击回调地址不刷新请求 如果是就改变flag不刷新预付订单接口
				tv_name.setText(bean.getName());
				tv_mobile.setText(bean.getMobile());
				tv_address.setText(bean.getProvincename() + bean.getCityname()
						+ bean.getCountyname() + bean.getAddress());
				addressId = bean.getId();
				address = bean.getAddress();
				linear_name.setVisibility(View.VISIBLE);
				// 调取设置默认地址接口
				MobclickAgent.onEvent(baseActivity,"C_ADR_MGR_4");
				ApiHttpCilent.getInstance(getApplicationContext()).SetDefaultAddress(mContext,
						addressId, new MyAddressCallBack());
				/*
				 * 重新生成预览订单 重新计算运费
				 * **/
				Preprview();
			}
			
		}else if(ConstantsUtil.REQUEST_CODE_TWO == requestCode){
			//回调支付方式
			if(intent!=null){
			paytype = intent.getStringExtra("type");
			/*
			 * 如果不是喝喝币支付 重新生成预付订单
			 * 
			 * 如果是直接显示视图
			 * */
//			if(!"6".equals(paytype))
				Preprview();
//			else
//			    setPayType(paytype);
			}
		}else if(ConstantsUtil.REQUEST_CODE_FOUR == requestCode){
			//优惠券回调
			if(intent!=null){
				//优惠券信息
				coupon = (PayCouponbean) intent.getSerializableExtra("PayCouponbean");
				// 生成预付订单 提交aid
				if(coupon!=null){
					couponid_sub  =	coupon.getCouponid();
					isdefault = "";//修改过优惠券信息
				}
				/*
				 * 重新获取预览订单信息
				 * */
				ApiHttpCilent.getInstance(getApplicationContext()).CreatPaymentOrder(
						baseActivity,paytype,isdefault,score,addressId,selectProductNotInEdit, from,couponid_sub,new MyCallBack());
				}
		}else if( ConstantsUtil.REQUEST_CODE_FIVE ==requestCode){
			//发票界面返回
			if(intent != null){
				Bundle bundle = intent.getBundleExtra("invoice");
				invoice = (Invoice) bundle.getSerializable("Invoice");//返回的发票信息
//				setInvoce();
				MobclickAgent.onEvent(baseActivity,"C_ORD_INV.INV_2");
			   }
		}else if(ConstantsUtil.REQUEST_CODE_SIX ==requestCode){
			if(intent !=null){
				score = intent.getStringExtra("score");
				Preprview();
			}
		}else if(ConstantsUtil.REQUEST_CODE_SEVEN ==requestCode){
			//喝喝比支付返回回调 暂时屏蔽
//			if(intent !=null){
//				coin = intent.getStringExtra("coin");
//				Preprview();
//			}
		}
	}
	/*
	 * 
	 * 保存发票信息 
	 * 
	 * */
//	void setInvoce(){
//		if(invoice!=null && invoice.getInvoice()!=null){
//			id_type = invoice.getInvoice().getInvoicetype();
//			id_content = invoice.getInvoice().getInvoicecontent();
//			id_title = invoice.getInvoice().getInbuertype();
//			id_title_content = invoice.getInvoice().getInvoicetitle();
//			if( 0 == id_content)
//				fronttitle = "明细";
//			else
//				fronttitle = "酒水";
//			if(0 == id_title){
//				tv_fp.setText(fronttitle+"-个人");
//			}else{
//				String fp_st = fronttitle+"-"+id_title_content;
//				tv_fp.setText(fp_st);
//			}
//		}else{
//			//不开发票
//				tv_fp.setText("不开发票");
//		}
//	}
	class MyCallBack extends BaseJsonHttpResponseHandler<NewOrderBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewOrderBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewOrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected NewOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<NewOrderBaseBean>() {
			}.getType();
			NewOrderBaseBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
				message.obj = bean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
						.getCode())) {
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
	/*
	 * 设置默认地址 *
	 */
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
		MobclickAgent.onPageStart("PG_ORD_SUB");
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PG_ORD_SUB");
		MobclickAgent.onPause(this);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()){
		case KeyEvent.KEYCODE_BACK:
		{
			if(iscancle)
				return false;
		}
		}
		return super.dispatchTouchEvent(ev);
		}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(ApiHttpCilent.loading !=null && ApiHttpCilent.loading.isShowing())
			ApiHttpCilent.loading .dismiss();
		ApiHttpCilent.loading = null;
		super.onDestroy();
	}
}
