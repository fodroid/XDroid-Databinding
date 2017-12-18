package cn.droidlover.xdroid.net.exception;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by shihao<shihao.me@qq.com> on 2017/12/18.
 */

public class ServerResultErrorFunc2<T> implements Function<Throwable, Flowable<T>> {

    @Override
    public Flowable<T> apply(Throwable throwable) throws Exception {
        return Flowable.error(handleException() == null ? ExceptionEngine.handleException(throwable) : handleException());
    }

    /**
     * 重写该方法可以自己实现统一错误处理，默认情况下会使用ExceptionEngine中统一处理
     *
     * @return
     */
    public Throwable handleException() {
        return null;
    }
}
