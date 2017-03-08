package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;

import com.heheys.ec.model.dataBean.HehePayBaseBean.HehePay;

/**
 * @author wangkui
 * 我的喝喝币充值卡界面
 */
public class MyhhMoneyBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private HeheCardAccount result;//返回结果
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
	public HeheCardAccount getResult() {
		return result;
	}
	public void setResult(HeheCardAccount result) {
		this.result = result;
	}

	public class HeheCardAccount implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private ArrayList<RechargeCard> list;
		private float price;
		public ArrayList<RechargeCard> getList() {
			return list;
		}
		public void setList(ArrayList<RechargeCard> list) {
			this.list = list;
		}
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
		
		
	}
	
	public class RechargeCard implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private String coin;
		private String price;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCoin() {
			return coin;
		}
		public void setCoin(String coin) {
			this.coin = coin;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
	}
}
