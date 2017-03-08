package com.heheys.ec.model.dataBean; 

import java.io.Serializable;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-14 下午12:09:07 
 * 类说明 
 * @param
 */
public class ShopBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ShopBean result;//返回结果
	private ErrorBean error;//返回失败结果
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public ShopBean getResult() {
		return result;
	}
	public void setResult(ShopBean result) {
		this.result = result;
	}
	

}
 