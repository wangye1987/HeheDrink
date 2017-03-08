package com.heheys.ec.model.dataBean;

import java.io.Serializable;

import com.heheys.ec.model.dataBean.DrinksDemandBean.DrinksDemandData;

/**
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-12-10 下午3:31:41 类说明
 * @param
 */
public class DemandDetailBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	// private List<DrinksDemandInfor> result;// 返回结果
	private ErrorBean error;// 返回失败结果
	private DrinksDemandDetail result;

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	public DrinksDemandDetail getResult() {
		return result;
	}

	public void setResult(DrinksDemandDetail result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static class DrinksDemandDetail implements Serializable {
/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//		"startTime": "2015年12月10日",
//        "drinksTypeId": 2,
//        "id": 171,
//        "drinksStatus": 0,
//        "degree": 58,
//        "drinksTypeName": "葡萄酒",
//        "drinksNums": 88,
//        "remark": "fsdfsdf",
//        "drinksName": "延后",
//        "endTime": "2015年12月11日",
//        "brandName": "玛蒂娜",
//        "numsUnit": "箱",
//        "drinksPrice": 88
		private String startTime;
		private String drinksTypeId;
		private String id;
		private String drinksStatus;
		private String degree;
		private String drinksTypeName;
		private String drinksNums;
		private String remark;
		private String drinksName;
		private String endTime;
		private String brandName;
		private String numsUnit;
		private String drinksPrice;
		private String brandId;
		private String deliverGoodsAreaIds;
		private String deliverGoodsAreaIdsArray;
		private String deliverGoodsAreaName;
		
		public String getBrandId() {
			return brandId;
		}
		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}
		public String getDeliverGoodsAreaName() {
			return deliverGoodsAreaName;
		}
		public void setDeliverGoodsAreaName(String deliverGoodsAreaName) {
			this.deliverGoodsAreaName = deliverGoodsAreaName;
		}
		public String getDeliverGoodsAreaIds() {
			return deliverGoodsAreaIds;
		}
		public void setDeliverGoodsAreaIds(String deliverGoodsAreaIds) {
			this.deliverGoodsAreaIds = deliverGoodsAreaIds;
		}
		public String getDeliverGoodsAreaIdsArray() {
			return deliverGoodsAreaIdsArray;
		}
		public void setDeliverGoodsAreaIdsArray(String deliverGoodsAreaIdsArray) {
			this.deliverGoodsAreaIdsArray = deliverGoodsAreaIdsArray;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getDrinksTypeId() {
			return drinksTypeId;
		}
		public void setDrinksTypeId(String drinksTypeId) {
			this.drinksTypeId = drinksTypeId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDrinksStatus() {
			return drinksStatus;
		}
		public void setDrinksStatus(String drinksStatus) {
			this.drinksStatus = drinksStatus;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		public String getDrinksTypeName() {
			return drinksTypeName;
		}
		public void setDrinksTypeName(String drinksTypeName) {
			this.drinksTypeName = drinksTypeName;
		}
		public String getDrinksNums() {
			return drinksNums;
		}
		public void setDrinksNums(String drinksNums) {
			this.drinksNums = drinksNums;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getDrinksName() {
			return drinksName;
		}
		public void setDrinksName(String drinksName) {
			this.drinksName = drinksName;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getBrandName() {
			return brandName;
		}
		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}
		public String getNumsUnit() {
			return numsUnit;
		}
		public void setNumsUnit(String numsUnit) {
			this.numsUnit = numsUnit;
		}
		public String getDrinksPrice() {
			return drinksPrice;
		}
		public void setDrinksPrice(String drinksPrice) {
			this.drinksPrice = drinksPrice;
		}
		
	}
}
