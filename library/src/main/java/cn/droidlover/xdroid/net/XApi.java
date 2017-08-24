package cn.droidlover.xdroid.net;

import android.util.Log;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroid.net.converter.StringConverterFactory;
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
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
            throw new IllegalStateException("provider can not be null");
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
                    Request request = chain.request().newBuilder()
                            .headers(provider.configCommonHeaders())
                            .build();
                    return chain.proceed(request);
                }
            });
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

            }
        }).compose(new ObservableTransformer<String, String>() {
            @Override
            public ObservableSource<String> apply(@NonNull Observable<String> upstream) {
                return upstream.map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return null;
                    }
                });
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> getObservableScheduler() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
