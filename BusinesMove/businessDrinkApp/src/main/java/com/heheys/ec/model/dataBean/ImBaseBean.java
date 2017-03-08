/**
 * 
 */
package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.heheys.ec.model.dataBean.ShareBaseBean.ShareResultBean;

/**
 * @author wangkui
 * 2016-4-28下午6:42:57
 * @version 1.0
 */
public class ImBaseBean implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private ImResultBean result;//返回结果
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
	public ImResultBean getResult() {
		return result;
	}
	public void setResult(ImResultBean result) {
		this.result = result;
	}

	
	public class ImResultBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ArrayList<ImHeHe> list;
		public ArrayList<ImHeHe> getList() {
			return list;
		}
		public void setList(ArrayList<ImHeHe> list) {
			this.list = list;
		}
	}
	
	public class ImHeHe implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		//客服组别名字
		private String name;
		//客服组别id
		private int groupid;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getGroupid() {
			return groupid;
		}
		public void setGroupid(int groupid) {
			this.groupid = groupid;
		}
		
	}
}
