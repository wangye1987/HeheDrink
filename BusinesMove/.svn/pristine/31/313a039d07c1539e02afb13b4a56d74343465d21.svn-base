package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-15 下午1:11:25 
 * 类说明 
 * @param 获取预付订单bean
 */
public  class PaymentBean implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	{
//	    "result": {
//	        "total": "910",
//	        "amount": "90",
//	        "address": {
//	            "uid": 38,
//	            "id": 15,
//	            "countyname": "紫云",
//	            "provincename": "贵州省",
//	            "address": "佛山行市",
//	            "county": 977,
//	            "name": "王总",
//	            "province": 8,
//	            "areacode": "",
//	            "cityname": "安顺市",
//	            "mobile": "15888888899",
//	            "city": 112
//	        },
//	        "list": [
//	            {
//	                "damount": "90",
//	                "iid": "12",
//	                "currentprice": "900",
//	                "pid": "24",
//	                "pic": "http://101.200.235.7:8080/img/img/b01599e5-f402-4358-8701-21b3a4e6bcbb.jpg",
//	                "aid": 20,
//	                "amount": "900",
//	                "atype": "1",
//	                "num": "1",
//	                "rate": "10%",
//	                "price": "1000",
//	                "name": "亲民五粮液，经典水晶瓶",
//	                "factory": "宜宾五粮液股份有限公司",
//	                "transamount": "10"
//	            }
//	        ],
//	        "transamount": "10",
//	        "totalamount": "900"
//	    },
//	    "error": {},
//	    "status": 1
//	}
	private AddressBean address; 
	private List<PayList> list;
	private String amount;//(支付金额)定金
	private String total;//商品总额
	private String transamount;//运费
	private String totalamount;//总金额
	
	
	public List<PayList> getList() {
		return list;
	}
	public void setList(List<PayList> list) {
		this.list = list;
	}
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}
//	public payinfo getList() {
//		return list;
//	}
//	public void setList(payinfo list) {
//		this.list = list;
//	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTransamount() {
		return transamount;
	}
	public void setTransamount(String transamount) {
		this.transamount = transamount;
	}

	public static class PayList implements Serializable{
		
		
//		"list":[{"amount":"22200",
//			"atype":1,"num":"30","price":"760","iid":"10","name":"五粮液﹡52°1000mL",
//			"currentprice":"740","pid":"22","factory":"惹事","aid":18,
//			"pic":"http://101.200.235.7:8080/img/img/dee75002-a6ad-44b4-a98a-221058f166b1.jpg","transamount":"600"},
//			{"amount":"100000","atype":1,"num":"100","price":"1000","iid":"4","name":"est1_五粮液",
//				"currentprice":"1000","pid":"10","factory":"123搜索","aid":12,
//				"pic":"http://101.200.235.7:8080/img/img/dd7d15db-ed1d-4414-8bfb-f4eb3b38df06.jpg",
//				"transamount":"880"},{"amount":"6400","atype":1,"num":"20","price":"345","iid":"11",
//					"name":":五粮液﹡52°1000mL五粮液﹡52°1000mL","currentprice":"320","pid":"23",
//					"factory":"托尔斯泰","aid":19,"pic":"http://101.200.235.7:8080/img/img/dee75002-a6ad-44b4-a98a-221058f166b1.jpg",
//					"transamount":"400"},{"amount":"900","atype":1,"num":"1","price":"1000","iid":"12","name":"亲民五粮液，经典水晶瓶",
//						"currentprice":"900","pid":"24","factory":"宜宾五粮液股份有限公司","aid":20,
//				"pic":"http://101.200.235.7:8080/img/img/b01599e5-f402-4358-8701-21b3a4e6bcbb.jpg","transamount":"10"}]
		
//		 "damount": "90",
//         "iid": "12",
//         "currentprice": "900",
//         "pid": "24",
//         "pic": "http://101.200.235.7:8080/img/img/b01599e5-f402-4358-8701-21b3a4e6bcbb.jpg",
//         "aid": 20,
//         "amount": "900",
//         "atype": "1",
//         "num": "1",
//         "rate": "10%",
//         "price": "1000",
//         "name": "亲民五粮液，经典水晶瓶",
//         "factory": "宜宾五粮液股份有限公司",
//         "transamount": "10"
		private static final long serialVersionUID = 1L;
		private String amount;
		private String atype;
		private String num;
		private String price;
		private String iid;
		private String name;
		private String pid;
		private String factory;
		private String currentprice;
		private int aid;
		private String transamount;
		private String pic;
		private String rate;
		private String damount;
		public String getDamount() {
			return damount;
		}
		public void setDamount(String damount) {
			this.damount = damount;
		}
		public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			this.rate = rate;
		}
		public String getAtype() {
			return atype;
		}
		public void setAtype(String atype) {
			this.atype = atype;
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
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getIid() {
			return iid;
		}
		public void setIid(String iid) {
			this.iid = iid;
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
		public String getFactory() {
			return factory;
		}
		public void setFactory(String factory) {
			this.factory = factory;
		}
		public String getCurrentprice() {
			return currentprice;
		}
		public void setCurrentprice(String currentprice) {
			this.currentprice = currentprice;
		}
		public int getAid() {
			return aid;
		}
		public void setAid(int aid) {
			this.aid = aid;
		}
		public String getTransamount() {
			return transamount;
		}
		public void setTransamount(String transamount) {
			this.transamount = transamount;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
	}
	public static class AddressBean implements Serializable{
		/**
		 * 
		 */
//		"uid": 38,
//        "id": 15,
//        "countyname": "紫云",
//        "provincename": "贵州省",
//        "address": "佛山行市",
//        "county": 977,
//        "name": "王总",
//        "province": 8,
//        "areacode": "",
//        "cityname": "安顺市",
//        "mobile": "15888888899",
//        "city": 112
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
 