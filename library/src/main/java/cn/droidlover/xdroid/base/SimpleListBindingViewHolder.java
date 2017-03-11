package cn.droidlover.xdroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * Created by shihao on 2017/3/11.
 */

public class SimpleListBindingViewHolder<T extends ViewDataBinding> {
    private T binding;

    public SimpleListBindingViewHolder(T binding) {
        this.binding = binding;
    }

    public SimpleListBindingViewHolder(View itemView) {
        this.binding = DataBindingUtil.bind(itemView);
    }

    public T getBinding() {
        return binding;
    }
}
