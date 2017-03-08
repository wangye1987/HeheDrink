package com.heheys.ec.model.adapter; 

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.controller.activity.SettingBaseActivity;
import com.heheys.ec.controller.fragment.NewAddFragemnt;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.AddressBean;
import com.heheys.ec.model.dataBean.AddressListBean;
import com.heheys.ec.utils.FragmentUtil;
import com.heheys.ec.utils.ProvinceCityCounty;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2015-9-23 下午4:10:50 
 * 类说明 
 * @param
 */
public class AddressAdapter extends BaseListAdapter<AddressListBean>  {
	private Context context;
	private TextView add_name;
	private TextView add_tel;
	private TextView add_isdefault;
	private TextView addressinfo;
	private boolean isEdit;
	private BackDateCallBack callback;
	private CheckBox iv_check;
	private CheckBox checkbox_chioce;
	public AddressAdapter(List<AddressListBean> data, Object obj, Context context) {
		super(data, obj, context);
	}

	public AddressAdapter(List<AddressListBean> data, Context context) {
		// TODO Auto-generated constructor stub
		super(data,  context);
		this.context = context;
	}
	//默认全部不选中状态
	public AddressAdapter(List<AddressListBean> data, Context context,BackDateCallBack callback) {
		// TODO Auto-generated constructor stub
		super(data,  context);
		this.context = context;
		this.callback = callback;
	}
	public void setIsedit(boolean isEdit) {
		this.isEdit = isEdit;
	}
//	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final AddressListBean bean = dataList.get(position);
		if(convertView==null){
			convertView = baseInflater.inflate(R.layout.address_item, parent, false);
		}
		add_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.add_name);
		add_tel = (TextView) ViewHolderUtil.getItemView(convertView, R.id.add_tel);
		add_isdefault = (TextView) ViewHolderUtil.getItemView(convertView, R.id.add_isdefault);
		addressinfo = (TextView) ViewHolderUtil.getItemView(convertView, R.id.addressinfo);
		iv_check = (CheckBox) ViewHolderUtil.getItemView(convertView, R.id.fragment_shopping_cart_item_cb);
		checkbox_chioce = (CheckBox) ViewHolderUtil.getItemView(convertView, R.id.checkbox_chioce);
		if(isEdit){
			  iv_check.setVisibility(View.VISIBLE);
			  iv_check.setChecked(bean.isCheck());
			  iv_check.setFocusable(false);
  		}else{
  			  iv_check.setVisibility(View.GONE);
  		}
		add_name.setText(bean.getName());
		add_tel.setText(bean.getMobile());
		if(bean.getEfdefault()==1){
			add_name.setTextColor(mcontext.getResources().getColor(R.color.red_text_color));
			add_tel.setTextColor(mcontext.getResources().getColor(R.color.red_text_color));
			addressinfo.setTextColor(mcontext.getResources().getColor(R.color.red_text_color));
			checkbox_chioce.setVisibility(View.VISIBLE);
		}else{
			add_name.setTextColor(mcontext.getResources().getColor(R.color.color_2b2b2b));
			add_tel.setTextColor(mcontext.getResources().getColor(R.color.color_2b2b2b));
			addressinfo.setTextColor(mcontext.getResources().getColor(R.color.color_999999));
			add_isdefault.setVisibility(View.GONE);
			checkbox_chioce.setVisibility(View.GONE);
		}
		addressinfo.setText(bean.getProvincename()+bean.getCityname()+bean.getCountyname()+bean.getAddress());
		final CheckBox iv_check_smple = iv_check;
		iv_check.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(iv_check_smple.isChecked()){
					callback.setAddId(bean.getId());
				}else{
					callback.setRemoveId(bean.getId());
				}
			}
		});
	
		return convertView;
	}

	//编辑,删除接口
	public  interface BackDateCallBack{
		void setAddId(int id);
		void setRemoveId(int id);
	}

}
 