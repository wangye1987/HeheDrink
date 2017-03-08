package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-25 下午2:30:58
 *  类说明
 */
public class BaseCardBean  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ResultCardBean result;//返回结果
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
	public ResultCardBean getResult() {
		return result;
	}
	public void setResult(ResultCardBean result) {
		this.result = result;
	}
	
	public class ResultCardBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
//		account:”6224 2556 2254 5555” 卡号
//		accountbank:”开户行”	所属银行
//		accountname:”张” 卡号所有者
//		{"result":{"accountnum":"66666688888888","accountbank":"中信银行","accountname":"zuo"},"error":{},"status":1}
		private String accountnum;
		private String accountbank;
		private String accountname;
		public String getAccountnum() {
			return accountnum;
		}
		public void setAccountnum(String accountnum) {
			this.accountnum = accountnum;
		}
		public String getAccountbank() {
			return accountbank;
		}
		public void setAccountbank(String accountbank) {
			this.accountbank = accountbank;
		}
		public String getAccountname() {
			return accountname;
		}
		public void setAccountname(String accountname) {
			this.accountname = accountname;
		}
		
	}
}
