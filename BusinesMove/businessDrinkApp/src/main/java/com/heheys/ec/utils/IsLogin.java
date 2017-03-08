package com.heheys.ec.utils; 

import android.app.Activity;

import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-13 下午4:42:20 
 * 类说明 
 * @param 是否登录 否则跳转到登录界面
 */
public class IsLogin {
	public static boolean Switch(Activity activity){
		boolean islogin =  SharedPreferencesUtil.getSharedPreferencesBoolean(activity, "login", "islogin", false);
		if(!islogin){
			StartActivityUtil.startActivity(activity,
					LoginActivity.class);
			return false;
		}
		return true;
	}
	
	public static boolean isLogin(Activity activity){
		boolean islogin =  SharedPreferencesUtil.getSharedPreferencesBoolean(activity, "login", "islogin", false);
		return islogin;
	}
}
 