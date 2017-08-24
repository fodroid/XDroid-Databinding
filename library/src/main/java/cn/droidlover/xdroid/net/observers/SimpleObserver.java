package cn.droidlover.xdroid.net.observers;

import cn.droidlover.xdroid.net.model.SimpleModel;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.ResourceObserver;

/**
 * Created by shihao on 2017/8/20.
 */

public abstract class SimpleObserver<T> extends ResourceObserver<T> {

    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof SimpleModel) {
            if (((SimpleModel) t).isValid()) {
                onSuccess(t);
            } else {
                onError(new Throwable(((SimpleModel) t).msg));
            }
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFinish();
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    public abstract void onSuccess(@NonNull T t);

    public abstract void onFinish();
}
