package com.heheys.ec.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class SoftEditText extends EditText {
	private List<TextWatcher> watchers = new ArrayList<TextWatcher>();

	public SoftEditText(Context context) {
		super(context);
		init();
	}

	public SoftEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SoftEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setInputType(EditorInfo.TYPE_CLASS_PHONE);
	}

	@Override
	public void addTextChangedListener(TextWatcher watcher) {
		watchers.add(watcher);
		super.addTextChangedListener(watcher);
	}

	public void removeTextChangedListener() {
		for (int i = 0; i < watchers.size(); i++) {
			removeTextChangedListener(watchers.get(i));
		}
	}
}
