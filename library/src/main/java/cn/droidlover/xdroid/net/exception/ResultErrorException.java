package cn.droidlover.xdroid.net.exception;

import java.io.IOException;

/**
 * Created by shihao<shihao.me@qq.com> on 2017/12/14.
 */

public class ResultErrorException extends IOException {
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
