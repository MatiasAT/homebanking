package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClientDto {

    private long id;
    private String firstName, lastName, email;
    public Set<AccountDto> accounts;
    public Set<ClientLoanDto> loans;
    public Set<CardDto> cards;

    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccountSet().stream().map(AccountDto::new).collect(Collectors.toSet());
        this.loans = client.getLoanSet().stream().map(ClientLoanDto::new).collect(Collectors.toSet());
        this.cards = client.getCardSet().stream().filter(Card -> Card.isDisabled()==false).map(CardDto::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDto> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDto> getLoans() {
        return loans;
    }

    public Set<CardDto> getCards() {
        return cards;
    }
}

