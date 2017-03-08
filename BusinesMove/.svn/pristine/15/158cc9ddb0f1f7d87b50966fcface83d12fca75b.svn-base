/*package com.heheys.ec.model.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.ProductDetail;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.TimeUtil;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.GroupBuyProductInfor;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.LogUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

*//**
 * 
 * Describe:拼单区列表样式数据适配器
 * 
 * Date:2015-9-29
 * 
 * Author:liuzhouliang
 *//*
public class GroupBuyListAdapter extends BaseListAdapter<GroupBuyProductInfor> {
	private String TAG = GroupBuyListAdapter.class.getName();
	// 存储倒计时差
	private List<Long> countDownTimeList;
	private boolean isPlay;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (!isPlay)
				return;
			handler.postDelayed(this, 1000);
			if (countDownTimeList != null && countDownTimeList.size() > 0) {
				int size = countDownTimeList.size();
				for (int i = 0; i < size; i++) {
					if (countDownTimeList.get(i) > 0) {
						countDownTimeList.set(i,
								countDownTimeList.get(i) - 1000);
					}
				}

			}

			notifyDataSetChanged();
		}
	};
	private TextView tv_price;
	private TextView tv_sold;
	private Animation mAnimation;
	private long totalnum;
	private String numStr;
	boolean mIsListViewidle;
	public GroupBuyListAdapter(boolean mIsListViewidle,List<GroupBuyProductInfor> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		initCountDownTime();
//		mAnimation = AnimationUtils.loadAnimation(mcontext, R.anim.show_in);
		this.mIsListViewidle = mIsListViewidle;
	}

	@Override
	public void setNewData(List<GroupBuyProductInfor> newData) {

		super.setNewData(newData);
		initCountDownTime();
	}

	*//**
	 * 
	 * Describe:启动倒计时
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 *//*
	public void startCountDownTime() {
		if (isPlay) {
			return;
		}
		isPlay = true;
		runnable.run();
	}

	*//**
	 * 
	 * Describe:停止倒计时
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 *//*

	public void stopCountDownTime() {
		isPlay = false;
	}

	*//**
	 * 
	 * Describe:初始化倒计时
	 * 
	 * Date:2015-10-10
	 * 
	 * Author:liuzhouliang
	 *//*
	public void initCountDownTime() {
		*//**
		 * 处理倒计时
		 *//*
		if (dataList != null) {
			int size = dataList.size();
			if (size > 0) {
				if (countDownTimeList != null) {
					countDownTimeList.clear();
				}
				countDownTimeList = new ArrayList<Long>();
				for (int i = 0; i < size; i++) {
					String startTimeString = dataList.get(i).getEndtime();
					// String endTimeString = System.currentTimeMillis();
					long appointTime = TimeUtil.changeDateToTime(startTimeString);
					long currentTime = System.currentTimeMillis();
					long gapTime = appointTime - currentTime;
					if (gapTime > 0) {
						countDownTimeList.add(i, gapTime);
					} else {
						countDownTimeList.add(i, 0L);
					}
				}
			}
		}
		LogUtil.d(TAG, countDownTimeList.toString());

	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.group_buy_list_item,
					parent, false);
		}
		GroupBuyProductInfor bean = dataList.get(position);
		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(convertView, R.id.group_buy_list_item_time_parent);
		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_day);
		TextView tv_time_day_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_list_item_day);
		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_hour);
		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_minitue);
		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_second);
		TextView tv_wine_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_wine_name);
		tv_price = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_price);
//		TextView tv_cost_price = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.tv_cost_price);
		tv_sold = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_totalnow);
		TextView tv_totalnum = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_totalnum);
		ProgressBar pb = (ProgressBar) ViewHolderUtil.getItemView(convertView,
				R.id.group_buy_list_item_pb);
		final ImageView iv_wineurl = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_wineurl);
		ImageView ivActivityIcon = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_list_item_activity_icon);
		if (countDownTimeList.get(position) == 0) {
			llTimeParentLayout.setVisibility(View.GONE);
		} else {
			llTimeParentLayout.setVisibility(View.VISIBLE);
			setCountDownTime(countDownTimeList.get(position), tv_time_day,
					tv_time_day_name, tv_time_hour, tv_time_minitue,
					tv_time_second);
		}

		tv_wine_name.setText(bean.getName());
		ViewUtil.setActivityPrice(tv_price, bean.getCprice());
//		ViewUtil.setNormalPrice(tv_cost_price, bean.getPrice());
		// 已经售卖数量
		int soldNum = 0;
		if (!StringUtil.isEmpty(bean.getSoldnum())) {
			soldNum = Integer.parseInt(bean.getSoldnum());
		}
		if (soldNum == Integer.parseInt(bean.getKnum())) {
			tv_sold.setText("已全部售完");
		} else {
			tv_sold.setText("已售" + soldNum + (bean.getUnit()==null?"":bean.getUnit()));

		}
		tv_sold.setTextColor(mcontext.getResources().getColor(R.color.color_f9883d));
		if(bean.getKnum()!=null){
			totalnum = Long.parseLong(bean.getKnum());
			if(totalnum>10000){
				totalnum = totalnum/1000;
				numStr = totalnum+"万";
			}else{
				numStr = bean.getKnum();
			}
		}
		tv_totalnum.setText("共" + numStr + (bean.getUnit()==null?"":bean.getUnit()));
		float proportion = ((float) soldNum)
				/ (float) (Integer.parseInt(bean.getKnum()));
		// DecimalFormat df = new DecimalFormat("0.00");//格式化小数
		// String s = df.format(proportion);//返回的是String类型
		int progress = (int) (proportion * 100);

		// MyApplication.imageLoader.displayImage(bean.getPic(), iv_wineurl,
		// MyApplication.options);
//		if(mIsListViewidle){
		MyApplication.imageLoader.displayImage(bean.getPic(), iv_wineurl,
				MyApplication.options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// iv_wineurl.startAnimation(mAnimation);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
					}
				});
//		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mcontext, ProductDetail.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						dataList.get(position).getId());
				StartActivityUtil.startActivity((Activity) mcontext, intent);
			}
		});
		*//**
		 * 控制活动图标的显示
		 *//*
		String activityStateString = dataList.get(position).getStatus();
		if (!StringUtil.isEmpty(activityStateString)) {
			if ("1".equals(activityStateString)) {
				*//**
				 * 已经发布
				 *//*
				llTimeParentLayout.setVisibility(View.VISIBLE);
				ivActivityIcon.setVisibility(View.GONE);
				Resources res = mcontext.getResources();
				pb.setProgressDrawable(res
						.getDrawable(R.drawable.progressbar_color));
				pb.setProgress(progress);
				setBlack();
			} else if ("2".equals(activityStateString)) {
				*//**
				 * 已经抢光
				 *//*
				llTimeParentLayout.setVisibility(View.VISIBLE);
				ivActivityIcon.setVisibility(View.VISIBLE);
				ivActivityIcon.setImageResource(R.drawable.panic_buy_over);
				Resources res = mcontext.getResources();
				pb.setProgressDrawable(res
						.getDrawable(R.drawable.progressbar_color_gray));
				pb.setProgress(progress);
				setGray();
			} else if ("3".equals(activityStateString)) {
				*//**
				 * 已经结束
				 *//*
				llTimeParentLayout.setVisibility(View.GONE);
				ivActivityIcon.setVisibility(View.VISIBLE);
				ivActivityIcon.setImageResource(R.drawable.activity_over);
				Resources res = mcontext.getResources();
				pb.setProgressDrawable(res
						.getDrawable(R.drawable.progressbar_color_gray));
				pb.setProgress(progress);
				setGray();
			} else {
				Resources res = mcontext.getResources();
				pb.setProgressDrawable(res
						.getDrawable(R.drawable.progressbar_color));
				pb.setProgress(progress);
				ivActivityIcon.setVisibility(View.GONE);
			}
		} else {
			Resources res = mcontext.getResources();
			pb.setProgressDrawable(res
					.getDrawable(R.drawable.progressbar_color));
			pb.setProgress(progress);
			ivActivityIcon.setVisibility(View.GONE);
		}
		return convertView;
	}

	// 设置回正常状态
	private void setBlack() {
		tv_price.setTextColor(mcontext.getResources().getColor(
				R.color.color_ff0000));
		tv_sold.setTextColor(mcontext.getResources().getColor(
				R.color.color_f9883d));
	}

	// 设置成灰色
	private void setGray() {
		tv_price.setTextColor(mcontext.getResources().getColor(
				R.color.color_b9b9b9));
		tv_sold.setTextColor(mcontext.getResources().getColor(
				R.color.gray_text_notice));
	}

	*//**
	 * 
	 * Describe:设置倒计时时间
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 *//*
	private void setCountDownTime(long millisUntilFinished, TextView tvDay,
			TextView tv_time_day_name, TextView tvHour, TextView tvMinute,
			TextView tvSeconds) {
		long days, hours, minutes, seconds;
		if (millisUntilFinished < 0) {
			return;
		}
		if (millisUntilFinished <= 1000 * 60 * 60 * 24) {
			tv_time_day_name.setVisibility(View.GONE);
			tvDay.setVisibility(View.GONE);
			// 获取小时值
			hours = millisUntilFinished / (60 * 60 * 1000);
			// 获取分值
			minutes = (millisUntilFinished - hours * (60 * 60 * 1000))
					/ (60 * 1000);
			// 获取秒值
			seconds = (millisUntilFinished - hours * (60 * 60 * 1000) - minutes
					* (60 * 1000)) / 1000;
			if (hours < 10) {
				tvHour.setText("0" + hours);
			} else
				tvHour.setText(hours + "");
			if (minutes < 10) {
				tvMinute.setText("0" + minutes);
			} else
				tvMinute.setText(minutes + "");
			if (seconds < 10) {
				tvSeconds.setText("0" + seconds);
			} else
				tvSeconds.setText(seconds + "");
		} else {
			// 获取天数
			days = millisUntilFinished / (60 * 60 * 1000 * 24);
			// 获取小时值
			hours = (millisUntilFinished - days * (60 * 60 * 1000 * 24))
					/ (60 * 60 * 1000);
			// 获取分值
			minutes = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)) - hours
					* (60 * 60 * 1000))
					/ (60 * 1000);
			// 获取秒值
			seconds = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)
					- hours * (60 * 60 * 1000) - minutes * (60 * 1000))) / 1000;
			if (days < 10) {
				tvDay.setText("0" + days);
			} else
				tvDay.setText(days + "");
			if (hours < 10) {
				tvHour.setText("0" + hours);
			} else
				tvHour.setText(hours + "");
			if (minutes < 10) {
				tvMinute.setText("0" + minutes);
			} else
				tvMinute.setText(minutes + "");
			if (seconds < 10) {
				tvSeconds.setText("0" + seconds);
			} else
				tvSeconds.setText(seconds + "");
		}

	}

}
*/