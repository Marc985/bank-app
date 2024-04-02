package com.prog3.exam.controller;

import com.prog3.exam.entity.Client;
import com.prog3.exam.repository.ClientRepository;
import com.prog3.exam.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;
    @GetMapping("/client/{idClient}")
public Client getById(@PathVariable String idClient){
    return clientRepository.findById(idClient);
}
@PostMapping("/client")
    public String addClient(@RequestBody  Client client){
        return clientService.createClient(client);
}
@PutMapping("/client/{idClient}")
    public Client updateClient(@PathVariable String idClient,@RequestBody Client client){
        return clientRepository.updateClientById(idClient,client);
}
}
