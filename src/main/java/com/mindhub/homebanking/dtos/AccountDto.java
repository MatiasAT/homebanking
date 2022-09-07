package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDto {

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private Set<TransactionDto> transactions;

    private AccountType accountType;
    public AccountDto(){};

    public AccountDto(Account account){
        this.id= account.getId();
        this.number= account.getNumber();
        this.creationDate= account.getCreationDate();
        this.balance= account.getBalance();
        this.transactions = account.getTransactionSet().stream().map(TransactionDto::new).collect(Collectors.toSet());
        this.accountType = account.getAccountType();
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

    public Set<TransactionDto> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
