package xyz.ruanxy.java.balance.payload.weixin;

import xyz.ruanxy.java.balance.payload.SignUpRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WxSignUpRequest extends SignUpRequest {
    @NotBlank
    private String code;

    @NotNull
    private SignUpRequest user;

    private String OpenId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SignUpRequest getUser() {
        return user;
    }

    public void setUser(SignUpRequest user) {
        this.user = user;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }
}
