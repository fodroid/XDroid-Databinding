package cn.droidlover.xdroid.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import java.util.List;

import cn.droidlover.xdroidbase.base.ItemCallback;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

/**
 * Created by shihao<shihao.me@qq.com> on 2017/12/6.
 */

public abstract class SimpleBindingRecAdapter<T, V extends ViewDataBinding> extends SimpleRecAdapter<T, SimpleRecBindingViewHolder<V>> {
    public SimpleBindingRecAdapter(Context context) {
        super(context);
    }

    public SimpleBindingRecAdapter(Context context, ItemCallback<T> callback) {
        super(context, callback);
    }

    public SimpleBindingRecAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public SimpleBindingRecAdapter(Context context, List<T> data, ItemCallback<T> callback) {
        super(context, data, callback);
    }

    @Override
    public SimpleRecBindingViewHolder<V> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder<V>(view);
    }
}
