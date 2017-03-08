package com.heheys.ec.controller.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.heheys.ec.controller.activity.EditAddressActivity;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.AddressAdapter;
import com.heheys.ec.model.adapter.AddressAdapter.BackDateCallBack;
import com.heheys.ec.model.dataBean.AddressListBean;
import com.heheys.ec.model.dataBean.BaseAddressBean;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.CommonDialog;
import com.heheys.ec.view.CommonDialog.OnDialogListener;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 作者 E-mail:wk
 * @version 创建时间：2015-9-23 下午3:41:25 类说明
 * @param地址列表
 */
public class AddressFragment extends BaseFragment implements OnItemClickListener{

	private View view;
	private  Activity activity;
	private ListView lv_add;
	private boolean isEdit = false;//是否是编辑状态
	private boolean isClick = true;//是否点击编辑按钮
	private AddressAdapter adapter;
	private Button add_address;
	private Button bt_delete;
	private ArrayList<AddressListBean> data;
	private MyHandler handler = new MyHandler(this);
	private LinearLayout linear_delete;
	private CheckBox iv_check_all;
	private StringBuffer sb  = new StringBuffer();
	private List<String> deleteList = new ArrayList<String>();//存储所有待删除地址ID集合
	private boolean isCenter;
	private ObjectAnimator animatorButton;

	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return true;
	}

	public AddressFragment(){
	}
	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = getActivity();
		view = inflater.inflate(R.layout.address_magener, container, true);
		initView();
		initDate();
		return view;
	}
	   /**
     * 删除item，并播放动画
     * @param rowView 播放动画的view
     * @param positon 要删除的item位置
     */ 
    protected void removeListItem(View rowView, final int positon) { 
         
        final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.item_anim);
        animation.setAnimationListener(new AnimationListener() { 
            public void onAnimationStart(Animation animation) {} 
 
            public void onAnimationRepeat(Animation animation) {} 
 
            public void onAnimationEnd(Animation animation) { 
            	if(data.size()>0){
                data.remove(positon); 
                adapter.notifyDataSetChanged(); 
                animation.cancel(); 
            	}
            } 
        }); 
        rowView.startAnimation(animation); 
} 
    private void Dimess() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}
	class MyCallBack extends BaseJsonHttpResponseHandler<BaseAddressBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseAddressBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseAddressBean arg3) {
			Dimess();
		}

		@Override
		protected BaseAddressBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			BaseAddressBean bean = gson.fromJson(response, BaseAddressBean.class);
			Message message = Message.obtain();
//			{"result":{"list":[{"uid":38,"id":2,"address":"12","county":12,"name":"12","province":1,"areacode":"","mobile":"12345678911","city":12}]},"error":{},"status":1}
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult().getList();
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = bean.getError().getInfo();
			}
			handler.sendMessage(message);

			return bean;
		}
	}
	class MyAddressCallBack extends BaseJsonHttpResponseHandler<BaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, BaseBean arg4) {
			Dimess();
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				BaseBean arg3) {
			Dimess();
		}
		
		@Override
		protected BaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			BaseBean bean = gson.fromJson(response, BaseBean.class);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS_LOGIN;
			} else {
				message.what = ConstantsUtil.HTTP_FAILE;// 错误
				message.obj = bean.getError().getInfo();
			}
			handler.sendMessage(message);
			
			return bean;
		}
	}

	public class MyHandler extends WeakHandler<AddressFragment> {

		public MyHandler(AddressFragment reference) {
			super(reference);
		}

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				data =  (ArrayList<AddressListBean>) msg.obj;
				if(data==null || data.size()==0){
					tvRight.setVisibility(View.INVISIBLE);
					showNoAddressView();
					tvAddAddress.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							startActivityForResult(new Intent(activity, EditAddressActivity.class), ConstantsUtil.REQUEST_CODE_TWO);
						}
					});
					linear_delete.setVisibility(View.GONE);
					add_address.setVisibility(View.GONE);
				}else{
					tvRight.setVisibility(View.VISIBLE);
					isClick = true;
					isEdit = false;
					adapter.setIsedit(isEdit);
					tvRight.setText("编辑");
					linear_delete.setVisibility(View.GONE);
					add_address.setVisibility(View.VISIBLE);
				//初始化全部不选中状态
				for(AddressListBean bean:data){
					bean.setCheck(false);
				 }
				}
				adapter.setNewData(data);
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(activity, back);
				break;
			case ConstantsUtil.HTTP_SUCCESS_LOGIN:
				ToastUtil.showToast(activity, "删除成功");
				//重新请求数据集合
				ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetAddressInfo(activity, new MyCallBack());
				break;
			default:
				break;
			}
		}
	}

	//设置删除按钮是否可以点击
	private void SetEable(){
		if(deleteList.size()>0){
			bt_delete.setBackgroundResource(R.drawable.bt_bg_yellow);
			bt_delete.setEnabled(true);
		}else{
			bt_delete.setBackgroundResource(R.drawable.shape_button_gray);
			bt_delete.setEnabled(false);
		}
	}
	private void initDate() {
		Bundle bundle = getArguments();//从activity传过来的Bundle  
        if(bundle!=null){  
        	isCenter = bundle.getBoolean("isCenter");
        	if(isCenter)
            ResetTitle("地址管理");
        }  
		data = new ArrayList<AddressListBean>(); 
		ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetAddressInfo(activity, new MyCallBack());
		adapter = new AddressAdapter(data,activity,new BackDateCallBack() {
			@Override
			
			public void setAddId(int id) {
				// TODO Auto-generated method stub
				if(!deleteList.contains(id+"")){
					deleteList.add(id+"");
				}
				if(deleteList.size()==data.size()){
					iv_check_all.setChecked(true);
				}
				SetEable();
				bt_delete.setVisibility(View.VISIBLE);
			}

			@Override
			public void setRemoveId(int id) {
				// TODO Auto-generated method stub
				if(deleteList.contains(id+"")){
					deleteList.remove(id+"");
				}
				if(deleteList.size()<data.size()){
					iv_check_all.setChecked(false);
				}
				if(deleteList.size()==0){
					bt_delete.setVisibility(View.INVISIBLE);
				}else{
					bt_delete.setVisibility(View.VISIBLE);
				}
				SetEable();
			}
		});
		lv_add.setAdapter(adapter);
			 /**
		     * 添加listview滑动接听
		     */ 
//		    lv_add.setOnTouchListener(new OnTouchListener() { 
//		        float x, y, upx, upy;
//		        public boolean onTouch(View view, MotionEvent event) { 
//		            if (event.getAction() == MotionEvent.ACTION_DOWN) { 
//		               x = event.getX(); 
//		               y = event.getY(); 
//		            } 
//		            if (event.getAction() == MotionEvent.ACTION_UP) { 
//		            	float upx = event.getX(); 
//		            	float upy = event.getY(); 
//		                int position1 = ((ListView) view).pointToPosition((int) x, (int) y); 
//		                int position2 = ((ListView) view).pointToPosition((int) upx,(int) upy); 
//		                 
//		                if (position1 == position2 && Math.abs(x - upx) > 60) { 
//		                    View v = ((ListView) view).getChildAt(position1); 
//		                    removeListItem(v,position1); 
//		                } 
//		            } 
//		            return false; 
//		        } 
//
//		    }); 
	}

	
	private void initView() {
		tvRight.setVisibility(View.INVISIBLE);
		lv_add = (ListView) view.findViewById(R.id.lv_add);
		add_address = (Button) view.findViewById(R.id.add_address);
		bt_delete = (Button) view.findViewById(R.id.bt_delete);
		linear_delete = (LinearLayout) view.findViewById(R.id.linaer_delete);
		iv_check_all = (CheckBox) view.findViewById(R.id.iv_check_all);
		add_address.setOnClickListener(this);
		iv_check_all.setOnClickListener(this);
		bt_delete.setOnClickListener(this);
		lv_add.setOnItemClickListener(this);
		
	}
	

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewListener() {
		// TODO Auto-generated method stub
		tvRight.setOnClickListener(this);
	}

	//是否显示编辑按钮
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		super.onClick(v);
		HashMap<String,String> map = new HashMap<String,String>();
		Intent intent ;
		switch (v.getId()) {
		//右上角按钮事件
		case R.id.base_activity_title_right_righttv:
			deleteList.clear();
			iv_check_all.setChecked(false);
			if(isClick){
				MobclickAgent.onEvent(baseActivity, "C_ADR_MGR_2");
//				map.put("editadd","");
//				MobclickAgent.onEvent(baseActivity, "0060", map);
				isEdit = true;
				isClick = false;
				adapter.setIsedit(isEdit);
				tvRight.setText("完成");
				linear_delete.setVisibility(View.VISIBLE);
				add_address.setVisibility(View.GONE);
				tvTitleName.setText(getResources().getString(R.string.setting_guanli));
				SetEable();
				if(animatorButton != null)
				animatorButton.cancel();
			}else{
				map.put("okaddress","");
				MobclickAgent.onEvent(baseActivity, "0064", map);
				tvTitleName.setText(getResources().getString(R.string.setting_default));
				isClick = true;
				isEdit = false;
				adapter.setIsedit(isEdit);
				tvRight.setText("编辑");

				linear_delete.setVisibility(View.GONE);
				add_address.setVisibility(View.VISIBLE);

				DisplayMetrics dis = new DisplayMetrics();
				 getActivity().getWindowManager().getDefaultDisplay().getMetrics(dis);
				add_address.getLayoutParams().width = 0;
				ViewContent viewContent = new ViewContent(add_address);
				animatorButton = ObjectAnimator.ofInt(viewContent, "width",dis.widthPixels- ViewUtil.px2dip(getActivity(),60)*2);
				animatorButton.setDuration(500);
				animatorButton.start();

//				ViewContent viewContentDelete = new ViewContent(linear_delete);
//				ObjectAnimator animatorS = ObjectAnimator.ofInt(viewContentDelete, "width", 0);
//				animatorS.setDuration(500);
//				animatorS.start();
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.add_address:
//			map.put("addaddress","");
			MobclickAgent.onEvent(baseActivity, "C_ADR_MGR_1");
			startActivityForResult(new Intent(activity, EditAddressActivity.class), ConstantsUtil.REQUEST_CODE_TWO);
			break;
		case R.id.iv_check_all:
			if(iv_check_all.isChecked()){
				for(AddressListBean bean:data){
					bean.setCheck(true);
				}
			}else{
				for(AddressListBean bean:data){
					bean.setCheck(false);
				}
			}
			adapter.setIsedit(true);
			adapter.setNewData(data);
			adapter.notifyDataSetChanged();
			if(iv_check_all.isChecked()){
				deleteList.clear();
				for(AddressListBean bean:data){
						deleteList.add(bean.getId()+"");
				}
			}else{
				deleteList.clear();
			}
			SetEable();
			break;
		case R.id.bt_delete:
			MobclickAgent.onEvent(baseActivity, "C_ADR_MGR_3");
			if(deleteList.size()==0){
				ToastUtil.showToast(activity, "请先选择地址在删除");
				return;
			}
			/**
			 * 是否删除
			 */
			ShowDialog("是否删除收货地址?","温馨提示");
			
			break;
		case R.id.base_activity_title_backicon:
			map.put("addressback","");
			MobclickAgent.onEvent(baseActivity, "0065", map);
			if(data==null || data.size()==0){
				intent = new Intent();
				intent.putExtra("data", 0);
				intent.putExtra("nodata", true);
				activity.setResult(ConstantsUtil.REQUEST_CODE, intent);
				activity.finish();
			}else if(data.size()!=0){
				Intent intents = new Intent();
				intents.putExtra("bean", data.get(0));
				intents.putExtra("data", data);
				activity.setResult(ConstantsUtil.REQUEST_CODE, intents);
				activity.finish();
			}else{
				onBackPressed();
			}
		break;
		case R.id.linear_back:
			map.put("addressback","");
			MobclickAgent.onEvent(baseActivity, "0065", map);
			if(data==null || data.size()==0){
				intent = new Intent();
				intent.putExtra("data", 0);
				intent.putExtra("nodata", true);
				activity.setResult(ConstantsUtil.REQUEST_CODE, intent);
				activity.finish();
				
			}else if(data.size()!=0){
				Intent intents = new Intent();
				intents.putExtra("bean", data.get(0));
				intents.putExtra("data", data);
				activity.setResult(ConstantsUtil.REQUEST_CODE, intents);
				activity.finish();
			}else{
				onBackPressed();
			}
			break;
		default:
			break;
		}
	}

	private  static class ViewContent{
		View mButton;
		ViewContent(View mButton){
			this.mButton = mButton;
		}

		public int getWidth() {
			return mButton.getLayoutParams().width;
		}

		public void setWidth(int mWidth) {
			mButton.getLayoutParams().width = mWidth;
			mButton.requestLayout();
		}
	}
	private void ShowDialog(String notice,String title) {
		CommonDialog.makeText(activity,
				title,notice,
				new OnDialogListener() {
					@Override
					public void onResult(int result,
							CommonDialog commonDialog,String tel) {
						// TODO Auto-generated method stub
						 if(OnDialogListener.LEFT == result){
							 sb.delete(0, sb.length());
								int size = deleteList.size();
								for(int i=0;i<size;i++){
									if(i==size-1){
										sb.append(deleteList.get(i));
										}else{
										sb.append(deleteList.get(i)+",");
									}
								}
								//删除地址
								ApiHttpCilent.getInstance(getContext().getApplicationContext()).DeleteAddress(activity, sb, new MyAddressCallBack());
								CommonDialog.Dissmess();
						} else {
							   CommonDialog.Dissmess();
						}
					}
				}).showDialog();
	}
	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
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
		return getResources().getString(R.string.setting_default);
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return  "编辑" ;
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long position) {
		// TODO Auto-generated method stub
		AddressListBean bean = data.get((int) position);
		Intent intent = new Intent(activity,EditAddressActivity.class);
		intent.putExtra("bean", bean);
		
		if(isEdit){
			//编辑状态下跳到详情
			startActivityForResult(intent, ConstantsUtil.REQUEST_CODE_TWO);
		}else{
			if(!isCenter){
			//非编辑状态下返回选择地址
			activity.setResult(ConstantsUtil.REQUEST_CODE, intent);
			activity.finish();
			}
		}
	}

	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(ConstantsUtil.REQUEST_CODE_TWO==requestCode){
			viewEmptyAddress.setVisibility(View.GONE);
			ApiHttpCilent.getInstance(getContext().getApplicationContext()).GetAddressInfo(activity, new MyCallBack());
		}
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("PG_ADDR_LST"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("PG_ADDR_LST"); 
	}
	
	public interface NoDateCallBack{
		void setDate(List<AddressListBean> data);
	}
}
