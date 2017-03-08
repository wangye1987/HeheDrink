/**
 * 
 */
package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * @author wangkui
 * 2016-4-28下午4:08:34
 * @version 1.0 
 */
public class ShareBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private ShareResultBean result;//返回结果
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
	public ShareResultBean getResult() {
		return result;
	}
	public void setResult(ShareResultBean result) {
		this.result = result;
	}

	
	public class ShareResultBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String shareurl;

		public String getShareurl() {
			return shareurl;
		}

		public void setShareurl(String shareurl) {
			this.shareurl = shareurl;
		}
	}
}
