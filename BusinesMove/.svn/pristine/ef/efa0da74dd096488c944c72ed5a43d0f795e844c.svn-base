
package com.heheys.ec.model.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.CustomerBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:
 *
 * Date:2015-9-28
 *
 * Author:liuzhouliang
 */
public class MyCustomerAdapter extends BaseListAdapter<CustomerBean> {
//	List<CustomerBean> data;
	Context context;
	boolean isEdit;
	ClickCallBack callback;
	boolean iscancle ;
	List<String> list_tel = new ArrayList<String>();//存储电话号码集合
	public MyCustomerAdapter(List<CustomerBean> data, Context context,ClickCallBack callback) {
		super(data, context);
		// TODO Auto-generated constructor stub
//		this.data = data;
		this.context = context;
		this.callback = callback;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}
	
	public void isEdit(boolean isEdit){
		this.isEdit = isEdit;
	}
	
	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		final CustomerBean bean =  dataList.get(position);
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.my_customer_item, parent,
					false);
		}
		LinearLayout linear_remark = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_remark);
		LinearLayout linear_check = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_check);
		LinearLayout linear_tel2 = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_tel2);
		LinearLayout linear_tel3 = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_tel3);
		LinearLayout linear_tel4 = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_tel4);
		LinearLayout linear_tel5 = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_tel5);
		LinearLayout linear_tel = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_tel);
		TextView tv_shop = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_shop);
		TextView tv_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_name);
		TextView tv_tel1 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_tel1);
		TextView tv_tel2 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_tel2);
		TextView tv_tel3 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_tel3);
		TextView tv_tel4 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_tel4);
		TextView tv_tel5 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_tel5);
		ImageView iv_mobile = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_mobile);
		TextView tv_address = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_address);
		TextView tv_remark = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_remark);
		CheckBox iv_check = (CheckBox) ViewHolderUtil.getItemView(convertView, R.id.iv_check);
		final CheckBox  iv= iv_check;
		tv_shop.setText("1".equals(bean.getBtype())?"门店":"超市");
		tv_name.setText(bean.getName());
		final String mobile = bean.getMobile();
		if(mobile.contains(",")){
			String mobilestring[] = mobile.split(",");
			for(int i=0;i<mobilestring.length;i++){
				if(i==0){
					tv_tel1.setText(mobilestring[i]);
				}else if(i==1){
					tv_tel2.setText(mobilestring[i]);
					linear_tel2.setVisibility(View.VISIBLE);
				}else if(i==2){
					tv_tel3.setText(mobilestring[i]);
					linear_tel3.setVisibility(View.VISIBLE);
				}else if(i==3){
					tv_tel4.setText(mobilestring[i]);
					linear_tel4.setVisibility(View.VISIBLE);
				}else if(i==4){
					tv_tel5.setText(mobilestring[i]);
					linear_tel5.setVisibility(View.VISIBLE);
				}
				
			}
		}else{
			tv_tel1.setText(mobile);
			
		}
		tv_address.setText(bean.getAddress());
		iv_check.setChecked(bean.isCheck());
		iv_check.setFocusable(false);
		if(!bean.getRemark().equals("") && bean.getRemark()!=null ){
			tv_remark.setText(bean.getRemark());
		}else{
			linear_remark.setVisibility(View.GONE);
		}
		if(isEdit){
			iv_check.setVisibility(View.VISIBLE);
			linear_tel.setVisibility(View.GONE);
		}else{
			linear_tel.setVisibility(View.VISIBLE);
			iv_check.setVisibility(View.INVISIBLE);
		}
		iv_check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iscancle = iv.isChecked();
				bean.setCheck(iscancle);
				callback.setId(bean.getId(),iscancle,dataList);
			}
		});
		linear_check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iscancle = iv.isChecked();
				bean.setCheck(iscancle);
				callback.setId(bean.getId(),iscancle,dataList);
			}
		});
		iv_mobile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("call","");
				MobclickAgent.onEvent(mcontext, "0091", map); 
				list_tel.clear();
				if(mobile.contains(",")){
					String mobileArray[] = mobile.split(",");
					for(int i=0;i<mobileArray.length;i++){
						list_tel.add(mobileArray[i]);
					}
				}else{
						list_tel.add(mobile);
				}
				
				ShowDialog(list_tel,bean.getName(),"1".equals(bean.getBtype())?"门店":"超市");
				list_tel.clear();
			}
		});
		return convertView;
	}
	private void ShowDialog(List<String> list_tel,String linkName,String shopName) {
		new AlertDialogCustom().CreateDialog((Activity) mcontext,linkName,shopName,list_tel,0);
	}
	/**
	 * 
	 * 点击是否删除回调接口
	 * */
	public  interface ClickCallBack{
		void setId(String id, boolean isCancle, List<CustomerBean> data);
	}
}

