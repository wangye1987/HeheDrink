package com.heheys.ec.model.adapter;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.MyCollectActivity;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.CollectBussinessResultBean;
import com.heheys.ec.model.dataBean.CollectBussinessResultBean.CollectBussiness;
import com.heheys.ec.utils.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wangkui on 2016/12/26 16:09
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class BussinessItemAdapter extends BaseListAdapter<CollectBussiness> {
    private MyCollectActivity.MyHandler mHandler;
    //待删除收藏id集合
    private List<String> deletefids;
    //全部选择或者不选只传递一次消息 正常状态是0 选中一次传递一次
    private String isAllCheck = "0";
    public BussinessItemAdapter(List<CollectBussinessResultBean.CollectBussiness> data, Context context, MyCollectActivity.MyHandler mHandler) {
        super(data, context);
        this.mHandler = mHandler;
        deletefids = new ArrayList<>();
    }

    public BussinessItemAdapter(List<CollectBussinessResultBean.CollectBussiness> data, Object obj, Context context) {
        super(data, obj, context);
    }

    private boolean isEdit;
    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }
    //是否全部选择
    public void setAllCheck(Boolean isCheckAll){
          for(CollectBussiness bean:dataList){
              bean.setCheck(isCheckAll);
          }
        isAllCheck = "1";
        notifyDataSetChanged();
        if(!isCheckAll){
            deletefids.clear();
        }else{
            for(CollectBussiness check : dataList){
                if(!deletefids.contains(check.getFid()))
                    deletefids.add(check.getFid());
            }
        }
        Message msg = new Message();
        msg.obj = deletefids;
        msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
        mHandler.sendMessage(msg);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        CollectBussiness bean = dataList.get(position);
        if(convertView == null){
            convertView = baseInflater.inflate(R.layout.bus_collect_item,parent,false);
        }
        CheckBox cb = (CheckBox) ViewHolderUtil.getItemView(convertView,R.id.collect_bus);
        ImageView bus_iv = (ImageView) ViewHolderUtil.getItemView(convertView,R.id.bus_iv);
        TextView bus_name = (TextView) ViewHolderUtil.getItemView(convertView,R.id.bus_name);
        TextView bus_goods_num = (TextView) ViewHolderUtil.getItemView(convertView,R.id.bus_goods_num);
        MyApplication.getInstance().imageLoader.displayImage(bean.getIcon(),bus_iv,MyApplication.options);
        bus_name.setText(bean.getName());
        cb.setChecked(bean.isCheck());
        bus_goods_num.setText("共有商品:"+bean.getPronum());
        if(isAllCheck.equals("0"))
        cb.setOnCheckedChangeListener(new ChioceCb(bean));
        if(isEdit)
            cb.setVisibility(View.VISIBLE);
        else
            cb.setVisibility(View.GONE);
            return convertView;
    }

    private class ChioceCb implements CompoundButton.OnCheckedChangeListener {
        CollectBussiness collectbeanChoice;

        private  ChioceCb(CollectBussiness collectbeanChoice) {
            this.collectbeanChoice = collectbeanChoice;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            AddOrRemove(collectbeanChoice,isChecked);
            Message msg = new Message();
            msg.obj = deletefids;
            msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
            mHandler.sendMessage(msg);
        }
    }

    void AddOrRemove(CollectBussiness collectbeanChoice, boolean isChecked) {
        if (collectbeanChoice != null) {
            collectbeanChoice.setCheck(isChecked);
            if (isChecked) {
                if (!deletefids.contains(collectbeanChoice.getFid())) {
                    deletefids.add(collectbeanChoice.getFid());
                }
            } else {
                if (deletefids.contains(collectbeanChoice.getFid())) {
                    deletefids.remove(collectbeanChoice.getFid());
                }
            }
        }
    }
}
