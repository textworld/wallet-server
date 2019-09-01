package xyz.ruanxy.java.balance.payload.vo;

import java.time.LocalDateTime;

public class AccountRecordVO {
    private long id;
    private long accountId;
    private long transactionId;
    private double money;
    private String accountName;
    private String accountComment;
    private LocalDateTime gmtTransaction;

    public AccountRecordVO(long id) {
        this.id = id;
    }

    public AccountRecordVO(long id, long accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    public AccountRecordVO(long id, long accountId, long transactionId, double money, String accountName,
        String accountComment, LocalDateTime gmtTransaction) {
        super();
        this.id = id;
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.money = money;
        this.accountName = accountName;
        this.accountComment = accountComment;
        this.gmtTransaction = gmtTransaction;
    }

    public AccountRecordVO() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountComment() {
        return accountComment;
    }

    public void setAccountComment(String accountComment) {
        this.accountComment = accountComment;
    }

    public LocalDateTime getGmtTransaction() {
        return gmtTransaction;
    }

    public void setGmtTransaction(LocalDateTime gmtTransaction) {
        this.gmtTransaction = gmtTransaction;
    }
}
