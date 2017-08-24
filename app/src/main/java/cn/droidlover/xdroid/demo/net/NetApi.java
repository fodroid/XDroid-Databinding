package cn.droidlover.xdroid.demo.net;

import cn.droidlover.xdroid.net.XApi;

/**
 * Created by wanglei on 2016/12/9.
 */

public class NetApi {
    public static final String API_BASE_URL = "http://gank.io/api/";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null) {
            synchronized (NetApi.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(API_BASE_URL).create(GankService.class);
                }
            }
        }
        return gankService;
    }

}
