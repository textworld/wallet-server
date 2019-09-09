package xyz.ruanxy.java.balance.payload;

import org.springframework.beans.BeanUtils;
import xyz.ruanxy.java.balance.model.AccountRecordModel;
import xyz.ruanxy.java.balance.util.CustomBeanUtils;

public class AccountRecordDTO {
    private Long id;
    private Long transactionId;
    private Long accountId;
    private double money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
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
