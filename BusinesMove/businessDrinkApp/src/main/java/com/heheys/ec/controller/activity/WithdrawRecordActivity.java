package com.heheys.ec.controller.activity;

import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.RecordListAdapter;
import com.heheys.ec.model.dataBean.BaseRecordBean;
import com.heheys.ec.model.dataBean.BaseRecordBean.MyRecordBean;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间
 *            ：2016-3-28 下午3:22:42 类说明 提现记录界面
 */
public class WithdrawRecordActivity extends BaseActivity implements
		OnRefreshListener, OnMoreListener {
	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;
	private RefreshListView mListView;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	private Context mContext;
	// 消息桥梁balanceHandler
	private RecordHandler mHandler = new RecordHandler(this);
	private BaseRecordBean recordBean;
	private List<MyRecordBean> listbean;
	private RecordListAdapter madapter;

	//6224 1211 0916 5333
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.record_item);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		mContext = WithdrawRecordActivity.this;
		mListView = (RefreshListView) findViewById(R.id.myrecord_lv);
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
	}

	@Override
	protected void getNetData() {
		// 提现记录
		ApiHttpCilent.getInstance(getApplicationContext()).getPayListRecord(baseActivity,
				"", startIndex, endIndex, new RecordCallBack());
	}

	private void Dimess() {
		WithdrawRecordActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	// 查询我的提现记录
	class RecordCallBack extends BaseJsonHttpResponseHandler<BaseRecordBean> {
		

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseRecordBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseRecordBean arg3) {
			Dimess();
		}

		@Override
		protected BaseRecordBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			recordBean = gson.fromJson(response, BaseRecordBean.class);
			Message message = Message.obtain();
			if ("1".equals(recordBean.getStatus())) {// 正常获取提现记录
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = recordBean.getResult();
			} else if ("0".equals(recordBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = recordBean.getError();
			}
			mHandler.sendMessage(message);
			return recordBean;
		}
	}

	// 列表获取成功
	class RecordHandler extends WeakHandler<WithdrawRecordActivity> {

		@SuppressLint("HandlerLeak")
		public RecordHandler(WithdrawRecordActivity reference) {
			super(reference);
		}

		@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					switch (msg.what) {
					case ConstantsUtil.HTTP_SUCCESS:
						getReference().bindData();
						break;
					case ConstantsUtil.HTTP_FAILE:
						ErrorBean error = (ErrorBean) msg.obj;
						ToastUtil.showToast(baseActivity, error.getInfo());
						break;
					default:
						break;
					}
				}
	}

	/*
	 * 绑定数据
	 * 
	 * */
	private void bindData(){
		if(startIndex==1){
			//第一页数据刷新界面
			if(recordBean!=null && recordBean.getResult().getList()!=null && recordBean.getResult().getList().size()>0){
				listbean = recordBean.getResult().getList();
				madapter = new RecordListAdapter(listbean,mContext);
				mListView.setAdapter(madapter);
				hideRefreshView();
			}else{
				ToastUtil.showToast(mContext, "暂无提现记录");
			}
		}else{
			//分页数据
			if(recordBean!=null && recordBean.getResult().getList()!=null && recordBean.getResult().getList().size()>0){
				List<MyRecordBean> morelist = recordBean.getResult().getList();//分页数据
				if (isLoadMore) {
					listbean.addAll(morelist);
					hideLoadMoreView();
				}
			}else{
				//回滚数据
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
			}
		}
	}
	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		mListView.setonLoadListener(this);
		mListView.setonRefreshListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "提现记录";
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
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (!NetWorkState.isNetWorkConnection(mContext)) {
			ToastUtil.showToast(mContext, "网络连接失败");
			return;
		}
		isLoadMore = true;
		startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
		getNetData();

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (!NetWorkState.isNetWorkConnection(mContext)) {
			ToastUtil.showToast(mContext, "网络连接失败");
			return;
		}
		isRefresh = true;
		initReuquestParams();
		getNetData();
	}

	//初始化开始和结束页面
	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	//
	private void hideRefreshView() {
		if (isRefresh) {
			mListView.onRefreshComplete();
			isRefresh = false;
		}
	}

	//更多界面
	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
			madapter.setData(listbean);
			isLoadMore = false;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("PG_WTH_REC");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("PG_WTH_REC");
		MobclickAgent.onPause(this);
	}
}