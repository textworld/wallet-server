package xyz.ruanxy.java.balance.model;

import javax.validation.constraints.Digits;
import org.hibernate.annotations.NaturalId;
import xyz.ruanxy.java.balance.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import xyz.ruanxy.java.balance.model.audit.GmtAudit;

@Entity
@Table(name = "T_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class AccountModel extends GmtAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 128)
    private String name;

    private int type = WalletType.ASSET.getValue();

    @Size(max = 10000)
    private String comment;

    private int active = 1;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
