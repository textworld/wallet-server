package xyz.ruanxy.java.balance.service;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.model.AccountModel;
import xyz.ruanxy.java.balance.model.TransactionModel;
import xyz.ruanxy.java.balance.model.WalletType;
import xyz.ruanxy.java.balance.model.typeeunm.TransactionStatus;
import xyz.ruanxy.java.balance.payload.AccountDTO;
import xyz.ruanxy.java.balance.payload.TransactionDTO;
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;
import xyz.ruanxy.java.balance.repository.TransactionRepository;
import xyz.ruanxy.java.balance.repository.WalletRepository;
import xyz.ruanxy.java.balance.util.CustomBeanUtils;

@Service
public class TransactionService {
    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository repository;

    private WalletRepository walletRepository;

    public List<TransactionDTO> getAll(){
        List<TransactionModel> modelList = repository.findAll(Sort.by("id").descending());
        return modelList.stream()
            .map(m -> {
                TransactionDTO dto = new TransactionDTO();
                CustomBeanUtils.copyNotNullProperties(m, dto);
                dto.setStatus(TransactionStatus.parse(m.getStatus()));
                return dto;
            })
            .collect(Collectors.toList());
    }

    public TransactionDTO begin(){
        Sort sortx = Sort.by("id").descending();
        List<TransactionModel> allTransaction = repository.findAll(sortx);

        if(!allTransaction.isEmpty()
            && allTransaction.get(0).getStatus() == TransactionStatus.BEGIN.getValue()) {
            throw new RuntimeException("请先完成上一次事务");
        }

        TransactionModel lastOne = allTransaction.get(0);

        TransactionModel model = new TransactionModel();
        model.setStatus(TransactionStatus.BEGIN.getValue());
        model.setPre(lastOne.getId());
        repository.save(model);

        TransactionDTO dto = new TransactionDTO();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }

    public List<AccountDTO> getUnRecordAccounts(long transactionId) {
        getTransactionModel(transactionId);
        List<AccountModel> accountModels = walletRepository.findUnRecordAccount(transactionId);
        return AccountDTO.build(accountModels);
    }

//    public List<AccountRecordVO> getAccountRecord(long transactionId) {
//        TransactionModel transactionModel = getTransactionModel(transactionId);
//        return repository.findAccountRecordVo(transactionId);
//        //return null;
//    }
    public List<Map<String, Object>> getAccountRecord(long transactionId) {
        TransactionModel transactionModel = getTransactionModel(transactionId);
        return repository.findAccountRecordVo(transactionId);
        //return null;
    }

    private TransactionModel getTransactionModel(long transactionId){
        Preconditions.checkArgument(transactionId > 0, "transactionId must be greater than 0.");
        TransactionModel transactionModel = repository.getOne(transactionId);
        if (transactionModel == null) {
            throw new IllegalArgumentException("No transaction found for " + transactionId);
        }

        return transactionModel;
    }
}
