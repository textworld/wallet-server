package xyz.ruanxy.java.balance.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ruanxy.java.balance.payload.AccountDTO;
import xyz.ruanxy.java.balance.payload.ResultBean;
import xyz.ruanxy.java.balance.payload.ResultListBean;
import xyz.ruanxy.java.balance.payload.TransactionDTO;
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;
import xyz.ruanxy.java.balance.payload.vo.TransactionVO;
import xyz.ruanxy.java.balance.service.TransactionService;

@RestController
@RequestMapping(value = "/api/v1/transaction")
public class TransactionController {
    private final static Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public TransactionDTO begin(){
        return transactionService.begin();
    }

    @GetMapping
    public ResultListBean<TransactionDTO> getAll(){
        return ResultListBean.success(transactionService.getAll());
    }

    @GetMapping(value = "/{id:\\d+}/unRecordAccounts")
    public ResultListBean<Map<String, Object>> getUnRecordAccounts(
        @PathVariable(name = "id") long id
    ){
        return ResultListBean.success(transactionService.getUnRecordAccounts(id
        ));
    }

    @GetMapping(value = "/{id:\\d+}/records")
    public ResultListBean<Map<String, Object>> getAccountRecords(
        @PathVariable(name = "id") long id
    ) {
        return ResultListBean.success(transactionService.getAccountRecord(id));
    }

    @GetMapping(value = "/{id:\\d+}/cal")
    public ResultListBean<TransactionVO> cal(
        @PathVariable(name = "id") long id
    ){
        return ResultListBean.success(transactionService.cal(id));
    }
}
