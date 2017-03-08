package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.model.adapter.BrowsePictureAdapter;
import com.heheys.ec.view.UrlTouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * 查看大图
 * 
 *@author wangkui
 *
 *@version 1.0
 *
 * 
 */
public class BrowsPictureActivity extends Activity implements
		OnPageChangeListener {

	private ViewPager mVp;
	private Bundle bundle;
	private BrowsePictureAdapter adapter;
	private Button closeBtn;
	private List<UrlTouchImageView> viewList;
	private ArrayList<String> urls;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private TextView picNumTv;
	private String baseurl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题栏
		setContentView(R.layout.layout_pic);
		mVp = (ViewPager) findViewById(R.id.vp);
		closeBtn = (Button) findViewById(R.id.close_btn);
		picNumTv = (TextView) findViewById(R.id.picnum_tv);
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		viewList = new ArrayList<UrlTouchImageView>();
		Intent intent = getIntent();
		if (intent != null) {
			bundle = intent.getBundleExtra("msg");
			urls = bundle.getStringArrayList("Urls");
			baseurl = bundle.getString("baseurl");
			for (int i = 0; i < urls.size(); i++) {
//				ImageView iv = new ImageView(this);
				final UrlTouchImageView iv = new UrlTouchImageView(this);
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				iv.setLayoutParams(params);
				iv.setScaleType(ScaleType.FIT_CENTER);
				iv.getImageView().setImageResource(R.drawable.ic_launcher);
//				iv.setBackgroundResource(R.drawable.ic_launcher);
//				ImageLoader.getInstance().displayImage(baseurl+urls.get(i),  iv.getImageView(),
//						MyApplication.options);
				iv.setUrl(baseurl+urls.get(i));
				viewList.add(iv);
			}
			adapter = new BrowsePictureAdapter(viewList);
			// getInternetData();

			mVp.setAdapter(adapter);
			int index = bundle.getInt("index", 1);
			mVp.setCurrentItem(index);
			picNumTv.setText(String.valueOf(index + 1) + "/" + urls.size());
			mVp.setOnPageChangeListener(this);

		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int arg0) {
		picNumTv.setText((arg0 + 1) + "/" + urls.size());
	}

}
