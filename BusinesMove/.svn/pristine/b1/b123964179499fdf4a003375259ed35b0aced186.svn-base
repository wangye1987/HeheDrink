package com.heheys.ec.controller.activity;

import java.util.List;

import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebViewClient;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.umeng.analytics.MobclickAgent;

/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param creatatime：2016-3-11 上午10:25:31 代理商界面
 *  
 */
public class DrinkInfoActivity extends BaseActivity {

	private WebView webView;
	private Context mcontext;
	private String h5url,title;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		setBaseContentView(R.layout.drink_info_detail);
		initView();
		initDate();
	}

	
	private void initDate() {
		h5url = getIntent().getStringExtra("h5url");
		title = getIntent().getStringExtra("title");
		if(!StringUtil.isEmpty(title))
		tvTitleName.setText(title);
//		webView.loadDataWithBaseURL(null, h5url, "text/html", "utf-8", null);
		Session(h5url);
		webView.loadUrl(h5url);
		webView.setWebViewClient(new MyhttpClient());
	}

	private void Session(String linkurl){
		// 获取session 加载到url中
				List<Cookie> listcook = MyApplication.getInstance().getCookieStore().getCookies();
				if(listcook.size()>0){
				Cookie sessionCookie = listcook.get(0);
				if (sessionCookie != null) {
					CookieSyncManager.createInstance(baseActivity);
					CookieManager cookieManager = CookieManager.getInstance();
					  cookieManager.setAcceptCookie(true);  
					cookieManager.removeSessionCookie();  
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String cookieString = sessionCookie.getName() + "="
							+ sessionCookie.getValue() + "; domain="
							+ sessionCookie.getDomain()+"; path="
									+ sessionCookie.getPath();
					cookieManager.setCookie(linkurl, cookieString);
					CookieSyncManager.getInstance().sync();
				 }
				}
	}

	public class MyhttpClient extends WebViewClient{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	/* 
	 * 初始化视图组件
	 * */
	private void initView() {
		webView = (WebView) findViewById(R.id.wb_detaile);
		mcontext = this;
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setSupportZoom(true);
		setting.setDefaultTextEncodingName("UTF-8");
		
		webView.setClickable(true);
		setting.setUseWideViewPort(true);
		setting.setUseWideViewPort(true); 
		setting.setLoadWithOverviewMode(true);
		setting.setSupportZoom(true);
		setting.setBuiltInZoomControls(true);
		setting.setDefaultZoom(ZoomDensity.FAR);
		setting.setUseWideViewPort(true);
		setting.setLoadWithOverviewMode(true);
		setting.setJavaScriptCanOpenWindowsAutomatically(true);
		
//		webView.loadDataWithBaseURL(webView, data, mimeType, encoding, historyUrl);
		
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void loadChildView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setChildViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	 public void onResume() {
		    super.onResume();
		    MobclickAgent.onPageStart("PG_MY_INF");
		    MobclickAgent.onResume(this);       //统计时长
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPageEnd("PG_MY_INF");
		    MobclickAgent.onPause(this);
		}
}
