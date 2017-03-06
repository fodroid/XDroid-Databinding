package cn.droidlover.xdroid.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import cn.droidlover.xdroid.base.SimpleRecAdapter;
import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroid.demo.R;
import cn.droidlover.xdroid.demo.databinding.AdapterGirlBinding;
import cn.droidlover.xdroid.demo.model.GankResults;
import cn.droidlover.xdroid.imageloader.ILFactory;


/**
 * Created by wanglei on 2016/12/10.
 */

public class GirlAdapter extends SimpleRecAdapter<GankResults.Item, SimpleRecBindingViewHolder<AdapterGirlBinding>> {


    public GirlAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder newViewHolder(View itemView) {
        return new SimpleRecBindingViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_girl;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterGirlBinding> holder, int position) {
        GankResults.Item item = data.get(position);
        ILFactory.getLoader().loadNet(holder.getBinding().ivGirl, item.getUrl(), null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {

                }
            }
        });
    }

}
