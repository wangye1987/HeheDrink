package com.heheys.ec.model.adapter;

import java.util.ArrayList;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.OrderBaseBean.OrderListBean;
import com.heheys.ec.model.dataBean.OrderBaseDetailBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 作者 E-mail:wk
 * @version 创建时间：2015-9-21 下午5:53:24 类说明
 * @param <E>
 * @param 订单列表list适配器
 */
public class OrderListAdapter<E> extends BaseListAdapter<OrderListBean> {

	private List<OrderListBean> data;
	private Context context;
	private TextView tv_orderFactory;
	private TextView tv_winename;
	private TextView tv_winenum;
	private Button tv_status;
	private TextView tv_orderTime;
	private ImageView iv_pic;
	private Button pay_button;
	private ArrayList<Integer> orderId;
	private MyHandler handler = new MyHandler(this);
	private int id;
	private RefreshCallBack callback;
	private TextView tvDeviderTextView;

	// public OrderListAdapter(List<OrderListBean> data, Object obj, Context
	// context) {
	// super(data, obj, context);
	// // TODO Auto-generated constructor stub
	// }

	public OrderListAdapter(List<OrderListBean> data, Context context,
			RefreshCallBack callback) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.context = context;
		orderId = new ArrayList<Integer>();
		this.callback = callback;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		OrderListBean bean = dataList.get(position);
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.order_item, parent,
					false);
		}
		tvDeviderTextView = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.textView1);
		if (dataList.size() == 1) {
			tvDeviderTextView.setVisibility(View.GONE);
		} else {
			tvDeviderTextView.setVisibility(View.VISIBLE);
		}
		pay_button = (Button) ViewHolderUtil.getItemView(convertView,
				R.id.pay_button);
		tv_orderFactory = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_orderFactory);
		tv_orderTime = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_createtime);
		tv_winename = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_winename);
		tv_winenum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_winenum);
		tv_status = (Button) ViewHolderUtil.getItemView(convertView,
				R.id.tv_status);
		iv_pic = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.iv_pic);
		id = bean.getId();
		final int ordersubmit = id;
		tv_orderFactory.setText(bean.getShopname());
		tv_orderTime.setText(bean.getCreatetime());
		tv_winename.setText(bean.getName());
		tv_winenum.setText(bean.getNum() + "" + bean.getUnit());
		MyApplication.imageLoader.displayImage(bean.getPic(), iv_pic,
				MyApplication.options);

		final int status = bean.getStatus();
		if (status == 1 || status == 2 || status == 3 || status == 7) {
			pay_button.setVisibility(View.VISIBLE);
			tv_status.setVisibility(View.INVISIBLE);
			pay_button.setText(bean.getStatusname());
		} else {
			tv_status.setVisibility(View.VISIBLE);
			pay_button.setVisibility(View.INVISIBLE);
			tv_status.setTextColor(context.getResources().getColor(
					R.color.color_f9883d));
			tv_status.setText(bean.getStatusname());
		}
		pay_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String,String> map = new HashMap<String,String>();
				if (status == 7) {
					ApiHttpCilent.getInstance(context.getApplicationContext()).ConfirmGoods(context,
							ordersubmit, new MyCallBack());
					map.put("getgoods","");
					MobclickAgent.onEvent(mcontext, "0072", map); 
				} else {
					map.put("payorder","");
					MobclickAgent.onEvent(mcontext, "0071", map); 
					orderId.clear();
					orderId.add(ordersubmit);
					Intent intents = new Intent(context, JDPayActivity.class);
					intents.putExtra("flag", 2);// 传递2是订单详细支付跳转过去
					intents.putIntegerArrayListExtra("orderId", orderId);// 传递子订单号集合
					StartActivityUtil
							.startActivity((Activity) context, intents);
				}
			}
		});
		return convertView;
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<OrderBaseDetailBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, OrderBaseDetailBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				OrderBaseDetailBean arg3) {
			Dimess();
		}

		@Override
		protected OrderBaseDetailBean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<OrderBaseDetailBean>() {
			}.getType();
			OrderBaseDetailBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = bean.getError().getInfo();
			}
			handler.sendMessage(message);
			return bean;
		}
	}

	private void Dimess() {
		((Activity) context).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	public class MyHandler extends WeakHandler<OrderListAdapter<E>> {

		public MyHandler(OrderListAdapter<E> reference) {
			super(reference);
		}

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				ToastUtil.showToast(context, "操作成功");
				callback.setback(true, 8);
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(context, back);
				break;
			default:
				break;
			}
		}
	}

	public interface RefreshCallBack {
		void setback(boolean isRresh, int status);
	}
}
