package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;


public class CouponBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private Couponbean result;//返回结果
	private ErrorBean error;//返回失败结果
//	"list[
//	     {   "CouponTitle":"优惠券title",
//	    	 "CouponContent":"优惠券",
//	    	 "id":"1"},
//	     {},
//	     {}
//	     ]"
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

	public Couponbean getResult() {
		return result;
	}
	public void setResult(Couponbean result) {
		this.result = result;
	}

	public class Couponbean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private ArrayList<couponitem> list;
		
		
		public ArrayList<couponitem> getList() {
			return list;
		}


		public void setList(ArrayList<couponitem> list) {
			this.list = list;
		}


		public class couponitem implements Serializable{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			private String couponTitle;//显示标题
			private String couponContent;//消息提示
			private String id;//消息id
			
			public String getCouponTitle() {
				return couponTitle;
			}
			public void setCouponTitle(String couponTitle) {
				this.couponTitle = couponTitle;
			}
			public String getCouponContent() {
				return couponContent;
			}
			public void setCouponContent(String couponContent) {
				this.couponContent = couponContent;
			}
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
		}
	}
}
