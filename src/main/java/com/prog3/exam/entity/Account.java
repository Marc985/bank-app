package com.prog3.exam.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prog3.exam.idgenerator.UniqueNumberGenerator;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

 private    Long accountNumber=UniqueNumberGenerator.generateUniqueId();
  private String clientName;
   private String clientLastName;
   private Timestamp birthdate;
   private double monthlyNetIncome;
   private boolean isEligible;
   private String user_id;

    public Account(String clientName, String clientLastName, Timestamp birthdate, double monthlyNetIncome, boolean isEligible, String user_id) {
        this.clientName=clientLastName;
        this.clientLastName=clientLastName;
        this.birthdate=birthdate;
        this.monthlyNetIncome=monthlyNetIncome;
        this.isEligible=isEligible;
        this.user_id=user_id;
    }
public boolean getIsEligible(){
        return isEligible;
}

}
