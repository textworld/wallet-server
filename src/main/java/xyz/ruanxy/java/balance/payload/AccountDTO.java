package xyz.ruanxy.java.balance.payload;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import xyz.ruanxy.java.balance.model.AccountModel;
import xyz.ruanxy.java.balance.model.WalletType;

public class AccountDTO {
    private Long id;
    private String name;
    private LocalDateTime gmtCreate;
    private WalletType type;
    private String comment;

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

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public WalletType getType() {
        return type;
    }

    public void setType(WalletType type) {
        this.type = type;
    }

    public static List<AccountDTO> build(List<AccountModel> accountModels) {
        return accountModels.stream().map(m -> {
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(m, accountDTO);
            accountDTO.setType(WalletType.parse(m.getType()));
            return accountDTO;
        }).collect(Collectors.toList());
    }
}
