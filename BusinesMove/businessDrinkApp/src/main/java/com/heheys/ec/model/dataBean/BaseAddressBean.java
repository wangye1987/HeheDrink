package com.heheys.ec.model.dataBean; 

import java.io.Serializable;

/** 
 * @author 作者 E-mail:wk 
 * @version 创建时间：2015-10-14 下午4:28:47 
 * 类说明 
 * @param 地址获取列表bean
 */
public class BaseAddressBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private AddressBean result;//返回结果
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
	public AddressBean getResult() {
		return result;
	}
	public void setResult(AddressBean result) {
		this.result = result;
	}
}
 
 