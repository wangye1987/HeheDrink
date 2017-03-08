package com.heheys.ec.model.dataBean;

import java.io.Serializable;


/**
 * @author wangkui
 *
 *  酒讯数据模型bean
 */
public class DrinkInfoBean implements Serializable{
//	id: 12
//	title:””标题
//	createtime:”2016-03-15” 发表时间
//	author:”jack” 作者
//	content:” 春的风抖落了冬日晶莹剔透的雪。。。” 内容
//	url:” 春的风抖落了冬日晶莹剔透的雪。。。” 内容
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String title;
	String createtime;
	String author;
	String content;
	String url;
	String h5Url;//详情界面
	public String getH5Url() {
		return h5Url;
	}
	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "DrinkInfoBean [id=" + id + ", title=" + title + ", createtime="
				+ createtime + ", author=" + author + ", content=" + content
				+ ", url=" + url + "]";
	}
	
}
