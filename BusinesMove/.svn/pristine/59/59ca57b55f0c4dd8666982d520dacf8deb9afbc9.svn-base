package com.heheys.ec.view;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuickAlphabeticBar extends ImageButton {

	public static final String TAG = "QuickAlphabeticBar";
	public static final int DEFAULT_SCREEN_HEIGHT = 800;
	public static final int DEFAULT_TEXT_SIZE = 20;
	private TextView mDialogText;
	private Handler mHandler;
	private ListView mList;
	private float mHight;
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	private HashMap<String, Integer> alphaIndexer;
	private float centerXY[] = null;
	// 每个字母的高度
	private int singleHeight;
	private int height;
	// 视图的角度
	private float arc = 0;
	// 圆形半径
	private float radius;
	private float normalWidth = 30;
	private float selectedWidth = 30;
	private boolean isselectedState = false;
	private int textSize;
	private int startPos = -1;
	private int endPos = -1;
	private float measureTextSize = -1;
	Paint paint = new Paint();
	boolean showBkg = false;
	int choose = -1;

	public QuickAlphabeticBar(Context context) {
		super(context);
		init(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		textSize = 10;
	}

	public void setTextView(TextView mTextDialog) {
		this.mDialogText = mTextDialog;
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}

	public void setListView(ListView mList) {
		this.mList = mList;
	}

	public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mHight = getHeight();
		singleHeight = height / letters.length;
		height = getHeight();
		getStartAndEndPosFromArg();
		/**
		 * 绘制文本位置
		 */
		boolean flag = false;
		for (int i = 0; i < letters.length; i++) {
			paint.setColor(getResources().getColor(android.R.color.black));
			paint.setTextSize(textSize);
			Typeface font = Typeface.create(Typeface.SANS_SERIF,
					Typeface.NORMAL);
			paint.setTypeface(font);
			paint.setAntiAlias(true);

			measureTextSize = paint.measureText(letters[i]);

			float xPos, yPos;
			if (isselectedState) {
				xPos = selectedWidth - normalWidth / 2 - measureTextSize / 2;
			} else {
				xPos = normalWidth / 2 - measureTextSize / 2;
			}

			yPos = singleHeight * i + singleHeight;

			if (i == choose) {
				paint.setColor(Color.parseColor("#00BFFF"));
				paint.setFakeBoldText(true);
			}

			if ((i >= startPos && i <= endPos) && choose != -1
					&& isselectedState) {
				/**
				 * 当前选中视图X,Y坐标
				 */
				if (centerXY == null) {
					centerXY = new float[2];
					centerXY[0] = selectedWidth - normalWidth / 2
							- measureTextSize / 2;
					centerXY[1] = singleHeight * choose + singleHeight;
				}

				if (!flag) {
					getStartPosFromArg(startPos);
					flag = true;
				}

				float[] arcXY = getXYFormArg();
				xPos = arcXY[0];
				yPos = arcXY[1];

				arc = (float) (arc - 22.5);
				/**
				 * 获取半圆位置上文本字体的大小
				 */
				int size = getArgLetterTextSize(i);
				paint.setTextSize(size);
			}
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}
		centerXY = null;
	}

	/**
	 * 
	 * Describe:获取起始和最后位置
	 * 
	 * Date:2015-11-4
	 * 
	 * Author:liuzhouliang
	 */
	private void getStartAndEndPosFromArg() {
		if (choose != -1) {
			if (choose <= 3) {
				startPos = 0;
			} else {
				startPos = choose - 3;
			}

			if (choose - letters.length + 4 <= 0) {
				endPos = choose + 3;
			} else {
				endPos = letters.length - 1;
			}
		}
	}

	/**
	 * 
	 * Describe: 设置半圆上各个文字的角度
	 * 
	 * Date:2015-11-4
	 * 
	 * Author:liuzhouliang
	 */
	private void getStartPosFromArg(int startPos) {

		if (startPos == choose) {
			arc = 180;
		} else if (startPos + 1 == choose) {
			arc = (float) 202.5;
		} else if (startPos + 2 == choose) {
			arc = 225;
		} else if (startPos + 3 == choose) {
			arc = (float) 247.5;
		}
	}

	/**
	 * 
	 * Describe:重置各个文本字体大小
	 * 
	 * Date:2015-11-4
	 * 
	 * Author:liuzhouliang
	 */
	private int getArgLetterTextSize(int i) {
		if (i == choose) {
			return textSize + 8;
		} else if (i + 1 == choose || choose + 1 == i) {
			return textSize + 6;
		} else if (i + 2 == choose || choose + 2 == i) {
			return textSize + 4;
		} else if (i + 3 == choose || choose + 3 == i) {
			return textSize + 4;
		}
		return textSize;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float y = event.getY();
		float x = event.getX();
		final int oldChoose = choose;

		radius = 8 * singleHeight / 2;
		// 触摸视图索引位置
		int selectIndex = (int) (y / (mHight / letters.length));
		if (selectIndex > -1 && selectIndex < letters.length) {
			String key = letters[selectIndex];
			if (alphaIndexer.containsKey(key)) {
				int pos = alphaIndexer.get(key);
				if (mList.getHeaderViewsCount() > 0) {
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				} else {
					this.mList.setSelectionFromTop(pos, 0);
				}
			}

			mDialogText.setText(letters[selectIndex]);
			arc = 0;

			if (!isselectedState) {
				isselectedState = true;
				setLayoutParams((int) selectedWidth);

			}
		}

		switch (action) {
		case MotionEvent.ACTION_DOWN:

			showBkg = true;
			/**
			 * 刷新选中索引的位置
			 */
			if (oldChoose != selectIndex) {
				if (selectIndex >= 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			/**
			 * 显示提示文本
			 */
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (x < selectedWidth * 0.5 && isselectedState) {
				reseAlphabeticBar();
				return super.onTouchEvent(event);
			}
			/**
			 * 刷新视图位置
			 */
			if (oldChoose != selectIndex) {
				if (selectIndex >= 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			reseAlphabeticBar();
			break;
		case MotionEvent.ACTION_CANCEL:
			reseAlphabeticBar();
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}
   /**
    * 
    * Describe:
    *
    * Date:2015-11-4
    *
    * Author:liuzhouliang
    */
	private void reseAlphabeticBar() {
		centerXY = null;
		showBkg = false;
		choose = -1;
		isselectedState = false;
		arc = 0;
		setLayoutParams((int) normalWidth);
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (mDialogText != null
							&& mDialogText.getVisibility() == View.VISIBLE) {
						mDialogText.setVisibility(INVISIBLE);
						invalidate();
					}
				}
			});
		}
	}

	private void setLayoutParams(int width) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// lp.addRule(RelativeLayout.BELOW, R.id.acbuwa_topbar);
		setLayoutParams(lp);
	}

	private float[] getXYFormArg() {
		float[] xy = new float[2];
		xy[0] = (float) (centerXY[0] + radius * Math.cos(arc * Math.PI / 180));
		xy[1] = (float) (centerXY[1] + radius * Math.sin(arc * Math.PI / 180));
		return xy;
	}

}
