package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.MyOrderAdapter;
import com.heheys.ec.model.adapter.MyOrderAdapter.OrderCallback;
import com.heheys.ec.model.dataBean.AlipayBaseBean;
import com.heheys.ec.model.dataBean.AllOrderBaseBean;
import com.heheys.ec.model.dataBean.AllOrderBaseBean.OrderResult;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BaseHehePayBean;
import com.heheys.ec.model.dataBean.BasebeanSign.ResultSignBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean.SidBean;
import com.heheys.ec.model.dataBean.WXPayBaseBean;
import com.heheys.ec.model.dataBean.WXPayBaseBean.weChat;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.Alipay;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.thirdPartyModule.payModule.PayResult;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BalanceCallBack;
import com.heheys.ec.view.AlertDialogCustom.HeheCoinCallBack;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间：2016-3-28 下午6:03:55 类说明 全部订单
 */
public class MyAllOrderActivity extends BaseActivity implements
		OnRefreshListener, OnMoreListener ,OnClickListener{

	private RefreshListView mListView;
	private Context mContext;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	// 订单状态 "status":"0" 0 全部订单 1 待付款 2 进行中 3备货中 4 待收货 5合伙中
	private String status;
	private OrderHandler mHandler = new OrderHandler(this);
	private LinearLayout linear_nodata;
	private AllOrderBaseBean orderbean;
	private ArrayList<OrderResult> data;
	private MyOrderAdapter mAdapter;
	private int cancleorsure;//是取消订单还是确认收货
	private OrderResult backbean;//点击支付方式返回点击数
	Alipay alipay;
	private final int BACK_CODE = 600;
	private AlipayBaseBean alibasebean;
//	private TextView textView2;
	private WXPayBaseBean wxpaybasebean;
	private IWXAPI api;
	private OrderResult bean;//当前点击的数据bean
	private AlertDialogCustom codedialog;
	private BaseHehePayBean baseHehePayBean;//喝喝币全额支付bean
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.my_order);
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
	
	}

	private void initData() {
		// TODO Auto-generated method stub
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
		codedialog = new AlertDialogCustom();
		Intent intent = getIntent();
		if(intent!=null){
			status = intent.getStringExtra("status");
//			 订单列表：0 全部订单 1 待付款 2  待发货  3待收货 4 已收货 
			if("1".equals(status)){
				ResetTitle("待付款");
			}else if("2".equals(status)){
				ResetTitle("合伙中");
			}else if("3".equals(status)){
				ResetTitle("待发货");
			}else if("4".equals(status)){
				ResetTitle("待收货");
			}else{
				ResetTitle("全部订单");
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
		mContext = MyAllOrderActivity.this;
		mListView = (RefreshListView) findViewById(R.id.myorder_lv);
		linear_nodata = (LinearLayout) findViewById(R.id.linear_nodata);
//		textView2 = (TextView) findViewById(textView2);
		initData();
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		ApiHttpCilent.getInstance(getApplicationContext()).getAllOrderList(mContext,
				status,startIndex,endIndex, new OrderCallBack());
	}
	private void Dimess() {
		MyAllOrderActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	class OrderCallBack extends BaseJsonHttpResponseHandler<AllOrderBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AllOrderBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AllOrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected AllOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			try {
				orderbean = gson.fromJson(response, AllOrderBaseBean.class);
			} catch (Exception e) {
//				System.out.println(e.toString());
			}
			Message message = Message.obtain();
			if ("1".equals(orderbean.getStatus())) {// 正常获取全部订单
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = orderbean.getResult();
			} else if ("0".equals(orderbean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = orderbean.getError().getInfo();
			} 
			mHandler.sendMessage(message);
			return orderbean;
		}
	}
	
	/**
	 * 取消订单 确认收货
	 * */
	class CancleSureCallBack extends BaseJsonHttpResponseHandler<BaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
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
			BaseBean basebean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(orderbean.getStatus()) && cancleorsure==1) {// 正常取消订单
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			}else if ("1".equals(orderbean.getStatus()) && cancleorsure==2) {// 正常确认收货
				message.what = BACK_CODE;
			}  else if ("0".equals(orderbean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = orderbean.getError().getInfo();
			}
			mHandler.sendMessage(message);
			return basebean;
		}
	}
	/**
	 * 微信支付
	 */
	private void WXPay() {
		// TODO Auto-generated method stub
        if(wxpaybasebean.getResult() != null){
        weChat wechat = wxpaybasebean.getResult().getWechat();
        if(wechat !=null){
		PayReq request = new PayReq();
		
		request.appId = wechat.getAppid();

		request.partnerId = wechat.getPartnerid();

		request.prepayId= wechat.getPrepayid();

		request.packageValue = wechat.getPackages();
		
		request.nonceStr= wechat.getNoncestr();

		request.timeStamp= wechat.getTimestamp();

		request.sign= wechat.getSign();
		
		request.extData		= "app data";
		
		if(api!=null){
			api.sendReq(request);
		}
			MyApplication.getInstance().setOrder(true);
			MyApplication.getInstance().setOid(backbean.getOid());
           }
        }
	}
	// 列表获取成功
	class OrderHandler extends WeakHandler<MyAllOrderActivity> {
		@SuppressLint("HandlerLeak")
		public OrderHandler(MyAllOrderActivity reference) {
			super(reference);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				ToastUtil.showToast(baseContext, "订单取消成功!");
				getNetData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_WXPAY:
				//微信支付获取鉴权信息成功
				WXPay();
				break;
			case BACK_CODE:
				getNetData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_HEHEPAY:
				ToastUtil.showToast(baseContext, "支付成功!");
				getNetData();//重新获取列表
				break;
			case ConstantsUtil.HTTP_FAILE:
				//code=100 登录失效
				String error = (String) msg.obj;
				if(!StringUtil.isEmpty(error))
				ToastUtil.showToast(baseActivity, error);
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				//支付鉴权成功
				ResultSignBean resultbean = (ResultSignBean) msg.obj;
				if (resultbean != null)
					alipay.Topay(resultbean.getAlipay());
				break;
			case  ConstantsUtil.ALI_BACK_CODE:
				//阿里支付回调
//				"status":"" 0、没有订单支付记录1、支付成功，可退款，2、支付失败，3、等待支付 4、支付超时关闭，5、交易结束，不可退款 
				String ststus = alibasebean.getResult().getStatus();
 				if("1".equals(ststus)){
					ToastUtil.showToast(mContext, "支付成功");
				}else if("2".equals(ststus)){
					ToastUtil.showToast(mContext, "支付失败");
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
				SignAlipay();
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				/*if (TextUtils.equals(resultStatus, "9000")) {
					
					Toast.makeText(getReference(), "支付成功", Toast.LENGTH_SHORT)
							.show();
					getNetData();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(getReference(), "支付结果确认中",
								Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(getReference(), "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}*/
				break;
			default:
				break;
			}
		}
	}

	//请求支付宝异步回调结果
	private void SignAlipay(){
		ApiHttpCilent.getInstance(getApplicationContext()).SignPayResult(mContext, backbean.getOid(),  new AlipayCallBack());
	}
	class AlipayCallBack extends BaseJsonHttpResponseHandler<AlipayBaseBean> {
	

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AlipayBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
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
			mHandler.sendMessage(message);
			return alibasebean;
		}
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (!NetWorkState.isNetWorkConnection(mContext)) {
			ToastUtil.showToast(mContext, "网络连接失败");
			return;
		}
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getNetData();

	}

	//绑定数据
	public void bindData() {
		// TODO Auto-generated method stub
		if(startIndex == 1){
			//首页第一页
			if(orderbean !=null && orderbean.getResult().getList()!=null && orderbean.getResult().getList().size()>0){
				data = orderbean.getResult().getList();
				mAdapter = new MyOrderAdapter(data, mContext,new OrderCallback() {
					
					@Override
					public void cancleOrder(final String oid,final String remark) {
						//弹出取消原因
//						new AlertDialogCustom().AlertCancleOrder(baseActivity,"请输入取消订单原因",new BackRemark() {
//							@Override
//							public void setRemark(String cid, String remark) {
								// TODO Auto-generated method stub
									//调用删除订单方法
								cancleorsure = 1;
								ApiHttpCilent.getInstance(getApplicationContext()).CancleOrder(mContext, oid,remark,new CancleSureCallBack());
//							}
//						});
					}
					@Override
					public void SureOrder(String oid) {
						//弹出确定收货
						cancleorsure = 2;
						ApiHttpCilent.getInstance(getApplicationContext()).GetGoodsOk(mContext, oid, new CancleSureCallBack());
					}
					@Override
					public void backOrderResult(OrderResult bean) {
						backbean = bean;//返回数据
					}
					@Override
					public void SignCode(String oid, String code) {
						//验证验证码是否合法 合法就支付
						ApiHttpCilent.getInstance(getApplicationContext()).PayHeheMoney(baseContext, oid,code, new AllHehePayCallBack());
					}

					@Override
					public void DeleteOrder() {
						//回调删除订单成功
                      ToastUtil.showToast(baseContext,"删除成功");
						initReuquestParams();
						getNetData();
					}
				});
				mListView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				hideRefreshView();
				linear_nodata.setVisibility(View.GONE);
				mListView.setVisibility(View.VISIBLE);
//				textView2.setVisibility(View.VISIBLE);
			}else{
				//空数据
				linear_nodata.setVisibility(View.VISIBLE);
//				textView2.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);
			}
		}else{
			//分页
			if(orderbean !=null && orderbean.getResult().getList()!=null && orderbean.getResult().getList().size()>0){
				ArrayList<OrderResult> listdata = orderbean.getResult().getList();
				data.addAll(listdata);
				hideLoadMoreView();
			}else{
				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.
						DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
				ToastUtil.showToasts(getApplication(),"没有更多数据了", Toast.LENGTH_SHORT);
			}
		}
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
			mHandler.sendMessage(message);
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
			mHandler.sendMessage(message);
			return baseHehePayBean;
		}
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (!NetWorkState.isNetWorkConnection(mContext)) {
			ToastUtil.showToast(mContext, "网络连接失败");
			return;
		}
		isRefresh = true;
		initReuquestParams();
		getNetData();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getNetData();
	}

	// 初始化开始和结束页面
	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	//
	private void hideRefreshView() {
		if (isRefresh) {
			mListView.onRefreshComplete();
			isRefresh = false;
		}
	}

	// 更多界面
	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
			mAdapter.setNewData(data);
			mAdapter.notifyDataSetChanged();
			isLoadMore = false;
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		mListView.setonLoadListener(this);
		mListView.setonRefreshListener(this);
		
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "全部订单";
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
			mHandler.sendMessage(message);
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
			mHandler.sendMessage(message);
			return wxpaybasebean;
		}
	}
	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestcode, resultcode, intent);
		switch (requestcode) {
		case ConstantsUtil.REQUEST_CODE_TWO:
			//返回支付方式
			String paytype = "";
			if(intent!=null){
			   paytype = intent.getStringExtra("type");
			   bean = (OrderResult) intent.getSerializableExtra("bean");
			   if(bean.getStatus().equals("1")|| bean.getStatus().equals("2"))
				   MobclickAgent.onEvent(mContext, "C_ORD_2P_4");//支付方式 付款
			}
			if(bean != null ){
				 if(ConstantsUtil.HEHE_PAY.equals(paytype)){
					new AlertDialogCustom().CreatePayMsg(1,false,(BaseActivity) mContext,bean.getOid(),new BalanceCallBack() {
						@Override
						public void backBalance(String balance) {
							getNetData();
						}
						@Override
						public void close() {
						}
					});
				}else if(ConstantsUtil.HB_PAY.equals(paytype)){
					CreateCode(paytype,bean.getOid(),bean.getStatus());
				}else{
//						if(bean.getCoinamount()==0){
							Topay(paytype,bean.getOid(),"",bean.getStatus());
//						}else{
//							CreateCode(paytype,bean.getOid(),bean.getStatus());
//						}
					}
			}
			break;

		default:
			break;
		}
	}
	//发送验证码
		private void CreateCode(final String paytype,final String oid,final String status) {
					// TODO Auto-generated method stub
					codedialog.CreateCoinPayMsg(1,true, (BaseActivity) mContext, bean.getOid(),new HeheCoinCallBack() {
						@Override
						public void code(String code) {
							// TODO Auto-generated method stub
								Topay(paytype,bean.getOid(),code,status);
						}
					} );
				}
	private void Topay(String paytype,String oid,String code,String status) {
		if( ConstantsUtil.ZFB_PAY.equals(paytype)) {
			SidBean sid = new SidBean();
			sid.setGoodsdesc(backbean.getGoodsdesc());
			sid.setGoodsname(backbean.getGoodsname());
			sid.setOid(backbean.getOid());
			alipay = new Alipay(mContext,mHandler);
			alipay.pay(oid,code,status);
		} else if (ConstantsUtil.WX_PAY.equals(paytype)) {
			//微信支付
			if(api.isWXAppInstalled() && api.isWXAppSupportAPI()){
				ApiHttpCilent.getInstance(getApplicationContext()).WXPaySign(mContext,oid,code,"wechat", new WXPayCallBack());
			}else{
				ToastUtil.showToast(baseActivity, "请您安装微信客户端在完成支付");
			}
		}else if(ConstantsUtil.JD_PAY.equals(paytype)){
			Intent intents = new Intent(mContext,
					JDPayActivity.class);
			intents.putExtra("flag", 1);// 传递1是购物车跳转过去
			intents.putExtra("orderId", oid);
			intents.putExtra("code", code);
			StartActivityUtil.startActivity((Activity) mContext,
					intents);
		}else if( ConstantsUtil.HB_PAY.equals(paytype)){
			//喝喝币支付
			ApiHttpCilent.getInstance(getApplicationContext()).PayHeheMoney(mContext, oid,code, new AllHehePayCallBack());
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		PG_ORD_2P	待付订单页
//		PG_ORD_2D	待发货订单页
//		PG_ORD_2C	待收货订单页
//		PG_ORD_CFM	已收货订单页
//		PG_ORD_ALL	全部订单页
//		 订单列表：0 全部订单 1 待付款 2  待发货  3待收货 4 已收货 
		if("0".equals(status)){
			MobclickAgent.onPageStart("PG_ORD_ALL");
		}else if("1".equals(status)){
			MobclickAgent.onPageStart("PG_ORD_2P");
		}else if("2".equals(status)){
			MobclickAgent.onPageStart("PG_ORD_2D");
		}else if("3".equals(status)){
			MobclickAgent.onPageStart("PG_ORD_2C");
		}else if("4".equals(status)){
			MobclickAgent.onPageStart("PG_ORD_CFM");
		}
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if("0".equals(status)){
			MobclickAgent.onPageEnd("PG_ORD_ALL");
		}else if("1".equals(status)){
			MobclickAgent.onPageEnd("PG_ORD_2P");
		}else if("2".equals(status)){
			MobclickAgent.onPageEnd("PG_ORD_2D");
		}else if("3".equals(status)){
			MobclickAgent.onPageEnd("PG_ORD_2C");
		}else if("4".equals(status)){
			MobclickAgent.onPageEnd("PG_ORD_CFM");
		}
		MobclickAgent.onPause(this);
	}
	void callPhone() {
		// TODO Auto-generated method stub
		Uri data = Uri.parse("tel:"+ MyApplication.getInstance().getServiceline());
		Intent intents = new Intent(Intent.ACTION_CALL,data);
		startActivity(intents);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,String[] permissions,  int[] grantResults) {
		if (requestCode == 1)
		{
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0)
			{
				callPhone();
			} else
			{
				// Permission Denied
				ToastUtil.showToast(baseActivity, "请去设置里面开启拨打电话权限");
			}
		}
	}
}
