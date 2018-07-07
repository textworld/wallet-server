package xyz.ruanxy.java.balance.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WalletType {
    DEBT("debt"),
    ASSET("asset");

    private String type;

    private WalletType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
