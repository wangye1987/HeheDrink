package com.heheys.ec.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.CouponActivity;
import com.heheys.ec.controller.activity.MyBalanceActivity;
import com.heheys.ec.controller.activity.MyOrderDetailActivity;
import com.heheys.ec.controller.activity.MyPointsActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.model.dataBean.MessageListBean.MsgBean;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;

import java.util.List;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-11-3 上午10:21:41 
 * 类说明 
 * @param
 */
public class MessgaeListAdapter extends BaseListAdapter<MsgBean> {
	private TextView msg_content;
	private TextView msg_time;
	private LinearLayout linear_msg;
	private LinearLayout linear_look;
	private  Context context;
	public MessgaeListAdapter(List<MsgBean> data, Context context) {
		super(data, context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MsgBean bean =  dataList.get(position);
		if(convertView == null){
			 convertView = baseInflater.inflate(R.layout.msg_item, parent, false);
		}
		linear_look  = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_look);
		linear_msg  = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_msg);
		msg_content  = (TextView) ViewHolderUtil.getItemView(convertView, R.id.msg_order_content);
		msg_time  = (TextView) ViewHolderUtil.getItemView(convertView, R.id.msg_order_time);
//		linear_msg = (LinearLayout) view.findViewById(R.id.linear_msg);
//		msg_content = (TextView) view.findViewById(R.id.msg_order_content);
//		msg_time = (TextView) view.findViewById(R.id.msg_order_time);
		msg_content.setText(""+bean.getMsg());
		msg_time.setText(bean.getTime());
		int isRead = bean.getIsread();//0 消息状态  0 未读  1 已读
		if(0==isRead){
			msg_content.setTextColor(context.getResources().getColor(R.color.color_454545));
			msg_time.setTextColor(context.getResources().getColor(R.color.color_454545));
		}else{
			msg_content.setTextColor(context.getResources().getColor(R.color.gray));
			msg_time.setTextColor(context.getResources().getColor(R.color.gray));
		}
		
		if(8 == bean.getBiztype() || 9 == bean.getBiztype() || 2 == bean.getBiztype() || 10 == bean.getBiztype()){
			//优惠券消息可以点击
			linear_look.setVisibility(View.VISIBLE);
		}else{
			linear_look.setVisibility(View.INVISIBLE);
		}
		final int currtPosition = position;
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(8 == dataList.get(currtPosition).getBiztype()){
					//优惠券消息可以点击
							Intent intents = new Intent(context,CouponActivity.class);
							intents.putExtra("key", "scan");//查看
							StartActivityUtil.startActivity((Activity) context, intents);
				}else if(9 == dataList.get(currtPosition).getBiztype()){
					//货到付款提示信息
							StartActivityUtil.startActivity((Activity) context, new Intent(context,MyPointsActivity.class));

				}else if(2 == dataList.get(currtPosition).getBiztype()){
					//订单详情
					Intent intent  = new Intent( mcontext, MyOrderDetailActivity.class);
					intent.putExtra("oid", dataList.get(currtPosition).getBiz_id());
					//传递0 是需要调取订单详情接口
					intent.putExtra("OrderFrom", "0");
					StartActivityUtil.startActivity((Activity) mcontext, intent);
				}else if(10 == dataList.get(currtPosition).getBiztype()){
					//我的余额
					if (!ToastNoNetWork.ToastError(context)){
						if (!IsLogin.isLogin((Activity) context)) {
							//登录
							NeedLoginStart(new Intent());
						} else {
							StartActivityUtil.startActivity((Activity) context, MyBalanceActivity.class);
						}}else{
						ToastUtil.showToast(context,"网络异常");
					}
				}
			}
		});
		return convertView;
	}
	private void NeedLoginStart(final Intent intent) {
		MyApplication.getInstance().startLogin(new MyApplication.LoginCallBack() {
			@Override
			public void loginSuccess() {
				intent.setClass(context, MyBalanceActivity.class);
				StartActivityUtil.startActivity((Activity) context, intent);
			}

			@Override
			public void loginFail() {

			}
		}, (Activity) context);
	}

}
 