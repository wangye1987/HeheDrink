package com.heheys.ec.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.model.adapter.MyPointsAdapter;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.MyPointBaseBean;
import com.heheys.ec.model.dataBean.MyPointBaseBean.MyPointsBean;
import com.heheys.ec.model.dataBean.PointsBaseBean;
import com.heheys.ec.model.dataBean.PointsBaseBean.Poinitem;
import com.heheys.ec.model.dataBean.SignPoingBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.ValidatorUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;

//import com.heheys.ec.thirdPartyModule.IM.LoginSampleHelper;

/**
 * @author wangkui
 * 
 *         2016-07-07
 * 
 *         我的积分
 * 
 */
public class MyPointsActivity extends BaseActivity implements
		OnRefreshListener, OnMoreListener, OnClickListener {
	// private RefreshOrLoadMoreListViewParent mListViewRefreshListView;
	private RefreshListView mListView;
	private Context mContext;
	private MyPointBaseBean mypointbean;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	private ArrayList<Poinitem> data = null;
	private MyPointsAdapter mAdapter;
	private PointsHandler mHandler;
	private PointsBaseBean orderbean;// 服务器返回数据
	private TextView help, tv_point, tv_signnotice;
//	private Button bt_remark;
	private boolean issign;// 是否签到
	private String helpurl;// 关于
	private SignPoingBean signPoingBean;
	private LinearLayout lv_empty;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.my_points);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		mHandler = new PointsHandler(this);
		data = new ArrayList<Poinitem>();
		mAdapter = new MyPointsAdapter(data, mContext);
		mListView.setAdapter(mAdapter);
		// GsonJson("points.txt");
		mListView.setonLoadListener(this);
		mListView.setonRefreshListener(this);
		help.setOnClickListener(this);
		help.setOnClickListener(this);
//		bt_remark.setOnClickListener(this);
		// 我的积分余额
	   ApiHttpCilent.getInstance(getApplicationContext()).UserPoints(baseActivity,
						new UserPonitsCallBack());
	}

	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.help:
			if(!StringUtil.isEmpty(helpurl)){
				Intent intent = new Intent(baseActivity, JDPayActivity.class);//获取电子合同
				intent.putExtra("url",helpurl);
				intent.putExtra("title", "积分使用帮助");
				intent.putExtra("flag", 3);//积分合同
				StartActivityUtil.startActivity(baseActivity, intent);
			}
			break;
//		case R.id.bt_remark:
//			//签到
//			ApiHttpCilent.getInstance(baseContext).PonitSign(mContext, new SignPonitsCallBack());
//			break;
		default:
			break;
		}
		
		}

	private void GsonJson(String name) {
		Gson gson = new Gson();
		orderbean = gson.fromJson(ValidatorUtil.ReadJson(mContext, name),
				PointsBaseBean.class);
		bindData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
		mContext = MyPointsActivity.this;
		mListView = (RefreshListView) findViewById(R.id.list_lv);
		help = (TextView) findViewById(R.id.help);
//		bt_remark = (Button) findViewById(R.id.bt_remark);
		tv_point = (TextView) findViewById(R.id.tv_point);
		tv_signnotice = (TextView) findViewById(R.id.tv_signnotice);
		lv_empty = (LinearLayout) findViewById(R.id.lv_empty);
		help.setOnClickListener(this);
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
		// 我的积分明细
		ApiHttpCilent.getInstance(getApplicationContext()).UserPointsDetail(baseActivity,startIndex,endIndex,
				new PonitsCallBack());

	}

	private void Dimess() {
		((Activity) mContext).runOnUiThread(new Runnable() {
			public void run() {
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	// 签到
	class SignPonitsCallBack extends BaseJsonHttpResponseHandler<SignPoingBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, SignPoingBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				SignPoingBean arg3) {
			Dimess();
		}

		@Override
		protected SignPoingBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			signPoingBean = gson.fromJson(response, SignPoingBean.class);
			Message message = Message.obtain();
			if ("1".equals(signPoingBean.getStatus())) {// 签到
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST;
				message.obj = signPoingBean.getResult();
			} else if ("0".equals(signPoingBean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = signPoingBean.getError();
			}
			mHandler.sendMessage(message);
			return signPoingBean;
		}
	}

	class UserPonitsCallBack extends
			BaseJsonHttpResponseHandler<MyPointBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, MyPointBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				MyPointBaseBean arg3) {
			Dimess();
		}

		@Override
		protected MyPointBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			mypointbean = gson.fromJson(response, MyPointBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(mypointbean.getStatus())) {// 我的积分
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				message.obj = mypointbean.getResult();
			} else if ("0".equals(mypointbean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = mypointbean.getError();
			}
			mHandler.sendMessage(message);
			return mypointbean;
		}
	}

	class PonitsCallBack extends BaseJsonHttpResponseHandler<PointsBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, PointsBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				PointsBaseBean arg3) {
			Dimess();
		}

		@Override
		protected PointsBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			orderbean = gson.fromJson(response, PointsBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(orderbean.getStatus())) {
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = orderbean.getResult();
			} else if ("0".equals(orderbean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = orderbean.getError();
			}
			mHandler.sendMessage(message);
			return orderbean;
		}
	}

	// 列表获取成功
	class PointsHandler extends WeakHandler<MyPointsActivity> {
		public PointsHandler(MyPointsActivity reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:// 我的积分明细
				getReference().bindData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:// 我的积分余额
				getReference().bindPonitData();
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:// 签到
				getReference().setNewPoint();
				break;
			case ConstantsUtil.HTTP_FAILE:
				ErrorBean errorBean = (ErrorBean) msg.obj;
				if(errorBean != null){
				if ("100".equals(errorBean.getCode())) {
					// 登录失效
					ToastUtil.showToast(getReference(), errorBean.getInfo());
					//注销阿里IM
//					LoginSampleHelper.getInstance().loginOut_Sample(mContext);
				} else {
					if (!StringUtil.isEmpty(errorBean.getInfo()))
						ToastUtil.showToast(mContext, errorBean.getInfo());
					hideRefreshView();
					hideLoadMoreView();
				}}
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	// 获取我的积分数据 填充
	private  void bindPonitData() {
		if (mypointbean != null && mypointbean.getResult() != null) {
			MyPointsBean myPointsResult = mypointbean.getResult();
			tv_point.setText(StringUtil.isEmpty(myPointsResult.getScore()) ? ""
					: myPointsResult.getScore());
			issign = myPointsResult.getIssign();
			helpurl = myPointsResult.getHelphtml();
			if (issign) {
				tv_signnotice.setVisibility(View.VISIBLE);
//				bt_remark.setVisibility(View.VISIBLE);
//				bt_remark.setEnabled(false);
//				bt_remark.setText("已签到");
//				bt_remark.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
//				bt_remark.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
				tv_signnotice.setText(StringUtil.isEmpty(myPointsResult
						.getTips()) ? "" : myPointsResult.getTips());
			} else {
				tv_signnotice.setVisibility(View.INVISIBLE);
//				bt_remark.setEnabled(true);
//				bt_remark.setVisibility(View.VISIBLE);
			}
		}
	}

	//签到获取积分总分
	private void setNewPoint(){
		if(signPoingBean != null && signPoingBean.getResult()!=null && !StringUtil.isEmpty(signPoingBean.getResult().getScore())){
			tv_point.setText(signPoingBean.getResult().getScore());
//			bt_remark.setEnabled(false);
//			bt_remark.setText("已签到");
//			bt_remark.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
			tv_signnotice.setText(StringUtil.isEmpty(signPoingBean.getResult()
					.getTips()) ? "" : signPoingBean.getResult().getTips());
			tv_signnotice.setVisibility(View.VISIBLE);
			getNetData();//重新获取积分明细
		}
	} 
	// 适配数据
	private void bindData() {
		// TODO Auto-generated method stub
		if (startIndex == 1) {
			// 第一页
			if (orderbean != null && orderbean.getResult() != null
					&& orderbean.getResult().getList() != null) {
				if(orderbean.getResult().getList().size()>0){
					data = orderbean.getResult().getList();
					mAdapter.setNewData(data);
					mAdapter.notifyDataSetChanged();
					hideRefreshView();
					lv_empty.setVisibility(View.GONE);
					mListView.setVisibility(View.VISIBLE);
				}else{
					//显示没有数据视图
					lv_empty.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
				}
			}
		} else {
			if (orderbean != null && orderbean.getResult() != null
					&& orderbean.getResult().getList() != null) {
				data.addAll(orderbean.getResult().getList());
				mAdapter.setNewData(data);
				mAdapter.notifyDataSetChanged();
				hideLoadMoreView();
			} else {
				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
			}
		}
	}

	private void hideRefreshView() {
		if (isRefresh) {
			mListView.onRefreshComplete();
			isRefresh = false;
		}
	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "我的积分";
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
		// initmore();
	}

	private void initmore() {
		// TODO Auto-generated method stub
		// GsonJson("pointss.txt");
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
		// GsonJson("points.txt");
		getNetData();
	}

	// 初始化开始和结束页面
	private void initReuquestParams() {
		startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
		endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	}

	// 更多界面
	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
			mAdapter.setNewData(data);
			mAdapter.notifyDataSetChanged();
			isLoadMore = false;
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("PG_MY_CRD");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MobclickAgent.onPageEnd("PG_MY_CRD");
		MobclickAgent.onPause(this);
	}
}