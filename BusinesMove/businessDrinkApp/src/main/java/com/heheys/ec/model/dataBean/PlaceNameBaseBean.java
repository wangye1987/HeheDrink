package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-17 下午3:18:05
 *  类说明
 */
public class PlaceNameBaseBean implements Serializable{
	/*{
	"list":[
	{
	”cid”: 国家ID
	“cname”:”国家名称”
	“region”: [
	 {“regionid”: 产区id
	  “regionname”: 产区名
	  “subregion”: [
	{“subregionid”: 子产区id
	 “subregionname”: 子产区名    
	  }]
	}
	]
	}
	]
	}*/
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private PlaceListResultBean result;//返回结果
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
	public PlaceListResultBean getResult() {
		return result;
	}
	public void setResult(PlaceListResultBean result) {
		this.result = result;
	}
	
	public static class PlaceListResultBean implements Serializable{
		private static final long serialVersionUID = 1L;
		private List<Region> list;
		private int count;
		public List<Region> getList() {
			return list;
		}
		public void setList(List<Region> list) {
			this.list = list;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
	
	public static class Region implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String cid;
		private String cname;
		private List<RegionList> region;
		//是否被选中
		public boolean isCheck;
		public boolean isCheck() {
			return isCheck;
		}
		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
		public String getCname() {
			return cname;
		}
		public void setCname(String cname) {
			this.cname = cname;
		}
		public List<RegionList> getRegion() {
			return region;
		}
		public void setRegion(List<RegionList> region) {
			this.region = region;
		}
	}

	public static class RegionList implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String regionid;
		private String regionname;
		private List<Subregion> subregion;
		public boolean isCheck;
		public boolean isCheck() {
			return isCheck;
		}
		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}
		public String getRegionid() {
			return regionid;
		}
		public void setRegionid(String regionid) {
			this.regionid = regionid;
		}
		public String getRegionname() {
			return regionname;
		}
		public void setRegionname(String regionname) {
			this.regionname = regionname;
		}
		public List<Subregion> getSubregion() {
			return subregion;
		}
		public void setSubregion(List<Subregion> subregion) {
			this.subregion = subregion;
		}
	}
	public static class Subregion implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String subregionid;
		private String subregionname;
		public String getSubregionid() {
			return subregionid;
		}
		public void setSubregionid(String subregionid) {
			this.subregionid = subregionid;
		}
		public String getSubregionname() {
			return subregionname;
		}
		public void setSubregionname(String subregionname) {
			this.subregionname = subregionname;
		}
	}
	
	
}
