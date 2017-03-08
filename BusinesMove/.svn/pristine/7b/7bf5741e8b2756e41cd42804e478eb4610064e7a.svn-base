package com.heheys.ec.model.dataBean; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-12-14 下午3:42:44 
 * 类说明 
 * @param 
 */
public class AddressJson implements Serializable{

	/**
	 * 
	 */
//	[
//	    {
//	        "7": [
//	            100,
//	            101,
//	            99,
//	            98
//	        ]
//	    },
//	    {
//	        "11": [
//	            153,
//	            154,
//	            155,
//	            152
//	        ]
//	    }
//	]
//	{"4":[53,54,55]}
	private static final long serialVersionUID = 1L;
	private List<CityBean> obj;
	public List<CityBean> getObj() {
		return obj;
	}
	
	public void setObj(List<CityBean> obj) {
		this.obj = obj;
	}

	public static class  CityBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Map<String,List<Integer>> map;

		public Map<String, List<Integer>> getMap() {
			return map;
		}

		public void setMap(Map<String, List<Integer>> map) {
			this.map = map;
		}
	}
	
}
 