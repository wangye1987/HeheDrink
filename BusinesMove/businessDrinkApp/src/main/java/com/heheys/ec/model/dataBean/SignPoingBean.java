package com.heheys.ec.model.dataBean;

import java.io.Serializable;


public class SignPoingBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private SignBean result;//返回结果
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
	public SignBean getResult() {
		return result;
	}
	public void setResult(SignBean result) {
		this.result = result;
	}
	
	public class SignBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String score;
		private String tips;

		public String getTips() {
			return tips;
		}

		public void setTips(String tips) {
			this.tips = tips;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}
	}
}
