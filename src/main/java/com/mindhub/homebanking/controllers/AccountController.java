package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.UtilsAccount.newAccountNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    private String number;

    @GetMapping("/accounts")
    public Set<AccountDto> getAllTransactions() {
        return this.accountRepository.findAll().stream().map(AccountDto::new).collect(Collectors.toSet());
    }
    @GetMapping("/accounts/{id}")
    public AccountDto getAccount (@PathVariable Long id){
        return this.accountRepository.findById(id).map(AccountDto::new).orElse(null);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDto> getAccounts(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getAccountSet().stream().map(AccountDto::new).collect(toList());
    }

    @PostMapping("/clients/current/accounts")

    public ResponseEntity<Object> newAccount(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccountSet().size() > 2) {
            return new ResponseEntity<>("you have exceeded the limit of allowed accounts", HttpStatus.FORBIDDEN);}

        //System.out.print(newAccountNumber());
        // Llama al metodo newAccountNumer() y retorna el numero de cuenta valido.
        //System.out.print(newAccountNumber(accountRepository));


        Account newAccount = new Account(newAccountNumber(accountRepository), LocalDateTime.now(), 0, AccountType.CORRIENTE);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
