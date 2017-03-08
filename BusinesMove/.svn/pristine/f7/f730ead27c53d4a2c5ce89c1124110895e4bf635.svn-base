package com.heheys.ec.controller.activity;

import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.SalonListAdapter;
import com.heheys.ec.model.dataBean.SalonListBean;
import com.heheys.ec.model.dataBean.SalonListBean.SalonResultBean.SalonInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:沙龙页面
 * 
 * Date:2015-9-25
 * 
 * Author:liuzhouliang
 */
public class SalonListActivity extends BaseActivity implements OnClickListener,
		OnRefreshListener, OnMoreListener {
	private RefreshListView mListView;
	private SalonListAdapter madapter;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	private SalonListBean salonListBean;
	private SalonMessageHandler messageHandler;
	private List<SalonInfor> salonList;
	public String userId;
	private boolean isPush;

	@Override
	protected void onCreate() {
		setBaseContentView(R.layout.salon);
		initData();
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadChildView() {
		mListView = (RefreshListView) findViewById(R.id.salon_lv);
	}

	/**
	 * 
	 * Describe:初始化数据
	 * 
	 * Date:2015-10-22
	 * 
	 * Author:liuzhouliang
	 */
	private void initData() {

		messageHandler = new SalonMessageHandler(this);

	}

	@Override
	protected void getNetData() {
		userId = getIntent().getStringExtra(ConstantsUtil.USER_ID_KEY);
		isPush = getIntent().getBooleanExtra("isPush", false);
//		ToastUtil.showToast(baseContext, "userId==" + userId);
//		 if(IsLogin.isLogin(this)){
//			 ResultBean userInfoResultBean = (ResultBean) SharedPreferencesUtil
//			 .getObject(baseActivity, "resultbean");
//			 userId = userInfoResultBean.getId();
//			 }
		ApiHttpCilent.getInstance(this).getSalonList(this, startIndex,
				endIndex, userId, new SalonListRequestCallBack());
	}

	/**
	 * 
	 * Describe:沙龙列表请求回调
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public class SalonListRequestCallBack extends
			BaseJsonHttpResponseHandler<SalonListBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, SalonListBean arg4) {
			ApiHttpCilent.loading.dismiss();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				SalonListBean arg3) {
		}

		@Override
		protected SalonListBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			salonListBean = gson.fromJson(response, SalonListBean.class);
			Message message = Message.obtain();
			if ("1".equals(salonListBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = salonListBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = salonListBean.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return salonListBean;
		}

	}
	private void Dimess() {
		SalonListActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	/**
	 * 
	 * Describe:沙龙列表消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	public static class SalonMessageHandler extends
			WeakHandler<SalonListActivity> {
		public SalonMessageHandler(SalonListActivity reference) {
			super(reference);
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
					ToastUtil.showToast(getReference(), messageString);
					/**
					 * 处理失败数据
					 */
				} else {
					ToastUtil.showToast(getReference(), "请求失败");
				}
				break;
			}
		}
	}

	/**
	 * 
	 * Describe:绑定控件数据
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 */
	private void bindViewData() {
		if (startIndex == 1) {
			/**
			 * 初始化数据
			 */
			hideRefreshView();
			if (salonListBean.getResult() != null
					&& salonListBean.getResult().getList() != null
					&& salonListBean.getResult().getList().size() > 0) {
				salonList = salonListBean.getResult().getList();
				madapter = new SalonListAdapter(userId,salonList, this, salonListBean
						.getResult().getBaseurl());
				mListView.setAdapter(madapter);
			} else {
				/**
				 * 处理数据为空
				 */
//				ToastUtil.showToast(baseContext, "数据为空");
				showNoSalonView();
			}
		} else {
			/**
			 * 分页数据
			 */
			if (salonListBean.getResult() != null
					&& salonListBean.getResult().getList() != null
					&& salonListBean.getResult().getList().size() > 0) {
				/**
				 * 分页数据存在
				 */
//				ToastUtil.showToast(baseContext, "分页起始页码" + startIndex);
				List<SalonInfor> pageData = salonListBean.getResult().getList();
				if (salonList != null) {
					if (isLoadMore) {
						salonList.addAll(pageData);
						hideLoadMoreView();
					}

				}
			} else {
				/**
				 * 分页数据不存在
				 */
				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
//				ToastUtil.showToast(baseContext, "数据加载完毕==页码==" + startIndex);
			}
		}

	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
		mListView.setonLoadListener(this);
		mListView.setonRefreshListener(this);
	}

	@Override
	protected String setTitleName() {
		return "沙龙";
	}

	@Override
	protected String setRightText() {
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		return 0;
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
//		Toast.makeText(this, "list+onLoadMore", Toast.LENGTH_LONG).show();
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getNetData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.base_activity_title_backicon:
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("salonback","");
			MobclickAgent.onEvent(baseContext, "0083", map); 
			if(isPush){
				Intent i = new Intent(baseContext,MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				StartActivityUtil.startActivity(baseActivity, i);
				finish();
			}else{
				onBackPressed();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
//		Toast.makeText(this, "list+OnRefresh", Toast.LENGTH_LONG).show();
		isRefresh = true;
		initReuquestParams();
		getNetData();
	}

	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	private void hideRefreshView() {
		if (isRefresh) {
			mListView.onRefreshComplete();
			isRefresh = false;
		}

	}

	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
			madapter.setNewData(salonList);
			isLoadMore = false;
		}
	}

	/**
	 * 报名后刷新当前页面数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		super.onActivityResult(requestCode, resultCode, arg2);
		switch (requestCode) {
		case 1001:
			getNetData();
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
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onPageStart("PG_MY_SLN");
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPageEnd("PG_MY_SLN");
		    MobclickAgent.onPause(this);
		}
}
