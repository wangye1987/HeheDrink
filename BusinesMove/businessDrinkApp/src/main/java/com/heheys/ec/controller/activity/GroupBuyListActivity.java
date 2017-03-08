//package com.heheys.ec.controller.activity;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.http.Header;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Message;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.inputmethod.EditorInfo;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.EditText;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.PopupWindow.OnDismissListener;
//import android.widget.RelativeLayout;
//import android.widget.RelativeLayout.LayoutParams;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.heheys.ec.R;
//import com.heheys.ec.application.MyApplication;
//import com.heheys.ec.base.BaseActivity;
//import com.heheys.ec.lib.activityManager.StartActivityUtil;
//import com.heheys.ec.lib.utils.FileManager;
//import com.heheys.ec.lib.utils.StringUtil;
//import com.heheys.ec.lib.utils.WeakHandler;
//import com.heheys.ec.lib.view.ViewUtil;
//import com.heheys.ec.model.adapter.GroupBuyBrandAdapter;
//import com.heheys.ec.model.adapter.GroupBuyBrandAdapter.CheckBrandListener;
//import com.heheys.ec.model.adapter.GroupBuyGridAdapter;
//import com.heheys.ec.model.adapter.GroupBuyListAdapter;
//import com.heheys.ec.model.dataBean.BrandBaseBean;
//import com.heheys.ec.model.dataBean.CityListBean.CityDataList.CityData;
//import com.heheys.ec.model.dataBean.GroupBuyProductlistBean;
//import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.BrandList;
//import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.GroupBuyProductInfor;
//import com.heheys.ec.model.dataBean.PlaceNameBaseBean;
//import com.heheys.ec.model.dataBean.WholeBaseBean;
//import com.heheys.ec.netWorkHelper.ApiHttpCilent;
//import com.heheys.ec.utils.ActivityManagerUtil;
//import com.heheys.ec.utils.ConstantsUtil;
//import com.heheys.ec.utils.ToastUtil;
//import com.heheys.ec.view.RefreshOrLoadMoreGridViewParent;
//import com.heheys.ec.view.RefreshOrLoadMoreGridViewParent.GridPullOrRefreshListener;
//import com.heheys.ec.view.RefreshOrLoadMoreListViewParent;
//import com.heheys.ec.view.RefreshOrLoadMoreListViewParent.ListPullOrRefreshListener;
//import com.loopj.android.http.BaseJsonHttpResponseHandler;
//import com.umeng.analytics.MobclickAgent;
//
///**
// * @author 作者 E-mail: wk
// * @version 创建时间：2015-9-28 上午10:47:38 类说明 拼单区
// * @param 
// * 
// * @param <已经废弃版本>
// */
//public class GroupBuyListActivity extends BaseActivity implements
//		GridPullOrRefreshListener, ListPullOrRefreshListener,
//		CheckBrandListener {
//	private static final String TAG = GroupBuyListActivity.class.getName();
//	protected ListView listView = null;
//	protected GridView gridView = null;
//	private PlaceNameBaseBean placeNameList;
//	private BrandBaseBean brandNameList;
//	private GroupBuyListAdapter listAdapter;
//	private GroupBuyGridAdapter gridAdapter;
//	// 商品列表页当前显示模式，是否宫格列表显示
//	protected boolean isListGridShow = false;
//	protected boolean pauseOnScroll = false;
//	protected boolean pauseOnFling = true;
//	private RefreshOrLoadMoreGridViewParent gridViewParent;
//	private RefreshOrLoadMoreListViewParent listViewParent;
//	private PopupWindow mPopupWindow;
//	private LinearLayout layout;
//	private RelativeLayout rlPriceParent, rlBrandParent;
//	private TextView tvSale, tvSort, tvPrice;
//	boolean price_b = true;
//	boolean brand_b = true;
//	boolean time_b = true;
//	private TextView line;;
//	// 返回到列表顶部控件
//	private ImageView ivBackToTop, ivTabPriceArrow;
//	private static MyMessageHandler messageHandler;
//	private GroupBuyProductlistBean groupBuyData;
//	private WholeBaseBean groupBuyDataList;
//	private List<GroupBuyProductInfor> groupBuyProductList;
//	private DataType checkType;
//	// 时间排序类型
//	private int timeSortType;
//	// 价格排序类型
//	private int priceSortType;
//	private int paramSortType;
//
//	// 搜索条件
//	private String searchContent;
//	private String dataTypeParam;
//	// 选择酒类品牌
//	private int windBrand;
//	// 酒类品牌数据适配器
//	private GroupBuyBrandAdapter brandAdapter;
//	// 酒类品牌数据
//	private List<BrandList> brandLists;
//	private GridView brandGvGridView;
//	// 列表数据开始和结束位置
//	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
//			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
//	// 标记是否是下拉刷新状态
//	private boolean isRefresh;
//	// 标记是否是分页状态
//	private boolean isLoadMore;
//	private ImageView ivShoppingCart;
//	private TimeType timeType;
//	private PriceType priceType;
//	private ImageView ivTimeArrow, ivBrandArrow;
//	private String searchContentString;
//	private boolean isPush;
//	public String checkBrandId;
//	private RelativeLayout relative_brand;
//	private EditText etInput;
//	String cityIdString = null;
//	// 是否在滑动
//	private boolean mIsGridViewidle = true;
//	private boolean mIsListViewidle = true;
//	private ImageView ivType;
//	private LinearLayout type_change;
//	// 判断是从拼单还是批发跳转过来
//	private int type_run;
//
//	public enum DataType {
//		PRICE, SALENUM, TIME
//	}
//
//	public enum ViewType {
//		LIST, GRID
//	}
//
//	public enum TimeType {
//		TIME_UP, TIME_DOWN
//	}
//
//	public enum PriceType {
//		PRICE_UP, PRICE_DOWN
//	}
//
//	@Override
//	protected void onCreate() {
//		// TODO Auto-generated method stub
//		setBaseContentView(R.layout.group_buy_list);
//	}
//
//	@Override
//	protected boolean hasTitle() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	protected void loadChildView() {
//		ivBrandArrow = (ImageView) findViewById(R.id.group_buy_list_brand_arrow);
//		ivTimeArrow = (ImageView) findViewById(R.id.group_buy_list_arrow_time);
//		ivShoppingCart = (ImageView) findViewById(R.id.group_buy_list_shopping_cart);
//		gridViewParent = (RefreshOrLoadMoreGridViewParent) findViewById(R.id.group_buy_list_gv);
//		listViewParent = (RefreshOrLoadMoreListViewParent) findViewById(R.id.group_buy_list_lv);
//		listView = listViewParent.getListView();
//		gridView = gridViewParent.getGridView();
//		rlPriceParent = (RelativeLayout) findViewById(R.id.group_buy_list_price);
//		rlBrandParent = (RelativeLayout) findViewById(R.id.group_buy_list_brand);
//		tvSale = (TextView) findViewById(R.id.group_buy_list_sale_tv);
//		tvSort = (TextView) findViewById(R.id.group_buy_list_sort);
//		tvPrice = (TextView) findViewById(R.id.group_buy_list_price_tv);
//		line = (TextView) findViewById(R.id.group_buy_list_line);
//		ivBackToTop = (ImageView) findViewById(R.id.group_buy_list_back_totop);
//		ivTabPriceArrow = (ImageView) findViewById(R.id.group_buy_list_arrow);
//		etInput = (EditText) findViewById(R.id.group_title_search_et);
//		ivType = (ImageView) findViewById(R.id.group_title_search_iv);
//		type_change = (LinearLayout) findViewById(R.id.group_type_change);
//		initData();
//
//	}
//
//	/**
//	 * 
//	 * Describe:初始化数据
//	 * 
//	 * Date:2015-9-29
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void initData() {
//		initPlaceNameData();
//		checkBrandId = "0";
//		searchContentString = getIntent().getStringExtra("searchContent");
//		isPush = getIntent().getBooleanExtra("isPush", false);
//		if (!StringUtil.isEmpty(searchContentString)) {
//			searchContent = searchContentString;
//		}
//		ivBrandArrow.setTag("DOWN");
//		/**
//		 * 默认时间，价格正序排列，进入默认选中时间
//		 */
//		timeType = TimeType.TIME_UP;
//		priceType = PriceType.PRICE_DOWN;
//		timeSortType = 0;
//		priceSortType = 1;
//		checkType = DataType.TIME;
//		messageHandler = new MyMessageHandler(this);
//		ivTitleRight.setTag(ViewType.GRID);
//
//		// InputMethodManager imm = (InputMethodManager)
//		// getSystemService(Context.INPUT_METHOD_SERVICE);
//		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//		listView.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				// TODO Auto-generated method stub
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					mIsListViewidle = true;
//					listAdapter.notifyDataSetChanged();
//				} else {
//					mIsListViewidle = false;
//				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		// gridView.setOnScrollListener(new OnScrollListener() {
//		//
//		// @Override
//		// public void onScrollStateChanged(AbsListView view, int scrollState) {
//		// // TODO Auto-generated method stub
//		// if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
//		// mIsGridViewidle = true;
//		// gridAdapter.notifyDataSetChanged();
//		// }else{
//		// mIsGridViewidle = false;
//		// }
//		// }
//		//
//		// @Override
//		// public void onScroll(AbsListView view, int firstVisibleItem,
//		// int visibleItemCount, int totalItemCount) {
//		// // TODO Auto-generated method stub
//		//
//		// }
//		// });
//	}
//
//	@Override
//	protected void getNetData() {
//		// TODO Auto-generated method stub
//
//		switch (checkType) {
//		case PRICE:
//			/**
//			 * 价格排序
//			 */
//			dataTypeParam = "price";
//			paramSortType = priceSortType;
//			break;
//		case SALENUM:
//			/**
//			 * 销量排序
//			 */
//
//			break;
//
//		case TIME:
//			/**
//			 * 时间排序
//			 */
//			dataTypeParam = "endtime";
//			paramSortType = timeSortType;
//			break;
//
//		default:
//			break;
//		}
//		CityData data = MyApplication.getCheckCityInfor();
//
//		if (data != null) {
//			cityIdString = data.getId();
//		} else {
//			data = (CityData) FileManager.getObject(baseActivity,
//					ConstantsUtil.SAVE_CHECK_CITY_INFOR);
//			cityIdString = data.getId();
//		}
//		ApiHttpCilent.getInstance(this).getProductList(this, dataTypeParam,
//				cityIdString, windBrand, startIndex, endIndex, 0,
//				paramSortType, searchContent, 0, new RequestCallBack());
////		ApiHttpCilent.getInstance(this).getWholeSaleProductList(this, dataTypeParam,
////				cityIdString, windBrand, startIndex, endIndex, 0,
////				 searchContent, "",0, new RequestCallBack());
//	}
//
//	/**
//	 * 
//	 * Describe:处理网络请求返回
//	 * 
//	 * Date:2015-10-11
//	 * 
//	 * Author:liuzhouliang
//	 */
////	public class RequestCallBack extends
////			BaseJsonHttpResponseHandler<WholeBaseBean> {
////
////		@Override
////		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
////				String arg3, WholeBaseBean arg4) {
////			ApiHttpCilent.loading.dismiss();
////			Message message = Message.obtain();
////			message.what = ConstantsUtil.HTTP_FAILE;// 错误
////			messageHandler.sendMessage(message);
////		}
////
////		@Override
////		public void onSuccess(int arg0, Header[] arg1, String arg2,
////				WholeBaseBean arg3) {
////		}
////
////		@Override
////		protected WholeBaseBean parseResponse(String response,
////				boolean arg1) throws Throwable {
////			ApiHttpCilent.loading.dismiss();
////			Gson gson = new Gson();
////			groupBuyDataList = gson.fromJson(response,
////					WholeBaseBean.class);
////			Message message = Message.obtain();
////			if ("1".equals(groupBuyData.getStatus())) {// 正常
////				message.what = ConstantsUtil.HTTP_SUCCESS;
////				message.obj = groupBuyData.getResult();
////			} else {
////				message.what = ConstantsUtil.HTTP_FAILE;// 错误
////				message.obj = groupBuyData.getError().getInfo();
////			}
////			messageHandler.sendMessage(message);
////			return groupBuyDataList;
////		}
////	}
//	public class RequestCallBack extends
//	BaseJsonHttpResponseHandler<GroupBuyProductlistBean> {
//		
//		@Override
//		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//				String arg3, GroupBuyProductlistBean arg4) {
//			Dimessis();
//			Message message = Message.obtain();
//			message.what = ConstantsUtil.HTTP_FAILE;// 错误
//			messageHandler.sendMessage(message);
//		}
//		
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, String arg2,
//				GroupBuyProductlistBean arg3) {
//		}
//		
//		@Override
//		protected GroupBuyProductlistBean parseResponse(String response,
//				boolean arg1) throws Throwable {
//			Dimessis();
//			Gson gson = new Gson();
//			groupBuyData = gson.fromJson(response,
//					GroupBuyProductlistBean.class);
//			Message message = Message.obtain();
//			if ("1".equals(groupBuyData.getStatus())) {// 正常
//				message.what = ConstantsUtil.HTTP_SUCCESS;
//				message.obj = groupBuyData.getResult();
//			} else {
//				message.what = ConstantsUtil.HTTP_FAILE;// 错误
//				message.obj = groupBuyData.getError().getInfo();
//			}
//			messageHandler.sendMessage(message);
//			return groupBuyData;
//		}
//	}
//
//	/**
//	 * 
//	 * Describe:处理网络请求消息
//	 * 
//	 * Date:2015-10-16
//	 * 
//	 * Author:liuzhouliang
//	 */
//	public static class MyMessageHandler extends
//			WeakHandler<GroupBuyListActivity> {
//
//		public MyMessageHandler(GroupBuyListActivity reference) {
//			super(reference);
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case ConstantsUtil.HTTP_SUCCESS:
//				/**
//				 * 处理成功的数据
//				 */
//				getReference().bindViewData();
//				break;
//			case ConstantsUtil.HTTP_FAILE:
//				/**
//				 * 处理失败的数据
//				 */
//				String messageString = (String) msg.obj;
//				if (messageString != null) {
//					ToastUtil.showToast(getReference(), messageString);
//					/**
//					 * 处理失败数据
//					 */
//				} else {
//					ToastUtil.showToast(getReference(), "请求失败");
//
//				}
//				if (getReference().isRefresh) {
//					getReference().hideRefreshView();
//				}
//				if (getReference().isLoadMore) {
//					getReference().hideLoadMoreView();
//				}
//
//				break;
//			}
//		}
//
//	}
//
//	/**
//	 * 
//	 * Describe:绑定控件数据
//	 * 
//	 * Date:2015-9-29
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void bindViewData() {
//		if (startIndex == 1) {
//			/**
//			 * 初始化的数据
//			 */
//			hideRefreshView();
//			// ToastUtil.showToast(baseContext, "初始化" + startIndex);
//			if (groupBuyData != null && groupBuyData.getResult() != null
//					&& groupBuyData.getResult().getList() != null
//					&& groupBuyData.getResult().getList().size() > 0) {
//				/**
//				 * 初始化数据不为空====================
//				 */
//				groupBuyProductList = groupBuyData.getResult().getList();
//				listAdapter = new GroupBuyListAdapter(mIsListViewidle,
//						groupBuyProductList, baseContext);
//				gridAdapter = new GroupBuyGridAdapter(mIsGridViewidle,
//						groupBuyProductList, baseContext);
//				/**
//				 * 品牌数据加入全部
//				 */
//				BrandList brandObj = new BrandList();
//				brandObj.setId("0");
//				brandObj.setName("全部");
//				brandLists = groupBuyData.getResult().getWinetypelist();
//				brandLists.add(0, brandObj);
//				// listView.setAdapter(listAdapter);
//				// // 启动倒计时
//				// listAdapter.startCountDownTime();
//				if (ivTitleRight.getTag() == ViewType.LIST) {
//					/**
//					 * 列表视图
//					 */
//					listView.setAdapter(listAdapter);
//					// // 启动倒计时
//					listAdapter.startCountDownTime();
//				} else {
//					/**
//					 * 宫格视图
//					 */
//					gridView.setAdapter(gridAdapter);
//					gridAdapter.startCountDownTime();
//				}
//
//			} else {
//				/**
//				 * 初始化数据为空================
//				 */
//				showNoGroupbuyView();
//			}
//
//		} else {
//			/**
//			 * 非初始化数据==================
//			 */
//
//			if (groupBuyData != null && groupBuyData.getResult() != null
//					&& groupBuyData.getResult().getList() != null
//					&& groupBuyData.getResult().getList().size() > 0) {
//				/**
//				 * 有分页数据=============
//				 */
//				// ToastUtil.showToast(baseContext, "分页起始页码" + startIndex);
//				List<GroupBuyProductInfor> pageData = groupBuyData.getResult()
//						.getList();
//				if (groupBuyProductList != null) {
//					if (isLoadMore) {
//						groupBuyProductList.addAll(pageData);
//						hideLoadMoreView();
//					}
//
//				}
//			} else {
//				/**
//				 * 分页数据不存在=================
//				 */
//
//				// 回滚页码
//				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
//				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
//				hideLoadMoreView();
//				// ToastUtil.showToast(baseContext, "数据加载完毕==页码==" +
//				// startIndex);
//			}
//
//		}
//
//	}
//
//	/**
//	 * 
//	 * Describe:隐藏分页加载视图
//	 * 
//	 * Date:2015-10-16
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void hideLoadMoreView() {
//		if (ivTitleRight.getTag() == ViewType.LIST) {
//			/**
//			 * 列表分页
//			 */
//			listViewParent.listPullToRefreshView.onFooterRefreshComplete();
//			listAdapter.setNewData(groupBuyProductList);
//			gridAdapter.setNewData(groupBuyProductList);
//			isLoadMore = false;
//
//		} else {
//			/**
//			 * 宫格分页
//			 */
//			gridViewParent.gridPullToRefreshView.onFooterRefreshComplete();
//			gridAdapter.setNewData(groupBuyProductList);
//			listAdapter.setNewData(groupBuyProductList);
//			isLoadMore = false;
//		}
//	}
//
//	/**
//	 * 
//	 * Describe:隐藏刷新的视图
//	 * 
//	 * Date:2015-10-16
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void hideRefreshView() {
//		if (isRefresh) {
//			if (ivTitleRight.getTag() == ViewType.LIST) {
//				/**
//				 * 列表刷新
//				 */
//				listViewParent.listPullToRefreshView.onHeaderRefreshComplete();
//			} else {
//				/**
//				 * 宫格刷新
//				 */
//				gridViewParent.gridPullToRefreshView.onHeaderRefreshComplete();
//			}
//			isRefresh = false;
//		}
//
//	}
//
//	@Override
//	protected void reloadCallback() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected void setChildViewListener() {
//		// TODO Auto-generated method stub
//		gridViewParent.setRefreshOrLoadListener(this);
//		listViewParent.setRefreshOrLoadListener(this);
//		ivTitleRight.setOnClickListener(this);
//		rlPriceParent.setOnClickListener(this);
//		rlBrandParent.setOnClickListener(this);
//		tvSort.setOnClickListener(this);
//		rlPriceParent.setOnClickListener(this);
//		ivBackToTop.setOnClickListener(this);
//		ivShoppingCart.setOnClickListener(this);
//		ivType.setOnClickListener(this);
//		tvSale.setOnClickListener(this);
//		type_change.setOnClickListener(this);
//		etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//					/**
//					 * 搜索事件
//					 */
//					searchContent = etInput.getText().toString();
//					if (StringUtil.isEmpty(searchContent)) {
//						// ToastUtil.showToast(baseActivity, "请输入搜索内容");
//						searchContent = "";
//					}
////					ApiHttpCilent.getInstance(GroupBuyListActivity.this)
////							.getWholeSaleProductList(GroupBuyListActivity.this,
////									dataTypeParam, cityIdString, windBrand,
////									startIndex, endIndex, 0,
////									searchContent, "",0, new RequestCallBack());
//					ApiHttpCilent.getInstance(GroupBuyListActivity.this)
//					.getProductList(GroupBuyListActivity.this,
//							dataTypeParam, cityIdString, windBrand,
//							startIndex, endIndex, 0, paramSortType,
//							searchContent, 0, new RequestCallBack());
//				}
//				return false;
//			}
//		});
//	}
//
//	@Override
//	protected String setTitleName() {
//		// TODO Auto-generated method stub
//		return "拼单云区";
//	}
//
//	@Override
//	protected String setRightText() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected int setLeftImageResource() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	protected int setMiddleImageResource() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	protected int setRightImageResource() {
//		// TODO Auto-generated method stub
//		// return R.drawable.show_type_grid;
//		return 0;
//	}
//
//	/*
//	 * 初始化产地数据
//	 */
//	private void initPlaceNameData() {
//		ApiHttpCilent.getInstance(baseContext).PlaceNameList(baseContext,
//				new RequestPlaceNameCallBack());
//		ApiHttpCilent.getInstance(baseContext).BrandList(baseContext,
//				new RequestBrandNameCallBack());
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		HashMap<String, String> map = new HashMap<String, String>();
//		switch (v.getId()) {
//		case R.id.base_activity_title_right_righticon:
//			map.put("bigimage", "");
//			MobclickAgent.onEvent(baseActivity, "0036", map);
//			/**
//			 * 列表视图类型切换事件
//			 */
//			ViewTypeChange();
//
//			break;
//		case R.id.group_type_change:
//			/**
//			 * 列表视图类型切换事件
//			 */
//			ViewTypeChange();
//
//			break;
//		case R.id.group_title_search_iv:
//			/**
//			 * 列表视图类型切换事件
//			 */
//			ViewTypeChange();
//
//			break;
//
//		case R.id.group_buy_list_price:
//			map.put("price", "");
//			MobclickAgent.onEvent(baseActivity, "0033", map);
//			/**
//			 * 选择价格
//			 */
//			initReuquestParams();
//			if (checkType != DataType.PRICE) {
//				/**
//				 * 当前选中类型非价格排序
//				 */
//				checkType = DataType.PRICE;
//				windBrand = 0;
//				initTabView();
//				priceType = PriceType.PRICE_DOWN;
//				ivTabPriceArrow.setImageResource(R.drawable.arrow_down_yellow);
//				priceSortType = 1;
//				tvPrice.setTextColor(getResources().getColor(
//						R.color.color_f9883d));
//				// ToastUtil.showToast(baseContext,
//				// "priceType==" + priceType.toString());
//				getNetData();
//			} else {
//				checkType = DataType.PRICE;
//				windBrand = 0;
//				initTabView();
//				if (PriceType.PRICE_DOWN == priceType) {
//					priceType = PriceType.PRICE_UP;
//					ivTabPriceArrow.setImageResource(R.drawable.arrow_up);
//					priceSortType = 0;
//				} else {
//					priceType = PriceType.PRICE_DOWN;
//					ivTabPriceArrow
//							.setImageResource(R.drawable.arrow_down_yellow);
//					priceSortType = 1;
//				}
//				tvPrice.setTextColor(getResources().getColor(
//						R.color.color_f9883d));
//				// ToastUtil.showToast(baseContext,
//				// "priceType==" + priceType.toString());
//				getNetData();
//			}
//
//			break;
//		// 销量
//		case R.id.group_buy_list_sale_tv:
//			initReuquestParams();
//			if (checkType != DataType.SALENUM) {
//				/**
//				 * 当前选中的不是时间排序
//				 */
//				checkType = DataType.TIME;
//				windBrand = 0;
//				initTabView();
//				timeType = TimeType.TIME_DOWN;
//				ivTimeArrow.setImageResource(R.drawable.arrow_down_yellow);
//				timeSortType = 1;
//				tvSort.setTextColor(getResources().getColor(
//						R.color.color_f9883d));
//				getNetData();
//			} else {
//				/**
//				 * 当前选中的是时间排序，点击事件
//				 */
//				checkType = DataType.TIME;
//				windBrand = 0;
//				initTabView();
//				if (TimeType.TIME_DOWN == timeType) {
//					timeType = TimeType.TIME_UP;
//					ivTimeArrow.setImageResource(R.drawable.arrow_up);
//					timeSortType = 0;
//				} else {
//					/**
//					 * 升序
//					 */
//					timeType = TimeType.TIME_DOWN;
//					ivTimeArrow.setImageResource(R.drawable.arrow_down_yellow);
//					timeSortType = 1;
//				}
//				tvSort.setTextColor(getResources().getColor(
//						R.color.color_f9883d));
//				getNetData();
//			}
//			break;
//		// case R.id.group_buy_list_brand:
//		// map.put("brand","");
//		// MobclickAgent.onEvent(baseActivity, "0034", map);
//		// /*
//		// * 选择品牌
//		// */
//		// // ToastUtil.showToast(baseContext, "test");
//		// initReuquestParams();
//		//
//		// checkType = DataType.BRAND;
//		// initTabView();
//		// // ivBrandArrow.setImageResource(R.drawable.groupbuy_arrow_up_gray);
//		// tvSale.setTextColor(getResources().getColor(R.color.color_f9883d));
//		// String tag = (String) ivBrandArrow.getTag();
//		// if ("DOWN".equals(ivBrandArrow.getTag())) {
////		  if(mPopupWindow!=null&&!mPopupWindow.isShowing()){
//		 //
//		 // }
//		// ivBrandArrow
//		// .setImageResource(R.drawable.groupbuy_arrow_up_gray);
//		// ivBrandArrow.setTag("UP");
//		// } else if ("UP".equals(ivBrandArrow.getTag())) {
//		// ivBrandArrow
//		// .setImageResource(R.drawable.groupbuy_arrow_down_gray);
//		// ivBrandArrow.setTag("DOWN");
//		// }
//		// showBrandWindow(GroupBuyListActivity.this, line,
//		// R.layout.groupbuy_list_brand);
//		// break;
//		case R.id.group_buy_list_sort:
//			initTabView();
//			Intent intents = new Intent(this, SelectPicPopupWindow.class);
//			if (placeNameList != null)
//				intents.putExtra("dataPlaceName", placeNameList.getResult());
//			intents.putExtra("dataBrandName", brandNameList.getResult());
//			startActivityForResult(intents, ConstantsUtil.ACTIVITY_BACK);
//			// map.put("time","");
//			// MobclickAgent.onEvent(baseActivity, "0035", map);
//			// /**
//			// * 选择时间事件
//			// */
//			// initReuquestParams();
//			// if (checkType != DataType.TIME) {
//			// /**
//			// * 当前选中的不是时间排序
//			// */
//			// checkType = DataType.TIME;
//			// windBrand = 0;
//			// initTabView();
//			// timeType = TimeType.TIME_DOWN;
//			// ivTimeArrow.setImageResource(R.drawable.arrow_down_yellow);
//			// timeSortType = 1;
//			// tvSort.setTextColor(getResources().getColor(
//			// R.color.color_f9883d));
//			// getNetData();
//			// } else {
//			// /**
//			// * 当前选中的是时间排序，点击事件
//			// */
//			// checkType = DataType.TIME;
//			// windBrand = 0;
//			// initTabView();
//			// if (TimeType.TIME_DOWN == timeType) {
//			// timeType = TimeType.TIME_UP;
//			// ivTimeArrow.setImageResource(R.drawable.arrow_up);
//			// timeSortType = 0;
//			// } else {
//			// /**
//			// * 升序
//			// */
//			// timeType = TimeType.TIME_DOWN;
//			// ivTimeArrow.setImageResource(R.drawable.arrow_down_yellow);
//			// timeSortType = 1;
//			// }
//			// tvSort.setTextColor(getResources().getColor(
//			// R.color.color_f9883d));
//			// getNetData();
//			// }
//
//			break;
//		case R.id.group_buy_list_back_totop:
//			/**
//			 * 返回到顶部
//			 */
//			if (ivTitleRight.getTag() == ViewType.LIST) {
//				/**
//				 * 当前是列表视图
//				 */
//				listView.requestFocusFromTouch();
//				listView.setSelection(0);
//			} else {
//				/**
//				 * 当前是网格视图
//				 */
//				gridView.requestFocusFromTouch();
//				gridView.setSelection(0);
//			}
//
//			break;
//
//		case R.id.group_buy_list_shopping_cart:
//			/**
//			 * 进入购物车
//			 */
//			// ToastUtil.showToast(baseContext, ActivityManagerUtil
//			// .getActivityManager().getActivityNum(MainActivity.class)
//			// + "");
//			int num = ActivityManagerUtil.getActivityManager().getActivityNum(
//					MainActivity.class);
//			Intent intent = new Intent(this, MainActivity.class);
//			intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
//					ConstantsUtil.MAIN_TAB_SHOP_CAR);
//			intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
//					ConstantsUtil.SHOPPING_CART_SHOW_BACK);
//			if (num > 1) {
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//			}
//			StartActivityUtil.startActivity(baseActivity, intent);
//
//			break;
//		case R.id.base_activity_title_backicon:
//			map.put("back", "");
//			MobclickAgent.onEvent(baseActivity, "0037", map);
//			if (isPush) {
//				Intent i = new Intent(baseContext, MainActivity.class);
//				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				StartActivityUtil.startActivity(baseActivity, i);
//				finish();
//			} else {
//				onBackPressed();
//			}
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void ViewTypeChange() {
//
//		if (ivType.getTag() == ViewType.LIST) {
//			/**
//			 * 当前是列表格式
//			 */
//			listViewParent.setVisibility(View.GONE);
//			gridViewParent.setVisibility(View.VISIBLE);
//			gridView.setAdapter(gridAdapter);
//			gridAdapter.notifyDataSetChanged();
//			// gridAdapter.startCountDownTime();
//			ivType.setTag(ViewType.GRID);
//			ivType.setImageResource(R.drawable.show_type_grid);
//
//		} else {
//			/**
//			 * 当前是宫格格式
//			 */
//			listViewParent.setVisibility(View.VISIBLE);
//			gridViewParent.setVisibility(View.GONE);
//			listView.setAdapter(listAdapter);
//			listAdapter.notifyDataSetChanged();
//			listAdapter.startCountDownTime();
//			ivType.setTag(ViewType.LIST);
//			ivType.setImageResource(R.drawable.show_type_list);
//		}
//	}
//
//	// 返回键处理
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (isPush) {
//				Intent i = new Intent(baseContext, MainActivity.class);
//				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				StartActivityUtil.startActivity(baseActivity, i);
//				finish();
//			} else {
//				onBackPressed();
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * Describe:显示品牌选择视图
//	 * 
//	 * Date:2015-10-16
//	 * 
//	 * Author:liuzhouliang
//	 */
//	public PopupWindow showBrandWindow(Activity activity, View position,
//			int layoutId) {
//		View mPopMenuView = null;
//		mPopMenuView = LayoutInflater.from(activity).inflate(layoutId, null);
//		layout = (LinearLayout) mPopMenuView
//				.findViewById(R.id.groupbuy_list_brand_parent);
//		brandGvGridView = (GridView) mPopMenuView
//				.findViewById(R.id.groupbuy_list_brand_gv);
//		relative_brand = (RelativeLayout) mPopMenuView
//				.findViewById(R.id.relative_brand);
//		relative_brand.setVisibility(View.GONE);
//		bindBrandData();
//		layout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				mPopupWindow.dismiss();
//			}
//		});
//		mPopupWindow = new PopupWindow(mPopMenuView,
//				ViewUtil.getScreenWith(activity), LayoutParams.WRAP_CONTENT,
//				false);
//		mPopupWindow
//				.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//		mPopupWindow.setFocusable(true);
//		mPopupWindow.setTouchable(true);
//		mPopupWindow.setOutsideTouchable(false);
//		mPopupWindow.showAsDropDown(position,
//				ViewUtil.getScreenWith((Context) activity), 0);
//		// mPopMenuView.getBackground().setAlpha(230);
//		layout.getBackground().setAlpha(230);
//		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
//		mPopupWindow.update();
//		mPopupWindow.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				ivBrandArrow
//						.setImageResource(R.drawable.groupbuy_arrow_down_gray);
//				ivBrandArrow.setTag("DOWN");
//			}
//		});
//		return mPopupWindow;
//	}
//
//	/**
//	 * 
//	 * Describe:绑定品牌数据
//	 * 
//	 * Date:2015-10-15
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void bindBrandData() {
//		if (groupBuyData != null) {
//
//			brandAdapter = new GroupBuyBrandAdapter(brandGvGridView,
//					brandLists, baseActivity, this);
//			brandGvGridView.setAdapter(brandAdapter);
//			brandAdapter.setCheckListener(this);
//		}
//
//	}
//
//	/**
//	 * 
//	 * Describe:初始化TAB视图背景
//	 * 
//	 * Date:2015-10-10
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void initTabView() {
//		ivBrandArrow.setImageResource(R.drawable.groupbuy_arrow_down_gray);
//		ivTimeArrow.setImageResource(R.drawable.arrow_down_gray);
//		ivTabPriceArrow.setImageResource(R.drawable.arrow_down_gray);
//		tvPrice.setTextColor(getResources().getColor(R.color.color_454545));
//		tvSort.setTextColor(getResources().getColor(R.color.color_454545));
//		tvSale.setTextColor(getResources().getColor(R.color.color_454545));
//	}
//
//	/**
//	 * 宫格视图刷新回调
//	 */
//	@Override
//	public void gridOnRefresh() {
//		// Toast.makeText(this, "grid+OnRefresh", Toast.LENGTH_LONG).show();
//		isRefresh = true;
//		initReuquestParams();
//		getNetData();
//	}
//
//	/**
//	 * 宫格视图分页回调
//	 */
//	@Override
//	public void gridonLoadMore() {
//		// Toast.makeText(this, "grid+onLoadMore", Toast.LENGTH_LONG).show();
//		isLoadMore = true;
//		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
//		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
//		getNetData();
//	}
//
//	/**
//	 * 列表视图刷新回调
//	 */
//	@Override
//	public void listOnRefresh() {
//		// Toast.makeText(this, "list+OnRefresh", Toast.LENGTH_LONG).show();
//		isRefresh = true;
//		initReuquestParams();
//		getNetData();
//	}
//
//	/**
//	 * 列表视图分页回调
//	 */
//	@Override
//	public void listonLoadMore() {
//		// Toast.makeText(this, "list+onLoadMore", Toast.LENGTH_LONG).show();
//		isLoadMore = true;
//		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
//		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
//		getNetData();
//	}
//
//	@Override
//	public void checkCallBack(BrandList data) {
//		if (mPopupWindow != null && mPopupWindow.isShowing()) {
//			mPopupWindow.dismiss();
//			windBrand = Integer.parseInt(data.getId());
//			checkBrandId = data.getId();
//			// ToastUtil.showToast(baseContext, data.getId() + "");
//			getNetData();
//		}
//	}
//
//	/**
//	 * 
//	 * Describe:下拉刷新初始化请求参数
//	 * 
//	 * Date:2015-10-16
//	 * 
//	 * Author:liuzhouliang
//	 */
//	private void initReuquestParams() {
//		timeSortType = 1;
//		priceSortType = 1;
//		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
//		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
//	}
//
//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this); // 统计时长
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);
//	}
//
//	public class RequestPlaceNameCallBack extends
//			BaseJsonHttpResponseHandler<PlaceNameBaseBean> {
//
//		@Override
//		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//				String arg3, PlaceNameBaseBean arg4) {
//			Dimessis();
//			Message message = Message.obtain();
//			message.what = ConstantsUtil.HTTP_FAILE;// 错误
//			messageHandler.sendMessage(message);
//		}
//
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, String arg2,
//				PlaceNameBaseBean arg3) {
//		}
//
//		@Override
//		protected PlaceNameBaseBean parseResponse(String response, boolean arg1)
//				throws Throwable {
//			Dimessis();
//			Gson gson = new Gson();
//			placeNameList = gson.fromJson(response, PlaceNameBaseBean.class);
//			Message message = Message.obtain();
//			if ("1".equals(placeNameList.getStatus())) {// 正常
//				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
//				message.obj = placeNameList.getResult();
//			} else {
//				message.what = ConstantsUtil.HTTP_FAILE;// 错误
//				message.obj = placeNameList.getError().getInfo();
//			}
//			messageHandler.sendMessage(message);
//			return placeNameList;
//		}
//
//	}
//	private void Dimessis() {
//		GroupBuyListActivity.this.runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				if(ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
//					ApiHttpCilent.loading.dismiss();
//			}
//		});
//	}
//
//	public class RequestBrandNameCallBack extends
//			BaseJsonHttpResponseHandler<BrandBaseBean> {
//
//		@Override
//		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//				String arg3, BrandBaseBean arg4) {
//			Dimessis();
//			Message message = Message.obtain();
//			message.what = ConstantsUtil.HTTP_FAILE;// 错误
//			messageHandler.sendMessage(message);
//		}
//
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, String arg2,
//				BrandBaseBean arg3) {
//		}
//
//		@Override
//		protected BrandBaseBean parseResponse(String response, boolean arg1)
//				throws Throwable {
//			Dimessis();
//			Gson gson = new Gson();
//			brandNameList = gson.fromJson(response, BrandBaseBean.class);
//			Message message = Message.obtain();
//			if ("1".equals(brandNameList.getStatus())) {// 正常
//				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
//				message.obj = brandNameList.getResult();
//			} else {
//				message.what = ConstantsUtil.HTTP_FAILE;// 错误
//				message.obj = brandNameList.getError().getInfo();
//			}
//			messageHandler.sendMessage(message);
//			return brandNameList;
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK) {
//			switch (requestCode) {
//			case ConstantsUtil.ACTIVITY_BACK:
//				areaId = data.getStringExtra("areaId");// 回调产区ID
//				break;
//			}
//		}
//	}
//	private String areaId;
//}
