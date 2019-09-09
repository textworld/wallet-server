package xyz.ruanxy.java.balance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ruanxy.java.balance.payload.AccountRecordDTO;
import xyz.ruanxy.java.balance.payload.ResultBean;
import xyz.ruanxy.java.balance.payload.ResultListBean;
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;
import xyz.ruanxy.java.balance.payload.vo.TransactionVO;
import xyz.ruanxy.java.balance.service.AccountRecordService;

@RestController
@RequestMapping(value = "/api/v1/accountRecord")
@CrossOrigin
public class AccountRecordController {
    private final static Logger logger = LoggerFactory.getLogger(AccountRecordController.class);

    @Autowired
    private AccountRecordService service;

    @PostMapping
    public ResultBean<AccountRecordDTO> create(@RequestBody AccountRecordDTO dto) {
        return ResultBean.success(service.create(dto));
    }

    @PutMapping(value = "/{id:\\d+}")
    public ResultBean<AccountRecordDTO> update(
        @PathVariable(name = "id") long id,
        @RequestBody AccountRecordDTO dto
    ){
        dto.setId(id);
        return ResultBean.success(service.updateMoney(dto));
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public ResultBean<AccountRecordDTO> delete(
        @PathVariable(name = "id") Long id
    ){
        service.delete(id);
        return ResultBean.success();
    }

}
