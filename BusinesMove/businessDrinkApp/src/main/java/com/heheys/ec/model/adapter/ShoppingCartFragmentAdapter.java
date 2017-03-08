package com.heheys.ec.model.adapter;/*package com.heheys.ec.model.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.controller.activity.ProductDetail;
import com.heheys.ec.controller.fragment.ShoppingCartAnimationManager;
import com.heheys.ec.controller.fragment.ShoppingCartFragment;
import com.heheys.ec.controller.fragment.ShoppingCartFragment.ShoppingCartMessageHandler;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.ToastUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.ShoppingCartInforBean.ShoppingCartInfor.ProductInfor;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.ShoppingCartProductDeleteBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.view.SoftEditText;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

*//**
 * Describe:购物车数据适配器
 * 
 * Date:2015-9-25
 * 
 * Author:liuzhouliang
 *//*
public class ShoppingCartFragmentAdapter extends BaseListAdapter<ProductInfor> {
	private static final String TAG = ShoppingCartFragmentAdapter.class
			.getName();
	private Activity mActivity;
	private ShoppingCartFragment mFragment;
	private ShoppingCartMessageHandler messageHandler;
	public long totalNum;
	public float totalPrice;
	private AddShoppingCartBean addShoppingCartData;
	private AddShoppingCartMessageHandler addShoppingCartMessageHandler;
	private int clickPosition;
	// Bitmap bitmap;
	private String clickTypeString;
	List<ProductInfor> filterList;

	public ShoppingCartFragmentAdapter(List<ProductInfor> data,
			Context context, ShoppingCartFragment fragment,
			ShoppingCartMessageHandler messageHandler, ListView lv) {
		super(data, context);
		// TODO Auto-generated constructor stub
		LogUtil.d(TAG, "ShoppingCartFragmentAdapter");
		addShoppingCartMessageHandler = new AddShoppingCartMessageHandler(this);
		mActivity = (Activity) context;
		mFragment = fragment;
		this.messageHandler = messageHandler;
		*//**
		 * 初始化选择按钮选择状态
		 *//*
		if (dataList != null) {
			int length = dataList.size();
			for (int i = 0; i < length; i++) {
				boolean state = isShowActivityIcon(dataList.get(i).getStatus());
				if (state) {
					*//**
					 * 显示活动状态图标
					 *//*
					dataList.get(i).setIsShowActivityIcon(true);
				} else {
					*//**
					 * 不显示活动状态图标
					 *//*
					dataList.get(i).setIsShowActivityIcon(false);
					if (!StringUtil.isEmpty(dataList.get(i).getNum())) {
						totalNum = totalNum
								+ Integer.parseInt(dataList.get(i).getNum());
					}
					if (!StringUtil.isEmpty(dataList.get(i).getCurrentprice())) {
						totalPrice = totalPrice
								+ Float.parseFloat(dataList.get(i)
										.getCurrentprice())
								* Integer.parseInt(dataList.get(i).getNum());
					}
				}
				// 初始化默认全部选中
				dataList.get(i).setCheckBoxState(true);
			}
			LogUtil.d(TAG, "商品数量" + totalNum);
		}

		*//**
		 * 初始化商品默认数量
		 *//*
		if (dataList != null) {
			int length = dataList.size();
			for (int i = 0; i < length; i++) {
				dataList.get(i).setItemNewNum(dataList.get(i).getNum());

			}
		}
		filterOutDateProductInfors();
	}

	*//**
	 * 
	 * Describe:过滤已经过去的活动商品
	 * 
	 * Date:2015-11-4
	 * 
	 * Author:liuzhouliang
	 *//*
	public List<ProductInfor> filterOutDateProductInfors() {
		filterList = new ArrayList<ProductInfor>();
		filterList.addAll(dataList);
		Iterator<ProductInfor> iterator = filterList.iterator();
		while (iterator.hasNext()) {
			String stateString = iterator.next().getStatus();
			if (isShowActivityIcon(stateString)) {
				iterator.remove();
			}

		}

		return filterList;
	}

	*//**
	 * 
	 * Describe:判断是否显示活动结束图标
	 * 
	 * Date:2015-10-14
	 * 
	 * Author:liuzhouliang
	 *//*
	private boolean isShowActivityIcon(String state) {
		if ("0".equals(state)) {
			*//**
			 * 未开始
			 *//*
			return false;
		} else if ("1".equals(state)) {
			*//**
			 * 已经开始
			 *//*
			return false;
		} else if ("2".equals(state)) {
			*//**
			 * 已经抢光
			 *//*
			return true;
		} else if ("3".equals(state)) {
			*//**
			 * 已经结束
			 *//*
			return true;
		} else {
			return false;
		}
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		LogUtil.d(TAG, "bindView");
		if (convertView == null) {
			convertView = baseInflater.inflate(
					R.layout.fragment_shopping_cart_item, parent, false);
		}
		TextView tvTotalNum = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_totoalnum);
		TextView tvLefnumTextView = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_lefnum);
		TextView tvName = (TextView) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_name);
		TextView tvActivityPrice = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_activity_price);
		TextView tvNormalPrice = (TextView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_normal_price);
		ViewUtil.setActivityPrice(tvActivityPrice, dataList.get(position)
				.getCurrentprice());
		ViewUtil.setNormalPrice(tvNormalPrice, dataList.get(position)
				.getPrice());
		final ImageView iView = (ImageView) convertView
				.findViewById(R.id.fragment_shopping_cart_item_iv);
		String urlString = dataList.get(position).getPic();

		MyApplication.imageLoader.displayImage(urlString, iView,
				MyApplication.options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						dataList.get(position).setMbitmap(loadedImage);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
					}
				});

		tvLefnumTextView.setText(dataList.get(position).getLefnum() + "");
		tvTotalNum.setText(dataList.get(position).getKcnum() + "");
		tvName.setText(dataList.get(position).getName());
		final CheckBox cb = (CheckBox) ViewHolderUtil.getItemView(convertView,
				R.id.fragment_shopping_cart_item_cb);
		LinearLayout llPriceParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(convertView,
						R.id.fragment_shopping_cart_item_price_parent);
		ImageView ivOutTime = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_outtime);
		ImageView tvReduce = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_num_reduce);
		final ImageView tvAdd = (ImageView) ViewHolderUtil.getItemView(
				convertView, R.id.fragment_shopping_cart_item_num_add);
		final SoftEditText etProductNum = (SoftEditText) ViewHolderUtil
				.getItemView(convertView,
						R.id.fragment_shopping_cart_item_num_et);
		LinearLayout llTotalNumParentLayout = (LinearLayout) ViewHolderUtil
				.getItemView(convertView,
						R.id.fragment_shopping_cart_item_total_num);
		boolean checkBoxState = dataList.get(position).getCheckBoxState();
		etProductNum.removeTextChangedListener();
		etProductNum.setText(dataList.get(position).getItemNewNum());
		etProductNum.addTextChangedListener(new MyTextWatcher(position,
				etProductNum));
		// 记录每个商品的旧数量
		dataList.get(position).setItemOldNum(etProductNum.getText().toString());
		// 获取商品活动状态
		final String state = "1";
//		final String state = dataList.get(position).getStatus();

		LogUtil.d(TAG, "getview oldnum==" + etProductNum.getText().toString());

		*//**
		 * 按钮选择事件================
		 *//*
		final HashMap<String,String> map = new HashMap<String,String>();
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

				if (arg1) {
					map.put("chioceok","");
					MobclickAgent.onEvent(mActivity, "0045", map);
					*//**
					 * 选中===========================
					 *//*
					if (!mFragment.isEditType) {
						*//**
						 * 非编辑状态
						 *//*
						int p = position;
						LogUtil.d(TAG, p + "");
						dataList.get(position).setCheckBoxState(true);
						ShoppingCartSelectBean obj = new ShoppingCartSelectBean();
						obj.setId(dataList.get(position).getId());
						obj.setNum(dataList.get(position).getItemNewNum());
						String skuIdString = dataList.get(position).getId();
						int size = mFragment.selectProductNotInEdit.size();
						boolean isOutDate = isShowActivityIcon(state);
						if (size > 0) {
							boolean isAdd = false;
							*//**
							 * 判断当前选中的商品，在缓存中是否存在
							 *//*

							for (int i = 0; i < mFragment.selectProductNotInEdit
									.size(); i++) {
								if (skuIdString
										.equals(mFragment.selectProductNotInEdit
												.get(i))) {

									isAdd = true;

								}
							}

							if (!isAdd && !isOutDate) {
								*//**
								 * 未在缓存中，加入缓存集合中，更新数量，价格
								 *//*

								mFragment.selectProductNotInEdit.add(obj);

								updateDataMessage();
							}
						} else {
							*//**
							 * 非编辑状态下，选中商品，加入缓存
							 *//*
							if (!isOutDate) {
								mFragment.selectProductNotInEdit.add(obj);
								updateDataMessage();
							}

						}

						*//**
						 * 选中所有的商品后，设置全选控件为选中状态
						 *//*
						if (mFragment.selectProductNotInEdit.size() == filterList
								.size() && filterList.size() != 0) {
							mFragment.cbSelectAllBox.setChecked(true);
							mFragment.isCancelAll = true;
						} else if (mFragment.selectProductNotInEdit.size() < filterList
								.size()) {
							mFragment.cbSelectAllBox.setChecked(false);
						}
					} else {
						*//**
						 * 编辑状态下选中=============
						 *//*
						dataList.get(position).setCheckBoxState(true);
						boolean isAdd = false;
						*//**
						 * 判断当前选中的商品，在缓存中是否存在
						 *//*
						for (int i = 0; i < mFragment.deleteProductList.size(); i++) {
							if (dataList
									.get(position)
									.getAid()
									.equals(mFragment.deleteProductList.get(i)
											.getId())) {

								isAdd = true;
							}
						}
						if (!isAdd) {
							ShoppingCartProductDeleteBean obj = new ShoppingCartProductDeleteBean();
							obj.setId(dataList.get(position).getAid());
//							obj.setId(dataList.get(position).getId());
							mFragment.deleteProductList.add(obj);
						}
						*//**
						 * 选中所有的商品后，设置全选控件为选中状态
						 *//*

						if (mFragment.deleteProductList.size() == dataList
								.size()) {
							mFragment.cbSelectAllBox.setChecked(true);
							mFragment.isCancelAll = true;
						} else if (mFragment.deleteProductList.size() < dataList
								.size()) {
							mFragment.cbSelectAllBox.setChecked(false);
						}
					}

				} else {
					*//**
					 * 取消选中=========================
					 *//*
					map.put("chiocecancle","");
					MobclickAgent.onEvent(mActivity, "0046", map);
					if (!mFragment.isEditType) {
						*//**
						 * 非编辑状态下，取消选中
						 *//*
						dataList.get(position).setCheckBoxState(false);
						Iterator<ShoppingCartSelectBean> iterator = mFragment.selectProductNotInEdit
								.iterator();
						while (iterator.hasNext()) {
							ShoppingCartSelectBean obj = iterator.next();
							if (obj.getAid().equals(
									dataList.get(position).getId())) {
								iterator.remove();
								*//**
								 * 更新数量，价格
								 *//*
								updateDataMessage();
							}

						}
						*//**
						 * 取消选中所有的商品后，设置全选控件为非选中状态
						 *//*
						if (mFragment.selectProductNotInEdit.size() == 0) {
							*//**
							 * 取消选中后，没有选中商品
							 *//*
							mFragment.isCancelAll = true;
							mFragment.cbSelectAllBox.setChecked(false);

						} else if (mFragment.selectProductNotInEdit.size() < filterList
								.size()) {
							*//**
							 * 取消选中后，选中的商品小于全部的商品数目
							 *//*
							mFragment.isCancelAll = false;
							mFragment.cbSelectAllBox.setChecked(false);
						}
					} else {
						*//**
						 * 编辑状态下，取消选中
						 *//*
						dataList.get(position).setCheckBoxState(false);
						Iterator<ShoppingCartProductDeleteBean> iterator = mFragment.deleteProductList
								.iterator();
						while (iterator.hasNext()) {
							ShoppingCartProductDeleteBean obj = iterator.next();
							if (obj.getId().equals(
									dataList.get(position).getId())) {
								iterator.remove();
							}

						}
						if (mFragment.deleteProductList.size() == 0) {
							*//**
							 * 取消选中后，没有选中商品
							 *//*
							mFragment.isCancelAll = true;
							mFragment.cbSelectAllBox.setChecked(false);

						} else if (mFragment.deleteProductList.size() < dataList
								.size()) {
							*//**
							 * 取消选中后，选中的商品小于全部的商品数目
							 *//*
							mFragment.isCancelAll = false;
							mFragment.cbSelectAllBox.setChecked(false);
						}
					}
				}
			}
		});
		if (!checkBoxState) {
			cb.setChecked(false);
			*//**
			 * 未选择状态=============
			 *//*
			if ("2".equals(state)) {
				*//**
				 * 已经抢光
				 *//*
				llTotalNumParentLayout.setVisibility(View.VISIBLE);
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.panic_buy_over);
				if (!mFragment.isEditType) {
					*//**
					 * 非编辑状态===================
					 *//*
					cb.setVisibility(View.INVISIBLE);
				} else {
					*//**
					 * 编辑状态===============
					 *//*
					cb.setVisibility(View.VISIBLE);
				}

				llPriceParentLayout.setVisibility(View.GONE);
				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
						R.color.color_b7b7b7));
			} else if ("3".equals(state)) {
				*//**
				 * 已经结束
				 *//*
				llTotalNumParentLayout.setVisibility(View.VISIBLE);
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.activity_over);
				if (!mFragment.isEditType) {
					*//**
					 * 非编辑状态===================
					 *//*
					cb.setVisibility(View.INVISIBLE);
				} else {
					*//**
					 * 编辑状态===============
					 *//*
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
						R.color.color_b7b7b7));
			} else {
				*//**
				 * 活动未结束
				 *//*
				llTotalNumParentLayout.setVisibility(View.GONE);
				ivOutTime.setVisibility(View.GONE);
				cb.setVisibility(View.VISIBLE);
				llPriceParentLayout.setVisibility(View.VISIBLE);
				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
						android.R.color.holo_red_dark));
			}

		} else {
			*//**
			 * 已经选择状态==========================
			 * 
			 * 此处监听必须在checkbox初始化状态前
			 * 添加监听器的代码在初始化checkBox属性的代码之后,也就是说当初始化checkBox属性时,由于可能改变其状态,
			 * 导致调用了onCheckedChange
			 * ()方法,而这个监听器是在上一次初始化的时候添加的,那么当然其index就是上一次的positon值
			 * ,而不是本次的,所以每次保存checkBox属性状态的时候
			 * ，都把值赋到的list集合里其它对象上去了,而不是与本次index相关的对象上,这才是发生莫名其妙错乱的真正原因.
			 *//*
			cb.setChecked(true);
			if ("2".equals(state)) {
				*//**
				 * 已经抢光
				 *//*
				llTotalNumParentLayout.setVisibility(View.VISIBLE);
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.panic_buy_over);
				if (!mFragment.isEditType) {
					*//**
					 * 非编辑状态===================
					 *//*
					cb.setVisibility(View.INVISIBLE);
				} else {
					*//**
					 * 编辑状态===============
					 *//*
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
						R.color.color_b7b7b7));
			} else if ("3".equals(state)) {
				*//**
				 * 已经结束
				 *//*
				llTotalNumParentLayout.setVisibility(View.VISIBLE);
				ivOutTime.setVisibility(View.VISIBLE);
				ivOutTime.setImageResource(R.drawable.activity_over);
				if (!mFragment.isEditType) {
					*//**
					 * 非编辑状态===================
					 *//*
					cb.setVisibility(View.INVISIBLE);
				} else {
					*//**
					 * 编辑状态===============
					 *//*
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
						R.color.color_b7b7b7));
			} else {
				llTotalNumParentLayout.setVisibility(View.GONE);
				ivOutTime.setVisibility(View.GONE);
				cb.setVisibility(View.VISIBLE);
				llPriceParentLayout.setVisibility(View.VISIBLE);
				tvActivityPrice.setTextColor(mcontext.getResources().getColor(
						android.R.color.holo_red_dark));

			}
		}
		if (dataList.get(position).getCheckBoxState()) {
			cb.setChecked(true);
		} else {
			cb.setChecked(false);
		}

		*//**
		 * 数量减少事件====================
		 *//*
		tvReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("Reduce","");
				MobclickAgent.onEvent(mActivity, "0042", map);

				// 最小购买量
				notifyDataSetChanged();
				long miniBuyNum = 0;
				if (!StringUtil.isEmpty(dataList.get(position).getInitnum())) {
					miniBuyNum = Long.parseLong(dataList.get(position)
							.getInitnum());
				}
				if (StringUtil.isEmpty(dataList.get(position).getItemNewNum())) {
					*//**
					 * 内存中当前数量为空
					 *//*
					if (!StringUtil
							.isEmpty(dataList.get(position).getInitnum())) {
						dataList.get(position).setItemNewNum(
								(Long.parseLong(dataList.get(position)
										.getInitnum())) + "");
					} else {
						dataList.get(position).setItemNewNum(1 + "");
					}
				}
				if (Integer.parseInt(dataList.get(position).getItemNewNum()) > miniBuyNum) {
					*//**
					 * 如果当前数量大于最小购买量，既可进行减操作
					 *//*
					clickPosition = position;
					addShoppingCart(
							dataList.get(position).getId(),dataList.get(position).getId(),
							(Long.parseLong(dataList.get(position)
									.getItemNewNum()) - 1) + "",dataList.get(position).getType());
					clickTypeString = "reduce";
				} else {
					ToastUtil.showToast(mcontext, "最少购买" + miniBuyNum + "件");

				}

			}
		});
		*//**
		 * 增加事件=============================
		 *//*
		tvAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("Add","");
				MobclickAgent.onEvent(mActivity, "0041", map);
				notifyDataSetChanged();
				clickTypeString = "add";
				clickPosition = position;
				if (StringUtil.isEmpty(dataList.get(position).getItemNewNum())) {
					*//**
					 * 内存中当前数量为空
					 *//*
					if (!StringUtil
							.isEmpty(dataList.get(position).getInitnum())) {
						dataList.get(position).setItemNewNum(
								(Long.parseLong(dataList.get(position)
										.getInitnum())) + "");
					} else {
						dataList.get(position).setItemNewNum(1 + "");
					}

				}
				addShoppingCart(
						dataList.get(position).getId(),dataList.get(position).getId(),
						(Long.parseLong(dataList.get(position).getItemNewNum()) + 1)
								+ "",dataList.get(position).getType());

				addShopCarAnimation(iView, dataList.get(position).getMbitmap());
			}
		});
		*//**
		 * 进入商品详情
		 *//*
		iView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mcontext, ProductDetail.class);
				intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
						dataList.get(position).getAid());
				StartActivityUtil.startActivity((Activity) mcontext, intent);
			}
		});
		return convertView;
	}

	*//**
	 * 
	 * Describe:添加购物车动画
	 * 
	 * Date:2015-9-28
	 * 
	 * Author:liuzhouliang
	 *//*
	private void addShopCarAnimation(View v, Bitmap bitmap) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
//		v.getLocationInWindow(location);
		ImageView animationImg = new ImageView(mActivity);
		if (bitmap != null) {
			animationImg.setImageBitmap(bitmap);
		} else {
			Resources res = mcontext.getResources();
			Bitmap bmp = BitmapFactory.decodeResource(res,
					R.drawable.imageview_default);
			animationImg.setImageBitmap(bmp);
		}

//		ShoppingCartAnimationManager.setListAnimation(mActivity, animationImg,
//				location,
//				MainActivity.ivShoppingCart);
		//购物车抛物线动画
		ShoppingCartAnimationManager.setDetailAnimation(mActivity, animationImg, location,MainActivity.ivShoppingCart);

	}

	*//**
	 * 
	 * Describe:EditText输入事件
	 * 
	 * Date:2015-9-28
	 * 
	 * Author:liuzhouliang
	 *//*
	private class MyTextWatcher implements TextWatcher {
		private int position;

		public MyTextWatcher(int position, SoftEditText etEditText) {
			this.position = position;

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (StringUtil.isEmpty(s.toString())) {
				*//**
				 * 输入后为空======================
				 *//*
				dataList.get(position).setItemNewNum(
						dataList.get(position).getInitnum());
				*//**
				 * 更新缓存商品数量
				 *//*
				updateCacheProductsNotInEdit(dataList.get(position).getId(),
						"" + dataList.get(position).getItemNewNum());

			} else {
				*//**
				 * 输入后非空========================点击事件也会调用
				 *//*
				dataList.get(position).setItemNewNum(s.toString());
				*//**
				 * 更新缓存商品数量
				 *//*
				updateCacheProductsNotInEdit(dataList.get(position).getId(),
						"" + dataList.get(position).getItemNewNum());

			}

		}
	}

	*//**
	 * 获取所有商品数量和价格
	 *//*
	public void getTotalPrice() {
		totalPrice = 0;
		totalNum = 0;
		int size = dataList.size();
		for (int i = 0; i < size; i++) {
			if (dataList.get(i).getCheckBoxState()
					&& !dataList.get(i).getIsShowActivityIcon()) {
				totalPrice = totalPrice
						+ Float.parseFloat(dataList.get(i).getCurrentprice())
						* Long.parseLong(dataList.get(i).getItemNewNum());
				totalNum = totalNum + 1;
			}

		}

	}

	*//**
	 * 
	 * Describe:发送消息更新结算商品数量
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 *//*
	private void updateDataMessage() {
		getTotalPrice();
		if (!mFragment.isEditType) {
			Message messageAdd = Message.obtain();
			messageAdd.what = 1000;
			Bundle dataBundleAdd = new Bundle();
			long[] num = new long[1];
			num[0] = totalNum;
			dataBundleAdd.putLongArray("totalNum", num);
			dataBundleAdd.putFloat("totalPrice", totalPrice);
			messageAdd.setData(dataBundleAdd);
			messageHandler.sendMessage(messageAdd);
		}

	}

	*//**
	 * 
	 * Describe:更新非编辑状态下缓存商品的数量
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 *//*
	private void updateCacheProductsNotInEdit(String id, String num) {
		List<ShoppingCartSelectBean> cacheProduct = mFragment.selectProductNotInEdit;
		if (cacheProduct != null && cacheProduct.size() > 0) {
			for (ShoppingCartSelectBean product : cacheProduct) {
				if (id.equals(product.getAid())) {
					product.setNum(num);
					break;
				}
			}
		}

	}

	*//**
	 * 
	 * Describe:添加购物车
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:liuzhouliang
	 *//*
	private void addShoppingCart(String shopid,String id, String num,String type) {
		ApiHttpCilent.getInstance(mcontext).addShoppingCart(mcontext,shopid,id, num,
				0,type, new AddShoppingCartRequestCallBack());
	}

	*//**
	 * 
	 * Describe:加入购物车请求回调
	 * 
	 * Date:2015-10-23
	 * 
	 * Author:liuzhouliang
	 *//*

	public class AddShoppingCartRequestCallBack extends
			BaseJsonHttpResponseHandler<AddShoppingCartBean> {

		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, AddShoppingCartBean arg4) {
			ApiHttpCilent.loading.dismiss();
			Message message = Message.obtain();
			message.what = ConstantsUtil.HTTP_FAILE;// 错误
			addShoppingCartMessageHandler.sendMessage(message);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				AddShoppingCartBean arg3) {
		}

		@Override
		protected AddShoppingCartBean parseResponse(String response,
				boolean arg1) throws Throwable {
			ApiHttpCilent.loading.dismiss();
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

	*//**
	 * 
	 * Describe:加入购物车消息返回处理
	 * 
	 * Date:2015-10-23
	 * 
	 * Author:liuzhouliang
	 *//*
	public static class AddShoppingCartMessageHandler extends
			WeakHandler<ShoppingCartFragmentAdapter> {

		public AddShoppingCartMessageHandler(
				ShoppingCartFragmentAdapter reference) {
			super(reference);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				*//**
				 * 处理成功的数据
				 *//*
				if ("reduce".equals(getReference().clickTypeString)) {
					*//**
					 * 减少操作==========================
					 *//*
					String currentNum = getReference().dataList.get(
							getReference().clickPosition).getItemNewNum();
					long newNum = Long.parseLong(currentNum) - 1;
					getReference().dataList.get(getReference().clickPosition)
							.setItemNewNum(newNum + "");
					getReference().notifyDataSetChanged();
					*//**
					 * 更新缓存中商品数量
					 *//*
					getReference().updateCacheProductsNotInEdit(
							getReference().dataList.get(
									getReference().clickPosition).getId(),
							getReference().dataList.get(
									getReference().clickPosition)
									.getItemNewNum());
					getReference().updateDataMessage();

				}
				if ("add".equals(getReference().clickTypeString)) {
					*//**
					 * 增加操作==================================
					 *//*
					String currentNum = getReference().dataList.get(
							getReference().clickPosition).getItemNewNum();
					long newNum = Long.parseLong(currentNum) + 1;
					getReference().dataList.get(getReference().clickPosition)
							.setItemNewNum(newNum + "");
					getReference().notifyDataSetChanged();
					*//**
					 * 更新缓存中商品数量
					 *//*
					getReference().updateCacheProductsNotInEdit(
							getReference().dataList.get(
									getReference().clickPosition).getId(),
							getReference().dataList.get(
									getReference().clickPosition)
									.getItemNewNum());
					getReference().updateDataMessage();

				}

				break;
			case ConstantsUtil.HTTP_FAILE:
				*//**
				 * 处理失败的数据
				 *//*
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference().mcontext, messageString);
					*//**
					 * 处理失败数据
					 *//*
				} else {
					ToastUtil.showToast(getReference().mcontext, "加入购物车失败");
				}
				break;
			}
		}

	}

}
*/