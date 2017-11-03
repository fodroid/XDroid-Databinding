package cn.droidlover.xdroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shihao on 2017/3/7.
 */

public class SimpleRecBindingViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private V binding;

    public SimpleRecBindingViewHolder(V binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public SimpleRecBindingViewHolder(View itemView) {
        super(itemView);
        this.binding = DataBindingUtil.bind(itemView);
    }

    public V getBinding() {
        return binding;
    }
}

