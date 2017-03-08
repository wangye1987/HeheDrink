package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * Describe:缓存删除购物车商品信息实体类
 * 
 * Date:2015-10-15
 * 
 * Author:liuzhouliang
 */
public class ShoppingCartProductDeleteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String aid;
	private String id;


	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	@Override
//	public String toString() {
//		return "ShoppingCartProductDeleteBean [iid=" + iid + ", id=" + id + "]";
//	}

}
