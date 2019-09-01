package xyz.ruanxy.java.balance.payload;

import org.springframework.beans.BeanUtils;
import xyz.ruanxy.java.balance.model.AccountRecordModel;
import xyz.ruanxy.java.balance.util.CustomBeanUtils;

public class AccountRecordDTO {
    private long id;
    private long transactionId;
    private long accountId;
    private double money;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public static AccountRecordDTO build(AccountRecordModel model) {
        AccountRecordDTO dto = new AccountRecordDTO();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }
}
