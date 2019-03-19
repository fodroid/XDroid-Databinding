package cn.droidlover.xdroid.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import cn.droidlover.xdroid.event.BusFactory;

/**
 * Created by wanglei on 2016/11/27.
 */

public abstract class XActivity<V extends ViewDataBinding> extends RxAppCompatActivity implements UiCallback {
    protected Activity context;
    protected UiDelegate uiDelegate;
    private V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;

        if (getLayoutId() > 0) {
            bindUI(getLayoutId());
        }
        setListener();
        initData(savedInstanceState);
    }

    protected V getBinding() {
        return binding;
    }

    public void bindUI(@LayoutRes int layoutResID) {
        binding = DataBindingUtil.setContentView(context, layoutResID);
    }


    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(this);
        }
        return uiDelegate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUiDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getUiDelegate().pause();
    }


    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus())
            BusFactory.getBus().unregister(this);
        getUiDelegate().destory();
    }
}
