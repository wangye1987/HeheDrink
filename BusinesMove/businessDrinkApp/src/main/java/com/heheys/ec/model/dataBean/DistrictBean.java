package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.ArrayList;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-12-3 下午5:08:33 
 * 类说明 
 * @param 保存选中的城市名字和id
 */
public class DistrictBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> cityName;//城市名字集合
	private ArrayList<String> cityId;//城市id集合
	private String name;//省份名字
	private String proviceId;//省份ID
	public String getProviceId() {
		return proviceId;
	}
	public void setProviceId(String proviceId) {
		this.proviceId = proviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getCityName() {
		return cityName;
	}
	public void setCityName(ArrayList<String> cityName) {
		this.cityName = cityName;
	}
	public ArrayList<String> getCityId() {
		return cityId;
	}
	public void setCityId(ArrayList<String> cityId) {
		this.cityId = cityId;
	}

}
 