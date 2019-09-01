package xyz.ruanxy.java.balance.payload;

public class ResultBean<T> {
    private int code;
    private String message;
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static ResultBean error(int code, String message) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static ResultBean success() {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        return resultBean;
    }

    public static ResultBean success(String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage(msg);
        return resultBean;
    }
    
    public static <V> ResultBean<V> success(V data) {
            ResultBean resultBean = new ResultBean();
            resultBean.setCode(0);
            resultBean.setMessage("success");
            resultBean.setResult(data);
            return resultBean;
    }
}
