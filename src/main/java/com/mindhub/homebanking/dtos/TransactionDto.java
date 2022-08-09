package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.TransactionType;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class TransactionDto {

    private long id;
    private  String description;
    private LocalDateTime date;
    private double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Account_id")
    private com.mindhub.homebanking.models.Account Account;
    private TransactionType type;

    public TransactionDto (){}

    public TransactionDto (TransactionType type, String description, LocalDateTime date, double amount){
        this.type = type;
        this.description = description;
        this.date = date;
        this.amount = amount;
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

    public com.mindhub.homebanking.models.Account getAccount() {
        return Account;
    }

    public TransactionType getType() {
        return type;
    }
}
