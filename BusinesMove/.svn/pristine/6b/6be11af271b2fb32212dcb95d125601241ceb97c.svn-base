package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;


/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-11-2 下午6:41:38 
 * 类说明 
 * @param 消息列表bean
 */
public class PushBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private PushListBean result;//返回结果
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
	public PushListBean getResult() {
		return result;
	}
	public void setResult(PushListBean result) {
		this.result = result;
	}
	
	

	public static class PushListBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String unread;
		private List<MsgBean> list;
		
		public String getUnread() {
			return unread;
		}
		public void setUnread(String unread) {
			this.unread = unread;
		}
		public List<MsgBean> getList() {
			return list;
		}
		public void setList(List<MsgBean> list) {
			this.list = list;
		}
	}
	
	
	public static class MsgBean implements Serializable{
//		{
//		"list":
//		[
//		"type":0   消息类型 0 系统 1 通知
//		"unread":10  未读数量
//		"lastmsg":"ssss" 最新消息内容
//		"time":  ""  发送时间
//		]
//		"unread":10  未读数量
//		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int type;
		private int unread;
		private String lastmsg;
		private String time;
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public int getUnread() {
			return unread;
		}
		public void setUnread(int unread) {
			this.unread = unread;
		}
		public String getLastmsg() {
			return lastmsg;
		}
		public void setLastmsg(String lastmsg) {
			this.lastmsg = lastmsg;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		
		
		
	}
}
 