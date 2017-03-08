package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:获取广告实体
 * 
 * Date:2015-10-20
 * 
 * Author:liuzhouliang
 */
public class GetAdvertisementBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private AdvertisementInforBean result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AdvertisementInforBean getResult() {
		return result;
	}

	public void setResult(AdvertisementInforBean result) {
		this.result = result;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "GetAdvertisementBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class AdvertisementInforBean implements Serializable {
		private static final long serialVersionUID = 1L;
		private List<AdverInfor> list;

		public List<AdverInfor> getList() {
			return list;
		}

		public void setList(List<AdverInfor> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "AdvertisementInforBean [list=" + list + "]";
		}

		public static class AdverInfor implements Serializable {
			private static final long serialVersionUID = 1L;
			private String picurl;
			private String linkurl;
			private String type;
			private String res;
			private String title;//专场title
//			"res": "http://test.heheys.com/api/coupon/list.jhtml",
//            "linkurl": "url",
//            "picurl": "http://test.heheys.com/images/lb1.jpg",
//            "type": "url"
			
			public String getPicurl() {
				return picurl;
			}
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getRes() {
				return res;
			}
			public void setRes(String res) {
				this.res = res;
			}
			public void setPicurl(String picurl) {
				this.picurl = picurl;
			}
			public String getLinkurl() {
				return linkurl;
			}
			public void setLinkurl(String linkurl) {
				this.linkurl = linkurl;
			}
			@Override
			public String toString() {
				return "AdverInfor [picurl=" + picurl + ", linkurl=" + linkurl
						+ "]";
			}
			
		}

	}
}
