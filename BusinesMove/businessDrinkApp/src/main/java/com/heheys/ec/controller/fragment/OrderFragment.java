package com.heheys.ec.controller.fragment;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.controller.activity.OrderInfoActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.OrderListAdapter;
import com.heheys.ec.model.adapter.OrderListAdapter.RefreshCallBack;
import com.heheys.ec.model.dataBean.OrderBaseBean;
import com.heheys.ec.model.dataBean.OrderBaseBean.OrderListBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 订单页面
 */
public class OrderFragment extends BaseFragment implements OnItemClickListener,
		OnClickListener, OnRefreshListener, OnMoreListener {

	private View view;
	private RefreshListView lv_order;
	private static OrderFragment orderFragment;
	private Activity activity;
	private LinearLayout show_layout;
	private LinearLayout linear_nodata;
	private boolean isClick = true;// 是否点击选择订单状态
	private boolean isClickTitle = true;// 是否点击选择title
	private Button all_statue;
	private MyHandler handler = new MyHandler(this);
	private static Activity context;
	private String status = "0";// 0 全部订单 1 待付款 2 进行中 3备货中 4 待收货
	private OrderListAdapter<Object> madapter;
	private List<OrderListBean> listbean;
	private TextView tv_wait;
	private TextView tv_all;
	private TextView tv_ing;
	private TextView tv_payed;
	private TextView tv_sending;
	private ImageView iv_sending;
	private ImageView iv_ing;
	private ImageView iv_payed;
	private ImageView iv_wait;
	private ImageView iv_all;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;

	private OrderBaseBean orderbean;
	private LinearLayout linear_not_login;
	private Button tv_not_login;
	private Animation animationShow,animationHide;

	public static OrderFragment getInstance() {
		if (orderFragment == null) {
			orderFragment = new OrderFragment();
		}
		return orderFragment;
	}

	/*
	 * 适配数据
	 */
	private void initDate() {
		listbean = new ArrayList<OrderListBean>();
		madapter = new OrderListAdapter<Object>(listbean, activity,
				new RefreshCallBack() {
					@Override
					public void setback(boolean isRresh, int statu) {
						// TODO Auto-generated method stub
						if (isRresh) {
							HttpOrderListStatus(statu + "");
						}
					}
				});
		lv_order.setAdapter(madapter);
		lv_order.setOnItemClickListener(this);
	}

	/*
	 * 初始化节目组件
	 */
	private void initView() {
		animationShow=AnimationUtils.loadAnimation(baseActivity, R.anim.pop_enter);
		animationHide=AnimationUtils.loadAnimation(baseActivity, R.anim.pop_out);
		context = getActivity();
		lv_order = (RefreshListView) view.findViewById(R.id.lv_order);
		show_layout = (LinearLayout) view.findViewById(R.id.show_layout);
		linear_nodata = (LinearLayout) view.findViewById(R.id.linear_nodata);
		linear_not_login = (LinearLayout) view.findViewById(R.id.base_orderfragment_not_login_parent);
		tv_not_login = (Button) view
				.findViewById(R.id.base_activity_no_order);
		tv_all = (TextView) view.findViewById(R.id.tv_all);
		tv_payed = (TextView) view.findViewById(R.id.tv_payed);
		tv_ing = (TextView) view.findViewById(R.id.tv_ing);
		tv_sending = (TextView) view.findViewById(R.id.tv_sending);
		tv_wait = (TextView) view.findViewById(R.id.tv_wait);

		iv_all = (ImageView) view.findViewById(R.id.iv_all);
		iv_payed = (ImageView) view.findViewById(R.id.iv_payed);
		iv_ing = (ImageView) view.findViewById(R.id.iv_ing);
		iv_sending = (ImageView) view.findViewById(R.id.iv_sending);
		iv_wait = (ImageView) view.findViewById(R.id.iv_wait);

		iv_sending.setOnClickListener(this);
		iv_ing.setOnClickListener(this);
		iv_payed.setOnClickListener(this);
		iv_wait.setOnClickListener(this);
		iv_all.setOnClickListener(this);

		tv_sending.setOnClickListener(this);
		tv_ing.setOnClickListener(this);
		tv_payed.setOnClickListener(this);
		tv_wait.setOnClickListener(this);
		tv_all.setOnClickListener(this);
		tv_not_login.setOnClickListener(this);
		LayoutTransition transition = new LayoutTransition();
		show_layout.setLayoutTransition(transition);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.iv_all:
			map.put("LookOrderAll","");
			MobclickAgent.onEvent(baseActivity, "0073", map); 
			tv_all.setPressed(true);
			Visibility();
			status = "0";
			HttpOrderListStatus(status);
			break;
		case R.id.tv_all:
			map.put("LookOrderAll","");
			MobclickAgent.onEvent(baseActivity, "0073", map); 
			Visibility();
			status = "0";
			HttpOrderListStatus(status);
			break;
		// 0 全部订单 1 待付款 2 进行中 3备货中 4 待收货
		case R.id.iv_payed:
			map.put("payed","");
			MobclickAgent.onEvent(baseActivity, "0074", map); 
			tv_payed.setPressed(true);
			Visibility();
			status = "1";
			HttpOrderListStatus(status);
			break;
		case R.id.tv_payed:
			map.put("payed","");
			MobclickAgent.onEvent(baseActivity, "0074", map); 
			Visibility();
			status = "1";
			HttpOrderListStatus(status);
			break;
		case R.id.iv_wait:
			map.put("wait","");
			MobclickAgent.onEvent(baseActivity, "0078", map); 
			tv_wait.setPressed(true);
			Visibility();
			status = "4";
			HttpOrderListStatus(status);
			break;
		case R.id.tv_wait:
			map.put("wait","");
			MobclickAgent.onEvent(baseActivity, "0078", map); 
			Visibility();
			status = "4";
			HttpOrderListStatus(status);
			break;
		case R.id.iv_ing:
			map.put("paying","");
			MobclickAgent.onEvent(baseActivity, "0075", map); 
			tv_ing.setPressed(true);
			Visibility();
			status = "2";
			HttpOrderListStatus(status);
			break;
		case R.id.tv_ing:
			map.put("paying","");
			MobclickAgent.onEvent(baseActivity, "0075", map); 
			Visibility();
			status = "2";
			HttpOrderListStatus(status);
			break;
		case R.id.iv_sending:
			map.put("sending","");
			MobclickAgent.onEvent(baseActivity, "0077", map); 
			tv_sending.setPressed(true);
			Visibility();
			status = "3";
			HttpOrderListStatus(status);
			break;
		case R.id.tv_sending:
			map.put("sending","");
			MobclickAgent.onEvent(baseActivity, "0077", map); 
			Visibility();
			status = "3";
			HttpOrderListStatus(status);
			break;
		case R.id.base_activity_no_order_buy:
			StartActivityUtil.startActivity(activity,
					LoginActivity.class);
			break;
		default:
			break;
		}
	}

	private void HttpOrderListStatus(String status) {
		if(ToastNoNetWork.ToastError(context))
			return;
		ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetOrderList(getActivity(),
				startIndex, endIndex, status, new MyCallBack());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		status = "0";
		HttpOrderListStatus(status);
		MobclickAgent.onPageStart("OrderFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		System.out.println("oncreate");
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		System.out.println("onStart");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
//		System.out.println("onDetach");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		System.out.println("onDestroy");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int id,
			long position) {
		// TODO Auto-generated method stub
		//统计查看订单
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("lookorder","");
		MobclickAgent.onEvent(baseActivity, "0069", map); 
		if(8==listbean.get((int) position).getStatus()){
			map.put("orderfinish","");
			MobclickAgent.onEvent(baseActivity, "0079", map); 
		}else if(21==listbean.get((int) position).getStatus()){
			map.put("ordercancle","");
			MobclickAgent.onEvent(baseActivity, "0080", map); 
		}
		int orderId = listbean.get((int) position).getId();// 传递子订单ID
		Intent intent = new Intent(activity, OrderInfoActivity.class);
		intent.putExtra("orderId", orderId);
		StartActivityUtil.startActivity(activity, intent);
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<OrderBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, OrderBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				OrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected OrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			orderbean = gson.fromJson(response, OrderBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(orderbean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = orderbean.getResult().getList();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				// message.obj = orderbean.getError().getInfo();
			}
			handler.sendMessage(message);

			return orderbean;
		}
	}

	private void Dimess() {
		context.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	public class MyHandler extends WeakHandler<OrderFragment> {

		public MyHandler(OrderFragment reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindDate();
				
				break;
			case ConstantsUtil.HTTP_FAILE:
				if (orderbean.getError().getCode().equals("100")) {// 未登录
					linear_not_login.setVisibility(View.VISIBLE);
					linear_nodata.setVisibility(View.GONE);
					lv_order.setVisibility(View.GONE);
					tv_not_login.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							StartActivityUtil.startActivity(activity,
									LoginActivity.class);
							
						}
					});
				} else {
//					String back = (String) msg.obj;
//					ToastUtil.showToast(context, back);
					linear_not_login.setVisibility(View.GONE);
					lv_order.setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = getActivity();
		view = inflater.inflate(R.layout.fragment_order, container, true);
		initView();
		initDate();
		return view;
	}

	private void hideRefreshView() {
		if (isRefresh) {
			lv_order.onRefreshComplete();
			isRefresh = false;
		}
	}

	private void hideLoadMoreView() {
		if (isLoadMore) {
			lv_order.onLoadComplete();
			madapter.setNewData(listbean);
			isLoadMore = false;
		}
	}

	// 绑定数据
	private void bindDate() {
		if (startIndex == 1) {
			/**
			 * 初始化数据
			 */
			hideRefreshView();
			if (orderbean.getResult() != null
					&& orderbean.getResult().getList() != null
					&& orderbean.getResult().getList().size() > 0) {
				listbean = orderbean.getResult().getList();// 数据集合
				madapter.setNewData(listbean);
				linear_nodata.setVisibility(View.GONE);
				linear_not_login.setVisibility(View.GONE);
				lv_order.setVisibility(View.VISIBLE);
			} else {
				/**
				 * 处理数据为空
				 */
//				ToastUtil.showToast(context, "数据为空");
				linear_nodata.setVisibility(View.VISIBLE);
				lv_order.setVisibility(View.GONE);
				linear_not_login.setVisibility(View.GONE);
			}
		} else {
			/**
			 * 分页数据
			 */
			if (orderbean.getResult() != null
					&& orderbean.getResult().getList() != null
					&& orderbean.getResult().getList().size() > 0) {
				/**
				 * 分页数据存在
				 */
				// ToastUtil.showToast(context, "分页起始页码" + startIndex);
				List<OrderListBean> pageData = orderbean.getResult().getList();
				if (orderbean != null) {
					if (isLoadMore) {
						listbean.addAll(pageData);
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
				// ToastUtil.showToast(context, "数据加载完毕==页码==" + startIndex);
			}
		}
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewListener() {
		// TODO Auto-generated method stub
		tvTitleName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("lookorderstatus","");
				MobclickAgent.onEvent(baseActivity, "0068", map); 
				if (isClick) {
					Invisibility();
				} else {
					Visibility();
				}
			}

		});
		 linear_titlename.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View arg0) {
		 // TODO Auto-generated method stub
		 if (isClick) {
			 Invisibility();
		 } else {
			 Visibility();
		 }
		 }
		 });
		lv_order.setonLoadListener(this);
		lv_order.setonRefreshListener(this);
	}

	private void Invisibility() {
		isClick = false;
		tv_all.setPressed(false);
		tv_ing.setPressed(false);
		tv_payed.setPressed(false);
		tv_sending.setPressed(false);
		tv_wait.setPressed(false);
		show_layout.setVisibility(View.VISIBLE);
		show_layout.startAnimation(animationShow);
		show_layout.setFocusable(true);
		show_layout.setClickable(true);
		ivTitleIcon.setBackgroundResource(R.drawable.arrow_up_order);

	}

	private void Visibility() {
		isClick = true;
		show_layout.setVisibility(View.GONE);
		show_layout.startAnimation(animationHide);
		ivTitleIcon.setBackgroundResource(R.drawable.arrow_down);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected String setTitleName() {
		return "全部";
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
	protected boolean isShowLeftIcon() {
		return false;
	}

	@Override
	protected boolean hasTitleIcon() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean hasDownIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "list+onLoadMore", Toast.LENGTH_LONG).show();
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		// getNetData();
		HttpOrderListStatus(status);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		isRefresh = true;
		initReuquestParams();
		// getNetData();
		HttpOrderListStatus(status);
	}

	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("OrderFragment");
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("NewAddFragemnt");
	}
}