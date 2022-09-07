package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDto {

    private TransactionType type;
    private long id;
    private  String description;
    private LocalDateTime date;
    private double amount;

    private double currentAmount;

    public TransactionDto (){};
    public TransactionDto (Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.currentAmount = transaction.getCurrentAmount();
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

}
