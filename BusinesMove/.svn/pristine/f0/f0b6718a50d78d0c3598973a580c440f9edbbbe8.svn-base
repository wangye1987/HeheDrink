package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-28 下午2:07:05
 *  类说明
 */
public class BaseRecordBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private StatueBean result;//返回结果
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
	public StatueBean getResult() {
		return result;
	}
	public void setResult(StatueBean result) {
		this.result = result;
	}
	
	public class StatueBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<MyRecordBean> list;
//		uid：用户
//		 amount:1200 金额
//		 ctime:”2016-03-16” 申请时间
//		 status:提现状态：-1发放
		public List<MyRecordBean> getList() {
			return list;
		}
		public void setList(List<MyRecordBean> list) {
			this.list = list;
		}
		
	}
	
	public class MyRecordBean implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String uid;
		private String amount;
		private String ctime;
		private int status;
		private String remark;//提现理由
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getCtime() {
			return ctime;
		}
		public void setCtime(String ctime) {
			this.ctime = ctime;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	}

}
