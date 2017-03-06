package cn.droidlover.xdroid.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.droidlover.xdroid.base.SimpleRecAdapter;
import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroid.demo.R;
import cn.droidlover.xdroid.demo.databinding.AdapterHomeBinding;
import cn.droidlover.xdroid.demo.model.GankResults;
import cn.droidlover.xdroid.imageloader.ILFactory;

/**
 * Created by wanglei on 2016/12/10.
 */

public class HomeAdapter extends SimpleRecAdapter<GankResults.Item, SimpleRecBindingViewHolder<AdapterHomeBinding>> {

    public static final int TAG_VIEW = 0;

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder newViewHolder(View itemView) {
        return new SimpleRecBindingViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_home;
    }

    @Override
    public void onBindViewHolder(final SimpleRecBindingViewHolder<AdapterHomeBinding> holder, final int position) {
        final GankResults.Item item = data.get(position);

        String type = item.getType();
        switch (type) {
            case "休息视频":
                holder.getBinding().rlMessage.setVisibility(View.VISIBLE);
                holder.getBinding().ivPart.setVisibility(View.GONE);
                holder.getBinding().ivVedio.setVisibility(View.VISIBLE);
                holder.getBinding().tvItem.setText(item.getDesc());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getRecItemClick() != null) {

                        }
                    }
                });
                break;
            case "福利":
                holder.getBinding().rlMessage.setVisibility(View.GONE);
                holder.getBinding().ivPart.setVisibility(View.VISIBLE);
                holder.getBinding().ivVedio.setVisibility(View.GONE);

                ILFactory.getLoader().loadNet(holder.getBinding().ivPart, item.getUrl(), null);
                holder.getBinding().tvItem.setText("瞧瞧妹纸，扩展扩展视野......");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getRecItemClick() != null) {

                        }
                    }
                });
                break;
            default:
                holder.getBinding().rlMessage.setVisibility(View.VISIBLE);
                holder.getBinding().ivPart.setVisibility(View.GONE);
                holder.getBinding().ivVedio.setVisibility(View.GONE);
                holder.getBinding().tvItem.setText(item.getDesc());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getRecItemClick() != null) {

                        }
                    }
                });
                break;
        }
        Uri uri = null;
        switch (item.getType()) {
            case "Android":
                holder.getBinding().ivType.setImageResource(R.mipmap.android_icon);
                break;
            case "iOS":
                holder.getBinding().ivType.setImageResource(R.mipmap.ios_icon);
                break;
            case "前端":
                holder.getBinding().ivType.setImageResource(R.mipmap.js_icon);
                break;
            case "拓展资源":
                holder.getBinding().ivType.setImageResource(R.mipmap.other_icon);
                break;
        }

        String author = item.getWho();
        if (author != null) {
            holder.getBinding().tvAuthor.setText(author);
            holder.getBinding().tvAuthor.setTextColor(Color.parseColor("#87000000"));
        } else {
            holder.getBinding().tvAuthor.setText("");
        }

        holder.getBinding().tvTime.setText(item.getCreatedAt());

        holder.getBinding().tvType.setText(type);

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
