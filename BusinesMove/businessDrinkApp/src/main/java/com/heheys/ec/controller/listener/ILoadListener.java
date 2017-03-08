package com.heheys.ec.controller.listener;

/**
 * 
 * Describe:加载数据监听接口
 * 
 * Date:2015-9-29
 * 
 * Author:liuzhouliang
 */
public interface ILoadListener<T> {
	/**
	 * 加载数据成功回调方法 绑定数据
	 * 
	 * @param data
	 *            加载数据成功回调数据实体对象
	 */
	public void dataCallBack(T data);
}
