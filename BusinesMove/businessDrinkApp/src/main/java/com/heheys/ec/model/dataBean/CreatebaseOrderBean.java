package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-10-16 下午6:29:37 类说明
 * @param
 */
public class CreatebaseOrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String status;// 状态码
	private SidBean result;// 返回结果
	private ErrorBean error;// 返回失败结果

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

	public SidBean getResult() {
		return result;
	}

	public void setResult(SidBean result) {
		this.result = result;
	}
// {"result":{"sid":[22]},"error":{},"status":1}
//	{"result":{"sid":[24,25,26]},"error":{},"status":1}
	public static class SidBean {
			private String oid;
			private double coinamount;//使用喝喝币数量
//			private List<Integer> sid;
			private String goodsname;//商品名称
			private String goodsdesc;//商品描述
			
			public double getCoinamount() {
				return coinamount;
			}

			public void setCoinamount(double coinamount) {
				this.coinamount = coinamount;
			}

			public String getOid() {
				return oid;
			}

			public void setOid(String oid) {
				this.oid = oid;
			}

			public String getGoodsname() {
				return goodsname;
			}

			public void setGoodsname(String goodsname) {
				this.goodsname = goodsname;
			}

			public String getGoodsdesc() {
				return goodsdesc;
			}

			public void setGoodsdesc(String goodsdesc) {
				this.goodsdesc = goodsdesc;
			}

//			public List<Integer> getSid() {
//				return sid;
//			}
//
//			public void setSid(List<Integer> sid) {
//				this.sid = sid;
//			}
	}
	
}
