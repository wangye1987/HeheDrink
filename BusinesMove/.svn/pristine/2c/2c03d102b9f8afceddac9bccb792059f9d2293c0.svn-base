package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-17 下午5:36:10
 *  类说明
 */
public class BrandBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private BrandList result;//返回结果
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
	public BrandList getResult() {
		return result;
	}
	public void setResult(BrandList result) {
		this.result = result;
	}
//	{
//		"list":[
//		{
//		“id”:”1”编号
//		“name”:”品类名称”
//		}
//		count:10
//		]}
	public static class BrandList implements Serializable{
		private static final long serialVersionUID = 1L;
		private List<BrandBean> list;
		private int count;
		public List<BrandBean> getList() {
			return list;
		}
		public void setList(List<BrandBean> list) {
			this.list = list;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		} 
	}
	
	public static class BrandBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private String name;
		private boolean isCheck;
		
		public boolean isCheck() {
			return isCheck;
		}
		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
