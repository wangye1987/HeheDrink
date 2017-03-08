package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail:wk
 * @version 创建时间：2015-10-16 上午9:44:26 
 * 类说明 
 * @param 订单列表bean
 */
public class OrderBaseBean implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private OrderList result;//返回结果
	private ErrorBean error;//返回失败结果
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public OrderList getResult() {
		return result;
	}
	public void setResult(OrderList result) {
		this.result = result;
	}
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public static class OrderList implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<OrderListBean> list;

		public List<OrderListBean> getList() {
			return list;
		}

		public void setList(List<OrderListBean> list) {
			this.list = list;
		}
		
	}
	public static class OrderListBean implements Serializable{
	private static final long serialVersionUID = 1L;
//	"list":[{"createtime":"2015-10-21 09:20:37","id":115,"unit":"瓶","num":100,"statusname":"待付款","status":1,
//		"iid":22,"oid":102,"name":"亲民五粮液，经典水晶瓶",
//		"pic":"http://101.200.235.7:8080/img/img/b01599e5-f402-4358-8701-21b3a4e6bcbb.jpg"},
	private int oid;
	private int id;//子订单id
	private int iid;//订单id
	private String name;//酒名字
	private String pic;//酒图片
	private int num;//酒数量
	private int status;//订单状态
	private String createtime;//创建时间
	private String unit;//单位
	private String statusname;//待付款提示
	private String shopname;//厂商
	
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatetime() {
		return createtime;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	}
}
 