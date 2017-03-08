package com.heheys.ec.model.adapter;

import java.util.ArrayList;
import java.util.List;

import com.heheys.ec.R;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * Describe:倒计时商品适配器
 * 
 * Date:2015-9-28
 * 
 * Author:liuzhouliang
 */
public class HomeCountdownOrderAdapter extends BaseListAdapter<Long> {
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

	public HomeCountdownOrderAdapter(List<Long> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		initCountDownTime();
	}

	public void initCountDownTime() {
		/**
		 * 处理倒计时
		 */
		if (dataList != null) {
			int size = dataList.size();
			if (size > 0) {
				if (countDownTimeList != null) {
					countDownTimeList.clear();
				}
				countDownTimeList = new ArrayList<Long>();
				for (int i = 0; i < size; i++) {

					long appointTime = dataList.get(i);
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

	}

	public void startCountDownTime() {
		isPlay = true;
		runnable.run();
	}

	public void stopCountDownTime() {
		isPlay = false;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.home_countdown_orderadapter_item, parent, false);

		}
		ImageView ivImageView = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.home_countdown_orderadaper_item_iv);
		TextView tvHourTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.home_countdown_orderadapter_item_hour);
		TextView tvMinuteTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.home_countdown_orderadapter_item_minute);
		TextView tvSecondTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.home_countdown_orderadapter_item_second);
		// tvHourTextView.setText(getTime(countDownTimeList.get(position)));
		setTime(countDownTimeList.get(position), tvHourTextView,
				tvMinuteTextView, tvSecondTextView);
		return convertView;
	}

	private void setTime(long millisUntilFinished, TextView tvHour,
			TextView tvMinute, TextView tvSeconds) {
		long hours, minutes, seconds;
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
	}

	private String getTime(long millisUntilFinished) {
		// 获取小时值
		long hours = millisUntilFinished / (60 * 60 * 1000);
		// 获取分值
		long minutes = (millisUntilFinished - hours * (60 * 60 * 1000))
				/ (60 * 1000);
		// 获取秒值
		long seconds = (millisUntilFinished - hours * (60 * 60 * 1000) - minutes
				* (60 * 1000)) / 1000;
		StringBuilder sb = new StringBuilder();
		if (hours < 10) {
			// tvHour.setText("0" + hours);
			sb.append("0" + hours + ":");
		} else
			// tvHour.setText(hours + "");
			sb.append(hours + ":");
		if (minutes < 10) {
			// tvMinute.setText("0" + minutes);
			sb.append("0" + minutes + ":");
		} else
			// tvMinute.setText(minutes + "");
			sb.append(minutes + ":");
		if (seconds < 10) {
			// tvSeconds.setText("0" + seconds);
			sb.append("0" + seconds + "");
		} else
			// tvSeconds.setText(seconds + "");
			sb.append(seconds + "");
		return sb.toString();
	}
}
