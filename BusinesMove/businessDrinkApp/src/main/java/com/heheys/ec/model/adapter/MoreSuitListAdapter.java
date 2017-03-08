package com.heheys.ec.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.MoreSuitActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.view.ScrollViewListView;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.MoreSuitBean.SuitInfoItem;
import com.heheys.ec.model.dataBean.MoreSuitBean.SuitList;
import com.heheys.ec.utils.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

import static com.heheys.ec.R.id.tv_more;

/**
 * @author wangkui
 *
 * 更多套装适配器
 */
public class MoreSuitListAdapter extends BaseListAdapter<SuitList> {

	private Context mContext;
	private TextView tv_title;
	private TextView tv_price;
	private TextView tv_save;
//	private ImageView tv_more;
	private ScrollViewListView lv_more;
	private TextView add_shoppingcart;
	private View view_line;
	private LinearLayout linear_sc;
	private LinearLayout linear_arrow;
	private SuitList moreSuitList;//更多套装列表数据
	private ImageView iv_thimal,iv_jia;
	private ArrayList<SuitInfoItem> listItem;
	private HorizontalScrollView scroll;
	private AddShopingCallback Suitcallback;
	private ImageView add_suit;
//	private LinearLayout listView_item_linear;
	private MoreSuitActivity.SuitListHandler mHandler;
    private int clickPosition = -1;
	private  MoreItemAdapter mItemAdapter;
	public MoreSuitListAdapter(ArrayList<SuitList> data, Context context,AddShopingCallback Suitcallback,MoreSuitActivity.SuitListHandler mHandler) {
		super(data, context);
		this.mContext = context;
		this.Suitcallback = Suitcallback;
		this.mHandler = mHandler;
	}

	public void setPosition(int clickPosition){
		this.clickPosition = clickPosition;
		notifyDataSetChanged();
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		moreSuitList = dataList.get(position);
		if(convertView == null){
			convertView = baseInflater.inflate(R.layout.more_suit_item, parent, false);
		}
		tv_title = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_title);
		tv_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_price);
		tv_save = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_save);
		final ImageView iv_more = (ImageView) ViewHolderUtil.getItemView(convertView, tv_more);
		add_suit = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.add_suit);
		lv_more = (ScrollViewListView) ViewHolderUtil.getItemView(convertView, R.id.lv_more);
		final LinearLayout listView_item_linear = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.listView_item_linear);
		add_shoppingcart = (TextView) ViewHolderUtil.getItemView(convertView, R.id.add_shoppingcart);
		view_line =  ViewHolderUtil.getItemView(convertView, R.id.view_line);
		linear_sc = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_sc);
		final LinearLayout linear_add = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_add);
		linear_arrow = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.linear_arrow);
		scroll = (HorizontalScrollView) ViewHolderUtil.getItemView(convertView, R.id.scroll);
		if(moreSuitList != null){
			/**
			 * 展开子项listview
			 * */
			listItem = moreSuitList.getListItem();
			if(listItem != null && listItem.size() > 0) {
				mItemAdapter = new MoreItemAdapter(listItem, mContext);
				lv_more.setAdapter(mItemAdapter);
				lv_more.requestLayout();
			}
		 linear_arrow.setOnClickListener(null);
			linear_arrow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(listView_item_linear.getVisibility() == View.VISIBLE){
						iv_more.setImageResource(R.drawable.down_suit);
						listView_item_linear.setVisibility(View.GONE);
						linear_add.setVisibility(View.VISIBLE);
					}else{
						linear_add.setVisibility(View.GONE);
						iv_more.setImageResource(R.drawable.up_suit);
						listView_item_linear.setVisibility(View.VISIBLE);
					}
				}
			});
		 tv_title.setText(StringUtil.isEmpty(moreSuitList.getSuitTile())?"":moreSuitList.getSuitTile());
		 ViewUtil.setActivityPrice(tv_price, StringUtil.isEmpty(moreSuitList.getSuitPrice())?"0":moreSuitList.getSuitPrice());
		 tv_save.setText("立省"+(StringUtil.isEmpty(moreSuitList.getSuitSave())?"":moreSuitList.getSuitSave())+"元");
		 ArrayList<SuitInfoItem> picList =  moreSuitList.getListItem();
		 //缩列图
		 linear_sc.removeAllViews();
		 int size = picList.size();
		 for(int i = 0;i<size;i++ ){
			 View view = LinearLayout.inflate(mContext, R.layout.item_image_suit, null);
			 iv_thimal = (ImageView)view.findViewById(R.id.iv_thimal);
			 iv_jia = (ImageView)view.findViewById(R.id.iv_jia);
			 MyApplication.imageLoader.displayImage(picList.get(i).getPic(), iv_thimal,
						MyApplication.options);
			 if(picList.get(picList.size()-1).equals(picList.get(i)))
				 iv_jia.setVisibility(View.GONE);
			 view.setOnClickListener(new Todetaile(picList.get(i).getId()));
			 linear_sc.addView(view);
		 }
		}
		/**
		 * 添加主商品购物车
		 * */
		add_shoppingcart.setOnClickListener(new addShoppingClick(moreSuitList.getSuitId(),Suitcallback));
		/**
		* 添加套装到购物车
		* */
		add_suit.setOnClickListener(new addShoppingClick(moreSuitList.getSuitId(),Suitcallback));

//		Utils.setListViewHeightBasedOnChildren(lv_more);
//		Utils.setViewHeightBasedOnChildren(convertView);
		return convertView;
	}
	
	
	public class Todetaile implements OnClickListener{
		String mid;
		public Todetaile(String id){
			mid = id;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//到商品详情
			Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
			intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, mid);
			StartActivityUtil.startActivity((Activity) mcontext, intent);
		}
		
	}
	
	public class addShoppingClick implements OnClickListener{
		  String id;//套装ID
		  AddShopingCallback Suitcallback;
		addShoppingClick(String id,AddShopingCallback Suitcallback){
			this.id = id;
			this.Suitcallback = Suitcallback;
		}

		@Override
		public void onClick(View v) {
			Suitcallback.getId(id);
		}
	}
	public class ListClick implements OnClickListener{
		private LinearLayout listView_item_linear;
		private LinearLayout linear_add;
		private ImageView tv_more;

		public ListClick(LinearLayout listView_item_linear,LinearLayout linear_add,
				ImageView tv_more){
			this.tv_more = tv_more;
			this.listView_item_linear = listView_item_linear;
			this.linear_add = linear_add;
		}
		@Override
		public void onClick(View v) {
			if(listView_item_linear.getVisibility() == View.VISIBLE){
				tv_more.setImageResource(R.drawable.down_suit);
				listView_item_linear.setVisibility(View.GONE);
				linear_add.setVisibility(View.VISIBLE);
			}else{
				tv_more.setImageResource(R.drawable.up_suit);
				listView_item_linear.setVisibility(View.VISIBLE);
				linear_add.setVisibility(View.GONE);
			}
		}
	}
	
	/* 
	 * item
	 * */
	class MoreItemAdapter extends BaseListAdapter<SuitInfoItem>{
		ImageView iv_jia;
		TextView tv_name,tv_one_unit,tv_one_unit_price,tv_one_unit_units;
		List<SuitInfoItem> data;
		Context mthisContext ;
		private LinearLayout rv_detaile;
		public MoreItemAdapter(List<SuitInfoItem> data, Context context) {
			super(data, context);
			this.mthisContext = context;
		}

		@Override
		public View bindView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView= baseInflater.inflate(R.layout.image_view,parent, false);
			}
			final SuitInfoItem  itemData= dataList.get(position);
			rv_detaile = (LinearLayout) ViewHolderUtil.getItemView(convertView, R.id.rv_detaile);
			 iv_jia = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_tc);
			 tv_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_name);
			 tv_one_unit = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_one_unit);
			 tv_one_unit_price = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_one_unit_price);
			 tv_one_unit_units = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_one_unit_units);
			 tv_name.setText(itemData.getName());
			 tv_one_unit.setText(itemData.getNumPerSuit()+itemData.getUnit()+"/套");
			 tv_one_unit_price.setText(itemData.getPriceInfo());
			 tv_one_unit_units.setText("/"+itemData.getUnit());
			 MyApplication.imageLoader.displayImage(itemData.getPic(), iv_jia,
						MyApplication.options);
			 rv_detaile.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//到商品详情
					Intent intent = new Intent(mcontext, NewProductDetailActivity.class);
					intent.putExtra(ConstantsUtil.PRODUCT_ID_KEY, itemData.getId());
					StartActivityUtil.startActivity((Activity) mcontext, intent);
				}
			});
			return convertView;
		}
	}

	public interface AddShopingCallback{
		void getId(String id);
	}
}
