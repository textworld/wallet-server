package xyz.ruanxy.java.balance.model;

import xyz.ruanxy.java.balance.model.audit.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "wallet_history")
public class WalletHistory  extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletId;

    private Double gauge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Double getGauge() {
        return gauge;
    }

    public void setGauge(Double gauge) {
        this.gauge = gauge;
    }
}
