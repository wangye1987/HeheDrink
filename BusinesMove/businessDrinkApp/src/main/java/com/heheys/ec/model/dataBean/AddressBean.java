package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-23 下午4:11:43 
 * 类说明  地址管理bean
 * @param
 */
public class AddressBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AddressListBean> list;
	
	public List<AddressListBean> getList() {
		return list;
	}

	public void setList(List<AddressListBean> list) {
		this.list = list;
	}

	
}
 