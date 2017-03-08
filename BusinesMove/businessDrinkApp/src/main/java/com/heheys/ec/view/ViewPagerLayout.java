package com.heheys.ec.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.lib.view.CustomViewPager;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告自动轮播控件
 */
public class ViewPagerLayout extends LinearLayout {
	private final static String TAG = "主页面fragment广告";
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 图片轮播视图
	 */
	public CustomViewPager mAdvPager = null;
	/**
	 * 滚动图片视图适配
	 */
	private ImageCycleAdapter mAdvAdapter;
	/**
	 * 图片轮播指示器控件
	 */
	private ViewGroup mGroup;

	/**
	 * 图片轮播指示个图
	 */
	private ImageView mImageView = null;

	/**
	 * 滚动图片指示视图列表
	 */
	private ImageView[] mImageViews = null;

	/**
	 * 图片滚动当前图片下标
	 */
	private int mImageIndex = 0;

	/**
	 * 手机密度
	 */
	private float mScale;
	private boolean isStop;
	private TextView[] mTextViews;
	private TextView mTextView;
	private ViewGroup mGroup2;
//	int position;
//	float positionOffset;
//	int positionOffsetPixels;
	/**
	 * 滑动时左边的元素
	 */
	private View mLeft;
	/**
	 * 滑动时右边的元素
	 */
	private View mRight;

	// ArrayList<String> imageNameList;

	/**
	 * @param context
	 */
	public ViewPagerLayout(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ViewPagerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScale = context.getResources().getDisplayMetrics().density;
		LayoutInflater.from(context).inflate(R.layout.home_adver_itme, this);
		mAdvPager = (CustomViewPager) findViewById(R.id.home_adver_item_vp);
		mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
		// mAdvPager.setOnTouchListener(new OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_UP:
		// // 开始图片滚动
		// startImageTimerTask();
		// break;
		// default:
		// // 停止图片滚动
		// stopImageTimerTask();
		// break;
		// }
		// return false;
		// }
		// });
		// 滚动图片右下指示器视
		mGroup = (ViewGroup) findViewById(R.id.home_adver_item_indicator_parent);
	}

	
	/**
	 * 装填图片数据
	 * 
	 * @param imageUrlList
	 * @param imageCycleViewListener
	 */
	public void setImageResources(List<String> imageUrlList,
			ImageCycleViewListener imageCycleViewListener) {
		// 清除
		mGroup.removeAllViews();
		// 图片广告数量
		final int imageCount = imageUrlList.size();
		LogUtil.d(TAG, "图片数量" + imageCount);
		mImageViews = new ImageView[imageCount];
		/**
		 * 添加下标指示器圆点
		 */
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
//			int imageParams = (int) (mScale * 8 + 0.5f);// XP与DP转换，适应应不同分辨率
			int imagePadding = (int) (mScale * 2 + 0.5f);
			LayoutParams params = new LayoutParams(12, 12);
			params.leftMargin = ViewUtil.dip2px(mContext, 3);
			mImageView.setScaleType(ScaleType.CENTER_CROP);
			mImageView.setLayoutParams(params);
			mImageView.setPadding(imagePadding, imagePadding, imagePadding,
					imagePadding);

			mImageViews[i] = mImageView;
			if (i == 0) {
				mImageViews[i]
						.setBackgroundResource(R.drawable.slideshow_selected);
			} else {
				mImageViews[i]
						.setBackgroundResource(R.drawable.slideshow_default);
			}
			mGroup.addView(mImageViews[i]);
		}
		/**
		 * 绑定图片数据
		 */
		mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList,
				imageCycleViewListener);
		mAdvPager.setAdapter(mAdvAdapter);
		if(imageUrlList.size() < 2)
		mAdvPager.setScrollble(false);
		else
		mAdvPager.setScrollble(true);
		startImageTimerTask();
	}

	/**
	 * 图片轮播(手动控制自动轮播与否，便于资源控件）
	 */
	public void startImageCycle() {
		startImageTimerTask();
	}

	/**
	 * 暂停轮播—用于节省资源
	 */
	public void pushImageCycle() {
		stopImageTimerTask();
	}

	/**
	 * 图片滚动任务
	 */
	private void startImageTimerTask() {
		stopImageTimerTask();
		// 图片滚动
		mHandler.postDelayed(mImageTimerTask, 3000);
	}

	/**
	 * 停止图片滚动任务
	 */
	private void stopImageTimerTask() {
		isStop = true;
		mHandler.removeCallbacks(mImageTimerTask);
	}

	private Handler mHandler = new Handler();

	/**
	 * 图片自动轮播Task
	 */
	private Runnable mImageTimerTask = new Runnable() {
		@Override
		public void run() {
			if (mImageViews != null) {
				mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
				if (!isStop) { // if isStop=true //当你退出后 要把这个给停下来 不然 这个一直存在
								// 就一直在后台循环
					mHandler.postDelayed(mImageTimerTask, 5000);
				}

			}
		}
	};

	/**
	 * 
	 * 
	 * @describe:轮播图片监听
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-24下午2:39:27
	 * 
	 */
	private final class GuidePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE)
				startImageTimerTask();
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			LogUtil.d(TAG, "onPageScrolled==" + position + "positionOffset"
					+ positionOffset + "positionOffsetPixels"
					+ positionOffsetPixels);
//			ViewPagerLayout.this.positionOffset = positionOffset;
//			ViewPagerLayout.this.positionOffsetPixels = positionOffsetPixels;
//			ViewPagerLayout.this.position = position;
			// 滑动特别小的距离时，
//						float effectOffset = mAdvPager.isSmall(positionOffset) ? 0
//								: positionOffset;
//
//						// 获取左边的View
//						mLeft = mAdvPager.findViewFromObject(position);
//						// 获取右边的View
//						mRight = mAdvPager.findViewFromObject(position + 1);
//
//						// 添加切换动画效果
//						mAdvPager.animateStack(mLeft, mRight, effectOffset,
//								positionOffsetPixels);
//						mAdvPager.animateStackStack(mLeft, mRight, effectOffset,
//								positionOffsetPixels);
//						mAdvPager.animateAccordion(mLeft, mRight, effectOffset);
		}

		@Override
		public void onPageSelected(int index) {

			index = index % mImageViews.length;
			// 设置当前显示的图片
			mImageIndex = index;
			// 设置图片滚动指示器背
			mImageViews[index]
					.setBackgroundResource(R.drawable.slideshow_selected);
			for (int i = 0; i < mImageViews.length; i++) {
				if (index != i) {
					mImageViews[i]
							.setBackgroundResource(R.drawable.slideshow_default);
				}
			}

//
//			// 获取左边的View
//			mLeft = mAdvPager.findViewFromObject(position);
//			// 获取右边的View
//			mRight = mAdvPager.findViewFromObject(position + 1);
//
//			// 添加切换动画效果
//			mAdvPager.animateStack(mLeft, mRight, effectOffset,
//					positionOffsetPixels);
//			mAdvPager.animateStackStack(mLeft, mRight, effectOffset,
//					positionOffsetPixels);
//			mAdvPager.animateAccordion(mLeft, mRight, effectOffset);
			LogUtil.d(TAG, "onPageSelected==" + index
					);
		}
	}

	/**
	 * 
	 * 
	 * @describe:轮播图片适配器
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2015-1-9下午6:16:37
	 * 
	 */
	private class ImageCycleAdapter extends PagerAdapter {

		/**
		 * 图片视图缓存列表
		 */
		private ArrayList<ImageView> mImageViewCacheList;

		/**
		 * 图片资源列表
		 */
		private List<String> mAdList = new ArrayList<String>();
		private ArrayList<String> nameList = new ArrayList<String>();

		/**
		 * 广告图片点击监听
		 */
		private ImageCycleViewListener mImageCycleViewListener;

		private Context mContext;

		public ImageCycleAdapter(Context context, List<String> adList,

		ImageCycleViewListener imageCycleViewListener) {
			this.mContext = context;
			this.mAdList = adList;
			mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<ImageView>();
		}

		/**
		 * 获取页面个数
		 */
		@Override
		public int getCount() {
			// LogUtil.d(TAG, "获取页面个数" + Integer.MAX_VALUE);
			return Integer.MAX_VALUE;
		}

		/**
		 * 决定instantiateItem()方法返回的Object对象是不是需要显示的页面关联, 这个方法必须要有;
		 */
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// LogUtil.d(TAG, "isViewFromObject");
			return view == obj;
		}

		/*
		 * 在给定的位置创建页面, PageAdapter负责向指定的position位置添加View页面;
		 */
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			String imageUrl = mAdList.get(position % mAdList.size());
			ImageView imageView = null;
			LogUtil.d(TAG, "instantiateItem==position==" + position + "图片数量"
					+ mAdList.size());
			if (mImageViewCacheList.isEmpty()) {
				LogUtil.d(TAG, "mImageViewCacheList empty" + position + "图片数量"
						+ mAdList.size());
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ScaleType.CENTER_CROP);
				// 设置图片点击监听
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mImageCycleViewListener.onImageClick(
								position % mAdList.size(), v);
						LogUtil.d(TAG, "当mImageViewCacheList empty前位置"
								+ position % mAdList.size() + "position=="
								+ position);
					}
				});
			} else {
				LogUtil.d(TAG, "mImageViewCacheList not empty" + position
						+ "图片数量" + mAdList.size());
				imageView = mImageViewCacheList.remove(0);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mImageCycleViewListener.onImageClick(
								position % mAdList.size(), v);
						LogUtil.d(TAG, "mImageViewCacheList not empty当前位置"
								+ position % mAdList.size());
					}
				});
			}
			imageView.setTag(imageUrl);
			mAdvPager.setObjectForPosition(imageView, position);
			container.addView(imageView);
			mImageCycleViewListener.displayImage(imageUrl, imageView);
			return imageView;
		}

		/**
		 * 删除container中指定位置position的页面
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			LogUtil.d(TAG, "destroyItem" + position);
			ImageView view = (ImageView) object;
			mAdvPager.removeView(view);
			mImageViewCacheList.add(view);

			
			
		}

	}

	/**
	 * 轮播控件的监听事件
	 * 
	 * @author minking
	 */
	public static interface ImageCycleViewListener {
		/**
		 * 加载图片资源
		 * 
		 * @param imageURL
		 * @param imageView
		 */
		public void displayImage(String imageURL, ImageView imageView);

		/**
		 * 单击图片事件
		 * 
		 * @param position
		 * @param imageView
		 */
		public void onImageClick(int position, View imageView);
	}

}
