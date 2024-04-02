package com.prog3.exam.controller;

import com.prog3.exam.entity.AccountStatement;
import com.prog3.exam.repository.AccountStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class AccountStatementController {
    @Autowired
    AccountStatementRepository accountStatementRepository;
    @GetMapping("/statement/{accountNumber}")
    public List<AccountStatement> getALl(@PathVariable long accountNumber, @RequestParam Date beginDate,@RequestParam Date endDate){
        return accountStatementRepository.getAccountStatement(accountNumber,beginDate,endDate);
    }
    @GetMapping("/statementReference/{reference}")
    public AccountStatement getByReference(@PathVariable String reference){
        return accountStatementRepository.getByReference(reference);
    }
}
