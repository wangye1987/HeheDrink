package com.heheys.ec.application;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.heheys.ec.R;
import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.BrandBaseBean;
import com.heheys.ec.model.dataBean.CityListBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.client.CookieStore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//import com.alibaba.wxlib.util.SysUtil;

/**
 *
 * Describe:应用入口
 *
 * Date:2015-9-22
 *
 * Author:liuzhouliang
 */

public class MyApplication extends Application {
	public static boolean debugToggle = false;
	private String TAG = getClass().getName();
	private static MyApplication mApplication = new MyApplication();
	public static ImageLoader imageLoader;
	public static DisplayImageOptions options;
	public static DisplayImageOptions optionsGoodsDetail;
	public static DisplayImageOptions options_gray;
	public static DisplayImageOptions options_white;
	public static DisplayImageOptions options_null;
	public static DisplayImageOptions options_headcircle;
	public static DisplayImageOptions options_banner;
	public static ImageLoadingListener animateFirstListener;
	public boolean isLogin = false;
	public CallbackRefresh refresh;
	public LoginCallBack loginCallBack;
	ResultBean resultbea;
	//	private YWIMKit mIMKit;
	public static BDLocation location;
	/**
	 * 百度定位
	 */
	public static LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public TextView mLocationResult, logMsg, tvCurrentCity;
	private MyLocationConfiguration.LocationMode tempMode = MyLocationConfiguration.LocationMode.NORMAL;
	//	private com.baidu.location.LocationClientOption.LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "bd09ll";
	public String locationCity;
	public String CityId;
	public static String currentCheckCity;
	//	public static boolean isRefreshShopcart;
	private static CityListBean.CityDataList.CityData checkCityInfor;
	public static final String APP_KEY = "23319080";
	//	private Boolean booleanresult;
	private static Context sContext;
	private String serviceline;//热线电话
	private String serverurl;//货到付款帮助
	private String groupHelpUrl;//合伙买帮助
	private String oid;//微信支付oid 通过oid查询结果
	private List<NewOrderBaseBean.OrderItemList> list;//微信支付list
	private CookieStore cookieStore;
	//微信支付时候使用 判断是订单 还是喝喝币充值 微信支付
	public boolean isOrder;
	public String GetCityId(){
		return CityId;
	}
	public void SetCityId(String CityId){
		this.CityId = CityId;
	}
	public BDLocation getLocation() {
		return location;
	}

	public String getHhmurl() {
		return groupHelpUrl;
	}

	public void setHhmurl(String groupHelpUrl) {
		this.groupHelpUrl = groupHelpUrl;
	}

	public void setLocation(BDLocation location) {
		this.location = location;
	}

	public String getServerurl() {
		return serverurl;
	}

	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		initData();
	}

	public boolean isOrder() {
		return isOrder;
	}

	public void setOrder(boolean order) {
		isOrder = order;
	}
//	public List<NewOrderBaseBean.OrderItemList> getList() {
//		return list;
//	}
//
//
//	public void setList(List<NewOrderBaseBean.OrderItemList> list) {
//		this.list = list;
//	}


	public String getOid() {
		return oid;
	}


	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getServiceline() {
		return serviceline;
	}

	public void setServiceline(String serviceline) {
		this.serviceline = serviceline;
	}
//	public YWIMKit getmIMKit() {
//		return mIMKit;
//	}
//
//	public void setmIMKit(YWIMKit mIMKit) {
//		this.mIMKit = mIMKit;
//	}

	//	public ResultBean getResultbea() {
//		return resultbea;
//	}
//	public void setResultbea(ResultBean resultbea) {
//		this.resultbea = resultbea;
//	}
	public static Context getContext(){
		return sContext;
	}
	public  ProvinceListBaseBean.Bean bean;
	public static final synchronized MyApplication getInstance() {
		if (mApplication == null) {
			mApplication = new MyApplication();
		}
		return mApplication;

	}
	public ProvinceListBaseBean.Bean GetCity(){
		return 	bean;
	}
	public  void SetCity(ProvinceListBaseBean.Bean bean){
		this.bean = bean;
	}
	public static CityListBean.CityDataList.CityData getCheckCityInfor() {
		return checkCityInfor;
	}

	public static void setCheckCityInfor(CityListBean.CityDataList.CityData checkCityInfor) {
		MyApplication.checkCityInfor = checkCityInfor;
	}
//	public LocationClient setLocationClient(LocationClient mLocationClient){
//		return this.mLocationClient = mLocationClient;
//	}
//public LocationClient getLocationClient(){
//	return mLocationClient;
//}
	/**
	 *
	 * Describe:初始化数据
	 *
	 * Date:2015-10-21
	 *
	 * Author:liuzhouliang
	 */
	private void initData() {
		SDKInitializer.initialize(this.getApplicationContext());
		InitRequestLocation();
		try {
			SecurityInit.Initialize(getApplicationContext());
		} catch (JAQException e) {
//			Log.e(TAG, "errorCode =" + e.getErrorCode());
		}

		initImageLoader();
		// 初始化个推服务
		PushManager.getInstance().initialize(this.getApplicationContext());
		//微信分享
		initWeiXinPay(this.getApplicationContext());


//		initIMBaseConfing();
	}


	public void InitRequestLocation(){
		mLocationClient = new LocationClient(this.getApplicationContext());
//		setLocationClient(mLocationClient);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener((BDLocationListener) mMyLocationListener);
		initLocation();
		mLocationClient.start();
		// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		mLocationClient.requestLocation();
	}
	//初始化IM
//	private void initIMBaseConfing() {
//		// TODO Auto-generated method stub
//		//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
//		SysUtil.setApplication(this);
//		if(SysUtil.isTCMSServiceProcess(this)){
//		return;
//		}
//		//第一个参数是Application Context
//		//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
//		if(SysUtil.isMainProcess()){
//			YWAPI.init(this, ConstantsUtil.APP_KEY);
//		}
//		InitHelper.initYWSDK(this);
//		//初始化云旺SDK
////		mIMKit = null;
//
//	}

	//	private boolean mustRunFirstInsideApplicationOnCreate() {
////		必须的初始化
////		SysUtil.setApplication(this);
////		sContext = getApplicationContext();
////		return SysUtil.isTCMSServiceProcess(sContext);
//	}
	//将app注册到微信
	private void initWeiXinPay(Context mContext) {
		msgApi = WXAPIFactory.createWXAPI(mContext, null);
		// 将该app注册到微信
		try {
			if(SecurityInit.Initialize(this) == 0){
				SecurityCipher securityCipher = new SecurityCipher(this);
				String wx_later_wx = securityCipher.decryptString(ConstantsUtil.APP_ID,ConstantsUtil.JAQ_KEY);
				msgApi.registerApp(wx_later_wx);
			}else{
				msgApi.registerApp(ConstantsUtil.APP_ID_WX);
			}
		} catch (JAQException e) {
			// TODO Auto-generated catch block
			msgApi.registerApp(ConstantsUtil.APP_ID_WX);
			e.printStackTrace();
		}
	}



//	public boolean isLogin() {
//		return isLogin;
//	}
//
//	public void setLogin(boolean isLogin) {
//		this.isLogin = isLogin;
//	}

	public void setLister(CallbackRefresh refresh) {
		this.refresh = refresh;
	}

	public CallbackRefresh getRefresh() {
		return refresh;
	}

	public void setRefresh(CallbackRefresh refresh) {
		this.refresh = refresh;
	}

	/**
	 *
	 * Describe:初始化图片加载库
	 *
	 * Date:2015-9-22
	 *
	 * Author:liuzhouliang
	 */
	@SuppressWarnings("deprecation")
	private void initImageLoader() {
		imageLoader = ImageLoader.getInstance();
		options_banner = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.imageview_default)
				.showImageForEmptyUri(R.drawable.imageview_default)
				.showImageOnFail(R.drawable.imageview_default)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		options_headcircle = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.list_user_default)
				.showImageForEmptyUri(R.drawable.list_user_default)
				.showImageOnFail(R.drawable.list_user_default)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		optionsGoodsDetail = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.imageview_default)
				.showImageForEmptyUri(R.drawable.imageview_default)
				.showImageOnFail(R.drawable.imageview_default)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.imageview_default)
				.showImageForEmptyUri(R.drawable.imageview_default)
				.showImageOnFail(R.drawable.imageview_default)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		options_gray = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.imageview_default)
				.showImageForEmptyUri(R.drawable.imageview_default)
				.showImageOnFail(R.drawable.imageview_default)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		options_white = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.imageview_default)
				.showImageForEmptyUri(R.drawable.imageview_default)
				.showImageOnFail(R.drawable.imageview_default)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		options_null = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 防止内存溢出的
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.build();
		animateFirstListener = new AnimateFirstDisplayListener();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this.getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)// 加载图片的线程数
				.denyCacheImageMultipleSizesInMemory() // 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置磁盘缓存文件名称
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置加载显示图片队列进程
				.writeDebugLogs().build();
		imageLoader.init(config);
	}

	/**
	 *
	 * Describe:图片加载状态监听
	 *
	 * Date:2015-9-22
	 *
	 * Author:liuzhouliang
	 */
	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
									  Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public interface CallbackRefresh {
		public void back();
	}

	/**
	 *
	 * Describe:调用登陆页面
	 *
	 * Date:2015-10-21
	 *
	 * Author:liuzhouliang
	 */
	public void startLogin(LoginCallBack callBack, Activity activity) {
		loginCallBack = callBack;
		Intent intent = new Intent(activity, LoginActivity.class);
		StartActivityUtil.startActivity(activity, intent);
	}

	/**
	 *
	 * Describe:登录后的回调
	 *
	 * Date:2015-10-20
	 *
	 * Author:liuzhouliang
	 */

	public interface LoginCallBack {
		public void loginSuccess();

		public void loginFail();
	}

	/**
	 *
	 * Describe:初始化定位信息
	 *
	 * Date:2015-10-21
	 *
	 * Author:liuzhouliang
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(tempMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType(tempcoor);// 可选，默认gcj02，设置返回的定位结果坐标系，
		int span = 3000;
		try {
			span = Integer.valueOf(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(5000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现实时位置回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			if (StringUtil.isEmpty(location.getCity())) {
				locationCity = "定位失败";
			} else {
				currentCheckCity = location.getCity();
				locationCity = location.getCity();

			}
			logMsg(location.getCity());
			setLocation(location);
//			Log.i("BaiduLocationApiDem", sb.toString());
//			mLocationClient.stop();
		}

	}


	/**
	 * 显示请求字符串
	 *
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
			if (tvCurrentCity != null) {
				mLocationResult.setText(currentCheckCity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  ResultBean getUserInfor() {
		// ResultBean loginObj = (ResultBean) com.heheys.ec.lib.utils.FileManager
		// .getObject(this, ConstantsUtil.LOGIN_OBJ_NAME);
		// return loginObj;
		ResultBean loginObj = (ResultBean) SharedPreferencesUtil.getObject(
				mApplication, "resultbean");
		return loginObj;
	}
	public void setUserInfo(ResultBean resultbea){
		SharedPreferencesUtil.saveObject(mApplication, resultbea, "resultbean");
	}
	/**
	 *
	 * Describe:清除登陆信息
	 *
	 * Date:2015-11-8
	 *
	 * Author:liuzhouliang
	 */
	public void clearLoginInor(Context mContext) {
		SharedPreferencesUtil.saveObject(mContext, "",
				"resultbean");
		SharedPreferencesUtil.writeSharedPreferencesBoolean(
				mContext, "login", "islogin", false);
	}

	private List<BrandBaseBean.BrandBean> listbrand;//品牌
	private List<PlaceNameBaseBean.Region> listplace;//产区
	//	public YWIMKit mIMKit;
	public IWXAPI msgApi;//微信支付API对象



	public List<BrandBaseBean.BrandBean> getListbrand() {
		return listbrand;
	}

	public void setListbrand(List<BrandBaseBean.BrandBean> listbrand) {
		this.listbrand = listbrand;
	}

	public List<PlaceNameBaseBean.Region> getListplace() {
		return listplace;
	}

	public void setListplace(List<PlaceNameBaseBean.Region> listplace) {
		this.listplace = listplace;
	}
}
