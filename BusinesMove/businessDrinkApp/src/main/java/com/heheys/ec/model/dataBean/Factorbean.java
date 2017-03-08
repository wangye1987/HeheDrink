package com.heheys.ec.model.dataBean;

import java.io.Serializable;


/**
 * @author wangkui
 * @param 筛选条件的值
 */
public class Factorbean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// heheys://activity/list?type={活动类型(0,1)}
//	&order={排序标识(sales,price)}&winetype=(酒类型ID)
//	&inputstr={搜索关键词}&asc={升序1/降序0}&place={地点ID}&recommend={是否精选(0,1)}&title="xx"&from=app 
	private String type;
	private String order;
	private String winetype;
	private String inputstr;
	private int asc;
	private String place;
	private String title;
	private String recommend;
	private String from;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getWinetype() {
		return winetype;
	}
	public void setWinetype(String winetype) {
		this.winetype = winetype;
	}
	public String getInputstr() {
		return inputstr;
	}
	public void setInputstr(String inputstr) {
		this.inputstr = inputstr;
	}
	public int getAsc() {
		return asc;
	}
	public void setAsc(int asc) {
		this.asc = asc;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
}
