package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

public class SalonDetailBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private SalonDetailInfor result;//返回结果
	private ErrorBean error;//返回失败结果
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SalonDetailInfor getResult() {
		return result;
	}

	public void setResult(SalonDetailInfor result) {
		this.result = result;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "SalonDetailBean [status=" + status + ", result=" + result + ", error=" + error + "]";
	}

	public static class SalonDetailInfor implements Serializable{
		
		private static final long serialVersionUID = 1L;
		private int id;
		private String subject ;
		private String address;
		private String guest;
		private String time;
		private String maxnum;
		private String usednum;
		private String content;
		private List<String > piclist;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getGuest() {
			return guest;
		}
		public void setGuest(String guest) {
			this.guest = guest;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getMaxnum() {
			return maxnum;
		}
		public void setMaxnum(String maxnum) {
			this.maxnum = maxnum;
		}
		public String getUsednum() {
			return usednum;
		}
		public void setUsednum(String usednum) {
			this.usednum = usednum;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public List<String> getPiclist() {
			return piclist;
		}
		public void setPiclist(List<String> piclist) {
			this.piclist = piclist;
		}
		@Override
		public String toString() {
			return "SalonDetailInfor [id=" + id + ", subject=" + subject + ", address=" + address + ", guest=" + guest
					+ ", time=" + time + ", maxnum=" + maxnum + ", usednum=" + usednum + ", content=" + content
					+ ", piclist=" + piclist + "]";
		}
		
	}

	
}
