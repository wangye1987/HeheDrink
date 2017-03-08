package com.heheys.ec.thirdPartyModule.payModule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BasebeanSign;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author wangkui
 * 
 * @category 支付宝支付接口 2016-03-10
 * 
 * @version 1.0
 * 
 */
public class Alipay {
	// 商户私钥，pkcs8格式
	
	public  String RSA_PRIVATE_SECURITY = "Rynk5jP5WmsG0HlwbD0gVCNbefePHHWI0jQBD55k5fx9BMQkQrVTnEzw36qFTV/5IYe/nFqhp/EVeZNbFPVMK0syF6gY/Ga9ujdjKPeOgpoFkrCHMSmTSdVahKY9Id3qnhwateJe3O5pHOI2h9hq3xNIcCR40Jor8R9HvazFkVF5eXbJcWu69M52WFugU+YZvhoMIPIAhsUEfm7yqopF08NL7FCTCDO2Sk0PQ+k+tK+7Mx1g+sN8MMrzO5k1Hr3BbIZqafmJpafuT2WeN/+L3VLum7nfp5mmEVXI3ceogLuWPi1rAaHpibahpV/jth5ShDEHC2IGp0muSDUfnMl3ZJIMUC7yuStVMqcIKe08NmMZ1vZQUwKo+kcmp2QrPalwQptabLjQZzdwoUTFCJLk7RYfhf7Un9FBlYDvzIDywJ3Rq5c5GiD4HsICK70deN96pr+zAbT7/yroo9TyLzFpQU3bYeZe0imnYPbYTwzLgZoYdGclChf+g+waoAtsKLJWEp/mjrqL9LpqL38ERt1zR8DeSu/gunNCUBPyrP15yc2ihvU5jsBeQCOADPCSRO8mHEWveOt7xSaPqOEr8IUmPQKGu4Fm3lEUZTAa+nH8EklDQPBEQ+IjJUAlQO2zUrKkEojW7NCdAdh/MlFuCHUyVVQsE3YYhvkVs3C7rItNOOwWt9Mh8dXzTgQ5wnUUDkOKXxdOpuKskgM99+T47PmozNRo22e3NOkXBuD6leTxe52j1pp4jMGtbqSIuer95mt3WjKQAs0/+uOGz5NwXljrMYqbnZVT/g7H9wfiiT+UdOF4RekAeo1HAeN427d5OQOcUzfdgBkajydRQSGWHrd0ziUSi1M89VR43a9MVcIKPMrLXbqN7Jqt7GdzkhNn32g6ldO97HJclnxcqLsLac8ur92KUpv2GCp3xhrOJ+Yn13nohbCPsHoqdYDK3P8NtDmUD+z87zY8iuUPAJIMFfcIgkiRGJS2xyH5iJkTzMH/QOXdOQg2Q5na1cQc5/DPY/EADaOkFkk46/a58gGtnpapVIMOGP3k3wnlP7L1eeVhPu6Rf6n5UkpEUmfzh2nJHK5LsHB/PIweiiAcCpHBWtNfBlfb61XWxYFKPwxHwPnfziU=";
	public  String RSA_PRIVATE ="";// 支付宝公钥
	
	
	public   String RSA_PUBLIC_SECURITY  = "22hNcqpUQYshuSC/PBOMld6KmpKeaG8VU2MNt5cDu5Qg1I07W06rwkvPNTEJIiZSZTtcFIlPaKSy4uqfv15iFsvOLXojaoTg7QtMWJs11VMFr9OglLOiJIG/vwhYAGgUZkYdkJo3O0hwNE8ZO2I0soNnewUVVCbOpX544rAwIRrzgZjuMmvXFA0oBtkYMAYDVYnPx/f9Kk7S+tXM8WeV3ky/CETFXiL/lbJtpXI2kc1nmvhXerGNwwD9lOps8nrQO+vqmST9XAOKAf2PiIZm229DP64UaYQC1Zf/Q9SXnXY=";
	public   String RSA_PUBLIC = "";// 商户PID
	
	
	public   String PARTNER_SECURITY = "UX7pFFhDL80e5N5WE8EtKnfWzd3T5XglKxfr36mVaFg=";
	public   String PARTNER = "";

	// 商户收款账号
	
	public  String SELLER_SECURITY="DCRlxL2oyCbQCViQPm9zwTNCgUAUTlyMGgD41KlCu9g=";
	public  String SELLER="";
	//聚安全key

	private static final int SDK_PAY_FLAG = 1;

	Context mContext;
	static WeakHandler<?> mHandler;
	private String orderInfo;
//	private String oid;
//	private String code;

	public Alipay(Context mContext,WeakHandler<?> mHandler) {
		this.mContext = mContext;
		Alipay.mHandler =  mHandler;
		Scriary(mContext);
//		TestKey();
	}

	//聚安全加密支付宝
	private void Scriary(Context mContext){
		try {
			if(SecurityInit.Initialize(mContext) == 0){
					 SecurityCipher securityCipher = new SecurityCipher(mContext);
					 RSA_PRIVATE = securityCipher.decryptString(RSA_PRIVATE_SECURITY,ConstantsUtil.JAQ_KEY);
					 RSA_PUBLIC = securityCipher.decryptString(RSA_PUBLIC_SECURITY,ConstantsUtil.JAQ_KEY);
					 PARTNER = securityCipher.decryptString(PARTNER_SECURITY,ConstantsUtil.JAQ_KEY);
					 SELLER = securityCipher.decryptString(SELLER_SECURITY,ConstantsUtil.JAQ_KEY);
			}
		} catch (JAQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

	//阿里聚安全脚本获取加密数据
//	void TestKey(){
//		try {
//			if(SecurityInit.Initialize(mContext) == 0){
//					 SecurityCipher securityCipher = new SecurityCipher(mContext);
//					 String RSA_PRIVATE_later = securityCipher.encryptString(RSA_PRIVATE_SECURITY_TEST, ConstantsUtil.JAQ_KEY);
//					 String RSA_PUBLIC_later = securityCipher.encryptString(RSA_PUBLIC_SECURITY_TEST, ConstantsUtil.JAQ_KEY);
//					 String PARTNER_later = securityCipher.encryptString(PARTNER_SECURITY_TEST, ConstantsUtil.JAQ_KEY);
//					 String SELLER_later = securityCipher.encryptString(SELLER_SECURITY_TEST, ConstantsUtil.JAQ_KEY);
//					 String wx_later = securityCipher.encryptString("wx119870bae7706fb6", ConstantsUtil.JAQ_KEY);
//					 Log.d("key1", "加密前"+RSA_PRIVATE_SECURITY_TEST);
//					 Log.d("key1", "加密前"+RSA_PUBLIC_SECURITY_TEST);
//					 Log.d("key1", "加密前"+PARTNER_SECURITY_TEST);
//					 Log.d("key1", "加密前"+SELLER_SECURITY_TEST);
//					 Log.d("key1", "加密前"+"wx119870bae7706fb6");
//					 
//					 Log.d("key1", "加密后"+RSA_PRIVATE_later);
//					 Log.d("key1", "加密后"+RSA_PUBLIC_later);
//					 Log.d("key1", "加密后"+PARTNER_later);
//					 Log.d("key1", "加密后"+SELLER_later);
//					 Log.d("key1", "加密后"+wx_later);
//					RSA_PRIVATE = securityCipher.decryptString(RSA_PRIVATE_later,ConstantsUtil.JAQ_KEY);
//					RSA_PUBLIC = securityCipher.decryptString(RSA_PUBLIC_later,ConstantsUtil.JAQ_KEY);
//					PARTNER = securityCipher.decryptString(PARTNER_later,ConstantsUtil.JAQ_KEY);
//					SELLER = securityCipher.decryptString(SELLER_later,ConstantsUtil.JAQ_KEY);
//					String wx_later_wx = securityCipher.decryptString(wx_later,ConstantsUtil.JAQ_KEY);
//					Log.d("key1", "解密后"+RSA_PRIVATE);
//					Log.d("key1","解密后"+RSA_PUBLIC);
//					Log.d("key1", "解密后"+PARTNER);
//					Log.d("key1", "解密后"+SELLER);
//					Log.d("key1", "解密后"+wx_later_wx);
//			}
//		} catch (JAQException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
//	}
	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String id,String price) {
		
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + id + "\"";
//		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
//				+ "\"";
		//测试环境通知
//		orderInfo += "&notify_url=" + "\"" + "http://test.heheys.com/notify/alipay.jhtml"
//				+ "\"";
		orderInfo += "&notify_url=" + "\"" + "https://www.heheys.com/notify/alipay.jhtml"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空 移动端可以不传 不然某些情况会出现订单支付不成功的情况，提示(ALI64)
        //orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String oid,String code,String status) {
		Scriary(mContext);
//		TestKey();
//		System.out.println("--RSA_PRIVATE--("+RSA_PRIVATE+")--RSA_PUBLIC--("+RSA_PUBLIC+")--SELLER--=("+SELLER+")--PARTNER--("+PARTNER+")");
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(mContext)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									// ((Object) mContext).finish();
								}
							}).show();
			return;
		}
		//如果是支付尾款就处理订单号
		if("2".equals(status))
			oid = oid+"-"+status;
		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		ApiHttpCilent.getInstance(mContext.getApplicationContext()).AlipaySign(mContext,oid,
				code, "ali", new RequestCallBack());
		
	}
	
	//去支付
	public  void Topay(final String signs){
//		String signs = sign(orderInfo);
//		try {
//			/**
//			 * 仅需对sign 做URL编码
//			 */
//			signs = URLEncoder.encode(signs, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//		/**
//		 * 完整的符合支付宝参数规范的订单信息
//		 */
//		
//		final String payInfo = orderInfo + "&sign=\"" + signs + "\"&"
//				+ getSignType();
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(signs, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	private void Dimess() {
		((Activity) mContext).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	public  class RequestCallBack extends
			BaseJsonHttpResponseHandler<BasebeanSign> {

		private BasebeanSign basebeanSign;

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BasebeanSign arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BasebeanSign arg3) {
			Dimess();
		}

		@Override
		protected BasebeanSign parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			basebeanSign = gson.fromJson(response,
					BasebeanSign.class);
			Message message = Message.obtain();
			if ("1".equals(basebeanSign.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
				message.obj = basebeanSign.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = basebeanSign.getError().getInfo();
			}
			mHandler.sendMessage(message);
			return basebeanSign;
		}

	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
}