package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-4-5 下午2:35:55
 *  类说明  店铺详情bean
 */
public class ShopDetaileBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ShopAdverBean result;//返回结果
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
	public ShopAdverBean getResult() {
		return result;
	}
	public void setResult(ShopAdverBean result) {
		this.result = result;
	}
//
//	Id:’1’
//	name:’adds’
//logo:”a.png”
//authen:”1” 0未认证 1已认证
//pics[“a.png”,”b.jpg”]
	public class ShopAdverBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String id;
		private String name;
		private String logo;
		private String authen;
		private List<String> pics;
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
		public String getLogo() {
			return logo;
		}
		public void setLogo(String logo) {
			this.logo = logo;
		}
		public String getAuthen() {
			return authen;
		}
		public void setAuthen(String authen) {
			this.authen = authen;
		}
		public List<String> getPics() {
			return pics;
		}
		public void setPics(List<String> pics) {
			this.pics = pics;
		}
	}
}
