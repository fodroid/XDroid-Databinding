package cn.droidlover.xdroid.net;

import android.util.Log;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroid.net.converter.StringConverterFactory;
import cn.droidlover.xdroid.net.converter.gson.GsonConverterFactory;
import cn.droidlover.xdroid.net.exception.ServerResultErrorFunc;
import cn.droidlover.xdroid.net.exception.ServerResultErrorFunc2;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by shihao on 2017/7/30.
 */

public class XApi {
    private static XApi instance;

    private XApi() {

    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                instance = new XApi();
            }
        }
        return instance;
    }

    public Retrofit getRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, new NetProvider());
    }

    public Retrofit getRetrofit(String baseUrl, NetProvider provider) {
        return getRetrofit(baseUrl, getOkHttpClient(provider));
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) // 添加gson转换器
                .client(okHttpClient);
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    public void getOkHttpClient() {
        getOkHttpClient(new NetProvider());
    }

    public OkHttpClient getOkHttpClient(final NetProvider provider) {
        if (provider == null) {
            throw new NullPointerException("provider can not be null");
        }
        //日志拦截
        LoggingInterceptor logging = new LoggingInterceptor(new LoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (!provider.log(message)) {
                    Log.d("XApi", message);
                }
            }
        });
        logging.setLevel(provider.configLogLevel());
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(provider.configConnectTimeoutMills(), TimeUnit.MILLISECONDS)
                .readTimeout(provider.configReadTimeoutMills(), TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(provider.configIsRetryOnConnectionFailure()) // 失败重发
                .addInterceptor(logging);
        //设置支持https
        if (provider.configHttps() != null)
            builder.sslSocketFactory(provider.configHttps().sSLSocketFactory, provider.configHttps().trustManager);
        //设置cookie
        if (provider.configCookie() != null)
            builder.cookieJar(provider.configCookie());
        //添加应用拦截
        if (provider.configInterceptors() != null)
            for (Interceptor interceptor : provider.configInterceptors())
                if (interceptor != null)
                    builder.addInterceptor(interceptor);
        //添加网络拦截
        if (provider.configNetworkInterceptors() != null)
            for (Interceptor interceptor : provider.configNetworkInterceptors())
                if (interceptor != null)
                    builder.addNetworkInterceptor(interceptor);
        //添加统一的header
        if (provider.configCommonHeaders() != null)
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builderHeader = chain.request().newBuilder();
                    Headers headers = provider.configCommonHeaders();
                    for (int i = 0; i < headers.size(); i++)
                        builderHeader.addHeader(headers.name(i), headers.value(i));
                    Request request = builderHeader.build();
                    return chain.proceed(request);
                }
            });
        return builder.build();
    }


    /**
     * 统一线程切换
     *
     * @return
     */
    public static <T> FlowableTransformer<T, T> getFlowableScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .onErrorResumeNext(new ServerResultErrorFunc2<T>())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> getObservableScheduler() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .onErrorResumeNext(new ServerResultErrorFunc<T>())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> getFlowableScheduler(final Function<? super Flowable<Throwable>, ? extends Publisher<?>> retryWhenHandler) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .retryWhen(retryWhenHandler)
                        .onErrorResumeNext(new ServerResultErrorFunc2<T>())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> getObservableScheduler(final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> retryWhenHandler) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .retryWhen(retryWhenHandler)
                        .onErrorResumeNext(new ServerResultErrorFunc<T>())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> getFlowableScheduler(final Function<? super Flowable<Throwable>, ? extends Publisher<?>> retryWhenHandler, final Function<? super Throwable, ? extends Publisher<? extends T>> resumeFunction) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .retryWhen(retryWhenHandler)
                        .onErrorResumeNext(resumeFunction)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> getObservableScheduler(final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> retryWhenHandler, final Function<? super Throwable, ? extends ObservableSource<? extends T>> resumeFunction) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .retryWhen(retryWhenHandler)
                        .onErrorResumeNext(resumeFunction)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable<T> create(Observable<T> resurce, Function<? super Observable<Throwable>, ? extends ObservableSource<?>> retryWhenHandler, Function<? super Throwable, ? extends ObservableSource<? extends T>> resumeFunction) {
        return resurce
                //失败重试的炒作，可以用于处理token过期
                .retryWhen(retryWhenHandler)
                .onErrorResumeNext(resumeFunction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> Observable<T> create(Observable<T> resurce, Function<? super Observable<Throwable>, ? extends ObservableSource<?>> retryWhenHandler) {
        return resurce
                //失败重试的炒作，可以用于处理token过期
                .retryWhen(retryWhenHandler)
                .onErrorResumeNext(new ServerResultErrorFunc<T>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> Observable<T> create(Observable<T> resurce) {
        return resurce
                .onErrorResumeNext(new ServerResultErrorFunc<T>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
