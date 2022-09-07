package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.UtilsAccount.newAccountNumber;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/clients")
    public Set<ClientDto> getAllClients(){
        return this.clientRepository.findAll().stream().map(ClientDto::new).collect(Collectors.toSet());
    }

    @GetMapping("/clients/{id}")
    public ClientDto getAccount (@PathVariable Long id){
        return this.clientRepository.findById(id).map(ClientDto::new).orElse(null);
    }

    @GetMapping("/clients/current")
    public ClientDto getAuthenticationClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return new ClientDto(client);
    }


    @PostMapping("/clients")

    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password ) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client clientNew = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(clientNew);

        Account newAccount = new Account(newAccountNumber(accountRepository), LocalDateTime.now(), 0, AccountType.AHORRO);
        clientNew.addAccount(newAccount);

        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
