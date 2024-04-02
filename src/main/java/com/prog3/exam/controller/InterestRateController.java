package com.prog3.exam.controller;

import com.prog3.exam.entity.InterestRate;
import com.prog3.exam.repository.InterestRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestRateController {
    @Autowired
    InterestRateRepository interestRateRepository;
    @PutMapping("/interestRate")
    public String updateInterestRate(@RequestBody  InterestRate interestRate){
         return    interestRateRepository.
                    updateInterestRate(interestRate.getFirst7days(),
                            interestRate.getAfter7days());
    }
}
