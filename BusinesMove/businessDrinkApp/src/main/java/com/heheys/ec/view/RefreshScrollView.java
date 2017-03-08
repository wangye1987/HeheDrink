package com.heheys.ec.view;

import com.heheys.ec.R;
import com.heheys.ec.utils.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * Describe:带刷新组件的SCROLLVIEW
 * 
 * Date:2015-10-29
 * 
 * Author:liuzhouliang
 */
public class RefreshScrollView extends ScrollView implements OnScrollListener {
	private String TAG = "自定义刷新控件";
	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	private LayoutInflater inflater;
	private LinearLayout headView;
	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;
	private int state;
	private boolean isBack;
	private OnRefreshListener refreshListener;
	private OnMoreListener loadMoreListener;
	private boolean isRefreshable;
	private ProgressBar moreProgressBar;
	private TextView loadMoreView;
	private View moreView;
	private ToTopCallBack listener;
	// 下来刷新提示语
	private TextView tvNote;
	// 加载进度框
	private ProgressBar refreshPb;
	private Context mContext;

	public RefreshScrollView(Context context) {
		super(context);
		mContext = context;
	}

	public RefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		setVerticalScrollBarEnabled(false);
		initView(mContext);
	}

	/**
	 * 
	 * Describe:初始化控件资源
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	private void initView(Context context) {
		LinearLayout layout = (LinearLayout) findViewById(R.id.head_panel);
		// 加载头部视图==================================
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(
				R.layout.scrollview_refresh_head, null);
		refreshPb = (ProgressBar) headView
				.findViewById(R.id.scrollview_refresh_head_pb);
		tvNote = (TextView) headView
				.findViewById(R.id.scrollview_refresh_head_tv);
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		// 设置间距隐藏头部视图
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
		// 刷新头部控件添加到容器中
		layout.addView(headView);
		state = DONE;
		isRefreshable = false;
	}

	/**
	 * 
	 * Describe:设置头部视图布局
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem,
			int visibleItemCount, int totalItemCount) {
		if (visibleItemCount == totalItemCount) {
			/**
			 * 数据未到一页
			 */
			if (listener != null) {
				listener.hide();
			}

		} else {
			if (visibleItemCount < totalItemCount) {
				/**
				 * 数据超过一页
				 */
				if (listener != null) {
					listener.show();
				}

			}
		}
		firstItemIndex = firstVisiableItem;
		if (visibleItemCount < totalItemCount
				&& (firstItemIndex + visibleItemCount) == totalItemCount
				&& loadMoreView != null) {
			if (getResources().getString(R.string.app_list_footer_more).equals(
					loadMoreView.getText())) {
				onLoad();
			}
		}
	}

	private void onLoad() {
		if (loadMoreListener != null) {
			moreProgressBar.setVisibility(View.VISIBLE);
			loadMoreView.setText(R.string.app_list_footer_loading);
			loadMoreListener.onLoadMore();
		}
	}

	private OnScrollListenerScrollview onScrollListener1;

	public void setOnScrollListener(OnScrollListenerScrollview onScrollListener1) {
		this.onScrollListener1 = onScrollListener1;
	}

	public interface OnScrollListenerScrollview {
		public void onScroll(int scrollY);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (onScrollListener1 != null) {
			onScrollListener1.onScroll(t);
		}
	}

	/**
	 * 滑动监听
	 */
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		switch (scrollState) {

		// 当不滚动时
		case OnScrollListener.SCROLL_STATE_IDLE:

			break;
		}
	}

	/**
	 * 
	 * Describe:设置刷新监听
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public void setonLoadListener(OnMoreListener loadListener) {
		this.loadMoreListener = loadListener;
	}

	/**
	 * 滑动事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				LogUtil.d(TAG, "ACTION_DOWN");
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:
				LogUtil.d(TAG, "ACTION_UP");
				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {

					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				LogUtil.d(TAG, "ACTION_MOVE");
				int tempY = (int) event.getY();

				if (!isRecored && firstItemIndex == 0) {
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {

					// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH) {

						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
						// 一下子推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
						// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (state == PULL_To_REFRESH) {

						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						} else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
					}

					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}

					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}

				}

				break;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 
	 * Describe:当状态改变时候，调用该方法，以更新界面
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			/**
			 * 松开刷新状态，显示动画
			 */
//			tvNote.setText("放手,是一种智慧...");
			tvNote.setText("松开刷新");
			refreshPb.setVisibility(View.VISIBLE);
			break;
		case PULL_To_REFRESH:
			/**
			 * 下拉刷新状态，显示默认图片
			 */
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
//				tvNote.setText("使劲...别勉强");
				tvNote.setText("下拉刷新");
			} else {
//				tvNote.setText("使劲...别勉强");
				tvNote.setText("下拉刷新");
			}
			refreshPb.setVisibility(View.VISIBLE);
			break;

		case REFRESHING:
			/**
			 * 正在刷新状态，显示圆形进度框
			 */
			headView.setPadding(0, 0, 0, 0);
			tvNote.setText("正在刷新...");
			refreshPb.setVisibility(View.VISIBLE);
			break;
		case DONE:
			/**
			 * 刷新完成状态，隐藏头部视图
			 */
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			tvNote.setText("下拉刷新");
//			tvNote.setText("下拉刷新");
			refreshPb.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 
	 * 
	 * @describe:刷新监听
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-12-15下午3:34:52
	 * 
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

	/**
	 * 
	 * Describe:加载更多监听
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	public interface OnMoreListener {
		public void onLoadMore();
	}

	/**
	 * 
	 * Describe:刷新完成
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	public void onRefreshComplete() {
		state = DONE;
		changeHeaderViewByState();
	}

	/**
	 * 
	 * 
	 * @describe:加载更多完成
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-12-15下午3:33:09
	 * 
	 */
	public void onLoadComplete() {
		moreView.setVisibility(View.GONE);
		moreProgressBar.setVisibility(View.GONE);
	}

	/**
	 * 
	 * 
	 * @describe:刷新回调
	 * 
	 * @author:LiuZhouLiang
	 * 
	 * @time:2014-12-15下午3:32:59
	 * 
	 */
	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	/**
	 * 
	 * Describe:没有更多数据回调
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	public void onNoData() {
		moreProgressBar.setVisibility(View.GONE);
		loadMoreView.setText("亲，已经到底了~~");
	}

	/**
	 * 
	 * Describe:设置到达顶部回调
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:liuzhouliang
	 */
	public void setTopCallBack(ToTopCallBack listener) {
		this.listener = listener;
	}

	public interface ToTopCallBack {
		public void show();

		public void hide();
	}
}
