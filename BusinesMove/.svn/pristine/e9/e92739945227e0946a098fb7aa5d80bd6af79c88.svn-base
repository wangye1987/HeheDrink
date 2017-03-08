package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

import com.heheys.ec.model.dataBean.DrinksDemandBean.DrinksDemandData;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.BrandList;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-25 下午5:26:17 
 * 类说明 
 * @param 
 */
public class BrandWineBaseBean implements Serializable{

//	"id":"2",
//	"name":"茅台"
//	"hotBrand":"热门皮牌 0不是热门 1热门"

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	// private List<DrinksDemandInfor> result;// 返回结果
	private ErrorBean error;// 返回失败结果
	private BrandListBean result;

	public BrandListBean getResult() {
		return result;
	}

	public void setResult(BrandListBean result) {
		this.result = result;
	}

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
	public static class BrandListBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<BrandList> list;

		public List<BrandList> getList() {
			return list;
		}

		public void setList(List<BrandList> list) {
			this.list = list;
		}
		
	}

	/**
	 * 
	 */
//	public static class BrandWine implements Serializable{
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		
//		private String id;
//		private String name;
//		private String hotBrand;
//		public String getId() {
//			return id;
//		}
//		public void setId(String id) {
//			this.id = id;
//		}
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public String getHotBrand() {
//			return hotBrand;
//		}
//		public void setHotBrand(String hotBrand) {
//			this.hotBrand = hotBrand;
//		}
//	}
	
}
 