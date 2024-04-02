package com.prog3.exam.controller;

import com.prog3.exam.entity.Sold;
import com.prog3.exam.entity.SoldWithLoan;
import com.prog3.exam.repository.SoldRepository;
import com.prog3.exam.service.SoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class SoldController {
    @Autowired
    SoldRepository soldRepository;
    @Autowired
    SoldService soldService;
    public void addSold(Sold sold){
        soldRepository.save(sold);
    }


    @GetMapping("/sold/{idAccount}")
    public Sold soldByDate(@PathVariable long idAccount, @RequestParam("date") Date date){
        return  soldService.getSoldByDate(idAccount,date);
    }

    @GetMapping("/soldAndLoan/{idAccount}")
      public SoldWithLoan getSolds(@PathVariable long idAccount){
        return soldService.getCurrentSolds(idAccount);
    }

    @GetMapping("/last/{id}")
    public Sold sold(@PathVariable long id){
        return soldRepository.findLastSoldByIdAccount(id);
    }
}
