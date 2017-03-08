package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

import com.heheys.ec.model.dataBean.OrderBaseBean.OrderListBean;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-17 上午11:52:01
 *  类说明
 */
public class WholeBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private WholeResultBean result;//返回结果
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
	public WholeResultBean getResult() {
		return result;
	}
	public void setResult(WholeResultBean result) {
		this.result = result;
	}
	
	
	public static class WholeResultBean implements Serializable{
		private static final long serialVersionUID = 1L;
		private List<WholeListBean> list;
		private int count;
		public List<WholeListBean> getList() {
			return list;
		}

		public void setList(List<WholeListBean> list) {
			this.list = list;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
	
	
	public static class WholeListBean implements Serializable{
//		"id":"1"//活动id
//			"pid":"商品id"
//			"name":"商品名称"
//			"pic":"商品图片",
//			"price":"市场价" 废弃
//			"cprice":"批发价格" 
//			"unit":"箱"//单位
//			"endtime":"" 结束时间 批发为空
//			"starttime":""开始时间 批发为空
//			"soldnum":"已售数量" ( 已售+冻结)
//			"knum":"12"批发的库存量
//			“minnum”:”最小起批量”
//			“type:"0" 活动类型、0拼单\1批发
//			"status":"1"//活动状态
//			“bottlevol” : 每瓶容量， “750ml”
//			“boxsize”: 每箱瓶数，6 
//			“placename” ：产地名， “意大利”
//			“salesvol” : 商品销量
		
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/* "endtime": "2016-04-17 11:56:12",
		"id": "1",
          "pid": "1",
          "name": "茅台52",
          "pic": "http://192.168.0.147/images/img2.jpg",
          "price": "25",
          "cprice": "35",
          "unit": "箱",
          "starttime": "2016-03-17 11:56:12",
          "soldnum": "120",
          "kcnum": "500",
          "minnum": "20",
          "type": "1",
          "status": "1",
          "bottlevol": "250ML",
          "boxsize": "10",
          "salvol": "150",
          "placename": "中国贵州"*/
		private String id;//批发活动id
		private String pid;
		private String name;
		private String pic;//大图
		private String minpic;//小图
		private String price;
		private String cprice;
		private String unit;
		private String endtime;
		private String starttime;
		private String soldnum;
		private String knum;
		private String minnum;
		private String type;
		private String status;
		private String bottlevol;
		private String boxsize;
		private String salvol;
		private String placename;
		private String recommend;//是否精选
		private String frozennum;
		private String univalent;//单价
		private String perunit;//单价单位
		private String currency;//羊角符号单位
		private String salesvol;//销量
		private int currentnum;//当前购买的数量在商品列表显示
		private int num;//当前购物车购买当前商品数量
		private String numresult;//显示服务器返回的当前库存不足
		private String deprice;
		private String hasrecom;//0:有套装 1:没有套装
		
		public String getNumresult() {
			return numresult;
		}
		public void setNumresult(String numresult) {
			this.numresult = numresult;
		}
		public String getHasrecom() {
			return hasrecom;
		}
		public void setHasrecom(String hasrecom) {
			this.hasrecom = hasrecom;
		}
		public String getDeprice() {
			return deprice;
		}
		public void setDeprice(String deprice) {
			this.deprice = deprice;
		}
		//		public String getNumresult() {
//			return numresult;
//		}
//		public void setNumresult(String numresult) {
//			this.numresult = numresult;
//		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public int getCurrentnum() {
			return currentnum;
		}
		public void setCurrentnum(int currentnum) {
			this.currentnum = currentnum;
		}
		public String getSalesvol() {
			return salesvol;
		}
		public void setSalesvol(String salesvol) {
			this.salesvol = salesvol;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getRecommend() {
			return recommend;
		}
		public void setRecommend(String recommend) {
			this.recommend = recommend;
		}
		public String getUnivalent() {
			return univalent;
		}
		public void setUnivalent(String univalent) {
			this.univalent = univalent;
		}
		public String getPerunit() {
			return perunit;
		}
		public void setPerunit(String perunit) {
			this.perunit = perunit;
		}
		public String getMinpic() {
			return minpic;
		}
		public void setMinpic(String minpic) {
			this.minpic = minpic;
		}
		public String getFrozennum() {
			return frozennum;
		}
		public void setFrozennum(String frozennum) {
			this.frozennum = frozennum;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
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
		public String getSoldnum() {
			return soldnum;
		}
		public void setSoldnum(String soldnum) {
			this.soldnum = soldnum;
		}
		public String getKnum() {
			return knum;
		}
		public void setKnum(String knum) {
			this.knum = knum;
		}
		public String getMinnum() {
			return minnum;
		}
		public void setMinnum(String minnum) {
			this.minnum = minnum;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
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
		public String getPlacename() {
			return placename;
		}
		public void setPlacename(String placename) {
			this.placename = placename;
		}
		
		public String getSalvol() {
			return salvol;
		}
		public void setSalvol(String salvol) {
			this.salvol = salvol;
		}
		@Override
		public String toString() {
			return "WholeListBean [id=" + id + ", pid=" + pid + ", name="
					+ name + ", pic=" + pic + ", price=" + price + ", cprice="
					+ cprice + ", unit=" + unit + ", endtime=" + endtime
					+ ", starttime=" + starttime + ", soldnum=" + soldnum
					+ ", knum=" + knum + ", minnum=" + minnum + ", type="
					+ type + ", status=" + status + ", bottlevol=" + bottlevol
					+ ", boxsize=" + boxsize + ", placename=" + placename
					+ ", salesvol=" + salvol + "]";
		}
		
	}
}
