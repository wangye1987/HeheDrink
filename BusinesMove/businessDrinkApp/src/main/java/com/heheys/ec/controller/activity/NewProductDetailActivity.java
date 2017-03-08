package com.heheys.ec.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heheys.ec.R;
import com.heheys.ec.animationManage.ProductDetailAnimation;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.CustomTimerTask;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.BadgeView;
import com.heheys.ec.lib.view.CustomViewPager;
import com.heheys.ec.lib.view.GridViewScrollview;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.PoductDetailBaseInforAdapter;
import com.heheys.ec.model.dataBean.AddCollectResultBean;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.GoodsMapList;
import com.heheys.ec.model.dataBean.ImBaseBean;
import com.heheys.ec.model.dataBean.ImBaseBean.ImHeHe;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.OrderList;
import com.heheys.ec.model.dataBean.NewProductDetailInfor;
import com.heheys.ec.model.dataBean.NewProductDetailInfor.NewProductInfor;
import com.heheys.ec.model.dataBean.NewProductDetailInfor.NewProductInfor.NewProductBaseInfor;
import com.heheys.ec.model.dataBean.NewProductDetailInfor.NewProductInfor.Suitinfo;
import com.heheys.ec.model.dataBean.NewProductDetailInfor.NewProductInfor.listItemDate;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.PoductDetailBaseInforBean;
import com.heheys.ec.model.dataBean.ShareBaseBean;
import com.heheys.ec.model.dataBean.ShareUrlResultBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.UrlsUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.Rotate3dAnimation;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.SlidingMenu;
import com.heheys.ec.view.YsnowWebView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.heheys.ec.R.id.produce_goods_name;


/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间 ：2016-3-18 上午9:58:40 类说明 商品详情
 */
public class NewProductDetailActivity extends BaseActivity {

    private static final String TAG = NewProductDetailActivity.class.getName();
    private int indexButtomStatus = 0;
    private int indexTopStatus = 0;
    // 图片浏览控件
    private CustomViewPager mViewPager;
    // 显示图片数量控件
    private TextView tvCurrentNum, tvTotalNum,supportarrive;
    public static int SCREENWIDTH;
    public static int SCREENHEIGHT;
    private TextView tvNormalPrice, tvActivityPrice;
    private NewProductDetailInfor productDetailInfor;
    private NewProductInfor productInfor;
    private ProductDetailMessageHandler productMessageHandler;
    private AddShoppingCartMessageHandler addShoppingCartMessageHandler;
    //活动ID
    private String productIdString;
    //活动类型ID
    private String productTypeString;
    private TextView tvProductDes, tvProductName, tvPriceRange, tvCanbuyNum,tvProductTotalNum;
    private TextView btAddShoppingCart, bt_buy, tv_dl;
    private AddShoppingCartBean addShoppingCartData;
    private List<String> pic;
    private PoductDetailBaseInforAdapter baseInforAdapter;
    private List<PoductDetailBaseInforBean> baseInforBeans;
    private GridViewScrollview gvBaseInfor;
    // 最大购买量
    private int maxBuyNum;
    // 最小购买量
    private int minBuyNum;
    private TextView tvProductFreight, tvProductStandard;
    private LinearLayout llBottomParent,linear_moreinfo;
    private boolean isPush;
    // 倒计时控件
    private TextView tvDay, tvHour;
    private TextView goods_name_hh;
    private TextView goods_min_num, goods_gg;
    private TextView tv_yunfei;
    private TextView  view_line_two;
    private CustomTimerTask task;
    private TextView tvMinBuy;
    private RelativeLayout llVpNumParent;
    private Bitmap currentBitmap;
    private ImageView ivAnimation;
    private LinearLayout product_detail_time_parent, time,
            linear_right, linear_qpl, linear_cod,time_linear;
    private OrderList paymentbean;
    private ArrayList<String> list = new ArrayList<String>();
    private int groupId;// im聊天组id
    private String groupName;// im聊天名字
    private IWXAPI api;// 微信api
    // 缓存非编辑状态下勾选商品的信息
    public List<ShoppingCartSelectBean> selectProductNotInEdit;
    private int issold;// 已售
    private int knum;// 库存
    private ImageView iv_jinxuan;
    private NewProductBaseInfor baseInfo;
    private NewOrderBaseBean bean;// 一键购买返回数据bean
    private OrderList orderbean;// 预览订单返回数据
    private String verifystatus;
    private MyApplication myApplication;
    private ViewPager pagerContent;
    private View view_detaile;
    private View view_h5;
//    private View view_business_h5;
    private ArrayList<View> viewContainter = new ArrayList<View>();
    private ArrayList<String> titleContainer = new ArrayList<String>();
    private MyPagerAdapter viewAdapter;
    private WebView webview_h5;
//    private WebView webview_business_h5;
    private TextView line_one;
    private TextView line_two;
    private TextView line_three;
    private TextView tv_sp;
    private TextView tv_xq;
    private LinearLayout linear_sj;
    private LinearLayout linear_sp;
    private LinearLayout linear_xq, linear_back;
    private MyOnPageChangeLister pageChangeLister;
//    private UserDefineScrollView product_detail_top_parent;
//    private UserDefineScrollView product_detail_top_child;
    private TextView linear_tel;
    private TextView tv_ontel;
    private TextView tv_online;
    private LinearLayout linear_horizontal;
    private RelativeLayout relative_more;
    // 购物车数据bean
    private NewShoppingCartInforBean shoppingCartData;
    private BadgeView shopping_cart;// 购物车小角标
    private TextView shoppingcare_num;
    private FrameLayout relative_shopping;
    private TextView imageView_shopping;
    private PopupWindow popupWindow;
    private ImageView iv_thimal;
    private TextView tv_more;
    private TextView tv_title;
    private TextView tv_tzj;
    private TextView tv_save;
    private TextView tv_num;
    private ImageView tv_dd;
    private ImageView iv_jia;
    private YsnowWebView h5_webview_main;
    private TextView tv_center;
    private AddCollectResultBean.AddResultBean collectResultListBean;
    //分享返回
    private ShareUrlResultBean ShareresultBean;
    private boolean isFirstBack = true;//是否是第一次点击返
    //合伙卖
    private static final int HHM = 0;
    //特卖
    private static final int TM = 1;
    //E发行
    private static final int EFX = 3;
    //合伙买
    private RelativeLayout vb_hhm;
    //特卖
    private RelativeLayout vb_tm;
    //发行
    private RelativeLayout vb_fx;
    private TextView produce_price_and_unit;
    private TextView produce_price_dj;
    private TextView produce_jxz;
    private ProgressBar group_buy_grid_item_pb;
    private TextView poduce_jxz_num;
    private TextView poduce_jxz_num_total_unit;
    private TextView produce_look_tv;
    private TextView produce_heart_tv;
    private TextView produce_tv_people;
    private RelativeLayout produce_tm;
    private TextView produce_goods_name_tm;
    private TextView produce_price_and_unit_tm;
    private TextView produce_price_and_unit_xl;
    private TextView produce_price_and_unit_syl;
    private TextView produce_look_tv_tm;
    private TextView produce_heart_tv_tm;
    private TextView produce_tv_people_tm;
    private TextView produce_goods_name_fx;
    private TextView produce_price_and_unit_fx;
    private TextView produce_guige_fx;
    private TextView produce_area_fx;
    private TextView produce_look_tv_fx;
    private TextView produce_heart_tv_fx;
    private LinearLayout linear_look;
    private LinearLayout linear_look_fx;
    private GoodsMapList goodsMapList;
    private LinearLayout linear_share;
    private ImageView iv_share;
    private String shareUrl;
    private LinearLayout linear_iv_heart_tm;
    private LinearLayout linear_look_tm;
    private LinearLayout linear_iv_hear_hhm;
    private ImageView produce_iv_heart_fx;
    private ImageView produce_iv_heart_tm;
    private ImageView produce_iv_heart_hhm;

    private String collectId = "1";
    private String flag = "1";//1 添加收藏 -1 删除收藏
    //0 单品详细 1商家详情
    private String shareType;
    private TextView tv_tfx;
    private int nowFlow;
    private int nowFlowAddDel;
    private TextView produce_title_tip_fx;
    private FrameLayout lnclude_buttom;
    private SlidingMenu menu_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.produce_detail);
    }

    /*
     * 执行动画滑动 index 当前是哪个页面
     */
    void startAnimation(int index) {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation tlanimetion;
        if (1 == index) {
            tlanimetion = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    ViewUtil.dip2px(baseActivity, 64) + tv_sp.getWidth(), 0, 0);
        } else {
            tlanimetion = new TranslateAnimation(ViewUtil.dip2px(baseActivity,
                    64) + tv_sp.getWidth(), Animation.RELATIVE_TO_SELF, 0, 0);
        }
        tlanimetion.setDuration(300);
        set.setFillAfter(true);
        set.addAnimation(tlanimetion);
        line_one.startAnimation(set);
    }

    private void initView() {
        pagerContent = (ViewPager) this.findViewById(R.id.viewpager);
        h5_webview_main = (YsnowWebView) this.findViewById(R.id.webView_content);
        menu_parent = (SlidingMenu) this.findViewById(R.id.menu_parent);
        lnclude_buttom = (FrameLayout) this.findViewById(R.id.lnclude_buttom);
        linear_share = (LinearLayout) this.findViewById(R.id.linear_share);
        iv_share = (ImageView) this.findViewById(R.id.iv_share);
        tv_center = (TextView) this.findViewById(R.id.tv_center);
        line_one = (TextView) this.findViewById(R.id.line_one);
        line_two = (TextView) this.findViewById(R.id.line_two);
        line_three = (TextView) this.findViewById(R.id.line_three);
        tv_sp = (TextView) this.findViewById(R.id.tv_sp);
        tv_xq = (TextView) this.findViewById(R.id.tv_xq);
//        product_detail_top_parent = (UserDefineScrollView)
//                findViewById(R.id.product_detail_top_parent);
        shoppingcare_num = (TextView) this.findViewById(R.id.shoppingcare_num);
        imageView_shopping = (TextView) this
                .findViewById(R.id.group_buy_list_shopping_cart);
        relative_shopping = (FrameLayout) this
                .findViewById(R.id.relative_shopping);
        linear_sp = (LinearLayout) this.findViewById(R.id.linear_sp);
        linear_xq = (LinearLayout) this.findViewById(R.id.linear_xq);
        linear_sj = (LinearLayout) this.findViewById(R.id.linear_sj);
        linear_back = (LinearLayout) this.findViewById(R.id.linear_back_produce);
        linear_sp.setOnClickListener(this);
        linear_xq.setOnClickListener(this);
        linear_sj.setOnClickListener(this);
        linear_back.setOnClickListener(this);
        linear_share.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        imageView_shopping.setOnClickListener(this);
        relative_shopping.setOnClickListener(this);
//        product_detail_top_parent.setScrollListener(this);
        view_detaile = LayoutInflater.from(this).inflate(
                R.layout.new_product_datail, null);
        view_h5 = LayoutInflater.from(this).inflate(R.layout.content_h5, null);
//        view_business_h5 = LayoutInflater.from(this).inflate(R.layout.content_h5, null);

        titleContainer.add("商品");
        titleContainer.add("详情1");

        productIdString = getIntent().getStringExtra(
                ConstantsUtil.PRODUCT_ID_KEY);
        if(!StringUtil.isEmpty(productIdString)){
        if(productIdString.contains(UrlsUtil.h5Url_activitydetaile))
        {
            if(productIdString.contains("aid=")){
                productIdString = productIdString.substring(productIdString.indexOf("aid=")+4,productIdString.length());
            }
        }}
        productTypeString = getIntent().getStringExtra(
                ConstantsUtil.PRODUCT_TYPE_KEY);
        shareType = getIntent().getStringExtra(
                 ConstantsUtil.PRODUCT_TYPE_KEY_SHARE);


        setH5(h5_webview_main);
//        pagerContent.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
        if(StringUtil.isEmpty(shareType)){
            if("0".equals(shareType)){
                if(pagerContent.getChildCount()>1)
                    pagerContent.setCurrentItem(1);
            }else if("1".equals(shareType)){
                if(pagerContent.getChildCount()>2)
                    pagerContent.setCurrentItem(2);
            }
        }
    }

    class MyOnPageChangeLister implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int index) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            pagerContent.getParent().requestDisallowInterceptTouchEvent(true);
            if(2 == arg0)
                HaseTelMenu();
            else
                NoTelMenu();
        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            LineVisible(arg0);
            if(arg0 == 1){
                menu_parent.setLoadMore(false);
            }else{
                menu_parent.setLoadMore(true);
            }
        }
    }

    private void LineVisible(int index) {
        AllINVisible();
        switch (index) {
            case 0:
                line_one.setVisibility(View.VISIBLE);
                break;
            case 1:
                line_two.setVisibility(View.VISIBLE);
                break;
            case 2:
                line_three.setVisibility(View.VISIBLE);
                break;
        }
    }

    void AllINVisible() {
        line_one.setVisibility(View.INVISIBLE);
        line_two.setVisibility(View.INVISIBLE);
        line_three.setVisibility(View.INVISIBLE);
    }

    // 商品信息适配器
    class MyPagerAdapter extends PagerAdapter {
        ArrayList<View> viewContaint;
        MyPagerAdapter(ArrayList<View> viewContaint){
            this.viewContaint = viewContaint;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewContaint.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView(viewContaint.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return titleContainer.get(position);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(viewContaint.get(position));
            return viewContaint.get(position);
        }
    }

    // 设置H5数据属性
    private void setH5(final WebView webview) {
        WebSettings ws = webview.getSettings();
        webview.setClickable(true);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
//        //设置webview 缓存属性 优化第二次启动速度
        ws.setDomStorageEnabled(true);

        //Indexed Database 存储机制
        ws.setJavaScriptEnabled(true);

        //AppCache 缓存属性
        ws.setAppCacheEnabled(true);
        String cachePath = getApplicationContext().getDir("cache",Context.MODE_PRIVATE).getPath();
        ws.setAppCachePath(cachePath);

        //database属性
        ws.setDatabaseEnabled(true);
        String dbPath = getApplicationContext().getDatabasePath("db").getPath();
        ws.setDatabasePath(dbPath);

        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setBuiltInZoomControls(true);// support zoom
        ws.setUseWideViewPort(true);// 这个很关键
        ws.setLoadWithOverviewMode(true);
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setDefaultFontSize(15);
        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        //重新测量
        webview.measure(w, h);
//        int width = wm.getDefaultDisplay().getWidth();
//        if (width > 650) {
//            webview.setInitialScale(190);
//        } else if (width > 520) {
//            webview.setInitialScale(160);
//        } else if (width > 450) {
//            webview.setInitialScale(140);
//        } else if (width > 300) {
//            webview.setInitialScale(120);
//        } else {
//            webview.setInitialScale(100);
//        }
        // sdk小于17版本号的 移除以下接口调用漏洞
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webview.removeJavascriptInterface("searchBoxJavaBridge_");
            webview.removeJavascriptInterface("accessibility");
            webview.removeJavascriptInterface("accessibilityTraversal");
        }
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
// TODO Auto-generated method stub
                Log.i("webview", "url = "+url);
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                int errorCodes =  errorCode;
                String descriptions = description;
                view.loadUrl(" file:///android_asset/error.html ");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });
    }
    public void resize(final float height) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                h5_webview_main.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
                webview_h5.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
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
        initView();
        webview_h5 = (WebView) view_h5.findViewById(R.id.webView_content_detail);
//        webview_business_h5 = (WebView) view_business_h5.findViewById(R.id.webView_content);
        tv_num = (TextView) view_detaile.findViewById(R.id.tv_num);
        tv_dd = (ImageView) view_detaile.findViewById(R.id.tv_dd);
        tv_more = (TextView) view_detaile.findViewById(R.id.tv_more);
        tv_save = (TextView) view_detaile.findViewById(R.id.tv_save);
        tv_tzj = (TextView) view_detaile.findViewById(R.id.tv_tzj);
        tv_title = (TextView) view_detaile.findViewById(R.id.tv_title);
        linear_look = (LinearLayout) view_detaile.findViewById(R.id.linear_look);
//        product_detail_top_child = (UserDefineScrollView)
//                view_detaile.findViewById(product_detail_top_parent);
        llVpNumParent = (RelativeLayout) view_detaile
                .findViewById(R.id.product_detail_num_parent);
        relative_more = (RelativeLayout) view_detaile
                .findViewById(R.id.relative_more);
        linear_moreinfo = (LinearLayout) view_detaile
                .findViewById(R.id.linear_moreinfo);
        linear_cod = (LinearLayout) view_detaile
                .findViewById(R.id.linear_cod);
        bt_buy = (TextView) findViewById(R.id.bt_buy);
        bt_buy.setOnClickListener(this);
        btAddShoppingCart = (TextView) findViewById(R.id.product_detail_add_shoppingcart);
        //我也代理按钮
        tv_dl = (TextView) findViewById(R.id.tv_dl);
        tv_dl.setOnClickListener(this);
        //购买视图
        llBottomParent = (LinearLayout) findViewById(R.id.product_detail_bottom_parent);
        ivAnimation = (ImageView) view_detaile
                .findViewById(R.id.product_detail_animation);
        linear_tel = (TextView) findViewById(R.id.linear_tel);
        supportarrive = (TextView) view_detaile
                .findViewById(R.id.supportarrive);
        tvTotalNum = (TextView) view_detaile
                .findViewById(R.id.product_detail_totaltnum_tv);
        tvCurrentNum = (TextView) view_detaile
                .findViewById(R.id.product_detail_currentnum_tv);
        tvDay = (TextView) view_detaile.findViewById(R.id.product_day);
        tvHour = (TextView) view_detaile.findViewById(R.id.product_hour);
        /**
         * 合伙买视图
         * */
        RelativeLayout produce_hhm = (RelativeLayout) view_detaile.findViewById(R.id.produce_hhm);
        vb_hhm = (RelativeLayout) view_detaile.findViewById(R.id.vb_hhm);
        goods_name_hh = (TextView) view_detaile.findViewById(produce_goods_name);
        //价格
        produce_price_and_unit = (TextView) view_detaile.findViewById(R.id.produce_price_and_unit);
        //订金
        produce_price_dj = (TextView) view_detaile.findViewById(R.id.produce_price_dj);
        //title 进行中
        produce_jxz = (TextView) view_detaile.findViewById(R.id.produce_jxz);
        //进度
        group_buy_grid_item_pb = (ProgressBar) view_detaile.findViewById(R.id.group_buy_grid_item_pb);
        //已买箱数
        poduce_jxz_num = (TextView) view_detaile.findViewById(R.id.poduce_jxz_num);
        //总箱数
        poduce_jxz_num_total_unit = (TextView) view_detaile.findViewById(R.id.poduce_jxz_num_total_unit);

        //浏览量
        produce_look_tv = (TextView) view_detaile.findViewById(R.id.produce_look_tv);
        //收藏量
        produce_heart_tv = (TextView) view_detaile.findViewById(R.id.produce_heart_tv);
        //购买量
        produce_tv_people = (TextView) view_detaile.findViewById(R.id.produce_tv_people);
        linear_iv_hear_hhm =  (LinearLayout) view_detaile.findViewById(R.id.linear_iv_heart);
        produce_iv_heart_hhm = (ImageView) view_detaile.findViewById(R.id.produce_iv_heart);
        produce_heart_tv.setOnClickListener(this);
        linear_iv_hear_hhm.setOnClickListener(this);
        produce_iv_heart_hhm.setOnClickListener(this);

        /***/

        /**
         * 特卖视图
         * */
        vb_tm = (RelativeLayout) view_detaile.findViewById(R.id.vb_tm);

        RelativeLayout produce_tm = (RelativeLayout) view_detaile.findViewById(R.id.produce_tm);
        produce_goods_name_tm = (TextView) view_detaile.findViewById(R.id.produce_goods_name_tm);
        //价格
        produce_price_and_unit_tm = (TextView) view_detaile.findViewById(R.id.produce_price_and_unit_tm);
        //销量
        produce_price_and_unit_xl = (TextView) view_detaile.findViewById(R.id.produce_price_and_unit_xl);
        //库存
        produce_price_and_unit_syl = (TextView) view_detaile.findViewById(R.id.produce_price_and_unit_syl);

        //浏览量
        produce_look_tv_tm = (TextView) view_detaile.findViewById(R.id.produce_look_tv_tm);
        //收藏量
        produce_heart_tv_tm = (TextView) view_detaile.findViewById(R.id.produce_heart_tv_tm);
        //购买量
        produce_tv_people_tm = (TextView) view_detaile.findViewById(R.id.produce_tv_people_tm);
        linear_iv_heart_tm = (LinearLayout) view_detaile.findViewById(R.id.linear_iv_heart_tm);
        linear_look_tm = (LinearLayout) view_detaile.findViewById(R.id.linear_look_tm);
        produce_iv_heart_tm = (ImageView) view_detaile.findViewById(R.id.produce_iv_heart_tm);
        produce_heart_tv_tm.setOnClickListener(this);
        linear_iv_heart_tm.setOnClickListener(this);
        linear_look_tm.setOnClickListener(this);
        produce_iv_heart_tm.setOnClickListener(this);
        /***/


        /**
         * 发行视图
         * */
        vb_fx = (RelativeLayout) view_detaile.findViewById(R.id.vb_fx);

        RelativeLayout produce_fx = (RelativeLayout) view_detaile.findViewById(R.id.produce_fx);
        linear_look_fx = (LinearLayout) view_detaile.findViewById(R.id.linear_look_fx);
        produce_goods_name_fx = (TextView) view_detaile.findViewById(R.id.produce_goods_name_fx);
        //价格
        produce_price_and_unit_fx = (TextView) view_detaile.findViewById(R.id.produce_price_and_unit_fx);
        //规格
        produce_guige_fx = (TextView) view_detaile.findViewById(R.id.produce_guige_fx);
        //发行区域
        produce_area_fx = (TextView) view_detaile.findViewById(R.id.produce_area_fx);

        produce_title_tip_fx = (TextView) view_detaile.findViewById(R.id.produce_title_tip_fx);

        //浏览量
        produce_look_tv_fx = (TextView) view_detaile.findViewById(R.id.produce_look_tv_fx);
        //收藏量
        produce_heart_tv_fx = (TextView) view_detaile.findViewById(R.id.produce_heart_tv_fx);
        produce_iv_heart_fx = (ImageView) view_detaile.findViewById(R.id.produce_iv_heart_fx);
        produce_heart_tv_fx.setOnClickListener(this);
        produce_iv_heart_fx.setOnClickListener(this);

        linear_horizontal = (LinearLayout) view_detaile
                .findViewById(R.id.linear_horizontal);
        goods_min_num = (TextView) view_detaile
                .findViewById(R.id.goods_min_num);
        goods_gg = (TextView) view_detaile.findViewById(R.id.goods_gg);
        tv_yunfei = (TextView) view_detaile.findViewById(R.id.tv_yunfei);
        view_line_two = (TextView) view_detaile.findViewById(R.id.view_line_two);
        iv_jinxuan = (ImageView) view_detaile.findViewById(R.id.iv_jinxuan);
        linear_right = (LinearLayout) findViewById(R.id.base_activity_title_right_parent);
        linear_qpl = (LinearLayout) view_detaile.findViewById(R.id.linear_qpl);

        time = (LinearLayout) view_detaile.findViewById(R.id.time);
        time_linear = (LinearLayout) view_detaile.findViewById(R.id.time_linear);
        tv_tfx = (TextView) view_detaile.findViewById(R.id.tv_tfx);

        mViewPager = (CustomViewPager) view_detaile
                .findViewById(R.id.product_detail_vp);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setFocusable(true);
        linear_tel.setOnClickListener(this);
        linear_moreinfo.setOnClickListener(this);
        relative_more.setOnClickListener(this);
        linear_horizontal.setOnClickListener(this);
        linear_look.setOnClickListener(this);
        linear_look_fx.setOnClickListener(this);
        setH5(webview_h5);
        initData();
    }

    /**
     * Describe:初始化数据
     * <p>
     * Date:2015-9-23
     * <p>
     * Author:liuzhouliang
     */
    private void initData() {
        // TODO Auto-generated method stub
        // AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
        // alpha.setDuration(0); // Make animation instant
        // alpha.setFillAfter(true); // Tell it to persist after the animation
        // ends
        // llBottomParent.startAnimation(alpha);
        llBottomParent.getBackground().setAlpha(204);
        llVpNumParent.getBackground().setAlpha(125);
        addShoppingCartMessageHandler = new AddShoppingCartMessageHandler(this);
        productMessageHandler = new ProductDetailMessageHandler(this);
        // orderMessageHandler = new MyOrderHandler(this);
        selectProductNotInEdit = new ArrayList<ShoppingCartSelectBean>();

        myApplication = MyApplication.getInstance();
//		mWIMKit = myApplication.getmIMKit();
        isPush = getIntent().getBooleanExtra("isPush", false);
//		if (isPush)
//			SharedPreferencesUtil.writeSharedPreferencesBoolean(this,
//					"message", "new", false);
        // ToastUtil.showToast(baseContext, "活动ID" + productIdString);
        try {
            if (SecurityInit.Initialize(this) == 0) {
                SecurityCipher securityCipher = new SecurityCipher(this);
                String wx_later_wx = securityCipher.decryptString(
                        ConstantsUtil.APP_ID, ConstantsUtil.JAQ_KEY);
                api = WXAPIFactory.createWXAPI(this, wx_later_wx);
            } else {
                api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
            }
        } catch (JAQException e) {
            // TODO Auto-generated catch block
            api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
            e.printStackTrace();
        }

    }

    @Override
    protected void getNetData() {
        // 获取商品信息页
        ApiHttpCilent.getInstance(getApplicationContext()).getNewProductDetailInfor(this,
                productIdString, new GetProductDetailRequestCallBack());

        // 获取客服列表
//        ApiHttpCilent.getInstance(this)
//                .RequsetIM(this, new ImRequestCallBack());

        // 获取购物车数据
        getShoppingCartDate();


//        // 获取需要分享数据
//        ShareUrl();
    }

    void getShoppingCartDate() {
        // 获取购物车数据
        ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(baseActivity,
                new ShoppingCartInforRequestCallBack());
    }

    /*
         * 获取地图分布数据 *
         */
    public class MapListRequestCallBack extends
            BaseJsonHttpResponseHandler<GoodsMapList> {

        @Override
        public void onSuccess(int i, Header[] headers, String s, GoodsMapList goodsMapList) {
            Dimess();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, GoodsMapList arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            addShoppingCartMessageHandler.sendMessage(message);
        }


        @Override
        protected GoodsMapList parseResponse(String response,
                                             boolean arg1) throws Throwable {
            Dimess();
            Gson gson = new Gson();
            goodsMapList = gson.fromJson(response,
                    GoodsMapList.class);
            Message message = Message.obtain();
            if ("1".equals(goodsMapList.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
                message.obj = goodsMapList.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = goodsMapList.getError().getInfo();
            }
            addShoppingCartMessageHandler.sendMessage(message);
            return goodsMapList;
        }
    }

    /*
     * 获取购物车数据 *
     */
    public class ShoppingCartInforRequestCallBack extends
            BaseJsonHttpResponseHandler<NewShoppingCartInforBean> {

        @SuppressWarnings("deprecation")
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, NewShoppingCartInforBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            addShoppingCartMessageHandler.sendMessage(message);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              NewShoppingCartInforBean arg3) {
            Dimess();
        }

        @Override
        protected NewShoppingCartInforBean parseResponse(String response,
                                                         boolean arg1) throws Throwable {
            Dimess();
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
            addShoppingCartMessageHandler.sendMessage(message);
            return shoppingCartData;
        }
    }

    /**
     * Describe:数据请求回调
     * <p>
     * Date:2015-10-12
     * <p>
     * Author:liuzhouliang
     */
    public class GetProductDetailRequestCallBack extends
            BaseJsonHttpResponseHandler<NewProductDetailInfor> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, NewProductDetailInfor arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            productMessageHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              NewProductDetailInfor arg3) {
        }

        @Override
        protected NewProductDetailInfor parseResponse(String response,
                                                      boolean arg1) throws Throwable {
            Dimess();
            Gson gson = new Gson();
            try {
                productDetailInfor = gson.fromJson(response,
                        NewProductDetailInfor.class);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
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

    /*
     * Im聊天
     */
    public class ImRequestCallBack extends
            BaseJsonHttpResponseHandler<ImBaseBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, ImBaseBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            productMessageHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              ImBaseBean arg3) {
        }

        @Override
        protected ImBaseBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimess();
            Gson gson = new Gson();
            ImBaseBean sharebean = gson.fromJson(response, ImBaseBean.class);
            Message message = Message.obtain();
            if ("1".equals(sharebean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
                message.obj = sharebean.getResult().getList();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = sharebean.getError().getInfo();
            }
            productMessageHandler.sendMessage(message);
            return sharebean;
        }
    }

    /*
     * 一键分享
     */
    public class ShareRequestCallBack extends
            BaseJsonHttpResponseHandler<ShareBaseBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, ShareBaseBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            productMessageHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              ShareBaseBean arg3) {
        }

        @Override
        protected ShareBaseBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimess();
            Gson gson = new Gson();
            ShareBaseBean sharebean = gson.fromJson(response,
                    ShareBaseBean.class);
            Message message = Message.obtain();
            if ("1".equals(sharebean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
                message.obj = sharebean.getResult().getShareurl();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = sharebean.getError().getInfo();
            }
            productMessageHandler.sendMessage(message);
            return sharebean;
        }
    }

    /**
     * Describe:处理消息返回
     * <p>
     * Date:2015-10-12
     * <p>
     * Author:liuzhouliang
     */
    public static class ProductDetailMessageHandler extends
            WeakHandler<NewProductDetailActivity> {

        public ProductDetailMessageHandler(NewProductDetailActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 501:
                    //获取添加结果
                    if(getReference().collectResultListBean != null){
                        getReference().setSuccessCollect();
                    }
                    break;
                case ConstantsUtil.HTTP_SUCCESS:
                    /**
                     * 处理成功的数据
                     */
                    getReference().productInfor = getReference().productDetailInfor
                            .getResult();
                    getReference().bindViewData();
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LOGIN:
                    /**
                     * 处理成功的获取的IM数据
                     */
                    @SuppressWarnings("unchecked")
                    ArrayList<ImHeHe> imList = (ArrayList<ImHeHe>) msg.obj;
                    if (imList != null && imList.size() > 0) {
                        getReference().groupName = imList.get(0).getName();
                        getReference().groupId = imList.get(0).getGroupid();
                    }
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
                    String shareurl = (String) msg.obj;
                    // 获取分享url
                    Intent intent = new Intent(getReference(),
                            PayTypeActivity.class);
                    intent.putExtra("share", "share");
                    intent.putExtra("url", "快来喝喝拼单吧  商品:"
                            + getReference().productInfor.getName() + "\r\n"
                            + shareurl);
                    StartActivityUtil.startActivity(getReference(), intent);
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LATER:
                    // 获取基本认证信息
                    break;
                case ConstantsUtil.ALI_BACK_CODE:
                    // 生成预览订单成功
                    getReference().orderbean = (OrderList) msg.obj;
                    getReference().ToOrderPre(getReference().orderbean);
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    /**
                     * 处理失败的数据
                     */
                    String errorBean = (String) msg.obj;
                    if (!StringUtil.isEmpty(errorBean)) {
                        ToastUtil.showToast(getReference(), errorBean);
//					if ("100".equals(ErrorBean.getCode())) {
//						LoginSampleHelper.getInstance().loginOut_Sample(
//								getReference().baseActivity);
//					}
                    }
                    break;
                case ConstantsUtil.HTTP_NEED_LOGIN:
                    //需要登录
                    MyApplication.getInstance().startLogin(new LoginCallBack() {

                        @Override
                        public void loginSuccess() {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(getReference().baseActivity, NewProductDetailActivity.class);
                            intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, getReference().productIdString);// 活动ID
                            StartActivityUtil.startActivity(getReference().baseActivity, intent);
                            getReference().baseActivity.finish();
                        }

                        @Override
                        public void loginFail() {
                            // TODO Auto-generated method stub

                        }
                    }, getReference().baseActivity);
                    break;
                case 500:
                    //获取分享内容
                    break;
            }
        }

    }

    /**
     * 获取关注结果
     * */
    private void setSuccessCollect(){

        if(productInfor!=null && "1".equals(productInfor.getIsFavorite())){
            //已经收藏过
            Toast.makeText(baseActivity,"取消收藏成功",Toast.LENGTH_SHORT).show();
            productInfor.setIsFavorite("0");
            setCollectbg(true);
        }else if(productInfor!=null && "0".equals(productInfor.getIsFavorite())){
            Toast.makeText(baseActivity,"收藏成功",Toast.LENGTH_SHORT).show();
            setCollectbg(false);
            productInfor.setIsFavorite("1");
        }
    }

    private void setCollectbg(boolean isCollect){
        if(isCollect) {
            nowFlowAddDel = nowFlow - collectResultListBean.getCount();
         }else{
              nowFlowAddDel = nowFlow + collectResultListBean.getCount();
            }
        nowFlow = nowFlowAddDel;
        if (type.equals(HHM + "")) {
            produce_heart_tv.setText(nowFlowAddDel +"");
            if(!isCollect)
            produce_iv_heart_hhm.setImageResource(R.drawable.good_callocationed);
            else
            produce_iv_heart_hhm.setImageResource(R.drawable.good_callocation);
        }else if(type.equals(TM + "")){
            produce_heart_tv_tm.setText(nowFlowAddDel +"");
            if(!isCollect)
            produce_iv_heart_tm.setImageResource(R.drawable.good_callocationed);
            else
            produce_iv_heart_tm.setImageResource(R.drawable.good_callocation);
        }else if(type.equals(EFX + "")){
            produce_heart_tv_fx.setText(nowFlowAddDel +"");
            if(!isCollect)
            produce_iv_heart_fx.setImageResource(R.drawable.good_callocationed);
             else
            produce_iv_heart_fx.setImageResource(R.drawable.good_callocation);
        }
    }
    // 跳转到生成预览订单界面
    private void ToOrderPre(OrderList bean) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setClass(baseContext, NewOrderDetailActivity.class);
        intent.putExtra("bean", orderbean);
        intent.putExtra("from", "0");
        // intent.putExtra("islist", false);//代码是列表传递 记录当前页面返回还是当前页面
        intent.putExtra("selectProductNotInEditToNext",
                (Serializable) selectProductNotInEdit);
        StartActivityUtil.startActivity((Activity) baseContext, intent);
    }

    private boolean isShowActivityIcon(String state, ImageView iv) {
        /*
		 * 5、已售罄 6、已下架 拼单： 1、正常 2、已抢光 3、已结束 4、已成单
		 */
        iv.setVisibility(View.VISIBLE);
        if ("0".equals(state)) {
            /**
             * 未开始
             */
            iv.setVisibility(View.INVISIBLE);
            return false;
        } else if ("1".equals(state)) {
            /**
             * 已经开始
             */
            iv.setVisibility(View.INVISIBLE);
            return false;
        } else if ("2".equals(state)) {
            /**
             * 已经抢光
             */
            iv.setImageResource(R.drawable.yiqiangguang);
            return true;
        } else if ("3".equals(state)) {
            /**
             * 已经结束
             */
            iv.setImageResource(R.drawable.yichengdan);
            return true;
        } else if ("4".equals(state)) {
            /**
             * 已经成单 不能生成订单
             */
            iv.setImageResource(R.drawable.yichengdan);
            return true;
        } else if ("5".equals(state)) {
            /**
             * 已经售罄
             */
            iv.setImageResource(R.drawable.yishouqing);
            return true;
        } else if ("6".equals(state)) {
            /**
             * 已经下架
             */
            iv.setImageResource(R.drawable.yishouqing);
            return true;
        } else {
            iv.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    // 设置H5页面
    void setH5Url(String url, WebView webview) {
        // 获取session 加载到url中
        List<Cookie> listcook = MyApplication.getInstance().getCookieStore()
                .getCookies();
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
                cookieManager.setCookie(url, cookieString);
                CookieSyncManager.getInstance().sync();
            }
        }
        webview.loadUrl(url);
    }

    /**
     * Describe:绑定控件数据
     * <p>
     * Date:2015-10-12
     * <p>
     * Author:liuzhouliang
     */
    String type;
    private void bindViewData() {

        if (productDetailInfor != null) {
            productInfor = productDetailInfor.getResult();
            linear_moreinfo.setVisibility(View.VISIBLE);
            if (productInfor != null) {
                // 0:普通商品 1:组合套装
                setH5Url(productInfor.getIntroduce_url(), h5_webview_main);
                setH5Url(productInfor.getIntroduce_url(), webview_h5);
                pic = productInfor.getPic();


                if (pic != null && pic.size() > 0) {
                    mViewPager.setAdapter(new ImagePagerAdapter(pic, productInfor.getIssuit(), baseActivity));
                    mViewPager.setCurrentItem(0);
                    mViewPager.addOnPageChangeListener(new PicPageChangeListener());// 设置监听
                    tvCurrentNum.setText("1");
                    tvTotalNum.setText(pic.size() + "");
                } else {
                    pic = new ArrayList<>();
                    pic.add("");
                    mViewPager.setAdapter(new ImagePagerAdapter(pic, productInfor.getIssuit(), baseActivity));
                    mViewPager.setCurrentItem(0);
                    mViewPager.addOnPageChangeListener(new PicPageChangeListener());// 设置监听
                    tvCurrentNum.setText("1");
                    tvTotalNum.setText(pic.size() + "");
                }
                LayoutParams para;
                para = mViewPager.getLayoutParams();
                para.width = LinearLayout.LayoutParams.MATCH_PARENT;
                if (productInfor.getIssuit() == 1) {
                    para.height = (ViewUtil.dip2px(this, (float) 375.00));
                    mViewPager.setLayoutParams(para);
                }
                 type = productInfor.getType();
                if (type.equals(HHM + "")) {
                    /**
                     * 合伙买
                     * */
                    //显示当前视图 按需加载 优化性能
                    vb_hhm.setVisibility(View.VISIBLE);
                    linear_qpl.setVisibility(View.VISIBLE);
                    linear_cod.setVisibility(View.GONE);
                    goods_name_hh.setText(productInfor.getName());
                    produce_price_and_unit.setText("¥ " + productInfor.getCurrentprice() + "/" + productInfor.getUnit());
                    if(!StringUtil.isEmpty(productInfor.getDeprice()) && Double.parseDouble(productInfor.getDeprice())> 0) {
                        produce_price_dj.setText("(订金:¥ " + productInfor.getDeprice()+"/"+ productInfor.getUnit() + ")");
                        produce_price_dj.setVisibility(View.VISIBLE);
                    }else{
                        produce_price_dj.setVisibility(View.GONE);
                    }

                    //进度条
                    if (!StringUtil.isEmpty(productInfor.getSoldnum())
                            && !StringUtil.isEmpty(productInfor.getKnum())) {
                        float proportion = Float.parseFloat(productInfor.getSoldnum())
                                / (Integer.parseInt(productInfor.getKnum())+ Float.parseFloat(productInfor.getSoldnum()));
                        int progress = (int) (proportion * 100);
//                        Resources res = this.getResources();
//						group_buy_grid_item_pb.setProgressDrawable(res
//								.getDrawable(R.drawable.progress_2));
////								.getDrawable(R.drawable.good_pb));
                        group_buy_grid_item_pb.setProgress(progress);
                    }

                    //已售
                    poduce_jxz_num.setText(productInfor.getSoldnum() + " / ");
                    //库存
                    poduce_jxz_num_total_unit.setText((Integer.parseInt(productInfor.getKnum()) +Integer.parseInt(productInfor.getSoldnum())) + productInfor.getUnit());

                    //浏览量
                    produce_look_tv.setText(productInfor.getViewNum());
                    //收藏
                    produce_heart_tv.setText(productInfor.getFlowNum());
                    nowFlow = Integer.parseInt(productInfor.getFlowNum());
                    //已卖
                    produce_tv_people.setText(productInfor.getSoldnum() + "(看谁买了?)");

                    llBottomParent.setVisibility(View.VISIBLE);
                    tv_dl.setVisibility(View.GONE);
                    if("1".equals(productInfor.getIsFavorite())){
                        produce_iv_heart_hhm.setImageResource(R.drawable.good_callocationed);
                    }else{
                        produce_iv_heart_hhm.setImageResource(R.drawable.good_callocation);
                    }
//                    2, "已成单"
//                    12, "未成单"
//                    1:进行中
                    if("2".equals(productInfor.getStatus())){
                        produce_jxz.setText("已成单");
                        llBottomParent.setVisibility(View.GONE);
                    }else if("12".equals(productInfor.getStatus())){
                        produce_jxz.setText("未成单");
                        llBottomParent.setVisibility(View.GONE);
                    }
                } else if (type.equals(TM + "")) {
                    /**
                     * 特卖
                     * */
                    vb_tm.setVisibility(View.VISIBLE);
                    linear_qpl.setVisibility(View.VISIBLE);
                    //特卖显示套装
                    relative_more.setVisibility(View.VISIBLE);
                    produce_goods_name_tm.setText(productInfor.getName());
                    produce_price_and_unit_tm.setText("¥"+productInfor.getCurrentprice() + "/" + productInfor.getUnit());
                    //销量
                    produce_price_and_unit_xl.setText("销量：" + productInfor.getSoldnum() + productInfor.getUnit());
                    //库存
                    produce_price_and_unit_syl.setText("剩余量：" + productInfor.getKnum() + productInfor.getUnit());

                    //浏览量
                    produce_look_tv_tm.setText(productInfor.getViewNum());
                    //收藏
                    produce_heart_tv_tm.setText(productInfor.getFlowNum());
                    nowFlow = Integer.parseInt(productInfor.getFlowNum());
                    //已卖
                    produce_tv_people_tm.setText(productInfor.getBuyerNum()+ "(看谁买了?)");
                    //绑定套装视图
                    BindSuitView();
                    llBottomParent.setVisibility(View.VISIBLE);
                    tv_dl.setVisibility(View.GONE);
                    if("1".equals(productInfor.getIsFavorite())){
                        produce_iv_heart_tm.setImageResource(R.drawable.good_callocationed);
                    }else{
                        produce_iv_heart_tm.setImageResource(R.drawable.good_callocation);
                    }

//                    if (productInfor.getIssuit() == 1) {
//                        // 套装
//                        // 剩下库存
//                        tv_kc.setText(StringUtil.isEmpty(productInfor.getKnum()) ? ""
//                                : productInfor.getKnum()+productInfor.getUnit());
//                        goods_name.setText(productInfor.getName());
//                        ViewUtil.setActivityPrice(goods_price,
//                                productInfor.getCurrentprice());
//                        tv_unit.setText("/" + productInfor.getUnit());
//                        goods_buyed.setText("销量:" + productInfor.getSalesvol()
//                                + productInfor.getUnit());
//                    }
                } else if (type.equals(EFX + "")) {
                    /**
                     * 发行
                     * */
                    llBottomParent.setVisibility(View.GONE);
                    if("0".equals(productInfor.getStatus())) {
                        tv_dl.setVisibility(View.GONE);
                        lnclude_buttom.setVisibility(View.GONE);
                    }else{
                        tv_dl.setVisibility(View.VISIBLE);
                        lnclude_buttom.setVisibility(View.VISIBLE);
                    }
                    vb_fx.setVisibility(View.VISIBLE);
//                    if (!StringUtil.isEmpty(productInfor.getShopurl()))
//                        setH5Url(productInfor.getShopurl(), webview_business_h5);
                    produce_goods_name_fx.setText(productInfor.getName());
                    //价格
                    produce_price_and_unit_fx.setText("¥ "+productInfor.getCurrentprice() + "/" + productInfor.getUnit());

                    //规格
                    produce_guige_fx.setText("规格 : "+productInfor.getBottlevol());
                    //规格
                    produce_area_fx.setText("发行区域 : "+productInfor.getReleaseArea());

                    //浏览量
                    produce_look_tv_fx.setText(productInfor.getViewNum());
                    //收藏
                    produce_heart_tv_fx.setText(productInfor.getFlowNum());
                    nowFlow = Integer.parseInt(productInfor.getFlowNum());

                    if("1".equals(productInfor.getIsFavorite())){
                        produce_iv_heart_fx.setImageResource(R.drawable.good_callocationed);
                    }else{
                        produce_iv_heart_fx.setImageResource(R.drawable.good_callocation);
                    }
                }



// else{
                // 普通商品
//				if(productInfor.getRecomDetail() != null)
//				   MyApplication.imageLoader.displayImage(productInfor.getRecomDetail().getImgUrl(), iv_advertising,
//						MyApplication.options);
//					linear_single.setVisibility(View.VISIBLE);
//					linear_tz.setVisibility(View.GONE);
                if (!StringUtil.isEmpty(productInfor.getMinnum()))
                    minBuyNum = Integer.parseInt(productInfor.getMinnum());
//					goods_name.setText(productInfor.getName());
//					ViewUtil.setActivityPrice(goods_price,
//							productInfor.getCurrentprice());
//					tv_unit.setText("/" + productInfor.getUnit());
                // 获取最大购买量
                maxBuyNum = (Integer.parseInt(productInfor.getKnum()) - Integer
                        .parseInt(productInfor.getSoldnum()));
                if (productInfor.getIscod() == 1)
                    supportarrive.setText("支持");
                else
                    supportarrive.setText("不支持");
                goods_min_num.setText(back(productInfor.getMinnum()));
                goods_gg.setText(back(productInfor.getBottlevol()));
                tv_yunfei.setText(back(productInfor.getTransdesc()));

                baseInfo = productInfor.getBaseinfo();
//                if (baseInfo != null) {
//                    goods_pp.setText(back(baseInfo.getbrand()));
//                    goods_area.setText(back(baseInfo.getorigin()));
//                    goods_tj.setText(back(baseInfo.getStorage()));
//                    goods_yl.setText(back(baseInfo.getMaterials()));
//                    goods_ds.setText(back(baseInfo.getAlcohol()));
//                    goods_jhl.setText(back(baseInfo.getContent()));
//                }
//                0合伙买 1甩卖 2:甩卖活动 3:E发行
                if ("3".equals(productInfor.getType())) {
                    tv_tfx.setVisibility(View.VISIBLE);
                    /**
                     * 处理倒计时
                     */
                    String endTime = productInfor.getDiffTime();
                    if(!StringUtil.isEmpty(endTime)) {
                        tv_tfx.setText("距发行时间: " + endTime);
                        time_linear.setVisibility(View.VISIBLE);
                    }else{
                        time_linear.setVisibility(View.GONE);
                        produce_title_tip_fx.setText("发行价");
                    }
                } else {
                    time_linear.setVisibility(View.GONE);
                }

                    /**
                     * 控制商品状态：是否活动结束
                     */
                    String activityStateString = productInfor.getStatus();
                    if (!StringUtil.isEmpty(activityStateString)) {
                        if ("1".equals(activityStateString)) {
                            /**
                             * 已经发布
                             */
                            btAddShoppingCart.setOnClickListener(this);
                            btAddShoppingCart
                                    .setBackgroundColor(getResources()
                                            .getColor(R.color.color_FFFDC901));
                            bt_buy.setBackgroundColor(getResources()
                                    .getColor(R.color.color_FF372D6C));
                            bt_buy.setOnClickListener(this);
                            bt_buy.setEnabled(true);
                        } else if ("2".equals(activityStateString)) {
                            /**
                             * 已经抢光
                             */
                            btAddShoppingCart
                                    .setBackgroundResource(R.drawable.bg_gray_corner_rec);
                            btAddShoppingCart.setOnClickListener(null);
                            bt_buy.setBackgroundResource(R.drawable.bg_gray_corner_rec);
                            bt_buy.setOnClickListener(null);
                            bt_buy.setEnabled(false);
                        } else if ("3".equals(activityStateString)) {
                            /**
                             * 已经结束
                             */
                            btAddShoppingCart
                                    .setBackgroundResource(R.drawable.bg_gray_corner_rec);
                            btAddShoppingCart.setOnClickListener(null);
                            bt_buy.setBackgroundResource(R.drawable.bg_gray_corner_rec);
                            bt_buy.setOnClickListener(null);
                            bt_buy.setEnabled(false);
                        } else {
                            btAddShoppingCart.setOnClickListener(this);
                            btAddShoppingCart
                                    .setBackgroundColor(getResources().getColor(R.color.color_333333));
                            bt_buy.setEnabled(true);
                        }
                    } else {
                        btAddShoppingCart.setOnClickListener(this);
                        btAddShoppingCart.setBackgroundColor(getResources()
                                .getColor(R.color.color_333333));
                        bt_buy.setEnabled(true);
                    }

//                goods_cs.setText(back(productInfor.getFactory()));
//                goods_pz.setText(back(baseInfo.getvariety()));
//                goods_zl.setText(back(baseInfo.getvariety1()));
//                TextView02.setText(back(productInfor.getShopname()));
//                tv_fl.setText(back(baseInfo.getvariety2()));
            }
        }
        //设置viewpage数据
        viewContainter.add(view_detaile);
        viewContainter.add(view_h5);
        viewAdapter = new MyPagerAdapter(viewContainter);
        pageChangeLister = new MyOnPageChangeLister();
        pagerContent.setAdapter(viewAdapter);
        pagerContent.addOnPageChangeListener(pageChangeLister);
        pagerContent.setCurrentItem(0);
        viewAdapter.notifyDataSetChanged();
        menu_parent.SetH5Detaile(productInfor.getIntroduce_url());
        menu_parent.setCallBack(new SlidingMenu.HindTitle() {
            @Override
            public void CenterTitle(boolean isHind) {
                    if(isHind){
                        SetTopAnimation();
                    }else{
                        SetButtomAnimation();
                    }
            }
        });
    }
     /**
      *
      * 生成套餐视图
      * */
    private void BindSuitView() {
        if (Integer.parseInt(StringUtil.isEmpty(productInfor.getSuitcount()) ? "0" : productInfor.getSuitcount()) <= 0) {
            relative_more.setVisibility(View.GONE);
            view_line_two.setVisibility(View.GONE);
        } else {
            relative_more.setVisibility(View.VISIBLE);
            view_line_two.setVisibility(View.VISIBLE);
        }
        Suitinfo suitinfo = productInfor.getSuit();
        if (suitinfo != null && suitinfo.getListItem() != null) {
            List<listItemDate> listDate = suitinfo.getListItem();
            int size = listDate.size();
            tv_num.setText("共" + size + "件");
            if (size > 2)
                tv_dd.setVisibility(View.VISIBLE);
            else
                tv_dd.setVisibility(View.INVISIBLE);
            if (listDate != null && size > 0) {
                for (int i = 0; i < size; i++) {
                    View view = getLayoutInflater().inflate(
                            R.layout.item_image_suit, null);
                    LinearLayout linear_thimal = (LinearLayout) view
                            .findViewById(R.id.linear_thimal);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewUtil.dip2px(this, 65), ViewUtil.dip2px(this, 65));
                    linear_thimal.setLayoutParams(lp);
                    iv_thimal = (ImageView) view
                            .findViewById(R.id.iv_thimal);
                    iv_jia = (ImageView) view
                            .findViewById(R.id.iv_jia);
                    MyApplication.imageLoader.displayImage(listDate
                                    .get(i).getPic(), iv_thimal,
                            MyApplication.options);
                    if (i == size - 1)
                        iv_jia.setVisibility(View.GONE);
                    linear_horizontal.addView(view);
                }
            }
        }

        tv_more.setText("共" + productInfor.getSuitcount() + "个套装");
        tv_save.setText("立省" + suitinfo.getSuitSave() + "元");
        tv_tzj.setText("¥ " + (StringUtil.isEmpty(suitinfo.getSuitPrice()) ? ""
                : suitinfo.getSuitPrice()));
    }

    private String back(String tv) {
        if (StringUtil.isEmpty(tv))
            return "—";
        else
            return tv;

    }

    /**
     * Describe:设置商品基础信息
     * <p>
     * Date:2015-10-13
     * <p>
     * Author:liuzhouliang
     */
    private void setBaseProductInfor(NewProductBaseInfor baseInfor) {
        baseInforBeans = new ArrayList<PoductDetailBaseInforBean>();

//		if (!StringUtil.isEmpty(baseInfor.getflavor())) {
//			/**
//			 * 香型不为空
//			 */
//			PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
//			obj.setNameString("香型: ");
//			obj.setContenString(baseInfor.getflavor());
//			baseInforBeans.add(obj);
//		}

        if (!StringUtil.isEmpty(baseInfor.getMaterials())) {
            /**
             * 原料不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("原料: ");
            obj.setContenString(baseInfor.getMaterials());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getAlcohol())) {
            /**
             * 度数不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("度数: ");
            obj.setContenString(baseInfor.getAlcohol());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getcategory())) {
            /**
             * 品类不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("品类: ");
            obj.setContenString(baseInfor.getcategory());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getStorage())) {
            /**
             * 存储条件不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("存储条件: ");
            obj.setContenString(baseInfor.getStorage());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getbrand())) {
            /**
             * 品牌不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("品牌: ");
            obj.setContenString(baseInfor.getbrand());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getorigin())) {
            /**
             * 产地不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("产地: ");
            obj.setContenString(baseInfor.getorigin());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getvariety())) {
            /**
             * 品类不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("品类: ");
            obj.setContenString(baseInfor.getvariety());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(baseInfor.getContent())) {
            /**
             * 净含量不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("净含量: ");
            obj.setContenString(baseInfor.getContent());
            baseInforBeans.add(obj);
        }
        if (!StringUtil.isEmpty(productInfor.getFactory())) {
            /**
             * 厂家不为空
             */
            PoductDetailBaseInforBean obj = new PoductDetailBaseInforBean();
            obj.setNameString("厂家: ");
            obj.setContenString(productInfor.getFactory());
            baseInforBeans.add(obj);
        }
        if (baseInforBeans != null && baseInforBeans.size() > 0) {
            /**
             * 绑定基础商品信息数据
             */
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
//		llShareParent.setOnClickListener(this);
//        linear_tj.setOnClickListener(this);
//        linear_yl.setOnClickListener(this);
        linear_right.setOnClickListener(this);
    }

    @Override
    protected String setTitleName() {
        return "商品";
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

    // 获取购物车当前单品的数据
    private int getCartNum() {
        if (shoppingCartData != null && shoppingCartData.getResult() != null
                && shoppingCartData.getResult().getList() != null) {
            ArrayList<NewProductInfo> listShoppingCart = (ArrayList<NewProductInfo>) shoppingCartData
                    .getResult().getList();
            if (productInfor != null) {
                for (NewProductInfo shoppingproductInfo : listShoppingCart) {
                    // 如果购物车里面有当前加入的商品 获取数据
                    if (shoppingproductInfo.getAid().equals(
                            productInfor.getId())) {
                        return Integer.parseInt(shoppingproductInfo.getNum());
                    }
                }
            }
            return 0;
        } else {
            return 0;
        }
    }


    void OpenBaiduMap(Intent intent) {
        ApiHttpCilent.getInstance(getApplicationContext()).GoodsMapList(this,
                productIdString, new MapListRequestCallBack());
    }

//    //获取分享URl
//    private void ShareUrl(){
//        ApiHttpCilent.getInstance(this).getShareUrl(this,productIdString,new MyShareJsonHttpResponseHandler());
//    }
    /**
     * 获取扫描结果
     * */

    public class MyShareJsonHttpResponseHandler extends BaseJsonHttpResponseHandler<ShareUrlResultBean> {

        @Override
        public void onSuccess(int i, Header[] headers, String s, ShareUrlResultBean shareUrlResultBean) {
            Dimess();
        }

        @Override
        public void onFailure(int i, Header[] headers, Throwable throwable, String s, ShareUrlResultBean shareUrlResultBean) {
            Dimess();
            Message msg = new Message();
            msg.obj = new ShareUrlResultBean.ErrorScan();
            productMessageHandler.obtainMessage(ConstantsUtil.HTTP_FAILE,msg);
        }

        @Override
        protected ShareUrlResultBean parseResponse(String result, boolean b) throws Throwable {
            Dimess();
            Gson gson = new Gson();
             ShareresultBean = gson.fromJson(result,ShareUrlResultBean.class);
            if(ShareresultBean !=null){
                String status = ShareresultBean.getStatus();
                Message msg = new Message();
                if(status.equals("1")){
                    msg.obj = ShareresultBean.getResult();
                    msg.what = 500;
                    productMessageHandler.sendMessage(msg);
                }else{
                    msg.obj = ShareresultBean.getError();
                    msg.what = ConstantsUtil.HTTP_FAILE;
                    productMessageHandler.sendMessage(msg);
                }
            }
            return ShareresultBean;
        }

    }

    private void ClickShare(){
        if(productDetailInfor != null && productDetailInfor.getResult() != null && productDetailInfor.getResult().getActivityShare() != null){

            shareUrl = productDetailInfor.getResult().getActivityShare().getShareUrl();
            if(!StringUtil.isEmpty(shareUrl)){

            // 一键分享
            Intent intent = new Intent(this,
                    PayTypeActivity.class);
            intent.putExtra("share", "share");
            intent.putExtra("url", shareUrl);
            intent.putExtra("pic",productDetailInfor.getResult().getActivityShare().getPic());
            intent.putExtra("title", productDetailInfor.getResult().getActivityShare().getTitle());
            StartActivityUtil.startActivityFromBottom(this, intent);

            }
        }
    }

    /**
     * 添加删除收藏
     * */
    private void SetCollect(){
          ApiHttpCilent.getInstance(getApplicationContext()).AddCollectApi(this,collectId,productIdString,flag,new AddCollectCallBack());
    }

    public class AddCollectCallBack extends
            BaseJsonHttpResponseHandler<AddCollectResultBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, AddCollectResultBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            productMessageHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              AddCollectResultBean arg3) {
        }

        @Override
        protected AddCollectResultBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimess();
            Gson gson = new Gson();
            AddCollectResultBean searchResultBean = gson.fromJson(response,
                    AddCollectResultBean.class);
            Message message = Message.obtain();
            if ("1".equals(searchResultBean.getStatus())) {// 正常
                message.what = 501;
                collectResultListBean = searchResultBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = searchResultBean.getError().getInfo();
            }
            productMessageHandler.sendMessage(message);
            return searchResultBean;
        }
    }
    @Override
    public void onClick(View v) {
//		super.onClick(v);
        if (productInfor != null) {
            issold = Integer.parseInt(productInfor.getSoldnum());// 已售
            knum = Integer.parseInt(productInfor.getKnum());// 库存
        }
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_dl:
                CallTel();
                break;
            case R.id.linear_look_tm:
                OpenBaiduMap(intent);
                break;
            case R.id.linear_iv_heart_tm:
                //特卖加入收藏
                ToCollect();
                break;
            case R.id.linear_iv_heart:
                ToCollect();
                break;
            case R.id.produce_heart_tv_fx:
                ToCollect();
                break;
            case R.id.iv_share:
                ClickShare();
                break;
            case R.id.linear_share:
                ClickShare();
                break;
            case R.id.linear_look:
                OpenBaiduMap(intent);
                break;
            case R.id.linear_look_fx:
                ToCollect();
                break;
            case R.id.produce_iv_heart_fx:
                ToCollect();
                break;
            case R.id.produce_iv_heart_tm:
                ToCollect();
                break;
            case R.id.produce_iv_heart:
                ToCollect();
                break;
            case R.id.product_detail_add_shoppingcart:
                /**
                 * 添加购物车
                 */
                if (productDetailInfor != null
                        && productDetailInfor.getResult() != null
                        && productDetailInfor.getResult().getType() != null) {
                    if ("1".equals(productDetailInfor.getResult().getType()))
                        MobclickAgent.onEvent(baseActivity, "C_SAL_INF_6");
                    else
                        MobclickAgent.onEvent(baseActivity, "C_PD_INF_2");
                }
                boolean isLogin = IsLogin.isLogin(this);
                if (isLogin) {
                    try {
                        if (productInfor != null) {
                            if (Long.parseLong(productInfor.getKnum()) == 0) {
                                ToastUtil.showToast(baseContext, "该商品已卖完，请选择其他商品");
                                return;
                            } else {
                                if(Integer.parseInt(productInfor
                                        .getMinnum()) > Long.parseLong(productInfor.getKnum())){
                                    //如果库存小于最小起批量直接全部买了
                                    addShoppingCart(productInfor.getId(), productInfor.getKnum(),
                                            productInfor.getType());
                                }else{
                                if (getCartNum() >= Integer.parseInt(productInfor
                                        .getMinnum())) {
                                    // 如果购物车有该商品并且数量大于最小起批量
                                    addShoppingCart(productInfor.getId(), "1",
                                            productInfor.getType());
                                } else if (getCartNum() == 0) {
                                    addShoppingCart(productInfor.getId(),
                                            productInfor.getMinnum(),
                                            productInfor.getType());
                                } else if (getCartNum() > 0
                                        && getCartNum() < Integer
                                        .parseInt(productInfor.getMinnum())) {
                                    // 当前数量大入0 小于最小起批量 直接加入最小起批量和当前购物车数量差值
                                    addShoppingCart(
                                            productInfor.getId(),
                                            ""
                                                    + (Integer
                                                    .parseInt(productInfor
                                                            .getMinnum()) - getCartNum()),
                                            productInfor.getType());
                                 }
                                }
                            }

                        }
                    } catch (NumberFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    MyApplication.getInstance().startLogin(new LoginCallBack() {
                        @Override
                        public void loginSuccess() {
                            try {

                                if (productInfor != null) {
                                    if (Long.parseLong(productInfor.getKnum()) == 0) {
                                        ToastUtil.showToast(baseContext,
                                                "该商品已卖完，请选择其他商品");
                                        return;
                                    } else {
                                        if (getCartNum() >= Integer
                                                .parseInt(productInfor.getMinnum())) {
                                            // 如果购物车有该商品并且数量大入最小起批量
                                            addShoppingCart(productInfor.getId(),
                                                    "1", productInfor.getType());
                                        } else if (getCartNum() == 0) {
                                            addShoppingCart(productInfor.getId(),
                                                    productInfor.getMinnum(),
                                                    productInfor.getType());
                                        } else if (getCartNum() > 0
                                                && getCartNum() < Integer
                                                .parseInt(productInfor
                                                        .getMinnum())) {
                                            // 当前数量大入0 小于最小起批量 直接加入最小起批量和当前购物车数量差值
                                            addShoppingCart(
                                                    productInfor.getId(),
                                                    ""
                                                            + (Integer
                                                            .parseInt(productInfor
                                                                    .getMinnum()) - getCartNum()),
                                                    productInfor.getType());
                                        }
                                    }
                                }
                            } catch (NumberFormatException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void loginFail() {
                        }
                    }, this);
                }

                break;
            case R.id.base_activity_title_right_righticon:
                /**
                 * 进入购物车事件
                 */
                NeedLogin();
                break;
            case R.id.relative_shopping:
                NeedLogin();
                break;
            case R.id.group_buy_list_shopping_cart:
                NeedLogin();
                break;
            case R.id.base_activity_title_right_parent:
                NeedLogin();
                break;
            case R.id.linear_more:
                if (productDetailInfor != null
                        && productDetailInfor.getResult() != null
                        && productDetailInfor.getResult().getType() != null) {
                    if ("1".equals(productDetailInfor.getResult().getType()))
                        MobclickAgent.onEvent(baseActivity, "C_SAL_INF_10");
                    else
                        MobclickAgent.onEvent(baseActivity, "C_PD_INF_6");
                }
                intent.setClass(baseContext, DrinkInfoActivity.class);
                intent.putExtra("h5url", productInfor.getIntroduce_url());
                intent.putExtra("title", "详情");
                StartActivityUtil.startActivity(NewProductDetailActivity.this,
                        intent);
                break;
            case R.id.bt_buy:
                if (productDetailInfor != null
                        && productDetailInfor.getResult() != null
                        && productDetailInfor.getResult().getType() != null) {
                    if ("1".equals(productDetailInfor.getResult().getType()))
                        MobclickAgent.onEvent(baseActivity, "C_SAL_INF_7");
                    else
                        MobclickAgent.onEvent(baseActivity, "C_PD_INF_3");
                    if (knum >= Integer.parseInt(productDetailInfor.getResult()
                            .getMinnum())) {
                        CreateOrderNow(productDetailInfor.getResult().getMinnum());
                    }else{
                            //如果库存小于最小起批量直接全部买了
                        CreateOrderNow(productDetailInfor.getResult().getKnum());
//                            addShoppingCart(productInfor.getId(), productInfor.getKnum(),
//                                    productInfor.getType());
                    }
                    if (knum <= 0) {
                        ToastUtil.showToast(baseActivity, "该商品已卖完，请选择其他商品吧");
                    }
                } else {
                    ToastUtil.showToast(baseActivity, "服务器异常,请稍后重试");
                }
                break;
//		case R.id.product_detail_share_parent:
//			// 一键分享
//			if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
//				ApiHttpCilent.getInstance(this).RequsetShare(this,
//						productInfor.getId(), new ShareRequestCallBack());
//			} else {
//				ToastUtil.showToast(baseActivity, "请您安装微信客户端再分享");
//			}
//			break;
//            case linear_tj:
                // 点击存储条件
//                ShowDialog(goods_tj, "储存条件:", back(baseInfo.getStorage()));
//                break;
//            case R.id.linear_yl:
                // 点击原料条件
//                ShowDialog(goods_yl, "原料:", back(baseInfo.getMaterials()));
//                break;
            case R.id.linear_sp:
                // 点击商品
                pagerContent.setCurrentItem(0);
                break;
            case R.id.linear_xq:
                // 点击详情
                pagerContent.setCurrentItem(1);
                break;
//            case R.id.linear_sj:
//                // 点击商家
//                pagerContent.setCurrentItem(2);
//                break;
            case R.id.linear_tel:
                // 弹出框
//                showPopUp(llBottomParent);
                if(productInfor != null && !StringUtil.isEmpty(productInfor.getHotline())) {
                    ShowDialog("是否拨打电话:" + productInfor.getHotline(), "温馨提示");
                }

                break;
//            case R.id.linear_moreinfo:
//                //上拉更多
//                menu_parent.openMenu();
//                SetTopAnimation();
//                break;
            case R.id.tv_ontel:
                // 电话
                if (productDetailInfor != null
                        &&
                        productDetailInfor.getResult() != null
                        && productDetailInfor.getResult().getType() != null) {
                    if ("1".equals(productDetailInfor.getResult().getType()))
                        MobclickAgent.onEvent(baseActivity, "C_SAL_INF_12");
                    else
                        MobclickAgent.onEvent(baseActivity, "C_PD_INF_8");
                    String tel = MyApplication.getInstance().getServiceline();
                    list.clear();
                    list.add(tel);
                    new AlertDialogCustom().CreateDialog(NewProductDetailActivity.this,
                            "喝喝客服", "", list, 1);
                    PopuwShowing();
                } else {
                    ToastUtil.showToast(baseActivity, "服务器异常,请稍后重试");
                }
                break;
//		case R.id.tv_online:
//			// 在线客服
//			MobclickAgent.onEvent(baseActivity, "C_SAL_INF_11");
//
//			if (mWIMKit != null) {
//				OpenAppService();
//			} else {
//				myApplication = MyApplication.getInstance();
//				mWIMKit = myApplication.getmIMKit();
//				if (mWIMKit != null){
//					OpenAppService();
//			    }else{
//			    	Utils.StartLogin(baseActivity,false);
//				}
//			}
//			PopuwShowing();
//			break;
            // 更多套装
            case R.id.relative_more:
                intent.putExtra("id", productInfor.getId());
                intent.setClass(baseActivity, MoreSuitActivity.class);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.linear_horizontal:
                intent.putExtra("id", productInfor.getId());
                intent.setClass(baseActivity, MoreSuitActivity.class);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.linear_back_produce:
                // 返回键处理
                backPrewiew(intent);
                break;
            case R.id.base_activity_title_backicon:
                backPrewiew(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 一键生成预付单
     * */
    private void CreateOrderNow(String minnum) {
        selectProductNotInEdit.clear();
        ShoppingCartSelectBean dataBean = new ShoppingCartSelectBean();
        dataBean.setNum(minnum);
        dataBean.setAid(productInfor.getId());
        selectProductNotInEdit.add(dataBean);
        // 一键购买 直接生成预付订单
        Preprview();
    }

    private void ToCollect() {
        if( "1".equals(productInfor.getIsFavorite()))
            flag = "-1";
        else
            flag = "1";
        SetCollect();
    }

    private void NeedLogin() {
        boolean isLogin = IsLogin.isLogin(this);
        if (isLogin) {
            StartShoppingPage();
        } else {
            Utils.StartLogin(baseActivity, false);
        }
    }


    private void backPrewiew(Intent intent) {
        if (isPush) {
            intent.setClass(baseContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isPush", true);
            StartActivityUtil.startActivity(baseActivity, intent);
            finish();
//            if (!isFirstBack) {
//                SetButtomAnimation(pagerContent, h5_webview_main);
//                isFirstBack = true;
//            } else {
//                onBackPressed();
//            }
        } else {
//            if (!isFirstBack) {
//                SetButtomAnimation(pagerContent, h5_webview_main);
//                isFirstBack = true;
//            } else {
//                onBackPressed();
//            }
        }
        onBackPressed();
    }

    /*
     *
     * ViewEnter 显示视图view
     *
     * viewExit 退出视图view
     *
     * 最小边的时候拉动向上
     * */
    void SetTopAnimation() {
//        AnimationUtil.getInstance(this).buttomEnterView(ViewEnter, ViewExit);
//        ViewEnter.setVisibility(View.VISIBLE);
//        ViewExit.setVisibility(View.GONE);
        tv_center.setText("详情");
        tv_center.setVisibility(View.VISIBLE);
        linear_sp.setVisibility(View.INVISIBLE);
        linear_xq.setVisibility(View.INVISIBLE);
        line_one.setVisibility(View.INVISIBLE);
//        line_one.clearAnimation();
//        line_one.invalidate();
    }

    /*
     *
     *  最上边的时候拉动向下
     */
    void SetButtomAnimation() {
//        AnimationUtil.getInstance(this).TopEnterView(ViewEnter, ViewExit);
        tv_center.setVisibility(View.GONE);
        linear_sp.setVisibility(View.VISIBLE);
        linear_xq.setVisibility(View.VISIBLE);
        line_one.setVisibility(View.VISIBLE);
//        ViewEnter.setVisibility(View.VISIBLE);
//        ViewExit.setVisibility(View.GONE);
    }


    void PopuwShowing() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    // 弹出电话和客服
    private void showPopUp(View v) {
        View layout = getLayoutInflater().inflate(R.layout.popu_tel_kf, null);
        popupWindow = new PopupWindow(layout, ViewUtil.dip2px(this, 78), ViewUtil.dip2px(this, 78));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        tv_ontel = (TextView) layout.findViewById(R.id.tv_ontel);
        tv_online = (TextView) layout.findViewById(R.id.tv_online);
        tv_ontel.setOnClickListener(this);
        tv_online.setOnClickListener(this);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0],
                location[1] - popupWindow.getHeight());
    }

	/*
	 * 跳转到购物车
	 */

    void StartShoppingPage() {
        if (productDetailInfor != null
                && productDetailInfor.getResult() != null
                && productDetailInfor.getResult().getType() != null) {
            if ("1".equals(productDetailInfor.getResult().getType()))
                MobclickAgent.onEvent(baseActivity, "C_SAL_INF_5");
            else
                MobclickAgent.onEvent(baseActivity, "C_PD_INF_1");
        }
//		int num = ActivityManagerUtil.getActivityManager().getActivityNum(
//				MainActivity.class);
//		Intent intent = new Intent();
//		intent.setClass(NewProductDetailActivity.this, MainActivity.class);
//		intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
//				ConstantsUtil.MAIN_TAB_SHOP_CAR);
//		intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
//				ConstantsUtil.SHOPPING_CART_SHOW_BACK);
//		if (num > 1) {
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		}
//		StartActivityUtil.startActivity(baseActivity, intent);
        StartActivityUtil.startActivity(baseActivity, ShoppingCartActivity.class);
    }

    // 生成预览订单
    private void Preprview() {
        ApiHttpCilent.getInstance(getApplicationContext()).CreatPaymentOrder(baseActivity,
                null, "1", null, -1, selectProductNotInEdit, "0", null,
                new MyShoppingCallBack());
        // ApiHttpCilent.getInstance(baseActivity).CreatPaymentOrder(baseActivity,null,"1",null,null,-1,selectProductNotInEdit,
        // "0",null,new MyShoppingCallBack());
    }

    // 预览订单返回数据
    class MyShoppingCallBack extends
            BaseJsonHttpResponseHandler<NewOrderBaseBean> {
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
                message.what = ConstantsUtil.ALI_BACK_CODE;
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
            productMessageHandler.sendMessage(message);
            return bean;
        }
    }

    // 打开APP客服
//	private void OpenShoper() {
//		// TODO Auto-generated method stub
//		String target = "maggie";// 消息接收者ID
//		Intent intentData = mWIMKit.getChattingActivityIntent(target);
//		startActivity(intentData);
//	}

    // 打开商铺客服
//	private void OpenAppService() {
//		// TODO Auto-generated method stub
//		// userid是客服帐号，第一个参数是客服帐号，第二个是组ID，如果没有，传0
//		EServiceContact contact = new EServiceContact(groupName.trim(), 0);
//		// EServiceContact contact = new EServiceContact("喝喝酒类平台", 0);
//		// 如果需要发给指定的客服帐号，不需要Server进行分流(默认Server会分流)，请调用EServiceContact对象
//		// 的setNeedByPass方法，参数为false。
//		// contact.setNeedByPass(false);
//		Intent intent = mWIMKit.getChattingActivityIntent(contact);
//		startActivity(intent);
//	}

    // 1 零售商
    // 2 代理商员工
    // 3 代理商
    // 4 VIP商户
    // 5 经销商
    // 6 品牌连锁店
    // 判断当前用户是否认证通过
    // private boolean NotVerifystatus() {
    // if(loginBean !=null )
    // {
    // if( "3".equals(loginBean.getResult().getVerifystatus())){
    // String roleid = loginBean.getResult().getRoleid();
    // if(!"1".equals(roleid) && !"6".equals(roleid)){
    // // 1 零售商 6 品牌连锁店 角色才可以购买 其他角色不能购买
    // ToastUtil.showToast(baseContext, "您的账号角色无法购买商品");
    // return true;
    // }
    // }else{
    // ToastUtil.showToast(baseContext, "该账户未认证,请提交认证资料后再购买");
    // return true;
    // }
    // }
    // return false;
    // }

    // 创建下来预览
    private void ShowDialog(TextView ly, String tvname, String tvvalue) {
        View view = LinearLayout.inflate(this, R.layout.popupview, null);
        PopupWindow pw = new PopupWindow(view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvName = (TextView) view.findViewById(R.id.tvname);
        TextView tvValue = (TextView) view.findViewById(R.id.tvvalue);
        tvName.setText(tvname);
        tvValue.setText(tvvalue);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAsDropDown(ly);
        pw.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub

            }
        });
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

    // 返回键处理 //处理消息推送后退事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (isPush) {
//				Intent i = new Intent(baseContext, GroupWholeSaleActivity.class);
//				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				StartActivityUtil.startActivity(baseActivity, i);
//			} else {
//				onBackPressed();
//			}
            backPrewiew(new Intent());
        }
        return false;
    }


    private void addShopCarAnimation(View startView, View endView, Bitmap bitmap) {
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
        ImageView animationImg = new ImageView(this);
        if (bitmap != null) {
            animationImg.setImageBitmap(bitmap);
        } else {
            Resources res = baseContext.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res,
                    R.drawable.imageview_default);
            animationImg.setImageBitmap(bmp);
        }

        ProductDetailAnimation.getInstance().startAnimation(animationImg,
                endView, startLocation, endLocation, this);

    }


    /**
     * Describe:添加购物车
     * <p>
     * Date:2015-10-13
     * <p>
     * Author:liuzhouliang sku 商品sku id 活动ID
     */
    private void addShoppingCart(String id, String num, String type) {
        ApiHttpCilent.getInstance(getApplicationContext()).addShoppingCart(this, id, num, 1,
                new AddShoppingCartRequestCallBack());
    }

    public class AddShoppingCartRequestCallBack extends
            BaseJsonHttpResponseHandler<AddShoppingCartBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, AddShoppingCartBean arg4) {
            Dimess();
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
            Dimess();
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
            WeakHandler<NewProductDetailActivity> {

        public AddShoppingCartMessageHandler(NewProductDetailActivity reference) {
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
                    getReference().addShopCarAnimation(getReference().ivAnimation,
                            getReference().bt_buy,
                            getReference().currentBitmap);
                    ToastUtil.showToast(getReference(), "加入购物车成功");
                    getReference().getShoppingCartDate();
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LATER:
                    // 获取到购物车数据成功
                    getReference().ShoppingCartSuccess();
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
                    // 获取到地图数据成功
                    GoodsMapList.MapResultBean listMap = (GoodsMapList.MapResultBean) msg.obj;
                    //查看多少人买过地图
                    if (listMap != null && listMap.getList() != null && listMap.getList().size()>0) {
                        List<GoodsMapList.MapList> listmap =  listMap.getList();
                        Intent intent = new Intent(getReference(),BaiduMapActivity.class);
                        intent.putExtra("listmap", (Serializable) listmap);
                        StartActivityUtil.startActivity(getReference(), intent);
                        getReference().overridePendingTransition(R.anim.dialog_buttom_enter, 0);
                    }
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    /**
                     * 处理失败的数据
                     */
                    String messageString = (String) msg.obj;
                    if (!StringUtil.isEmpty(messageString)) {
                        if(!"登录已失效".equals(messageString)){
                            ToastUtil.showToast(getReference(), messageString);
                        }
                        //登录失效不处理
                    }else{
                        ToastUtil
                                .showToast(getReference(),"数据异常，请稍后重试");
                    }
                    break;
            }
        }
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
            } else {
                shoppingcare_num.setVisibility(View.INVISIBLE);
            }
        } else {
            selectProductNotInEdit.clear();
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private List<String> images;
        private LayoutInflater inflater;
        private int issuit;
        private Context mContext;

        ImagePagerAdapter(List<String> images, int issuit, Context mContext) {
            this.images = images;
            this.issuit = issuit;
            this.mContext = mContext;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            // LogUtil.d(TAG, "instantiateItem==" + position + "====");
            View rootView = inflater.inflate(
                    R.layout.product_detail_viewpager_item, view, false);
            assert rootView != null;
            final ImageView ivImageView = (ImageView) rootView
                    .findViewById(R.id.product_detail_viewpager_item_iv);
            final ProgressBar progressBar = (ProgressBar) rootView
                    .findViewById(R.id.product_detail_viewpager_item_pb);
            progressBar.setVisibility(View.VISIBLE);
            LayoutParams para;
            para = ivImageView.getLayoutParams();
            para.width = LinearLayout.LayoutParams.MATCH_PARENT;
            if (issuit == 1) {
                para.height = LinearLayout.LayoutParams.MATCH_PARENT;
                ivImageView.setLayoutParams(para);
            }
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
                            ivImageView
                                    .setImageResource(R.drawable.imageview_default);
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
            rootView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 点击查看大图
                    Intent intent = new Intent(NewProductDetailActivity.this,
                            BrowsPictureActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("Urls",
                            (ArrayList<String>) images);
                    bundle.putInt("index", position);
                    bundle.putString("baseurl", "");
                    intent.putExtra("msg", bundle);
                    startActivity(intent);
                }
            });
            return rootView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
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


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 统计时长
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        // 获取购物车数据
        getShoppingCartDate();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        indexButtomStatus = 0;
        indexTopStatus = 0;
    }

//    @Override
//    public void scrollOritention(boolean isbuttom) {
//        if (isbuttom)
//            indexButtomStatus++;
//        else
//            indexButtomStatus = 0;
//        if (indexButtomStatus != 2 && isbuttom && tv_center.getVisibility() == View.GONE) {
//            SetTopAnimation(h5_webview_main, pager);
//            indexButtomStatus = 0;
//            isFirstBack = false;
//        }
//    }

}
