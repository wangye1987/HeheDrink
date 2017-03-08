package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-16 下午3:53:53 
 * 类说明 
 * @param 添加个人信息
 */
public class AddbusCardBean implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//"id":名片id
	private int id;
//"status":名片审核状态
	private String status;
//	"name":"姓名",
	private String name;
//	"mobile":"手机号",String[]
	private String mobile;
//	"landline":"座机号",String[]
	private String landline;
//	"weixin":"微信号",String[]
	private String weixin;
//	private List<String> mobile;
////	"landline":"座机号",String[]
//	private List<String> landline;
////	"weixin":"微信号",String[]
//	private List<String> weixin;
//	"company":"公司名称"
	private String company;
//	"position":"担当职位"
	private String position;
//	“address”:”公司地址” 
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
 