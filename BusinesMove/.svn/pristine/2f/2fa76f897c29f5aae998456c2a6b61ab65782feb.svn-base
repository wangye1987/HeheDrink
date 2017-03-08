package com.heheys.ec.controller.activity; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.GroupBuyBrandAdapter;
import com.heheys.ec.model.adapter.GroupBuyBrandAdapter.CheckBrandListener;
import com.heheys.ec.model.dataBean.BrandWineBaseBean;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean.Productlist.BrandList;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.CharacterParser;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.DeleteEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-25 下午5:22:38 
 * 类说明 
 * @param 
 */
public class BrandWineActivity extends BaseActivity implements CheckBrandListener{
	private BrandWineBaseBean drinksDemandBean;
	private GridView brandGvGridView;
	private List<BrandList> brandLists;
//	private String pid = "";
	private String buyorsale = "";
	private BrandHandler brandHandler;
	public String checkBrandId;
	public String drinksTypeId;
	private List<BrandList> sourceDataList;
	private CharacterParser characterParser;
	private List<String> mList;
	private DeleteEditText etInput;
	private GroupBuyBrandAdapter brandAdapter;
	private ArrayList<BrandList> newData;
	private List<BrandList> brandData;
	private ArrayList<BrandList> brandName;
	private int flag;
	private RelativeLayout relative_brand;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.groupbuy_list_brand);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		brandGvGridView = (GridView) findViewById(R.id.groupbuy_list_brand_gv);
		relative_brand = (RelativeLayout) findViewById(R.id.relative_brand);
		etInput = (DeleteEditText) findViewById(R.id.check_city_search_et);
		brandHandler = new BrandHandler(this);
		initData();
		relative_brand.setVisibility(View.VISIBLE);
	}
	@SuppressWarnings("unchecked")
	private void initData() {
		// TODO Auto-generated method stub
		checkBrandId = "0";
		characterParser = CharacterParser.getInstance();
		Intent intent = getIntent();
		if(intent!=null){
			buyorsale = intent.getStringExtra("buyorsale");
			brandName = (ArrayList<BrandList>) intent.getSerializableExtra("wineTypeName");
			sourceDataList = changeSourceData(brandName);
			brandAdapter = new GroupBuyBrandAdapter(brandGvGridView,buyorsale,brandName, baseActivity, this);
			brandGvGridView.setAdapter(brandAdapter);
			flag = 0;
		}
		brandAdapter.setCheckListener(this);
	}
	@Override
	public void checkCallBack(BrandList data) {
		if(data != null){
			if(0 == flag){
				drinksTypeId = data.getId();
				flag = 1;
				if(ToastNoNetWork.ToastError(baseContext))
					return;	
				if(StringUtil.isEmpty(drinksTypeId)){
					ToastUtil.showToast(baseContext, "数据异常请稍后重试");
					return;	
				}
				ApiHttpCilent.getInstance(baseContext).getDrinksBrandList(baseActivity, drinksTypeId,new AdverRequestCallBack());
			}else if(1 ==flag){
				checkBrandId = data.getId();
				Intent intent = new Intent();
				data.setDrinksTypeId(drinksTypeId);
				intent.putExtra("bean",data);
				baseActivity.setResult(RESULT_OK, intent);
				baseActivity.finish();
				}
			}
	}
	private void bindBrandData() {
		brandAdapter = new GroupBuyBrandAdapter(brandGvGridView,buyorsale,
					brandLists, baseActivity, this);
			brandGvGridView.setAdapter(brandAdapter);
			brandAdapter.setCheckListener(this);
			sourceDataList = changeSourceData(brandLists);
			
	}
	private List<BrandList> changeSourceData(List<BrandList>  data) {
		List<BrandList> mSortList = new ArrayList<BrandList>();

		for (int i = 0; i < data.size(); i++) {
			BrandList sortModel = new BrandList();
			sortModel.setNamefilter(data.get(i).getName());
			sortModel.setName(data.get(i).getName());
			sortModel.setHotBrand(data.get(i).getHotBrand());
			sortModel.setId(data.get(i).getId());
			// 地址汉字转换成拼音
			String pinyin = characterParser.getSelling(data.get(i).getName());
			String abbreviation = getAbbreviation(data.get(i).getName());
			sortModel.setAbbreviation(abbreviation);
			// 截取拼音首位将字母转换成大写
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}
	private String getAbbreviation(String string) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		if (string != null) {
			char[] singleName = string.toCharArray();
			for (char ch : singleName) {
				String pinyin = characterParser.getSelling(ch + "");
				if (pinyin != null) {
					sb.append(pinyin.charAt(0));
				}
			}
		} else {
			return "";
		}
		return sb.toString();
	}
	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		
	}
	private void Dimess() {
		BrandWineActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	public class AdverRequestCallBack extends
			BaseJsonHttpResponseHandler<BrandWineBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BrandWineBaseBean arg4) {
			// ApiHttpCilent.hideLoading();
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			brandHandler.sendMessage(message);
		}
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BrandWineBaseBean arg3) {
			Dimess();
		}
		@Override
		protected BrandWineBaseBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dimess();
			Gson gson = new Gson();
			drinksDemandBean = gson.fromJson(response,
					BrandWineBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(drinksDemandBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = drinksDemandBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = drinksDemandBean.getError().getInfo();
			}
			brandHandler.sendMessage(message);
			return drinksDemandBean;
		}
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		super.onClick(v);
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
			HashMap<String,String> map = new HashMap<String,String>();
			if(0==flag){
				// 返回键处理
				onBackPressed();
			}else{
				brandAdapter.setNewData(brandName);
				brandGvGridView.setAdapter(brandAdapter);
				flag = 0;
			}
			if("buy".equals(buyorsale)){
				map.put("Buy_brand_return","");
				MobclickAgent.onEvent(baseContext, "Buy_brand_return", map); 
			}else{
				map.put("Sell_brand_return","");
				MobclickAgent.onEvent(baseContext, "Sell_brand_return", map); 
			}
			break;

		default:
			break;
		}
	}
	/**
	 * 
	 * Describe:获取广告数据处理网络请求消息
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 */
	public static class BrandHandler extends WeakHandler<BrandWineActivity> {

		public BrandHandler(BrandWineActivity reference) {
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
				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference().baseActivity,
							messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					// ToastUtil.showToast(getReference().baseActivity, "请求失败");

				}

				break;
			}
		}

	}
	
	private void bindViewData(){
		if(drinksDemandBean.getResult().getList()!=null && drinksDemandBean.getResult().getList().size()>0){
			brandLists = drinksDemandBean.getResult().getList();
			bindBrandData();
		}else{
			ToastUtil.showToast(baseContext, "数据异常,请稍后重试");
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		etInput.setOnClickListener(this);
		etInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				searchData(s.toString());
				HashMap<String,String> map = new HashMap<String,String>();
				if("buy".equals(buyorsale)){
					map.put("Buy_brand_search","");
					MobclickAgent.onEvent(baseActivity, "Buy_brand_search", map); 
				}else{
					map.put("Sell_brand_search","");
					MobclickAgent.onEvent(baseActivity, "Sell_brand_search", map); 
					
				}
			}
		});
	}
	public void searchData(String filterStr) {

		if (TextUtils.isEmpty(filterStr)) {
			// 输入为空显示初始数据
			brandAdapter.setNewData(sourceDataList);
			brandAdapter.notifyDataSetChanged();
			return;
		} else {
			brandData = new ArrayList<BrandList>();
			newData = new ArrayList<BrandList>();
			for (BrandList sortModel : sourceDataList) {
				String name = sortModel.getName();
				String abbreviation = sortModel.getAbbreviation();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name)
								.startsWith(filterStr.toString())
						|| abbreviation.equals(filterStr)) {

					newData.add(sortModel);
				}
			}
			if (newData.size() == 0) {
				ToastUtil.showToast(baseContext, "没有搜索到该品牌的酒");
				brandData.clear();
				brandAdapter.setNewData(brandData);
				brandAdapter.notifyDataSetChanged();
				return;
			}else{
				brandAdapter.setNewData(newData);
				brandAdapter.notifyDataSetChanged();
			}
		}
//		filterDateList = newData;
//		searchAdaper.setNewData(filterDateList);
//		searchAdaper.notifyDataSetChanged();
		/**
		 * 设置为可搜索状态
		 */
		// CheckCityActivity.tvCancle.setText("搜索");
		// tagIsSearch = true;
	}
	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "品牌";
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
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
 