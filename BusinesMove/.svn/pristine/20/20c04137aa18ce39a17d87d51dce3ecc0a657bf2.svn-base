package com.heheys.ec.view;

import com.heheys.ec.controller.listener.IScrollListener;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * Describe:分页控制器
 * 
 * Date:2015-9-29
 * 
 * Author:liuzhouliang
 */
public class BaseViewScrollController implements OnScrollListener {
	// 滑动监听器
	protected IScrollListener iScrollListener = null;
	// 是否滑动到过底部
	protected boolean hasScrollBottom = false;
	// 标记是否是显示最后一行
	protected boolean isLastRow = false;
	// 是否还有数据
	protected boolean isEnd = false;
	// 是否正在加载数据
	protected boolean isLoadingData = false;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 判断是否滑动到了最后一行
		if (firstVisibleItem + visibleItemCount == totalItemCount
				&& totalItemCount > 0) {
			isLastRow = true;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 滑动到最后一行并且停止滑动
		if (!isEnd
				&& !isLoadingData
				&& isLastRow
				&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			iScrollListener.onScrollToBottom();
			hasScrollBottom = true;
			isLastRow = false;
		}

		// 滑动到最后一页并且停止滑动
		if (isEnd
				&& !isLoadingData
				&& isLastRow
				&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			iScrollListener.onScrollToLastPage();
			hasScrollBottom = true;
			isLastRow = false;
		}
	}

	/**
	 * 
	 * Describe:获取是否滑动到过底部
	 * 
	 * Date:2015-9-29
	 * 
	 * Author:liuzhouliang
	 */
	public boolean hasScrollToBottom() {
		return hasScrollBottom;
	}

	/**
	 * 
	 * Describe:true 已经最后一页，没有加载数据了;false 还有数据可加载
	 * 
	 * Date:2015-9-29
	 * 
	 * Author:liuzhouliang
	 */
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	/**
	 * 
	 * Describe:获取数据是否加载完毕
	 * 
	 * Date:2015-9-29
	 * 
	 * Author:liuzhouliang
	 */
	public boolean getEnd() {
		return isEnd;
	}

	/**
	 * 
	 * Describe:设置是否正在加载数据
	 * 
	 * Date:2015-9-29
	 * 
	 * Author:liuzhouliang
	 */
	public void setLoadingData(boolean isLoadingData) {
		this.isLoadingData = isLoadingData;
	}

}
