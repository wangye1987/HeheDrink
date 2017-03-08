package com.heheys.ec.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.LoactionBean;

import java.util.List;

/**
 * 作者：wangkui on 2016/12/21 17:58
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class LocationAdapter extends BaseListAdapter<LoactionBean> {

    private TextView tv_title;
    private TextView tv_content;

    public LocationAdapter(List data, Context context) {
        super(data, context);
    }

    public LocationAdapter(List data, Object obj, Context context) {
        super(data, obj, context);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        LoactionBean bean =  dataList.get(position);
        if(convertView == null){
            convertView = baseInflater.inflate(R.layout.location_item,parent,false);
        }
        tv_title = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_title);
        tv_content = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_content);
        tv_title.setText(bean.getNameTitle());
        tv_content.setText(bean.getNameContent());
        return convertView;
    }
}
