package xyz.ruanxy.java.balance.payload;

public class GeneralResponse {
    private String message;
    private RespMsgType status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RespMsgType getStatus() {
        return status;
    }

    public void setStatus(RespMsgType status) {
        this.status = status;
    }

    public static GeneralResponse build(RespMsgType type){
        GeneralResponse resp = new GeneralResponse();
        resp.setStatus(type);
        return resp;
    }
}
