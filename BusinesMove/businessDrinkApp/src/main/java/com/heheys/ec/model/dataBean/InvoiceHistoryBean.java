package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

public class InvoiceHistoryBean implements Serializable {

	/**
	 * @author wangkui 
	 * 
	 * @param 发票数据bean
	 */
	private static final long serialVersionUID = 1L;

	private String status;// 状态码
	private Invoice result;// 返回结果
	private ErrorBean error;// 返回失败结果

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(final ErrorBean error) {
		this.error = error;
	}

	public Invoice getResult() {
		return result;
	}

	public void setResult(final Invoice result) {
		this.result = result;
	}

	public static class Invoice implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private InvoiceHistory invoice;
		private String invoiceinfo;
		public InvoiceHistory getInvoice() {
			return invoice;
		}
		public void setInvoice(InvoiceHistory invoice) {
			this.invoice = invoice;
		}
		public String getInvoiceinfo() {
			return invoiceinfo;
		}
		public void setInvoiceinfo(String invoiceinfo) {
			this.invoiceinfo = invoiceinfo;
		}
		
	}
	public static class InvoiceHistory implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//发票类型 1 普通发票 2增值税发票
		private int invoicetype;
		//发票title
		private String invoicetitle;
		//发票内容 1 明细 2酒水
		private int invoicecontent;
		//发票抬头 
		private List<String> historytitle;
		//发票抬头 1 个人 2单位
		private int inbuertype;
		//发票tips
		private String tips;
		//发票notice
		private String notice;
		
		public int getInvoicetype() {
			return invoicetype;
		}
		public void setInvoicetype(int invoicetype) {
			this.invoicetype = invoicetype;
		}
		public String getInvoicetitle() {
			return invoicetitle;
		}
		public void setInvoicetitle(String invoicetitle) {
			this.invoicetitle = invoicetitle;
		}
		public int getInvoicecontent() {
			return invoicecontent;
		}
		public void setInvoicecontent(int invoicecontent) {
			this.invoicecontent = invoicecontent;
		}
		public List<String> getHistorytitle() {
			return historytitle;
		}
		public void setHistorytitle(List<String> historytitle) {
			this.historytitle = historytitle;
		}
		public int getInbuertype() {
			return inbuertype;
		}
		public void setInbuertype(int inbuertype) {
			this.inbuertype = inbuertype;
		}
		public String getTips() {
			return tips;
		}
		public void setTips(String tips) {
			this.tips = tips;
		}
		public String getNotice() {
			return notice;
		}
		public void setNotice(String notice) {
			this.notice = notice;
		}

	}

	
}
