package com.heheys.ec.model.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.MyAllOrderActivity;
import com.heheys.ec.controller.activity.MyOrderDetailActivity;
import com.heheys.ec.controller.activity.ShoppingCartActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.SharedPreferencesUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.AllOrderBaseBean.OrderResult;
import com.heheys.ec.model.dataBean.AllOrderBaseBean.SubBean;
import com.heheys.ec.model.dataBean.BaseMessageBean;
import com.heheys.ec.model.dataBean.BuyMoreBean;
import com.heheys.ec.model.dataBean.CancleMsgBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BackRemark;
import com.heheys.ec.view.AlertDialogCustom.CancleOrder;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;

//import com.heheys.ec.thirdPartyModule.IM.LoginSampleHelper;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-28 下午6:21:12 类说明 订单列表适配器
 */
public class MyOrderAdapter extends BaseListAdapter<OrderResult> {

	private TextView tv_ordertv;
	private TextView tv_orderid;
	private TextView tv_order_status;
	private TextView tv_time;
	private TextView tv_totalNum;
	private TextView bt_cancle;
	private TextView tv_totalamount;
	private TextView bt_receive;
	private TextView textView1;
	private LinearLayout image_linear;
	private ImageView iv_order;
	private ImageView iv_pin;
	private OrderCallback callback;
	private HorizontalScrollView hs_scrollview;
	private CancleOrderHandler mHandler ;
	private OrderResult bean;//当前数据bean
	private CancleMsgBean cancleMsgBean;//返回取消数据bean
	private Context mContext;
	private int myindex;
	private AlertDialogCustom codedialog;
  	private String buyOid;
	public MyOrderAdapter(ArrayList<OrderResult> data, Context context,OrderCallback callback) {
		super(data, context);
		this.callback = callback;
		this.mcontext = context;
		mHandler = new CancleOrderHandler((MyAllOrderActivity) context);
		codedialog = new AlertDialogCustom();
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.my_order_item, parent,
					false);
		}
		bean = dataList.get(position);
		tv_ordertv = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_ordertv);
		tv_orderid = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_orderid);
		tv_order_status = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_order_status);
		tv_time = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_time);
		tv_totalNum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_totalNum);
		bt_cancle = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.bt_cancle);
		tv_totalamount = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_totalamount);
		bt_receive = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.bt_receive);
		textView1 = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.textView1);
		iv_pin = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.iv_pin);
		image_linear = (LinearLayout) ViewHolderUtil.getItemView(convertView,
				R.id.image_linear);
		hs_scrollview = (HorizontalScrollView) ViewHolderUtil.getItemView(convertView,
				R.id.hs_scrollview);
		if (bean != null) { 
			if("2".equals(bean.getOrdertype())){
				tv_orderid.setVisibility(View.INVISIBLE);
				tv_ordertv.setVisibility(View.INVISIBLE);
			}else{
				tv_orderid.setText(bean.getOid());
				tv_orderid.setVisibility(View.VISIBLE);
				tv_ordertv.setVisibility(View.VISIBLE);
			}
			tv_order_status.setText(bean.getStatusname());
			tv_time.setText("下单时间:"+bean.getCreatetime());
			tv_totalNum.setText("共:"+bean.getNum()+"件");
			ViewUtil.setActivityPrice(tv_totalamount, bean.getAmount());
			 final String statusbean = bean.getStatus();
//			if("0".equals(bean.getType()))
//				//合伙买
//				iv_pin.setVisibility(View.INVISIBLE);
//			else{
//				//批发
//				iv_pin.setVisibility(View.INVISIBLE);
//			}
			//显示订单不同状态
			ControlVisible(dataList.get(position).getStatus());
			if(bean.getSettlement_type() == 1 && "7".equals(statusbean)){
				//货到付款订单
					bt_receive.setVisibility(View.GONE);
			}
		final int index = position;
		ArrayList<SubBean> sublist = bean.getSuborder();
		if(sublist !=null && sublist.size()>0){
			image_linear.removeAllViews();
		for(SubBean sub:sublist){
				image_linear.addView(inflate(sub));
			}
		}

		image_linear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JumpActivity(dataList.get(index));
			}
		});
		textView1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JumpActivity(dataList.get(index));
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JumpActivity(dataList.get(index));
				String status = bean.getStatus();
				HashMap<String, String> map = new HashMap<String,String>();
				if("1".equals(status) || "2".equals(status)){
					map.put("orderStatus", status);
					MobclickAgent.onEvent(mcontext, "C_ORD_2P_2",map);//待付款
				}else if("6".equals(status)){
					map.put("orderStatus", status);
					MobclickAgent.onEvent(mcontext, "C_ORD_2D_2",map);//待发货
				}else if("7".equals(status)){
					map.put("orderStatus", status);
					MobclickAgent.onEvent(mcontext, "C_ORD_2C_2",map);//待收货
				}
			}
		});
		bt_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//删除订单
				if(ReturnIsCanDelete(dataList.get(index).getStatus())){
					ShowDeleteDialog("确认删除此订单?","提示",index);
				}else {
				//取消订单
					ApiHttpCilent.getInstance(mContext.getApplicationContext()).CancleOrderList(mcontext, dataList.get(index).getOid(), new CancleCallBack());
				}
				myindex = index;
				HashMap<String, String> map = new HashMap<String,String>();
				String status = dataList.get(myindex).getStatus();
				if("1".equals(status) || "2".equals(status)){
					map.put("orderStatus", Value(status));
					MobclickAgent.onEvent(mcontext, "C_ORD_2P_1",map);//修改
				}else if("6".equals(status)){
					map.put("orderStatus", Value(status));
					MobclickAgent.onEvent(mcontext, "C_ORD_2D_1",map);//待发货
				}else if("7".equals(status)){
					map.put("orderStatus", Value(status));
					MobclickAgent.onEvent(mcontext, "C_ORD_2C_1",map);//修改
				}
			}
		});

		//确定收货
		bt_receive.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myindex = index;
				if(statusbean.equals("1")|| statusbean.equals("2")|| statusbean.equals("3")|| statusbean.equals("9")){
					JumpActivity(dataList.get(index));
				}else if( ReturnIsCanBuy(dataList.get(index).getStatus())){
					//再次购买
					buyOid = dataList.get(index).getOid();
					ApiHttpCilent.getInstance(mContext.getApplicationContext()).BuyMoreOrderList(mcontext, buyOid,"", new BuyAgainCallBack());
				}else{
					//二次收货确认
					ShowDialog("是否确定收货?", "温馨提示", dataList.get(myindex).getOid());
				}
			}
		});}
		
	
		return convertView;
	}
//	正常: 1 待付款(定金)，9 进行中，--成单操作-- 2 待付款(尾款)，-3 待付款(全额)-，--付款结束-- 10 受理中
//	 * ，--受理操作-- 6 备货中，--发货操作-- 7待收货， --确认收货-- 8 已完成 异常: 21 已取消 22已退款 23 已失效
//
//	张双磊【技术部】 2017/1/7 13:43:55
//	正常: 1 待付款(定金)，9 合伙中， 2 待付款(尾款)，-3 待付款(全额)-- 6 备货中， 7待收货， 8 已收货 21 已取消 22已退款 23 已失效
//13:49:14
//	张双磊【技术部】 2017/1/7 13:49:14
//			24 交易成功 25交易失败
  //取消需要打电话
	private Boolean ReturnIsCanCancle(String status){
		if("9".equals(status)){
			return true;
		}else if("2".equals(status)){
			return true;
		}else if("6".equals(status)){
			return true;
		}else if("7".equals(status)){
			return true;
		}
		return false;
	}
	//是否可以删除
	private Boolean ReturnIsCanDelete(String status){
		if("21".equals(status)){
			return true;
		}else if("23".equals(status)){
			return true;
		}else if("24".equals(status)){
			return true;
		}else if("25".equals(status)){
			return true;
		}
		return false;
	}
	//是否可以再去购买
	private Boolean ReturnIsCanBuy(String status){
		if("21".equals(status)){
			return true;
		}else if("23".equals(status)){
			return true;
		}else if("24".equals(status)){
			return true;
		}else if("25".equals(status)){
			return true;
		}else if("8".equals(status)){
			return true;
		}
		return false;
	}
	//再次购买 type 传1 剔除没有库存的商品
	private void ShowAgainDialog(String notice, String title,final String type) {
		CommonDialog.makeText(mcontext, title, notice, new CommonDialog.OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
								 String tel) {
				// TODO Auto-generated method stub
				if (CommonDialog.OnDialogListener.LEFT == result ) {
					ApiHttpCilent.getInstance(mcontext.getApplicationContext()).BuyMoreOrderList(mcontext, buyOid, type, new BuyAgainCallBack());
				}
					CommonDialog.Dissmess();
			}
		}).showDialog();
	}
	private void ShowDeleteDialog(String notice, String title,final int index) {

		CommonDialog.makeText(mcontext, title, notice, new CommonDialog.OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
								 String tel) {
				// TODO Auto-generated method stub
				if (CommonDialog.OnDialogListener.LEFT == result ) {
					ApiHttpCilent.getInstance(mcontext.getApplicationContext()).DeleteOrderList(mcontext, dataList.get(index).getOid(), new DeleteOrderCallBack());
					CommonDialog.Dissmess();
				}  else {
					CommonDialog.Dissmess();
				}
			}
		}).showDialog();
	}
	//取消收货二次确定
	private void ShowDialog(String notice, String title, final String oid) {
		CommonDialog.makeText(mcontext, title, notice, new OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
					String tel) {
				// TODO Auto-generated method stub
				if (OnDialogListener.LEFT == result) {
					//确定
					if(!StringUtil.isEmpty(oid)){
						callback.SureOrder(oid);
					}
					CommonDialog.Dissmess();
				} else if (OnDialogListener.LEFT == result) {
					CommonDialog.Dissmess();
				} else {
					CommonDialog.Dissmess();
				}
			}
		}).showDialog();
	}
	private void Dimess() {
		((Activity) mcontext).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	public void Alert() {
		// TODO Auto-generated method stub
		if(cancleMsgBean!=null && cancleMsgBean.getResult()!=null)
			AlertDialog(dataList.get(myindex).getType(), dataList.get(myindex).getStatus(), dataList.get(myindex).getOid(),cancleMsgBean.getResult().getMsg());	
	}
	class CancleOrderHandler extends WeakHandler<MyAllOrderActivity> {
		@SuppressLint("HandlerLeak") 
		public CancleOrderHandler(MyAllOrderActivity reference) {
			super(reference);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				Alert();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				//删除成功回调
				callback.DeleteOrder();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				//再次购买
				BuyMoreBean.BaseResult resultTip = (BuyMoreBean.BaseResult) msg.obj;
				if(resultTip != null){
                        if(!StringUtil.isEmpty(resultTip.getTips())){
							//再次购买
							ShowAgainDialog(resultTip.getTips(),"温馨提示","1");
						}else{
                            StartActivityUtil.startActivity(getReference(), ShoppingCartActivity.class);
						}
				}
				break;
			case ConstantsUtil.HTTP_FAILE:
				String error = (String) msg.obj;
				if(!StringUtil.isEmpty(error)){
					ToastUtil.showToast(getReference(), error);
				}else{
					ToastUtil.showToast(getReference(), "数据异常,请稍后重试");
				}
				break;
			default:
				break;
			}
		}
	}
  /**
   * 删除订单回调
   * */
	class DeleteOrderCallBack extends BaseJsonHttpResponseHandler<BaseMessageBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
							  String arg3, BaseMessageBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
							  BaseMessageBean arg3) {
			Dimess();
		}

		@Override
		protected BaseMessageBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			BaseMessageBean deleteMsgBean = gson.fromJson(response, BaseMessageBean.class);
			Message message = Message.obtain();
			if ("1".equals(deleteMsgBean.getStatus())) {// 正常获取全部订单
				message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
				message.obj = deleteMsgBean.getResult();
			} else if ("0".equals(deleteMsgBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = deleteMsgBean.getError().getInfo();
			}
			mHandler.sendMessage(message);
			return deleteMsgBean;
		}
	}
	class BuyAgainCallBack extends BaseJsonHttpResponseHandler<BuyMoreBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
							  String arg3, BuyMoreBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
							  BuyMoreBean arg3) {
			Dimess();
		}

		@Override
		protected BuyMoreBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			BuyMoreBean buyMoreBean = gson.fromJson(response, BuyMoreBean.class);
			Message message = Message.obtain();
			if ("1".equals(buyMoreBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
				message.obj = buyMoreBean.getResult();
			} else if ("0".equals(buyMoreBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = buyMoreBean.getError().getInfo();
			}
			mHandler.sendMessage(message);
			return buyMoreBean;
		}
	}
	class CancleCallBack extends BaseJsonHttpResponseHandler<CancleMsgBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, CancleMsgBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				CancleMsgBean arg3) {
			Dimess();
		}

		@Override
		protected CancleMsgBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			cancleMsgBean = gson.fromJson(response, CancleMsgBean.class);
			Message message = Message.obtain();
			if ("1".equals(cancleMsgBean.getStatus())) {// 正常获取全部订单
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = cancleMsgBean.getResult();
			} else if ("0".equals(cancleMsgBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = cancleMsgBean.getError().getInfo();
			} 
			mHandler.sendMessage(message);
			return cancleMsgBean;
		}
	}
	
	private String Value(String key){
		if("1".equals(key)){
			return "待付订金取消";
		}else if("2".equals(key)){
			return "待付尾款取消";
		}else if("6".equals(key)){
			return "待发货取消";
		}else if("7".equals(key)){
			return "待收货取消";
		}
		return "其他状态取消";
	}


	 void callPhone() {
		// TODO Auto-generated method stub
		 String telService = MyApplication.getInstance().getServiceline();
		 if(StringUtil.isEmpty(telService)){
			 telService = SharedPreferencesUtil.getSharedPreferencesString(mcontext,"telShare","tel","01085782811");
		 }
		 if(StringUtil.isEmpty(telService)) {
			 Uri data = Uri.parse("tel:" + telService);
			 Intent intents = new Intent(Intent.ACTION_CALL, data);
			 mcontext.startActivity(intents);
		 }
	}
	/**
	 * type 是批发还是拼单
	 * 
	 * statue 是状态码
	 * 
	 * oid 订单号
	 * */
	private void AlertDialog(final String type,final String statue,final String oid,final String canclemsg){
		if(ReturnIsCanCancle(statue)){
			//待收货
//			new AlertDialogCustom().heheTel((Activity) mcontext,canclemsg);
			CommonDialog.makeTextCancle(mcontext, "提示", canclemsg,"拨打客服热线", new CommonDialog.OnDialogListener() {
				@Override
				public void onResult(int result, CommonDialog commonDialog,
									 String tel) {
					// TODO Auto-generated method stub
					if (CommonDialog.OnDialogListener.LEFT == result ) {
						if(Build.VERSION.SDK_INT >= 23){
							if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
							{
								ActivityCompat.requestPermissions((Activity) mcontext,new String[]{Manifest.permission.CALL_PHONE},
										1);
							} else{
								callPhone();
							}
						}else{
							callPhone();
						}
						//6.0权限处理
						CommonDialog.Dissmess();
					}  else {
						CommonDialog.Dissmess();
					}
				}
			}).showDialog();
		}else{
			if(!StringUtil.isEmpty(canclemsg)){
			new AlertDialogCustom().CancleCommit((Activity) mcontext,canclemsg, new CancleOrder() {
				@Override
				public void isCancle(boolean iscancle) {
					// TODO Auto-generated method stub
					if(iscancle){
					new AlertDialogCustom().cancleOrder_remark((Activity) mcontext, type, statue, new BackRemark() {
						@Override
						public void setRemark(String cid, String remark) {
							callback.cancleOrder(oid,remark);
						}
					});
					}
				   }
				});
			}else{
				new AlertDialogCustom().cancleOrder_remark((Activity) mcontext, type, statue, new BackRemark() {
					@Override
					public void setRemark(String cid, String remark) {
						callback.cancleOrder(oid,remark);
					}
				});
			} 
		}
	}
	private void JumpActivity(OrderResult bean) {
		Intent intent  = new Intent( mcontext, MyOrderDetailActivity.class);
		intent.putExtra("oid", bean.getOid());
		//传递0 是需要调取订单详情接口
		intent.putExtra("OrderFrom", "0");
//		intent.putExtra("Goodsname", bean.getGoodsname());
//		intent.putExtra("Goodsdesc", bean.getGoodsdesc());
//		intent.putExtra("status", bean.getStatus());
		StartActivityUtil.startActivity((Activity) mcontext, intent);
	}
//	活动审核状态
//	1正在审核 2审核通过 3审核失败
//	店铺状态
//	1未认证，2认证通过，3认证中，4认证失败
//	酒水需求状态
//	1 已发布 2 已处理 
//	拼单活动状态
//	1 未开始 2已开始 3已抢光 4 已结束
//	订单状态
//	订单状态  正常 1待付款(1定金,2尾款,3全款) 5已支付(尾款,全款)
//	 5 已支付 6 备货中 7待发货 8 已完成 9 进行中 10 受理中 异常 21 已取消 22已退款 23  已失效 24 已退货
	//订单列表:0 全部订单 1 待付款 2 待发货 3待收货 4 已收货 5合伙中
	//21 已取消 23已失效  24交易成功  25交易失败
	@SuppressLint("ResourceAsColor") public  boolean ControlVisible(String status) {
		bt_cancle.setText("取消订单");
		bt_cancle.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
		if("1".equals(status)){
			 isVisible(true, true);
			 bt_receive.setText("支付");
			 bt_receive.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			 return true;
		 }else if("2".equals(status)){
			 isVisible(true, true);
			 bt_receive.setText("支付");
			 bt_receive.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			 return true;
		 }else if("3".equals(status)){
			 isVisible(true, true);
			 bt_receive.setText("支付");
			 bt_receive.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			 return true;
		 }else if("6".equals(status)){
			 tv_order_status.setText("待发货");
			 isVisible(true, false);
			 return false;
		 }else if("7".equals(status)){
			 bt_receive.setText("确认收货");
			 bt_receive.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			 isVisible(true, true);
			 return true;
		 }else if("9".equals(status)){
			 isVisible(true, false);
			 return true;
		 }else if("10".equals(status)){
			 isVisible(true, false);
			 return false;
		 }else if("13".equals(status)){
			 isVisible(false, false);
			 return false;
		 }else if("-10".equals(status)){
			 isVisible(false, false);
			 return false;
		 }else if("-1".equals(status)){
			 isVisible(false, false);
			 return false;
		 }else if("8".equals(status)){
			isVisible(false, true);
			bt_receive.setText("再次购买");
			bt_receive.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			return false;
		}else if(ReturnIsCanBuy(status)){
			bt_receive.setText("再次购买");
			bt_receive.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			bt_cancle.setText("删除订单");
			bt_cancle.setTextColor(mcontext.getResources().getColor(R.color.color_FF3838));
			isVisible(true, true);
			return false;
		}
		return false;
	}

	/**
	 * 是否显示按钮
	 * */
	private void isVisible(boolean iscancle,boolean isreceive){
		if(iscancle){
				bt_cancle.setVisibility(View.VISIBLE);
		}else{
				bt_cancle.setVisibility(View.GONE);
		}
		if(isreceive){
				bt_receive.setVisibility(View.VISIBLE);
		}else{
				bt_receive.setVisibility(View.GONE);
		}
	}

	
	private View  inflate(final SubBean pic){
		View view = baseInflater.inflate(R.layout.order_image_item, null);
		iv_order = (ImageView) view.findViewById(R.id.iv_order);
		iv_order.setTag(pic.getUrl());
		if(iv_order.getTag()!=null && iv_order.getTag().equals(pic.getUrl())){
		MyApplication.imageLoader.displayImage(pic.getUrl(), iv_order,
				MyApplication.options);
		}
//		MyApplication.imageLoader.displayImage(pic.getUrl(), iv_order, new ImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {
//			}
//			@Override
//			public void onLoadingFailed(String imageUri, View view,
//					FailReason failReason) {
//			}
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				if(iv_order.getTag()!=null && iv_order.getTag().toString().equals(pic.getUrl())){
//					iv_order.setImageBitmap(loadedImage);
//				}
//			}
//			@Override
//			public void onLoadingCancelled(String imageUri, View view) {
//			}
//		});
		return view;
	}
	
	public interface OrderCallback{
		void cancleOrder(String oid, String mark);//取消订单
		void SureOrder(String oid);//确认收货
		void backOrderResult(OrderResult bean);
		void SignCode(String oid,String code);//验证验证码合法性回调
		void DeleteOrder();
	}
}
