package com.prog3.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfert implements Serializable {
   private String reference;
   private String reason;
   private double amount;
   private Date effectiveDate;
   private Date registrationDate;
   private String status;
   private long senderAccount;
   private long recipientAccount;
   private int idCategory;
}
