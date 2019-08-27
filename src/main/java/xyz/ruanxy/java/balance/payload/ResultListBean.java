package xyz.ruanxy.java.balance.payload;

import java.util.Collection;

public class ResultListBean<T> {
    private int code;
    private String message;
    private Collection<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public static ResultListBean error(int code, String message) {
        ResultListBean resultListBean = new ResultListBean();
        resultListBean.setCode(code);
        resultListBean.setMessage(message);
        return resultListBean;
    }

    public static ResultListBean success() {
        ResultListBean resultListBean = new ResultListBean();
        resultListBean.setCode(0);
        resultListBean.setMessage("success");
        return resultListBean;
    }

    public static <V> ResultListBean<V> success(Collection<V> data) {
        ResultListBean resultListBean = new ResultListBean();
        resultListBean.setCode(0);
        resultListBean.setMessage("success");
        resultListBean.setData(data);
        return resultListBean;
    }
}
