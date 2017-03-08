package com.heheys.ec.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.controller.activity.ShoppingCartActivity;
import com.heheys.ec.controller.activity.ShoppingCartActivity.shoppingCartMessageThisHandler;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.SuitListShopping;
import com.heheys.ec.model.dataBean.ShoppingCartProductDeleteBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.utils.StatusString;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.AlertDialogCustom.BackRemark;
import com.heheys.ec.view.SoftEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//import com.heheys.ec.controller.fragment.ShoppingCartFragment;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param创建时间 ：2016-3-24 上午10:49:40 类说明
 */
public class NewShoppingCartFragmentAdapter extends
		BaseListAdapter<NewProductInfo> {
	private static final String TAG = NewShoppingCartFragmentAdapter.class
			.getName();
//	private Activity mActivity;
	private ShoppingCartActivity mFragment;
	private shoppingCartMessageThisHandler messageHandler;
	public static long totalNum;//购物车右上角数字 是购物车总件数 不会随选中的个数变化
	public long totalNumAll;// 选择商品个数
	public float totalPrice = 0.00f;
	public String totalPrice_two = "";
	private AddShoppingCartBean addShoppingCartData;
	private AddShoppingCartMessageHandler addShoppingCartMessageHandler;
	private int clickPosition;
	// Bitmap bitmap;
	private String clickTypeString;
	List<NewProductInfo> filterList;
	private NewProductInfo bean;
	private ImageView ivShoppingCart;
	private int touchedPosition;
	private final AlertDialogCustom mdialog;
	private boolean isClickAddReduce = false;
	// 全局的修改当前数量的中介对象
	private long newNumCurrt;
	public static boolean cancommit = true;// 是否可以提交
	public SoftEditText etProductNum;
	private ImageView ivanim;
	private Bitmap bitanim;
	private boolean ischange;
	private static final int MAX_TYPE_NUM = 2;
	private static final int TYPE_SIGLE = 0;// 单品视图
	private static final int TYPE_SUIT = 1;// 套装视图
	boolean isTextChange = true;// 表示是否是手动修改文本

	public NewShoppingCartFragmentAdapter(List<NewProductInfo> data,
										  Context context, ShoppingCartActivity fragment,
										  shoppingCartMessageThisHandler shoppingCartMessageThisHandler, ListView lv,
										  ImageView ivShoppingCart) {
		super(data, context);
		// TODO Auto-generated constructor stub
		LogUtil.d(TAG, "ShoppingCartFragmentAdapter");
		addShoppingCartMessageHandler = new AddShoppingCartMessageHandler(this);
//		mActivity = (Activity) context;
		mFragment = fragment;
		this.ivShoppingCart = ivShoppingCart;
		this.messageHandler = shoppingCartMessageThisHandler;
		mdialog = new AlertDialogCustom();
		/**
		 * 初始化选择按钮选择状态
		 */
		if (dataList != null) {
			int length = dataList.size();
			for (int i = 0; i < length; i++) {
				boolean state = isShowActivityIcon(dataList.get(i).getStatus());
				if (state) {
					/**
					 * 显示活动状态图标
					 */
					dataList.get(i).setCheckBoxState(false);
					dataList.get(i).setShowActivityIcon(true);
				} else {
					dataList.get(i).setCheckBoxState(true);
					/**
					 * 不显示活动状态图标
					 */
					dataList.get(i).setShowActivityIcon(false);
					if (!StringUtil.isEmpty(dataList.get(i).getNum())) {
						totalNum = totalNum
								+ Integer.parseInt(dataList.get(i).getNum());
					}
					if (!StringUtil.isEmpty(dataList.get(i).getCurrentprice())) {
						if ("1".equals(dataList.get(i).getType())) {// 批发是全部价格
							totalPrice = (float) (totalPrice + Double
									.parseDouble(StringUtil.isEmpty(dataList
											.get(i).getCurrentprice()) ? "0"
											: dataList.get(i).getCurrentprice())
									* Integer
											.parseInt(StringUtil
													.isEmpty(dataList.get(i)
															.getNum()) ? "0"
													: dataList.get(i).getNum()));
						} else {
							totalPrice = (float) (totalPrice + Double
									.parseDouble(StringUtil.isEmpty(dataList
											.get(i).getCprice()) ? "0"
											: dataList.get(i).getCprice())
									* Integer
											.parseInt(StringUtil
													.isEmpty(dataList.get(i)
															.getNum()) ? "0"
													: dataList.get(i).getNum()));
						}
					}
				}
				// 初始化默认全部选中
				dataList.get(i).setCheckBoxState(dataList.get(i).getCheckBoxState());
				mFragment.cbSelectAllBox.setChecked(true);
			}
		}

		/**
		 * 初始化商品默认数量
		 */
		if (dataList != null) {
			int length = dataList.size();
			for (int i = 0; i < length; i++) {
				dataList.get(i).setItemNewNum(dataList.get(i).getNum());

			}
		}
		filterOutDateProductInfors();// 过滤已经结束和已经完成的活动
	}

	void setImage(ImageView ivanim, Bitmap bitanim) {
		this.ivanim = ivanim;
		this.bitanim = bitanim;
	}

	/**
	 *
	 * Describe:过滤已经过去的活动商品
	 *
	 * Date:2015-11-4
	 *
	 * Author:liuzhouliang
	 */
	public List<NewProductInfo> filterOutDateProductInfors() {
		filterList = new ArrayList<NewProductInfo>();
		filterList.addAll(dataList);
		Iterator<NewProductInfo> iterator = filterList.iterator();
		while (iterator.hasNext()) {
			String stateString = iterator.next().getStatus();
			if (isShowActivityIcon(stateString)) {
				iterator.remove();
			}
		}

		return filterList;
	}

	/**
	 *
	 * Describe:判断是否显示活动结束图标
	 *
	 * Date:2015-10-14
	 *
	 * Author:liuzhouliang
	 */
	public static boolean isShowActivityIcon(String state) {
		/*
		 * 5、已售罄 6、已下架 拼单： 1、正常 2、已抢光 3、已结束 4、已成单
		 */
		if ("0".equals(state)) {
			/**
			 * 未开始
			 */
			return false;
		} else if ("1".equals(state)) {
			/**
			 * 已经开始
			 */
			return false;
		} else if ("2".equals(state)) {
			/**
			 * 已经抢光
			 */
			return true;
		} else if ("3".equals(state)) {
			/**
			 * 未成单
			 */
			return true;
		} else if ("4".equals(state)) {
			/**
			 * 已经成单 不能生成订单
			 */
			return true;
		} else if ("5".equals(state)) {
			/**
			 * 已经售罄
			 */
			return true;
		} else if ("6".equals(state)) {
			/**
			 * 已经下架
			 */
			return true;
		} else if ("7".equals(state)) {
			/**
			 * 已经结束
			 */
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position).getIssuit() == 2 ? TYPE_SUIT : TYPE_SIGLE;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return MAX_TYPE_NUM;
	}

	private class CheckBoxChangeClick implements OnCheckedChangeListener {

		private int position;
		private String state;
		private CheckBox cb;

		CheckBoxChangeClick(int position, String state) {
			this.position = position;
			this.state = state;
			bean = dataList.get(position);
		}

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {}
	}

	/*
	 * cb checkbox选择按钮呢
	 *
	 * position 当前位置
	 *
	 * ivOutTime 是否售罄 戳
	 *
	 * llPriceParentLayout 加减号父视图 *
	 */
	private void CbStatus(boolean checkBoxState, final CheckBox cb,
			final int position, final String state, final ImageView ivOutTime,
			final LinearLayout llPriceParentLayout) {
		cb.setTag(position);
		final HashMap<String, String> map = new HashMap<String, String>();

			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
//					position = (Integer)buttonView.getTag();
						if (isChecked) {
						map.put("chioceok", "");
						MobclickAgent.onEvent(mcontext, "0045", map);
						/**
						 * 选中===========================
						 */
						if (!mFragment.isEditType) {
							/**
							 * 非编辑状态
							 */
							int p = position;
							LogUtil.d(TAG, p + "");
							dataList.get((Integer)buttonView.getTag()).setCheckBoxState(true);
							String skuIdString;
							ShoppingCartSelectBean obj = new ShoppingCartSelectBean();
							if (dataList.get((Integer)buttonView.getTag()).getIssuit() == 2) {
								obj.setSid(dataList.get((Integer)buttonView.getTag()).getSid());
								skuIdString = dataList.get((Integer)buttonView.getTag()).getSid();
								obj.setAid("");
							} else {
								obj.setAid(dataList.get((Integer)buttonView.getTag()).getAid());
								skuIdString = dataList.get((Integer)buttonView.getTag()).getAid();
								obj.setSid("");
							}
							obj.setNum(dataList.get((Integer)buttonView.getTag()).getItemNewNum());

							int size = ShoppingCartActivity.selectProductNotInEdit
									.size();
							boolean isOutDate = isShowActivityIcon(state);
							if (size > 0) {
								boolean isAdd = false;
								/**
								 * 判断当前选中的商品，在缓存中是否存在
								 */

								for (int i = 0; i < ShoppingCartActivity.selectProductNotInEdit
										.size(); i++) {
									if (skuIdString
											.equals(StatusString
													.getSign(ShoppingCartActivity.selectProductNotInEdit
															.get(i).getAid()))
											|| skuIdString
													.equals(StatusString
															.getSign(ShoppingCartActivity.selectProductNotInEdit
																	.get(i).getSid()))) {

										isAdd = true;

									}
								}

								if (!isAdd && !isOutDate) {
									/**
									 * 未在缓存中，加入缓存集合中，更新数量，价格
									 */

									ShoppingCartActivity.selectProductNotInEdit
											.add(obj);

									updateDataMessage();
								}
							} else {
								/**
								 * 非编辑状态下，选中商品，加入缓存
								 */
								if (!isOutDate) {
									ShoppingCartActivity.selectProductNotInEdit
											.add(obj);
									updateDataMessage();
								}

							}

							/**
							 * 选中所有的商品后，设置全选控件为选中状态
							 */
							if (ShoppingCartActivity.selectProductNotInEdit.size() == filterList
									.size() && filterList.size() != 0) {
								mFragment.cbSelectAllBox.setChecked(true);
								mFragment.isCancelAll = true;
							} else if (ShoppingCartActivity.selectProductNotInEdit
									.size() < filterList.size()) {
								mFragment.cbSelectAllBox.setChecked(false);
							}
						} else {
							/**
							 * 编辑状态下选中=============
							 */
							dataList.get(position).setCheckBoxState(true);
							boolean isAdd = false;
							/**
							 * 判断当前选中的商品，在缓存中是否存在
							 */
							for (int i = 0; i < mFragment.deleteProductList.size(); i++) {
								if (dataList
										.get(position)
										.getId()
										.equals(mFragment.deleteProductList.get(i)
												.getId())) {

									isAdd = true;
								}
							}
							if (!isAdd) {
								ShoppingCartProductDeleteBean obj = new ShoppingCartProductDeleteBean();
								obj.setId(dataList.get((Integer)buttonView.getTag()).getId());
								mFragment.deleteProductList.add(obj);
							}
							/**
							 * 选中所有的商品后，设置全选控件为选中状态
							 */

							if (mFragment.deleteProductList.size() == dataList.size()) {
								mFragment.cbSelectAllBox.setChecked(true);
								mFragment.isCancelAll = true;
							} else if (mFragment.deleteProductList.size() < dataList
									.size()) {
								mFragment.cbSelectAllBox.setChecked(false);
							}
						}

					} else {
						/**
						 * 取消选中=========================
						 */
						map.put("chiocecancle", "");
						MobclickAgent.onEvent(mcontext, "0046", map);
						if (!mFragment.isEditType) {
							/**
							 * 非编辑状态下，取消选中
							 */
							dataList.get((Integer)buttonView.getTag()).setCheckBoxState(false);
							Iterator<ShoppingCartSelectBean> iterator = ShoppingCartActivity.selectProductNotInEdit
									.iterator();
									if(dataList.get((Integer)buttonView.getTag()).getIssuit() == 2){
										while (iterator.hasNext()) {
											ShoppingCartSelectBean obj = iterator.next();
										//组合套餐
										if(StatusString.getSign(obj.getSid()).equals(dataList.get((Integer)buttonView.getTag()).getSid())){
											//当前选中的数据等于集合里面的数据就 在集合中删除
											iterator.remove();
											/**
											 * 更新数量，价格
											 */
											updateDataMessage();
											break;
										  }
										}
									}else{
										//等于单品
										while (iterator.hasNext()) {
											ShoppingCartSelectBean obj = iterator.next();
										//组合套餐
										if(StatusString.getSign(obj.getAid()).equals(dataList.get((Integer)buttonView.getTag()).getAid())){
											//当前选中的数据等于集合里面的数据就 在集合中删除
											iterator.remove();
											/**
											 * 更新数量，价格
											 */
											updateDataMessage();
											break;
										  }
										}
									}
							/**
							 * 取消选中所有的商品后，设置全选控件为非选中状态
							 */
							if (ShoppingCartActivity.selectProductNotInEdit.size() == 0) {
								/**
								 * 取消选中后，没有选中商品
								 */
								mFragment.isCancelAll = true;
								mFragment.cbSelectAllBox.setChecked(false);

							} else if (ShoppingCartActivity.selectProductNotInEdit
									.size() < filterList.size()) {
								/**
								 * 取消选中后，选中的商品小于全部的商品数目
								 */
								mFragment.isCancelAll = false;
								mFragment.cbSelectAllBox.setChecked(false);
							}
						} else {
							/**
							 * 编辑状态下，取消选中  可以删除
							 */
							dataList.get((Integer)buttonView.getTag()).setCheckBoxState(false);
							Iterator<ShoppingCartProductDeleteBean> iterator = mFragment.deleteProductList
									.iterator();
							while (iterator.hasNext()) {
								ShoppingCartProductDeleteBean obj = iterator.next();
								if (obj.getId().equals(dataList.get(position).getId())) {
									iterator.remove();
								}

							}
							if (mFragment.deleteProductList.size() == 0) {
								/**
								 * 取消选中后，没有选中商品
								 */
								mFragment.isCancelAll = true;
								mFragment.cbSelectAllBox.setChecked(false);

							} else if (mFragment.deleteProductList.size() < dataList
									.size()) {
								/**
								 * 取消选中后，选中的商品小于全部的商品数目
								 */
								mFragment.isCancelAll = false;
								mFragment.cbSelectAllBox.setChecked(false);
							}
						}
					}
				}
			});
			if (dataList.get(position).getCheckBoxState()) {
				cb.setChecked(true);
			} else {
				cb.setChecked(false);
			}
			// 状态盖戳
			IvImageStatue(checkBoxState, state, cb, dataList.get(position),
					ivOutTime, llPriceParentLayout);
		}

	/*
	 * 盖戳的状态
	 */
	private void IvImageStatue(boolean checkBoxState, String state,
			CheckBox cb, NewProductInfo bean, ImageView ivOutTime,
			LinearLayout llPriceParentLayout) {
		if (!checkBoxState) {
			cb.setChecked(false);
			/**
			 * 未选择状态=============
			 */
			if ("2".equals(state)) {
				/**
				 * 已经抢光
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null)
					ivOutTime.setVisibility(View.VISIBLE);
				if (bean.getType().equals("1")) {
					if (ivOutTime != null)
						ivOutTime.setImageResource(R.drawable.yishouqing);
				} else {
					if (ivOutTime != null)
						ivOutTime.setImageResource(R.drawable.yiqiangguang);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}

				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("3".equals(state)) {
				/**
				 * 未成单
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.weichengdan);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("7".equals(state)) {
				/**
				 * 已经结束
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.activity_over);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("5".equals(state)) {
				/**
				 * 已经售罄
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("6".equals(state)) {
				/**
				 * 已经下架
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("4".equals(state)) {
				// 已成单
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.yichengdan);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else {
				/**
				 * 活动未结束
				 */
				// llTotalNumParentLayout.setVisibility(View.VISIBLE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.GONE);
				}
				cb.setVisibility(View.VISIBLE);
				llPriceParentLayout.setVisibility(View.VISIBLE);
			}

		} else {
			/**
			 * 已经选择状态==========================
			 *
			 * 此处监听必须在checkbox初始化状态前
			 * 添加监听器的代码在初始化checkBox属性的代码之后,也就是说当初始化checkBox属性时,由于可能改变其状态,
			 * 导致调用了onCheckedChange
			 * ()方法,而这个监听器是在上一次初始化的时候添加的,那么当然其index就是上一次的positon值
			 * ,而不是本次的,所以每次保存checkBox属性状态的时候
			 * ，都把值赋到的list集合里其它对象上去了,而不是与本次index相关的对象上,这才是发生莫名其妙错乱的真正原因.
			 */
			cb.setChecked(true);
			if ("2".equals(state)) {
				/**
				 * 已经抢光
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
				}
				if (bean.getType().equals("1")) {
					if (ivOutTime != null)
						ivOutTime.setImageResource(R.drawable.yishouqing);
				} else {
					if (ivOutTime != null)
						ivOutTime.setImageResource(R.drawable.yiqiangguang);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
				// tvActivityPrice.setTextColor(mcontext.getResources().getColor(
				// R.color.color_b7b7b7));
			} else if ("7".equals(state)) {
				/**
				 * 已经结束
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.activity_over);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("5".equals(state)) {
				/**
				 * 已经售罄
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("6".equals(state)) {
				/**
				 * 已经下架
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.yishouqing);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("4".equals(state)) {
				// 已成单
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.yichengdan);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else if ("3".equals(state)) {
				/**
				 * 未成单
				 */
				// llTotalNumParentLayout.setVisibility(View.GONE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.VISIBLE);
					ivOutTime.setImageResource(R.drawable.weichengdan);
				}
				if (!mFragment.isEditType) {
					/**
					 * 非编辑状态===================
					 */
					cb.setVisibility(View.INVISIBLE);
				} else {
					/**
					 * 编辑状态===============
					 */
					cb.setVisibility(View.VISIBLE);
				}
				llPriceParentLayout.setVisibility(View.GONE);
			} else {
				// llTotalNumParentLayout.setVisibility(View.VISIBLE);
				if (ivOutTime != null) {
					ivOutTime.setVisibility(View.GONE);
				}
				cb.setVisibility(View.VISIBLE);
				llPriceParentLayout.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		LogUtil.d(TAG, "bindView");
		bean = dataList.get(position);
		int type = getItemViewType(position);
		final boolean checkBoxState = bean.getCheckBoxState();
		if (convertView == null) {
			switch (type) {
			case TYPE_SIGLE:
				convertView = baseInflater
						.inflate(R.layout.new_fragment_shopping_cart_item,
								parent, false);
				break;
			case TYPE_SUIT:
				convertView = baseInflater.inflate(R.layout.suit_shopping_cart,
						parent, false);
				break;
			}
		}
		// 获取商品活动状态
		final String state = bean.getStatus();
		if (TYPE_SUIT == type) {
			// 套装视图
			final CheckBox cb = (CheckBox) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_suit_item_cb);// 套餐选中
			TextView tv_suitprice = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_suitprice);// 套餐价格
			ImageView tvReduce = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_num_reduce);// 减少按钮呢
			final ImageView tvAdd = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_num_add);// 增加按钮
			SoftEditText etProductNumSuit = (SoftEditText) ViewHolderUtil
					.getItemView(convertView,
							R.id.fragment_shopping_cart_item_num_et);
			ViewUtil.setActivityPrice(tv_suitprice,
					StatusString.getSign(bean.getCurrentprice()));
			etProductNumSuit
					.setText(StatusString.getSign(bean.getItemNewNum()));

			ListView lv_suit_cart = (ListView) ViewHolderUtil.getItemView(
					convertView, R.id.lv_suit_cart);
			TextView tv_goods_saleover = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_goods_saleover);
			LinearLayout llPriceParentLayout = (LinearLayout) ViewHolderUtil
					.getItemView(convertView,
							R.id.fragment_shopping_cart_item_price_parent);// 加键号父视图
			ShoppingCartItemAdapter cartItemAdapter = new ShoppingCartItemAdapter(
					bean.getSuitlist(), mcontext);
			lv_suit_cart.setAdapter(cartItemAdapter);

			Utils.setListViewHeightBasedOnChildren(lv_suit_cart);

			CbStatus(checkBoxState, cb, position, state, null,
					llPriceParentLayout);

			if (isShowActivityIcon(state))
				tv_goods_saleover.setVisibility(View.VISIBLE);
			else
				tv_goods_saleover.setVisibility(View.INVISIBLE);
			// 获取当前点击的数据
			ClickInput(etProductNumSuit, position);

			/**
			 * 数量减少事件====================
			 */
			ClickReduce(position, tvReduce);
			/**
			 * 增加事件=============================
			 */
			ClickAdd(position, tvAdd);
		} else {
			// 单品视图

			final CheckBox cb = (CheckBox) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_cb);
			TextView tv_wine_name = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_wine_name);// 名字
			TextView tip = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tip);// 标识商品类型
			TextView goods_wk = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.goods_wk);
			TextView goods_wk_unit = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.goods_wk_unit);

//			ImageView iv_pin = (ImageView) ViewHolderUtil.getItemView(
//					convertView, R.id.iv_pin);
			ImageView iv_activity = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_outtime);
			//合伙买定金
			LinearLayout linear_hhm_dj = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_hhm_dj);
			LinearLayout linear_gg = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_gg);
			LinearLayout linear_price_tv = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_price_tv);
			LinearLayout linear_dqj = (LinearLayout) ViewHolderUtil
					.getItemView(convertView, R.id.linear_dqj);
			TextView tv_nonum = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_nonum);// 库存不足
			// 批发视图
			LinearLayout linear_pf = (LinearLayout) ViewHolderUtil.getItemView(
					convertView, R.id.linear_pf);
			// 拼单视图
			LinearLayout linear_pd = (LinearLayout) ViewHolderUtil.getItemView(
					convertView, R.id.linear_pd);
			final ImageView iView = (ImageView) convertView
					.findViewById(R.id.iv_wineurl);

			ImageView tvReduce = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_num_reduce);
			final ImageView tvAdd = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_num_add);
			etProductNum = (SoftEditText) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_num_et);
			/*
			 * 批发视图初始化
			 */
			/**************************************************************************/
			TextView tv_guige = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_guige);// 规格
			TextView tv_price_tip = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_price_tip);//
			TextView tv_price = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_price);// 多少钱一瓶
			TextView tv_price_unit = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_price_unit);// 多少钱一瓶单位

			/**************************************************************************/
			/*
			 * 拼单视图初始化
			 */
			/**************************************************************************/
			TextView tv_price_now = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_price_now);// 当前价 ¥150
			TextView tv_price_unit_now = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_price_unit_now);// 当前价单位 /瓶
			TextView tv_unit = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_unit);// 定金
			TextView tv_unit_now = (TextView) ViewHolderUtil.getItemView(
					convertView, R.id.tv_unit_now);// 定金 单位

			/**************************************************************************/
			tv_wine_name.setText(bean.getName());
			tv_unit.setText("/" + bean.getUnit());
//			0合伙买 1甩卖 2:甩卖活动 3:E发行
			String tips = "";
			if("0".equals(bean.getType())){
				tips = "合伙买";
				if(!StringUtil.isEmpty(bean.getBalanceprice())){
					if(Double.parseDouble(bean.getBalanceprice())<=0){
					//尾款是否大于0
					goods_wk.setVisibility(View.INVISIBLE);
					goods_wk_unit.setVisibility(View.INVISIBLE);
					linear_hhm_dj.setVisibility(View.INVISIBLE);
					tv_price_tip.setText("合伙价:");
				}else{
					goods_wk.setVisibility(View.VISIBLE);
					goods_wk_unit.setVisibility(View.VISIBLE);
					linear_hhm_dj.setVisibility(View.VISIBLE);
					goods_wk.setText("¥ "+bean.getBalanceprice());
					goods_wk_unit.setText("/"+bean.getUnit());
					tv_price_tip.setText("订金:");
				 }
				}
			}else if("1".equals(bean.getType())){
				tips = "特卖";
				tv_price_tip.setVisibility(View.GONE);
			}else if("3".equals(bean.getType())){
				tips = "E发行";
			}
			tip.setVisibility(View.VISIBLE);
			tip.setText(tips);
			linear_pf.setVisibility(View.VISIBLE);
				linear_pd.setVisibility(View.GONE);
				if(bean.getIssuit() == 0){
					//组合套餐不显示规格
					tv_guige.setText(bean.getGuige());
					tv_guige.setVisibility(View.VISIBLE);
				}else{
					tv_guige.setVisibility(View.GONE);
				}
				ViewUtil.setActivityPrice(tv_price, bean.getCurrentprice());// 当前价
				tv_price_unit.setText("/" + bean.getUnit());// 当前价单位
//			if ("0".equals(bean.getType())) {
//				// 拼单
////				iv_pin.setVisibility(View.VISIBLE);
//				linear_pf.setVisibility(View.GONE);
//				linear_pd.setVisibility(View.VISIBLE);
//				ViewUtil.setActivityPrice(tv_price_now, bean.getCurrentprice());// 当前价
//				tv_price_unit_now.setText("/" + bean.getUnit());// 当前价单位
//				tv_unit_now.setText("/" + bean.getUnit());// 定金价格单位
//				ViewUtil.setActivityPrice(tv_unit, bean.getCprice());// 定金
//			} else {
//				// 批发
//				iv_pin.setVisibility(View.INVISIBLE);
//				linear_pf.setVisibility(View.VISIBLE);
//				linear_pd.setVisibility(View.GONE);
//				if(bean.getIssuit() == 0){
//					//组合套餐不显示规格
//					tv_guige.setText(bean.getGuige());
//					tv_guige.setVisibility(View.VISIBLE);
//				}else{
//					tv_guige.setVisibility(View.GONE);
//				}
//				ViewUtil.setActivityPrice(tv_price, bean.getCurrentprice());// 当前价
//				tv_price_unit.setText("/" + bean.getUnit());// 当前价单位
//			}
			//是否达到最小起批量
			if (!StringUtil.isEmpty(bean.getNumresult())) {
					tv_nonum.setText(bean.getNumresult());
					tv_nonum.setVisibility(View.VISIBLE);
			} else {
				tv_nonum.setVisibility(View.INVISIBLE);
			}
			iv_activity.setVisibility(View.VISIBLE);
			if ("2".equals(bean.getStatus())) {
				/*
				 * 已抢光
				 */
				if ("1".equals(bean.getType())) {
					iv_activity.setImageResource(R.drawable.yishouqing);
				} else {
					iv_activity.setImageResource(R.drawable.yiqiangguang);
				}
			} else if ("4".equals(bean.getStatus())) {
				/*
				 * 已成单
				 */
				iv_activity.setImageResource(R.drawable.yichengdan);
			} else if ("3".equals(bean.getStatus())) {
				/*
				 * 未成单
				 */
				iv_activity.setImageResource(R.drawable.weichengdan);
			} else if ("5".equals(bean.getStatus())) {
				/*
				 * 已售罄
				 */
				iv_activity.setImageResource(R.drawable.yishouqing);
			} else if ("6".equals(bean.getStatus())) {
				/*
				 * 已下架
				 */
				iv_activity.setImageResource(R.drawable.yishouqing);
			} else if ("7".equals(bean.getStatus())) {
				/*
				 * 已结束
				 */
				iv_activity.setImageResource(R.drawable.activity_over);
			} else {
				iv_activity.setVisibility(View.INVISIBLE);
			}

			String urlString = bean.getPic();

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
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							bean.setMbitmap(loadedImage);
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
						}
					});

			LinearLayout llPriceParentLayout = (LinearLayout) ViewHolderUtil
					.getItemView(convertView,
							R.id.fragment_shopping_cart_item_price_parent);// 加键号父视图
			ImageView ivOutTime = (ImageView) ViewHolderUtil.getItemView(
					convertView, R.id.fragment_shopping_cart_item_outtime);// 是否完成视图

			LinearLayout llTotalNumParentLayout = (LinearLayout) ViewHolderUtil
					.getItemView(convertView,
							R.id.fragment_shopping_cart_item_total_num);// 是否加减购买视图

			ischange = true;
			etProductNum.setText(dataList.get(position).getItemNewNum());
			ischange = false;
			// 获取当前点击的数据
			ClickInput(etProductNum, position);
			// 初始化购买数量
			if (!StringUtil.isEmpty(dataList.get(position).getKcnum())
					&& !StringUtil.isEmpty(dataList.get(position)
							.getItemNewNum())
					&& !StringUtil.isEmpty(dataList.get(position).getMinnum())) {
				int currentdate = Integer.parseInt(dataList.get(position)
						.getItemNewNum());// 当前购买数
				int kcnum = Integer.parseInt(dataList.get(position).getKcnum());// 库存
				int kmim = Integer.parseInt(dataList.get(position).getMinnum());// 最小起批量
				if (kcnum > 0
						&& !isShowActivityIcon(dataList.get(position)
								.getStatus())) {
					if (kcnum >= kmim) {
						// 有库存
						if (currentdate > kcnum) {
							// 当前购买量大于库存
							tv_nonum.setText("当前库存量" + kcnum);
							tv_nonum.setVisibility(View.VISIBLE);
						} else if (currentdate < kmim) {
							// 当前购买量小于最小起批量
							tvReduce.setEnabled(true);
							tvAdd.setEnabled(true);
							tv_nonum.setVisibility(View.VISIBLE);
							tv_nonum.setText("最小起批量" + kmim);
						} else {
							// 正常情况
							tvReduce.setEnabled(true);
							tvAdd.setEnabled(true);
							tv_nonum.setVisibility(View.GONE);
						}
					} else {
						tvReduce.setEnabled(false);
						tvAdd.setEnabled(false);
						tvReduce.setBackgroundResource(R.drawable.gou_jian);
						tvAdd.setBackgroundResource(R.drawable.gou_jia);
						tv_nonum.setVisibility(View.INVISIBLE);
						//当前起批量大于库存
						dataList.get(position).setItemNewNum(kcnum+"");
						etProductNum.setText(dataList.get(position).getItemNewNum());
//						tv_nonum.setText("当前库存:" + kcnum + "\n当前起批量:" + kmim);
					}
				} else if (!isShowActivityIcon(dataList.get(position)
						.getStatus())) {
					// 库存不足
					tvReduce.setEnabled(false);
					tvAdd.setEnabled(false);
					tv_nonum.setVisibility(View.VISIBLE);
					tv_nonum.setText("库存不足");
				}
			}

			if(dataList.get(position).getIssuit() == 1){
				//组合套装隐藏单位
				linear_gg.setVisibility(View.INVISIBLE);
				tv_price_unit.setText("/"+(StringUtil.isEmpty(dataList.get(position).getUnit())?"":dataList.get(position).getUnit()));
			}
			/**
			 * 按钮选择事件================
			 */
			cb.setTag(position);
			CbStatus(checkBoxState, cb, position, state, ivOutTime,
					llPriceParentLayout);

			/**
			 * 数量减少事件====================
			 */
			ClickReduce(position, tvReduce);
			/**
			 * 增加事件=============================
			 */
			ClickAdd(position, tvAdd);
			/**
			 * 进入商品详情
			 */
			iView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mcontext,
							NewProductDetailActivity.class);
					intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY,
							dataList.get(position).getAid());
					StartActivityUtil
							.startActivity((Activity) mcontext, intent);
				}
			});

		}
		return convertView;
	}

	private void ClickAdd(final int position, final ImageView tvAdd) {
		tvAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MobclickAgent.onEvent(mcontext, "C_SC_4");
				notifyDataSetChanged();
				clickTypeString = "add";
				clickPosition = position;
				isTextChange = false;
				if (!StringUtil.isEmpty(dataList.get(position).getKcnum())
						&& !StringUtil.isEmpty(dataList.get(position)
								.getItemNewNum())
						&& !StringUtil.isEmpty(dataList.get(position)
								.getMinnum())) {
					Long Newnum = Long.parseLong(dataList.get(position)
							.getItemNewNum());
					Long kcum = Long.parseLong(dataList.get(position)
							.getKcnum());
					Long Minnum = Long.parseLong(dataList.get(position)
							.getMinnum());
					if (Newnum >= kcum && kcum >= Minnum) {
						// 如果当前数据大于库存
						ToastUtil.showToast(mcontext, "最大库存是" + kcum);
						dataList.get(position).setItemNewNum(
								dataList.get(position).getKcnum());
						AddReduceShoppingCart(position);
					} else if (Newnum < Minnum && Minnum <= kcum) {
						// 小于最小起批量
						ToastUtil.showToast(mcontext, "最小起批量是" + Minnum);
						dataList.get(position).setItemNewNum(
								dataList.get(position).getMinnum());
						AddReduceShoppingCart(position);
					} else if (Newnum >= Minnum && Newnum < kcum) {
						// 正常加1
						dataList.get(position).setItemNewNum((Newnum + 1) + "");
						AddReduceShoppingCart(position);
					}
				}
				setImage(tvAdd, dataList.get(position).getMbitmap());
			}
		});
	}

	private void ClickReduce(final int position, ImageView tvReduce) {
		tvReduce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickReduce(position);
			}

			private void clickReduce(final int position) {
				MobclickAgent.onEvent(mcontext, "C_SC_5");
				// 最小购买量
				notifyDataSetChanged();
				clickPosition = position;
				clickTypeString = "reduce";
				isTextChange = false;
				if (!StringUtil.isEmpty(dataList.get(position).getKcnum())
						&& !StringUtil.isEmpty(dataList.get(position)
								.getItemNewNum())
						&& !StringUtil.isEmpty(dataList.get(position)
								.getMinnum())) {
					Long Newnum = Long.parseLong(dataList.get(position)
							.getItemNewNum());
					Long kcum = Long.parseLong(dataList.get(position)
							.getKcnum());
					Long Minnum = Long.parseLong(dataList.get(position)
							.getMinnum());
					if (Newnum > Minnum && Newnum <= kcum) {
						// 如果当前数量大于最小起批量
						dataList.get(position).setItemNewNum(
								(Long.parseLong(dataList.get(position)
										.getItemNewNum()) - 1) + "");
						AddReduceShoppingCart(position);
					} else if (Newnum > Minnum && Newnum >= kcum) {
						// 大于最小起批量 大于库存 直接给到最大库存
						ToastUtil.showToast(mcontext, "最大库存是" + kcum);
						dataList.get(position).setItemNewNum(kcum + "");
						AddReduceShoppingCart(position);
					} else if (Newnum <= Minnum && Minnum <= kcum) {
						// 小于最小起批量
						ToastUtil.showToast(mcontext, "最小起批量是" + Minnum);
						dataList.get(position).setItemNewNum(Minnum + "");
//						AddReduceShoppingCart(position);
					}
				}
			}

		});
	}

	/*
	 * 加入购物车判断 Issuit ==2 套装 其他情况单品处理 *
	 */
	private void AddReduceShoppingCart(final int position) {
		if (dataList.get(position).getIssuit() != 2)
			addShoppingCart(dataList.get(position).getAid(),
					dataList.get(position).getItemNewNum());
		else
			AddSuitToShopping(dataList.get(position).getSid(),
					dataList.get(position).getItemNewNum());
	}

	/*
	 * etProductNum 输入框
	 */
	private void ClickInput(final SoftEditText etProductNum, final int position) {
		etProductNum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickPosition = position;
				mdialog.AlertUpdateNum((Activity) mcontext, StatusString
						.getSign(dataList.get(clickPosition).getMinnum()),
						Integer.parseInt(StatusString.getSign(dataList.get(
								clickPosition).getKcnum())), ((SoftEditText) v)
								.getText().toString(), new BackRemark() {
							@Override
							public void setRemark(String cid, String currtText) {
								if (!StringUtil.isEmpty(currtText)) {
									String currentdate = currtText;
									dataList.get(clickPosition).setItemNewNum(
											currentdate);
									clickTypeString = "textchange";
									cancommit = true;
									// 加入购物车 =2是组合套装 其他是单品加入购物车处理
									AddReduceShoppingCart(position);
									etProductNum.setText(currentdate + "");
								}
							}
						});
			}
		});
	}

	/*
	 * 套装加入购物车 *
	 */
	void AddSuitToShopping(String id, String newnum) {
		ApiHttpCilent.getInstance(mcontext.getApplicationContext()).AddSuitToShopping(mcontext, id,
				newnum, new AddSuitShoppingCartRequestCallBack());
	}

	/*
	 *
	 * 套装加入购物车回调 *
	 */
	public class AddSuitShoppingCartRequestCallBack extends
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
		}

		@Override
		protected AddShoppingCartBean parseResponse(String response,
				boolean arg1) throws Throwable {
			Dismess();
			Gson gson = new Gson();
			AddShoppingCartBean addShoppingCartData = gson.fromJson(response,
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

	private int nowNum;// 输入前的数据


	/**
	 * 获取所有商品数量和价格
	 */
	public void getTotalPrice() {
		totalPrice = 0;
		totalNum = 0;
		totalNumAll = 0;
		int size = dataList.size();
		for (int i = 0; i < size; i++) {
			if (!dataList.get(i).isShowActivityIcon()) {
				if ("1".equals(dataList.get(i).getType())) {
					// 批发取getCurrentprice
					if (dataList.get(i).getCheckBoxState())
						totalPrice = (float) (totalPrice + Double
								.parseDouble(StatusString.getSign(dataList.get(
										i).getCurrentprice()))
								* Integer.parseInt(StatusString
										.getSign(dataList.get(i)
												.getItemNewNum())));
				} else {
					// 拼单取cprice
					if (dataList.get(i).getCheckBoxState())
						totalPrice = (float) (totalPrice + Double
								.parseDouble(StatusString.getSign(dataList.get(
										i).getCprice()))
								* Integer.parseInt(StatusString
										.getSign(dataList.get(i)
												.getItemNewNum())));
				}
				if (dataList.get(i).getCheckBoxState())
					if(dataList.get(i).getIssuit() != 2){
					    totalNumAll = totalNumAll
							+ Integer.parseInt(StatusString.getSign(dataList
									.get(i).getItemNewNum()));
					    totalNum = totalNum
					    		+ Integer.parseInt(StatusString.getSign(dataList.get(i)
					    				.getItemNewNum()));
					}else{
						//推荐套装
						 List<SuitListShopping> suititem = dataList.get(i).getSuitlist();
						 int itemNumThis  = 0;
						for(SuitListShopping item : suititem){
							itemNumThis   = itemNumThis+Integer.parseInt(StringUtil.isEmpty(item.getNumPerSuit())?"1":item.getNumPerSuit());
						}

						totalNumAll = totalNumAll
								+ (Integer.parseInt(StatusString.getSign(dataList
										.get(i).getItemNewNum()))) * itemNumThis;

						totalNum = totalNum
								+ (Integer.parseInt(StatusString.getSign(dataList.get(i)
										.getItemNewNum())) * itemNumThis);
					}
			}
		}
	}

	/**
	 *
	 * Describe:发送消息更新结算商品数量
	 *
	 * Date:2015-10-16
	 *
	 * Author:liuzhouliang
	 */
	public void updateDataMessage() {
		// 获取商品总价格
		getTotalPrice();
		Message messageAdd = Message.obtain();
		messageAdd.what = 1000;
		Bundle dataBundleAdd = new Bundle();
		long[] num = new long[2];
		num[0] = totalNum;
		num[1] = totalNumAll;
		dataBundleAdd.putLongArray("totalNum", num);
		dataBundleAdd.putFloat("totalPrice", totalPrice);
		messageAdd.setData(dataBundleAdd);
		messageHandler.sendMessage(messageAdd);

	}

	/**
	 *
	 * Describe:更新非编辑状态下缓存商品的数量
	 *
	 * Date:2015-10-29
	 *
	 * Author:liuzhouliang
	 */
	private void updateCacheProductsNotInEdit(String id, String num) {
		List<ShoppingCartSelectBean> cacheProduct = ShoppingCartActivity.selectProductNotInEdit;
		if (cacheProduct != null && cacheProduct.size() > 0) {
			for (ShoppingCartSelectBean product : cacheProduct) {
				if (id.equals(product.getAid()) || id.equals(product.getSid())) {
					product.setNum(num);
					break;
				}
			}
		}
	}

	/**
	 *
	 * Describe:添加购物车
	 *
	 * Date:2015-10-16
	 *
	 * Author:liuzhouliang shopid 购物车ID
	 */
	private void addShoppingCart(String aid, String num) {
		ApiHttpCilent.getInstance(mcontext.getApplicationContext()).addShoppingCart(mcontext, aid, num,
				0, new AddShoppingCartRequestCallBack());
	}

	// 隐藏等待框
	private void Dismess() {
		((Activity) mcontext).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (ApiHttpCilent.loading != null
						&& ApiHttpCilent.loading.isShowing())
					ApiHttpCilent.loading.dismiss();
			}
		});
	}

	/**
	 *
	 * Describe:加入购物车请求回调
	 *
	 * Date:2015-10-23
	 *
	 */

	public class AddShoppingCartRequestCallBack extends
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

//	// 动画效果
//	private void SetAnimation() {
//		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
//		ivanim.getLocationInWindow(start_location);// 这是获取购买文本框的在屏幕的X、Y坐标（这也是动画开始的坐标）
//		ImageView buyImg = new ImageView(mcontext);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 60);
//		buyImg.setLayoutParams(lp);
//		MyApplication.imageLoader.displayImage(dataList.get(clickPosition)
//				.getPic(), buyImg, MyApplication.options);
//		// setAnim(buyImg, start_location);
//		AnimationCartUtil.setAnim(mcontext, ivShoppingCart, buyImg,
//				start_location);
//	}

	/**
	 *
	 * Describe:加入购物车消息返回处理
	 *
	 * Date:2015-10-23
	 *
	 * Author:liuzhouliang
	 */
	public static class AddShoppingCartMessageHandler extends
			WeakHandler<NewShoppingCartFragmentAdapter> {

		public AddShoppingCartMessageHandler(
				NewShoppingCartFragmentAdapter reference) {
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

				if ("reduce".equals(getReference().clickTypeString)) {
					/**
					 * 减少操作==========================
					 */
					getReference().notifyDataSetChanged();
					/**
					 * 更新缓存中商品数量 ==2 是套装商品
					 */
					if (getReference().dataList.get(
							getReference().clickPosition).getIssuit() != 2)
						getReference().updateCacheProductsNotInEdit(
								getReference().dataList.get(
										getReference().clickPosition).getAid(),
								getReference().dataList.get(
										getReference().clickPosition)
										.getItemNewNum());
					else
						getReference().updateCacheProductsNotInEdit(
								getReference().dataList.get(
										getReference().clickPosition).getSid(),
								getReference().dataList.get(
										getReference().clickPosition)
										.getItemNewNum());
					getReference().updateDataMessage();

				}
				if ("add".equals(getReference().clickTypeString)) {
					/**
					 * 增加操作==================================
					 */
					getReference().notifyDataSetChanged();

					/**
					 * 更新缓存中商品数量
					 */
					if (getReference().dataList.get(
							getReference().clickPosition).getIssuit() != 2)
						getReference().updateCacheProductsNotInEdit(
								getReference().dataList.get(
										getReference().clickPosition).getAid(),
								getReference().dataList.get(
										getReference().clickPosition)
										.getItemNewNum());
					else
						getReference().updateCacheProductsNotInEdit(
								getReference().dataList.get(
										getReference().clickPosition).getSid(),
								getReference().dataList.get(
										getReference().clickPosition)
										.getItemNewNum());
					getReference().updateDataMessage();
				}
				if ("textchange".equals(getReference().clickTypeString)) {
					getReference().notifyDataSetChanged();
					getReference().dataList.get(getReference().clickPosition)
							.setItemNewNum(
									getReference().dataList.get(
											getReference().clickPosition)
											.getItemNewNum());
					getReference().updateDataMessage();
				}
				cancommit = true;
				break;
			case ConstantsUtil.HTTP_FAILE:
				/**
				 * 处理失败的数据
				 */
				getReference().isTextChange = true;
				String messageString = (String) msg.obj;
				if (messageString != null) {
					ToastUtil.showToast(getReference().mcontext, messageString);
					/**
					 * 处理失败数据
					 */
					getReference().cancommit = false;
				} else {
					ToastUtil.showToast(getReference().mcontext, "加入购物车失败");
				}
				break;
			}
		}
	}

}
