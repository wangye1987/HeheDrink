package com.heheys.ec.controller.receiver;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.heheys.ec.utils.ConstantsUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		try {
			if(SecurityInit.Initialize(context) == 0){
				 SecurityCipher securityCipher = new SecurityCipher(context);
				 String wx_later_wx = securityCipher.decryptString(ConstantsUtil.APP_ID,ConstantsUtil.JAQ_KEY);
					msgApi.registerApp(wx_later_wx);
				 }else{
					 msgApi.registerApp(ConstantsUtil.APP_ID_WX);
				 }
		} catch (JAQException e) {
			msgApi.registerApp(ConstantsUtil.APP_ID_WX);
			e.printStackTrace();
		}
	}
}
