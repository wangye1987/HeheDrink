/*
package com.heheys.ec.controller.fragment;

import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.model.adapter.SalonListAdapter;
import com.heheys.ec.model.dataBean.SalonListBean;
import com.heheys.ec.model.dataBean.SalonListBean.SalonResultBean.SalonInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

public class SalonListFragment extends BaseFragment {

	private View view;
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
	private static Context mConxtet;
	private View viewNoSalon;
	RadioGroup radioGroup;
	public SalonListFragment(){
	
	}
	public SalonListFragment(RadioGroup radioGroup){
		this.radioGroup = radioGroup;
	}
	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.salon, container, true);
		mConxtet = getActivity();
		initView();
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		messageHandler = new SalonMessageHandler(this);
	}

	//初始化界面元素
	private void initView() {
		mListView = (RefreshListView) view.findViewById(R.id.salon_lv);
		viewNoSalon = view.findViewById(R.id.base_activity_no_salon);
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		userId = ((Activity) mConxtet).getIntent().getStringExtra(ConstantsUtil.USER_ID_KEY);
//		isPush = getIntent().getBooleanExtra("isPush", false);
//		ToastUtil.showToast(baseContext, "userId==" + userId);
//		 if(IsLogin.isLogin(this)){
//			 ResultBean userInfoResultBean = (ResultBean) SharedPreferencesUtil
//			 .getObject(baseActivity, "resultbean");
//			 userId = userInfoResultBean.getId();
//			 }
		ApiHttpCilent.getInstance(mConxtet).getSalonList(mConxtet, startIndex,
				endIndex, userId, new SalonListRequestCallBack());
	}
	private void Dimess() {
		((Activity) mConxtet).runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	*/
/**
	 * 
	 * Describe:沙龙列表请求回调
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 *//*

	public class SalonListRequestCallBack extends
			BaseJsonHttpResponseHandler<SalonListBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, SalonListBean arg4) {
			Dimess();
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
	*/
/**
	 * 
	 * Describe:沙龙列表消息处理
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 *//*

	public static class SalonMessageHandler extends
			WeakHandler<SalonListFragment> {

		public SalonMessageHandler(SalonListFragment reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				*/
/**
				 * 处理成功的数据
				 *//*

				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				*/
/**
				 * 处理失败的数据
				 *//*

				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(mConxtet, messageString);
					*/
/**
					 * 处理失败数据
					 *//*

				} else {
					ToastUtil.showToast(mConxtet, "请求失败");

				}

				break;
			}
		}

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
	*/
/**
	 * 
	 * Describe:沙龙列表空白页面
	 * 
	 * Date:2015-10-30
	 * 
	 * Author:liuzhouliang
	 *//*

	protected void showNoSalonView() {
//		 if (llParent != null) {
//		 llParent.removeViewAt(1);
//		 }
		viewNoSalon.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.GONE);

	}
	*/
/**
	 * 
	 * Describe:绑定控件数据
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:liuzhouliang
	 *//*

	private void bindViewData() {
		if (startIndex == 1) {
			*/
/**
			 * 初始化数据
			 *//*

			hideRefreshView();
			if (salonListBean.getResult() != null
					&& salonListBean.getResult().getList() != null
					&& salonListBean.getResult().getList().size() > 0) {
				salonList = salonListBean.getResult().getList();
				madapter = new SalonListAdapter(userId,salonList, mConxtet, salonListBean
						.getResult().getBaseurl());
				mListView.setAdapter(madapter);
			} else {
				*/
/**
				 * 处理数据为空
				 *//*

//				ToastUtil.showToast(baseContext, "数据为空");
				showNoSalonView();
			}
		} else {
			*/
/**
			 * 分页数据
			 *//*

			if (salonListBean.getResult() != null
					&& salonListBean.getResult().getList() != null
					&& salonListBean.getResult().getList().size() > 0) {
				*/
/**
				 * 分页数据存在
				 *//*

//				ToastUtil.showToast(baseContext, "分页起始页码" + startIndex);
				List<SalonInfor> pageData = salonListBean.getResult().getList();
				if (salonList != null) {
					if (isLoadMore) {
						salonList.addAll(pageData);
						hideLoadMoreView();
					}

				}
			} else {
				*/
/**
				 * 分页数据不存在
				 *//*

				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
//				ToastUtil.showToast(baseContext, "数据加载完毕==页码==" + startIndex);
			}
		}

	}
	@Override
	protected void setViewListener() {
		mListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == SCROLL_STATE_IDLE){
					radioGroup.setVisibility(View.VISIBLE);
				}else{
					radioGroup.setVisibility(View.GONE);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return false;
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
}
*/
