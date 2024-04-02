package com.prog3.exam.service;

import com.prog3.exam.entity.Client;
import com.prog3.exam.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    public String createClient(Client client){
        LocalDate date=LocalDate.now();
        LocalDate birthdate= client.getBirthdate().toLocalDate();
        long age= Math.abs(ChronoUnit.YEARS.between(birthdate,date));
        if(age>=21){
            clientRepository.save(client);
            return "you can create account now";
        }
        else
            return "you must be at least 21 years old to create an account";

    }
}
