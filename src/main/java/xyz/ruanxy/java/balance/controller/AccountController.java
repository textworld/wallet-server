package xyz.ruanxy.java.balance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ruanxy.java.balance.payload.AccountDTO;
import xyz.ruanxy.java.balance.payload.AccountRecordDTO;
import xyz.ruanxy.java.balance.payload.ResultBean;
import xyz.ruanxy.java.balance.payload.ResultListBean;
import xyz.ruanxy.java.balance.service.AccountRecordService;
import xyz.ruanxy.java.balance.service.AccountService;

@RestController
@RequestMapping(value = "/api/v1/account")
@CrossOrigin
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRecordService accountRecordService;

    @PostMapping
    public ResultBean<AccountDTO> create(@RequestBody AccountDTO dto) {
        return ResultBean.success(accountService.create(dto));
    }

    @PutMapping(value = "/{id:\\d+}")
    public ResultBean update(@PathVariable(name = "id") long id, @RequestBody AccountDTO dto) {
        accountService.update(id, dto);
        return ResultBean.success();
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public ResultBean delete(@PathVariable(name = "id") long id) {
        accountService.delete(id);
        return ResultBean.success("删除成功");
    }

    @GetMapping(value = "/{id:\\d+}/record")
    public ResultListBean<AccountRecordDTO> getRecords(
        @PathVariable(name = "id") long accountId
    ){
        return ResultListBean.success(accountRecordService.getByAccount(accountId));
    }

    @GetMapping()
    public ResultListBean<AccountDTO> getAll(){
        return ResultListBean.success(accountService.getAll());
    }
}
