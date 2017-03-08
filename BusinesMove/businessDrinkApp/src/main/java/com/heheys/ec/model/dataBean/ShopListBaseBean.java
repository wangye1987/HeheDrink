package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;


/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-31 下午5:49:13
 *  类说明  商品列表bean
 */
public class ShopListBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ShopListResult result;//返回结果
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
	public ShopListResult getResult() {
		return result;
	}
	public void setResult(ShopListResult result) {
		this.result = result;
	}
	
	public class ShopListResult implements Serializable{
/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//		icon :”/shop/abc.jpg” 店铺logo
//		name:”喝喝旗舰店” 店铺名称
//		starnum:4.5 多少星级
//		winetype:0 主打酒类ID
//		desc: “喝喝旗舰店” 店铺描述
//		owner: 10 所属Uesr id
//		“goodsnum”://商品量
		private List<ShopResult> list;

		public List<ShopResult> getList() {
			return list;
		}

		public void setList(List<ShopResult> list) {
			this.list = list;
		}
		
	}
	public  class ShopResult implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String icon;
		private String name;
		private String starnum;
		private String desc;
		private String pronum;
		private String shopid;
		private String owner;
		public String getOwner() {
			return owner;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
		public String getPronum() {
			return pronum;
		}
		public void setPronum(String pronum) {
			this.pronum = pronum;
		}
		public String getShopid() {
			return shopid;
		}
		public void setShopid(String shopid) {
			this.shopid = shopid;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStarnum() {
			return starnum;
		}
		public void setStarnum(String starnum) {
			this.starnum = starnum;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
