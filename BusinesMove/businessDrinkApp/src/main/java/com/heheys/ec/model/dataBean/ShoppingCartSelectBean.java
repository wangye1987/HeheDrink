package com.heheys.ec.model.dataBean;

import java.io.Serializable;

import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder.shoppingbean;

/**
 * Describe:购物车商品选中数据结构
 * 
 * Date:2015-10-9
 * 
 * Author:liuzhouliang
 */
public class ShoppingCartSelectBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String aid;
	private String num;
	private String sid;//套餐是有用
	private String currentprice;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCurrentprice() {
		return currentprice;
	}

	public void setCurrentprice(String currentprice) {
		this.currentprice = currentprice;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
