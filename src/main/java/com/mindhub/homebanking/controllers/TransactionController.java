package com.mindhub.homebanking.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.mindhub.homebanking.utils.UtilsTransaction.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    @PostMapping("/transactions")

    public ResponseEntity<Object> transactions(
            @RequestParam double amount,              @RequestParam String description,
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication ) {


        Client currentClient = clientRepository.findByEmail(authentication.getName());
        Account fromValidAccount = accountRepository.findByNumber(fromAccountNumber);
        Account fromClientAccount = accountRepository.findByClientAndNumber(currentClient, fromValidAccount.getNumber());
        Account toAccount = accountRepository.findByNumber(toAccountNumber); // si es una cuenta desitno de terceros?
        boolean unavailableBalance = fromClientAccount.getBalance()<amount;
        double fromAmount = Math.abs(amount);

        //System.out.print(" description ");
        if(fromAccountNumber.isEmpty()||toAccountNumber.isEmpty()||amount<0||description.isEmpty()){
           return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("The destination account must be different", HttpStatus.FORBIDDEN);
        }

        if(fromValidAccount == null){
            //System.out.print("test OK");
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(fromClientAccount == null){
           return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(toAccount == null){
            return new ResponseEntity<>("Missing date", HttpStatus.FORBIDDEN);
        }



        if(unavailableBalance){
            return new ResponseEntity<>("Unavailable balance", HttpStatus.FORBIDDEN);
        }

        newTransaction(description, fromAmount, fromClientAccount, toAccount, transactionRepository, accountRepository);

        return new ResponseEntity<>("Accepted transaction", HttpStatus.ACCEPTED);
    }


}
