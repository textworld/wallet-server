package xyz.ruanxy.java.balance.model;

import org.springframework.data.mapping.model.CamelCaseSplittingFieldNamingStrategy;
import xyz.ruanxy.java.balance.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "wx_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "open_id"
        })
})
public class WxUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=128)
    @Column(name = "open_id")
    private  String openId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(WxUser obj) {
        return this.openId == obj.getOpenId();
    }
}
