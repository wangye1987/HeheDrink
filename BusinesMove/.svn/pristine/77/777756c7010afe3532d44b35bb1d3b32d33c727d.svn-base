package com.heheys.ec.view;

import com.heheys.ec.R;
import com.heheys.ec.view.PullToRefreshView.OnFooterLoadMoreListener;
import com.heheys.ec.view.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RefreshOrLoadMoreListViewParent extends LinearLayout implements
		OnHeaderRefreshListener, OnFooterLoadMoreListener {
	Context mContext = null;
	RefreshOrLoadMoreListView refreshOrLoadMoreGridView = null;
	public PullToRefreshView  listPullToRefreshView = null;
	private ListPullOrRefreshListener commViewListener = null;

	public ListView gridView = null;

	public ListView getListView() {
		return gridView;
	}

	public void setGridView(ListView gridView) {
		this.gridView = gridView;
	}

	public void setRefreshOrLoadListener(
			ListPullOrRefreshListener commViewListener) {
		this.commViewListener = commViewListener;
	}

	public RefreshOrLoadMoreListViewParent(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public RefreshOrLoadMoreListViewParent(Context context,
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
		View layout = inflater.inflate(R.layout.list_refresh_loadmore_parent,
				null);
		this.addView(layout);
		refreshOrLoadMoreGridView = (RefreshOrLoadMoreListView) findViewById(R.id.lmgriviewpullview);
		listPullToRefreshView = refreshOrLoadMoreGridView.getPullToRefreshView();
		listPullToRefreshView.setOnHeaderRefreshListener(this);
		listPullToRefreshView.setOnFooterRefreshListener(this);
		gridView = refreshOrLoadMoreGridView.getGridView();

	}

	/**
	 * 执行数据
	 */
	public void initData() {
		listPullToRefreshView.headerRefreshing();
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
		listPullToRefreshView.setEnablePullTorefresh(enablePullTorefresh);
	}

	/**
	 * 是否开启上拉加载更多功能
	 * 
	 * @param enableloadmore
	 *            true为开启 false 禁用下拉刷新功能
	 */
	public void setPullTpFootViewLoadMoreDataEnbale(boolean enableloadmore) {
		listPullToRefreshView.setEnablePullLoadMoreDataStatus(enableloadmore);
		setIsLoadMoreData(enableloadmore);
	}

	public interface ListPullOrRefreshListener {
		public void listOnRefresh();

		public void listonLoadMore();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		commViewListener.listonLoadMore();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		commViewListener.listOnRefresh();

	}
}
