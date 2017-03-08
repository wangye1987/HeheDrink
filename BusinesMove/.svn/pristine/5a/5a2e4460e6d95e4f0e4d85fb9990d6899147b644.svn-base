package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;

public class ExpressBaseBean implements Serializable{
	
	/**
	 * 快递地址
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private ResultExpressBean result;//返回结果
	private ErrorBean error;//返回失败结果
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public ResultExpressBean getResult() {
		return result;
	}


	public void setResult(ResultExpressBean result) {
		this.result = result;
	}


	public ErrorBean getError() {
		return error;
	}


	public void setError(ErrorBean error) {
		this.error = error;
	}


	public class ResultExpressBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private ArrayList<Express> expresslist;

		public ArrayList<Express> getExpresslist() {
			return expresslist;
		}

		public void setExpresslist(ArrayList<Express> expresslist) {
			this.expresslist = expresslist;
		}

	}
	public class Express implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String expressname;
		private String deliverytime;
		private String expressnum;
		public String getExpressname() {
			return expressname;
		}
		public void setExpressname(String expressname) {
			this.expressname = expressname;
		}
		public String getDeliverytime() {
			return deliverytime;
		}
		public void setDeliverytime(String deliverytime) {
			this.deliverytime = deliverytime;
		}
		public String getExpressnum() {
			return expressnum;
		}
		public void setExpressnum(String expressnum) {
			this.expressnum = expressnum;
		}
	}
}
