package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

public class SalonListBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private SalonResultBean result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SalonResultBean getResult() {
		return result;
	}

	public void setResult(SalonResultBean result) {
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
		return "SalonListBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class SalonResultBean implements Serializable {

		private static final long serialVersionUID = 1L;
		private int count;
		private List<SalonInfor> list;
		private String baseurl;

		public String getBaseurl() {
			return baseurl;
		}

		public void setBaseurl(String baseurl) {
			this.baseurl = baseurl;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<SalonInfor> getList() {
			return list;
		}

		public void setList(List<SalonInfor> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "SalonResultBean [count=" + count + ", list=" + list
					+ ", baseurl=" + baseurl + "]";
		}

		public static class SalonInfor implements Serializable {

			private static final long serialVersionUID = 1L;
			private int id;
			private String pic;
			private String subject;
			private String starttime;
			private String endtime;
			private String address;
			private String maxnum;
			private String status;
			private String sign;

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

			public String getPic() {
				return pic;
			}

			public void setPic(String pic) {
				this.pic = pic;
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

			public String getMaxnum() {
				return maxnum;
			}

			public void setMaxnum(String maxnum) {
				this.maxnum = maxnum;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public String getSign() {
				return sign;
			}

			public void setSign(String sign) {
				this.sign = sign;
			}

			@Override
			public String toString() {
				return "SalonInfor [id=" + id + ", pic=" + pic + ", subject="
						+ subject + ", starttime=" + starttime + ", endtime="
						+ endtime + ", address=" + address + ", maxnum="
						+ maxnum + ", status=" + status + ", sign=" + sign
						+ "]";
			}

		}
	}
}
