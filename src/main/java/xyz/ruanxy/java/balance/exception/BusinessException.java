package xyz.ruanxy.java.balance.exception;

public class BusinessException extends RuntimeException{
    private int code;

    public BusinessException() {
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
