package com.heheys.ec.controller.activity; 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.AdapterCompany;
import com.heheys.ec.model.adapter.AdapterCompany.ClickCallBack;
import com.heheys.ec.model.adapter.AdapterIdCard;
import com.heheys.ec.model.adapter.AdapterIdCard.ClickCallBacks;
import com.heheys.ec.model.adapter.AdapterLicnese;
import com.heheys.ec.model.adapter.AdapterLicnese.ClickCallBackss;
import com.heheys.ec.model.adapter.AdapterZhuzhi;
import com.heheys.ec.model.adapter.AreaAdapter;
import com.heheys.ec.model.adapter.ProviceAdapter;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.ImageBaseBean;
import com.heheys.ec.model.dataBean.ImageBaseBean.ImageBean;
import com.heheys.ec.model.dataBean.LoginNamePwd;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.Bean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.CityBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.ProvinceList;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.model.dataBean.ShopBaseBean;
import com.heheys.ec.model.dataBean.ShopBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ImageUploadUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.alibaba.mobileim.IYWLoginService;
//import com.alibaba.mobileim.YWAPI;
//import com.alibaba.mobileim.YWIMKit;
//import com.alibaba.mobileim.YWLoginParam;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-11-3 下午3:25:32 
 * 类说明 
 * @param提交商品认证修复版本
 */
public class AuthActivity extends BaseActivity {

	private Context context;
	private MyHandler handler = new MyHandler(AuthActivity.this);
	private RelativeLayout root_view;
	private EditText et_shopName;
	private EditText et_shopAdd;
	private EditText et_leadname;
//	private EditText et_linkName;
	private TextView et_linkTel;
	private EditText et_cardid;
	private EditText et_blicense;
	private EditText et_shopdetaileadd;
	private ShopBean shopbean = new ShopBean();
	private int imageId;
	private int codeId;
//	private Button commit_verify;
//	private Button back_verify;
	private TextView tvPhoto;
	private TextView tvCamera;
	private TextView tvCancel;
	private TextView tv_auth_all;
	private TextView step1;
	private TextView step2;
	private TextView step1_submit;
	private TextView step2_submit;
	private TextView step3_submit;
	private TextView step2_prewer;
	private TextView step3_prewer;
	private TextView tv_deal;//电子合同
	private PopupWindow mPopupWindow;
	private static final int CAMERA = 1;
	private static final int PHOTO = 2;
	private String picName;// 调取相机生成图片名称
	private String picNamethumbnail;// 调取相机生成图片名称
	// 图片存储路径
	private static final String PATH = Environment
			.getExternalStorageDirectory() + "/DCIM/";
	private String mCurrentPhotoPath;// 图片本地存储地址
	private File picFile;
	private Uri imageUri;
	private static Uri photoUri;
//	private YWIMKit mIMKit;
	private Bitmap bitmaps;
//	private LinearLayout linear_one;
//	private LinearLayout linear_five;
	private LinearLayout linear_idcard;
	private LinearLayout linear_license;
	private LinearLayout linear_button;
//	private LinearLayout linear_zhuce;
//	private LinearLayout linear_shuiwu;
	private ImageView ivback;
	private int temple = 0;
	private Bitmap newBitmap;
	private Bean addressbean;//元数据地址
	String pic1 = "";
	String pic2 = "";
	String pic3 = "";
	int indexPress;//当前点击的是哪个按钮
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	List<String> list3 = new ArrayList<String>();
	//返回回来的数据集合
	List<String> list1_return = new ArrayList<String>();//公司环境
	List<String> list2_return = new ArrayList<String>();//身份证
	List<String> list3_return = new ArrayList<String>();//营业执照
	private ImageView ivs;
	private String uerId;
	private InputMethodManager imm;
	private int role;
	private LinearLayout reason_lv;
	private Spinner spinner_provice;
	private Spinner spinner_area;
	private List<ProvinceList> provences;
	private List<CityBean> list;
	private ImageView arr1;
	private ImageView arr2;
	private ImageBean bean;
	public  static int imageFlag;//判断跳转
	private boolean modified;
	private boolean isRegister;
	private String imgPath;
	private int picSize;
	private String st_name = "";//公司名称
	private String st_shopAdd = "";
	private String st_leadname = "";
	private String st_linkTel = "";
	private String st_cardid = "";
	private String st_blicense = "";
	private String st_shopdetaileadd = "";
	private String simpleName;
	private String simpleAddress;
	private String simpleLegal;
	private String simpleContact;
	private String simplemobile;
	private String simpleCardid;
	private String simpleBlicense;
	private String simplepic1;
	private String simplepic2;
	private String simplepic3;
	private String simpleUrl;
	private String simpleAuthentication;
	private TextView tv_edit_idcard;
	private TextView tv_edit_company;
	private TextView tv_edit_license;
	private TextView tv_reason_time;
	private TextView tv_reason;
	private TextView reason_tv;//是否审核通过原因
	public ImageCallBack ivcallbacks;
	//分别点击5个编辑按钮的状态
	private boolean click1 = true;
	private boolean click2 = true;
	private boolean click3 = true;
	private boolean click4 = true;
	private boolean click5 = true;
	private  Activity myPhoto;
	private int cityid;//城市id
	private int proviceid;//省份id
	private int total;
	private GridView grid_layout,grid_layout_license,grid_layout_idcard;
	AdapterCompany gridAdapter;//公司照片
	AdapterIdCard gridAdapterIdCard;//身份证
	AdapterLicnese gridAdapterLicense;//营业执照
	AdapterZhuzhi adapterZhuzhi;
	AdapterZhuzhi adapterShuWu;
	List<HashMap<String,ImageView>> listMap = new ArrayList<HashMap<String,ImageView>>();
	private AreaAdapter madapterarea;
	private ProviceAdapter provicemadapter;
	private String baseUrl = "";
	private LinearLayout linear_step1;
	private LinearLayout linear_step2;
	private LinearLayout linear_step3;
	/**初始化请求返回的数据bean**/
	private ShopBaseBean baseBean;
	private String regagreementurl;//获取的电子合同
	private CheckBox cb_deal;
//	private CheckBox cb_deal_one;
//	private CheckBox cb_deal_two;
	//进度四个圆对象
	private ImageView iv_round_one;
	private ImageView iv_round_two;
	private ImageView iv_round_three;
	private ImageView iv_round_four;
	//进度线三个对象
	private TextView line_one;
	private TextView line_two;
	private TextView line_two_next;
	private TextView line_three_next;
	private TextView line_three;
	private int viplevel;//认证级别
	private LinearLayout linear_auth_one;
	private LinearLayout linear_auth_two;
	private LinearLayout linear_auth_three;
	private LinearLayout linear_cb;
	private String pwd_two;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.auth);
		initView();
		HttpDate();
	}

	private void initView() {
		spinner_provice = (Spinner) findViewById(R.id.spinner_provice);//省份
		spinner_area = (Spinner) findViewById(R.id.spinner_area);//地区
		reason_lv = (LinearLayout) findViewById(R.id.reason_lv);
		root_view = (RelativeLayout) findViewById(R.id.root_view);
		et_shopName = (EditText) findViewById(R.id.et_shopName);
		et_shopAdd = (EditText) findViewById(R.id.et_shopAdd);
		et_leadname = (EditText) findViewById(R.id.et_leadname);
		et_linkTel = (TextView) findViewById(R.id.et_linkTel);
		et_cardid = (EditText) findViewById(R.id.et_cardid);
		et_blicense = (EditText) findViewById(R.id.et_blicense);
		tv_deal = (TextView) findViewById(R.id.tv_deal);
		tv_auth_all = (TextView) findViewById(R.id.tv_auth_all);
		step1_submit = (TextView) findViewById(R.id.step1_submit);
		step1 = (TextView) findViewById(R.id.step1);
		step2_submit = (TextView) findViewById(R.id.step2_submit);
		step2 = (TextView) findViewById(R.id.step2);
		step3_submit = (TextView) findViewById(R.id.step3_submit);
		step2_prewer = (TextView) findViewById(R.id.step2_prewer);
		step3_prewer = (TextView) findViewById(R.id.step3_prewer);
		et_shopdetaileadd = (EditText) findViewById(R.id.et_shopdetaileadd);
		linear_auth_one = (LinearLayout) findViewById(R.id.linear_auth_one);
		linear_auth_two = (LinearLayout) findViewById(R.id.linear_auth_two);
		linear_auth_three = (LinearLayout) findViewById(R.id.linear_auth_three);
		linear_cb = (LinearLayout) findViewById(R.id.linear_cb);
//		commit_verify = (Button) findViewById(R.id.commit_verify);
//		back_verify = (Button) findViewById(R.id.back_verify);
		cb_deal = (CheckBox) findViewById(R.id.cb_deal);
		tvRight.setOnClickListener(this);
		linear_step1 = (LinearLayout) findViewById(R.id.linear_step1);
		linear_step2 = (LinearLayout) findViewById(R.id.linear_step2);
		linear_step3 = (LinearLayout) findViewById(R.id.linear_step3);
		
		linear_idcard = (LinearLayout) findViewById(R.id.linear_idcard_image);
		linear_license = (LinearLayout) findViewById(R.id.linear_license_image);
		linear_button = (LinearLayout) findViewById(R.id.linear_button);
		tv_edit_company = (TextView) findViewById(R.id.tv_edit_company);
		tv_edit_idcard = (TextView) findViewById(R.id.tv_edit_idcard);
		tv_edit_license = (TextView) findViewById(R.id.tv_edit_license);
		reason_tv = (TextView) findViewById(R.id.reason_tv);
		tv_reason_time = (TextView) findViewById(R.id.tv_reason_time);
		tv_reason = (TextView) findViewById(R.id.tv_reason);
		grid_layout = (GridView) findViewById(R.id.grid_layout);
		grid_layout_idcard = (GridView) findViewById(R.id.grid_layout_idcard);
		grid_layout_license = (GridView) findViewById(R.id.grid_layout_license);
		
		iv_round_one = (ImageView) findViewById(R.id.iv_round_one);
		iv_round_two = (ImageView) findViewById(R.id.iv_round_two);
		iv_round_three = (ImageView)findViewById(R.id.iv_round_three);
		iv_round_four = (ImageView)findViewById(R.id.iv_round_four);
		
		line_one = (TextView)findViewById(R.id.line_one);
		line_two = (TextView)findViewById(R.id.line_two);
		line_two_next = (TextView)findViewById(R.id.line_two_next);
		line_three_next = (TextView)findViewById(R.id.line_three_next);
		line_three = (TextView)findViewById(R.id.line_three);
		
		arr1 = (ImageView) findViewById(R.id.arr1);
		arr2 = (ImageView) findViewById(R.id.arr2);
		step1.setOnClickListener(this);
		step2.setOnClickListener(this);
		step1_submit.setOnClickListener(this);
		step2_submit.setOnClickListener(this);
		step3_submit.setOnClickListener(this);
		step2_prewer.setOnClickListener(this);
		step3_prewer.setOnClickListener(this);
		
		linear_cb.setOnClickListener(this);
		tv_edit_company.setOnClickListener(this);
		tv_edit_idcard.setOnClickListener(this);
		tv_edit_license.setOnClickListener(this);
//		commit_verify.setOnClickListener(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		//设置部分字体红色
		SetRedText(tv_deal,cb_deal);
	}

	private void SetRedText(TextView tv,CheckBox cb) {
		SpannableStringBuilder spannable = new SpannableStringBuilder(tv.getText().toString().trim());
		spannable.setSpan(new MyClickText(this), 6, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(spannable);
		//设置部分文字可以点击
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setHighlightColor(Color.TRANSPARENT);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					MobclickAgent.onEvent(AuthActivity.this, "C_AUTH_7");
				}
			}
		});
	}
	
	
	//监听点击电子合同
	class MyClickText extends ClickableSpan{
        private Context context;

        public MyClickText(Context context) {
            this.context = context;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor(getResources().getColor(R.color.color_af2942));
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
//            Toast.makeText(context,"发生了点击效果"+regagreementurl,Toast.LENGTH_SHORT).show();
        	if(!StringUtil.isEmpty(regagreementurl)){
            Intent intent = new Intent(baseActivity, JDPayActivity.class);//获取电子合同
			intent.putExtra("url", regagreementurl);
			intent.putExtra("title", "喝喝云商用户协议");
			intent.putExtra("flag", 3);//电子合同
			StartActivityUtil.startActivity(baseActivity, intent);
        	}
        }
    }
	/**
	 * 获取当前登录用户信息
	 * 
	 * */
	private void HttpDate() {
		// TODO Auto-generated method stub
	runOnUiThread(new Runnable() {
			@Override
			public void run() {
			// TODO Auto-generated method stub
			addressbean = (Bean) SharedPreferencesUtil.getObject(AuthActivity.this, "Province");
			provences = new ArrayList<ProvinceList>();
			list = new ArrayList<CityBean>();
			provences = addressbean.getList();
			list = provences.get(0).getProvince().getCity();
			//初始化默认第一个是id
			if(provences!=null && provences.size()>0){
				proviceid = provences.get(0).getProvince().getId();
				  if(provences.get(0).getProvince().getCity()!=null && provences.get(0).getProvince().getCity().size()>0){
					  cityid = provences.get(0).getProvince().getCity().get(0).getId();
				  }
			}
			provicemadapter = new ProviceAdapter(provences,AuthActivity.this);
			spinner_provice.setAdapter(provicemadapter);
			spinner_provice.setSelection(0, true);
			madapterarea = new AreaAdapter(list, AuthActivity.this);
			spinner_area.setAdapter(madapterarea);
			spinner_area.setSelection(0, true);
		
			spinner_provice.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					list = provences.get(position).getProvince().getCity();
					 madapterarea = new AreaAdapter(list, AuthActivity.this);
					 spinner_area.setAdapter(madapterarea);
					 proviceid = provences.get(position).getProvince().getId();
					 if(provences.get(position).getProvince().getCity()!=null && provences.get(position).getProvince().getCity().size()>0)
					 cityid = provences.get(position).getProvince().getCity().get(0).getId();
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			spinner_area.setOnItemSelectedListener(new OnItemSelectedListener() {
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					cityid = list.get(position).getId();
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
		}
	});
		context = AuthActivity.this;
		Intent intent = getIntent();
		if(intent!=null){
			isRegister = intent.getBooleanExtra("isRegister", false);
			pwd_two = intent.getStringExtra("pwd_two");
			role = intent.getIntExtra("role", -1);
			codeId = 1;
//			CanEditPhoto();
			if(isRegister){
				tvRight.setVisibility(View.INVISIBLE);
				DisplayImage("",1);//环境照片
				DisplayImage("",2);//身份证照片
				DisplayImage("",3);//营业执照照片
				codeId = 4;
				cb_deal.setChecked(false);
			}else{
//				ApiHttpCilent.getInstance(context).getUserInfo(context,
//						new MyCallBack());
				cb_deal.setChecked(true);
			}
			ApiHttpCilent.getInstance(context).getUserInfo(context,
					new MyCallBack());
			
		}
	}

	private void CanEditPhoto() {
	 if(shopbean!=null && shopbean.getAuthentication()!=null){
		if(1 == role){
			if(shopbean.getAuthentication().equals("2") || shopbean.getAuthentication().equals("3")){
			//普通零售商角色
//				1 未申请 2 审核中 3 审核通过 4 审核失败
//			tv_edit_company.setVisibility(View.GONE);
//			tv_edit_idcard.setVisibility(View.GONE);
//			tv_edit_license.setVisibility(View.GONE);
//			grid_layout.setClickable(false);
//			grid_layout_license.setClickable(false);
//			grid_layout_idcard.setClickable(false);
			  }
			}
		}
	}
	class MyCallBack extends BaseJsonHttpResponseHandler<ShopBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ShopBaseBean arg4) {
			Dismess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ShopBaseBean arg3) {
			Dismess();
		}

		@Override
		protected ShopBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dismess();
			Gson gson = new Gson();
			baseBean = gson.fromJson(response, ShopBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(baseBean.getStatus()) && codeId == 1) {// 正常获取个人信息
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = baseBean.getResult();
			} else if ("1".equals(baseBean.getStatus()) && codeId == 2) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;// 提交信息成功
			} else if("1".equals(baseBean.getStatus()) &&codeId==3){
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;// 提交图片成功
			}else if ("0".equals(baseBean.getStatus()) && codeId == 1) {
				message.what = ConstantsUtil.HTTP_FAILE;
				message.obj = baseBean.getError();
			} else if ("1".equals(baseBean.getStatus()) && codeId == 4) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
				message.obj = baseBean.getError();
			}else {
				message.what = ConstantsUtil.HTTP_FAILE_LOGIN;
				message.obj = baseBean.getError();
			}
			handler.sendMessage(message);
			return baseBean;
		}
	}
	public void GetLogin(){
		if(!StringUtil.isEmpty(pwd_two) && !StringUtil.isEmpty(et_linkTel.getText().toString().trim())){
			 isCode = 1;
		ApiHttpCilent.getInstance(context).login(this,
				StringUtil.filterSpace(et_linkTel.getText().toString().trim()),
				StringUtil.filterSpace(pwd_two), new MyLoginCallBack());}
		}
	
	private BaseBean loginBean;
	private ResultBean loginObj;
	private int isCode;//1 登录 2登录个推
	private MyApplication myApplication;
	private String statusAuth;
	private String account;
	class MyLoginCallBack extends  BaseJsonHttpResponseHandler<BaseBean>{
		

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dismess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Dismess();
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dismess();
			Gson gson = new Gson();
			loginBean = gson.fromJson(response,BaseBean.class);
			Message message = Message.obtain();
			if("1".equals(loginBean.getStatus()) && isCode == 1){
				message.what = 105;
				message.obj = baseBean.getResult();
			}else if("1".equals(loginBean.getStatus()) && isCode == 2){
				message.what = 106;
				message.obj = baseBean.getResult();
			}else{
				message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 注册完成登录错误
				message.obj = loginBean.getError();
			}
			handler.sendMessage(message);
			return loginBean;
		}
	}
//	 private void loginService(String imaccount, String impassword) {
//			// TODO Auto-generated method stub
//			//开始登录
//			String userid = imaccount;
//			String password = impassword;
//			IYWLoginService loginService = myApplication.getmIMKit().getLoginService();
//			YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
//			loginService.login(loginParam, new com.alibaba.mobileim.channel.event.IWxCallback() {
//				@Override
//				public void onSuccess(Object... arg0) {
//				}
//
//				@Override
//				public void onProgress(int arg0) {
//				}
//
//				@Override
//				public void onError(int arg0, String arg1) {
//				}
//			});
//		}
	/**
	  * 注册主动登录推送和IM聊天
	  * 
	  * */
	 private void loginCidAndIM() {
		 loginObj = loginBean.getResult();
//			final String userid = loginObj.getImaccount();
//			mIMKit = YWAPI.getIMKitInstance(userid, ConstantsUtil.APP_KEY);
//			myApplication = MyApplication.getInstance();
//			myApplication.setmIMKit(mIMKit);
//			loginService(userid,loginObj.getImpassword());
			/*
			 * 登录个推
			 * */
			MoreUserInfo();
			SharedPreferencesUtil.saveObject(context, loginObj,"resultbean");
			//保存当前登录获取的数据
			SharedPreferencesUtil.writeSharedPreferencesBoolean(context,"login", "islogin", true);
			Intent intent = new Intent();
			intent.setAction(ConstantsUtil.LOGIIN_ACTION);
			context.sendBroadcast(intent);
			
			//登录成功调取绑定cid事件
		    String cid = SharedPreferencesUtil.getSharedPreferencesString(baseContext, "loginCid", "cid", "");
		    isCode = 2;
			ApiHttpCilent.getInstance(baseContext).BindCid(AuthActivity.this, loginObj.getId(), cid, new MyLoginCallBack());
			SharedPreferencesUtil.writeSharedPreferencesString(baseContext, "loginId", "loginId",  loginObj.getId());
		 
	 }
	 
	//存储当前用户名密码
			private void MoreUserInfo() {
				ArrayList<LoginNamePwd> listpwd = new ArrayList<LoginNamePwd>();
				listpwd.clear();
				//安全存储用户名
				LoginNamePwd loginbean = new LoginNamePwd();
				loginbean.setUserName(loginObj.getMobile());
				loginbean.setUserPwd(pwd_two);
				listpwd.add(loginbean);
				SharedPreferencesUtil.saveObject(baseContext, listpwd, "loginUserPwd");
			}
	public  class MyHandler extends WeakHandler<AuthActivity> {
 
		@SuppressLint("HandlerLeak") 
		public MyHandler(AuthActivity reference) {
			super(reference);
		}
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 105:
				//登录成功
				loginCidAndIM();
				Intent intents = new Intent();
				intents.setClass(context,MainActivity.class);
				StartActivityUtil.startActivity((Activity) context, intents);
				finish();
				break;
			case 106:
				//登录个推成功
				break;
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindViewData();//获取个人信息成功
				break;
			case ConstantsUtil.HTTP_SUCCESS_LATER:
				//获取基本信息 电子合同
				getcontract();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				ToastUtil.showToast(getReference(), getReference()
						.getResources().getString(R.string.commit_update_ing));
				SaveDate();
				if(indexPress == 1){
					step1_submit.setVisibility(View.INVISIBLE);
					step1.setVisibility(View.INVISIBLE);
				}else if(indexPress == 2){
					step2_submit.setVisibility(View.INVISIBLE);
					step2.setVisibility(View.INVISIBLE);
					step2_prewer.setVisibility(View.INVISIBLE);
				}else if(indexPress == 3){
					step3_submit.setVisibility(View.INVISIBLE);
					step3_prewer.setVisibility(View.INVISIBLE);
				}
				GetLogin();
				if(isRegister){
//					Intent intent = new Intent(context,LoginActivity.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					StartActivityUtil.startActivity((Activity) context, intent);
//					finish();
//					return;
				}else{
//					GetLogin();
//					Intent intent = new Intent(context,MainActivity.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					StartActivityUtil.startActivity((Activity) context, intent);
//					finish();
				}
				account = SharedPreferencesUtil.getSharedPreferencesString(context, "loginaccount", "account", "");
				SharedPreferencesUtil.writeSharedPreferencesString(baseActivity, "authfail", "isfirst"+account, "0");
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				bean = (ImageBean) msg.obj;
				//提交图片成功 生成图片界面ui imageFlag 当前点击图片的下标
				if(imageFlag<6){
					//公司照片
						list1_return.remove(imageFlag-1);
						list1_return.add(imageFlag-1, bean.getImgName());
						if(list1_return.size()-imageFlag==0){
							gridAdapter.setData(list1_return,false);
						}else{
							gridAdapter.setData(list1_return,true);
						}
						gridAdapter.setBaseUrl(bean.getBaseurl());
						gridAdapter.notifyDataSetChanged();
						IsCanClick(context,tv_edit_company, list1_return);
						//2 是当前不可编辑
						if(viplevel == 2)
							tv_edit_company.setVisibility(View.INVISIBLE);
				}else if(imageFlag<8){//6 7
					//身份证
					list2_return.remove(imageFlag-6);
					list2_return.add(imageFlag-6, bean.getImgName());
					if(list2_return.size()+5-imageFlag==0){
						gridAdapterIdCard.setData(list2_return,false);
					}else{
						gridAdapterIdCard.setData(list2_return,true);
					}
					gridAdapterIdCard.setbaseUrl(bean.getBaseurl());
					gridAdapterIdCard.notifyDataSetChanged();
					IsCanClick(context,tv_edit_idcard, list2_return);
					if(viplevel == 2)
						tv_edit_idcard.setVisibility(View.INVISIBLE);
				}else if(imageFlag<11){//8 9 10
					//营业执照
					list3_return.remove(imageFlag-8);
					list3_return.add(imageFlag-8, bean.getImgName());
					if(list3_return.size()+7-imageFlag==0){
						gridAdapterLicense.setData(list3_return,false);
					}else{
						gridAdapterLicense.setData(list3_return,true);
					}
					gridAdapterLicense.setBaseUrl(bean.getBaseurl());
					gridAdapterLicense.notifyDataSetChanged();
					IsCanClick(context,tv_edit_license, list3_return);
					if(viplevel == 3)
						tv_edit_license.setVisibility(View.INVISIBLE);
				}
				break; 
			case ConstantsUtil.HTTP_FAILE:
				if( msg.obj!=null){
				ErrorBean beans = (ErrorBean) msg.obj;
					ToastUtil.showToast(getReference(), beans.getInfo());
				}else{
					ToastUtil.showToast(getReference(), "数据异常，请稍后重试");
				}
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN:
				ErrorBean beanError = (ErrorBean) msg.obj;
				ToastUtil.showToast(getReference(), StringUtil.isEmpty(beanError.getInfo())?"数据错误,请稍后重试":beanError.getInfo());
				break;
			default:
				break;
			}
		}
	}
	//获取电子合同和初始化电话 认证状态等信息
	private void getcontract() {
		if(baseBean !=null && baseBean.getResult() !=null){
			regagreementurl = baseBean.getResult().getRegagreementurl();
			et_linkTel.setText(StringUtil.isEmpty(baseBean.getResult().getContactmobile())?"":baseBean.getResult().getContactmobile());
			shopbean = baseBean.getResult();
			setViewVisible();
		}
	}
	
	/*
	 * 判断当前用户是否
	 * 
	 * 0:未有特权 1:已经通过特权一 ,2:已经通过特权二, 3:已经认证成功
	 * **/
	void AuthUser(int codeStatue){
		switch (codeStatue) {
		case 0:
			step1.setVisibility(View.VISIBLE);
			step1_submit.setVisibility(View.INVISIBLE);
			break;
		case 1:
			step2.setVisibility(View.VISIBLE);
			step2_submit.setVisibility(View.VISIBLE);
			break;
		case 2:
			step3_submit.setVisibility(View.VISIBLE);
			
			break;
		case 3:
			tv_auth_all.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
	
	void setOneeable(){
		et_shopName.setEnabled(false);
		et_shopAdd.setEnabled(false);
		et_leadname.setEnabled(false);
		et_linkTel.setEnabled(false);
		et_cardid.setEnabled(false);
	}
	void setTwoeable(){
		grid_layout.setEnabled(false);
		grid_layout_idcard.setEnabled(false);
	}
	void setThreable(){
		grid_layout_license.setEnabled(false);
	}
	
//	@SuppressLint("NewApi") void setViewVisible(int viplevel,String authentication){
	@SuppressLint("NewApi") void setViewVisible(){
		//1 未申请 2 审核中 3 审核通过 4 审核失败
		//0 没有特权 1 2 3
//		shopbean.setAuthentication(authentication);
		if(viplevel == 0){
			step1.setVisibility(View.VISIBLE);
			step1_submit.setVisibility(View.VISIBLE);
			if(shopbean.getAuthentication().equals("2")){
				step1.setVisibility(View.INVISIBLE);
				step1_submit.setVisibility(View.INVISIBLE);
			}
			tv_edit_company.setVisibility(View.INVISIBLE);
			tv_edit_idcard.setVisibility(View.INVISIBLE);
			tv_edit_license.setVisibility(View.INVISIBLE);
			setpb(false, false,false,false,false,false,false);
		}else if(viplevel == 1){
			step1.setVisibility(View.VISIBLE);
			step1.setEnabled(false);
			step1.setText("已认证");
			setOneeable();
			step1.setBackground(getResources().getDrawable(R.drawable.shape_forget));
			step1.setTextColor(getResources().getColor(R.color.color_ffffff));
			step1_submit.setVisibility(View.INVISIBLE);
			step2_prewer.setVisibility(View.GONE);
			step2.setVisibility(View.VISIBLE);
			step2_submit.setVisibility(View.VISIBLE);
			if(shopbean.getAuthentication().equals("2")){
				step2.setVisibility(View.INVISIBLE);
				step2_submit.setVisibility(View.INVISIBLE);
			}
			setpb(true, true,false,false,false,false,false);
		}else if(viplevel == 2){
			step3_prewer.setVisibility(View.GONE);
			step1.setVisibility(View.VISIBLE);
			step1_submit.setVisibility(View.INVISIBLE);
			step1.setEnabled(false);
			step1.setText("已认证");
			step1.setBackground(getResources().getDrawable(R.drawable.shape_forget));
			step1.setTextColor(getResources().getColor(R.color.color_ffffff));
			step2.setVisibility(View.VISIBLE);
			step2_submit.setVisibility(View.INVISIBLE);
			step2.setEnabled(false);
			step2.setText("已认证");
			step2.setBackground(getResources().getDrawable(R.drawable.shape_forget));
			step2.setTextColor(getResources().getColor(R.color.color_ffffff));
			step3_submit.setVisibility(View.VISIBLE);
			if(shopbean.getAuthentication().equals("2")){
				step3_submit.setVisibility(View.INVISIBLE);
				tv_edit_license.setVisibility(View.INVISIBLE);
			}
			tv_edit_company.setVisibility(View.INVISIBLE);
			tv_edit_idcard.setVisibility(View.INVISIBLE);
			setpb(true, true,true,true,false,false,false);
			setOneeable();
			setTwoeable();
		}else if(viplevel == 3){
			tv_edit_company.setVisibility(View.INVISIBLE);
			tv_edit_idcard.setVisibility(View.INVISIBLE);
			tv_edit_license.setVisibility(View.INVISIBLE);
			tv_auth_all.setText("已完全认证");
			linear_button.setVisibility(View.VISIBLE);
			setpb(true, true,true,true,true,true,true);
			setOneeable();
			setTwoeable();
			setThreable();
		}
	}
	
	//绑定获取的数据 生成界面
	private void bindViewData() {
		if(baseBean!=null){
			shopbean = baseBean.getResult();
		if (shopbean != null) {
			modified = true;
			 //1 未申请 2 审核中 3 审核通过 4 审核失败
			if (shopbean.getAuthentication() != null) {
				/*
				 * 获取当前认证级别
				 * */
				viplevel = shopbean.getViplevel();
				statusAuth = shopbean.getAuthentication();
				GetTextInput();//获取输入框内容
				//1 未申请 2 审核中 3 审核通过 4 审核失败
				//0 没有特权 1 2 3
				setViewVisible();
//				setViewVisible(1,"3");
				linear_button.setVisibility(View.VISIBLE);
				if (shopbean.getAuthentication().equals("2")) {//审核中...
					ToastUtil.showToast(context,context.getResources().getString(R.string.commit_success_ing));
					spinner_provice.setVisibility(View.GONE);
					spinner_area.setVisibility(View.GONE);
					et_shopdetaileadd.setVisibility(View.GONE);
					arr1.setVisibility(View.GONE);
					arr2.setVisibility(View.GONE);
					et_shopAdd.setVisibility(View.VISIBLE);
					et_shopAdd.setText(shopbean.getProvincename()+shopbean.getCityname()+shopbean.getAddress());
					tv_auth_all.setVisibility(View.VISIBLE);
					tv_auth_all.setText("认证中...");
					tv_auth_all.setTextColor(getResources().getColor(R.color.color_41c646));
				} else if (shopbean.getAuthentication().equals("3")) {//审核通过
//					ToastUtil.showToast(context,context.getResources().getString(R.string.commit_success));
					spinner_provice.setVisibility(View.GONE);
					spinner_area.setVisibility(View.GONE);
					et_shopdetaileadd.setVisibility(View.GONE);
					arr1.setVisibility(View.GONE);
					arr2.setVisibility(View.GONE);
					et_shopAdd.setVisibility(View.VISIBLE);
				} else if (shopbean.getAuthentication().equals("4")) {//认证失败
					ToastUtil.showToast(context,context.getResources().getString(R.string.commit_shop_fail));
					linear_button.setVisibility(View.VISIBLE);
				}
				regagreementurl = shopbean.getRegagreementurl();
				initDate(shopbean);
				CanEditPhoto();
			 }
			}
		}
	}
	
	/*
	 * 设置认证进度条颜色
	 * 
	 * 
	 * **/
	
	void setpb(boolean iv_one,boolean le_one,boolean iv_two,boolean le_two,boolean iv_three,boolean le_three,boolean iv_four
			){
		if(iv_one)
			iv_round_one.setBackgroundResource(R.drawable.green_point);
		else
			iv_round_one.setBackgroundResource(R.drawable.white_point);
		if(iv_two){
			iv_round_two.setBackgroundResource(R.drawable.green_point);
		}else{
			iv_round_two.setBackgroundResource(R.drawable.white_point);
		}
		if(iv_three)
			iv_round_three.setBackgroundResource(R.drawable.green_point);
		else
			iv_round_three.setBackgroundResource(R.drawable.white_point);
		if(iv_four)
			iv_round_four.setBackgroundResource(R.drawable.green_point);
		else
			iv_round_four.setBackgroundResource(R.drawable.white_point);
		
		if(le_one){
			line_one.setBackgroundResource(R.drawable.register_green_line);
			line_two_next.setBackgroundResource(R.drawable.register_green_line);
		}else{
			line_one.setBackgroundResource(R.drawable.register_line);
			line_two_next.setBackgroundResource(R.drawable.register_line);
		}
		
		if(le_two){
			line_two.setBackgroundResource(R.drawable.register_green_line);
			line_three_next.setBackgroundResource(R.drawable.register_green_line);
		}else{
			line_two.setBackgroundResource(R.drawable.register_line);
			line_three_next.setBackgroundResource(R.drawable.register_line);
		}
		
		if(le_three)
			line_three.setBackgroundResource(R.drawable.register_green_line);
//		line_three.setBackgroundResource(R.drawable.register_green_line);
		else
			line_three.setBackgroundResource(R.drawable.register_line);
		
	}
	
	
	/**
	 * 填充数据
	 * 
	 * */
	private void initDate(ShopBean shopbean) {
		uerId = SharedPreferencesUtil.getSharedPreferencesString(context, "loginId", "loginId", "");
		if(shopbean!=null)
		SharedPreferencesUtil.saveObject(context, shopbean, "AgentAuth"+uerId);
		ShopBean shopbeans = (ShopBean) SharedPreferencesUtil.getObject(context, "AgentAuth"+uerId);
		if(shopbean==null){
			setOldDate(shopbeans);
		}else{
			setOldDate(shopbean);
		}
		//获取回填的数据
		GetReurnDate();
	}
	
	/*
	 *  回填数据
	 * 
	 *  
	 * */
	private void setOldDate(ShopBean shopbean){
		//审核不通过
		if(shopbean.getAuthentication().equals("4")){
			reason_tv.setText("内容:"+shopbean.getRemark());
			tv_reason_time.setText("时间:"+shopbean.getUpdatetime());
			reason_lv.setVisibility(View.VISIBLE);
		}else{
			reason_lv.setVisibility(View.GONE);
		}
		et_shopName.setText(shopbean.getName());
		et_shopAdd.setText(shopbean.getProvincename()+shopbean.getCityname()+shopbean.getAddress());
		et_leadname.setText(shopbean.getLegal());
		et_linkTel.setText(shopbean.getContactmobile());
		et_cardid.setText(shopbean.getCardid());
		et_blicense.setText(shopbean.getBlicense());
		baseUrl = shopbean.getBaseurl();
		DisplayImage(shopbean.getPic1(),1);//环境照片
		DisplayImage(shopbean.getPic2(),2);//身份证照片
		DisplayImage(shopbean.getPic3(),3);//营业执照照片
		if(shopbean.getProvincename()!=null && shopbean.getCityname()!=null && shopbean.getAddress()!=null){
			et_shopdetaileadd.setText(shopbean.getProvincename()+shopbean.getCityname()+shopbean.getAddress());
		}
		initdataspinner();
	}
	
	//回填地址
	private void initdataspinner() {
		if(spinner_provice !=null && spinner_area!=null){
			if(addressbean!=null){
				provences = addressbean.getList();
				provicemadapter.setNewData( provences);
			for(int i=0;i<provences.size();i++){
				if(shopbean.getProvince()!=null){
				if(provences.get(i).getProvince().getId()==Integer.parseInt(shopbean.getProvince())){
					  spinner_provice.setSelection(i, true);
					  list =   provences.get(i).getProvince().getCity();
					  break;
					}
				}
			 }
			madapterarea.setNewData(list);
			for(int i=0;i<list.size();i++){
				if(shopbean.getCity()!=null){
					if(list.get(i).getId() == Integer.parseInt(shopbean.getCity())){
						spinner_area.setSelection(i, true);
						break;
					 }
					}
				}
			}
		}
		
	}

	/*****************编辑按钮是否可以点击*************/
	/*
	 * 动态回填图片视图
	 * 
	 * */
	private void DisplayImage(String imagereturn,int id) {
		if(imagereturn!=null){
			//多张图片处理方式
			if(imagereturn.contains(",")){
				String imageArr[] = imagereturn.split(",");
					if(id==1){//环境照片处理
						for(int i=0;i<imageArr.length;i++){
							if(!list1_return.contains(imageArr[i]))
							list1_return.add(imageArr[i]);
						}
						gridAdapter = new AdapterCompany(context,isRegister, list1_return,baseUrl,tv_edit_company,new ImageCallBack() {
							@Override
							public void setImage(ImageView image,Bitmap bitmap) {
								// TODO Auto-generated method stub
								image.setImageBitmap(bitmap);
							}
						},role,viplevel,statusAuth);
						 grid_layout.setAdapter(gridAdapter);
						 IsCanClick(context,tv_edit_company, list1_return);//是否可以点击
						 if(viplevel == 2){
							 tv_edit_company.setVisibility(View.INVISIBLE);
						 }
					}else if(id==2){//身份证照片处理 6 7
						for(int i=0;i<imageArr.length;i++){
							if(!list2_return.contains(imageArr[i]))
								list2_return.add(imageArr[i]);
						}
						gridAdapterIdCard = new AdapterIdCard(context,isRegister, list2_return,baseUrl,tv_edit_idcard,new ImageCallBack() {
							
							@Override
							public void setImage(ImageView image,Bitmap bitmap) {
								// TODO Auto-generated method stub
								image.setImageBitmap(bitmap);
							}
						},role,viplevel,statusAuth);
						grid_layout_idcard.setAdapter(gridAdapterIdCard);
						IsCanClick(context,tv_edit_idcard, list2_return);//是否可以点击
						if(viplevel == 2){
							tv_edit_idcard.setVisibility(View.INVISIBLE);
						 }
					}else if(id==3){//营业执照处理 8 9 10
						for(int i=0;i<imageArr.length;i++){
							if(!list3_return.contains(imageArr[i]))
								list3_return.add(imageArr[i]);
						}
						gridAdapterLicense = new AdapterLicnese(context,isRegister, list3_return,baseUrl,tv_edit_license,new ImageCallBack() {
							@Override
							public void setImage(ImageView image,Bitmap bitmap) {
								// TODO Auto-generated method stub
								image.setImageBitmap(bitmap);
							}
						},role,viplevel,statusAuth);
						grid_layout_license.setAdapter(gridAdapterLicense);
						IsCanClick(context,tv_edit_license, list3_return);//是否可以点击
						if(viplevel == 3){
							tv_edit_license.setVisibility(View.INVISIBLE);
						 }
					}
			}else{
				/*************单张图片处理方式**********/
				if(id==1){//环境照片
						if(!list1_return.contains(imagereturn) && !imagereturn.equals(""))
							list1_return.add(imagereturn);
					gridAdapter = new AdapterCompany(context,isRegister, list1_return, baseUrl,tv_edit_company,new ImageCallBack() {
						@Override
						public void setImage(ImageView image,Bitmap bitmap) {
							// TODO Auto-generated method stub
							image.setImageBitmap(bitmap);
						}
					},role,viplevel,statusAuth);
					grid_layout.setAdapter(gridAdapter);
					IsCanClick(context,tv_edit_company, list1_return);//是否可以点击
					if(viplevel == 2){
						tv_edit_company.setVisibility(View.INVISIBLE);
					 }
				}else if(id==2){//身份证
						if(!list2_return.contains(imagereturn)&& !imagereturn.equals(""))
							list2_return.add(imagereturn);
						gridAdapterIdCard = new AdapterIdCard(context,isRegister,list2_return, baseUrl,tv_edit_idcard,new ImageCallBack() {
							@Override
							public void setImage(ImageView image,Bitmap bitmap) {
								image.setImageBitmap(bitmap);
							}
						},role,viplevel,statusAuth);
						grid_layout_idcard.setAdapter(gridAdapterIdCard);
					IsCanClick(context,tv_edit_idcard, list2_return);//是否可以点击
					if(viplevel == 2){
						tv_edit_idcard.setVisibility(View.INVISIBLE);
					 }
				}else if(id==3){//营业执照
						if(!list3_return.contains(imagereturn)&& !imagereturn.equals(""))
							list3_return.add(imagereturn);
						gridAdapterLicense = new AdapterLicnese(context,isRegister, list3_return, baseUrl,tv_edit_license,new ImageCallBack() {
							@Override
							public void setImage(ImageView image,Bitmap bitmap) {
								// TODO Auto-generated method stub
								image.setImageBitmap(bitmap);
							}
						},role,viplevel,statusAuth);
						grid_layout_license.setAdapter(gridAdapterLicense);
					IsCanClick(context,tv_edit_license, list3_return);//是否可以点击
					if(viplevel == 3){
						tv_edit_license.setVisibility(View.INVISIBLE);
					 }
		}
			}
		}
	}
	private void GetReurnDate() {
		simpleName = et_shopName.getText().toString().trim();
		simpleAddress = et_shopAdd.getText().toString().trim();
		simpleLegal = et_leadname.getText().toString().trim();
//		simpleContact = et_linkName.getText().toString().trim();
		simplemobile = et_linkTel.getText().toString().trim();
		simpleCardid = et_cardid.getText().toString().trim();
		simpleBlicense = et_blicense.getText().toString().trim();
		st_shopdetaileadd = et_shopdetaileadd.getText().toString().trim();
		simplepic1 = getPic(list1_return);
		simplepic2 = getPic(list2_return);
		simplepic3 = getPic(list3_return);
		simpleUrl = shopbean.getBaseurl();
		simpleAuthentication = shopbean.getAuthentication();
		
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
		return "认证信息";
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

	
	//点击上传图片监听
	public class MyClassClick implements OnClickListener{
		ImageView iv;
		int tag;
		public ImageCallBack imageCallBacks;
		public Context contexts;
		public MyClassClick(int tag,ImageView iv,Context context,ImageCallBack imageCallBack){
			this.tag = tag;
			this.imageCallBacks = imageCallBack;
			this.iv = iv;
			this.contexts = context;
			setImageCallBack(iv,imageCallBacks);
			onClick(iv);
		}
		@Override
		public void onClick(View v) {
			imageFlag = tag;
			setImageCallBack((ImageView)v,imageCallBacks);
			showAtWindowTop(contexts, iv, R.layout.forum_release_pic);
			if(imm!=null)
			imm.hideSoftInputFromWindow(root_view.getWindowToken(), 0); //强制隐藏键盘  
		}
	}
	
	private String getPic(List<String> list){
		String pic = "";
		picSize = list.size(); 
		if(list.contains(""))
			picSize = picSize-1;
		for(int i=0;i<picSize;i++){
			if(i==picSize-1){
				pic+=list.get(i);
			}else{
				pic+=list.get(i)+",";
			}
		}
		return pic;
	}
	@SuppressLint("NewApi") @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		/*case R.id.commit_verify:
			if(baseBean !=null && baseBean.getResult()!=null){
				if(StringUtil.isEmpty(baseBean.getResult().getName()))
				    map.put("submitType","首次提交");
				else
					map.put("submitType","重新提交");
			
			}
			if(isRegister){
			    map.put("entry","注册页");
			}else{
				map.put("entry","我的页");
			}
			MobclickAgent.onEvent(baseActivity, "C_AUTH_8",map); 
			GetTextInput();//获取输入框内容
			if (ToastString(st_name, R.string.shop_null)) {
				return;
			}
			if (ToastString(st_leadname, R.string.name_out)) {
				return;
			}
			if (ToastString(st_linkTel, R.string.tel_out)) {
				return;
			}
			if (ToastString(st_cardid, R.string.idcard_out)) {
				return;
			}
			if (ToastString(st_blicense, R.string.license_out)) {
				return;
			}
			if (ToastString(st_shopdetaileadd, R.string.add_out_null)) {
				return;
			}
			if(st_leadname.length()<2){
				ToastUtil.showToast(context, R.string.faren_text);
				return;
			}
			if(st_shopdetaileadd.length()<2){
				ToastUtil.showToast(context, R.string.add_text);
				return;
			}
			if(st_linkTel.length()<11){
				ToastUtil.showToast(context, R.string.renxiren_tel);
				return;
			}
			if(st_cardid.length()!=18 && st_cardid.length()!=15){
				ToastUtil.showToast(context, R.string.shenfenzheng_text);
				return;
			}
			
			codeId = 2;
			GetPicDate();
			AuthTwo();
			if (ToastString(pic3, R.string.shop_null3)) {
				return;
			}
			if(!cb_deal.isChecked()){
				ToastUtil.showToast(context, "注册需要同意用户协议");
				return;
			}
			if(picSize<2){
				ToastUtil.showToast(context, "法人身份证照片请上传正反面噢");
				return;
			}
			
			//提交认证信息
			SubmitInfo();
			break;*/
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
		case R.id.base_activity_title_right_righttv:
			if(isRegister){
			Intent intent = new Intent(context,MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			StartActivityUtil.startActivity((Activity) context, intent);
			finish();
			}
			break;
		case R.id.base_activity_title_backicon:
			map.put("commitauthback","");
			MobclickAgent.onEvent(baseActivity, "00137", map); 
			backPress();
			break;
		case R.id.linear_back:
			map.put("commitauthback","");
			MobclickAgent.onEvent(baseActivity, "00137", map); 
			backPress();
			break;
		case R.id.tv_edit_company:
			if(click1){
				gridAdapter.isEdit(click1,new ClickCallBack() {
					@Override
					public void setClick(boolean click) {
						click1 = click;
					}
				});
				click1 = false;
				//友盟统计
				MobclickAgent.onEvent(AuthActivity.this, "C_AUTH_4");
			}else{
				gridAdapter.isEdit(click1,new ClickCallBack() {
					@Override
					public void setClick(boolean click) {
						// TODO Auto-generated method stub
						click1 = click;
					}
				});
				click1 = true;
			}
			gridAdapter.notifyDataSetChanged();
			break;
		case R.id.tv_edit_idcard:
			if(click2){
				gridAdapterIdCard.isEdit(click2, new ClickCallBacks() {
					@Override
					public void setClick(boolean click) {
						click2 = click;
					}
				});
				click2 = false;
				//友盟统计
				MobclickAgent.onEvent(AuthActivity.this, "C_AUTH_5");
			}else{
				gridAdapterIdCard.isEdit(click2, new ClickCallBacks() {
					@Override
					public void setClick(boolean click) {
						click2 = click;
					}
				});
				click2 = true;
			}
			gridAdapterIdCard.notifyDataSetChanged();
			break;
		case R.id.tv_edit_license:
			if(click3){
				gridAdapterLicense.isEdit(click3, new ClickCallBackss() {
					@Override
					public void setClick(boolean click) {
						click3 = click;
					}
				});
				click3 = false;
				//友盟统计
				MobclickAgent.onEvent(AuthActivity.this, "C_AUTH_6");
			}else{
				gridAdapterLicense.isEdit(click3, new ClickCallBackss() {
					@Override
					public void setClick(boolean click) {
						click3 = click;
					}
				});
				click3 = true;
			}
			gridAdapterLicense.notifyDataSetChanged();
			break;
		case R.id.step1:
			//继续认证
			if(AuthOne()){
			linear_step1.setEnabled(false);
			step1.setText("已锁定");
			step1.setEnabled(false);
			step1.setBackground(getResources().getDrawable(R.drawable.shape_forget));
			step1.setTextColor(getResources().getColor(R.color.color_ffffff));
			step1_submit.setVisibility(View.INVISIBLE);
			step2.setVisibility(View.VISIBLE);
			step2_submit.setVisibility(View.VISIBLE);
			step2_prewer.setVisibility(View.VISIBLE);
			if(list1_return.size()>0){
				tv_edit_company.setEnabled(true);
				tv_edit_company.setTextColor(getResources().getColor(R.color.color_af2942));
			}else{
				tv_edit_company.setEnabled(false);
				tv_edit_company.setTextColor(getResources().getColor(R.color.gray));
			}
			if(list2_return.size()>0){
				tv_edit_idcard.setEnabled(true);
				tv_edit_idcard.setTextColor(getResources().getColor(R.color.color_af2942));
			}else{
				tv_edit_idcard.setEnabled(false);
				tv_edit_idcard.setTextColor(getResources().getColor(R.color.gray));
			}
			}
			break;
			//继续开同
		case R.id.step2:
			if(AuthTwo()){
			linear_step2.setEnabled(false);
			step2.setText("已锁定");
			step2.setBackground(getResources().getDrawable(R.drawable.shape_forget));
			step2.setTextColor(getResources().getColor(R.color.color_ffffff));
			step2.setEnabled(false);
			step2_submit.setVisibility(View.INVISIBLE);
			step2_prewer.setVisibility(View.INVISIBLE);
			step3_submit.setVisibility(View.VISIBLE);
			step3_prewer.setVisibility(View.VISIBLE);
			setTvEable(false, context.getResources().getColor(R.color.gray));
			if(list3_return.size()>0){
				tv_edit_license.setEnabled(true);
				tv_edit_license.setTextColor(getResources().getColor(R.color.color_af2942));
			}else{
				tv_edit_license.setEnabled(false);
				tv_edit_license.setTextColor(getResources().getColor(R.color.gray));
			}
			}
			break;
			//立即认证
		case R.id.step1_submit:
			indexPress = 1;
			SubmitRegister();
			break;
		case R.id.step2_submit:
			indexPress = 2;
			SubmitRegister();
			break;
		case R.id.step3_submit:
			indexPress = 3;
			SubmitRegister();
			break;
			//上一步
		case R.id.step2_prewer:
			step2_submit.setVisibility(View.INVISIBLE);
			step2.setVisibility(View.INVISIBLE);
			step2_prewer.setVisibility(View.INVISIBLE);
			step1_submit.setVisibility(View.VISIBLE);
			step1.setBackground(getResources().getDrawable(R.drawable.rechang_red_bg));
			step1.setTextColor(getResources().getColor(R.color.color_af2942));
			step1.setText("继续开通");
			step1.setEnabled(true);
			step1.setVisibility(View.VISIBLE);
			linear_step1.setEnabled(true);
			setTvEable(false, context.getResources().getColor(R.color.gray));
			break;
		case R.id.step3_prewer:
			step3_submit.setVisibility(View.INVISIBLE);
			step2.setText("继续开通");
			step2.setEnabled(true);
			step3_prewer.setVisibility(View.INVISIBLE);
			step2_prewer.setVisibility(View.VISIBLE);
			step2_submit.setVisibility(View.VISIBLE);
			step2.setVisibility(View.VISIBLE);
			linear_step2.setEnabled(true);
			setTvEable(true, context.getResources().getColor(R.color.color_af2942));
			step2.setBackground(getResources().getDrawable(R.drawable.rechang_red_bg));
			step2.setTextColor(getResources().getColor(R.color.color_af2942));
			tv_edit_license.setEnabled(false);
			tv_edit_license.setTextColor(context.getResources().getColor(R.color.gray));
			if(viplevel==1)
				step2_prewer.setVisibility(View.GONE);
			break;
		case R.id.linear_cb:
			if(cb_deal.isChecked())
				cb_deal.setChecked(false);
			else
				cb_deal.setChecked(true);
			break;
		}
	}
	
	void setTvEable(boolean isclick,int color){
		tv_edit_company.setEnabled(isclick);
		tv_edit_company.setTextColor(color);
		tv_edit_idcard.setEnabled(isclick);
		tv_edit_idcard.setTextColor(color);
	}
	
	private void SubmitInfo() {
		ApiHttpCilent.getInstance(context).commitUserInfo(context, st_name,
				st_shopAdd, st_leadname, st_linkTel,
				st_cardid, st_blicense, pic1, pic2, pic3,proviceid,cityid, new MyCallBack());
	}
	
	void backPress(){
		//获取当前填写的数据
		GetTextInput();
		GetPicDate();
		if(isEdite(st_name, simpleName)){
			return;
		}
		if(isEdite(st_shopAdd, simpleAddress)){
			return;
		}
		if(isEdite(st_leadname, simpleLegal)){
			return;
		}
		if(isEdite(st_linkTel, simplemobile)){
			return;
		}
		if(isEdite(st_cardid, simpleCardid)){
			return;
		}
		if(isEdite(st_blicense, simpleBlicense)){
			return;
		}
		if(isEdite(pic1, simplepic1)){
			return;
		}
		if(isEdite(pic2, simplepic2)){
			return;
		}
		if(isEdite(pic3, simplepic3)){
			return;
		}
		onBackPressed();
	}
	//0 1 2
	/*
	 * 提交特权
	 * **/

	void SubmitRegister(){
		//回调返回的标志 说明是提交成功
		codeId = 2;
		if(viplevel==0){
			if(indexPress ==1){
			if(!AuthOne())
				return;
			}else if(indexPress ==2){
				if(!AuthOne())
					return;
				if(!AuthTwo())
					return;
			}else if(indexPress ==3){
				if(!AuthOne())
					return;
				if(!AuthTwo())
					return;
				if(!AuthThree())
					return;
			}
		}else if(viplevel == 1){
			if(indexPress ==2){
			if(!AuthTwo())
				return;
			}else if(indexPress ==3){
				if(!AuthTwo())
					return;
				if(!AuthThree())
					return;
			}
			st_name = ""; 
			st_shopAdd = ""; 
			st_leadname = ""; 
			st_cardid = ""; 
			st_linkTel = ""; 
		}else if(viplevel == 2){
			if(indexPress ==3){
			if(!AuthThree())
				return;
			}
			st_shopAdd = ""; 
			st_leadname = ""; 
			st_linkTel = ""; 
			st_cardid = ""; 
			st_name = ""; 
			pic1 = ""; 
			pic2 = ""; 
		}
		SubmitInfo();
	}

	private boolean AuthThree() {
		GetTextInput();//获取输入框内容
		GetPicDate();//获取图像对象
		/*
		 * 认证三 验证
		 * */
		if (ToastString(pic3, R.string.shop_null3)) {
			return false;
		}
		if (ToastString(st_blicense, R.string.license_out)) {
			SetHint(et_blicense, "营业执照号不能为空");
			return false;
		}
		if(!cb_deal.isChecked()){
			ToastUtil.showToast(context, "认证需要同意用户协议");
			return false;
		}
		return true;
	}

	private boolean AuthTwo() {
		GetTextInput();//获取输入框内容
		GetPicDate();//获取图像对象
		/*
		 * 认证二 验证
		 * */
		if (ToastString(pic1, R.string.shop_null1)) {
			return false;
		}
		if (ToastString(pic2, R.string.shop_null2)) {
			return false;
		}
		//验证法人身份证是否是2张
		GetPicDate();
		if(picSize<2){
			ToastUtil.showToast(context, "法人身份证照片请上传正反面噢");
			return false;
		}
		if(!cb_deal.isChecked()){
			ToastUtil.showToast(context, "认证需要同意用户协议");
			return false;
		}
		return true;
	}

	
	void SetHint(EditText et,String hint){
		et.setHint(hint);
		et.setHintTextColor(getResources().getColor(R.color.color_af2942));
	}
	private boolean AuthOne() {
		GetTextInput();//获取输入框内容
		/*
		 * 认证一 验证
		 * */
			//店铺名称
			if (ToastString(st_name, R.string.shop_null)) {
				SetHint(et_shopName, "公司名称不能为空");
				return false;
			}
			//店铺地址
			if (ToastString(st_shopdetaileadd, R.string.add_out_null)) {
				SetHint(et_shopAdd, "公司地址和详细地址不能为空");
				return false;
			}
			//法人姓名
			if (ToastString(st_leadname, R.string.name_out)) {
				SetHint(et_leadname, "法人姓名不能为空");
				return false;
			}
			//身份证号
			if (ToastString(st_cardid, R.string.idcard_out)) {
				SetHint(et_cardid, "身份证号不能为空");
				return false;
			}
			//联系电话
			if (ToastString(st_linkTel, R.string.tel_out)) {
				return false;
			}
			
			if(st_leadname.length()<2){
				ToastUtil.showToast(context, R.string.faren_text);
				return false;
			}
			if(st_shopdetaileadd.length()<2){
				ToastUtil.showToast(context, R.string.add_text);
				return false;
			}
			if(st_linkTel.length()<11){
				ToastUtil.showToast(context, R.string.renxiren_tel);
				return false;
			}
			if(st_cardid.length()!=18 && st_cardid.length()!=15){
				ToastUtil.showToast(context, R.string.shenfenzheng_text);
				return false;
			}
			if(!cb_deal.isChecked()){
				ToastUtil.showToast(context, "认证需要同意用户协议");
				return false;
			}
			return true;
	}
	
	
	/*****************编辑按钮是否可以点击*************/
	public void IsCanClick(Context context,TextView tv,List<String> piclist){
		if(piclist.size()>0){
			if(piclist.size()==1 && piclist.get(0).equals("")){
				tv.setTextColor(context.getResources().getColor(R.color.gray));
				tv.setEnabled(false);
			}else{
				tv.setTextColor(context.getResources().getColor(R.color.title_bg));
				tv.setEnabled(true);
				tv.setVisibility(View.VISIBLE);
				if(!StringUtil.isEmpty(statusAuth)){
				if(!statusAuth.equals("2") && !statusAuth.equals("3"))
				tv.setVisibility(View.VISIBLE);
				}
			}
		}else{
			tv.setTextColor(context.getResources().getColor(R.color.gray));
			tv.setEnabled(false);
		}
	}

	private boolean isEdite(String currtname,String beforeName){
//		if(!currtname.equals(beforeName)){
//			ShowDialog("是否保存修改?", "温馨提示");			
//			return true;
//		}
		return false;
	}
	//提示框是否保存修改
	public void ShowDialog(String notice, String title) {
		CommonDialog.makeText( AuthActivity.this, title, notice, new OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
					String tel) {
				// TODO Auto-generated method stub
				if (OnDialogListener.LEFT == result ) {
					SaveDate();
					onBackPressed();
					CommonDialog.Dissmess();
				} else {
					onBackPressed();
					CommonDialog.Dissmess();
				}
			}

		}).showDialog();
	}
	
	/*
	 * 保存本地编辑的数据到本地
	 * */
	private void SaveDate() {
		ShopBean savebean = new ShopBean();
		savebean.setName(st_name);
		savebean.setAddress(st_shopAdd);
		savebean.setLegal(st_leadname);
		savebean.setContactmobile(st_linkTel);
		savebean.setCardid(st_cardid);
		savebean.setBlicense(st_blicense);
		savebean.setBaseurl(simpleUrl);
		savebean.setPic1(pic1);
		savebean.setPic2(pic2);
		savebean.setPic3(pic3);
		savebean.setAuthentication(simpleAuthentication);
		SharedPreferencesUtil.saveObject(context, savebean, "AgentAuth"+uerId);
	}
	//获取编辑后图片数据
	private void GetPicDate() {
			pic1 = getPic(list1_return);
			pic2 = getPic(list2_return);
			pic3 = getPic(list3_return);
			if(list2_return.contains("")){
				picSize = list2_return.size()-1;
			}else{
				picSize = list2_return.size();
			}
	}
	/*
	 * 获取数据框内容
	 * **/
	private void GetTextInput() {
		st_name = et_shopName.getText().toString().trim();
		st_leadname = et_leadname.getText().toString().trim();
		st_linkTel = et_linkTel.getText().toString().trim();
		st_cardid = et_cardid.getText().toString().trim();
		st_shopAdd = et_shopdetaileadd.getText().toString().trim();
		st_shopdetaileadd = et_shopdetaileadd.getText().toString().trim();
		st_blicense = et_blicense.getText().toString().trim();
	}

	//处理字段最后一位是“，”
	private String RetureString(String imageUrl) {
		if(imageUrl.contains(",")){
		if(imageUrl.lastIndexOf(",")==imageUrl.length()-1){
			imageUrl = imageUrl.substring(0, imageUrl.length()-1);
			}
		}else{
			return imageUrl;
		}
		return imageUrl;
	}

	private boolean ToastString(String name, int id) {
		if (name == null || name.equals("")) {
			ToastUtil.showToast(context, getResources().getString(id));
			return true;
		}
		return false;
	}

	private void Dismess() {
		((Activity) context).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	//图片上传成功事件
	class MyImageCallBack extends BaseJsonHttpResponseHandler<ImageBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ImageBaseBean arg4) {
			Dismess();
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ImageBaseBean arg3) {
			Dismess();
		}
		
		@Override
		protected ImageBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dismess();
			Gson gson = new Gson();
			ImageBaseBean baseBean = gson.fromJson(response, ImageBaseBean.class);
			Message message = Message.obtain();
			if("1".equals(baseBean.getStatus()) ){
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;// 提交图片成功
				message.obj = baseBean.getResult();
			}else if ("0".equals(baseBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;
				message.obj = baseBean.getResult();
			}
			handler.sendMessage(message);
			return baseBean;
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
	private void showAtWindowTop(Context activity, View position, int layoutId) {

		View mPopMenuView = null;
		myPhoto = (Activity) activity;
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
		mPopupWindow.setFocusable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.showAtLocation(position, Gravity.BOTTOM, 0, 0);
		setCheckPhotoListener();

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
		Intent i = new Intent(Intent.ACTION_PICK,
				Media.EXTERNAL_CONTENT_URI);// 调用android的图库
		myPhoto.startActivityForResult(i, PHOTO);
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
	@SuppressLint("SimpleDateFormat") public void startCamera() {
		 String sdStatus = Environment.getExternalStorageState();
		 if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			 ToastUtil.showToast(context, "未检测到SD卡,请检查");
			 return;
		 }
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 SimpleDateFormat timeStampFormat = new SimpleDateFormat(
		 "yyyy_MM_dd_HH_mm_ss");
		 String filename = timeStampFormat.format(new Date())+ ".jpg";
		 ContentValues values = new ContentValues();
		 values.put(Media.TITLE, filename);

		 photoUri = myPhoto.getContentResolver().insert(
		 Media.EXTERNAL_CONTENT_URI, values);

		 intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);


		 myPhoto.startActivityForResult(intent, CAMERA);
	}

	 @SuppressLint("NewApi") public int getBitmapSize(Bitmap bitmap){
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
		        return bitmap.getAllocationByteCount();
		    }
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
		        return bitmap.getByteCount();
		    }
		    return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
		}
	private  long getFileSize(File file) throws Exception {
		  long size = 0;
		  if (file.exists()) {
		   FileInputStream fis = null;
		   try {
			     fis = new FileInputStream(file);
			     size = fis.available();
			   } finally {
			     if (fis != null) {
			    	 fis.close();
			     }
			   }
		  } else {
		   file.createNewFile();
		  }
		  return size;
		 }
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case CAMERA:// 照相完成点击确定
				Uri uri = null;
				 if (data != null && data.getData() != null) {
					 uri = data.getData();
					 }
					 // 一些机型无法从getData中获取uri，则需手动指定拍照后存储照片的Uri
					 if (uri == null) {
					 if (photoUri != null) {
						 uri = photoUri;
					 	}
					 }
//					 content://media/external/images/media/22831
					try {
						 String imagepath = ImageUploadUtil.getAbsoluteImagePath(uri,baseActivity);
						 File thumbnailFile = ImageUploadUtil.getPath(imagepath);
						 newBitmap = ImageUploadUtil.newBitmap;
						 codeId = 3;
						 ApiHttpCilent.getInstance(context).UploadImage(context, thumbnailFile, new MyImageCallBack());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
//		            try {
					
//						System.out.println(getFileSize(thumbnailFile)/1024+"大小");
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//		            ivcallbacks.setImage(ivs,newBitmap);
		        
				break;
			case PHOTO:
				Uri uris = data.getData(); 
				Cursor cursor = context.getContentResolver().query(uris, null, 
				null, null, null); 
				if(cursor!=null){
				cursor.moveToFirst(); 
//				String imgNo = cursor.getString(0); // 图片编号 
				imgPath = cursor.getString(1); // 图片文件路径 
//				String imgSize = cursor.getString(2); // 图片大小 3541161
//				String imgName = cursor.getString(3); // 图片文件名 
				cursor.close(); 
				}else{
					 if (uris.getScheme().compareTo("file")==0)         //file:///开头的uri
					   {
						 imgPath = uris.toString();
						 imgPath = uris.toString().replace("file://", "");
//						 content://media/external/images/media/22831
					//替换file://
//					      if(!imgPath.startsWith("/mnt")){
//					//加上"/mnt"头
//					    	  imgPath += "/mnt"; 
//					   } 
					   }
				}
				File imagepathUri = ImageUploadUtil.getPath(imgPath);
				newBitmap = ImageUploadUtil.newBitmap;
//				ivcallbacks.setImage(ivs,newBitmap);
//				 try {
//						System.out.println(getFileSize(imagepathUri)/1024+"大小");
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
				codeId = 3;
				ApiHttpCilent.getInstance(context).UploadImage(context, imagepathUri, new MyImageCallBack());
				break;
			}
		}
	}
	
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onPageStart("PG_AUTH_SUB");
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPageEnd("PG_AUTH_SUB");
		    MobclickAgent.onPause(this);
		}
	
		public void setImageCallBack(ImageView iv,ImageCallBack callback){
			this.ivs = iv;
			this.ivcallbacks = callback;
		}
		public interface ImageCallBack{
			void setImage(ImageView image, Bitmap bitmap);
		}
}