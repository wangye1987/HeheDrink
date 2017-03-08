package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Describe:业务数据类型
 *
 * Date:2015年11月25日下午4:51:16
 *
 * Author:LZL
 *
 */
public class BusinessDataBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private BusinessInfor result;// 返回结果
	private ErrorBean error;// 返回失败结果
	public class ErrorBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/*错误代码*/
		private String code;
		/*错误信息*/
		private String info;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		
	}
	@Override
	public String toString() {
		return "BusinessDataBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BusinessInfor getResult() {
		return result;
	}

	public void setResult(BusinessInfor result) {
		this.result = result;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static class BusinessInfor implements Serializable {
		private static final long serialVersionUID = 1L;
		private Map<String, String> winetype;
		private Map<String, String> countunit;
		private Map<String, String> pindanstatus;
		private Map<String, String> shopstatus;
		private Map<String, String> salonstatus;
		private Map<String, String> paytype;
		private Map<String, String> winereqstatus;
		private Map<String, String> verifystatus;
		private Map<String, String> thirdpay;
		private Map<String, String> orderstatus;

		public Map<String, String> getWinetype() {
			return winetype;
		}

		public void setWinetype(Map<String, String> winetype) {
			this.winetype = winetype;
		}

		public Map<String, String> getCountunit() {
			return countunit;
		}

		public void setCountunit(Map<String, String> countunit) {
			this.countunit = countunit;
		}

		public Map<String, String> getPindanstatus() {
			return pindanstatus;
		}

		public void setPindanstatus(Map<String, String> pindanstatus) {
			this.pindanstatus = pindanstatus;
		}

		public Map<String, String> getShopstatus() {
			return shopstatus;
		}

		public void setShopstatus(Map<String, String> shopstatus) {
			this.shopstatus = shopstatus;
		}

		public Map<String, String> getSalonstatus() {
			return salonstatus;
		}

		public void setSalonstatus(Map<String, String> salonstatus) {
			this.salonstatus = salonstatus;
		}

		public Map<String, String> getPaytype() {
			return paytype;
		}

		public void setPaytype(Map<String, String> paytype) {
			this.paytype = paytype;
		}

		public Map<String, String> getWinereqstatus() {
			return winereqstatus;
		}

		public void setWinereqstatus(Map<String, String> winereqstatus) {
			this.winereqstatus = winereqstatus;
		}

		public Map<String, String> getVerifystatus() {
			return verifystatus;
		}

		public void setVerifystatus(Map<String, String> verifystatus) {
			this.verifystatus = verifystatus;
		}

		public Map<String, String> getThirdpay() {
			return thirdpay;
		}

		public void setThirdpay(Map<String, String> thirdpay) {
			this.thirdpay = thirdpay;
		}

		public Map<String, String> getOrderstatus() {
			return orderstatus;
		}

		public void setOrderstatus(Map<String, String> orderstatus) {
			this.orderstatus = orderstatus;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
		public String toString() {
			return "BusinessInfor [winetype=" + winetype + ", countunit="
					+ countunit + ", pindanstatus=" + pindanstatus
					+ ", shopstatus=" + shopstatus + ", salonstatus="
					+ salonstatus + ", paytype=" + paytype + ", winereqstatus="
					+ winereqstatus + ", verifystatus=" + verifystatus
					+ ", thirdpay=" + thirdpay + ", orderstatus=" + orderstatus
					+ "]";
		}

	}
}
