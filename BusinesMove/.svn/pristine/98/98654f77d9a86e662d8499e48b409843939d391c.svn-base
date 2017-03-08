package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

import com.heheys.ec.model.dataBean.SalonListBean.SalonResultBean.SalonInfor;

public class DrinkInfoBaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private DrinkResultBean result;// 返回结果
	private ErrorBean error;// 返回失败结果
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public DrinkResultBean getResult() {
		return result;
	}


	public void setResult(DrinkResultBean result) {
		this.result = result;
	}


	public ErrorBean getError() {
		return error;
	}


	public void setError(ErrorBean error) {
		this.error = error;
	}


	public static class DrinkResultBean implements Serializable{
		private static final long serialVersionUID = 1L;
		private int count;
		private List<DrinkInfoBean> list;
		private String baseurl;
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public List<DrinkInfoBean> getList() {
			return list;
		}
		public void setList(List<DrinkInfoBean> list) {
			this.list = list;
		}
		public String getBaseurl() {
			return baseurl;
		}
		public void setBaseurl(String baseurl) {
			this.baseurl = baseurl;
		}
		
	}
}
