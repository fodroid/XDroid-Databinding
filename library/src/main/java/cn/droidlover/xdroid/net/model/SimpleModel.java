package cn.droidlover.xdroid.net.model;

/**
 * Created by shihao on 2017/8/20.
 */

public class SimpleModel {

    public String msg;
    public int code;

    /**
     * 数据是否有效
     *
     * @return true 有效
     */
    public boolean isValid() {
        return code == 0;
    }
}
