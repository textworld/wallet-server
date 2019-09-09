package xyz.ruanxy.java.balance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import xyz.ruanxy.java.balance.model.audit.GmtAudit;

@Entity
@Table(name = "T_account_record", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"transaction_id", "account_id"})
})
public class AccountRecordModel extends GmtAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private TransactionModel transaction;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountModel account;
    private double money;
    private LocalDateTime gmtTransaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public LocalDateTime getGmtTransaction() {
        return gmtTransaction;
    }

    public void setGmtTransaction(LocalDateTime gmtTransaction) {
        this.gmtTransaction = gmtTransaction;
    }

    public TransactionModel getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionModel transaction) {
        this.transaction = transaction;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }
}
