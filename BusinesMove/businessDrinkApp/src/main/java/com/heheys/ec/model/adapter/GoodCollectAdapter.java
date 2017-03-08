package com.heheys.ec.model.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.MyCollectActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.CollectGoodsResultBean.CollectGoodResultListBean.CollectGoods;
import com.heheys.ec.utils.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wangkui on 2016/12/26 11:13
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class GoodCollectAdapter extends BaseListAdapter<CollectGoods> {
    //合伙买视图
    private static final int HHM_LAYOUT = 0;
    //特卖视图
    private static final int SM_LAYOUT = 1;
    //E发行视图
    private static final int YFX_LAYOUT = 2;


    //是否编辑
    private boolean isEdit = false;
    private int returnLayout;
    private int TOTAL = 3;
    private MyCollectActivity.MyHandler mHandler;
    //待删除收藏id集合
    private List<String> deletefids;
    //全部选择或者不选只传递一次消息 正常状态是0 选中一次传递一次
    private String isAllCheck = "0";
    public GoodCollectAdapter(List<CollectGoods> data, Context context, MyCollectActivity.MyHandler mHandler) {
        super(data, context);
        this.mHandler = mHandler;
        deletefids = new ArrayList<>();
    }

    public GoodCollectAdapter(List<CollectGoods> data, Object obj, Context context) {
        super(data, obj, context);
    }
    //是否全部选择
    public void setAllCheck(Boolean isCheckAll){
        for(CollectGoods bean:dataList){
            bean.setCheck(isCheckAll);
        }
        isAllCheck = "1";
        notifyDataSetChanged();
        if(!isCheckAll){
            deletefids.clear();
        }else{
            for(CollectGoods check : dataList){
                if(!deletefids.contains(check.getFid()))
                deletefids.add(check.getFid());
            }
        }
        Message msg = new Message();
        msg.obj = dataList;
        msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
        mHandler.sendMessage(msg);
    }
    @Override
    public int getItemViewType(int position) {
//        0:合伙 1:特卖 3:发行价
        String type = dataList.get(position).getType();
        if ("0".equals(type)) {
            returnLayout = HHM_LAYOUT;
        } else if ("1".equals(type)) {
            returnLayout = SM_LAYOUT;
        } else if ("3".equals(type)) {
            returnLayout = YFX_LAYOUT;
        }
        return returnLayout;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL;
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {

        CollectGoods collectbean = dataList.get(position);
        int viewType = getItemViewType(position);
        if (convertView == null) {
            switch (viewType) {
                case SM_LAYOUT:
                    convertView = baseInflater.inflate(R.layout.search_tm_item, parent, false);
                    break;
                case HHM_LAYOUT:
                    convertView = baseInflater.inflate(R.layout.search_hhm_item, parent, false);
                    break;
                case YFX_LAYOUT:
                    convertView = baseInflater.inflate(R.layout.search_yfx_item, parent, false);
                    break;

            }
        }
        if (SM_LAYOUT == viewType) {
            //特卖视图
            CheckBox collect_all_tm = (CheckBox) ViewHolderUtil.getItemView(convertView, R.id.collect_all_tm);
            TextView tm_xl = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tm_xl);
            TextView tm_kc = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tm_kc);
            ImageView tm_goods = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.tm_goods);
            ImageView tm_tip = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.tm_tip);
            tm_xl.setText(StringUtil.isEmpty(collectbean.getSoldnum())?"":collectbean.getSoldnum());
            tm_kc.setText(StringUtil.isEmpty(collectbean.getKcnum())?"":collectbean.getKcnum());
            MyApplication.getInstance().imageLoader.displayImage(collectbean.getPic(), tm_goods, MyApplication.options);
            setIsEdit(collect_all_tm);
            collect_all_tm.setChecked(collectbean.isCheck());
            if(isAllCheck.equals("0")) {
                collect_all_tm.setOnClickListener(null);
                collect_all_tm.setOnClickListener(new ChioceCb(collectbean,collect_all_tm));
            }
            if(!collectbean.getStatus().equals("1"))
                tm_tip.setImageResource(R.drawable.collect_ysx);
            else
                tm_tip.setImageResource(R.drawable.result_tm);
        } else if (HHM_LAYOUT == viewType) {
            //合伙买视图
            CheckBox collect_all_hhm = (CheckBox) ViewHolderUtil.getItemView(convertView, R.id.collect_all_hhm);
            TextView hhm_ys = (TextView) ViewHolderUtil.getItemView(convertView, R.id.hhm_ys);
            TextView hhm_kc = (TextView) ViewHolderUtil.getItemView(convertView, R.id.hhm_kc);
            TextView tv_jjx = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_jjx);
            ImageView iv_hhm = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_hhm);
            ImageView hhm_tip = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.hhm_tip);
            ProgressBar hhm_pb = (ProgressBar) ViewHolderUtil.getItemView(convertView, R.id.hhm_pb);
            hhm_ys.setText(collectbean.getSoldnum() + "/");
            hhm_kc.setText((Integer.parseInt(collectbean.getKcnum())+Integer.parseInt(collectbean.getSoldnum())) + collectbean.getUnit());
            collect_all_hhm.setChecked(collectbean.isCheck());
            MyApplication.getInstance().imageLoader.displayImage(collectbean.getPic(), iv_hhm, MyApplication.options_banner);
            //进度条
            if (!StringUtil.isEmpty(collectbean.getSoldnum())
                    && !StringUtil.isEmpty(collectbean.getKcnum())) {
                float proportion = (Float.parseFloat(collectbean
                        .getSoldnum()))
                        / (float) (Integer.parseInt(collectbean
                        .getKcnum()));
                int progress = (int) (proportion * 100);
//                Resources res = mcontext.getResources();
//                hhm_pb.setProgressDrawable(res
//								.getDrawable(R.drawable.good_pb));
//                        .getDrawable(R.drawable.progress_color_yellow));
                hhm_pb.setProgress(progress);
            }
            setIsEdit(collect_all_hhm);
            if(isAllCheck.equals("0")) {
                collect_all_hhm.setOnClickListener(null);
                collect_all_hhm.setOnClickListener(new ChioceCb(collectbean,collect_all_hhm));
            }
            if(!collectbean.getStatus().equals("1"))
                hhm_tip.setImageResource(R.drawable.collect_ysx);
            else
                hhm_tip.setImageResource(R.drawable.result_hhm);

            if("2".equals(collectbean.getStatus())){
                tv_jjx.setText("已成单");
            }else if("12".equals(collectbean.getStatus())){
                tv_jjx.setText("未成单");
            }else if("1".equals(collectbean.getStatus())){
                tv_jjx.setText("进行中");
            }
        } else if (YFX_LAYOUT == viewType) {
            //E发行视图
            CheckBox collect_all_fx = (CheckBox) ViewHolderUtil.getItemView(convertView, R.id.collect_all_yfx);
            TextView yfx_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.yfx_price);
            TextView fxj_area = (TextView) ViewHolderUtil.getItemView(convertView, R.id.fxj_area);
            TextView yfx_time = (TextView) ViewHolderUtil.getItemView(convertView, R.id.yfx_time);
            LinearLayout linear_time = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_time);
            TextView fxj = (TextView) ViewHolderUtil.getItemView(convertView, R.id.fxj);
            TextView textView100 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.textView100);
            TextView textView10 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.textView10);
            TextView textView9 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.textView9);
            ImageView iv_yfx = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_yfx);
            ImageView yfx_tip = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.yfx_tip);
            yfx_price.setText("¥ "+collectbean.getPrice() + "/" + collectbean.getUnit());
            fxj_area.setText(collectbean.getReleaseArea());

            textView10.setText(collectbean.getSoldnum());
            textView9.setText(collectbean.getUnit());
            collect_all_fx.setChecked(collectbean.isCheck());
            MyApplication.getInstance().imageLoader.displayImage(collectbean.getPic(), iv_yfx, MyApplication.options);
            setIsEdit(collect_all_fx);
            if(isAllCheck.equals("0")) {
                collect_all_fx.setOnClickListener(null);
                collect_all_fx.setOnClickListener(new ChioceCb(collectbean,collect_all_fx));
            }
            if(collectbean.getStatus().equals("0")) {
                yfx_tip.setImageResource(R.drawable.collect_ysx);
             }else if(collectbean.getStatus().equals("1")){
                    yfx_tip.setImageResource(R.drawable.result_yfx);
                }

            if(!StringUtil.isEmpty(collectbean.getDiffTime())){
                yfx_time.setText(collectbean.getDiffTime());
                linear_time.setVisibility(View.VISIBLE);
                fxj.setText("预售价 ");
                textView100.setText("已预售 ");
            }else{
                linear_time.setVisibility(View.GONE);
                fxj.setText("发行价 ");
                textView100.setText("已发行 ");
            }
        }

        final int currtPosition = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataList.get(currtPosition).getStatus().equals("1")) {
                    Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
                    intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, dataList.get(currtPosition).getId());
                    StartActivityUtil.startActivity((Activity) mcontext, intent);
                }
            }
        });
        return convertView;
    }

    private class ChioceCb implements View.OnClickListener {
        CollectGoods collectbeanChoice;
        CheckBox cb;
        private  ChioceCb(CollectGoods collectbeanChoice,CheckBox cb) {
            this.collectbeanChoice = collectbeanChoice;
            this.cb = cb;
        }

        @Override
        public void onClick(View v) {
                        AddOrRemove(collectbeanChoice,cb.isChecked());
                        Message msg = new Message();
                        msg.obj = dataList;
                        msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
                        mHandler.sendMessage(msg);
        }

        //        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        AddOrRemove(collectbeanChoice,isChecked);
//                        notifyDataSetChanged();
//                        Message msg = new Message();
//                        msg.obj = dataList;
//                        msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
//                        mHandler.sendMessage(msg);
//        }
    }

    void AddOrRemove(CollectGoods collectbeanChoice, boolean isChecked) {
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

    void setIsEdit(CheckBox cb) {
        if (isEdit)
            cb.setVisibility(View.VISIBLE);
        else
            cb.setVisibility(View.GONE);
    }

}
