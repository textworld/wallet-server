package xyz.ruanxy.java.balance.service;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.exception.BusinessException;
import xyz.ruanxy.java.balance.model.AccountModel;
import xyz.ruanxy.java.balance.payload.AccountDTO;
import xyz.ruanxy.java.balance.repository.UserRepository;
import xyz.ruanxy.java.balance.repository.WalletRepository;
import xyz.ruanxy.java.balance.util.CustomBeanUtils;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private WalletRepository walletRepository;

    public AccountDTO create(AccountDTO dto){
        AccountModel model = new AccountModel();

        BeanUtils.copyProperties(dto, model);

        walletRepository.save(model);

        dto.setId(model.getId());
        return dto;
    }

    public void update(long id, AccountDTO dto) {
        Preconditions.checkNotNull(dto, "no account dto supplied.");

        AccountModel account = walletRepository.getOne(id);
        if (account == null) {
            throw new BusinessException("no account found.", 404);
        }

        CustomBeanUtils.copyNotNullProperties(dto, account);

        account.setId(id);
        walletRepository.save(account);
    }

    public void delete(long id) {
        AccountModel account = walletRepository.getOne(id);
        if (account == null) {
            throw new BusinessException("no account found.", 404);
        }

        walletRepository.deleteById(id);
    }
}
