package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

import static com.heheys.ec.R.string.flavor;


/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-18 上午10:54:15
 *  类说明
 */
public class NewProductDetailInfor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private NewProductInfor result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NewProductInfor getResult() {
		return result;
	}

	public void setResult(NewProductInfor result) {
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
		return "ProductDetailInfor [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class NewProductInfor implements Serializable {
		private static final long serialVersionUID = 1L;
		private String isFavorite;
		private String buyerNum;

		private int issuit;//是否套装 0:普通商品 1:组合套装
		private String suitcount;//推荐套装数量
		private Suitinfo suit;
		private String knum;
		private String salerphonenum;
		private String boxsize;

		private String salerimid;
		private String shopname;
		private String bottlevol;
		private String id;
		private String shopid;
		private String promotiondesc;
		private String soldnum;
		private String description;
		private String hehekfphonenum;
		private String name;
		private String transamount;
		private String nextprice;//下一个价格节点
		private String introduce_url;
		private NewProductBaseInfor baseinfo;
		private AidShareBean activityShare;
		private ShopShareBean shopShare;
		private String status;
		private String pid;
		private List<String> pic;
		private String maxprice;
		private String introduce2;
		private String unit;
		private String introduce1;
		private String price;
		private String introduce3;
		private boolean supportarrive;//是否支持到付
		
		private String recommend;//是否精选
		private String minnum;
		private String minprice;
		private String factory;
		private String transdesc;
		private String salesvol;
		private String salerimname;
		private String endtime;
		private String univalent;

		//2.0新增字段 活动类型	0合伙买 1甩卖 2:甩卖活动 3:E发行
		private String type;
		//（E发行的预售价使用）
		private String currentprice;
		// 合伙买定金价格
		private String deprice;

		// 浏览量
		private String viewNum;
		// 关注量
		private String flowNum;
		// E发行时有值.0
		private String shopurl;
		// E发行 发行区域
		private String releaseArea;
		// 合伙买定金价格
		private RecomDetail recomDetail;

		// 距离发行/结
		private String diffTime;
		// 热线电话
		private String hotline;

		public ShopShareBean getShopShare() {
			return shopShare;
		}

		public void setShopShare(ShopShareBean shopShare) {
			this.shopShare = shopShare;
		}

		public AidShareBean getActivityShare() {
			return activityShare;
		}

		public void setActivityShare(AidShareBean activityShare) {
			this.activityShare = activityShare;
		}

		public String getIsFavorite() {
			return isFavorite;
		}

		public void setIsFavorite(String isFavorite) {
			this.isFavorite = isFavorite;
		}

		public String getBuyerNum() {
			return buyerNum;
		}

		public void setBuyerNum(String buyerNum) {
			this.buyerNum = buyerNum;
		}

		public static long getSerialVersionUID() {
			return serialVersionUID;
		}

		public String getDeprice() {
			return deprice;
		}

		public void setDeprice(String deprice) {
			this.deprice = deprice;
		}

		public String getViewNum() {
			return viewNum;
		}

		public void setViewNum(String viewNum) {
			this.viewNum = viewNum;
		}

		public void setFlowNum(String flowNum) {
			this.flowNum = flowNum;
		}

		public String getFlowNum() {
			return flowNum;
		}

		public String getShopurl() {
			return shopurl;
		}

		public void setShopurl(String shopurl) {
			this.shopurl = shopurl;
		}

		public String getReleaseArea() {
			return releaseArea;
		}

		public void setReleaseArea(String releaseArea) {
			this.releaseArea = releaseArea;
		}

		public RecomDetail getRecomDetail() {
			return recomDetail;
		}

		public void setRecomDetail(RecomDetail recomDetail) {
			this.recomDetail = recomDetail;
		}

		public String getDiffTime() {
			return diffTime;
		}

		public void setDiffTime(String diffTime) {
			this.diffTime = diffTime;
		}

		public String getHotline() {
			return hotline;
		}

		public void setHotline(String hotline) {
			this.hotline = hotline;
		}

		private int iscod;//0不支持货到付款 		1支持货到付款
		
		public int getIssuit() {
			return issuit;
		}

		public void setIssuit(int issuit) {
			this.issuit = issuit;
		}

		public String getSuitcount() {
			return suitcount;
		}

		public void setSuitcount(String suitcount) {
			this.suitcount = suitcount;
		}

		public Suitinfo getSuit() {
			return suit;
		}

		public void setSuit(Suitinfo suit) {
			this.suit = suit;
		}

		public int getIscod() {
			return iscod;
		}

		public void setIscod(int iscod) {
			this.iscod = iscod;
		}

		public boolean isSupportarrive() {
			return supportarrive;
		}

		public void setSupportarrive(boolean supportarrive) {
			this.supportarrive = supportarrive;
		}

		public String getUnivalent() {
			return univalent;
		}

		public void setUnivalent(String univalent) {
			this.univalent = univalent;
		}

		public String getRecommend() {
			return recommend;
		}

		public void setRecommend(String recommend) {
			this.recommend = recommend;
		}

		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		public String getMinnum() {
			return minnum;
		}

		public void setMinnum(String minnum) {
			this.minnum = minnum;
		}

		public String getNextprice() {
			return nextprice;
		}

		public void setNextprice(String nextprice) {
			this.nextprice = nextprice;
		}

		public String getTransdesc() {
			return transdesc;
		}

		public void setTransdesc(String transdesc) {
			this.transdesc = transdesc;
		}

		public String getBottlevol() {
			return bottlevol;
		}

		public void setBottlevol(String bottlevol) {
			this.bottlevol = bottlevol;
		}

		public String getBoxsize() {
			return boxsize;
		}

		public void setBoxsize(String boxsize) {
			this.boxsize = boxsize;
		}

		public String getSalesvol() {
			return salesvol;
		}

		public void setSalesvol(String salesvol) {
			this.salesvol = salesvol;
		}

		public String getIntroduce1() {
			return introduce1;
		}

		public void setIntroduce1(String introduce1) {
			this.introduce1 = introduce1;
		}

		public String getIntroduce2() {
			return introduce2;
		}

		public void setIntroduce2(String introduce2) {
			this.introduce2 = introduce2;
		}

		public String getIntroduce3() {
			return introduce3;
		}

		public void setIntroduce3(String introduce3) {
			this.introduce3 = introduce3;
		}

		public String getIntroduce_url() {
			return introduce_url;
		}

		public void setIntroduce_url(String introduce_url) {
			this.introduce_url = introduce_url;
		}

		public String getShopid() {
			return shopid;
		}

		public void setShopid(String shopid) {
			this.shopid = shopid;
		}

		public String getShopname() {
			return shopname;
		}

		public void setShopname(String shopname) {
			this.shopname = shopname;
		}
//
//		public String getShoplogurl() {
//			return shoplogurl;
//		}
//
//		public void setShoplogurl(String shoplogurl) {
//			this.shoplogurl = shoplogurl;
//		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSalerimid() {
			return salerimid;
		}

		public void setSalerimid(String salerimid) {
			this.salerimid = salerimid;
		}

		public String getSalerimname() {
			return salerimname;
		}

		public void setSalerimname(String salerimname) {
			this.salerimname = salerimname;
		}

		public String getSalerphonenum() {
			return salerphonenum;
		}

		public void setSalerphonenum(String salerphonenum) {
			this.salerphonenum = salerphonenum;
		}

		public String getHehekfphonenum() {
			return hehekfphonenum;
		}

		public void setHehekfphonenum(String hehekfphonenum) {
			this.hehekfphonenum = hehekfphonenum;
		}

		public String getPromotiondesc() {
			return promotiondesc;
		}

		public void setPromotiondesc(String promotiondesc) {
			this.promotiondesc = promotiondesc;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}


		public String getTransamount() {
			return transamount;
		}

		public void setTransamount(String transamount) {
			this.transamount = transamount;
		}

		public String getFactory() {
			return factory;
		}

		public void setFactory(String factory) {
			this.factory = factory;
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

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getMinprice() {
			return minprice;
		}

		public void setMinprice(String minprice) {
			this.minprice = minprice;
		}

		public String getMaxprice() {
			return maxprice;
		}

		public void setMaxprice(String maxprice) {
			this.maxprice = maxprice;
		}


		public List<String> getPic() {
			return pic;
		}

		public void setPic(List<String> pic) {
			this.pic = pic;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getCurrentprice() {
			return currentprice;
		}

		public void setCurrentprice(String currentprice) {
			this.currentprice = currentprice;
		}

		public String getKnum() {
			return knum;
		}

		public void setKnum(String knum) {
			this.knum = knum;
		}

		public String getSoldnum() {
			return soldnum;
		}

		public void setSoldnum(String soldnum) {
			this.soldnum = soldnum;
		}

		public NewProductBaseInfor getBaseinfo() {
			return baseinfo;
		}

		public void setBaseinfo(NewProductBaseInfor baseinfo) {
			this.baseinfo = baseinfo;
		}


		public static class ShopShareBean implements  Serializable{
			String title;
			String shareUrl;
			String pic;

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getShareUrl() {
				return shareUrl;
			}

			public void setShareUrl(String shareUrl) {
				this.shareUrl = shareUrl;
			}

			public String getPic() {
				return pic;
			}

			public void setPic(String pic) {
				this.pic = pic;
			}
		}
		public static class AidShareBean implements  Serializable{
			String title;
			String shareUrl;
			String pic;

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getShareUrl() {
				return shareUrl;
			}

			public void setShareUrl(String shareUrl) {
				this.shareUrl = shareUrl;
			}

			public String getPic() {
				return pic;
			}

			public void setPic(String pic) {
				this.pic = pic;
			}
		}
		public static class Suitinfo implements Serializable{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			private String suitId;//套装ID
			private String suitTile;//套装标题
			private String suitPrice;//套装价格
			private String suitSave;//节省信息
			private List<listItemDate> listItem;
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
			public List<listItemDate> getListItem() {
				return listItem;
			}
			public void setListItem(List<listItemDate> listItem) {
				this.listItem = listItem;
			}
			
		}


		public class RecomDetail implements Serializable{
			//推荐位名称
			String title;
			//推荐位图片地址
			String imgUrl;
			//推荐位链接地址
			String linkUrl;

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getImgUrl() {
				return imgUrl;
			}

			public void setImgUrl(String imgUrl) {
				this.imgUrl = imgUrl;
			}

			public String getLinkUrl() {
				return linkUrl;
			}

			public void setLinkUrl(String linkUrl) {
				this.linkUrl = linkUrl;
			}
		}
		public static class listItemDate implements Serializable{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String id;//活动ID
			private String name;//活动名称
			private String bottlevol;//规格
			private String numPerSuit;//每套含数量
			private String unit;//单位
			private String priceInfo;//单个价格信息
			private String pic;//活动图片
			
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
			public String getPic() {
				return pic;
			}
			public void setPic(String pic) {
				this.pic = pic;
			}
			
			
			
		}
		public static class NewProductBaseInfor implements Serializable {

			private static final long serialVersionUID = 1L;
			// 原料
			private String materials;
			// 贮存条件
			private String storage;
			// 度数
			private String alcohol;
			// 品牌
			private String brand;
			// 产地
			private String origin;
			// 品种/品类
			private String variety;
			// 子类型
			private String variety1;
			// 子类型的小类
			private String variety2;
			// 品类
			private String category;
			// 香型
//			private String flavor;
			// 净含量
			private String content;
//			// 规格
//			private String guige;

//			public String getGuige() {
//				return guige;
//			}
//
//			public void setGuige(String guige) {
//				this.guige = guige;
//			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getMaterials() {
				return materials;
			}

			public void setMaterials(String materials) {
				this.materials = materials;
			}

			public String getStorage() {
				return storage;
			}

			public void setStorage(String storage) {
				this.storage = storage;
			}

			public String getAlcohol() {
				return alcohol;
			}

			public void setAlcohol(String alcohol) {
				this.alcohol = alcohol;
			}

			public String getbrand() {
				return brand;
			}

			public void setbrand(String brand) {
				this.brand = brand;
			}

			public String getorigin() {
				return origin;
			}

			public void setorigin(String origin) {
				this.origin = origin;
			}

			public String getvariety() {
				return variety;
			}

			public void setvariety(String variety) {
				this.variety = variety;
			}

			public String getvariety1() {
				return variety1;
			}

			public void setvariety1(String variety1) {
				this.variety1 = variety1;
			}

			public String getvariety2() {
				return variety2;
			}

			public void setvariety2(String variety2) {
				this.variety2 = variety2;
			}

			public String getcategory() {
				return category;
			}

			public void setcategory(String category) {
				this.category = category;
			}
//
//			public String getflavor() {
//				return flavor;
//			}
//
//			public void setflavor(String flavor) {
//				this.flavor = flavor;
//			}

			@Override
			public String toString() {
				return "ProductBaseInfor [materials=" + materials
						+ ", storage=" + storage + ", alcohol=" + alcohol
						+ ", brand=" + brand + ", origin=" + origin + ", variety="
						+ variety + ", variety1=" + variety1 + ", variety2=" + variety2
						+ ", category=" + category + ", flavor="
						+ ", content=" + content+"]";
			}

		}

	}
}