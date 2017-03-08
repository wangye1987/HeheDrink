package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-16 上午10:49:42 
 * 类说明 
 * @param 名片管理基础数据bean
 */
public class BusinessCardBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private BusinessListCardBean result;//返回结果
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
	public BusinessListCardBean getResult() {
		return result;
	}
	public void setResult(BusinessListCardBean result) {
		this.result = result;
	}

	
	public static class BusinessListCardBean  implements Serializable{
		/**
		 * 
		 */
//		{"result":{"count":2,"list":[{"id":1,"position":"111","landline":"","weixin":"test","remark":"","address":"1111","company":"11","name":"1","mobile":"18500567890"},
//		                             {"id":4,"position":"测试","landline":"01098988777, 01023333334","weixin":"767667, 788888","remark":"","address":"北京","company":"北京","name":"王凯","mobile":"13577777765, 13888888888"}]},
//		"error":{},"status":1}
		private static final long serialVersionUID = 1L;
		private List<BusinessCardBean> list;

		public List<BusinessCardBean> getList() {
			return list;
		}

		public void setList(List<BusinessCardBean> list) {
			this.list = list;
		}
	}
	public static class BusinessCardBean  implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * "id":1,"position":"111","landline":"","weixin":"test","remark":"","address":"1111","company":"11","name":"1","mobile":"18500567890"
		 */
		//id
		private String id; 
		//姓名
		private String name; 
		//职位
		private String position; 
		//公司名称
		private String company; 
		//手机号
		private String mobile; 
		//座机
		private String landline; 
		//微信号
		private String weixin; 
		//微信号
		private String address; 
		//备注
		private String remark;
		//时间戳
		private String time;
		//名片状态
		private String status;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		//存储名字首字母大小写
//		private AddressModel model;
//		public AddressModel getModel() {
//			return model;
//		}
//		public void setModel(AddressModel model) {
//			this.model = model;
//		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getLandline() {
			return landline;
		}
		public void setLandline(String landline) {
			this.landline = landline;
		}
		public String getWeixin() {
			return weixin;
		}
		public void setWeixin(String weixin) {
			this.weixin = weixin;
		} 
		
	}
}
 