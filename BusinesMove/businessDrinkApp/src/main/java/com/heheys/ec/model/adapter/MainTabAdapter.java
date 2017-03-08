package com.heheys.ec.model.adapter;

import com.heheys.ec.lib.view.CustomViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Author:LiuZhouLiang
 * <p/>
 * Date:2015/9/20
 * <p/>
 * Describe:主页面TAB数据源
 */
public class MainTabAdapter extends FragmentPagerAdapter {
    private Fragment[] mdata;

    public MainTabAdapter(FragmentManager fm, Fragment[] data) {
        super(fm);
        mdata = data;
    }

    @Override

    public Fragment getItem(int position) {
        return mdata[position];
    }

    @Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
    public int getCount() {
        return mdata.length;
    }
}
