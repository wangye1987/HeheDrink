package com.heheys.ec.model.dataBean;

import java.io.Serializable;

public class ErrorBackBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*错误代码*/
	private String code;
	/*错误信息*/
	private String info;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	

}
