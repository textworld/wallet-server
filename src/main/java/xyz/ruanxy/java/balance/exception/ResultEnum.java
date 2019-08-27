package xyz.ruanxy.java.balance.exception;

public enum ResultEnum {
    SUCCESS(0, "success"),
    ERROR(-1, "error");


    private int code;
    private String msg;

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
