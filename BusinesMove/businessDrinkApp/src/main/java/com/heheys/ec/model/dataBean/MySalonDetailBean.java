package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

public class MySalonDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private MySalonDetailInfor result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MySalonDetailInfor getResult() {
		return result;
	}

	public void setResult(MySalonDetailInfor result) {
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
		return "MySalonDetailBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class MySalonDetailInfor implements Serializable {

		private static final long serialVersionUID = 1L;
		private int id;
		private String subject;
		private String address;
		private String guest;
		private String starttime;
		private String endtime;
		private String maxnum;
		private String usednum;
		private List<String> piclist;
		private String summary;
		private String consulmobile;
		private String sign;
		private String baseurl;
		private List<MySalonCustomerInfor> signer;

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getConsulmobile() {
			return consulmobile;
		}

		public void setConsulmobile(String consulmobile) {
			this.consulmobile = consulmobile;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getBaseurl() {
			return baseurl;
		}

		public void setBaseurl(String baseurl) {
			this.baseurl = baseurl;
		}

		public String getStarttime() {
			return starttime;
		}

		public void setStarttime(String starttime) {
			this.starttime = starttime;
		}

		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getGuest() {
			return guest;
		}

		public void setGuest(String guest) {
			this.guest = guest;
		}

		public String getMaxnum() {
			return maxnum;
		}

		public void setMaxnum(String maxnum) {
			this.maxnum = maxnum;
		}

		public String getUsednum() {
			return usednum;
		}

		public void setUsednum(String usednum) {
			this.usednum = usednum;
		}

		public List<String> getPiclist() {
			return piclist;
		}

		public void setPiclist(List<String> piclist) {
			this.piclist = piclist;
		}

		public List<MySalonCustomerInfor> getSigner() {
			return signer;
		}

		public void setSigner(List<MySalonCustomerInfor> signer) {
			this.signer = signer;
		}

		

		@Override
		public String toString() {
			return "MySalonDetailInfor [id=" + id + ", subject=" + subject
					+ ", address=" + address + ", guest=" + guest
					+ ", starttime=" + starttime + ", endtime=" + endtime
					+ ", maxnum=" + maxnum + ", usednum=" + usednum
					+ ", piclist=" + piclist + ", summary=" + summary
					+ ", consulmobile=" + consulmobile + ", sign=" + sign
					+ ", baseurl=" + baseurl + ", signer=" + signer + "]";
		}



		public static class MySalonCustomerInfor implements Serializable {

			private static final long serialVersionUID = 1L;
			private String uid;
			private String name;
			private String position;
			// 0 未交换联系方式 1 已发送 2 已交换 5 对方发送交换请求 6 本人
			private String status;
			private String company;
			private String mobile;
			private String weixin;

			public String getUid() {
				return uid;
			}

			public void setUid(String uid) {
				this.uid = uid;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPosition() {
				return position;
			}

			public void setPosition(String position) {
				this.position = position;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public String getCompany() {
				return company;
			}

			public void setCompany(String company) {
				this.company = company;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			public String getWeixin() {
				return weixin;
			}

			public void setWeixin(String weixin) {
				this.weixin = weixin;
			}

			@Override
			public String toString() {
				return "MySalonCustomerInfor [uid=" + uid + ", name=" + name
						+ ", position=" + position + ", status=" + status
						+ ", company=" + company + ", mobile=" + mobile
						+ ", weixin=" + weixin + "]";
			}

		}
	}
}
