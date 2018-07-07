package xyz.ruanxy.java.balance.payload;

import xyz.ruanxy.java.balance.model.WalletType;

import java.time.Instant;

public class WalletDto {
    private Long id;
    private String name;
    private String alias;
    private String comment;
    private WalletType Type;
    private Instant creationDateTime;
    private Instant expirationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public WalletType getType() {
        return Type;
    }

    public void setType(WalletType type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "WalletDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", comment='" + comment + '\'' +
                ", Type=" + Type +
                ", creationDateTime=" + creationDateTime +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
