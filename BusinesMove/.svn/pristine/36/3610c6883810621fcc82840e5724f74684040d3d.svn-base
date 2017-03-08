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

public class RefreshOrLoadMoreGridView extends LinearLayout {
	private PullToRefreshView pullToRefreshView = null;
	private GridView gridView = null;

	public GridView getGridView() {
		return gridView;
	}

	public void setGridView(GridView gridView) {
		this.gridView = gridView;
	}

	public PullToRefreshView getPullToRefreshView() {
		return pullToRefreshView;
	}

	public void setPullToRefreshView(PullToRefreshView pullToRefreshView) {
		this.pullToRefreshView = pullToRefreshView;
	}

	Context mContext = null;

	public RefreshOrLoadMoreGridView(Context context) {
		super(context);
		mContext = context;
	}

	public RefreshOrLoadMoreGridView(Context context,
			AttributeSet attributeSet) {
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
		View layout = inflater.inflate(R.layout.grid_loadmore_parent,
				null);
		// 添加布局参数
		this.addView(layout, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		pullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		gridView = (GridView) findViewById(R.id.gvIndexData);
		// 设置周围没有
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}
}
