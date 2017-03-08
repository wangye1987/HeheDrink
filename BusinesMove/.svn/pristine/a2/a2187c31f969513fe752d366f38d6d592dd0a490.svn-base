package com.heheys.ec.view;

import com.heheys.ec.controller.listener.IScrollListener;

import android.widget.ListView;

/**
 * 
 * Describe:ListView控制器
 * 
 * Date:2015-9-29
 * 
 * Author:liuzhouliang
 */

public class ListViewController extends BaseViewScrollController {

	private ListView mListView = null;

	public ListViewController(ListView listView, IScrollListener iScrollListener) {
		mListView = listView;
		mListView.setOnScrollListener(this);
		this.iScrollListener = iScrollListener;
	}

}
