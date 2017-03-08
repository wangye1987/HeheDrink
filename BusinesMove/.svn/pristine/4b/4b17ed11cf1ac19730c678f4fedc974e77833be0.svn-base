package com.heheys.ec.model.dataBean;

import java.io.Serializable;


public class ServicelineBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private LineResult result;//返回结果
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
	public LineResult getResult() {
		return result;
	}
	public void setResult(LineResult result) {
		this.result = result;
	}
	public class LineResult implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String serviceline;//客服电话
		private String indexurl;//首页url
		private String codhelpurl;//活动付款url
		private String groupHelpUrl;//活动付款url
		private ShareInfo appShare;

		public String getGroupHelpUrl() {
			return groupHelpUrl;
		}

		public void setGroupHelpUrl(String groupHelpUrl) {
			this.groupHelpUrl = groupHelpUrl;
		}

		public ShareInfo getAppShare() {
			return appShare;
		}

		public void setAppShare(ShareInfo appShare) {
			this.appShare = appShare;
		}

		public String getCodhelpurl() {
			return codhelpurl;
		}

		public void setCodhelpurl(String codhelpurl) {
			this.codhelpurl = codhelpurl;
		}

		public String getServiceline() {
			return serviceline;
		}

		public void setServiceline(String serviceline) {
			this.serviceline = serviceline;
		}

		public String getIndexurl() {
			return indexurl;
		}

		public void setIndexurl(String indexurl) {
			this.indexurl = indexurl;
		}
		
	}

	public static class ShareInfo implements  Serializable{
           private String title;
           private String shareUrl;
           private String pic;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getShareUrl() {
			return shareUrl;
		}

		public void setShareUrl(String shareUrl) {
			this.shareUrl = shareUrl;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}
	}
}
