package com.heheys.ec.model.dataBean; 

import java.io.Serializable;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-24 下午5:09:42 
 * 类说明 
 * @param 登录用户名和密码
 */
public class LoginNamePwd implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//登录用户名
	private String userName;
	//登录密码
	private String userPwd;
	//是否记住登录用户名
	private boolean isRemeber;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public boolean isRemeber() {
		return isRemeber;
	}
	public void setRemeber(boolean isRemeber) {
		this.isRemeber = isRemeber;
	}

}
 