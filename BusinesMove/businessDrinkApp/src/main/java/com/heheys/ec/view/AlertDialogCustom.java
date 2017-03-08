package com.heheys.ec.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.controller.activity.AuthActivity;
import com.heheys.ec.controller.activity.MyAllOrderActivity;
import com.heheys.ec.controller.activity.PaySuccessActivity;
import com.heheys.ec.controller.activity.RegisterActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.DrawLineTextView;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.MyOrderAdapter.OrderCallback;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.HehePayBaseBean;
import com.heheys.ec.model.dataBean.LoginNamePwd;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangkui
 * 
 * @param电话弹出界面
 * */
public class AlertDialogCustom {

	private Context mcontext;
	private Activity mActivity;
	public  MyDialog builders;
	private View layout;
	private BackRemark callback;
	private IsRegsiterOrMain registercallback;
	private UpdateOrNot updateOrNot;
	private BalanceCallBack balancecallback;
	private String cid;
	private String remark;
	private LinearLayout call_root;
	private TextView tv_title;
	private LinearLayout linear_top;
	private LinearLayout linear_buttom;
	private TextView tv_notice_tel;
	private TextView tv_notice_code;
	private DeleteEditText code_et;
	private Button outh_code,commit_verify;
	private  MyHandler handler;
	private String oid ;//当前喝喝余额支付订单id
	private HehePayBaseBean hehepay;
	private boolean isjump;
	private OrderCallback orderCallback;
	private String type;//订单类型
	private String remark_reason;
	private CancleOrder cancleback;
	private   HeheCoinCallBack coincallback;
	
	/*
	 * 当前dialog 适用于 终端用户拨打电话 
	 * 名片管理拨打电话 发送信息和修改备注 所有
	 * linkName 拨打电话界面 联系人姓名 
	 * 
	 * shopName 拨打电话界面 联系人店面类型
	 * 
	 * list 拨打电话联系人集合
	 * 
	 * id 0 终端管理 1 拨打电话 2发生短信 3修改备注
	 * 
	 * callback 修改备注回调接口
	 * 
	 * cid 当前名片id
	 * **/
	
//	public AlertDialogCustom(){
//		
//	}
	
	//发票须知界面
	public MyDialog CreateFpDialog(Activity mcontext,String notice){
		this.mcontext = mcontext;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.invoice_notice,null);
		tv_notice = (TextView) layout.findViewById(R.id.tv_notice);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		tv_notice.setText(StringUtil.isEmpty(notice)?"":notice);
		TextView tv_sure = (TextView) builders.findViewById(R.id.tv_sure);
		tv_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Demiss();
			}
		});
		Show();
		return builders;
	}
	public MyDialog CreateDialog(Activity mcontext,String linkName,String shopName,List<String> list,int id){
		this.mcontext = mcontext;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 layout = inflater.inflate(R.layout.alert_tel,null);
		 call_root = (LinearLayout) layout.findViewById(R.id.call_root);
         builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		 initView(list,linkName,shopName,id);
		 Show();
		return builders;
	}
	//弹出名片管理5个电话号码
	public MyDialog CreateDialog(Activity mcontext,String linkName,String shopName,List<String> list,int id,final BackRemark callback,final String cid,final String remark){
		this.mcontext = mcontext;
		this.callback = callback;
		this.cid = cid;
		this.remark = remark;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.dialog_input,null);
		call_root = (LinearLayout) layout.findViewById(R.id.call_root);
		tv_title = (TextView) layout.findViewById(R.id.tv_title);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		initView(list,linkName,shopName,id);
		Show();
		return builders;
	}
	
	//弹出喝喝客服IM和电话 还有登录资料未审核弹窗
	public MyDialog AlertHeHe(final String type,final Activity mcontext,String text,String tv_notice,boolean islogin,final boolean isregister,final OrderCallback orderCallback){
		this.type = type;
		this.mcontext = mcontext;
		this.orderCallback = orderCallback;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.heheim,null);
		call_root = (LinearLayout) layout.findViewById(R.id.call_root);
		tv_resason = (TextView) layout.findViewById(R.id.tv_order_resason);
		tv_order = (TextView) layout.findViewById(R.id.tv_order);
		if(!StringUtil.isEmpty(text)){
			tv_resason.setText(text);
			tv_resason.setVisibility(View.VISIBLE);
		}
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		TextView hehe_im  = (TextView) layout.findViewById(R.id.hehe_im);
		TextView hehe_tel  = (TextView) layout.findViewById(R.id.hehe_tel);
		TextView textView1  = (TextView) layout.findViewById(R.id.textView1);
		TextView tv_sure  = (TextView) layout.findViewById(R.id.tv_sure);
		TextView textView2  = (TextView) layout.findViewById(R.id.textView2);
		LinearLayout linear_close  = (LinearLayout) layout.findViewById(R.id.linear_close);
		LinearLayout linear_tel  = (LinearLayout) layout.findViewById(R.id.linear_tel);
		hehe_im.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Demiss();
			}
		});
		linear_close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Demiss();
			}
		});
		hehe_tel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					Demiss();
					call(ConstantsUtil.SERVICE_TEL);
			}
		});
		if(!StringUtil.isEmpty(tv_notice)){
			//是取消订单操作
			if(type!=null){
				if("0".equals(type)){
					//拼单
					textView2.setText(tv_notice);
				}else{
					textView2.setText("订单取消");
				}
			}else{
				textView2.setText(tv_notice);
			}
			tv_sure.setText("继续");
			tv_sure.setVisibility(View.VISIBLE);
			tv_order.setVisibility(View.GONE);
			textView2.setVisibility(View.GONE);
			hehe_tel.setVisibility(View.GONE);
			tv_sure.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(orderCallback != null){
						orderCallback.cancleOrder("","");
					}
					Demiss();
				}
			});
		}
		if(islogin){
			tv_resason.setText(text);
			if(tv_notice.contains("正在审核中")){
				textView2.setVisibility(View.VISIBLE);
				linear_tel.setVisibility(View.GONE);
				tv_sure.setVisibility(View.GONE);
				textView2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_CALL);
						Uri data = Uri.parse("tel:" + ConstantsUtil.SERVICE_TEL);
						intent.setData(data);
						mcontext.startActivity(intent);
						Demiss();
					}
				});
			}else{
//				tv_order.setVisibility(View.GONE);
				hehe_im.setVisibility(View.GONE);
				hehe_tel.setVisibility(View.GONE);
				textView1.setVisibility(View.GONE);
				tv_sure.setVisibility(View.VISIBLE);
				tv_sure.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					if(isregister){
						StartActivityUtil.startActivity(mcontext, RegisterActivity.class);
					}else{
						StartActivityUtil.startActivity(mcontext, AuthActivity.class);
					}
					Demiss();
				}
			});}
		}
		Show();
		return builders;
	}
	
	/*
	 * 登录权限
	 * notice 提示信息
	 * */
	@SuppressLint("NewApi")
//	public MyDialog LoginPremission(ResultBean loginObj,final String passWord,final Activity mActivity,String notice,int flag,final IsRegsiterOrMain registercallback){
//
//		this.mcontext  =  mActivity;
//		this.registercallback  =  registercallback;
//		LayoutInflater inflater = (LayoutInflater)
//				mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		layout = inflater.inflate(R.layout.login_permission,null);
//
//		LinearLayout linear_tel = (LinearLayout) layout.findViewById(R.id.linear_tel);
//		LinearLayout linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
//		LinearLayout title_linear = (LinearLayout) layout.findViewById(R.id.title_linear);
//		TextView tv_ts_title = (TextView)layout.findViewById(R.id.tv_ts_title);
//		TextView tv_ts_news = (TextView)layout. findViewById(R.id.tv_ts_news);
//		TextView tv_ts = (TextView) layout.findViewById(R.id.tv_ts);
//		TextView tv_tips = (TextView) layout.findViewById(R.id.tv_tips);
//		LinearLayout linear_auth_success = (LinearLayout) layout.findViewById(R.id.linear_auth_success);
//		TextView tv_tq1 = (TextView) layout.findViewById(R.id.tv_tq1);
//		TextView tv_tq2 = (TextView) layout.findViewById(R.id.tv_tq2);
//		TextView tv_tq3 = (TextView) layout.findViewById(R.id.tv_tq3);
//		TextView hehe_register = (TextView) layout.findViewById(R.id.hehe_register);
//		TextView hehe_main = (TextView) layout.findViewById(R.id.hehe_main);
//		hehe_main.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stubT
//				registercallback.setResult(2);
//				Demiss();
//			}
//		});
//		hehe_register.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putExtra("pwd_two", passWord);
//				intent.setClass(mActivity, AuthActivity.class);
//				StartActivityUtil.startActivity(mActivity,intent);
//				Demiss();
//				mActivity.finish();
//			}
//		});
//		linear_close.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Demiss();
//			}
//		});
//		if(4 == flag){
//			hehe_register.setText("立即修改");
//			hehe_main.setText("稍后再说");
//			hehe_main.setBackground(mActivity.getResources().getDrawable(R.drawable.gary_button));
//			hehe_main.setTextColor(mActivity.getResources().getColor(R.color.color_ffffff));
//		}
//		builders = new MyDialog(mActivity,0,0,layout,R.style.dialog);
//		 title_linear.setVisibility(View.GONE);
//		  linear_auth_success.setVisibility(View.VISIBLE);
//		Show();
//		return builders;
//	}
	
	//弹出取消原因
	public MyDialog AlertCancleOrder(Activity mcontext,String name,final BackRemark callback){
		this.mcontext = mcontext;
		this.callback = callback;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.dialog_input,null);
		call_root = (LinearLayout) layout.findViewById(R.id.call_root);
		et_remark = (EditText) layout.findViewById(R.id.et_remark);
		tv_title = (TextView) layout.findViewById(R.id.tv_title);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		et_remark.setHint(name);
		Button bt_cancle  = (Button) layout.findViewById(R.id.bt_cancle);
		Button bt_ok  = (Button) layout.findViewById(R.id.bt_ok);
		bt_cancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Demiss();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(callback!=null){
						callback.setRemark("", et_remark.getText().toString().trim());
					Demiss();
				}
			}
		});
		Show();
		return builders;
	}
	//弹出修改
	public MyDialog AlertUpdateNum(final Activity mcontext,final String minNum,final int kcum,String text,final BackRemark callback){
		this.mcontext = mcontext;
		this.callback = callback;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.alert_input,null);
		call_root = (LinearLayout) layout.findViewById(R.id.call_root);
		et_remark = (EditText) layout.findViewById(R.id.et_remark);
		Button reduce = (Button) layout.findViewById(R.id.reduce);
		Button add = (Button) layout.findViewById(R.id.add);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		Button bt_cancle  = (Button) layout.findViewById(R.id.bt_cancle);
		Button bt_ok  = (Button) layout.findViewById(R.id.bt_ok);
		et_remark.setText(text);
		reduce.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String currtText = et_remark.getText().toString().trim();
				if(!StringUtil.isEmpty(currtText)){
					long nownum = Long.parseLong(currtText);
					if(nownum > Long.parseLong(minNum)){
						nownum--;
						et_remark.setText(nownum+"");
					}else{
						ToastUtil.showToast(mcontext, "最小起批量是"+minNum);
					}
				}
			}
		});
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!StringUtil.isEmpty(et_remark.getText().toString().trim())){
					long nownum = Long.parseLong(et_remark.getText().toString().trim());
					if(nownum < kcum){
						nownum++;
						et_remark.setText(nownum+"");
					}else{
						ToastUtil.showToast(mcontext, "最大库存是"+kcum);
					}
				}
			}
		});
		bt_cancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Demiss();
				InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);  
				imm.hideSoftInputFromWindow(et_remark.getWindowToken(), 0); //强制隐藏键盘  
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(callback!=null){
					String input = et_remark.getText().toString().trim();
					if(!StringUtil.isEmpty(input)){
						if(Long.parseLong(input) >= Long.parseLong(minNum) && Long.parseLong(input) <= kcum){
							callback.setRemark("", et_remark.getText().toString().trim());
							Demiss();
							InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);  
							imm.hideSoftInputFromWindow(et_remark.getWindowToken(), 0); //强制隐藏键盘  
						}else if(Long.parseLong(input) < Long.parseLong(minNum)){
							ToastUtil.showToast(mcontext, "最小购买量是"+minNum);
						}else if(Long.parseLong(input) > kcum){
							ToastUtil.showToast(mcontext, "最大库存是"+kcum);
						}
					}else{
						ToastUtil.showToast(mcontext, "请输入商品数量");
					}
				}
			}
		});
		Show();
		return builders;
	}
	
	   
	
	//有喝喝币支付时创建发送验证码页面
	public MyDialog CreateCoinPayMsg(int index,boolean isjump,BaseActivity mcontext,String oid,final HeheCoinCallBack coincallback){
		this.isjump = isjump;
		this.mcontext = mcontext;
		this.oid = oid;
		this.index = index;
		this.coincallback = coincallback;
		handler = new MyHandler(mcontext);
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.alert_tel,null);
		layout.setFocusable(true);
		linear_top = (LinearLayout) layout.findViewById(R.id.linear_top);
		linear_buttom = (LinearLayout) layout.findViewById(R.id.linear_buttom);
		linear_top.setVisibility(View.GONE);
		linear_buttom.setVisibility(View.VISIBLE);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		builders.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
				{
					return true;
				}
				return false;
			}
		});
		builders.setCanceledOnTouchOutside(false);//
		initView(oid,coincallback);
		Show();
		return builders;
	}
	int index;
	//创建发送验证码页面
	public MyDialog CreatePayMsg(int index,boolean isjump,BaseActivity mcontext,String oid,BalanceCallBack balancecallback){
		//index ==1,3,4 是订单 2 是充值喝喝币 
		this.index = index;
		this.isjump = isjump;
		this.mcontext = mcontext;
		this.oid = oid;
		this.balancecallback = balancecallback;
		handler = new MyHandler(mcontext);
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 layout = inflater.inflate(R.layout.alert_tel,null);
		 layout.setFocusable(true);
		 linear_top = (LinearLayout) layout.findViewById(R.id.linear_top);
		 linear_buttom = (LinearLayout) layout.findViewById(R.id.linear_buttom);
		 linear_top.setVisibility(View.GONE);
		 linear_buttom.setVisibility(View.VISIBLE);
         builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
         builders.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				 if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
		            {
		             return true;
		            }
				return false;
			}
		});
         builders.setCanceledOnTouchOutside(false);//
         initView(oid,null);
		 Show();
		return builders;
	}
	
	private void initView(final String oid,final HeheCoinCallBack coincallback){
		tv_notice_tel = (TextView) layout.findViewById(R.id.tv_notice_tel);
		tv_notice_code = (TextView) layout.findViewById(R.id.tv_notice_code);
		code_et = (DeleteEditText) layout.findViewById(R.id.code_et);
		outh_code = (Button) layout.findViewById(R.id.outh_code);
		iv_close = (ImageView) layout.findViewById(R.id.iv_close);
		commit_verify = (Button) layout.findViewById(R.id.commit_verify);
		@SuppressWarnings("unchecked")
		ArrayList<LoginNamePwd> listlogin = (ArrayList<LoginNamePwd>) SharedPreferencesUtil.getObject(mcontext, "loginUserPwd");
		if(listlogin!=null && listlogin.size()>0){
			final String tel = listlogin.get(listlogin.size()-1).getUserName().toString();
			if(!StringUtil.isEmpty(tel))
			tv_notice_tel.setText(tel.replace(tel.substring(3, 8), "*****"));
			outh_code.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(ToastNoNetWork.ToastError(mcontext)){
						return;
					}
					if(balancecallback !=null)
						AuthTel(tel,ConstantsUtil.CODE_FLAG_7);
					else
						AuthTel(tel,ConstantsUtil.CODE_FLAG_10);
				}
			});
		}
		iv_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Demiss();
				if(isjump){
					if(index !=1){
						Intent intent = new Intent(mcontext, MyAllOrderActivity.class);
						intent.putExtra("status", "1");
						StartActivityUtil.startActivity((Activity) mcontext, intent);
					}
					if(balancecallback != null){
						balancecallback.close();
					}
				}else{
					//跳转到我的支付结果
					if(index != 1){
					Intent intent = new Intent(mcontext, PaySuccessActivity.class);
					Bundle bundle = new Bundle();
					if(index == 3 || index == 4){
						bundle.putBoolean("isOrder", true);//订单 区分订单支付结果和喝喝币支付结果
						bundle.putString("orderid", oid);
					}else{
						bundle.putBoolean("isOrder", false);//订单 区分订单支付结果和喝喝币支付结果
						bundle.putString("oid", oid);
					}
				    intent.putExtra("bundle", bundle);
					StartActivityUtil.startActivity((Activity) mcontext,intent);
					}
				}
			}
		});
		commit_verify.setOnClickListener(new OnClickListener() {
			@Override
			
			public void onClick(View v) {
//				ToastUtil.showToast(mcontext, "提交余额支付");
				String code = code_et.getText().toString().trim();
				if(!StringUtil.isEmpty(code)){
					//余额支付
					if(coincallback ==null){
					ApiHttpCilent.getInstance(mcontext.getApplicationContext()).hehpay(mcontext,
							oid,code,new HeheCallBack());
					}else{
						coincallback.code(code);
						Demiss();
					}
				}else{
					ToastUtil.showToast(mcontext, "验证码没有填写");
				}
			}
		});
	}
	//发送验证码
	private void AuthTel(String tellength,String codetype) {
//		if(ValidatorUtil.isMobile(tellength)){
			ApiHttpCilent.getInstance(mcontext.getApplicationContext()).ObtinCode(mcontext, tellength, new MyCallBack(),codetype);//获取验证码
//		}
	}
	
	//验证码倒计时
		private void startCountDown() {
			mCountDown = ConstantsUtil.BACK_SECOND;
			countDownTimer = new Timer();
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					mCountDown--;
					if (mCountDown < 0) {
						countDownHandler
								.sendEmptyMessage(ConstantsUtil.COUNT_DOWN_END);
						countDownTimer.cancel();
						countDownTimer = null;
					} else {
						countDownHandler.sendEmptyMessage(ConstantsUtil.COUNT_DOWNING);
					}
				}
			};
			countDownTimer.schedule(timerTask, 0, ConstantsUtil.COUNT_DOWN_TIME);
		}

		/**
		 * 倒计时消息处理
		 */
		private Handler countDownHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ConstantsUtil.COUNT_DOWNING:
					outh_code.setText(mCountDown + "秒");
					outh_code.setClickable(false);
					outh_code.setBackgroundResource(R.drawable.shape_button_gray);
					commit_verify.setBackgroundResource(R.drawable.bt_bg_yellow);
					break;
				case ConstantsUtil.COUNT_DOWN_END:
					outh_code.setText("发验证码");
					outh_code.setClickable(true);
					outh_code.setBackgroundResource(R.drawable.sharp_round);
					break;
				default:
					break;
				}
			}
		};
		private void Dimess(){
			((Activity) mcontext).runOnUiThread( new Runnable() {
				public void run() {
					if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
						ApiHttpCilent.loading.dismiss();
				}
			});
		}
		/**
		 * 获取手机号码回调
		 * */
		class MyCallBack extends  BaseJsonHttpResponseHandler<BaseBean>{
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
				BaseBean loginBean = gson.fromJson(response,BaseBean.class);
				Message message = Message.obtain();
				if("1".equals(loginBean.getStatus()) ){//正常获取验证码
					message.what = ConstantsUtil.HTTP_SUCCESS;
				}else if("0".equals(loginBean.getStatus()) ){
					message.what = ConstantsUtil.HTTP_FAILE;//验证码错误
					message.obj = loginBean.getError().getInfo();
				}
				handler.sendMessage(message);
				return loginBean;
			}
		}
		
		/**
		 * 喝喝余额支付回调
		 * 
		 * */
		class HeheCallBack extends  BaseJsonHttpResponseHandler<HehePayBaseBean>{
			

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					String arg3, HehePayBaseBean arg4) {
				Dimess();
			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2,
					HehePayBaseBean arg3) {
				Dimess();
			}
			
			@Override
			protected HehePayBaseBean parseResponse(String response, boolean arg1)
					throws Throwable {
				// TODO Auto-generated method stub
				Dimess();
				Gson gson = new Gson();
				hehepay = gson.fromJson(response,HehePayBaseBean.class);
				Message message = Message.obtain();
				if("1".equals(hehepay.getStatus()) ){//正常支付成功
					message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				}else if("0".equals(hehepay.getStatus()) ){
					message.what = ConstantsUtil.HTTP_FAILE;//支付错误
					message.obj = hehepay.getError().getInfo();
				}
				handler.sendMessage(message);
				return hehepay;
			}
		}
		 public  class MyHandler extends WeakHandler<BaseActivity>{
			public MyHandler(BaseActivity mcontext) {
				super(mcontext);
				// TODO Auto-generated constructor stub
			}

			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case ConstantsUtil.HTTP_SUCCESS:
						startCountDown();
						ToastUtil.showToast(getReference(),getReference().getResources().getString(R.string.getcode_success));
						commit_verify.setEnabled(true);
					break;
				case ConstantsUtil.HTTP_SUCCESS_LOGIN:
					if(hehepay!=null){
						if(balancecallback != null && hehepay.getResult().getBanace() != null)
							balancecallback.backBalance(hehepay.getResult().getBanace());
					}
					Demiss();
					break;
				case ConstantsUtil.HTTP_FAILE:
					String back = (String) msg.obj;
					ToastUtil.showToast(getReference(),back);
					break;
				
			
				default:
					break;
				}
			}
			}
		private int mCountDown;
		private Timer countDownTimer;
		private int isCode;
		private ImageView iv_close;
		private EditText et_remark;
		private TextView tv_resason;
		private TextView tv_order;
		private TextView hehe_main;
		private TextView hehe_register;
		private TextView hehe_updatelater;
		private TextView tv_ts;
		private TextView hehe_updatenow;
		private LinearLayout linear_close;
		private TextView tv_notice;
		public ProgressBar pb;
	
	public class MyDialog extends Dialog {
	     
	    private static final int default_width = 160; //默认宽度
	    private static final int default_height = 120;//默认高度
	    public MyDialog(Context context, int width, int height, View layout, int style) {
	        super(context, style);
	        setContentView(layout);
	        Window window = getWindow();
//	        window.setWindowAnimations(R.anim.pop_buttom);
	        WindowManager.LayoutParams params = window.getAttributes();
	        params.gravity = Gravity.CENTER;
	        float widthdp = window.getWindowManager().getDefaultDisplay().getWidth();
	        params.width  = (int) (widthdp-ViewUtil.dip2px(context, 80));
	        window.setWindowAnimations(R.style.mystyle);  //添加动画  
	        window.setAttributes(params);
	    }
	}

	public MyDialog getInstance(Context mContext){
		builders  = null;
		if(builders == null){
			builders = new MyDialog(mContext,0,0,layout,R.style.dialog);
		}
		return builders;
	}
	
	
	//动态生成界面view
	private void initView(final List<String> list,String linkName,String shopName,final int id) {
		if(3==id){
			final EditText et_remark  = (EditText) layout.findViewById(R.id.et_remark);
			if(list==null){//删除省市
				tv_title.setVisibility(View.VISIBLE);
				et_remark.setVisibility(View.GONE);
				tv_title.setText(shopName);
			}else{
				tv_title.setVisibility(View.GONE);
				et_remark.setVisibility(View.VISIBLE);
				et_remark.setText(remark);
			}
			Button bt_cancle  = (Button) layout.findViewById(R.id.bt_cancle);
			Button bt_ok  = (Button) layout.findViewById(R.id.bt_ok);
			bt_cancle.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Demiss();
				}
			});
			bt_ok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(callback!=null){
						if(list==null){//删除城市省份
							callback.setRemark(null, null);
						}else{
							callback.setRemark(cid, et_remark.getText().toString().trim());
						}
						Demiss();
					}
				}
			});
			
		}else if(1==id){
			TextView tv_LinkName = (TextView) layout.findViewById(R.id.tv_LinkName);
			TextView tv_shopName = (TextView) layout.findViewById(R.id.tv_shopName);
			TextView textView2 = (TextView) layout.findViewById(R.id.textView2);
			RelativeLayout title_linear = (RelativeLayout) layout.findViewById(R.id.title_linear);
			LinearLayout linear_tel = (LinearLayout) layout.findViewById(R.id.linear_tel);
			title_linear.setVisibility(View.VISIBLE);
			tv_LinkName.setVisibility(View.INVISIBLE);
			tv_shopName.setVisibility(View.INVISIBLE);
			textView2.setVisibility(View.VISIBLE);
			textView2.setText(linkName);
			for(int i=0;i<list.size();i++){
				View view = LinearLayout.inflate(mcontext, R.layout.button_item, null);
				final Button bu_call = (Button) view.findViewById(R.id.bt_call);
				TextView tv_tel = (TextView) view.findViewById(R.id.tv_tel);
				TextView tv_telText = (TextView) view.findViewById(R.id.tv_telText);
				if(i==0){
					tv_telText.setText("电话：");
				}
				final String tel = list.get(i);
				tv_tel.setText(tel);
				if(1==id){
					bu_call.setText("拨打");
				}
				bu_call.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
							call(tel);
					}
				});
				linear_tel.addView(view);
		}
		}else{
		TextView tv_LinkName = (TextView) layout.findViewById(R.id.tv_LinkName);
		TextView tv_shopName = (TextView) layout.findViewById(R.id.tv_shopName);
		RelativeLayout title_linear = (RelativeLayout) layout.findViewById(R.id.title_linear);
		DrawLineTextView line = (DrawLineTextView) layout.findViewById(R.id.line);
		LinearLayout linear_tel = (LinearLayout) layout.findViewById(R.id.linear_tel);
			title_linear.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
			line.setVisibility(View.VISIBLE);
		for(int i=0;i<list.size();i++){
				View view = LinearLayout.inflate(mcontext, R.layout.button_item, null);
				final Button bu_call = (Button) view.findViewById(R.id.bt_call);
				TextView tv_tel = (TextView) view.findViewById(R.id.tv_tel);
				TextView tv_telText = (TextView) view.findViewById(R.id.tv_telText);
				if(i==0){
					tv_telText.setText("电话一：");
				}else if(i==1){
					tv_telText.setText("电话二：");
				}else if(i==2){
					tv_telText.setText("电话三：");
				}else if(i==3){
					tv_telText.setText("电话四：");
				}else if(i==4){
					tv_telText.setText("电话五：");
				}
				final String tel = list.get(i);
				tv_tel.setText(tel);
				if(1==id){
					bu_call.setText("拨打");
				}else if(2==id){
					bu_call.setText("发送短信");
				}
				bu_call.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// id==2 是发生短信 其他是打电话
						if(2==id){
							sendSMS(tel);
						}else{
							call(tel);
						}
					}
				});
				
				linear_tel.addView(view);
		}
		
		}
		
	}
	//发生短信
	private void sendSMS(String tel)
	{
		Uri smsToUri = Uri.parse("smsto:"+tel);
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		mcontext.startActivity(intent);
		Demiss();
	}
	
	
	//拨打电话
	private void call(String tel)
	{
		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri data = Uri.parse("tel:"+ tel);
		intent.setData(data);
		mcontext.startActivity(intent);
		Demiss();
	}
	/*
	 * 显示当前对话框
	 * */
	public void Show(){
		if(builders!=null && !builders.isShowing()){
			 builders.show();
		}
	}
	/*
	 * 显示当前对话框
	 * */
	public void Demiss(){
		if(builders!=null){
			builders.dismiss();
		}
	}
	void setLeftDraw(int drawables,TextView tv){
		Drawable drawable= mcontext.getResources().getDrawable(drawables);  
		/// 这一步必须要做,否则不会显示.  
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
		tv.setCompoundDrawables(drawable,null,null,null);  
	}
	/**
	 * 是否跳转到首页
	 * */
	public MyDialog CheckRegisterMain(Activity mcontext,ResultBean result, final IsRegsiterOrMain registercallback){
		this.mcontext = mcontext;
		this.registercallback = registercallback;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.checkmodle,null);

		tv_ts = (TextView) layout.findViewById(R.id.tv_ts);
		TextView tv_tips = (TextView) layout.findViewById(R.id.tv_tips);
		TextView tv_tip1 = (TextView) layout.findViewById(R.id.tv_tip1);
		TextView tv_tip2 = (TextView) layout.findViewById(R.id.tv_tip2);
		TextView tv_tip3 = (TextView) layout.findViewById(R.id.tv_tip3);
//		Msgtips tips = result.getMsgtips();
//		if( tips != null ){
//			if(tips.isSuccess())
//				 setLeftDraw(R.drawable.yes_auth ,tv_ts);
//				  else
//			    setLeftDraw(R.drawable.no_auth ,tv_ts);
//			tv_ts.setText(StringUtil.isEmpty(tips.getTitle())?"":tips.getTitle());
//			tv_tips.setText(StringUtil.isEmpty(tips.getContent())?"":tips.getContent());
//
//			int size = tips.getMsglist().size();
//			for(int i = 0;i<size;i++){
//				switch (i) {
//				case 0:
//					tv_tip1.setText(tips.getMsglist().get(i).getMsg());
//					break;
//				case 1:
//					tv_tip2.setText(tips.getMsglist().get(i).getMsg());
//					break;
//				case 2:
//					tv_tip3.setText(tips.getMsglist().get(i).getMsg());
//					break;
//				default:
//					break;
//				}
//			}
//	   }
		//关闭按钮
		linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
		//跳转到提交认证资料
		hehe_register = (TextView) layout.findViewById(R.id.hehe_register);
		//跳转到首页
		hehe_main = (TextView) layout.findViewById(R.id.hehe_main);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		builders.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
				{
					return true;
				}
				return false;
			}
		});
		//不能点击外面消除
		builders.setCanceledOnTouchOutside(false);
		linear_close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Demiss();
			}
		});
		hehe_register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				registercallback.setResult(1);
			}
		});
		hehe_main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				registercallback.setResult(2);
			}
		});
		Show();
		return builders;
	}
	/**
	 * 选择更新app
	 * */
	public MyDialog Updategrade(Activity mcontext,String updateNotice,final UpdateOrNot updateOrNot){
		this.mcontext = mcontext;
		this.updateOrNot = updateOrNot;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.upgrade_app,null);
		//关闭按钮
		linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
		//取消更新
		hehe_updatelater = (TextView) layout.findViewById(R.id.hehe_updatelater);
		//更新提示文本
		tv_ts = (TextView) layout.findViewById(R.id.tv_ts);
		pb = (ProgressBar) layout.findViewById(R.id.pb);
		//更新下载app
		hehe_updatenow = (TextView) layout.findViewById(R.id.hehe_updatenow);
		tv_ts.setText(StringUtil.isEmpty(updateNotice)?"":updateNotice);
		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
	    builders.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					 if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
			            {
			             return true;
			            }
					return false;
				}
			});
		 //不能点击外面消除
	    builders.setCanceledOnTouchOutside(false);
//		linear_close.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Demiss();
//			}
//		});
		hehe_updatelater.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateOrNot.setResult(1);
			}
		});
		hehe_updatenow.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateOrNot.setResult(2);
			}
		});
		Show();
		return builders;
	}
	private int indexModle;//0列表 1 购物车
	//弹出购物车商品不满足条件
	public MyDialog AlertShoppingError(int indexModle,Activity mcontext,String updateNotice,final UpdateOrNot updateOrNot){
		this.indexModle = indexModle;
		this.mcontext = mcontext;
		this.updateOrNot = updateOrNot;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.shopping_error,null);
		//取消提交
		TextView hehe_cancle = (TextView) layout.findViewById(R.id.hehe_cancle);
		if(indexModle == 0)
			hehe_cancle.setText("去购物车查看");
		else
			hehe_cancle.setText("取消");
		tv_ts = (TextView) layout.findViewById(R.id.tv_ts);
		TextView hehe_commit = (TextView) layout.findViewById(R.id.hehe_commit);
		tv_ts.setText(StringUtil.isEmpty(updateNotice)?"":updateNotice);
		if(indexModle == 2){
			//删除商品提示
			hehe_commit.setText("删除");
		}else if(indexModle == 1){
			hehe_commit.setText("去结算");
		}else if(indexModle == 3){
			hehe_commit.setText("确定");
		}
		builders = getInstance(mcontext);
		//不能点击外面消除
		builders.setCanceledOnTouchOutside(false);
		hehe_cancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateOrNot.setResult(1);//取消提交1
			}
		});
		hehe_commit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateOrNot.setResult(2);//继续提交2
			}
		});
		Show();
		return builders;
	}
	/**
	 * 弹出提示系统提示框
	 * */
//	public MyDialog SystemDialog(Activity mcontext,PushBaseBean pushBaseBean){
//		this.mcontext = mcontext;
//		LayoutInflater inflater = (LayoutInflater)
//				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		layout = inflater.inflate(R.layout.news_item,null);
//		LinearLayout linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
//		TextView tv_ts_title = (TextView) layout.findViewById(R.id.tv_ts_title);
//		TextView tv_ts_news = (TextView) layout.findViewById(R.id.tv_ts_news);
//		tv_ts_news.setText(StringUtil.isEmpty(pushBaseBean.getResult().getContent())?"":pushBaseBean.getResult().getContent());
//		tv_ts_title.setText(StringUtil.isEmpty(pushBaseBean.getResult().getTitle())?"":pushBaseBean.getResult().getTitle());
//		builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
//		//不能点击外面消除
//		builders.setCanceledOnTouchOutside(false);
//		linear_close.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Demiss();
//			}
//		});
////		Show();
//		return builders;
//	}
			//待发货取消订单
	//	DAI_FU_KUAN_DINGJIN(1,"待付款—订金"), 1 3  9
	//	DAI_FU_KUAN_WEIKUAN(2,"待付款—尾款"),
	//	DAI_FU_KUAN_QUANE(3,"待付款"),//批发
	//	JIN_XING_ZHONG(9,"拼单进行中"),//拼单进行中
	//	SHOU_LI_ZHONG(10,"受理中"),
	//	BEI_HUO_ZHONG(6,"备货中"),//待发货
			public MyDialog cancleOrder_remark(final Activity mcontext,final String type,final String statue,final BackRemark callback){
				this.mcontext = mcontext;
				this.callback = callback;
				this.type = type;
				LayoutInflater inflater = (LayoutInflater)
						mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = inflater.inflate(R.layout.cancle_order,null);
				LinearLayout linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
				LinearLayout linear_dj = (LinearLayout) layout.findViewById(R.id.linear_dj);//定金
				LinearLayout linear_reason = (LinearLayout) layout.findViewById(R.id.linear_reason);//理由
				TextView bt_countine = (TextView) layout.findViewById(R.id.bt_countine);//继续
				if(statue==null){
					linear_dj.setVisibility(View.VISIBLE);
					linear_reason.setVisibility(View.GONE);
				}else{
					linear_dj.setVisibility(View.GONE);
					linear_reason.setVisibility(View.VISIBLE);
				}
				final EditText et_reason = (EditText) layout.findViewById(R.id.et_reason);//取消原因
				builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
				Show();
				//取消订单
				bt_countine.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						//批发取消
						if(statue != null && "1".equals(type)){
							callback.setRemark("", et_reason.getText().toString());
							Demiss();
						}else{
							if("0".equals(type) && statue==null){
								callback.setRemark("", remark_reason);
							}
						}
						//拼单取消
						if("0".equals(type)){
							if(statue!=null){
								if("2".equals(statue) || "6".equals(statue) || "10".equals(statue)){
									remark_reason = et_reason.getText().toString();
									Demiss();
									cancleOrder_remark(mcontext, type,null, callback);
								}else{
									callback.setRemark("", et_reason.getText().toString());
									Demiss();
								}
							}
						}else{
							
						}
						if(statue==null)
						Demiss();
					}
				});
				//关闭当前弹框
				linear_close.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
							Demiss();
					}
				});
				return builders;
			}
		//弹出客服电话
			public MyDialog heheTel(Activity mcontext,String canclemsg){
				this.mcontext = mcontext;
				LayoutInflater inflater = (LayoutInflater)
						mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = inflater.inflate(R.layout.hehe_tel,null);
				TextView hehe_tel = (TextView) layout.findViewById(R.id.hehe_tel);
				TextView tv_cancle = (TextView) layout.findViewById(R.id.tv_cancle);
				TextView tv_order = (TextView) layout.findViewById(R.id.tv_order);
				tv_order.setText(StringUtil.isEmpty(canclemsg)?"":canclemsg);
				builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
				//喝喝客服
				hehe_tel.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						call(ConstantsUtil.SERVICE_TEL);
					}
				});
				//关闭当前弹框
				tv_cancle.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Demiss();
					}
				});
				Show();
				return builders;
			}
			//弹出是否取消订单确定界面
			public MyDialog CancleCommit(Activity mcontext,String text,final CancleOrder cancleback){
				this.mcontext = mcontext;
				this.cancleback = cancleback;
				LayoutInflater inflater = (LayoutInflater)
						mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = inflater.inflate(R.layout.canclecommit,null);
				LinearLayout linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
				TextView tv_ts_cancle = (TextView) layout.findViewById(R.id.tv_ts_cancle);
				TextView hehe_bt_countine = (TextView) layout.findViewById(R.id.hehe_bt_countine);
				builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
				tv_ts_cancle.setText(text);
				//继续
				hehe_bt_countine.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						cancleback.isCancle(true);
						Demiss();
					}
				});
				//关闭当前弹框
				linear_close.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						cancleback.isCancle(false);
						Demiss();
					}
				});
				Show();
				return builders;
			}
			//确定收货二次确定界面 防止误点
//			public MyDialog CancleOrderCommitTwo(Activity mcontext,final CancleOrder cancleback){
//				this.mcontext = mcontext;
//				this.cancleback = cancleback;
//				LayoutInflater inflater = (LayoutInflater)
//						mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				layout = inflater.inflate(R.layout.canclecommit,null);
//				LinearLayout linear_close = (LinearLayout) layout.findViewById(R.id.linear_close);
//				TextView tv_ts_cancle = (TextView) layout.findViewById(R.id.tv_ts_cancle);
//				TextView hehe_bt_countine = (TextView) layout.findViewById(R.id.hehe_bt_countine);
//				builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
//				//继续
//				hehe_bt_countine.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						cancleback.isCancle(true);
//						Demiss();
//					}
//				});
//				//关闭当前弹框
//				linear_close.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						cancleback.isCancle(false);
//						Demiss();
//					}
//				});
//				Show();
//				return builders;
//			}
	
			
	//备注修改回调接口
	public interface BackRemark{
		void setRemark(String cid, String remark);
	}
	
	//备注是否跳转到首页面还是提交资料 1 是到提交认证资料 2 是跳转到首页
	public interface IsRegsiterOrMain{
		void setResult(int modle);
	}
	
	//回调接口
	public interface UpdateOrNot{
		void setResult(int modle);
	}
	
	public interface CancleOrder{
		void isCancle(boolean iscancle);
	}
	public interface BalanceCallBack{
		void backBalance(String balance);
		void close();
	}
	
	//发送喝喝币支付时候 验证码
	public interface HeheCoinCallBack{
		void code(String code);
	}
}
