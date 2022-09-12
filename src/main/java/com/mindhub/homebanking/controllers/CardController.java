package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.dtos.SetCardTransactionDto;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.UtilsAccount.newAccountNumber;
import static com.mindhub.homebanking.utils.UtilsCard.*;
import static com.mindhub.homebanking.utils.UtilsTransaction.debitTransaction;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;


    @PostMapping("/clients/current/cards")

    public ResponseEntity<Object> addNewCard(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){

        Client clientCurrent = clientRepository.findByEmail(authentication.getName());

        if(cardRepository.findByClientAndCardType(clientCurrent, cardType).size() >= 3){
            return new ResponseEntity<>("you have exceeded the limit of allowed accounts", HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(clientCurrent.getFirstName()+ " " +clientCurrent.getLastName(), cardColor, cardType, cardNumber(cardRepository), cvvNumber(cardRepository),LocalDateTime.now(), LocalDateTime.now().plusYears(5),clientCurrent);
        cardRepository.save(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/current/cards")
    public ResponseEntity<Object> deleteCard(@RequestParam String number, Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        Card cardDeleted = cardRepository.findByClientAndNumber(clientCurrent, number);

        if(cardRepository.findByClientAndNumber(clientCurrent, number) == null){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        cardDeleted.setDisabled(true);
        cardRepository.save(cardDeleted);
        return new ResponseEntity<>("Card deleted successfully", HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/cardTransaction")
    public ResponseEntity<Object> setCardTransaction(@RequestBody SetCardTransactionDto setCardTransactionDto){

        Card card = cardRepository.findByNumberAndCvv(setCardTransactionDto.getNumber(),setCardTransactionDto.getCvv());
        boolean cardNotFound = card == null;
        boolean disabledCard = card.isDisabled();

        if (cardNotFound){
            return new ResponseEntity<>("Card not found", HttpStatus.FORBIDDEN);
        }

        if(disabledCard){
            return new ResponseEntity<>("Disable Card",HttpStatus.FORBIDDEN);
        }

        boolean cardExpired = LocalDateTime.now().isAfter(card.getThruDate());
        System.out.print(cardExpired);

        if(cardExpired){
            return new ResponseEntity<>("Expired card",HttpStatus.FORBIDDEN);
        }

        Client client = card.getClient();
        Account account = client.getAccountSet().stream().max(Comparator.comparingDouble(Account::getBalance)).get();

        if(account.getBalance()< setCardTransactionDto.getAmount()){
            return new ResponseEntity<>("exceeded its limit",HttpStatus.FORBIDDEN);
        }

        String description = setCardTransactionDto.getDescription() + " || " + " Card: " + setCardTransactionDto.getNumber();
        debitTransaction(description, setCardTransactionDto.getAmount(), account, transactionRepository, accountRepository);
        System.out.print(account.getBalance());

        return new ResponseEntity<>("Successful operation",HttpStatus.ACCEPTED);
    }

}
