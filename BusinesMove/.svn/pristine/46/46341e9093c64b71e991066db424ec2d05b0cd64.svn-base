package com.heheys.ec.view;

import com.heheys.ec.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class UmengUpdateDialog extends Dialog {
	
	private static final String TAG = UmengUpdateDialog.class.getSimpleName(); 

	TextView mMessageTextView;

	Button mOkayButton;

	Button mCancelButton;

	CheckBox mUpdateIgnoreCheckBox;
	
	private final Delegate mDelegate;
	private final UmengDataSource mDataSource;
	
	public UmengUpdateDialog(Context context, Delegate delegate, UmengDataSource dataSource) {
		super(context, R.style.umeng_update_dialog_background_custom);
		mDelegate = delegate;
		mDataSource = dataSource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
		setCancelable(false);

		setContentView(R.layout.umeng_update_dialog);
	}
	
	@Override
    protected void onStart() {
	    super.onStart();
		viewDidload();
    }

	private void viewDidload() {
		String msg = mDataSource.getMessage();
		mMessageTextView.setText(msg);
		
		boolean isForceUpdate = mDataSource.isForceUpdate();
		if (isForceUpdate) {
			mCancelButton.setText(R.string.UMCLOSE);
			mUpdateIgnoreCheckBox.setVisibility(View.GONE);
		} else {
			mCancelButton.setText(R.string.UMNotNow);
			mUpdateIgnoreCheckBox.setVisibility(View.VISIBLE);
		}
	}

    void downLoadApk() {
		Log.i(TAG, "downloadApk");
		dismiss();
		mDelegate.downloadApk();
    }

	void cancelUpdate() {
		Log.i(TAG, "cancelUpdete");
		dismiss();

		if (mDataSource.isForceUpdate()) {
			mDelegate.cancelUpdate();
		}
	}
    
	void onIgonreCheckUpdate(boolean checked) {
		Log.i(TAG, "onIgonreCheckUpdate");
		mUpdateIgnoreCheckBox.setChecked(checked);
		mDelegate.ignoreCheckUpdate(checked);
	}

	public interface Delegate {
		void downloadApk();
		
		void cancelUpdate();
		
		void ignoreCheckUpdate(boolean isIgonre);
	}
	
	public interface UmengDataSource {
		boolean isForceUpdate();
		boolean isIgonreUpdate();
		String getMessage();
	}
}
