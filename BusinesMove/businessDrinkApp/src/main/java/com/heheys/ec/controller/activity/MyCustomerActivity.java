package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.CallbackRefresh;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.MyCustomerAdapter;
import com.heheys.ec.model.adapter.MyCustomerAdapter.ClickCallBack;
import com.heheys.ec.model.dataBean.CustomerBaseBean;
import com.heheys.ec.model.dataBean.CustomerBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Describe:下游用户页面
 * 
 * Date:2015-9-28
 * 
 * Author:wk
 */
public class MyCustomerActivity extends BaseActivity implements
		OnRefreshListener, OnMoreListener, OnItemClickListener, CallbackRefresh{
	private RefreshListView mListView;
	private MyCustomerAdapter madapter;
	private Context mContext;
	private MyHandler handler = new MyHandler(this);
	private List<CustomerBean> data;
	private Button bt_add;
	private Button bt_delete;
	private LinearLayout linear_delete;
	private boolean isclick = false;
	private int flag;// 区分回调标志物 1是全部列表请求操作 2 是删除联系人操作
	// 所有待删除上下游账户id值
	private List<String> listid = new ArrayList<String>();
	private StringBuffer idcommit = new StringBuffer();
	private CheckBox iv_check;
	private int sizeTotal;
	private ReceiverLogin receiver;
	private IntentFilter intentFilter;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	private CustomerBaseBean customBasebean;
	private int value;
	private LinearLayout base_activity_no_client;
	private boolean isAdd;
	private View footerView;

	@Override
	protected void onCreate() {
		setBaseContentView(R.layout.my_customer);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	class ReceiverLogin extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ConstantsUtil.LOGIIN_ACTION)) {
				if (!NetWorkState.isNetWorkConnection(mContext)) {
					ToastUtil.showToast(mContext, "网络连接失败");
					return;
				}
				flag = 1;// 获取列表是1
				ApiHttpCilent.getInstance(getApplicationContext()).GetCustomerList(mContext,
						1, 10, new MyCallBack());
			}
		}
	}

	@Override
	protected void loadChildView() {
		footerView = baseLayoutInflater.inflate(R.layout.footview_white, null);
		mContext = MyCustomerActivity.this;
		mListView = (RefreshListView) findViewById(R.id.mycustomer_lv);
		base_activity_no_client = (LinearLayout) findViewById(R.id.base_activity_no_client);
		bt_add = (Button) findViewById(R.id.bt_add);
		bt_delete = (Button) findViewById(R.id.bt_delete);
		iv_check = (CheckBox) findViewById(R.id.iv_check);
		iv_check.setChecked(true);
		linear_delete = (LinearLayout) findViewById(R.id.linaer_delete);
		MyApplication.getInstance().setLister(this);
		intentFilter = new IntentFilter();
		receiver = new ReceiverLogin();
		intentFilter.addAction(ConstantsUtil.LOGIIN_ACTION);
		this.registerReceiver(receiver, intentFilter);
		tvRight.setVisibility(View.INVISIBLE);
		data = new ArrayList<CustomerBean>();
		mListView.setOnTouchListener(new onTouch());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}

	class CheckCallBack implements ClickCallBack {

		@SuppressLint("NewApi")
		@Override
		public void setId(String id, boolean cancle, List<CustomerBean> data) {
			// TODO Auto-generated method stub
			if (cancle) {
				if (!listid.contains(id)) {
					listid.add(id);
				}
			} else {
				if (listid.contains(id)) {
					listid.remove(id);
				}
			}
			int size = listid.size();
			// 判断当前选择的数据长度是否和总数据长度一样
			if (size == sizeTotal) {
				iv_check.setChecked(true);
				bt_delete.setBackground(getResources().getDrawable(
						R.drawable.bt_bg_yellow));
				bt_delete.setEnabled(true);
			} else {
				iv_check.setChecked(false);
				if (size == 0) {
					bt_delete.setBackground(getResources().getDrawable(
							R.drawable.shape_button_gray));
					bt_delete.setEnabled(false);
				} else {
					bt_delete.setBackground(getResources().getDrawable(
							R.drawable.bt_bg_yellow));
					bt_delete.setEnabled(true);
				}
			}
			madapter.setNewData(data);
		}
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<CustomerBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, CustomerBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				CustomerBaseBean arg3) {
			Dimess();
		}

		@Override
		protected CustomerBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			customBasebean = gson.fromJson(response, CustomerBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(customBasebean.getStatus()) && flag == 1) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else if ("1".equals(customBasebean.getStatus()) && flag == 2) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else if ("0".equals(customBasebean.getStatus())) {
				if (customBasebean.getError().getCode()
						.equals(ConstantsUtil.ERROE_LOGIN_CODE)) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;// 错误
				} else {
					message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 错误
					message.obj = customBasebean.getError().getInfo();
				}
			}
			handler.sendMessage(message);
			return customBasebean;
		}

		private void Dimess() {
			MyCustomerActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
						ApiHttpCilent.loading.dismiss();
				}
			});
		}
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (!NetWorkState.isNetWorkConnection(mContext)) {
			ToastUtil.showToast(mContext, "网络连接失败");
			return;
		}
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getNetData();

	}

	private void setViable() {
		isclick = false;
		if (madapter != null){
			madapter.isEdit(isclick);
		}
		tvRight.setText("管理");
		linear_delete.setVisibility(View.GONE);
		bt_add.setVisibility(View.VISIBLE);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (!NetWorkState.isNetWorkConnection(mContext)) {
			ToastUtil.showToast(mContext, "网络连接失败");
			return;
		}
		isRefresh = true;
		initReuquestParams();
		getNetData();
	}

	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	private void hideRefreshView() {
		if (isRefresh) {
			mListView.onRefreshComplete();
			isRefresh = false;
		}
	}

	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
			madapter.isEdit(isclick);
		 
			isLoadMore = false;
		}
	}

	private void bindViewData() {
		if (startIndex == 1) {
			/**
			 * 初始化数据
			 */
			if (customBasebean.getResult() != null
					&& customBasebean.getResult().getList() != null
					&& customBasebean.getResult().getList().size() > 0) {
				data = customBasebean.getResult().getList();
				tvRight.setVisibility(View.VISIBLE);
				for (CustomerBean bean : data) {
					bean.setCheck(false);// 初始化所有数据为不选中状态
				}
				iv_check.setChecked(false);// 初始化选中状态
				sizeTotal = data.size();// 服务器所有数据集合长度
				madapter = new MyCustomerAdapter(data, this,
						new CheckCallBack());
				if (value == 1 && !isAdd) {// 编辑后返回标志
					madapter.isEdit(isclick);
				}
				if(isRefresh || isLoadMore){
					madapter.isEdit(isclick);
				}
				mListView.addFooterView(footerView);
				mListView.setAdapter(madapter);
				base_activity_no_client.setVisibility(View.GONE);
				hideRefreshView();
			} else {
				/**
				 * 处理数据为空
				 */
				tvRight.setVisibility(View.INVISIBLE);
				bt_add.setVisibility(View.VISIBLE);
				linear_delete.setVisibility(View.GONE);
				if (madapter != null) {
					if (value == 1) {// 编辑后返回标志
						madapter.isEdit(isclick);
					}
					madapter.setNewData(customBasebean.getResult().getList());
				}

				base_activity_no_client.setVisibility(View.VISIBLE);
			}
		} else {
			/**
			 * 分页数据
			 */
			if (customBasebean.getResult() != null
					&& customBasebean.getResult().getList() != null
					&& customBasebean.getResult().getList().size() > 0) {
				/**
				 * 分页数据存在
				 */
				List<CustomerBean> pageData = customBasebean.getResult()
						.getList();
				if (data != null) {
					if (isLoadMore) {
						data.addAll(pageData);
						hideLoadMoreView();
					}
				}
			} else {
				/**
				 * 分页数据不存在
				 */
				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
			}
		}
	}

	public class MyHandler extends WeakHandler<MyCustomerActivity> {

		public MyHandler(MyCustomerActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				ToastUtil.showToast(mContext,
						getResources()
								.getString(R.string.custom_delete_success));
				flag = 1;// 获取列表是1
				value = 1;
				ApiHttpCilent.getInstance(getApplicationContext()).GetCustomerList(mContext,
						1, 10, new MyCallBack());
				break;
			case ConstantsUtil.HTTP_NEED_LOGIN:
				StartActivityUtil.startActivity((Activity) mContext,
						LoginActivity.class);
				break;
			case ConstantsUtil.HTTP_FAILE:
				ToastUtil.showToast(mContext, (String) msg.obj);
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN:
				ToastUtil.showToast(mContext, (String) msg.obj);
				break;
			default:
				break;
			}
		}
		/*
		 * 删除指定的id设备 *
		 */
	}

	private void CreateList() {
		idcommit.delete(0, idcommit.length());
		for (int i = 0; i < listid.size(); i++) {
			if (i != listid.size() - 1) {
				idcommit.append(listid.get(i)).append(",");
			} else {
				idcommit.append(listid.get(i));
			}
		}
	}

	@Override
	protected void getNetData() {
		flag = 1;// 获取列表是1
		ApiHttpCilent.getInstance(getApplicationContext()).GetCustomerList(mContext,
				startIndex, endIndex, new MyCallBack());

	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		tvRight.setOnClickListener(this);
		iv_check.setOnClickListener(this);
		bt_add.setOnClickListener(this);
		bt_delete.setOnClickListener(this);
		mListView.setonLoadListener(this);
		mListView.setonRefreshListener(this);
		mListView.setOnItemClickListener(this);
	}

	@Override
	protected String setTitleName() {
		return "终端管理";
	}

	@Override
	protected String setRightText() {
		return "管理";
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

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.base_activity_title_right_righttv:
			/**
			 * 编辑用户
			 */
			linear_delete.setVisibility(View.VISIBLE);
			bt_add.setVisibility(View.GONE);
			if (!isclick) {
				map.put("editcustome","");
				MobclickAgent.onEvent(mContext, "0093", map);
				isclick = true;
				madapter.isEdit(isclick);
				tvRight.setText("完成");
				linear_delete.setVisibility(View.VISIBLE);
				bt_add.setVisibility(View.GONE);
			} else {
				map.put("okcustome","");
				MobclickAgent.onEvent(mContext, "0094", map);
				setViable();
			}
			madapter.notifyDataSetChanged();
			break;
		case R.id.bt_delete:
			map.put("deletecustome","");
			MobclickAgent.onEvent(mContext, "0095", map);
			if (listid.size() == 0) {
				ToastUtil.showToast(mContext, "您没有选择需要删除的终端");
				return;
			}
			ShowDialogDelete("是否删除终端?", "温馨提示");
			break;
		case R.id.bt_add:
			map.put("addcustome","");
			MobclickAgent.onEvent(mContext, "0092", map); 
			Intent intent = new Intent();
			intent.setClass( mContext, AddMyCustomerActivity.class);
			StartActivityUtil.startActivity((Activity) mContext, intent);
			isAdd = true;
			break;
		case R.id.iv_check:
			boolean isChecked = iv_check.isChecked();
			listid.clear();
			for (CustomerBean bean : data) {
				if (isChecked) {
					bean.setCheck(true);// 初始化所有数据为不选中状态
					listid.add(bean.getId());
					bt_delete.setBackground(getResources().getDrawable(
							R.drawable.bt_bg_yellow));
					bt_delete.setEnabled(true);
				} else {
					bean.setCheck(false);
					listid.clear();
					bt_delete.setBackground(getResources().getDrawable(
							R.drawable.shape_button_gray));
					bt_delete.setEnabled(false);
				}
			}
			madapter.notifyDataSetChanged();
			break;
		case R.id.base_activity_no_client_add:
			Intent i = new Intent();
			i.setClass( mContext, AddMyCustomerActivity.class);
			StartActivityUtil.startActivity((Activity) mContext, i);
			break;
		case R.id.base_activity_title_backicon:
			map.put("customback","");
			MobclickAgent.onEvent(baseActivity, "0096", map); 
			// 返回键处理
			onBackPressed();
			break;
		default:
			break;
		}
	}

	public void ShowDialogDelete(String notice, String title) {
		CommonDialog.makeText(mContext, title, notice, new OnDialogListener() {

			@Override
			public void onResult(int result, CommonDialog commonDialog,
					String tel) {
				// TODO Auto-generated method stub
				if (OnDialogListener.LEFT == result) {
					CreateList();
					flag = 2;// 删除联系人是2
					ApiHttpCilent.getInstance(getApplicationContext()).DeleteCustomer(
							mContext, idcommit, new MyCallBack());
					CommonDialog.Dissmess();
				} else {
					CommonDialog.Dissmess();
				}
			}
		}).showDialog();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (isclick) {
			Intent intent = new Intent(this, AddMyCustomerActivity.class);
			intent.putExtra("CustomerBean", data.get((int) id));
			StartActivityUtil.startActivity(this, intent);
			isAdd = false;
		}
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub
		flag = 1;// 获取列表是1
		value = 1;
		ApiHttpCilent.getInstance(getApplicationContext()).GetCustomerList(mContext, 1, 10,
				new MyCallBack());
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	
	class onTouch implements OnTouchListener{

		private float downX;
		private float downY;
		private float moveX;
		private float moveY;
		private float upX;
		private float upY;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				moveX = event.getX();
				moveY = event.getY();
//				if(downX-moveX>300){
					//位移大于300像素 是删除动作
//				}
				
				break;
			case MotionEvent.ACTION_UP:
				upX = event.getX();
				upY = event.getY();
				break;
			default:
				break;
			}
			mListView = (RefreshListView) v;
//			System.out.println(mListView.getSelectedItemPosition()+"id=="+mListView.getId()+" -----"+
//					mListView.getSelectedItemId()+" --"+mListView.getSelectedItem()+mListView.getTag());
			return false;
		}
	}
}
