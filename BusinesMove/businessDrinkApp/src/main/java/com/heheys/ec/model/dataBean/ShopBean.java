package com.heheys.ec.model.dataBean; 

import java.io.Serializable;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-9 下午6:11:41 
 * 类说明 
 * @param 店铺认证bean
 */
public class ShopBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*"name":"",//店铺名称
	"address":"",//店铺地址
	"legal":"",//法人
	"contact":"",联系人信息
	"contactmobile":""联系人方式
	"pic1":"",门头图片
	"pic2":"",法人身份证
	"pic3":"",营业执照*/
//	"pic1":"","legal":"","contactmobile":"","address":"","name":"","blicense":"","contact":"","pic3":"","cardid":"","pic2":""
	private String name;
	private String address;
	private String legal;
	private String contact;
	private String contactmobile;
	private String cardid;
	private String blicense ;
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private String pic5;
	private String baseurl;
	private String remark;//审核失败原因
	private String province;
	private String city;
	private String region;
	private String provincename;
	private String cityname;
	private String regionname;
	private String updatetime;
	private String regagreementurl;//电子合同
	private int viplevel;//认证级别 0:未有特权 1:已经通过特权一 ,2:已经通过特权二, 3:已经认证成功
	
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public int getViplevel() {
		return viplevel;
	}
	public void setViplevel(int viplevel) {
		this.viplevel = viplevel;
	}
	public String getRegagreementurl() {
		return regagreementurl;
	}
	public void setRegagreementurl(String regagreementurl) {
		this.regagreementurl = regagreementurl;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
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
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPic4() {
		return pic4;
	}
	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}
	public String getPic5() {
		return pic5;
	}
	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}
	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	private String authentication;//1 未申请 2 审核中 3 审核通过 4 审核失败
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getBlicense() {
		return blicense;
	}
	public void setBlicense(String blicense) {
		this.blicense = blicense;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLegal() {
		return legal;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactmobile() {
		return contactmobile;
	}
	public void setContactmobile(String contactmobile) {
		this.contactmobile = contactmobile;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	
}
 