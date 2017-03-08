package com.heheys.ec.controller.activity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.util.ArrayList;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.CouponMessageAdapter;
import com.heheys.ec.model.dataBean.CouponBaseBean;
import com.heheys.ec.model.dataBean.CouponBaseBean.Couponbean.couponitem;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

/**
 * @author wangkui
 *
 * @param 消息列表里面优惠券
 */
public class CouponMessageActivity extends BaseActivity {

	private ListView lv_coupon;
	private CouponMessageAdapter couponAdapter;
	private CouponHandler mhandler ;
	private CouponBaseBean basebean;
	private ArrayList<couponitem>  list;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.couponmessage_);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		mhandler = new CouponHandler(this);
		lv_coupon = (ListView) findViewById(R.id.lv_coupon);
		try {
			InputStream in = getResources().getAssets().open("Coupon.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			int size = reader.read();
			String line = "";
			StringBuffer sb = new StringBuffer();
			while((line = reader.readLine())!=null){
				sb.append(line);
			}
			Gson gson = new Gson();
			basebean = gson.fromJson(sb.toString(), CouponBaseBean.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		couponAdapter = new CouponMessageAdapter(basebean.getResult().getList(),this);
		lv_coupon.setAdapter(couponAdapter);
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
//		ApiHttpCilent.getInstance(baseContext).CouponMessage(this, new CouponMessageCallBack());
	}
	
	public class CouponHandler extends WeakHandler<CouponMessageActivity>{
		@SuppressLint("HandlerLeak")
		public CouponHandler(CouponMessageActivity reference) {
			super(reference);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				
				break;

			default:
				break;
			}
		}
	}
	// 异常等待框
	private void Dimess() {
		CouponMessageActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(ApiHttpCilent.loading !=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	public void bindData() {
			if(basebean != null && basebean.getResult()!= null && basebean.getResult().getList()!=null){
				list = basebean.getResult().getList();
				couponAdapter.notifyDataSetChanged();
			}
	}

	public class CouponMessageCallBack extends BaseJsonHttpResponseHandler<CouponBaseBean>{

		

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, CouponBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				CouponBaseBean arg3) {
		}

		@Override
		protected CouponBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			 Dimess();
			Gson gson = new Gson();
			basebean = gson.fromJson(response, CouponBaseBean.class);
			if (basebean != null) {
				Message msg = new Message();
				if (("1").equals(basebean.getStatus())) {
					msg.what = ConstantsUtil.HTTP_SUCCESS;
					msg.obj = basebean.getResult();
				} else if (("0").equals(basebean.getStatus())) {
					msg.what = ConstantsUtil.HTTP_FAILE;
					msg.obj = basebean.getError();
				}
				mhandler.sendMessage(msg);
			}
			return basebean;
		}

	}
	
	
	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "优惠券消息";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

}
