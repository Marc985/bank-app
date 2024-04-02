package com.prog3.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client implements Serializable {
   private String idClient=UUID.randomUUID().toString();
   private String firstName;
   private String lastName;
   private Date birthdate;
   private double monthlyNetSalary;

}
