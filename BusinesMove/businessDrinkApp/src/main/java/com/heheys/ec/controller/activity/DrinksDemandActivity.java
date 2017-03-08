package com.heheys.ec.controller.activity;

import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.DrinksDemandAdapter;
import com.heheys.ec.model.dataBean.DrinksDemandBean;
import com.heheys.ec.model.dataBean.DrinksDemandBean.DrinksDemandData.DrinksDemandInfor;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * Describe:酒水需求
 *
 * Date:2015年11月23日下午3:09:38
 *
 * Author:LZL
 *
 */
public class DrinksDemandActivity extends BaseActivity implements OnItemClickListener{
	private View line;
	private PopupWindow mPopupWindow;
	private DrinksDemandAdapter drinksDemandAdapter;
	private static MessgaeHandler messageHandler;
	private static DrinksDemandBean drinksDemandBean;
	private static List<DrinksDemandInfor> drinksDemandList;
	private ListView mListView;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.drinks_demand);

		initData();
	}

	/**
	 * 
	 * Describe:初始化数据
	 *
	 * Date:2015年11月24日下午5:11:07
	 *
	 * Author:LZL
	 *
	 */
	private void initData() {
		// TODO Auto-generated method stub
		messageHandler = new MessgaeHandler(this);

	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub
		line = findViewById(R.id.drinks_demand_line);
		mListView = (ListView) findViewById(R.id.drinks_demand_lv);

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
		if(ToastNoNetWork.ToastError(baseContext))
		return;	
		ApiHttpCilent.getInstance(baseContext).getDrinksDemandList(baseContext,
				new RequestCallBack());

	}
	void Dimess(){
	DrinksDemandActivity.this.runOnUiThread(new Runnable() {
		public void run() {
			if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
				ApiHttpCilent.loading.dismiss();
		}
	});
	}
	/**
	 * 
	 * 网络数据请求回调
	 *
	 */
	public class RequestCallBack extends
			BaseJsonHttpResponseHandler<DrinksDemandBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, DrinksDemandBean arg4) {
			Dimess();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			messageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				DrinksDemandBean arg3) {
		}

		@Override
		protected DrinksDemandBean parseResponse(String response, boolean arg1)
				throws Throwable {
			Dimess();
			Gson gson = new Gson();
			drinksDemandBean = gson.fromJson(response, DrinksDemandBean.class);
			drinksDemandList = drinksDemandBean.getResult()
					.getDrinksDemandList();
			Message message = Message.obtain();
			if ("1".equals(drinksDemandBean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = drinksDemandBean.getResult();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = drinksDemandBean.getError().getInfo();
			}
			messageHandler.sendMessage(message);
			return drinksDemandBean;
		}

	}

	/**
	 * 
	 * 处理请求数据结果消息
	 *
	 */
	public static class MessgaeHandler extends
			WeakHandler<DrinksDemandActivity> {

		public MessgaeHandler(DrinksDemandActivity reference) {
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
				if (drinksDemandList != null && drinksDemandList.size() > 0) {
					getReference().drinksDemandAdapter = new DrinksDemandAdapter(
							DrinksDemandActivity.drinksDemandList,
							getReference());
					getReference().bindViewData();

				} else {
					/**
					 * 处理空数据
					 */
					getReference().showNoDrinksDemand();
				}

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
					getReference().showLoadFailView();
				} else {
					ToastUtil.showToast(getReference(), "请求失败");
					getReference().showLoadFailView();
				}
				break;
			}
		}
	}

	/**
	 * 
	 * Describe:绑定控件数据
	 *
	 * Date:2015年11月26日下午4:45:42
	 *
	 * Author:LZL
	 *
	 */
	private void bindViewData() {
		mListView.setAdapter(drinksDemandAdapter);
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText("添加");
		tvRight.setOnClickListener(this);
		hideNoDrinksDemand();
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub
		getNetData();
	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub
		mListView.setOnItemClickListener(this);
	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "酒水需求";
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		switch (v.getId()) {
		case R.id.base_activity_no_drink_demand_add:
			/**
			 * 添加酒水需求
			 */
			map.put("Demand_list_add","");
			MobclickAgent.onEvent(baseActivity, "Demand_list_add", map); 
			showBrandWindow(line, R.layout.add_drinks_demand_type);
			break;
		case R.id.base_activity_title_right_righttv:
			/**
			 * 买酒需求
			 */
			map.put("Demand_list_add","");
			MobclickAgent.onEvent(baseActivity, "Demand_list_add", map); 
			showBrandWindow(line, R.layout.add_drinks_demand_type);
			
//			StartActivityUtil.startActivity(baseActivity, intent);
			break;
		case R.id.base_activity_title_backicon:
			map.put("Demand_list_return","");
			MobclickAgent.onEvent(baseActivity, "Demand_list_return", map); 
			// 返回键处理
			onBackPressed();
			break;
		default:
			break;
		}
	}

	private void showCheckDialog() {
		Dialog dialog = new Dialog(this, R.style.popwin_anim_style);
		dialog.setCanceledOnTouchOutside(false);
		View view = baseLayoutInflater.inflate(R.layout.add_drinks_demand_type,
				null);
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.popwin_anim_style);
		WindowManager.LayoutParams wm = window.getAttributes();
		wm.gravity = Gravity.CENTER;
		wm.width = wm.MATCH_PARENT;
		wm.alpha = 229;
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

	}

	public PopupWindow showBrandWindow(View position, int layoutId) {
		View mPopMenuView = null;
		RelativeLayout llRootView;
		TextView tvBuy, tvSell;
		ImageView ivClose;
		mPopMenuView = LayoutInflater.from(this).inflate(layoutId, null);
		llRootView = (RelativeLayout) mPopMenuView
				.findViewById(R.id.add_drinks_demand_type_rootview);
		llRootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				mPopupWindow.dismiss();
			}
		});
		ivClose = (ImageView) mPopMenuView
				.findViewById(R.id.add_drinks_demand_type_close);
		tvBuy = (TextView) mPopMenuView
				.findViewById(R.id.add_drinks_demand_type_buy);
		tvSell = (TextView) mPopMenuView
				.findViewById(R.id.add_drinks_demand_type_sell);
		final HashMap<String,String> map = new HashMap<String,String>();
		ivClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
				map.put("Requirement_type_close","");
				MobclickAgent.onEvent(baseActivity, "Requirement_type_close", map); 
			}
		});
		tvBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
				/**
				 * 买酒需求
				 */
				
				map.put("Requirement_type_buy","");
				MobclickAgent.onEvent(baseActivity, "Requirement_type_buy", map); 
				Intent intent = new Intent(baseActivity,
						DrinksDemandBuyActivity.class);
				intent.putExtra("buyorsale", "buy");//买货跳转
				StartActivityUtil.startActivityForResult(baseActivity, intent, 1001);
			}
		});
		tvSell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				map.put("Requirement_type_sell","");
				MobclickAgent.onEvent(baseActivity, "Requirement_type_sell", map); 
				mPopupWindow.dismiss();
				Intent intent = new Intent(baseActivity,
						DrinksDemandBuyActivity.class);
				intent.putExtra("buyorsale", "sale");//卖货跳转
				StartActivityUtil.startActivityForResult(baseActivity, intent, 1001);
			}
		});
		mPopupWindow = new PopupWindow(mPopMenuView,ViewUtil.getScreenWith(this), LayoutParams.MATCH_PARENT, false);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mPopupWindow.setFocusable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(false);
		mPopupWindow.showAsDropDown(position, ViewUtil.getScreenWith(this), 0);
		mPopupWindow.showAsDropDown(position, 0, 0);
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
		mPopupWindow.update();
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});
		return mPopupWindow;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, arg2);
		switch (requestCode) {
		case 1001:
			if (arg2 != null) {
				String result=arg2.getStringExtra("RESULT");
				if("OK".equals(result)){
					getNetData();
				}
			}
			break;
		case ConstantsUtil.REQUEST_CODE:
			getNetData();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Demand_list_view","");
		MobclickAgent.onEvent(baseActivity, "Demand_list_view", map); 
		Intent intent = new Intent(baseActivity, DrinksDemandBuyActivity.class);
		intent.putExtra("beanitem", DrinksDemandActivity.drinksDemandList.get((int)id).getId());
		if("1".equals(DrinksDemandActivity.drinksDemandList.get((int)id).getTradeMark())){
			intent.putExtra("buyorsale", "buy");//买货跳转
		}else{
			intent.putExtra("buyorsale", "sale");//卖货跳转
		}
		StartActivityUtil.startActivityForResult(baseActivity, intent, ConstantsUtil.REQUEST_CODE);
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
