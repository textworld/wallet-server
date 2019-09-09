package xyz.ruanxy.java.balance.payload.vo;

public class TransactionVO {
    private double money;
    private int type;
    private Long transactionId;

    public TransactionVO(Long transactionId, double money, int type) {
        this.transactionId = transactionId;
        this.money = money;
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
