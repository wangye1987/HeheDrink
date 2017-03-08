package com.heheys.ec.controller.activity;/*package com.heheys.ec.controller.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.application.MyApplication.LoginCallBack;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.BitmapUtil;
import com.heheys.ec.lib.utils.ToastUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.ImageBaseBean;
import com.heheys.ec.model.dataBean.ImageBaseBean.ImageBean;
import com.heheys.ec.model.dataBean.ShopBaseBean;
import com.heheys.ec.model.dataBean.ShopBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

*//**
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-23 下午2:54:16 类说明 认证店铺
 * @param 商品认证界面(已经废弃)
 *//*
public class AgentAuthActivity extends BaseActivity {

	private Context context;
	private MyHandler handler = new MyHandler(AgentAuthActivity.this);
	private EditText et_shopName;
	private EditText et_shopAdd;
	private EditText et_leadname;
	private EditText et_linkName;
	private EditText et_linkTel;
	private EditText et_cardid;
	private EditText et_blicense;
	private ShopBean shopbean = new ShopBean();
	private ImageView iv_shop1;
	private ImageView iv_shop2;
	private ImageView iv_shop3;
	private ImageView iv_shop4;
	private ImageView iv_shop5;
	private ImageView iv_idcard1;
	private ImageView iv_idcard2;
	private ImageView iv_idcard3;
	private ImageView iv_license1;
	private ImageView iv_license2;
	private ImageView iv_zhuzhi;
	private ImageView iv_shuiwu;
	private int imageId;
	private int codeId;
	private Button commit_verify;
	private TextView tvPhoto;
	private TextView tvCamera;
	private TextView tvCancel;
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
	private Uri photoUri;
	private Bitmap bitmaps;
	private LinearLayout linear_one;
	private LinearLayout linear_five;
	private LinearLayout linear_idcard;
	private LinearLayout linear_license;
	private ImageView ivback;
	private ImageCallBack callback;
	private int temple = 0;
	private Bitmap newBitmap;
	String pic1 = "";
	String pic2 = "";
	String pic3 = "";
	String pic4 = "";
	String pic5 = "";
	String pic4_return = "";//返回回来的数据
	String pic5_return = "";
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	List<String> list3 = new ArrayList<String>();
	//返回回来的数据集合
	List<String> list1_return = new ArrayList<String>();
	List<String> list2_return = new ArrayList<String>();
	List<String> list3_return = new ArrayList<String>();
	private int imageFlag;//判断跳转
	private boolean modified;
	private boolean isRegister;
	private String imgPath;
	private int picSize;
	private String st_name;
	private String st_shopAdd;
	private String st_leadname;
	private String st_linkName;
	private String st_linkTel;
	private String st_cardid;
	private String st_blicense;
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
	private String simplepic4;
	private String simplepic5;
	private String simpleUrl;
	private String simpleAuthentication;
	private TextView tv_edit_company;
	private TextView tv_edit_idcard;
	private TextView tv_edit_license;
	private TextView tv_edit_zhuzhi;
	private TextView tv_edit_shuwu;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.agent_auth);
		initView();
		HttpDate();
	}

	private void initView() {
		et_shopName = (EditText) findViewById(R.id.et_shopName);
		et_shopAdd = (EditText) findViewById(R.id.et_shopAdd);
		et_leadname = (EditText) findViewById(R.id.et_leadname);
		et_linkName = (EditText) findViewById(R.id.et_linkName);
		et_linkTel = (EditText) findViewById(R.id.et_linkTel);
		et_cardid = (EditText) findViewById(R.id.et_cardid);
		et_blicense = (EditText) findViewById(R.id.et_blicense);
		iv_shop1 = (ImageView) findViewById(R.id.iv_shop1);
		iv_shop2 = (ImageView) findViewById(R.id.iv_shop2);
		iv_shop3 = (ImageView) findViewById(R.id.iv_shop3);
		iv_shop4 = (ImageView) findViewById(R.id.iv_shop4);
		iv_shop5 = (ImageView) findViewById(R.id.iv_shop5);
		iv_idcard1 = (ImageView) findViewById(R.id.iv_idcard1);
		iv_idcard2 = (ImageView) findViewById(R.id.iv_idcard2);
		iv_idcard3 = (ImageView) findViewById(R.id.iv_idcard3);
		iv_license1 = (ImageView) findViewById(R.id.iv_license1);
		iv_license2 = (ImageView) findViewById(R.id.iv_license2);
		iv_zhuzhi = (ImageView) findViewById(R.id.iv_zhuzhi);//组织资格证
		iv_shuiwu = (ImageView) findViewById(R.id.iv_shuiwu);//税务登记证
		commit_verify = (Button) findViewById(R.id.commit_verify);
		
		tvRight.setOnClickListener(this);
		
		linear_one = (LinearLayout) findViewById(R.id.linear_one);
		linear_five = (LinearLayout) findViewById(R.id.linear_five);
		linear_idcard = (LinearLayout) findViewById(R.id.linear_idcard);
		linear_license = (LinearLayout) findViewById(R.id.linear_license);
		
		tv_edit_company = (TextView) findViewById(R.id.tv_edit_company);
		tv_edit_idcard = (TextView) findViewById(R.id.tv_edit_idcard);
		tv_edit_license = (TextView) findViewById(R.id.tv_edit_license);
		tv_edit_zhuzhi = (TextView) findViewById(R.id.tv_edit_zhuzhi);
		tv_edit_shuwu = (TextView) findViewById(R.id.tv_edit_shuwu);
		commit_verify.setOnClickListener(this);
		iv_shop1.setOnClickListener(new MyClassClick(iv_shop1,1));
		iv_shop2.setOnClickListener(new MyClassClick(iv_shop2,2));
		iv_shop3.setOnClickListener(new MyClassClick(iv_shop3,3));
		iv_shop4.setOnClickListener(new MyClassClick(iv_shop4,4));
		iv_shop5.setOnClickListener(new MyClassClick(iv_shop5,5));
		
		iv_idcard1.setOnClickListener(new MyClassClick(iv_idcard1,6));
		iv_idcard2.setOnClickListener(new MyClassClick(iv_idcard2,7));
		iv_idcard3.setOnClickListener(new MyClassClick(iv_idcard3,8));
		iv_license1.setOnClickListener(new MyClassClick(iv_license1,9));
		iv_license2.setOnClickListener(new MyClassClick(iv_license2,10));
		iv_zhuzhi.setOnClickListener(new MyClassClick(iv_zhuzhi,11));
		iv_shuiwu.setOnClickListener(new MyClassClick(iv_shuiwu,12));
		tv_edit_company.setOnClickListener(this);
		tv_edit_idcard.setOnClickListener(this);
		tv_edit_license.setOnClickListener(this);
		tv_edit_zhuzhi.setOnClickListener(this);
		tv_edit_shuwu.setOnClickListener(this);
	}

	private void SetImage(final int id,final ImageView iv) {
		setImage(new ImageCallBack() {
			@Override
			public void setImage(Bitmap bitmap) {
				// TODO Auto-generated method stub
				iv.setImageBitmap(bitmap);
				switch (id) {
				case 1:
					iv_shop2.setVisibility(View.VISIBLE);
					break;
				case 2:
					iv_shop3.setVisibility(View.VISIBLE);
					break;
				case 3:
					linear_five.setVisibility(View.VISIBLE);
					iv_shop4.setVisibility(View.VISIBLE);
					break;
				case 4:
					iv_shop5.setVisibility(View.VISIBLE);
					break;
				case 6:
					iv_idcard2.setVisibility(View.VISIBLE);
					break;
				case 7:
					iv_idcard3.setVisibility(View.VISIBLE);
					break;
				case 9:
					iv_license2.setVisibility(View.VISIBLE);
					break;
				case 11:
					iv_shuiwu.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		});
	}

	*//**
	 * 获取当前登录用户信息
	 * 
	 * *//*
	private void HttpDate() {
		// TODO Auto-generated method stub
		context = AgentAuthActivity.this;
		Intent intent = getIntent();
		if(intent!=null){
			isRegister = intent.getBooleanExtra("isRegister", false);
			if(isRegister){
				tvRight.setText("跳过");
				tvRight.setVisibility(View.VISIBLE);
			}else{
			codeId = 1;
			ApiHttpCilent.getInstance(context).getUserInfo(context,
					new MyCallBack());
			}
		}
	}

	//动态生成上传图片view
	@SuppressLint("NewApi")
	private void CreateView(final int imageId){
		final ImageView iv = new ImageView(context);
		iv.setBackground(getResources().getDrawable(R.drawable.upload));
		int width = (int) getResources().getDimension(R.dimen.linearLayout_width_52);
		int height = (int) getResources().getDimension(R.dimen.linearLayout_height_42);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
		lp.setMargins(14, 14, 14, 14);
		iv.setLayoutParams(lp);
//		setImage(iv, new ImageCallBack() {
//			@Override
//			public void setImage(Bitmap bitmap,ImageView imageView) {
//				// TODO Auto-generated method stub
//				imageView.setBackground(null);
//				imageView.setImageBitmap(bitmap);
//			}
//		});
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {   
				showAtWindowTop(context, iv, R.layout.forum_release_pic);
			} 
		});  
		
		if(imageId==1){
			if(linear_one.getChildCount()<3){
				linear_one.addView(iv);
			}else{
				if(linear_five.getChildCount()<2){
					lp.setMargins(28, 28, 14, 14);
					linear_five.setVisibility(View.VISIBLE);
					linear_five.addView(iv);
				}
			}
		  }else if(imageId==2){
			  if(linear_idcard.getChildCount()<3){
				  linear_idcard.addView(iv);
			  }
		  }else if(imageId==3){
			  if(linear_license.getChildCount()<2){
				  linear_license.addView(iv);
			  }
		  }
		
	}
	private ImageView ReturnImage(int flag,final int id){
		switch (id) {
		case 0:
			if(flag==1){
				iv_shop2.setVisibility(View.VISIBLE);
				return iv_shop1;
			}else if(flag==2){
				iv_idcard2.setVisibility(View.VISIBLE);
				return iv_idcard1;
			}else if(flag==3){
				iv_license2.setVisibility(View.VISIBLE);
				return iv_license1;
			}
		case 1:
			if(flag==1){
				iv_shop3.setVisibility(View.VISIBLE);
				return iv_shop2;
			}else if(flag==2){
				iv_idcard3.setVisibility(View.VISIBLE);
				return iv_idcard2;
			}else if(flag==3){
				return iv_license2;
			}
			return iv_shop2;
		case 2:
			if(flag==1){
				linear_five.setVisibility(View.VISIBLE);
				iv_shop4.setVisibility(View.VISIBLE);
				return iv_shop3;
			}else if(flag==2){
				return iv_idcard3;
			}
			return iv_shop2;
		case 3:
			if(flag==1){
				iv_shop5.setVisibility(View.VISIBLE);
				return iv_shop4;
			}
		case 4:
			if(flag==1){
				return iv_shop5;
			}
		default:
			break;
		}
		return null;
	}
	*//**
	 * 填充数据
	 * 
	 * *//*
	private void initDate(ShopBean shopbean) {
		// TODO Auto-generated method stub
		
		ShopBean shopbeans = (ShopBean) SharedPreferencesUtil.getObject(context, "AgentAuth");
		if(shopbeans!=null){
			setOldDate(shopbeans);
		}else{
			setOldDate(shopbean);
		}
		//获取回填的数据
		GetReurnDate();
	}

	private void setOldDate(ShopBean shopbean){
		et_shopName.setText(shopbean.getName());
		et_shopAdd.setText(shopbean.getAddress());
		et_leadname.setText(shopbean.getLegal());
		et_linkName.setText(shopbean.getContact());
		et_linkTel.setText(shopbean.getContactmobile());
		et_cardid.setText(shopbean.getCardid());
		et_blicense.setText(shopbean.getBlicense());
		DisplayImage(shopbean.getPic1(),1);
		DisplayImage(shopbean.getPic2(),2);
		DisplayImage(shopbean.getPic3(),3);
		pic4_return = shopbean.getPic4();
		pic5_return = shopbean.getPic5();
		if(pic4_return!=null)
			MyApplication.imageLoader.displayImage(shopbean.getBaseurl()+pic4_return, iv_zhuzhi,MyApplication.options);
		if(pic5_return!=null)
			MyApplication.imageLoader.displayImage(shopbean.getBaseurl()+pic5_return, iv_shuiwu,MyApplication.options);
		
	}
	private void GetReurnDate() {
		simpleName = et_shopName.getText().toString().trim();
		simpleAddress = et_shopAdd.getText().toString().trim();
		simpleLegal = et_leadname.getText().toString().trim();
		simpleContact = et_linkName.getText().toString().trim();
		simplemobile = et_linkTel.getText().toString().trim();
		simpleCardid = et_cardid.getText().toString().trim();
		simpleBlicense = et_blicense.getText().toString().trim();
		simplepic1 = getPic(list1_return);
		simplepic2 = getPic(list2_return);
		simplepic3 = getPic(list3_return);
		simplepic4 = pic4_return;
		simplepic5 = pic5_return;
		simpleUrl = shopbean.getBaseurl();
		simpleAuthentication = shopbean.getAuthentication();
	}

	private void DisplayImage(String imagereturn,int id) {
		if(imagereturn!=null){
			if(imagereturn.contains(",")){
				String imageArr[] = imagereturn.split(",");
				for(int i=0;i<imageArr.length;i++){
					MyApplication.imageLoader.displayImage(shopbean.getBaseurl()+imageArr[i], ReturnImage(id, i),MyApplication.options);
						if(!list1_return.contains(imageArr[i]) && id==1){
							list1_return.add(imageArr[i]);
						}else if(!list2_return.contains(imageArr[i])&& id==2){
							list2_return.add(imageArr[i]);
						}else if(!list3_return.contains(imageArr[i])&& id==3){
							list3_return.add(imageArr[i]);
						}
				}
			}else{
				MyApplication.imageLoader.displayImage(shopbean.getBaseurl()+imagereturn, ReturnImage(id, 0),MyApplication.options);
				if(!list1_return.contains(imagereturn)&& id==1){
					list1_return.add(imagereturn);
				}else if(!list2_return.contains(imagereturn)&& id==2){
					list2_return.add(imagereturn);
				}else if(!list3_return.contains(imagereturn)&& id==3){
					list3_return.add(imagereturn);
				}
			}
		}
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
		return "基本信息";
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

	
	class MyClassClick implements OnClickListener{
		ImageView iv;
		int id;
		MyClassClick(ImageView iv,int id){
			this.iv = iv;
			this.id = id;
		}
		@Override
		public void onClick(View v) {
			imageFlag = id;
			SetImage(id,iv);
			showAtWindowTop(context, iv, R.layout.forum_release_pic);
		}
	}
	
	
	private String getPic(List<String> list){
		String pic = "";
		picSize = list.size(); 
		for(int i=0;i<list.size();i++){
			if(i==list.size()-1){
				pic+=list.get(i);
			}else{
				pic+=list.get(i)+",";
			}
		}
		return pic;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		super.onClick(v);
		switch (v.getId()) {
		case R.id.commit_verify:
			GetTextInput();//获取输入框内容
			if (ToastString(st_name, R.string.shop_null)) {
				return;
			}
			if (ToastString(st_shopAdd, R.string.add_out)) {
				return;
			}
			if (ToastString(st_leadname, R.string.name_out)) {
				return;
			}
			if (ToastString(st_linkName, R.string.linkname_out)) {
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
			if(st_leadname.length()<2){
				ToastUtil.showToast(context, R.string.faren_text);
				return;
			}
			if(st_linkName.length()<2){
				ToastUtil.showToast(context, R.string.lianxiren_text);
				return;
			}
			if(st_linkTel.length()<11){
				ToastUtil.showToast(context, R.string.renxiren_tel);
				return;
			}
			if(st_cardid.length()<18){
				ToastUtil.showToast(context, R.string.shenfenzheng_text);
				return;
			}
			codeId = 2;
			GetPicDate();
			if (ToastString(pic1, R.string.shop_null1)) {
				return;
			}
			if (ToastString(pic2, R.string.shop_null2)) {
				return;
			}
			if (ToastString(pic3, R.string.shop_null3)) {
				return;
			}
			if (ToastString(pic4, R.string.shop_null4)) {
				return;
			}
			if (ToastString(pic5, R.string.shop_null5)) {
				return;
			}
			
			if(picSize<2){
				ToastUtil.showToast(context, "法人身份证照片请上传正反面噢");
				return;
			}
			
			ApiHttpCilent.getInstance(context).commitUserInfo(context, st_name,
					st_shopAdd, st_leadname, st_linkName, st_linkTel,
					st_cardid, st_blicense, pic1, pic2, pic3,pic4,pic5, new MyCallBack());
			break;
		case R.id.forum_release_pic_photo:
			*//**
			 * 调取相册
			 *//*
			mPopupWindow.dismiss();
			startPhoto();
			break;
		case R.id.forum_release_pic_camera:
			*//**
			 * 调取照相机
			 *//*
			mPopupWindow.dismiss();
			startCamera();

			break;
		case R.id.forum_release_pic_cancel:
			*//**
			 * 取消
			 *//*
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
			//获取当前填写的数据
			GetTextInput();
			GetPicDate();
			if(modified){
			if(isEdite(st_name, simpleName)){
				return;
			}
			if(isEdite(st_shopAdd, simpleAddress)){
				return;
			}
			if(isEdite(st_leadname, simpleLegal)){
				return;
			}
			if(isEdite(st_linkName, simpleContact)){
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
			if(isEdite(pic4, simplepic4)){
				return;
			}
			if(isEdite(pic5, simplepic5)){
				return;
				}
			onBackPressed();
			}
			break;
		case R.id.tv_edit_company:
//			s
			break;
		case R.id.tv_edit_idcard:
			break;
		case R.id.tv_edit_license:
			break;
		case R.id.tv_edit_zhuzhi:
			break;
		case R.id.tv_edit_shuwu:
			break;
		}
	}

	private boolean isEdite(String currtname,String beforeName){
		if(!currtname.equals(beforeName)){
			ShowDialog("是否保存修改?", "温馨提示");			
			return true;
		}
		return false;
	}
	//提示框是否保存修改
	private void ShowDialog(String notice, String title) {
		CommonDialog.makeText( AgentAuthActivity.this, title, notice, new OnDialogListener() {
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
	
	
	 * 保存本地编辑的数据到本地
	 * 
	private void SaveDate() {
		ShopBean savebean = new ShopBean();
		savebean.setName(st_name);
		savebean.setAddress(st_shopAdd);
		savebean.setLegal(st_leadname);
		savebean.setContact(st_linkName);
		savebean.setContactmobile(st_linkTel);
		savebean.setCardid(st_cardid);
		savebean.setBlicense(st_blicense);
		savebean.setBaseurl(simpleUrl);
		savebean.setPic1(pic1);
		savebean.setPic2(pic2);
		savebean.setPic3(pic3);
		savebean.setPic4(pic4);
		savebean.setPic5(pic5);
		savebean.setAuthentication(simpleAuthentication);
		SharedPreferencesUtil.saveObject(context, savebean, "AgentAuth");
	}
	//获取编辑后图片数据
	private void GetPicDate() {
		if(modified){//编辑模式
			pic1 = getPic(list1_return);
			pic2 = getPic(list2_return);
			pic3 = getPic(list3_return);
			pic4 = pic4_return;
			pic5 = pic5_return;
			picSize = list2_return.size();
		}else{
			if(list1.size()!=0){
				pic1 = getPic(list1);
			}else{
				pic1 = getPic(list1_return);
			}
			if(list2.size()!=0){
				pic2 = getPic(list2);
				picSize = list2.size();
			}else{
				pic2 = getPic(list2_return);
				picSize = list2_return.size();
			}
			if(list3.size()!=0){
				pic3 = getPic(list3);
			}else{
				pic3 = getPic(list3_return);
			}
		}
	}
	
	 * 获取数据框内容
	 * *
	private void GetTextInput() {
		st_name = et_shopName.getText().toString().trim();
		st_shopAdd = et_shopAdd.getText().toString().trim();
		st_leadname = et_leadname.getText().toString().trim();
		st_linkName = et_linkName.getText().toString().trim();
		st_linkTel = et_linkTel.getText().toString().trim();
		st_cardid = et_cardid.getText().toString().trim();
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
		runOnUiThread(new Runnable() {
			public void run() {
				ApiHttpCilent.loading.dismiss();
			}
		});
	}

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
			ShopBaseBean baseBean = gson.fromJson(response, ShopBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(baseBean.getStatus()) && codeId == 1) {// 正常获取个人信息
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = baseBean.getResult();
			} else if ("1".equals(baseBean.getStatus()) && codeId == 2) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;// 提交信息成功
				// message.obj = baseBean.getResult();
			} else if("1".equals(baseBean.getStatus()) &&codeId==3){
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;// 提交图片成功
				
			}else if ("0".equals(baseBean.getStatus()) && codeId == 1) {
				message.what = ConstantsUtil.HTTP_FAILE;
				message.obj = baseBean.getError();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE_LOGIN;
				message.obj = baseBean.getError();
			}
			handler.sendMessage(message);
			return baseBean;
		}
	}

	public class MyHandler extends WeakHandler<AgentAuthActivity> {


		public MyHandler(AgentAuthActivity reference) {
			super(reference);
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				shopbean = (ShopBean) msg.obj;
				if (shopbean != null && !shopbean.getAuthentication().equals("0")) {
					modified = true;
					// //1 未申请 2 审核中 3 审核通过 4 审核失败
					if (shopbean.getAuthentication() != null) {
						if (shopbean.getAuthentication().equals("2")) {
							ToastUtil.showToast(
									getReference(),
									getReference().getResources().getString(
											R.string.commit_success_ing));
						} else if (shopbean.getAuthentication().equals("3")) {
							ToastUtil.showToast(
									getReference(),
									getReference().getResources().getString(
											R.string.commit_success));
							commit_verify.setEnabled(false);
							commit_verify.setText("审核通过");
							commit_verify.setTextColor(getResources().getColor(R.color.white));
							commit_verify.setBackgroundResource(R.drawable.verifystatus_button);
						} else if (shopbean.getAuthentication().equals("4")) {
							ToastUtil.showToast(
									getReference(),
									getReference().getResources().getString(
											R.string.commit_shop_fail));
						}
						initDate(shopbean);
					}
				}else{
					modified = false;//未获取到数据就是新增操作
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				ToastUtil.showToast(getReference(), getReference()
						.getResources().getString(R.string.commit_update_ing));
				SaveDate();
				if(isRegister){
					Intent intent = new Intent(context,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					StartActivityUtil.startActivity((Activity) context, intent);
					finish();
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				ImageBean bean = (ImageBean) msg.obj;
				if(imageFlag<6){
					SetDate(list1_return, imageFlag, bean.getImgName(), list1);
				}else if(imageFlag<9){
					SetDate(list2_return, imageFlag, bean.getImgName(), list2);
				}else if(imageFlag<11){
					SetDate(list3_return, imageFlag, bean.getImgName(), list3);
				}else if(imageFlag==11){
					if(pic4_return ==""){//返回值是空代表是修改操作
						pic4_return = bean.getImgName();
					}else{//否则是新增操作
						if(!pic4.contains(bean.getImgName())){
							pic4 = bean.getImgName();
						}
					}
				}else if(imageFlag==12){
					if(pic5_return ==""){//返回值是空代表是修改操作
						pic5_return = bean.getImgName();
					}else{//否则是新增操作
						if(!pic5.contains(bean.getImgName())){
							pic5 = bean.getImgName();
						}
					}
				}
				break; 
			case ConstantsUtil.HTTP_FAILE:
				ErrorBean beans = (ErrorBean) msg.obj;
				if(beans.getCode().equals("100")){
					MyApplication.getInstance().startLogin(new LoginCallBack() {
						@Override
						public void loginSuccess() {
							// TODO Auto-generated method stub
							Intent i = new Intent(context,AgentAuthActivity.class);
							StartActivityUtil.startActivity((Activity) context, i);
						}
						
						@Override
						public void loginFail() {
							// TODO Auto-generated method stub
							
						}
					}, (Activity) context);
				}
				ToastUtil.showToast(getReference(), beans.getInfo());
				break;
			case ConstantsUtil.HTTP_FAILE_LOGIN:
				ErrorBean beanError = (ErrorBean) msg.obj;
				ToastUtil.showToast(getReference(), beanError.getInfo());
				break;
			default:
				break;
			}
		}
	}

	//是否是修改操作 否则先删除 在新增
	private void SetDate(List<String> list_return,int imageFlag,String name,List<String> initlist){
		if(list_return !=null){//返回值是空代表是修改操作
			modified = true;
			DeleteRepate(imageFlag, list_return, name);
		}else{//否则是新增操作
			modified = false;
			if(!initlist.contains(name)){
				initlist.add(name);
			}
		}
	}
	
	 * 修改图像后删除之前点击位置的图片 重新添加到集合
	 * id 是点击图片的下标 list是需要删除的数据图像集合
	  
	private void DeleteRepate(int id,List<String> list,String name){
		//判断是否是修改操作 是就替换图片地址 否则添加
		switch (id) {
		case 1:
			deleteList(list, id, name);
			break;
		case 2:
			deleteList(list, id, name);
			break;
		case 3:
			deleteList(list, id, name);
			break;
		case 4:
			deleteList(list, id, name);
			break;
		case 5:
			deleteList(list, id, name);
			break;
		case 6:
			deleteList(list, id, name);
			break;
		case 7:
			deleteList(list, id, name);
			break;
		case 8:
			deleteList(list, id, name);
			break;
		case 9:
			deleteList(list, id, name);
			break;
		case 10:
			deleteList(list, id, name);
			break;
		case 11:
			deleteList(list, id, name);
			break;
		case 12:
			deleteList(list, id, name);
			break;
		default:
			break;
		}
	}
	
	//修改后删除 集合值被替换的那张图片
	private void deleteList(List<String> list,int id,String name){
			if(id<6){
				if(list.size()>=id)
				list.remove(id-1);
			}else if(id<9){//6 7 8
				if(list.size()>=id-5)
				list.remove(id-6);
			}else if(id<11){
				if(list.size()>=id-8)
				list.remove(id-9);
			}
			list.add(name);
	}
	*//**
	 * 
	 * 
	 * @describe:显示选择图片对话框
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:42:21
	 * 
	 *//*
	private void showAtWindowTop(Context activity, View position, int layoutId) {

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

	*//**
	 * 
	 * 
	 * @describe:设置选择图片监听
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:42:47
	 * 
	 *//*
	private void setCheckPhotoListener() {
		// TODO Auto-generated method stub
		tvPhoto.setOnClickListener(this);
		tvCamera.setOnClickListener(this);
		tvCancel.setOnClickListener(this);
	}

	*//**
	 * 
	 * 
	 * @describe:调用相册
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-26上午11:43:27
	 * 
	 *//*
	private void startPhoto() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
		startActivityForResult(i, PHOTO);
	}

	*//**
	 * 
	 * 
	 * @describe:调取相机
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-11-19上午11:35:11
	 * 
	 *//*
	public void startCamera() {
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

		 photoUri = getContentResolver().insert(
		 MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		 intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);


		 startActivityForResult(intent, CAMERA);
	}
	//获取图片path
	protected String getAbsoluteImagePath(Uri uri)  
	   {  
	       // can post image  
	       String [] proj={MediaStore.Images.Media.DATA};  
	       Cursor cursor = managedQuery( uri,  
	                       proj,                 // Which columns to return  
	                       null,       // WHERE clause; which rows to return (all rows)  
	                       null,       // WHERE clause selection arguments (none)  
	                       null);                 // Order-by clause (ascending by name)  
	        
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
	       cursor.moveToFirst();  
	          
	       return cursor.getString(column_index);  
	   } 
	
	private File getPath(String imagepath){
		BitmapFactory.Options options = new BitmapFactory.Options(); 
        options.inJustDecodeBounds = true; 
        bitmaps = BitmapFactory.decodeFile(imagepath, options);
        options.inJustDecodeBounds = false; 
         //缩放比 
        int be = (int)(options.outHeight / (float)500); 
        if (be <= 0) 
            be = 1; 
        options.inSampleSize = be; 
        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦 
        bitmaps=BitmapFactory.decodeFile(imagepath,options); 
        picNamethumbnail = DateFormat.format("yyyyMMdd_hhmmss",
        		Calendar.getInstance(Locale.CHINA))+ ".jpg";
        newBitmap = BitmapUtil.compressImage(bitmaps);
        File file = new File(PATH, picNamethumbnail);
        FileOutputStream bao = null;
     		try {
     			bao = new FileOutputStream(file);
     		} catch (FileNotFoundException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
     		//向文件写图片流
     		newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        int w = newBitmap.getWidth(); 
        int h = newBitmap.getHeight(); 
//        System.out.println(w+"   "+h); 
//        System.out.println(getBitmapSize(newBitmap)+"字节");
        return file;
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
//		   Log.e("获取文件大小", "文件不存在!");
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
				String imagepath = getAbsoluteImagePath(uri);
				File thumbnailFile = getPath(imagepath);
		            try {
						System.out.println(getFileSize(thumbnailFile)/1024+"大小");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            callback.setImage(newBitmap);
		            codeId = 3;
		            ApiHttpCilent.getInstance(context).UploadImage(context, thumbnailFile, new MyImageCallBack());
		        
				break;
			case PHOTO:
				Uri uris = data.getData(); 
				Cursor cursor = context.getContentResolver().query(uris, null, 
				null, null, null); 
				if(cursor!=null){
				cursor.moveToFirst(); 
				String imgNo = cursor.getString(0); // 图片编号 
				imgPath = cursor.getString(1); // 图片文件路径 
				String imgSize = cursor.getString(2); // 图片大小 3541161
				String imgName = cursor.getString(3); // 图片文件名 
				cursor.close(); 
				}else{
					 if (uris.getScheme().compareTo("file")==0)         //file:///开头的uri
					   {
						 imgPath = uris.toString();
						 imgPath = uris.toString().replace("file://", "");
					//替换file://
//					      if(!imgPath.startsWith("/mnt")){
//					//加上"/mnt"头
//					    	  imgPath += "/mnt"; 
//					   } 
					   }
				}
				File imagepathUri = getPath(imgPath);
				callback.setImage(newBitmap);
				 try {
						System.out.println(getFileSize(imagepathUri)/1024+"大小");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				codeId = 3;
				ApiHttpCilent.getInstance(context).UploadImage(context, imagepathUri, new MyImageCallBack());
				break;
			}
		}
	}

	
	private void setImage(ImageCallBack callback){
		this.callback = callback;
	}
	private interface ImageCallBack{
		void setImage(Bitmap bitmap);
	}
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
*/