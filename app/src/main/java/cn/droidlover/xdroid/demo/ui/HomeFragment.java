package cn.droidlover.xdroid.demo.ui;


import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroid.demo.adapter.HomeAdapter;
import cn.droidlover.xdroid.demo.databinding.AdapterHomeBinding;
import cn.droidlover.xdroid.demo.model.GankResults;
import cn.droidlover.xdroidbase.base.SimpleItemCallback;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by wanglei on 2016/12/10.
 */

public class HomeFragment extends BasePagerFragment {

    HomeAdapter adapter;

    @Override
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new HomeAdapter(context);
            adapter.setItemClick(new SimpleItemCallback<GankResults.Item,
                    SimpleRecBindingViewHolder<AdapterHomeBinding>>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag,
                                        SimpleRecBindingViewHolder<AdapterHomeBinding> holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (tag) {
                        case HomeAdapter.TAG_VIEW:
                            WebActivity.launch(context, model.getUrl(), model.getDesc());
                            break;
                    }
                }
            });
        }
        return adapter;
    }

    @Override
    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    @Override
    public String getType() {
        return "all";
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


}
