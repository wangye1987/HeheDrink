package com.heheys.ec.model.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity.MyMessageHandler;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.controller.activity.ShopListDetailActivity.AdverHandler;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.TimeUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.DeleteShoppingCartProduct;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.model.dataBean.ShoppingCartProductDeleteBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.WholeBaseBean.WholeListBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.AnimationCartUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.StatusString;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BackRemark;
import com.heheys.ec.view.SoftEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间 ：2016-3-14 上午11:22:12 类说明 批发列表适配器
 */
public class WholeSaleListAdapter extends BaseListAdapter<WholeListBean> {
	private String TAG = GroupWholeSaleActivity.class.getName();
	// // 存储倒计时差
	private Animation mAnimation;
	private long totalnum;
	private String numStr;
	boolean mIsListViewidle;
	private String recommend;
	private ImageView ivShoppingCart;
	private ViewGroup anim_mask_layout;// 动画层
	public int buyNum;
	private int currentdate;// 添加加1
	private String bdviewtv;
	private String clickTypeString;// 当前的动作
	private int clickPosition;// 当前点击的位置
	List<WholeListBean> listdate = new ArrayList<WholeListBean>();
	AddShoppingCartBean addShoppingCartData;
	// 存储倒计时差
	private List<Long> countDownTimeList;
	private boolean isPlay;
	private Handler handler = new Handler();
	private AddCartMessageHandler addShoppingCartMessageHandler;
	MyMessageHandler messageHandler;
	AdverHandler mhandler;
	float totalPrice = 0;
	int totalNum = 0;
	private Button shoppingcare_num;
	private List<ShoppingCartSelectBean> selectProductNotInEdit;
	private NewShoppingCartInforBean shoppingCartData;
	public static boolean cancommit = true;// 是否可以提交
	private ListView listView;
	private DeleteShoppingCartProduct deleteshoppingCartData;
	public List<ShoppingCartProductDeleteBean> deleteProductList;// 删除的商品ID
	private ImageView iv_reduces;
	private SoftEditText buy_edittext;
	private View v;
	private boolean hasnowgoods = false;
	private String nowString;
	private ResultBean loginObj;
	private final AlertDialogCustom mdialog;
	private boolean isPopuAlert;
	private int nowNum;// 输入前的数据
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (!isPlay)
				return;
			// isPlay = false;
			handler.postDelayed(this, 1000);
			if (countDownTimeList != null && countDownTimeList.size() > 0) {
				int size = countDownTimeList.size();
				for (int i = 0; i < size; i++) {
					if (countDownTimeList.get(i) > 0) {
						countDownTimeList.set(i,
								countDownTimeList.get(i) - 1000);
					}
				}
			}
			// notifyDataSetChanged();
			int viewsize = dataList.size();
			for (int id = 0; id < viewsize; id++) {
				updateSingleRow(listView, id);
			}
		}
	};

	List<WholeListBean> groupBuyProductList;

	public void setData(List<WholeListBean> groupBuyProductList) {
		this.groupBuyProductList = groupBuyProductList;
	}

	private void updateSingleRow(ListView listView, long id) {

		if (listView != null) {
			int start = listView.getFirstVisiblePosition();
			for (int i = start, j = listView.getLastVisiblePosition(); i <= j; i++) {
				// if (id == ((Messages) listView.getItemAtPosition(i)).getId())
				// {
				View view = listView.getChildAt(i - start);
				setDateNotify(i, view);
				// break;
			}
		}
	}

	// 局部刷新
	void setDateNotify(int i, View view) {
		setNewData(i, view);
	}

	void setNewData(int i, View view) {
		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(view,
				R.id.tv_time_day);//
		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(view,
				R.id.tv_time_hour);//
		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(view,
				R.id.tv_time_minitue);//
		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(view,
				R.id.tv_time_second);//
		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(view, R.id.group_buy_list_item_time_parent);
		if (i < dataList.size() - 1) {
			if (dataList.get(i).getType().equals("0")) {

				// 拼单
				if (countDownTimeList.get(i) == 0) {
					llTimeParentLayout.setVisibility(View.VISIBLE);
					tv_time_day.setText("00");
					tv_time_hour.setText("00");
					tv_time_minitue.setText("00");
					tv_time_second.setText("00");
				} else {
					llTimeParentLayout.setVisibility(View.VISIBLE);
					setCountDownTime(countDownTimeList.get(i), tv_time_day,
							tv_time_hour, tv_time_hour, tv_time_minitue,
							tv_time_second);
				}
				llTimeParentLayout.setVisibility(View.VISIBLE);
			}
		}
	}

	public WholeSaleListAdapter(ImageView ivShoppingCart,
			boolean mIsListViewidle, List<WholeListBean> data, Context context,
			String recommend, MyMessageHandler messageHandler,
			AdverHandler mhandler, Button shoppingcare_num,
			List<ShoppingCartSelectBean> selectProductNotInEdit,
			NewShoppingCartInforBean shoppingCartData, ListView listView) {
		super(data, context);
		this.groupBuyProductList = data;
		this.ivShoppingCart = ivShoppingCart;
		this.mIsListViewidle = mIsListViewidle;
		initCountDownTime();
		this.recommend = recommend;
		this.shoppingcare_num = shoppingcare_num;
		this.selectProductNotInEdit = selectProductNotInEdit;
		this.shoppingCartData = shoppingCartData;
		this.listView = listView;
		bdviewtv = shoppingcare_num.getText().toString().trim();
		if (!StringUtil.isEmpty(bdviewtv))
			buyNum = Integer.parseInt(bdviewtv);
		addShoppingCartMessageHandler = new AddCartMessageHandler(this);
		if (messageHandler != null)
			this.messageHandler = messageHandler;
		if (mhandler != null)
			this.mhandler = mhandler;
		if (SharedPreferencesUtil.getObject(context,
				"resultbean")!= null && !SharedPreferencesUtil.getObject(context,
						"resultbean").equals(""))
			loginObj = (ResultBean) SharedPreferencesUtil.getObject(context,
					"resultbean");
		deleteProductList = new ArrayList<ShoppingCartProductDeleteBean>();
		mdialog = new AlertDialogCustom();
	}

	public void setshoppingcart(NewShoppingCartInforBean shoppingCartData) {
		this.shoppingCartData = shoppingCartData;
	}

	/**
	 * 
	 * Describe:启动倒计时
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */
	public void startCountDownTime() {
		if (isPlay) {
			return;
		}
		isPlay = true;
		runnable.run();
	}

	/**
	 * 
	 * Describe:停止倒计时
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */

	public void stopCountDownTime() {
		isPlay = false;
	}

	/**
	 * 
	 * Describe:初始化倒计时
	 * 
	 * Date:2015-10-10
	 * 
	 * Author:liuzhouliang
	 */
	public void initCountDownTime() {
		/**
		 * 处理倒计时
		 */
		if (dataList != null) {
			int size = dataList.size();
			if (size > 0) {
				if (countDownTimeList != null) {
					countDownTimeList.clear();
				}
				countDownTimeList = new ArrayList<Long>();

				for (int i = 0; i < size; i++) {
					String startTimeString = dataList.get(i).getEndtime();
					// String endTimeString = System.currentTimeMillis();
					long appointTime = 0;
					if (!StringUtil.isEmpty(startTimeString)) {
						appointTime = TimeUtil
								.changeDateToTime(startTimeString);
					}
					long currentTime = System.currentTimeMillis();
					long gapTime = appointTime - currentTime;
					if (gapTime > 0) {
						countDownTimeList.add(i, gapTime);
					} else {
						countDownTimeList.add(i, 0L);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 添加到购物车
	 * */
	public void AddToShoppcart(final int position, String typeshopping,
			boolean isPopu) {
		clickTypeString = typeshopping;
		clickPosition = position;
		isPopuAlert = isPopu;
		/**
		 * 内存中当前数量为空
		 */
		nowNum = dataList.get(position).getCurrentnum();
		if ("add".equals(clickTypeString)) {
			// 增加
			if (dataList.get(position).getCurrentnum() == 0
					|| dataList.get(position).getCurrentnum() < Integer
							.parseInt(dataList.get(position).getMinnum())) {
				// 第一次加直接加到最小起批 量 弹出框
				currentdate = Integer.parseInt((StringUtil.isEmpty(dataList
						.get(position).getMinnum()) ? "1" : dataList.get(
						position).getMinnum()));
				if (!isPopu
						&& Integer.parseInt((StringUtil.isEmpty(dataList.get(
								position).getHasrecom()) ? "0" : dataList.get(
								position).getHasrecom())) == 1) {
					Message messageAdd = Message.obtain();
					messageAdd.what = 1001;
					Bundle dataBundleAdd = new Bundle();
					dataBundleAdd.putString("aid", dataList.get(position)
							.getId());
					dataBundleAdd.putString("url", dataList.get(position)
							.getPic());
					dataBundleAdd.putInt("position", position);
					messageAdd.setData(dataBundleAdd);
					if (mhandler != null)
						mhandler.sendMessage(messageAdd);
					if (messageHandler != null)
						messageHandler.sendMessage(messageAdd);
					return;
				}
			} else {
				currentdate = dataList.get(position).getCurrentnum() + 1;
			}

		} else if ("reduce".equals(clickTypeString)) {
			// 减少
			if (dataList.get(position).getCurrentnum() <= Integer
					.parseInt((StringUtil.isEmpty(dataList.get(position)
							.getMinnum()) ? "1" : dataList.get(position)
							.getMinnum()))) {
				List<NewProductInfo> listdelete = null;
				if (shoppingCartData != null
						&& shoppingCartData.getResult() != null) {
					listdelete = shoppingCartData.getResult().getList();
				}
				if (listdelete != null && deleteProductList != null) {
					deleteProductList.clear();
					ShoppingCartProductDeleteBean dataBean = new ShoppingCartProductDeleteBean();
					dataBean.setAid(dataList.get(position).getId());
					deleteProductList.add(dataBean);
				}
				// 删除商品
				ApiHttpCilent.getInstance(mcontext).deleteShoppingCartInfor(
						mcontext, deleteProductList,
						new DeleteProductCallBack());
				currentdate = 0;
				return;
			} else {
				if (dataList.get(position).getCurrentnum() > dataList.get(
						position).getNum()) {
					// 如果当前的数量大于库存 直接减到库存量
					currentdate = dataList.get(position).getNum();
				} else {
					currentdate = dataList.get(position).getCurrentnum() - 1;
				}
			}

		} else if ("textchange".equals(clickTypeString)) {
			currentdate = dataList.get(position).getCurrentnum();
		}
		dataList.get(position).setCurrentnum(currentdate);
		// 加入购物车
		addShoppingCart(dataList.get(position).getId(), currentdate + "");// id是当前的商品活动ID

	}

	public class DeleteProductCallBack extends
			BaseJsonHttpResponseHandler<DeleteShoppingCartProduct> {

		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, DeleteShoppingCartProduct arg4) {
			Dismess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			addShoppingCartMessageHandler.sendMessage(message);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				DeleteShoppingCartProduct arg3) {
			Dismess();
		}

		@Override
		protected DeleteShoppingCartProduct parseResponse(String response,
				boolean arg1) throws Throwable {
			Dismess();
			Gson gson = new Gson();
			deleteshoppingCartData = gson.fromJson(response,
					DeleteShoppingCartProduct.class);
			Message message = Message.obtain();
			if ("1".equals(deleteshoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 错误
				message.obj = deleteshoppingCartData.getError().getInfo();
			}
			addShoppingCartMessageHandler.sendMessage(message);
			return deleteshoppingCartData;
		}

	}

	// 获取所有有效商品数据总价格
	public void getTotalPrice() {
		totalPrice = 0;
		totalNum = 0;
		List<ShoppingCartSelectBean> listcommit = selectProductNotInEdit;
		if (listcommit != null) {
			for (ShoppingCartSelectBean info : listcommit) {
				// 拼单取cprice
				totalPrice = (float) (totalPrice + Double
						.parseDouble(StatusString.getSign(info
								.getCurrentprice()))
						* Integer.parseInt(StatusString.getSign(info.getNum())));
				totalNum = totalNum
						+ Integer.parseInt(StatusString.getSign(info.getNum()));
			}
		}
	}

	// 更新下面的有效的商品个数和总价格
	private void updateDataMessage() {
		// 重新加上带生成订单bean价格
		// CaclutePrice();
		getTotalPrice();
		Message messageAdd = Message.obtain();
		messageAdd.what = 1000;
		Bundle dataBundleAdd = new Bundle();
		long[] num = new long[1];
		num[0] = totalNum;
		dataBundleAdd.putLongArray("totalNum", num);
		dataBundleAdd.putFloat("totalPrice", totalPrice);
		messageAdd.setData(dataBundleAdd);
		if (messageHandler != null)
			messageHandler.sendMessage(messageAdd);
		if (mhandler != null)
			mhandler.sendMessage(messageAdd);

	}
	@SuppressWarnings("ResourceType")
	public static ViewGroup createAnimLayout(Activity mContext) {
		ViewGroup rootView = (ViewGroup) (mContext.getWindow().getDecorView());
		LinearLayout animLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private static View addViewToAnimLayout(final ViewGroup vg,
			final View view, int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	public void setAnim(final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout((Activity) mcontext);
		anim_mask_layout.addView(v);// 把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		ivShoppingCart.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 0f, 0.6f, 0f,
				Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT,
				1f);
		scaleAnimation.setRepeatCount(0);// 动画重复执行的次数

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);

		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			}
		});
	}

	// 加入到购物车
	public void addShoppingCart(String aid, String num) {
		ApiHttpCilent.getInstance(mcontext).addShoppingCart(mcontext, aid, num,
				0, new AddCartRequestCallBack());
	}

	private void Dismess() {
		((Activity) mcontext).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	public static class AddCartMessageHandler extends
			WeakHandler<WholeSaleListAdapter> {
		public AddCartMessageHandler(WholeSaleListAdapter reference) {
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
				if ("reduce".equals(getReference().clickTypeString)) {
					/**
					 * 减少操作==========================
					 */
					getReference()
							.updateCacheProductsNotInEdit(
									getReference().dataList
											.get(getReference().clickPosition),
									getReference().dataList.get(
											getReference().clickPosition)
											.getCurrentnum()
											+ "");
					// 设置当前列表数量
					if (getReference().currentdate > 0) {
						getReference().buy_edittext
								.setText(getReference().currentdate + "");
						getReference().iv_reduces.setVisibility(View.VISIBLE);
						getReference().buy_edittext.setVisibility(View.VISIBLE);
					} else {
						getReference().iv_reduces.setVisibility(View.INVISIBLE);
						getReference().buy_edittext
								.setVisibility(View.INVISIBLE);
						getReference().shoppingcare_num
								.setVisibility(View.INVISIBLE);
					}

					getReference().updateDataMessage();
					getReference().notifyDataSetChanged();
				}
				if ("add".equals(getReference().clickTypeString)) {
					/**
					 * 增加操作==================================
					 */
					/**
					 * 更新缓存中商品数量
					 */
					getReference()
							.updateCacheProductsNotInEdit(
									getReference().dataList
											.get(getReference().clickPosition),
									getReference().dataList.get(
											getReference().clickPosition)
											.getCurrentnum()
											+ "");

					// 设置当前列表数量
					if (getReference().currentdate > 0) {
						if (!getReference().isPopuAlert) {
							getReference().buy_edittext
									.setText(getReference().currentdate + "");
							getReference().iv_reduces
									.setVisibility(View.VISIBLE);
							getReference().buy_edittext
									.setVisibility(View.VISIBLE);
							getReference().shoppingcare_num
									.setVisibility(View.VISIBLE);
						}
					}
					// 加入购物车成功动画
					if (!getReference().isPopuAlert)
						getReference().SetAnimation();

					getReference().updateDataMessage();
					getReference().notifyDataSetChanged();
				}
				if ("textchange".equals(getReference().clickTypeString)) {
					getReference()
							.updateCacheProductsNotInEdit(
									getReference().dataList
											.get(getReference().clickPosition),
									getReference().dataList.get(
											getReference().clickPosition)
											.getCurrentnum()
											+ "");
					getReference().updateDataMessage();
				}
				cancommit = true;
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference().mcontext, messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					ToastUtil.showToast(getReference().mcontext, "加入购物车失败");
				}
				// 加入失败刷新界面
				getReference().dataList.get(getReference().clickPosition)
						.setCurrentnum(getReference().nowNum);
				getReference().notifyDataSetChanged();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				// 删除成功
				getReference()
						.updateCacheProductsNotInEdit(
								getReference().dataList
										.get(getReference().clickPosition),
								getReference().currentdate + "");
				// 设置当前列表数量
				getReference().dataList.get(getReference().clickPosition)
						.setCurrentnum(getReference().currentdate);
				if (getReference().currentdate > 0) {
					getReference().buy_edittext
							.setText(getReference().currentdate + "");
					getReference().iv_reduces.setVisibility(View.VISIBLE);
					getReference().buy_edittext.setVisibility(View.VISIBLE);
				} else {
					getReference().iv_reduces.setVisibility(View.INVISIBLE);
					getReference().buy_edittext.setVisibility(View.INVISIBLE);
					getReference().shoppingcare_num
							.setVisibility(View.INVISIBLE);
				}
				getReference().updateDataMessage();
				getReference().notifyDataSetChanged();
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN:
				//
				String controlText = (String) msg.obj;
				if (!StringUtil.isEmpty(controlText)) {
					ToastUtil.showToast(getReference().mcontext, controlText);
				} else {
					ToastUtil.showToast(getReference().mcontext, "操作失败!");
				}
				break;
			}
		}

	}

	private void SetAnimation() {
		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		buy_edittext.getLocationInWindow(start_location);// 这是获取购买文本框的在屏幕的X、Y坐标（这也是动画开始的坐标）
		ImageView buyImg = new ImageView(mcontext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 60);
		buyImg.setLayoutParams(lp);
		MyApplication.imageLoader.displayImage(dataList.get(clickPosition)
				.getPic(), buyImg, MyApplication.options);
		AnimationCartUtil.setAnim(mcontext, ivShoppingCart, buyImg,
				start_location);
	}

	/*
	 * beanlist商品列表 当前添加的数据 num商品列表当前点击产品数量
	 */
	private void updateCacheProductsNotInEdit(WholeListBean beanlist, String num) {
		// 提交订单的数据bean
		List<ShoppingCartSelectBean> cacheProduct = selectProductNotInEdit;
		// 存取临时的不在当前购物车的数据
		List<ShoppingCartSelectBean> cacheProductTemp = new ArrayList<ShoppingCartSelectBean>();
		cacheProductTemp.clear();
		if (cacheProduct != null && cacheProduct.size() > 0) {
			for (ShoppingCartSelectBean product : cacheProduct) {
				if (beanlist.getId().equals(product.getAid())) {
					// 当前添加的数据是购物车存在就添加 不存在就重新加入
					product.setNum(num);// 提交订单bean
					if (beanlist.getType().equals("1")) {
						product.setCurrentprice(beanlist.getCprice());
					} else {
						product.setCurrentprice(beanlist.getDeprice());
					}
					hasnowgoods = true;
				}
			}
			if (!hasnowgoods) {
				List<ShoppingCartSelectBean> seletcbean = new ArrayList<ShoppingCartSelectBean>();
				ShoppingCartSelectBean bean = new ShoppingCartSelectBean();
				seletcbean.clear();
				bean.setAid(beanlist.getId());
				bean.setNum(num);
				if (beanlist.getType().equals("1")) {
					bean.setCurrentprice(beanlist.getCprice());
				} else {
					bean.setCurrentprice(beanlist.getDeprice());
				}
				seletcbean.add(bean);
				cacheProductTemp.addAll(seletcbean);
			}

		} else if (cacheProduct != null && cacheProduct.size() == 0) {
			// 购物车原本就是空
			ShoppingCartSelectBean seletcbean = new ShoppingCartSelectBean();
			seletcbean.setAid(beanlist.getId());
			seletcbean.setNum(num);
			if (beanlist.getType().equals("1")) {
				seletcbean.setCurrentprice(beanlist.getCprice());
			} else {
				seletcbean.setCurrentprice(beanlist.getDeprice());
			}
			cacheProductTemp.add(seletcbean);
		}
		cacheProduct.addAll(cacheProductTemp);

		for (int i = 0; i < cacheProduct.size(); i++) {
			if (cacheProduct.get(i).getNum().equals("0")) {
				cacheProduct.remove(cacheProduct.get(i));
				break;
			}
		}
		hasnowgoods = false;
		selectProductNotInEdit = cacheProduct;// 重新赋值
		setShoppcartdata(cacheProduct, shoppingCartData);
	}

	// 设置购物车当前数据newnum数据
	void setShoppcartdata(List<ShoppingCartSelectBean> cacheProduct,
			NewShoppingCartInforBean shoppingCartData) {
		if (shoppingCartData != null && shoppingCartData.getResult() != null
				&& shoppingCartData.getResult().getList() != null) {
			List<NewProductInfo> listshop = shoppingCartData.getResult()
					.getList();
			if (listshop.size() > 0) {
				for (NewProductInfo produce : listshop) {// 当前购物车
					for (ShoppingCartSelectBean select : cacheProduct) {// 提交生成订单数据bean
						if (produce.getAid().equals(select.getAid())) {
							produce.setNum(select.getNum());
						}
					}
				}
			}
		}
	}

	public class AddCartRequestCallBack extends
			BaseJsonHttpResponseHandler<AddShoppingCartBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AddShoppingCartBean arg4) {
			Dismess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			addShoppingCartMessageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AddShoppingCartBean arg3) {
			Dismess();
		}

		@Override
		protected AddShoppingCartBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dismess();
			Gson gson = new Gson();
			addShoppingCartData = gson.fromJson(response,
					AddShoppingCartBean.class);
			Message message = Message.obtain();
			if ("1".equals(addShoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = addShoppingCartData.getError().getInfo();
			}
			addShoppingCartMessageHandler.sendMessage(message);
			return addShoppingCartData;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.whole_sale_item,
					parent, false);
		}
		WholeListBean bean = groupBuyProductList.get(position);
		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(convertView, R.id.group_buy_list_item_time_parent);
		ImageView iv_activity = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_activity);// 是否过期戳
		TextView tv_wine_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_wine_name);// 商品名称
		ImageView iv_wineurl = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_wineurl);// 商品图片
		ImageView iv_jinxuan = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_jinxuan);// 是否精选图标

		final ImageView iv_reduce = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_num_reduce);// 减少商品
		final SoftEditText buy_edittext = (SoftEditText) ViewHolderUtil
				.getItemView(convertView,
						R.id.fragment_shopping_cart_item_num_et);// 减少商品
		buy_edittext.setFocusable(true);
		LinearLayout linear_reduce = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_reduce);//
		ImageView iv_add = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_num_add);// 增加商品
		TextView tv_knum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_knum);
		TextView tv_notice_nogoods = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_notice_nogoods);

		/**
		 * 
		 * 以下是批发视图初始化
		 * */
		/******************************************************************/
		TextView tv_salenum = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_salenum);// 批发销量
		TextView tv_unit_next = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_unit_next);// 批发单价多少钱一瓶数据单位
		TextView tv_unit_next_one_price = (TextView) ViewHolderUtil
				.getItemView(convertView, R.id.tv_unit_next_one_price);// 批发单价多少钱一瓶数据
		TextView tv_price = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_price);// 批发多少钱一箱
		TextView tv_unit = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_unit);// 批发箱单位

		// 批发父视图
		LinearLayout linear_pf = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_pf);
		/******************************************************************/

		/**
		 * 
		 * 以下是拼单视图初始化
		 * */

		/******************************************************************/
		ImageView iv_pin = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.iv_pin);// 是否是拼单左上角图标
		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_day);
		TextView tv_time_day_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_hour);
		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_hour);
		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_minitue);
		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_time_second);
		LinearLayout linear_dqj = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_dqj);

		// 拼单父视图
		LinearLayout linear_pd = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_pd);

		// 当前价
		TextView tv_dq = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_dq);// 当前价
		TextView tv_price_now = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price_now);// 拼单当前价
		TextView currt_price_unit = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.currt_price_unit);// 拼单当前价单位

		// 拼单热度
		LinearLayout linear_hot = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_hot);// 拼单热度
		TextView tv_hot = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_hot);// 拼单热度
		TextView hot_tv = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.hot_tv);// 拼单热度单位

		// 拼单热度进度条
		RelativeLayout linear_pb = (RelativeLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_pb);// 拼单进度条父视图
		ProgressBar pb = (ProgressBar) ViewHolderUtil.getItemView(convertView,
				R.id.group_buy_grid_item_pb);// 拼单进度条

		/******************************************************************/

		final int indexthis = position;
		buy_edittext.setText(dataList.get(position).getCurrentnum() + "");
		buy_edittext.setTag(indexthis);
		buy_edittext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mdialog.AlertUpdateNum((Activity) mcontext,
						dataList.get(position).getMinnum(),
						dataList.get(position).getNum(), buy_edittext.getText()
								.toString(), new BackRemark() {
							@Override
							public void setRemark(String cid, String currtText) {
								if (!StringUtil.isEmpty(currtText)) {
									currentdate = Integer.parseInt(currtText);
									dataList.get(position).setCurrentnum(
											currentdate);
									clickTypeString = "textchange";
									cancommit = true;
									// 加入购物车
									AddToShoppcart(position, clickTypeString,
											false);
									buy_edittext.setText(currentdate + "");
								}
							}
						});
			}
		});
		// 新增
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 添加购物车
				AddToShoppcart(position, "add", false);// 新增
				setAmationImage(buy_edittext, iv_reduce, v);
			}
		});

		// 减少
		iv_reduce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddToShoppcart(position, "reduce", false);// 减少
				setAmationImage(buy_edittext, iv_reduce, v);
			}
		});

		tv_wine_name.setText(bean.getName());
		tv_price.setText(bean.getCurrency() == null ? "¥" : bean.getCurrency()
				+ bean.getCprice());
		tv_unit.setText("/" + bean.getUnit());
		String totalPrice = bean.getCprice();
		String boxsize = bean.getBoxsize();

		if (bean.getType().equals("0")) {
			linear_pf.setVisibility(View.GONE);
			linear_pd.setVisibility(View.VISIBLE);

			// 拼单
			if (countDownTimeList.get(position) == 0) {
				llTimeParentLayout.setVisibility(View.VISIBLE);
				tv_time_day.setText("00");
				tv_time_hour.setText("00");
				tv_time_minitue.setText("00");
				tv_time_second.setText("00");
			} else {
				llTimeParentLayout.setVisibility(View.VISIBLE);
				setCountDownTime(countDownTimeList.get(position), tv_time_day,
						tv_time_day_name, tv_time_hour, tv_time_minitue,
						tv_time_second);
			}

			llTimeParentLayout.setVisibility(View.VISIBLE);
			linear_dqj.setVisibility(View.VISIBLE);
			linear_pb.setVisibility(View.VISIBLE);
			tv_dq.setVisibility(View.VISIBLE);
			linear_hot.setVisibility(View.VISIBLE);
			tv_hot.setText("拼单热度:" + bean.getFrozennum() + bean.getUnit());
			hot_tv.setText("/" + bean.getKnum() + bean.getUnit());
			currt_price_unit.setText(bean.getUnit());
			ViewUtil.setActivityPrice(tv_price_now,
					bean.getCprice() == null ? "0" : bean.getCprice());
			currt_price_unit.setText("/" + bean.getUnit());
			float proportion = (Float.parseFloat(bean.getFrozennum()))
					/ (float) (Integer.parseInt(bean.getKnum()));
			int progress = (int) (proportion * 100);
			Resources res = mcontext.getResources();
			pb.setProgressDrawable(res
					.getDrawable(R.drawable.progressbar_color_gray));
			pb.setProgress(progress);
			iv_pin.setVisibility(View.VISIBLE);

		} else if (bean.getType().equals("1")) {
			// 批发
			linear_pf.setVisibility(View.VISIBLE);
			linear_pd.setVisibility(View.GONE);

			iv_pin.setVisibility(View.INVISIBLE);
			tv_salenum.setVisibility(View.VISIBLE);
			llTimeParentLayout.setVisibility(View.GONE);
			// linear_price_tv.setVisibility(View.VISIBLE);
			if (!StringUtil.isEmpty(bean.getRecommend())
					&& "1".equals(bean.getRecommend())) {
				if (!StringUtil.isEmpty(recommend))
					iv_jinxuan.setVisibility(View.VISIBLE);
			} else {
				iv_jinxuan.setVisibility(View.GONE);
			}
		}
		currentdate = dataList.get(position).getCurrentnum();
		if (currentdate > 0) {
			if (dataList.get(position).getNum() > 0) {
				// 有库存
				if (currentdate > dataList.get(position).getNum()
						&& dataList.get(position).getNum() > Integer
								.parseInt(dataList.get(position).getMinnum())) {
					// 当前购买量大于库存
					iv_add.setVisibility(View.VISIBLE);
					iv_add.setEnabled(true);
					iv_add.setBackground(mcontext.getResources().getDrawable(
							R.drawable.list_yes));
					linear_reduce.setVisibility(View.VISIBLE);
					buy_edittext.setVisibility(View.VISIBLE);
					iv_reduce.setVisibility(View.VISIBLE);
					tv_knum.setVisibility(View.GONE);
					tv_notice_nogoods.setVisibility(View.VISIBLE);
					tv_notice_nogoods.setText("当前库存:"
							+ dataList.get(position).getNum());
				} else if (dataList.get(position).getNum() < Integer
						.parseInt(dataList.get(position).getMinnum())) {
					// 当前库存小于起批量
					iv_add.setVisibility(View.VISIBLE);
					iv_add.setEnabled(false);
					iv_add.setBackground(mcontext.getResources().getDrawable(
							R.drawable.list_no));
					iv_reduce.setBackground(mcontext.getResources()
							.getDrawable(R.drawable.jianhaohui));
					linear_reduce.setVisibility(View.VISIBLE);
					buy_edittext.setVisibility(View.VISIBLE);
					buy_edittext.setEnabled(false);
					iv_reduce.setEnabled(false);
					iv_reduce.setVisibility(View.VISIBLE);
					tv_knum.setVisibility(View.GONE);
					tv_notice_nogoods.setVisibility(View.VISIBLE);
					tv_notice_nogoods.setText("当前库存:" + bean.getNum()
							+ "\n起批量:" + bean.getMinnum());
				} else if (currentdate < Integer.parseInt(dataList
						.get(position).getMinnum())) {
					// 当前购买量小于最小起批量
					iv_add.setVisibility(View.VISIBLE);
					iv_add.setBackground(mcontext.getResources().getDrawable(
							R.drawable.list_yes));
					linear_reduce.setVisibility(View.VISIBLE);
					buy_edittext.setVisibility(View.VISIBLE);
					buy_edittext.setEnabled(true);
					iv_add.setEnabled(true);
					iv_reduce.setEnabled(true);
					iv_reduce.setVisibility(View.VISIBLE);
					tv_knum.setVisibility(View.GONE);
					tv_notice_nogoods.setVisibility(View.VISIBLE);
					tv_notice_nogoods.setText("最小起批量:"
							+ dataList.get(position).getMinnum());
				} else {
					// 正常情况
					iv_add.setVisibility(View.VISIBLE);
					iv_add.setBackground(mcontext.getResources().getDrawable(
							R.drawable.list_yes));
					linear_reduce.setVisibility(View.VISIBLE);
					buy_edittext.setVisibility(View.VISIBLE);
					buy_edittext.setEnabled(true);
					iv_add.setEnabled(true);
					iv_reduce.setEnabled(true);
					iv_reduce.setVisibility(View.VISIBLE);
					tv_knum.setVisibility(View.GONE);
					tv_notice_nogoods.setVisibility(View.GONE);
				}
			} else {
				// 库存不足 或者当前库存小于最小起批量
				KnumError(iv_reduce, buy_edittext, linear_reduce, iv_add,
						tv_knum, tv_notice_nogoods);
			}

		} else {
			// 当前购买量为0
			if (dataList.get(position).getNum() <= 0
					|| dataList.get(position).getNum() < Integer
							.parseInt(dataList.get(position).getMinnum())) {
				// 库存不足 或者当前库存小于最小起批量
				if (dataList.get(position).getNum() < Integer.parseInt(dataList
						.get(position).getMinnum())) {
					KnumError(iv_reduce, buy_edittext, linear_reduce, iv_add,
							tv_knum, tv_notice_nogoods);
					// }
				} else {
					KnumError(iv_reduce, buy_edittext, linear_reduce, iv_add,
							tv_knum, tv_notice_nogoods);
				}
			} else if (dataList.get(position).getNum() > 0) {
				// 库存大于0 正常
				iv_add.setVisibility(View.VISIBLE);
				iv_add.setEnabled(true);
				iv_add.setBackground(mcontext.getResources().getDrawable(
						R.drawable.list_yes));
				linear_reduce.setVisibility(View.GONE);
				iv_reduce.setVisibility(View.GONE);
				buy_edittext.setVisibility(View.INVISIBLE);
				tv_knum.setVisibility(View.GONE);
				tv_notice_nogoods.setVisibility(View.INVISIBLE);
			}

		}

		if ((!StringUtil.isEmpty(totalPrice)) && (!StringUtil.isEmpty(boxsize))) {
			if ("箱".equals(bean.getUnit())) {
				tv_unit_next_one_price.setText(bean.getCurrency() == null ? "¥"
						: bean.getCurrency() + bean.getUnivalent() + "/");
				tv_unit_next.setText(bean.getPerunit());
				tv_unit_next_one_price.setVisibility(View.VISIBLE);
				tv_unit_next.setVisibility(View.VISIBLE);
			} else {
				tv_unit_next.setVisibility(View.INVISIBLE);
				tv_unit_next_one_price.setVisibility(View.INVISIBLE);
			}
		}
		if ("2".equals(bean.getStatus())) {
			/*
			 * 已抢光
			 */
			iv_activity.setVisibility(View.VISIBLE);
			iv_activity.setImageResource(R.drawable.yiqiangguang);
		} else if ("4".equals(bean.getStatus())) {
			iv_activity.setVisibility(View.VISIBLE);
			iv_activity.setImageResource(R.drawable.yichengdan);
		} else {
			iv_activity.setVisibility(View.INVISIBLE);
		}
		// 已经售卖数量
		tv_salenum.setText("销量:" + bean.getSalesvol() + bean.getUnit());
		MyApplication.imageLoader.displayImage(bean.getPic(), iv_wineurl,
				MyApplication.options);
		Click(tv_wine_name, position, bean);
		Click(linear_pf, position, bean);
		Click(linear_pd, position, bean);
		Click(iv_wineurl, position, bean);
		return convertView;
	}

	// 库存不足
	@SuppressLint("NewApi")
	private void KnumError(final ImageView iv_reduce,
			final SoftEditText buy_edittext, LinearLayout linear_reduce,
			ImageView iv_add, TextView tv_knum, TextView tv_notice_nogoods) {
		// 库存不足
		tv_knum.setText("库存不足");
		tv_knum.setVisibility(View.VISIBLE);
		iv_add.setEnabled(false);
		iv_add.setBackground(mcontext.getResources().getDrawable(
				R.drawable.list_no));
		linear_reduce.setVisibility(View.GONE);
		buy_edittext.setVisibility(View.GONE);
		iv_reduce.setVisibility(View.GONE);
		tv_notice_nogoods.setVisibility(View.GONE);
	}

	class MyOnFocusChangeListener implements OnFocusChangeListener {
		SoftEditText buy_edittext;
		int index;
		String inputTextBefore;
		String inputTextAfter;

		MyOnFocusChangeListener(int index, SoftEditText buy_edittext) {
			this.buy_edittext = buy_edittext;
			this.index = index;
			clickTypeString = "textchange";
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (loginObj != null) {
//				if (loginObj.getRoleid().equals("1")
//						|| loginObj.getRoleid().equals("6")) {
					if (!hasFocus && index == (Integer) v.getTag()) {
						buy_edittext.setOnFocusChangeListener(null);
						// 失去焦点的时候
						inputTextAfter = buy_edittext.getText().toString();
						if (!StringUtil.isEmpty(inputTextAfter)
								&& !inputTextAfter.contains(".")) {
							nowNum = dataList.get(index).getCurrentnum();
							if (!inputTextAfter.equals(inputTextBefore)) {
								if (Long.parseLong(inputTextAfter) >= Long
										.parseLong(dataList.get(index)
												.getMinnum())
										&& Long.parseLong(inputTextAfter) <= dataList
												.get(index).getNum()) {
									// 如果当前输入的数据大于最小起批量 并且小于库存量 可以添加
									dataList.get(index).setCurrentnum(
											Integer.parseInt(inputTextAfter));
									AddToShoppcart(index, clickTypeString,
											false);
								} else if (Long.parseLong(inputTextAfter) >= Long
										.parseLong(dataList.get(index)
												.getMinnum())
										&& Long.parseLong(inputTextAfter) >= dataList
												.get(index).getNum()) {
									// 如果当前输入的数据 大于最小起批量 并且也大于库存量可以添加 添加到最大库存数
									ToastUtil.showToast(mcontext, "最大库存量是"
											+ dataList.get(index).getNum());
									dataList.get(index).setCurrentnum(
											dataList.get(index).getNum());
									buy_edittext.setText(dataList.get(index)
											.getCurrentnum() + "");
									AddToShoppcart(index, clickTypeString,
											false);
								} else if (Long.parseLong(inputTextAfter) < Long
										.parseLong(dataList.get(index)
												.getMinnum())
										&& Long.parseLong(inputTextAfter) <= dataList
												.get(index).getNum()) {
									ToastUtil.showToast(
											mcontext,
											"商品数量不能小于最小购买量"
													+ dataList.get(index)
															.getMinnum());
									dataList.get(index).setCurrentnum(
											Integer.parseInt(dataList
													.get(index).getMinnum()));
									buy_edittext.setText(dataList.get(index)
											.getCurrentnum() + "");
									/**
									 * 更新缓存商品数量
									 */
									AddToShoppcart(index, clickTypeString,
											false);
								}
								cancommit = true;
							}
						} else {
							ToastUtil.showToast(mcontext, "商品数量输入错误,请重新填写");
							cancommit = false;
						}
					} else if (index == (Integer) v.getTag()) {
						InputMethodManager in = (InputMethodManager) buy_edittext
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						in.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
						// 有焦点的时候
						if (!StringUtil.isEmpty(buy_edittext.getText()
								.toString())) {
							inputTextBefore = buy_edittext.getText().toString();
						}
					}
//				}
//				else {
//					ToastUtil.showToast(mcontext, "你的用户角色不能购买商品");
//				}
			}

		}
	}

	void Click(View view, final int position, final WholeListBean bean) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ToDetaile(position, bean);
			}
		});
	}

	// 到商品详情
	public void ToDetaile(int position, WholeListBean bean) {
		Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
		intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, dataList.get(position)
				.getId());
		intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, dataList.get(position)
				.getType());
		StartActivityUtil.startActivity((Activity) mcontext, intent);
		if (!"1".equals(bean.getRecommend())) {
			if ("1".equals(bean.getType())) {
				MobclickAgent.onEvent(mcontext, "C_SAL_INF_10");
			} else {
				MobclickAgent.onEvent(mcontext, "C_PD_INF_6");
			}
		} else {
			MobclickAgent.onEvent(mcontext, "C_PROM_LST_11");
		}
	}

	/**
	 * iv_reduce 减号 视图 buy_edittext 输入件数 视图 v 当前点击v 视图
	 * 
	 * */

	// 设置当前空间的位置
	void setAmationImage(SoftEditText buy_edittext, ImageView iv_reduce, View v) {
		this.buy_edittext = buy_edittext;
		this.iv_reduces = iv_reduce;
		this.v = v;
	}

	// 处理成2位小数点
	private float DoPrice(float ft) {
		int scale = 2;// 设置位数
		int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
		BigDecimal bd = new BigDecimal((double) ft);
		bd = bd.setScale(scale, roundingMode);
		ft = bd.floatValue();
		return ft;
	}

	/**
	 * 
	 * Describe:设置倒计时时间
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */
	private void setCountDownTime(long millisUntilFinished, TextView tvDay,
			TextView tv_time_day_name, TextView tvHour, TextView tvMinute,
			TextView tvSeconds) {
		long days, hours, minutes, seconds;
		if (millisUntilFinished < 0) {
			return;
		}
		if (millisUntilFinished <= 1000 * 60 * 60 * 24) {
			// tv_time_day_name.setVisibility(View.GONE);
			// tvDay.setVisibility(View.GONE);
			// 获取小时值
			days = millisUntilFinished / (60 * 60 * 1000 * 24);
			// 获取小时值
			hours = millisUntilFinished / (60 * 60 * 1000);
			// 获取分值
			minutes = (millisUntilFinished - hours * (60 * 60 * 1000))
					/ (60 * 1000);
			// 获取秒值
			seconds = (millisUntilFinished - hours * (60 * 60 * 1000) - minutes
					* (60 * 1000)) / 1000;
			if (days < 10) {
				tvDay.setText("0" + hours);
			} else
				tvDay.setText(hours + "");
			if (hours < 10) {
				tvHour.setText("0" + hours);
			} else
				tvHour.setText(hours + "");
			if (minutes < 10) {
				tvMinute.setText("0" + minutes);
			} else
				tvMinute.setText(minutes + "");
			if (seconds < 10) {
				tvSeconds.setText("0" + seconds);
			} else
				tvSeconds.setText(seconds + "");
		} else {
			// 获取天数
			days = millisUntilFinished / (60 * 60 * 1000 * 24);
			// 获取小时值
			hours = (millisUntilFinished - days * (60 * 60 * 1000 * 24))
					/ (60 * 60 * 1000);
			// 获取分值
			minutes = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)) - hours
					* (60 * 60 * 1000))
					/ (60 * 1000);
			// 获取秒值
			seconds = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)
					- hours * (60 * 60 * 1000) - minutes * (60 * 1000))) / 1000;
			if (days < 10) {
				tvDay.setText("0" + days);
			} else
				tvDay.setText(days + "");
			if (hours < 10) {
				tvHour.setText("0" + hours);
			} else
				tvHour.setText(hours + "");
			if (minutes < 10) {
				tvMinute.setText("0" + minutes);
			} else
				tvMinute.setText(minutes + "");
			if (seconds < 10) {
				tvSeconds.setText("0" + seconds);
			} else
				tvSeconds.setText(seconds + "");
		}
	}

}
