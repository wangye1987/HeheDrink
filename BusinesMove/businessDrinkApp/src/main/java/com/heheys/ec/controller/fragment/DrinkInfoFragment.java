/*
package com.heheys.ec.controller.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RadioGroup;
import android.widget.AbsListView.OnScrollListener;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.model.adapter.DrinkInfoListAdapter;
import com.heheys.ec.model.adapter.SalonListAdapter;
import com.heheys.ec.model.dataBean.DrinkInfoBaseBean;
import com.heheys.ec.model.dataBean.DrinkInfoBean;
import com.heheys.ec.model.dataBean.SalonListBean;
import com.heheys.ec.model.dataBean.SalonListBean.SalonResultBean.SalonInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

*/
/**
 * @author wangkui
 * 
 * @version 1.0
 * 
 * 酒讯界面 
 * 
 * *//*

public class DrinkInfoFragment extends BaseFragment {

	private View view;
	private RefreshListView mListView;
	// 列表数据开始和结束位置
		private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
				endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
		// 标记是否是下拉刷新状态
		private boolean isRefresh;
		// 标记是否是分页状态
		private boolean isLoadMore;
		private MyDrinkInfoHandler myDrinkInfoHandler;
		private Context mContext;
		private DrinkInfoBaseBean drinkListBean;
		private List<DrinkInfoBean> drinkList;
		private DrinkInfoListAdapter madapter;
		private View viewNoSalon;
		private RadioGroup radioGroup;
		protected DrinkInfoFragment(RadioGroup radioGroup){
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
		view = inflater.inflate(R.layout.drink_info, container, true);
		initView();
		return view;
	}

	//初始化组件
	private void initView() {
		mContext = getActivity();
		mListView = (RefreshListView) view.findViewById(R.id.drink_info_lv);
		viewNoSalon = view.findViewById(R.id.base_activity_no_salon);
		myDrinkInfoHandler = new MyDrinkInfoHandler(this);
	}

	@Override
	protected void getNetData() {
		String jsonst = ReadJson();
		Gson gson = new Gson();
		drinkListBean = gson.fromJson(jsonst, DrinkInfoBaseBean.class);
		bindViewData();
//		ApiHttpCilent.getInstance(getContext()).DrinksInfoList(mContext, new BaseJsonHttpResponseHandler<DrinkInfoBaseBean>() {
//			
//
//			@Override
//			protected DrinkInfoBaseBean parseResponse(String response, boolean arg1)
//					throws Throwable {
//				// TODO Auto-generated method stub
//				ApiHttpCilent.loading.dismiss();
//				Gson gson = new Gson();
//				drinkListBean = gson.fromJson(response, DrinkInfoBaseBean.class);
//				Message message = Message.obtain();
//				if ("1".equals(drinkListBean.getStatus())) {// 正常
//					message.what = ConstantsUtil.HTTP_SUCCESS;
//					message.obj = drinkListBean.getResult();
//				} else {
//					message.what = ConstantsUtil.HTTP_FAILE;// 错误
//					message.obj = drinkListBean.getError().getInfo();
//				}
//				myDrinkInfoHandler.sendMessage(message);
//				return drinkListBean;
//			}
//			
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, String arg2,
//					DrinkInfoBaseBean arg3) {
//				
//			}
//			
//			@Override
//			public void onFailure(int arg0, Header[] arg1, Throwable arg2, String arg3,
//					DrinkInfoBaseBean arg4) {
//				ApiHttpCilent.loading.dismiss();
//				Message message = Message.obtain();
//				message.what = ConstantsUtil.HTTP_FAILE;// 错误
//				myDrinkInfoHandler.sendMessage(message);
//				
//			}
//		});
	}

	//测试方法 读取本地json
	private String ReadJson(){
		try {
			InputStream in = mContext.getResources().getAssets().open("json.txt");
			byte buffer[] = new byte[in.available()];
			in.read(buffer);
			String json = new String(buffer,"UTF-8");
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public class MyDrinkInfoHandler extends WeakHandler<DrinkInfoFragment>{

		public MyDrinkInfoHandler(DrinkInfoFragment reference) {
			super(reference);
		}

		@Override
		public DrinkInfoFragment getReference() {
			// TODO Auto-generated method stub
			return super.getReference();
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				getReference().bindViewData();
				break;
			case ConstantsUtil.HTTP_FAILE:
				
				break;

			default:
				break;
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
	private void hideRefreshView() {
		if (isRefresh) {
			mListView.onRefreshComplete();
			isRefresh = false;
		}
	}

	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
			madapter.setNewData(drinkList);
			isLoadMore = false;
		}
	}
	
	protected void showNoSalonView() {
		// if (llParent != null) {
		// llParent.removeViewAt(1);
		// }
		llParent.setVisibility(View.GONE);
		viewNoSalon.setVisibility(View.VISIBLE);

	}
	//绑定数据
		private void bindViewData() {
			if (startIndex == 1) {
				*/
/**
				 * 初始化数据
				 *//*

				hideRefreshView();
				if (drinkListBean.getResult() != null
						&& drinkListBean.getResult().getList() != null
						&& drinkListBean.getResult().getList().size() > 0) {
					drinkList = drinkListBean.getResult().getList();
					madapter = new DrinkInfoListAdapter(drinkList,mContext,drinkListBean.getResult().getBaseurl());
					mListView.setAdapter(madapter);
				} else {
					*/
/**
					 * 处理数据为空
					 *//*

//					ToastUtil.showToast(baseContext, "数据为空");
					showNoSalonView();
				}
			} else {
				*/
/**
				 * 分页数据
				 *//*

				if (drinkListBean.getResult() != null
						&& drinkListBean.getResult().getList() != null
						&& drinkListBean.getResult().getList().size() > 0) {
					*/
/**
					 * 分页数据存在
					 *//*

//					ToastUtil.showToast(baseContext, "分页起始页码" + startIndex);
					List<DrinkInfoBean> pageData = drinkListBean.getResult().getList();
					if (drinkListBean != null) {
						if (isLoadMore) {
							drinkList.addAll(pageData);
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
//					ToastUtil.showToast(baseContext, "数据加载完毕==页码==" + startIndex);
				}
			}
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
