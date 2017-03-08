package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:拼单列表数据实体类
 * 
 * Date:2015-10-10
 * 
 * Author:liuzhouliang
 */
public class GroupBuyProductlistBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private Productlist result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Productlist getResult() {
		return result;
	}

	public void setResult(Productlist result) {
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
		return "ProductlistBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class Productlist implements Serializable {
		private static final long serialVersionUID = 1L;
		// 商品列表
		private List<GroupBuyProductInfor> list;
		private int count;
		// 品牌列表
		private List<BrandList> winetypelist;

		public List<GroupBuyProductInfor> getList() {
			return list;
		}

		public void setList(List<GroupBuyProductInfor> list) {
			this.list = list;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<BrandList> getWinetypelist() {
			return winetypelist;
		}

		public void setWinetypelist(List<BrandList> winetypelist) {
			this.winetypelist = winetypelist;
		}

		@Override
		public String toString() {
			return "Productlist [list=" + list + ", count=" + count
					+ ", winetypelist=" + winetypelist + "]";
		}

		public static class GroupBuyProductInfor implements Serializable {

			private static final long serialVersionUID = 1L;
			private String id;
			private String iid;
			private String pid;
			private String name;
			private String pic;
			private String price;
			private String cprice;
			private String endtime;
			private String starttime;
			private String knum;
			private String unit;
			private String soldnum;
			private String status;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getIid() {
				return iid;
			}

			public void setIid(String iid) {
				this.iid = iid;
			}

			public String getPid() {
				return pid;
			}

			public void setPid(String pid) {
				this.pid = pid;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPic() {
				return pic;
			}

			public void setPic(String pic) {
				this.pic = pic;
			}

			public String getPrice() {
				return price;
			}

			public void setPrice(String price) {
				this.price = price;
			}

			public String getCprice() {
				return cprice;
			}

			public void setCprice(String cprice) {
				this.cprice = cprice;
			}

			public String getEndtime() {
				return endtime;
			}

			public void setEndtime(String endtime) {
				this.endtime = endtime;
			}

			public String getStarttime() {
				return starttime;
			}

			public void setStarttime(String starttime) {
				this.starttime = starttime;
			}

			public String getKnum() {
				return knum;
			}

			public void setKnum(String knum) {
				this.knum = knum;
			}

			public String getUnit() {
				return unit;
			}

			public void setUnit(String unit) {
				this.unit = unit;
			}

			public String getSoldnum() {
				return soldnum;
			}

			public void setSoldnum(String soldnum) {
				this.soldnum = soldnum;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			@Override
			public String toString() {
				return "GroupBuyProductInfor [id=" + id + ", iid=" + iid
						+ ", pid=" + pid + ", name=" + name + ", pic=" + pic
						+ ", price=" + price + ", cprice=" + cprice
						+ ", endtime=" + endtime + ", starttime=" + starttime
						+ ", knum=" + knum + ", unit=" + unit + ", soldnum="
						+ soldnum + ", status=" + status + "]";
			}

		}

		public static class BrandList implements Serializable {
			private static final long serialVersionUID = 1L;
			private String drinksTypeId;
			private String id;
			private String name;
			private String hotBrand;
			// 地址名称汉字
			private String namefilter;
			// 显示数据拼音的首字母
			private String sortLetters;
			// 缩写名
			private String abbreviation;


			public String getDrinksTypeId() {
				return drinksTypeId;
			}

			public void setDrinksTypeId(String drinksTypeId) {
				this.drinksTypeId = drinksTypeId;
			}

			public String getNamefilter() {
				return namefilter;
			}

			public void setNamefilter(String namefilter) {
				this.namefilter = namefilter;
			}

			public String getSortLetters() {
				return sortLetters;
			}

			public void setSortLetters(String sortLetters) {
				this.sortLetters = sortLetters;
			}

			public String getAbbreviation() {
				return abbreviation;
			}

			public void setAbbreviation(String abbreviation) {
				this.abbreviation = abbreviation;
			}
			public String getHotBrand() {
				return hotBrand;
			}

			public void setHotBrand(String hotBrand) {
				this.hotBrand = hotBrand;
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

			@Override
			public String toString() {
				return "BrandList [id=" + id + ", name=" + name + "]";
			}

		}

	}
}
