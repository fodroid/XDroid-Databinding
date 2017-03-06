package cn.droidlover.xdroid.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroid.demo.R;
import cn.droidlover.xdroid.demo.databinding.ActivityWebBinding;
import cn.droidlover.xdroid.demo.kit.AppKit;
import cn.droidlover.xdroid.router.Router;
import cn.droidlover.xstatecontroller.XStateController;

/**
 * Created by wanglei on 2016/12/11.
 */

public class WebActivity extends XActivity<ActivityWebBinding> {


    String url;
    String desc;

    public static final String PARAM_URL = "url";
    public static final String PARAM_DESC = "desc";


    @Override
    public void initData(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(PARAM_URL);
        desc = getIntent().getStringExtra(PARAM_DESC);


        initToolbar();
        initContentLayout();
        initRefreshLayout();
        initWebView();
    }


    @Override
    public void setListener() {

    }

    private void initContentLayout() {
        getBinding().contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
    }

    private void initRefreshLayout() {
        getBinding().swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        getBinding().swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBinding().webView.loadUrl(url);
            }
        });

    }

    private void initWebView() {
        getBinding().webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (getBinding().contentLayout != null)
                        getBinding().contentLayout.showContent();
                    if (getBinding().webView != null)
                        url = getBinding().webView.getUrl();
                } else {
                    if (getBinding().contentLayout != null)
                        getBinding().contentLayout.showLoading();
                }
            }
        });
        getBinding().webView.setWebViewClient(new WebViewClient());
        getBinding().webView.getSettings().setBuiltInZoomControls(true);
        getBinding().webView.getSettings().setJavaScriptEnabled(true);
        getBinding().webView.getSettings().setDomStorageEnabled(true);
        getBinding().webView.getSettings().setDatabaseEnabled(true);
        getBinding().webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        getBinding().webView.getSettings().setAppCacheEnabled(true);

        getBinding().webView.loadUrl(url);
    }

    private void initToolbar() {
        setSupportActionBar(getBinding().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle(desc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                AppKit.shareText(this, getBinding().webView.getTitle() + " " + getBinding().webView.getUrl() + " " +
                        "来自「XDroid」");
                break;
            case R.id.action_refresh:
                getBinding().webView.reload();
                break;
            case R.id.action_copy:
                AppKit.copyToClipBoard(this, getBinding().webView.getUrl());
                break;
            case R.id.action_open_in_browser:
                AppKit.openInBrowser(this, getBinding().webView.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (getBinding().webView.canGoBack()) {
                    getBinding().webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getBinding().webView != null) getBinding().webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getBinding().webView != null) getBinding().webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getBinding().webView != null) {
            ViewGroup parent = (ViewGroup) getBinding().webView.getParent();
            if (parent != null) {
                parent.removeView(getBinding().webView);
            }
            getBinding().webView.removeAllViews();
            getBinding().webView.destroy();
        }
    }

    public static void launch(Activity activity, String url, String desc) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putString(PARAM_URL, url)
                .putString(PARAM_DESC, desc)
                .launch();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

}
