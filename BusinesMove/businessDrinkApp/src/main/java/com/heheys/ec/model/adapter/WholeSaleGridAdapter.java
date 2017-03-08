package com.heheys.ec.model.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity.MyMessageHandler;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.TimeUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.WholeSaleListAdapter.AddCartMessageHandler;
import com.heheys.ec.model.adapter.WholeSaleListAdapter.AddCartRequestCallBack;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.ResultBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.WholeBaseBean.WholeListBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.AnimationCartUtil;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.SoftEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-14 上午11:39:39
 *  类说明
 */
public class WholeSaleGridAdapter extends BaseListAdapter<WholeListBean> {
	private String TAG = WholeSaleGridAdapter.class.getName();
	// 存储倒计时差
	private List<Long> countDownTimeList;
	private boolean isPlay;
	int progress;
	// ProgressBar mpb;
	String recommend;
	private Handler handler = new Handler();
	MyMessageHandler messageHandler ;
	private String bdviewtv;//右上角商品个数
	private int	buyNum;
	private AddCartMessageHandler addShoppingCartMessageHandler;
	private String clickTypeString;
	private int clickPosition;
	private int  currentdate;
	private boolean hasnowgoods;
	private AddShoppingCartBean addShoppingCartData;
	private ImageView ivShoppingCart;
	private GridView gridView;
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (!isPlay)
				return;
			handler.postDelayed(this, 1000);
			if (countDownTimeList != null && countDownTimeList.size() > 0) {
				int size = countDownTimeList.size();
				for (int i = 0; i < size; i++) {
					if (countDownTimeList.get(i) > 0) {
						countDownTimeList.set(i,
								countDownTimeList.get(i) - 1000);
					}
				}
			}
//			notifyDataSetChanged();
			int viewsize = dataList.size();
			for (int id = 0; id < viewsize; id++) {
				updateSingleRow(gridView, id);
			}
		}
	};
	private void updateSingleRow(GridView gridView, long id) {

		if (gridView != null) {
			int start = gridView.getFirstVisiblePosition();
			for (int i = start, j = gridView.getLastVisiblePosition(); i <= j; i++) {
				// if (id == ((Messages) listView.getItemAtPosition(i)).getId())
				// {
				View view = gridView.getChildAt(i - start);
				setDateNotify(i, view);
				// break;
			}
		}
	}

	// 局部刷新
	void setDateNotify(int i, View view) {
		// @SuppressWarnings("rawtypes")
		// SparseArray sparseArray = (SparseArray) view.getTag();
		setNewData(i, view);
	}

	void setNewData(int i, View convertView) {
		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(convertView, R.id.group_buy_grid_item_time_parent);
		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_day);
		TextView tv_time_day_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_list_item_day);
		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_hour);
		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_minute);
		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_second);
		
//		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(view,
//				R.id.tv_time_day);//
//		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(view,
//				R.id.tv_time_hour);//
//		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(view,
//				R.id.tv_time_minitue);//
//		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(view,
//				R.id.tv_time_second);//
//		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
//				.getItemView(view, R.id.group_buy_list_item_time_parent);
		if (i < dataList.size() - 1) {
			if (dataList.get(i).getType().equals("0")) {

				// 拼单
				if (countDownTimeList.get(i) == 0) {
					llTimeParentLayout.setVisibility(View.VISIBLE);
					tv_time_day.setText("00");
					tv_time_hour.setText("00");
					tv_time_minitue.setText("00");
					tv_time_second.setText("00");
				} else {
					llTimeParentLayout.setVisibility(View.VISIBLE);
					setCountDownTime(countDownTimeList.get(i), tv_time_day,
							tv_time_hour, tv_time_hour, tv_time_minitue,
							tv_time_second);
				}
				llTimeParentLayout.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public WholeSaleGridAdapter(boolean mIsGridViewidle,List<WholeListBean> data, Context context,String recommend,MyMessageHandler messageHandler,ImageView ivShoppingCart,GridView gridView) {
		super(data, context);
		initCountDownTime();
		this.recommend = recommend; 
		this.gridView = gridView;
		this.messageHandler = messageHandler;
		bdviewtv = GroupWholeSaleActivity.shoppingcare_num.getText().toString()
				.trim();
		this.ivShoppingCart = ivShoppingCart;
		if (!StringUtil.isEmpty(bdviewtv))
			buyNum = Integer.parseInt(bdviewtv);
		addShoppingCartMessageHandler = new AddCartMessageHandler(this);
//		loginObj = (ResultBean) SharedPreferencesUtil.getObject(context,"resultbean");
	}
	/*
	 * beanlist商品列表 当前添加的数据
	 * num商品列表当前点击产品数量
	 * */
	private void updateCacheProductsNotInEdit(WholeListBean beanlist, String num) {
		//提交订单的数据bean
		List<ShoppingCartSelectBean> cacheProduct = GroupWholeSaleActivity.selectProductNotInEdit;
		//存取临时的不在当前购物车的数据
		List<ShoppingCartSelectBean> cacheProductTemp  = new ArrayList<ShoppingCartSelectBean>();
		cacheProductTemp.clear();
		if (cacheProduct != null && cacheProduct.size() > 0) {
			for (ShoppingCartSelectBean product : cacheProduct) {
				if (beanlist.getId().equals(product.getAid())) {
					//当前添加的数据是购物车存在就添加 不存在就重新加入
					product.setNum(num);//提交订单bean
//					product.setCurrentprice(beanlist.getCprice());
					if(beanlist.getType().equals("1")){
						product.setCurrentprice(beanlist.getCprice());
					}else{
						product.setCurrentprice(beanlist.getDeprice());
					}
					hasnowgoods = true;
				}
			}
			if(!hasnowgoods){
				ShoppingCartSelectBean seletcbean = new ShoppingCartSelectBean();
				seletcbean.setAid(beanlist.getId());
				seletcbean.setNum(num);
				if(beanlist.getType().equals("1")){
					seletcbean.setCurrentprice(beanlist.getCprice());
				}else{
					seletcbean.setCurrentprice(beanlist.getDeprice());
				}
//				seletcbean.setCurrentprice(beanlist.getCprice());
				cacheProductTemp.add(seletcbean);
			}
		}else if(cacheProduct != null && cacheProduct.size() == 0){
			//购物车原本就是空
			ShoppingCartSelectBean seletcbean = new ShoppingCartSelectBean();
			seletcbean.setAid(beanlist.getId());
			seletcbean.setNum(num);
//			seletcbean.setCurrentprice(beanlist.getCprice());
			if(beanlist.getType().equals("1")){
				seletcbean.setCurrentprice(beanlist.getCprice());
			}else{
				seletcbean.setCurrentprice(beanlist.getDeprice());
			}
			cacheProductTemp.add(seletcbean);
		}
		if(cacheProductTemp.size()>0)
		cacheProduct.addAll(cacheProductTemp);
		GroupWholeSaleActivity.selectProductNotInEdit = cacheProduct;//重新赋值
		setShoppcartdata(cacheProduct,GroupWholeSaleActivity.shoppingCartData);
	}
	//设置购物车当前数据newnum数据
	void setShoppcartdata(List<ShoppingCartSelectBean> cacheProduct,NewShoppingCartInforBean shoppingCartData){
		if(shoppingCartData !=null && shoppingCartData.getResult() != null
				&&  shoppingCartData.getResult().getList() != null){
			List<NewProductInfo> listshop = shoppingCartData.getResult().getList();
			if(listshop.size()>0){
				for(NewProductInfo produce:listshop){//当前购物车
					for(ShoppingCartSelectBean select:cacheProduct){//提交生成订单数据bean
										if(produce.getAid().equals(select.getAid())){
											produce.setNum(select.getNum());
									}
					}
				 }
				}
		}
	}
	public static class AddCartMessageHandler extends
	WeakHandler<WholeSaleGridAdapter> {
		public AddCartMessageHandler(WholeSaleGridAdapter reference) {
			super(reference);
			
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

			case ConstantsUtil.HTTP_SUCCESS:
		if ("add".equals(getReference().clickTypeString)) {
			/**
			 * 增加操作==================================
			 */
//			/**
//			 * 更新缓存中商品数量
//			 */
			getReference().updateCacheProductsNotInEdit(getReference().dataList.get(getReference().clickPosition),getReference().dataList.get(getReference().clickPosition).getCurrentnum()+"");
			//加入购物车成功动画
			getReference().SetAnimation();
			getReference().updateDataMessage();
			getReference().notifyDataSetChanged();
		}
		break;
	case ConstantsUtil.HTTP_FAILE:
		/**
		 * 处理失败的数据
		 */
		if("add".equals(getReference().clickTypeString))
			getReference().currentdate = getReference().dataList.get(getReference().clickPosition).getCurrentnum()-1;
		else
			getReference().currentdate = getReference().dataList.get(getReference().clickPosition).getCurrentnum()+1;
		String messageString = (String) msg.obj;
		if (messageString != null) {
			ToastUtil.showToast(getReference().mcontext, messageString);
			/**
			 * 处理失败数据
			 */
//			getReference().cancommit = false;
		} else {
			ToastUtil.showToast(getReference().mcontext, "加入购物车失败");
		}
		break;
			}
		}
		}
	
	public void getTotalPrice() {
		totalPrice = 0;
		totalNum = 0;
		List<ShoppingCartSelectBean> listcommit = GroupWholeSaleActivity.selectProductNotInEdit;
		if( listcommit !=null ){
			for(ShoppingCartSelectBean info :listcommit){
						//拼单取cprice
						totalPrice = (float) (totalPrice+ Double.parseDouble(info.getCurrentprice()) *Integer.parseInt(info.getNum()));
						totalNum = totalNum + Integer.parseInt(info.getNum());
					}
				}
	}
	//更新下面的商品个数和总价格
		private void updateDataMessage() {
				getTotalPrice();
				Message messageAdd = Message.obtain();
				messageAdd.what = 1000;
				Bundle dataBundleAdd = new Bundle();
				long[] num = new long[1];
				num[0] = totalNum;
				dataBundleAdd.putLongArray("totalNum", num);
				dataBundleAdd.putFloat("totalPrice", totalPrice);
				messageAdd.setData(dataBundleAdd);
				if(messageHandler != null)
					messageHandler.sendMessage(messageAdd);

		}
	private void SetAnimation() {
		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		iv_add.getLocationInWindow(start_location);// 这是获取购买文本框的在屏幕的X、Y坐标（这也是动画开始的坐标）
		ImageView buyImg = new ImageView(mcontext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				60, 60);
		buyImg.setLayoutParams(lp);
		MyApplication.imageLoader.displayImage(dataList.get(clickPosition).getPic(), buyImg,
				MyApplication.options);
		AnimationCartUtil.setAnim(mcontext, ivShoppingCart, buyImg, start_location);
		}
	@Override
	public void setNewData(List<WholeListBean> newData) {
		super.setNewData(newData);
		initCountDownTime();
	}


	/**
	 * 
	 * Describe:启动倒计时
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */
	public void startCountDownTime() {
		if (isPlay) {
			return;
		}
		isPlay = true;
		runnable.run();
	}

	/**
	 * 
	 * Describe:停止倒计时
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */

	public void stopCountDownTime() {
		isPlay = false;
	}

	/**
	 * 
	 * Describe:初始化倒计时
	 * 
	 * Date:2015-10-10
	 * 
	 * Author:liuzhouliang
	 */
	public void initCountDownTime() {
		/**
		 * 处理倒计时
		 */
		if (dataList != null) {
			int size = dataList.size();
			if (size > 0) {
				if (countDownTimeList != null) {
					countDownTimeList.clear();
				}
				countDownTimeList = new ArrayList<Long>();
				for (int i = 0; i < size; i++) {
					String startTimeString = dataList.get(i).getEndtime();
					long currentTime = 0;;
					long appointTime = 0;
					if(!StringUtil.isEmpty(startTimeString)){
						appointTime = TimeUtil.changeDateToTime(startTimeString);
						currentTime = System.currentTimeMillis();
					}
					long gapTime = appointTime - currentTime;
					if (gapTime > 0) {
						countDownTimeList.add(i, gapTime);
					} else {
						countDownTimeList.add(i, 0L);
					}
				}
			}
		}
//		LogUtil.d(TAG, countDownTimeList.toString());

	}
	private void updateProgress(final ProgressBar pb, final int maxProgress) {
		// mpb = pb;
		// int progress;
		final Timer timer;
		TimerTask timerTask;
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				progress = progress + 2;
				if (progress < maxProgress || progress == maxProgress) {
					pb.setProgress(progress);
				} else {
					timer.cancel();
				}

			}
		};
		timer.schedule(timerTask, 0, 10);
	}
	/**
	 * 
	 * Describe:设置倒计时时间
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */
	private void setCountDownTime(long millisUntilFinished, TextView tvDay,
			TextView tv_time_day_name, TextView tvHour, TextView tvMinute,
			TextView tvSeconds) {
		long days, hours, minutes, seconds;
		if (millisUntilFinished < 0) {
			return;
		}
		if (millisUntilFinished <= 1000 * 60 * 60 * 24) {
//			tv_time_day_name.setVisibility(View.GONE);
//			tvDay.setVisibility(View.GONE);
			// 获取小时值
			days = millisUntilFinished / (60 * 60 * 1000 *24);
			// 获取小时值
			hours = millisUntilFinished / (60 * 60 * 1000);
			// 获取分值
			minutes = (millisUntilFinished - hours * (60 * 60 * 1000))
					/ (60 * 1000);
			// 获取秒值
			seconds = (millisUntilFinished - hours * (60 * 60 * 1000) - minutes
					* (60 * 1000)) / 1000;
			if(days<10){
				tvDay.setText("0" + days);
			}else
				tvDay.setText(days+"");
			if (hours < 10) {
				tvHour.setText("0" + hours);
			} else
				tvHour.setText(hours + "");
			if (minutes < 10) {
				tvMinute.setText("0" + minutes);
			} else
				tvMinute.setText(minutes + "");
			if (seconds < 10) {
				tvSeconds.setText("0" + seconds);
			} else
				tvSeconds.setText(seconds + "");
		} else {
			// 获取天数
			days = millisUntilFinished / (60 * 60 * 1000 * 24);
			// 获取小时值
			hours = (millisUntilFinished - days * (60 * 60 * 1000 * 24))
					/ (60 * 60 * 1000);
			// 获取分值
			minutes = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)) - hours
					* (60 * 60 * 1000))
					/ (60 * 1000);
			// 获取秒值
			seconds = ((millisUntilFinished - days * (60 * 60 * 1000 * 24)
					- hours * (60 * 60 * 1000) - minutes * (60 * 1000))) / 1000;
			if (days < 10) {
				tvDay.setText("0" + days);
			} else
				tvDay.setText(days + "");
			if (hours < 10) {
				tvHour.setText("0" + hours);
			} else
				tvHour.setText(hours + "");
			if (minutes < 10) {
				tvMinute.setText("0" + minutes);
			} else
				tvMinute.setText(minutes + "");
			if (seconds < 10) {
				tvSeconds.setText("0" + seconds);
			} else
				tvSeconds.setText(seconds + "");
		}

	}
	
	/**
	 * 
	 * 添加到购物车
	 * */
	private void AddToShoppcart(final int position,String typeshopping,boolean isPopu) {
			clickTypeString = typeshopping;
			clickPosition = position;
			/**
			 * 内存中当前数量为空
			 */
			if("add".equals(clickTypeString)){
				//第一次加直接加到最小起批量 
				if(dataList.get(clickPosition).getCurrentnum()==0 || dataList.get(clickPosition).getCurrentnum()<Integer.parseInt(dataList.get(clickPosition).getMinnum())){
					if( dataList.get(clickPosition).getCurrentnum()<Integer.parseInt(dataList.get(clickPosition).getMinnum()))
					    currentdate = Integer.parseInt((StringUtil.isEmpty(dataList.get(clickPosition).getMinnum())?"1":dataList.get(clickPosition).getMinnum()));
					if(!isPopu && Integer.parseInt((StringUtil.isEmpty(dataList.get(position).getHasrecom())?"0":dataList.get(position).getHasrecom())) == 1){
		 				Message messageAdd = Message.obtain();
		 				messageAdd.what = 1001;
		 				Bundle dataBundleAdd = new Bundle();
		 				dataBundleAdd.putString("aid", dataList.get(position).getId());
		 				dataBundleAdd.putString("url", dataList.get(position).getPic());
		 				dataBundleAdd.putInt("position", position);
		 				messageAdd.setData(dataBundleAdd);
		 				if (messageHandler != null)
		 					messageHandler.sendMessage(messageAdd);
						return;
		 				}
				}else{
						currentdate = dataList.get(clickPosition).getCurrentnum()+1;
				}
				dataList.get(clickPosition).setCurrentnum(currentdate);
			}
			addShoppingCart(dataList.get(clickPosition).getId(), currentdate+ "");//id是当前的商品活动ID

	}
	// 加入到购物车
		private void addShoppingCart(String aid, String num) {
			ApiHttpCilent.getInstance(mcontext).addShoppingCart(mcontext,
					aid, num, 0, new AddCartRequestCallBack());
		}
		private void Dismess() {
			((Activity) mcontext).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
						ApiHttpCilent.loading.dismiss();
				}
			});
		}
		public class AddCartRequestCallBack extends
		BaseJsonHttpResponseHandler<AddShoppingCartBean> {

	

	@Override
	public void onFailure(int arg0, Header[] arg1, Throwable arg2,
			String arg3, AddShoppingCartBean arg4) {
		Dismess();
		Message message = Message.obtain();
		message.what = ConstantsUtil.HTTP_FAILE;// 错误
		addShoppingCartMessageHandler.sendMessage(message);
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2,
			AddShoppingCartBean arg3) {
		Dismess();
	}

	@Override
	protected AddShoppingCartBean parseResponse(String response,
			boolean arg1) throws Throwable {
		Dismess();
		Gson gson = new Gson();
		addShoppingCartData = gson.fromJson(response,
				AddShoppingCartBean.class);
		Message message = Message.obtain();
		if ("1".equals(addShoppingCartData.getStatus())) {// 正常
			message.what = ConstantsUtil.HTTP_SUCCESS;
		} else {
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			message.obj = addShoppingCartData.getError().getInfo();
		}
		addShoppingCartMessageHandler.sendMessage(message);
		return addShoppingCartData;
	}

}
		private ImageView iv_add;
		private SoftEditText buy_edittext;
		private View v;
		private float totalPrice;
		private int totalNum;
//		private ResultBean loginObj;
		//设置当前空间的位置
		void setAmationImage(ImageView iv_add,View v){
			 this.iv_add = iv_add;
			 this.v = v;
		}
	@SuppressLint("NewApi") @Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = baseInflater.inflate(R.layout.whole_grid_item,
					parent, false);
		}
		WholeListBean bean = dataList.get(position);
		LinearLayout group_buy_grid_item_top = (LinearLayout) ViewHolderUtil
				.getItemView(convertView, R.id.group_buy_grid_item_top);
		LinearLayout llTimeParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(convertView, R.id.group_buy_grid_item_time_parent);
		TextView tv_time_day = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_day);
		TextView tv_time_day_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_list_item_day);
		TextView tv_time_hour = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_hour);
		TextView tv_time_minitue = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_minute);
		TextView tv_time_second = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_time_second);
		TextView tv_wine_name = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_wine_name);//名称
		ImageView iv_wineurl = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_wineurl);
		ImageView iv_pin = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_pin);
		ImageView iv_activity = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_activity_icon);
		ImageView iv_jinxuan = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.iv_jinxuan);
		TextView tv_knum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_knum);
		TextView tv_nokcum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_nokcum);//最小起批量
		/**
		 * 批发产品视图
		 * 
		 * */
		/**********************************************************************/
		LinearLayout linear_pf = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_pf);//批发父视图
		TextView tv_salenum = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_salenum);//销量
		TextView tv_unit_next_one_price = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_unit_next_one_price);//批发一瓶多少钱
		TextView tv_unit_next = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.tv_unit_next);//批发一瓶多少钱单位
		TextView tv_price = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price);//多少钱一箱
		TextView tv_unit = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_unit);//多少钱一箱单位
		/**********************************************************************/
		
		/**
		 * 拼单产品视图
		 * 
		 * */
		/**********************************************************************/
		LinearLayout linear_pd = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_pd);//拼单父视图
		TextView tv_dq = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_dq);//当前价
		TextView tv_price_pd = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_price_pd);//当前价数值
		TextView tv_unit_pd = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_unit_pd);//当前价数值单位
		LinearLayout linear_hot = (LinearLayout) ViewHolderUtil.getItemView(
				convertView, R.id.linear_hot);
		TextView tv_hot = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_hot);//拼单热度
		TextView tv_hot_total = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.tv_hot_total);//拼单热度数据
		ProgressBar pb = (ProgressBar) ViewHolderUtil.getItemView(convertView,
				R.id.group_buy_grid_item_pb);//拼单进度条
		TextView tv_sold = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.group_buy_grid_item_totalnow);
		TextView totalNum = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.group_buy_grid_item_totalnum);//拼单进度条数值
		final ImageView iv_add = (ImageView) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_num_add);// 增加商品
		/**********************************************************************/
		RelativeLayout linear_pb = (RelativeLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_pb);
		tv_wine_name.setText(bean.getName());
		tv_price.setText(bean.getCurrency()==null?"¥":bean.getCurrency()+bean.getCprice());
		tv_unit.setText("/"+bean.getUnit());
		String totalPrice  = bean.getCprice();
		String boxsize  = bean.getBoxsize();
		
		
		iv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddToShoppcart(position,"add",false);//新增
				setAmationImage(iv_add,v);
			}
		});
		
	
		currentdate = dataList.get(position).getCurrentnum();
		if(dataList.get(position).getNum()<=0){
			tv_knum.setVisibility(View.VISIBLE);
			tv_knum.setText("库存不足");
			iv_add.setEnabled(false);
			iv_add.setBackground(mcontext.getResources().getDrawable(R.drawable.grid_no));
			iv_add.setVisibility(View.VISIBLE);
		}else{
			tv_knum.setVisibility(View.INVISIBLE);
			if(currentdate > 0){
			tv_knum.setVisibility(View.GONE);
			iv_add.setEnabled(true);
			iv_add.setBackground(mcontext.getResources().getDrawable(R.drawable.grid_yes));
			if( Integer.parseInt(dataList.get(position).getMinnum()) < dataList.get(position).getNum()){
			if(dataList.get(position).getCurrentnum() < Integer.parseInt(dataList.get(position).getMinnum())){
				//购买量小于最小起批量
				tv_nokcum.setText("最小起批量:"+dataList.get(position).getMinnum());
				tv_nokcum.setVisibility(View.VISIBLE);
			}else if(dataList.get(position).getCurrentnum() > dataList.get(position).getNum()){
				tv_nokcum.setText("当前库存"+ dataList.get(position).getNum());
				tv_nokcum.setVisibility(View.VISIBLE);
				}else{
					tv_nokcum.setVisibility(View.INVISIBLE);
				}
			}else if(dataList.get(position).getNum() < Integer.parseInt(dataList.get(position).getMinnum())){
				tv_nokcum.setText("当前库存:"+bean.getNum()+"\n起批量:"+bean.getMinnum());
				tv_nokcum.setVisibility(View.VISIBLE);
				iv_add.setEnabled(false);
				iv_add.setBackgroundResource(R.drawable.grid_no);
			 }
			}else{
				//当前未购买
				if(dataList.get(position).getNum() < Integer.parseInt(dataList.get(position).getMinnum())){
					tv_nokcum.setVisibility(View.INVISIBLE);
					iv_add.setEnabled(false);
					iv_add.setBackgroundResource(R.drawable.grid_no);
					tv_knum.setText("库存不足");
					tv_knum.setVisibility(View.VISIBLE);
				}else{
					tv_nokcum.setVisibility(View.INVISIBLE);
					iv_add.setEnabled(true);
					iv_add.setBackgroundResource(R.drawable.grid_yes);
				}
			}
		}
		if(dataList.get(position).getType().equals("0")){
			//拼单
			linear_pf.setVisibility(View.GONE);
			linear_pd.setVisibility(View.VISIBLE);
			if (countDownTimeList.get(position) == 0) {
				llTimeParentLayout.setVisibility(View.VISIBLE);
				tv_time_day.setText("00");
				tv_time_hour.setText("00");
				tv_time_minitue.setText("00");
				tv_time_second.setText("00");
			} else {
				llTimeParentLayout.setVisibility(View.VISIBLE);
				setCountDownTime(countDownTimeList.get(position), tv_time_day,
						tv_time_day_name, tv_time_hour, tv_time_minitue,
						tv_time_second);
			}
			llTimeParentLayout.setVisibility(View.VISIBLE);
			linear_pb.setVisibility(View.VISIBLE);
			tv_dq.setVisibility(View.VISIBLE);
//			linear_gg.setVisibility(View.GONE);
//			tv_price_pd.setText(bean.getCprice() == null ? "¥" : bean
//					.getCprice());
			ViewUtil.setActivityPrice(tv_price_pd, bean.getCprice() == null ? "0" : bean
					.getCprice());
			tv_unit_pd.setText("/"+bean.getUnit());
			linear_hot.setVisibility(View.VISIBLE);
			tv_salenum.setVisibility(View.GONE);
			tv_hot.setText("拼单热度:"+dataList.get(position).getFrozennum()+bean.getUnit());
			tv_hot_total.setText("/"+dataList.get(position).getKnum()+bean.getUnit());
			totalNum.setText(dataList.get(position).getKnum());
			if(!StringUtil.isEmpty(dataList.get(position).getSoldnum()) && !StringUtil.isEmpty(dataList.get(position).getKnum())){
			float proportion = (Float.parseFloat( dataList.get(position).getFrozennum()))/ (float) (Integer.parseInt(dataList.get(position).getKnum()));
			int progress = (int) (proportion * 100);
			Resources res = mcontext.getResources();
			pb.setProgressDrawable(res
					.getDrawable(R.drawable.progressbar_color_gray));
			pb.setProgress(progress);
			}
			iv_pin.setVisibility(View.VISIBLE);
//			tv_price_bottom.setVisibility(View.GONE);
		}else if(bean.getType().equals("1")){
			//批发
			linear_pf.setVisibility(View.VISIBLE);
			linear_pd.setVisibility(View.GONE);
			iv_pin.setVisibility(View.INVISIBLE);
			tv_salenum.setVisibility(View.VISIBLE);
//			linear_gg.setVisibility(View.GONE);
			llTimeParentLayout.setVisibility(View.GONE);
			linear_pb.setVisibility(View.GONE);
			linear_hot.setVisibility(View.GONE);
			tv_dq.setVisibility(View.GONE);
			if(!StringUtil.isEmpty(dataList.get(position).getRecommend()) && "1".equals(dataList.get(position).getRecommend())){
				  if(!StringUtil.isEmpty(recommend))
					iv_jinxuan.setVisibility(View.VISIBLE);
			}else{
				    iv_jinxuan.setVisibility(View.GONE);
			}
		}
		
			if("箱".equals(dataList.get(position).getUnit()) && dataList.get(position).getType().equals("1")){
//				ViewUtil.setActivityPrice(tv_unit_next_one_price, DoPrice(Float.parseFloat(totalPrice)/Float.parseFloat(boxsize))+"");
					String currency =  bean.getCurrency();
					if(currency==null)
						currency = "¥";
					tv_unit_next_one_price.setText(currency+dataList.get(position).getUnivalent());
					tv_unit_next.setText("/"+dataList.get(position).getPerunit());
			}else{
				tv_unit_next_one_price.setVisibility(View.GONE);
				tv_unit_next.setVisibility(View.GONE);
			}
		if("2".equals(dataList.get(position).getStatus())){
			/*
			 * 已抢光
			 * */
			iv_activity.setVisibility(View.VISIBLE);
			iv_activity.setImageResource(R.drawable.yiqiangguang);
		}else if("4".equals(dataList.get(position).getStatus())){
			/*
			 * 已成单
			 * */
			iv_activity.setVisibility(View.VISIBLE);
			iv_activity.setImageResource(R.drawable.yichengdan);
		}else{
			iv_activity.setVisibility(View.INVISIBLE);
		}
		// 已经售卖数量
		tv_salenum.setText("销量:"+dataList.get(position).getSalesvol()+bean.getUnit());
		MyApplication.imageLoader.displayImage(bean.getPic(), iv_wineurl,
				MyApplication.options);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						dataList.get(position).getId());
				intent.putExtra(ConstantsUtil.PRODUCT_TYPE_KEY,dataList.get(position).getType());
				StartActivityUtil.startActivity((Activity) mcontext, intent);
			}
		});
		return convertView;
	}
	//处理成2位小数点
		private float DoPrice(float ft){
			 int   scale  =   2;//设置位数    
			 int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.    
			 BigDecimal   bd  =   new  BigDecimal((double)ft);    
			 bd   =  bd.setScale(scale,roundingMode);    
			 ft   =  bd.floatValue(); 
			 return ft;
		}
}

