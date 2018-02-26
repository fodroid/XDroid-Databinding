package cn.droidlover.xdroid.net.exception;

import java.io.IOException;

/**
 * Created by shihao<shihao.me@qq.com> on 2017/12/14.
 */

public class ResultErrorException extends IOException {

    /**
     * 网络错误
     */
    public static final int CODE_HTTP_ERROR = -100;
    /**
     * 数据解析失败
     */
    public static final int CODE_PARSE_ERROR = -101;
    /**
     * 网络连接错误
     */
    public static final int CODE_CONNECT_ERROR = -102;


    public int code;
    public String msg;

    public ResultErrorException() {

    }

    public ResultErrorException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultErrorException(String msg) {
        this.msg = msg;
    }
}
