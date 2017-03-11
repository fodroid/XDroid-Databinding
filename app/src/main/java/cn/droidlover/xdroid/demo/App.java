package cn.droidlover.xdroid.demo;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroidbase.XDroidBaseConf;
import cn.droidlover.xdroidbase.imageloader.GlideLoader;
import cn.droidlover.xdroidbase.imageloader.ILoader;
import cn.droidlover.xdroidbase.router.Router;
import okhttp3.OkHttpClient;

/**
 * Created by wanglei on 2016/12/9.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        context = getApplicationContext();

        XDroidBaseConf.getInstance()
                .setLog(true)//是否显示log输出
                .setDefLogTag("XDroid")//默认的日志输出tag
                .setCacheSpName("config")//配置sharedPref的文件名
                .setCacheDiskDir("cache")//配置缓存文件夹名称
                .setILLoadingRes(ILoader.Options.RES_NONE)//配置图片加载loding占位图
                .setILErrorRes(ILoader.Options.RES_NONE)//配置图片加载失败占位图
                .setILoader(new GlideLoader())//配置图片框架
                .setRouterLaunchAnim(Router.RES_NONE, Router.RES_NONE)//配置launch动画
                .setRouterPopAnim(Router.RES_NONE, Router.RES_NONE)//配置pop动画
                .build();//设置生效
    }

    public static Context getContext() {
        return context;
    }
}
