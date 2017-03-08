package com.heheys.ec.model.dataBean; 

import java.io.Serializable;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-16 下午1:12:25 
 * 类说明 
 * @param 订单详情
 */
public class OrderBaseDetailBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private OrderinfoBean result;//返回结果
	private ErrorBean error;//返回失败结果
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OrderinfoBean getResult() {
		return result;
	}
	public void setResult(OrderinfoBean result) {
		this.result = result;
	}
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
//	"id":"1"  子订单id
//	"oid":""  订单id
//	"name":""  名称
//	"num":""  数量
//	"pic":""   图片
//	"factory":"", 厂家
//	"amount ":"" 金额
//	"transamount ":""  运费
//	"deamount":""  定金支付金额
//	"tamount":""  尾款支付金额
//	"status":""
//	address:{
//	"contact ":"",姓名
//	"mobile":"", 手机
//	"address":"" 地址
//	}
	
	public static class AddressBean implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String contact;
		private String mobile;
		private String address;
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	}
	public static class  OrderinfoBean implements Serializable{
		/**
		 * "
		 */
		private static final long serialVersionUID = 1L;
		//子订单id
		private int id;
		//订单id
		private int oid;
		//名称
		private String name;
		//数量
		private String num;
		//图片
		private String pic;
		//厂家
		private String factory;
		//金额
		private String amount;
		//运费
		private String transamount;
		//定金支付金额
		private String deamount;
		//尾款支付金额
		private String tamount;
		//订单总金额
		private String orderamount;
		//已支付金额
		private String haspayamount;
		//状态
		private int status;
		//状态
		private String statusname;
		//单位
		private String unit;
		//已支付金额
		private String price;
		//订金比例
		private String rate;
		//当前价格
		private String currentprice;
		
		private AddressBean address;
		
		public String getStatusname() {
			return statusname;
		}
		public void setStatusname(String statusname) {
			this.statusname = statusname;
		}
		public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			this.rate = rate;
		}
		public String getOrderamount() {
			return orderamount;
		}
		public void setOrderamount(String orderamount) {
			this.orderamount = orderamount;
		}
		public String getHaspayamount() {
			return haspayamount;
		}
		public void setHaspayamount(String haspayamount) {
			this.haspayamount = haspayamount;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getCurrentprice() {
			return currentprice;
		}
		public void setCurrentprice(String currentprice) {
			this.currentprice = currentprice;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getOid() {
			return oid;
		}
		public void setOid(int oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getFactory() {
			return factory;
		}
		public void setFactory(String factory) {
			this.factory = factory;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getTransamount() {
			return transamount;
		}
		public void setTransamount(String transamount) {
			this.transamount = transamount;
		}
		public String getDeamount() {
			return deamount;
		}
		public void setDeamount(String deamount) {
			this.deamount = deamount;
		}
		public String getTamount() {
			return tamount;
		}
		public void setTamount(String tamount) {
			this.tamount = tamount;
		}
		public AddressBean getAddress() {
			return address;
		}
		public void setAddress(AddressBean address) {
			this.address = address;
		}
	}
	
}
 