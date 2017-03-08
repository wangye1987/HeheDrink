package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;

/**
 * Describe:购物车对应数据实体类
 * 
 * Date:2015-10-9
 * 
 * Author:liuzhouliang
 */
public class ShoppingCartInforBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private ShoppingCartInfor result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ShoppingCartInfor getResult() {
		return result;
	}

	public void setResult(ShoppingCartInfor result) {
		this.result = result;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ShoppingCartInforBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class ShoppingCartInfor implements Serializable {
		private static final long serialVersionUID = 1L;
		private List<ProductInfor> list;
		private String amount;

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public List<ProductInfor> getList() {
			return list;
		}

		public void setList(List<ProductInfor> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "ShoppingCartInfor [list=" + list + ", amount=" + amount
					+ "]";
		}

		public class ProductInfor implements Serializable {
			private static final long serialVersionUID = 1L;
			private String id;
//			private String iid;
			private String pid;
			private String num;
			private String pic;
			private String name;
			private String price;
			private String currentprice;
			private int lefnum;
			private String initnum;
			private String aid;
			private String atype;
			private String status;
			private String kcnum;
			//type 商品属性 0是属于拼单 1是属于批发
			private String type;
			// checkbox选中状态
			private boolean checkBoxState;
			// 记录每个item 中数量变化前的值
			private String itemOldNum;
			// 保存每个item 中数量变化后的值
			private String itemNewNum;
			// 记录活动是否结束
			private boolean isShowActivityIcon;
			// 动画用bitmap
			private Bitmap mbitmap;
			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}
			public Bitmap getMbitmap() {
				return mbitmap;
			}

			public void setMbitmap(Bitmap mbitmap) {
				this.mbitmap = mbitmap;
			}

			public void setShowActivityIcon(boolean isShowActivityIcon) {
				this.isShowActivityIcon = isShowActivityIcon;
			}

			public boolean getIsShowActivityIcon() {
				return isShowActivityIcon;
			}

			public void setIsShowActivityIcon(boolean isShowActivityIcon) {
				this.isShowActivityIcon = isShowActivityIcon;
			}

			public String getCurrentprice() {
				return currentprice;
			}

			public void setCurrentprice(String currentprice) {
				this.currentprice = currentprice;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public String getKcnum() {
				return kcnum;
			}

			public void setKcnum(String kcnum) {
				this.kcnum = kcnum;
			}

			public boolean getCheckBoxState() {
				return checkBoxState;
			}

			public void setCheckBoxState(boolean checkBoxState) {
				this.checkBoxState = checkBoxState;
			}

			public String getItemOldNum() {
				return itemOldNum;
			}

			public void setItemOldNum(String itemOldNum) {
				this.itemOldNum = itemOldNum;
			}

			public String getItemNewNum() {
				return itemNewNum;
			}

			public void setItemNewNum(String itemNewNum) {
				this.itemNewNum = itemNewNum;
			}

			// public boolean getIsNumViewShow() {
			// return isNumViewShow;
			// }
			//
			// public void setNumViewShow(boolean isNumViewShow) {
			// this.isNumViewShow = isNumViewShow;
			// }

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

			public String getPrice() {
				return price;
			}

			public void setPrice(String price) {
				this.price = price;
			}

			public int getLefnum() {
				return lefnum;
			}

			public void setLefnum(int lefnum) {
				this.lefnum = lefnum;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

//			public String getIid() {
//				return iid;
//			}
//
//			public void setIid(String iid) {
//				this.iid = iid;
//			}

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public String getNum() {
				return num;
			}

			public void setNum(String num) {
				this.num = num;
			}

			public String getInitnum() {
				return initnum;
			}

			public void setInitnum(String initnum) {
				this.initnum = initnum;
			}

			public String getAid() {
				return aid;
			}

			public void setAid(String aid) {
				this.aid = aid;
			}

			public String getAtype() {
				return atype;
			}

			public void setAtype(String atype) {
				this.atype = atype;
			}

			@Override
			public String toString() {
				return "ProductInfor [id=" + id +  ", pid="
						+ pid + ", num=" + num + ", pic=" + pic + ", name="
						+ name + ", price=" + price + ", currentprice="
						+ currentprice + ", lefnum=" + lefnum + ", initnum="
						+ initnum + ", aid=" + aid + ", atype=" + atype
						+ ", status=" + status + ", kcnum=" + kcnum
						+ ", checkBoxState=" + checkBoxState + ", itemOldNum="
						+ itemOldNum + ", itemNewNum=" + itemNewNum
						+ ", isShowActivityIcon=" + isShowActivityIcon
						+ ", mbitmap=" + mbitmap + "]";
			}

		}

	}
}
