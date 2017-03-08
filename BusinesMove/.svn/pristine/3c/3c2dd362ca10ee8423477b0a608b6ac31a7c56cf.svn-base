package com.heheys.ec.model.adapter; 

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.AuthActivity;
import com.heheys.ec.controller.activity.BrowsPictureActivity;
import com.heheys.ec.controller.activity.AuthActivity.ImageCallBack;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2015-11-12 上午11:18:08 
 * 类说明 
 * @param
 */
public class AdapterZhuzhi extends BaseAdapter{

	private List<String> mNameList_zhuzhi = new ArrayList<String>();
	private List<String> mNameList__shuiwu = new ArrayList<String>();
	private List<String> mNameList = new ArrayList<String>();
//	private ArrayList<Drawable> mDrawableList = new ArrayList<Drawable>();
	private LayoutInflater mInflater;
	private Context mContext_zhuzhi;
	private Context mContext_shuwu;
	private Context mContext;
	private String baseurl;
	LinearLayout.LayoutParams params;
	private String currtUrl;
	private boolean isEdit;
	private boolean isEdit_zhuzhi;
	private boolean isEdit_shuiwu;
	private int id;
	ClickCallBackzhu clickBack;
	ClickCallBackzhu clickBack_zhuzhi;
	ClickCallBackzhu clickBack_shuiwu;
	private ImageCallBack ivcallbacks;
	private ImageCallBack ivcallbacks_zhuzhi;
	private ImageCallBack ivcallbacks_shuiwu;
	TextView tv;
	TextView tv_edit_zhuchi;
	TextView tv_edit_shuiwu;
	int flag;
	AuthActivity auth = new AuthActivity();
	private int role;
	private boolean isRegister;
	private ArrayList<String> pics = new ArrayList<String>();
	public AdapterZhuzhi(int flag,Context context, boolean isRegister,List<String> nameList,String baseurl,TextView tv,ImageCallBack ivcallback,int role) {
		this.isRegister = isRegister;
		this.role = role;
		mInflater = LayoutInflater.from(context);
		this.baseurl = baseurl;
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		this.flag = flag;
		this.mNameList = nameList;
		this.mContext = context;
		this.ivcallbacks = ivcallback;
		this.tv  = tv;
		pics.clear();
		for(String st:mNameList){
			pics.add(st);
		}
		if(nameList.size()!=1)
		mNameList.add("");
	}

	public void setBaseUrl(String baseurl){
		this.baseurl = baseurl;
	}
	public void isEdit(int flag,boolean isEdit,ClickCallBackzhu clickBack){
		this.isEdit= isEdit;
		this.clickBack = clickBack;
	}
	public int getCount() {
		return mNameList.size();
	}

	public Object getItem(int position) {
		return mNameList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void setData(List<String> nameList,boolean isReplace){
		this.mNameList = nameList;
		pics.clear();
		for(String st:mNameList){
			pics.add(st);
		}
	}
	
	@SuppressLint("NewApi") public View getView(int position, View convertView, ViewGroup parent) {
		final ItemViewTag viewTag;
		final int index = position;
		currtUrl = mNameList.get(index);
		convertView = mInflater.inflate(R.layout.image_upload, null);
		// construct an item tag
		viewTag = new ItemViewTag(
				(ImageView) convertView.findViewById(R.id.iv_upload), 
				(LinearLayout) convertView.findViewById(R.id.linear_delete));
		convertView.setTag(viewTag);
		if(position==mNameList.size()-1){
			if(mNameList.get(position).equals("")){
				viewTag.icon.setImageResource(R.drawable.upload);
			}else{
				MyApplication.imageLoader.displayImage(baseurl+currtUrl, viewTag.icon,MyApplication.options);
			}
		}else{
			MyApplication.imageLoader.displayImage(baseurl+currtUrl, viewTag.icon,MyApplication.options);
		}
		if(isEdit){
			if(position==mNameList.size()-1 && currtUrl.equals("")){
				convertView.setVisibility(View.GONE);
			}else{
				convertView.setVisibility(View.VISIBLE);
				//图片删除动画
				Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.delete_image);
				convertView.startAnimation(animation);
				viewTag.mLinear_delete.setVisibility(View.VISIBLE);
				viewTag.mLinear_delete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						mNameList.remove(index);
						if(mNameList.size()==0){
							mNameList.add("");
							clickBack.setClick(true);
							isEdit = false;
							auth.IsCanClick(mContext,tv, mNameList);
						}
						notifyDataSetChanged();
					}});
			}
		}else{
//			convertView.setVisibility(View.VISIBLE);
//			viewTag.mLinear_delete.setVisibility(View.GONE);
//			if(role!=1){
//			if(flag==4){
//				viewTag.icon.setOnClickListener(auth.new MyClassClick(position+11,viewTag.icon,mContext, ivcallbacks));
//			}else{
//				viewTag.icon.setOnClickListener(auth.new MyClassClick(position+12,viewTag.icon,mContext, ivcallbacks));
//			}}
		}
//		if(!isRegister){
			viewTag.icon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!mNameList.get(index).equals("")){
					Intent intent = new Intent(mContext, BrowsPictureActivity.class);
					Bundle bundle = new Bundle();
					bundle.putStringArrayList("Urls", pics);
					bundle.putInt("index", index);
					bundle.putString("baseurl", baseurl);
					intent.putExtra("msg", bundle);
					mContext.startActivity(intent);
					}else{
						viewTag.mLinear_delete.setVisibility(View.GONE);
//						if(role!=1){
						if(flag==4){
							viewTag.icon.setOnClickListener(auth.new MyClassClick(index+11,viewTag.icon,mContext, ivcallbacks));
						}else{
							viewTag.icon.setOnClickListener(auth.new MyClassClick(index+12,viewTag.icon,mContext, ivcallbacks));
//						}
						}
					}
				}
			});
//		}
		return convertView;
	}
	class ItemViewTag
	{
		private LinearLayout mLinear_delete;
		private ImageView icon;
		private ItemViewTag(ImageView icon, LinearLayout mLinear_delete)
		{
			this.icon = icon;
			this.mLinear_delete = mLinear_delete;
		}
	}
	
	public interface ClickCallBackzhu{
		void setClick(boolean click);
	}
}
 