package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private  String description;
    private LocalDateTime date;
    private double amount;
    private double currentAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private TransactionType type;

    public Transaction (){}

    public Transaction (TransactionType type, String description, LocalDateTime date, double amount, Account account, double currentAmount){
        this.type = type;
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.account = account;
        this.currentAmount = currentAmount;
    }

    public long getId(){
        return id;
    }

    public TransactionType getType(){
        return type;
    }
    public void setType(TransactionType type){
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccounts() {
        return account;
    }

    public void addAccounts(Account accounts) {
        account = accounts;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }
}
