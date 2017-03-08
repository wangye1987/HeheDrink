package com.heheys.ec.model.dataBean;

import java.io.Serializable;


/**
 * @author wangkui
 *
 * 取消消息bean
 */
public class CancleMsgBean implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private CancleResult result;//返回结果
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
	public CancleResult getResult() {
		return result;
	}
	public void setResult(CancleResult result) {
		this.result = result;
	}

  public class CancleResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msg;
	private String ordernum;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
  }
}
