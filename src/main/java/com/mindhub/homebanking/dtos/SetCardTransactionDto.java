package com.mindhub.homebanking.dtos;


import java.time.LocalDateTime;

public class SetCardTransactionDto {

    private String number, cvv, description;
    private double amount;
    private LocalDateTime transactionTime;

    SetCardTransactionDto(){}

    SetCardTransactionDto(String number, String cvv, double amount, String description){
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
        this.transactionTime = LocalDateTime.now();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

}
