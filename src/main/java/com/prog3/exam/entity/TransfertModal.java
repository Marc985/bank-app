package com.prog3.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransfertModal {
   private String reference;
   private long senderAccount;
   private  String reason;
   private double amount;
   private Date registerDate;
   private Date effecitveDate;
   private String status;
   private int idCategory;

}
