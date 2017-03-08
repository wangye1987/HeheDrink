package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间：2016-3-23 下午2:58:15 类说明
 */
public class NewOrderBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;// 状态码
	private OrderList result;// 返回结果
	private ErrorBeanorder error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorBeanorder getError() {
		return error;
	}

	public void setError(ErrorBeanorder error) {
		this.error = error;
	}

	public OrderList getResult() {
		return result;
	}

	public void setResult(OrderList result) {
		this.result = result;
	}

	public class ErrorBeanorder implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/*错误代码*/
		private String code;
		/*错误信息*/
		private String info;
		
		private String tips;
		/*预览订单不足商品列表*/
		private List<shoppingbean> list;
		
		public String getTips() {
			return tips;
		}

		public void setTips(String tips) {
			this.tips = tips;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public List<shoppingbean> getList() {
			return list;
		}

		public void setList(List<shoppingbean> list) {
			this.list = list;
		}

		public class shoppingbean implements Serializable{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String aid;
			private String msg;
			private String kcnum;
			private String sid;//组合套装商品活动ID
			
			public String getSid() {
				return sid;
			}
			public void setSid(String sid) {
				this.sid = sid;
			}
			public String getAid() {
				return aid;
			}
			public void setAid(String aid) {
				this.aid = aid;
			}
			public String getMsg() {
				return msg;
			}
			public void setMsg(String msg) {
				this.msg = msg;
			}
			public String getKcnum() {
				return kcnum;
			}
			public void setKcnum(String kcnum) {
				this.kcnum = kcnum;
			}
			//判断2个list是否有相同的地方
			@Override
			public boolean equals(Object obj) {
				  if(((ShoppingCartSelectBean)obj).getAid().equals(aid))
			            return true;//这里以name为判定标准。
			        else {
			            return false;
			        }
			}
		}
	}
	/*
	 * { "result": { "totalprice": "30",//合计价格 "address": {}, "paytype": "1",
	 * //1支付宝 2 微信 3 京东 “totalnum”:"2"//商品总件数 “transdesc”：“”//运费描述
	 * “transamount”：“”//运费 "list": [ { "damount": "0",//每箱定金金额 "currentprice":
	 * 220,//当前价格 "pid": "1",//商品id “unit”:"箱" //单位 "pic":
	 * "http://192.168.0.147//images/img2.jpg", "aid": 1,//活动ID "type": 0,//活动类型
	 * 0拼单 1批发 "amount": "220",//拼单定金支付金额 "num": "1", "name": "aa",//商品名称
	 * “itemtotal”:"10000"// 批发每栏商品的价格 } ], }, "error": {}, "status": 1 }
	 */
	public class OrderList implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String totalprice;
		private AddressBean address;
		private String paytype;//空不支付
		private String totalnum;
		private String transdesc;
		private String transamount;
		private List<OrderItemList> list;
		private String balance;
		private String madescore;//订单获取的积分
		private String unique_id;//创建预付订单返回的随机数
		
		private String baseamount;//优惠基础价格
		private boolean usecoupon;
		private String couponnum;
		private String couponamount;
		private Coupon coupon;
		private Score score;
		private String scoreinfo;
		private Hehemoney coin;
		private String coininfo;
		private String paytypeinfo;//不可支付时显示信息	已抵扣全部金额
		private List<Paytypelist> paytypelist;


		public List<Paytypelist> getPaytypelist() {
			return paytypelist;
		}

		public void setPaytypelist(List<Paytypelist> paytypelist) {
			this.paytypelist = paytypelist;
		}

		public String getPaytypeinfo() {
			return paytypeinfo;
		}

		public void setPaytypeinfo(String paytypeinfo) {
			this.paytypeinfo = paytypeinfo;
		}

		public String getCoininfo() {
			return coininfo;
		}

		public void setCoininfo(String coininfo) {
			this.coininfo = coininfo;
		}

		public Hehemoney getCoin() {
			return coin;
		}

		public void setCoin(Hehemoney coin) {
			this.coin = coin;
		}

		public String getMadescore() {
			return madescore;
		}

		public void setMadescore(String madescore) {
			this.madescore = madescore;
		}

		public Score getScore() {
			return score;
		}

		public void setScore(Score score) {
			this.score = score;
		}

		public String getScoreinfo() {
			return scoreinfo;
		}

		public void setScoreinfo(String scoreinfo) {
			this.scoreinfo = scoreinfo;
		}

		public Coupon getCoupon() {
			return coupon;
		}

		public void setCoupon(Coupon coupon) {
			this.coupon = coupon;
		}

		public String getBaseamount() {
			return baseamount;
		}

		public void setBaseamount(String baseamount) {
			this.baseamount = baseamount;
		}

		public boolean getUsecoupon() {
			return usecoupon;
		}

		public void setUsecoupon(boolean usecoupon) {
			this.usecoupon = usecoupon;
		}

		public String getCouponnum() {
			return couponnum;
		}

		public void setCouponnum(String couponnum) {
			this.couponnum = couponnum;
		}

		public String getCouponamount() {
			return couponamount;
		}

		public void setCouponamount(String couponamount) {
			this.couponamount = couponamount;
		}

		public String getUnique_id() {
			return unique_id;
		}

		public void setUnique_id(String unique_id) {
			this.unique_id = unique_id;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getPaytype() {
			return paytype;
		}

		public void setPaytype(String paytype) {
			this.paytype = paytype;
		}

		public List<OrderItemList> getList() {
			return list;
		}

		public void setList(List<OrderItemList> list) {
			this.list = list;
		}

		public AddressBean getAddress() {
			return address;
		}

		public void setAddress(AddressBean address) {
			this.address = address;
		}

		public String getTotalprice() {
			return totalprice;
		}

		public void setTotalprice(String totalprice) {
			this.totalprice = totalprice;
		}

		public String getTotalnum() {
			return totalnum;
		}

		public void setTotalnum(String totalnum) {
			this.totalnum = totalnum;
		}

		public String getTransdesc() {
			return transdesc;
		}

		public void setTransdesc(String transdesc) {
			this.transdesc = transdesc;
		}

		public String getTransamount() {
			return transamount;
		}

		public void setTransamount(String transamount) {
			this.transamount = transamount;
		}

	}

	
	public static class Paytypelist implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private boolean isdefault;
		private boolean enable;
		private String remark;
		
		public boolean isEnable() {
			return enable;
		}
		public void setEnable(boolean enable) {
			this.enable = enable;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isIsdefault() {
			return isdefault;
		}
		public void setIsdefault(boolean isdefault) {
			this.isdefault = isdefault;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
		
	}
	
	public static class Hehemoney implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String maxcoin;//最多可以使用的喝喝币数量
		private String coinblance;//当前账户的喝喝币余额
		private String usecoin;
		private String coinprice;//1 喝喝币抵扣金额
		public String getMaxcoin() {
			return maxcoin;
		}
		public void setMaxcoin(String maxcoin) {
			this.maxcoin = maxcoin;
		}
		public String getCoinblance() {
			return coinblance;
		}
		public void setCoinblance(String coinblance) {
			this.coinblance = coinblance;
		}
		public String getUsecoin() {
			return usecoin;
		}
		public void setUsecoin(String usecoin) {
			this.usecoin = usecoin;
		}
		public String getCoinprice() {
			return coinprice;
		}
		public void setCoinprice(String coinprice) {
			this.coinprice = coinprice;
		}
		
		
	}
	public static class Score implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String maxscore;
		private String scoreblance;
		private String scoreprice;
		private String usescore;
		public String getMaxscore() {
			return maxscore;
		}
		public void setMaxscore(String maxscore) {
			this.maxscore = maxscore;
		}
		public String getScoreblance() {
			return scoreblance;
		}
		public void setScoreblance(String scoreblance) {
			this.scoreblance = scoreblance;
		}
		public String getScoreprice() {
			return scoreprice;
		}
		public void setScoreprice(String scoreprice) {
			this.scoreprice = scoreprice;
		}
		public String getUsescore() {
			return usescore;
		}
		public void setUsescore(String usescore) {
			this.usescore = usescore;
		}
		
	}
	public class Coupon implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String amount;
		private String couponid;
		private String msg;
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getCouponid() {
			return couponid;
		}
		public void setCouponid(String couponid) {
			this.couponid = couponid;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	public static class OrderItemList implements Serializable {
		// "damount": "0",//每箱定金金额
		// "currentprice": 220,//当前价格
		// "pid": "1",//商品id
		// “unit”:"箱" //单位
		// "pic": "http://192.168.0.147//images/img2.jpg",
		// "aid": 1,//活动ID
		// "type": 0,//活动类型 0拼单 1批发
		// "amount": "220",//拼单定金支付金额
		// "num": "1",
		// "name": "aa",//商品名称
		// “itemtotal”:"10000"// 批发每栏商品的价格

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
//		private String damount;
		private String deamount;//单品订金总金额
		private String balacePayAmount;//尾款总金额	尾款为0时不展示
		private String currentprice;
		private String pid;
		private String unit;
		private String pic;
		private String aid;
		private String sid;
		private String type;
		private String amount;//每列的合计金额
		private String num;
		private String name;
		private String bottlevol;
		private String errorInfo;
		private int iscod;//是否支持货到付款 0不支持 1 支持
		private int issuit;//0:不是套餐活动 1套餐活动
		private int  supportCoupon;//0不支持是否支持优惠券
		private int  supportScore;//0不支持是否支持使用积分
		private int  supportRewardScore;//0不支持是否产生积分
		private List<SuitListItem> suitlist;

		public String getBottlevol() {
			return bottlevol;
		}

		public void setBottlevol(String bottlevol) {
			this.bottlevol = bottlevol;
		}

		public String getErrorInfo() {
			return errorInfo;
		}

		public void setErrorInfo(String errorInfo) {
			this.errorInfo = errorInfo;
		}

		public String getDeamount() {
			return deamount;
		}

		public void setDeamount(String deamount) {
			this.deamount = deamount;
		}

		public String getBalacePayAmount() {
			return balacePayAmount;
		}

		public void setBalacePayAmount(String balacePayAmount) {
			this.balacePayAmount = balacePayAmount;
		}

		public String getSid() {
			return sid;
		}

		public void setSid(String sid) {
			this.sid = sid;
		}

		public int getSupportCoupon() {
			return supportCoupon;
		}

		public void setSupportCoupon(int supportCoupon) {
			this.supportCoupon = supportCoupon;
		}

		public int getSupportScore() {
			return supportScore;
		}

		public void setSupportScore(int supportScore) {
			this.supportScore = supportScore;
		}

		public int getSupportRewardScore() {
			return supportRewardScore;
		}

		public void setSupportRewardScore(int supportRewardScore) {
			this.supportRewardScore = supportRewardScore;
		}

	
        
  		public List<SuitListItem> getSuitlist() {
			return suitlist;
		}

		public void setSuitlist(List<SuitListItem> suitlist) {
			this.suitlist = suitlist;
		}

		public int getIssuit() {
			return issuit;
		}

		public void setIssuit(int issuit) {
			this.issuit = issuit;
		}

		public int getIscod() {
			return iscod;
		}

		public void setIscod(int iscod) {
			this.iscod = iscod;
		}

//		public String getDamount() {
//			return damount;
//		}
//
//		public void setDamount(String damount) {
//			this.damount = damount;
////		}

		public String getCurrentprice() {
			return currentprice;
		}

		public void setCurrentprice(String currentprice) {
			this.currentprice = currentprice;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public String getAid() {
			return aid;
		}

		public void setAid(String aid) {
			this.aid = aid;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	
	public static class SuitListItem implements Serializable{

	 /**
	  * result.list[i].suitlist[i].aid	活动ID
		result.list[i].suitlist[i].pid	商品ID
		result.list[i].suitlist[i].bottlevol	规格
		result.list[i].suitlist[i].currentprice	当前价格
		result.list[i].suitlist[i].num	单品总数量
		result.list[i].suitlist[i].pic	商品图片
		result.list[i].suitlist[i].type	活动类型
		result.list[i].suitlist[i].unit	商品单位
		result.list[i].suitlist[i].numPerSuit	每套含量
		result.list[i].suitlist[i].numPerSuitInfo	每套含量信息
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String aid;
		private String pid;
		private String bottlevol;
		private String currentprice;
		private String num;
		private String pic;
		private String type;
		private String unit;
		private String numPerSuit;
		private String numPerSuitInfo;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getBottlevol() {
			return bottlevol;
		}
		public void setBottlevol(String bottlevol) {
			this.bottlevol = bottlevol;
		}
		public String getCurrentprice() {
			return currentprice;
		}
		public void setCurrentprice(String currentprice) {
			this.currentprice = currentprice;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getNumPerSuit() {
			return numPerSuit;
		}
		public void setNumPerSuit(String numPerSuit) {
			this.numPerSuit = numPerSuit;
		}
		public String getNumPerSuitInfo() {
			return numPerSuitInfo;
		}
		public void setNumPerSuitInfo(String numPerSuitInfo) {
			this.numPerSuitInfo = numPerSuitInfo;
		}
		
	}
	/**
	 * 
	 */
	// "uid": 38,
	// "id": 15,
	// "countyname": "紫云",
	// "provincename": "贵州省",
	// "address": "佛山行市",
	// "county": 977,
	// "name": "王总",
	// "province": 8,
	// "areacode": "",
	// "cityname": "安顺市",
	// "mobile": "15888888899",
	// "city": 112
	public class AddressBean implements Serializable {
		private static final long serialVersionUID = 1L;
		private int uid;
		private int id;
		private int county;
		private int province;
		private String areacode;
		private int city;
		private String name;
		private String mobile;
		private String address;
		private String provincename;
		private String cityname;
		private String countyname;

		public String getProvincename() {
			return provincename;
		}

		public void setProvincename(String provincename) {
			this.provincename = provincename;
		}

		public String getCityname() {
			return cityname;
		}

		public void setCityname(String cityname) {
			this.cityname = cityname;
		}

		public String getCountyname() {
			return countyname;
		}

		public void setCountyname(String countyname) {
			this.countyname = countyname;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCounty() {
			return county;
		}

		public void setCounty(int county) {
			this.county = county;
		}

		public int getProvince() {
			return province;
		}

		public void setProvince(int province) {
			this.province = province;
		}

		public int getCity() {
			return city;
		}

		public void setCity(int city) {
			this.city = city;
		}

		public String getAreacode() {
			return areacode;
		}

		public void setAreacode(String areacode) {
			this.areacode = areacode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}
}
