package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.MoreSuitListAdapter.AddShopingCallback;
import com.heheys.ec.model.adapter.WholeSaleListAdapter;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.MoreSuitBean;
import com.heheys.ec.model.dataBean.MoreSuitBean.SuitList;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder.shoppingbean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderList;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.model.dataBean.ShopDetaileBaseBean;
import com.heheys.ec.model.dataBean.ShopDetaileBaseBean.ShopAdverBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.WholeBaseBean;
import com.heheys.ec.model.dataBean.WholeBaseBean.WholeListBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ActivityManagerUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.UpdateOrNot;
import com.heheys.ec.view.RefreshOrLoadMoreListViewParent;
import com.heheys.ec.view.RefreshOrLoadMoreListViewParent.ListPullOrRefreshListener;
import com.heheys.ec.view.ViewPagerLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-4-5 下午5:29:47 类说明
 */
public class ShopListDetailActivity extends BaseActivity implements
		ListPullOrRefreshListener, OnClickListener {
	public final static String[] imageUrls = new String[] { "null" };
	private ViewPagerLayout adverLayout;
	private String shopid;
	private ShopDetaileBaseBean adverBean;
	private AdverHandler mhandler = new AdverHandler(this);
	private ImageView iv_logo;
	private TextView tv_shopname;
	private TextView tv_isrz;
	private String pname = "";// 检索条件
	private String order = "";// 筛选条件
	private String asc = "1";// 0倒序 1正序
	private boolean mIsListViewidle = true;
	private Context mContext;
	private String recommend;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	private ListView mListView;
	private RefreshOrLoadMoreListViewParent listViewParent;
	private WholeBaseBean groupBuyData;
	public static List<ShoppingCartSelectBean> selectProductNotInEdit;
	private WholeSaleListAdapter mAdapter;
	private List<WholeListBean> groupBuyProductList;
	private TextView tv_default;
	private TextView tv_price;
	private TextView tv_sale;
	private RelativeLayout rlsale;
	private RelativeLayout rlprice;
	private RelativeLayout rldefault;
	private RelativeLayout bottomParent;
	private ImageView ivprice;
	private ImageView ivsale;
	private EditText etInput;
	private ImageView ivShoppingCart;
	private Button shoppingcare_num;
	private TextView tv_total_price;
	private NewShoppingCartInforBean shoppingCartData;
	private OrderList bean;
	private AlertDialogCustom mDialogCoustom;
	private List<ShoppingCartSelectBean> selectProductNotInEditToNext;// 存储当前提交成功的商品列表
	private MoreSuitBean suitlistbean;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.shop_detaile);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		shopid = intent.getStringExtra("shopid");
		selectProductNotInEdit = new ArrayList<ShoppingCartSelectBean>();
		mDialogCoustom = new AlertDialogCustom();
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		mContext = ShopListDetailActivity.this;
		adverLayout = (ViewPagerLayout) findViewById(R.id.fragment_home_adver);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		tv_shopname = (TextView) findViewById(R.id.tv_shopname);
		tv_pay = (TextView) findViewById(R.id.tv_pay);
		tv_default = (TextView) findViewById(R.id.group_buy_list_sort_default);
		tv_price = (TextView) findViewById(R.id.group_buy_list_price_tv);
		tv_sale = (TextView) findViewById(R.id.group_buy_list_sale_sort);
		ivprice = (ImageView) findViewById(R.id.group_buy_list_arrow);
		ivsale = (ImageView) findViewById(R.id.group_buy_list_arrow_sale);
		rlsale = (RelativeLayout) findViewById(R.id.group_buy_list_saleNum);
		rlprice = (RelativeLayout) findViewById(R.id.group_buy_list_price);
		rldefault = (RelativeLayout) findViewById(R.id.group_buy_list_default);
		bottomParent = (RelativeLayout) findViewById(R.id.bottomParent);
		etInput = (EditText) findViewById(R.id.fragment_home_title_search_et);
		ivShoppingCart = (ImageView) findViewById(R.id.group_buy_list_shopping_cart);
		listViewParent = (RefreshOrLoadMoreListViewParent) findViewById(R.id.group_buy_list_lv);
		mListView = listViewParent.getListView();

		tv_total_price = (TextView) findViewById(R.id.tv_total_price);
		shoppingcare_num = (Button) findViewById(R.id.shoppingcare_num);

		tv_isrz = (TextView) findViewById(R.id.tv_isrz);
		initData();
		ApiHttpCilent.getInstance(getApplicationContext()).ShoppingDetaile(baseActivity,
				shopid, new AdverRequestCallBack());
		int height = ViewUtil.getHeight(bottomParent);
		LinearLayout llLayout = new LinearLayout(baseContext);
		View childView = new View(baseContext);
		childView.setBackgroundColor(getResources().getColor(
				R.color.transparent));

		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, height);
		llLayout.addView(childView, llParams);
		llLayout.setOnClickListener(null);
		llLayout.setEnabled(false);
		mListView.addFooterView(llLayout);
	}

	private String DoPrice(float ft) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String fts = fnum.format(ft);
		return fts;
	}

	public class AdverHandler extends WeakHandler<ShopListDetailActivity> {

		private String aidThis;
		private String urlThis;
		private int positionThis;

		public AdverHandler(ShopListDetailActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取更新后的价格和数量
			case 1000:
				Bundle dataBundle = msg.getData();
				long[] num = dataBundle.getLongArray("totalNum");
				float productPrice = dataBundle.getFloat("totalPrice");
				ViewUtil.setActivityPrice(getReference().tv_total_price,
						getReference().DoPrice(productPrice) + "");
				if (num[0] > 0) {
					// shoppingcare_num.setText(num[0] + "");
					shoppingcare_num.setVisibility(View.VISIBLE);
					ivShoppingCart.setBackgroundResource(R.drawable.have_goods);
					tv_pay.setBackgroundColor(baseActivity.getResources()
							.getColor(R.color.color_ef5150));
					tv_pay.setEnabled(true);
				} else {
					shoppingcare_num.setVisibility(View.INVISIBLE);
					ivShoppingCart.setBackgroundResource(R.drawable.no_goods);
					tv_pay.setBackgroundColor(baseActivity.getResources()
							.getColor(R.color.color_888888));
					tv_pay.setEnabled(false);
				}
				ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(
						baseActivity, new ShoppingCartInforRequestCallBack());
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
				ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(
						baseActivity, new ShoppingCartInforRequestCallBack());
				break;
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				getReference().bindListData();
				break;
			case ConstantsUtil.ALI_BACK_CODE:
				// 生成预览订单成功
				getReference().bean = (OrderList) msg.obj;
				getReference().ToOrderPre(getReference().bean);
				break;
			case ConstantsUtil.HTTP_FAILE:
				String error = (String) msg.obj;
				ToastUtil.showToast(baseActivity, error);
				if (getReference().isRefresh) {
					getReference().hideRefreshView();
				}
				if (getReference().isLoadMore) {
					getReference().hideLoadMoreView();
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				// 获取基本信息成功
				saveVerdata((ResultBean) msg.obj);// 保存认证信息
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				// 获取购车车信息成功
				getReference().ShoppingCartSuccess();
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN:
				// 提交预览订单失败
				ErrorBeanorder ErrorBeanorder = (ErrorBeanorder) msg.obj;
				getReference().sortListByBack(ErrorBeanorder);
				break;
			default:
				break;
			}
		}
	}

	/*
	 * aid 当前点击数据aid **弹出框 获取优惠套装列表
	 */
	public void showPopUp(View v, final String url, final String aid,
			final int position) {
		View populayout = getLayoutInflater().inflate(R.layout.suit_list, null);
		int screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();// 获取屏幕高度
		popupWindow = new PopupWindow(populayout,
		// LinearLayout.LayoutParams.MATCH_PARENT, screenHeight );
				LinearLayout.LayoutParams.MATCH_PARENT, screenHeight * 4 / 5);
		popupWindow.setFocusable(false);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.pop_buttom_anim_style);
		LinearLayout linear_close = (LinearLayout) populayout
				.findViewById(R.id.linear_close);
		ListView lv_listsuit = (ListView) populayout
				.findViewById(R.id.lv_listsuit);
		FrameLayout frame_layout = (FrameLayout) populayout
				.findViewById(R.id.frame_layout);

		View mHeaderView = getLayoutInflater().inflate(R.layout.suit_head_view,
				null);
		RelativeLayout relative_produce = (RelativeLayout) mHeaderView
				.findViewById(R.id.relative_produce);
		TextView tv_price = (TextView) mHeaderView.findViewById(R.id.tv_price);
		TextView tv_add = (TextView) mHeaderView.findViewById(R.id.tv_add);
		TextView tv_unit = (TextView) mHeaderView.findViewById(R.id.tv_unit);
		TextView tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
		final ImageView iv_pic = (ImageView) mHeaderView
				.findViewById(R.id.iv_pic);
		lv_listsuit.addHeaderView(mHeaderView);
		if (groupBuyProductList != null) {
			MyApplication.imageLoader.displayImage(
					groupBuyProductList.get(position).getPic(), iv_pic,
					MyApplication.options);
			tv_name.setText(groupBuyProductList.get(position).getName());
			tv_price.setText(groupBuyProductList.get(position).getCurrency() == null ? "¥"
					: groupBuyProductList.get(position).getCurrency() + "/"
							+ groupBuyProductList.get(position).getCprice());
			tv_unit.setText(groupBuyProductList.get(position).getUnit());
		}

		linear_close.setOnClickListener(this);
		relative_produce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAdapter != null) {
					mAdapter.ToDetaile(position,
							groupBuyProductList.get(position));
				}
			}
		});
		tv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAdapter != null) {
					mAdapter.AddToShoppcart(position, "add", true);
					// SetAnimation(url);
					// ImageView buyImg = new ImageView(
					// GroupWholeSaleActivity.this);
					// LinearLayout.LayoutParams lp = new
					// LinearLayout.LayoutParams(
					// 60, 60);
					// buyImg.setLayoutParams(lp);
					// buyImg.setImageResource(R.drawable.ic_launcher);
					// addShopCarAnimation(tv_add, ivShoppingCart, buyImg);
				}
			}
		});
		iv_pic.setOnClickListener(this);
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
				(location[0] + v.getWidth() / 2) - popupWindow.getWidth() / 2,
				location[1] - popupWindow.getHeight());
		// Gson gson = new Gson();
		// MoreSuitBean moreSuitBean =
		// gson.fromJson(Utils.ReadJson(this,"xc.txt"),
		// MoreSuitBean.class);

		if (suitlistbean != null && suitlistbean.getResult() != null) {
			ArrayList<SuitList> dataSuitList = suitlistbean.getResult()
					.getSuit();
			//暂时注销
//			MoreSuitListAdapter suitAdapter = new MoreSuitListAdapter(
//					dataSuitList, this, new ShopSuitback());
//			lv_listsuit.setAdapter(suitAdapter);
		}
	}

	// 添加购物车回调id
	class ShopSuitback implements AddShopingCallback {
		@Override
		public void getId(String id) {
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
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AddShoppingCartBean arg3) {
		}

		@Override
		protected AddShoppingCartBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			AddShoppingCartBean addShoppingCartData = gson.fromJson(response,
					AddShoppingCartBean.class);
			Message message = Message.obtain();
			if ("1".equals(addShoppingCartData.getStatus())) {// 正常
				message.what = 1003;
				// message.obj = addShoppingCartData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = addShoppingCartData.getError().getInfo();
			}
			mhandler.sendMessage(message);
			return addShoppingCartData;
		}

	}

	public void getMoreSuitList(String aid) {
		ApiHttpCilent.getInstance(getApplicationContext()).getSuitList(this, aid,
				new SuitListRequestCallBack());
	}

	public class SuitListRequestCallBack extends
			BaseJsonHttpResponseHandler<MoreSuitBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MoreSuitBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MoreSuitBean arg3) {
		}

		@Override
		protected MoreSuitBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimessis();
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
			mhandler.sendMessage(message);
			return suitlistbean;
		}
	}

	// 保存认证信息
	private void saveVerdata(ResultBean resultbean) {
		SharedPreferencesUtil.saveObject(mContext, resultbean, "resultbean");
	}

	// 获取 不符合要求的商品列表
	private void sortListByBack(ErrorBeanorder ErrorBeanorder) {
		if (ErrorBeanorder != null) {
			final List<shoppingbean> listshoppingbean = ErrorBeanorder
					.getList();
			int errorSize = listshoppingbean.size();
			if (listshoppingbean != null) {
				if (errorSize > 0) {
					selectProductNotInEditTemp = new ArrayList<ShoppingCartSelectBean>();
					selectProductNotInEditTemp.addAll(selectProductNotInEdit);
					// 继续提交
					for (shoppingbean a : listshoppingbean) {
						if (selectProductNotInEditTemp.contains(a)) {
							for (ShoppingCartSelectBean bean : selectProductNotInEditTemp) {
								if (bean.getAid().equals(a.getAid())) {
									selectProductNotInEditTemp.remove(bean);
									break;
								}
							}
						}
					}
					// 存在可以提交的商品 弹框
					if (selectProductNotInEditTemp != null
							&& selectProductNotInEditTemp.size() > 0) {
						mDialogCoustom
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
												mDialogCoustom.Demiss();
											}
										});
					} else {
						ToastUtil
								.showToast(baseActivity, "商品数量存在异常,请您重新确认后再结算");
					}
				} else {
					ToastUtil.showToast(baseActivity, StringUtil
							.isEmpty(ErrorBeanorder.getInfo()) ? "获取失败"
							: ErrorBeanorder.getInfo());
				}
			}
		}
	}

	// 遍历集合获取当前界面没有库存的数据
	// public void SortCartListNum(List<shoppingbean> listshoppingbean) {
	// // 购物车数据
	// if (groupBuyProductList != null && groupBuyProductList.size()>0) {
	// for (shoppingbean wholebean:listshoppingbean) {
	// for (WholeListBean shopcart : groupBuyProductList) {
	// if (wholebean.getAid().equals(wholebean.getAid())) {
	// // 购车商品在当前列表有数据
	// shopcart.setNumresult(wholebean.getMsg()+wholebean.getKcnum());
	// break;
	// }
	// }
	// }
	// }
	// mAdapter.notifyDataSetChanged();
	// }
	// 跳转到生成预览订单界面
	private void ToOrderPre(OrderList bean) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(baseContext, NewOrderDetailActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("selectProductNotInEditToNext",
				(Serializable) selectProductNotInEditToNext);
		StartActivityUtil.startActivity((Activity) baseContext, intent);
	}

	// 获取购物车数据成功
	private void ShoppingCartSuccess() {
		if (shoppingCartData != null && shoppingCartData.getResult() != null
				&& shoppingCartData.getResult().getList() != null) {
			if (shoppingCartData.getResult().getList().size() > 0) {
				if (shoppingCartData.getResult().getCount() > 0) {
					shoppingcare_num.setText(shoppingCartData.getResult()
							.getCount() + "");
					shoppingcare_num.setVisibility(View.VISIBLE);
				} else {
					shoppingcare_num.setVisibility(View.INVISIBLE);
				}
				if (!StringUtil.isEmpty(shoppingCartData.getResult()
						.getAmount()))
					ViewUtil.setActivityPrice(tv_total_price, shoppingCartData
							.getResult().getAmount());
				// 获取可以结算的商品集合
				selectProductNotInEdit = GroupWholeSaleActivity.getPayNext(
						selectProductNotInEdit, shoppingCartData);
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
				// 购物车数据为空
				if (selectProductNotInEdit != null)
					selectProductNotInEdit.clear();
				shoppingcare_num.setVisibility(View.INVISIBLE);
				ivShoppingCart.setBackgroundResource(R.drawable.no_goods);
				ViewUtil.setActivityPrice(tv_total_price, DoPrice(0.00F) + "");
				tv_pay.setBackgroundColor(baseActivity.getResources().getColor(
						R.color.color_888888));
				tv_pay.setEnabled(false);
			}
		} else {
			// 购物车数据为空
			if (selectProductNotInEdit != null)
				selectProductNotInEdit.clear();
			shoppingcare_num.setVisibility(View.INVISIBLE);
			ivShoppingCart.setBackgroundResource(R.drawable.no_goods);
			ViewUtil.setActivityPrice(tv_total_price, DoPrice(0.00F) + "");
			tv_pay.setBackgroundColor(baseActivity.getResources().getColor(
					R.color.color_888888));
			tv_pay.setEnabled(false);
		}
		/**
		 * 
		 * 首先获取购车数据 在获取商品烈表
		 * */
		getData();
	}

	private void getData() {
		// 获取当前列表数据
		ApiHttpCilent.getInstance(getApplicationContext()).ShoppingDetaileList(
				baseActivity, shopid, pname, order, asc, startIndex, endIndex,
				new RequestCallBack());
	}

	private void hideLoadMoreView() {
		/**
		 * 列表分页
		 */
		listViewParent.listPullToRefreshView.onFooterRefreshComplete();
		mAdapter.setNewData(groupBuyProductList);
		isLoadMore = false;
		listViewParent.setVisibility(View.VISIBLE);

	}

	private void hideRefreshView() {
		if (isRefresh) {
			/**
			 * 列表刷新
			 */
			listViewParent.listPullToRefreshView.onHeaderRefreshComplete();
			isRefresh = false;
		}
	}

	// 排列 遍历购物车商品在当前列表是否有购买过
	public void SortCartList(List<WholeListBean> pageData) {
		// 购物车数据
		if (shoppingCartData != null && shoppingCartData.getResult() != null
				&& shoppingCartData.getResult().getList() != null) {
			List<NewProductInfo> listcart = shoppingCartData.getResult()
					.getList();
			if (listcart.size() > 0) {
				for (NewProductInfo shopcart : listcart) {
					for (WholeListBean wholebean : pageData) {
						if (shopcart.getAid().equals(wholebean.getId())) {
							// 购车商品在当前列表有数据
							wholebean.setCurrentnum(Integer.parseInt(StringUtil
									.isEmpty(shopcart.getNum()) ? "0"
									: shopcart.getNum()));
							break;
						}
					}
				}
				// 列表数据
			}
		}
	}

	/**
	 * 绑定商品列表数据
	 * */
	public void bindListData() {
		if (1 == startIndex) {
			/**
			 * 初始化的数据
			 */
			hideRefreshView();
			if (groupBuyData != null && groupBuyData.getResult() != null
					&& groupBuyData.getResult().getList() != null
					&& groupBuyData.getResult().getList().size() > 0) {
				groupBuyProductList = groupBuyData.getResult().getList();
				SortCartList(groupBuyProductList);
				mAdapter = new WholeSaleListAdapter(ivShoppingCart,
						mIsListViewidle, groupBuyProductList, baseContext,
						recommend, null, mhandler, shoppingcare_num,
						selectProductNotInEdit, shoppingCartData, mListView);
				/**
				 * 列表视图
				 */
				mListView.setAdapter(mAdapter);
				// // 启动倒计时
				// if("0".equals(groupBuyProductList.ge))
				mAdapter.startCountDownTime();
				listViewParent.setVisibility(View.VISIBLE);
				viewNoGroupBuy.setVisibility(View.GONE);
			} else {
				/**
				 * 初始化数据为空================
				 */
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
				List<WholeListBean> pageData = groupBuyData.getResult()
						.getList();
				if (groupBuyProductList != null) {
					if (isLoadMore) {
						groupBuyProductList.addAll(pageData);
						SortCartList(groupBuyProductList);
						hideLoadMoreView();
					}
				}
			} else {
				/**
				 * 分页数据不存在=================
				 */

				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
			}
		}
	}

	// 绑定广告页数据
	public void bindData() {
		/**
		 * 绑定数据
		 * */
		if (adverBean != null && adverBean.getResult() != null) {
			ShopAdverBean databean = adverBean.getResult();
			if (databean != null && databean.getPics() != null
					&& databean.getPics().size() > 0) {
				adverLayout.setImageResources(databean.getPics(),
						mAdCycleViewListener);
				adverLayout.startImageCycle();
				MyApplication.imageLoader.displayImage(databean.getLogo(),
						iv_logo, MyApplication.options);
				tv_shopname.setText(databean.getName());
				tv_isrz.setText(databean.getAuthen().equals("0") ? "未认证"
						: "已认证");
			}
		} else {
			adverLayout.setImageResources(Arrays.asList(imageUrls),
					mAdCycleViewListener);
			adverLayout.startImageCycle();
		}
	}

	/**
	 * 绑定广告图片数据
	 */
	private ViewPagerLayout.ImageCycleViewListener mAdCycleViewListener = new ViewPagerLayout.ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {
			// TODO 单击图片处理事件
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			MyApplication.imageLoader.displayImage(imageURL, imageView,
					MyApplication.options);
		}
	};
	private TextView tv_pay;
	private List<ShoppingCartSelectBean> selectProductNotInEditTemp;
	private PopupWindow popupWindow;

	private void Dimessis() {
		ShopListDetailActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		getShoppingcartData();
	}

	void getShoppingcartData() {
		// 获取购物车数据
		ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(
				baseActivity, new ShoppingCartInforRequestCallBack());

		// ApiHttpCilent.getInstance(this).GetInfo(this, new MyCallBack());
	}

	public class ShoppingCartInforRequestCallBack extends
			BaseJsonHttpResponseHandler<NewShoppingCartInforBean> {

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewShoppingCartInforBean arg4) {
			DimissLoding();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewShoppingCartInforBean arg3) {
			DimissLoding();
		}

		@Override
		protected NewShoppingCartInforBean parseResponse(String response,
				boolean arg1) throws Throwable {
			DimissLoding();
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
			mhandler.sendMessage(message);
			return shoppingCartData;
		}

		private void DimissLoding() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ApiHttpCilent.loading.dismiss();
				}
			});
		}
	}

	/*
	 * 列表回调
	 */
	public class RequestCallBack extends
			BaseJsonHttpResponseHandler<WholeBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, WholeBaseBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				WholeBaseBean arg3) {
		}

		@Override
		protected WholeBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			groupBuyData = gson.fromJson(response, WholeBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(groupBuyData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				message.obj = groupBuyData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = groupBuyData.getError().getInfo();
			}
			mhandler.sendMessage(message);
			return groupBuyData;
		}
	}

	/*
	 * 简介回调
	 */
	public class AdverRequestCallBack extends
			BaseJsonHttpResponseHandler<ShopDetaileBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ShopDetaileBaseBean arg4) {
			// ApiHttpCilent.hideLoading();
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mhandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ShopDetaileBaseBean arg3) {
		}

		@Override
		protected ShopDetaileBaseBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			adverBean = gson.fromJson(response, ShopDetaileBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(adverBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = adverBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = adverBean.getError().getInfo();
			}
			mhandler.sendMessage(message);
			return adverBean;
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		listViewParent.setRefreshOrLoadListener(this);
		rldefault.setOnClickListener(this);
		rlprice.setOnClickListener(this);
		rlsale.setOnClickListener(this);
		tv_pay.setOnClickListener(this);
		ivShoppingCart.setOnClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext,
						NewProductDetailActivity.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, groupBuyData
						.getResult().getList().get(position).getId());
				intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, groupBuyData
						.getResult().getList().get(position).getType());
				StartActivityUtil.startActivity((Activity) mContext, intent);
			}
		});
		etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					/**
					 * 搜索事件
					 */
					pname = etInput.getText().toString();
					if (StringUtil.isEmpty(pname)) {
						// ToastUtil.showToast(baseActivity, "请输入搜索内容");
						pname = "";
					}
					getNetData();
					MobclickAgent.onEvent(baseActivity, "C_SHP_INF_4");
				}
				return false;
			}
		});
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "品牌云店";
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

	private void initReuquestParams() {
		// timeSortType = 1;
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	@Override
	public void listOnRefresh() {
		// TODO Auto-generated method stub
		isRefresh = true;
		initReuquestParams();
		getNetData();
	}

	@Override
	public void listonLoadMore() {
		// TODO Auto-generated method stub
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getNetData();
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent intent = new Intent();
		initReuquestParams();
		switch (v.getId()) {
		case R.id.group_buy_list_price:// 价格
			MobclickAgent.onEvent(baseActivity, "C_SHP_INF_2");
			if (asc.equals("1")) {
				asc = "0";
				ivprice.setBackground(getResources().getDrawable(
						R.drawable.xiangxiajiantoubaise));
			} else {
				asc = "1";
				ivprice.setBackground(getResources().getDrawable(
						R.drawable.jiagtoubaise));
			}
			order = "price";
			initTv();
			setbg(rlprice, tv_price);
			break;
		case R.id.group_buy_list_default:// 默认
			MobclickAgent.onEvent(baseActivity, "C_SHP_INF_1");
			if (asc.equals("1"))
				asc = "0";
			else
				asc = "1";
			order = "";
			initTv();
			setbg(rldefault, tv_default);
			break;
		case R.id.group_buy_list_saleNum:// 销量
			MobclickAgent.onEvent(baseActivity, "C_SHP_INF_3");
			if (asc.equals("1")) {
				asc = "0";
				ivsale.setBackground(getResources().getDrawable(
						R.drawable.xiangxiajiantoubaise));
			} else {
				asc = "1";
				ivsale.setBackground(getResources().getDrawable(
						R.drawable.jiagtoubaise));
			}
			order = "sales";
			initTv();
			setbg(rlsale, tv_sale);
			break;
		case R.id.tv_pay:
			// 快捷结算
			Preprview(selectProductNotInEdit);
			break;
		case R.id.group_buy_list_shopping_cart:
			boolean isLogin = IsLogin.isLogin(this);
			if (isLogin) {
				int num = ActivityManagerUtil.getActivityManager()
						.getActivityNum(MainActivity.class);
				intent.setClass(this, MainActivity.class);
				intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
						ConstantsUtil.MAIN_TAB_SHOP_CAR);
				intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
						ConstantsUtil.SHOPPING_CART_SHOW_BACK);
				if (num > 1) {
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}
				StartActivityUtil.startActivity(baseActivity, intent);
			} else {
				Utils.StartLogin(baseActivity, false);
			}
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

	// 生成预览订单
	private void Preprview(List<ShoppingCartSelectBean> selectProductNotInEdit) {
		this.selectProductNotInEditToNext = selectProductNotInEdit;
		ApiHttpCilent.getInstance(getApplicationContext()).CreatPaymentOrder(baseActivity,
				"", "1", null, -1, selectProductNotInEdit, "1", null,
				new MyShoppingCallBack());
		// ApiHttpCilent.getInstance(baseActivity).CreatPaymentOrder(baseActivity,"","1",null,null,-1,selectProductNotInEdit,
		// "1",null,new MyShoppingCallBack());
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
			NewOrderBaseBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.ALI_BACK_CODE;
				message.obj = bean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
						.getCode())) {
					message.what = ConstantsUtil.HTTP_FAILE;
					message.obj = bean.getError().getInfo();
				} else {
					message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 错误
					message.obj = bean.getError();
				}
			}
			mhandler.sendMessage(message);
			return bean;
		}
	}

	private void initTv() {
		tv_default.setTextColor(getResources().getColor(R.color.color_333333));
		tv_price.setTextColor(getResources().getColor(R.color.color_333333));
		tv_sale.setTextColor(getResources().getColor(R.color.color_333333));
		rldefault.setBackgroundColor(getResources().getColor(R.color.white));
		rlprice.setBackgroundColor(getResources().getColor(R.color.white));
		rlsale.setBackgroundColor(getResources().getColor(R.color.white));
	}

	private void setbg(RelativeLayout re, TextView tv) {
		re.setBackgroundColor(getResources().getColor(R.color.color_cb7484));
		tv.setTextColor(getResources().getColor(R.color.white));
		getNetData();
	}

	// 云店详情页
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("PG_PROD_INFO");
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
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
				message.obj = loginBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = loginBean.getError().toString();
			}
			mhandler.sendMessage(message);

			return loginBean;
		}
	}

	// 返回刷新界面
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getNetData();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PG_PROD_INFO");
		MobclickAgent.onPause(this);
	}
}
