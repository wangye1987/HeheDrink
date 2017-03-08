package com.heheys.ec.model.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.BaseRecordBean.MyRecordBean;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-28 下午3:58:56
 *  类说明
 */
public class RecordListAdapter extends BaseListAdapter<MyRecordBean> {

	private View view;
	private TextView tv_title;
	private TextView tv_time;
	private TextView tv_notice;
	private TextView tv_money;
	private TextView tv_review;
	private MyRecordBean bean;
	private List<MyRecordBean> data; 
	public RecordListAdapter(List<MyRecordBean> data, Context context) {
		super(data, context);
		this.data =data;
	}

	public void setData(List<MyRecordBean> data){
		this.data = data;
	}
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView== null){
			convertView = baseInflater.inflate(R.layout.record_item_item, parent, false);
		}
		bean = data.get(position);
		tv_title = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_title);
		tv_time = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_time);
		tv_notice = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_notice);
		tv_money = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_money);
		tv_review = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_review);
	     if(bean.getCtime()!=null)
		tv_time.setText(bean.getCtime());
		tv_notice.setText(switchStatus(bean.getStatus()));
		if(bean.getAmount()!=null)
		tv_money.setText(bean.getAmount());
//		1发放失败；0待审核 ；1审核通过  2已发放 3审核不通过
		if(!StringUtil.isEmpty(bean.getRemark())){
			tv_review.setVisibility(View.VISIBLE);
			tv_review.setText("原因 : "+bean.getRemark());
		}else{
			tv_review.setVisibility(View.GONE);
		}
		return convertView;
	}
//	FAIL(-2,"提现失败"),
//	AUDIT_UNPASS(-1,"审核不通过"),
//	AUDITING(0,"审核中"),
//	SUBMITE_FINACIAL(1,"已提交财务"),
//	SUCCESS(2,"提现成功");
	 String switchStatus(int status){
		switch (status) {
		case -1:
			return "审核不通过";
		case -2:
			return "提现失败";
		case 0:
			return "审核中";
		case 1:
			return "已提交财务";
		case 2:
			return "提现成功";

		default:
			break;
		}
		return "";
	}
}
