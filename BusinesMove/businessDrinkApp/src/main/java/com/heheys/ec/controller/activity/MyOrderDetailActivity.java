package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.MyOrderDetaileAdapter;
import com.heheys.ec.model.dataBean.AlipayBaseBean;
import com.heheys.ec.model.dataBean.BaseHehePayBean;
import com.heheys.ec.model.dataBean.BasebeanSign.ResultSignBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean.SidBean;
import com.heheys.ec.model.dataBean.ExpressBaseBean;
import com.heheys.ec.model.dataBean.ExpressBaseBean.Express;
import com.heheys.ec.model.dataBean.MyOrderBaseBean;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.MyOrderResult;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.PaytypelistDetaile;
import com.heheys.ec.model.dataBean.MyOrderBaseBean.orderItem;
import com.heheys.ec.model.dataBean.PayCouponbean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.WXPayBaseBean;
import com.heheys.ec.model.dataBean.WXPayBaseBean.weChat;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.RequestConfiguration;
import com.heheys.ec.thirdPartyModule.payModule.Alipay;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.thirdPartyModule.payModule.PayResult;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BalanceCallBack;
import com.heheys.ec.view.AlertDialogCustom.HeheCoinCallBack;
import com.heheys.ec.view.AlertDialogCustom.MyDialog;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-29 上午10:59:02 类说明 我的订单详细页面 订单列表进入
 */
public class MyOrderDetailActivity extends BaseActivity {

	private Context mContext;
	private ListView lv_order;
	private FrameLayout bottomParent;
	private TextView tv_totalNum;
	private TextView tv_final;
	private Button to_pay;
	private ImageView iv_pay, iv_arrow_buttom;
	private TextView tv_yunfei;
	private TextView tv_transuredec;
	private LinearLayout linear_pay;
	private TextView tv_pay;
	private LinearLayout linear_name;
	private TextView tv_address;
	private TextView tv_name;
	private TextView tv_mobile;
	private TextView tv_hehebalance;
	private ImageView iv_arrow;
	private String seachoid;// 传递过来的订单号 查询
	private String oid;// 根据路径 扫描 和正常查看订单 订单号来源不一样
	private OrderDetaileHandler mhandler;
	private MyOrderBaseBean orderbean;
	private MyOrderDetaileAdapter mAdapter;
	private int addressId;
	private String paytype = "";
	private Alipay alipay;
	private String status;
	private MyOrderResult mydata;
	private WXPayBaseBean wxpaybasebean;
	private IWXAPI api;
	private AlipayBaseBean alibasebean;
	private LinearLayout linear_express;
	private ImageView iv_express;
	private ExpressBaseBean expressbean;
	private boolean expressclick = true;
	// 第一个元素的高度 所有元素的高度
	private int height, totalheight;
	private RelativeLayout rv_express;
	private LinearLayout linear_yhj;
	private PayCouponbean bean;
	private TextView tv_yhj;
	private String invoiceinfo;// 积分
	private LinearLayout linear_jf;
	private ImageView iv_yhj;
	private AlertDialogCustom codedialog;
	private BaseHehePayBean baseHehePayBean;
	private List<ShoppingCartSelectBean> selectProductNotInEdit;
	//1 扫描 0 正常
	private String orderFrom;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.order_detail);
		initView();
		initData();
	}

	public boolean ControlVisiblePay(String status) {
		if ("1".equals(status)) {
			return true;
		} else if ("9".equals(status)) {
			return false;
		} else if ("2".equals(status)) {
			return true;
		} else if ("3".equals(status)) {
			return true;
		} else if ("-10".equals(status)) {
			return false;
		} else if ("6".equals(status)) {
			return false;
		} else if ("21".equals(status)) {
			return false;
		} else if ("7".equals(status)) {
			return false;
		} else if ("8".equals(status)) {
			return false;
		} else if ("-1".equals(status)) {
			return false;
		} else if ("23".equals(status)) {
			return false;
		}
		return false;
	}

	// 获取订单号
	private void initData() {
		try {
			if (SecurityInit.Initialize(this) == 0) {
				SecurityCipher securityCipher = new SecurityCipher(this);
				String wx_later_wx = securityCipher.decryptString(
						ConstantsUtil.APP_ID, ConstantsUtil.JAQ_KEY);
				api = WXAPIFactory.createWXAPI(this, wx_later_wx);
			} else {
				api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
			}
		} catch (JAQException e) {

			// TODO Auto-generated catch block
			api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
			e.printStackTrace();
		}
		mhandler = new OrderDetaileHandler(this);
		Intent intent = getIntent();
		if (intent != null) {
			seachoid = intent.getStringExtra("oid");
			orderFrom = intent.getStringExtra("OrderFrom");
		}
		if (!StringUtil.isEmpty(seachoid)) {
			//传递  1 是需要调取扫码货到付款接口  0 订单详情
			if ("0".equals(orderFrom)){
				ApiHttpCilent.getInstance(getApplicationContext()).getOrderDetail(mContext, seachoid,
						new MyOrderCallBack());
		    }else{
				ApiHttpCilent.getInstance(getApplicationContext()).getScanOrderDetail(mContext, seachoid,
						new MyOrderCallBack());
			}
		}
	}

	private void setPayType(String type) {
		/*
		 * 生成下面的视图
		 * 
		 * *
		 */
		tv_hehebalance.setVisibility(View.INVISIBLE);
		to_pay.setText("支付");
		if (ConstantsUtil.HEHE_PAY.equals(type)) {
			tv_pay.setText("余额支付");
			iv_pay.setImageResource(R.drawable.hehe);
			tv_hehebalance.setVisibility(View.VISIBLE);
			ViewUtil.setActivityPrice(tv_hehebalance, mydata.getBalance());
		} else if (ConstantsUtil.ZFB_PAY.equals(type)) {
			tv_pay.setText("支付宝");
			iv_pay.setImageResource(R.drawable.zhifubao);
		} else if (ConstantsUtil.WX_PAY.equals(type)) {
			tv_pay.setText("微信");
			iv_pay.setImageResource(R.drawable.weixin);
		} else if (ConstantsUtil.JD_PAY.equals(type)) {
			tv_pay.setText("京东");
			iv_pay.setImageResource(R.drawable.jingdong);
		} else if (ConstantsUtil.HB_PAY.equals(type)) {
			tv_pay.setText("喝币支付");
			iv_pay.setImageResource(R.drawable.hehe_pay);
			tv_hehebalance.setVisibility(View.VISIBLE);
			ViewUtil.setActivityPrice(tv_hehebalance, mydata.getCoinbalance());
		} else if (ConstantsUtil.LINE_OFF.equals(type)) {
			List<PaytypelistDetaile> listTitle = mydata.getPaytypelist();
			if (listTitle != null && listTitle.size() > 0) {
				for (PaytypelistDetaile title : listTitle) {
					if (title.getId() == -1) {
						tv_pay.setText(StringUtil.isEmpty(title.getName()) ? ""
								: title.getName());
						break;
					}
				}
			}
			iv_pay.setImageResource(R.drawable.arrive);
			to_pay.setText("提交");
			iv_pay.setVisibility(View.VISIBLE);
		} else if ("".equals(type)) {
			// 处理使用喝喝币等情况
			tv_pay.setText(StringUtil.isEmpty(mydata.getPaytypeinfo()) ? ""
					: mydata.getPaytypeinfo());
			iv_pay.setVisibility(View.INVISIBLE);
			// 支付方式不可点击
			linear_pay.setEnabled(false);
		}
	}

	private void initView() {
		bottomParent = (FrameLayout) findViewById(R.id.order_detail_bottom_parent);
		mContext = MyOrderDetailActivity.this;
		lv_order = (ListView) findViewById(R.id.order_lv);
		tv_totalNum = (TextView) findViewById(R.id.tv_totalNum);// 总件数
		tv_final = (TextView) findViewById(R.id.tv_final);// 总件数
		to_pay = (Button) findViewById(R.id.to_pay);
		View headerView = getLayoutInflater().inflate(R.layout.listview_header,
				null);
		View footerView = getLayoutInflater().inflate(
				R.layout.new_listview_footer, null);
		// headr视图不可点击
		lv_order.addHeaderView(headerView, null, true);

		// footer视图不可点击
		lv_order.addFooterView(footerView, null, true);
		lv_order.setDivider(null);
		lv_order.setDividerHeight(0);
		iv_arrow_buttom = (ImageView) footerView
				.findViewById(R.id.iv_arrow_buttom);
		iv_pay = (ImageView) footerView.findViewById(R.id.iv_pay);
		tv_pay = (TextView) footerView.findViewById(R.id.tv_pay);
		linear_pay = (LinearLayout) footerView.findViewById(R.id.linear_pay);
		tv_transuredec = (TextView) footerView
				.findViewById(R.id.tv_transuredec);
		tv_yunfei = (TextView) footerView.findViewById(R.id.tv_yunfei);
		tv_hehebalance = (TextView) footerView
				.findViewById(R.id.tv_hehebalance);
		linear_yhj = (LinearLayout) footerView.findViewById(R.id.linear_yhj);
		tv_yhj = (TextView) footerView.findViewById(R.id.tv_yhj);
//		tv_fp = (TextView) footerView.findViewById(R.id.tv_fp);
//		tv_jf = (TextView) footerView.findViewById(R.id.tv_jf);
//		tv_hb = (TextView) footerView.findViewById(R.id.tv_hb);
//		linear_fp = (LinearLayout) footerView.findViewById(R.id.linear_fp);
		linear_jf = (LinearLayout) footerView.findViewById(R.id.linear_jf);
		iv_yhj = (ImageView) footerView.findViewById(R.id.iv_yhj);
//		iv_fp = (ImageView) footerView.findViewById(R.id.iv_fp);
//		iv_jf = (ImageView) footerView.findViewById(R.id.iv_jf);
//		iv_hb = (ImageView) footerView.findViewById(R.id.iv_hb);
		iv_yhj.setVisibility(View.INVISIBLE);
//		iv_fp.setVisibility(View.INVISIBLE);
//		iv_jf.setVisibility(View.INVISIBLE);
//		iv_hb.setVisibility(View.INVISIBLE);
		// 快递信息视图
		linear_express = (LinearLayout) headerView
				.findViewById(R.id.linear_express);
		rv_express = (RelativeLayout) headerView.findViewById(R.id.rv_express);
		iv_express = (ImageView) headerView.findViewById(R.id.iv_express);
		iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
		tv_name = (TextView) headerView.findViewById(R.id.tv_name_connact);
		tv_mobile = (TextView) headerView.findViewById(R.id.tv_mobile);
		tv_address = (TextView) headerView.findViewById(R.id.tv_address);
		LinearLayout linear_address = (LinearLayout) headerView
				.findViewById(R.id.linear_address);
		linear_name = (LinearLayout) headerView.findViewById(R.id.linear_name);
		linear_address.setOnClickListener(this);
		linear_pay.setOnClickListener(this);
		to_pay.setOnClickListener(this);
		iv_express.setOnClickListener(this);
		rv_express.setOnClickListener(this);
		linear_address.setClickable(false);
//		linear_fp.setOnClickListener(this);
//		tv_fp.setOnClickListener(this);
//		linear_fp.setClickable(true);
		linear_yhj.setClickable(false);
		to_pay.setText("支付");
		codedialog = new AlertDialogCustom();
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

	private void Dimess() {
		((Activity) mContext).runOnUiThread(new Runnable() {
			public void run() {
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	// 快递信息回调
	class MyExpressCallBack extends
			BaseJsonHttpResponseHandler<ExpressBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ExpressBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ExpressBaseBean arg3) {
			Dimess();
		}

		@Override
		protected ExpressBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			expressbean = gson.fromJson(response, ExpressBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(expressbean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = expressbean.getError().getInfo();
			}
			mhandler.sendMessage(message);
			return expressbean;
		}
	}

	class MyOrderCallBack extends BaseJsonHttpResponseHandler<MyOrderBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MyOrderBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MyOrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected MyOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			try {
				orderbean = gson.fromJson(response, MyOrderBaseBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message message = Message.obtain();
			if ("1".equals(orderbean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = orderbean.getError().getInfo();
			}
			mhandler.sendMessage(message);
			return orderbean;
		}
	}

	public class OrderDetaileHandler extends WeakHandler<MyOrderDetailActivity> {

		@SuppressLint("HandlerLeak")
		public OrderDetaileHandler(MyOrderDetailActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindDate();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
					SidBean result = (SidBean) msg.obj;
					if (result != null) {
						String oidSubmit = result.getOid();
						Intent intent = new Intent(baseActivity, PaySuccessActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("orderId", oidSubmit);
						bundle.putBoolean("isOrder", true);
						intent.putExtra("bundle", bundle);
						StartActivityUtil.startActivity(baseActivity, intent);
				   }
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				getReference().bindExpressDate();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				// 支付鉴权成功
				ResultSignBean resultbean = (ResultSignBean) msg.obj;
				if (resultbean != null)
					alipay.Topay(resultbean.getAlipay());
				break;
			case ConstantsUtil.HTTP_SUCCESS_WXPAY:
				// 微信支付获取鉴权信息成功
				WXPay();
				break;
			case ConstantsUtil.HTTP_SUCCESS_HEHEPAY:
				// 全额喝喝币支付
				PaySuccessCallBack();
				break;
			case ConstantsUtil.HTTP_FAILE:
				String erroe = (String) msg.obj;
				if (!StringUtil.isEmpty(erroe))
					ToastUtil.showToast(mContext, erroe);
				break;
			case ConstantsUtil.ALI_BACK_CODE:
				// 阿里支付回调
				// "status":"" 0、没有订单支付记录1、支付成功，可退款，2、支付失败，3、等待支付
				// 4、支付超时关闭，5、交易结束，不可退款
				if(alibasebean.getResult() != null) {
					String status = alibasebean.getResult().getStatus();
					PaySuccessCallBack();
					//支付失败关闭支付页
					if ("2".equals(status))
						finish();
				}
				break;
			case NewOrderDetailActivity.SDK_PAY_FLAG:
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 支付宝结果为最终结果指标
				SignAlipay();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				// if (TextUtils.equals(resultStatus, "9000")) {
				// Toast.makeText(getReference(), "支付成功", Toast.LENGTH_SHORT)
				// .show();
				// PaySuccessCallBack("0");
				// } else {
				// // 判断resultStatus 为非"9000"则代表可能支付失败
				// //
				// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
				// if (TextUtils.equals(resultStatus, "8000")) {
				// Toast.makeText(getReference(), "支付结果确认中",
				// Toast.LENGTH_SHORT).show();
				// PaySuccessCallBack("1");
				// } else {
				// // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
				// Toast.makeText(getReference(), "支付失败",
				// Toast.LENGTH_SHORT).show();
				// PaySuccessCallBack("2");//订单列表进入不需要跳转界面
				// }
				// }
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 支付完成跳转
	 * */
//	private void PaySuccessJump() {
//		if(!StringUtil.isEmpty(oid)) {
//			Intent intent = new Intent(baseActivity, PaySuccessActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putBoolean("isOrder", true);// 订单 区分订单支付结果和喝喝币支付结果
//			bundle.putString("orderId", oid);// 订单号 查询结果
//			intent.putExtra("bundle", bundle);
//			StartActivityUtil.startActivity(baseActivity, intent);
//		}
//	}

	// 回填快递信息
	public void bindExpressDate() {
		if (expressbean != null && expressbean.getResult() != null) {
			int size = expressbean.getResult().getExpresslist().size();
			if (expressbean.getResult().getExpresslist() != null && size > 0) {
				ArrayList<Express> express_list = expressbean.getResult()
						.getExpresslist();
				for (int i = 0; i < size; i++) {
					LayoutInflater inflater = (LayoutInflater) mContext
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View layout_express = inflater.inflate(
							R.layout.express_item, null);
					TextView textView1 = (TextView) layout_express
							.findViewById(R.id.textView1);
					TextView textView2 = (TextView) layout_express
							.findViewById(R.id.textView2);
					TextView express_name = (TextView) layout_express
							.findViewById(R.id.express_name);
					TextView express_time = (TextView) layout_express
							.findViewById(R.id.express_time);
					TextView express_num = (TextView) layout_express
							.findViewById(R.id.express_num);
					express_name.setText(express_list.get(i).getExpressname());
					express_time.setText(express_list.get(i).getDeliverytime());
					express_num.setText(express_list.get(i).getExpressnum());
					linear_express.addView(layout_express);
					if (size > 1 && i < size - 1) {
						textView2.setVisibility(View.VISIBLE);
					}

					if (linear_express.getChildCount() == 1) {
						height = ViewUtil.getHeight(layout_express);
						;
						textView1.setVisibility(View.VISIBLE);
					} else {
						textView1.setVisibility(View.GONE);
					}
					totalheight += ViewUtil.getHeight(layout_express);
				}
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.height = height;
				linear_express.setLayoutParams(lp);
			}
			if (size < 2)
				iv_express.setVisibility(View.INVISIBLE);
			else
				iv_express.setVisibility(View.VISIBLE);
		}

	}

	// 请求支付宝异步回调结果
	private void SignAlipay() {
		ApiHttpCilent.getInstance(getApplicationContext()).SignPayResult(mContext, oid,
				new AlipayCallBack());
	}

	// 支付宝支付成功回调
	class AlipayCallBack extends BaseJsonHttpResponseHandler<AlipayBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AlipayBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
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
			if ("1".equals(alibasebean.getStatus())) {// 获取支付结果
				message.what = ConstantsUtil.ALI_BACK_CODE;
			} else if ("0".equals(alibasebean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = alibasebean.getError().getInfo();
			}
			mhandler.sendMessage(message);
			return alibasebean;
		}
	}

	/**
	 * 微信支付
	 */
	private void WXPay() {
		// TODO Auto-generated method stub
		if (wxpaybasebean.getResult() != null) {
			weChat wechat = wxpaybasebean.getResult().getWechat();
			if (wechat != null) {
				PayReq request = new PayReq();
				request.appId = wechat.getAppid();

				request.partnerId = wechat.getPartnerid();

				request.prepayId = wechat.getPrepayid();

				request.packageValue = wechat.getPackages();

				request.nonceStr = wechat.getNoncestr();

				request.timeStamp = wechat.getTimestamp();

				request.sign = wechat.getSign();

				request.extData = "app data";
				if (api != null)
					api.sendReq(request);
				/**
				 * 设置当前订单号 好查询
				 * */
				MyApplication.getInstance().setOid(oid);
				MyApplication.getInstance().setOrder(true);
			}
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	// 获取订单详情数据
	public void bindDate() {
		if (orderbean != null && orderbean.getResult() != null) {
			mydata = orderbean.getResult();
			oid = mydata.getOrdernum();
			selectProductNotInEdit = new ArrayList<ShoppingCartSelectBean>();
            status = mydata.getStatus();
            if (ControlVisiblePay(status)) {
                to_pay.setVisibility(View.VISIBLE);
            } else {
                to_pay.setVisibility(View.INVISIBLE);
            }
			ArrayList<orderItem> itemorder = mydata.getList();
			for (orderItem item : itemorder) {
				ShoppingCartSelectBean shoppingbean = new ShoppingCartSelectBean();
				shoppingbean.setAid(item.getAid());
				shoppingbean.setNum(item.getNum());
				shoppingbean.setCurrentprice(item.getCurrentprice());
				selectProductNotInEdit.add(shoppingbean);
			}
			initNewData(mydata);
			if (!StringUtil.isEmpty(mydata.getPaytype()))
				paytype = mydata.getPaytype();
			setPayType(paytype);
			ArrayList<orderItem> mdata = orderbean.getResult().getList();
			View childView = new View(baseContext);
			childView
					.setBackgroundColor(getResources().getColor(R.color.white));
			int height = ViewUtil.getHeight(bottomParent);
			LinearLayout llLayout = new LinearLayout(baseContext);
			View childViews = new View(baseContext);
			LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, height);
			llLayout.addView(childViews, llParams);
			lv_order.addFooterView(llLayout);
			mAdapter = new MyOrderDetaileAdapter(mdata,orderbean.getResult().getStatus(), mContext);
			lv_order.setAdapter(mAdapter);
		}
	}

	// 回填头部和底部数据
	private void initNewData(MyOrderResult bean) {
		/**
		 * 回填地址
		 */
		linear_name.setVisibility(View.VISIBLE);
		tv_name.setText("  " + bean.getAddress().getName());
		tv_mobile.setText("  " + bean.getAddress().getMobile());
		tv_address.setText(bean.getAddress().getAddress());
		// tv_address.setText(bean.getAddress().getProvincename()
		// + bean.getAddress().getCityname()
		// + bean.getAddress().getCountyname()
		// + bean.getAddress().getAddress());
		addressId = bean.getAddress().getId();// 地址id
		/**
		 * 回填底部
		 */
		if (bean.getTotalamount() != null)
			ViewUtil.setActivityPrice(tv_final, bean.getTotalamount());
		tv_totalNum.setText("共计:" + bean.getTotalnum() + "件");
		// 1 待付款(定金),2 待付款(尾款) 3 待付款(全额)
		String payStatus = bean.getPaystatus();

		if(orderFrom.equals("1")){
			linear_pay.setEnabled(true);
			iv_arrow_buttom.setVisibility(View.VISIBLE);
		}else{
			if ("1".equals(payStatus) || "2".equals(payStatus)
					|| "3".equals(payStatus)) {
				linear_pay.setEnabled(true);
				iv_arrow_buttom.setVisibility(View.VISIBLE);
			} else {
				linear_pay.setEnabled(false);
				iv_arrow_buttom.setVisibility(View.INVISIBLE);
			}
		}
		iv_arrow.setVisibility(View.INVISIBLE);
		tv_transuredec.setText(bean.getTransdesc());
		tv_yunfei.setText(bean.getTransamount());
		tv_yhj.setText(" " + bean.getCouponinfo());
//		tv_fp.setText(StringUtil.isEmpty(bean.getInvoiceinfo()) ? "未开发票" : bean
//				.getInvoiceinfo());
//		tv_jf.setText(StringUtil.isEmpty(bean.getScoreinfo()) ? "未使用积分" : bean
//				.getScoreinfo());
//		tv_hb.setText(StringUtil.isEmpty(bean.getCoininfo()) ? "未使用喝币" : bean
//				.getCoininfo());
//		invoiceinfo = bean.getInvoiceinfo();
//
//		if ("不开发票".equals(bean.getInvoiceinfo())) {
//			iv_fp.setVisibility(View.GONE);
//			linear_fp.setEnabled(false);
//			tv_fp.setEnabled(false);
//		} else {
//			iv_fp.setVisibility(View.VISIBLE);
//			linear_fp.setEnabled(true);
//			tv_fp.setEnabled(true);
//		}
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rv_express:
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			if (expressclick) {
				expressclick = false;
				lp.height = totalheight;
				iv_express.setImageResource(R.drawable.xiangshangjiantou);
			} else {
				expressclick = true;
				lp.height = height;
				iv_express.setImageResource(R.drawable.xiangxiajiantou);
			}
			linear_express.setLayoutParams(lp);
			break;
		case R.id.linear_pay:
			intent.putExtra("payType", paytype + "");
			// 传递订单详情bean 判断是否使用了优惠券或者开了发票
			intent.putExtra("myOrderResult", mydata);
			intent.setClass(mContext, PayTypeActivity.class);
			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE_TWO);
			overridePendingTransition(R.anim.dialog_buttom_enter, 0);
			break;
		case R.id.to_pay:
			//去支付按钮
			MobclickAgent.onEvent(mContext, "C_ORD_DTL_1");// 支付方式
			if (ConstantsUtil.HEHE_PAY.equals(paytype)) {
				MyDialog dialogs = codedialog.CreatePayMsg(1, false,
						(BaseActivity) mContext, oid, new BalanceCallBack() {
							public void backBalance(String balance) {
								// s TODO Auto-generated method stub
								ViewUtil.setActivityPrice(tv_hehebalance,
										balance);
								//喝喝支付跳转支付结果页
								PaySuccessCallBack();
							}

							@Override
							public void close() {
								// finish();
							}
						});
				dialogs.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				// 禁止返回键
				dialogs.setOnKeyListener(new DialogInterface.OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							finish();
						}
						return false;
					}
				});
			} else if (ConstantsUtil.HB_PAY.equals(paytype)) {
				// 如果使用了喝喝币 发送短消息
				CreateCode();
			} else if (ConstantsUtil.LINE_OFF.equals(paytype)) {
				// 线下支付
				ApiHttpCilent.getInstance(getApplicationContext()).SubmitLineOff(mContext,
						oid, new MySubmitOrderCallBack());
			} else {
				// 如果没有使用喝喝币
				ToPay(oid, "");
			}
			break;
		case R.id.linear_yhj:
			// 点击获取是否可以使用优惠券
			intent.setClass(this, CouponActivity.class);
			intent.putExtra("key", "use");
			StartActivityUtil.startActivity(baseActivity, intent);
			break;
//		case R.id.linear_fp:
//			ToMyOrderDetaile(intent);
//			break;
//		case R.id.tv_fp:
//			ToMyOrderDetaile(intent);
//			break;
		default:
			break;
		}
	}

	class MySubmitOrderCallBack extends
			BaseJsonHttpResponseHandler<CreatebaseOrderBean> {
		private CreatebaseOrderBean createorderbean;

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, CreatebaseOrderBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
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
				message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
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
			mhandler.sendMessage(message);
			return createorderbean;
		}
	}

	// 发送验证码
	private void CreateCode() {
		// TODO Auto-generated method stub
		codedialog.CreateCoinPayMsg(1, true, (BaseActivity) mContext, oid,
				new HeheCoinCallBack() {
					@Override
					public void code(String code) {
						// TODO Auto-generated method stub
						ToPay(oid, code);
					}
				});
	}

	// 支付方式
	private void ToPay(String oid, String code) {
		Intent intent = new Intent();
		// 去支付
		if (ConstantsUtil.ZFB_PAY.equals(paytype)) {
			//支付宝支付
			alipay = new Alipay( mContext, mhandler);
			alipay.pay(oid, code, orderbean.getResult().getStatus());
		} else if (ConstantsUtil.JD_PAY.equals(paytype)) {
			//京东支付
			intent.setClass(mContext, JDPayActivity.class);
			intent.putExtra("flag", 2);// 传递1是购物车跳转过去
			intent.putExtra("orderId", oid);
			intent.putExtra("code", code);
			StartActivityUtil.startActivity((Activity) mContext, intent);
		} else if (ConstantsUtil.WX_PAY.equals(paytype)) {
			// 微信支付
			if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
				ApiHttpCilent.getInstance(getApplicationContext()).WXPaySign(mContext,
						oid, code, "wechat", new WXPayCallBack());
			} else {
				ToastUtil.showToast(baseActivity, "请您安装微信客户端在完成支付");
			}
		} else if (ConstantsUtil.LINE_OFF.equals(paytype)) {
			// 线下支付

		} else if (ConstantsUtil.HB_PAY.equals(paytype)) {
			// 当前订单总金额是0.00 时执行
			ApiHttpCilent.getInstance(getApplicationContext()).PayHeheMoney(mContext, oid,
					code, new AllHehePayCallBack());
		}
	}

	// 全额喝喝币支付
	class AllHehePayCallBack extends
			BaseJsonHttpResponseHandler<BaseHehePayBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseHehePayBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseHehePayBean arg3) {
			Dimess();
		}

		@Override
		protected BaseHehePayBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			baseHehePayBean = gson.fromJson(response, BaseHehePayBean.class);
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
			mhandler.sendMessage(message);
			return baseHehePayBean;
		}
	}

	// 跳转到我的订单详情
	private void ToMyOrderDetaile(Intent intent) {
		if (!StringUtil.isEmpty(invoiceinfo)) {
			intent.putExtra("oid", oid);
			intent.putExtra("createOrder", false);// 查看订单详情
			intent.setClass(baseActivity, InvoiceActivity.class);
			StartActivityUtil.startActivity(baseActivity, intent);
		}
	}

	/*
	 * 
	 * 微信支付回调
	 */
	class WXPayCallBack extends BaseJsonHttpResponseHandler<WXPayBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, WXPayBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				WXPayBaseBean arg3) {
			Dimess();
		}

		@Override
		protected WXPayBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			wxpaybasebean = gson.fromJson(response, WXPayBaseBean.class);
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
			mhandler.sendMessage(message);
			return wxpaybasebean;
		}
	}

	/**
	 * 支付成功跳转
	 * */
	private void PaySuccessCallBack() {
			if("1".equals(orderFrom)){
				//扫描跳转到H5
				Intent intent = new Intent();
				intent.setClass(baseActivity, JDPayActivity.class);
				intent.putExtra("url", RequestConfiguration.BASE_URL_TEST_LACALHOST+"order/payresult.jhtml?oid="+oid);
				intent.putExtra("title", "确认收货");
				intent.putExtra("flag", 10);//区分到H5界面返回键
				StartActivityUtil.startActivity(baseActivity, intent);
			}else {
				Intent intent = new Intent(baseActivity, PaySuccessActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("orderId", oid);
				bundle.putBoolean("isOrder", true);// 订单 区分订单支付结果和喝喝币支付结果
				intent.putExtra("bundle", bundle);
				StartActivityUtil.startActivity(baseActivity, intent);
			}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (ConstantsUtil.REQUEST_CODE_TWO == requestCode) {
			// 回调支付方式
			if (intent != null) {
				paytype = intent.getStringExtra("type");
				/*
				 * 重新获取预览订单信息
				 */
				// ApiHttpCilent.getInstance(baseActivity).CreatPaymentOrder(
				// baseActivity,paytype,"1",orderbean.getResult().get,addressId,selectProductNotInEdit,
				// 0,couponid_sub,new MyCallBack());
			}
			setPayType(paytype);
		} else if (ConstantsUtil.REQUEST_CODE_FOUR == requestCode) {
			// 使用优惠券回调
			if (intent != null) {
				bean = (PayCouponbean) intent
						.getSerializableExtra("PayCouponbean");
				tv_yhj.setText(bean.getCouponContent());
			}
		}
	}
}
