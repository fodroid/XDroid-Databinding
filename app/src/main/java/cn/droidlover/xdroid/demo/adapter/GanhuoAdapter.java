package cn.droidlover.xdroid.demo.adapter;

import android.content.Context;
import android.view.View;

import cn.droidlover.xdroid.base.SimpleRecAdapter;
import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroid.demo.R;
import cn.droidlover.xdroid.demo.databinding.AdapterGanhuoBinding;
import cn.droidlover.xdroid.demo.model.GankResults;


/**
 * Created by wanglei on 2016/12/10.
 */

public class GanhuoAdapter extends SimpleRecAdapter<GankResults.Item,
        SimpleRecBindingViewHolder<AdapterGanhuoBinding>> {

    public static final int TAG_VIEW = 0;

    public GanhuoAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder newViewHolder(View itemView) {
        return new SimpleRecBindingViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_ganhuo;
    }

    @Override
    public void onBindViewHolder(final SimpleRecBindingViewHolder<AdapterGanhuoBinding> holder, final int position) {
        final GankResults.Item item = data.get(position);
        holder.getBinding().setItem(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
    }

}
