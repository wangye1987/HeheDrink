package com.heheys.ec.view;

import com.heheys.ec.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RefreshOrLoadMoreListView extends LinearLayout {
	private PullToRefreshView pullToRefreshView = null;
	private ListView gridView = null;

	public ListView getGridView() {
		return gridView;
	}

	public void setGridView(ListView gridView) {
		this.gridView = gridView;
	}

	public PullToRefreshView getPullToRefreshView() {
		return pullToRefreshView;
	}

	public void setPullToRefreshView(PullToRefreshView pullToRefreshView) {
		this.pullToRefreshView = pullToRefreshView;
	}

	Context mContext = null;

	public RefreshOrLoadMoreListView(Context context) {
		super(context);
		mContext = context;
	}

	public RefreshOrLoadMoreListView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		mContext = context;
		initWidget(context);
	}

	/**
	 * 初始化布局
	 * 
	 * @param mcontext
	 */
	public void initWidget(Context mcontext) {
		LayoutInflater inflater = (LayoutInflater) mcontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.list_refresh_loadmore_child, null);
		// 添加布局参数
		this.addView(layout, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		pullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		gridView = (ListView) findViewById(R.id.gvIndexData);
		// 设置周围没有
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}
}
