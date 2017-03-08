package com.heheys.ec.thirdPartyModule.payModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.controller.activity.HeheMoneyListActivity;
import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.controller.activity.MipcaActivityCapture;
import com.heheys.ec.controller.activity.MyPointsActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.JDpayBean;
import com.heheys.ec.model.dataBean.PayCouponbean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.UrlsUtil;
import com.heheys.ec.service.HeartService;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.BuildConfig;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-13 下午2:35:11
 *          类说明
 * @param京东支付
 */
public class JDPayActivity extends BaseActivity {

    private Handler handler = new MyHandler(this);
    private Context context;
    private String form;
    private WebView webView;
    private int address;
    private int paytype;
    private int flag;
    private int flag_mode;
    private LinearLayout linear_null;
    private WebSettings ws;
    private String linkurl;
    private PayCouponbean bean;//回调优惠券的id和title
    private String title = "";
    private String code;//支付验证码
    private String aid;
    //退出登录
    private BaseBean loginBean;
    private LocationClient locationClient;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
    }

    /**
     * 生成京东订单必要信息支付
     * 调取支付接口 支付
     */
    private void creatOrder(String orderId) {
        if (ToastNoNetWork.ToastError(context))
            return;
        flag = 2;
        ApiHttpCilent.getInstance(context).GetOrderInfo(context, orderId, code, new MyCallBack());
    }

    private void Dimess() {
        JDPayActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }

    class MyOrderCallBack extends BaseJsonHttpResponseHandler<CreatebaseOrderBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, CreatebaseOrderBean arg4) {
            Dimess();
        }

        @Override

        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              CreatebaseOrderBean arg3) {
            Dimess();
        }


        @Override
        protected CreatebaseOrderBean parseResponse(String response, boolean arg1)
                throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            CreatebaseOrderBean bean = gson.fromJson(response, CreatebaseOrderBean.class);
            Message message = Message.obtain();
            if ("1".equals(bean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = bean.getResult().getOid();
            } else {
                if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError().getCode())) {
                    message.what = ConstantsUtil.HTTP_NEED_LOGIN;
                } else {
                    message.what = ConstantsUtil.HTTP_FAILE;// 错误
                    message.obj = bean.getError().getInfo();
                }
            }
            handler.sendMessage(message);
            return bean;
        }
    }

    class MyCallBack extends BaseJsonHttpResponseHandler<JDpayBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, JDpayBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE_CONNECTTIMEOUT;
            handler.sendMessage(message);
        }

        @Override

        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              JDpayBean arg3) {
            Dimess();
        }


        @Override
        protected JDpayBean parseResponse(String response, boolean arg1)
                throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            JDpayBean bean = gson.fromJson(response, JDpayBean.class);
            Message message = Message.obtain();
            if ("1".equals(bean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = bean.getResult().getForm();
            } else {
                if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError().getCode())) {
                    message.what = ConstantsUtil.HTTP_NEED_LOGIN;
                } else {
                    message.what = ConstantsUtil.HTTP_FAILE;// 错误
                    message.obj = bean.getError().getInfo();
                }
            }
            handler.sendMessage(message);
            return bean;
        }
    }

    public class MyHandler extends WeakHandler<JDPayActivity> {
        @SuppressLint("HandlerLeak")
        public MyHandler(JDPayActivity reference) {
            super(reference);
        }

        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    webView.loadDataWithBaseURL(null, (String) msg.obj, "text/html", "utf-8", null);
                    break;
                case ConstantsUtil.HTTP_NEED_LOGIN:
                    StartActivityUtil.startActivity((Activity) context,
                            LoginActivity.class);
                    break;
                case ConstantsUtil.HTTP_FAILE:
//				String back = (String) msg.obj;
//				ToastUtil.showToast(context, back);
                    //错误也加载表单
                    webView.loadDataWithBaseURL(null, (String) msg.obj, "text/html", "utf-8", null);
                    getReference().linear_null.setVisibility(View.VISIBLE);
                    getReference().webView.setVisibility(View.GONE);
                    break;
                case ConstantsUtil.HTTP_FAILE_CONNECTTIMEOUT:
                    ToastUtil.showToast(baseContext, "连接超时");
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LATER:
                    //退出成功 切换账号
                    StartActivityUtil.startActivity((Activity) context,
                            LoginActivity.class);
                    finish();
                    break;
                case ConstantsUtil.HTTP_FAILE_LOGIN:
                    //退出失败 切换账号
//				ToastUtil.showToast(baseContext, "切换账号失败,请重试");
                    ErrorBean errorInfo = (ErrorBean) msg.obj;
                    if (errorInfo != null && !StringUtil.isEmpty(errorInfo.getInfo())) {
                        ToastUtil.showToast(getReference(), errorInfo.getInfo());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    protected final class LoadHtml {
        public void returnValue(final String htmls) {
        }
    }

    public class mWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

//		  @Override
        //播放网络视频时全屏会被调用的方法
//		  public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
//		             setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		  videowebview.setVisibility(View.GONE);
//		  //如果一个视图已经存在，那么立刻终止并新建一个
//		  if (xCustomView != null) {
//		                 callback.onCustomViewHidden();
//		  return;
//		  }
//		 videoview.addView(view);
//		  xCustomView = view;
//		  xCustomViewCallback = callback;
//		  videoview.setVisibility(View.VISIBLE);
//		  }

//		 @Override
        //视频播放退出全屏会被调用的
//		  public void onHideCustomView() {
//		 if (xCustomView == null)//不是全屏播放状态
//		  return;
//		  // Hide the custom view.
//		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		  xCustomView.setVisibility(View.GONE);
//		  // Remove the custom view from its container.
//		  videoview.removeView(xCustomView);
//		  xCustomView = null;
//		  videoview.setVisibility(View.GONE);
//		  xCustomViewCallback.onCustomViewHidden();
//		  videowebview.setVisibility(View.VISIBLE);
//		  }

        // //视频加载添加默认图标
        // @Override
        // public Bitmap getDefaultVideoPoster() {
        // //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
        // if (xdefaltvideo == null) {
        // xdefaltvideo = BitmapFactory.decodeResource(getResources(), R.drawable.seach_icon);
        // }
        // return xdefaltvideo;
        // }

        //视频加载时进程loading
        // @Override
        // public View getVideoLoadingProgressView() {
        // //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
        // if (xprogressvideo == null) {
        // LayoutInflater inflater = LayoutInflater.from(PlayVideoWebViewActivity.this);
        // xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
        // }
        // return xprogressvideo;
        // }

        //网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            (JDPayActivity.this).setTitle(title);
        }
    }

    //退出登录和基本信息
    class LoginOutCallBack extends BaseJsonHttpResponseHandler<BaseBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, BaseBean arg4) {
            Dimess();
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              BaseBean arg3) {
            Dimess();
        }

        @Override
        protected BaseBean parseResponse(String response, boolean arg1)
                throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            loginBean = gson.fromJson(response, BaseBean.class);
            Message message = Message.obtain();
            if ("1".equals(loginBean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
                message.obj = loginBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 错误
                message.obj = loginBean.getError();
            }
            handler.sendMessage(message);
            return loginBean;
        }
    }

    private void InitLocation() {
        locationClient = new LocationClient(getApplicationContext());
//		locationClient.registerLocationListener(myListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate() {
        // TODO Auto-generated method stub
        setBaseContentView(R.layout.jd_pay);
        context = JDPayActivity.this;
        bean = new PayCouponbean();
        webView = (WebView) findViewById(R.id.webView);
        linear_null = (LinearLayout) findViewById(R.id.linear_null);
        webView.setClickable(true);
        ws = webView.getSettings();
        ws.setLoadsImagesAutomatically(true);
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);// 解决缓存问题
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        ws.setTextZoom(100);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setUserAgentString(ws.getUserAgentString() + "heheys");
        ws.setSavePassword(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setDisplayZoomControls(false);
        ws.setJavaScriptEnabled(true); // 设置支持javascript脚本
        ws.setAllowFileAccess(true); // 允许访问文件
        ws.setBuiltInZoomControls(true); // 设置显示缩放按钮
        ws.setSupportZoom(true); // 支持缩放
        webView.requestFocusFromTouch();

        ws.setJavaScriptEnabled(true);  //支持js


        //启用数据库
        ws.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();

      //启用地理定位
        ws.setGeolocationEnabled(true);
     //设置定位的数据库路径
        ws.setGeolocationDatabasePath(dir);

       //设置localStorage
        ws.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        ws.setAppCachePath(appCachePath);
        ws.setAllowFileAccess(true);
        ws.setAppCacheEnabled(true);
        ws.setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient(){
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                final Intent intent = new Intent();
                Uri uri = Uri.parse(url);
                if (uri != null) {
                    String listerurl = uri.toString();
                    if (listerurl.contains(UrlsUtil.h5Url)) {
                        HashMap<String, String> mapsss = new HashMap<String, String>();
                        mapsss.put("seachorder", "");
                        MobclickAgent.onEvent(baseActivity, "0053", mapsss);
                        view.loadUrl(url);
                        intent.setClass(context, MainActivity.class);
                        intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY, ConstantsUtil.MAIN_TAB_ORDER);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        StartActivityUtil.startActivity(baseActivity, intent);
                    } else if (listerurl.contains(UrlsUtil.h5Url_yhq)) {
                        String backcontent = uri.toString();
                        String coupontemp = backcontent.split("\\?")[1];
                        String couponid = coupontemp.split("&")[0].split("=")[1];
                        String couponContent = coupontemp.split("&")[0].split("=")[0];
                        bean.setCouponContent(couponContent);
                        bean.setCouponid(couponid);
                        intent.putExtra("PayCouponbean", bean);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if (listerurl.contains(UrlsUtil.h5Url_hdzc)) {
                        //618活动专场
                        if (listerurl.contains("&")) {
                            aid = listerurl.split("&")[0];
                            if (aid.contains("=")) {
                                aid = aid.split("=")[1];
                            }
                        } else {
                            if (listerurl.contains("=")) {
                                aid = listerurl.split("=")[1];
                            }
                        }
                        intent.setClass(context, NewProductDetailActivity.class);
                        intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, aid);//17
                        intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "0");
                        StartActivityUtil.startActivity(baseActivity, intent);
                    } else if (listerurl.contains(UrlsUtil.h5Url_coinitem)) {
                        //我的充值记录
                        intent.setClass(baseActivity, HeheMoneyListActivity.class);
                        StartActivityUtil.startActivity(baseActivity, intent);
                    } else if (listerurl.contains(UrlsUtil.h5Url_login)) {
                        //页面登录失效
                        MyApplication.getInstance().startLogin(new LoginCallBack() {
                            @Override
                            public void loginSuccess() {
                                intent.setClass(baseActivity, JDPayActivity.class);//获取优惠券列表
                                intent.putExtra("url", linkurl);
                                intent.putExtra("title", title);
                                intent.putExtra("flag", 3);//优惠券传递3
                                StartActivityUtil.startActivity(baseActivity, intent);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("url", linkurl);
                                MobclickAgent.onEvent(baseActivity, "0001", map);
                            }

                            @Override
                            public void loginFail() {
                            }
                        }, baseActivity);
                    } else if (listerurl.contains(UrlsUtil.H5_SCAN)) {
                        //扫描
                        StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity, MipcaActivityCapture.class));
                    } else if (listerurl.contains(UrlsUtil.H5_TEL)) {
                        CallTel();
                    } else if (listerurl.contains(UrlsUtil.h5Url_login_pay)) {
                        //切换账号
                        ApiHttpCilent.getInstance(baseActivity).loginoOut(baseActivity,
                                new LoginOutCallBack());
                    } else if (listerurl.contains(UrlsUtil.h5Url_coin)) {
                        //我的积分
                        StartActivityUtil.startActivity((Activity) context,
                                MyPointsActivity.class);
                    } else if (listerurl.contains(UrlsUtil.h5Url_info)) {
                        //我的个人中心
                        finish();
                    } else if (listerurl.contains(UrlsUtil.H5_PAYAGAIN)) {
                        //继续支付
                        finish();
                    } else if (listerurl.contains(UrlsUtil.h5Url_route_map)) {
                        //我的导航
//                        intent.setClass(baseActivity, JDPayActivity.class);
//                        intent.putExtra("url", listerurl);
//                        intent.putExtra("title", "我的导航");
//                        intent.putExtra("flag", 6);
//                        StartActivityUtil.startActivity(baseActivity, intent);
                        Intent intents = new Intent(Intent.ACTION_VIEW, Uri.parse(listerurl));
                        startActivity(intents);
                    }
                    else if (listerurl.contains(UrlsUtil.h5Url_activitydetaile)) {
                        // 商品详情
                        String type = "";
                        String shareType = "";
                        if (listerurl.contains("type=")) {
                            type = listerurl.substring(listerurl.indexOf("type=") + 5);
                            if (type.contains("&")) {
                                type = type.substring(0, type.indexOf("&"));
                            }
                        }
                        if (listerurl.contains("shareType=")) {
                            shareType = listerurl.substring(listerurl.indexOf("shareType=") + 10);
                            if (shareType.contains("&")) {
                                shareType = shareType.substring(0, type.indexOf("&"));
                            }
                        }
                        if (listerurl.contains("aid")
                                && !listerurl.contains("&")) {
                            listerurl = listerurl.substring(
                                    listerurl.indexOf("aid") + 4,
                                    listerurl.length());
                        } else if (listerurl.contains("aid")
                                && listerurl.contains("&")) {
                            listerurl = listerurl.split("aid=")[1];
                            listerurl = listerurl.split("&")[0];
                        }

                        Intent intentRightBottom = new Intent(baseActivity,
                                NewProductDetailActivity.class);
                        intentRightBottom.putExtra(
                                ConstantsUtil.PRODUCT_ID_KEY,// 14
                                listerurl);
                        intentRightBottom.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, type);
                        intentRightBottom.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY_SHARE, shareType);
                        StartActivityUtil.startActivity(baseActivity,
                                intentRightBottom);
                    } else {
                        Session(listerurl);
                        view.loadUrl(listerurl);
                    }
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handle, SslError error) {
                // TODO Auto-generated method stub
                super.onReceivedSslError(view, handle, error);
//				handle.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
//				CookieManager cookieManager = CookieManager.getInstance();
//				String CookieStr = cookieManager.getCookie(url);
//				Log.e("sunzn", "Cookies = " + CookieStr);
                super.onPageFinished(view, url);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            flag_mode = intent.getIntExtra("flag", -1);
            code = intent.getStringExtra("code");
            if (flag_mode == 1) {//购物车详细跳转支付
                if (!NetWorkState.isNetWorkConnection(context)) {
                    ToastUtil.showToast(context, "网络连接异常，请您重试");
                    return;
                }
                String oid = intent.getStringExtra("orderId");
                creatOrder(oid);
            } else if (flag_mode == 2) {//订单列表详细跳转支付
                String oid = intent.getStringExtra("orderId");
                creatOrder(oid);
            } else {
                linkurl = intent.getStringExtra("url");
                title = intent.getStringExtra("title");
                ResetTitle(title);
                Session(linkurl);
                webView.loadUrl(linkurl);
                /**
                 * 发送位置 传30  服务端 打开心跳
                 * */
                if (flag_mode == 30) {
                    StartHeartService();
                    setBackIcon(R.drawable.base_left_close);
                }

                //我要发行
                if (flag_mode == 99) {
                    ivBack_close.setVisibility(View.VISIBLE);
                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT >= 14) {// 4.0 需打开硬件加速
            getWindow().setFlags(0x1000000, 0x1000000);
        }
    }

    private void StartHeartService() {
        if (Utils.isProessRunning(this, Utils.process_name)) {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            am.killBackgroundProcesses(Utils.process_name);
        }
        Intent intent = new Intent(this, HeartService.class);
        startService(intent);
//		bindService(intent,seviceConnection,BIND_AUTO_CREATE);
//		PollingUtils.startPollingService(this, 5, HeartService.class, HeartService.ACTION);
    }

    private void Session(String linkurl) {
        List<Cookie> listCook = MyApplication.getInstance().getCookieStore().getCookies();
        if (listCook.size() > 0) {
            Cookie sessionCookie = listCook.get(0);
            if (sessionCookie != null) {
                CookieSyncManager.createInstance(baseActivity);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String cookieString = sessionCookie.getName() + "="
                        + sessionCookie.getValue() + "; domain="
                        + sessionCookie.getDomain() + "; path="
                        + sessionCookie.getPath();
                cookieManager.setCookie(linkurl, cookieString);
                CookieSyncManager.getInstance().sync();
            }
        }
//		 List<Cookie> listcook = MyApplication.getInstance().getCookieStore().getCookies();
//		 if(listcook.size()>0){
//				Cookie sessionCookie = listcook.get(0);
//				if (sessionCookie != null) {
////					CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(baseActivity);
//					CookieManager cookieManager = CookieManager.getInstance();
////					cookieManager.setAcceptCookie(true);
////					cookieManager.removeSessionCookie();
//					if(Build.VERSION.SDK_INT < 21){
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}}
//					String cookieString = sessionCookie.getName() + "="
//							+ sessionCookie.getValue() + "; domain="
//							+ sessionCookie.getDomain()+"; path="
//									+ sessionCookie.getPath();
//					cookieManager.setCookie(linkurl, cookieString);
////					cookieSyncManager.;
//				 }
//				}
    }

    @Override
    protected boolean hasTitle() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void loadChildView() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void getNetData() {
        // TODO Auto-generated method stub


    }

    @Override
    protected void reloadCallback() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setChildViewListener() {
        // TODO Auto-generated method stub

    }

    @Override
    protected String setTitleName() {
        // TODO Auto-generated method stub
        return "京东支付";
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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
//		super.onClick(v);
        switch (v.getId()) {
            case R.id.base_activity_title_backicon:
                backPress();
                break;
            case R.id.linear_back:
                backPress();
                break;
            case R.id.base_activity_title_close:
                super.onBackPressed();
            default:
                break;
        }
    }


    void backPress() {
        HashMap<String, String> mapsss = new HashMap<String, String>();
        mapsss.put("jdpayback", "");
        MobclickAgent.onEvent(baseActivity, "0058", mapsss);
        if (flag_mode == 1) {//购物车过来返回键到订单列表
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY, ConstantsUtil.MAIN_TAB_ORDER);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            StartActivityUtil.startActivity(baseActivity, intent);
        } else {
            onBackPressed();
        }
    }

    //返回键处理
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

    public void updateCookies(String url) {
        List<Cookie> listcook = MyApplication.getInstance().getCookieStore().getCookies();
        if (listcook.size() > 0) {
            Cookie sessionCookie = listcook.get(0);
            if (sessionCookie != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) { // 2.3及以下
                    CookieSyncManager.createInstance(getApplicationContext());
                }
                String cookieString = sessionCookie.getName() + "="
                        + sessionCookie.getValue() + "; domain="
                        + sessionCookie.getDomain() + "; path="
                        + sessionCookie.getPath();
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.setCookie(url, cookieString);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                    CookieSyncManager.getInstance().sync();
                }
            }
        }

    }

    public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //解决setBuiltInZoomControls 问题
        webView.setVisibility(View.GONE);
        //解决webview加载东西过多 内存泄漏
        releaseAllWebViewCallback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.getSettings().setJavaScriptEnabled(false);
    }

    public void onResume() {
        super.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
        if (flag != 2)//等于2是京东支付
            MobclickAgent.onPageStart("PG_ADV");
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        if (flag != 2)//等于2是京东支付
            MobclickAgent.onPageEnd("PG_ADV");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        /**
         *
         * 10 订单详情支付结果跳转 直接关闭
         * 30 服务端 直接关闭
         * */
        if (flag_mode == 10 || flag_mode == 30 || flag_mode == 2 || flag_mode == 5) {
            //处理返回键  扫描 订单详情页过来 支付结果查询 直接返回
            super.onBackPressed();
        } else {
            if (webView.canGoBack()) {
                if (webView.getUrl().equals(linkurl)) {
                    super.onBackPressed();
                } else {
                    webView.goBack();
                }
            } else {
                super.onBackPressed();
            }
        }
    }
}
 