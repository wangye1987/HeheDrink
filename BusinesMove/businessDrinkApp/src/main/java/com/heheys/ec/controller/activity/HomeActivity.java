/*
package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.CustomTimerTask;
import com.heheys.ec.lib.utils.FileManager;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.TimeUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.CustomCoverFlow;
import com.heheys.ec.model.adapter.HomeCountdownOrderAdapter;
import com.heheys.ec.model.dataBean.CityListBean.CityDataList.CityData;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.Factorbean;
import com.heheys.ec.model.dataBean.GetAdvertisementBean;
import com.heheys.ec.model.dataBean.GetAdvertisementBean.AdvertisementInforBean.AdverInfor;
import com.heheys.ec.model.dataBean.ServicelineBaseBean;
import com.heheys.ec.model.dataBean.WholeBaseBean;
import com.heheys.ec.model.dataBean.WholeBaseBean.WholeListBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.UrlsUtil;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.RefreshScrollView;
import com.heheys.ec.view.RefreshScrollView.OnScrollListenerScrollview;
import com.heheys.ec.view.ViewPagerLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
*/
/**
 * Created by wangkui on 2016/12/12.
 *//*


public class HomeActivity extends BaseActivity implements
        OnScrollListenerScrollview, RefreshScrollView.OnRefreshListener {
    public final static String[] imageUrls = new String[] { "null", "null",
            "null", "null", "null", };
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int TYPE_PIFA = 99;// 批发传递标志位
    public static final int TYPE_PINGDAN = 100;// 拼单传递标志位
    private ViewPagerLayout adverLayout;
    private CustomCoverFlow cardLv;
    private HomeCountdownOrderAdapter adapter;
    private TextView tvSalon, tvMyCustomer;
    // private TextView tvDrinksDemand;
    private LinearLayout llTitleParentLayout;
    private AdverMessageHandler adverMessageHandler;
    private GetAdvertisementBean advertisementBean;
    private ActivityListMessageHandler activityListMessageHandler;
    // private GroupBuyProductlistBean groupBuyData;
    private LinearLayout llCheckCity;
    private WholeBaseBean wholeBaseBean;
    private String aid;
    private Factorbean factorbean;
    // 存储倒计时差
    private List<Long> countDownTimeList;
    private WebSettings ws;
    */
/**
     * 左边活动商品区控件
     *//*

    private ImageView ivLeftProduct, iv_pin_one;
    private LinearLayout time_one, linear_left;
    private TextView left_name, price_left, tv_left_unit, tv_left_unit_price;
    */
/**
     * 右侧上部控件区域
     *//*

    private ImageView ivRightProduct, iv_pin_two;
    private LinearLayout time_two, linear_top;
    private TextView product_hour_top, product_minute_top, product_seconds_top,
            top_name, top_currtprice, tv_top_unit, tv_top_unit_price;
    */
/**
     * 右侧下部控件区域
     *//*

    private ImageView ivButtomProduct, iv_pin_three;
    private LinearLayout time_three, linear_buttom;
    private TextView product_hour_buttom, product_minute_buttom,
            product_seconds_buttom, buttom_name, buttom_currtprice,
            tv_buttom_unit, tv_buttom_unit_price;
    */
/**
     * 倒计时控件
     *//*

    private TextView tvday, tvHour, tvMinute, tvSecond;
    private TextView tvday_top, tvHour_top, tvMinute_top, tvSecond_top;
    private TextView tvday_buttom, tvHour_buttom, tvMinute_buttom,
            tvSecond_buttom;
    private TextView tv_moreOrder;
    private TextView tv_excellent;
    private TextView tvLocationCity;
    private LinearLayout llLeftParent;
    private RelativeLayout rlRightTopParent, rlRightBottomParent;
    private String activityIdLeft, activityIdRightTop, activityIdRightBottom;
    private ImageView ivSearch;
    // private EditText etSearch;
    private RefreshScrollView scrollView;;
    private boolean isRefresh;
    private EditText etInput;
    private RelativeLayout rlMessageParent;
    private Button btMessage, btMessageNum;
    private CustomTimerTask task1;
    private CustomTimerTask task2;
    private CustomTimerTask task3;
    private List<GetAdvertisementBean.AdvertisementInforBean.AdverInfor> activityPageUirList;
    private TextView tvMessage;
    private ServicelineBaseBean beanservice;// 客服电话
    private ServicelineBaseBean.LineResult lineResult;// 基础数据bean
    private String mainh5 = "";// 首页H5
    boolean issx  = false;//是否筛选条件
    // 控制是否抢光标志
    private MyHandler handler;
    private TextView wholesale;
    private TextView shopCloud;
    private TextView tv_right_top_left;
    private TextView tv_right_top;
    private TextView tv_right_buttom;
    private WebView webView_main;
    private String title = "";
    private String urlyhj;//h5监听url 优惠券
//    public HomeActivity() {
//    }

//	public static HomeActivity newInstance(String param1, String param2) {
//		HomeActivity fragment = new HomeActivity();
//		Bundle args = new Bundle();
//		args.putString(ARG_PARAM1, param1);
//		args.putString(ARG_PARAM2, param2);
//		fragment.setArguments(args);
//		return fragment;
//	}
//
//	@Override
//	protected View setContentView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.fragment_home, container,
//				true);
//		initData();
//		initView(rootView);
//		return rootView;
//	}

    */
/**
     *
     * Describe:初始化数据
     *
     * Date:2015-10-20
     *
     * Author:liuzhouliang
     *//*

    private void initData() {
        handler = new MyHandler(this);
        activityPageUirList = new ArrayList<AdverInfor>();
        activityListMessageHandler = new ActivityListMessageHandler(this);
        adverMessageHandler = new AdverMessageHandler(this);
    }

    public class MyHandler extends WeakHandler<HomeActivity> {

        public MyHandler(HomeActivity reference) {
            super(reference);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS_LATER:
//				bindBaseInfo();
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    ErrorBean errorinfo = (ErrorBean) msg.obj;
                    if(errorinfo!=null){
                        ToastUtil.showToast(baseActivity, errorinfo.getInfo());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // 获取基本信息
    private void bindBaseInfo() {
        if (beanservice != null && beanservice.getResult() != null) {
            lineResult = beanservice.getResult();
            mainh5 = lineResult.getIndexurl();
            MyApplication.getInstance().setTel(lineResult.getServiceline());
            MyApplication.getInstance().setServerurl(lineResult.getCodhelpurl());
            SetSession(StringUtil.isEmpty(mainh5) ? "": mainh5);
            webView_main.loadUrl(StringUtil.isEmpty(mainh5) ? ""
                    : mainh5);
        }
    }


    private void SetSession(String linkurl) {
        // 获取session 加载到url中
        List<Cookie> listcook = MyApplication.getInstance().getCookieStore().getCookies();
        if(listcook.size()>0){
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
                        + sessionCookie.getDomain()+"; path="
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
        ws.setJavaScriptEnabled(true);
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);// 解决缓存问题
        webView_main.setClickable(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDefaultZoom(ZoomDensity.FAR);
        ws.setTextZoom(100);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setUserAgentString(ws.getUserAgentString()+"heheys");
        ws.setSavePassword(false);
        webView_main.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
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
                        Intent intentOrder = new Intent(baseActivity,GroupWholeSaleActivity.class);
                        if (listerurl.contains("type")&& !listerurl.contains("&")){
                            listerurl = listerurl.substring(listerurl.indexOf("type") + 5,listerurl.length());
                        } else if (listerurl.contains("type")&& listerurl.contains("&")) {
                            listerurl = listerurl.split("heheys://activity/list?")[1];
                            if(listerurl.contains("?"))
                                listerurl = listerurl.replace("?", "");
                            String factor[] = listerurl.split("&");
                            if(factor.length>2){
                                issx = true;
                            }else{
                                issx = false;
                            }
                            //将String数组转化为list集合
                            List<String> arrlist = (List<String>) Arrays.asList(factor);
                            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
                            for(String value:arrlist){
                                HashMap<String,String> map = new HashMap<String,String>();
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
                        intentOrder.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY,listerurl);
                        intentOrder.putExtra("factorbean",factorbean);//传递检索条件
                        intentOrder.putExtra("issx",issx);
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
                        Intent intent = new Intent(baseActivity,ShopListDetailActivity.class);
                        intent.putExtra("shopid", listerurl);
                        StartActivityUtil.startActivity(baseActivity, intent);

                    } else if (listerurl.contains(UrlsUtil.h5Url_activitydetaile)) {
                        // 商品详情
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
                        StartActivityUtil.startActivity(baseActivity,
                                intentRightBottom);

                    }else if(listerurl.contains(UrlsUtil.h5Url_login)){
                        MyApplication.getInstance().startLogin(new LoginCallBack() {
                            @Override
                            public void loginSuccess() {
                            }
                            @Override
                            public void loginFail() {
                            }
                        }, baseActivity);

                    } else if(listerurl.equals(UrlsUtil.h5Url_myaccount)){
                        //我的钱包

                        if(isLogin){
                            StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
                        }else{
                            Utils.StartLogin(baseActivity, false);
                        }
                    }else if(listerurl.equals(UrlsUtil.h5Url_coupon)){
                        //我的优惠券
                        if(isLogin){
                            Intent intent = new Intent();
                            intent.setClass(baseActivity,CouponActivity.class);
                            intent.putExtra("key", "scan");//查看
                            StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
                        }else{
                            Utils.StartLogin(baseActivity, false);
                        }
                    }else if(listerurl.equals(UrlsUtil.h5Url_activitydetaile)){

                    }else if(listerurl.contains(UrlsUtil.h5Url_mycoupn)){
                        if(listerurl.contains("title=") ){
                            title = listerurl.substring(listerurl.indexOf("title=")+6);
                            if(title.contains("&")){
                                title = title.split("&")[0];
                            }
                        }
                        if(listerurl.contains("heheurl=")){
                            urlyhj = listerurl.substring(listerurl.indexOf("heheurl=")+8);
                            if(urlyhj.contains("&")){
                                urlyhj = urlyhj.split("&")[0];
                            }
                            //领取优惠券界面
                            Intent intent = new Intent(baseActivity,JDPayActivity.class);
                            intent.putExtra("url", urlyhj);
                            intent.putExtra("title", title);
                            intent.putExtra("flag", 3);
                            StartActivityUtil.startActivity(baseActivity, intent);
                        }
                    }else if(listerurl.contains(UrlsUtil.h5Url_recharge)){
                        //充值喝喝币
                        StartActivityUtil.startActivity(baseActivity, HeHeMoneyActivity.class);
                    }else if(listerurl.contains(UrlsUtil.h5Url_scoreitem)){
                        //我的积分
                        StartActivityUtil.startActivity(baseActivity, MyPointsActivity.class);
                    }else {
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
        });
    }

    private void Value(Factorbean factorbean,String key,String value){
        if(key.equals("type")){
            factorbean.setType(value);
        }else if(key.equals("order")){
            factorbean.setOrder(value);
        }else if(key.equals("winetype")){
            factorbean.setWinetype(value);
        }else if(key.equals("inputstr")){
            factorbean.setInputstr(value);
        }else if(key.equals("asc")){
            factorbean.setAsc(Integer.parseInt(StringUtil.isEmpty(value)?"0":value));
        }else if(key.equals("place")){
            factorbean.setPlace(value);
        }else if(key.equals("recommend")){
            factorbean.setRecommend(value);
        }else if(key.equals("title")){
            factorbean.setTitle(value);
        }else if(key.equals("from")){
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
        tvMessage = (TextView) rootView
                .findViewById(R.id.fragment_home_title_mes_tv);
        btMessage = (Button) rootView
                .findViewById(R.id.fragment_home_title_message_icon);
        btMessageNum = (Button) rootView
                .findViewById(R.id.fragment_home_title_mes_num);
        rlMessageParent = (RelativeLayout) rootView
                .findViewById(R.id.fragment_home_title_message_parent);
        etInput = (EditText) rootView
                .findViewById(R.id.fragment_home_title_search_et);
        ivSearch = (ImageView) rootView
                .findViewById(R.id.fragment_home_title_search_icon);
        wholesale = (TextView) rootView
                .findViewById(R.id.fragment_home_drinks_wholesale);
        shopCloud = (TextView) rootView
                .findViewById(R.id.fragment_home_salonshop);
        adverLayout = (ViewPagerLayout) rootView
                .findViewById(R.id.fragment_home_adver);
        adapter = new HomeCountdownOrderAdapter(dataList, baseActivity);
        adapter.startCountDownTime();
        cardLv = (CustomCoverFlow) rootView.findViewById(R.id.order_wash_card);
        cardLv.setTotalCount(dataList.size());
        this.cardLv.setUnselectedAlpha(1.0f);
        this.cardLv.setUnselectedSaturation(0.0f);
        this.cardLv.setUnselectedScale(0.98f);
        this.cardLv.setSpacing(10);
        this.cardLv.setMaxRotation(20);
        this.cardLv.setScaleDownGravity(0.4f);
        this.cardLv.setActionDistance(CustomCoverFlow.ACTION_DISTANCE_AUTO);
        cardLv.setAdapter(adapter);
        tvMyCustomer = (TextView) rootView
                .findViewById(R.id.fragment_home_mycustomer);
        // tvDrinksDemand = (TextView) rootView
        // .findViewById(R.id.fragment_home_drinks_demand);
        tvSalon = (TextView) rootView.findViewById(R.id.fragment_home_salon);
        tv_moreOrder = (TextView) rootView.findViewById(R.id.tv_moreOrder);
        tv_excellent = (TextView) rootView.findViewById(R.id.tv_excellent);
        tv_moreOrder.setOnClickListener(this);
        tv_excellent.setOnClickListener(this);
        llTitleParentLayout = (LinearLayout) rootView
                .findViewById(R.id.fragment_home_title_parent);
        llCheckCity = (LinearLayout) rootView
                .findViewById(R.id.fragment_home_title_check_city);
        AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
        alpha.setDuration(0); // Make animation instant
        alpha.setFillAfter(true); // Tell it to persist after the animation ends
        llTitleParentLayout.startAnimation(alpha);
        // Drawable a = llTitleParentLayout.getBackground();
        // a.setAlpha(100);
        // llTitleParentLayout.setBackgroundDrawable(a);
        llTitleParentLayout.getBackground().setAlpha(127);
        tvLocationCity = (TextView) rootView
                .findViewById(R.id.fragment_home_title_location_city);
        scrollView = (RefreshScrollView) rootView
                .findViewById(R.id.fragment_home_middle_parent);
        scrollView.setOnScrollListener(this);
        scrollView.setonRefreshListener(this);
        */
/**
         * 左侧活动商品控件
         *//*


        ivLeftProduct = (ImageView) rootView
                .findViewById(R.id.fragment_home_middle_left_iv);
        iv_pin_one = (ImageView) rootView.findViewById(R.id.iv_pin_one);
        time_one = (LinearLayout) rootView.findViewById(R.id.time_one);// 时间视图
        linear_left = (LinearLayout) rootView.findViewById(R.id.linear_left);//

        left_name = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_left_name);// 酒名称
        price_left = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_left_sold_price);// 酒价格
        tv_left_unit = (TextView) rootView.findViewById(R.id.tv_left_unit);// 酒单位
        tv_left_unit_price = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_left_total_price);// 每箱几瓶
        */
/**
         * 右侧上部分商品控件
         *//*


        ivRightProduct = (ImageView) rootView
                .findViewById(R.id.fragment_home_middle_right_top_iv);
        iv_pin_two = (ImageView) rootView.findViewById(R.id.iv_pin_two);
        time_two = (LinearLayout) rootView.findViewById(R.id.time_two);// 时间视图
        top_name = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_right_top_name);// 酒名称
        top_currtprice = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_right_top_activity_price);// 单价
        tv_top_unit = (TextView) rootView.findViewById(R.id.tv_top_unit);// 酒单位
        tv_top_unit_price = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_right_top_normal_price);// 每箱几瓶
        linear_top = (LinearLayout) rootView.findViewById(R.id.linear_top);// 每箱视图
        */
/**
         * 右侧下部分商品控件
         *//*


        ivButtomProduct = (ImageView) rootView
                .findViewById(R.id.fragment_home_middle_right_bottom_iv);
        iv_pin_three = (ImageView) rootView.findViewById(R.id.iv_pin_three);
        time_three = (LinearLayout) rootView.findViewById(R.id.time_three);// 时间视图
        product_hour_buttom = (TextView) rootView
                .findViewById(R.id.product_hour_buttom);// 时
        product_minute_buttom = (TextView) rootView
                .findViewById(R.id.product_minute_buttom);// 时
        product_seconds_buttom = (TextView) rootView
                .findViewById(R.id.product_seconds_buttom);// 时
        buttom_name = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_right_bottom_name);// 酒名称
        buttom_currtprice = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_right_bottom_activity_price);// 单价
        tv_buttom_unit = (TextView) rootView.findViewById(R.id.tv_buttom_unit);// 酒单位
        tv_buttom_unit_price = (TextView) rootView
                .findViewById(R.id.fragment_home_middle_right_bottom_normal_price);// 每箱几瓶单价
        linear_buttom = (LinearLayout) rootView
                .findViewById(R.id.linear_buttom);// 每箱视图
        */
/**
         * 左边倒计时控件
         *//*

        tvday = (TextView) rootView.findViewById(R.id.tv_time_day);// 时
        tvHour = (TextView) rootView.findViewById(R.id.product_hour_left);
        tvMinute = (TextView) rootView.findViewById(R.id.product_minute_left);
        tvSecond = (TextView) rootView.findViewById(R.id.product_seconds_left);
        tv_right_top_left = (TextView) rootView
                .findViewById(R.id.tv_item_top_left);

        */
/**
         * 上边倒计时控件
         *//*

        tvday_top = (TextView) rootView.findViewById(R.id.tv_time_day_top);// 时
        tvHour_top = (TextView) rootView.findViewById(R.id.product_hour_top);
        tvMinute_top = (TextView) rootView
                .findViewById(R.id.product_minute_top);
        tvSecond_top = (TextView) rootView
                .findViewById(R.id.product_seconds_top);
        tv_right_top = (TextView) rootView.findViewById(R.id.tv_item_top);

        */
/**
         * 下边倒计时控件
         *//*

        tvday_buttom = (TextView) rootView
                .findViewById(R.id.tv_time_day_buttom);// 时
        tvHour_buttom = (TextView) rootView
                .findViewById(R.id.product_hour_buttom);
        tvMinute_buttom = (TextView) rootView
                .findViewById(R.id.product_minute_buttom);
        tvSecond_buttom = (TextView) rootView
                .findViewById(R.id.product_seconds_buttom);

        tv_right_buttom = (TextView) rootView.findViewById(R.id.tv_right);
        llLeftParent = (LinearLayout) rootView
                .findViewById(R.id.fragment_home_middle_left);
        rlRightTopParent = (RelativeLayout) rootView
                .findViewById(R.id.fragment_home_middle_right_top_parent);
        rlRightBottomParent = (RelativeLayout) rootView
                .findViewById(R.id.fragment_home_middle_right_bottom_parent);
        isLogin = IsLogin.isLogin(baseActivity);
    }

    */
/**
     * 绑定广告图片数据
     *//*

    private ViewPagerLayout.ImageCycleViewListener mAdCycleViewListener = new ViewPagerLayout.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            if (activityPageUirList != null && activityPageUirList.size() > 0) {
                // String linkurl =
                // activityPageUirList.get(position).getLinkurl();
                // if (!StringUtil.isEmpty(linkurl)) {
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
                            adver.getRes());// 17
                    intentLeft.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "0");
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
            case R.id.tv_excellent:
                */
/**
                 * 精选专区页面
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                Intent intentjx = new Intent(baseActivity,
                        GroupWholeSaleActivity.class);
                intentjx.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY_JX, "1");// 精选传1
                StartActivityUtil.startActivity(baseActivity, intentjx);
                break;
            case R.id.fragment_home_salon:
                */
/**
                 * 沙龙页面
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                HashMap<String, String> mapsss = new HashMap<String, String>();
                mapsss.put("salon", "");
                MobclickAgent.onEvent(baseActivity, "0024", mapsss);
                Intent intentSalon = new Intent(baseActivity,
                        SalonListActivity.class);
                StartActivityUtil.startActivity(baseActivity, intentSalon);

                break;
            case R.id.tv_moreOrder:
                */
/**
                 * 拼单页面
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("pingdan", "");
                MobclickAgent.onEvent(baseActivity, "0021", map);
                Intent intentOrder = new Intent(baseActivity,
                        GroupWholeSaleActivity.class);
                intentOrder.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "0");
                StartActivityUtil.startActivity(baseActivity, intentOrder);
                break;
            case R.id.fragment_home_drinks_wholesale:
                */
/**
                 * 批发页面
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                Intent intentWholeSale = new Intent(baseActivity,
                        GroupWholeSaleActivity.class);
                intentWholeSale.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "1");
                StartActivityUtil.startActivity(baseActivity, intentWholeSale);
                break;
            case R.id.fragment_home_mycustomer:
                */
/**
                 * 终端
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                HashMap<String, String> maps = new HashMap<String, String>();
                maps.put("comsuer", "");
                MobclickAgent.onEvent(baseActivity, "0022", maps);
                if (!IsLogin.Switch(baseActivity)) {
                    return;
                }
                Intent intentMyCustomer = new Intent(baseActivity,
                        MyCustomerActivity.class);
                StartActivityUtil.startActivity(baseActivity, intentMyCustomer);
                break;

            case R.id.fragment_home_title_check_city:
                */
/**
                 * 进入城市选择页面
                 *//*

                HashMap<String, String> mapss = new HashMap<String, String>();
                mapss.put("citychioce", "");
                MobclickAgent.onEvent(baseActivity, "C_HMN_CTY_1");
                Intent intent = new Intent(baseActivity, CheckCityActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ConstantsUtil.CHECK_CITY_FROM_KEY,
                        ConstantsUtil.CHECK_CITY_FROM_MAIN);
                intent.putExtra(ConstantsUtil.CHECK_CITY_SHOW_KEY,
                        ConstantsUtil.CHECK_CITY_SHOW_BACK);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;

            case R.id.fragment_home_middle_left:
                */
/**
                 * 左侧商品详情事件
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (StringUtil.isEmpty(activityIdLeft))
                    return;
                Intent intentLeft = new Intent(baseActivity,
                        NewProductDetailActivity.class);
                intentLeft.putExtra(ConstantsUtil.PRODUCT_ID_KEY, activityIdLeft);// 17
                // intentLeft.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY, "0");
                StartActivityUtil.startActivity(baseActivity, intentLeft);
                break;
            case R.id.fragment_home_middle_right_top_parent:
                */
/**
                 * 右侧上部商品详情事件
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (StringUtil.isEmpty(activityIdRightTop))
                    return;
                Intent intentRightTop = new Intent(baseActivity,
                        NewProductDetailActivity.class);
                intentRightTop.putExtra(ConstantsUtil.PRODUCT_ID_KEY,// //15
                        activityIdRightTop);
                StartActivityUtil.startActivity(baseActivity, intentRightTop);
                break;
            case R.id.fragment_home_middle_right_bottom_parent:
                */
/**
                 * 右侧商品下部详情事件
                 *//*

                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (StringUtil.isEmpty(activityIdRightBottom))
                    return;
                Intent intentRightBottom = new Intent(baseActivity,
                        NewProductDetailActivity.class);
                intentRightBottom.putExtra(ConstantsUtil.PRODUCT_ID_KEY,// 14
                        activityIdRightBottom);
                StartActivityUtil.startActivity(baseActivity, intentRightBottom);
                break;

            case R.id.fragment_home_title_search_icon:
                */
/**
                 * 搜索事件
                 *//*

                break;

            case R.id.fragment_home_title_message_parent:
                */
/**
                 * 进入消息页面
                 *//*

                LoginMsg();
                break;

            case R.id.fragment_home_title_message_icon:
                */
/**
                 * 进入消息页面
                 *//*

                LoginMsg();
                break;
            case R.id.fragment_home_title_mes_tv:
                */
/**
                 * 进入消息页面
                 *//*

                LoginMsg();
                break;
            case R.id.fragment_home_salonshop:
                // 获取云店
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                StartActivityUtil.startActivity(baseActivity,
                        HomeShopActivity.class);
                break;
            case R.id.iv_scan:
                //扫描二维码
                StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity,MipcaActivityCapture.class));
                break;
            default:
                break;
        }
    }

    private void LoginMsg() {
        MobclickAgent.onEvent(baseActivity,"C_HMN_MSG_1");//消息
        if (ToastNoNetWork.ToastError(baseActivity))
            return;
        if (!IsLogin.isLogin(baseActivity)) {
            Utils.StartLogin(baseActivity, false);
        } else {
            Intent intentMessage = new Intent(baseActivity,
                    MessageActivity.class);
            StartActivityUtil.startActivity(baseActivity, intentMessage);
//			SharedPreferencesUtil.writeSharedPreferencesBoolean(baseActivity,
//					"message", "new", false);
        }
    }

    @Override
    protected void getNetData() {
        CityData data = MyApplication.getCheckCityInfor();
        String cityIdString = null;
        if (data != null) {
            cityIdString = data.getId();
        } else {
            data = (CityData) FileManager.getObject(baseActivity,
                    ConstantsUtil.SAVE_CHECK_CITY_INFOR);
            if(data != null)
                cityIdString = data.getId();
        }
        */
/**
         * 获取广告信息
         *//*

        ApiHttpCilent.getInstance(baseActivity).getAdvertisement(baseActivity,
                new AdverRequestCallBack());

//		ApiHttpCilent.getInstance(baseActivity).HomeGoods(baseActivity, "",
//				cityIdString, "", 1, 10, 0, "", "", "", new RequestCallBack());
        */
/**
         * 获取客服电话等基本信息
         *//*

        ApiHttpCilent.getInstance(baseActivity).InitBaseInfo(baseActivity,
                new BaseinoCallBack());
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
//			message.obj = "数据获取失败,请稍后重试";
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

    */
/**
     *
     * Describe:获取活动商品列表请求回调
     *
     * Date:2016-03-30
     *
     * Author:wangkui
     *//*

//	public class RequestCallBack extends
//			BaseJsonHttpResponseHandler<WholeBaseBean> {
//
//		@Override
//		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//				String arg3, WholeBaseBean arg4) {
//			Dimessis();
//			Message message = Message.obtain();
//			message.what = ConstantsUtil.HTTP_FAILE;// 错误
//			activityListMessageHandler.sendMessage(message);
//		}
//
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, String arg2,
//				WholeBaseBean arg3) {
//		}
//
//		@Override
//		protected WholeBaseBean parseResponse(String response, boolean arg1)
//				throws Throwable {
//			Dimessis();
//			Gson gson = new Gson();
//			wholeBaseBean = gson.fromJson(response, WholeBaseBean.class);
//			Message message = Message.obtain();
//			if ("1".equals(wholeBaseBean.getStatus())) {// 正常
//				message.what = ConstantsUtil.HTTP_SUCCESS;
//				message.obj = wholeBaseBean.getResult();
//			} else {
//				message.what = ConstantsUtil.HTTP_FAILE;// 错误
//				message.obj = wholeBaseBean.getError();
//			}
//			activityListMessageHandler.sendMessage(message);
//			return wholeBaseBean;
//		}
//	}

    private void Dimessis() {
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ApiHttpCilent.loading != null
                        && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }

    */
/**
     *
     * Describe:获取广告数据请求回调
     *
     * Date:2015-10-20
     *
     * Author:liuzhouliang
     *//*

    public class AdverRequestCallBack extends
            BaseJsonHttpResponseHandler<GetAdvertisementBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, GetAdvertisementBean arg4) {
            // ApiHttpCilent.hideLoading();
            Dimessis();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            adverMessageHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              GetAdvertisementBean arg3) {
            Dimessis();
        }

        @Override
        protected GetAdvertisementBean parseResponse(String response,
                                                     boolean arg1) throws Throwable {
            Dimessis();
            Gson gson = new Gson();
            advertisementBean = gson.fromJson(response,
                    GetAdvertisementBean.class);
            Message message = Message.obtain();
            if ("1".equals(advertisementBean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = advertisementBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = advertisementBean.getError();
            }
            adverMessageHandler.sendMessage(message);
            return advertisementBean;
        }

    }

    */
/**
     *
     * Describe:获取广告数据处理网络请求消息
     *
     * Date:2015-10-16
     *
     * Author:liuzhouliang
     *//*

    public static class AdverMessageHandler extends WeakHandler<HomeActivity> {

        public AdverMessageHandler(HomeActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    */
/**
                     * 处理成功的数据
                     *//*

                    getReference().bindViewData();
                    if (getReference().isRefresh) {
                        getReference().scrollView.onRefreshComplete();
                        getReference().isRefresh = false;
                    }
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LATER:
                    getReference().bindBaseInfo();
                    if (getReference().isRefresh) {
                        getReference().scrollView.onRefreshComplete();
                        getReference().isRefresh = false;
                    }
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    */
/**
                     * 处理失败的数据
                     *//*

                    ErrorBean messageString = (ErrorBean) msg.obj;
                    if (messageString != null && !StringUtil.isEmpty(messageString.getCode())) {

                        if("100".equals(messageString.getCode())){
                            StartActivityUtil.startActivity(
                                    getReference().baseActivity,
                                    LoginActivity.class);
                        }else{
                            ToastUtil.showToast(getReference().baseActivity,
                                    messageString.getInfo());
                        }
                    }
                    if (getReference().isRefresh) {
                        getReference().scrollView.onRefreshComplete();
                        getReference().isRefresh = false;
                        // ToastUtil.showToast(getReference().baseActivity, "test");
                    }
                    break;
            }
        }

    }

    */
/**
     *
     * Describe:处理活动列表消息
     *
     * Date:2015-10-16
     *
     * Author:liuzhouliang
     *//*

    public static class ActivityListMessageHandler extends
            WeakHandler<HomeActivity> {

        public ActivityListMessageHandler(HomeActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    */
/**
                     * 处理成功的数据
                     *//*

                    getReference().bindViewData();

                    break;
                case ConstantsUtil.HTTP_FAILE:
                    */
/**
                     * 处理失败的数据
                     *//*

                    ErrorBean messageString = (ErrorBean) msg.obj;
                    if (messageString != null) {
                        ToastUtil.showToast(getReference().baseActivity,
                                messageString.getInfo());
                        */
/**
                         * 处理失败数据
                         *//*

                    } else {
                        ToastUtil.showToast(getReference().baseActivity, "请求失败");

                    }

                    break;
            }
        }

    }

    */
/**
     *
     * Describe:绑定控件数据
     *
     * Date:2015-10-20
     *
     * Author:liuzhouliang
     *//*

    private void bindViewData() {
        */
/**
         * 绑定广告数据
         *//*

        if (advertisementBean != null && advertisementBean.getResult() != null
                && advertisementBean.getResult().getList() != null
                && advertisementBean.getResult().getList().size() > 0) {
            List<AdverInfor> infor = advertisementBean.getResult().getList();
            List<String> urlStrings = new ArrayList<String>();
            for (AdverInfor obj : infor) {
                urlStrings.add(obj.getPicurl());
            }
            activityPageUirList = infor;
            if (urlStrings != null && urlStrings.size() > 0) {
                adverLayout.setImageResources(urlStrings, mAdCycleViewListener);
                if(urlStrings.size()>1)
                    adverLayout.startImageCycle();
                else
                    adverLayout.pushImageCycle();
            }
        } else {
            adverLayout.setImageResources(Arrays.asList(imageUrls),
                    mAdCycleViewListener);
            adverLayout.startImageCycle();
            // ToastUtil.showToast(baseActivity, "广告页为空");
        }

        */
/**
         * 绑定活动商品数据
         *//*

        if (wholeBaseBean != null && wholeBaseBean.getResult() != null
                && wholeBaseBean.getResult().getList() != null
                && wholeBaseBean.getResult().getList().size() > 0) {
            List<WholeListBean> productInfors = wholeBaseBean.getResult()
                    .getList();
            int index = 0;
            for (WholeListBean product : productInfors) {
                if (index == 0) {
                    activityIdLeft = product.getId();
                    MyApplication.imageLoader.displayImage(StringUtil
                                    .isEmpty(product.getPic()) ? product.getMinpic()
                                    : product.getPic(), ivLeftProduct,
                            MyApplication.options, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String imageUri,
                                                             View view) {
                                }

                                @Override
                                public void onLoadingFailed(String imageUri,
                                                            View view, FailReason failReason) {
                                }

                                @Override
                                public void onLoadingComplete(String imageUri,
                                                              View view, Bitmap loadedImage) {
                                }

                                @Override
                                public void onLoadingCancelled(String imageUri,
                                                               View view) {
                                }
                            });
                    left_name.setText(product.getName());
                    if (product.getType().equals("0")) {
                        iv_pin_one.setVisibility(View.VISIBLE);
                        time_one.setVisibility(View.VISIBLE);

                        long appointTime = TimeUtil.changeDateToTime(product
                                .getEndtime());
                        long currentTime = System.currentTimeMillis();
                        long gapTime = appointTime - currentTime;

                        if (gapTime > 0) {
                            if (task1 == null) {
                                task1 = new CustomTimerTask(tvday, tvHour,
                                        tvMinute, tvSecond, gapTime, 1000);
                                task1.startTimer();
                            } else {
                                task1.stopTimer();
                                task1 = new CustomTimerTask(tvday, tvHour,
                                        tvMinute, tvSecond, gapTime, 1000);
                                task1.startTimer();
                            }
                        } else {
                        }
                    } else {
                        iv_pin_one.setVisibility(View.INVISIBLE);
                        time_one.setVisibility(View.INVISIBLE);
                    }
                    price_left.setText(product.getCurrency() == null ? "¥"
                            : product.getCurrency() + product.getCprice());
                    tv_left_unit.setText("/" + product.getUnit());
                    tv_right_top_left.setText("/" + product.getPerunit() + ")");
                    if (!StringUtil.isEmpty(product.getUnit())
                            && product.getUnit().equals("箱")) {
                        linear_left.setVisibility(View.VISIBLE);
                        tv_left_unit_price
                                .setText(product.getCurrency() == null ? "¥"
                                        : product.getCurrency()
                                        + product.getUnivalent());
                    } else {
                        linear_left.setVisibility(View.INVISIBLE);
                    }
                    index = index + 1;
                } else if (index == 1) {
                    activityIdRightTop = product.getId();
                    MyApplication.imageLoader.displayImage(StringUtil
                                    .isEmpty(product.getMinpic()) ? product.getPic()
                                    : product.getMinpic(), ivRightProduct,
                            MyApplication.options, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String imageUri,
                                                             View view) {
                                }

                                @Override
                                public void onLoadingFailed(String imageUri,
                                                            View view, FailReason failReason) {
                                }

                                @Override
                                public void onLoadingComplete(String imageUri,
                                                              View view, Bitmap loadedImage) {
                                }

                                @Override
                                public void onLoadingCancelled(String imageUri,

                                                               View view) {
                                }
                            });
                    top_name.setText(product.getName());
                    if (product.getType().equals("0")) {
                        iv_pin_two.setVisibility(View.VISIBLE);
                        time_two.setVisibility(View.VISIBLE);
                        // 拼单
                        long appointTime = TimeUtil.changeDateToTime(product
                                .getEndtime());
                        long currentTime = System.currentTimeMillis();
                        long gapTime = appointTime - currentTime;
                        if (gapTime > 0) {
                            if (task2 == null) {
                                task2 = new CustomTimerTask(tvday_top,
                                        tvHour_top, tvMinute_top, tvSecond_top,
                                        gapTime, 1000);
                                task2.startTimer();
                            } else {
                                task2.stopTimer();
                                task2 = new CustomTimerTask(tvday_top,
                                        tvHour_top, tvMinute_top, tvSecond_top,
                                        gapTime, 1000);
                                task2.startTimer();
                            }
                        } else {
                            // ToastUtil.showToast(baseActivity, "时间为0");
                        }
                    } else {
                        iv_pin_two.setVisibility(View.INVISIBLE);
                        time_two.setVisibility(View.INVISIBLE);
                    }
                    if (!StringUtil.isEmpty(product.getUnit())
                            && product.getUnit().equals("箱")) {
                        linear_top.setVisibility(View.VISIBLE);
                        tv_top_unit_price
                                .setText(product.getCurrency() == null ? "¥"
                                        : product.getCurrency()
                                        + product.getUnivalent());
                    } else {
                        linear_top.setVisibility(View.INVISIBLE);
                    }
                    tv_top_unit.setText("/" + product.getUnit());
                    tv_right_top.setText("/" + product.getPerunit() + ")");
                    top_currtprice.setText(product.getCurrency() == null ? "¥"
                            : product.getCurrency() + product.getCprice());
                    index = index + 1;
                } else if (index == 2) {
                    activityIdRightBottom = product.getId();
                    MyApplication.imageLoader.displayImage(StringUtil
                                    .isEmpty(product.getMinpic()) ? product.getPic()
                                    : product.getMinpic(), ivButtomProduct,
                            MyApplication.options, new ImageLoadingListener() {

                                @Override
                                public void onLoadingStarted(String imageUri,
                                                             View view) {
                                }

                                @Override
                                public void onLoadingFailed(String imageUri,
                                                            View view, FailReason failReason) {
                                }

                                @Override
                                public void onLoadingComplete(String imageUri,
                                                              View view, Bitmap loadedImage) {
                                }

                                @Override
                                public void onLoadingCancelled(String imageUri,
                                                               View view) {
                                }
                            });
                    buttom_name.setText(product.getName());
                    if (product.getType().equals("0")) {
                        iv_pin_three.setVisibility(View.VISIBLE);
                        time_three.setVisibility(View.VISIBLE);
                        // 拼单
                        long appointTime = TimeUtil.changeDateToTime(product
                                .getEndtime());
                        long currentTime = System.currentTimeMillis();
                        long gapTime = appointTime - currentTime;
                        if (gapTime > 0) {
                            if (task3 == null) {
                                task3 = new CustomTimerTask(tvday_buttom,
                                        tvHour_buttom, tvMinute_buttom,
                                        tvSecond_buttom, gapTime, 1000);
                                task3.startTimer();
                            } else {
                                task3.stopTimer();
                                task3 = new CustomTimerTask(tvday_buttom,
                                        tvHour_buttom, tvMinute_buttom,
                                        tvSecond_buttom, gapTime, 1000);
                                task3.startTimer();
                            }
                        } else {
                            // ToastUtil.showToast(baseActivity, "时间为0");
                        }
                    } else {
                        iv_pin_three.setVisibility(View.INVISIBLE);
                        time_three.setVisibility(View.INVISIBLE);
                    }
                    if (!StringUtil.isEmpty(product.getUnit())
                            && product.getUnit().equals("箱")) {
                        linear_buttom.setVisibility(View.VISIBLE);
                        tv_buttom_unit_price
                                .setText(product.getCurrency() == null ? "¥"
                                        : product.getCurrency()
                                        + product.getUnivalent());
                    } else {
                        linear_buttom.setVisibility(View.INVISIBLE);
                    }
                    tv_buttom_unit.setText("/" + product.getUnit());
                    tv_right_buttom.setText("/" + product.getPerunit() + ")");
                    // ViewUtil.setActivityPrice(buttom_currtprice,
                    // product.getCprice());
                    buttom_currtprice
                            .setText(product.getCurrency() == null ? "¥"
                                    : product.getCurrency()
                                    + product.getCprice());
                    break;
                }
            }
        } else {
            // ToastUtil.showToast(baseActivity, "活动列表为空");
        }
        tvLocationCity.setText(MyApplication.currentCheckCity);
    }


    @Override
    protected void setChildViewListener() {
        iv_scan.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        btMessage.setOnClickListener(this);
        rlMessageParent.setOnClickListener(this);
        llCheckCity.setOnClickListener(this);
        shopCloud.setOnClickListener(this);
        tvSalon.setOnClickListener(this);
        wholesale.setOnClickListener(this);
        cardLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(HomeActivity.this.baseActivity,
                        NewProductDetailActivity.class);
                StartActivityUtil.startActivity(
                        HomeActivity.this.baseActivity, intent);
            }

        });
        tvMyCustomer.setOnClickListener(this);
        llLeftParent.setOnClickListener(this);
        rlRightTopParent.setOnClickListener(this);
        rlRightBottomParent.setOnClickListener(this);
        // ivSearch.setOnClickListener(this);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    */
/**
                     * 搜索事件
                     *//*

                    HashMap<String, String> mapss = new HashMap<String, String>();
                    mapss.put("seach", "");
                    MobclickAgent.onEvent(baseActivity, "0019", mapss);
                    if (StringUtil.isEmpty(etInput.getText().toString())) {
                        ToastUtil.showToast(baseActivity, "请输入搜索内容");
                    } else {
                        Intent intentGroupBuy = new Intent(baseActivity,
                                GroupWholeSaleActivity.class);
                        intentGroupBuy.putExtra("searchContent", etInput
                                .getText().toString());
                        StartActivityUtil.startActivity(baseActivity,
                                intentGroupBuy);
                    }
                }
                return false;
            }
        });
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

    @Override
    protected void onCreate() {

    }

    @Override
    protected boolean hasTitle() {
        return false;
    }

    @Override
    protected void loadChildView() {

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

//	@Override
//	protected boolean isShowLeftIcon() {
//		return false;
//	}
//
//	@Override
//	protected boolean hasTitleIcon() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	protected boolean hasDownIcon() {
//		// TODO Auto-generated method stub
//		return false;
//	}

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("PG_HOME");
        boolean haveMeg = SharedPreferencesUtil.getSharedPreferencesBoolean(
                baseActivity, "message", "new", false);
        if (haveMeg)
            btMessageNum.setVisibility(View.VISIBLE);
        else
            btMessageNum.setVisibility(View.GONE);
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
}
*/