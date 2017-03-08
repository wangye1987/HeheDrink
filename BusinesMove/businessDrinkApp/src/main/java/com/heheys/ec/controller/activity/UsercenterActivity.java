package com.heheys.ec.controller.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.BitmapUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.CircleImageView;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BaseCardBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.LoginNamePwd;
import com.heheys.ec.model.dataBean.MyBalanceBaseBean;
import com.heheys.ec.model.dataBean.PushBean;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.BasicHttpClient;
import com.heheys.ec.service.HeartService;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.CommonDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.heheys.ec.R.id.layout_login_collaction;

/**
 * Created by wangkui on 2016/12/12.
 */

public class UsercenterActivity extends BaseActivity implements
        CommonDialog.BackGroundListener, View.OnClickListener {

    private static UsercenterActivity UsercenterActivity;
    private TextView tv_rz;
    private ImageView iv_right;
    private String tel = "";
    private View view;
    private TextView username_tv;
    private TextView tv_tel_base;
    private LinearLayout layout_salon;
    private LinearLayout linear_about;
    private LinearLayout layout_add;
    private LinearLayout layout_answer;
    private LinearLayout layout_tel;
    private LinearLayout layout_setting;
    private CircleImageView user_image;
    private PopupWindow mPopupWindow;
    private TextView tvPhoto;
    private TextView tvCamera;
    private TextView tvCancel;
    private static final int CAMERA = 1;
    private static final int PHOTO = 2;
    private Bitmap saveBitmapCamera;
    private Bitmap saveBitmapPhoto;
    private String mCurrentPhotoPath;
    private int type = 0;// 退出操作是0 获取基本信息是1
    private MyHandler handler = new MyHandler(this);
    // 图片存储路径
    private static final String PATH = Environment
            .getExternalStorageDirectory() + "/DCIM";
    private String mobile_user;
    private boolean islogin;
    private ResultBean object;
    //    private TextView my_statue;
    private LinearLayout my_order;
    //    private LinearLayout linear_address;
    private LinearLayout linear_right;
    //基本信息
    private BaseBean loginBean;
    // 图片名
    public String picName;
    private LinearLayout llParentLayout;
    private Animation animationShow, animationHide;
    private LinearLayout llDrinksDemand;
    private TextView tv_dfk, tv_dfh, tv_dsh, tv_all, tv_hhz;
    private BaseCardBean.ResultCardBean resultcardbean;
    private String h5url;
    private String vipClubUrl;
    private String uid;
    private String title;
    private IWXAPI api;
    private Button btMessage;
    private Button btMessageNum;
    private RelativeLayout rlMessageParent;
    private Button fragment_home_title_message_icon;
    private Button fragment_home_title_mes_num;
    private RelativeLayout fragment_home_title_message_parent;
    //    private TextView tv_empty;
//    private LinearLayout linear_jf;
    private RelativeLayout fragme_top;
    private RelativeLayout top_layout;
    private TextView tv_register;
    private TextView tv_login;
    private TextView textView2;
    private TextView textView4;
    private TextView textView3;
    private TextView tv_service;
    private TextView textView8;
    private TextView my_count;
    private LinearLayout linear_count;
    private TextView my_line_one;
    private TextView my_count_hb;
    private LinearLayout linear_hhmoney;
    private TextView my_line_two;
    private TextView my_count_coupon;
    private LinearLayout linear_coupon;
    private TextView my_line_three;
    private TextView my_count_scroe;
    private LinearLayout linear_scroe;
    private TextView TextView01;
    private ImageView imageView1;
    private TextView my_info;
    private ImageView imageView2;
    private TextView my_salon;
    private TextView add_manager;
    private TextView answer_back;
    private LinearLayout layout_demand;
    private ImageView imageView3;
    private TextView hehe_tel;
    private TextView hehe_setting;
    private View buttom_view;
    private LinearLayout buttom_ralative;
    private ScrollView scrollView1;
    private ImageView tv_back;
    private LinearLayout linear_back;
    private LinearLayout fragment_user_center_parent;
    private LinearLayout layout_login_member;
    private LinearLayout layout_login_account;
    //账户信息
    MyBalanceBaseBean Balancebasebean;
    ResultBean bean;
    private TextView num_three;
    private TextView num_two;
    private TextView num_one;
    private TextView num_four;
    private LinearLayout my_collect;
    private PushBean pushBean;
    private LinearLayout linear_notLogin;
    private RelativeLayout relative_count;

    private void initViews() {
        tel = MyApplication.getInstance().getServiceline();
        api = WXAPIFactory.createWXAPI(baseActivity, "wx17e4df51dd029038");
        animationShow = AnimationUtils.loadAnimation(baseActivity,
                R.anim.show_scale);
        animationShow.setDuration(800);
        animationShow.setFillAfter(true);
        animationHide = AnimationUtils.loadAnimation(baseActivity,
                R.anim.hide_scale);
        animationHide.setDuration(800);
        animationHide.setFillAfter(true);
        initView();
        llParentLayout = (LinearLayout) findViewById(R.id.fragment_user_center_parent);

        username_tv = (TextView) findViewById(R.id.username_tv);
        tv_tel_base = (TextView) findViewById(R.id.tv_tel_base);
        num_one = (TextView) findViewById(R.id.num_one);
        num_two = (TextView) findViewById(R.id.num_two);
        num_three = (TextView) findViewById(R.id.num_three);
        num_four = (TextView) findViewById(R.id.num_four);
        my_collect = (LinearLayout) findViewById(layout_login_collaction);//我的收藏
        tv_tel_base.setText(tel);
        linear_right = (LinearLayout) findViewById(R.id.linear_right);

        //消息视图
        btMessage = (Button) findViewById(R.id.fragment_home_title_message_icon);
        btMessageNum = (Button) findViewById(R.id.fragment_home_title_mes_num);
        rlMessageParent = (RelativeLayout) findViewById(R.id.fragment_home_title_message_parent);

        tv_dfk = (TextView) findViewById(R.id.tv_dfk);
        tv_dfh = (TextView) findViewById(R.id.tv_dfh);
        tv_dsh = (TextView) findViewById(R.id.tv_dsh);
        tv_hhz = (TextView) findViewById(R.id.tv_hhz);
        tv_all = (TextView) findViewById(R.id.tv_all);

        tv_back = (ImageView) findViewById(R.id.tv_back);
        linear_back = (LinearLayout) findViewById(R.id.linear_back_center);
        user_image = (CircleImageView) findViewById(R.id.user_image);
        layout_salon = (LinearLayout) findViewById(R.id.layout_salon);
        layout_add = (LinearLayout) findViewById(R.id.layout_add);
        linear_about = (LinearLayout) findViewById(R.id.linear_about);
        layout_answer = (LinearLayout) findViewById(R.id.layout_answer);
        layout_tel = (LinearLayout) findViewById(R.id.layout_tel);
        layout_setting = (LinearLayout) findViewById(R.id.layout_setting);

        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_rz = (TextView) findViewById(R.id.tv_rz);
        layout_login_member = (LinearLayout) findViewById(R.id.layout_login_member);
        layout_login_account = (LinearLayout) findViewById(R.id.layout_login_account);
        SetClickListener();
        ArrayList<LoginNamePwd> listlogin = (ArrayList<LoginNamePwd>) SharedPreferencesUtil.getObject(baseContext, "loginUserPwd");
        if(listlogin!=null && listlogin.size()>0){
            String userName = listlogin.get(listlogin.size()-1).getUserName().toString();
            username_tv.setText(StringUtil.isEmpty(userName)?"":userName);
            relative_count.setVisibility(View.VISIBLE);
            linear_notLogin.setVisibility(View.GONE);
        }
    }


    private void SetClickListener() {
        CommonDialog.setBackListener(this);
        user_image.setOnClickListener(this);
        tv_dfk.setOnClickListener(this);
        tv_dfh.setOnClickListener(this);
        tv_dsh.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_hhz.setOnClickListener(this);
        my_collect.setOnClickListener(this);
        tv_service.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        linear_back.setOnClickListener(this);
        linear_about.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);

        layout_tel.setOnClickListener(this);
        linear_count.setOnClickListener(this);
        linear_hhmoney.setOnClickListener(this);
        linear_coupon.setOnClickListener(this);
        linear_scroe.setOnClickListener(this);
        layout_login_member.setOnClickListener(this);
        layout_login_account.setOnClickListener(this);
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                // Permission Denied
                ToastUtil.showToast(baseActivity, "请去设置里面开启拨打电话权限");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //聊天IM退出登录
//	public void logout() {
//        // openIM SDK提供的登录服务
//		YWIMKit mIMKit =  MyApplication.getInstance().getmIMKit();
//		if(mIMKit != null){
//        IYWLoginService mLoginService = mIMKit.getLoginService();
//        mLoginService.logout(new IWxCallback() {
//            //此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
//            @Override
//            public void onSuccess(Object... arg0) {
//                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
//                MobclickAgent.onEvent(baseActivity, "C_MY_LOF_1"); //退出登录
//                baseActivity.finish();
//    			StartActivityUtil.startActivity(activity, LoginActivity.class);
//            }
//
//            @Override
//            public void onProgress(int arg0) {
//
//            }
//
//            @Override
//            public void onError(int arg0, String arg1) {
////               ToastUtil.showToast(activity, "退出失败,请重新登录");
//            }
//        });}
//    }
    private void callPhone() {
        // TODO Auto-generated method stub
        Uri data = Uri.parse("tel:" + tel);
        Intent intents = new Intent(Intent.ACTION_CALL, data);
        baseActivity.startActivity(intents);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        HashMap<String, String> map = new HashMap<String, String>();
        final Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.layout_login_member:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (!IsLogin.isLogin(baseActivity)) {
                    //登录
                    NeedLoginStart(intent);
                } else {
                    GoToH5HY(intent, vipClubUrl, "会员俱乐部");
                }
                break;
            case R.id.tv_register:
                intent.setClass(baseActivity, RegisterActivity.class);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.tv_login:
                //登录
                NeedLoginStart(intent);
                break;
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.linear_back_center:
                onBackPressed();
                break;
            case R.id.layout_add:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                intent.setClass(baseActivity, SettingBaseActivity.class);
                intent.putExtra("type", ConstantsUtil.ADD_MANAGER);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.layout_answer:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                intent.setClass(baseActivity, SettingBaseActivity.class);
                intent.putExtra("type", ConstantsUtil.RETROACTION);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.layout_tel:
                MobclickAgent.onEvent(baseActivity, "C_MY_CS_1");//客服
                if (!StringUtil.isEmpty(tel))
                    ShowDialog("是否拨打电话:" + tel, "温馨提示", 1, 0);
                break;
            case R.id.layout_setting:
                intent.setClass(baseActivity, SettingBaseActivity.class);
                intent.putExtra("type", ConstantsUtil.SETTING);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.user_image:
//			showAtWindowTop(activity, user_image, R.layout.forum_release_pic);
//			startActivity(IMHelper.getInstance().getYwIMKit().getConversationActivityIntent());
                //跳转到百度地图
//                Intent intents =  new Intent(baseActivity, PaySuccessActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("isOrder", true);//订单 区分订单支付结果和喝喝币支付结果
//                bundle.putString("orderId", "1");//订单号
//                intents.putExtra("bundle", bundle);
//                StartActivityUtil.startActivity(baseActivity, intents);
//                String url = "http://map.qq.com/m/drive/routeplan/type=drive&sword=%E6%88%91%E7%9A%84%E4%BD%8D%E7%BD%AE&spointx=116.53247&spointy=39.904466&eword=%E6%9C%9D%E9%98%B3%E5%8C%BA%E9%AB%98%E7%A2%91%E5%BA%97&epointx=116.583967&epointy=39.907573&cond=0&noback=&navibutton=&canclebutton=&referer=undefined";
//                GoToH5(intent,url,"导航");
                break;

            case R.id.forum_release_pic_photo:
                /**
                 * 调取相册
                 */
                mPopupWindow.dismiss();
                startPhoto();
                break;
            case R.id.forum_release_pic_camera:
                /**
                 * 调取照相机
                 */
                mPopupWindow.dismiss();
                startCamera();

                break;
            case R.id.forum_release_pic_cancel:
                /**
                 * 取消
                 */
                mPopupWindow.dismiss();
                break;

            case R.id.tv_service:
                //前往服务端
                GoToH5(intent, h5url, "服务端");
                break;
            case R.id.tv_all:
                MobclickAgent.onEvent(baseActivity, "C_MY_ORD_1");//我的订单
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                //获取全部订单
                isLoginOrder("");
                break;
//			 订单列表：0 全部订单 1 待付款 2  待发货  3待收货 4 已收货 5合伙中
            case R.id.tv_dfk:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                isLoginOrder("1");
                break;
            case R.id.tv_dfh:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                isLoginOrder("3");
                break;
            case R.id.tv_dsh:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                isLoginOrder("4");
                break;
            case R.id.tv_hhz:
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                isLoginOrder("2");
                break;
            case R.id.linear_address:
                MobclickAgent.onEvent(baseActivity, "C_MY_ADR_1");//我的收货地址
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                //获取收货地址
                intent.setClass(baseActivity, SettingBaseActivity.class);
                intent.putExtra("type", ConstantsUtil.ADD_MANAGER);
                intent.putExtra("isCenter", true);
                StartActivityUtil.startActivity(baseActivity, intent);
                break;
            case R.id.linear_about:
                MobclickAgent.onEvent(baseActivity, "C_MY_ABT_1");//关于
                StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity, AboutUs.class));
                break;
            case R.id.fragment_home_title_message_parent:
                /**
                 * 进入消息页面
                 */
                LoginMsg(intent);
                break;
            case R.id.fragment_home_title_message_icon:
                /**
                 * 进入消息页面
                 */
                LoginMsg(intent);
                break;
            default:
                break;
            case R.id.linear_count:
                //我的余额
                GoMyCount(intent);
                break;
            case R.id.linear_hhmoney:
                //喝币
                if (ToastNoNetWork.ToastError(baseActivity)) {
                    return;
                }
                if (!IsLogin.isLogin(baseActivity)) {
                    NeedLoginStart(intent);
                } else {
                    intent.setClass(this, HeHeMoneyActivity.class);
                    StartActivityUtil.startActivity(this, intent);
                }
                break;
            case R.id.linear_coupon:
                //我的优惠券
                MobclickAgent.onEvent(baseActivity, "C_WAL_CPN_1");
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (!IsLogin.isLogin(baseActivity)) {
                    //登录
                    NeedLoginStart(intent);
                } else {
                    intent.setClass(this, CouponActivity.class);
                    intent.putExtra("key", "scan");//查看
                    StartActivityUtil.startActivity(this, intent);
                }
                break;
            case R.id.linear_scroe:
                //我的积分
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (!IsLogin.isLogin(baseActivity)) {
                    NeedLoginStart(intent);
                } else {
                    intent.setClass(this, MyPointsActivity.class);
                    StartActivityUtil.startActivityForResult(this, intent, ConstantsUtil.REQUEST_CODE_FIVE);
                }

                break;
            case R.id.layout_login_account:
                //账户设置
                if (!IsLogin.isLogin(baseActivity)) {
                    NeedLoginStart(intent);
                } else {
                    if (resultcardbean != null) {
                        intent.setClass(baseActivity, CountSettingActivity.class);
                        intent.putExtra("name", StringUtil.isEmpty(bean.getMobile()) ? "" : bean.getMobile());
                        intent.putExtra("resultcardbean", resultcardbean);
                        StartActivityUtil.startActivity(baseActivity, intent);
                    }
                }
                break;
            case R.id.layout_login_collaction:
                //我的收藏
                if (ToastNoNetWork.ToastError(baseActivity))
                    return;
                if (!IsLogin.isLogin(baseActivity)) {
                    //登录
                    NeedLoginStart(intent);
                } else {
                    intent.setClass(baseActivity, MyCollectActivity.class);
                    StartActivityUtil.startActivity(baseActivity, intent);
                }
                break;
        }
    }

    private void GoToH5(Intent intent, String url, String title) {
        if (!StringUtil.isEmpty(url)) {
            intent.setClass(baseActivity, JDPayActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("title", title);
            intent.putExtra("flag", 30);//传30  服务端 打开心跳
            StartActivityUtil.startActivityForResult(baseActivity, intent, ConstantsUtil.REQUEST_CODE);
        }
    }
    private void GoToH5HY(Intent intent, String url, String title) {
        if (!StringUtil.isEmpty(url)) {
            intent.setClass(baseActivity, JDPayActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("title", title);
            intent.putExtra("flag", 5);//会员俱乐部
            StartActivityUtil.startActivity(baseActivity, intent);
        }
    }

    private void NeedLoginStart(final Intent intent) {
        MyApplication.getInstance().startLogin(new MyApplication.LoginCallBack() {
            @Override
            public void loginSuccess() {
                intent.setClass(baseActivity, UsercenterActivity.class);
                StartActivityUtil.startActivity(baseActivity, intent);
            }

            @Override
            public void loginFail() {

            }
        }, baseActivity);
    }

    private void GoMyCount(Intent intent) {
        MobclickAgent.onEvent(baseActivity, "C_MY_WAL_1");//我的钱包
        if (ToastNoNetWork.ToastError(baseActivity))
            return;
        if (!IsLogin.isLogin(baseActivity)) {
            //登录
            NeedLoginStart(intent);
        } else {
            StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
        }
    }

    private void LoginMsg(Intent intent) {
        MobclickAgent.onEvent(baseActivity, "C_HMN_MSG_1");//消息
        if (ToastNoNetWork.ToastError(baseActivity))
            return;
        intent = new Intent(baseActivity,
                MessageActivity.class);
        if (!IsLogin.isLogin(baseActivity)) {
            //登录
            NeedLoginStart(intent);
        } else {
            intent.putExtra("pushBean", pushBean);
            StartActivityUtil.startActivity(baseActivity, intent);
        }
    }

    //跳转到管理商后台
//    private void JumpH5() {
//        Intent intents = new Intent(baseActivity, DrinkInfoActivity.class);
//        if (!StringUtil.isEmpty(h5url)) {
//            intents.putExtra("h5url", h5url + "?uid=" + uid);
//        } else {
//            intents.putExtra("h5url", RequestConfiguration.BASE_URL_TEST + "?uid=" + uid);
//        }
//        intents.putExtra("title", title);
//        StartActivityUtil.startActivity(baseActivity, intents);
//    }

    private void isLoginOrder(final String status) {
        final Intent intent = new Intent(baseActivity, MyAllOrderActivity.class);
        intent.putExtra("status", status);
        if (!IsLogin.isLogin(baseActivity)) {
            //登录
            NeedLoginStart(intent);
        } else {
            StartActivityUtil.startActivity(baseActivity, intent);
        }
    }

    public void ShowDialog(String notice, String title, final int flag,
                           final int type) {
        CommonDialog.makeText(baseActivity, title, notice, new CommonDialog.OnDialogListener() {
            @Override
            public void onResult(int result, CommonDialog commonDialog,
                                 String tel) {
                // TODO Auto-generated method stub
                if (CommonDialog.OnDialogListener.LEFT == result && flag == 1) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(baseActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(baseActivity, new String[]{Manifest.permission.CALL_PHONE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        } else {
                            callPhone();
                        }
                    } else {
                        callPhone();
                    }
                    //6.0权限处理
                    CommonDialog.Dissmess();
                } else if (CommonDialog.OnDialogListener.LEFT == result && flag == 2) {
                    if (!ToastNoNetWork.ToastError(baseActivity)) {
                        ApiHttpCilent.getInstance(getApplicationContext()).loginoOut(baseActivity,
                                new MyCallBack());
                    }
                    CommonDialog.Dissmess();
                } else {
                    CommonDialog.Dissmess();
                }
            }
        }).showDialog();
    }


    private void Dimess() {
        baseActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }

    private void initView() {
        fragment_home_title_message_icon = (Button) findViewById(R.id.fragment_home_title_message_icon);
        fragment_home_title_mes_num = (Button) findViewById(R.id.fragment_home_title_mes_num);
        fragment_home_title_message_parent = (RelativeLayout) findViewById(R.id.fragment_home_title_message_parent);
        linear_notLogin = (LinearLayout) findViewById(R.id.linear_notLogin);
        relative_count = (RelativeLayout) findViewById(R.id.relative_count);
        fragme_top = (RelativeLayout) findViewById(R.id.fragme_top);
        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView3 = (TextView) findViewById(R.id.textView3);
        tv_service = (TextView) findViewById(R.id.tv_service);
//        textView = (TextView) findViewById(R.id.textView);
        textView8 = (TextView) findViewById(R.id.textView8);
        my_count = (TextView) findViewById(R.id.my_count);
        linear_count = (LinearLayout) findViewById(R.id.linear_count);
        my_line_one = (TextView) findViewById(R.id.my_line_one);
        my_count_hb = (TextView) findViewById(R.id.my_count_hb);
        linear_hhmoney = (LinearLayout) findViewById(R.id.linear_hhmoney);
        my_line_two = (TextView) findViewById(R.id.my_line_two);
        my_count_coupon = (TextView) findViewById(R.id.my_count_coupon);
        linear_coupon = (LinearLayout) findViewById(R.id.linear_coupon);
        my_line_three = (TextView) findViewById(R.id.my_line_three);
        my_count_scroe = (TextView) findViewById(R.id.my_count_scroe);
        linear_scroe = (LinearLayout) findViewById(R.id.linear_scroe);
//        ImageView01 = (ImageView) findViewById(R.id.ImageView01);
        TextView01 = (TextView) findViewById(R.id.TextView01);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
//        my_info = (TextView) findViewById(R.id.my_info);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        my_salon = (TextView) findViewById(R.id.my_salon);
        add_manager = (TextView) findViewById(R.id.add_manager);
        answer_back = (TextView) findViewById(R.id.answer_back);
//        layout_demand = (LinearLayout) findViewById(R.id.layout_demand);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        hehe_tel = (TextView) findViewById(R.id.hehe_tel);
        hehe_setting = (TextView) findViewById(R.id.hehe_setting);
        buttom_view = findViewById(R.id.buttom_view);
        buttom_ralative = (LinearLayout) findViewById(R.id.buttom_ralative);
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        fragment_user_center_parent = (LinearLayout) findViewById(R.id.fragment_user_center_parent);

        fragment_home_title_message_icon.setOnClickListener(this);
        fragment_home_title_mes_num.setOnClickListener(this);
        fragment_home_title_message_parent.setOnClickListener(this);

    }

    //退出登录和基本信息
    class MyCallBack extends BaseJsonHttpResponseHandler<BaseBean> {

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
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = loginBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = loginBean.getError();
            }
            handler.sendMessage(message);

            return loginBean;
        }
    }

    private void changeIcon() {
        PackageManager pm = baseActivity.getPackageManager();
//        System.out.println(getComponentName());
        ComponentName compontName = baseActivity.getComponentName();
        //去除旧图标，不去除的话会出现2个App图标
        pm.setComponentEnabledSetting(compontName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        //显示新图标
        pm.setComponentEnabledSetting(new ComponentName(
                        baseActivity,
                        "com.heheys.ec.ActivityAlias2"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static class MyHandler extends WeakHandler<UsercenterActivity> {

        private MyBalanceBaseBean.ResultBanlanceBean resultBanlanceBean;

        public MyHandler(UsercenterActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                        //获取基本信息成功 1 未申请 2 正在审核 3 审核通过 4 审核失败
                        getReference().bean = (ResultBean) msg.obj;
                        bindData(getReference().bean);
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LATER:
                    if (getReference().pushBean.getResult() != null && getReference().pushBean.getResult().getList() != null && getReference().pushBean.getResult().getList().size() > 0) {
                        if (Integer.parseInt(getReference().pushBean.getResult().getUnread()) > 0) {
                            getReference().btMessageNum.setText(getReference().pushBean.getResult().getUnread());
                            getReference().btMessageNum.setVisibility(View.VISIBLE);
                        } else {
                            getReference().btMessageNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case ConstantsUtil.HTTP_SUCCESS_LOGIN:
                    getReference().resultcardbean = (BaseCardBean.ResultCardBean) msg.obj;
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    ErrorBean back = (ErrorBean) msg.obj;
                    // code=100代表登录状态失效 需要重新登录
                    if (back != null) {
                        if ("100".equals(back.getCode())) {
                            getReference().linear_notLogin.setVisibility(View.VISIBLE);
                            SharedPreferencesUtil.writeSharedPreferencesBoolean(getReference(), "login", "islogin", false);
                            bindData(null);
                        }
                    } else {
                        ToastUtil.showToast(getReference(), "数据异常，请稍后重试");
                    }
                    break;
                default:
                    break;
            }
        }

        /**
         * 绑定数据
         */
        private void bindData(ResultBean bean) {
            if (bean != null) {
                setCount(bean);
                getReference().username_tv.setText(StringUtil.isEmpty(bean.getMobile()) ? "" : bean.getMobile());
                getReference().uid = bean.getId();
                getReference().h5url = bean.getUrl();
                getReference().vipClubUrl = bean.getVipClubUrl();
                getReference().SetNum(bean);
                getReference().linear_notLogin.setVisibility(View.GONE);
                getReference().relative_count.setVisibility(View.VISIBLE);
                getReference().tv_rz.setText(StringUtil.isEmpty(bean.getVipname()) ? "喝喝会员" : bean.getVipname());
                if (!StringUtil.isEmpty(getReference().h5url)) {
                    getReference().tv_service.setVisibility(View.VISIBLE);
                } else {
                    getReference().tv_service.setVisibility(View.GONE);
                }
            }else{
                //退出登录 返回界面显示数据
                setCount(bean);
                getReference().relative_count.setVisibility(View.GONE);
                getReference().SetNum(bean);
                getReference().linear_notLogin.setVisibility(View.VISIBLE);
                getReference().tv_rz.setText( "喝喝会员" );
                getReference().tv_service.setVisibility(View.GONE);
    }

}

        /**
         * 设置账户余额积分等信息
         */
        void setCount(ResultBean bean) {
            if(bean != null && bean.getWallet() != null) {
                getReference().my_count.setText(StringUtil.isEmpty(bean.getWallet().getCashBlance()) ? "0.00" : bean.getWallet().getCashBlance());
                getReference().my_count_hb.setText(StringUtil.isEmpty(bean.getWallet().getCoinBlance()) ? "0.00" : bean.getWallet().getCoinBlance());
                getReference().my_count_coupon.setText(StringUtil.isEmpty(bean.getWallet().getCouponCount()) ? "0" : bean.getWallet().getCouponCount() + "张");
                getReference().my_count_scroe.setText(StringUtil.isEmpty(bean.getWallet().getScoreBalance()) ? "0.00" : bean.getWallet().getScoreBalance());
            }else{
                getReference().my_count.setText( "0.00" );
                getReference().my_count_hb.setText( "0.00" );
                getReference().my_count_coupon.setText( "0 张");
                getReference().my_count_scroe.setText("0.00" );
            }
        }

        // 登录失效界面ui状态 退出状态
        private void NotLoginStatus() {
//            getReference().login_out.setVisibility(View.INVISIBLE);
//            getReference().bt_login_out.setVisibility(View.INVISIBLE);
            getReference().username_tv.setVisibility(View.INVISIBLE);
            ApiHttpCilent.getInstance(getReference().getApplicationContext()).clearCookie(getReference().baseActivity);
        }
    }

    private void SetNum(ResultBean bean) {
        if (bean!= null && bean.getOrder() != null) {
            if (bean.getOrder().getDfk() > 0) {
                setNumVisible(bean.getOrder().getDfk(), num_one, View.VISIBLE);
            } else {
                num_one.setVisibility(View.GONE);
            }

            if (bean.getOrder().getHhz() > 0) {
                setNumVisible(bean.getOrder().getHhz(), num_two, View.VISIBLE);
            } else {
                num_two.setVisibility(View.GONE);
            }

            if (bean.getOrder().getDfh() > 0) {
                setNumVisible(bean.getOrder().getDfh(), num_three, View.VISIBLE);
            } else {
                num_three.setVisibility(View.GONE);
            }

            if (bean.getOrder().getDsh() > 0) {
                setNumVisible(bean.getOrder().getDsh(), num_four, View.VISIBLE);
            } else {
                num_four.setVisibility(View.GONE);
            }
        }else{
            num_one.setVisibility(View.GONE);
            num_two.setVisibility(View.GONE);
            num_three.setVisibility(View.GONE);
            num_four.setVisibility(View.GONE);
        }
    }


    void setNumVisible(int num, TextView tv, int visible) {
        tv.setVisibility(visible);
        if (num > 99)
            num = 99;
        tv.setText(num + "");
    }

    /**
     * @describe:设置选择图片监听
     * @author:LiuZhouLiang
     * @time:2014-11-26上午11:42:47
     */
    private void setCheckPhotoListener() {
        // TODO Auto-generated method stub
        tvPhoto.setOnClickListener(this);
        tvCamera.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    /**
     * @describe:调用相册
     * @author:LiuZhouLiang
     * @time:2014-11-26上午11:43:27
     */
    private void startPhoto() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO);

    }

    /**
     * @describe:调取相机
     * @author:LiuZhouLiang
     * @time:2014-11-19上午11:35:11
     */
    public void startCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            new DateFormat();
            picName = DateFormat.format("yyyyMMdd_hhmmss",
                    Calendar.getInstance(Locale.CHINA))
                    + ".jpg";
            File picFile = new File(PATH, picName);
            Uri imageUri = Uri.fromFile(picFile);
            mCurrentPhotoPath = picFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @describe:显示选择图片对话框
     * @author:LiuZhouLiang
     * @time:2014-11-26上午11:42:21
     */
    private void showAtWindowTop(Activity activity, View position, int layoutId) {

        View mPopMenuView = null;
        mPopMenuView = LayoutInflater.from(activity).inflate(layoutId, null);
        RelativeLayout layout = (RelativeLayout) mPopMenuView
                .findViewById(R.id.show_select_camer_photo);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mPopupWindow.dismiss();
            }
        });
        tvPhoto = (TextView) mPopMenuView
                .findViewById(R.id.forum_release_pic_photo);
        tvCamera = (TextView) mPopMenuView
                .findViewById(R.id.forum_release_pic_camera);
        tvCancel = (TextView) mPopMenuView
                .findViewById(R.id.forum_release_pic_cancel);
        mPopupWindow = new PopupWindow(mPopMenuView,
                ViewUtil.getScreenWith((baseActivity)), RelativeLayout.LayoutParams.WRAP_CONTENT,
                true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mPopupWindow.getBackground().setAlpha(50);
        // mPopupWindow.setBackgroundDrawable(baseactivity.getResources()
        // .getDrawable(R.drawable.popupwindow_bg));
        // mPopMenuView.setBackground(baseactivity.getResources().getDrawable(
        // R.drawable.popupwindow_bg));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(position, Gravity.BOTTOM, 0, 0);
        // if (mPopupWindow.getBackground() != null) {
        // mPopMenuView.getBackground().setAlpha(150);
        // }
        setCheckPhotoListener();

    }

    /**
     * @describe:保存图片信息
     * @author:LiuZhouLiang
     * @time:2014-11-26上午11:49:05
     */

    private void saveBitmap(Bitmap bitmap) {
        // String stringBitmap = ViewUtil.bitmaptoString(bitmap, 40);
        // picObj = new UpLoadPic.Pic();
        // picObj.setPicPic(stringBitmap);
        // picList.clear();
        // picList.add(picObj);
        // picListObj.setItemArray(picList);

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = baseActivity.managedQuery(contentUri, proj, null, null,
                null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void completeInfor() {

        if (!NetWorkState.isNetWorkConnection(baseActivity)) {
            ToastUtil.showToast(baseActivity, "请检查网络设置");

            return;
        }

        // LogUtil.d(TAG, "图片数据object==" + picListObj.toString());
        // // String jsonString = gson.toJson(picListObj);
        // LogUtil.d(TAG, "图片数据--转换json后==" + picListObj.toString());
        RequestParams paramsIn = new RequestParams();
        Map<Object, Object> params = new HashMap<Object, Object>();
        // if(picListObj.getItemArray() != null){
        // params.put("jsonPictures", picListObj);
        // }
        //
        // paramsIn = MsApplication.getRequestParams(params, paramsIn,
        // MsConfiguration.CustomerUserParam);
        BasicHttpClient.getInstance(baseActivity).post(baseActivity, paramsIn,
                mCurrentPhotoPath, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    /**
     * 完善个人信息消息处理
     */
    Handler handlerCompleteInfor = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                // 上传拍照头像
                case 100:

                    user_image.setImageBitmap(ViewUtil
                            .toRoundBitmap(saveBitmapCamera));
                    completeInfor();

                    break;
                // 上传相册头像
                case 101:

                    user_image.setImageBitmap(ViewUtil
                            .toRoundBitmap(saveBitmapPhoto));
                    completeInfor();

                    break;

            }
        }

    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CAMERA:
                // 添加到图库,这样可以在手机的图库程序中看到程序拍摄的照片
                BitmapUtil.galleryAddPic(baseActivity, mCurrentPhotoPath);
                /**
                 * 相机回调处理
                 */
                /**
                 * 处理部分相机图片旋转问题
                 */

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        BitmapUtil.galleryAddPic(baseActivity, mCurrentPhotoPath);
                        /**
                         * 相机回调处理
                         */
                        String picPath = PATH + "/" + picName;

                        Bitmap bitmap1 = BitmapUtil.getimage(picPath);

                        int degree = BitmapUtil.readPictureDegree(picPath);
                        Bitmap camera = BitmapUtil.rotateBitmap(degree, bitmap1);
                        // 上传后台大小
                        saveBitmapCamera = ViewUtil.zoomBitmapProportion(camera,
                                200);
                        saveBitmap(saveBitmapCamera);

                        // 输入框中显示大小

                        handlerCompleteInfor.sendEmptyMessage(100);

                    }
                }).start();

                break;

            case PHOTO:

                ContentResolver resolver = baseActivity.getContentResolver();
                // 照片的原始资源地址
                final Uri imgUri = intent.getData();
                final String uri = getRealPathFromURI(imgUri);

                if (uri != null) {

                    // showRoundProcessDialog();
                    new Thread(new Runnable() {

                        public void run() {

                            Bitmap bitmap2 = BitmapUtil.getimage(uri);
                            int photoDegree = BitmapUtil.readPictureDegree(uri);
                            Bitmap photo = BitmapUtil.rotateBitmap(photoDegree,
                                    bitmap2);
                            saveBitmapPhoto = ViewUtil.zoomBitmapProportion(photo,
                                    200);
                            saveBitmap(saveBitmapPhoto);
                            handlerCompleteInfor.sendEmptyMessage(101);
                        }

                        ;
                    }).start();

                } else {

                    // showRoundProcessDialog();
                    new Thread(new Runnable() {
                        public void run() {

                            Bitmap bitmap2 = null;
                            InputStream is = null;
                            Bitmap photoTemp = null;

                            try {
                                is = baseActivity.getContentResolver().openInputStream(
                                        imgUri);
                                bitmap2 = BitmapUtil.InputToBitmap(is, 50);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 上传后台大小
                            // saveBitmapPhoto = zoomImage(bitmap2, 200, 270);
                            Bitmap saveBitmapPhoto = ViewUtil.zoomBitmapProportion(
                                    bitmap2, 200);
                            saveBitmap(saveBitmapPhoto);
                            // handlerCompleteInfor.sendEmptyMessage(101);
                        }

                        ;
                    }).start();
                    break;
                }
            case ConstantsUtil.REQUEST_CODE:
                //返回到个人中心 停止服务
                stopService(new Intent(this, HeartService.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void getNetData() {
        // TODO Auto-generated method stub

        if (ToastNoNetWork.ToastError(baseActivity))
            return;
        ApiHttpCilent.getInstance(getApplicationContext()).GetUserCard(baseActivity,
                new CardCallBack());
        ApiHttpCilent.getInstance(getApplicationContext()).GetInfo(baseActivity, new MyCallBack());
        type = 1;
        //获取消息
        ApiHttpCilent.getInstance(getApplicationContext()).MsgPush(baseActivity, "1", "10", new MyMsgCallBack());
    }

    public class MyMsgCallBack extends
            BaseJsonHttpResponseHandler<PushBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, PushBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            handler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              PushBean arg3) {
        }

        @Override
        protected PushBean parseResponse(String response,
                                         boolean arg1) throws Throwable {
            Dimess();
            Gson gson = new Gson();
            pushBean = gson.fromJson(response,
                    PushBean.class);
            Message message = Message.obtain();
            if ("1".equals(pushBean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = pushBean.getError();
            }
            handler.sendMessage(message);
            return pushBean;
        }
    }

    //获取账户信息
//    public class BanlanceCallBack extends
//            BaseJsonHttpResponseHandler<MyBalanceBaseBean> {
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//                              String arg3, MyBalanceBaseBean arg4) {
//            Dimess();
//            Message message = Message.obtain();
//            message.what = ConstantsUtil.HTTP_FAILE;// 错误
//            handler.sendMessage(message);
//        }
//
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, String arg2,
//                              MyBalanceBaseBean arg3) {
//        }
//
//        @Override
//        protected MyBalanceBaseBean parseResponse(String response, boolean arg1)
//                throws Throwable {
//            Dimess();
//            Gson gson = new Gson();
//             Balancebasebean = gson.fromJson(response, MyBalanceBaseBean.class);
//            if (Balancebasebean != null) {
//                Message msg = new Message();
//                if (("1").equals(Balancebasebean.getStatus())) {
//                    msg.what = ConstantsUtil.HTTP_SUCCESS_LATER;
//                    msg.obj = Balancebasebean.getResult();
//                } else if (("0").equals(Balancebasebean.getStatus())) {
//                    msg.what = ConstantsUtil.HTTP_FAILE;
//                    msg.obj = Balancebasebean.getError();
//                }
//                handler.sendMessage(msg);
//            }
//            return Balancebasebean;
//        }
//
//    }
    //账户
    public class CardCallBack extends BaseJsonHttpResponseHandler<BaseCardBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, BaseCardBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            handler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              BaseCardBean arg3) {
        }

        @Override
        protected BaseCardBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimess();
            Gson gson = new Gson();
            BaseCardBean basecardbean = gson.fromJson(response, BaseCardBean.class);
            if (basecardbean != null) {
                Message msg = new Message();
                if (("1").equals(basecardbean.getStatus())) {
                    msg.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
                    msg.obj = basecardbean.getResult();
                } else if (("0").equals(basecardbean.getStatus())) {
                    msg.what = ConstantsUtil.HTTP_FAILE;
                    msg.obj = basecardbean.getError();
                }
                handler.sendMessage(msg);
            }
            return basecardbean;
        }
    }
//    @Override
//    protected void setViewListener() {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    protected boolean isShowLeftIcon() {
//        // TODO Auto-generated method stub
//        return false;
//    }

//    @Override
//    protected View setContentView(LayoutInflater inflater, ViewGroup container,
//                                  Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        activity = (MainActivity) baseActivity;
//        view = inflater.inflate(R.layout.fragment_user_center, container, true);
//        initViews();
//        return view;
//    }

    @Override
    protected void onCreate() {
        setBaseContentView(R.layout.fragment_user_center);
        initViews();
    }


    /*
    * 启动模式设置为singleTask 的时候 只会有一个实例 不会调用Oncreate 会调用onNewIntent方法
    * **/
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getNetData();
    }

    @Override
    protected boolean hasTitle() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void loadChildView() {

    }

    @Override
    protected void reloadCallback() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setChildViewListener() {

    }

    @Override
    protected String setTitleName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String setRightText() {
        // TODO Auto-generated method stub
        return "我的";
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

//    @Override
//    protected boolean hasTitleIcon() {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    protected boolean hasDownIcon() {
//        // TODO Auto-generated method stub
//        return false;
//    }

    // shopStatusMap.put("0", "未认证");
    // shopStatusMap.put("1", "未申请");
    // shopStatusMap.put("2", "正在审核");
    // shopStatusMap.put("3", "审核通过");
    // shopStatusMap.put("4", "审核失败")username_tv
//    private void VerifyStatus(ResultBean status, TextView tv) {
////		status.setViplevel(1);
//        tv_rz.setText("认证店铺");
//        iv_right.setImageResource(R.drawable.my_register);
//        tv.setText("认证有惊喜");
//        switch (status.getViplevel()) {
//            case 0:
//                tv_rz.setText("未认证店铺");
//                iv_right.setImageResource(R.drawable.my_not_register);
//                break;
//            case 1:
//                tv_rz.setText("部分认证店铺");
//                tv_jf.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                tv_rz.setText("部分认证店铺");
//                tv_jf.setVisibility(View.VISIBLE);
//                tv_fk.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                tv_jf.setVisibility(View.VISIBLE);
//                tv_fk.setVisibility(View.VISIBLE);
//                tv.setText("已全部认证");
//                tv.setTextColor(getResources().getColor(R.color.color_45b722));
//                break;
//            default:
//                break;
//        }
////		switch (Integer.parseInt(status.getVerifystatus())) {
////		case 0:
////			tv.setText("未认证");
////			break;
////		case 1:
////			tv.setText("未申请");
////			break;
////		case 2:
////			tv.setText("正在审核");
////			break;
////		case 3:
////			tv.setText("已认证");
////			break;
////		case 4:
////			tv.setText("审核失败");
////			break;
////		default:
////			break;
////		}
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        System.out.println("onRestart");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("PG_MY");
        islogin = SharedPreferencesUtil.getSharedPreferencesBoolean(baseActivity,
                "login", "islogin", false);
        MobclickAgent.onPageStart("UsercenterActivity");
//        System.out.println("onResume");
        getNetData();
    }


    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        MobclickAgent.onPageEnd("UsercenterActivity");
//        System.out.println("onStop");
    }

    @Override
    public void showListenr() {
        llParentLayout.startAnimation(animationShow);
    }

    @Override
    public void hideListener() {
        llParentLayout.startAnimation(animationHide);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PG_MY");
        System.out.println("onPause");
    }
}