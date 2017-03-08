package com.heheys.ec.model.dataBean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;


/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-24 上午10:38:30 类说明
 */
public class NewShoppingCartInforBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private NewShoppingCartInfor result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NewShoppingCartInfor getResult() {
		return result;
	}

	public void setResult(NewShoppingCartInfor result) {
		this.result = result;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	public class NewShoppingCartInfor implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<NewProductInfo> list;
		private String amount;
		private int count;
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public List<NewProductInfo> getList() {
			return list;
		}
		public void setList(List<NewProductInfo> list) {
			this.list = list;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
	}
	
	public static class NewProductInfo implements Serializable{
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/*"status": 1,//拼单状态 1是正常 2 是以结束 3是已完成
         "currentprice": 250,//当前价格
         "pid": 1,//商品ID
         "aid": 2,//活动ID
         "pic": "http://192.168.0.147//images/img2.jpg",//商品图片
         "id": 16,//购物车ID
         "unit": "瓶",//单位
         "num": 2,//已经购买数量
         "type": 0,//0 拼单 1 批发
         "kcnum": 2000,//批发库存 批发适用
         "name": "茅台52",
         "minnum": 1,//最小购买量
         "cprice": 250,//定金 拼单有 批发没有
         "leftnum": 2000//拼单库存 拼单适用
		"guige":"750ml*6"，//规格
		"factory":"产地"//产地
		"boxsize":"6"//瓶
		  */	
		private String status;
		private String currentprice;//合伙买为定金
		private String balanceprice;//合伙买尾款
		private String pid;
		private String aid;
		private String pic;
		private String id;//购物车id
		private String unit;
		private String num;
		private String type;
		private String kcnum;
		private String name;
		private String minnum;
		private String cprice;
		private String leftnum;
		private String guige;
		private String factory;
		private String boxsize;
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
		private String numresult;
		private String sid;
		private int issuit;
		//0是单品
//		1:组合套餐
//		2:推荐套餐
		private List<SuitListShopping> suitlist;

		public void setBalanceprice(String balanceprice) {
			this.balanceprice = balanceprice;
		}

		public String getBalanceprice() {
			return balanceprice;
		}

		public boolean isCheckBoxState() {
			return checkBoxState;
		}

		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
		}
		public List<SuitListShopping> getSuitlist() {
			return suitlist;
		}
		public void setSuitlist(List<SuitListShopping> suitlist) {
			this.suitlist = suitlist;
		}
		public String getNumresult() {
			return numresult;
		}
		public int getIssuit() {
			return issuit;
		}
		public void setIssuit(int issuit) {
			this.issuit = issuit;
		}
		public void setNumresult(String numresult) {
			this.numresult = numresult;
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
		public boolean isShowActivityIcon() {
			return isShowActivityIcon;
		}
		public void setShowActivityIcon(boolean isShowActivityIcon) {
			this.isShowActivityIcon = isShowActivityIcon;
		}
		public Bitmap getMbitmap() {
			return mbitmap;
		}
		public void setMbitmap(Bitmap mbitmap) {
			this.mbitmap = mbitmap;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
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
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getKcnum() {
			return kcnum;
		}
		public void setKcnum(String kcnum) {
			this.kcnum = kcnum;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMinnum() {
			return minnum;
		}
		public void setMinnum(String minnum) {
			this.minnum = minnum;
		}
		public String getCprice() {
			return cprice;
		}
		public void setCprice(String cprice) {
			this.cprice = cprice;
		}
		public String getLeftnum() {
			return leftnum;
		}
		public void setLeftnum(String leftnum) {
			this.leftnum = leftnum;
		}
		public String getGuige() {
			return guige;
		}
		public void setGuige(String guige) {
			this.guige = guige;
		}
		public String getFactory() {
			return factory;
		}
		public void setFactory(String factory) {
			this.factory = factory;
		}
		public String getBoxsize() {
			return boxsize;
		}
		public void setBoxsize(String boxsize) {
			this.boxsize = boxsize;
		}
		
	}
	
	
	public static class SuitListShopping implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String aid;
		private String boxsize;
		private String cprice;
		private String currentprice;
		private String guige;
		private String id;
		private String name;
		private String num;
		private String pic;
		private String numPerSuit;
		private String numPerSuitInfo;
		private String unit;
		private String status;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
		}
		public String getBoxsize() {
			return boxsize;
		}
		public void setBoxsize(String boxsize) {
			this.boxsize = boxsize;
		}
		public String getCprice() {
			return cprice;
		}
		public void setCprice(String cprice) {
			this.cprice = cprice;
		}
		public String getCurrentprice() {
			return currentprice;
		}
		public void setCurrentprice(String currentprice) {
			this.currentprice = currentprice;
		}
		public String getGuige() {
			return guige;
		}
		public void setGuige(String guige) {
			this.guige = guige;
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

}
