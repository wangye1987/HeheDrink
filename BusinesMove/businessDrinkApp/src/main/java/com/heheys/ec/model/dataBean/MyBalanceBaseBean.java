package com.heheys.ec.model.dataBean;

import java.io.Serializable;

import com.heheys.ec.model.dataBean.BasebeanSign.ResultSignBean;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-25 下午1:53:53
 *  类说明 我的余额bean
 */
public class MyBalanceBaseBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ResultBanlanceBean result;//返回结果
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
	public ResultBanlanceBean getResult() {
		return result;
	}
	public void setResult(ResultBanlanceBean result) {
		this.result = result;
	}
	
	public class ResultBanlanceBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
//		balance:10012.50 余额
//		   “Withdraw”//最小提现额度
		private String balance;
		private String withdraw;
		private String coupon;//可用优惠券张数
		private String score;//可用积分
		private String coin;
		
		public String getCoin() {
			return coin;
		}
		public void setCoin(String coin) {
			this.coin = coin;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
		public String getCoupon() {
			return coupon;
		}
		public void setCoupon(String coupon) {
			this.coupon = coupon;
		}
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getWithdraw() {
			return withdraw;
		}
		public void setWithdraw(String withdraw) {
			this.withdraw = withdraw;
		}
		
	}
}
