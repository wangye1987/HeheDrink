package com.heheys.ec.controller.activity;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;

/**
 * 作者：wangkui on 2017/1/12 18:56
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class H5Activity extends BaseActivity {

    private WebView tbsContent;

    @Override
    protected void onCreate() {
        setBaseContentView(R.layout.h5);
        initView();

    }

        private void initView() {
            tbsContent = (WebView)findViewById(R.id.tbsContent);
            WebSettings webSettings = tbsContent.getSettings();
            webSettings.setJavaScriptEnabled(true);
            tbsContent.requestFocusFromTouch();
            tbsContent.loadUrl("http://test.heheys.com/html/index/distrTask/list.html");
            tbsContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
    }

    @Override
    protected boolean hasTitle() {
        return true;
    }

    @Override
    protected void loadChildView() {

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
        return null;
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
