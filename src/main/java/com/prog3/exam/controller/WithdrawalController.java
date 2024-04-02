package com.prog3.exam.controller;

import com.prog3.exam.entity.Withdrawal;
import com.prog3.exam.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;

    @PostMapping("/account/{id}/withdrawals")
    public String withdrawalSold(@PathVariable long id,@RequestBody Withdrawal withdrawal){
    return     withdrawalService
                .makeWithdrawal(id,withdrawal.getReason(),withdrawal.getAmount(),withdrawal.getCategory());

    }
}
