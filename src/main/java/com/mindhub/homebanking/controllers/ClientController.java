package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    private Supplier<ClientDto> aNew;



    @RequestMapping("/clients")
    public Set<ClientDto> getAllClients(){
        return this.clientRepository.findAll().stream().map(ClientDto::new).collect(Collectors.toSet());
    }
    @RequestMapping("/clients/{id}")
    public ResponseEntity<Map<String, Object>> getClient(@PathVariable Long id){
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> client = new HashMap<>();

        error.put("error", "no existe el cliente id"+id.toString());
        if(this.clientRepository.findById(id).orElse(null)==null){
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        client.put("client", this.clientRepository.findById(id).get()); //<- Si no indico que debuelva un cliente dto, toma de cliente repository y genera la recursiviad.
            return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }
}
