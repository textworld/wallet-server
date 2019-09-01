package xyz.ruanxy.java.balance.service;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.model.AccountModel;
import xyz.ruanxy.java.balance.model.AccountRecordModel;
import xyz.ruanxy.java.balance.model.TransactionModel;
import xyz.ruanxy.java.balance.model.typeeunm.TransactionStatus;
import xyz.ruanxy.java.balance.payload.AccountRecordDTO;
import xyz.ruanxy.java.balance.repository.AccountRecordRepository;
import xyz.ruanxy.java.balance.repository.TransactionRepository;
import xyz.ruanxy.java.balance.repository.WalletRepository;
import xyz.ruanxy.java.balance.util.CustomBeanUtils;

@Service
public class AccountRecordService {
    private static final Logger logger = LoggerFactory.getLogger(AccountRecordService.class);

    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private WalletRepository walletRepo;
    @Autowired
    private AccountRecordRepository accountRecordRepo;

    public List<AccountRecordDTO> getByAccount(long accountId) {
        AccountModel accountModel = walletRepo.getOne(accountId);
        if(accountModel == null) {
            throw new IllegalArgumentException("no account found for ID:" + accountId);
        }

        AccountRecordModel recordModel = new AccountRecordModel();
        recordModel.setAccount(accountModel);
        Example<AccountRecordModel> example = Example.of(recordModel);

        List<AccountRecordModel> models = accountRecordRepo.findAll(example, Sort.by("gmt_transaction"));

        List<AccountRecordDTO> records = models.stream()
            .map(m -> AccountRecordDTO.build(m))
            .collect(Collectors.toList());

        return records;
    }
    /**
     * 创建记录
     * @param dto
     * @return AccountRecordDTO
     */
    public AccountRecordDTO create(AccountRecordDTO dto) {

        validDto(dto);

        AccountRecordModel model = new AccountRecordModel();

        CustomBeanUtils.copyNotNullProperties(dto, model);

        accountRecordRepo.save(model);

        dto.setId(model.getId());
        return dto;
    }

    /**
     * 修改记录
     * @param dto
     * @return
     */
    public AccountRecordDTO updateMoney(AccountRecordDTO dto) {
        AccountRecordModel model = accountRecordRepo.getOne(dto.getId());
        if (model == null) {
            throw new IllegalArgumentException("no account record found for " + dto.getId());
        }

        CustomBeanUtils.copyNotNullProperties(model, dto);

        validDto(dto);

        model.setMoney(dto.getMoney());

        accountRecordRepo.save(model);
        CustomBeanUtils.copyNotNullProperties(model, dto);

        return dto;
    }

    private void validDto(AccountRecordDTO dto) {
        Preconditions.checkNotNull(dto, "dto should provided.");
        Preconditions.checkArgument(dto.getAccountId() > 0, "accountId should be greater than 0");
        Preconditions.checkArgument(dto.getTransactionId() > 0, "transactionId should be greater than 0");

        AccountModel accountModel = walletRepo.getOne(dto.getAccountId());

        if(accountModel == null) {
            throw new IllegalArgumentException("no account found");
        }

        TransactionModel transactionModel = transactionRepo.getOne(dto.getTransactionId());

        if(transactionModel == null) {
            throw new IllegalArgumentException("no transaction found");
        }

        if(transactionModel.getStatus() == TransactionStatus.COMMIT.getValue()) {
            throw new IllegalArgumentException(" transaction " + transactionModel.getId() + " has already committed.");
        }
    }
}
