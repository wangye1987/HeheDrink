package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-22 下午5:37:30
 *  类说明
 */
public class BasebeanSign implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ResultSignBean result;//返回结果
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
	public ResultSignBean getResult() {
		return result;
	}
	public void setResult(ResultSignBean result) {
		this.result = result;
	}
	
	public class ResultSignBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String alipay;
		public String getAlipay() {
			return alipay;
		}
		public void setAlipay(String alipay) {
			this.alipay = alipay;
		}

		
	}
}
