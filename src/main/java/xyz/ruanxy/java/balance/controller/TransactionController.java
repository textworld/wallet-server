package xyz.ruanxy.java.balance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ruanxy.java.balance.payload.TransactionDTO;
import xyz.ruanxy.java.balance.service.TransactionService;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
    private final static Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public TransactionDTO begin(){
        return transactionService.begin();
    }
}
