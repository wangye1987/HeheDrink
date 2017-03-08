//package com.heheys.ec.model.adapter;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.heheys.ec.R;
//import com.heheys.ec.application.MyApplication;
//import com.heheys.ec.controller.activity.ProductDetail;
//import com.heheys.ec.lib.activityManager.StartActivityUtil;
//import com.heheys.ec.lib.base.BaseListAdapter;
//import com.heheys.ec.lib.base.ViewHolderUtil;
//import com.heheys.ec.lib.utils.StringUtil;
//import com.heheys.ec.lib.utils.TimeUtil;
//import com.heheys.ec.lib.utils.WeakHandler;
//import com.heheys.ec.lib.view.ViewUtil;
//import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.GroupBuyProductInfor;
//import com.heheys.ec.utils.ConstantsUtil;
//import com.heheys.ec.utils.LogUtil;
//
///**
// * Describe:拼单区域网格样式数据适配器
// * 
// * Date:2015-9-29
// * 
// * Author:liuzhouliang
// */
//public class GroupBuyGridAdapter extends BaseListAdapter<GroupBuyProductInfor> {
//	private String TAG = GroupBuyGridAdapter.class.getName();
//	// 存储倒计时差
//	private List<Long> countDownTimeList;
//	private boolean isPlay;
//	// private ProgressBar pb;
//	// private int p = 0;
//	// private Timer timer;
//	// private TimerTask timerTask;
//	// final ProgressHandler handlerProgress = new ProgressHandler(this);
//	int progress;
//	// ProgressBar mpb;
//	private Handler handler = new Handler();
//	private Runnable runnable = new Runnable() {
//
//		@Override
//		public void run() {
//			if (!isPlay)
//				return;
//			handler.postDelayed(this, 1000);
//			if (countDownTimeList != null && countDownTimeList.size() > 0) {
//				int size = countDownTimeList.size();
//				for (int i = 0; i < size; i++) {
//					if (countDownTimeList.get(i) > 0) {
//						countDownTimeList.set(i,
//								countDownTimeList.get(i) - 1000);
//					}
//				}
//			}
//
//			notifyDataSetChanged();
//		}
//	};
//
//	public GroupBuyGridAdapter(boolean mIsGridViewidle,List<GroupBuyProductInfor> data, Context context) {
//		super(data, context);
//		// TODO Auto-generated constructor stub
//		initCountDownTime();
//	}
//
//	@Override
//	public void setNewData(List<GroupBuyProductInfor> newData) {
//
//		super.setNewData(newData);
//		initCountDownTime();
//	}
//
//	/**
//	 * 
//	 * Describe:启动倒计时
//	 * 
//	 * Date:2015-10-12
//	 * 
//	 * Author:liuzhouliang
//	 */
//	public void startCountDownTime() {
//		if (isPlay) {
//			return;
//		}
//		isPlay = true;
//		runnable.run();
//	}
//
//	/**
//	 * 
//	 * Describe:停止倒计时
//	 * 
//	 * Date:2015-10-12
//	 * 
//	 * Author:liuzhouliang
//	 */
//
//	public void stopCountDownTime() {
//		isPlay = false;
//	}
//
//	/**
//	 * 
//	 * Describe:初始化倒计时
//	 * 
//	 * Date:2015-10-10
//	 * 
//	 * Author:liuzhouliang
//	 */
//	public void initCountDownTime() {
//		/**
//		 * 处理倒计时
//		 */
//		if (dataList != null) {
//			int size = dataList.size();
//			if (size > 0) {
//				if (countDownTimeList != null) {
//					countDownTimeList.clear();
//				}
//				countDownTimeList = new ArrayList<Long>();
//				for (int i = 0; i < size; i++) {
//					String startTimeString = dataList.get(i).getEndtime();
//					long appointTime = TimeUtil
//							.changeDateToTime(startTimeString);
//					long currentTime = System.currentTimeMillis();
//					long gapTime = appointTime - currentTime;
//					if (gapTime > 0) {
//						countDownTimeList.add(i, gapTime);
//					} else {
//						countDownTimeList.add(i, 0L);
//					}
//				}
//			}
//		}
////		LogUtil.d(TAG, countDownTimeList.toString());
//
//	}
//
//	@Override
//	public View bindView(final int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			convertView = baseInflater.inflate(R.layout.group_buy_grid_item,
//					parent, false);
//		}
//		GroupBuyProductInfor bean = dataList.get(position);
//		TextView tvActivityPrice = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_activity_price);
//		TextView tvNormalPrice = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_normal_price);
//		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
//				.getItemView(convertView, R.id.group_buy_grid_item_time_parent);
//		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_time_day);
//		TextView tv_time_day_name = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_list_item_day);
//		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_time_hour);
//		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_time_minute);
//		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_time_second);
//		TextView tv_wine_name = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_wine_name);
//		TextView tv_sold = (TextView) ViewHolderUtil.getItemView(convertView,
//				R.id.group_buy_grid_item_totalnow);
//		TextView tv_totalnum = (TextView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_totalnum);
//		ImageView iv_wineurl = (ImageView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_product_pic);
//		ImageView ivActivityIcon = (ImageView) ViewHolderUtil.getItemView(
//				convertView, R.id.group_buy_grid_item_activity_icon);
//		ProgressBar pb = (ProgressBar) ViewHolderUtil.getItemView(convertView,
//				R.id.group_buy_grid_item_pb);
//		if (countDownTimeList.get(position) == 0) {
//			llTimeParentLayout.setVisibility(View.VISIBLE);
//			tv_time_day.setText("00");
//			tv_time_hour.setText("00");
//			tv_time_minitue.setText("00");
//			tv_time_second.setText("00");
//		} else {
//			llTimeParentLayout.setVisibility(View.VISIBLE);
//			setCountDownTime(countDownTimeList.get(position), tv_time_day,
//					tv_time_day_name, tv_time_hour, tv_time_minitue,
//					tv_time_second);
//		}
//
//		tv_wine_name.setText(bean.getName());
//		ViewUtil.setActivityPrice(tvActivityPrice, bean.getCprice());
//		ViewUtil.setNormalPrice(tvNormalPrice, bean.getPrice());
//		// 已经售卖数量
//		int soldNum = 0;
//		if (!StringUtil.isEmpty(bean.getSoldnum())) {
//			soldNum = Integer.parseInt(bean.getSoldnum());
//		}
//
//		if (soldNum == Integer.parseInt(bean.getKnum())) {
//			tv_sold.setText("已全部售完");
//		} else {
//			tv_sold.setText("已售" + soldNum + "" + (bean.getUnit()==null?"":bean.getUnit()));
//		}
//		// tv_totalnow.setText(bean.getSoldnum());
//		float totalnum = 0;
//		String numStr = "";
//		//处理数量太多bug
//		if(bean.getKnum()!=null){
//			totalnum = Long.parseLong(bean.getKnum());
//			if(totalnum>10000){
//				totalnum = totalnum/1000;
//				numStr = totalnum+"万";
//			}else{
//				numStr = bean.getKnum();
//			}
//		}
//		tv_totalnum.setText("共" + numStr + (bean.getUnit()==null?"":bean.getUnit()));
//		float proportion = ((float) soldNum)
//				/ (float) (Integer.parseInt(bean.getKnum()));
//		// DecimalFormat df = new DecimalFormat("0.00");//格式化小数
//		// String s = df.format(proportion);//返回的是String类型
//		int maxProgress = (int) (proportion * 100);
////		pb.setProgress(maxProgress);
//		// updateProgress(pb, maxProgress);
////		if(mIsGridViewidle){
//		MyApplication.imageLoader.displayImage(bean.getPic(), iv_wineurl,
//				MyApplication.options);
////		}
//		convertView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(mcontext, ProductDetail.class);
//				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
//						dataList.get(position).getId());
//				StartActivityUtil.startActivity((Activity) mcontext, intent);
//			}
//		});
//		/**
//		 * 控制活动图标的显示
//		 */
//		String activityStateString = dataList.get(position).getStatus();
//		if (!StringUtil.isEmpty(activityStateString)) {
//			if ("1".equals(activityStateString)) {
//				/**
//				 * 已经发布
//				 */
//				Resources res = mcontext.getResources();
//				pb.setProgressDrawable(res
//						.getDrawable(R.drawable.progressbar_color));
//				pb.setProgress(maxProgress);
//				ivActivityIcon.setVisibility(View.GONE);
//				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
//						R.color.color_ff0000));
//				tv_sold.setTextColor(mcontext.getResources().getColor(
//						R.color.color_f9883d));
//			} else if ("2".equals(activityStateString)) {
//				/**
//				 * 已经抢光
//				 */
//				Resources res = mcontext.getResources();
//				pb.setProgressDrawable(res
//						.getDrawable(R.drawable.progressbar_color_gray));
//				pb.setProgress(maxProgress);
//				ivActivityIcon.setVisibility(View.VISIBLE);
//				ivActivityIcon.setImageResource(R.drawable.panic_buy_over);
//				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
//						R.color.color_b7b7b7));
//				tv_sold.setTextColor(mcontext.getResources().getColor(
//						R.color.gray_text_notice));
//			} else if ("3".equals(activityStateString)) {
//				/**
//				 * 已经结束
//				 */
//				Resources res = mcontext.getResources();
//				pb.setProgressDrawable(res
//						.getDrawable(R.drawable.progressbar_color_gray));
//				pb.setProgress(maxProgress);
//				ivActivityIcon.setVisibility(View.VISIBLE);
//				ivActivityIcon.setImageResource(R.drawable.activity_over);
//				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
//						R.color.color_b7b7b7));
//				tv_sold.setTextColor(mcontext.getResources().getColor(
//						R.color.gray_text_notice));
//			} else {
//				tv_sold.setTextColor(mcontext.getResources().getColor(
//						R.color.color_f9883d));
//				Resources res = mcontext.getResources();
//				pb.setProgressDrawable(res
//						.getDrawable(R.drawable.progressbar_color));
//				pb.setProgress(maxProgress);
//				ivActivityIcon.setVisibility(View.GONE);
//			}
//		} else {
//			Resources res = mcontext.getResources();
//			pb.setProgressDrawable(res
//					.getDrawable(R.drawable.progressbar_color));
//			pb.setProgress(maxProgress);
//			ivActivityIcon.setVisibility(View.GONE);
//		}
//		return convertView;
//	}
//
//	private void updateProgress(final ProgressBar pb, final int maxProgress) {
//		// mpb = pb;
//		// int progress;
//		final Timer timer;
//		TimerTask timerTask;
//		timer = new Timer();
//		timerTask = new TimerTask() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				progress = progress + 2;
//				if (progress < maxProgress || progress == maxProgress) {
//					// Message message = Message.obtain();
//					// message.what = 1001;
//					// handlerProgress.sendMessage(message);
//					pb.setProgress(progress);
//				} else {
//					timer.cancel();
//				}
//
//			}
//		};
//		timer.schedule(timerTask, 0, 10);
//
//	}
//
//	// public static class ProgressHandler extends
//	// WeakHandler<GroupBuyGridAdapter> {
//	//
//	// public ProgressHandler(GroupBuyGridAdapter reference) {
//	// super(reference);
//	// // TODO Auto-generated constructor stub
//	// }
//	//
//	// @Override
//	// public void handleMessage(Message msg) {
//	// // TODO Auto-generated method stub
//	// super.handleMessage(msg);
//	// switch (msg.what) {
//	// case 1001:
//	// getReference().mpb.setProgress(getReference().progress);
//	// break;
//	// }
//	// }
//	//
//	// }
//	/**
//	 * 
//	 * Describe:设置倒计时时间
//	 * 
//	 * Date:2015-10-12
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void setCountDownTime(long millisUntilFinished, TextView tvDay,
//			TextView tv_time_day_name, TextView tvHour, TextView tvMinute,
//			TextView tvSeconds) {
//		long days, hours, minutes, seconds;
//		if (millisUntilFinished < 0) {
//			return;
//		}
//		if (millisUntilFinished <= 1000 * 60 * 60 * 24) {
//			tv_time_day_name.setVisibility(View.GONE);
//			tvDay.setVisibility(View.GONE);
//			// 获取小时值
//			hours = millisUntilFinished / (60 * 60 * 1000);
//			// 获取分值
//			minutes = (millisUntilFinished - hours * (60 * 60 * 1000))
//					/ (60 * 1000);
//			// 获取秒值
//			seconds = (millisUntilFinished - hours * (60 * 60 * 1000) - minutes
//					* (60 * 1000)) / 1000;
//			if (hours < 10) {
//				tvHour.setText("0" + hours);
//			} else
//				tvHour.setText(hours + "");
//			if (minutes < 10) {
//				tvMinute.setText("0" + minutes);
//			} else
//				tvMinute.setText(minutes + "");
//			if (seconds < 10) {
//				tvSeconds.setText("0" + seconds);
//			} else
//				tvSeconds.setText(seconds + "");
//		} else {
//			// 获取天数
//			days = millisUntilFinished / (60 * 60 * 1000 * 24);
//			// 获取小时值
//			hours = (millisUntilFinished - days * (60 * 60 * 1000 * 24))
//					/ (60 * 60 * 1000);
//			// 获取分值
//			minutes = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)) - hours
//					* (60 * 60 * 1000))
//					/ (60 * 1000);
//			// 获取秒值
//			seconds = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)
//					- hours * (60 * 60 * 1000) - minutes * (60 * 1000))) / 1000;
//			if (days < 10) {
//				tvDay.setText("0" + days);
//			} else
//				tvDay.setText(days + "");
//			if (hours < 10) {
//				tvHour.setText("0" + hours);
//			} else
//				tvHour.setText(hours + "");
//			if (minutes < 10) {
//				tvMinute.setText("0" + minutes);
//			} else
//				tvMinute.setText(minutes + "");
//			if (seconds < 10) {
//				tvSeconds.setText("0" + seconds);
//			} else
//				tvSeconds.setText(seconds + "");
//		}
//
//	}
//}
