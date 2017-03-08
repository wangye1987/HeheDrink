package com.heheys.ec.controller.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heheys.ec.R;
import com.heheys.ec.animationManage.ProductDetailAnimation;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.FileManager;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.DestanceAreaAdapter;
import com.heheys.ec.model.adapter.GroupBuyBrandAdapter;
import com.heheys.ec.model.adapter.GroupBuyBrandAdapter.CheckBrandListener;
import com.heheys.ec.model.adapter.MoreSuitListAdapter.AddShopingCallback;
import com.heheys.ec.model.adapter.NewShoppingCartFragmentAdapter;
import com.heheys.ec.model.adapter.WholeSaleGridAdapter;
import com.heheys.ec.model.adapter.WholeSaleListAdapter;
import com.heheys.ec.model.adapter.WineBrandAdapter;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.AddressModel;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BrandBaseBean;
import com.heheys.ec.model.dataBean.BrandBaseBean.BrandBean;
import com.heheys.ec.model.dataBean.CityListBean.CityDataList.CityData;
import com.heheys.ec.model.dataBean.Factorbean;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.BrandList;
import com.heheys.ec.model.dataBean.MoreSuitBean;
import com.heheys.ec.model.dataBean.MoreSuitBean.SuitList;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder.shoppingbean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderList;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.SuitListShopping;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean.PlaceListResultBean;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.WholeBaseBean;
import com.heheys.ec.model.dataBean.WholeBaseBean.WholeListBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ActivityManagerUtil;
import com.heheys.ec.utils.CharacterParser;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.PinyinComparator;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.MyDialog;
import com.heheys.ec.view.AlertDialogCustom.UpdateOrNot;
import com.heheys.ec.view.RefreshOrLoadMoreGridViewParent;
import com.heheys.ec.view.RefreshOrLoadMoreGridViewParent.GridPullOrRefreshListener;
import com.heheys.ec.view.RefreshOrLoadMoreListViewParent;
import com.heheys.ec.view.RefreshOrLoadMoreListViewParent.ListPullOrRefreshListener;
import com.heheys.ec.view.SideView;
import com.heheys.ec.view.SideView.OnTouchingLetterChangedListener;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//import com.heheys.ec.controller.fragment.ShoppingCartFragment;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-14 上午11:03:50 类说明 云批界面
 */
@SuppressLint("InflateParams")
public class GroupWholeSaleActivity extends BaseActivity implements
		GridPullOrRefreshListener, ListPullOrRefreshListener,
		CheckBrandListener {
	private static final String TAG = GroupWholeSaleActivity.class.getName();
	protected ListView listView = null;
	protected GridView gridView = null;

	private WholeSaleListAdapter listAdapter;
	private WholeSaleGridAdapter gridAdapter;
	// 商品列表页当前显示模式，是否宫格列表显示
	protected boolean isListGridShow = false;
	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;
	private RefreshOrLoadMoreGridViewParent gridViewParent;
	private RefreshOrLoadMoreListViewParent listViewParent;
	private PopupWindow mPopupWindow;
	private LinearLayout layout;
	private RelativeLayout rlPriceParent, rlDefault, rlSale, rlScreenParent;
	private TextView tvFilter, tvPrice, tvDefalut, tvSale, pd_pf_tv;
	boolean price_b = true;
	boolean brand_b = true;
	boolean time_b = true;
	private TextView line;;
	private NewOrderBaseBean beanorder;
	// 返回到列表顶部控件
	private ImageView ivBackToTop;
	private static MyMessageHandler messageHandler;
	private WholeBaseBean groupBuyData;
	private PlaceNameBaseBean placeNameList;
	private BrandBaseBean brandNameList;
	private List<WholeListBean> groupBuyProductList;
	// private DataType checkType;
	// 时间排序类型
	private int timeSortType;
	// 价格排序类型
	private int priceSortType;
	// 销量排序类型
	private int saleSortType;

	// 搜索条件
	private String searchContent = "";
	// 筛选类型 "order": "price/sales/", 排序 价格、销量，空为默认排序
	private String order = "";
	// 选择酒类品牌
	private String windType = "";
	// 酒类品牌数据适配器
	private GroupBuyBrandAdapter brandAdapter;
	// 酒类品牌数据
	private List<BrandList> brandLists;
	// 左侧筛选品牌数据
	// 品牌数据bean
	private com.heheys.ec.model.dataBean.BrandBaseBean.BrandList beanbrands;
	private GridView brandGvGridView;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	private ImageView ivShoppingCart;
	// private SaleType saleType;
	// private PriceType priceType;
	// private FilterType filterType;
	private ImageView ivfilterArrow, ivsaleArrow, ivTabPriceArrow;
	private String searchContentString;
	private AlertDialogCustom mDialogCoustom;
	// 酒品类
	private boolean isPush;
	public String checkBrandId;// 选择品牌id
	private RelativeLayout relative_brand;
	private EditText etInput;
	// 城市ID
	String cityIdString = "";
	// 是否在滑动
	private boolean mIsGridViewidle = true;
	private boolean mIsListViewidle = true;
	private ImageView ivType;
	private LinearLayout type_change;
	private LinearLayout linear_right, linear_hind_two;
	// fragment管理器
	private FragmentManager fragmentManager;
	private ArrayList<String> cityList;
	public List<CityData> listObj;
//	ShowCityFragment showCityListFragment;
	private FrameLayout view_content;
	private LinearLayout parent_content;
	View mpopuView = null;
	private TextView tvCurrentPosition;
	private ListView mListView;
	private TextView tvToast;
	private SideView sideView;
	private TextView tvLocation;
	private TextView tvHotCity;
	private RelativeLayout currentCityParent;
	private View headView;
	private int sortUpDown = 1;
	private PlaceListResultBean placeList;// 产地集合数据
	private boolean isdefault = true;// 默认是否选中
	private boolean isprice = false;// 价格是否选中
	private boolean issale = false;// 销量是否选中
	private boolean isselect = false;// 筛选是否选中
	boolean isDownprice = true;// 价格是否正序
	boolean isDownSale = true;// 销量
	boolean isDownfilter = true;// 筛选
	protected static LayoutInflater baseInflate;
	private PinyinComparator pinyinComparator;
	private CharacterParser characterParser;
	private List<AddressModel> sourceDataList;
	private DestanceAreaAdapter cityadapter;
	private String place = "";// 产地
	private String type = "";// 默认是批发界面
	private int num;
	private String recommend = "";
	private Factorbean factorbean;// 是否带筛选条件
	private boolean issx;
	private ListView lv_brands;
	private WineBrandAdapter leftbrandAdapter;
	private List<BrandBean> listbrand;// 品牌列表
	private ImageView iv_hind;
	private TextView iv_hind_two;
	private LinearLayout linear_brands;
	private LinearLayout linear_left;
	private int asc = 1;// 默认正序 从小到大
	public static Button shoppingcare_num;// 购物车总件数小角标
	public static NewShoppingCartInforBean shoppingCartData;// 购物车数据bean
	private TextView tv_total_price;// 总价格
	private TextView tv_total_num;// 建议购物车个数
	private OrderList bean;// 预览订单返回数据result
	// 准备结算的商品id 和 num
	public static List<ShoppingCartSelectBean> selectProductNotInEdit;
	private TextView tv_pay;
	private List<ShoppingCartSelectBean> selectProductNotInEditToNext;// 存储当前提交成功的商品列表
	private List<ShoppingCartSelectBean> selectProductNotInEditTemp;
	private RelativeLayout linear_buttom_parent;
	private boolean back;// 是否返回
	private MyDialog alertdialog;
	private PopupWindow popupWindow;
	private LinearLayout linear_close;
	private View populayout;
	private MoreSuitBean suitlistbean;
	private FrameLayout frame_layout;
	private int height;
	/**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];
     int i=0;
	private LinearLayout linear_parent;
	public enum ViewType {
		LIST, GRID
	}

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.group_whole_sale);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		ivTabPriceArrow = (ImageView) findViewById(R.id.group_buy_list_arrow);// 价格
		ivfilterArrow = (ImageView) findViewById(R.id.group_buy_list_filter_arrow);// 筛选
		ivsaleArrow = (ImageView) findViewById(R.id.group_buy_list_arrow_sale);// 销量

		lv_brands = (ListView) findViewById(R.id.lv_brands);// 酒品类列表
		iv_hind = (ImageView) findViewById(R.id.iv_hind);
		tv_total_price = (TextView) findViewById(R.id.tv_total_price);
		tv_total_num = (TextView) findViewById(R.id.tv_total_num);
		iv_hind_two = (TextView) findViewById(R.id.iv_hind_two);
		linear_brands = (LinearLayout) findViewById(R.id.linear_brands);
		linear_left = (LinearLayout) findViewById(R.id.linear_left);

		linear_buttom_parent = (RelativeLayout) findViewById(R.id.linear_buttom_parent);
		ivShoppingCart = (ImageView) findViewById(R.id.group_buy_list_shopping_cart);
		shoppingcare_num = (Button) findViewById(R.id.shoppingcare_num);
		tv_pay = (TextView) findViewById(R.id.tv_pay);// 直接结算按钮
		// bdview = new BadgeView(this, ivShoppingCart);
		gridViewParent = (RefreshOrLoadMoreGridViewParent) findViewById(R.id.group_buy_list_gv);
		listViewParent = (RefreshOrLoadMoreListViewParent) findViewById(R.id.group_buy_list_lv);
		listView = listViewParent.getListView();
		gridView = gridViewParent.getGridView();
		listView.setVerticalScrollBarEnabled(true);
		gridView.setVerticalScrollBarEnabled(true);
		rlSale = (RelativeLayout) findViewById(R.id.group_buy_list_saleNum);// 销量排序界面
		rlDefault = (RelativeLayout) findViewById(R.id.group_buy_list_default);// 默认排序界面
		rlPriceParent = (RelativeLayout) findViewById(R.id.group_buy_list_price);// 价格排序
		rlScreenParent = (RelativeLayout) findViewById(R.id.group_buy_list_screen);// 筛选

		pd_pf_tv = (TextView) findViewById(R.id.pd_pf_tv);
		tvDefalut = (TextView) findViewById(R.id.group_buy_list_sort_default);
		tvSale = (TextView) findViewById(R.id.group_buy_list_sale_sort);
		tvPrice = (TextView) findViewById(R.id.group_buy_list_price_tv);
		tvFilter = (TextView) findViewById(R.id.group_buy_list_filter_tv);

		line = (TextView) findViewById(R.id.group_buy_list_line);
		ivBackToTop = (ImageView) findViewById(R.id.group_buy_list_back_totop);
		etInput = (EditText) findViewById(R.id.group_title_search_et);
		ivType = (ImageView) findViewById(R.id.group_title_search_iv);
		linear_hind_two = (LinearLayout) findViewById(R.id.linear_hind_two);
		type_change = (LinearLayout) findViewById(R.id.group_type_change);
		linear_right = (LinearLayout) findViewById(R.id.linear_right);
		viewNoGroupBuy = findViewById(R.id.base_activity_group_buy_nodata);
		pd_pf_tv = (TextView) findViewById(R.id.pd_pf_tv);
		initData();
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == SCROLL_STATE_IDLE) {
					line.setVisibility(View.VISIBLE);
				} else {
					line.setVisibility(View.GONE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == SCROLL_STATE_IDLE) {
					line.setVisibility(View.VISIBLE);
				} else {
					line.setVisibility(View.GONE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		beanbrands = new com.heheys.ec.model.dataBean.BrandBaseBean.BrandList();
		List<BrandBean> leftBrand = new ArrayList<BrandBean>();
		leftbrandAdapter = new WineBrandAdapter(leftBrand, this);
		lv_brands.setAdapter(leftbrandAdapter);
		lv_brands.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				windType = listbrand.get(position).getId();
				initReuquestParams();
				getData();
				leftbrandAdapter.changeSelected(position);// 刷新
			}
		});
		lv_brands.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				leftbrandAdapter.changeSelected(position);// 刷新
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		int heapSize = manager.getMemoryClass();
//		System.out.println("系统内存" + heapSize);
	}

	/**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-9-29
	 * 
	 * Author:liuzhouliang
	 */
	private void initData() {

		// 初始化获取购物车数据
		selectProductNotInEdit = new ArrayList<ShoppingCartSelectBean>();
		mDialogCoustom = new AlertDialogCustom();
		checkBrandId = "0";
		searchContentString = getIntent().getStringExtra("searchContent");
		type = getIntent().getStringExtra(ConstantsUtil.PRODUCT_TYPE_KEY);// 判断是拼单还是批发
		factorbean = (Factorbean) getIntent()
				.getSerializableExtra("factorbean");
		recommend = getIntent().getStringExtra(
				ConstantsUtil.PRODUCT_TYPE_KEY_JX);
		issx = getIntent().getBooleanExtra("issx", false);
		if (!StringUtil.isEmpty(recommend)) {
			rlSale.setVisibility(View.VISIBLE);
			tvTitleName.setText("精选专区");
			// 精选无侧边筛选
			linear_brands.setVisibility(View.GONE);
			iv_hind_two.setVisibility(View.GONE);
			type_change.setTag(ViewType.LIST);
		} else {
			if ("1".equals(type) && !issx) {
				// 批发
				rlSale.setVisibility(View.VISIBLE);
				tvTitleName.setText("云批专区");
				linear_brands.setVisibility(View.VISIBLE);
				type_change.setTag(ViewType.LIST);
			} else {
				// 拼单
				tvTitleName.setText("拼单专区");
				// 精选无侧边筛选
				linear_brands.setVisibility(View.GONE);
				iv_hind_two.setVisibility(View.GONE);
				if (!issx) {
					type_change.setTag(ViewType.GRID);
					rlSale.setVisibility(View.GONE);
				} else {
					type_change.setTag(ViewType.LIST);
					rlSale.setVisibility(View.VISIBLE);
				}
			}
		}
		isPush = getIntent().getBooleanExtra("isPush", false);
		if (!StringUtil.isEmpty(searchContentString)) {
			searchContent = searchContentString;
		}
		messageHandler = new MyMessageHandler(this);
		setbg(rlDefault, tvDefalut);
		CityData data = MyApplication.getCheckCityInfor();

		if (data != null) {
			cityIdString = data.getId();
		} else {
			data = (CityData) FileManager.getObject(baseActivity,
					ConstantsUtil.SAVE_CHECK_CITY_INFOR);
			if(data !=null)
			cityIdString = data.getId();
		}
		if (factorbean != null) {
			order = factorbean.getOrder();
			windType = factorbean.getWinetype();
			asc = factorbean.getAsc();
			searchContent = factorbean.getInputstr();
			place = factorbean.getPlace();
			type = factorbean.getType();
			recommend = factorbean.getRecommend();
			if (!StringUtil.isEmpty(factorbean.getTitle())) {
				ResetTitle(factorbean.getTitle());
			}
			if (order == null)
				order = "";
			if (windType == null)
				windType = "";
			if (searchContent == null)
				searchContent = "";
			if (place == null)
				place = "";
			if (type == null)
				type = "";
			if (recommend == null)
				recommend = "";
		}

		height = ViewUtil.getHeight(linear_buttom_parent);
		LinearLayout llLayout = new LinearLayout(baseContext);
		View childView = new View(baseContext);
		childView.setBackgroundColor(getResources().getColor(R.color.white));

		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, height);
		llLayout.addView(childView, llParams);
	}

	// 获取需要快速结算的商品集合
	public static List<ShoppingCartSelectBean> getPayNext(
			List<ShoppingCartSelectBean> selectProductNotInEdit,
			NewShoppingCartInforBean shoppingCartData) {
		selectProductNotInEdit.clear();
		int size = shoppingCartData.getResult().getList().size();
		for (int i = 0; i < size; i++) {
			String stateString = shoppingCartData.getResult().getList().get(i)
					.getStatus();
			boolean isOutTime = isActivityOutTime(stateString);
			if (!isOutTime) {
				/**
				 * 商品活动未过期
				 */
				ShoppingCartSelectBean dataBean = new ShoppingCartSelectBean();
				dataBean.setNum(shoppingCartData.getResult().getList().get(i)
						.getNum());// 商品数量
				/*
				 * 推荐套餐获取Sid生成订单 单品和组合套餐直接去aid(活动Id)
				 * **/
				if(shoppingCartData.getResult().getList().get(i).getIssuit()==2){
				dataBean.setSid(shoppingCartData.getResult().getList().get(i)
						.getSid());// 活动ID
				dataBean.setAid("");// 活动ID
				}else{
				 dataBean.setAid(shoppingCartData.getResult().getList().get(i)
						 .getAid());// 活动ID
				 dataBean.setSid("");// 活动ID
				}
				if ("1".equals(shoppingCartData.getResult().getList().get(i)
						.getType()))
					dataBean.setCurrentprice(shoppingCartData.getResult()
							.getList().get(i).getCurrentprice());
				else
					dataBean.setCurrentprice(shoppingCartData.getResult()
							.getList().get(i).getCprice());
				selectProductNotInEdit.add(dataBean);
			}
		}
		return selectProductNotInEdit;
	}

	public static boolean isActivityOutTime(String state) {
		if ("0".equals(state)) {
			/**
			 * 未开始
			 */
			return false;
		} else if ("1".equals(state)) {
			/**
			 * 已经开始
			 */
			return false;
		} else if ("2".equals(state)) {
			/**
			 * 已经抢光
			 */
			return true;
		} else if ("3".equals(state)) {
			/**
			 * 未成单
			 */
			return true;
		} else if ("4".equals(state)) {
			/**
			 * 已经成单 不能生成订单
			 */
			return true;
		} else if ("5".equals(state)) {
			/**
			 * 已经售罄抢光
			 */
			return true;
		} else if ("6".equals(state)) {
			/**
			 * 已经下架
			 */
			return true;
		} else if ("7".equals(state)) {
			/**
			 * 已经结束
			 */
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void getNetData() {
		/*
		 * 获取左侧品牌数据
		 */
		ApiHttpCilent.getInstance(this).BrandList(this,"",
				new RequestBrandNameCallBack());
		// ApiHttpCilent.getInstance(this).GetInfo(this, new MyCallBack());
		getData();
	}

	void getShoppingcartData() {
		// 获取购物车数据
		ApiHttpCilent.getInstance(GroupWholeSaleActivity.this).getShoppingCartInfor(
				GroupWholeSaleActivity.this, new ShoppingCartInforRequestCallBack());
	}

	/*
	 * 获取左侧品牌回调数据
	 */
	public class RequestBrandNameCallBack extends
			BaseJsonHttpResponseHandler<BrandBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BrandBaseBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BrandBaseBean arg3) {
		}

		@Override
		protected BrandBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			BrandBaseBean brandNameList = gson.fromJson(response,
					BrandBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(brandNameList.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
				beanbrands = brandNameList.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = brandNameList.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return brandNameList;
		}
	}

	private void getData() {
		// 获取商铺列表
		ApiHttpCilent.getInstance(this).getWholeSaleProductList(this, order,
				cityIdString, windType, startIndex, endIndex, asc,
				searchContent, place, type, recommend, new RequestCallBack());
	}

	public class ShoppingCartInforRequestCallBack extends
			BaseJsonHttpResponseHandler<NewShoppingCartInforBean> {

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewShoppingCartInforBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewShoppingCartInforBean arg3) {
			Dimessis();
		}

		@Override
		protected NewShoppingCartInforBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			shoppingCartData = gson.fromJson(response,
					NewShoppingCartInforBean.class);
			Message message = Message.obtain();
			if ("1".equals(shoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
				message.obj = shoppingCartData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE_CONNECTTIMEOUT;// 错误
				message.obj = shoppingCartData.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return shoppingCartData;
		}
	}

	private void Dimessis() {
		GroupWholeSaleActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tv_pay.setEnabled(true);
				if (GroupWholeSaleActivity.this != null
						&& !GroupWholeSaleActivity.this.isFinishing()) {
					if (ApiHttpCilent.loading != null
							&& ApiHttpCilent.loading.isShowing())
						ApiHttpCilent.loading.dismiss();
				}
			}
		});
	}

	/**
	 * 
	 * Describe:处理网络请求返回
	 * 
	 * Date:2015-10-11
	 * 
	 * Author:liuzhouliang
	 */
	public class RequestCallBack extends
			BaseJsonHttpResponseHandler<WholeBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, WholeBaseBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				WholeBaseBean arg3) {
		}

		@Override
		protected WholeBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// System.out.println("列表后"+System.currentTimeMillis());
			Dimessis();
			Gson gson = new Gson();
			groupBuyData = gson.fromJson(response, WholeBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(groupBuyData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = groupBuyData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = groupBuyData.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return groupBuyData;
		}
	}

	private String DoPrice(float ft) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String fts = fnum.format(ft);
		return fts;
	}
	 /* @Override  
	    public void onWindowFocusChanged(boolean hasFocus) {  
	        super.onWindowFocusChanged(hasFocus);  
	        if(hasFocus){  
	            int[] location = new int[2];  
	            int[] location_L = new int[2];  
	            mTargetView.getLocationOnScreen(location);  //获取目标View的相对屏幕位置  
	            mParentView.getLocationOnScreen(location_L);     //获取父View相对屏幕位置                          
	            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mCopyView.getLayoutParams();  
	            params.leftMargin = location[0] - location_L[0];     
	            params.topMargin = location[1] - location_L[1];   //给副本View设置位置，与目标View重合  
	                         .......    //这里还需要设置大小等属性，省略  
	        }  
	    }  */
	/*
	 * aid 当前点击数据aid **弹出框 获取优惠套装列表
	 */
	public void showPopUp(View v, final String url, final String aid,
			final int position) {
		populayout = getLayoutInflater().inflate(R.layout.suit_list, null);
		int screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();// 获取屏幕高度
		popupWindow = new PopupWindow(populayout,
				LinearLayout.LayoutParams.MATCH_PARENT, screenHeight * 4/5);
//		LinearLayout.LayoutParams.MATCH_PARENT, screenHeight - (ViewUtil.getHeight(v)*2));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.pop_buttom_anim_style);
		linear_close = (LinearLayout) populayout
				.findViewById(R.id.linear_close);
		linear_parent = (LinearLayout) populayout
				.findViewById(R.id.linear_parent);
		
		
		
		ListView lv_listsuit = (ListView) populayout
				.findViewById(R.id.lv_listsuit);
		frame_layout = (FrameLayout) populayout.findViewById(R.id.frame_layout);
		RelativeLayout re_animation = (RelativeLayout) populayout.findViewById(R.id.re_animation);
		final ImageView iv_pic_animation = (ImageView) populayout.findViewById(R.id.iv_pic_animation);
		//添加头部视图
		View mHeaderView =  getLayoutInflater().inflate(R.layout.suit_head_view, null);
		RelativeLayout relative_produce = (RelativeLayout) mHeaderView
				.findViewById(R.id.relative_produce);
		TextView tv_price = (TextView) mHeaderView.findViewById(R.id.tv_price);
		TextView tv_add = (TextView) mHeaderView.findViewById(R.id.tv_add);
		TextView tv_unit = (TextView) mHeaderView.findViewById(R.id.tv_unit);
		TextView tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
		final ImageView iv_pic = (ImageView) mHeaderView.findViewById(R.id.iv_pic);
		lv_listsuit.addHeaderView(mHeaderView);
		if (groupBuyProductList != null) {
			MyApplication.imageLoader.displayImage(
					groupBuyProductList.get(position).getPic(), iv_pic,
					MyApplication.options);
			tv_name.setText(groupBuyProductList.get(position).getName());
			tv_price.setText(groupBuyProductList.get(position).getCurrency() == null ? "¥"
					: groupBuyProductList.get(position).getCurrency()
							+ groupBuyProductList.get(position).getCprice());
			tv_unit.setText("/"+groupBuyProductList.get(position).getUnit());
		}

		linear_close.setOnClickListener(this);
		relative_produce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listAdapter != null) {
					listAdapter.ToDetaile(position,
							groupBuyProductList.get(position));
				}
			}
		});
		iv_pic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listAdapter != null) {
					listAdapter.ToDetaile(position,
							groupBuyProductList.get(position));
				}
			}
		});
		
		iv_pic.setOnClickListener(this);
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
				(location[0] + v.getWidth() / 2) - popupWindow.getWidth() / 2,
				location[1] - popupWindow.getHeight());
		popupWindow.update();
		  //new执行动画的图片
        MyApplication.imageLoader.displayImage(
    		 groupBuyProductList.get(position).getPic(), iv_pic_animation,
    		 MyApplication.options);
        //获取当前点击按钮的位置
        int[] locations =new int[2];
        tv_add.getLocationOnScreen(locations);
        final int x = locations[0];
		final int y = locations[1];
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewUtil.dip2px(baseActivity, 30),ViewUtil.dip2px(baseActivity, 30));
		lp.leftMargin = x;
		lp.topMargin = y;
//		iv_pic_animation.setLayoutParams(lp);
		//当前需要执行动画的imageview添加到视图
//		re_animation.addView(iv_pic_animation);
		tv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listAdapter != null) {
//					iv_pic_animation.setVisibility(View.VISIBLE);
					listAdapter.AddToShoppcart(position, "add", true);
				}
			}
		});
		if (suitlistbean != null && suitlistbean.getResult() != null) {
			ArrayList<SuitList> dataSuitList = suitlistbean.getResult()
					.getSuit();
			//暂时注销
//			MoreSuitListAdapter suitAdapter = new MoreSuitListAdapter(
//					dataSuitList, this, new ShopSuitback());
//			lv_listsuit.setAdapter(suitAdapter);
		}
	}
	
	 private void addCart(final ImageView iv,Context mContext,ImageView cart) {
		
//	      一、创造出执行动画的主题---imageview
	        //代码new一个imageview，图片资源是上面的imageview的图片
	        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
//	        final ImageView goods = new ImageView(mContext);
//	        goods.setImageDrawable(iv.getDrawable());
//	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
//	        ((ViewManager) populayout).addView(goods, params);

//	        二、计算动画开始/结束点的坐标的准备工作
	        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
	        int[] parentLocation = new int[2];
	        linear_parent.getLocationInWindow(parentLocation);

	        //得到商品图片的坐标（用于计算动画开始的坐标）
	        int startLoc[] = new int[2];
	        iv.getLocationInWindow(startLoc);

	        //得到购物车图片的坐标(用于计算动画结束后的坐标)
	        int endLoc[] = new int[2];
	        cart.getLocationInWindow(endLoc);


//	        三、正式开始计算动画开始/结束的坐标
	        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
	        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
	        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;

	        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
	        float toX = endLoc[0] - parentLocation[0] + cart.getWidth() / 5;
	        float toY = endLoc[1] - parentLocation[1];

//	        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
	        //开始绘制贝塞尔曲线
	        Path path = new Path();
	        //移动到起始点（贝塞尔曲线的起点）
	        path.moveTo(startX, startY);
	        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
	        path.quadTo((startX + toX) / 2, startY, toX, toY);
	        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
	        // 如果是true，path会形成一个闭环
	       final  PathMeasure  mPathMeasure = new PathMeasure(path, false);

	        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
	        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
	        valueAnimator.setDuration(1000);
	        // 匀速线性插值器
	        valueAnimator.setInterpolator(new LinearInterpolator());
	        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	            @Override
	            public void onAnimationUpdate(ValueAnimator animation) {
	                // 当插值计算进行时，获取中间的每个值，
	                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
	                float value = (Float) animation.getAnimatedValue();
	                // ★★★★★获取当前点坐标封装到mCurrentPosition
	                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
	                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
	                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
	                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
	                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
	                iv.setTranslationX(mCurrentPosition[0]);
	                iv.setTranslationY(mCurrentPosition[1]);
	            }
	        });
//	      五、 开始执行动画
	        valueAnimator.start();

//	      六、动画结束后的处理
	        valueAnimator.addListener(new Animator.AnimatorListener() {
	            @Override
	            public void onAnimationStart(Animator animation) {

	            }

	            //当动画结束后：
	            @Override
	            public void onAnimationEnd(Animator animation) {
	                // 购物车的数量加1
	                i++;
//	                count.setText(String.valueOf(i));
	                // 把移动的图片imageview从父布局里移除
	                linear_parent.removeView(iv);
	            }

	            @Override
	            public void onAnimationCancel(Animator animation) {

	            }

	            @Override
	            public void onAnimationRepeat(Animator animation) {

	            }
	        });
	    }
	// 添加购物车回调id
	class ShopSuitback implements AddShopingCallback {

		@Override
		public void getId(String id) {
			AddSuitToShopping(id);
		}
	}

	void AddSuitToShopping(String id) {
		ApiHttpCilent.getInstance(this).AddSuitToShopping(this, id,"1",
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
			messageHandler.sendMessage(message);
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
			AddShoppingCartBean	addShoppingCartData = gson.fromJson(response,
					AddShoppingCartBean.class);
			Message message = Message.obtain();
			if ("1".equals(addShoppingCartData.getStatus())) {// 正常
				message.what = 1003;
				// message.obj = addShoppingCartData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = addShoppingCartData.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return addShoppingCartData;
		}

	}

	private void addShopCarAnimation(View startView, View endView,
			ImageView animationImg) {
		/**
		 * 获取开始动画，结束动画视图位置
		 */
		int[] startLocation = new int[2];
		int[] endLocation = new int[2];
		startView.getLocationInWindow(startLocation);
		endView.getLocationInWindow(endLocation);
		/**
		 * 执行动画视图:ImageView
		 */
		// ImageView animationImg = new ImageView(this);
		// if (bitmap != null) {
		// animationImg.setImageBitmap(bitmap);
		// } else {
		// Resources res = baseContext.getResources();
		// Bitmap bmp = BitmapFactory.decodeResource(res,
		// R.drawable.imageview_default);
		// animationImg.setImageBitmap(bmp);
		// }

		ProductDetailAnimation.getInstance().startAnimation(animationImg,
				endView, startLocation, endLocation, this);

	}

	/**
	 * 
	 * Describe:处理网络请求消息
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 */
	public static class MyMessageHandler extends
			WeakHandler<GroupWholeSaleActivity> {

		private String aidThis;
		private String urlThis;
		private int positionThis;

		public MyMessageHandler(GroupWholeSaleActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1000:
				Bundle dataBundle = msg.getData();
				long[] num = dataBundle.getLongArray("totalNum");
				float productPrice = dataBundle.getFloat("totalPrice");
				ViewUtil.setActivityPrice(getReference().tv_total_price,
						getReference().DoPrice(productPrice) + "");
//				getReference().tv_total_num.setText("(共" + num[0] + "件)");
				if (num[0] > 0) {
//					shoppingcare_num.setText(num[0] + "");
					shoppingcare_num.setVisibility(View.VISIBLE);
					getReference().tv_pay.setEnabled(true);
					getReference().tv_pay
							.setBackgroundColor(getReference().baseActivity
									.getResources().getColor(
											R.color.color_ef5150));
					getReference().ivShoppingCart
							.setBackgroundResource(R.drawable.have_goods);
				} else {
					getReference().tv_pay.setEnabled(false);
					getReference().tv_pay
							.setBackgroundColor(getReference().baseActivity
									.getResources().getColor(
											R.color.color_888888));
					getReference().ivShoppingCart
							.setBackgroundResource(R.drawable.no_goods);
				}
				//获取购物车 需要优化 只是获取总件数
				getReference().getShoppingcartData();
				break;
			case 1001:
				// 弹框获取更多套装视图
				Bundle dataBundleAid = msg.getData();
				aidThis = dataBundleAid.getString("aid");
				urlThis = dataBundleAid.getString("url");
				positionThis = dataBundleAid.getInt("position");
				getReference().getMoreSuitList(aidThis);
				break;
			case 1002:
				// 获取列表成功
				getReference().showPopUp(getReference().ivShoppingCart,
						urlThis, aidThis, positionThis);
				break;
			case 1003:
				//添加购物车成功
				getReference().getShoppingcartData();
				break;
			case ConstantsUtil.ALI_BACK_CODE:
				// 生成预览订单成功
				getReference().bean = (OrderList) msg.obj;
				getReference().ToOrderPre(getReference().bean);
				break;
			case ConstantsUtil.HTTP_SUCCESS:
				/**
				 * 处理成功的数据 获取购物数据
				 */
				getReference().bindViewData();
				getReference().getShoppingcartData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				getReference().tv_pay.setEnabled(true);
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					if (!NetWorkState
							.isNetWorkConnection(getReference().baseContext))
						ToastNoNetWork.ToastError(getReference().baseContext);
					else
						ToastUtil.showToast(getReference(), "请求失败");
				}
				if (getReference().isRefresh) {
					getReference().hideRefreshView();
				}
				if (getReference().isLoadMore) {
					getReference().hideLoadMoreView();
				}

				break;
			// 产地数据
			case ConstantsUtil.HTTP_NEED_LOGIN:
				StartActivityUtil.startActivity(getReference().baseActivity,
						LoginActivity.class);
				getReference().finish();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				/**
				 * 处理成功的数据
				 */
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				getReference().bindLeftBrand();
				break;
			case ConstantsUtil.HTTP_SUCCESS_HEHEPAY:
				// 获取基本信息成功
				getReference().saveVerdata((ResultBean) msg.obj);// 保存认证信息
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				// 获取购车车信息成功
				getReference().ShoppingCartSuccess();
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN:
				// 获取失败的情况下返回不满足条件的商品数量
				ErrorBeanorder ErrorBeanorder = (ErrorBeanorder) msg.obj;
				getReference().sortListByBack(ErrorBeanorder);
				break;
			case ConstantsUtil.HTTP_FAILE_CONNECTTIMEOUT:
				// 购物车获取失败也获取列表数据
				// getReference().getData();
				break;
			}
		}

	}

	// 保存认证信息
	private void saveVerdata(ResultBean resultbean) {
		SharedPreferencesUtil.saveObject(this, resultbean, "resultbean");
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

	// 获取更多套装列表
	public void getMoreSuitList(String aid) {
		ApiHttpCilent.getInstance(this).getSuitList(this, aid,
				new SuitListRequestCallBack());
	}

	public class SuitListRequestCallBack extends
			BaseJsonHttpResponseHandler<MoreSuitBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MoreSuitBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
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
				message.what = 1002;
				message.obj = suitlistbean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = suitlistbean.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return suitlistbean;
		}
	}

	// 获取 不符合要求的商品列表
	private void sortListByBack(ErrorBeanorder ErrorBeanorder) {
		if (ErrorBeanorder != null) {
			final List<shoppingbean> listshoppingbean = ErrorBeanorder
					.getList();
			if (listshoppingbean != null) {
				int errorSize = listshoppingbean.size();
				if (errorSize > 0) {
					selectProductNotInEditTemp = new ArrayList<ShoppingCartSelectBean>();
					selectProductNotInEditTemp.addAll(selectProductNotInEdit);
					// 继续提交
					for (shoppingbean a : listshoppingbean) {
//						if (selectProductNotInEditTemp.contains(a)) {
							for (ShoppingCartSelectBean bean : selectProductNotInEditTemp) {
								if (bean.getAid().equals(a.getAid()) || bean.getSid().equals(a.getSid())) {
									selectProductNotInEditTemp.remove(bean);
									break;
								}
							}
//						}
					}
					// 存在可以提交的商品 弹框
					if (selectProductNotInEditTemp != null
							&& selectProductNotInEditTemp.size() > 0) {
						alertdialog = mDialogCoustom
								.AlertShoppingError(0, baseActivity, StringUtil
										.isEmpty(ErrorBeanorder.getTips()) ? ""
										: ErrorBeanorder.getTips(),
										new UpdateOrNot() {
											@Override
											public void setResult(int modle) {
												if (2 == modle) {
													Preprview(selectProductNotInEditTemp);
												} else {
													// SortCartListNum(listshoppingbean);
													int num = ActivityManagerUtil
															.getActivityManager()
															.getActivityNum(
																	MainActivity.class);
													Intent intent = new Intent();
													intent.setClass(
															baseActivity,
															MainActivity.class);
													intent.putExtra(
															ConstantsUtil.MAIN_TAB_TYPE_KEY,
															ConstantsUtil.MAIN_TAB_SHOP_CAR);
													intent.putExtra(
															ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
															ConstantsUtil.SHOPPING_CART_SHOW_BACK);
													if (num > 1) {
														intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
													}
													StartActivityUtil
															.startActivityForResult(
																	baseActivity,
																	intent,
																	ConstantsUtil.REQUEST_CODE);
												}
												if (alertdialog != null
														&& alertdialog
																.isShowing())
													alertdialog.dismiss();
											}
										});

					} else {
						ToastUtil
								.showToast(baseActivity, "商品数量存在异常,请您重新确认后再提交");
					}
				} else {
					ToastUtil.showToast(baseActivity, StringUtil
							.isEmpty(ErrorBeanorder.getInfo()) ? "获取失败"
							: ErrorBeanorder.getInfo());
				}
			}else{
				ToastUtil.showToast(baseActivity, StringUtil
						.isEmpty(ErrorBeanorder.getInfo()) ? "获取失败"
								: ErrorBeanorder.getInfo());
			}
		}
	}

	// 跳转到生成预览订单界面
	private void ToOrderPre(OrderList bean) {
		Intent intent = new Intent();
		intent.setClass(baseContext, NewOrderDetailActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("selectProductNotInEditToNext",
				(Serializable) selectProductNotInEditToNext);
		StartActivityUtil.startActivity((Activity) baseContext, intent);
	}

	/**
	 * 获取有效商品总件数
	 *
	 */
    private int getTotalNum(){
    	num = 0;
		for(NewProductInfo info:shoppingCartData.getResult().getList()){
			if(!NewShoppingCartFragmentAdapter.isShowActivityIcon(info.getStatus())){
				if(info.getIssuit() != 2){
					//单品
					num =  num +Integer.parseInt(info.getNum());
				}else{
					//组合套装计算方法
					List<SuitListShopping> listSuit = info.getSuitlist();
				if(listSuit != null && listSuit.size() > 0){
					int suitTotalNum = 0;
					for(SuitListShopping suit:listSuit){
						suitTotalNum += Integer.parseInt(StringUtil.isEmpty(suit.getNumPerSuit())?"0":suit.getNumPerSuit());
					 }
					num += suitTotalNum * Integer.parseInt(info.getNum());
				 }
				}
			 }
			}
		return num;
    }
	// 获取购物车数据成功
	@SuppressLint("NewApi")
	private void ShoppingCartSuccess() {
		if (shoppingCartData != null && shoppingCartData.getResult() != null
				&& shoppingCartData.getResult().getList() != null) {
			if (shoppingCartData.getResult().getList().size() > 0) {
				if (shoppingCartData.getResult().getCount() > 0) {
					shoppingcare_num.setText(getTotalNum() + "");
					shoppingcare_num.setVisibility(View.VISIBLE);
				} else {
					shoppingcare_num.setVisibility(View.INVISIBLE);
				}
				if (!StringUtil.isEmpty(shoppingCartData.getResult()
						.getAmount()))
					ViewUtil.setActivityPrice(tv_total_price, shoppingCartData
							.getResult().getAmount());
				tv_total_num.setText("(共"
						+ shoppingCartData.getResult().getCount() + "件)");          
				// 获取可以结算的商品集合
				selectProductNotInEdit = getPayNext(selectProductNotInEdit,
						shoppingCartData);
				if (selectProductNotInEdit != null
						&& selectProductNotInEdit.size() > 0) {
					tv_pay.setEnabled(true);
					tv_pay.setBackgroundColor(baseActivity.getResources()
							.getColor(R.color.color_ef5150));
					ivShoppingCart.setBackgroundResource(R.drawable.have_goods);
				} else {
					tv_pay.setEnabled(false);
					tv_pay.setBackgroundColor(baseActivity.getResources()
							.getColor(R.color.color_888888));
					ivShoppingCart.setBackgroundResource(R.drawable.no_goods);
				}
			} else {
				if (!StringUtil.isEmpty(shoppingCartData.getResult()
						.getAmount()))
					ViewUtil.setActivityPrice(tv_total_price, shoppingCartData
							.getResult().getAmount());
				tv_total_num.setText("(共"
						+ shoppingCartData.getResult().getCount() + "件)");
				if (shoppingCartData.getResult().getCount() == 0) {
					shoppingcare_num.setVisibility(View.INVISIBLE);
					tv_pay.setEnabled(false);
					tv_pay.setBackgroundColor(baseActivity.getResources()
							.getColor(R.color.color_888888));
					ivShoppingCart.setBackgroundResource(R.drawable.no_goods);
				} else {
					tv_pay.setEnabled(true);
					tv_pay.setBackgroundColor(baseActivity.getResources()
							.getColor(R.color.color_ef5150));
					ivShoppingCart.setBackgroundResource(R.drawable.have_goods);
				}
				selectProductNotInEdit.clear();
			}
		} else {
			ivShoppingCart.setEnabled(false);// 不可点击
			tv_pay.setEnabled(false);
			tv_pay.setBackgroundColor(baseActivity.getResources().getColor(
					R.color.color_888888));
			ivShoppingCart.setBackgroundResource(R.drawable.no_goods);
			selectProductNotInEdit.clear();
		}

		/**
		 * 
		 * 首先在获取商品列表 在获取购物车数据 绑定列表数据
		 * */
		// 遍历列表和购物车
		if (groupBuyProductList != null && shoppingCartData != null) {
			SortCartListFast(groupBuyProductList);
			if (listAdapter != null) {
//				listAdapter.setNewData(groupBuyProductList);
				listAdapter.setData(groupBuyProductList);
				listAdapter.setshoppingcart(shoppingCartData);
				listAdapter.notifyDataSetChanged();
			}
			if (gridAdapter != null) {
				gridAdapter.setNewData(groupBuyProductList);
				gridAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 
	 * 绑定左侧品牌数据
	 * 
	 * */
	private void bindLeftBrand() {
		listbrand = beanbrands.getList();
		if (listbrand != null && listbrand.size() > 0) {
			if (!listbrand.get(0).getName().equals("全部")) {
				BrandBean brand = new BrandBean();
				brand.setId("");
				brand.setName("全部");
				listbrand.add(0, brand);
			}
		}
		leftbrandAdapter.setNewData(listbrand);
		leftbrandAdapter.notifyDataSetChanged();
	}

	// 排列 遍历购物车商品在当前列表是否有购买过
	public void SortCartListFast(List<WholeListBean> pageData) {
		// 购物车数据
		if (shoppingCartData != null && shoppingCartData.getResult() != null
				&& shoppingCartData.getResult().getList() != null) {
			List<NewProductInfo> listcart = shoppingCartData.getResult()
					.getList();
			if (listcart.size() > 0) {

				for (Iterator<NewProductInfo> iterator = listcart.iterator(); iterator
						.hasNext();) {
					NewProductInfo shopcart =  iterator.next();
					for (Iterator<WholeListBean> iteratorlist = pageData
							.iterator(); iteratorlist.hasNext();) {
						WholeListBean listdata =  iteratorlist
								.next();
						if (shopcart.getAid().equals(listdata.getId())) {
							listdata.setCurrentnum(Integer.parseInt(StringUtil
									.isEmpty(shopcart.getNum()) ? "0"
									: shopcart.getNum()));
							break;
						}
					}
				}
				// 列表数据
			} else {
				// 如果当前购物车数据没有
				for (WholeListBean wholebean : pageData) {
					// 购车商品在当前列表有数据
					wholebean.setCurrentnum(0);
				}
			}
			// System.out.println("迭代器后"+System.currentTimeMillis());
		}
	}

	/**
	 * 
	 * Describe:绑定控件数据
	 * 
	 * Date:2015-9-29
	 * 
	 * Author:liuzhouliang
	 */
	private void bindViewData() {

		if (startIndex == 1) {
			/**
			 * 初始化的数据
			 */

			// System.out.println("获取列表成功前"+System.currentTimeMillis());
			hideRefreshView();
			// ToastUtil.showToast(baseContext, "初始化" + startIndex);
			if (groupBuyData != null && groupBuyData.getResult() != null
					&& groupBuyData.getResult().getList() != null
					&& groupBuyData.getResult().getList().size() > 0) {
				/**
				 * 初始化数据不为空====================
				 */
				groupBuyProductList = groupBuyData.getResult().getList();

				gridAdapter = new WholeSaleGridAdapter(mIsGridViewidle,
						groupBuyProductList, baseContext, recommend,
						messageHandler, ivShoppingCart, gridView);
				listAdapter = new WholeSaleListAdapter(ivShoppingCart,
						mIsListViewidle, groupBuyProductList, baseContext,
						recommend, messageHandler, null, shoppingcare_num,
						selectProductNotInEdit, shoppingCartData, listView);

				/**
				 * 品牌数据加入全部
				 */
				if ("0".equals(type)) {
					listAdapter.startCountDownTime();
					gridAdapter.startCountDownTime();
				}
				if (type_change.getTag() == ViewType.LIST) {
					/**
					 * 列表视图
					 */
					listView.setAdapter(listAdapter);
					// // 启动倒计时
					listViewParent.setVisibility(View.VISIBLE);
				} else {
					/**
					 * 宫格视图
					 */
					gridView.setAdapter(gridAdapter);
					gridViewParent.setVisibility(View.VISIBLE);
				}

				// System.out.println("获取列表成功排序前"+System.currentTimeMillis());
				//
				// System.out.println("获取列表成功排序后"+System.currentTimeMillis());
				viewNoGroupBuy.setVisibility(View.GONE);
				ivBackToTop.setVisibility(View.GONE);
				ivShoppingCart.setVisibility(View.VISIBLE);
				// System.out.println("获取列表成功后"+System.currentTimeMillis());

			} else {
				/**
				 * 初始化数据为空================
				 */
				setNoDataView();
				if ("0".equals(type))
					pd_pf_tv.setText("目前还没有拼单商品!");
				else
					pd_pf_tv.setText("目前还没有批发商品!");
			}

		} else {
			/**
			 * 非初始化数据==================
			 */
			if (groupBuyData != null && groupBuyData.getResult() != null
					&& groupBuyData.getResult().getList() != null
					&& groupBuyData.getResult().getList().size() > 0) {
				/**
				 * 有分页数据=============
				 */
				// ToastUtil.showToast(baseContext, "分页起始页码" + startIndex);
				List<WholeListBean> pageData = groupBuyData.getResult()
						.getList();
				if (groupBuyProductList != null) {
					// 遍历列表和购物车
					/*
					 * 避免返回重新加载 多加载一次
					 */
					if (!back)
						groupBuyProductList.addAll(pageData);
					hideLoadMoreView();
				}
			} else {
				/**
				 * 分页数据不存在=================
				 */

				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
				// ToastUtil.showToast(baseContext, "数据加载完毕==页码==" +
				// startIndex);
			}

		}

	}

	// 数据为空视图
	private void setNoDataView() {
		viewNoGroupBuy.setVisibility(View.VISIBLE);
		gridViewParent.setVisibility(View.GONE);
		listViewParent.setVisibility(View.GONE);
		ivBackToTop.setVisibility(View.GONE);
		ivShoppingCart.setVisibility(View.VISIBLE);
	}

	// private void setDataView(){
	// viewNoGroupBuy.setVisibility(View.GONE);
	// gridViewParent.setVisibility(View.VISIBLE);
	// listViewParent.setVisibility(View.VISIBLE);
	// ivBackToTop.setVisibility(View.VISIBLE);
	// ivShoppingCart.setVisibility(View.VISIBLE);
	// }
	/**
	 * 
	 * Describe:隐藏分页加载视图
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 */
	private void hideLoadMoreView() {
		if (type_change.getTag() == ViewType.LIST) {
			/**
			 * 列表分页
			 */
			listViewParent.listPullToRefreshView.onFooterRefreshComplete();
			if (listAdapter != null)
				listAdapter.setNewData(groupBuyProductList);
			if (gridAdapter != null)
				gridAdapter.setNewData(groupBuyProductList);
			isLoadMore = false;
			// listViewParent.setVisibility(View.VISIBLE);
		} else {
			/**
			 * 宫格分页
			 */
			gridViewParent.gridPullToRefreshView.onFooterRefreshComplete();
			if (gridAdapter != null)
				gridAdapter.setNewData(groupBuyProductList);
			if (listAdapter != null)
				listAdapter.setNewData(groupBuyProductList);
			isLoadMore = false;
			// gridViewParent.setVisibility(View.VISIBLE);
		}
		viewNoGroupBuy.setVisibility(View.GONE);
		ivBackToTop.setVisibility(View.GONE);
		// ivBackToTop.setVisibility(View.VISIBLE);
		ivShoppingCart.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * Describe:隐藏刷新的视图
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 */
	private void hideRefreshView() {
		if (isRefresh) {
			if (type_change.getTag() == ViewType.LIST) {
				/**
				 * 列表刷新
				 */
				listViewParent.listPullToRefreshView.onHeaderRefreshComplete();
			} else {
				/**
				 * 宫格刷新
				 */
				gridViewParent.gridPullToRefreshView.onHeaderRefreshComplete();
			}
			isRefresh = false;
		}

	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		gridViewParent.setRefreshOrLoadListener(this);
		listViewParent.setRefreshOrLoadListener(this);
		ivTitleRight.setOnClickListener(this);

		iv_hind.setOnClickListener(this);
		iv_hind_two.setOnClickListener(this);
		rlDefault.setOnClickListener(this);
		rlSale.setOnClickListener(this);
		rlPriceParent.setOnClickListener(this);
		rlScreenParent.setOnClickListener(this);
		linear_left.setOnClickListener(this);
		tv_pay.setOnClickListener(this);
		linear_hind_two.setOnClickListener(this);

		ivBackToTop.setOnClickListener(this);
		ivShoppingCart.setOnClickListener(this);
		ivType.setOnClickListener(this);
		type_change.setOnClickListener(this);
		etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					/**
					 * 搜索事件
					 */
					if ("1".equals(recommend)) {
						MobclickAgent.onEvent(baseActivity, "C_PROM_LST_1");
					} else {
						if ("1".equals(type))
							MobclickAgent.onEvent(baseActivity, "C_SAL_LST_1");
						else
							MobclickAgent.onEvent(baseActivity, "C_PD_LST_1");
					}
					searchContent = etInput.getText().toString();
					if (StringUtil.isEmpty(searchContent)) {
						// ToastUtil.showToast(baseActivity, "请输入搜索内容");
						searchContent = "";
					}
					getData();
				}
				return false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(GroupWholeSaleActivity.this,
						NewProductDetailActivity.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						groupBuyProductList.get(position).getId());
				intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY,
						groupBuyProductList.get(position).getType());
				StartActivityUtil.startActivity(GroupWholeSaleActivity.this,
						intent);
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(GroupWholeSaleActivity.this,
						NewProductDetailActivity.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						groupBuyProductList.get(position).getId());
				intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY,
						groupBuyProductList.get(position).getType());
				StartActivityUtil.startActivity(GroupWholeSaleActivity.this,
						intent);

			}
		});
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "云批专区";
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
		// return R.drawable.show_type_grid;
		return 0;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		HashMap<String, String> map = new HashMap<String, String>();
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.base_activity_title_right_righticon:
			map.put("bigimage", "");
			MobclickAgent.onEvent(baseActivity, "0036", map);
			/**
			 * 列表视图类型切换事件
			 */
			ViewTypeChange();

			break;
		case R.id.group_type_change:
			/**
			 * 列表视图类型切换事件
			 */
			ViewTypeChange();

			break;
		case R.id.group_title_search_iv:
			/**
			 * 列表视图类型切换事件
			 */
			ViewTypeChange();

			break;
		/*
		 * 默认选项
		 */
		case R.id.group_buy_list_default:
			if ("1".equals(recommend)) {
				MobclickAgent.onEvent(baseActivity, "C_PROM_LST_4");
			} else {
				if ("1".equals(type))
					MobclickAgent.onEvent(baseActivity, "C_SAL_LST_4");
				else
					MobclickAgent.onEvent(baseActivity, "C_PD_LST_4");
			}
			initTabView();
			initReuquestParams();
			setcheck(R.id.group_buy_list_default);
			if (asc == 0) {
				asc = 1;
			} else {
				asc = 0;
			}
			if (!order.equals("")) {
				asc = 1;
			}
			order = "";
			setbg(rlDefault, tvDefalut);
			getData();
			break;
		/**
		 * 当前选中类型非价格排序
		 */
		case R.id.group_buy_list_price:
			if ("1".equals(recommend)) {
				MobclickAgent.onEvent(baseActivity, "C_PROM_LST_2");
			} else {
				if ("1".equals(type))
					MobclickAgent.onEvent(baseActivity, "C_SAL_LST_2");
				else
					MobclickAgent.onEvent(baseActivity, "C_PD_LST_2");
			}
			map.put("price", "");
			MobclickAgent.onEvent(baseActivity, "0033", map);
			initTabView();
			initReuquestParams();
			setcheck(R.id.group_buy_list_price);
			if (asc == 0) {
				asc = 1;
			} else {
				asc = 0;
			}
			if (!order.equals("price")) {
				asc = 1;
				ivTabPriceArrow.setImageResource(R.drawable.jiagtoubaise);
				isDownprice = true;
			}
			if (isDownprice) {
				isDownprice = false;
				ivTabPriceArrow.setImageResource(R.drawable.jiagtoubaise);
			} else {
				isDownprice = true;
				ivTabPriceArrow
						.setImageResource(R.drawable.xiangxiajiantoubaise);
			}
			order = "price";
			setbg(rlPriceParent, tvPrice);
			getData();
			break;
		// 销量
		case R.id.group_buy_list_saleNum:
			if ("1".equals(recommend)) {
				MobclickAgent.onEvent(baseActivity, "C_PROM_LST_3");
			} else {
				if ("1".equals(type))
					MobclickAgent.onEvent(baseActivity, "C_SAL_LST_3");
				else
					MobclickAgent.onEvent(baseActivity, "C_PD_LST_3");
			}
			initTabView();
			initReuquestParams();
			if (asc == 0) {
				asc = 1;// 从小到大
			} else {
				asc = 0;
			}
			if (!order.equals("sales")) {
				asc = 1;
				ivsaleArrow.setImageResource(R.drawable.jiagtoubaise);
				isDownSale = true;
			}
			if (isDownSale) {
				isDownSale = false;
				ivsaleArrow.setImageResource(R.drawable.jiagtoubaise);
			} else {
				isDownSale = true;
				ivsaleArrow.setImageResource(R.drawable.xiangxiajiantoubaise);
			}
			setcheck(R.id.group_buy_list_saleNum);

			order = "sales";
			setbg(rlSale, tvSale);
			getData();
			break;

		/*
		 * 筛选条件
		 */
		case R.id.group_buy_list_screen:
			initTabView();
			order = "";
			asc = 1;
			Intent intents = new Intent(this, SelectPicPopupWindow.class);
			intents.putExtra("recommend", recommend);
			intents.putExtra("type", type);
			intents.putExtra("place", place);
			intents.putExtra("issx", issx);// 筛选条件
			startActivityForResult(intents, ConstantsUtil.ACTIVITY_BACK);
			setbg(rlScreenParent, tvFilter);
			ivfilterArrow.setImageResource(R.drawable.baisanjiao);
			break;
		case R.id.group_buy_list_back_totop:
			/**
			 * 返回到顶部
			 */
			if (type_change.getTag() == ViewType.LIST) {
				/**
				 * 当前是列表视图
				 */
				listView.requestFocusFromTouch();
				listView.setSelection(0);
			} else {
				/**
				 * 当前是网格视图
				 */
				gridView.requestFocusFromTouch();
				gridView.setSelection(0);
			}

			break;

		case R.id.group_buy_list_shopping_cart:
			/**
			 * 进入购物车
			 */
             boolean islogin = IsLogin.isLogin(baseActivity);
			if(islogin){
//			int num = ActivityManagerUtil.getActivityManager().getActivityNum(
//					MainActivity.class);
//			intent.setClass(this, MainActivity.class);
//			intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
//					ConstantsUtil.MAIN_TAB_SHOP_CAR);
//			intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
//					ConstantsUtil.SHOPPING_CART_SHOW_BACK);
//			if (num > 1) {
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			}
            intent.setClass(baseActivity,ShoppingCartActivity.class);
			StartActivityUtil.startActivityForResult(baseActivity, intent,
					ConstantsUtil.REQUEST_CODE);
			}else{
				Utils.StartLogin(baseActivity,false);
			}
			break;
		case R.id.base_activity_title_backicon:
			map.put("back", "");
			MobclickAgent.onEvent(baseActivity, "0037", map);
			if (isPush) {
				Intent i = new Intent(baseContext, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				onBackPressed();
			}
			break;
		case R.id.iv_hind:
			// 隐藏左侧品牌筛选
			ShowLeftView();
			break;
		case R.id.iv_hind_two:
			hindLeftView();
			break;
		case R.id.linear_hind_two:
			hindLeftView();
			break;
		case R.id.linear_left:
			ShowLeftView();
			break;
		case R.id.tv_pay:
			// 快捷结算
			tv_pay.setEnabled(false);
			if (selectProductNotInEdit != null
					&& selectProductNotInEdit.size() > 0)
				Preprview(selectProductNotInEdit);
			break;
		case R.id.linear_close:
			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
				popupWindow = null;
			}
			break;
		default:
			break;
		}
	}

//	private void SetAnimation(String url) {
//		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
//		tv_add.getLocationInWindow(start_location);// 这是获取购买文本框的在屏幕的X、Y坐标（这也是动画开始的坐标）
//		ImageView buyImg = new ImageView(this);
//		MyApplication.imageLoader.displayImage(url, buyImg,
//				MyApplication.options_headcircle);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 60);
//		buyImg.setLayoutParams(lp);
//		buyImg.setImageResource(R.drawable.ic_launcher);
//		AnimationCartUtil.setViewAnim(this, ivShoppingCart, buyImg,
//				start_location, frame_layout);
//	}

	// 生成预览订单
	private void Preprview(List<ShoppingCartSelectBean> selectProductNotInEdit) {
		selectProductNotInEditToNext = selectProductNotInEdit;
		ApiHttpCilent.getInstance(baseActivity).CreatPaymentOrder(baseActivity,
				null, "1", null, -1, selectProductNotInEdit, "1", null,
				new MyShoppingCallBack());
	}

	// 预览订单返回数据
	class MyShoppingCallBack extends
			BaseJsonHttpResponseHandler<NewOrderBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewOrderBaseBean arg4) {
			Dimessis();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewOrderBaseBean arg3) {
			Dimessis();
		}

		@Override
		protected NewOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimessis();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<NewOrderBaseBean>() {
			}.getType();
			beanorder = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(beanorder.getStatus())) {// 正常
				message.what = ConstantsUtil.ALI_BACK_CODE;
				message.obj = beanorder.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(beanorder.getError()
						.getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
					message.obj = beanorder.getError();
				} else {
					message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 错误
					message.obj = beanorder.getError();
				}
			}
			messageHandler.sendMessage(message);
			return beanorder;
		}
	}

	private void setcheck(int tv_value) {
		initCheck();
		switch (tv_value) {
		case R.id.group_buy_list_default:
			isdefault = true;
			num = 1;
			break;
		case R.id.group_buy_list_price:
			isprice = true;
			num = 2;
			break;
		case R.id.group_buy_list_saleNum:
			issale = true;
			num = 3;
			break;
		case R.id.group_buy_list_screen:
			isselect = true;
			num = 4;
			break;
		default:
			break;
		}
	}

	// 显示左边品类筛选
	private void ShowLeftView() {
		TranslateAnimation anim_out = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, (float) 0.0,
				Animation.RELATIVE_TO_SELF, (float) -1,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);// 从右往左移
		anim_out.setInterpolator(new LinearInterpolator());
		anim_out.setDuration(300);
		anim_out.setFillAfter(true);
		linear_brands.setAnimation(anim_out);
		linear_brands.setVisibility(View.GONE);
		iv_hind.setVisibility(View.GONE);
		iv_hind_two.setVisibility(View.VISIBLE);
	}

	// 隐藏左边品类筛选
	private void hindLeftView() {
		iv_hind.setVisibility(View.VISIBLE);
		iv_hind_two.setVisibility(View.GONE);
		TranslateAnimation anim_in = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, (float) -1,
				Animation.RELATIVE_TO_SELF, (float) 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);// 从右往左移
		anim_in.setInterpolator(new LinearInterpolator());
		anim_in.setDuration(300);
		anim_in.setFillAfter(true);
		linear_brands.setAnimation(anim_in);
		linear_brands.setVisibility(View.VISIBLE);
		iv_hind_two.setVisibility(View.GONE);
		iv_hind.setVisibility(View.VISIBLE);
	}

	// 初始化所有数据不要选择
	private void initCheck() {
		isdefault = false;
		isprice = false;
		issale = false;
		isselect = false;
	}

	/*
	 * 初始化产地数据
	 */

	private void ViewTypeChange() {
		if (groupBuyData != null) {
			isRefresh = true;
			isLoadMore = true;
			if (type_change.getTag() == ViewType.LIST) {
				/**
				 * 当前是列表格式
				 */
				if ("1".equals(recommend)) {
					MobclickAgent.onEvent(baseActivity, "C_PROM_LST_5");
				} else {
					if ("1".equals(type))
						MobclickAgent.onEvent(baseActivity, "C_SAL_LST_5");
					else
						MobclickAgent.onEvent(baseActivity, "C_PD_LST_5");
				}
				if ("0".equals(type)) {
					if (listAdapter != null)
						listAdapter.startCountDownTime();
					if (gridAdapter != null)
						gridAdapter.startCountDownTime();
				}
				listViewParent.setVisibility(View.GONE);
				gridViewParent.setVisibility(View.VISIBLE);
				if (gridAdapter != null && gridView != null)
					gridView.setAdapter(gridAdapter);
				if (gridAdapter != null) {
					gridAdapter.notifyDataSetChanged();
				}
				type_change.setTag(ViewType.GRID);
				ivType.setImageResource(R.drawable.show_type_grid);
			} else {
				/**
				 * 当前是宫格格式
				 */
				if ("1".equals(recommend)) {
					MobclickAgent.onEvent(baseActivity, "C_PROM_LST_6");
				} else {
					if ("1".equals(type))
						MobclickAgent.onEvent(baseActivity, "C_SAL_LST_6");
					else
						MobclickAgent.onEvent(baseActivity, "C_PD_LST_6");
				}
				listViewParent.setVisibility(View.VISIBLE);
				gridViewParent.setVisibility(View.GONE);
				if (listView != null && listAdapter != null)
					listView.setAdapter(listAdapter);
				if (listAdapter != null)
					listAdapter.notifyDataSetChanged();
				// listAdapter.startCountDownTime();
				type_change.setTag(ViewType.LIST);
				ivType.setImageResource(R.drawable.show_type_list);
			}
		} else {
			ToastUtil.showToast(this, "请求失败，请稍后重试");
		}
	}

	// 返回键处理
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isPush) {
				Intent i = new Intent(baseContext, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
						ConstantsUtil.SHOPPING_CART_SHOW_BACK);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				onBackPressed();
			}
		}
		return false;
	}

	/**
	 * 
	 * Describe:显示品牌选择视图
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 */
	public PopupWindow showBrandWindow(Activity activity, View position,
			int layoutId) {
		View mPopMenuView = null;
		mPopMenuView = LayoutInflater.from(activity).inflate(layoutId, null);
		layout = (LinearLayout) mPopMenuView
				.findViewById(R.id.groupbuy_list_brand_parent);
		brandGvGridView = (GridView) mPopMenuView
				.findViewById(R.id.groupbuy_list_brand_gv);
		relative_brand = (RelativeLayout) mPopMenuView
				.findViewById(R.id.relative_brand);
		relative_brand.setVisibility(View.GONE);
		bindBrandData();
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
			}
		});
		mPopupWindow = new PopupWindow(mPopMenuView,
				ViewUtil.getScreenWith(activity), LayoutParams.WRAP_CONTENT,
				false);
		mPopupWindow
				.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mPopupWindow.setFocusable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(false);
		mPopupWindow.showAsDropDown(position,
				ViewUtil.getScreenWith( activity), 0);
		// mPopMenuView.getBackground().setAlpha(230);
		layout.getBackground().setAlpha(230);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
		mPopupWindow.update();
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				ivTabPriceArrow
						.setImageResource(R.drawable.groupbuy_arrow_down_gray);
				ivTabPriceArrow.setTag("DOWN");
			}
		});
		return mPopupWindow;
	}

	/**
	 * 
	 * Describe:绑定品牌数据
	 * 
	 * Date:2015-10-15
	 * 
	 * Author:liuzhouliang
	 */
	private void bindBrandData() {
		if (groupBuyData != null) {

			brandAdapter = new GroupBuyBrandAdapter(brandGvGridView,
					brandLists, baseActivity, GroupWholeSaleActivity.this);
			brandGvGridView.setAdapter(brandAdapter);
			brandAdapter.setCheckListener(GroupWholeSaleActivity.this);
		}

	}

	/**
	 * 
	 * Describe:初始化TAB视图背景
	 * 
	 * Date:2015-10-10
	 * 
	 * Author:liuzhouliang
	 */
	private void initTabView() {

		ivTabPriceArrow.setImageResource(R.drawable.arrow_down_gray);
		ivfilterArrow.setImageResource(R.drawable.heisanjiao);
		ivsaleArrow.setImageResource(R.drawable.arrow_down_gray);

		tvSale.setTextColor(getResources().getColor(R.color.color_333333));
		tvDefalut.setTextColor(getResources().getColor(R.color.color_333333));
		tvPrice.setTextColor(getResources().getColor(R.color.color_333333));
		tvFilter.setTextColor(getResources().getColor(R.color.color_333333));

		rlDefault.setBackgroundColor(getResources().getColor(R.color.white));
		rlSale.setBackgroundColor(getResources().getColor(R.color.white));
		rlScreenParent.setBackgroundColor(getResources()
				.getColor(R.color.white));
		rlPriceParent
				.setBackgroundColor(getResources().getColor(R.color.white));

	}

	// 设置当前点击视图背景颜色
	private void setbg(RelativeLayout re, TextView tv) {
		re.setBackgroundColor(getResources().getColor(R.color.color_cb7484));
		tv.setTextColor(getResources().getColor(R.color.white));
	}

	/**
	 * 宫格视图刷新回调
	 */
	@Override
	public void gridOnRefresh() {
		isRefresh = true;
		initReuquestParams();
		getData();
	}

	/**
	 * 宫格视图分页回调
	 */
	@Override
	public void gridonLoadMore() {
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getData();
	}

	/**
	 * 列表视图刷新回调
	 */
	@Override
	public void listOnRefresh() {
		isRefresh = true;
		initReuquestParams();
		getData();
	}

	/**
	 * 列表视图分页回调
	 */
	@Override
	public void listonLoadMore() {
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getData();
	}

	@Override
	public void checkCallBack(BrandList data) {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			windType = data.getId() + "";
			checkBrandId = data.getId();
			// ToastUtil.showToast(baseContext, data.getId() + "");
			// getNetData();
			getData();
		}
	}

	/**
	 * 
	 * Describe:下拉刷新初始化请求参数
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 */
	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	public void onResume() {
		super.onResume();
		if (StringUtil.isEmpty(recommend)) {
			if (type.equals("1")) {
				MobclickAgent.onPageStart("PG_PROD_LST");
			} else if (type.equals("0")) {
				MobclickAgent.onPageStart("PG_PD_LST");
			}
		} else {
			MobclickAgent.onPageStart("PG_PROM_LST");
		}
		MobclickAgent.onResume(this); // 统计时长
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<BaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimessis();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Dimessis();
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimessis();
			Gson gson = new Gson();
			BaseBean loginBean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(loginBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_HEHEPAY;
				message.obj = loginBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = loginBean.getError().getInfo();
			}
			messageHandler.sendMessage(message);

			return loginBean;
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		back = true;
		// if(alertdialog != null && alertdialog.isShowing())
		// alertdialog.dismiss();
		if (mDialogCoustom.builders != null
				&& mDialogCoustom.builders.isShowing())
			mDialogCoustom.builders.dismiss();
//		getData();
		getNetData();
	}

	public void onPause() {
		super.onPause();
		if (StringUtil.isEmpty(recommend)) {
			if (type.equals("1")) {
				MobclickAgent.onPageStart("PG_PROD_LST");
			} else if (type.equals("0")) {
				MobclickAgent.onPageEnd("PG_PD_LST");
			}
		} else {
			MobclickAgent.onPageEnd("PG_PROM_LST");
		}
		MobclickAgent.onPause(this);
	}

	private void initView(View mpopuView) {
		currentCityParent = (RelativeLayout) mpopuView
				.findViewById(R.id.destance_content);
		sideView = (SideView) mpopuView
				.findViewById(R.id.show_city_fragment_sideview);
		tvToast = (TextView) mpopuView
				.findViewById(R.id.show_city_fragment_taost);
		sideView.setTextView(tvToast);
		mListView = (ListView) mpopuView
				.findViewById(R.id.show_city_fragment_lv);
		sideView.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = cityadapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					// LISTVIEW滚动到指定item
					mListView.setSelection(position);
				}
			}
		});
	}

	private void initCurrentData() {
		pinyinComparator = new PinyinComparator();
		characterParser = CharacterParser.getInstance();
		sourceDataList = changeSourceData(cityList);
		// 根据a-z进行排序源数据
		Collections.sort(sourceDataList, pinyinComparator);
		cityadapter = new DestanceAreaAdapter(this, sourceDataList,
				messageHandler);
		// mListView.addHeaderView(headView);
		mListView.setAdapter(cityadapter);

	}

	private List<AddressModel> changeSourceData(List<String> data) {
		List<AddressModel> mSortList = new ArrayList<AddressModel>();

		for (int i = 0; i < data.size(); i++) {
			AddressModel sortModel = new AddressModel();
			sortModel.setName(data.get(i));
			// 地址汉字转换成拼音
			String pinyin = characterParser.getSelling(data.get(i));
			String abbreviation = getAbbreviation(data.get(i));
			sortModel.setAbbreviation(abbreviation);
			// 截取拼音首位将字母转换成大写
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 
	 * Describe:获取缩写名
	 * 
	 * Date:2015-9-22
	 * 
	 * Author:liuzhouliang
	 */
	private String getAbbreviation(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		if (string != null) {
			char[] singleName = string.toCharArray();
			for (char ch : singleName) {
				String pinyin = characterParser.getSelling(ch + "");
				if (pinyin != null) {
					sb.append(pinyin.charAt(0));
				}
			}
		} else {
			return "";
		}
		return sb.toString();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ConstantsUtil.ACTIVITY_BACK:
				place = data.getStringExtra("areaId");// 回调产区ID
				if (!"1".equals(type)) {
					windType = data.getStringExtra("brand_id");// 回调品牌ID
				}
				initReuquestParams();
				getData();
				break;
			case ConstantsUtil.REQUEST_CODE:
				if (data != null) {
					// startIndex = data.getIntExtra("startIndex", 1);
					// endIndex = data.getIntExtra("endIndex", 10);
				}
				break;
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing()) {
			ApiHttpCilent.loading.dismiss();
			ApiHttpCilent.loading = null;
		}
		// if(alertdialog != null && alertdialog.isShowing())
		// alertdialog.dismiss();
		if (mDialogCoustom.builders != null)
			mDialogCoustom.builders.dismiss();
		finish();
		super.onDestroy();
	}
}
