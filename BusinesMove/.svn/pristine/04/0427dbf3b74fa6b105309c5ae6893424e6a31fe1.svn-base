package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.AdapterDistrict;
import com.heheys.ec.model.adapter.AdapterDistrict.CallbackDistrict;
import com.heheys.ec.model.dataBean.AddBackBean;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BackRemark;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-12-3 下午1:52:13 类说明
 * @param 卖货单选择送货地址
 */
public class SaleDistanceActivity extends BaseActivity {

	private LinearLayout linear_title;
	private LinearLayout linear_address;
	private RadioButton rb_all;
	private RadioButton rb_chioce;
	private String tv_text;// 回调显示文本信息
	private StringBuffer id = new StringBuffer();// 回调显示文本信息
	AddBackBean addbean = new AddBackBean();
	private ArrayList<AddBackBean> list_data_db = new ArrayList<AddBackBean>();// 上传到服务器存储的数据
	private ArrayList<AddBackBean> list_data_cache = new ArrayList<AddBackBean>();// 本地缓存的数据
	private TextView my_country;	// 点击全国显示全国
	private TextView tv_notice;//提示语句
	private TextView tv_add;//添加区域文字
	//存储id集合 回填时候需要
	private ArrayList<HashMap<String, ArrayList<String>>> id_group = new ArrayList<HashMap<String, ArrayList<String>>>();
	//判断当前界面是否被编辑过
	boolean isChioce;
	private int isClickAll;//全国是1 部分区域是2
	private ArrayList<AddBackBean> add_list;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.sale_distance);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		linear_address = (LinearLayout) findViewById(R.id.linear_address);
		linear_title = (LinearLayout) findViewById(R.id.linear_title);
		rb_all = (RadioButton) findViewById(R.id.rb_all);
		rb_chioce = (RadioButton) findViewById(R.id.rb_chioce);
		my_country = (TextView) findViewById(R.id.my_country);
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_notice = (TextView) findViewById(R.id.tv_notice);
		list_data_cache.clear();
		list_data_db.clear();
		my_country.setVisibility(View.GONE);
		getDate();
	}

	@Override
	protected void getNetData() {
		
	}

	//选择区域点击效果
	private void ChiocePressed() {
		rb_chioce.setBackgroundResource(R.drawable.groupbuy_list_item_bg_select);
		rb_all.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
		my_country.setVisibility(View.GONE);
		rb_chioce.setTextColor(getResources().getColor(R.color.color_f9883d));
		rb_all.setTextColor(getResources().getColor(R.color.color_454545));
	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		rb_chioce.setOnClickListener(this);
		rb_all.setOnClickListener(this);
		tvRight.setOnClickListener(this);
		tv_add.setOnClickListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "供货区域";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return "完成";
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
//		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
			if(isChioce)
				ShowDialog("是否放弃修改的内容？", "温馨提示",1,null);
			else
				onBackPressed();
			break;
		case R.id.rb_chioce:
			/*
			 * 选择区域按钮
			 * */
			map.put("Sell_area_part","");
			MobclickAgent.onEvent(baseActivity, "Sell_area_part", map);
			my_country.setVisibility(View.GONE);
			tv_add.setVisibility(View.VISIBLE);
			if(linear_address.getChildCount()==0){
				tv_notice.setVisibility(View.GONE);
				tv_add.setVisibility(View.VISIBLE);
			}else{
				linear_address.setVisibility(View.VISIBLE);
				tv_notice.setVisibility(View.VISIBLE);
				tv_notice.setText("已选择区域");
			}
			isChioce = true;
			id.delete(0, id.length());
//			if (rb_chioce.isPressed()) {
				ChiocePressed();
//			} else {
//				rb_chioce.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
//				Visible(my_country, 1);
//				Visible(linear_address, 0);
//				rb_chioce.setTextColor(getResources().getColor(R.color.color_454545));
//			}
			isClickAll = 2;
			break;
		case R.id.rb_all:
			map.put("Sell_area_national","");
			MobclickAgent.onEvent(baseActivity, "Sell_area_national", map);
			id.delete(0, id.length());
			/*
			 * 选择全国按钮
			 * */
			linear_address.setVisibility(View.GONE);
			tv_notice.setVisibility(View.VISIBLE);
			tv_notice.setText("已选择区域");
			my_country.setVisibility(View.VISIBLE);
			tv_add.setVisibility(View.GONE);
//			if (rb_all.isPressed()) {
				allpressed();
//			} else {
//				rb_all.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
//				Visible(my_country, 0);
//				Visible(linear_address, 1);
//				rb_all.setTextColor(getResources().getColor(R.color.color_454545));
//			}
			isClickAll = 1;
			break;
		case R.id.base_activity_title_right_righttv:
			if(1==isClickAll){
				list_data_db.clear();
				tv_text = "全国";
				AddBackBean allAdd = new AddBackBean();
				allAdd.setCityName(tv_text);
				List<String> list_id= new ArrayList<String>();
				list_id.add("1");
				allAdd.setList_id(list_id);
				list_data_db.add(allAdd);
				id.delete(0, id.length());
				id.append("1");
			}else if(2==(isClickAll)){
				if(list_data_db.size()>0){
					tv_text = "已选择";
					id.delete(0, id.length());
					setList(list_data_db);
				}
			}
			if(id.length()==0){
				ToastUtil.showToast(baseContext, "请选择供货区域");
				return;
			}
			SaveDate();
			Intent intents = new Intent();
			addbean.setId(id.toString());
			addbean.setCityName(tv_text);
			addbean.setId_group(id_group);
			intents.putExtra("bean", addbean);
			setResult(RESULT_OK, intents);
			finish();
			break;
		case R.id.tv_add:
			map.put("Sell_area_add","");
			MobclickAgent.onEvent(baseActivity, "Sell_area_add", map);
			isChioce = true;//界面被编辑
			Intent intent = new Intent(baseActivity,
					DestanceChioceActivity.class);
			intent.putExtra("index", 2);
			//当前界面已经有的城市传递过去合并相同省份数据
			intent.putExtra("listbean", list_data_db);
			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE);
			break;
		default:
			break;
		}
	}

	private void allpressed() {
//		tv_add.setVisibility(View.INVISIBLE);
		rb_all.setBackgroundResource(R.drawable.groupbuy_list_item_bg_select);
		rb_chioce.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
//		Visible(my_country, 1);
//		Visible(linear_address, 0);
		rb_all.setTextColor(getResources().getColor(R.color.color_f9883d));
		rb_chioce.setTextColor(getResources().getColor(R.color.color_454545));
	}
	private void ShowDialog(String notice, String 
			title,final int flag,final String tv_privoice) {
		CommonDialog.makeText(baseActivity, title, notice, new OnDialogListener() {
			@Override
			public void onResult(int result, CommonDialog commonDialog,
					String tel) {
				// TODO Auto-generated method stub
				if (OnDialogListener.LEFT == result) {
					if(1==flag){
						onBackPressed();
					}else{
						//删除视图
						deleteView(tv_privoice);
					}
					CommonDialog.Dissmess();
				} else {
					CommonDialog.Dissmess();
				}
			}

		}).showDialog();
	}

	/*
	 * 删除城市视图
	 * 
	 * */
	private void deleteView(final String tv_privoice) {
		if(tv_privoice != null){
			for(int i=0;i<linear_address.getChildCount();i++){
				if(linear_address.getChildAt(i).getTag().equals(tv_privoice)){
					linear_address.removeViewAt(i);
					list_data_db.remove(i);
					break;
				}
			}
			if(linear_address.getChildCount()==0)
				tv_notice.setVisibility(View.INVISIBLE);
			else
				tv_notice.setVisibility(View.VISIBLE);
		}
	}
	private void Visible(View view,int index){
		if(view instanceof TextView){
			if(1==index)
				view.setVisibility(View.VISIBLE);
			else
				view.setVisibility(View.GONE);
		}else{
			if(1==index)
				view.setVisibility(View.VISIBLE);
			else
				view.setVisibility(View.GONE);
		}
	}
	//本地存储选择的城市数据
	private void SaveDate() {
		SharedPreferencesUtil.saveObject(baseContext, list_data_db,"db_districe");
	}
	//获取本地存储选择的城市数据
	private void getDate() {
		Intent intent = getIntent();
		add_list = (ArrayList<AddBackBean>) intent.getSerializableExtra("add_list");
		if(add_list == null){
			//新增操作
		list_data_cache = (ArrayList<AddBackBean>) SharedPreferencesUtil.getObject(baseContext, "db_districe");
		id.delete(0, id.length());
		if(list_data_cache!=null && list_data_cache.size()>0){
			if(list_data_cache.get(0).getProviceid()!=null){
				//多个区域选择数据
				for(AddBackBean dbbean:list_data_cache){
					setData(dbbean.getProviceid(),dbbean.getList_name(), dbbean.getProivinceName(), dbbean.getList_id());
				}
				ChiocePressed();
				list_data_db = list_data_cache;
				isClickAll = 2;
				my_country.setVisibility(View.GONE);
				tv_add.setVisibility(View.VISIBLE);
			}else{
				//全国选择数据
				isClickAll = 1;
				allpressed();
				id.append("1");
				my_country.setVisibility(View.VISIBLE);
			}
			tv_notice.setText("已选择供货区域");
			tv_notice.setVisibility(View.VISIBLE);
			linear_address.setVisibility(View.VISIBLE);
		}else{
			tv_notice.setVisibility(View.INVISIBLE);
			my_country.setVisibility(View.GONE);
		}
		/*if(list_data_cache!=null && list_data_cache.size()>0){
			if(list_data_cache.get(0).getList_name()!=null){
				//多个区域选择数据
				for(AddBackBean dbbean:list_data_cache){
					setData(dbbean.getProviceid(),dbbean.getList_name(), dbbean.getProivinceName(), dbbean.getList_id());
				}
					ChiocePressed();
				list_data_db = list_data_cache;
				isClickAll = 2;
			}else{
				//全国选择数据
				isClickAll = 1;
				allpressed();
				id.append("1");
			}	
			tv_notice.setText("已选择供货区域");
			tv_notice.setVisibility(View.VISIBLE);
			linear_address.setVisibility(View.VISIBLE);
			my_country.setVisibility(View.GONE);
			tv_add.setVisibility(View.VISIBLE);
		}else if(list_data_cache.size()==1){//全国
			tv_notice.setText("已选择供货区域");
			tv_add.setVisibility(View.GONE);
			tv_notice.setVisibility(View.VISIBLE);
			my_country.setVisibility(View.VISIBLE);
		}else{
			tv_notice.setVisibility(View.INVISIBLE);
			}*/
		linear_title.setVisibility(View.VISIBLE);
		}else{
			//查询详情 当前界面不可编辑
			for(AddBackBean dbbean:add_list){
				setData(dbbean.getProviceid(),dbbean.getList_name(), dbbean.getProivinceName(), dbbean.getList_id());
			}
			tv_notice.setText("当前卖货区域");
			tvRight.setVisibility(View.INVISIBLE);
			linear_title.setVisibility(View.GONE);
		}
	}
	//清除本地存储选择的城市数据
	private void clearDate() {
		list_data_db.clear();
		SharedPreferencesUtil.saveObject(baseContext, list_data_db,"db_districe");
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultcode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultcode, data);
		switch (requestCode) {
		case ConstantsUtil.REQUEST_CODE:
			if (data != null) {
			 list_data_db = (ArrayList<AddBackBean>) data.getSerializableExtra("bean");
				linear_address.removeAllViews();
				if(list_data_db!=null){
					tv_notice.setVisibility(View.VISIBLE);
					linear_address.setVisibility(View.VISIBLE);
					id_group.clear();
				for(int i=0;i<list_data_db.size();i++){
						setData(list_data_db.get(i).getProviceid(),list_data_db.get(i).getList_name(), list_data_db.get(i).getProivinceName(),list_data_db.get(i).getList_id());
					}
				}
			}
			break;

		default:
			break;
		}
	}

	// 处理成","的文本信息
	private void setList(ArrayList<AddBackBean> cityid) {
		int size = cityid.size();
		for (int i = 0; i < size; i++) {
			int size_addbean = cityid.get(i).getList_id().size();
			for(int k = 0;k < size_addbean;k++ ){
				if (k == size_addbean - 1 && i == size-1) {
					id.append(cityid.get(i).getList_id().get(k));
				} else {
					id.append(cityid.get(i).getList_id().get(k) + ",");
				}
			}
		}
	}

	
	//生成每个城市视图
	private void setData(final String privoceId,final List<String> list_data, final String tv_privoice,final List<String> id_list_item) {
		final View view = LinearLayout.inflate(baseContext, R.layout.gridview_item,
				null);
		final TextView tv_privoice_name = (TextView) view
				.findViewById(R.id.tv_privoice);
		final ImageView iv_delete_provice = (ImageView) view
				.findViewById(R.id.iv_delete_provice);
		final TextView tv_edit = (TextView) view.findViewById(R.id.tv_edit);
		final GridView gridview = (GridView) view
				.findViewById(R.id.gridview);
		if(add_list != null){
			//如果不是查看详情操作 就异常编辑按钮
			tv_edit.setVisibility(View.INVISIBLE);
		}
		final AdapterDistrict adapter = new AdapterDistrict(baseContext, list_data,id_list_item,
				new CallbackDistrict() {
					@Override
					public void setBack(String id) {
						if(id_list_item.size()==0){
							//完全删除城市 整个视图都remove
							deleteView(tv_privoice);
						}
					}
				});
		gridview.setAdapter(adapter);
		tv_privoice_name.setText(tv_privoice);
		tv_edit.setOnClickListener(new OnClickListener() {
			boolean isedit = true;
			public void onClick(View v) {
				if (isedit) {
					tv_edit.setText("完成");
					adapter.isEdit(isedit);
					isedit = false;
					iv_delete_provice.setVisibility(View.VISIBLE);
				} else {
					tv_edit.setText("编辑");
					adapter.isEdit(isedit);
					isedit = true;
					iv_delete_provice.setVisibility(View.INVISIBLE);
				}
				adapter.notifyDataSetChanged();
			}
		});
		iv_delete_provice.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				ShowDialog("是否删除"+tv_privoice+"?", "温馨提示",2,tv_privoice);
				new AlertDialogCustom().CreateDialog(baseActivity,null,"是否将"+tv_privoice+"全部删除?",null,3,new BackRemark() {
					@Override
					public void setRemark(String cid, String remark) {
						// TODO Auto-generated method stub
						//删除视图
						deleteView(tv_privoice);
					}
				},null,null);
			}
		});
		view.setTag(tv_privoice);
		if(linear_address.getChildCount()>0){
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, ViewUtil.dip2px(baseContext, 20), 0, 0);
			view.setLayoutParams(lp);
		}
		linear_address.addView(view);
		/**************************/
		HashMap<String,ArrayList<String>> hashMap = new HashMap<String,ArrayList<String>>();
		ArrayList<String> list_id = new ArrayList<String>();
		list_id.addAll(id_list_item);
		hashMap.put(privoceId, list_id);
		id_group.add(hashMap);
		/*
		 * 本地保存数据，回填显示
		 */
//		AddBackBean addback = new AddBackBean();
//		addback.setProivinceName(tv_privoice);
//		addback.setList_name(list_data);
//		addback.setList_id(id_list_item);
//		list_data_db.add(addback);

//		list_id.addAll(id_list_item);//存储所有id集合
	}

}
