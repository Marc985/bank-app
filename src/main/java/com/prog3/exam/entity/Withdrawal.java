package com.prog3.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Withdrawal implements Serializable {
   private double amount;
   private String reason;
   private Date date;
   private int category;
}
