package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2015-10-28 上午10:35:59 
 * 类说明 
 * @param 推送消息bean 三种数据类型都写在一起
 */
public class PushBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private PushInfoBean result;//返回结果
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
	public PushInfoBean getResult() {
		return result;
	}
	public void setResult(PushInfoBean result) {
		this.result = result;
	}
	
	public static class PushInfoBean implements Serializable{
		/**
		 * {"result":{"content":"\\(^o^)\\/~,您有一个新的优惠券!","title":"喝喝云商","biz_id":"1","biztype":"8"},"error":{},"status":1}
		 */
		private static final long serialVersionUID = 1L;
		//消息id
		private String biz_id;
		//消息type
		private String biztype;
		//当前登录用户id
		private String uid;
		//通知栏标题
		private String title;
		//通知栏内容
		private String content;
		//通知栏消息级别
		private String level;
		
		private Msgtips msgtips;
		
		public Msgtips getMsgtips() {
			return msgtips;
		}
		public void setMsgtips(Msgtips msgtips) {
			this.msgtips = msgtips;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getBiz_id() {
			return biz_id;
		}
		public void setBiz_id(String biz_id) {
			this.biz_id = biz_id;
		}
		public String getBiztype() {
			return biztype;
		}
		public void setBiztype(String biztype) {
			this.biztype = biztype;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
	}
	
	public class Msgtips implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String content;

		private String title;

		private boolean success;

		private List<Msglist> msglist ;

		public void setContent(String content){
		this.content = content;
		}
		public String getContent(){
		return this.content;
		}
		public void setTitle(String title){
		this.title = title;
		}
		public String getTitle(){
		return this.title;
		}
		public void setSuccess(boolean success){
		this.success = success;
		}
		public boolean getSuccess(){
		return this.success;
		}
		public void setMsglist(List<Msglist> msglist){
		this.msglist = msglist;
		}
		public List<Msglist> getMsglist(){
		return this.msglist;
		}

		}
	public static class Msglist implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String msg;

		private boolean success;

		public void setMsg(String msg){
		this.msg = msg;
		}
		public String getMsg(){
		return this.msg;
		}
		public void setSuccess(boolean success){
		this.success = success;
		}
		public boolean getSuccess(){
		return this.success;
		}
	}

}
 