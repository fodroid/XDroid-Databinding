package cn.droidlover.xdroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * Created by shihao on 2017/3/11.
 */

public class SimpleListBindingViewHolder<V extends ViewDataBinding> {
    private V binding;

    public SimpleListBindingViewHolder(V binding) {
        this.binding = binding;
    }

    public SimpleListBindingViewHolder(View itemView) {
        this.binding = DataBindingUtil.bind(itemView);
    }

    public V getBinding() {
        return binding;
    }
}
