package com.heheys.ec.controller.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.InvoiceHistoryBean;
import com.heheys.ec.model.dataBean.InvoiceHistoryBean.Invoice;
import com.heheys.ec.model.dataBean.InvoiceHistoryBean.InvoiceHistory;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author wangkui  2016-06-23
 *
 * @param 发票现在界面
 */
public class InvoiceActivity extends BaseActivity implements OnClickListener{

	//普通发票
	private CheckBox rb_nomarl;
	//明细个人
	private CheckBox rb_one;
	//明细
	private CheckBox rb_company;
	//发票明细
	private RadioButton rb_detail;
	//酒水
	private RadioButton rb_wine;

	//发票类型  初始化选中普通发票
	private boolean isNomarlfp = true;
	//发票抬头  初始化选中个人
	private boolean isTitlefp = true;
	//发票内容  初始化明细
	private boolean isContentlfp = true;
	private Context mActivity;
	private InvoiceHandler hanlder;
	//存储是否是普通发票 1是普通发票
	private int id_type = 1;
	
	//存储是否是普通发票 0是个人 1公司
	private int id_title =0;
	
	//存储是否是普通发票 发票单位内容
	private String id_title_content;
	
	//存储是否是普通发票 发票内容 0明细1酒水
	private int id_content = 0;
	private RadioGroup rb_group;
	private Button bt_sure;
	private Button bt_cancle;
	private EditText fp_et;
	private PopupWindow mPopupWindow;
	private InvoiceHistoryBean usercouponBean;//服务器返回发票数据
	private InvoiceHistory invoiceHistory;
	private Invoice invoice;
	private List<String> listdate = new ArrayList<String>();
	private TextView tv_tips;
	private String notice;
	private boolean isneedinvoice;
	private boolean look;
	private LinearLayout linear_buttom;
	private String oid;
	private boolean createOrder;//true 下订单界面过来 false 查看订单详情
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.invoice);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		mActivity = InvoiceActivity.this;
		hanlder = new InvoiceHandler(this);
		rb_nomarl = (CheckBox) findViewById(R.id.rb_nomarl);
		rb_one = (CheckBox) findViewById(R.id.rb_one);
		rb_company = (CheckBox) findViewById(R.id.rb_company);
		fp_et = (EditText) findViewById(R.id.fp_et);
		tv_tips = (TextView) findViewById(R.id.tv_tips);
		
		rb_group = (RadioGroup) findViewById(R.id.rb_group);
		rb_detail = (RadioButton) findViewById(R.id.rb_detail);
		rb_wine = (RadioButton) findViewById(R.id.rb_wine);
		bt_sure = (Button) findViewById(R.id.bt_sure);
		bt_cancle = (Button) findViewById(R.id.bt_cancle);
		linear_buttom = (LinearLayout) findViewById(R.id.linear_buttom);
		rb_one.setChecked(isTitlefp);
		rb_detail.setChecked(isContentlfp);
		tvRight.setVisibility(View.VISIBLE);
		rb_one.setOnCheckedChangeListener(new CheckOnelister());
		rb_company.setOnCheckedChangeListener(new CheckCompanylister());
		rb_group.setOnCheckedChangeListener(new RbgroupLister());
		bt_sure.setOnClickListener(new surebutton());
		bt_cancle.setOnClickListener(new surebutton());
		fp_et.setOnClickListener(new surebutton());
		rb_nomarl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){ 
					id_type = 1;
					rb_nomarl.setBackgroundResource(R.drawable.groupbuy_list_item_bg_select);
				}else{
					id_type = -1;
					rb_nomarl.setBackgroundResource(R.drawable.groupbuy_list_item_bg_normal);
				}
			}
		});
		tvRight.setTextColor(Color.parseColor("#d8d8d8"));
		tvRight.setTextSize(14);
		tvRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialogCustom().CreateFpDialog((Activity) mActivity,notice);	
			}
		});
		
		InitDateFrom();
	}
	//获取上个界面
	private void InitDateFrom() {
		//获取传递的数据
				Intent intent = getIntent();
				createOrder = intent.getBooleanExtra("createOrder", false);
				if(createOrder){
					//下订单界面
					invoice = (Invoice) intent.getSerializableExtra("usercouponBean");
					ApiHttpCilent.getInstance(this).invoiceHistory(this,"",new InvoiceCallBack());//查询历史发票记录
					rb_one.setEnabled(true);
					rb_company.setEnabled(true);
					fp_et.setEnabled(true);
					rb_detail.setEnabled(true);
					rb_wine.setEnabled(true);
					fp_et.setEnabled(true);
					linear_buttom.setVisibility(View.VISIBLE);
				}else{
					//查看订单界面
					oid = intent.getStringExtra("oid");
					ApiHttpCilent.getInstance(this).invoiceHistory(this,oid,new InvoiceCallBack());//查询历史发票记录
					//查看发票功能
					rb_one.setEnabled(false);
					rb_company.setEnabled(false);
					fp_et.setEnabled(false);
					fp_et.setClickable(false);
					rb_detail.setEnabled(false);
					rb_wine.setEnabled(false);
					linear_buttom.setVisibility(View.INVISIBLE);
					fp_et.setEnabled(false);
				}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		if(id_title == 1){
			id_title_content  = fp_et.getText().toString().trim();
		}
		if(invoice!=null){
			invoiceHistory.setNotice(notice);
			invoiceHistory.setTips(invoice.getInvoice().getTips());
			invoiceHistory.setHistorytitle(invoice.getInvoice().getHistorytitle());
			invoiceHistory.setInbuertype(id_title);
			invoiceHistory.setInvoicecontent(id_content);
			invoiceHistory.setInvoicetitle(id_title_content);
			invoiceHistory.setInvoicetype(id_type);
		}
		//传递回调发票信息
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("Invoice", invoice);
		intent.putExtra("invoice", bundle);
		if(!isneedinvoice)
			intent = null;
		setResult(RESULT_OK, intent);
		finish();
	}
	class CompanyAdpater extends BaseListAdapter<String>{

		public CompanyAdpater(Context context, List<String> data) {
			super(data, context);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View bindView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = baseInflater.inflate(R.layout.brand_item, parent, false);
			}
			String brand = dataList.get(position);
			TextView tv_brand = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_brand);
			if(brand!= null){
					tv_brand.setText(brand);
			}
			return convertView;
		}
		
	}
	public void bindBrandData() {
		showBrandWindow((Activity) mActivity, R.layout.show_company);
	}
	public PopupWindow showBrandWindow(Activity activity,
			int layoutId) {
		View mPopMenuView = null;
		mPopMenuView = LayoutInflater.from(activity).inflate(layoutId, null);
		LinearLayout layout = (LinearLayout) mPopMenuView
				.findViewById(R.id.groupbuy_company_parent);
		ListView lv_company = (ListView) mPopMenuView
				.findViewById(R.id.lv_company);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = ViewUtil.dip2px(mActivity, 28)+ViewUtil.getWidth(rb_company);
//		layout.setLayoutParams(lp);
		if(usercouponBean !=null){
		if(usercouponBean.getResult() !=null  && usercouponBean.getResult().getInvoice()!=null && 
				usercouponBean.getResult().getInvoice().getHistorytitle()!=null){
			    listdate = usercouponBean.getResult().getInvoice().getHistorytitle();
			}
		}else{
			listdate = invoice.getInvoice().getHistorytitle();
		}
		lv_company.setAdapter(new CompanyAdpater(mActivity,listdate));
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
			}
		});
		lv_company.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPopupWindow.dismiss();
				fp_et.setText(listdate.get(position));
			}
		});
		mPopupWindow = new PopupWindow(mPopMenuView,
				ViewUtil.getScreenWith(activity), LayoutParams.WRAP_CONTENT,
				false);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); 
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
//		 mPopMenuView.getBackground().setAlpha(230);
//		layout.getBackground().setAlpha(230);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
		//防止虚拟软键盘被弹出菜单遮住
		mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mPopupWindow.update();
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
//				group_title_down.setImageResource(R.drawable.groupbuy_arrow_up_gray);
			}
		});
		return mPopupWindow;
	}
	
	
	 boolean ischeck(int id){
		 if(id!=0 && id!=1)
			 return true; 	
		 else
			 return false; 	
	}
	class surebutton implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.bt_sure:
				if(ischeck(id_type)){
					ToastUtil.showToast(mActivity, "请选择发票类型");
					return;
				}
				if(ischeck(id_title)){
					ToastUtil.showToast(mActivity, "请选择发票抬头");
					return;
				}
				if(ischeck(id_content)){
					ToastUtil.showToast(mActivity, "请选择发票内容");
					return;
				}
				if(id_title == 1){
					id_title_content  = fp_et.getText().toString().trim();
					if(StringUtil.isEmpty(id_title_content))
					{
						ToastUtil.showToast(mActivity, "请填写单位名称");
						return ;
					}
				}
				if(invoice!=null && invoiceHistory !=null){
				invoiceHistory.setNotice(notice);
				invoiceHistory.setTips(invoice.getInvoice().getTips());
				invoiceHistory.setHistorytitle(invoice.getInvoice().getHistorytitle());
				invoiceHistory.setInbuertype(id_title);
				invoiceHistory.setInvoicecontent(id_content);
				invoiceHistory.setInvoicetitle(id_title_content);
				invoiceHistory.setInvoicetype(id_type);
				}
				invoice.setInvoice(invoiceHistory);
				invoice.setInvoiceinfo(id_title_content);
				//传递回调发票信息
				Bundle bundle = new Bundle();
				bundle.putSerializable("Invoice", invoice);
				intent.putExtra("invoice", bundle);
				setResult(RESULT_OK, intent);
				finish();
				break;
			case R.id.bt_cancle:
				Bundle bundles = new Bundle();
				bundles.putSerializable("Invoice", null);
				intent.putExtra("invoice", bundles);
				setResult(RESULT_OK, intent);
				finish();
				break;
			case R.id.fp_et:
			 if(createOrder){
				if(mPopupWindow!=null){
					if(!mPopupWindow.isShowing()){
						mPopupWindow.showAsDropDown(fp_et,ViewUtil.getScreenWith( mActivity), 0);
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
						imm.showSoftInput(fp_et,InputMethodManager.SHOW_FORCED);   
					}else{
						mPopupWindow.dismiss();
					}
					}
				}
				break;
				
			default:
				break;
			}
		}
		
	}
	
	class RbgroupLister implements android.widget.RadioGroup.OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId==R.id.rb_detail){
				id_content = 0;
			}else if(checkedId==R.id.rb_wine){
				id_content = 1;
			}
		}
	}
	
	class CheckOnelister implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(isChecked){
					rb_one.setChecked(true);
					rb_company.setChecked(false);
					id_title = 0;
					fp_et.setEnabled(false);
			}else{
				id_title = 1;
			}
		}
	}
	class CheckCompanylister implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(isChecked){
				rb_one.setChecked(false);
				rb_company.setChecked(true);
				id_title = 1;
				fp_et.setEnabled(true);
				fp_et.requestFocus();
			}else{
				id_title = 0;
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
	}

	@Override
	protected void getNetData() {
		
	}
	public class InvoiceCallBack extends BaseJsonHttpResponseHandler<InvoiceHistoryBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, InvoiceHistoryBean arg4) {
			Dismess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			hanlder.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				InvoiceHistoryBean arg3) {
		}
		
		@Override
		protected InvoiceHistoryBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dismess();
			Gson gson = new Gson();
			usercouponBean = gson.fromJson(response, InvoiceHistoryBean.class);
			Message message = Message.obtain();
			if ("1".equals(usercouponBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = usercouponBean.getError().getInfo();
			}
			hanlder.sendMessage(message);
			return usercouponBean;
		}
	}

	private void Dismess() {
		InvoiceActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading !=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	
	/**
	 * 
	 * Describe:获取消息数据处理网络请求消息
	 * 
	 * Date:2016-06-23
	 * 
	 * Author:wangkui
	 */
	public static class InvoiceHandler extends WeakHandler<InvoiceActivity> {

		public InvoiceHandler(InvoiceActivity reference) {
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
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
//				getReference().bindUserViewData();
				
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

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	//绑定数据
	public void bindViewData() {
	if(usercouponBean !=null && usercouponBean.getResult()!=null && usercouponBean.getResult().getInvoice()!=null){
			BindInvoice();
		}
	}
	//绑定数据
	private void BindInvoice() {
	if(createOrder){
		//创建订单跳转
		if(invoice !=null){
			/*
			 * 已经填充数据回看
			 * */
			BindInvoiceData();
		}else{
			/*
			 * 初始化查看
			 * **/
			invoice = usercouponBean.getResult();
			BindInvoiceData();
			}
		}else{
			//查看订单跳转
			invoice = usercouponBean.getResult();
			BindInvoiceData();
		}
	}
	private void BindInvoiceData() {
		invoiceHistory = invoice.getInvoice();
		id_type = invoiceHistory.getInvoicetype();
		id_title = invoiceHistory.getInbuertype();
		id_content = invoiceHistory.getInvoicecontent();
		tv_tips.setText(StringUtil.isEmpty(invoiceHistory.getTips())?"":invoiceHistory.getTips());
		fp_et.setText(StringUtil.isEmpty(invoiceHistory.getInvoicetitle())?"":invoiceHistory.getInvoicetitle());
		notice = invoice.getInvoice().getNotice();
		setCheck(id_title);
		setContentCheck(id_content);
		//绑定历史公司数据
		bindBrandData();
	}
	
	void setCheck(int id){
		if(0 == id){
			rb_one.setChecked(true);
			rb_company.setChecked(false);
		}else{
			rb_one.setChecked(false);
			rb_company.setChecked(true);
		}
	}
	void setContentCheck(int id){
		if(0 == id){
			rb_detail.setChecked(true);
			rb_wine.setChecked(false);
		}else{
			rb_detail.setChecked(false);
			rb_wine.setChecked(true);
		}
	}
	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "发票详情";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return "发票须知";
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
	protected void onCreate() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("PG_INV_INF");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("PG_INV_INF");
		MobclickAgent.onPause(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
			// 返回键处理
			setResult(RESULT_OK, null);
			finish();
			break;

		default:
			break;
		}
	}
}
