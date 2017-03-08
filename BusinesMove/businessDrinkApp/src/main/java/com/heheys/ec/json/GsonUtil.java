package com.heheys.ec.json;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import android.text.TextUtils;
import android.util.Log;

/**
 * @description: 网络数据解析
 */
public class GsonUtil {

	/**
	 * 通过返回的字符串转实体类
	 * 
	 * @param object
	 * @param t
	 * @return
	 * @throws AppException
	 */
	public static <T> List<T> listJson(String object, Class<T> t)
			 {
		if (TextUtils.isEmpty(object)) {
			return null;
		}
		object = object.trim();
		List<T> objs = new ArrayList<T>();
		try {
			JSONObject json = new JSONObject(object);
			int status = json.optInt("Status");
			if (status == 0) {
				return objs;
			}
			Object data = json.opt("result");
			String curStr = data.toString();
			JsonElement je = new JsonParser().parse(curStr);
			if (je.isJsonArray()) {
				objs = listFromJson(curStr, t);
			}else{
				objs.add(objFromJson(curStr, t));//解析一条数据是封装成集合 wk
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			throw AppException.http(AppException.TYPE_JSON);
			
		}

		return objs;
	}

	/**
	 * 字符串转化成 实体类list
	 * 
	 * @param String
	 * @param Class
	 * @return ArrayList<T>
	 */
	public static <T> List<T> listFromJson(String object, Class<T> t) {
		object = object.trim();
		try {
			if (TextUtils.isEmpty(object)) {
				return null;
			}
			StringReader reader = new StringReader(object);
			JsonReader jsonReader = new JsonReader(reader);
			List<T> objs = new ArrayList<T>();
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				T obj = (new Gson()).fromJson(jsonReader, t);
				objs.add(obj);
			}
			return objs;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> listFromJson1(String object, Class<T> t) {
		object = object.trim();
		try {
			if (TextUtils.isEmpty(object)) {
				return null;
			}
			StringReader reader = new StringReader(object);
			JsonReader jsonReader = new JsonReader(reader);
			List<T> objs = new ArrayList<T>();
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				T obj = (new Gson()).fromJson(jsonReader, t);
				objs.add(obj);
			}
			return objs;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转化成 实体类
	 * 
	 * @param String
	 * @param Class
	 * @return ArrayList<T>
	 */
	public static <T> T objFromJson(String object, Class<T> t) {
		object = object.trim();
		if (TextUtils.isEmpty(object.trim())) {
			return null;
		}
		T obj = new Gson().fromJson(object, t);
		return obj;
	}

	/**
	 * 转化成 实体类
	 * 
	 * @param String
	 * @param Class
	 * @return HttpResultObj
	 */
	public static <T> ResultObj getResultFromHttp(String str, Class<T> t) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}

		ResultObj result = new ResultObj();
		try {
			JSONObject json = new JSONObject(str);
			int status = json.optInt("Status");
//			result.setResult(result_back);
//			result.setError(error_back);
			result.setStatus(status);
			if (status == 0) {//1 正常 0错误
				Object error_back = json.opt("error");
				String error = error_back.toString();
				result.setError(error);
				return result;
			}
			Object result_back = json.opt("result");
			String curStr = result_back.toString();
			JsonElement je = new JsonParser().parse(curStr);
			if (je.isJsonArray()) {
				Object dataT = listFromJson(curStr, t);
				result.setResult(dataT);
			} else if (je.isJsonObject()) {
				Object dataT = objFromJson(curStr, t);
				result.setResult(dataT);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	/*public static <T> HouseObj getHouseFrom(String str) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		HouseObj result = new HouseObj();
		try {
			JSONObject json = new JSONObject(str);
			String msg = json.optString("Msg");
			int status = json.optInt("Status");
			result.setMsg(msg);
			result.setStatus(status);
			if (status == 0) {
				return result;
			}
			Object data = json.opt("Data");
			String curStr = data.toString();
			JsonElement je = new JsonParser().parse(curStr);
			if (je.isJsonArray()) {
				List<House> listhouse = new ArrayList<House>();
				JSONArray array = new JSONArray(curStr);
				for (int i = 0; i < array.length(); i++) {
					House house = new House();
					JSONObject obj = array.getJSONObject(i);
					house.setHousesName(obj.getString("HousesName"));
					house.setHousesID(obj.getInt("HousesID"));
					house.setIsCall(obj.getInt("IsCall"));
					house.setLinkman(obj.getString("Linkman"));
					house.setTel(obj.getString("Tel"));
					house.setHousesCompanyID(obj.getInt("HousesCompanyID"));
					house.setHousesCompanyName(obj
							.getString("HousesCompanyName"));
					if (obj.has("CatePage")) {
						JSONArray CParray = (JSONArray) obj.get("CatePage");
						List<CatePage> listCatePage = new ArrayList<CatePage>();
						for (int j = 0; j < CParray.length(); j++) {
							JSONObject obj1 = CParray.getJSONObject(j);
							CatePage catePage = new CatePage();
							catePage.setAppTitle(obj1.getString("AppTitle"));
							catePage.setHousesPageID(obj1
									.getInt("HousesPageID"));
							catePage.setAppName(obj1.getString("AppName"));
							catePage.setPageID(obj1.getInt("PageID"));
							if (obj1.has("Page")) {
								JSONArray Parray = (JSONArray) obj1.get("Page");
								List<Page> listPage = new ArrayList<Page>();
								for (int k = 0; k < Parray.length(); k++) {
									JSONObject obj2 = Parray.getJSONObject(k);
									Page page = new Page();
									page.setAppName(obj2.getString("AppName"));
									page.setAppTitle(obj2.getString("AppTitle"));
									page.setHousesPageID(obj2
											.getInt("HousesPageID"));
									listPage.add(page);
								}
								catePage.setPage(listPage);
							}
							listCatePage.add(catePage);
						}
						house.setCatePage(listCatePage);
					}
					listhouse.add(house);
				}
				result.setHouse(listhouse);
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	static int HousesIDindex = 0;
	static int buildingIDindex = 0;
	static int housesnumIDindex = 0;
	*//**
	 * 获取联动地址
	 * @param housesbuilding
	 * @param defh
	 * @param defb
	 * @param defhn
	 * @return
	 *//*
	public static AddressMsg getAddList(String housesbuilding,String defh,String defb,String defhn){
		AddressMsg msg = new AddressMsg();
		List<LinkHouses> list = new ArrayList<LinkHouses>();
		try {
			JsonElement je = new JsonParser().parse(housesbuilding);
			if (je.isJsonArray()) {
				JSONArray array = new JSONArray(housesbuilding);
				for (int i = 0; i < array.length(); i++) {
					LinkHouses linkHouses = new LinkHouses();
					JSONObject obj = array.getJSONObject(i);
					linkHouses.setHousesID(obj.getInt("HousesID"));
					linkHouses.setHousesName(obj.getString("HousesName"));
					if(defh!=null&&defh.equals(obj.getInt("HousesID")+"")){
						HousesIDindex = i;
					}
					if (obj.has("Building")) {
						JSONArray buildingarray = (JSONArray) obj.get("Building");
						List<LinkBuilding> listbuilding = new ArrayList<LinkBuilding>();
						for (int j = 0; j < buildingarray.length(); j++) {
							JSONObject objj = buildingarray.getJSONObject(j);
							LinkBuilding lin = new LinkBuilding();
							lin.setBuildingID(objj.getInt("BuildingID"));
							lin.setBuildingName(objj.getString("BuildingName"));
							if(defb!=null&&defb.equals(objj.getInt("BuildingID")+"")){
								buildingIDindex = j;
							}
							if (objj.has("housesNum")) {
								JSONArray housesnumarray = (JSONArray) objj.get("housesNum");
								List<LinkHousesNum> listhousenum = new ArrayList<LinkHousesNum>();
								for (int k = 0; k < housesnumarray.length(); k++) {
									JSONObject objk = housesnumarray.getJSONObject(k);
									LinkHousesNum linknum = new LinkHousesNum();
									linknum.setHousesNumName(objk.getString("housesNumName"));
									linknum.setHousesNumNameID(objk.getString("housesNumNameID"));
									if(defhn!=null&&defhn.equals(objk.getString("housesNumNameID"))){
										housesnumIDindex = k;
									}
									listhousenum.add(linknum);
								}
								lin.setHousesNum(listhousenum);
							}
							listbuilding.add(lin);
						}
						linkHouses.setBuilding(listbuilding);
					}
					list.add(linkHouses);
				}
				msg.setLink(list);
				msg.setHousesIDindex(HousesIDindex);
				msg.setBuildingIDindex(buildingIDindex);
				msg.setHousesnumIDindex(housesnumIDindex);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	*//**
	 * 获取用户信息
	 * 
	 * @param str
	 * @return
	 *//*
	public static <T> ResultObj getUserFrom(String str) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		ResultObj result = new ResultObj();
		try {
			JSONObject json = new JSONObject(str);
			String msg = json.optString("Msg");
			int status = json.optInt("Status");
			result.setMsg(msg);
			result.setStatus(status);
			if (status == 0) {
				return result;
			}
			Object data = json.opt("Data");
			String curStr = data.toString();
			JsonElement je = new JsonParser().parse(curStr);
			if (je.isJsonObject()) {
				User user = new User();
				JSONObject obj = new JSONObject(curStr);
				user.setHousesName(obj.getString("HousesName"));
				user.setHousesID(obj.getString("HousesID"));
				user.setIsAuth(obj.getInt("IsAuth"));
				user.setLastNewsID(obj.getInt("LastNewsID"));
				user.setLinkman(obj.getString("Linkman"));
				user.setMobile(obj.getString("Mobile"));
				user.setNickname(obj.getString("Nickname"));
				user.setPicturePath(obj.getString("PicturePath"));
				user.setTel(obj.getString("Tel"));
				user.setUnitID(obj.getString("UnitID"));
				user.setUserID(obj.getString("UserID"));
				user.setUserName(obj.getString("UserName"));
				user.setUserTel(obj.getString("UserTel"));
				user.setBuildingID(obj.getString("BuildingID"));
				user.setAddress(obj.getString("Address"));
				user.setAccessToken(obj.getString("AccessToken"));
				if (obj.has("CatePage")) {
					JSONArray CParray = (JSONArray) obj.get("CatePage");
					List<CatePage> listCatePage = new ArrayList<CatePage>();
					for (int j = 0; j < CParray.length(); j++) {
						JSONObject obj1 = CParray.getJSONObject(j);
						CatePage catePage = new CatePage();
						catePage.setAppTitle(obj1.getString("AppTitle"));
						catePage.setHousesPageID(obj1.getInt("HousesPageID"));
						catePage.setAppName(obj1.getString("AppName"));
						catePage.setPageID(obj1.getInt("PageID"));
						if (obj1.has("Page")) {
							JSONArray Parray = (JSONArray) obj1.get("Page");
							List<Page> listPage = new ArrayList<Page>();
							for (int k = 0; k < Parray.length(); k++) {
								JSONObject obj2 = Parray.getJSONObject(k);
								Page page = new Page();
								page.setAppName(obj2.getString("AppName"));
								page.setAppTitle(obj2.getString("AppTitle"));
								page.setHousesPageID(obj2
										.getInt("HousesPageID"));
								listPage.add(page);
							}
							catePage.setPage(listPage);
						}
						listCatePage.add(catePage);
					}
					user.setCatePage(listCatePage);
				}
				result.setData(user);
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}*/
}