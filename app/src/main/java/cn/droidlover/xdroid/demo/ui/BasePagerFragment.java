package cn.droidlover.xdroid.demo.ui;

import android.os.Bundle;
import android.view.View;

import cn.droidlover.xdroid.base.XLazyFragment;
import cn.droidlover.xdroid.demo.R;
import cn.droidlover.xdroid.demo.databinding.FragmentBasePagerBinding;
import cn.droidlover.xdroid.demo.model.GankResults;
import cn.droidlover.xdroid.demo.net.NetApi;
import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.XRecyclerView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanglei on 2016/12/10.
 */

public abstract class BasePagerFragment extends XLazyFragment<FragmentBasePagerBinding> {


    protected static final int PAGE_SIZE = 10;
    protected static final int MAX_PAGE = 10;


    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        loadData(1);
    }

    private void initAdapter() {
        setLayoutManager(getBinding().contentLayout.getRecyclerView());
        getBinding().contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        getBinding().contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        loadData(1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        loadData(page);
                    }
                });

        getBinding().contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
        getBinding().contentLayout.getRecyclerView().useDefLoadMoreView();
    }

    public void loadData(final int page) {
        NetApi.getGankService().getGankData(getType(), PAGE_SIZE, page)
                .compose(XApi.<GankResults>getObservableScheduler())
                .compose(this.<GankResults>bindToLifecycle())
                .subscribe(new Observer<GankResults>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankResults gankResults) {
                        if (!gankResults.isError()) {
                            if (page > 1) {
                                getAdapter().addData(gankResults.getResults());
                            } else {
                                getAdapter().setData(gankResults.getResults());
                            }

                            getBinding().contentLayout.getRecyclerView().setPage(page, MAX_PAGE);

                            if (getAdapter().getItemCount() < 1) {
                                getBinding().contentLayout.showEmpty();
                                return;
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getBinding().contentLayout.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public abstract SimpleRecAdapter getAdapter();

    public abstract void setLayoutManager(XRecyclerView recyclerView);

    public abstract String getType();


    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_pager;
    }

}
