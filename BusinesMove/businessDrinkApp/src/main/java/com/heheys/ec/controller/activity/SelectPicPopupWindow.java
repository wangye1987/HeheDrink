package com.heheys.ec.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BrandBaseBean;
import com.heheys.ec.model.dataBean.BrandBaseBean.BrandBean;
import com.heheys.ec.model.dataBean.BrandBaseBean.BrandList;
import com.heheys.ec.model.dataBean.Factorbean;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean.PlaceListResultBean;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean.Region;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean.RegionList;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间：2016-3-16 下午5:19:58 类说明
 * 
 * @category 弹出筛选现在框界面
 */

public class SelectPicPopupWindow extends Activity implements OnClickListener {

	private TextView button_cancle;// 取消按钮
	private TextView tv_brand;// 取消按钮
	private TextView sure_bt;// 取消按钮
	private TextView production_bt;// 取消按钮
	private TextView tv_production;//
	private TextView tv_country;//
	private TextView tv_pl;//

	private static int screenHeight;
	private static int screenWidth;

	private List<RegionList> dataRegion;
	private PlaceListResultBean dataPlaceName;
	private BrandList dataBrandName;
	private PlaceListResultBean dataPlaceNameback;//每次返回是数据
	private BrandList dataBrandNameback;//每次请求返回的数据

	private LinearLayout country_content;
	private LinearLayout linear_pl;

	private LinearLayout area_content;
	private LinearLayout content_title;
	private LinearLayout linear_back;

	private ListView listView_country;
	private Context mContext;

	// 传递到后台参数集合
	private String country_id;
	private String country_st;
	private String area_id;
	private String area_st;
	private String brand_id;
	private ArrayList<String> country_list = new ArrayList<String>();// 国家集合
	private ArrayList<String> area_list = new ArrayList<String>();// 产地集合
	private MyMessageHandler messageHandler;
	private int indexArea;// 当前点击的地区下标
	private int type;
	private String brandtype;//1 批发 0拼单
	private int currtPosition;
	private ImageView iv_back;
	private TextView bt_clear;
	private List<Region> listregio ;//产地
	private List<BrandBean> listbrand ;//品牌
	private MyAreaAdapter areaAdapter;
	private MyCountryAdapter countryAdapter;
	private Factorbean factorbean;//是否带筛选条件
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 this.requestWindowFeature(Window.FEATURE_NO_TITLE); //
//		// 设置Activity标题不显示
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏显示
		setContentView(R.layout.alert_right);
		init();
		initData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
		
	}
	@SuppressWarnings("unchecked")
	private void initData() {
		listregio = MyApplication.getInstance().getListplace();
		setplacecheck();
		listbrand = MyApplication.getInstance().getListbrand();
		setbrandcheck();
	}

	private void setbrandcheck() {
		if (listbrand != null ) {
			if(listbrand.size() > 0){
				List<BrandBean> brandlist = listbrand;
			for (BrandBean brand : brandlist) {
				if (brand.isCheck()) {
					tv_brand.setText(brand.getName());
					brand_id = brand.getId();
					break; 
				}
			}}
		}
	}

	//初始化之前选择的信息 再次选中
	private void setplacecheck() {
		if (listregio != null) {
			int size = listregio.size();
			if( size > 0){
			for (int i=0;i<size;i++) {
				if (listregio.get(i).isCheck) {
					tv_country.setText(listregio.get(i).getCname());
					country_id = listregio.get(i).getCid();
					currtPosition = i;
					List<RegionList> listregion = listregio.get(i).getRegion();
					for (RegionList regionlist : listregion) {
						if (regionlist.isCheck) {
							tv_production.setText(regionlist.getRegionname());
							area_id = regionlist.getRegionid();
							    break;
							}
					  }
					break;
				  }
				}
			}
		}
	}
	
	//清除筛选条件
	private void ClearSelect(){
		listregio = MyApplication.getInstance().getListplace();
		listbrand = MyApplication.getInstance().getListbrand();
		//清除产地 筛选保存的信息
		if (listregio != null) {
			for(Region region:listregio){
				if(region.isCheck())
					region.setCheck(false);
			}
		}
		if (listbrand != null) {
			for(BrandBean brandBean:listbrand){
				if(brandBean.isCheck())
					brandBean.setCheck(false);
			}
		}
	}
	
	private void init() {
		mContext = SelectPicPopupWindow.this;
		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();// 获取屏幕宽度
		LayoutParams lp = getWindow().getAttributes();// //lp包含了布局的很多信息，通过lp来设置对话框的布局
		lp.height = LayoutParams.MATCH_PARENT;
		lp.gravity = Gravity.RIGHT;
		lp.width = (screenWidth * 4 / 5);// lp宽度设置为屏幕的4/5
//		lp.alpha = 0.8f;
		getWindow().setAttributes(lp);// 将设置好属性的lp应用到对话框

		iv_back = (ImageView) findViewById(R.id.iv_back);
		bt_clear = (TextView) findViewById(R.id.bt_clear);
		tv_country = (TextView) findViewById(R.id.tv_country);
		tv_country.setText("全部");
		tv_production = (TextView) findViewById(R.id.tv_production);
		tv_production.setText("全部");
		button_cancle = (TextView) findViewById(R.id.cancle_bt);
		tv_brand = (TextView) findViewById(R.id.tv_brand);
		tv_brand.setText("全部");
		sure_bt = (TextView) findViewById(R.id.sure_bt);
		production_bt = (TextView) findViewById(R.id.production_bt);
		tv_pl = (TextView) findViewById(R.id.tv_pl);
		linear_pl = (LinearLayout) findViewById(R.id.linear_pl);// 品类
		country_content = (LinearLayout) findViewById(R.id.country_content);// 国家
		area_content = (LinearLayout) findViewById(R.id.area_content);// 产区
		content_title = (LinearLayout) findViewById(R.id.content_title);// 产区
		linear_back = (LinearLayout) findViewById(R.id.linear_back);//返回
		listView_country = (ListView) findViewById(R.id.listView_country);
		bt_clear.setOnClickListener(this);// 取消按钮的点击事件监听
		button_cancle.setOnClickListener(this);// 取消按钮的点击事件监听
		country_content.setOnClickListener(this);//
		area_content.setOnClickListener(this);//
		sure_bt.setOnClickListener(this);//
		tv_brand.setOnClickListener(this);//
		iv_back.setOnClickListener(this);//
		linear_back.setOnClickListener(this);//
		messageHandler = new MyMessageHandler(this);
		// 初始化产地
		initPlaceNameData();
		Intent intent = getIntent();
		if(intent!=null){
		recommend = intent.getStringExtra("intent"); 
		brandtype = intent.getStringExtra("type"); 
		place = intent.getStringExtra("place"); 
		boolean  issx = intent.getBooleanExtra("issx", false); 
		if("1".equals(recommend)){
			tv_pl.setVisibility(View.VISIBLE);
			linear_pl.setVisibility(View.VISIBLE);
		}else{
		if("1".equals(brandtype)){
			tv_pl.setVisibility(View.GONE);
			linear_pl.setVisibility(View.GONE);	
		}else{
			tv_pl.setVisibility(View.VISIBLE);
			linear_pl.setVisibility(View.VISIBLE);
		 }
		}
		//隐藏品类
			if(issx){
				tv_pl.setVisibility(View.GONE);			
				linear_pl.setVisibility(View.GONE);			
			}else{
				if(!"1".equals(brandtype)){
					tv_pl.setVisibility(View.VISIBLE);			
					linear_pl.setVisibility(View.VISIBLE);			
					}
				
				if("1".equals(recommend)){
					tv_pl.setVisibility(View.VISIBLE);			
					linear_pl.setVisibility(View.VISIBLE);	
				}
			  }
			}
	}
	
	//绑定产区列表
	private void bindPlaceDate(){
		if (listregio != null) {
			if(!listregio.get(0).getCname().equals("全部")){
			Region regio = new Region();
			ArrayList<RegionList> childregio = new ArrayList<RegionList>();
			RegionList regionList = new RegionList();
			regio.setCid("");
			regio.setCname("全部");
			regionList.setRegionname("全部");
			regionList.setRegionid("");
			childregio.add(regionList);
			regio.setRegion(childregio);
			listregio.add(0, regio);
			}
		}
		if(tv_country.getText()==null)
			tv_country.setText("全部");
		if(tv_production.getText()==null)
			tv_production.setText("全部");
		countryAdapter = new MyCountryAdapter(listregio,
				SelectPicPopupWindow.this);
		areaAdapter = new MyAreaAdapter(listregio.get(currtPosition)
				.getRegion(), SelectPicPopupWindow.this);
		listView_country.setAdapter(countryAdapter);
	}
	
	//绑定品牌列表
	private void bindBrandDate(){
		if (listbrand != null && listbrand.size() > 0) {
			if(!listbrand.get(0).getName().equals("全部")){
			BrandBean brand = new BrandBean();
			brand.setId("");
			brand.setName("全部");
			listbrand.add(0, brand);
			} 
		}
		if(tv_brand.getText()==null)
			tv_brand.setText("全部");
	}
	public static class MyMessageHandler extends
			WeakHandler<SelectPicPopupWindow> {

		public MyMessageHandler(SelectPicPopupWindow reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					ToastUtil.showToast(getReference(), "请求失败");
				}
				break;
			// 产地数据
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				/**
				 * 处理成功的数据
				 */
				if(getReference().beanplace!=null ){
					if( getReference().listregio==null){
						getReference().listregio = getReference().beanplace.getList();
					}
						getReference().bindPlaceDate();
				}
				break;
				//品牌
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
				if(getReference().beanbrand!=null){
					if(getReference().listbrand==null){
						getReference().listbrand = getReference().beanbrand.getList();
					}
					getReference().bindBrandDate();
				}
				break;
			}
		}
	}

	/*
	 * 初始化产地数据
	 */
	private void initPlaceNameData() {
		ApiHttpCilent.getInstance(getApplicationContext()).PlaceNameList(this,
				new RequestPlaceNameCallBack());
		ApiHttpCilent.getInstance(getApplicationContext()).BrandList(this,"",
				new RequestBrandNameCallBack());
	}

	public PlaceListResultBean beanplace;// 产地数据bean
	public BrandList beanbrand;// 品类数据bea是
	private String recommend;//是否精选
	private String place;
	private void Dimessis() {
		SelectPicPopupWindow.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	// 产地
	public class RequestPlaceNameCallBack extends
			BaseJsonHttpResponseHandler<PlaceNameBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, PlaceNameBaseBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				PlaceNameBaseBean arg3) {
		}

		@Override
		protected PlaceNameBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			PlaceNameBaseBean placeNameList = gson.fromJson(response, PlaceNameBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(placeNameList.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				beanplace = placeNameList.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = placeNameList.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return placeNameList;
		}
	}

	public class RequestBrandNameCallBack extends
			BaseJsonHttpResponseHandler<BrandBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BrandBaseBean arg4) {
			Dimessis();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BrandBaseBean arg3) {
		}

		@Override
		protected BrandBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimessis();
			Gson gson = new Gson();
			BrandBaseBean brandNameList = gson.fromJson(response, BrandBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(brandNameList.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
				beanbrand = brandNameList.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = brandNameList.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return brandNameList;
		}
	}

	// 重写finish()方法，加入关闭时的动画
	public void finish() {
		super.finish();
		SelectPicPopupWindow.this.overridePendingTransition(0,
				R.anim.dialog_exit);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		// 取消按钮的点击事件，关闭对话框
		case R.id.cancle_bt:
			finish();
			break;
		case R.id.sure_bt:
			CloseCheck();
			break;
		case R.id.bt_clear:
			tv_country.setText("全部");
			tv_production.setText("全部");
			tv_brand.setText("全部");
			area_id = "";
			brand_id = "";
			// CloseCheck();
			if("1".equals(recommend)){
				MobclickAgent.onEvent(mContext,"C_PROM_LST_10");
			}else{
			if("1".equals(type))
				MobclickAgent.onEvent(mContext,"C_SAL_INF_4");
			else
				MobclickAgent.onEvent(mContext,"C_PD_LST_10");
			}
			//清除之前保存的选中标记
			ClearSelect();
			break;
			
		case R.id.country_content:
			if("1".equals(recommend)){
				MobclickAgent.onEvent(mContext,"C_PROM_LST_7");
			}else{
			if("1".equals(type))
				MobclickAgent.onEvent(mContext,"C_SAL_INF_1");
			else
				MobclickAgent.onEvent(mContext,"C_PD_LST_7");
			}
			type = 0;
			content_title.setVisibility(View.GONE);
			listView_country.setVisibility(View.VISIBLE);
			if(countryAdapter!=null){
			countryAdapter.setNewData(listregio);
			countryAdapter.notifyDataSetChanged();
			listView_country.setAdapter(countryAdapter);
			}
			setText("国家");
			break;
		case R.id.area_content:
			if("1".equals(recommend)){
				MobclickAgent.onEvent(mContext,"C_PROM_LST_8");
			}else{
			if("1".equals(type))
				MobclickAgent.onEvent(mContext,"C_SAL_INF_2");
			else
				MobclickAgent.onEvent(mContext,"C_PD_LST_8");
			}
			type = 1;
			content_title.setVisibility(View.GONE);
			listView_country.setVisibility(View.VISIBLE);
			// 产区
			listView_country.setAdapter(areaAdapter);
			if(areaAdapter!=null){
			areaAdapter.setNewData(listregio.get(currtPosition).getRegion());
			areaAdapter.notifyDataSetChanged();
			}
			setText("产区");
			iv_back.setVisibility(View.VISIBLE);

			break;
		case R.id.tv_brand:
			if("1".equals(recommend)){
				MobclickAgent.onEvent(mContext,"C_PROM_LST_9");
			}else{
			if("1".equals(type))
				MobclickAgent.onEvent(mContext,"C_SAL_INF_3");
			else
				MobclickAgent.onEvent(mContext,"C_PD_LST_9");
			}
			content_title.setVisibility(View.GONE);
			listView_country.setVisibility(View.VISIBLE);
			listView_country
					.setAdapter(new MyBrandAdapter(listbrand, mContext));
			setText("品类");
			iv_back.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_back:
			setBackText("筛选");
			iv_back.setVisibility(View.INVISIBLE);
			setplacecheck();
			setbrandcheck();
			listView_country.setVisibility(View.GONE);
			content_title.setVisibility(View.VISIBLE);
			break;
		case R.id.linear_back:
			setBackText("筛选");
			iv_back.setVisibility(View.INVISIBLE);
			setplacecheck();
			setbrandcheck();
			listView_country.setVisibility(View.GONE);
			content_title.setVisibility(View.VISIBLE);
			break;
		}
	}

	private void CloseCheck() {
		Intent intent = new Intent();
		intent.putExtra("areaId", area_id == null ? country_id : area_id);
		intent.putExtra("brand_id", brand_id);
		setResult(RESULT_OK, intent);
		finish();
	}

	private void setText(String text) {
		production_bt.setText(text);
		button_cancle.setVisibility(View.GONE);
		sure_bt.setVisibility(View.INVISIBLE);
		iv_back.setVisibility(View.VISIBLE);
	}

	private void setBackText(String text) {
		production_bt.setText(text);
		button_cancle.setVisibility(View.VISIBLE);
		sure_bt.setVisibility(View.VISIBLE);
		iv_back.setVisibility(View.GONE);
	}

	

	/**
	 * 国家适配器
	 * */
	public class MyCountryAdapter extends BaseListAdapter<Region> {

		private MyCountryAdapter(List<Region> list, Context mContext) {
			super(list, mContext);
		}

		@Override
		public View bindView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = baseInflater.inflate(R.layout.country_item,
						parent, false);
			}
			TextView tv_conutry = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_conutry);
			tv_conutry.setText(dataList.get(position).getCname());
			final int index = position;
			tv_conutry.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					content_title.setVisibility(View.VISIBLE);
					listView_country.setVisibility(View.GONE);
					country_id = dataList.get(index).getCid();
					country_st = dataList.get(index).getCname();
					tv_country.setText(country_st);
					int size = dataList.size();
					for (int i = 0; i < size; i++) {
						if (dataList.get(i).getCid().equals(country_id)) {
							currtPosition = i;
							dataList.get(i).setCheck(true);
						} else {
							dataList.get(i).setCheck(false);
						}
						if(country_id.equals(""))
							dataList.get(i).setCheck(true);
					}
					MyApplication.getInstance().setListplace(dataList);//存储当前选中的数据到内存
					setBackText("筛选");
				}
			});
			return convertView;
		}
	}

	/**
	 * 产区适配器
	 * */
	public class MyAreaAdapter extends BaseListAdapter<RegionList> {

		private MyAreaAdapter(List<RegionList> list, Context mContext) {
			super(list, mContext);
		}

		@Override
		public View bindView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = baseInflater.inflate(R.layout.country_item,
						parent, false);
			}
			TextView tv_conutry = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_conutry);
			tv_conutry.setText(dataList.get(position).getRegionname());
			tv_conutry.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					content_title.setVisibility(View.VISIBLE);
					listView_country.setVisibility(View.GONE);
					area_id = dataList.get(position).getRegionid();
					area_st = dataList.get(position).getRegionname();
					tv_production.setText(area_st);
					for (RegionList regionlist : dataList) {
						if (area_id.equals(regionlist.getRegionid()))
							regionlist.setCheck(true);
						else
							regionlist.setCheck(false);
						if(area_id.equals(""))
							regionlist.setCheck(true);
					}
					MyApplication.getInstance().setListplace(listregio);//存储当前选中的数据到内存
					setBackText("筛选");
				}
			});
			return convertView;
		}
	}

	//品类数据
	public class MyBrandAdapter extends BaseListAdapter<BrandBean> {
		private MyBrandAdapter(List<BrandBean> list, Context mContext) {
			super(list, mContext);
		}

		@Override
		public View bindView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = baseInflater.inflate(R.layout.country_item,
						parent, false);
			}
			TextView tv_conutry = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_conutry);
			tv_conutry.setText(dataList.get(position).getName());
			tv_conutry.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					content_title.setVisibility(View.VISIBLE);
					listView_country.setVisibility(View.GONE);
					brand_id = dataList.get(position).getId();
					for (BrandBean brand : dataList) {
						if (brand_id.equals(brand.getId()))
							brand.setCheck(true);
						else
							brand.setCheck(false);
						if(brand_id.equals(""))
							brand.setCheck(true);
					}
					MyApplication.getInstance().setListbrand(dataList);
					tv_brand.setText(dataList.get(position).getName());
					setBackText("筛选");
				}
			});
			return convertView;
		}
	}

}
