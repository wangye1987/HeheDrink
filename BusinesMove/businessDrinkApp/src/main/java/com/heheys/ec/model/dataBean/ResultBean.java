package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-29 下午4:20:59 
 * 类说明 
 * @param
 */
public class ResultBean implements Serializable{
	private String id;
	private String mobile;

	private String role;
	private String shopname;
	private String url;//管理商后台h5url
	private int viplevel;
	private String vipname;
	private String vipClubUrl;
	private WalletInfo wallet;
	private OrderInfo order;
	private String verifystatus;

	public String getVipClubUrl() {
		return vipClubUrl;
	}

	public void setVipClupUrl(String vipClubUrl) {
		this.vipClubUrl = vipClubUrl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getVipname() {
		return vipname;
	}

	public void setVipname(String vipname) {
		this.vipname = vipname;
	}

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}

	public WalletInfo getWallet() {
		return wallet;
	}

	public void setWallet(WalletInfo wallet) {
		this.wallet = wallet;
	}
	public int getViplevel() {
		return viplevel;
	}
	public void setViplevel(int viplevel) {
		this.viplevel = viplevel;
	}
	public String getUrl(){
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVerifystatus() {
		return verifystatus;
	}
	public void setVerifystatus(String verifystatus) {
		this.verifystatus = verifystatus;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static class WalletInfo implements  Serializable{
		private  String cashBlance;//用户余额
		private  String coinBlance;//喝币金额
		private  String couponCount;//优惠券数量
		private  String scoreBalance;//积分余额

		public String getCashBlance() {
			return cashBlance;
		}

		public void setCashBlance(String cashBlance) {
			this.cashBlance = cashBlance;
		}

		public String getCoinBlance() {
			return coinBlance;
		}

		public void setCoinBlance(String coinBlance) {
			this.coinBlance = coinBlance;
		}

		public String getCouponCount() {
			return couponCount;
		}

		public void setCouponCount(String couponCount) {
			this.couponCount = couponCount;
		}

		public String getScoreBalance() {
			return scoreBalance;
		}

		public void setScoreBalance(String scoreBalance) {
			this.scoreBalance = scoreBalance;
		}
	}
	public static class OrderInfo implements  Serializable{
		private  int dfk;
		private  int hhz;
		private  int dfh;
		private  int dsh;

		public int getDsh() {
			return dsh;
		}

		public void setDsh(int dsh) {
			this.dsh = dsh;
		}

		public int getDfk() {
			return dfk;
		}

		public void setDfk(int dfk) {
			this.dfk = dfk;
		}

		public int getHhz() {
			return hhz;
		}

		public void setHhz(int hhz) {
			this.hhz = hhz;
		}

		public int getDfh() {
			return dfh;
		}

		public void setDfh(int dfh) {
			this.dfh = dfh;
		}

	}
	public static class Msgtips implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String title;
		private String content;
		private boolean success;
		private List<Msglist> msglist;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public List<Msglist> getMsglist() {
			return msglist;
		}
		public void setMsglist(List<Msglist> msglist) {
			this.msglist = msglist;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
	}
	
	public static class Msglist implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String msg;
		private boolean  success;
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
	}
}
 