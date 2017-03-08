package com.heheys.ec.model.dataBean;

import java.io.Serializable;

import com.heheys.ec.model.dataBean.MyPointBaseBean.MyPointsBean;


/**
 * @author wangkui
 * 充值喝喝币
 */
public class Rechargebasebean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private RechargeBean result;//返回结果
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
	public RechargeBean getResult() {
		return result;
	}
	public void setResult(RechargeBean result) {
		this.result = result;
	}

	
	public class RechargeBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String oid;
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		
	}
}
