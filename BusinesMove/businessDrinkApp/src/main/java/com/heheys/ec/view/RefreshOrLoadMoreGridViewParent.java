package com.heheys.ec.view;

import com.heheys.ec.R;
import com.heheys.ec.view.PullToRefreshView.OnFooterLoadMoreListener;
import com.heheys.ec.view.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

public class RefreshOrLoadMoreGridViewParent extends LinearLayout implements
		OnHeaderRefreshListener, OnFooterLoadMoreListener {
	Context mContext = null;
	RefreshOrLoadMoreGridView refreshOrLoadMoreGridView = null;
	public PullToRefreshView gridPullToRefreshView = null;
	private GridPullOrRefreshListener commViewListener = null;

	public GridView gridView = null;

	public GridView getGridView() {
		return gridView;
	}

	public void setGridView(GridView gridView) {
		this.gridView = gridView;
	}

	public void setRefreshOrLoadListener(
			GridPullOrRefreshListener commViewListener) {
		this.commViewListener = commViewListener;
	}

	public RefreshOrLoadMoreGridViewParent(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public RefreshOrLoadMoreGridViewParent(Context context,
			AttributeSet attributeSet) {
		super(context, attributeSet);
		mContext = context;
		initView(context);
	}

	/**
	 * 
	 * Describe:初始化控件
	 * 
	 * Date:2015-9-30
	 * 
	 * Author:liuzhouliang
	 */
	public void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.loadmore_grid_layout, null);
		this.addView(layout);
		refreshOrLoadMoreGridView = (RefreshOrLoadMoreGridView) findViewById(R.id.lmgriviewpullview);
		gridPullToRefreshView = refreshOrLoadMoreGridView.getPullToRefreshView();
		gridPullToRefreshView.setOnHeaderRefreshListener(this);
		gridPullToRefreshView.setOnFooterRefreshListener(this);
		gridView = refreshOrLoadMoreGridView.getGridView();

	}

	/**
	 * 执行数据
	 */
	public void initData() {
		gridPullToRefreshView.headerRefreshing();
	}

	/**
	 * 设置没有数据时候加载没有数据的布局
	 */
	public void setNoDataShowLayout(boolean hasdatalayoutstatus) {
	}

	/**
	 * 设置没有数据的默认图片
	 * 
	 * @param id
	 */
	public void setNodataShowNoIcon(int id) {
	}

	/**
	 * 设置没有数据的提示语
	 * 
	 * @param msg
	 */
	public void setNodateMsg(String msg) {
	}

	/**
	 * 设置是否开启加载更多功能
	 * 
	 * @param isloadmorestatus
	 *            true开启加载更多功能 false禁止加载更多功能 默认开启
	 */
	public void setIsLoadMoreData(boolean isloadmorestatus) {
		setPullTpFootViewLoadMoreDataEnbale(isloadmorestatus);
	}

	/**
	 * 是否开启下拉刷新功能 默认开启
	 * 
	 * @param enablePullTorefresh
	 *            true为开启 false 禁用下拉刷新功能
	 */
	public void setPullToreshareEnable(boolean enablePullTorefresh) {
		gridPullToRefreshView.setEnablePullTorefresh(enablePullTorefresh);
	}

	/**
	 * 是否开启上拉加载更多功能
	 * 
	 * @param enableloadmore
	 *            true为开启 false 禁用下拉刷新功能
	 */
	public void setPullTpFootViewLoadMoreDataEnbale(boolean enableloadmore) {
		gridPullToRefreshView.setEnablePullLoadMoreDataStatus(enableloadmore);
		setIsLoadMoreData(enableloadmore);
	}

	public interface GridPullOrRefreshListener {
		public void gridOnRefresh();

		public void gridonLoadMore();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		commViewListener.gridonLoadMore();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		commViewListener.gridOnRefresh();

	}
}
