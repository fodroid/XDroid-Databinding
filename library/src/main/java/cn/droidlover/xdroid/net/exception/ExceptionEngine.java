package cn.droidlover.xdroid.net.exception;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * Created by shihao<shihao.me@qq.com> on 2017/12/18.
 */

public class ExceptionEngine {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResultErrorException handleException(Throwable e) {
        //输出错误信息
        e.printStackTrace();
        ResultErrorException ex = new ResultErrorException();
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ResultErrorException();
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.code = ResultErrorException.CODE_HTTP_ERROR;
                    ex.msg = "网络错误";  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ResultErrorException) {    //服务器返回的错误
            return (ResultErrorException) e;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex.code = ResultErrorException.CODE_PARSE_ERROR;
            ex.msg = "解析错误";            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException) {
            ex.code = ResultErrorException.CODE_CONNECT_ERROR;
            ex.msg = "连接失败";  //均视为网络错误
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex.code = ResultErrorException.CODE_CONNECT_ERROR;
            ex.msg = "连接失败";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex.code = ResultErrorException.CODE_HTTP_ERROR;
            ex.msg = "网络错误";
            return ex;
        } else {
            ex.msg = "未知错误";          //未知错误
            return ex;
        }
    }
}
