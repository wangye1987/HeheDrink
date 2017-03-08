package com.heheys.ec.controller.fragment;

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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.controller.activity.AboutUs;
import com.heheys.ec.controller.activity.AuthActivity;
import com.heheys.ec.controller.activity.BaiduMapActivity;
import com.heheys.ec.controller.activity.DrinkInfoActivity;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.controller.activity.MyAllOrderActivity;
import com.heheys.ec.controller.activity.SalonListActivity;
import com.heheys.ec.controller.activity.SettingBaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.BitmapUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.CircleImageView;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BaseCardBean;
import com.heheys.ec.model.dataBean.BaseCardBean.ResultCardBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.netWorkHelper.BasicHttpClient;
import com.heheys.ec.netWorkHelper.RequestConfiguration;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.BackGroundListener;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 用户中心页面
 */
public class UserCenterFragment extends BaseFragment implements
		BackGroundListener {

	private static UserCenterFragment userCenterFragment;
	private  MainActivity activity;
	private LinearLayout linear_dls;
	private TextView tv_dls;
//	private TextView tv_jf;
//	private TextView tv_fk;
	private TextView tv_rz;
	private ImageView iv_dls;
	private ImageView iv_right;
	private String tel = "";
	private View view;
//	private Button login_button;
//	private Button register_button;
	private  Button bt_login_out;
	private  LinearLayout login_out;
	private  TextView username_tv;
	private  TextView tv_tel_base;
	private LinearLayout layout_salon;
//	private  LinearLayout login_layout;
	private LinearLayout linear_about;
	private LinearLayout layout_add;
	private LinearLayout layout_businessCard;
	private LinearLayout layout_answer;
	private LinearLayout layout_login_mybalnace;
	private LinearLayout layout_tel;
	private LinearLayout layout_setting;
	private LinearLayout layout_login_info;
	private LinearLayout linear_setbalance;
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
	private  int type = 0;// 退出操作是0 获取基本信息是1
	private MyHandler handler = new MyHandler(this);
	// 图片存储路径
	private static final String PATH = Environment
			.getExternalStorageDirectory() + "/DCIM";
	private String mobile_user;
	private boolean islogin;
	private ResultBean object;
	private  TextView my_statue;
//	private LinearLayout my_order;
	private LinearLayout linear_address;
	private LinearLayout linear_right;
	//基本信息
	private BaseBean loginBean;
	// 图片名
	public String picName;
	private LinearLayout llParentLayout;
	private Animation animationShow, animationHide;
//	private LinearLayout llDrinksDemand;
	private TextView tv_dfk,tv_dfh,tv_dsh,tv_yfh;
	private ResultCardBean resultcardbean;
	private String h5url;
	private String uid;
	private String title;
//	private List<String> listrole;
//	private String roleid;
	private IWXAPI api;
	public UserCenterFragment(){}
	private void initViews() {
		tel = MyApplication.getInstance().getServiceline();
		api = WXAPIFactory.createWXAPI(getActivity(), "wx17e4df51dd029038");
//		llDrinksDemand = (LinearLayout) view.findViewById(R.id.layout_demand);
		animationShow = AnimationUtils.loadAnimation(baseActivity,
				R.anim.show_scale);
		animationShow.setDuration(800);
		animationShow.setFillAfter(true);
		animationHide = AnimationUtils.loadAnimation(baseActivity,
				R.anim.hide_scale);
		animationHide.setDuration(800);
		animationHide.setFillAfter(true);
		llParentLayout = (LinearLayout) view
				.findViewById(R.id.fragment_user_center_parent);
//		my_order = (LinearLayout) view
//				.findViewById(R.id.my_order);
//		login_button = (Button) view.findViewById(R.id.login_button);
//		register_button = (Button) view.findViewById(R.id.register_button);
		bt_login_out = (Button) view.findViewById(R.id.bt_login_out);
		login_out = (LinearLayout) view.findViewById(R.id.login_out);
//		my_statue = (TextView) view.findViewById(R.id.my_statue);
		username_tv = (TextView) view.findViewById(R.id.username_tv);
		tv_tel_base = (TextView) view.findViewById(R.id.tv_tel_base);
		tv_tel_base.setText(tel);
		linear_address = (LinearLayout) view.findViewById(R.id.linear_address);
		linear_right = (LinearLayout) view.findViewById(R.id.linear_right);
		
		tv_dfk = (TextView) view.findViewById(R.id.tv_dfk);
		tv_dfh = (TextView) view.findViewById(R.id.tv_dfh);
		tv_dsh = (TextView) view.findViewById(R.id.tv_dsh);
//		tv_yfh = (TextView) view.findViewById(R.id.tv_yfh);
		user_image = (CircleImageView) view.findViewById(R.id.user_image);
		layout_salon = (LinearLayout) view.findViewById(R.id.layout_salon);
		layout_add = (LinearLayout) view.findViewById(R.id.layout_add);
		linear_about = (LinearLayout) view.findViewById(R.id.linear_about);
//		layout_businessCard = (LinearLayout) view
//				.findViewById(R.id.layout_businessCard);
//		layout_login_mybalnace = (LinearLayout) view.findViewById(R.id.layout_login_mybalnace);//我的余额
		layout_answer = (LinearLayout) view.findViewById(R.id.layout_answer);
		layout_tel = (LinearLayout) view.findViewById(R.id.layout_tel);
		layout_setting = (LinearLayout) view.findViewById(R.id.layout_setting);
//		layout_login_info = (LinearLayout) view.findViewById(R.id.layout_login_info);
//		linear_setbalance = (LinearLayout) view.findViewById(R.id.linear_setbalance);
//		linear_dls = (LinearLayout) view.findViewById(R.id.linear_dls);
//		tv_dls = (TextView) view.findViewById(R.id.tv_dls);
//		iv_dls = (ImageView) view.findViewById(R.id.iv_dls);
		
		iv_right = (ImageView) view.findViewById(R.id.iv_right);
		tv_rz = (TextView) view.findViewById(R.id.tv_rz);
//		tv_fk = (TextView) view.findViewById(R.id.tv_fk);
//		tv_jf = (TextView) view.findViewById(R.id.tv_jf);
		SetClickListener();
		
	}
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(!hidden){
			ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetInfo(activity, new MyCallBack());
		}
	}
	private void SetClickListener() {
		CommonDialog.setBackListener(this);
//		login_button.setOnClickListener(this);
//		register_button.setOnClickListener(this);
		layout_salon.setOnClickListener(this);
		layout_add.setOnClickListener(this);
		layout_answer.setOnClickListener(this);
		layout_tel.setOnClickListener(this);
		layout_setting.setOnClickListener(this);
		login_out.setOnClickListener(this);
		layout_businessCard.setOnClickListener(this);
		layout_login_info.setOnClickListener(this);
		bt_login_out.setOnClickListener(this);
//		llDrinksDemand.setOnClickListener(this);
		user_image.setOnClickListener(this);
		layout_login_mybalnace.setOnClickListener(this);
//		my_order.setOnClickListener(this);
		tv_dfk.setOnClickListener(this);
		tv_dfh.setOnClickListener(this);
		tv_dsh.setOnClickListener(this);
		tv_yfh.setOnClickListener(this);
		linear_right.setOnClickListener(this);
		linear_dls.setOnClickListener(this);
		linear_address.setOnClickListener(this);
		linear_setbalance.setOnClickListener(this);
		linear_about.setOnClickListener(this);
	}
	  private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
	@Override
	public void onRequestPermissionsResult(int requestCode,
			@NonNull String[] permissions, @NonNull int[] grantResults) {
		  if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
	        {
	            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
	            {
	                callPhone();
	            } else
	            {
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
//                getActivity().finish();
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
		Uri data = Uri.parse("tel:"+tel);
		Intent intents = new Intent(Intent.ACTION_CALL,data);
		getActivity().startActivity(intents);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		HashMap<String,String> map = new HashMap<String,String>();
		final Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.login_button:
        	if(ToastNoNetWork.ToastError(baseActivity))
        		return;
			break;
		case R.id.linear_right:
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			MobclickAgent.onEvent(baseActivity, "C_MY_AUT_1"); 
			intent.setClass(activity,AuthActivity.class);
			if (!IsLogin.Switch(activity)) {
				return;
			}
//			if(!StringUtil.isEmpty(roleid) && "1".equals(roleid))
//				intent.putExtra("role", 1);//普通商户角色
//			else
				intent.putExtra("role", -1);//其他角色
			StartActivityUtil.startActivity(activity,intent);
			break;
		case R.id.layout_salon:
			MobclickAgent.onEvent(baseActivity, "C_MY_SLN_1"); //沙龙
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			map.put("mysalon","");
//			MobclickAgent.onEvent(baseActivity, "00130", map); 
			if (!IsLogin.isLogin(getActivity())) {
				MyApplication.getInstance().startLogin(new LoginCallBack() {

					@Override
					public void loginSuccess() {
						intent.setClass(getActivity(),
								SalonListActivity.class);
						if (SharedPreferencesUtil.getObject(baseActivity,
								"resultbean") != null) {
							ResultBean userInfoResultBean = (ResultBean) SharedPreferencesUtil.getObject(
									baseActivity, "resultbean");
							intent.putExtra(ConstantsUtil.USER_ID_KEY,
									userInfoResultBean.getId());
							StartActivityUtil.startActivity(getActivity(),
									intent);
						}

					}

					@Override
					public void loginFail() {
					}
				}, getActivity());
			} else {
				intent.setClass(getActivity(),
						SalonListActivity.class);
				if (SharedPreferencesUtil.getObject(baseActivity, "resultbean") != null) {
					ResultBean userInfoResultBean = (ResultBean) SharedPreferencesUtil
							.getObject(baseActivity, "resultbean");
					intent.putExtra(ConstantsUtil.USER_ID_KEY,
							userInfoResultBean.getId());
					StartActivityUtil.startActivity(getActivity(), intent);
				}
			}
			break;
		case R.id.layout_add:
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			intent.setClass(activity, SettingBaseActivity.class);
			intent.putExtra("type", ConstantsUtil.ADD_MANAGER);
			StartActivityUtil.startActivity(activity, intent);
			break;
		case R.id.layout_answer:
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			intent.setClass(activity,SettingBaseActivity.class);
			intent.putExtra("type", ConstantsUtil.RETROACTION);
			StartActivityUtil.startActivity(activity, intent);
			break;
		case R.id.layout_tel:
			MobclickAgent.onEvent(baseActivity, "C_MY_CS_1");//客服
			ShowDialog("是否拨打电话:" + tel, "温馨提示", 1, 0);
			break;
		case R.id.layout_setting:
			intent.setClass(activity,SettingBaseActivity.class);
			intent.putExtra("type", ConstantsUtil.SETTING);
			StartActivityUtil.startActivity(activity, intent);
			break;
//		case R.id.layout_login_info:
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			MobclickAgent.onEvent(baseActivity, "C_MY_AUT_1");
//			intent.setClass(activity,AuthActivity.class);
//			if (!IsLogin.Switch(activity)) {
//				return;
//			}
//			if(!StringUtil.isEmpty(roleid) && "1".equals(roleid))
//				intent.putExtra("role", 1);//普通商户角色
//			else
//				intent.putExtra("role", -1);//其他角色
//			StartActivityUtil.startActivity(activity,intent);
//			break;
		case R.id.user_image:
//			showAtWindowTop(activity, user_image, R.layout.forum_release_pic);
//			startActivity(IMHelper.getInstance().getYwIMKit().getConversationActivityIntent());
			//跳转到百度地图
			StartActivityUtil.startActivity(activity,new Intent(activity,BaiduMapActivity.class));
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
		case R.id.bt_login_out:
			/**
			 * 退出
			 */
			// llParentLayout.startAnimation(animationShow);
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			type = 0;
			ShowDialog("是否退出登录?", "温馨提示", 2, type);

			break;
		// 获得名片管理数据
//		case R.id.layout_businessCard:
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			map.put("My_card_management","");
//			MobclickAgent.onEvent(baseActivity, "My_card_management", map);
//			if (!IsLogin.isLogin(getActivity())) {
//				MyApplication.getInstance().startLogin(new LoginCallBack() {
//					@Override
//					public void loginSuccess() {
//						Intent intentbusinessCard = new Intent(getActivity(),
//								IdCardManagerActivity.class);
//						StartActivityUtil.startActivity(getActivity(),
//								intentbusinessCard);
//					}
//
//					@Override
//					public void loginFail() {
//					}
//				}, getActivity());
//			} else {
//				intent.setClass(activity,
//						IdCardManagerActivity.class);
//				StartActivityUtil.startActivity(getActivity(),
//						intent);
//			}
//			break;
//
//		case R.id.layout_demand:
//			/**
//			 * 酒水需求
//			 */
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			map.put("My_drinks_demand","");
//			MobclickAgent.onEvent(baseActivity, "My_drinks_demand", map);
//			if (!IsLogin.isLogin(getActivity())) {
//				MyApplication.getInstance().startLogin(new LoginCallBack() {
//
//					@Override
//					public void loginSuccess() {
//						Intent intent_demand=new Intent(baseActivity,DrinksDemandActivity.class);
//						StartActivityUtil.startActivity(baseActivity, intent_demand);
//
//					}
//
//					@Override
//					public void loginFail() {
//					}
//				}, getActivity());
//			} else {
//				intent.setClass(activity,
//						DrinksDemandActivity.class);
//				StartActivityUtil.startActivity(baseActivity, intent);
//			}
//
//			break;
//		case R.id.layout_login_mybalnace:
//			MobclickAgent.onEvent(baseActivity, "C_MY_WAL_1");//我的钱包
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			if (!IsLogin.isLogin(getActivity())) {
//				MyApplication.getInstance().startLogin(new LoginCallBack() {
//					@Override
//					public void loginSuccess() {
////						StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
//					}
//					@Override
//					public void loginFail() {
//					}
//				}, getActivity());
//			}else{
//				StartActivityUtil.startActivity(baseActivity, MyBalanceActivity.class);
//			}
//
//			break;
//		case R.id.my_order:
//			MobclickAgent.onEvent(baseActivity, "C_MY_ORD_1");//我的订单
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			//获取全部订单
//			isLoginOrder("");
//			break;
//			 订单列表：0 全部订单 1 待付款 2  待发货  3待收货 4 已收货 
		case R.id.tv_dfk:
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			isLoginOrder("1");
			break;
		case R.id.tv_dfh:
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			isLoginOrder("2");
			break;
		case R.id.tv_dsh:
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			isLoginOrder("3");
			break;
//		case R.id.tv_yfh:
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			isLoginOrder("4");
//			break;
		case R.id.linear_address:
			MobclickAgent.onEvent(baseActivity, "C_MY_ADR_1");//我的收货地址
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			//获取收货地址
			intent.setClass(activity,SettingBaseActivity.class);
			intent.putExtra("type", ConstantsUtil.ADD_MANAGER);
			intent.putExtra("isCenter", true);
			StartActivityUtil.startActivity(activity,intent);
			break;
//		case R.id.linear_setbalance:
//			MobclickAgent.onEvent(baseActivity, "C_MY_BAC_1");//我的收款账号
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			//设置默认提现账户
//			if (!IsLogin.isLogin(getActivity())) {
//				MyApplication.getInstance().startLogin(new LoginCallBack() {
//					@Override
//					public void loginSuccess() {
//						intent.setClass(activity,
//								WithDrawActivity.class);
//						intent.putExtra("account", resultcardbean);
////						if(roleid!=null && listrole.contains("3")){
////							intent.putExtra("role", 3);//代理商后台角色
////						}else if(listrole!=null && listrole.contains("5")){
////							intent.putExtra("role", 5);//代理商后台角色
////						}else{
////							intent.putExtra("role", -1);//其他角色
////						}
//						if(!StringUtil.isEmpty(roleid)){
//							if("3".equals(roleid)){
//								intent.putExtra("role", 3);//代理商后台角色
//							}else if("5".equals(roleid)){
//								intent.putExtra("role", 5);//代理商后台角色
//							}
//						}else{
//							intent.putExtra("role", -1);//其他角色
//						}
//						StartActivityUtil.startActivity(baseActivity, intent);
//					}
//					@Override
//					public void loginFail() {
//					}
//				}, getActivity());
//			}else{
//				intent.setClass(activity,
//						WithDrawActivity.class);
//				intent.putExtra("account", resultcardbean);
//				if(!StringUtil.isEmpty(roleid) && "3".equals(roleid))
//					intent.putExtra("role", 3);//代理商后台角色
//				else
//					intent.putExtra("role", -1);//其他角色
//				StartActivityUtil.startActivity(baseActivity, intent);
//			}
//			break;
//		case R.id.linear_dls:
//			//代理商
//			if(ToastNoNetWork.ToastError(baseActivity))
//				return;
//			if (!IsLogin.isLogin(getActivity())) {
//				MyApplication.getInstance().startLogin(new LoginCallBack() {
//					@Override
//					public void loginSuccess() {
//						JumpH5();
//					}
//					@Override
//					public void loginFail() {
//					}
//				}, getActivity());
//			}else{
//				JumpH5();
//			}
//			break;
		case R.id.linear_about:
			MobclickAgent.onEvent(baseActivity, "C_MY_ABT_1");//关于
				StartActivityUtil.startActivity(baseActivity, new Intent(baseActivity,AboutUs.class));
			break;
		default:
			break;
		}
	}
	
	//跳转到管理商后台
	private void JumpH5() {
		Intent intents = new Intent(baseActivity,DrinkInfoActivity.class);
		if(!StringUtil.isEmpty(h5url)){
			intents.putExtra("h5url",h5url+"?uid="+uid);
		}else{
			intents.putExtra("h5url",RequestConfiguration.BASE_URL_TEST+"?uid="+uid);
		}
		intents.putExtra("title",title);
		StartActivityUtil.startActivity(baseActivity , intents);
	}
	
	private void isLoginOrder(final String status) {
		final Intent intent = new Intent(baseActivity, MyAllOrderActivity.class);
		intent.putExtra("status", status);
		if (!IsLogin.isLogin(getActivity())) {
			MyApplication.getInstance().startLogin(new LoginCallBack() {
				@Override
				public void loginSuccess() {
					StartActivityUtil.startActivity(baseActivity, intent);
				}
				@Override
				public void loginFail() {
				}
			}, getActivity());
		}else{
			StartActivityUtil.startActivity(baseActivity, intent);
		}
	}

	private void ShowDialog(String notice, String title, final int flag,
			final int type) {
		CommonDialog.makeText(activity, title, notice, new OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
					String tel) {
				// TODO Auto-generated method stub
				if (OnDialogListener.LEFT == result && flag == 1) {
					if(Build.VERSION.SDK_INT >= 23){
						if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
						{
							userCenterFragment.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
									MY_PERMISSIONS_REQUEST_CALL_PHONE);
						} else{
							callPhone();
						}
					}else{
						callPhone();
					}
					//6.0权限处理
					CommonDialog.Dissmess();
				} else if (OnDialogListener.LEFT == result && flag == 2) {
					if(!ToastNoNetWork.ToastError(baseActivity)){
						ApiHttpCilent.getInstance(getContext().getApplicationContext()).loginoOut(activity,
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
		activity.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}	
		});
	}

	//退出登录
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
	public static class MyHandler extends WeakHandler<UserCenterFragment> {
		public MyHandler(UserCenterFragment reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				if (getReference().type == 0) {
					//退出登录成功 IM
//					LoginSampleHelper.getInstance().loginOut_Sample(getReference().baseActivity);
					Utils.StartLogin(getReference().activity,true);
					ToastUtil.showToast(getReference().activity, getReference().activity.getResources()
							.getString(R.string.loginout_success));
					MyApplication.getInstance().clearLoginInor(
							getReference().baseActivity);
					NotLoginStatus();
//					MyApplication.isRefreshShopcart = true;// 控制购物车刷新事件
					UserCenterFragment fragment = getReference();
					MainActivity mainActivity = (MainActivity) fragment
							.getActivity();
//					if (mainActivity != null
//							&& mainActivity.shoppingCartFragment != null) {
//						mainActivity.shoppingCartFragment.getNetData();
//						mainActivity.shoppingCartFragment.clearData();
//					}
				} else if (getReference().type == 1) {
					// 1 未申请 2 正在审核 3 审核通过 4 审核失败
					ResultBean bean = (ResultBean) msg.obj;
					if(bean != null){
//					getReference().VerifyStatus(bean,
//							getReference().my_statue);
					getReference().username_tv.setText(StringUtil.isEmpty(bean.getMobile())?"":bean.getMobile());
					getReference().my_statue.setVisibility(View.VISIBLE);
					getReference().linear_address.setVisibility(View.VISIBLE);
//					getReference().roleid = bean.getRoleid();
					getReference().uid = bean.getId();
					getReference().h5url = bean.getUrl();
					if(StringUtil.isEmpty(getReference().h5url)){
						getReference().h5url = RequestConfiguration.BASE_URL_TEST+"/h5/franchise.jhtml";
					}
//					if(!StringUtil.isEmpty(getReference().h5url)){
//						if(!StringUtil.isEmpty(getReference().roleid)){
//							if("3".equals(getReference().roleid)){
//								getReference().linear_dls.setVisibility(View.VISIBLE);
//								getReference().title = "代理商后台";
//								getReference().tv_dls.setText(getReference().title);
//								getReference().layout_login_info.setVisibility(View.GONE);
//								getReference().iv_dls.setImageResource(R.drawable.dailishang);
//							}else if("2".equals(getReference().roleid)){
//								getReference().linear_dls.setVisibility(View.VISIBLE);
//								getReference(). title= "代理商员工后台";
//								getReference().tv_dls.setText(getReference().title);
//								getReference().layout_login_info.setVisibility(View.GONE);
//								getReference().iv_dls.setImageResource(R.drawable.yuangong);
//							}else if("5".equals(getReference().roleid)){
//								getReference().linear_dls.setVisibility(View.VISIBLE);
//								getReference(). title= "经销商";
//								getReference().tv_dls.setText(getReference().title);
//								getReference().layout_login_info.setVisibility(View.GONE);
//								getReference().iv_dls.setImageResource(R.drawable.yuangong);
//							}else{
//								getReference().title= "零售商";
//								getReference().linear_dls.setVisibility(View.GONE);
//								getReference().layout_login_info.setVisibility(View.VISIBLE);
//							}
//						}else{
//							getReference().linear_dls.setVisibility(View.GONE);
//							getReference().layout_login_info.setVisibility(View.VISIBLE);
//						}
//					}else{
//						getReference().linear_dls.setVisibility(View.GONE);
//						getReference().layout_login_info.setVisibility(View.VISIBLE);
//					}
					}
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				getReference().resultcardbean = (ResultCardBean) msg.obj;
				break;
			case ConstantsUtil.HTTP_FAILE:
				ErrorBean back = (ErrorBean) msg.obj;
				// code=100代表登录状态失效 需要重新登录
				if (back !=null && "100".equals(back.getCode())) {
//					 ToastUtil.showToast(getReference().activity, back.getInfo());
//					SharedPreferencesUtil.writeSharedPreferencesBoolean(
//							getReference().activity, "login", "islogin", false);
					NotLoginStatus();
//					LoginSampleHelper.getInstance().loginOut_Sample(getReference().baseActivity);
					Utils.StartLogin(getReference().activity ,true);
				}
				break;
			default:
				break;
			}
		}

		// 登录失效界面ui状态 退出状态
		private void NotLoginStatus() {
			getReference().my_statue.setVisibility(View.INVISIBLE);
//			getReference().login_layout.setVisibility(View.VISIBLE);
			getReference().login_out.setVisibility(View.INVISIBLE);
			getReference().bt_login_out.setVisibility(View.INVISIBLE);
			getReference().username_tv.setVisibility(View.INVISIBLE);
			getReference().linear_address.setVisibility(View.INVISIBLE);
			ApiHttpCilent.getInstance(getReference().getContext().getApplicationContext()).clearCookie(getReference().activity);
		}
	}


	/**
	 * 
	 * 
	 * @describe:设置选择图片监听
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:42:47
	 * 
	 */
	private void setCheckPhotoListener() {
		// TODO Auto-generated method stub
		tvPhoto.setOnClickListener(this);
		tvCamera.setOnClickListener(this);
		tvCancel.setOnClickListener(this);
		
	}

	/**
	 * 
	 * 
	 * @describe:调用相册
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:43:27
	 * 
	 */
	private void startPhoto() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO);

	}

	/**
	 * 
	 * 
	 * @describe:调取相机
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-19上午11:35:11
	 * 
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
	 * 
	 * 
	 * @describe:显示选择图片对话框
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:42:21
	 * 
	 */
	private void showAtWindowTop(Activity activity, View position, int layoutId) {

		View mPopMenuView = null;
		mPopMenuView = LayoutInflater.from(activity).inflate(layoutId, null);
		RelativeLayout layout = (RelativeLayout) mPopMenuView
				.findViewById(R.id.show_select_camer_photo);
		layout.setOnClickListener(new OnClickListener() {

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
				ViewUtil.getScreenWith((activity)), LayoutParams.WRAP_CONTENT,
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
	 * 
	 * 
	 * @describe:保存图片信息
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:49:05
	 * 
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
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(contentUri, proj, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void completeInfor() {

		if (!NetWorkState.isNetWorkConnection(activity)) {
			ToastUtil.showToast(activity, "请检查网络设置");

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
		BasicHttpClient.getInstance(activity).post(activity, paramsIn,
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
			BitmapUtil.galleryAddPic(activity, mCurrentPhotoPath);
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
					BitmapUtil.galleryAddPic(activity, mCurrentPhotoPath);
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

			ContentResolver resolver = activity.getContentResolver();
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

					};
				}).start();

			} else {

				// showRoundProcessDialog();
				new Thread(new Runnable() {
					public void run() {

						Bitmap bitmap2 = null;
						InputStream is = null;
						Bitmap photoTemp = null;

						try {
							is = activity.getContentResolver().openInputStream(
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
					};
				}).start();
				break;
			}
		default:
			break;
		}
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
//		if(MyApplication.getInstance().isLogin){
		ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetUserCard(baseActivity,
				new CardCallBack());
//		ApiHttpCilent.getInstance(activity).GetInfo(activity, new MyCallBack());
//		}else{
//			 Utils.StartLogin(baseActivity,true);
//		}
	}
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
	@Override
	protected void setViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = (MainActivity) getActivity();
		view = inflater.inflate(R.layout.fragment_user_center, container, true);
		initViews();
		return view;
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return null;
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
	protected boolean hasTitleIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean hasDownIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	// shopStatusMap.put("0", "未认证");
	// shopStatusMap.put("1", "未申请");
	// shopStatusMap.put("2", "正在审核");
	// shopStatusMap.put("3", "审核通过");
	// shopStatusMap.put("4", "审核失败")
	private  void VerifyStatus(ResultBean status, TextView tv) {
//		status.setViplevel(1);
		tv_rz.setText("认证店铺");
		iv_right.setImageResource(R.drawable.my_register);
		tv.setText("认证有惊喜");
		switch (status.getViplevel()) {
		case 0:
			tv_rz.setText("未认证店铺");
			iv_right.setImageResource(R.drawable.my_not_register);
			break;
		case 1:
			tv_rz.setText("部分认证店铺");
//			tv_jf.setVisibility(View.VISIBLE);
			break;
		case 2:
			tv_rz.setText("部分认证店铺");
//			tv_jf.setVisibility(View.VISIBLE);
//			tv_fk.setVisibility(View.VISIBLE);
			break;
		case 3:
//			tv_jf.setVisibility(View.VISIBLE);
//			tv_fk.setVisibility(View.VISIBLE);
			tv.setText("已全部认证");
			tv.setTextColor(getResources().getColor(R.color.color_45b722));
			break;
		default:
			break;
		}
//		switch (Integer.parseInt(status.getVerifystatus())) {
//		case 0:
//			tv.setText("未认证");
//			break;
//		case 1:
//			tv.setText("未申请");
//			break;
//		case 2:
//			tv.setText("正在审核");
//			break;
//		case 3:
//			tv.setText("已认证");
//			break;
//		case 4:
//			tv.setText("审核失败");
//			break;
//		default:
//			break;
//		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 MobclickAgent.onPageStart("PG_MY");
		islogin = SharedPreferencesUtil.getSharedPreferencesBoolean(activity,
				"login", "islogin", false);
		ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetInfo(activity, new MyCallBack());
//		if (MyApplication.getInstance().isLogin) {
//			object = (ResultBean) SharedPreferencesUtil.getObject(activity,
//					"resultbean");
//			if (object != null) {
//				mobile_user = object.getMobile();
//				String statu = object.getVerifystatus();
//				
//				if (statu != null && !statu.equals(""))
//					VerifyStatus(object,
//							my_statue);
//			}
//			login_out.setVisibility(View.VISIBLE);
//			bt_login_out.setVisibility(View.VISIBLE);
//			username_tv.setVisibility(View.VISIBLE);
//			username_tv.setText(mobile_user);
//		} else {
//			login_out.setVisibility(View.INVISIBLE);
//			username_tv.setVisibility(View.INVISIBLE);
//			bt_login_out.setVisibility(View.INVISIBLE);
//		}
		type = 1;
		MobclickAgent.onPageStart("UserCenterFragment");
		
	}

	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("UserCenterFragment");
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
	}
}