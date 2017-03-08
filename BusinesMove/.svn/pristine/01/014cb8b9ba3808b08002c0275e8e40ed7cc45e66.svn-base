package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author wangkui
 *  喝喝币bean
 */
public class HeheMoneyBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private HeheBean result;//返回结果
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
	public HeheBean getResult() {
		return result;
	}
	public void setResult(HeheBean result) {
		this.result = result;
	}
	
	public class HeheBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<HeheMoneyitem> list;
		public ArrayList<HeheMoneyitem> getList() {
			return list;
		}
		public void setList(ArrayList<HeheMoneyitem> list) {
			this.list = list;
		}
		
	}
	
	
	public class HeheMoneyitem implements Serializable{
		/**
		 * result.count	明细数量	10
result.list[i].coin	喝喝币发生金额	0.01
result.list[i].createTime	发生时间	2016-10-10 00:00:00
result.list[i].coinAction	动作类型	1,2为订单号可跳转
result.list[i].action	动作描述	充值/消费/取消订单返还
result.list[i].showInfo	用户显示信息	TODXXXX/10.0元
		 */
		private static final long serialVersionUID = 1L;
		private String coin;
		private String showInfo;
		private String createTime;
		private String action;
		private int coinAction;
		public String getCoin() {
			return coin;
		}
		public void setCoin(String coin) {
			this.coin = coin;
		}
		public String getShowInfo() {
			return showInfo;
		}
		public void setShowInfo(String showInfo) {
			this.showInfo = showInfo;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public int getCoinAction() {
			return coinAction;
		}
		public void setCoinAction(int coinAction) {
			this.coinAction = coinAction;
		}
		
	}
}
