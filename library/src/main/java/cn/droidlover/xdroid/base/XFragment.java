package cn.droidlover.xdroid.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import cn.droidlover.xdroid.event.BusFactory;

/**
 * Created by wanglei on 2016/11/27.
 */

public abstract class XFragment<V extends ViewDataBinding> extends RxFragment implements UiCallback {
    protected View rootView;
    protected LayoutInflater layoutInflater;
    protected Activity context;
    protected UiDelegate uiDelegate;
    private V binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null);
            bindUI(rootView);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }

        return rootView;
    }

    protected V getBinding() {
        return binding;
    }

    public void bindUI(View rootView) {
        binding = DataBindingUtil.bind(rootView);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
        setListener();
        initData(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (useEventBus())
            BusFactory.getBus().unregister(this);
        getUiDelegate().destory();
    }

    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(getContext());
        }
        return uiDelegate;
    }

}
