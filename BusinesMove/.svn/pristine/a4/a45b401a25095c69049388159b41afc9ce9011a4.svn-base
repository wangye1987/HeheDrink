package com.heheys.ec.model.dataBean;

import java.io.Serializable;


public class RechargeResultBean implements Serializable{


		private static final long serialVersionUID = 1L;
		private String status;//状态码
		private PayResultBean result;//返回结果
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
		public PayResultBean getResult() {
			return result;
		}
		public void setResult(PayResultBean result) {
			this.result = result;
		}


		
		public class PayResultBean implements Serializable{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			private boolean success;

			public boolean getSuccess() {
				return success;
			}

			public void setSuccess(boolean success) {
				this.success = success;
			}
		}
}
