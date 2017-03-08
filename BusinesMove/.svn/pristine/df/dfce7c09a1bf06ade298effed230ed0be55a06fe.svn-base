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
import com.heheys.ec.lib.utils.StringUtil;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2015-11-11 下午4:34:49 
 * 类说明 
 * @param
 */
public class AdapterLicnese extends BaseAdapter{

	private List<String> mNameList = new ArrayList<String>();
//	private ArrayList<Drawable> mDrawableList = new ArrayList<Drawable>();
	private LayoutInflater mInflater;
	private Context mContext;
	private String baseurl;
	LinearLayout.LayoutParams params;
	private String currtUrl;
	private boolean isEdit;
	private int id;
	private ImageCallBack ivcallbacks;
	ClickCallBackss clickBack;
	TextView tv_edit_license;
	final AuthActivity auth ;
	private int role;
	private boolean isRegister;
	private ArrayList<String> pics = new ArrayList<String>();
	private int viplevel;
	private String statusAuth;
	public AdapterLicnese(Context context,boolean isRegister, List<String> nameList,String baseurl,TextView tv_edit_license,
			ImageCallBack ivcallback,int role,int viplevel,String statusAuth) {
		this.statusAuth  = statusAuth;
		this.viplevel  = viplevel;
		this.isRegister  = isRegister;
		this.role  = role;
		this.mNameList = nameList;
		this.mContext = context;
		auth = (AuthActivity) context;
		mInflater = LayoutInflater.from(context);
		this.baseurl = baseurl;
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		this.ivcallbacks  = ivcallback;
		this.tv_edit_license = tv_edit_license;
		pics.clear();
		for(String st:mNameList){
			pics.add(st);
		}
		if(nameList.size()!=3)
		mNameList.add("");
	}
	public void setBaseUrl(String baseurl){
		this.baseurl = baseurl;
	}
	public void isEdit(boolean isEdit,ClickCallBackss clickBack){
		this.isEdit = isEdit;
		this.clickBack  = clickBack;
		if(!isEdit){
			if(!mNameList.contains("") && mNameList.size()<3)
			mNameList.add("");
		}
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
		if(mNameList.size()<3 && mNameList.size()!=3){
			if(!isReplace)
			mNameList.add("");
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
//		if (convertView == null)
//		{
//			convertView = mInflater.inflate(R.layout.image_upload, null);
//			
//			// construct an item tag
//			viewTag = new ItemViewTag(
//					(ImageView) convertView.findViewById(R.id.iv_upload), 
//					(LinearLayout) convertView.findViewById(R.id.linear_delete));
//			convertView.setTag(viewTag);xz
//		} else
//		{
//			viewTag = (ItemViewTag) convertView.getTag();
//		}
//		
		if(position==mNameList.size()-1){
			if(mNameList.get(position).equals("")){
				viewTag.icon.setImageResource(R.drawable.upload);
				if(viplevel == 3)
					viewTag.icon.setVisibility(View.GONE);
				if(!StringUtil.isEmpty(statusAuth)){
				if(statusAuth.equals("2") )
					viewTag.icon.setVisibility(View.GONE);
				}
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
				Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.delete_image);
				convertView.startAnimation(animation);
				viewTag.mLinear_delete.setVisibility(View.VISIBLE);
				viewTag.mLinear_delete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						mNameList.remove(index);
						if(mNameList.size()==1 && mNameList.get(0).equals("")){
							auth.IsCanClick(mContext,tv_edit_license, mNameList);
							clickBack.setClick(true);
							isEdit = false;
						}else if(mNameList.size()==0){
							mNameList.add("");
							clickBack.setClick(true);
							isEdit = false;
							auth.IsCanClick(mContext,tv_edit_license, mNameList);
						}
						notifyDataSetChanged();
					}});
			}
		}else{
//			convertView.setVisibility(View.VISIBLE);
//			viewTag.mLinear_delete.setVisibility(View.GONE);
//			AuthActivity auth = new AuthActivity();
//			//不是普通代理商角色才可以点击
//			if(role!=1)
//			viewTag.icon.setOnClickListener(auth.new MyClassClick(position+8,viewTag.icon,mContext, ivcallbacks));
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
				AuthActivity auth = new AuthActivity();
				//不是普通代理商角色才可以点击
//				if(role!=1)
				viewTag.icon.setOnClickListener(auth.new MyClassClick(index+8,viewTag.icon,mContext, ivcallbacks));
				MobclickAgent.onEvent(mContext, "C_AUTH_3");
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
	public interface ClickCallBackss{
		void setClick(boolean click);
	}
}
 