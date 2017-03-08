package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.lib.view.RefreshListView.OnMoreListener;
import com.heheys.ec.lib.view.RefreshListView.OnRefreshListener;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.ShopListAdapter;
import com.heheys.ec.model.dataBean.BrandBaseBean;
import com.heheys.ec.model.dataBean.BrandBaseBean.BrandBean;
import com.heheys.ec.model.dataBean.BrandBaseBean.BrandList;
import com.heheys.ec.model.dataBean.ShopListBaseBean;
import com.heheys.ec.model.dataBean.ShopListBaseBean.ShopResult;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间
 *            ：2016-3-31 下午5:32:57 类说明 商铺列表
 */
public class HomeShopActivity extends BaseActivity implements
		OnRefreshListener, OnMoreListener, OnClickListener {

	private Context mContext;
	// 列表数据开始和结束位置
	private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
			endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
	// 标记是否是下拉刷新状态
	private boolean isRefresh;
	// 标记是否是分页状态
	private boolean isLoadMore;
	// 店铺展示组件
	private RefreshListView mListView;
	// 商品种类组件
	private LinearLayout group_bland,group_type_bland;
	private PopupWindow mPopupWindow;
	String name;// 酒名称
	String winetype;// 酒类别
	private ShopHandler mHandler = new ShopHandler(this);
	private ShopListBaseBean basebean;
	private ShopListAdapter mAdapter;
	private List<ShopResult> data;
	private BrandBaseBean brandNameList;//商铺列表
	private ListView lv_brand;
	private LinearLayout layout;
	private ImageView group_title_down;
	private BrandList brandlist;
	private boolean isSeach  = false;
	private EditText etInput;
	private TextView tv_null;
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.heheshop);
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		mContext = HomeShopActivity.this;
		mListView = (RefreshListView) findViewById(R.id.list_shop);
		group_bland = (LinearLayout) findViewById(R.id.group_type_bland);
		group_title_down = (ImageView) findViewById(R.id.group_title_down);
		etInput = (EditText) findViewById(R.id.shop_title_search_et);
		tv_null = (TextView) findViewById(R.id.tv_null);
		group_bland.setOnClickListener(this);
		ApiHttpCilent.getInstance(getApplicationContext()).BrandList(baseContext,"", new RequestBrandNameCallBack());
	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		ApiHttpCilent.getInstance(getApplicationContext()).ShoppingList(mContext,
				name, winetype,startIndex,endIndex, new ShopCallBack());
	}
	/**
	 * 品牌回调
	 * */
	public class RequestBrandNameCallBack extends
	BaseJsonHttpResponseHandler<BrandBaseBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BrandBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BrandBaseBean arg3) {
		}
		
		@Override
		protected BrandBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			brandNameList = gson.fromJson(response, BrandBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(brandNameList.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
				message.obj = brandNameList.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = brandNameList.getError().getInfo();
			}
			mHandler.sendMessage(message);
			return brandNameList;
		}
	}
	// 列表获取成功
		class ShopHandler extends WeakHandler<HomeShopActivity> {
			@SuppressLint("HandlerLeak")
			public ShopHandler(HomeShopActivity reference) {
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
				case ConstantsUtil.HTTP_SUCCESS_LOGIN:
					brandlist = (BrandList) msg.obj;
					getReference().bindBrandData();
					break;
				case ConstantsUtil.HTTP_FAILE:
					String error = (String) msg.obj;
					ToastUtil.showToast(HomeShopActivity.this, error);
					break;
				default:
					break;
				}
			}
		}
		private void Dimess() {
			((Activity) mContext).runOnUiThread(new Runnable() {
				public void run() {
					if(ApiHttpCilent.loading !=null && ApiHttpCilent.loading.isShowing())
						ApiHttpCilent.loading.dismiss();
				}
			});
		}
		
		/**
		 * 适配品牌列表
		 * */
		public void bindBrandData() {
			showBrandWindow((Activity) mContext, group_bland, R.layout.show_brand);
		}
		
		public PopupWindow showBrandWindow(Activity activity, View position,
				int layoutId) {
			View mPopMenuView = null;
			mPopMenuView = LayoutInflater.from(activity).inflate(layoutId, null);
			layout = (LinearLayout) mPopMenuView
					.findViewById(R.id.groupbuy_brand_parent);
			lv_brand = (ListView) mPopMenuView
					.findViewById(R.id.lv_brand);
			if(brandlist.getList()!=null){
				BrandBean branBean = new BrandBean();
				branBean.setId("");
				branBean.setId("");
				List<BrandBean> listdate = brandlist.getList();
				listdate.add(0, branBean);
				lv_brand.setAdapter(new BrandAdpater(HomeShopActivity.this,listdate));
			}
//			bindBrandData();
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					mPopupWindow.dismiss();
				}
			});
			lv_brand.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mPopupWindow.dismiss();
					winetype = brandlist.getList().get(position).getId();
					isSeach = true;
					initReuquestParams();
				    ApiHttpCilent.getInstance(getApplicationContext()).ShoppingList(mContext,
									name, winetype,startIndex,endIndex, new ShopCallBack());
					
				}
			});
			mPopupWindow = new PopupWindow(mPopMenuView,
					ViewUtil.getScreenWith(activity), LayoutParams.WRAP_CONTENT,
					false);
			mPopupWindow
					.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			mPopupWindow.setFocusable(true);
			mPopupWindow.setTouchable(true);
			mPopupWindow.setOutsideTouchable(false);
//			 mPopMenuView.getBackground().setAlpha(230);
//			layout.getBackground().setAlpha(230);
			mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
			mPopupWindow.update();
			mPopupWindow.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss() {
					group_title_down.setImageResource(R.drawable.groupbuy_arrow_up_gray);
//					group_title_down.setTag("DOWN");
				}
			});
			return mPopupWindow;
		}
		
		class BrandAdpater extends BaseListAdapter<BrandBean>{

			public BrandAdpater(Context context, List<BrandBean> data) {
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
				BrandBean brand = dataList.get(position);
				TextView tv_brand = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_brand);
				if(brand!= null){
					if(position==0)
						tv_brand.setText("全部");
					else
						tv_brand.setText(brand.getName());
				}
				return convertView;
			}
			
		}
	//商铺列表集合
	class ShopCallBack extends BaseJsonHttpResponseHandler<ShopListBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, ShopListBaseBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			mHandler.sendMessage(message);
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				ShopListBaseBean arg3) {
			Dimess();
		}
		
		@Override
		protected ShopListBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			basebean = gson.fromJson(response, ShopListBaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(basebean.getStatus())) {// 正常获取商铺列表
				message.what = ConstantsUtil.HTTP_SUCCESS;
			} else if ("0".equals(basebean.getStatus())) {
				message.what = ConstantsUtil.HTTP_FAILE;//
				message.obj = basebean.getError().getInfo();
			}
			mHandler.sendMessage(message);
			return basebean;
		}
	}
	@Override
	protected void reloadCallback() {

	}


	public void bindData() {
		// TODO Auto-generated method stub
		if(startIndex == 1){
			//首页第一页
			if(basebean !=null && basebean.getResult().getList()!=null && basebean.getResult().getList().size()>0){
				data = basebean.getResult().getList();
				if(isSeach){
					mAdapter.setNewData(data);
					mAdapter.notifyDataSetChanged();
				}else{
					mAdapter = new ShopListAdapter(data, mContext);
					mListView.setAdapter(mAdapter);
				}
				hideRefreshView();
//				linear_nodata.setVisibility(View.GONE);
				mListView.setVisibility(View.VISIBLE);
				tv_null.setVisibility(View.GONE);
			}else{
				//空数据
				mListView.setVisibility(View.GONE);
				tv_null.setVisibility(View.VISIBLE);
			}
		}else{
			tv_null.setVisibility(View.GONE);
			//分页
			if(basebean !=null && basebean.getResult().getList()!=null && basebean.getResult().getList().size()>0){
				ArrayList<ShopResult> listdata = (ArrayList<ShopResult>) basebean.getResult().getList();
				data.addAll(listdata);
				hideLoadMoreView();
			}else{
				// 回滚页码
				startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
				hideLoadMoreView();
			}
		}
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "品牌云店";
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
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		mListView.setonLoadListener(this);
		mListView.setonRefreshListener(this);
		etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					name = etInput.getText().toString();
					if (StringUtil.isEmpty(name)) {
						name = "";
					}
					if(ToastNoNetWork.ToastError(baseActivity))
						return false;
					getNetData();
					MobclickAgent.onEvent(baseActivity,"C_SHP_LST_1");
				}
				return false; 
			}
		});
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

	// 初始化开始和结束页面
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

	// 更多界面
	private void hideLoadMoreView() {
		if (isLoadMore) {
			mListView.onLoadComplete();
//			mAdapter.setNewData(data);
			isLoadMore = false;
		}
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.group_type_bland:
			MobclickAgent.onEvent(baseActivity,"C_SHP_LST_2");
			if(ToastNoNetWork.ToastError(baseActivity))
				return;
			group_title_down.setImageResource(R.drawable.groupbuy_arrow_down_gray);
			if(mPopupWindow!=null){
				if(!mPopupWindow.isShowing()){
					mPopupWindow.showAsDropDown(group_bland,
					ViewUtil.getScreenWith( mContext), 0);
				}else{
					mPopupWindow.dismiss();
				}
			}
			break;
		default:
			break;
		}
	}
	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onPageStart("PG_SHOP_LST");
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPageEnd("PG_SHOP_LST");
		    MobclickAgent.onPause(this);
		}
}
