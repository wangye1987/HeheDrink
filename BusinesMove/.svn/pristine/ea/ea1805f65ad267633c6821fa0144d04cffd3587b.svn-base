package com.heheys.ec.model.dataBean;

import java.io.Serializable;


public class MyCouponBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private MyCoupon result;//返回结果
	private ErrorBean error;//返回失败结果
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MyCoupon getResult() {
		return result;
	}

	public void setResult(MyCoupon result) {
		this.result = result;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	public class MyCoupon implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Invalid invalid ;
		private Unused unused ;
		private Used used ;
		
	public Invalid getInvalid() {
			return invalid;
		}
		public void setInvalid(Invalid invalid) {
			this.invalid = invalid;
		}
		public Unused getUnused() {
			return unused;
		}
		public void setUnused(Unused unused) {
			this.unused = unused;
		}
		public Used getUsed() {
			return used;
		}
		public void setUsed(Used used) {
			this.used = used;
		}
	public class Invalid implements Serializable{
		private String count;
		private String url;
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	public class Unused implements Serializable{
		private String count;
		private String url;
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	public class Used implements Serializable{
		private String count;
		private String url;
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	}
	
}
