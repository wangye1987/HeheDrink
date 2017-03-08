package com.heheys.ec.controller.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
//import com.heheys.ec.controller.fragment.FindFragment;
import com.heheys.ec.controller.fragment.HomeFragment;
import com.heheys.ec.controller.fragment.OrderFragment;
import com.heheys.ec.controller.fragment.UserCenterFragment;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.NewShoppingCartFragmentAdapter;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewShoppingCartInfor;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.SuitListShopping;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.Bean;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.model.dataBean.VersionInitdatabean;
import com.heheys.ec.model.dataBean.VersionInitdatabean.VersionInfo.VersionBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.service.HeartService;
import com.heheys.ec.utils.ActivityManagerUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.UpdateOrNot;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.alibaba.mobileim.YWIMKit;

//import com.heheys.ec.controller.fragment.ShoppingCartFragment;
//import com.heheys.ec.utils.CheckUpdateServer;

/**
 * Describe:主页面
 * 
 * Date:2015-11-2
 * 
 * Author:liuzhouliang
 */
public class MainActivity extends BaseActivity {
	// fragment管理器
	private FragmentManager fragmentManager;
	// 主页页面
	private HomeFragment homeFragment;
	// 订单页面
	private OrderFragment orderFragment;
	// 购物车页面
//	public ShoppingCartFragment shoppingCartFragment;
	// 我的页面
	private UserCenterFragment userCenterFragment;
	// 发现页面
//	private FindFragment findFragment;
	private String tabTypeString;
	private MyHandler handler = new MyHandler(this);
	private RelativeLayout rlTabHomeParent, 
			rlTabMyParent, rlTabFindParent;
	public static RelativeLayout rlTabShoppingCartParent;
	public ImageView ivHome;
	public ImageView ivShoppingCart;
	public ImageView ivMy;
	public ImageView ivFind;
	private TextView tvHome, tvShoppingCart, tvMy, tvFind;
	public  Button btProductNum;
	public FragmentType currentFragmentType;
	public String isShowBack;
	public LinearLayout llTabParent;
	private MyReceiver receiver;
//	private YWIMKit mIMKit;
	private VersionInitdatabean versionInitdatabean;
	// 弹出系统弹框分支
	public AlertDialogCustom mdialog;
	public NewShoppingCartInforBean shoppingCartData;
	private RelativeLayout relative_parent;

	int x;
	int y;
	private void getLocation(View view){
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		 x = location[0];
		 y = location[1];
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getLocation(button_hidded_linear);
		if(ev.getX() < x || ev.getX() > (x + button_hidded_linear.getWidth()) || ev.getY() < y || ev.getY() > (y + button_hidded_linear.getHeight())){
			getLocation(linear_menu);
			//如果当前点击位置不在组件区域就隐藏菜单 否则执行view本身事件
//			if(ev.getX() < x || ev.getX() > (x + linear_menu.getWidth()) || ev.getY() < y || ev.getY() > (y + linear_menu.getHeight())){
//				//隐藏菜单
//				hiddenMenu(true);
//			}
		}else{
			//点击位置在view范围就显示菜单
			if(linear_menu.getVisibility() == View.GONE) {
				hiddenMenu(false);
				return false;
			}else{
				return super.dispatchTouchEvent(ev);
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	public enum FragmentType {
		HOME, SHOPPING_CAR, ORDER, USERCENTER, FIND

	}
	@Override
	protected void onCreate() {
		if(Build.VERSION.SDK_INT>21)
			CookieSyncManager.createInstance(this);
		setBaseContentView(R.layout.main_activity);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void SetNoTitleBar(){

		Window window = baseActivity.getWindow();
       //设置透明状态栏,这样才能让 ContentView 向上
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

     //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
		window.setStatusBarColor(getResources().getColor(R.color.color_ffd946));

		ViewGroup mContentView = (ViewGroup) baseActivity.findViewById(Window.ID_ANDROID_CONTENT);
		View mChildView = mContentView.getChildAt(0);
		if (mChildView != null) {
			//注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
			ViewCompat.setFitsSystemWindows(mChildView, false);
		}
	}

	@Override
	protected boolean hasTitle() {
		return false;
	}

	@Override
	protected void loadChildView() {
		initView();
		initData();
	}

	/**
	 * 
	 * Describe:获取要显示的TAB类型
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
	private void initData() {
		HaseTelMenu();
		HaseMenu();
		receiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.order.success");
		registerReceiver(receiver, intentFilter);
		currentFragmentType = FragmentType.HOME;
		fragmentManager = getSupportFragmentManager();
		showHomeFragment();

		tabTypeString = getIntent().getStringExtra(
				ConstantsUtil.MAIN_TAB_TYPE_KEY);
		isShowBack = getIntent().getStringExtra(
				ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY);
		mdialog = new AlertDialogCustom();
		if (IsLogin.isLogin(MainActivity.this)) {
			ResultBean resultbean = (ResultBean) SharedPreferencesUtil
					.getObject(this, "resultbean");
		}

		Intent i_getvalue = getIntent();
		String action = i_getvalue.getAction();
		if (Intent.ACTION_VIEW.equals(action)) {
			Uri uri = i_getvalue.getData();
			if (uri != null) {
				String id = uri.getQueryParameter("id");
				if(id != null) {
					LogUtil.d("Tag", "id=" + id);
					Intent intentLeft = new Intent(this,
							NewProductDetailActivity.class);
					intentLeft.putExtra(ConstantsUtil.PRODUCT_ID_KEY, id);// 17
					intentLeft.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "0");
					StartActivityUtil.startActivity(MainActivity.this, intentLeft);
				}
			}
		}
		islist = i_getvalue.getBooleanExtra("islist", false);
		if (islist) {
			startIndex = i_getvalue.getIntExtra("startIndex", 1);
			endIndex = i_getvalue.getIntExtra("endIndex", 10);
		}
		//获取购物车信息
		ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(
				baseActivity, new ShoppingCartInforRequestCallBack());
	}


private HeartService.HeartBinder heartbindler;

	private ServiceConnection seviceConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			heartbindler = (HeartService.HeartBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};


	public  class ShoppingCartInforRequestCallBack extends
			BaseJsonHttpResponseHandler<NewShoppingCartInforBean> {
		@SuppressWarnings("deprecation")
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewShoppingCartInforBean arg4) {
			DimissLoding();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			handler.sendMessage(message);
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
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = shoppingCartData.getError().getInfo();
			}
			handler.sendMessage(message);
			return shoppingCartData;
		}

		private void DimissLoding() {
				MainActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(ApiHttpCilent.loading !=null)
						ApiHttpCilent.loading.dismiss();
					}
				});
		}
	}

	/*
	 * 获取版本信息 是否可以升级 *
	 */
	class VersionCallBack extends
			BaseJsonHttpResponseHandler<VersionInitdatabean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, VersionInitdatabean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				VersionInitdatabean arg3) {
			Dimess();
		}

		@Override
		protected VersionInitdatabean parseResponse(String response,
				boolean arg1) throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			versionInitdatabean = gson.fromJson(response,
					VersionInitdatabean.class);
			Message message = Message.obtain();
			if ("1".equals(versionInitdatabean.getStatus())) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = versionInitdatabean.getError().getInfo();
			}
			handler.sendMessage(message);
			return versionInitdatabean;
		}
	}



//	private void loginService(String imaccount, String impassword) {
//		// TODO Auto-generated method stub
//		// 开始登录
//		String userid = imaccount;
//		String password = impassword;
//		IYWLoginService loginService = myApplication.getmIMKit()
//				.getLoginService();
//		YWLoginParam loginParam = YWLoginParam.createLoginParam(userid,
//				password);
//		loginService.login(loginParam,
//				new com.alibaba.mobileim.channel.event.IWxCallback() {
//					@Override
//					public void onSuccess(Object... arg0) {
//						// TODO Auto-generated method stub
//						// System.out.println("success");
//					}
//
//					@Override
//					public void onProgress(int arg0) {
//						// TODO Auto-generated method stub
//						// System.out.println("onprogress");
//					}
//
//					@Override
//					public void onError(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//						// System.out.println("error");
//					}
//				});
//	}

	/**
	 * 
	 * Describe:初始化控件资源
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
	private void initView() {
		relative_parent = (RelativeLayout) findViewById(R.id.relative_parent);
		llTabParent = (LinearLayout) findViewById(R.id.main_activity_tab_parent);
		btProductNum = (Button) findViewById(R.id.main_activity_product_num);
		tvHome = (TextView) findViewById(R.id.main_activity_tab_home_tv);
		tvShoppingCart = (TextView) findViewById(R.id.main_activity_tab_shopcart_tv);
		// tvOrder = (TextView) findViewById(R.id.main_activity_tab_order_tv);
		tvMy = (TextView) findViewById(R.id.main_activity_tab_my_tv);
		tvFind = (TextView) findViewById(R.id.main_activity_tab_find_tv);
		ivHome = (ImageView) findViewById(R.id.main_activity_tab_home_iv);
		ivShoppingCart = (ImageView) findViewById(R.id.main_activity_tab_shopcart_iv);
		// ivOrder = (ImageView) findViewById(R.id.main_activity_tab_order_iv);
		ivMy = (ImageView) findViewById(R.id.main_activity_tab_my_iv);
		ivFind = (ImageView) findViewById(R.id.main_activity_tab_find_iv);
		rlTabMyParent = (RelativeLayout) findViewById(R.id.main_activity_tab_my);
		// rlTabOrderParent = (RelativeLayout)
		// findViewById(R.id.main_activity_tab_order);
		rlTabShoppingCartParent = (RelativeLayout) findViewById(R.id.main_activity_tab_shopping_cart);
		rlTabFindParent = (RelativeLayout) findViewById(R.id.main_activity_tab_find);// 发现
		rlTabHomeParent = (RelativeLayout) findViewById(R.id.main_activity_tab_home);
		alertDialogUpdate = new AlertDialogCustom();// 初始化弹出框
//		setCheckedTab();

//		/**
//		 * 获取省市信息
//		 */
//		ApiHttpCilent.getInstance(baseContext).InitProvinceList(baseContext,
//				new MyCallBack());
		/**
		 * 获取版本信息是否升级
		 */
		/*
		 * ApiHttpCilent.getInstance(baseContext).initVersiondata(baseContext,
		 * new BaseinoCallBack());
		 */
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		//获取购物车信息
		ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(
						baseActivity, new ShoppingCartInforRequestCallBack());
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if(ev.getAction()== MotionEvent.ACTION_DOWN){
////			View view = getCurrentFocus();
////			if(view!=linear_menu && view != button_hidded){
////			}
//			if(linear_menu.getVisibility() == View.VISIBLE) {
//				linear_menu.setVisibility(View.GONE);
//				button_hidded.setVisibility(View.VISIBLE);
//			}
//		}
//
//		return super.dispatchTouchEvent(ev);
//	}
	/**
	 * 
	 * Describe:检测升级
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
//	private void checkUpdate() {
//		new CheckUpdateServer(this).checkUpdate();
//	}

	/**
	 * 
	 * Describe:设置TAB选中信息
	 * 
	 * Date:2015-10-26
	 * 
	 * Author:liuzhouliang
	 */
//	private void setCheckedTab() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		if (ConstantsUtil.MAIN_TAB_HOME.equals(tabTypeString)) {
//			map.put("home", "");
//			// MobclickAgent.onEvent(baseActivity, "0023", map);
//			initTab();
//			ivHome.setImageResource(R.drawable.tab_home_focus);
//			tvHome.setTextColor(getResources().getColor(R.color.red_text_color));
//			showHomeFragment();
//		} else if (ConstantsUtil.MAIN_TAB_SHOP_CAR.equals(tabTypeString)) {
//			map.put("Shopcart", "");
//			MobclickAgent.onEvent(baseActivity, "0027", map);
//			initTab();
//			ivShoppingCart.setImageResource(R.drawable.tab_shoppingcart_focus);
//			tvShoppingCart.setTextColor(getResources().getColor(
//					R.color.title_bg));
//			showShopcartFragment();
//		}
//		// else if (ConstantsUtil.MAIN_TAB_ORDER.equals(tabTypeString)) {
//		// map.put("Order","");
//		// MobclickAgent.onEvent(baseActivity, "0026", map);
//		// initTab();
//		// ivOrder.setImageResource(R.drawable.tab_order_focus);
//		// tvOrder.setTextColor(getResources().getColor(R.color.red_text_color));
//		// showOrderFragment();
//		// }
//		else if (ConstantsUtil.MAIN_TAB_USER.equals(tabTypeString)) {
//			map.put("my", "");
//			MobclickAgent.onEvent(baseActivity, "0028", map);
//			initTab();
//			ivMy.setImageResource(R.drawable.tab_usercenter_focus);
//			tvMy.setTextColor(getResources().getColor(R.color.red_text_color));
//			showUserCenterFragment();
//		} else {
//			map.put("home", "");
//			// MobclickAgent.onEvent(baseActivity, "0023", map);
//			initTab();
//			ivHome.setImageResource(R.drawable.tab_home_focus);
//			tvHome.setTextColor(getResources().getColor(R.color.red_text_color));
//			showHomeFragment();
//		}
//	}

	/**
	 * 
	 * Describe:显示主页面
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
	private void showHomeFragment() {
		// ViewUtil.hideKeyBoard(etInput, baseContext);
		currentFragmentType = FragmentType.HOME;
		FragmentTransaction transaction = fragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.out_alpha, 0);
		// 隐藏存在的fragment
//		hideFragment(transaction);

		if (homeFragment == null) {
			// 对象不存在
			homeFragment = new HomeFragment();
		}
		if (!homeFragment.isAdded()) {
			// 没有被添加过
			transaction.add(R.id.fragment_content, homeFragment)
					.commitAllowingStateLoss();
		} else {
			// 被添加过
			transaction.show(homeFragment).commitAllowingStateLoss();
		}
	}


	private static  final int  DOEN = 1;
	private static final int  UP = 2;
	/**
	 * 
	 * Describe:显示购物车FRAGMENT
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
//	private void showShopcartFragment() {
//		// ViewUtil.hideKeyBoard(etInput, baseContext);
//		currentFragmentType = FragmentType.SHOPPING_CAR;
//		transaction = fragmentManager.beginTransaction().setCustomAnimations(
//				R.anim.out_alpha, 0);
//		// 隐藏存在的fragment
//		hideFragment(transaction);
//
//		if (shoppingCartFragment == null) {
//			// 对象不存在
//			shoppingCartFragment = new ShoppingCartFragment(this,
//					ivShoppingCart);
//
//		}
//		if (!shoppingCartFragment.isAdded()) {
//			// 没有被添加过
//			transaction.add(R.id.fragment_content, shoppingCartFragment)
//					.commitAllowingStateLoss();
//			// if (ShoppingCartFragment.productListData != null
//			// && ShoppingCartFragment.productListData.size() > 0) {
//			// int num = 0 ;
//			// for(NewProductInfo info:ShoppingCartFragment.productListData){
//			// if(!NewShoppingCartFragmentAdapter.isShowActivityIcon(info.getStatus())){
//			// num = num +Integer.parseInt(info.getNum());
//			// }
//			// }
//			// if(num>0){
//			// btProductNum.setText(num + "");
//			// btProductNum.setVisibility(View.VISIBLE);
//			// }else{
//			// btProductNum.setVisibility(View.GONE);
//			// }
//			// } else {
//			// btProductNum.setVisibility(View.INVISIBLE);
//			// // ShoppingCartFragment.rlBottom.setVisibility(View.INVISIBLE);
//			// }
//		} else {
//			// 被添加过
//			transaction.show(shoppingCartFragment).commitAllowingStateLoss();
//			// if(NewShoppingCartFragmentAdapter.totalNum>0){
//			// btProductNum.setText(NewShoppingCartFragmentAdapter.totalNum +
//			// "");
//			// btProductNum.setVisibility(View.VISIBLE);
//			// }else{
//			// btProductNum.setVisibility(View.GONE);
//			// }
//			// ShoppingCartFragment.rlBottom.setVisibility(View.VISIBLE);
//		}
//		/*
//		 * //传页数值到购物车 Bundle bundle = new Bundle(); bundle.putInt("startIndex",
//		 * startIndex); bundle.putInt("endIndex", endIndex);
//		 * bundle.putBoolean("islist", islist);
//		 * shoppingCartFragment.setArguments(bundle);
//		 */
//	}

	// 过滤过期的活动
	public List<NewProductInfo> filterOutDateProductInfors(
			List<NewProductInfo> dataList) {
		ArrayList<NewProductInfo> filterList = new ArrayList<NewProductInfo>();
		filterList.addAll(dataList);
		Iterator<NewProductInfo> iterator = filterList.iterator();
		while (iterator.hasNext()) {
			String stateString = iterator.next().getStatus();
			if (NewShoppingCartFragmentAdapter.isShowActivityIcon(stateString)) {
				iterator.remove();
			}
		}

		return filterList;
	}

	public class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.order.success")) {
//				btProductNum.setVisibility(View.GONE);
			}
		}

	}

	/**
	 * 
	 * Describe:显示订单页面
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
//	private void showOrderFragment() {
//		// ViewUtil.hideKeyBoard(etInput, baseContext);
//		currentFragmentType = FragmentType.ORDER;
//		transaction = fragmentManager.beginTransaction().setCustomAnimations(
//				R.anim.out_alpha, 0);
//		// 隐藏存在的fragment
//		hideFragment(transaction);
//
//		if (orderFragment == null) {
//			// 对象不存在
//			orderFragment = new OrderFragment();
//
//		}
//		if (!orderFragment.isAdded()) {
//			// 没有被添加过
//			transaction.add(R.id.fragment_content, orderFragment)
//					.commitAllowingStateLoss();
//		} else {
//			// 被添加过
//			transaction.show(orderFragment).commitAllowingStateLoss();
//		}
//	}

	/**
	 * 
	 * Describe:显示发现页面
	 * 
	 * Date:2016-3-9
	 * 
	 * Author:wangkui
	 */
//	private void showFindFragment() {
//		// ViewUtil.hideKeyBoard(etInput, baseContext);
//		currentFragmentType = FragmentType.FIND;
//		transaction = fragmentManager.beginTransaction().setCustomAnimations(
//				R.anim.out_alpha, 0);
//		// 隐藏存在的fragment
//		hideFragment(transaction);
//
//		if (findFragment == null) {
//			// 对象不存在
//			findFragment = new FindFragment();
//
//		}
//		if (!findFragment.isAdded()) {
//			// 没有被添加过
//			transaction.add(R.id.fragment_content, findFragment)
//					.commitAllowingStateLoss();
//		} else {
//			// 被添加过
//			transaction.show(findFragment).commitAllowingStateLoss();
//		}
//	}

	/**
	 * 
	 * Describe:显示个人中心页面
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
//	private void showUserCenterFragment() {
//		// ViewUtil.hideKeyBoard(etInput, baseContext);
//		currentFragmentType = FragmentType.USERCENTER;
//		transaction = fragmentManager.beginTransaction().setCustomAnimations(
//				R.anim.out_alpha, 0);
//		// 隐藏存在的fragment
//		hideFragment(transaction);
//
//		if (userCenterFragment == null) {
//			// 对象不存在
//			userCenterFragment = new UserCenterFragment();
//
//		}
//		if (!userCenterFragment.isAdded()) {
//			// 没有被添加过
//			transaction.add(R.id.fragment_content, userCenterFragment)
//					.commitAllowingStateLoss();
//		} else {
//			// 被添加过
//			transaction.show(userCenterFragment).commitAllowingStateLoss();
//		}
//	}

	/**
	 * 
	 * Describe:隐藏所有的FRAGMENT
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
//	private void hideFragment(FragmentTransaction transaction) {
//		if (homeFragment != null) {
//			transaction.hide(homeFragment);
//		}
//		if (orderFragment != null) {
//			transaction.hide(orderFragment);
//		}
//		if (shoppingCartFragment != null) {
//			transaction.hide(shoppingCartFragment);
//		}
//		if (userCenterFragment != null) {
//			transaction.hide(userCenterFragment);
//		}
//		if (findFragment != null) {
//			transaction.hide(findFragment);
//		}
//	}

	private void Dimess() {
		MainActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

//	class MyCallBack extends BaseJsonHttpResponseHandler<ProvinceListBaseBean> {
//		@Override
//		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//				String arg3, ProvinceListBaseBean arg4) {
//			Dimess();
//		}
//
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, String arg2,
//				ProvinceListBaseBean arg3) {
//			Dimess();
//		}
//
//		@Override
//		protected ProvinceListBaseBean parseResponse(String response,
//				boolean arg1) throws Throwable {
//			// TODO Auto-generated method stub
//			Dimess();
//			Gson gson = new Gson();
//			ProvinceListBaseBean bean = gson.fromJson(response,
//					ProvinceListBaseBean.class);
//			Message message = Message.obtain();
//			if ("1".equals(bean.getStatus())) {// 正常
//				message.what = ConstantsUtil.HTTP_SUCCESS;
//				message.obj = bean.getResult();
//			} else {
//				message.what = ConstantsUtil.HTTP_FAILE;// 错误
//				message.obj = bean.getError().getInfo();
//			}
//			handler.sendMessage(message);
//
//			return bean;
//		}
//	}

	// 升级版本
	private void upgradeVersion() {
		if (versionInitdatabean != null
				&& versionInitdatabean.getResult() != null
				&& versionInitdatabean.getResult().getVersion() != null) {
			VersionBean versionBean = versionInitdatabean.getResult()
					.getVersion();
			// 获取当前app版本号
			int currtversion = Utils.getCurrentAppVersionCode(baseActivity);
			// 获取服务器版本号
			int serverversion = versionBean.getVersion();
			// 是否强制更新
			final boolean isfocuse = versionBean.getIsmust();
			// 升级地址
			final String appUrl = versionBean.getUrl();
			// 升级提示
			final String updateNotice = versionBean.getUpdateinfo();
			if (currtversion < serverversion) {
				if (alertDialogUpdate != null)
					alertDialogUpdate.Updategrade(MainActivity.this,
							updateNotice, new UpdateOrNot() {
								@Override
								public void setResult(int modle) {
									if (1 == modle) {
										if (alertDialogUpdate != null) {
											alertDialogUpdate.Demiss();
										}
										if (isfocuse)
											System.exit(0);
									} else {
										// 更新
										// ToastUtil.showToast(baseActivity,
										// "更新aspp");
										if (Environment
												.getExternalStorageState()
												.equals(Environment.MEDIA_MOUNTED)) {
											downFile(appUrl); // 在下面的代码段
										} else {
											Toast.makeText(baseActivity,
													"SD卡不可用，请插入SD卡",
													Toast.LENGTH_SHORT).show();
										}
									}
								}

							});
			}
		}
	}

	// 下载apk
	private void downFile(final String appUrl) {
		// pd = new ProgressDialog(baseActivity);
		// alertDialog.pb.setProgressDrawable(getResources().getColor(R.color.color_af2942));
		alertDialogUpdate.pb.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(appUrl);
				HttpResponse response;
				HttpEntity httpentry;
				FileOutputStream fo = null;
				InputStream in = null;
				try {
					response = httpclient.execute(httpget);
					httpentry = response.getEntity();
					int apksize = (int) httpentry.getContentLength();
					alertDialogUpdate.pb.setMax(apksize);
					in = httpentry.getContent();
					if (in != null) {
						File file = new File(
								Environment.getExternalStorageDirectory(),
								"hehe.apk");
						fo = new FileOutputStream(file);
						byte bytezie[] = new byte[1024];
						int ch;
						int lenthcurrt = 0;
						while ((ch = in.read(bytezie)) != -1) {
							// 如果还有就写入到本地文件
							fo.write(bytezie, 0, ch);
							lenthcurrt += ch;
							alertDialogUpdate.pb.setProgress(lenthcurrt);
						}
						fo.flush();// 刷新缓存
						if (fo != null)
							fo.close();

						if (in != null)
							in.close();
						InitApk();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void InitApk() {
		handler.post(new Runnable() {
			public void run() {
				alertDialogUpdate.Demiss();
				update();
			}
		});
	}

	// 安装文件，固定写法
	void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "hehe.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public class MyHandler extends WeakHandler<MainActivity> {

		public MyHandler(MainActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				// 存储省份 城市 地区 元数据
				Bean bean = (Bean) msg.obj;
				SharedPreferencesUtil.saveProvinceObject(
						getReference().baseActivity, bean);
				//存储地址
				MyApplication.getInstance().SetCity(bean);
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				// 获取版本信息成功
				upgradeVersion();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				// 获取购物车信息成功
				ShowShopping();
				break;
			case ConstantsUtil.HTTP_FAILE:
				break;
			default:
				break;
			}
		}

	}

	private void ShowShopping() {
		// TODO Auto-generated method stub
		int totalNum = 0;
		if(shoppingCartData !=null && shoppingCartData.getResult()!=null && shoppingCartData.getResult().getList()!=null){
		   int size = shoppingCartData.getResult().getList().size();
		   List<NewProductInfo> newProduce = shoppingCartData.getResult().getList();
		   if(size  >0){
		   //获取有效商品数
			for(NewProductInfo info : newProduce){
				if(!NewShoppingCartFragmentAdapter.isShowActivityIcon(info.getStatus())){
					if(info.getIssuit() != 2){
					 totalNum = totalNum + Integer.parseInt(StringUtil.isEmpty(info.getNum())?"0":info.getNum());
					}else{
						int itemNum = 0;
						List<SuitListShopping> listSuit = info.getSuitlist();
						for(SuitListShopping item : listSuit){
							itemNum = itemNum + Integer.parseInt(StringUtil.isEmpty(item.getNumPerSuit())?"1":item.getNumPerSuit());
						}
						totalNum = totalNum + Integer.parseInt(StringUtil.isEmpty(info.getNum())?"0":info.getNum()) * itemNum;
					}
				}
			}
			 if(totalNum>0){
				 menu_shoppingnum.setVisibility(View.VISIBLE);
//				btProductNum.setText(totalNum+"");
				 menu_shoppingnum.setText(totalNum+"");
			 }else{
				 menu_shoppingnum.setVisibility(View.INVISIBLE);
			 }
			}else{
			   menu_shoppingnum.setVisibility(View.INVISIBLE);
			}		
		}else{
			menu_shoppingnum.setVisibility(View.INVISIBLE);
		}
		
	}
	/*
	 * //获取基本信息 private void bindBaseInfo() { if(beanservice !=null &&
	 * beanservice.getResult() !=null ){ lineResult = beanservice.getResult();
	 * mainh5 = lineResult.getIndexurl();
	 * SharedPreferencesUtil.writeSharedPreferencesString(MainActivity.this,
	 * "baseinfo", "line", lineResult.getServiceline()); setCheckedTab(); } }
	 */
	@Override
	protected void getNetData() {
		// 获取升级数据
		ApiHttpCilent.getInstance(getApplicationContext()).initVersiondata(baseActivity,
				"android", new VersionCallBack());
		// 获取购物车数据
		// ApiHttpCilent.getInstance(baseActivity).getShoppingCartInfor(
		// baseActivity, new ShoppingCartInforRequestCallBack());
	}

	public static class ShoppingCartMessageHandler extends
			WeakHandler<MainActivity> {

		public ShoppingCartMessageHandler(MainActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				/**
				 * 处理成功获取购物车的数据
				 */
				if (msg.obj != null && msg.obj instanceof NewShoppingCartInfor) {
					// getReference().shoppingCartInfor = (NewShoppingCartInfor)
					// msg.obj;
					// getReference().productListData =
					// getReference().shoppingCartInfor
					// .getList();
					// if (getReference().productListData != null
					// && getReference().productListData.size() > 0) {
					// getReference().bindViewData();
					// mydateList = getReference().mAdapter.dataList;
					// getReference().lvListView.setVisibility(View.VISIBLE);
					// getReference().linear_no_login.setVisibility(View.GONE);
					// getReference().tvRight.setVisibility(View.VISIBLE);
					// getReference().tvRight.setText("编辑");
					// } else {
					/**
					 * 处理空数据
					 */
					// getReference().showShopCartEmpty();
					// getReference().tvRight.setVisibility(View.INVISIBLE);
					// getReference().linear_no_login.setVisibility(View.GONE);
					// }

				} else {
					/**
					 * 处理空数据
					 */
					// ToastUtil.showToast(getReference().baseActivity,
					// "请求数据为空");
					// 处理空白页
					// getReference().showShopCartEmpty();
					// getReference().linear_no_login.setVisibility(View.GONE);
				}

				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					// {"result":{},"error":{"info":"服务器异常。","code":"101"},"status":0}
					ToastUtil.showToast(getReference().baseActivity,
							messageString);
				}

				break;
			default:
				break;
			}
		}

	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		rlTabHomeParent.setOnClickListener(this);
		rlTabMyParent.setOnClickListener(this);
		rlTabShoppingCartParent.setOnClickListener(this);
		// rlTabOrderParent.setOnClickListener(this);
		rlTabFindParent.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		return null;
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
	public void onClick(View v) {
		// super.onClick(v);
		switch (v.getId()) {
		case R.id.main_activity_tab_home:
			/**
			 * 主界面
			 */
//			if (!(FragmentType.HOME == currentFragmentType)) {
//				currentFragmentType = FragmentType.HOME;
//				initTab();
//				ivHome.setImageResource(R.drawable.tab_home_focus);
//				tvHome.setTextColor(getResources().getColor(
//						R.color.red_text_color));
//				showHomeFragment();
//			}
			MobclickAgent.onEvent(baseActivity, "C_HMN_HMN_1");
			break;

		case R.id.main_activity_tab_shopping_cart:
			/**
			 * 购物车页面
			 */
//			if (!(FragmentType.SHOPPING_CAR == currentFragmentType)) {
//				currentFragmentType = FragmentType.SHOPPING_CAR;
//				initTab();
//				ivShoppingCart
//						.setImageResource(R.drawable.tab_shoppingcart_focus);
//				tvShoppingCart.setTextColor(getResources().getColor(
//						R.color.red_text_color));
//				showShopcartFragment();
//
//			}
			MobclickAgent.onEvent(baseActivity, "C_HMN_SC_1");
			break;
		case R.id.main_activity_tab_my:
			/**
			 * 个人中心页面
			 */
//			if (!(FragmentType.USERCENTER == currentFragmentType)) {
//				currentFragmentType = FragmentType.USERCENTER;
//				initTab();
//				ivMy.setImageResource(R.drawable.tab_usercenter_focus);
//				tvMy.setTextColor(getResources().getColor(
//						R.color.red_text_color));
//				showUserCenterFragment();
//			}
			MobclickAgent.onEvent(baseActivity, "C_HMN_MY_1");

			break;
		case R.id.main_activity_tab_find:
			/**
			 * 发现页面
			 */
//			if (!(FragmentType.FIND == currentFragmentType)) {
//				currentFragmentType = FragmentType.FIND;
//				initTab();
//				ivFind.setImageResource(R.drawable.tab_order_focus);
//				tvFind.setTextColor(getResources().getColor(
//						R.color.red_text_color));
//				showFindFragment();
//			}
			MobclickAgent.onEvent(baseActivity, "C_HMN_DIS_1");
			break;
		case R.id.base_activity_title_backicon:
			// 回传页码
			if (islist) {
				Intent intent = new Intent();
				intent.putExtra("startIndex", startIndex);
				intent.putExtra("endIndex", endIndex);
				setResult(RESULT_OK, intent);
			}
			finish();
		default:
			break;
		}
	}


	public class TimerTask extends java.util.TimerTask {
		private String point;
		TimerTask(String point){
			this.point = point;
		}
		@Override
		public void run() {
			heartbindler.SendLocation("发送"+point);
		}
	}
	/**
	 * 
	 * Describe:初始化TAB视图
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:liuzhouliang
	 */
	private void initTab() {
		ivHome.setImageResource(R.drawable.tab_home_not_focus);
		ivShoppingCart.setImageResource(R.drawable.tab_shoppingcart_not_focus);
		ivMy.setImageResource(R.drawable.tab_usercenter_not_focus);
		ivFind.setImageResource(R.drawable.tab_order_not_focus);
		tvHome.setTextColor(getResources().getColor(R.color.color_8f8f8f));
		tvShoppingCart.setTextColor(getResources().getColor(
				R.color.color_8f8f8f));
		tvMy.setTextColor(getResources().getColor(R.color.color_8f8f8f));
		tvFind.setTextColor(getResources().getColor(R.color.color_8f8f8f));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CookieSyncManager.getInstance().startSync();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		 CookieSyncManager.getInstance().stopSync(); 
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// TODO Auto-generated method stub
		int num = ActivityManagerUtil.getActivityManager().getActivityNum(
				MainActivity.class);
		if (num == 1) {
			exitApplication(keyCode, event);
		} else {
			super.onKeyDown(keyCode, event);
		}

		return true;
	}

	private long exitTime;
	private VelocityTracker velocityTracker;
	private boolean booleanresult;
	private String id;
	private FragmentTransaction transaction;
	private MyApplication myApplication;
	private AlertDialog alertDialog;
	private AlertDialogCustom alertDialogUpdate;
	private int startIndex;
	private int endIndex;
	private boolean islist;

	protected void exitApplication(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				ToastUtil.showToast(this,
						getResources().getString(R.string.exit_message));
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				ActivityManagerUtil.getActivityManager().exitApp(baseContext);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// velocityTracker.clear();
		// velocityTracker.recycle();
		unregisterReceiver(receiver);
		if(ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing()){
			ApiHttpCilent.loading.dismiss();
			ApiHttpCilent.loading = null;
		}
		super.onDestroy();
	}
//	
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		if(arg0 == 1000){
//			currentFragmentType = FragmentType.HOME;
//			initTab();
//			ivHome.setImageResource(R.drawable.tab_home_focus);
//			tvHome.setTextColor(getResources().getColor(
//					R.color.red_text_color));
//			showHomeFragment();
//		}
////		super.onActivityResult(arg0, arg1, arg2);
//	}
	
}