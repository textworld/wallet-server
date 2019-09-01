package xyz.ruanxy.java.balance.model.typeeunm;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;


public enum TransactionStatus {
    BEGIN(1, "begin"),
    COMMIT(2, "commit");


    @JsonValue
    private String type;
    private int value;

    private TransactionStatus(int value, String type)
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


    public static TransactionStatus parse(String type) {
        for (TransactionStatus t : TransactionStatus.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("no wallet type found for " + type);
    }

    public static TransactionStatus parse(int value){
        for (TransactionStatus t : TransactionStatus.values()) {
            if(t.getValue() == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("no wallet type found for " + value);
    }
}
