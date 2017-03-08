package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;

public class MoreSuitBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private MoreSuitListBean result;// 返回结果
	private ErrorBean error;// 返回失败结果

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

	public MoreSuitListBean getResult() {
		return result;
	}

	public void setResult(MoreSuitListBean result) {
		this.result = result;
	}

	/**
	 * @param 更多套装
	 */

	public class MoreSuitListBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<SuitList> suit;
		public ArrayList<SuitList> getSuit() {
			return suit;
		}
		public void setSuit(ArrayList<SuitList> suit) {
			this.suit = suit;
		}

	}

	public static class SuitList implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String suitId;// 套装ID
		private String suitTile;// 套装名称
		private String suitPrice;// 套装价格
		private String suitSave;// 套装节省价格
		private ArrayList<SuitInfoItem> listItem;// 优惠套装明细

		public String getSuitId() {
			return suitId;
		}

		public void setSuitId(String suitId) {
			this.suitId = suitId;
		}


		public String getSuitTile() {
			return suitTile;
		}

		public void setSuitTile(String suitTile) {
			this.suitTile = suitTile;
		}

		public String getSuitPrice() {
			return suitPrice;
		}

		public void setSuitPrice(String suitPrice) {
			this.suitPrice = suitPrice;
		}

		public String getSuitSave() {
			return suitSave;
		}

		public void setSuitSave(String suitSave) {
			this.suitSave = suitSave;
		}

//		public ArrayList<SuitSrc> getSuitSrc() {
//			return suitSrc;
//		}
//
//		public void setSuitSrc(ArrayList<SuitSrc> suitSrc) {
//			this.suitSrc = suitSrc;
//		}

		public ArrayList<SuitInfoItem> getListItem() {
			return listItem;
		}

		public void setListItem(ArrayList<SuitInfoItem> listItem) {
			this.listItem = listItem;
		}
		
	}

	
	public static class SuitSrc implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String pic;

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}
		
	}
	// 优惠套装明细
	public static class SuitInfoItem implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String pic;// 图片
		private String name;// 名称
		private String unit;// 套
		private String priceInfo;// 价格
		private String bottlevol;
		private String numPerSuit;
		private String id;
//		result.list[i].listItem[i].id
//		result.list[i].listItem[i].name
//		result.list[i].listItem[i].bottlevol
//		result.list[i].listItem[i].numPerSuit
//		result.list[i].listItem[i].unit
//		result.list[i].listItem[i].priceInfo
//		result.list[i].listItem[i].pic
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getPriceInfo() {
			return priceInfo;
		}
		public void setPriceInfo(String priceInfo) {
			this.priceInfo = priceInfo;
		}
		public String getBottlevol() {
			return bottlevol;
		}
		public void setBottlevol(String bottlevol) {
			this.bottlevol = bottlevol;
		}
		public String getNumPerSuit() {
			return numPerSuit;
		}
		public void setNumPerSuit(String numPerSuit) {
			this.numPerSuit = numPerSuit;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

	}
}
