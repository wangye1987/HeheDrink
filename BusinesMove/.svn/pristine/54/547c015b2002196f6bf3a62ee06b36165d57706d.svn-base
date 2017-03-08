package com.heheys.ec.controller.activity; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.adapter.BusinessCardAdapter;
import com.heheys.ec.model.dataBean.BusinessCardBaseBean;
import com.heheys.ec.model.dataBean.BusinessCardBaseBean.BusinessCardBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.sqliteHelper.IdCardManager;
import com.heheys.ec.utils.CharacterParser;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.PinyinComparator;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.AlertDialogCustom.BackRemark;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/** 
 * @author 作者 E-mail: 王奎
 * @version 创建时间：2015-11-16 上午10:46:02 
 * 类说明 
 * @param 个人名片管理界面
 */
public class IdCardManagerActivity extends BaseActivity {


	private ListView businessCard_lv;
	private Activity mContex;
	private BusinessCardAdapter cardAdapter;
	private CardHandler myhandler;

	/*// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
				endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;*/
	
	private BusinessCardBaseBean businessCardBaseBean;//服务器返回数据bean
	private List<BusinessCardBean> data = new ArrayList<BusinessCardBean>();
	private Animation animationShow,animationHide;
	private LinearLayout linear_mycard;
	private boolean ishide = false;
	private int flag;
	private EditText ed_search;
	private List<BusinessCardBean> listsearch;//搜索联系人结果list
	private CharacterParser characterParser;
	private List<String> mlist = new ArrayList<String>();
	private List<BusinessCardBean> sourceDataList;
	private PinyinComparator pinyinComparator;
	private boolean isPush;
	private List<BusinessCardBean> dblist;
	private String time;
	private long timeSend;
	HashMap<String,String> map = new HashMap<String,String>();
	private TextView tv_title;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.businesscard_manager);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		mContex = IdCardManagerActivity.this;
		listsearch = new ArrayList<BusinessCardBean>();
//		ivTitleMiddle.setBackgroundResource(R.drawable.add_address);
//		tvRight.setTextSize(12);
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setTextColor(getResources().getColor(R.color.white));
		ivTitleMiddle.setVisibility(View.VISIBLE);
		ivTitleMiddle.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		businessCard_lv = (ListView) findViewById(R.id.businessCard_lv);
		ed_search =(EditText) findViewById(R.id.fragment_home_title_search_et);
		linear_mycard = (LinearLayout) findViewById(R.id.linear_mycard);	
		cardAdapter = new BusinessCardAdapter(data, mContex,new RemarkUpdateBack());
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				//获取本地数据
//				dblist = IdCardManager.getIntence(mContex).getOrderInfor();
//				cardAdapter.setData(dblist);
//				cardAdapter.notifyDataSetChanged();
//			}
//		});
		businessCard_lv.setAdapter(cardAdapter);
		myhandler = new CardHandler((IdCardManagerActivity) mContex);
		characterParser = CharacterParser.getInstance();
		animationShow=AnimationUtils.loadAnimation(baseActivity, R.anim.pop_enter);
		animationHide=AnimationUtils.loadAnimation(baseActivity, R.anim.pop_out);;
		
		ed_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}
			public void afterTextChanged(Editable s) {
				if(dblist!=null){
					cardAdapter.setData(dblist);
					cardAdapter.notifyDataSetChanged();
					}
			}
		});
		ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					/**
					 * 搜索事件
					 */
				
						map.put("card_management_list_search","");
						MobclickAgent.onEvent(baseActivity, "card_management_list_search", map); 	
					if (StringUtil.isEmpty(ed_search.getText().toString())) {
						if(dblist!=null){
						cardAdapter.setData(dblist);
						cardAdapter.notifyDataSetChanged();
						}
					} else {
						listsearch.clear();
						String searchst = ed_search.getText().toString().trim();
						if(dblist!=null){
						for(BusinessCardBean search:dblist){
							if(search.getName().contains(searchst)||search.getAddress().contains(searchst)||
							   search.getCompany().contains(searchst)||search.getMobile().contains(searchst)){
								listsearch.add(search);
							}
						}
						if(listsearch.size()==0)
							ToastUtil.showToast(mContex, "没有找到相关信息");
						cardAdapter.setData(listsearch);
						cardAdapter.notifyDataSetChanged();
						}
					}
				}
				return false;
			}
		});
	}
	
	//初始化数据
	private void initData() {
		pinyinComparator = new PinyinComparator();
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
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
	/*
	 * 修改备注后回调数据处理
	 * 
	 * */
	public class RemarkUpdateBack implements BackRemark{
		@Override
		public void setRemark(String cid, String remark) {
			if(ToastNoNetWork.ToastError(mContex)){
				return;
			}
			flag = 2;
			ApiHttpCilent.getInstance(mContex).UpdateBusinessCard(mContex, cid, remark, new  CallBackBusinessCard());
		}
	}
	
	//获取名片列表数据
	@Override
	protected void getNetData() {
		isPush = getIntent().getBooleanExtra("isPush", false);
		flag = 1;
		if(dblist!=null && dblist.size()>0){
			time = dblist.get(0).getTime();
			timeSend = Long.parseLong(time)+1;
		}else{
			timeSend = 0;
		}
		if(ToastNoNetWork.ToastError(mContex)){
			return;
		}
		ApiHttpCilent.getInstance(mContex).BusinessCardList(mContex,timeSend, new CallBackBusinessCard());
		
	}
  
	
	public static class CardHandler extends WeakHandler<IdCardManagerActivity>{

		public CardHandler(IdCardManagerActivity reference) {
			super(reference);
		}
		
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch (msg.what) {
			//获得正常数据
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindDate();
				break;
			//获得异常数据
			case ConstantsUtil.HTTP_FAILE:
				ErrorBean back = (ErrorBean) msg.obj;
				if(back!=null){
					if(back.getInfo()!=null)
					ToastUtil.showToast(getReference(), back.getInfo());
				}else{
					ToastUtil.showToast(getReference(), "数据异常,请稍后重试");
				}
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				getReference().getNetData();//重新刷新数据
				break;
			default:
				break;
			}
			
		}
		
	}
	//隐藏等待框
	private void Dismess(){
		mContex.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(ApiHttpCilent.loading  !=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	private class CallBackBusinessCard extends BaseJsonHttpResponseHandler<BusinessCardBaseBean>{

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BusinessCardBaseBean arg4) {
			// TODO Auto-generated method stub
			Dismess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BusinessCardBaseBean bean) {
			// TODO Auto-generated method stub
			 Dismess();
		}

		@Override
		protected BusinessCardBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dismess();
			Gson gson = new Gson();
			businessCardBaseBean = gson.fromJson(response, BusinessCardBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(businessCardBaseBean.getStatus()) && flag==1) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = businessCardBaseBean.getResult();
			}else if("1".equals(businessCardBaseBean.getStatus()) && flag==2){//修改备注成功
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = businessCardBaseBean.getError();
			}
			myhandler.sendMessage(message);
			return businessCardBaseBean;
		}
	}
	
	/****************绑定数据 处理界面*******************/
	private void bindDate(){
		if(businessCardBaseBean!=null && businessCardBaseBean.getResult()!=null 
				&& businessCardBaseBean.getResult().getList().size()>0){
			final List<BusinessCardBean> list = businessCardBaseBean.getResult().getList();
			runOnUiThread(new Runnable() {
				public void run() {
					IdCardManager.getIntence(mContex).insertCardInfo(list);
					dblist = IdCardManager.getIntence(mContex).getOrderInfor();
					cardAdapter.setData(dblist);
					cardAdapter.notifyDataSetChanged();
				}
			});
		}else{
			//显示暂无数据
			runOnUiThread(new Runnable() {
				public void run() {
					dblist = IdCardManager.getIntence(mContex).getOrderInfor();
					if(dblist!=null && dblist.size()>0){
						cardAdapter.setData(dblist);
						cardAdapter.notifyDataSetChanged();
					}else{
						showBusinessNoData();
					}
				}
			});
		}
	}
//	private void bindDate(){
//		if(startIndex==1){
//			//处理第一页数据
//			if(businessCardBaseBean!=null && businessCardBaseBean.getResult()!=null 
//					&& businessCardBaseBean.getResult().getList().size()>0){
//				data  = businessCardBaseBean.getResult().getList();
//				cardAdapter.setData(data);
//				mlist.clear();
//				for(BusinessCardBean busbean:data){
//					mlist.add(busbean.getName());
//				}
//				//名字转拼音
//				sourceDataList = changeSourceData(mlist);
//				cardAdapter.notifyDataSetChanged();
//				hideRefreshView();
//			}else{
//				//显示暂无数据
//				showBusinessNoData();
//			}
//		}else{
//			//处理分页数据
//			if(businessCardBaseBean!=null && businessCardBaseBean.getResult()!=null 
//					&& businessCardBaseBean.getResult().getList().size()>0){
//				List<BusinessCardBean> newdata  = businessCardBaseBean.getResult().getList();
//				data.addAll(newdata);
//				hideLoadMoreView();
//			}else{
//				//显示暂无数据 回滚当前刷新发页码
//				startIndex = startIndex-ConstantsUtil.DEFAULT_PAGE_SIZE;	
//				endIndex = endIndex-ConstantsUtil.DEFAULT_PAGE_SIZE;	
//				hideLoadMoreView();
//			}
//		}
//	}
	
	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		tvRight.setOnClickListener(this);
		ed_search.setOnClickListener(this);
//		businessCard_lv.setonLoadListener(this);
//		businessCard_lv.setonRefreshListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "名片管理";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return "个人名片";
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
		switch (v.getId()) {
		case R.id.base_activity_no_business_add:
			Intent intentSalon = new Intent(mContex,
					SalonListActivity.class);
			StartActivityUtil.startActivity(mContex, intentSalon);
			break;
		case R.id.base_activity_title_right_righttv:
			map.put("card_management_list_view","");
			MobclickAgent.onEvent(mContex, "card_management_list_view", map);
			StartActivityUtil.startActivity(mContex, MyBusCardActivity.class);
			break;
		case R.id.base_activity_title_backicon:
			if(isPush){
				Intent i = new Intent(baseContext,MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			}else{
				map.put("card_management_list_return","");
				MobclickAgent.onEvent(mContex, "card_management_list_return", map);
				onBackPressed();
			}
			break;
		case R.id.fragment_home_title_search_et:
				tv_title.setVisibility(View.GONE);
//				AnimationUtils.loadAnimation(context, id);
			break;
		default:
			break;
		}
	}
	//返回键处理
	public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	if(isPush){
				Intent i = new Intent(baseContext,MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			}else{
				onBackPressed();
			}
        }  
        return false;  
    }  
//	@Override
//	public void onLoadMore() {
//		// TODO Auto-generated method stub
//		HaveNetWork();
//		isLoadMore = true;
//		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
//		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
//		getNetData();
//		
//	}

	//检查网络状态
	private void HaveNetWork() {
		if (!NetWorkState.isNetWorkConnection(getBaseContext())) {
			ToastUtil.showToast(getBaseContext(), "网络连接失败,请检查网络设置");
			return;
		}
	}
//
//	@Override
//	public void onRefresh() {
//		HaveNetWork();
//		isRefresh = true;
//		initReuquestParams();
//		getNetData();
//	}
//	private void hideRefreshView() {
//		if (isRefresh) {
//			businessCard_lv.onRefreshComplete();
//			isRefresh = false;
//		}
//	}
//
//	private void hideLoadMoreView() {
//		if (isLoadMore) {
//			businessCard_lv.onLoadComplete();
//			cardAdapter.setNewData(data);
//			isLoadMore = false;
//		}
//	}
//
//	private void initReuquestParams() {
//		// TODO Auto-generated method stub
//		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
//		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
//	}
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);       //统计时长
		    tv_title.setVisibility(View.VISIBLE);
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
 