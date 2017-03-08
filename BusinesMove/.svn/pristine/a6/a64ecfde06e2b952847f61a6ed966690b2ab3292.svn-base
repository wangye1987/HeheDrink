package com.heheys.ec.model.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.controller.activity.SalonDetailActivity;
import com.heheys.ec.controller.activity.SalonJoinActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.view.DrawLineTextView;
import com.heheys.ec.model.dataBean.SalonListBean.SalonResultBean.SalonInfor;
import com.heheys.ec.utils.IsLogin;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

//import com.heheys.ec.controller.fragment.SalonListFragment;

/**
 * Describe:沙龙列表数据适配器
 * 
 * Date:2015-9-25
 * 
 * Author:liuzhouliang
 */
public class SalonListAdapter extends BaseListAdapter<SalonInfor> {
	Context context;
	private TextView tv_name;
	private TextView tv_address;
	private TextView tv_time_start;
	private TextView tv_time_end;
	private TextView tv_people;
	private ImageView iv_iamge;
	private Button commit_verify;
	private String baseUrlString;
//	private SalonListActivity mactivity;
	private ImageView ivState;
	private String userIdString;
	private DrawLineTextView line;
	private Animation maniAnimation;

	public SalonListAdapter(String userId, List<SalonInfor> data,
			Context context, String mBaseUrl) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.baseUrlString = mBaseUrl;
//		this.mactivity = (SalonListActivity) context;
//		this.mactivity =  (SalonListFragment)context;
		this.userIdString = userId;
		maniAnimation = AnimationUtils.loadAnimation(mcontext, R.anim.show_in);
	}

	@SuppressLint("NewApi")
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.salon_item, parent,
					false);
		}
		line = (DrawLineTextView) ViewHolderUtil.getItemView(convertView,
				R.id.salon_item_line);
		ivState = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.salon_item_state);
		tv_name = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_name);
		tv_address = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_address);
		tv_time_start = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.salon_item_starttime);
		tv_time_end = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.salon_item_endtime);
		tv_time_end = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.salon_item_endtime);
		tv_people = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_people);
		iv_iamge = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.iv_iamge);
		commit_verify = (Button) ViewHolderUtil.getItemView(convertView,
				R.id.commit_verify);
		iv_iamge.startAnimation(maniAnimation);
		tv_name.setText(dataList.get(position).getSubject());
		tv_address.setText(dataList.get(position).getAddress());
		tv_time_start.setText(dataList.get(position).getStarttime());
		tv_time_end.setText(dataList.get(position).getEndtime());
		tv_people.setText("限制参与人数" + dataList.get(position).getMaxnum() + "人");
		String sign = dataList.get(position).getSign();
		String stateString = dataList.get(position).getStatus();

		/**
		 * 判断是否报名===========================
		 */
		if ("0".equals(sign)) {
			/**
			 * 未报名=====================
			 */

			if ("2".equals(stateString) || "7".equals(stateString)) {
				// commit_verify.setBackground(mcontext.getResources()
				// .getDrawable(R.drawable.bg_gray_corner));
				/**
				 * 还未报名，但报名已经结束，还未报名，活动已经开始
				 */
				commit_verify.setBackground(mcontext.getResources().getDrawable(R.drawable.bg_gray_corner));
				commit_verify.setVisibility(View.VISIBLE);
				commit_verify.setText("报名已结束");
				commit_verify.setOnClickListener(null);

			} else if ("3".equals(stateString)) {
				/**
				 * 还未报名，但报名已经报满
				 */
				commit_verify.setBackground(mcontext.getResources().getDrawable(R.drawable.bg_gray_corner));
				commit_verify.setVisibility(View.VISIBLE);
				commit_verify.setText("报名已经报满");
				commit_verify.setOnClickListener(null);
			} else {
				/**
				 * 可以报名
				 */
				// commit_verify.setBackground(mcontext.getResources()
				// .getDrawable(R.drawable.sharp_round));
				commit_verify.setBackground(mcontext.getResources().getDrawable(R.drawable.sharp_round));
				commit_verify.setText("立即报名");
				commit_verify.setVisibility(View.VISIBLE);
				line.setVisibility(View.VISIBLE);
				commit_verify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("apply", "");
						MobclickAgent.onEvent(mcontext, "0082", map);
						if (!IsLogin.isLogin((Activity) mcontext)) {
							/**
							 * 未登录
							 */
							MyApplication.getInstance().startLogin(
									new LoginCallBack() {

										@Override
										public void loginSuccess() {
											Intent intent = new Intent(
													 mcontext,
													SalonJoinActivity.class);
											intent.putExtra("sid", dataList
													.get(position).getId());
											intent.putExtra("FROM", 1);
											StartActivityUtil
													.startActivityForResult(
															(Activity) mcontext,
															intent, 1001);
										}

										@Override
										public void loginFail() {
										}
									}, (Activity) mcontext);

						} else {
							Intent intent = new Intent( mcontext,
									SalonJoinActivity.class);
							intent.putExtra("sid", dataList.get(position)
									.getId());
							intent.putExtra("FROM", 1);
							StartActivityUtil.startActivityForResult(
									(Activity) mcontext, intent, 1001);

						}
					}
				});

			}

		} else if ("1".equals(sign)) {
			/**
			 * 已经报名
			 */
			commit_verify.setVisibility(View.VISIBLE);
			commit_verify.setText("已报名");
			// commit_verify.setBackground(mcontext.getResources().getDrawable(
			// R.drawable.bg_gray_corner));
//			commit_verify.setBackground(mcontext.getDrawable(R.drawable.bg_gray_corner));
			commit_verify.setBackground(mcontext.getResources().getDrawable(R.drawable.bg_gray_corner));
		} else {
			commit_verify.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
		}

		if ("1".equals(stateString)) {
			/**
			 * 进行中
			 */
			ivState.setVisibility(View.VISIBLE);
//			ivState.setBackground(mcontext.getResources().getDrawable(R.drawable.fiery_registration));
			ivState.setImageResource(R.drawable.fiery_registration);
		}
		if ("2".equals(stateString)) {
			/**
			 * 已经结束
			 */
			line.setVisibility(View.GONE);
			commit_verify.setVisibility(View.GONE);
			ivState.setVisibility(View.VISIBLE);
//			ivState.setBackground(mcontext.getResources().getDrawable(R.drawable.salon_over));
			ivState.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.salon_over));
		}
		if ("3".equals(stateString)) {
			/**
			 * 已经报满
			 */
			ivState.setVisibility(View.VISIBLE);
			ivState.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.salon_apply_full));
		}
		if ("5".equals(stateString)) {
			/**
			 * 报名未开始，报名不可用
			 */
			ivState.setVisibility(View.VISIBLE);
			ivState.setImageResource(R.drawable.registration_to_start);
			commit_verify.setBackground(mcontext.getResources().getDrawable(R.drawable.bg_gray_corner));
			commit_verify.setText("立即报名");
			commit_verify.setOnClickListener(null);
		}
		if ("6".equals(stateString)) {
			/**
			 * 活动即将 开始
			 */
			ivState.setVisibility(View.VISIBLE);
			ivState.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.about_to_begin));
//			ivState.setImageResource(R.drawable.about_to_begin);
		}
		if ("7".equals(stateString)) {
			/**
			 * 活动进行中
			 */
			ivState.setVisibility(View.VISIBLE);
//			ivState.setImageResource(R.drawable.activities_carried_out);
			ivState.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.activities_carried_out));
		}
		if (!StringUtil.isEmpty(dataList.get(position).getPic())) {
			MyApplication.imageLoader.displayImage(baseUrlString
					+ dataList.get(position).getPic(), iv_iamge,
					MyApplication.options);

		} else {
			// iv_iamge.setImageResource(R.drawable.imageview_default);
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("salondetail", "");
				MobclickAgent.onEvent(mcontext, "0081", map);
				Intent intent = new Intent( mcontext,
						SalonDetailActivity.class);
				intent.putExtra("id", dataList.get(position).getId());
				intent.putExtra("state", dataList.get(position).getStatus());
				intent.putExtra("userid", userIdString);
				StartActivityUtil.startActivity((Activity) mcontext, intent);
			}
		});

		return convertView;
	}

}
