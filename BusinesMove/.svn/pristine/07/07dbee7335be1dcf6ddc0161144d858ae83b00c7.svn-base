package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-28 下午6:22:02
 *  类说明 我的全部订单数据bean
 */
public class AllOrderBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ResultOrderBean result;//返回结果
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
	public ResultOrderBean getResult() {
		return result;
	}
	public void setResult(ResultOrderBean result) {
		this.result = result;
	}

	public class ResultOrderBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<OrderResult> list;

		public ArrayList<OrderResult> getList() {
			return list;
		}

		public void setList(ArrayList<OrderResult> list) {
			this.list = list;
		}
	}
	public class OrderResult implements Serializable{
/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//		"oid":""订单id
//		"suborder":[
//			{"id":"1", 商品sku 的id
//			"url":"数量"
//			},{"id":"1", 商品sku 的id
//			"url":"数量"
//			},]
//		"num":"" 数量
//		"status":  订单状态
//		"statusname":""  状态显示名称
//		"createtime":"2015-10-10 10:10:10"
//		“type”:”0”,//0拼单 1 批发
		private String oid;
		private String num;
		private String status;
		private String statusname;
		private String createtime;
		private String type;
		private String ordertype;//是否显示订单号 2不显示
		private String amount;
		private ArrayList<SubBean> suborder;
		private String goodsname;
		private String goodsdesc;
		private String paytype;
		private double couponamount;
		private int needbill;//0不开发票
		private double coinamount;//验证是否使用过喝喝币
		private int settlement_type;
//		0:在线支付订单
//		1:货到付款订单[货到付款订单没有确认收货按钮]
//		注:货到付款要根据这个字段判断 不要根据支付类型判断
		public int getSettlement_type() {
			return settlement_type;
		}
		public void setSettlement_type(int settlement_type) {
			this.settlement_type = settlement_type;
		}
		public double getCouponamount() {
			return couponamount;
		}
		public void setCouponamount(double couponamount) {
			this.couponamount = couponamount;
		}
		public int getNeedbill() {
			return needbill;
		}
		public void setNeedbill(int needbill) {
			this.needbill = needbill;
		}
		public double getCoinamount() {
			return coinamount;
		}
		public void setCoinamount(double coinamount) {
			this.coinamount = coinamount;
		}
		public String getOrdertype() {
			return ordertype;
		}
		public void setOrdertype(String ordertype) {
			this.ordertype = ordertype;
		}
		public String getPaytype() {
			return paytype;
		}
		public void setPaytype(String paytype) {
			this.paytype = paytype;
		}
		public String getGoodsname() {
			return goodsname;
		}
		public void setGoodsname(String goodsname) {
			this.goodsname = goodsname;
		}
		public String getGoodsdesc() {
			return goodsdesc;
		}
		public void setGoodsdesc(String goodsdesc) {
			this.goodsdesc = goodsdesc;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getStatusname() {
			return statusname;
		}
		public void setStatusname(String statusname) {
			this.statusname = statusname;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public ArrayList<SubBean> getSuborder() {
			return suborder;
		}
		public void setSuborder(ArrayList<SubBean> suborder) {
			this.suborder = suborder;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
		

	}
	public class SubBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private String url;
		private String tag;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		
	}
}
