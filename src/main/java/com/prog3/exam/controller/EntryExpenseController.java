package com.prog3.exam.controller;

import com.prog3.exam.entity.EntryExpense;
import com.prog3.exam.repository.EntryExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController

public class EntryExpenseController {
    @Autowired
    EntryExpenseRepository entryExpenseRepository;

    @GetMapping("entryExpense/{accountNumber}")
    public EntryExpense getEntryExpenseSum(@PathVariable long accountNumber, @RequestParam Date startDate,@RequestParam Date endDate){
        return entryExpenseRepository.getEntryExepenseSum(accountNumber,startDate,endDate);
    }
}
