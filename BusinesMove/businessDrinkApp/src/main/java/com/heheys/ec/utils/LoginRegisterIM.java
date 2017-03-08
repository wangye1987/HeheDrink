/*package com.heheys.ec.utils;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.thirdPartyModule.IM.IMHelper;

*//**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间
 *            ：2016-3-14 上午10:50:28 类说明
 *//*
public class LoginRegisterIM {
	private YWIMKit mIMKit;

	public void LoginIM(final String md5UserName,final String md5Pwd) {
		// 此实现不一定要放在Application onCreate中
		final String userid = "18620218750";
		final String password = "123456";
//		mIMKit = IMHelper.getInstance().getYwIMKit();
		mIMKit = MyApplication.getInstance().mIMKit;
		if (mIMKit != null) {// 开始登录
			YWLoginParam loginParam = YWLoginParam.createLoginParam(userid,
					password);
			IYWLoginService loginService = mIMKit.getLoginService();
			loginService.login(loginParam, new IWxCallback() {
				@Override
				public void onSuccess(Object... arg0) {
//					System.out.println("login_success");

				}

				@Override
				public void onProgress(int arg0) {
//					System.out.println("login_progress");
					// TODO Auto-generated method stub
				}

				@Override
				public void onError(int errCode, String description) {
//					System.out.println("login_fail");
					// 如果登录失败，errCode为错误码,description是错误的具体描述信息
				}
			});
		}
	}
}
*/