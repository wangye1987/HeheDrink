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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.BusinessCardBaseBean.BusinessCardBean;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BackRemark;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-16 上午11:34:16 
 * 类说明 
 * @param 
 */
public class BusinessCardAdapter extends BaseListAdapter<BusinessCardBean>{

	private List<BusinessCardBean> data;
	private Context mContext;
	private TextView card_name;
	private TextView card_position;
	private TextView card_company;
	private TextView card_tel;
	private TextView card_weixin;
	private TextView card_address;
	private TextView card_remark;
	private TextView card_landline;
//	private BusinessCardBean bean;
	private Button card_call;
	private Button card_msg;
	private LinearLayout card_linear_remark;
	List<String> list_tel = new ArrayList<String>();//存储电话号码集合
	private BackRemark callback;
	private LinearLayout card_linear;
	HashMap<String,String> map = new HashMap<String,String>();
	public BusinessCardAdapter(List<BusinessCardBean> data, Context context,BackRemark callback) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.callback = callback;
	}
	public void setData(List<BusinessCardBean> data){
		this.dataList = data;
	}
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
//		bean = dataList.get(position);
		if(convertView==null){
			convertView = baseInflater.inflate(R.layout.business_card_item, parent, false);
		}
		card_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_name);
		card_position = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_position);
		card_company = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_company);
		card_tel = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_tel);
		card_weixin = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_weixin);
		card_address = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_address);
		card_remark = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_remark);
		card_landline = (TextView) ViewHolderUtil.getItemView(convertView, R.id.card_landline);
		card_call = (Button) ViewHolderUtil.getItemView(convertView, R.id.bt_call);
		card_msg = (Button) ViewHolderUtil.getItemView(convertView, R.id.bt_msg);
		card_linear_remark = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_remark);
		final ImageView card_iv_remark = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_remark);
		card_iv_remark.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				map.put("card_management_list_note","");
				MobclickAgent.onEvent(mContext, "card_management_list_note", map); 	
				new AlertDialogCustom().CreateDialog((Activity) mcontext,null,null,list_tel,3,callback,dataList.get(position).getId(),dataList.get(position).getRemark());
			}
		});
		card_linear_remark.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				map.put("card_management_list_note","");
				MobclickAgent.onEvent(mContext, "card_management_list_note", map); 	
				new AlertDialogCustom().CreateDialog((Activity) mcontext,null,null,list_tel,3,callback,dataList.get(position).getId(),dataList.get(position).getRemark());
			}
		});
		card_linear = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_landline);
		card_name.setText(dataList.get(position).getName());
		card_position.setText(dataList.get(position).getPosition());
		card_company.setText(dataList.get(position).getCompany());
		String sttel = dataList.get(position).getMobile();
		if(sttel.contains(",")){
			String landline[] = sttel.split(",");
			if(landline.length>2){
				card_tel.setText(sttel.substring(0, sttel.lastIndexOf(","))+"\n"+sttel.substring(sttel.lastIndexOf(",")+1,sttel.length()));
			}else{
				card_tel.setText(sttel);
			}
		}else{
			card_tel.setText(sttel);
		}
		card_weixin.setText(dataList.get(position).getWeixin());
		card_address.setText(dataList.get(position).getAddress());
		if(dataList.get(position).getRemark()!=null){
			card_remark.setText(dataList.get(position).getRemark().equals("")?"暂无备注":dataList.get(position).getRemark());
		}else{
			card_remark.setText("暂无备注");
		}
		if(dataList.get(position).getLandline().equals("") || dataList.get(position).getLandline()==null){
			card_linear.setVisibility(View.GONE);
		}else{
			card_linear.setVisibility(View.VISIBLE);
			String stland = dataList.get(position).getLandline();
			if(stland.contains(",")){
				String landline[] = stland.split(",");
				if(landline.length>2){
					card_landline.setText(stland.substring(0, stland.lastIndexOf(","))+"\n"+stland.substring(stland.lastIndexOf(",")+1,stland.length()));
				}else{
					card_landline.setText(stland);
				}
			}else{
				card_landline.setText(stland);
			}
		}
		//调取系统拨打电话界面
		card_call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				map.put("card_management_list_call","");
				MobclickAgent.onEvent(mContext, "card_management_list_call", map);
				list_tel.clear();
				GetTelList(position,1);
				// TODO Auto-generated method stub
				new AlertDialogCustom().CreateDialog((Activity) mcontext,null,null,list_tel,1);
			}
		});
		//调取系统发生短信界面
		card_msg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				map.put("card_management_list_message","");
				MobclickAgent.onEvent(mContext, "card_management_list_message", map);
				list_tel.clear();
				GetTelList(position,2);
				new AlertDialogCustom().CreateDialog((Activity) mcontext,null,null,list_tel,2);
			}
		});
		
		return convertView;
	}
	private void GetTelList(final int currtposition,int id) {
		if(dataList.get(currtposition).getMobile().contains(",")){
			String mobileArray[] = dataList.get(currtposition).getMobile().split(",");
			for(int i=0;i<mobileArray.length;i++){
				list_tel.add(mobileArray[i]);
			}
		}else{
			list_tel.add(dataList.get(currtposition).getMobile());
		}
		if(id==2)
			return;
		if(dataList.get(currtposition).getLandline().equals("") ||dataList.get(currtposition).getLandline()==null)
			return;
		if(dataList.get(currtposition).getLandline().contains(",")){
			String mobileArray[] = dataList.get(currtposition).getLandline().split(",");
			for(int i=0;i<mobileArray.length;i++){
				list_tel.add(mobileArray[i]);
			}
		}else{
			list_tel.add(dataList.get(currtposition).getLandline());
		}
	}
}
 