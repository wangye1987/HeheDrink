package com.heheys.ec.model.dataBean; 

import java.io.Serializable;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-22 下午5:10:40 
 * 类说明 
 * @param 图片bean
 */
public class ImageBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//状态码
	private ImageBean result;//返回结果
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
	public ImageBean getResult() {
		return result;
	}
	public void setResult(ImageBean result) {
		this.result = result;
	}
	
	public static class ImageBean  implements Serializable{
	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	//	"imgUrl":"http://101.200.235.7:8080/img/img/aed06737-63a1-4245-bbef-71ced6e8395c.jpg",
//	'imgName':'aed06737-63a1-4245-bbef-71ced6e8395c.jpg'
	private String imgUrl;//图像地址
	private String imgName;//图像名字
	private String baseurl;//图像名字前缀
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	}
}
 