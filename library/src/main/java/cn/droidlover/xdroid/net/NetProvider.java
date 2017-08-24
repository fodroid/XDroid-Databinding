package cn.droidlover.xdroid.net;

import cn.droidlover.xdroid.net.cookie.store.MemoryCookieStore;
import cn.droidlover.xdroid.net.https.HttpsManager;
import cn.droidlover.xdroid.net.https.SSLParams;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Interceptor;

/**
 * 网络设置
 * Created by shihao on 2017/8/4.
 */

public class NetProvider {

    /**
     * 设置是否重试
     *
     * @return
     */
    public boolean configIsRetryOnConnectionFailure() {
        return true;
    }

    /**
     * 添加公共的header
     *
     * @return
     */
    public Headers configCommonHeaders() {
        return null;
    }

    /**
     * 为OkHttpClient添加NetworkInterceptors
     *
     * @return
     */

    public Interceptor[] configNetworkInterceptors() {
        return null;
    }

    /**
     * 为OkHttpClient添加Interceptors
     *
     * @return
     */
    public Interceptor[] configInterceptors() {
        return null;
    }

    /**
     * 配置https
     *
     * @return 默认忽略验证，可访问所有https
     */
    public SSLParams configHttps() {
        return HttpsManager.getSslSocketFactory(null, null, null);
    }

    /**
     * 设置cookie保存,默认不保存，内置实现了：内存保存{@link MemoryCookieStore},
     * 持久化SharedPreferences中保存{@link cn.droidlover.xdroid.net.cookie.store.PersistentCookieStore}
     *
     * @return null 默认不保存
     */
    public CookieJar configCookie() {
        return null;
    }

    /**
     * 日志输出等级 {@link cn.droidlover.xdroid.net.LoggingInterceptor.Level}
     *
     * @return
     * @see cn.droidlover.xdroid.net.LoggingInterceptor.Level
     */

    public LoggingInterceptor.Level configLogLevel() {
        return LoggingInterceptor.Level.BODY;
    }

    /**
     * 网络请求日志输出，需要使用自己的日志输出工具打印的，重写该方法
     *
     * @param message 待输出日志
     * @return 返回true当前日志已处理，内置输出工具不再打印
     */
    public boolean log(String message) {
        return false;
    }

    /**
     * 连接超时时间，默认10s
     *
     * @return
     */
    public long configConnectTimeoutMills() {
        return 1000 * 10;
    }

    /**
     * 读取超时时间，默认20s
     *
     * @return
     */
    public long configReadTimeoutMills() {
        return 1000 * 2000;
    }

}
