package com.heheys.ec.model.dataBean;

import java.io.Serializable;


public class MyPointBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private MyPointsBean result;//返回结果
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
	public MyPointsBean getResult() {
		return result;
	}
	public void setResult(MyPointsBean result) {
		this.result = result;
	}
	
	public class MyPointsBean implements Serializable{
/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//		result.issign
//		result.score
//		result.tips
//		result.helphtml
		private boolean issign;
		private String score;
		private String tips;
		private String helphtml;
		public boolean getIssign() {
			return issign;
		}
		public void setIssign(boolean issign) {
			this.issign = issign;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
		public String getTips() {
			return tips;
		}
		public void setTips(String tips) {
			this.tips = tips;
		}
		public String getHelphtml() {
			return helphtml;
		}
		public void setHelphtml(String helphtml) {
			this.helphtml = helphtml;
		}
	}
}
