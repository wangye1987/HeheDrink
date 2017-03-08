package com.heheys.ec.controller.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.controller.activity.CouponActivity;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.controller.activity.HeHeMoneyActivity;
import com.heheys.ec.controller.activity.HomeShopActivity;
import com.heheys.ec.controller.activity.LocationSearchActivity;
import com.heheys.ec.controller.activity.LoginActivity;
import com.heheys.ec.controller.activity.MipcaActivityCapture;
import com.heheys.ec.controller.activity.MyBalanceActivity;
import com.heheys.ec.controller.activity.MyPointsActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.controller.activity.SearchWineActivity;
import com.heheys.ec.controller.activity.ShopListDetailActivity;
import com.heheys.ec.controller.activity.UsercenterActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.CustomTimerTask;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.Factorbean;
import com.heheys.ec.model.dataBean.GetAdvertisementBean;
import com.heheys.ec.model.dataBean.GetAdvertisementBean.AdvertisementInforBean.AdverInfor;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean;
import com.heheys.ec.model.dataBean.ServicelineBaseBean;
import com.heheys.ec.model.dataBean.ServicelineBaseBean.LineResult;
import com.heheys.ec.model.dataBean.WholeBaseBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.UrlsUtil;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.RefreshScrollView;
import com.heheys.ec.view.RefreshScrollView.OnRefreshListener;
import com.heheys.ec.view.RefreshScrollView.OnScrollListenerScrollview;
import com.heheys.ec.view.ViewPagerLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nineoldandroids.animation.IntEvaluator;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.heheys.ec.netWorkHelper.UrlsUtil.H5_PUBLISH;

//import com.heheys.ec.controller.activity.CaptureActivity;

/**
 * Describe:主页面
 * <p>
 * Date:2015-9-28
 * <p>
 * Author:liuzhouliang
 */
public class HomeFragment extends BaseFragment implements
        OnScrollListenerScrollview, OnRefreshListener {
    public final static String[] imageUrls = new String[]{"null", "null",
            "null", "null", "null",};
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int TYPE_PIFA = 99;// 批发传递标志位
    public static final int TYPE_PINGDAN = 100;// 拼单传递标志位
    private ViewPagerLayout adverLayout;
    private LinearLayout llTitleParentLayout;
    private AdverMessageHandler adverMessageHandler;
    private GetAdvertisementBean advertisementBean;
//    private ActivityListMessageHandler activityListMessageHandler;
    // private GroupBuyProductlistBean groupBuyData;
    private LinearLayout llCheckCity;
    private WholeBaseBean wholeBaseBean;
    private String aid;
    private Factorbean factorbean;
    // 存储倒计时差
    private List<Long> countDownTimeList;
    private WebSettings ws;
    private TextView tvLocationCity,fragment_home_title_title;
    private RefreshScrollView scrollView;
    ;
    private boolean isRefresh;
    private LinearLayout rlSearchParent;
    private LinearLayout rlScanParent;
    private ImageView fragment_home_title_search;
    private CustomTimerTask task1;
    private CustomTimerTask task2;
    private CustomTimerTask task3;
    private List<AdverInfor> activityPageUirList;
    private ServicelineBaseBean beanservice;// 客服电话
    private LineResult lineResult;// 基础数据bean
    private String mainh5 = "";// 首页H5
    boolean issx = false;//是否筛选条件
    // 控制是否抢光标志
    private MyHandler handler;
    private TextView tv_right_top_left;
    private TextView tv_right_top;
    private TextView tv_right_buttom;
    private WebView webView_main;
    private String title = "";
    private String urlyhj;//h5监听url 优惠券
    //	ShareAppInfo shareAppInfoCallback;
    public BDLocationListener myListener = new MyLocationListener();
    private String currtCity;
    //当前定位的城市ID
    private String cityId = "";
    private LocationClient locationClient;
    private String backCityName;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                true);
        initData();
        initView(rootView);
        return rootView;
    }

    /**
     * Describe:初始化数据
     * <p>
     * Date:2015-10-20
     * <p>
     * Author:liuzhouliang
     */
    private void initData() {
        handler = new MyHandler(this);
        activityPageUirList = new ArrayList<>();
//        activityListMessageHandler = new ActivityListMessageHandler(this);
        adverMessageHandler = new AdverMessageHandler(this);
        //初始化定位数据
        InitData();
    }


    public class MyHandler extends WeakHandler<HomeFragment> {


        public MyHandler(HomeFragment reference) {
            super(reference);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    // 存储省份 城市 地区 元数据
                    ProvinceListBaseBean.Bean bean = (ProvinceListBaseBean.Bean) msg.obj;
                    //存储地址
                    if(bean != null) {
                        SharedPreferencesUtil.saveProvinceObject(
                                getReference().baseActivity, bean);
                        MyApplication.getInstance().SetCity(bean);
                        GetLocationCityId(MyApplication.getInstance().GetCity(),currtCity);
                    }

                    break;
                case ConstantsUtil.HTTP_SUCCESS_LATER:
//				bindBaseInfo();
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    ErrorBean errorinfo = (ErrorBean) msg.obj;
                    if (errorinfo != null) {
                        ToastUtil.showToast(baseActivity, errorinfo.getInfo());
                    }
                    /**
                     * 获取客服电话等基本信息
                     */
                    ApiHttpCilent.getInstance(getContext().getApplicationContext()).InitBaseInfo(baseActivity, "52",
                            new BaseinoCallBack());
                    break;

                default:
                    break;
            }
        }

    }



    /**
     * 获取当前定位城市id
     */
    private void GetLocationCityId(ProvinceListBaseBean.Bean bean,String currtCity) {
        if (!StringUtil.isEmpty(currtCity) && bean != null) {
            List<ProvinceListBaseBean.ProvinceList> listProvice = bean.getList();
            for (ProvinceListBaseBean.ProvinceList provice : listProvice) {
                List<ProvinceListBaseBean.CityBean> listCity = provice.getProvince().getCity();
                for (ProvinceListBaseBean.CityBean city : listCity) {
//                    List<ProvinceListBaseBean.County> listCounty = city.getCounty();
                    if (city.getName().equals(currtCity) || currtCity.contains(city.getName())) {
                        cityId = city.getId() + "";
                        MyApplication.getInstance().SetCityId(cityId);
                        break;
                    }
                }
            }
        }
        /**
         * 获取客服电话等基本信息
         */
        ApiHttpCilent.getInstance(getContext().getApplicationContext()).InitBaseInfo(baseActivity, cityId,
                new BaseinoCallBack());
    }

    // 获取基本信息
    private void bindBaseInfo() {
        if (beanservice != null && beanservice.getResult() != null) {
            lineResult = beanservice.getResult();
            mainh5 = lineResult.getIndexurl();
            SharedPreferencesUtil.writeSharedPreferencesString(baseActivity,"telShare","tel",lineResult.getServiceline());
            MyApplication.getInstance().setServiceline(lineResult.getServiceline());
            MyApplication.getInstance().setServerurl(lineResult.getCodhelpurl());
            MyApplication.getInstance().setHhmurl(lineResult.getGroupHelpUrl());
            if(StringUtil.isEmpty(cityId))
                cityId = "52";
            SetSession(StringUtil.isEmpty(mainh5) ? "" : mainh5+"?cityId="+cityId);
            webView_main.loadUrl(StringUtil.isEmpty(mainh5) ? ""
                    : mainh5+"?cityId="+cityId);
            setServicelineBaseBean(beanservice);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//		shareAppInfoCallback = (ShareAppInfo) context;
//		shareAppInfoCallback.ShareData(beanservice);
    }

    private void SetSession(String linkurl) {
        // 获取session 加载到url中
        List<Cookie> listcook = MyApplication.getInstance().getCookieStore().getCookies();
        if (listcook.size() > 0) {
            Cookie sessionCookie = listcook.get(0);
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
    }
    // 设置为WebView属性
    @SuppressLint("SetJavaScriptEnabled")
    void setWebView() {
        ws = webView_main.getSettings();
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);// 解决缓存问题
        webView_main.setClickable(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(true);
        ws.setDefaultZoom(ZoomDensity.FAR);
        ws.setTextZoom(100);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setUserAgentString(ws.getUserAgentString() + "heheys");
        ws.setSavePassword(false);

        //设置webview 缓存属性 优化第二次启动速度
        ws.setDomStorageEnabled(true);

        //Indexed Database 存储机制
        ws.setJavaScriptEnabled(true);

        //AppCache 缓存属性
        ws.setAppCacheEnabled(true);
        String cachePath = getActivity().getApplicationContext().getDir("cache",Context.MODE_PRIVATE).getPath();
        ws.setAppCachePath(cachePath);

        //database属性
        ws.setDatabaseEnabled(true);
        String dbPath = getActivity().getApplicationContext().getDatabasePath("db").getPath();
        ws.setDatabasePath(dbPath);
        //先不加载图片 在页面加载完毕再加载
        if(Build.VERSION.SDK_INT >= 19){
            ws.setLoadsImagesAutomatically(true);
        }else {
            ws.setLoadsImagesAutomatically(false);
        }

        webView_main.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载完毕再加载图片
                if (!view.getSettings().getLoadsImagesAutomatically()) {
                    view.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                Uri uri = Uri.parse(url);
                if (uri != null) {
                    String listerurl = uri.toString();
                    listerurl = Uri.decode(listerurl);
                    factorbean = null;//清除之前保存的筛选条件
                    if (listerurl.contains(UrlsUtil.h5Url_pdlb)) {
                        // 批发拼发精选监听
                        Intent intentOrder = new Intent(baseActivity, GroupWholeSaleActivity.class);
                        if (listerurl.contains("type") && !listerurl.contains("&")) {
                            listerurl = listerurl.substring(listerurl.indexOf("type") + 5, listerurl.length());
                        } else if (listerurl.contains("type") && listerurl.contains("&")) {
                            listerurl = listerurl.split("heheys://activity/list?")[1];
                            if (listerurl.contains("?"))
                                listerurl = listerurl.replace("?", "");
                            String factor[] = listerurl.split("&");
                            if (factor.length > 2) {
                                issx = true;
                            } else {
                                issx = false;
                            }
                            //将String数组转化为list集合
                            List<String> arrlist =  Arrays.asList(factor);
                            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                            for (String value : arrlist) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(value.split("=")[0], value.split("=")[1]);
                                //将list的值分解为map类型 key 是筛选条件
                                list.add(map);
                            }
                            factorbean = new Factorbean();//
                            for (HashMap<String, String> m : list) {
                                for (String key : m.keySet()) {
                                    //遍历取出key value 放入Factorbean对象 方便检索
                                    Value(factorbean, key, m.get(key));
                                }
                            }
                            listerurl = factorbean.getType();


                        } else if (listerurl.contains("recommend") && !listerurl.contains("&")) {
                            listerurl = listerurl.substring(
                                    listerurl.indexOf("recommend") + 10,
                                    listerurl.length());
                            intentOrder.putExtra(
                                    ConstantsUtil.PRODUCT_TYPE_KEY_JX,
                                    listerurl);// 精选传1
                            MobclickAgent.onEvent(baseActivity, "C_HMN_PROM_1");
                            factorbean = new Factorbean();
                            factorbean.setRecommend(listerurl);

                        } else if (listerurl.contains("recommend")
                                && listerurl.contains("&")) {
                            listerurl = listerurl.split("recommend=")[1];
                            listerurl = listerurl.split("&")[0];
                            intentOrder.putExtra(
                                    ConstantsUtil.PRODUCT_TYPE_KEY_JX,
                                    listerurl);// 精选传1
                            MobclickAgent.onEvent(baseActivity, "C_HMN_PROM_1");
                            factorbean = new Factorbean();
                            factorbean.setRecommend(listerurl);
                        }
                        if (("0").equals(listerurl))
                            MobclickAgent.onEvent(baseActivity, "C_HMN_PD_1");
                        else
                            MobclickAgent.onEvent(baseActivity, "C_HMN_SAL_1");
                        intentOrder.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, listerurl);
                        intentOrder.putExtra("factorbean", factorbean);//传递检索条件
                        intentOrder.putExtra("issx", issx);
                        StartActivityUtil.startActivity(baseActivity,
                                intentOrder);
                    } else if (listerurl.contains(UrlsUtil.h5Url_cloudshop)) {
                        MobclickAgent.onEvent(baseActivity, "C_HMN_SHP_1");
                        // 云店监听
                        StartActivityUtil.startActivity(baseActivity,
                                HomeShopActivity.class);
                    } else if (listerurl.contains(UrlsUtil.h5Url_maoshop)) {
                        // 茅台云店监听
                        if (listerurl.contains("id")
                                && !listerurl.contains("&")) {
                            listerurl = listerurl.substring(
                                    listerurl.indexOf("id") + 3,
                                    listerurl.length());
                        } else if (listerurl.contains("id")
                                && listerurl.contains("&")) {
                            listerurl = listerurl.split("id=")[1];
                            listerurl = listerurl.split("&")[0];
                        }
                        Intent intent = new Intent(baseActivity, ShopListDetailActivity.class);
                        intent.putExtra("shopid", listerurl);
                        StartActivityUtil.startActivity(baseActivity, intent);

                    } else if (listerurl.contains(UrlsUtil.h5Url_activitydetaile)) {
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

                    } else if (listerurl.contains(UrlsUtil.h5Url_login)) {
                        MyApplication.getInstance().startLogin(new LoginCallBack() {
                            @Override
                            public void loginSuccess() {
                            }

                            @Override
                            public void loginFail() {
                            }
                        }, baseActivity);

                    } else if (listerurl.equals(UrlsUtil.h5Url_myaccount)) {
                        //我的钱包

                        if (isLogin) {
                            StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
                        } else {
                            Utils.StartLogin(baseActivity, false);
                        }
                    } else if (listerurl.equals(UrlsUtil.h5Url_coupon)) {
                        //我的优惠券
                        if (isLogin) {
                            Intent intent = new Intent();
                            intent.setClass(baseActivity, CouponActivity.class);
                            intent.putExtra("key", "scan");//查看
                            StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
                        } else {
                            Utils.StartLogin(baseActivity, false);
                        }
                    } else if (listerurl.equals(UrlsUtil.h5Url_activitydetaile)) {

                    } else if (listerurl.contains(UrlsUtil.h5Url_mycoupn)) {
                        if (listerurl.contains("title=")) {
                            title = listerurl.substring(listerurl.indexOf("title=") + 6);
                            if (title.contains("&")) {
                                title = title.split("&")[0];
                            }
                        }
                        if (listerurl.contains("heheurl=")) {
                            urlyhj = listerurl.substring(listerurl.indexOf("heheurl=") + 8);
                            if (urlyhj.contains("&")) {
                                urlyhj = urlyhj.split("&")[0];
                            }
                            //领取优惠券界面
                            Intent intent = new Intent(baseActivity, JDPayActivity.class);
                            intent.putExtra("url", urlyhj);
                            intent.putExtra("title", title);
                            if(listerurl.contains(H5_PUBLISH)) {
                                //显示左上角关闭按钮
                                intent.putExtra("flag", 99);
                            }else {
                                intent.putExtra("flag", 3);
                            }
                            StartActivityUtil.startActivity(baseActivity, intent);
                        }
                    } else if (listerurl.contains(UrlsUtil.h5Url_recharge)) {
                        //充值喝喝币
                        StartActivityUtil.startActivity(baseActivity, HeHeMoneyActivity.class);
                    } else if (listerurl.contains(UrlsUtil.h5Url_scoreitem)) {
                        //我的积分
                        StartActivityUtil.startActivity(baseActivity, MyPointsActivity.class);
                    } else if (listerurl.contains(UrlsUtil.H5_SCAN)) {
                        //扫描
                        StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity, MipcaActivityCapture.class));
                    } else if (listerurl.contains(UrlsUtil.H5_MY)) {
                        //我的
                        StartActivityUtil.startActivity(baseActivity, UsercenterActivity.class);
                    } else {
                        SetSession(url);
                        view.loadUrl(url);
                    }
                }
                return true;

            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handle, SslError error) {
                // TODO Auto-generated method stub
                super.onReceivedSslError(view, handle, error);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                int errorCodes =  errorCode;
                String descriptions = description;
                view.loadUrl(" file:///android_asset/error.html ");
            }

        });
    }

    private void Value(Factorbean factorbean, String key, String value) {
        if (key.equals("type")) {
            factorbean.setType(value);
        } else if (key.equals("order")) {
            factorbean.setOrder(value);
        } else if (key.equals("winetype")) {
            factorbean.setWinetype(value);
        } else if (key.equals("inputstr")) {
            factorbean.setInputstr(value);
        } else if (key.equals("asc")) {
            factorbean.setAsc(Integer.parseInt(StringUtil.isEmpty(value) ? "0" : value));
        } else if (key.equals("place")) {
            factorbean.setPlace(value);
        } else if (key.equals("recommend")) {
            factorbean.setRecommend(value);
        } else if (key.equals("title")) {
            factorbean.setTitle(value);
        } else if (key.equals("from")) {
            factorbean.setFrom(value);
        }
    }

    @SuppressWarnings("deprecation")
    private void initView(View rootView) {
        List<Long> dataList = new ArrayList<Long>();
        for (long i = 0; i < 10; i++) {
            dataList.add(System.currentTimeMillis() + 1000 * 60 * 60);
        }
        webView_main = (WebView) rootView.findViewById(R.id.webView_main);
        setWebView();
        iv_scan = (ImageView) rootView
                .findViewById(R.id.iv_scan);

        rlScanParent = (LinearLayout) rootView
                .findViewById(R.id.scan_linear);
        rlSearchParent = (LinearLayout) rootView
                .findViewById(R.id.fragment_home_search_relative);
        fragment_home_title_search = (ImageView) rootView
                .findViewById(R.id.fragment_home_title_search);
        rlSearchParent.setOnClickListener(this);
        fragment_home_title_search.setOnClickListener(this);
        adverLayout = (ViewPagerLayout) rootView
                .findViewById(R.id.fragment_home_adver);
        llTitleParentLayout = (LinearLayout) rootView
                .findViewById(R.id.fragment_home_title_parent);
        llCheckCity = (LinearLayout) rootView
                .findViewById(R.id.fragment_home_title_check_city);
        AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
        alpha.setDuration(0); // Make animation instant
        alpha.setFillAfter(true); // Tell it to persist after the animation ends
//		llTitleParentLayout.startAnimation(alpha);
        // Drawable a = llTitleParentLayout.getBackground();
        // a.setAlpha(100);
        // llTitleParentLayout.setBackgroundDrawable(a);
//		llTitleParentLayout.getBackground().setAlpha(127);
        tvLocationCity = (TextView) rootView
                .findViewById(R.id.fragment_home_title_location_city);
        fragment_home_title_title = (TextView) rootView
                .findViewById(R.id.fragment_home_title_title);
        scrollView = (RefreshScrollView) rootView
                .findViewById(R.id.fragment_home_middle_parent);
        scrollView.setOnScrollListener(this);
        scrollView.setonRefreshListener(this);
        isLogin = IsLogin.isLogin(baseActivity);

    }

    //获取定位数据
    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null) {
                tvLocationCity.setText("北京");
                fragment_home_title_title.setVisibility(View.INVISIBLE);
                return;
            }
            locationClient.stop();
            if (location != null) {
                String address = location.getDistrict()+location.getStreet()+location.getStreetNumber();
                if(!StringUtil.isEmpty(address) && !"nullnullnull".equals(address)) {
                    if(address.length() <= 8) {
                        tvLocationCity.setText(address);
                    }else{
                        address = address.substring(0,8)+"...";
                        tvLocationCity.setText(address);
                    }
                }else{
                    tvLocationCity.setText( "北京" );
                }
                //当前定位城市
                currtCity = location.getCity();
            }
            /**
             * 获取省市信息
             */
            ApiHttpCilent.getInstance(getContext().getApplicationContext()).InitProvinceList(baseActivity,
                    new MyCallBack());
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }
    void InitData() {
        // 声明LocationClient类
        locationClient = new LocationClient(baseActivity);
        locationClient.registerLocationListener(myListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setScanSpan(1000);
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
        locationClient.start();
    }

    /**
     * 绑定广告图片数据
     */
    private ViewPagerLayout.ImageCycleViewListener mAdCycleViewListener = new ViewPagerLayout.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            if (activityPageUirList != null && activityPageUirList.size() > 0) {
                MobclickAgent.onEvent(baseActivity, "C_HMN_BAN_1");
                AdverInfor adver = activityPageUirList.get(position);
                if (adver.getType().equals("url")) {// url跳转
                    Intent intent = new Intent(baseActivity,
                            JDPayActivity.class);// 获取优惠券列表
                    intent.putExtra("url", adver.getRes());
                    intent.putExtra("title", adver.getTitle());
                    intent.putExtra("flag", 3);// 优惠券传递3
                    StartActivityUtil.startActivity(baseActivity, intent);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("url", activityPageUirList.get(position).getRes());
                } else if (adver.getType().equals("activity")) {// 商品详情跳转
                    Intent intentLeft = new Intent(baseActivity,
                            NewProductDetailActivity.class);
                    intentLeft.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
                            adver.getRes());// 活动ID
                    //标记是E发行还是合伙买
                    intentLeft.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, adver.getType());
                    StartActivityUtil.startActivity(baseActivity, intentLeft);
                }
            }
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            MyApplication.imageLoader.displayImage(imageURL, imageView,
                    MyApplication.options);
        }
    };
    private boolean isLogin;
    private ImageView iv_scan;

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fragment_home_title_check_city:
                /**
                 * 进入城市选择页面
                 */
                HashMap<String, String> mapss = new HashMap<String, String>();
                mapss.put("citychioce", "");
                MobclickAgent.onEvent(baseActivity, "C_HMN_CTY_1");
                Intent intent = new Intent(baseActivity, LocationSearchActivity.class);
//                startActivityForResult(intent, ConstantsUtil.REQUEST_CODE);
                StartActivityUtil.startActivityForResult(baseActivity,intent, ConstantsUtil.REQUEST_CODE);
                break;
            case R.id.fragment_home_search_relative:
                //搜索条件
                StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity, SearchWineActivity.class));
                break;
            case R.id.fragment_home_title_search:
                //搜索条件
                StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity, SearchWineActivity.class));
                break;

            case R.id.scan_linear:
                //扫描二维码
                OpenScan();
                break;
            case R.id.iv_scan:
                //扫描二维码
                OpenScan();
                 IntEvaluator mEvaluator = new IntEvaluator();
                mEvaluator.evaluate(0.5f, 100, 500);
                break;
            default:
                break;
        }
    }

    private void OpenScan() {
        if (Utils.cameraIsCanUse()){

            StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity, MipcaActivityCapture.class));
         }else{
            CommonDialog.makeText(baseActivity, "开启相机","相机权限关闭，是否开启?", new CommonDialog.OnDialogListener() {
                @Override
                public void onResult(int result, CommonDialog commonDialog,String tel) {
                    // TODO Auto-generated method stub
                    if (CommonDialog.OnDialogListener.LEFT == result ) {
                        gotoLocServiceSettings(baseActivity);
                    }
                    CommonDialog.Dissmess();
                }
            }).showDialog();
          }
    }

    /**
     * 跳转设置摄像头
     *
     * @param context 全局信息接口
     */
    public static void gotoLocServiceSettings(Context context) {
        final Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName","com.heheys.ec");
        try {
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantsUtil.REQUEST_CODE) {
            if (data != null) {
                String addressBack = data.getStringExtra("add");
                if(addressBack.length()> 8) {
                    addressBack = addressBack.substring(0,8)+"...";
                }
                tvLocationCity.setText(StringUtil.isEmpty(addressBack)?"":addressBack);
                backCityName = data.getStringExtra("cityName");
                //重新请求url
                GetLocationCityId(MyApplication.getInstance().GetCity(),backCityName);
            }
        }
    }

    @Override
    protected void getNetData() {

    }

    class MyCallBack extends BaseJsonHttpResponseHandler<ProvinceListBaseBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, ProvinceListBaseBean arg4) {
            Message message = Message.obtain();
            ErrorBean error = new ErrorBean();
            error.setInfo("网络异常");
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            message.obj = error;
            handler.sendMessage(message);
            Dimess();
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              ProvinceListBaseBean arg3) {
            Dimess();
        }

        @Override
        protected ProvinceListBaseBean parseResponse(String response,
                                                     boolean arg1) throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            ProvinceListBaseBean bean = gson.fromJson(response,
                    ProvinceListBaseBean.class);
            Message message = Message.obtain();
            if ("1".equals(bean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = bean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = bean.getError().getInfo();
            }
            handler.sendMessage(message);

            return bean;
        }
    }

    private void Dimess() {
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (ApiHttpCilent.loading != null
                        && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }

    class BaseinoCallBack extends
            BaseJsonHttpResponseHandler<ServicelineBaseBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, ServicelineBaseBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            adverMessageHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              ServicelineBaseBean arg3) {
            Dimess();
        }

        @Override
        protected ServicelineBaseBean parseResponse(String response,
                                                    boolean arg1) throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            beanservice = gson.fromJson(response, ServicelineBaseBean.class);
            Message message = Message.obtain();
            if ("1".equals(beanservice.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
                message.obj = beanservice.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = beanservice.getError();
            }
            adverMessageHandler.sendMessage(message);

            return beanservice;
        }
    }

    /**
     * Describe:获取广告数据请求回调
     * <p>
     * Date:2015-10-20
     * <p>
     * Author:liuzhouliang
     */
//    public class AdverRequestCallBack extends
//            BaseJsonHttpResponseHandler<GetAdvertisementBean> {
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//                              String arg3, GetAdvertisementBean arg4) {
//            Dimess();
//            Message message = Message.obtain();
//            message.what = ConstantsUtil.HTTP_FAILE;// 错误
//            adverMessageHandler.sendMessage(message);
//        }
//
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, String arg2,
//                              GetAdvertisementBean arg3) {
//            Dimess();
//        }
//
//        @Override
//        protected GetAdvertisementBean parseResponse(String response,
//                                                     boolean arg1) throws Throwable {
//            Dimess();
//            Gson gson = new Gson();
//            advertisementBean = gson.fromJson(response,
//                    GetAdvertisementBean.class);
//            Message message = Message.obtain();
//            if ("1".equals(advertisementBean.getStatus())) {// 正常
//                message.what = ConstantsUtil.HTTP_SUCCESS;
//                message.obj = advertisementBean.getResult();
//            } else {
//                message.what = ConstantsUtil.HTTP_FAILE;// 错误
//                message.obj = advertisementBean.getError();
//            }
//            adverMessageHandler.sendMessage(message);
//            return advertisementBean;
//        }
//
//    }
//
//    /**
//     * Describe:获取广告数据处理网络请求消息
//     * <p>
//     * Date:2015-10-16
//     * <p>
//     * Author:liuzhouliang
//     */
    public static class AdverMessageHandler extends WeakHandler<HomeFragment> {

        public AdverMessageHandler(HomeFragment reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS_LATER:
                    getReference().bindBaseInfo();
                    if (getReference().isRefresh) {
                        getReference().scrollView.onRefreshComplete();
                        getReference().isRefresh = false;
                    }
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    /**
                     * 处理失败的数据
                     */
                    ErrorBean messageString = (ErrorBean) msg.obj;
                    if (messageString != null && !StringUtil.isEmpty(messageString.getCode())) {

                        if ("100".equals(messageString.getCode())) {
                            StartActivityUtil.startActivity(
                                    getReference().baseActivity,
                                    LoginActivity.class);
                        } else {
                            ToastUtil.showToast(getReference().baseActivity,
                                    messageString.getInfo());
                        }
                    }
                    if (getReference().isRefresh) {
                        getReference().scrollView.onRefreshComplete();
                        getReference().isRefresh = false;
                    }
                    break;
            }
        }
    }
//
//    /**
//     * Describe:绑定控件数据
//     * <p>
//     * Date:2015-10-20
//     * <p>
//     * Author:liuzhouliang
//     */
//    private void bindViewData() {
//        /**
//         * 绑定广告数据
//         */
//        if (advertisementBean != null && advertisementBean.getResult() != null
//                && advertisementBean.getResult().getList() != null
//                && advertisementBean.getResult().getList().size() > 0) {
//            List<AdverInfor> infor = advertisementBean.getResult().getList();
//            List<String> urlStrings = new ArrayList<String>();
//            for (AdverInfor obj : infor) {
//                urlStrings.add(obj.getPicurl());
//            }
//            activityPageUirList = infor;
//            if (urlStrings != null && urlStrings.size() > 0) {
//                adverLayout.setImageResources(urlStrings, mAdCycleViewListener);
//                if (urlStrings.size() > 1)
//                    adverLayout.startImageCycle();
//                else
//                    adverLayout.pushImageCycle();
//            }
//        } else {
//            adverLayout.setImageResources(Arrays.asList(imageUrls),
//                    mAdCycleViewListener);
//            adverLayout.startImageCycle();
//        }
//    }


    @Override
    protected void setViewListener() {
        iv_scan.setOnClickListener(this);
        rlSearchParent.setOnClickListener(this);
        rlScanParent.setOnClickListener(this);
        fragment_home_title_search.setOnClickListener(this);
        llCheckCity.setOnClickListener(this);
    }

    @Override
    protected boolean hasTitle() {
        return false;
    }

    @Override
    protected void reloadCallback() {
    }

    @Override
    protected String setTitleName() {
        return "喝喝";
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
        return false;
    }

    @Override
    protected boolean hasDownIcon() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("PG_HOME");
        boolean haveMeg = SharedPreferencesUtil.getSharedPreferencesBoolean(
                baseActivity, "message", "new", false);
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        MobclickAgent.onPageEnd("PG_HOME");
    }

    @Override
    public void onScroll(int scrollY) {
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        getNetData();
    }


    public ServicelineBaseBean getServicelineBaseBean() {
        return beanservice;
    }

    public void setServicelineBaseBean(ServicelineBaseBean beanservice) {
        this.beanservice = beanservice;
    }
}
