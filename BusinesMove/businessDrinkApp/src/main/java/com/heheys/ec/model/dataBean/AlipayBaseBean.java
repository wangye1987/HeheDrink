package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间：2016-4-6 下午4:39:43
 *  类说明
 */
public class AlipayBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private Status result;//返回结果
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
	public Status getResult() {
		return result;
	}
	public void setResult(Status result) {
		this.result = result;
	}
	
	
	public static class Status implements Serializable{
		private static final long serialVersionUID = 1L;
		private String status;
		private Order order;
		
		public Order getOrder() {
			return order;
		}
		public void setOrder(Order order) {
			this.order = order;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		}
	
	public static class Order implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<OrderItemList> list;
//		private String madescore;//产生积分数量
		private String freight;//运费
//		private String totalamount;//整张单价格
//		private String orderid;//整张单单号
		private String madescore;
		private String paytype;
		private String orderid;

		public String getPaytype() {
			return paytype;
		}

		public void setList(List<OrderItemList> list) {
			this.list = list;
		}

		public void setPaytype(String paytype) {
			this.paytype = paytype;
		}

		public List<OrderItemList> getList() {
			return list;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public String getMadescore() {
			return madescore;
		}

		public void setMadescore(String madescore) {
			this.madescore = madescore;
		}

		public String getFreight() {
			return freight;
		}

		public void setFreight(String freight) {
			this.freight = freight;
		}

//		public String getPaytype() {
//			return paytype;
//		}
//
//		public void setPaytype(String paytype) {
//			this.paytype = paytype;
//		}
//
//		public String getMadescore() {
//			return madescore;
//		}
//
//		public void setMadescore(String madescore) {
//			this.madescore = madescore;
//		}
//
//		public String getTotalamount() {
//			return totalamount;
//		}
//
//		public void setTotalamount(String totalamount) {
//			this.totalamount = totalamount;
//		}
//
//		public String getOrderid() {
//			return orderid;
//		}
//
//		public void setOrderid(String orderid) {
//			this.orderid = orderid;
//		}

//		public List<OrderItemList> getList() {
//			return list;
//		}

//		public void setList(List<OrderItemList> list) {
//			this.list = list;
//		}
		
	}
	public static class OrderItemList implements Serializable {
		// "damount": "0",//每箱定金金额
		// "currentprice": 220,//当前价格
		// "pid": "1",//商品id
		// “unit”:"箱" //单位
		// "pic": "http://192.168.0.147//images/img2.jpg",
		// "aid": 1,//活动ID
		// "type": 0,//活动类型 0拼单 1批发
		// "amount": "220",//拼单定金支付金额
		// "num": "1",
		// "name": "aa",//商品名称
		// “itemtotal”:"10000"// 批发每栏商品的价格

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
//		private String damount;
//		private String currentprice;
//		private String pid;
//		private String unit;
//		private String pic;
//		private String aid;
//		private String type;
//		private String amount;//每列的合计金额
//		private String num;
//		private String name;

		private String payMoney;//实付金额
		private String orderNum;//订单号
        private List<Products> products;

		public List<Products> getProducts() {
			return products;
		}

		public void setProducts(List<Products> products) {
			this.products = products;
		}

		public String getPayMoney() {
			return payMoney;
		}

		public void setPayMoney(String payMoney) {
			this.payMoney = payMoney;
		}

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

//		public String getDamount() {
//			return damount;
//		}
//
//		public void setDamount(String damount) {
//			this.damount = damount;
//		}
//
//		public String getCurrentprice() {
//			return currentprice;
//		}
//
//		public void setCurrentprice(String currentprice) {
//			this.currentprice = currentprice;
//		}
//
//		public String getPid() {
//			return pid;
//		}
//
//		public void setPid(String pid) {
//			this.pid = pid;
//		}
//
//		public String getUnit() {
//			return unit;
//		}
//
//		public void setUnit(String unit) {
//			this.unit = unit;
//		}
//
//		public String getPic() {
//			return pic;
//		}
//
//		public void setPic(String pic) {
//			this.pic = pic;
//		}
//
//		public String getAid() {
//			return aid;
//		}
//
//		public void setAid(String aid) {
//			this.aid = aid;
//		}
//
//		public String getType() {
//			return type;
//		}
//
//		public void setType(String type) {
//			this.type = type;
//		}
//
//		public String getAmount() {
//			return amount;
//		}
//
//		public void setAmount(String amount) {
//			this.amount = amount;
//		}
//
//		public String getNum() {
//			return num;
//		}
//
//		public void setNum(String num) {
//			this.num = num;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}

	}
	public class Products implements  Serializable{
		String title;
		String num;
		String unit;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}
	}
}
