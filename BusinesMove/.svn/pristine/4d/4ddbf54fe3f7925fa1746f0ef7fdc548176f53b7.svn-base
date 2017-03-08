package com.heheys.ec.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyCustomView extends LinearLayout {

	private Scroller scroller;
	public MyCustomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		scroller = new Scroller(context);
	}

	public MyCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
	}

	public MyCustomView(Context context) {
		super(context);
		scroller = new Scroller(context);
	}

	private void smoothScrollTo(int fex,int fey){
		int scrollx = fex-scroller.getFinalX();
		int scrolly = fey-scroller.getFinalY();
		scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), scrollx, scrolly);
		invalidate();
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(scroller.computeScrollOffset()){
			smoothScrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		}
		super.computeScroll();
	}
	
}
