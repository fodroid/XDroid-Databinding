package cn.droidlover.xdroid.net.model;

/**
 * Created by shihao on 2017/8/20.
 */

public interface SimpleModel {

    /**
     * 数据是否有效
     *
     * @return true 有效
     */
    boolean isValid();

    /**
     * 获取结果码
     *
     * @return
     */
    int getResultCode();

    /**
     * 获取结果信息
     *
     * @return
     */
    String getResultMsg();
}
