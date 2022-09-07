package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.UtilsAccount.newAccountNumber;
import static com.mindhub.homebanking.utils.UtilsCard.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;


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



}
