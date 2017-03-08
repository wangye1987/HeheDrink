package com.heheys.ec.model.dataBean;

import java.io.Serializable;


public class CanUserCouponBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private MyUserCoupon result;//返回结果
	private ErrorBean error;//返回失败结果
	
	public MyUserCoupon getResult() {
		return result;
	}

	public void setResult(MyUserCoupon result) {
		this.result = result;
	}

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

	public class MyUserCoupon implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Enable enable ;
		private Disable disable ;
		
		public Enable getEnable() {
			return enable;
		}
		public void setEnable(Enable enable) {
			this.enable = enable;
		}
		public Disable getDisable() {
			return disable;
		}
		public void setDisable(Disable disable) {
			this.disable = disable;
		}
		public class Enable implements Serializable{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
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
		public class Disable implements Serializable{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
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
