/*package com.heheys.ec.controller.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.animationManage.ProductDetailAnimation;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.CustomTimerTask;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.TimeUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.CustomViewPager;
import com.heheys.ec.lib.view.GridViewScrollview;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.PoductDetailBaseInforAdapter;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.PoductDetailBaseInforBean;
import com.heheys.ec.model.dataBean.ProductDetailInfor;
import com.heheys.ec.model.dataBean.ProductDetailInfor.ProductInfor;
import com.heheys.ec.model.dataBean.ProductDetailInfor.ProductInfor.ProductBaseInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
//import com.heheys.ec.thirdPartyModule.IM.IMHelper;
import com.heheys.ec.utils.ActivityManagerUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.utils.Rotate3dAnimation;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

*//**
 * 
 * Describe:商品详情页面
 * 
 * Date:2015-9-23
 * 
 * Author:liuzhouliang
 *//*

public class ProductDetail extends BaseActivity implements TextWatcher {
	private static final String TAG = ProductDetail.class.getName();
	// 图片浏览控件
	private CustomViewPager mViewPager;
	// 显示图片数量控件
	private TextView tvCurrentNum, tvTotalNum;
	// public String[] imageUrls = new String[] {
	// "http://store.baotime.com/attachments/2011/0524/351a2f05bc706da92299ce2e8c58ce21.jpg",
	// "http://img0.bdstatic.com/img/image/shouye/duorou1008.jpg",
	// "http://store.baotime.com/attachments/2011/0524/351a2f05bc706da92299ce2e8c58ce21.jpg",
	// "http://store.baotime.com/attachments/2011/0524/351a2f05bc706da92299ce2e8c58ce21.jpg",
	// "http://d.hiphotos.baidu.com/image/h%3D360/sign=13505bc7af18972bbc3a06ccd6cc7b9d/267f9e2f07082838dc76bbc7bc99a9014d08f1ee.jpg",
	// };
	public static int SCREENWIDTH;
	public static int SCREENHEIGHT;
	private RelativeLayout rlNumParentLayout;
	private TextView tvNormalPrice, tvActivityPrice;
	private ProductDetailInfor productDetailInfor;
	private ProductInfor productInfor;
	private ProductDetailMessageHandler productMessageHandler;
	private AddShoppingCartMessageHandler addShoppingCartMessageHandler;
	private String productIdString;
	private TextView tvProductDes, tvProductName, tvPriceRange, tvCanbuyNum,
			tvProductTotalNum;
	private TextView btAddShoppingCart;
	private AddShoppingCartBean addShoppingCartData;
	private EditText etNum;
	private List<String> pic;
	private PoductDetailBaseInforAdapter baseInforAdapter;
	private List<PoductDetailBaseInforBean> baseInforBeans;
	private GridViewScrollview gvBaseInfor;
	private ImageView ivReduce, ivAdd;
	// 最大购买量
	private int maxBuyNum;
	// 最小购买量
	private int minBuyNum;
	private TextView tvProductFreight, tvProductStandard;
	private RelativeLayout llBottomParent;
	private LinearLayout linear_reduce;
	private LinearLayout linear_add;
	private boolean isPush;
	// 倒计时控件
	private TextView tvDay,tvHour, tvMinute, tvSecond;
	private CustomTimerTask task;
	private LinearLayout llTimeParent;
	private TextView tvMinBuy;
	private RelativeLayout llVpNumParent;
	private Bitmap currentBitmap;
	private ImageView ivAnimation;
	private ImageView hechat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.product_detail);
	}

	@Override
	protected void onCreate() {
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadChildView() {
		ivAnimation = (ImageView) findViewById(R.id.product_detail_animation);
		llVpNumParent = (RelativeLayout) findViewById(R.id.product_detail_num_parent);
		tvMinBuy = (TextView) findViewById(R.id.product_detail_min_buy);
		llTimeParent = (LinearLayout) findViewById(R.id.product_detail_time_parent);
		tvDay = (TextView) findViewById(R.id.product_day);
		tvHour = (TextView) findViewById(R.id.product_hour);
		tvMinute = (TextView) findViewById(R.id.product_minute);
		tvSecond = (TextView) findViewById(R.id.product_second);
		llBottomParent = (RelativeLayout) findViewById(R.id.product_detail_bottom_parent);
		linear_reduce = (LinearLayout) findViewById(R.id.linear_reduce);
		linear_add = (LinearLayout) findViewById(R.id.linear_add);
		tvProductStandard = (TextView) findViewById(R.id.product_detail_standard);
		tvProductFreight = (TextView) findViewById(R.id.product_detail_freight);
		ivReduce = (ImageView) findViewById(R.id.product_detail_num_reduce);
		ivAdd = (ImageView) findViewById(R.id.product_detail_num_add);
		gvBaseInfor = (GridViewScrollview) findViewById(R.id.product_detail_baseinfor);
		tvProductTotalNum = (TextView) findViewById(R.id.product_detail_totoal_num);
		etNum = (EditText) findViewById(R.id.product_detail_et_num);
		btAddShoppingCart = (TextView) findViewById(R.id.product_detail_add_shoppingcart);
		tvCanbuyNum = (TextView) findViewById(R.id.product_detail_canbuy_num);
		tvPriceRange = (TextView) findViewById(R.id.product_detail_price_range);
		tvProductName = (TextView) findViewById(R.id.product_detail_name);
		tvProductDes = (TextView) findViewById(R.id.product_detail_describe);
		tvActivityPrice = (TextView) findViewById(R.id.product_detail_activity_price);
		tvNormalPrice = (TextView) findViewById(R.id.product_detail_normal_price);
		tvTotalNum = (TextView) findViewById(R.id.product_detail_totaltnum_tv);
		tvCurrentNum = (TextView) findViewById(R.id.product_detail_currentnum_tv);
		hechat = (ImageView) findViewById(R.id.hechat);
		mViewPager = (CustomViewPager) findViewById(R.id.product_detail_vp);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setFocusable(true);
		rlNumParentLayout = (RelativeLayout) findViewById(R.id.product_detail_num_parent);
		initData();
	}

	*//**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-9-23
	 * 
	 * Author:liuzhouliang
	 *//*
	private void initData() {
		// TODO Auto-generated method stub
		// AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
		// alpha.setDuration(0); // Make animation instant
		// alpha.setFillAfter(true); // Tell it to persist after the animation
		// ends
		// llBottomParent.startAnimation(alpha);
		llBottomParent.getBackground().setAlpha(204);
		rlNumParentLayout.getBackground().setAlpha(125);
		addShoppingCartMessageHandler = new AddShoppingCartMessageHandler(this);
		productMessageHandler = new ProductDetailMessageHandler(this);
		productIdString = getIntent().getStringExtra(
				ConstantsUtil.PRODUCT_ID_KEY);
		isPush = getIntent().getBooleanExtra("isPush", false);
		if (isPush)
			SharedPreferencesUtil.writeSharedPreferencesBoolean(this,
					"message", "new", false);
		// ToastUtil.showToast(baseContext, "活动ID" + productIdString);
	}

	@Override
	protected void getNetData() {
		ApiHttpCilent.getInstance(this).getProductDetailInfor(this,
				productIdString, new GetProductDetailRequestCallBack());
	}

	*//**
	 * 
	 * Describe:数据请求回调
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 *//*
	public class GetProductDetailRequestCallBack extends
			BaseJsonHttpResponseHandler<ProductDetailInfor> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ProductDetailInfor arg4) {
			ApiHttpCilent.loading.dismiss();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			productMessageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ProductDetailInfor arg3) {
		}

		@Override
		protected ProductDetailInfor parseResponse(String response, boolean arg1)
				throws Throwable {
			ApiHttpCilent.loading.dismiss();
			Gson gson = new Gson();
			productDetailInfor = gson.fromJson(response,
					ProductDetailInfor.class);
			Message message = Message.obtain();
			if ("1".equals(productDetailInfor.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = productDetailInfor.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = productDetailInfor.getError().getInfo();
			}
			productMessageHandler.sendMessage(message);
			return productDetailInfor;
		}

	}

	*//**
	 * 
	 * Describe:处理消息返回
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 *//*
	public static class ProductDetailMessageHandler extends
			WeakHandler<ProductDetail> {

		public ProductDetailMessageHandler(ProductDetail reference) {
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
				getReference().productInfor = getReference().productDetailInfor
						.getResult();
				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				*//**
				 * 处理失败的数据
				 *//*
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					*//**
					 * 处理失败数据
					 *//*
				} else {
				}

				break;
			}
		}

	}

	*//**
	 * 
	 * Describe:绑定控件数据
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 *//*
	private void bindViewData() {
		if (productInfor != null) {
			if (!StringUtil.isEmpty(productInfor.getKnum())
					&& !StringUtil.isEmpty(productInfor.getSoldnum())) {
				// 获取最大购买量
				maxBuyNum = (Integer.parseInt(productInfor.getKnum()) - Integer
						.parseInt(productInfor.getSoldnum()));
				LogUtil.d(TAG, "最大购买量" + maxBuyNum);
			}
			if (!StringUtil.isEmpty(productInfor.getMinuint())) {
				// 最小购买量
				minBuyNum = Integer.parseInt(productInfor.getMinuint());
				if (minBuyNum == 0 || minBuyNum < 0) {
					minBuyNum = 1;
				}
			} else {
				minBuyNum = 1;
			}
			tvMinBuy.setText(minBuyNum + productInfor.getUnit());
			// ToastUtil.showToast(baseContext, "maxBuyNum=" + maxBuyNum
			// + "minBuyNum==" + minBuyNum);
			tvProductFreight.setText(productInfor.getTransamount() + "元/"
					+ productInfor.getUnit());
			if (productInfor.getBaseinfo() != null) {
				tvProductStandard
						.setText(productInfor.getBaseinfo().getGuige());
			}

			etNum.setText(minBuyNum + "");
			tvPriceRange.setText("价格区间:" + "¥" + productInfor.getMinprice()
					+ "~" + productInfor.getMaxprice());
			tvProductDes.setText(productInfor.getDescription());
			tvProductName.setText(productInfor.getName());
			float currentPrice = productInfor.getCurrentprice();
			DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
			String currentPriceF = df.format(currentPrice);// 返回的是String类型
			ViewUtil.setActivityPrice(tvActivityPrice, currentPriceF);
			String content = tvActivityPrice.getText().toString();
			int index = content.indexOf(".");
			SpannableString sb = new SpannableString(tvActivityPrice.getText());
			sb.setSpan(new AbsoluteSizeSpan(15, true), index + 1, index + 3,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tvActivityPrice.setText(sb);
			ViewUtil.setNormalPrice(tvNormalPrice, productInfor.getPrice());
			if (!StringUtil.isEmpty(productInfor.getKnum())
					&& !StringUtil.isEmpty(productInfor.getSoldnum())) {
				tvCanbuyNum.setText(maxBuyNum + productInfor.getUnit());
				tvProductTotalNum.setText(productInfor.getKnum() + ""
						+ productInfor.getUnit());
			}
			if (productInfor.getBaseinfo() != null) {
				setBaseProductInfor(productInfor.getBaseinfo());
			}
			pic = productInfor.getPic();
			if (pic != null && pic.size() > 0) {
				mViewPager.setAdapter(new ImagePagerAdapter(pic));
				mViewPager.setCurrentItem(0);
				mViewPager.setOnPageChangeListener(new PicPageChangeListener());// 设置监听
				tvCurrentNum.setText("1");
				tvTotalNum.setText(pic.size() + "");
			}
			*//**
			 * 控制商品状态：是否活动结束
			 *//*
			String activityStateString = productInfor.getStatus();
			if (!StringUtil.isEmpty(activityStateString)) {
				if ("1".equals(activityStateString)) {
					*//**
					 * 已经发布
					 *//*
					btAddShoppingCart.setOnClickListener(this);
					btAddShoppingCart
							.setBackgroundResource(R.drawable.sharp_round_rec);
				} else if ("2".equals(activityStateString)) {
					*//**
					 * 已经抢光
					 *//*
					btAddShoppingCart
							.setBackgroundResource(R.drawable.bg_gray_corner_rec);
					btAddShoppingCart.setOnClickListener(null);
				} else if ("3".equals(activityStateString)) {
					*//**
					 * 已经结束
					 *//*
					btAddShoppingCart
							.setBackgroundResource(R.drawable.bg_gray_corner_rec);
					btAddShoppingCart.setOnClickListener(null);
				} else {
					btAddShoppingCart.setOnClickListener(this);
					btAddShoppingCart
							.setBackgroundResource(R.drawable.sharp_round_rec);
				}
			} else {
				btAddShoppingCart.setOnClickListener(this);
				btAddShoppingCart.setBackgroundResource(R.drawable.sharp_round_rec);
			}
			*//**
			 * 处理倒计时
			 *//*
			String endTime = productInfor.getEndtime();

			long appointTime = 0;
			if (!StringUtil.isEmpty(endTime)) {
				appointTime = TimeUtil.changeDateToTime(productInfor
						.getEndtime());
			}
			long currentTime = System.currentTimeMillis();
			long gapTime = appointTime - currentTime;
			if (gapTime > 0) {
				llTimeParent.setVisibility(View.VISIBLE);
				if (task == null) {
					task = new CustomTimerTask(tvDay,tvHour, tvMinute, tvSecond,
							gapTime, 1000);
					task.startTimer();
				} else {
					task.stopTimer();
					task = new CustomTimerTask(tvDay,tvHour, tvMinute, tvSecond,
							gapTime, 1000);
					task.startTimer();
				}

			} else {
				llTimeParent.setVisibility(View.GONE);
				// ToastUtil.showToast(baseActivity, "时间为0");
			}
		}

	}

	*//**
	 * 
	 * Describe:设置商品基础信息
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:liuzhouliang
	 *//*
	private void setBaseProductInfor(ProductBaseInfor baseInfor) {
		baseInforBeans = new ArrayList<PoductDetailBaseInforBean>();

		if (!StringUtil.isEmpty(baseInfor.getType7())) {
			*//**
			 * 香型不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("香型: ");
			obj.setContenString(baseInfor.getType7());
			baseInforBeans.add(obj);
		}

		if (!StringUtil.isEmpty(baseInfor.getMaterials())) {
			*//**
			 * 原料不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("原料: ");
			obj.setContenString(baseInfor.getMaterials());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getAlcohol())) {
			*//**
			 * 度数不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("度数: ");
			obj.setContenString(baseInfor.getAlcohol());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getType3())) {
			*//**
			 * 品类不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("品类: ");
			obj.setContenString(baseInfor.getType3());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getStorage())) {
			*//**
			 * 存储条件不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("存储条件: ");
			obj.setContenString(baseInfor.getStorage());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getType1())) {
			*//**
			 * 品牌不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("品牌: ");
			obj.setContenString(baseInfor.getType1());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getType2())) {
			*//**
			 * 产地不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("产地: ");
			obj.setContenString(baseInfor.getType2());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getType6())) {
			*//**
			 * 品类不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("品类: ");
			obj.setContenString(baseInfor.getType6());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(baseInfor.getContent())) {
			*//**
			 * 净含量不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("净含量: ");
			obj.setContenString(baseInfor.getContent());
			baseInforBeans.add(obj);
		}
		if (!StringUtil.isEmpty(productInfor.getFactory())) {
			*//**
			 * 厂家不为空
			 *//*
			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
			obj.setNameString("厂家: ");
			obj.setContenString(productInfor.getFactory());
			baseInforBeans.add(obj);
		}
		if (baseInforBeans != null && baseInforBeans.size() > 0) {
			*//**
			 * 绑定基础商品信息数据
			 *//*
			baseInforAdapter = new PoductDetailBaseInforAdapter(baseInforBeans,
					baseContext);
			gvBaseInfor.setAdapter(baseInforAdapter);
		}

	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		btAddShoppingCart.setOnClickListener(this);
		ivTitleRight.setOnClickListener(this);
		ivReduce.setOnClickListener(this);
		ivAdd.setOnClickListener(this);
		// etNum.addTextChangedListener(this);

		linear_reduce.setOnClickListener(this);
		linear_add.setOnClickListener(this);
		hechat.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		return "商品详情";
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
		return R.drawable.shopping_cart_white;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.product_detail_add_shoppingcart:
			*//**
			 * 添加购物车
			 *//*
			boolean isLogin = IsLogin.isLogin(this);
			if (isLogin) {
				addShopCarAnimation(ivAnimation, ivTitleRight, currentBitmap);
				try {
					if (!StringUtil.isEmpty(etNum.getText().toString())
							&& Long.parseLong(etNum.getText().toString()) > 0) {
						if (productInfor != null) {
							addShoppingCart(productInfor.getIid(), etNum
									.getText().toString());
							MobclickAgent.onEvent(baseContext, "AddToCart");// 统计加入购物车次数
							// 购买上面种类和数量
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("Name", productInfor.getName());// 购买商品名称
							map.put("num", etNum.getText().toString());// 购买商品的数量
							MobclickAgent.onEvent(baseContext, "purchase", map);
						}

					} else {
						// ToastUtil.showToast(baseContext, "最少购买" + minBuyNum);
						etNum.setText("" + minBuyNum);
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					addShoppingCart(productInfor.getIid(), etNum.getText()
							.toString());
				}
			} else {
				MyApplication.getInstance().startLogin(new LoginCallBack() {

					@Override
					public void loginSuccess() {
						try {
							if (!StringUtil.isEmpty(etNum.getText().toString())
									&& Long.parseLong(etNum.getText()
											.toString()) > 0) {
								if (productInfor != null) {
									addShoppingCart(productInfor.getIid(),
											etNum.getText().toString());
									MobclickAgent.onEvent(baseContext,
											"AddToCart");// 统计加入购物车次数
									// 购买上面种类和数量
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("Name", productInfor.getName());// 购买商品名称
									map.put("num", etNum.getText().toString());// 购买商品的数量
									MobclickAgent.onEvent(baseContext,
											"purchase", map);
								}

							} else {
								ToastUtil.showToast(baseContext, "最少购买"
										+ minBuyNum);
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							addShoppingCart(productInfor.getIid(), etNum
									.getText().toString());
						}
					}

					@Override
					public void loginFail() {
					}
				}, this);
			}

			break;
		case R.id.base_activity_title_right_righticon:
			*//**
			 * 进入购物车事件
			 *//*
			// ToastUtil.showToast(baseContext, ActivityManagerUtil
			// .getActivityManager().getActivityNum(MainActivity.class)
			// + "");
			// Intent intent = new Intent(this, MainActivity.class);
			// intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
			// ConstantsUtil.MAIN_TAB_SHOP_CAR);
			// // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// StartActivityUtil.startActivity(baseActivity, intent);
			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// TODO Auto-generated method stub

			// }
			// }).start();
			int num = ActivityManagerUtil.getActivityManager().getActivityNum(
					MainActivity.class);
			Intent intent = new Intent(ProductDetail.this, MainActivity.class);
			intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
					ConstantsUtil.MAIN_TAB_SHOP_CAR);
			intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
					ConstantsUtil.SHOPPING_CART_SHOW_BACK);
			if (num > 1) {
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			StartActivityUtil.startActivity(baseActivity, intent);
			break;

		case R.id.product_detail_num_reduce:
			*//**
			 * 商品减少事件
			 *//*
			Delete();
			break;
		case R.id.product_detail_num_add:
			*//**
			 * 商品增加事件
			 *//*
			Add();

			break;
		case R.id.linear_reduce:
			*//**
			 * 商品减少事件
			 *//*
			Delete();
			break;
		case R.id.linear_add:
			*//**
			 * 商品增加事件
			 *//*
			Add();
			break;
		case R.id.base_activity_title_backicon:
			if (isPush) {
				Intent i = new Intent(baseContext, GroupBuyListActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("isPush", true);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				
				onBackPressed();
			}
			break;
		case R.id.hechat:
//			if(IMHelper.getInstance().getYwIMKit()!=null){
////				Intent intents = IMHelper.getInstance().getYwIMKit().getChattingActivityIntent( new EServiceContact("3c4ed6b4f64985c1da9aa7d128544c93", 0));
////				startActivity(intents);
//				//userid是客服帐号，第一个参数是客服帐号，第二个是组ID，如果没有，传0
////				EServiceContact contact = new EServiceContact("喝喝云商服务中心:售前咨询1", 0);
////				EServiceContact contact = new EServiceContact("喝喝云商服务中心", 0);
//				//如果需要发给指定的客服帐号，不需要Server进行分流(默认Server会分流)，请调用EServiceContact对象
//				//的setNeedByPass方法，参数为false。
//				//contact.setNeedByPass(false);
////				Intent intentss = IMHelper.getInstance().getYwIMKit().getChattingActivityIntent(contact);
////				startActivity(intentss);
//			}
//				startActivity(IMHelper.getInstance().getYwIMKit().getChattingActivityIntent("hehekf@heheys.com"));
			//userid是客服帐号，第一个参数是客服帐号，第二个是组ID，如果没有，传0
			//如果需要发给指定的客服帐号，不需要Server进行分流(默认Server会分流)，请调用EServiceContact对象
			//的setNeedByPass方法，参数为false。
			//contact.setNeedByPass(false);
//			startActivity(IMHelper.getInstance().getYwIMKit().getChattingActivityIntent("喝喝云商服务中心"));
//			startActivity(IMHelper.getInstance().getYwIMKit().getChattingActivityIntent("13601160745"));
//			startActivity(IMHelper.getInstance().getYwIMKit().getChattingActivityIntent("18810662538"));
			break;
		default:
			break;
		}
	}

	// 返回键处理 //处理消息推送后退事件
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isPush) {
				Intent i = new Intent(baseContext, GroupBuyListActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			} else {
				onBackPressed();
			}
		}
		return false;
	}

	private void Add() {
		String numAdd = etNum.getText().toString().trim();
		if (!numAdd.equals("")) {
			long numAddInt;
			try {
				numAddInt = Long.parseLong(numAdd);
				if (maxBuyNum > 0) {
					if (numAddInt >= maxBuyNum) {
						ToastUtil.showToast(baseContext, "超过最大购买量" + maxBuyNum);
						etNum.setText(maxBuyNum + "");
					} else if (numAddInt < minBuyNum) {
						etNum.setText(minBuyNum + "");
					} else {
						numAddInt++;
						etNum.setText(numAddInt + "");
					}
				} else {
					numAddInt++;
					etNum.setText(numAddInt + "");
				}

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ToastUtil.showToast(baseContext, "超过最大购买量");
				etNum.setText(maxBuyNum + "");
			}

		} else {
			// LogUtil.e("数量为空");
			etNum.setText(minBuyNum + "");
		}
	}

	private void addShopCarAnimation(View startView, View endView, Bitmap bitmap) {
		*//**
		 * 获取开始动画，结束动画视图位置
		 *//*
		int[] startLocation = new int[2];
		int[] endLocation = new int[2];
		startView.getLocationInWindow(startLocation);
		endView.getLocationInWindow(endLocation);
		*//**
		 * 执行动画视图:ImageView
		 *//*
		ImageView animationImg = new ImageView(this);
		if (bitmap != null) {
			animationImg.setImageBitmap(bitmap);
		} else {
			Resources res = baseContext.getResources();
			Bitmap bmp = BitmapFactory.decodeResource(res,
					R.drawable.imageview_default);
			animationImg.setImageBitmap(bmp);
		}

		ProductDetailAnimation.getInstance().startAnimation(animationImg,endView,
				startLocation, endLocation, this);

	}

	private void Delete() {
		ToastUtil.showToast(baseContext, "未到达最小购买量:" + minBuyNum);
		String numReduce = etNum.getText().toString().trim();
		if (!numReduce.equals("")) {
			// LogUtil.e("减少数量");
			long numReduceInt;
			try {
				numReduceInt = Long.parseLong(numReduce);

				if (numReduceInt > 1) {
					LogUtil.e("减少数量" + numReduceInt);
					if (numReduceInt <= minBuyNum) {
						ToastUtil.showToast(baseContext, "未到达最小购买量");
						etNum.setText(minBuyNum + "");
					} else {
						numReduceInt--;
						etNum.setText(numReduceInt + "");
					}

				} else {
					ToastUtil.showToast(baseContext, "未到达最小购买量");
					etNum.setText(minBuyNum + "");
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				etNum.setText(minBuyNum + "");
			}

		}
	}

	*//**
	 * 
	 * Describe:添加购物车
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:liuzhouliang
	 *//*
	private void addShoppingCart(String sku, String num) {
		ApiHttpCilent.getInstance(this).addShoppingCart(this,"","1", num, 1,"1",
				new AddShoppingCartRequestCallBack());
	}

	public class AddShoppingCartRequestCallBack extends
			BaseJsonHttpResponseHandler<AddShoppingCartBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AddShoppingCartBean arg4) {
			ApiHttpCilent.loading.dismiss();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			addShoppingCartMessageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AddShoppingCartBean arg3) {
		}

		@Override
		protected AddShoppingCartBean parseResponse(String response,
				boolean arg1) throws Throwable {
			ApiHttpCilent.loading.dismiss();
			Gson gson = new Gson();
			addShoppingCartData = gson.fromJson(response,
					AddShoppingCartBean.class);
			Message message = Message.obtain();
			if ("1".equals(addShoppingCartData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				// message.obj = addShoppingCartData.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = addShoppingCartData.getError().getInfo();
			}
			addShoppingCartMessageHandler.sendMessage(message);
			return addShoppingCartData;
		}

	}

	public static class AddShoppingCartMessageHandler extends
			WeakHandler<ProductDetail> {

		public AddShoppingCartMessageHandler(ProductDetail reference) {
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
				// getReference().productInfor =
				// getReference().productDetailInfor
				// .getResult();
				// getReference().bindViewData();
				MyApplication.isRefreshShopcart = true;
				ToastUtil.showToast(getReference(), "加入购物车成功");
				break;
			case ConstantsUtil.HTTP_FAILE:
				*//**
				 * 处理失败的数据
				 *//*
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					*//**
					 * 处理失败数据
					 *//*
				} else {
				}
				MyApplication.isRefreshShopcart = false;
				break;
			}
		}

	}

	private class ImagePagerAdapter extends PagerAdapter {

		private List<String> images;
		private LayoutInflater inflater;

		ImagePagerAdapter(List<String> images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			// LogUtil.d(TAG,
			// "destroyItem==" + position + "====" + object.toString());
		}

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			// LogUtil.d(TAG, "instantiateItem==" + position + "====");
			View rootView = inflater.inflate(
					R.layout.product_detail_viewpager_item, view, false);
			assert rootView != null;
			ImageView ivImageView = (ImageView) rootView
					.findViewById(R.id.product_detail_viewpager_item_iv);
			final ProgressBar progressBar = (ProgressBar) rootView
					.findViewById(R.id.product_detail_viewpager_item_pb);
			progressBar.setVisibility(View.VISIBLE);
			MyApplication.imageLoader.displayImage(images.get(position),
					ivImageView, new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String imageUri, View view) {
							progressBar.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							progressBar.setVisibility(View.GONE);
							currentBitmap = loadedImage;
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
							progressBar.setVisibility(View.GONE);
						}
					});
			mViewPager.setObjectForPosition(rootView, position);
			view.addView(rootView, 0);
			return rootView;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// LogUtil.d(TAG, "isViewFromObject==");
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			// LogUtil.d(TAG, "restoreState==");
		}

		@Override
		public Parcelable saveState() {
			// LogUtil.d(TAG, "saveState==");
			return null;
		}

	}

	class PicPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			tvCurrentNum.setText(arg0 + 1 + "");

			int mCenterX = llVpNumParent.getWidth() / 2;
			int mCenterY = llVpNumParent.getHeight() / 2;
			Rotate3dAnimation rotation = new Rotate3dAnimation(180, 0,
					mCenterX, mCenterY, 0, false);
			rotation.setDuration(1000);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			llVpNumParent.startAnimation(rotation);
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		// ToastUtil.showToast(baseContext, s.toString());
		if (minBuyNum > 0) {
			if (minBuyNum > maxBuyNum) {
				return;
			}
			if (StringUtil.isEmpty(s.toString())) {
				etNum.setText(minBuyNum + "");
			} else {
				if (maxBuyNum > 0) {
					int num = Integer.parseInt(s.toString());
					if (num > maxBuyNum) {
						*//**
						 * 输入数值大于最大购买量
						 *//*
						etNum.setText(maxBuyNum + "");
					} else if (num < minBuyNum) {
						*//**
						 * 输入数值小于最小购买量
						 *//*
						etNum.setText(minBuyNum + "");
					}
				} else {
					int num = Integer.parseInt(s.toString());
					if (num < minBuyNum) {
						*//**
						 * 输入数值小于最小购买量
						 *//*
						etNum.setText(minBuyNum + "");
					}
				}

			}
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
*/