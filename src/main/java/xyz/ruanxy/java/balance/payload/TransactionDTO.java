package xyz.ruanxy.java.balance.payload;

import java.time.LocalDateTime;
import xyz.ruanxy.java.balance.model.typeeunm.TransactionStatus;

public class TransactionDTO {
    private long id;
    private TransactionStatus status;
    private double asset;
    private double debt;
    private LocalDateTime gmtTransaction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public double getAsset() {
        return asset;
    }

    public void setAsset(double asset) {
        this.asset = asset;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public LocalDateTime getGmtTransaction() {
        return gmtTransaction;
    }

    public void setGmtTransaction(LocalDateTime gmtTransaction) {
        this.gmtTransaction = gmtTransaction;
    }
}
