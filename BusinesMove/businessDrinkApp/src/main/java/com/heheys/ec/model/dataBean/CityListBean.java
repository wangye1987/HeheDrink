package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:城市列表对应数据实体
 * 
 * Date:2015-10-8
 * 
 * Author:liuzhouliang
 */
public class CityListBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;// 状态码
	private CityDataList result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CityDataList getResult() {
		return result;
	}

	public void setResult(CityDataList result) {
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
		return "CityListBean [status=" + status + ", result=" + result
				+ ", error=" + error + "]";
	}

	public static class CityDataList implements Serializable {

		private static final long serialVersionUID = 1L;
		private List<CityData> citylist;

		@Override
		public String toString() {
			return "CityDataList [list=" + citylist + "]";
		}

		public List<CityData> getList() {
			return citylist;
		}

		public void setList(List<CityData> list) {
			this.citylist = list;
		}

		public static class CityData implements Serializable {
			private static final long serialVersionUID = 1L;
			private String id;
			private String name;
			private int hot;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getHot() {
				return hot;
			}

			public void setHot(int hot) {
				this.hot = hot;
			}

			@Override
			public String toString() {
				return "CityData [id=" + id + ", name=" + name + ", hot=" + hot
						+ "]";
			}

		}

	}

}
