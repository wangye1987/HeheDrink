package com.heheys.ec.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.SeachBaseBean.BrandSearchList;

import java.util.List;

/**
 * Created by wangkui on 2016/12/15.
 */

public class BrandGridAdapter extends BaseListAdapter<BrandSearchList> {
    public BrandGridAdapter(List data, Context context) {
        super(data, context);
    }

    public BrandGridAdapter(List data, Object obj, Context context) {
        super(data, obj, context);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        BrandSearchList bean = dataList.get(position);
        if (convertView == null) {
            convertView = baseInflater.inflate(R.layout.grid_item,
                    parent, false);
        }

        ImageView iv = (ImageView) ViewHolderUtil.getItemView(convertView,R.id.image_grid_item);
        TextView tv = (TextView) ViewHolderUtil.getItemView(convertView,R.id.tv_name);
        MyApplication.imageLoader.displayImage(bean.getLogo(), iv,
                MyApplication.options);
        tv.setText(StringUtil.isEmpty(bean.getName())?"":bean.getName());
        return convertView;
    }
}
