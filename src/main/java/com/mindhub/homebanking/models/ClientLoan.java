package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "nati", strategy = "native")
    private long id;
    private double amount;
    private int payment;

    private  double availableAmount;

    @ManyToOne
    @JoinColumn(name="clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name="loanId")
    private Loan loan;

    public ClientLoan(){}

    public ClientLoan(double amount, int payment, Client client, Loan loan){
        this.amount = amount;
        this.payment = payment;
        this.client = client;
        this.loan = loan;
        this.availableAmount = loan.getMaxAmount();
    }



    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }
}
