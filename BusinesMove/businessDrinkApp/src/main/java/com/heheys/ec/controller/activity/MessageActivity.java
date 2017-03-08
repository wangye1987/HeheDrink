package com.heheys.ec.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.PushBean;
import com.heheys.ec.model.dataBean.PushBean.MsgBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.List;

/**
 * Describe:消息页面
 * 
 * Date:2015-10-29
 * 
 * Author:wk
 */
public class MessageActivity extends BaseActivity {

	private RelativeLayout kefu_msg;
	private RelativeLayout system_msg;
	private RelativeLayout relative_msg;
	private TextView tv_preview;
	private TextView tv_time;
	private TextView tv_msg_time;
	private TextView tv_message;
	private TextView tv_num_system;
	private TextView tv_num_notice;
	private Context mContext;
	private PushBean pushBean;
	private MessageHandler hanlder = new MessageHandler(this);
	private int type;//系统消息
	private RelativeLayout yhj_msg;
	private TextView tv_title_message_yhj;
	private TextView tv_message_yhj;
	private TextView msg_num_notice_yhj;
	private TextView tv_msg_time_yhj;
	private List<MsgBean> listData;
	@Override
	protected void onCreate() {
		setBaseContentView(R.layout.message_layout);
		mContext = MessageActivity.this;
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadChildView() {
		yhj_msg = (RelativeLayout) findViewById(R.id.relative_yhj_msg);
		kefu_msg = (RelativeLayout) findViewById(R.id.relative_kefu_msg);
		system_msg = (RelativeLayout) findViewById(R.id.relative_system_msg);
		relative_msg = (RelativeLayout) findViewById(R.id.relative_salon_msg);
		tv_preview = (TextView) findViewById(R.id.tv_preview);//系统消息最后一条消息预览
		tv_time = (TextView) findViewById(R.id.tv_time);//系统消息最后一条消息发送时间
		tv_msg_time = (TextView) findViewById(R.id.tv_msg_time);//通知消息最后一条消息发送时间
		tv_message = (TextView) findViewById(R.id.tv_message);//通知消息最后一条消息预览
		tv_num_system = (TextView) findViewById(R.id.msg_num_system);//通知消息未读数量
		tv_num_notice = (TextView) findViewById(R.id.msg_num_notice);//通知消息未读数量
		
		tv_title_message_yhj = (TextView) findViewById(R.id.tv_title_message_yhj);//消息title
		tv_message_yhj = (TextView) findViewById(R.id.tv_message_yhj);//消息预览
		msg_num_notice_yhj = (TextView) findViewById(R.id.msg_num_notice_yhj);//未读消息个数
		tv_msg_time_yhj = (TextView) findViewById(R.id.tv_msg_time_yhj);//消息时间
		initData();
	}

	private void initData() {
		Intent intent = getIntent();
		if(intent != null){
			pushBean = (PushBean) intent.getSerializableExtra("pushBean");
			if(pushBean.getResult()!=null && pushBean.getResult().getList()!=null && pushBean.getResult().getList().size()>0) {
				bindViewData();
			}else{
				ApiHttpCilent.getInstance(getApplicationContext()).MsgPush(baseActivity, "1","10",new MyMsgCallBack());
			}
		}
	}

	@Override
	protected void getNetData() {
//		ApiHttpCilent.getInstance(baseActivity).MsgPush(baseActivity, new MyMsgCallBack());
	}
	
	/**
	 * 
	 * Describe:获取消息数据请求回调
	 * 
	 * Date:2015-11-03
	 * 
	 * Author:wk
	 */
	public class MyMsgCallBack extends
			BaseJsonHttpResponseHandler<PushBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, PushBean arg4) {
			Dismess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			hanlder.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				PushBean arg3) {
		}

		@Override
		protected PushBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dismess();
			Gson gson = new Gson();
			pushBean = gson.fromJson(response,
					PushBean.class);
			Message message = Message.obtain();
			if ("1".equals(pushBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = pushBean.getError().getInfo();
			}
			hanlder.sendMessage(message);
			return pushBean;
		}
	}
	private void Dismess() {
		MessageActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	/**
	 * 
	 * Describe:获取消息数据处理网络请求消息
	 * 
	 * Date:2015-11-03
	 * 
	 * Author:王奎
	 */
	public static class MessageHandler extends WeakHandler<MessageActivity> {

		public MessageHandler(MessageActivity reference) {
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
				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference().baseActivity,
							messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					// ToastUtil.showToast(getReference().baseActivity, "请求失败");

				}

				break;
			}
		}
	}
	
	
	private void bindViewData() {
		if(pushBean.getResult()!=null && pushBean.getResult().getList()!=null && pushBean.getResult().getList().size()>0){
			listData = pushBean.getResult().getList();
			int size = listData.size();
			for(int i=0;i<size;i++){
				if(i==0){
					tv_preview.setText(listData.get(i).getLastmsg());
					tv_time.setText(listData.get(i).getTime());
					if(listData.get(i).getUnread()>0){
//						tv_num_system.setText(listData.get(i).getUnread()+"");
//						tv_num_system.setText("");
						tv_num_system.setVisibility(View.VISIBLE);
						tv_num_system.setText(listData.get(i).getUnread()+"");
					}else{
						tv_num_system.setVisibility(View.INVISIBLE);	
					}
					type = listData.get(i).getType();
				}else if(i==1){
					tv_msg_time.setText(listData.get(i).getTime());
					tv_message.setText(listData.get(i).getLastmsg());
					if(listData.get(i).getUnread()>0){
						tv_num_notice.setVisibility(View.VISIBLE);
						tv_num_notice.setText(listData.get(i).getUnread()+"");
					}else{
						tv_num_notice.setVisibility(View.INVISIBLE);	
					}
					type = listData.get(i).getType();
				}else if(i==2){
					type = listData.get(i).getType();
					tv_msg_time_yhj.setText(listData.get(i).getTime());
					tv_message_yhj.setText(listData.get(i).getLastmsg());
					if(listData.get(i).getUnread()>0){
						msg_num_notice_yhj.setVisibility(View.VISIBLE);
						msg_num_notice_yhj.setText(listData.get(i).getUnread()+"");
					}else{
						msg_num_notice_yhj.setVisibility(View.INVISIBLE);	
					}
				}
			}
		}
	}
	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		system_msg.setOnClickListener(this);
		relative_msg.setOnClickListener(this);
		kefu_msg.setOnClickListener(this);
		yhj_msg.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
		super.onClick(v);
		HashMap<String,String> map  = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.relative_system_msg://点击系统消息
			if(listData!=null && listData.size()>0)
				type = listData.get(0).getType();
			startactivity();
			break;
		case R.id.relative_salon_msg://点击通知消息
			if(listData!=null && listData.size()>0)
				type = listData.get(1).getType();
			startactivity();
			
			break;
		case R.id.relative_kefu_msg:
//			MyApplication myapplication = MyApplication.getInstance();
//			if(myapplication.getmIMKit()!=null){
//				Intent intents = myapplication.getmIMKit().getConversationActivityIntent();
//				startActivity(intents);
//			}
//			if(LoginSampleHelper.getInstance().getIMKit()!=null){
//				Intent intents = LoginSampleHelper.getInstance().getIMKit().getConversationActivityIntent();
//				startActivity(intents);
//			}else{
////				LoginSampleHelper.getInstance().initSDK_Sample(getApplication());
////				ApiHttpCilent.getInstance(this).RequsetIM(this, productInfor.getId(),new ImRequestCallBack());
//			}
			break;
		case R.id.relative_yhj_msg:
			if(listData!=null && listData.size()>0)
			type = listData.get(2).getType();
			startactivity();
			break;
		
		default:
			break;
		}
//		 0 系统 1 通知 2优惠券 3 聊天
		map.put("msgType",MapValue(type));
		MobclickAgent.onEvent(baseActivity,"C_HMN_HMN_1",map);
	}
	 void startactivity(){
		 Intent intent = new Intent(this,MessageDetailActivity.class);
		 intent.putExtra("type", type);
		 StartActivityUtil.startActivity(baseActivity,intent);
	 }
	 
	 private String MapValue(int type){
		 switch (type) {
		case 0:
			return "系统消息";
		case 1:
			return "通知消息";
		case 2:
			return "优惠券消息";
		case 3:
			return "聊天消息";
		default:
			break;
		}
			return "系统消息";
	 }
	@Override
	protected String setTitleName() {
		return "消息";
	}

	@Override
	protected String setRightText() {
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		return 0;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getNetData();
		MobclickAgent.onResume(this);       //统计时长
	}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}