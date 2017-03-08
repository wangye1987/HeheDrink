package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:酒水需求列表数据实体类
 *
 * Date:2015年11月24日下午3:08:43
 *
 * Author:LZL
 *
 */
public class DrinksDemandBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	// private List<DrinksDemandInfor> result;// 返回结果
	private ErrorBean error;// 返回失败结果
	private DrinksDemandData result;

	public DrinksDemandData getResult() {
		return result;
	}

	public void setResult(DrinksDemandData result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// public List<DrinksDemandInfor> getResult() {
	// return result;
	// }
	//
	// public void setResult(List<DrinksDemandInfor> result) {
	// this.result = result;
	// }

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DrinksDemandBean [status=" + status + ", error=" + error
				+ ", result=" + result + "]";
	}

	public static class DrinksDemandData implements Serializable {

		private static final long serialVersionUID = 1L;
		private List<DrinksDemandInfor> drinksDemandList;

		public List<DrinksDemandInfor> getDrinksDemandList() {
			return drinksDemandList;
		}

		public void setDrinksDemandList(List<DrinksDemandInfor> drinksDemandList) {
			this.drinksDemandList = drinksDemandList;
		}

		@Override
		public String toString() {
			return "DrinksDemandData [drinksDemandList=" + drinksDemandList
					+ "]";
		}

		public static class DrinksDemandInfor implements Serializable {

			private static final long serialVersionUID = 1L;
			private String drinksName;
			private String startTime;
			private String endTime;
			private String drinksNums;
			private float drinksPrice;
			// 1代表买,0代表卖
			private String tradeMark;
			private String createTime;
			
			//拓展的几个参数
			private String numsUnit;
			private String id;
			private String drinksTypeId;
			private String brandName;
			private String deliverGoodsAreaName;
			private String remark;
			private String deliverGoodsAreaIds;
			private String brandId;
			private String degree;
////			id":"酒水需求id"
//			" drinksTypeId":"酒水类别id" 比如白酒,洋酒
//			" brandName":"品牌名称"
//			" drinksName":"酒水名称"
//			"drinksNums":"酒水数量"
//			" numsUnit":"数量单位"
//			" drinksPrice ":"酒水价格"
//			" deliverGoodsAreaName ":"送货区域 城市"
//			" startTime ":"酒水需求起始时间"
//			" endTime ":"酒水需求结束时间"
//			"remark":"备注"

			public String getNumsUnit() {
				return numsUnit;
			}
			
			public void setNumsUnit(String numsUnit) {
				this.numsUnit = numsUnit;
			}
			public String getDegree() {
				return degree;
			}

			public void setDegree(String degree) {
				this.degree = degree;
			}

			public String getDrinksName() {
				return drinksName;
			}

			public String getDeliverGoodsAreaIds() {
				return deliverGoodsAreaIds;
			}

			public void setDeliverGoodsAreaIds(String deliverGoodsAreaIds) {
				this.deliverGoodsAreaIds = deliverGoodsAreaIds;
			}

			public String getBrandId() {
				return brandId;
			}

			public void setBrandId(String brandId) {
				this.brandId = brandId;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getDrinksTypeId() {
				return drinksTypeId;
			}

			public void setDrinksTypeId(String drinksTypeId) {
				this.drinksTypeId = drinksTypeId;
			}

			public String getBrandName() {
				return brandName;
			}

			public void setBrandName(String brandName) {
				this.brandName = brandName;
			}

			public String getDeliverGoodsAreaName() {
				return deliverGoodsAreaName;
			}

			public void setDeliverGoodsAreaName(String deliverGoodsAreaName) {
				this.deliverGoodsAreaName = deliverGoodsAreaName;
			}

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}

			public void setDrinksName(String drinksName) {
				this.drinksName = drinksName;
			}

			public String getStartTime() {
				return startTime;
			}

			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}

			public String getEndTime() {
				return endTime;
			}

			public void setEndTime(String endTime) {
				this.endTime = endTime;
			}

			public String getDrinksNums() {
				return drinksNums;
			}

			public void setDrinksNums(String drinksNums) {
				this.drinksNums = drinksNums;
			}

			public float getDrinksPrice() {
				return drinksPrice;
			}

			public void setDrinksPrice(float drinksPrice) {
				this.drinksPrice = drinksPrice;
			}


			public String getTradeMark() {
				return tradeMark;
			}

			public void setTradeMark(String tradeMark) {
				this.tradeMark = tradeMark;
			}

			public String getCreateTime() {
				return createTime;
			}

			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}

			public static long getSerialversionuid() {
				return serialVersionUID;
			}

			@Override
			public String toString() {
				return "DrinksDemandInfor [drinksName=" + drinksName
						+ ", startTime=" + startTime + ", endTime=" + endTime
						+ ", drinksNums=" + drinksNums + ", drinksPrice="
						+ drinksPrice + ", tradeMark=" + tradeMark + ", createTime="
						+ createTime + "]";
			}

		}
	}

}
