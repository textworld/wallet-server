package xyz.ruanxy.java.balance.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.model.TransactionModel;
import xyz.ruanxy.java.balance.payload.TransactionDTO;
import xyz.ruanxy.java.balance.repository.TransactionRepository;

@Service
public class TransactionService {
    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository repository;

    public TransactionDTO begin(){
        Sort sortx = Sort.by("id").descending();
        List<TransactionModel> allTransaction = repository.findAll(sortx);

        if(!allTransaction.isEmpty()
            && "commit".equalsIgnoreCase(allTransaction.get(0).getStatus())) {
            throw new RuntimeException("请先完成上一次事务");
        }

        TransactionModel lastOne = allTransaction.get(0);

        TransactionModel model = new TransactionModel();
        model.setStatus("begin");
        model.setPre(lastOne.getId());
        repository.save(model);

        TransactionDTO dto = new TransactionDTO();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }
}
