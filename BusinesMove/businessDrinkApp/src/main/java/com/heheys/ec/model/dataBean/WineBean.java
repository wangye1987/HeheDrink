package com.heheys.ec.model.dataBean;

/**
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-24 下午2:18:08 类说明 酒类需求bean
 * @param
 */
public class WineBean {
	private String wineName;// 需求酒类名称
	private String wineNum;// 需求酒类数量
	private String wineRemark;// 需求备注
	private String wineDate;// 需求日期

	public String getWineName() {
		return wineName;
	}

	public void setWineName(String wineName) {
		this.wineName = wineName;
	}

	public String getWineNum() {
		return wineNum;
	}

	public void setWineNum(String wineNum) {
		this.wineNum = wineNum;
	}

	public String getWineRemark() {
		return wineRemark;
	}

	public void setWineRemark(String wineRemark) {
		this.wineRemark = wineRemark;
	}

	public String getWineDate() {
		return wineDate;
	}

	public void setWineDate(String wineDate) {
		this.wineDate = wineDate;
	}
}
