package xyz.ruanxy.java.balance.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WalletType {
    DEBT(1, "debt"),
    ASSET(2, "asset");

    @JsonValue
    private String type;
    private int value;

    private WalletType(int value, String type)
    {
        this.value = value;
        this.type = type;
    }

    @JsonGetter
    public String getType() {
        return type;
    }

    @JsonSetter
    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public static WalletType parse(String type) {
        for (WalletType t : WalletType.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("no wallet type found for " + type);
    }

    public static WalletType parse(int value){
        for (WalletType t : WalletType.values()) {
            if(t.getValue() == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("no wallet type found for " + value);
    }
}
