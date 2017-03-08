package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.ArrayList;


public class PointsBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private PointsBean result;//返回结果
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
	public PointsBean getResult() {
		return result;
	}
	public void setResult(PointsBean result) {
		this.result = result;
	}
	/*
	result:{
		pointstotal:"1000",//积分 
		pointshelp:"www.heheys.com",
		isremark:"true",//是否签到
		isreceive:"2",//今天领取的积分
		list:
		   [  {
		   pointnum:"10",
		   pointdetail:"签到积分",
		   time:"2016.07.07"
		   }],
		
		
	}*/
	public class PointsBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<Poinitem> list;
		public ArrayList<Poinitem> getList() {
			return list;
		}
		public void setList(ArrayList<Poinitem> list) {
			this.list = list;
		}
		
	}
	
	
	public class Poinitem implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int score;
		private String sourcesInfo;
		private String createTime;
		private String sourcesType;
		
		public String getSourcesType() {
			return sourcesType;
		}
		public void setSourcesType(String sourcesType) {
			this.sourcesType = sourcesType;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public String getSourcesInfo() {
			return sourcesInfo;
		}
		public void setSourcesInfo(String sourcesInfo) {
			this.sourcesInfo = sourcesInfo;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
	}
}
