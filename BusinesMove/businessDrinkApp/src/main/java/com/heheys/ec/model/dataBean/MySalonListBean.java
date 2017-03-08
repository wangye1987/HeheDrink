package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;



public class MySalonListBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private MySalonBean result;// 返回结果
	private ErrorBean error;// 返回失败结果
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MySalonBean getResult() {
		return result;
	}

	public void setResult(MySalonBean result) {
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
		return "MySalonList [status=" + status + ", result=" + result + ", error=" + error + "]";
	}

	public static class MySalonBean implements Serializable{
		private static final long serialVersionUID = 1L;
		private int count;
		private List<MySalonInfor> list;
		
		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<MySalonInfor> getList() {
			return list;
		}

		public void setList(List<MySalonInfor> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "MySalonBean [count=" + count + ", list=" + list + "]";
		}

		public static class MySalonInfor implements Serializable{
			private static final long serialVersionUID = 1L;
			private String id;
			private String pic;
			private String subject;
			private String time;
			private String address;
			private String maxnum;
			private String status;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getPic() {
				return pic;
			}
			public void setPic(String pic) {
				this.pic = pic;
			}
			public String getSubject() {
				return subject;
			}
			public void setSubject(String subject) {
				this.subject = subject;
			}
			public String getTime() {
				return time;
			}
			public void setTime(String time) {
				this.time = time;
			}
			public String getAddress() {
				return address;
			}
			public void setAddress(String address) {
				this.address = address;
			}
			public String getMaxnum() {
				return maxnum;
			}
			public void setMaxnum(String maxnum) {
				this.maxnum = maxnum;
			}
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			@Override
			public String toString() {
				return "MySalonInfor [id=" + id + ", pic=" + pic + ", subject=" + subject + ", time=" + time
						+ ", address=" + address + ", maxnum=" + maxnum + ", status=" + status + "]";
			}
			
		}
	}
}
