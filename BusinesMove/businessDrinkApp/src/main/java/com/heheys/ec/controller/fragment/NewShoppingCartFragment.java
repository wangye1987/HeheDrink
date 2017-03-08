/*package com.heheys.ec.controller.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.controller.activity.NewOrderDetailActivity;
import com.heheys.ec.controller.fragment.NewShoppingCartFragment.DeleteProductCallBack;
import com.heheys.ec.controller.fragment.NewShoppingCartFragment.DeleteProductMessageHandler;
import com.heheys.ec.controller.fragment.NewShoppingCartFragment.MyCallBack;
import com.heheys.ec.controller.fragment.NewShoppingCartFragment.MyOrderHandler;
import com.heheys.ec.controller.fragment.NewShoppingCartFragment.ShoppingCartInforRequestCallBack;
import com.heheys.ec.controller.fragment.NewShoppingCartFragment.ShoppingCartMessageHandler;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.ToastUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.NewNewShoppingCardAdapter;
import com.heheys.ec.model.adapter.NewShoppingCartFragmentAdapter;
import com.heheys.ec.model.dataBean.DeleteShoppingCartProduct;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.ShoppingCartListBean;
import com.heheys.ec.model.dataBean.ShoppingCartProductDeleteBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderList;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewShoppingCartInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

*//**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间
 *            ：2016-4-1 下午2:06:38 类说明 新购物车界面
 *//*
public class NewShoppingCartFragment extends BaseFragment implements
		OnCheckedChangeListener {
	private static final String TAG = NewShoppingCartFragment.class.getName();
	private ListView lvListView;
	private NewShoppingCartFragmentAdapter mAdapter;
	public Button btSettlement;
	public CheckBox cbSelectAllBox;
	private RelativeLayout rlBottom;
	private LinearLayout llTotalLayout;
	// 标记是否为编辑状态，默认为false，编辑状态为true
	public boolean isEditType;
	// 购物车商品数据
	private List<NewProductInfo> productListData;
	private NewShoppingCartInfor shoppingCartInfor;
	private TextView tvTotalPriceTextView;
	// 获取购物车信息消息
	private static ShoppingCartMessageHandler shoppingCartMessageHandler;
	// 删除购物车商品信息消息处理
	private static DeleteProductMessageHandler deleteProductMessageHandler;
	// 生产预付订单信息消息处理
	private static MyOrderHandler orderMessageHandler;
	// 缓存非编辑状态下勾选商品的信息
	public List<ShoppingCartSelectBean> selectProductNotInEdit;
	// 编辑状态下缓存选择删除的商品信息
	public List<ShoppingCartProductDeleteBean> deleteProductList;
	private static NewShoppingCartInforBean shoppingCartData;
	// 预付订单数据bean
	private OrderList paymentbean;
	private View footerView;
	private View emptyView;
	private LinearLayout linear_no_login;
	private Button tv_no_login;
	private MainActivity mainActivity;
	public boolean isCancelAll = true;
	private TextView no_shopcart_buy;
	private List<NewProductInfo> listData;
	private NewNewShoppingCardAdapter mewadapter;
	public NewShoppingCartFragment() {

	}

	public NewShoppingCartFragment(MainActivity mainActivity) {
		super();
		// TODO Auto-generated constructor stub
		this.mainActivity = mainActivity;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopping_cart,
				container, true);
		initView(view);
		return view;
	}

	@Override
	public void onPause() {
		// LogUtil.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStart() {
		// LogUtil.d(TAG, "onStart");
		super.onStart();
	}

	*//**
	 * 
	 * Describe:初始化控件
	 * 
	 * Date:2015-9-25
	 * 
	 * Author:liuzhouliang
	 *//*
	private void initView(View parentView) {
		emptyView = parentView
				.findViewById(R.id.base_activity_no_shopcart_parent);
		no_shopcart_buy = (TextView) parentView
				.findViewById(R.id.base_activity_no_shopcart_buy);
		tv_no_login = (Button) parentView
				.findViewById(R.id.base_activity_no_shopcart);
		linear_no_login = (LinearLayout) parentView
				.findViewById(R.id.base_acticity_no_login_parent);
		rlBottom = (RelativeLayout) parentView
				.findViewById(R.id.fragment_shopping_cart_bottom_parent);
		tvTotalPriceTextView = (TextView) parentView
				.findViewById(R.id.fragment_shopping_cart_bottom_totalprice_num);
		lvListView = (ListView) parentView
				.findViewById(R.id.fragment_shopping_cart_lv);
		btSettlement = (Button) parentView
				.findViewById(R.id.fragment_shopping_cart_bottom_settlement);
		cbSelectAllBox = (CheckBox) parentView
				.findViewById(R.id.fragment_shopping_cart_bottom_cb);
		llTotalLayout = (LinearLayout) parentView
				.findViewById(R.id.fragment_shopping_cart_bottom_totalprice);
		footerView = baseInflate.inflate(R.layout.footview_white, null);
		if (ConstantsUtil.SHOPPING_CART_SHOW_BACK
				.equals(mainActivity.isShowBack)) {
			*//**
			 * 显示返回键
			 *//*
			showBack();
			mainActivity.llTabParent.setVisibility(View.GONE);
		} else if (ConstantsUtil.SHOPPING_CART_NOT_SHOW_BACK
				.equals(mainActivity.isShowBack)) {
			*//**
			 * 不显示返回键
			 *//*
			hideBack();
		} else {
			hideBack();
		}
	}

	*//**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	private void initData() {
		// 初始化默认非编辑状态
		isEditType = false;
		selectProductNotInEdit = new ArrayList<ShoppingCartSelectBean>();
		deleteProductList = new ArrayList<ShoppingCartProductDeleteBean>();
		deleteProductMessageHandler = new DeleteProductMessageHandler(this);
		shoppingCartMessageHandler = new ShoppingCartMessageHandler(this);
		orderMessageHandler = new MyOrderHandler(this);
		productListData = new ArrayList<NewProductInfo>();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		final HashMap<String, String> map = new HashMap<String, String>();
		switch (v.getId()) {
		case R.id.base_activity_title_right_righttv:
			map.put("edit", "");
			MobclickAgent.onEvent(baseActivity, "0038", map);
			*//**
			 * 编辑事件
			 *//*
			if ((Boolean) btSettlement.getTag()) {
				*//**
				 * 显示编辑视图===================
				 *//*
				isEditType = true;
				if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
					rlBottom.setVisibility(View.VISIBLE);
					cancelSelectkAll();
					cbSelectAllBox.setChecked(false);
					btSettlement.setTag(false);
					btSettlement.setText("删除");
					btSettlement.setBackgroundColor(getResources().getColor(
							R.color.red_text_color));
					tvRight.setText("完成");
					llTotalLayout.setVisibility(View.INVISIBLE);
					btSettlement.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							map.put("deleteshop", "");
							MobclickAgent.onEvent(baseActivity, "0039", map);
							if (deleteProductList != null

							&& deleteProductList.size() > 0) {
								// ToastUtil.showToast(baseActivity, "test"
								// + deleteProductList.size());
								deleteProductRequest();
							} else {
								ToastUtil.showToast(baseActivity, "请先选择");
							}

						}
					});
				} else {

					btSettlement.setTag(false);
					rlBottom.setVisibility(View.GONE);
					tvRight.setText("完成");
				}

			} else {
				*//**
				 * 显示非编辑视图====================
				 *//*
				isEditType = false;
				if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
					selectAllProduct();
					btSettlement.setTag(true);
					btSettlement.setText("结算" + "(" + mAdapter.totalNum + ")");
					tvRight.setText("编辑");
					llTotalLayout.setVisibility(View.VISIBLE);
					btSettlement.setOnClickListener(this);
				} else {
					btSettlement.setTag(true);
					// btSettlement.setText("结算");
					tvRight.setText("编辑");
					// llTotalLayout.setVisibility(View.GONE);
					// btSettlement.setOnClickListener(null);
					// btSettlement
					// .setBackgroundResource(R.drawable.bg_gray_corner);
					rlBottom.setVisibility(View.GONE);
					mainActivity.btProductNum.setVisibility(View.GONE);
				}
			}
			break;

		case R.id.fragment_shopping_cart_bottom_settlement:
			map.put("edit", "");
			MobclickAgent.onEvent(baseActivity, "0047", map);
			*//**
			 * 结算事件
			 *//*
			if (selectProductNotInEdit != null
					&& selectProductNotInEdit.size() > 0) {
				// 生成预付订单
				ApiHttpCilent.getInstance(baseActivity).CreatPaymentOrder(
						baseActivity, selectProductNotInEdit, "0",
						new MyCallBack());
			} else {
				// LogUtil.d("TAG", selectProductNotInEdit.size() + "");
				ToastUtil.showToast(baseActivity, "请先选中商品");
			}

			break;

		default:
			break;
		case R.id.base_activity_no_shopcart_buy:
			*//**
			 * 购物车去逛逛
			 *//*
			Intent intent = new Intent(baseActivity,
					GroupWholeSaleActivity.class);
			intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "0");// 没有默认到拼单界面
			StartActivityUtil.startActivity(baseActivity, intent);
			break;
		// case R.id.base_activity_title_backicon:
		// ToastUtil.showToast(baseActivity, "test");
		// super.onBackPressed();
		// break;
		case R.id.base_activity_title_backicon:
			HashMap<String, String> maps = new HashMap<String, String>();
			maps.put("back", "");
			MobclickAgent.onEvent(baseActivity, "0048", maps);
			onBackPressed();
			break;
		}

	}

	*//**
	 * 
	 * Describe:删除购物车商品请求
	 * 
	 * Date:2015-10-15
	 * 
	 * Author:liuzhouliang
	 *//*
	private void deleteProductRequest() {
		ApiHttpCilent.getInstance(baseActivity).deleteShoppingCartInfor(
				baseActivity, deleteProductList, new DeleteProductCallBack());
	}

	*//**
	 * 
	 * Describe:删除购物车商品请求回调
	 * 
	 * Date:2015-10-15
	 * 
	 * Author:liuzhouliang
	 *//*
	public static class DeleteProductCallBack extends
			BaseJsonHttpResponseHandler<DeleteShoppingCartProduct> {

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, DeleteShoppingCartProduct arg4) {
			ApiHttpCilent.loading.dismiss();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			deleteProductMessageHandler.sendMessage(message);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				DeleteShoppingCartProduct arg3) {
		}

		@Override
		protected DeleteShoppingCartProduct parseResponse(String response,
				boolean arg1) throws Throwable {
			ApiHttpCilent.loading.dismiss();
			Gson gson = new Gson();
			DeleteShoppingCartProduct shoppingCartData = gson.fromJson(
					response, DeleteShoppingCartProduct.class);
			Message message = Message.obtain();
			if ("1".equals(shoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = shoppingCartData.getError().getInfo();
			}
			deleteProductMessageHandler.sendMessage(message);
			return shoppingCartData;
		}

	}

	*//**
	 * 
	 * Describe:删除购物车请求消息回调
	 * 
	 * Date:2015-10-15
	 * 
	 * Author:liuzhouliang
	 *//*
	public static class DeleteProductMessageHandler extends
			WeakHandler<NewShoppingCartFragment> {

		public DeleteProductMessageHandler(NewShoppingCartFragment reference) {
			super(reference);
			// TODO Auto-generated constructor stub

		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				*//**
				 * 处理成功的数据
				 *//*
				getReference().deleteSelectProduct();
				ToastUtil.showToast(getReference().baseActivity, "删除成功");
				getReference().mAdapter.filterOutDateProductInfors();
				getReference().mAdapter.notifyDataSetChanged();
				getReference().deleteProductList.clear();
				getReference().linear_no_login.setVisibility(View.GONE);
				getReference().rlBottom.setVisibility(View.VISIBLE);
				// 删除所有数据后隐藏下面按钮 显示去逛逛页面 (wk修改)
				if (getReference().productListData.size() == 0) {
					getReference().emptyView.setVisibility(View.VISIBLE);
					getReference().rlBottom.setVisibility(View.GONE);
					getReference().no_shopcart_buy.setClickable(true);
					getReference().no_shopcart_buy.setEnabled(true);
					getReference().tvRight.setVisibility(View.INVISIBLE);
					// getReference().no_shopcart_buy.setOnClickListener(new
					// OnClickListener() {
					// public void onClick(View v) {
					// Intent intent = new Intent(getReference().baseActivity,
					// GroupBuyListActivity.class);
					// StartActivityUtil.startActivity(getReference().baseActivity,
					// intent);
					// }
					// });
				} else {

				}
				//
				break;
			case ConstantsUtil.HTTP_FAILE:
				*//**
				 * 处理失败的数据
				 *//*
				getReference().linear_no_login.setVisibility(View.GONE);
				getReference().emptyView.setVisibility(View.GONE);
				ToastUtil.showToast(getReference().baseActivity, "删除失败");
				getReference().tvRight.setVisibility(View.INVISIBLE);

				break;
			default:
				break;
			}
		}

	}

	*//**
	 * 
	 * Describe:删除购物车中商品
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	private void deleteSelectProduct() {
		*//**
		 * 更新缓存选中的商品
		 *//*
		int size = deleteProductList.size();
		Iterator<NewProductInfo> iterator = mAdapter.dataList.iterator();
		while (iterator.hasNext()) {
			String skuId = iterator.next().getAid();
			for (int i = 0; i < size; i++) {
				if (deleteProductList.get(i).getId().equals(skuId)) {
					iterator.remove();
				}
			}
		}
		if (mAdapter.dataList != null && mAdapter.dataList.size() == 0) {
			mainActivity.btProductNum.setVisibility(View.GONE);
		} else {
			mainActivity.btProductNum.setText(mAdapter.dataList.size() + "");
		}
	}

	*//**
	 * 获取购物车商品列表
	 *//*
	@Override
	protected void getNetData() {
		initData();
		ApiHttpCilent.getInstance(baseActivity).getShoppingCartInfor(
				baseActivity, new ShoppingCartInforRequestCallBack());
	}

	*//**
	 * 
	 * Describe:获取购物车信息网络请求回调
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	public static class ShoppingCartInforRequestCallBack extends
			BaseJsonHttpResponseHandler<NewShoppingCartInforBean> {

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewShoppingCartInforBean arg4) {
			ApiHttpCilent.loading.dismiss();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			shoppingCartMessageHandler.sendMessage(message);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewShoppingCartInforBean arg3) {
		}

		@Override
		protected NewShoppingCartInforBean parseResponse(String response,
				boolean arg1) throws Throwable {
			ApiHttpCilent.loading.dismiss();
			Gson gson = new Gson();
			shoppingCartData = gson.fromJson(response,
					NewShoppingCartInforBean.class);
			Message message = Message.obtain();
			if ("1".equals(shoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = shoppingCartData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = shoppingCartData.getError().getInfo();
			}
			shoppingCartMessageHandler.sendMessage(message);
			return shoppingCartData;
		}

	}

	*//**
	 * 
	 * Describe:处理获取购物车返回数据
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 *//*
	public static class ShoppingCartMessageHandler extends
			WeakHandler<NewShoppingCartFragment> {

		

		public ShoppingCartMessageHandler(NewShoppingCartFragment reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1000:
				*//**
				 * 商品数量价格更新
				 *//*
				Bundle dataBundle = msg.getData();
				long[] num = dataBundle.getLongArray("totalNum");
				float productPrice = dataBundle.getFloat("totalPrice");
				if (getReference().selectProductNotInEdit.size() == 0) {
					*//**
					 * 没有选中的商品
					 *//*
					getReference().mainActivity.btProductNum
							.setVisibility(View.GONE);
					ViewUtil.setActivityPrice(
							getReference().tvTotalPriceTextView, 0 + "");
					getReference().tvTotalPriceTextView.setText("");
					getReference().btSettlement.setText("结算");
				} else {
					*//**
					 * 有选中的商品
					 *//*
					ViewUtil.setActivityPrice(
							getReference().tvTotalPriceTextView, productPrice
									+ "");
					getReference().tvTotalPriceTextView
							.setText(getReference().tvTotalPriceTextView
									.getText() + "元");
					getReference().btSettlement.setText("结算" + "(" + num[0]
							+ ")");
					if (num[0] == 0) {
						getReference().mainActivity.btProductNum
								.setVisibility(View.GONE);
					} else {
						getReference().mainActivity.btProductNum
								.setVisibility(View.VISIBLE);
						getReference().mainActivity.btProductNum.setText(num[0]
								+ "");
					}

				}

				break;
			case ConstantsUtil.HTTP_SUCCESS:
				*//**
				 * 处理成功获取购物车的数据
				 *//*
				if (msg.obj != null && msg.obj instanceof NewShoppingCartInfor) {
					getReference().listData = ((NewShoppingCartInfor) msg.obj).getList();
					getReference().bindData();
					getReference().shoppingCartInfor = (NewShoppingCartInfor) msg.obj;
					getReference().productListData = getReference().shoppingCartInfor
							.getList();
					if (getReference().productListData != null
							&& getReference().productListData.size() > 0) {
						getReference().bindViewData();
						getReference().lvListView.setVisibility(View.VISIBLE);
						getReference().linear_no_login.setVisibility(View.GONE);
					} else {
						*//**
						 * 处理空数据
						 *//*
						getReference().showShopCartEmpty();
						getReference().linear_no_login.setVisibility(View.GONE);
					}

				} else {
					*//**
					 * 处理空数据
					 *//*
					// ToastUtil.showToast(getReference().baseActivity,
					// "请求数据为空");
					// 处理空白页
					getReference().showShopCartEmpty();
					getReference().linear_no_login.setVisibility(View.GONE);
				}

				break;
			case ConstantsUtil.HTTP_FAILE:
				*//**
				 * 处理失败的数据
				 *//*
				String messageString = (String) msg.obj;
				if (messageString != null) {
					// {"result":{},"error":{"info":"服务器异常。","code":"101"},"status":0}
					if (shoppingCartData.getError().getCode().equals("100")) {
						getReference().lvListView.setVisibility(View.GONE);
						getReference().linear_no_login
								.setVisibility(View.VISIBLE);
						getReference().tv_no_login
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										StartActivityUtil.startActivity(
												getReference().baseActivity,
												LoginActivity.class);
									}
								});
					} else {
						ToastUtil.showToast(getReference().baseActivity,
								messageString);
						getReference().lvListView.setVisibility(View.VISIBLE);
						getReference().linear_no_login.setVisibility(View.GONE);
					}
					*//**
					 * 处理失败数据
					 *//*
				} else {
					ToastUtil.showToast(getReference().baseActivity, "请求失败");
					getReference().lvListView.setVisibility(View.VISIBLE);
					getReference().linear_no_login.setVisibility(View.GONE);
				}

				break;
			default:
				break;
			}
		}

	}

	*//**
	 * 
	 * Describe:清除数据
	 * 
	 * Date:2015-11-3
	 * 
	 * Author:liuzhouliang
	 *//*
	public void clearData() {
		rlBottom.setVisibility(View.GONE);
		// cbSelectAllBox.setChecked(false);
		// cbSelectAllBox.setOnCheckedChangeListener(null);
		// btSettlement.setText("结算");
		// btSettlement.setBackgroundResource(R.drawable.bg_gray_corner);
		// tvTotalPriceTextView.setText("");
		// btSettlement.setOnClickListener(null);
	}

	//回调填写购物车数据
	public void bindData() {
		mewadapter = new NewNewShoppingCardAdapter(listData, mainActivity);
		lvListView.setAdapter(mewadapter);
		//过滤失效的商品
		int num = 0;
		for(NewProductInfo produc:listData){
			if(isShowActivityIcon(produc.getStatus())){
				num++;
			}
		}
		
		if(listData.size()>0 && num==listData.size()){
			//如果抢购了的和
			rlBottom.setVisibility(View.VISIBLE);
		}else{
			rlBottom.setVisibility(View.INVISIBLE);
		}
	}
	private boolean isShowActivityIcon(String state) {
		if ("0".equals(state)) {
			*//**
			 * 未开始
			 *//*
			return false;
		} else if ("1".equals(state)) {
			*//**
			 * 已经开始
			 *//*
			return false;
		} else if ("2".equals(state)) {
			*//**
			 * 已经抢光
			 *//*
			return true;
		} else if ("3".equals(state)) {
			*//**
			 * 已经结束
			 *//*
			return true;
		} else {
			return false;
		}
	}
	*//**
	 * 
	 * 王奎 处理生产预付订单数据
	 * 
	 * *//*

	private void Dimess() {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				ApiHttpCilent.loading.dismiss();
			}
		});
	}

	class MyCallBack extends BaseJsonHttpResponseHandler<NewOrderBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewOrderBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewOrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected NewOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<NewOrderBaseBean>() {
			}.getType();
			NewOrderBaseBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
						.getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = bean.getError().getInfo();
				}
			}
			orderMessageHandler.sendMessage(message);
			return bean;
		}
	}

	public class MyOrderHandler extends WeakHandler<NewShoppingCartFragment> {

		@SuppressLint("HandlerLeak")
		public MyOrderHandler(NewShoppingCartFragment reference) {
			super(reference);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				paymentbean = (OrderList) msg.obj;
				if (paymentbean != null) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("commitorder", "");
					MobclickAgent.onEvent(baseActivity, "0047", map);
					Intent intent = new Intent(getActivity(),
							NewOrderDetailActivity.class);
					intent.putExtra(ConstantsUtil.ORDER_CREATEPREPARE_KEY,
							paymentbean);
					intent.putExtra("from", "0");// 购物车购买
					// 结算需要的id num
					ShoppingCartListBean obj = new ShoppingCartListBean();
					obj.setListDataBeans(selectProductNotInEdit);
					intent.putExtra(ConstantsUtil.SHOPCAR_PRODUCT_LIST_KEY, obj);
					StartActivityUtil.startActivity(baseActivity, intent);
				}
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(baseActivity, back);
				// to_pay.setEnabled(false);
				// to_pay.setBackground(getResources().getDrawable(R.drawable.verifystatus_button));
				break;
			default:
				break;
			}
		}
	}

	*//**
	 * 
	 * Describe:绑定视图数据
	 * 
	 * Date:2015-9-27
	 * 
	 * Author:liuzhouliang
	 *//*
	private void bindViewData() {
		// getReference().productListData
		if (shoppingCartInfor != null && shoppingCartInfor.getList() != null
				&& productListData.size() > 0) {
			llTotalLayout.setVisibility(View.VISIBLE);
			btSettlement.setBackgroundResource(R.drawable.bt_bg_corners_yellow);
			rlBottom.setVisibility(View.VISIBLE);
			rlBottom.getBackground().setAlpha(204);
			lvListView.addFooterView(footerView);
//			mAdapter = new NewShoppingCartFragmentAdapter(productListData,
//					getActivity(), this, shoppingCartMessageHandler, lvListView);
			lvListView.setAdapter(mAdapter);
			btSettlement.setText("结算" + "(" + mAdapter.totalNum + ")");
			mainActivity.btProductNum.setVisibility(View.VISIBLE);
			mAdapter.getTotalPrice();
			if (mAdapter.totalNum == 0) {
				mainActivity.btProductNum.setVisibility(View.GONE);
			} else {
				mainActivity.btProductNum.setText(mAdapter.totalNum + "");
			}

			ViewUtil.setActivityPrice(tvTotalPriceTextView, mAdapter.totalPrice
					+ "");
			tvTotalPriceTextView.setText(tvTotalPriceTextView.getText()
					.toString() + "元");
			btSettlement.setTag(true);
			*//**
			 * 初始化默认全选商品
			 *//*
			// cbSelectAllBox.setChecked(true);
			selectAllProduct();
			setChildViewListener();
		} else {
			*//**
			 * 处理列表数据为空
			 *//*

		}

	}

	*//**
	 * 
	 * Describe:设置控件监听
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 *//*
	private void setChildViewListener() {
		ivBack.setOnClickListener(this);
		tvRight.setOnClickListener(this);
		cbSelectAllBox.setOnCheckedChangeListener(this);
		btSettlement.setOnClickListener(this);
		no_shopcart_buy.setOnClickListener(this);
	}

	@Override
	protected void setViewListener() {
		btSettlement.setOnClickListener(this);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void reloadCallback() {
		getNetData();
	}

	@Override
	protected String setTitleName() {
		return "购物车";
	}

	@Override
	protected String setRightText() {
		return "编辑";
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
		return true;
	}

	@Override
	protected boolean hasTitleIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean hasDownIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (isChecked) {
			*//**
			 * 全选择
			 *//*
			map.put("allchioce", "");
			MobclickAgent.onEvent(baseActivity, "0043", map);
			if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
				selectAllProduct();
			} else {
				ToastUtil.showToast(baseActivity, "商品为空");
			}
		} else {
			*//**
			 * 取消全选择
			 *//*
			map.put("allchiocecancle", "");
			MobclickAgent.onEvent(baseActivity, "0044", map);
			if (isCancelAll) {
				cancelSelectkAll();
			}

		}
	}

	*//**
	 * 
	 * Describe:在非编辑状态下，是否可以取消全部选中
	 * 
	 * Date:2015-11-3
	 * 
	 * Author:liuzhouliang
	 *//*
	private boolean notEditIsShow() {
		for (NewProductInfo productInfor : mAdapter.dataList) {
			if (productInfor.getCheckBoxState()) {
				return false;
			}
		}
		return true;
	}

	*//**
	 * Describe:取消全部选中
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	private void cancelSelectkAll() {
		if (!isEditType) {
			*//**
			 * 非编辑状态
			 *//*
			List<NewProductInfo> checkArray = mAdapter.dataList;
			int size = checkArray.size();
			for (int i = 0; i < size; i++) {
				checkArray.get(i).setCheckBoxState(false);
			}
			mAdapter.notifyDataSetChanged();
			cancelAllProductNotInEdit();
		} else {
			*//**
			 * 编辑状态
			 *//*
			List<NewProductInfo> checkArray = mAdapter.dataList;
			int size = checkArray.size();
			for (int i = 0; i < size; i++) {
				checkArray.get(i).setCheckBoxState(false);
			}
			mAdapter.notifyDataSetChanged();
			cancelAllProductInEdit();
		}
	}

	*//**
	 * 
	 * Describe:全部选中事件
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	private void selectAllProduct() {
		if (!isEditType) {
			*//**
			 * 非编辑状态:过滤已经活动过期的商品
			 *//*
			if (mAdapter != null && mAdapter.dataList != null) {

				List<NewProductInfo> checkArray = mAdapter.dataList;
				int size = checkArray.size();
				for (int i = 0; i < size; i++) {
					checkArray.get(i).setCheckBoxState(true);
				}
				mAdapter.notifyDataSetChanged();

				mAdapter.getTotalPrice();
				btSettlement.setText("结算" + "(" + mAdapter.totalNum + ")");
				if (mAdapter.totalNum == 0) {
					mainActivity.btProductNum.setVisibility(View.GONE);
				} else {
					mainActivity.btProductNum.setVisibility(View.VISIBLE);
					mainActivity.btProductNum.setText(mAdapter.totalNum + "");
				}

				ViewUtil.setActivityPrice(tvTotalPriceTextView,
						mAdapter.totalPrice + "");
				tvTotalPriceTextView.setText(tvTotalPriceTextView.getText()
						+ "元");
				// cbSelectAllBox.setChecked(true);
				selectAllProductNotInEdit();
				// LogUtil.d("TAG", selectProductNotInEdit.toString());
			}
		} else {
			*//**
			 * 编辑状态
			 *//*

			List<NewProductInfo> checkArray = mAdapter.dataList;
			int size = checkArray.size();
			for (int i = 0; i < size; i++) {
				checkArray.get(i).setCheckBoxState(true);
			}
			mAdapter.notifyDataSetChanged();
			btSettlement.setText("删除");
			*//**
			 * 缓存所有选中的商品信息
			 *//*
			selectAllProductInEdit();
		}

	}

	*//**
	 * 非编辑状态下，全部选择商品的缓存
	 *//*
	private void selectAllProductNotInEdit() {
		selectProductNotInEdit.clear();
		int size = mAdapter.dataList.size();

		for (int i = 0; i < size; i++) {
			// String stateString = "1";
			String stateString = mAdapter.dataList.get(i).getStatus();
			boolean isOutTime = isActivityOutTime(stateString);
			if (!isOutTime) {
				*//**
				 * 商品活动未过期
				 *//*
				ShoppingCartSelectBean dataBean = new ShoppingCartSelectBean();
				dataBean.setNum(mAdapter.dataList.get(i).getItemNewNum());// 商品数量
				dataBean.setId(mAdapter.dataList.get(i).getAid());// 活动ID
				selectProductNotInEdit.add(dataBean);
			}

		}

		if (selectProductNotInEdit != null
				&& selectProductNotInEdit.size() == 0) {
			*//**
			 * 没有可以结算的商品吗，置灰结算控件
			 *//*
			rlBottom.setVisibility(View.GONE);
			btSettlement.setBackgroundResource(R.drawable.bg_gray_corner);
			cbSelectAllBox.setChecked(false);
		} else {
			cbSelectAllBox.setChecked(true);
		}

	}

	private boolean isActivityOutTime(String state) {
		if ("0".equals(state)) {
			*//**
			 * 未开始
			 *//*
			return false;
		} else if ("1".equals(state)) {
			*//**
			 * 已经开始
			 *//*
			return false;
		} else if ("2".equals(state)) {
			*//**
			 * 已经抢光
			 *//*
			return true;
		} else if ("3".equals(state)) {
			*//**
			 * 已经结束
			 *//*
			return true;
		} else {
			return false;
		}
	}

	*//**
	 * 
	 * Describe:编辑状态下，全部缓存商品
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	private void selectAllProductInEdit() {
		deleteProductList.clear();
		int size = mAdapter.dataList.size();
		for (int i = 0; i < size; i++) {
			String skuid = mAdapter.dataList.get(i).getAid();
			boolean isInCache = isInCacheDeleteProduct(skuid);
			if (!isInCache) {
				ShoppingCartProductDeleteBean dataBean = new ShoppingCartProductDeleteBean();
				dataBean.setId(mAdapter.dataList.get(i).getId());
				// dataBean.setIid(mAdapter.dataList.get(i).getId());
				deleteProductList.add(dataBean);
			}

		}
		// ToastUtil.showToast(baseActivity, deleteProductList.size() + "");
	}

	*//**
	 * 判断在编辑状态下，缓存中是否存在已经选中的商品
	 * 
	 * @param skuid
	 * @return
	 *//*
	private boolean isInCacheDeleteProduct(String skuid) {
		boolean isAdd = false;
		*//**
		 * 判断当前选中的商品，在缓存中是否存在
		 *//*
		for (int i = 0; i < deleteProductList.size(); i++) {
			if (skuid.equals(deleteProductList.get(i).getId())) {

				isAdd = true;
			}
		}
		return isAdd;
	}

	*//**
	 * 
	 * Describe:编辑状态下取消选中全部商品
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 *//*
	private void cancelAllProductInEdit() {
		if (deleteProductList != null) {
			deleteProductList.clear();
		}
		// ToastUtil.showToast(baseActivity, deleteProductList.size() + "");
	}

	*//**
	 * 非编辑状态下，取消全部商品信息缓存
	 *//*
	public void cancelAllProductNotInEdit() {
		if (selectProductNotInEdit != null) {
			selectProductNotInEdit.clear();
			btSettlement.setText("结算");
			tvTotalPriceTextView.setText("");
			mainActivity.btProductNum.setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (MyApplication.isRefreshShopcart) {
			getNetData();
			MyApplication.isRefreshShopcart = true;
			hideShopCartEmpty();
		}
		LogUtil.d(TAG, "onResume");
		MobclickAgent.onPageStart("ShoppingCartFragment");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtil.d(TAG, "onStop");
		MobclickAgent.onPageEnd("ShoppingCartFragment");
	}

}*/