package com.prog3.exam.controller;

import com.prog3.exam.entity.Transfert;
import com.prog3.exam.service.TransfertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplyAccountController {
    @Autowired
    TransfertService transfertService;
    @PostMapping("/account/supply")
    public String performSupply(@RequestBody  Transfert transfert){
        return transfertService.supplyAccount(transfert.getRecipientAccount(),transfert.getAmount(),transfert.getRegistrationDate(),transfert.getEffectiveDate(),transfert.getReason(),transfert.getIdCategory());
    }
}
