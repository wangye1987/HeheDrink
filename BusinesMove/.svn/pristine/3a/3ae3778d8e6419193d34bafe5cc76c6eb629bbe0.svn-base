package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.List;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-16 下午3:16:59 
 * 类说明 
 * @param获取省市区bean
 */
public class ProvinceListBaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private Bean result;//返回结果
	private ErrorBean error;//返回失败结果
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Bean getResult() {
		return result;
	}
	public void setResult(Bean result) {
		this.result = result;
	}
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	
	public static class Bean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<ProvinceList> list;

		public List<ProvinceList> getList() {
			return list;
		}
		public void setList(List<ProvinceList> list) {
			this.list = list;
		}
	}

//	{
//	    "list": [
//	        {
//	            "province": {
//	                "id": 1,
//	                "name": "北京",
//	                "city": [{"id": 2,
//	                        "name": "北京市",
//	                        "county": [
//	                            {
//	                                "id": 3,
//	                                "name": "朝阳"
//	                            }
	
	public static class ProvinceList implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Province province;

		public Province getProvince() {
			return province;
		}

		public void setProvince(Province province) {
			this.province = province;
		}
		
	}
	public static class Province  implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private String name;
		private int id;
		private List<CityBean> city;
		private String pinyin;//对于拼音
		public String getPinyin() {
			return pinyin;
		}
		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public List<CityBean> getCity() {
			return city;
		}
		public void setCity(List<CityBean> city) {
			this.city = city;
		}
	}
	
	public static class CityBean  implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private List<County> county;
		private String pinyin;//拼音
		public String getPinyin() {
			return pinyin;
		}
		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
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
		public List<County> getCounty() {
			return county;
		}
		public void setCounty(List<County> county) {
			this.county = county;
		}
	}
	
	public static class County implements Serializable{
		/**
		 * 
		 */
		public County(){
			super();
		}
		public County(String name,int id){
			super();
			this.name = name;
			this.id = id;
		}
		private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private String pinyin;//拼音
		public String getPinyin() {
			return pinyin;
		}
		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
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
	}
}
 