package cn.droidlover.xdroid.base;

/**
 * Created by shihao on 2017/8/24.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;

public class LazyFragment extends RxFragment {
    protected LayoutInflater layoutInflater;
    protected Activity context;
    private View rootView;
    private ViewGroup container;
    private boolean isInitReady = false;
    private int isVisibleToUserState = -1;
    private Bundle saveInstanceState;
    private boolean isLazyEnable = true;
    private boolean isStart = false;
    private FrameLayout layout;
    private static final int STATE_VISIBLE = 1;
    private static final int STATE_NO_SET = -1;
    private static final int STATE_NO_VISIBLE = 0;
    private static final String TAG_ROOT_FRAMELAYOUT = "tag_root_framelayout";

    public LazyFragment() {
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        this.layoutInflater = inflater;
        this.container = container;
        this.onCreateView(savedInstanceState);
        return this.rootView == null ? super.onCreateView(inflater, container, savedInstanceState) : this.rootView;
    }

    private void onCreateView(Bundle savedInstanceState) {
        this.saveInstanceState = savedInstanceState;
        boolean isVisible;
        if (this.isVisibleToUserState == -1) {
            isVisible = this.getUserVisibleHint();
        } else {
            isVisible = this.isVisibleToUserState == 1;
        }

        if (this.isLazyEnable) {
            if (isVisible && !this.isInitReady) {
                this.onCreateViewLazy(savedInstanceState);
                this.isInitReady = true;
            } else {
                LayoutInflater mInflater = this.layoutInflater;
                if (mInflater == null && this.context != null) {
                    mInflater = LayoutInflater.from(this.context);
                }

                this.layout = new FrameLayout(this.context);
                this.layout.setTag("tag_root_framelayout");
                View view = this.getPreviewLayout(mInflater, this.layout);
                if (view != null) {
                    this.layout.addView(view);
                }

                this.layout.setLayoutParams(new LayoutParams(-1, -1));
                this.setContentView(this.layout);
            }
        } else {
            this.onCreateViewLazy(savedInstanceState);
            this.isInitReady = true;
        }

    }

    protected View getRealRootView() {
        return this.rootView != null && this.rootView instanceof FrameLayout && "tag_root_framelayout".equals(this
                .rootView.getTag()) ? ((FrameLayout) this.rootView).getChildAt(0) : this.rootView;
    }

    protected View getRootView() {
        return this.rootView;
    }

    protected View $(int id) {
        return this.rootView != null ? this.rootView.findViewById(id) : null;
    }

    protected void setContentView(int layoutResID) {
        if (this.isLazyEnable && this.getRootView() != null && this.getRootView().getParent() != null) {
            this.layout.removeAllViews();
            View view = this.layoutInflater.inflate(layoutResID, this.layout, false);
            this.layout.addView(view);
        } else {
            this.rootView = this.layoutInflater.inflate(layoutResID, this.container, false);
        }

    }

    protected void setContentView(View view) {
        if (this.isLazyEnable && this.getRootView() != null && this.getRootView().getParent() != null) {
            this.layout.removeAllViews();
            this.layout.addView(view);
        } else {
            this.rootView = view;
        }

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUserState = isVisibleToUser ? 1 : 0;
        if (isVisibleToUser && !this.isInitReady && this.getRootView() != null) {
            this.isInitReady = true;
            this.onCreateViewLazy(this.saveInstanceState);
            this.onResumeLazy();
        }

        if (this.isInitReady && this.getRootView() != null) {
            if (isVisibleToUser) {
                this.isStart = true;
                this.onStartLazy();
            } else {
                this.isStart = false;
                this.onStopLazy();
            }
        }

    }

    public void onResume() {
        super.onResume();
        if (this.isInitReady) {
            this.onResumeLazy();
        }

    }

    public void onPause() {
        super.onPause();
        if (this.isInitReady) {
            this.onPauseLazy();
        }

    }

    public void onStart() {
        super.onStart();
        if (this.isInitReady && !this.isStart && this.getUserVisibleHint()) {
            this.isStart = true;
            this.onStartLazy();
        }

    }

    public void onStop() {
        super.onStop();
        if (this.isInitReady && this.isStart && this.getUserVisibleHint()) {
            this.isStart = false;
            this.onStopLazy();
        }

    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }

    }

    public void onDetach() {
        super.onDetach();
        this.context = null;

        try {
            Field e = Fragment.class.getDeclaredField("mChildFragmentManager");
            e.setAccessible(true);
            e.set(this, (Object) null);
        } catch (NoSuchFieldException var2) {
            throw new RuntimeException(var2);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.rootView = null;
        this.container = null;
        this.layoutInflater = null;
        if (this.isInitReady) {
            this.onDestoryLazy();
        }

        this.isInitReady = false;
    }

    protected View getPreviewLayout(LayoutInflater mInflater, FrameLayout layout) {
        return null;
    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {
    }

    protected void onStartLazy() {
    }

    protected void onStopLazy() {
    }

    protected void onResumeLazy() {
    }

    protected void onPauseLazy() {
    }

    protected void onDestoryLazy() {
    }
}
