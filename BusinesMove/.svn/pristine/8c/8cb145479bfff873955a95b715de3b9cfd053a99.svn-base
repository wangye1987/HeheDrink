package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * Describe:沙龙客户详情对应数据实体类
 * 
 * Date:2015-10-19
 * 
 * Author:liuzhouliang
 */
public class SalonCustomerDetailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private CustomerDetailBean result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustomerDetailBean getResult() {
		return result;
	}

	public void setResult(CustomerDetailBean result) {
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
		return "SalonCustomerDetailBean [status=" + status + ", result="
				+ result + ", error=" + error + "]";
	}

	public class CustomerDetailBean implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private String company;
		private String status;
		private String mobile;
		private String weixin;
		private String position;

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
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
			return "CustomerDetailBean [name=" + name + ", company=" + company
					+ ", status=" + status + ", mobile=" + mobile + ", weixin="
					+ weixin + ", position=" + position + "]";
		}

	}
}
