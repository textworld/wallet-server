package xyz.ruanxy.java.balance.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"gmt_create", "gmt_modify"},
    allowGetters = true
)
public class GmtAudit {
    @CreatedDate
    private Instant gmtCreate;
    @LastModifiedDate
    private Instant gmtModify;

    public Instant getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Instant gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Instant getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Instant gmtModify) {
        this.gmtModify = gmtModify;
    }
}
