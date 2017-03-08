package com.heheys.ec.model.dataBean;

import java.io.Serializable;

import com.heheys.ec.model.dataBean.BrandBaseBean.BrandList;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-31 上午9:32:36
 *  类说明 喝喝支付bean
 */
public class HehePayBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private HehePay result;//返回结果
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
	public HehePay getResult() {
		return result;
	}
	public void setResult(HehePay result) {
		this.result = result;
	}
	public class HehePay implements Serializable{
			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			String balance;

			public String getBanace() {
				return balance;
			}

			public void setBanace(String balance) {
				this.balance = balance;
			}
	}
}
