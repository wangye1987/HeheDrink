package com.heheys.ec.model.adapter;

import java.util.List;

import com.heheys.ec.view.UrlTouchImageView;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 图片适配器
 * 
 * @author LJH
 * 
 */
public class BrowsePictureAdapter extends PagerAdapter {

	private List<UrlTouchImageView> mList;

	public BrowsePictureAdapter(List<UrlTouchImageView> list) {
		this.mList = list;
	}

	public void setData(List<UrlTouchImageView> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mList.get(position % mList.size()));

		return mList.get(position % mList.size());
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mList.get(position % mList.size()));
	}

}
