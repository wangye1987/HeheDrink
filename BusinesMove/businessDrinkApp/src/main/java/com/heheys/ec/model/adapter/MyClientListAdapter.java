package com.heheys.ec.model.adapter;

import java.util.List;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.model.dataBean.LinkManListBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-9-22 下午1:57:41 类说明
 * @param
 */
public class MyClientListAdapter extends BaseListAdapter<LinkManListBean> {

	private List<LinkManListBean> data;
	private Context context;

	public MyClientListAdapter(List<LinkManListBean> data, Object obj,
			Context context) {
		super(data, obj, context);
		// TODO Auto-generated constructor stub
	}

	public MyClientListAdapter(List<LinkManListBean> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.data = data;
		this.context = context;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView!=null){
			convertView = baseInflater.inflate(R.layout.order_item, parent, false);
		}
		return null;
	}

}
