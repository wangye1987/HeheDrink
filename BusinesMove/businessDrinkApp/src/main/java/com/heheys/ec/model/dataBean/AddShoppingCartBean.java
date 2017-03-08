package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * Describe:添加购物车实体类
 * 
 * Date:2015-10-13
 * 
 * Author:liuzhouliang
 */
public class AddShoppingCartBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private ErrorBean error;// 返回失败结果
	

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

	@Override
	public String toString() {
		return "AddShopCartBean [status=" + status + ", error=" + error + "]";
	}
}
