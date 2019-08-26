package xyz.ruanxy.java.balance.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import xyz.ruanxy.java.balance.model.audit.GmtAudit;
import javax.persistence.*;

@Entity
@Table(name="T_transaction")
public class TransactionModel extends GmtAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private double asset;
    private double debt;
    private long pre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public long getPre() {
        return pre;
    }

    public void setPre(long pre) {
        this.pre = pre;
    }
}
