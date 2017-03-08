package com.heheys.ec.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.MoreSuitListAdapter;
import com.heheys.ec.model.adapter.MoreSuitListAdapter.AddShopingCallback;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.MoreSuitBean;
import com.heheys.ec.model.dataBean.MoreSuitBean.MoreSuitListBean;
import com.heheys.ec.model.dataBean.MoreSuitBean.SuitList;
import com.heheys.ec.model.dataBean.NewProductDetailInfor.NewProductInfor.Suitinfo;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * @author wangkui
 * 
 *         更多套装
 */
public class MoreSuitActivity extends BaseActivity {

	private ListView lv_moresuit;
	private ArrayList<SuitList> dataSuitList;
	private MoreSuitListAdapter suitAdapter;
	private Suitinfo suitinfo;
	private String aid;
	private MoreSuitBean suitlistbean;// 返回套装json
	private SuitListHandler suitHandle;
	private MoreSuitListBean suitlist;
	private LinearLayout linear_right;
	private ImageView iv_shopping;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.more_suit);
		initView();
		initDate();
	}

	private void initDate() {
		suitHandle = new SuitListHandler(this);
		Intent intent = getIntent();
		if (intent != null) {
			aid = intent.getStringExtra("id");
			if (!StringUtil.isEmpty(aid))
				getData();
		}
		dataSuitList = new ArrayList<SuitList>();
		suitAdapter = new MoreSuitListAdapter(dataSuitList, this,
				new ShopSuitBack(),suitHandle);
		lv_moresuit.setAdapter(suitAdapter);
	}


	class ShopSuitBack implements AddShopingCallback {

		@Override
		public void getId(String id) {
			/*
			 * 套装加入购物车
			 * */
			AddSuitToShopping(id);
		}
	}

	void AddSuitToShopping(String id) {
		ApiHttpCilent.getInstance(getApplicationContext()).AddSuitToShopping(this, id, "1",
				new AddSuitShoppingCartRequestCallBack());
	}

	public class AddSuitShoppingCartRequestCallBack extends
			BaseJsonHttpResponseHandler<AddShoppingCartBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AddShoppingCartBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			suitHandle.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AddShoppingCartBean arg3) {
		}

		@Override
		protected AddShoppingCartBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dimess();
			Gson gson = new Gson();
			AddShoppingCartBean addShoppingCartData = gson.fromJson(response,
					AddShoppingCartBean.class);
			Message message = Message.obtain();
			if ("1".equals(addShoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
//				 message.obj = addShoppingCartData.G;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = addShoppingCartData.getError();
			}
			suitHandle.sendMessage(message);
			return addShoppingCartData;
		}
	}

	// 获取当前商品套装信息
	void getData() {
		ApiHttpCilent.getInstance(getApplicationContext()).getSuitList(this, aid,
				new SuitListRequestCallBack());
	}

	public static class SuitListHandler extends WeakHandler<MoreSuitActivity> {

		public SuitListHandler(MoreSuitActivity reference) {
			super(reference);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().suitlist = (MoreSuitListBean) msg.obj;
				getReference().bindDate();
				break;
			case ConstantsUtil.HTTP_NEED_LOGIN:
				//重新更新listview高度
				getReference().suitAdapter.setPosition(msg.arg1);
				Utils.setListViewHeightBasedOnChildren(getReference().lv_moresuit);
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				//添加购物车成功
				ToastUtil.showToast(getReference(), "添加购物车成功");
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				// 获取到购物车数据成功
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				ErrorBean messageString = (ErrorBean) msg.obj;
				if (messageString != null) {
					ToastUtil
							.showToast(getReference(), messageString.getInfo());
				}
				break;
			}
		}

	}

	/*
	 * 绑定数据
	 * 
	 * *
	 */
	private void bindDate() {
		dataSuitList = suitlist.getSuit();
		suitAdapter.setNewData(dataSuitList);
	}

	public class SuitListRequestCallBack extends
			BaseJsonHttpResponseHandler<MoreSuitBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MoreSuitBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			suitHandle.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MoreSuitBean arg3) {
		}

		@Override
		protected MoreSuitBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			suitlistbean = gson.fromJson(response, MoreSuitBean.class);
			Message message = Message.obtain();
			if ("1".equals(suitlistbean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = suitlistbean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = suitlistbean.getError();
			}
			suitHandle.sendMessage(message);
			return suitlistbean;
		}
	}

	private void Dimess() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	private void initView() {
		lv_moresuit = (ListView) findViewById(R.id.lv_moresuit);
		linear_right = (LinearLayout) findViewById(R.id.base_activity_title_right_parent);
		linear_right.setOnClickListener(this);
		iv_shopping = (ImageView) findViewById(R.id.base_activity_title_right_righticon);
		iv_shopping.setOnClickListener(this);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub

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
		return "优惠套装";
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
		return R.drawable.shopping_cart_white;
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.base_activity_title_right_parent:
			StartShoppingPage();
			break;
		case R.id.base_activity_title_right_righticon:
			/**
			 * 进入购物车事件
			 */
			StartShoppingPage();
			break;
		default:
			break;
		}
	}
	/*
	 * 跳转到购物车
	 */

	void StartShoppingPage() {
		Intent intent = new Intent();
		intent.setClass(this, ShoppingCartActivity.class);
		StartActivityUtil.startActivity(baseActivity, intent);
	}

}