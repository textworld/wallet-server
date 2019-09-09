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
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;
import xyz.ruanxy.java.balance.payload.vo.TransactionVO;
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

        Optional<AccountModel> accountModel = walletRepo.findById(dto.getAccountId());
        Optional<TransactionModel> transactionModel = transactionRepo.findById(dto.getTransactionId());

        model.setTransaction(transactionModel.get());
        model.setAccount(accountModel.get());
        model.setGmtTransaction(transactionModel.get().getGmtTransaction());

        AccountRecordModel accountRecordModel = accountRecordRepo.findByAccountAndTransaction(accountModel.get(), transactionModel.get());

        if (accountRecordModel != null) {
            CustomBeanUtils.copyNotNullProperties(model, accountRecordModel);
            model = accountRecordModel;
        }

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

    public void delete(Long id) {
        Preconditions.checkNotNull(id, "no record id was provided.");
        //TODO 权限校验
        accountRecordRepo.deleteById(id);
    }

    private void validDto(AccountRecordDTO dto) {
        Preconditions.checkNotNull(dto, "dto should provided.");
        Preconditions.checkArgument(dto.getAccountId() > 0, "accountId should be greater than 0");
        Preconditions.checkArgument(dto.getTransactionId() > 0, "transactionId should be greater than 0");

        Optional<AccountModel> accountModel = walletRepo.findById(dto.getAccountId());

        if (!accountModel.isPresent()){
            throw new IllegalArgumentException("no account found");
        }

        Optional<TransactionModel> optionalModel = transactionRepo.findById(dto.getTransactionId());

        if (!optionalModel.isPresent()){
            throw new IllegalArgumentException("no transaction found");
        }
        TransactionModel transactionModel = optionalModel.get();

        if(transactionModel.getStatus() == TransactionStatus.COMMIT.getValue()) {
            throw new IllegalArgumentException(" transaction " + transactionModel.getId() + " has already committed.");
        }
    }
}
