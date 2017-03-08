package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * @author wangkui
 * 2016.10.29 提交生成预览订单
 */
public class ShoppingSubmitCartSelectBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String aid;
	private String num;
	private String sid;//套餐是有用

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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
