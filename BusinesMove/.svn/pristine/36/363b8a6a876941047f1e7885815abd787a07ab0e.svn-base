package com.heheys.ec.model.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.DrinkInfoActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.DrinkInfoBean;

public class DrinkInfoListAdapter extends BaseListAdapter<DrinkInfoBean> {
	
	List<DrinkInfoBean> data;
	final Context context;
	private ImageView iv_image;
	private TextView tv_title;
	private LinearLayout drink_content;
	private Animation maniAnimation;
	private String baseUrl;
	public DrinkInfoListAdapter(List<DrinkInfoBean> data, Context context,String baseUrl) {
		super(data, context);
		this.data = data;
		this.context = context;
		this.baseUrl = baseUrl;
		maniAnimation = AnimationUtils.loadAnimation(mcontext, R.anim.show_in);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView =   baseInflater.inflate(R.layout.drink_info_item, parent, false);
		}
		iv_image = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_image);
		tv_title = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_title);
		drink_content = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.drink_content);
		
		String imageUrl = data.get(position).getUrl();
		String st_title = data.get(position).getTitle();
		final String h5_url = data.get(position).getH5Url();
		MyApplication.imageLoader.displayImage(baseUrl+imageUrl, iv_image,MyApplication.options);
		tv_title.setText(st_title);
		drink_content.setAnimation(maniAnimation);
		drink_content.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,DrinkInfoActivity.class);
				intent.putExtra("h5url", h5_url);
				intent.putExtra("title","详情");
				StartActivityUtil.startActivity((Activity) context, intent);
			}
		});
		
		return convertView;
	}

}
