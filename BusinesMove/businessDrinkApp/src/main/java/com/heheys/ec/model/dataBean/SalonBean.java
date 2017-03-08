package com.heheys.ec.model.dataBean;

/**
 * @author 作者 E-mail:wk
 * @version 创建时间：2015-10-10 下午4:30:20 类说明
 * @param 沙龙bean
 */
public class SalonBean {
	/*
	 * "pic":"", 缩略图 "subject":"", 主题 "time":"", 时间 "address":"", 地址
	 * "maxnum":""人数 "status":"", 活动状态 "sign":"" 是否已报名
	 */
	private String pic;
	private String subject;
	private String time;
	private String address;
	private String maxnum;
	private String status;
	private String sign;
	private String id;//沙龙ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMaxnum() {
		return maxnum;
	}
	public void setMaxnum(String maxnum) {
		this.maxnum = maxnum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "SalonBean [pic=" + pic + ", subject=" + subject + ", time="
				+ time + ", address=" + address + ", maxnum=" + maxnum
				+ ", status=" + status + ", sign=" + sign + "]";
	}
	
}
