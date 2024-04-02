package com.prog3.exam.controller;

import com.prog3.exam.entity.Account;
import com.prog3.exam.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;


   @GetMapping("/accounts/{idClient}")
    public List<Account> getAccounts(@PathVariable String idClient){
        return  accountRepository.findAllByIdClient(idClient);
    }


    @PostMapping("/account")
    public Account createAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }
@GetMapping("/account/{id}")
    public Account findById(@PathVariable  long id){
       return accountRepository.findAccountById(id);
}
@PutMapping("/account/{idAccount}/eligibility")
    public String setEligibility(@PathVariable float idAccount,@RequestParam boolean isEligible){
       return accountRepository.updateEligibility(idAccount,isEligible);
}
}
