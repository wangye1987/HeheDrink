package com.heheys.ec.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.SearchResultBean;
import com.heheys.ec.utils.ConstantsUtil;

import java.util.List;

/**
 * 作者：wangkui on 2016/12/22 15:18
 * 邮箱：wangkui20090909@sina.com
 * 说明:检索结果适配器
 */

public class SearchResultAdapter extends BaseListAdapter<SearchResultBean.SearchBean> {


    //特卖视图
    private static final int SM_LAYOUT = 0;
    //合伙买视图
    private static final int HHM_LAYOUT = 1;
    //E发行视图
    private static final int YFX_LAYOUT = 2;

    private int returnLayout;
    private int TOTAL = 3;
    private TextView tm_xl;
    private TextView tm_kc;
    private TextView tm_unit;
    private TextView tm_unit_one;
    private ImageView tm_goods;
    private TextView hhm_ys;
    private TextView hhm_kc;
    private ImageView iv_hhm;
    private ProgressBar hhm_pb;
    private TextView yfx_price;
    private TextView fxj_area;
    private TextView yfx_time;
    private TextView textView10;
    private ImageView iv_yfx;
    private LinearLayout linear_time;

    public SearchResultAdapter(List<SearchResultBean.SearchBean> data, Context context) {
        super(data, context);
    }

    public SearchResultAdapter(List<SearchResultBean.SearchBean> data, Object obj, Context context) {
        super(data, obj, context);
    }

    @Override
    public int getItemViewType(int position) {
//        0:合伙 1:特卖 3:发行价
        String type = dataList.get(position).getType();
        if("1".equals(type)){
            returnLayout = SM_LAYOUT;
        }else if("0".equals(type)){
            returnLayout = HHM_LAYOUT;
        }else if("3".equals(type)){
            returnLayout = YFX_LAYOUT;
        }
        return returnLayout;
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL;
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        final SearchResultBean.SearchBean searchBean = dataList.get(position);
        int viewType = getItemViewType(position);
        if (convertView == null) {
            switch (viewType){
                case SM_LAYOUT:
                    convertView =  baseInflater.inflate(R.layout.search_tm_item,parent,false);
                    break;
                case HHM_LAYOUT:
                    convertView =  baseInflater.inflate(R.layout.search_hhm_item,parent,false);
                    break;
                case YFX_LAYOUT:
                    convertView =  baseInflater.inflate(R.layout.search_yfx_item,parent,false);
                    break;
            }
        }
            /**
             * 查找组件
             * */
            if(SM_LAYOUT == viewType){
                //特卖视图
                tm_xl = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tm_xl);
                tm_kc = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tm_kc);
                tm_unit = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tm_unit);
                tm_unit_one = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tm_unit_one);
                tm_goods = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.tm_goods);
                if(searchBean != null) {
                    tm_xl.setText(searchBean.getSoldnum());
                    tm_kc.setText( searchBean.getKcnum());
                    tm_unit.setText(" "+searchBean.getUnit());
                    tm_unit_one.setText(" "+searchBean.getUnit());
                    MyApplication.getInstance().imageLoader.displayImage(searchBean.getPic(), tm_goods,MyApplication.options);
                }
            }else if(HHM_LAYOUT== viewType){
                //合伙买视图
                hhm_ys = (TextView) ViewHolderUtil.getItemView(convertView, R.id.hhm_ys);
                hhm_kc = (TextView) ViewHolderUtil.getItemView(convertView, R.id.hhm_kc);
                iv_hhm = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_hhm);
                hhm_pb = (ProgressBar) ViewHolderUtil.getItemView(convertView, R.id.hhm_pb);
                if(searchBean != null) {
                hhm_ys.setText(searchBean.getSoldnum()+" / ");
                hhm_kc.setText(searchBean.getKcnum()+" "+searchBean.getUnit());
                MyApplication.getInstance().imageLoader.displayImage(searchBean.getPic(), iv_hhm,MyApplication.options_banner);
                //进度条
                if (!StringUtil.isEmpty(searchBean.getSoldnum())
                        && !StringUtil.isEmpty(searchBean.getKcnum())) {
                    float proportion = (Float.parseFloat(searchBean
                            .getSoldnum()))
                            / (float) (Integer.parseInt(searchBean
                            .getKcnum()));
                    int progress = (int) (proportion * 100);
//                    Resources res = mcontext.getResources();
//                    hhm_pb.setProgressDrawable(res
//								.getDrawable(R.drawable.progress_color_yellow));
                    hhm_pb.setProgress(progress);
                }}
            }else if(YFX_LAYOUT == viewType){
                //E发行视图
                yfx_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.yfx_price);
                fxj_area = (TextView) ViewHolderUtil.getItemView(convertView, R.id.fxj_area);
                yfx_time = (TextView) ViewHolderUtil.getItemView(convertView, R.id.yfx_time);
                textView10 = (TextView) ViewHolderUtil.getItemView(convertView, R.id.textView10);
                iv_yfx = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_yfx);
                linear_time = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_time);
                if(searchBean != null) {
                    yfx_price.setText("¥"+searchBean.getPrice() + "/" + searchBean.getUnit());
                    fxj_area.setText(searchBean.getReleaseArea());
                    textView10.setText(searchBean.getSoldnum()+ searchBean.getUnit());
                    MyApplication.getInstance().imageLoader.displayImage(searchBean.getPic(), iv_yfx, MyApplication.options);
                    if(!StringUtil.isEmpty(searchBean.getDiffTime())){
                        linear_time.setVisibility(View.VISIBLE);
                        yfx_time.setText(searchBean.getDiffTime());
                    }else{
                        linear_time.setVisibility(View.GONE);
                    }
                }
           }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("1".equals(searchBean.getStatus())){
                    Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
                    intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, searchBean.getId());
                    StartActivityUtil.startActivity((Activity) mcontext, intent);
                }
            }
        });

        return convertView;
    }
}
