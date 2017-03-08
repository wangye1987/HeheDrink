package com.heheys.ec.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.heheys.ec.model.dataBean.CityChioceBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.Bean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.CityBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.County;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.ProvinceList;
/**
 * 
 * 获取城市ID 对应的中文地址
 * 
 *  @author wangkui
 * 
 * */
public class ProvinceCityCounty {

	private  Bean province;
	//对应的省份名称
	private  String tv_province;
	//对应的城市名称
	private  String tv_city;
	//对应的地区名称
	private  String tv_county;
	private static final int INDEX_ONE = 1;
	private static final int INDEX_TWO = 2;
	private static final int INDEX_THREE = 3;
	private List<CityChioceBean> list = new  ArrayList<CityChioceBean>(); ;
	public ProvinceCityCounty(final Context mContext,final MyCallBack callback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				province = (Bean) SharedPreferencesUtil.getObject(mContext, "Province");
				callback.setDate(province);
			}
		}).start();
	}
	
	public ProvinceCityCounty(){
		
	}
	public  String GetAddress( int province_id,int city_id,int county_id){
		//获取省市城市元数据
//				1 12 12
				List<ProvinceList> listAddress = province.getList();
				if(listAddress != null){
				for(ProvinceList province : listAddress){
					if(province.getProvince().getId() == province_id){
						tv_province = province.getProvince().getName();
						 List<CityBean> listCity = province.getProvince().getCity();
						for(CityBean citybean:listCity){
							if(citybean.getId()==city_id){
								tv_city = citybean.getName();
								 List<County> listCounty = citybean.getCounty();
								 for(County countyBean:listCounty){
									 if(countyBean.getId()==county_id){
										 tv_county = countyBean.getName(); 
										 break;
									 }
								 }
								 break;
							}
						}
						break;
					}
				}}
				return tv_province+tv_city+tv_county;
	}
	
	//获取省份名称集合
	public String GetProviceName(int province_id,Bean province){
		List<ProvinceList> listAddress = province.getList();
		if(listAddress != null){
			for(ProvinceList provinceList : listAddress){
				if(provinceList.getProvince().getId() == province_id){
					tv_province = provinceList.getProvince().getName();
				}
			}}
		return tv_province;
	}
	//获取城市名称集合
	public List<String> GetCityName(int province_id, List<String> city_id,Bean province){
		List<String> list_city = new ArrayList<String>();
		List<ProvinceList> listAddress = province.getList();
		if(listAddress != null){
		for(ProvinceList provincesList : listAddress){
			if(provincesList.getProvince().getId() == province_id){
				tv_province = provincesList.getProvince().getName();
				 List<CityBean> listCity = provincesList.getProvince().getCity();
				for(CityBean citybean:listCity){
					for(String it:city_id){
					if((citybean.getId()+"").equals(it)){
						tv_city = citybean.getName();
						list_city.add(tv_city);
					  }
					}
				}
				break;
			}
		}}
		return list_city;
	}
	
	/*
	 * 获取省份中文
	 * */
	public String GetProvicnce(int province_id){
		List<ProvinceList> listAddress = province.getList();
		for(ProvinceList province : listAddress){
			if(province.getProvince().getId() == province_id){
				tv_province = province.getProvince().getName();
				break;
			}
		}
		return tv_province;
	}
	/*
	 * 获取城市中文
	 * */
	public String GetCity(int city_id){
		List<ProvinceList> listAddress = province.getList();
		for(ProvinceList province : listAddress){
				 List<CityBean> listCity = province.getProvince().getCity();
				for(CityBean citybean:listCity){
					if(citybean.getId()==city_id){
						tv_city = citybean.getName();
						 break;
					}
			}
		}
		return tv_city;
	}
	/*
	 * 获取区县中文
	 * */
	public String GetCounty(int county_id){
		List<ProvinceList> listAddress = province.getList();
		for(ProvinceList province : listAddress){
				tv_province = province.getProvince().getName();
				 List<CityBean> listCity = province.getProvince().getCity();
				for(CityBean citybean:listCity){
						tv_city = citybean.getName();
						 List<County> listCounty = citybean.getCounty();
						 for(County countyBean:listCounty){
							 if(countyBean.getId()==county_id){
								 tv_county = countyBean.getName(); 
								 break;
							 }
						 }
				}
		}
		return tv_county;
	}
	/**
	 * 
	 * 获取选择的城市 省市 地区列表
	 * 
	 * */
	public List<CityChioceBean> getList(Bean provinceBean,int flag,int id){
		List<ProvinceList> addressbean = provinceBean.getList();
		if(addressbean==null)
			return null;
		switch (flag) {
		case INDEX_ONE:
			list.clear();
			 for(ProvinceList pro:addressbean){
				 CityChioceBean bean = new CityChioceBean();
				 bean.setId(pro.getProvince().getId());
				 bean.setName(pro.getProvince().getName());
				 bean.setPinyin(pro.getProvince().getPinyin());
				 list.add(bean);
			 }
			return list;
		case INDEX_TWO:
			list.clear();
			for(ProvinceList pro:addressbean){
				if(pro.getProvince().getId() == id){
				List<CityBean> citylist = pro.getProvince().getCity();
				for(CityBean city:citylist){
					CityChioceBean bean = new CityChioceBean();
					bean.setId(city.getId());
					bean.setName(city.getName());
					bean.setPinyin(city.getPinyin());
				    list.add(bean);
					}
				}
			}
			return list;
		case INDEX_THREE:
			list.clear();
			for(ProvinceList pro:addressbean){
				List<CityBean> citylist = pro.getProvince().getCity();
				for(CityBean city:citylist){
					List<County> countylist = city.getCounty();
					if(city.getId() == id){
					for(County county:countylist){
						CityChioceBean bean = new CityChioceBean();
						bean.setId(county.getId());
						bean.setName(county.getName());
						bean.setPinyin(county.getPinyin());
						list.add(bean);
					}
					}
				}
			}
			return list;
		default:
			break;
		}
		return null;
	}
	
	public interface MyCallBack{
		void setDate(Bean province);
	}
}
