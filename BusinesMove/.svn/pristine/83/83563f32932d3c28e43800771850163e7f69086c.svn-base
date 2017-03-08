package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;

/**
 * Describe:活动页面
 * 
 * Date:2015-11-10
 * 
 * Author:liuzhouliang
 */
public class ActivityPage extends BaseActivity {
	private WebView wv;
	private WebSettings mWebSettings;
	private String urlString;

	@Override
	protected void onCreate() {
		setBaseContentView(R.layout.activity_page);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadChildView() {
		wv = (WebView) findViewById(R.id.activity_page_wv);
		initData();
		initWebView();
	}

	private void initData() {
		if (null != wv) {
			mWebSettings = wv.getSettings();
		}
		urlString = getIntent().getStringExtra("url");
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initWebView() {
		if (null != mWebSettings) {
			mWebSettings.setUseWideViewPort(true);
			mWebSettings.setLoadWithOverviewMode(true);
			mWebSettings.setLoadsImagesAutomatically(true);
			mWebSettings.setPluginState(WebSettings.PluginState.ON);
			mWebSettings.setLightTouchEnabled(true);
			mWebSettings.setSaveFormData(false);
			mWebSettings.setSavePassword(false);
			mWebSettings.setNeedInitialFocus(false);
			mWebSettings.setSupportMultipleWindows(false);
			mWebSettings.setAppCacheEnabled(false);
			mWebSettings.setDatabaseEnabled(false);
			mWebSettings.setDomStorageEnabled(false);
			mWebSettings.setGeolocationEnabled(false);
			mWebSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			mWebSettings.setRenderPriority(RenderPriority.HIGH);
			mWebSettings.setBlockNetworkImage(false);
			mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
			mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
			wv.loadUrl(urlString);
			// 启用javascript
			wv.getSettings().setJavaScriptEnabled(true);
			wv.addJavascriptInterface(this, "jsObj");
			wv.setWebViewClient(new WebViewClient() {

				@Override
				public void onLoadResource(WebView view, String url) {
					super.onLoadResource(view, url);
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
					Uri uri = Uri.parse(url);
					if (uri != null && uri.toString().contains("tel:")) {
						Uri phoneUri = Uri.parse("tel:"
								+ uri.toString().substring(4));
						// 直接播出电话
						Intent call = new Intent(Intent.ACTION_CALL, phoneUri);
						startActivity(call);

					}
					return true;
				}
			});
			wv.setWebViewClient(new WebViewClient() {

				@Override
				public void onPageFinished(WebView view, String url) {
					// TODO Auto-generated method stub

					super.onPageFinished(view, url);
					// setViewListener();
				}

				@Override
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					// TODO Auto-generated method stub

					super.onPageStarted(view, url, favicon);
				}

			});

		}
	}

	@Override
	protected void getNetData() {
	}

	@Override
	protected void reloadCallback() {
	}

	@Override
	protected void setChildViewListener() {
	}

	@Override
	protected String setTitleName() {
		return "活动";
	}

	@Override
	protected String setRightText() {
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		return 0;
	}

}
