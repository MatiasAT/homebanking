package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Loan {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private double maxAmount;

    @OneToMany(mappedBy ="loan", fetch = FetchType.EAGER)
    private List<ClientLoan> loanList = new ArrayList<>();
    @ElementCollection
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();


    @Column(name="loanRate")
    private Double loanRate;

    public Loan(){}

    public Loan(String name, double maxAmount, List payments, Double loanRate){
         this.maxAmount = maxAmount;
         this.payments = payments;
         this.loanRate = loanRate;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<ClientLoan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<ClientLoan> loanList) {
        this.loanList = loanList;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(Double loanRate) {
        this.loanRate = loanRate;
    }

}
