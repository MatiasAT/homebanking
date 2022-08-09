package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;

public class AccountsDto {

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    public AccountsDto(){};

    public AccountsDto(Account account){
        this.id= account.getId();
        this.number= account.getNumber();
        this.creationDate= account.getCreationDate();
        this.balance= account.getBalance();

    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

}
