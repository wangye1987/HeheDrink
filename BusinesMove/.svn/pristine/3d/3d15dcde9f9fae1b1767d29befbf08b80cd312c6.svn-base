package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.TimeUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.GridViewScrollview;
import com.heheys.ec.model.adapter.DrinksDemandTypeAdapter;
import com.heheys.ec.model.adapter.DrinksDemandTypeAdapter.WineTypeCheckListener;
import com.heheys.ec.model.dataBean.AddBackBean;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BusinessDataBean;
import com.heheys.ec.model.dataBean.DemandDetailBaseBean;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.BrandList;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.Bean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ProvinceCityCounty;
import com.heheys.ec.utils.ProvinceCityCounty.MyCallBack;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:买酒需求页面
 *
 * Date:2015年11月25日上午11:38:29
 *
 * Author:LZL
 *
 */
public class DrinksDemandBuyActivity extends BaseActivity implements
		WineTypeCheckListener {
	private RelativeLayout unitCheckParent, startTimeParent, endTimeParent;
	private TextView tvUnit, tvStartTime, tvEndTime;
	private int year, month, day;
	private GridViewScrollview typeGv;
	private DrinksDemandTypeAdapter brandAdapter;
	private static MessageHandler messageHandler;
	private static AddDrinksDemandMessageHandler addDrinksDemandMessageHandler;
	private static DemandDetailMessageHandler demandDetailMessageHandler;//获取需求详情
	private MyCallBackType callBack;
	private MyCallBackDetail callBackdetail;
	private RelativeLayout my_demand_add;// 买货单地址选择
	private AddBackBean bean;
	private TextView demand_add;
	private TextView my_brand_tv;
	private RelativeLayout my_brand_relativeLayout;
	private BrandList brandList;
	private static Map<String, String> winetype;
	private static Map<String, String> wineUnit;
	//解析地址id 获取中文名称
	ArrayList<AddBackBean> add_list = new ArrayList<AddBackBean>();
	private ArrayList<String> list_name = new ArrayList<String>();// 名字集合
	// 酒分类名称
	private List<BrandList> wineTypeName;//存储酒类数据bean
	// 酒分类名称ID
	private List<String> wineTypeID;
	// 酒类单位ID
	private List<String> wineTypeUnitID;
	// 酒类单位名称
	private ArrayList<CharSequence> wineTypeUnitName;
	// 选中的酒分类ID
	private String wineTypeId;
	// 选中酒的单位
	private String checkWineUnit;
	// 选中区域ID
	private String checkCityId;
	private EditText etDrinksName, etDrinksNum, etDrinksPrice, etRemark;
	private String startTime,endTime;
	private long startTimeInt;
	private long startTimeInt_simple;
	private long endTimeInt;
	private EditText drink_degress;
	String drinksBrandId;
	boolean isClick  = true;
	private static DemandDetailBaseBean drinksData;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.drinks_demand_buy);
		initData();
	}

	/**
	 * 
	 * Describe:初始化数据
	 *
	 * Date:2015年11月26日下午3:05:21
	 *
	 * Author:LZL
	 *
	 */
	private void initData() {
		addDrinksDemandMessageHandler = new AddDrinksDemandMessageHandler(this);
		demandDetailMessageHandler = new DemandDetailMessageHandler(this);
		callBackdetail = new MyCallBackDetail();
		callBack = new MyCallBackType();
		messageHandler = new MessageHandler(this);
		wineTypeID = new ArrayList<String>();
		wineTypeName = new ArrayList<BrandList>();
		wineTypeUnitID = new ArrayList<String>();
		wineTypeUnitName = new ArrayList<CharSequence>(); 
		Intent intent = getIntent();
		if(intent!=null){
			buyorsale = intent.getStringExtra("buyorsale");
			itemId = intent.getStringExtra("beanitem");//获取
			if("buy".equals(buyorsale)){
				if(itemId != null){
					ApiHttpCilent.getInstance(baseContext).DrinksDemandDetail(baseContext, callBackdetail, itemId);
				}else{
					CanEable();
					ApiHttpCilent.getInstance(baseContext).getBusinessData(baseContext,callBack);
				}
				tv_district_notice.setText("送货地区");
				tv_price_notice.setText("求购单价");
			}else if("sale".equals(buyorsale)){
				//卖货单界面处理
				tv_district_notice.setText("供货区域");
				tv_price_notice.setText("供货单价");
				tvTitleName.setText("卖货单");
				if( itemId != null){
					ApiHttpCilent.getInstance(baseContext).DrinksDemandDetail(baseContext, callBackdetail, itemId);
				}else{
					callBack = new MyCallBackType();
					CanEable();
					ApiHttpCilent.getInstance(baseContext).getBusinessData(baseContext,callBack);
				}
			}
		}
	}

	private void CanEable() {
		tvRight.setVisibility(View.VISIBLE);
		method = 2;
		setNotEnable(tvRight);
		setNotEnable(etRemark);
		setNotEnable(etDrinksPrice);
		setNotEnable(etDrinksNum);
		setNotEnable(etDrinksName);
		setNotEnable(drink_degress);
		setNotEnable(tvStartTime);
		setNotEnable(tvEndTime);
		setNotEnable(demand_add);
		setNotEnable(my_brand_tv);
		setNotEnable(tvUnit);
		setNotEnable(unitCheckParent);
		setNotEnable(my_demand_add);
		setNotEnable(startTimeParent);
		setNotEnable(endTimeParent);
		setNotEnable(my_brand_relativeLayout);
	}

	//获取买货单详情
	private void SetData(){
		etRemark.setText(drinksData.getResult().getRemark());
		etDrinksPrice.setText(drinksData.getResult().getDrinksPrice()+"");
		etDrinksNum.setText(drinksData.getResult().getDrinksNums());
		etDrinksName.setText(drinksData.getResult().getDrinksName());
		drink_degress.setText(drinksData.getResult().getDegree());
		tvStartTime.setText(drinksData.getResult().getStartTime());
		tvEndTime.setText(drinksData.getResult().getEndTime());
			if("buy".equals(buyorsale)){
				demand_add.setText(drinksData.getResult().getDeliverGoodsAreaName());
				setEnable(my_demand_add);
			}else{
				setNotEnable(my_demand_add);
				String add_name = drinksData.getResult().getDeliverGoodsAreaIdsArray();
				if(!add_name.equals("全国")){
					ParserCityName(add_name);
					demand_add.setText("点击查看详情");
				}else{
					demand_add.setText("全国");
					setEnable(my_demand_add);
				}
			}
		my_brand_tv.setText(drinksData.getResult().getBrandName());
		tvUnit.setText(drinksData.getResult().getNumsUnit());
		checkCityId  = drinksData.getResult().getDeliverGoodsAreaIds();
		wineTypeId  = drinksData.getResult().getDrinksTypeId();
		drinksBrandId  = drinksData.getResult().getBrandId();
		checkWineUnit = drinksData.getResult().getNumsUnit();
		startTime = drinksData.getResult().getStartTime();
		endTime = drinksData.getResult().getEndTime();
		Replace(startTime,1);
		Replace(endTime,2);
		
		setEnable(tvRight);
		setEnable(etRemark);
		setEnable(etDrinksPrice);
		setEnable(etDrinksNum);
		setEnable(etDrinksName);
		setEnable(drink_degress);
		setEnable(tvStartTime);
		setEnable(tvEndTime);
		setEnable(demand_add);
		setEnable(my_brand_tv);
		setEnable(tvUnit);
		
		setEnable(unitCheckParent);
		setEnable(startTimeParent);
		setEnable(endTimeParent);
		setEnable(my_brand_relativeLayout);
	}

	//解析获取当前选中城市中文名称
	private void ParserCityName(String add_name) {
		//解析返回回来的地址数据id
//		[{"4":[53, 54, 55]}, {"6":[78, 79, 80, 81, 83]}]
		JSONArray objectArray;
			try {
				objectArray = new JSONArray(add_name);
				add_list.clear();
				for(int i=0;i<objectArray.length();i++){
					String st = objectArray.get(i).toString();
				    JSONObject object = new JSONObject(st);
					Iterator<String> iterator = object.keys();
					AddBackBean addbackbean = new AddBackBean();
					 while (iterator.hasNext()) {
						 String key = iterator.next().toString();
						 addbackbean.setProviceid(key);
						 ArrayList<String> list_id = new ArrayList<String>();// 地区id集合
						 JSONArray values = (JSONArray) object.get(key);
							for(int k=0;k<values.length();k++){
								Log.i("values","key"+key+"value:"+values.get(k).toString());
								list_id.add(values.get(k).toString());
								addbackbean.setList_id(list_id);
							}
					 }
					 add_list.add(addbackbean);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			new ProvinceCityCounty(baseActivity, new MyCallBack() {
				@Override
				public void setDate(Bean province) {
					// TODO Auto-generated method stub
				for(int i = 0;i< add_list.size();i++){
					//获取城市中文名称
				List<String> data = new ProvinceCityCounty().GetCityName(Integer.parseInt(add_list.get(i).getProviceid()),add_list.get(i).getList_id(),province);
				String provice_Name = new ProvinceCityCounty().GetProviceName(Integer.parseInt(add_list.get(i).getProviceid()),province);
				add_list.get(i).setList_name(data);
				add_list.get(i).setProivinceName(provice_Name);
				}
				}
			});
	}
	
	private void setEnable(View view){
		view.setEnabled(false);
	}
	private void setNotEnable(View view){
		view.setEnabled(true);
	}
	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		etRemark = (EditText) findViewById(R.id.drinks_demand_buy_remark);
		etDrinksPrice = (EditText) findViewById(R.id.drinks_demand_buy_price);
		etDrinksNum = (EditText) findViewById(R.id.drinks_demand_buy_num);
		etDrinksName = (EditText) findViewById(R.id.drinks_demand_buy_name);
		drink_degress = (EditText) findViewById(R.id.drinks_demand_buy_degress);
		typeGv = (GridViewScrollview) findViewById(R.id.drinks_demand_buy_type_gv);
		tvStartTime = (TextView) findViewById(R.id.drinks_demand_buy_starttime_tv);
		tvEndTime = (TextView) findViewById(R.id.drinks_demand_buy_endtime_tv);
		unitCheckParent = (RelativeLayout) findViewById(R.id.drinks_demand_buy_unit_parent);
		my_demand_add = (RelativeLayout) findViewById(R.id.my_demand_add);
		my_brand_relativeLayout = (RelativeLayout) findViewById(R.id.my_brand_RelativeLayout);
		demand_add = (TextView) findViewById(R.id.demand_add);
		my_brand_tv = (TextView) findViewById(R.id.my_brand_tv);
		tvUnit = (TextView) findViewById(R.id.drinks_demand_buy_unit_tv);
		tv_district_notice = (TextView) findViewById(R.id.tv_district_notice);
		tv_price_notice = (TextView) findViewById(R.id.tv_price_notice);
		tv_starttime_notice = (TextView) findViewById(R.id.tv_starttime_notice);
		tv_endtime_notice = (TextView) findViewById(R.id.tv_endtime_notice);
		startTimeParent = (RelativeLayout) findViewById(R.id.drinks_demand_buy_starttime_parent);
		endTimeParent = (RelativeLayout) findViewById(R.id.drinks_demand_buy_endtime_parent);
		linear_address = (LinearLayout) findViewById(R.id.linear_address);
		tvRight.setText("提交");
		tvRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if("buy".equals(buyorsale)){
					//添加买货需求
					addDrinksDemand("1","buy");
				}else if("sale".equals(buyorsale)){
					//添加卖货需求
					addDrinksDemand("0","sale");
				}
			}
		});
		etRemark.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HashMap<String,String> map = new HashMap<String,String>();
				if("buy".equals(buyorsale)){
					map.put("Buy_note","");
					MobclickAgent.onEvent(baseActivity, "Buy_note", map); 	
				}else{
					map.put("Sell_note","");
					MobclickAgent.onEvent(baseActivity, "Sell_note", map); 	
					
				}
			}
		});
		if("sale".equals(buyorsale))
			setText("供货区域","供货价格","开始供货时间","结束供货时间");
		else
			setText("送货区域","送货价格","需求开始时间","需求结束时间");
	}
	
	private void setText(String st1,String st2,String st3,String st4){
		tv_district_notice.setText(st1);
		tv_price_notice.setText(st2);
		tv_starttime_notice.setText(st3);
		tv_endtime_notice.setText(st4);
	}

	@Override
	protected void getNetData() {
	}
	
	
	
	void Dimess(){
	DrinksDemandBuyActivity.this.runOnUiThread(new Runnable() {
		public void run() {
			if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
				ApiHttpCilent.loading.dismiss();
		}
	});
	}
	/**
	 * 
	 * 获取酒分类数据回调
	 *
	 */
	public  class MyCallBackType extends
			BaseJsonHttpResponseHandler<BusinessDataBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BusinessDataBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BusinessDataBean arg3) {
		}

		@Override
		protected BusinessDataBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			BusinessDataBean businessData = gson.fromJson(response,
					BusinessDataBean.class);
			winetype = businessData.getResult().getWinetype();
			wineUnit = businessData.getResult().getCountunit();
			Message message = Message.obtain();
			if ("1".equals(businessData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = businessData.getResult();
			} else if ("1".equals(businessData.getStatus())) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = businessData.getError().getInfo();
			}
			messageHandler.sendMessage(message);

			return businessData;
		}
	}
	
	
	//获取详情数据
	public  class MyCallBackDetail extends
	BaseJsonHttpResponseHandler<DemandDetailBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, DemandDetailBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			demandDetailMessageHandler.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				DemandDetailBaseBean arg3) {
		}
		
		@Override
		protected DemandDetailBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			drinksData = gson.fromJson(response,
					DemandDetailBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(drinksData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = drinksData.getResult();
			} else if ("1".equals(drinksData.getStatus())) {
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = drinksData.getError().getInfo();
			}
			demandDetailMessageHandler.sendMessage(message);
			
			return drinksData;
		}
	}

	public static class MessageHandler extends
			WeakHandler<DrinksDemandBuyActivity> {

		public MessageHandler(DrinksDemandBuyActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				/**
				 * 酒分类数据
				 */
				if (winetype != null) {

					Set<String> data = winetype.keySet();
					if (data != null && data.size() > 0) {
						for (String str : data) {
							BrandList brandList = new BrandList();
							brandList.setId(str);
							brandList.setName(winetype.get(str));
							getReference().wineTypeName.add(brandList);
//							getReference().wineTypeID.add(str);
//							getReference().wineTypeName.add(winetype.get(str));
						}
						// getReference().bindViewData();

					} else {
						/**
						 * 酒类分类数据为空
						 */
					}
				} else {
					/**
					 * 酒类分类数据为空
					 */
				}
				/*
				 * 酒单位数据
				 */
				if (wineUnit != null) {

					Set<String> data = wineUnit.keySet();
					if (data != null && data.size() > 0) {
						for (String str : data) {
							getReference().wineTypeUnitID.add(str);
							getReference().wineTypeUnitName.add(wineUnit
									.get(str));
						}
						getReference().bindViewData();

					} else {
						/**
						 * 酒类单位数据为空
						 */
					}
				} else {
					/**
					 * 酒类单位数据为空
					 */
				}
				break;
			case ConstantsUtil.HTTP_FAILE:
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					ToastUtil.showToast(getReference(), "请求失败");
					// getReference().showLoadFailView();
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 * Describe:绑定数据
	 *
	 * Date:2015年11月26日上午11:19:45
	 *
	 * Author:LZL
	 *
	 */
	private void bindViewData() {
		if (wineTypeName != null && wineTypeName.size() > 0) {
//			brandAdapter = new DrinksDemandTypeAdapter(wineTypeName,
//					baseContext, typeGv);
//			typeGv.setAdapter(brandAdapter);
//			brandAdapter.setWineTypeCheckListener(this);
		} else {
			typeGv.setVisibility(View.GONE);
		}
		
	}

	/**
	 * 
	 * Describe:添加酒水需求
	 *
	 * Date:2015年11月26日下午3:10:16
	 *
	 * Author:LZL
	 *
	 */
	private void addDrinksDemand(String type,String buyorsale) {
		
		callBacks = new AddDrinksDemandHandler();
//		Form form = new Form();
		//验证酒名称不能为空
//		Validate validate_name = new Validate(etDrinksName);
//		validate_name.addValidator(new NotEmptyValidator(this));
		//验证度数不能为空
//		Validate validate_degress = new Validate(drink_degress);
//		validate_degress.addValidator(new NotEmptyValidator(this));
		//验证数量不能为空
//		Validate validate_winnnum = new Validate(etDrinksNum);
//		validate_winnnum.addValidator(new NotEmptyValidator(this));
		//验证单位不能为空
//		Validate validate_unit = new Validate(tvUnit);
//		validate_unit.addValidator(new NotEmptyValidator(this));
		//验证价格不能为空
//		Validate validate_buy = new Validate(etDrinksPrice);
//		validate_buy.addValidator(new NotEmptyValidator(this));
		//验证地址不能为空
//		Validate validate_destance = new Validate(demand_add);
//		validate_destance.addValidator(new NotEmptyValidator(this));
		//验证开始不能为空
//		Validate validate_starttime = new Validate(tvStartTime);
//		validate_starttime.addValidator(new NotEmptyValidator(this));
		//验证结束不能为空
//		Validate validate_endtime = new Validate(tvEndTime);
//		validate_endtime.addValidator(new NotEmptyValidator(this));
		
		if (etDrinksName ==null || etDrinksName.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写酒的名称");
			return;
		}
		if (drink_degress ==null || drink_degress.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写酒的度数");
			return;
		}
		if (etDrinksNum ==null || etDrinksNum.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写酒的数量");
			return;
		}
		if (tvUnit ==null || tvUnit.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写酒的规格");
			return;
		}
		if (etDrinksPrice ==null || etDrinksPrice.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写酒的价格");
			return;
		}
		if (demand_add ==null || demand_add.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写地址");
			return;
		}
		if (tvStartTime ==null || tvStartTime.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写开始时间");
			return;
		}
		if (tvEndTime ==null || tvEndTime.equals("")) {
			ToastUtil.showToast(baseContext, "请您填写结束时间");
			return;
		}
	
		if (drinksBrandId ==null || drinksBrandId.equals("")) {
			ToastUtil.showToast(baseContext, "请您先选择酒类品牌");
			return;
		}
//		form.addValidates(validate_name);
//		form.addValidates(validate_degress);
//		form.addValidates(validate_winnnum);
//		form.addValidates(validate_unit);
//		form.addValidates(validate_buy);
//		form.addValidates(validate_destance);
//		form.addValidates(validate_starttime);
//		form.addValidates(validate_endtime);
//		if(!form.validate())
//			return;
		if (etDrinksName.getText().toString().trim().length()<2) {
			ToastUtil.showToast(baseContext, "名称填写错误");
			return;
		}
		if (Float.parseFloat(drink_degress.getText().toString().trim())<=0 || Float.parseFloat(drink_degress.getText().toString().trim())>100) {
			ToastUtil.showToast(baseContext, "请您填正确的度数");
			return;
		}
		if (StringUtil.isEmpty(checkWineUnit)) {
			ToastUtil.showToast(baseContext, "请您先酒的单位");
			return;
		}
		if (StringUtil.isEmpty(checkCityId)) {
			ToastUtil.showToast(baseContext, "请您先选中配送区域");
			return;
		}
		if (StringUtil.isEmpty(startTime)) {
			ToastUtil.showToast(baseContext, "请您先选择开始时间");
			return;
		}
		if (StringUtil.isEmpty(endTime)) {
			ToastUtil.showToast(baseContext, "请您先选择结束时间");
			return;
		}
		Replace(startTime,1);
		Replace(endTime,2);
		if(buyorsale.equals("buy")){
			tvRight.setEnabled(false);
			RequestNet(type);
		}else if(buyorsale.equals("sale")){
			tvRight.setEnabled(true);
			RequestNet(type);
		}
	}
	private void RequestNet(String type){
		String data = "";
		HashMap<String,String> map = new HashMap<String,String>();
		if("1".equals(type)){
			map.put("Buy_Submit","");
			MobclickAgent.onEvent(baseActivity, "Buy_Submit", map); 	
		}else{
			map.put("Sell_Submit","");
			MobclickAgent.onEvent(baseActivity, "Sell_Submit", map); 	
			data = addBackBean.getId_group().toString();
			if(data != null && !data.equals("")){
				data = data.replace("{", "{\"");
				data = data.replace("=", "\":");
			}
			if(checkCityId.equals("1")){
				data = "全国";
			}
		}
		/*	[{ "4": [58, 57,56,55]}, { "4": [58, 57,56,55]}]*/
		ApiHttpCilent.getInstance(baseContext).addDrinksDemand(baseContext,
				callBacks, wineTypeId, drinksBrandId, drink_degress.getText().toString().trim(),
				etDrinksName.getText().toString(),
				etDrinksNum.getText().toString(), checkWineUnit,
				etDrinksPrice.getText().toString(), checkCityId+"", startTime, endTime,
				type, etRemark.getText().toString(),data);
	}

	private void Replace(String stName,int flag){
		if(stName.contains("年"))
			stName = stName.replace("年", "-");
		if(stName.contains("月"))
			stName = stName.replace("月", "-");
		if(stName.contains("日"))
			stName = stName.replace("日", "");
		if(1==flag)
			startTime = stName;
		else if(2==flag)
			endTime = stName;
	}
	/**
	 * 
	 * @author Administrator 添加酒水需求回调
	 */
	public  class AddDrinksDemandHandler extends
			BaseJsonHttpResponseHandler<BaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			addDrinksDemandMessageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_SUCCESS;// 错误
		}

		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			BaseBean businessData = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			
			if ("1".equals(businessData.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = businessData.getResult();
			}  else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = businessData.getError().getInfo();
			}
			addDrinksDemandMessageHandler.sendMessage(message);
			return businessData;
		}
	}

	/**
	 * 
	 * @author Administrator 添加酒水需求消息处理
	 */
	public static class AddDrinksDemandMessageHandler extends
			WeakHandler<DrinksDemandBuyActivity> {

		public AddDrinksDemandMessageHandler(DrinksDemandBuyActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				Intent intent=new Intent();
				intent.putExtra("RESULT", "OK");
				getReference().setResult(RESULT_OK, intent);
                getReference().finish();
                getReference().tvRight.setEnabled(true);
                getReference().clearDate();//清除本地缓存的地址数据
				break;
			case ConstantsUtil.HTTP_FAILE:
				getReference().tvRight.setEnabled(true);
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					ToastUtil.showToast(getReference(), "请求失败");
					// getReference().showLoadFailView();
				}
				break;
			default:
				break;
			}
		}
	}
	
	public static class DemandDetailMessageHandler extends
	WeakHandler<DrinksDemandBuyActivity> {
		
		public DemandDetailMessageHandler(DrinksDemandBuyActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}
		
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().method = 1;
				getReference().SetData();
				getReference().tvRight.setVisibility(View.GONE);
				break;
			case ConstantsUtil.HTTP_FAILE:
				getReference().tvRight.setVisibility(View.GONE);
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
			default:
				break;
			}
		}
	}
	//清除本地存储选择的城市数据
		private void clearDate() {
			 ArrayList<AddBackBean> list_data_cache = new ArrayList<AddBackBean>();// 清除本地缓存的数据
			SharedPreferencesUtil.saveObject(baseContext, list_data_cache,"db_districe");
		}
	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		unitCheckParent.setOnClickListener(this);
		my_demand_add.setOnClickListener(this);
		startTimeParent.setOnClickListener(this);
		endTimeParent.setOnClickListener(this);
		my_brand_relativeLayout.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "买货单";
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
		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		Intent intent;
		switch (v.getId()) {
		case R.id.drinks_demand_buy_unit_parent:
			/**
			 * 进行单位选择
			 */
			 intent = new Intent(baseActivity,
					DrinksDemandUnitCheckActivity.class);
			intent.putCharSequenceArrayListExtra("NAME", wineTypeUnitName);
			intent.putExtra("buyorsale", buyorsale);
			StartActivityUtil
					.startActivityForResult(baseActivity, intent, 1001);
			// StartActivityUtil.startActivity(baseActivity, intent);
			break;
		case R.id.my_demand_add:
			/**
			 * 进行城市选择
			 */
			if(add_list.size()>0){
				//查询详情
				intent = new Intent(baseActivity,SaleDistanceActivity.class);
				intent.putExtra("add_list", add_list);
				StartActivityUtil.startActivity(baseActivity, intent);
			}else{
				if("buy".equals(buyorsale))
					Intent();
				else
					StartActivityUtil.startActivityForResult(baseActivity, SaleDistanceActivity.class,1002);
				if("buy".equals(buyorsale)){
					map.put("Buy_class","");
					MobclickAgent.onEvent(baseActivity, "Buy_class", map); 
				}else{
					map.put("Sell_class","");
					MobclickAgent.onEvent(baseActivity, "Sell_class", map); 
				}
			}
			break;
		case R.id.drinks_demand_buy_starttime_parent:
			/**
			 * 开始时间
			 */
			showDateDialog("start");
			break;
		case R.id.drinks_demand_buy_endtime_parent:
			/**
			 * 结束时间
			 */
			showDateDialog("end");
//			if("buy".equals(buyorsale)){
//				map.put("Buy_class","");
//				MobclickAgent.onEvent(baseActivity, "Buy_class", map); 
//			}else{
//				map.put("Sell_class","");
//				MobclickAgent.onEvent(baseActivity, "Sell_class", map); 
//			}
			break;
		case R.id.my_brand_RelativeLayout:
			/**
			 * 品牌选择
			 * 
			 */
			if("buy".equals(buyorsale)){
				map.put("Buy_class","");
				MobclickAgent.onEvent(baseActivity, "Buy_class", map); 
			}else{
				map.put("Sell_class","");
				MobclickAgent.onEvent(baseActivity, "Sell_class", map); 
			}
			if (wineTypeName!=null && wineTypeName.size()>0) {
				intent = new Intent(this, BrandWineActivity.class);
				intent.putExtra("buyorsale", buyorsale);
				intent.putExtra("wineTypeName", (ArrayList<BrandList>) wineTypeName);
				StartActivityUtil.startActivityForResult(baseActivity, intent,
						ConstantsUtil.REQUEST_CODE_TWO);
			} else {
				ToastUtil.showToast(baseContext, "品牌数据异常,请稍后重试");
			}

			break;
		case R.id.base_activity_title_backicon:
			// 返回键处理
			onBackPressed();
			if(buyorsale.equals("buy")){
					map.put("Buy_return","");
					MobclickAgent.onEvent(baseActivity, "Buy_return", map);
				}else{
					map.put("Sell_return","");
					MobclickAgent.onEvent(baseActivity, "Sell_return", map);
				}
			clearDate();
			break;
		default:
			break;
		}
	}

	private void Intent() {
		Intent intent = new Intent(baseActivity, DestanceChioceActivity.class);
		intent.putExtra("index", 1);// 区分是订单地址 0 还是买货的地址1 卖货单是2
		intent.putExtra("addBackBean",addBackBean);
		startActivityForResult(intent, ConstantsUtil.REQUEST_CODE);
	}

	private void showDateDialog(String type) {
		// 创建DatePickerDialog对象
		// 初始化Calendar日历对象

		Calendar mycalendar = Calendar.getInstance(Locale.CHINA);

		Date mydate = new Date(); // 获取当前日期Date对象

		mycalendar.setTime(mydate);// //为Calendar对象设置时间为当前日期

		year = mycalendar.get(Calendar.YEAR); // 获取Calendar对象中的年

		month = mycalendar.get(Calendar.MONTH);// 获取Calendar对象中的月

		day = mycalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天
		if ("start".equals(type)) {
			DatePickerDialog dpd = new DatePickerDialog(baseContext,
					dateListenerStart, year, month, day);
			if(dpd!=null)
			dpd.show();// 显示DatePickerDialog组件
		}
		if ("end".equals(type)) {
			DatePickerDialog dpd = new DatePickerDialog(baseContext,
					dateListenerEnd, year, month, day);
			if(dpd!=null)
			dpd.show();// 显示DatePickerDialog组件
		}

	}

	/**
	 * 开始时间监听器
	 */
	private DatePickerDialog.OnDateSetListener dateListenerStart = new DatePickerDialog.OnDateSetListener()

	{

		/**
		 * params：view：该事件关联的组件
		 * 
		 * params：myyear：当前选择的年
		 * 
		 * params：monthOfYear：当前选择的月
		 * 
		 * params：dayOfMonth：当前选择的日
		 */

		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {

			// 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			endTimeInt=0;
			tvEndTime.setText("请选择");
			endTime=null;
			year = myyear;

			month = monthOfYear;

			day = dayOfMonth;
			
			startTimeInt = TimeUtil.dateToTimeTwo(myyear + "-" + (monthOfYear + 1) + "-"+ dayOfMonth);
			if (startTimeInt  < TimeUtil.getEndDayMills()/1000) {
				ToastUtil.showToast(baseActivity, "开始时间需要从明天开始");
				return;
			}
			/*
			 * 修改日期修改后bug 当前startTimeInt数据修改了 但是界面UI没有修改
			 * 以下保证前端ui和数据变量值一致
			 * */
			startTimeInt_simple = startTimeInt;
			// 更新日期

			updateDate();

		}

		// 当DatePickerDialog关闭时，更新日期显示

		private void updateDate()

		{

			// 在TextView上显示日期
			tvStartTime.setText("开始时间：" + year + "-" + (month + 1) + "-" + day);
			startTime=year + "-" + (month + 1) + "-" + day;
		}

	};

	/**
	 * 结束时间监听器
	 */
	private DatePickerDialog.OnDateSetListener dateListenerEnd = new DatePickerDialog.OnDateSetListener()

	{

		/**
		 * params：view：该事件关联的组件
		 * 
		 * params：myyear：当前选择的年
		 * 
		 * params：monthOfYear：当前选择的月
		 * 
		 * params：dayOfMonth：当前选择的日
		 */

		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {

			// 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值

			if(startTimeInt==0)
			{
				ToastUtil.showToast(baseActivity, "请您先选择需求开始时间");
				return;
			}
			year = myyear;

			month = monthOfYear;

			day = dayOfMonth;
			endTimeInt = TimeUtil.dateToTimeTwo(myyear + "-" + (monthOfYear + 1) + "-"
					+ dayOfMonth);
			if(endTimeInt<startTimeInt_simple||endTimeInt==startTimeInt_simple){
				ToastUtil.showToast(baseActivity, "结束时间必须大于开始时间");
				return;
			}
			if((endTimeInt-startTimeInt_simple)>(60*60*24*365)){
				ToastUtil.showToast(baseActivity, "需求时间区间最多是365天");
				return;
			}
			// 更新日期
			updateDate();
		}

		// 当DatePickerDialog关闭时，更新日期显示

		private void updateDate()

		{

			// 在TextView上显示日期
			tvEndTime.setText("结束时间：" + year + "-" + (month + 1) + "-" + day);
			endTime=year + "-" + (month + 1) + "-" + day;
		}

	};
	private int method = 2;//method ==1 是修改接口 method ==2 是新增接口 
	private String buyorsale;
	private AddDrinksDemandHandler callBacks;
	private TextView tv_district_notice;
	private TextView tv_price_notice;
	private TextView tv_starttime_notice;
	private TextView tv_endtime_notice;
	private String add_sts[];
	private String itemId;
	private AddBackBean addBackBean;
	private LinearLayout linear_address;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, arg2);
		switch (requestCode) {
		case 1001:
			if (arg2 != null) {
				tvUnit.setText(arg2.getStringExtra("unit"));
				checkWineUnit = arg2.getStringExtra("unit");
			}

			break;
		case ConstantsUtil.REQUEST_CODE:
			/***
			 * 买货单地址回调
			 */
			if (arg2 != null) {
				addBackBean = (AddBackBean) arg2.getSerializableExtra("bean");
				demand_add.setText(addBackBean.getProivinceName() + addBackBean.getCityName());
				if(addBackBean.getId()!=null && !addBackBean.getId().equals(""))
				checkCityId = addBackBean.getId();
			}
			break;
		case ConstantsUtil.REQUEST_CODE_TWO:
			if (arg2 != null) {
				brandList = (BrandList) arg2.getSerializableExtra("bean");//获取酒类品牌信息
				my_brand_tv.setText(brandList.getName());
				drinksBrandId = brandList.getId();
				wineTypeId = brandList.getDrinksTypeId();
			}
			break;
		case 1002://卖货单地址回调
			if (arg2 != null) {
				addBackBean = (AddBackBean) arg2.getSerializableExtra("bean");
				checkCityId = addBackBean.getId();
				demand_add.setText(addBackBean.getCityName());
			}
			break;
		}
	}

	@Override
	public void checkWineType(int type) {
		// TODO Auto-generated method stub
//		ToastUtil.showToast(baseContext,
//				wineTypeID.get(type) + wineTypeName.get(type));
//		wineTypeId = wineTypeID.get(type);
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
