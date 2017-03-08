/**
 * 
 */
package com.heheys.ec.model.dataBean;

import java.io.Serializable;


/**
 * @author wangkui
 * 2016-4-29上午10:04:51
 * @version 1.0
 * 微信支付鉴权结果
 */
public class WXPayBaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private Result result;//返回结果
	private ErrorBean error;//返回失败结果
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}

	public class Result implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private weChat wechat;
		public weChat getWechat() {
			return wechat;
		}
		public void setWechat(weChat wechat) {
			this.wechat = wechat;
		}
		
	}
	public static class weChat implements Serializable{

		/**
		 *  "sign": "", //签名
            "timestamp": "1461835231430",//时间戳
            "noncestr": "", /./随机字符串
            "partnerid": "",  //微信支付分配的商户号
            "prepayid": "",//商户id
            "package": "Sign=WXPay",//固定值
            "appid": ""//开放平台应用appid
		 */
		private static final long serialVersionUID = 1L;
		
		private String sign;
		private String timestamp;
		private String noncestr;
		private String partnerid;
		private String prepayid;
		private String packages;
		private String appid;
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getNoncestr() {
			return noncestr;
		}
		public void setNoncestr(String noncestr) {
			this.noncestr = noncestr;
		}
		public String getPartnerid() {
			return partnerid;
		}
		public void setPartnerid(String partnerid) {
			this.partnerid = partnerid;
		}
		public String getPrepayid() {
			return prepayid;
		}
		public void setPrepayid(String prepayid) {
			this.prepayid = prepayid;
		}
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getPackages() {
			return packages;
		}
		public void setPackages(String packages) {
			this.packages = packages;
		}
		
	}
}
