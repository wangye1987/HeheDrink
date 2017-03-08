package com.heheys.ec.model.adapter;

import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.controller.activity.SalonCustomerDetail;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.DrawLineTextView;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.MySalonDetailBean.MySalonDetailInfor.MySalonCustomerInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * Describe:沙龙详情客户列表数据适配器
 * 
 * Date:2015-10-19
 * 
 * Author:liuzhouliang
 */
public class SalonCustomerListAdapter extends
		BaseListAdapter<MySalonCustomerInfor> {
	private BaseBean dataResultBaseBean;

	private CustomerMessageHandler messageHandler;
	private int sid;
	private CheckType type;
	private int currentPosition;
	private String muserId;

	public enum CheckType {
		AGREE_EXCHANGE, EXCHANGE
	}

	public SalonCustomerListAdapter(List<MySalonCustomerInfor> data,
			Context context, int id, String userId) {
		super(data, context);
		// TODO Auto-generated constructor stub
		messageHandler = new CustomerMessageHandler(this);
		sid = id;
		muserId=userId;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.salon_customer_list_item, parent, false);
		}
		DrawLineTextView line = (DrawLineTextView) ViewHolderUtil.getItemView(
				convertView, R.id.salon_customer_list_item_line);
		if (position == (dataList.size()-1)) {
			line.setVisibility(View.GONE);
		} else {
			line.setVisibility(View.VISIBLE);
		}
		TextView nameTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.salon_customer_name);
		TextView positionTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.salon_customer_position);
		TextView companyTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.salon_customer_company);
		Button contactStateButton = (Button) ViewHolderUtil.getItemView(
				convertView, R.id.salon_customer_contact_state);
		nameTextView.setText(dataList.get(position).getName());

		positionTextView.setText(dataList.get(position).getPosition());

		companyTextView.setText(dataList.get(position).getCompany());

		String stateString = dataList.get(position).getStatus();
		
		if ("0".equals(stateString)) {
			contactStateButton.setVisibility(View.VISIBLE);
			contactStateButton.setText("交换联系方式");
			contactStateButton.setTextColor(mcontext.getResources().getColor(
					R.color.white));
			// contactStateButton.setBackground(mcontext
			// .getDrawable(R.drawable.sharp_round));
			contactStateButton.setBackgroundResource(R.drawable.sharp_round);
			contactStateButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("changetel","");
					MobclickAgent.onEvent(mcontext, "0086", map); 
					currentPosition = position;
					type = CheckType.EXCHANGE;
					exchangeContact(dataList.get(position).getUid());
				}
			});
			convertView.setOnClickListener(null);
		} else if ("1".equals(stateString)) {
			contactStateButton.setVisibility(View.VISIBLE);
			contactStateButton.setText("已发送");
			contactStateButton.setTextColor(mcontext.getResources().getColor(
					R.color.title_bg));
			contactStateButton.setBackgroundColor(mcontext.getResources()
					.getColor(android.R.color.transparent));
			contactStateButton.setOnClickListener(null);
			convertView.setOnClickListener(null);
			// convertView.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent(mcontext,
			// SalonCustomerDetail.class);
			// intent.putExtra("uid", dataList.get(position).getUid());
			// intent.putExtra("sid", sid + "");
			// StartActivityUtil
			// .startActivity((Activity) mcontext, intent);
			// }
			// });
		} else if ("2".equals(stateString)) {
			contactStateButton.setVisibility(View.VISIBLE);
			contactStateButton.setText("已交换");
			contactStateButton.setTextColor(mcontext.getResources().getColor(
					R.color.title_bg));
			contactStateButton.setBackgroundColor(mcontext.getResources()
					.getColor(android.R.color.transparent));
			contactStateButton.setOnClickListener(null);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mcontext,
							SalonCustomerDetail.class);
					intent.putExtra("uid", dataList.get(position).getUid());
					intent.putExtra("sid", sid + "");
					StartActivityUtil
							.startActivity((Activity) mcontext, intent);
				}
			});
		} else if ("5".equals(stateString)) {
			contactStateButton.setVisibility(View.VISIBLE);
			currentPosition = position;
			contactStateButton.setText("同意交换");
			contactStateButton.setTextColor(mcontext.getResources().getColor(
					R.color.white));
			// contactStateButton.setBackground(mcontext
			// .getDrawable(R.drawable.sharp_round));
			contactStateButton.setBackgroundResource(R.drawable.sharp_round);
			contactStateButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("changeteled","");
					MobclickAgent.onEvent(mcontext, "0087", map); 
					type = CheckType.AGREE_EXCHANGE;
					agreeExchangeContact(dataList.get(position).getUid());
				}
			});
			convertView.setOnClickListener(null);
		} else {
			contactStateButton.setOnClickListener(null);
			contactStateButton.setVisibility(View.GONE);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("checkbuscard","");
					MobclickAgent.onEvent(mcontext, "0088", map); 
					Intent intent = new Intent(mcontext,
							SalonCustomerDetail.class);
					intent.putExtra("uid", dataList.get(position).getUid());
					intent.putExtra("sid", sid + "");
					StartActivityUtil
							.startActivity((Activity) mcontext, intent);
				}
			});
		}
		if(!StringUtil.isEmpty(muserId)){
			if(muserId.equals(dataList.get(position).getUid())){
				convertView.setOnClickListener(null);
			}
		}
		
		return convertView;
	}

	/**
	 * 
	 * Describe:同意交换联系方式
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void agreeExchangeContact(String uid) {
		ApiHttpCilent.getInstance(mcontext).agreeExchangeContanct(mcontext,
				uid, sid + "", new RequestCallBack());
	}

	/**
	 * 
	 * Describe:请求交换联系方式
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void exchangeContact(String uid) {
		ApiHttpCilent.getInstance(mcontext).exchangeContanct(mcontext, uid,
				sid + "", new RequestCallBack());
	}
	private void Dimess() {
		((Activity) mcontext).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	/**
	 * 
	 * Describe:请求返回
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public class RequestCallBack extends BaseJsonHttpResponseHandler<BaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			dataResultBaseBean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(dataResultBaseBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = dataResultBaseBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = dataResultBaseBean.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return dataResultBaseBean;
		}

	}

	/**
	 * 
	 * Describe：请求消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public static class CustomerMessageHandler extends
			WeakHandler<SalonCustomerListAdapter> {

		public CustomerMessageHandler(SalonCustomerListAdapter reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				/**
				 * 处理成功的数据
				 */
				switch (getReference().type) {
				case AGREE_EXCHANGE:
					/**
					 * 同意交换名片
					 */
					ToastUtil.showToast(getReference().mcontext, "同意交换名片成功");
					getReference().dataList.get(getReference().currentPosition)
							.setStatus("2");
					getReference().notifyDataSetChanged();
					break;
				case EXCHANGE:
					/**
					 * 交换名片
					 */
					ToastUtil.showToast(getReference().mcontext, "请求交换名片成功");
					getReference().dataList.get(getReference().currentPosition)
							.setStatus("1");
					getReference().notifyDataSetChanged();
					break;
				default:
					break;
				}
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference().mcontext, messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					switch (getReference().type) {
					case AGREE_EXCHANGE:
						/**
						 * 同意交换名片
						 */
						ToastUtil
								.showToast(getReference().mcontext, "同意交换名片失败");
						break;
					case EXCHANGE:
						/**
						 * 交换名片
						 */
						ToastUtil.showToast(getReference().mcontext, "交换名片失败");
						break;
					default:
						break;
					}

				}

				break;
			}
		}

	}
}
